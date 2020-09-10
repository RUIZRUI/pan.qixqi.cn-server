package cn.qixqi.pan;

import java.io.IOException;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.websocket.OnOpen;  
import javax.websocket.OnMessage;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.qixqi.pan.entity.Message;
import cn.qixqi.pan.util.MessageUtil;

/**
 * todo
 * 1. 限制用户不能重复登录
 */

@ServerEndpoint("/MessageSocket/{userId}")
public class MessageSocket{
    // 当前连接数，线程安全
    private static int onlineCount = 0;

    // 线程安全
    private static ConcurrentMap<Integer, MessageSocket> socketList = new ConcurrentHashMap<Integer, MessageSocket>();

    // 与某个客户端的连接会话，通过它给客户端发送数据
    private Session session;


    /**
     * 连接建立时调用
     * @param session
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") int userId){
        if(socketList.containsKey(userId)){
            return;
        }
        this.session = session;
        socketList.put(userId, this);
        addOnlineCount();
        System.out.println("有新连接加入！userId = " + userId + "   当前在线人数为：" + getOnlineCount() + "    size = " + socketList.keySet().size());
    }

    /**
     * 连接关闭时调用
     */
    @OnClose
    public void onClose(@PathParam("userId") int userId){
        socketList.remove(userId);
        subOnlineCount();
        System.out.println("有一连接关闭！" + userId + "     当前在线人数为：" + getOnlineCount());
    }

    /**
     * 接收到客户端信息时调用
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("userId") int userId){
        System.out.println("来自userId=" + userId + " 的消息 " + message);
        String response;    // 传回客户端的响应数据
        JSONObject responseJson = new JSONObject();
        boolean responseFlag = true;       // 是否传回数据，处理心电包
        try{
            JSONObject jsonObject = JSON.parseObject(message);
            String method = jsonObject.getString("method");
            if("heart".equals(method)){
                response = "heart";
                responseJson.put("response", response);
                response = responseJson.toJSONString();
                responseFlag = false;
            }else if("add".equals(method)){
                JSONObject requestJson1 = new JSONObject(); // 响应的request
                requestJson1.put("method", "addResponse");
                responseJson.put("request", requestJson1);
                int msgId = (int)((Math.random()*9+1)*1000000);     // 7位随机数
                Message addMessage = JSON.parseObject(jsonObject.getString("message"), Message.class);
                addMessage.setMsgId(msgId);
                boolean flag = MessageUtil.add(addMessage);
                if(flag){
                    // response = JSON.toJSONString(addMessage);       // 返回给发送方的消息
                    response = Integer.toString(msgId);
                    responseJson.put("response", response);
                    response = responseJson.toJSONString();

                    pushToReceiver(addMessage);           // 推送消息给接收方
                }else{
                    response = "null";
                    responseJson.put("response", response);
                    response = responseJson.toJSONString();
                }
            }else if("searchAll".equals(method)){
                responseJson.put("request", jsonObject);
                List<Message> messageList;
                int userId1 = jsonObject.getIntValue("userId1");
                if(jsonObject.containsKey("userId2")){
                    int userId2 = jsonObject.getIntValue("userId2");
                    messageList = MessageUtil.searchAll(userId1, userId2);
                }else{
                    messageList = MessageUtil.searchAll(userId1);
                }
                if(messageList != null){
                    response = JSON.toJSONString(messageList);
                }else{
                    response = "empty";
                }
                responseJson.put("response", response);
                response = responseJson.toJSONString();
            }else{
                response = "你搞的什么操作，我不晓得";
                responseJson.put("response", response);
                response = responseJson.toJSONString();
            }
            if(responseFlag){
                this.sendMessage(response);
                System.out.println("response: " + response);
            }
        } catch(NamingException ne){
            System.out.println("操作失败: NamingException " + ne.getMessage());
            // out.println("<h3 align='center'>操作失败: NamingException " + ne.getMessage() + "</h3>\n");
            // out.println("error");
            response = "error";
            responseJson.put("response", response);
            response = responseJson.toJSONString();
            this.sendMessage(response);
            ne.printStackTrace();
        } catch(SQLException se){
            System.out.println("操作失败: SQLException " + se.getMessage());
            // out.println("<h3 align='center'>操作失败: SQLException " + se.getMessage() + "</h3>\n");
            // out.println("error");
            response = "error";
            responseJson.put("response", response);
            response = responseJson.toJSONString();
            this.sendMessage(response);
            se.printStackTrace();
        } catch(Exception e){
            System.out.println("操作失败: Exception" + e.getMessage());
            // out.println("error");
            response = "error";
            responseJson.put("response", response);
            response = responseJson.toJSONString();
            this.sendMessage(response);
            e.printStackTrace();
        }
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable t){
        System.out.println("发生错误" + t.getMessage());
        t.printStackTrace();
    }

    

    /**
     * 后台推送消息给某个客户端
     * @param message
     */
    public void sendMessage(String message){
        try{
            this.session.getBasicRemote().sendText(message);
        } catch(IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    
    /**
     * 推送消息给接收方
     * @param addMessage 发送的消息
     */
    public void pushToReceiver(Message addMessage){
        JSONObject pushJson = new JSONObject();     // 推送
        JSONObject requestJson2 = new JSONObject(); // 推送的request
        requestJson2.put("method", "addPush");
        pushJson.put("request", requestJson2);
        pushJson.put("push", JSON.toJSONString(addMessage));

        int toId = addMessage.getToId();
        socketList.get(toId).sendMessage(pushJson.toJSONString());
    }




    // synchronized 保证某一个时刻只有一个线程执行该方法
    public static synchronized int getOnlineCount(){
        return onlineCount;
    }

    public static synchronized void addOnlineCount(){
        MessageSocket.onlineCount ++;
    }

    public static synchronized void subOnlineCount(){
        MessageSocket.onlineCount --;
    }
}
