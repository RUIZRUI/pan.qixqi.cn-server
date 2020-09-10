package cn.qixqi.pan;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnOpen;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnError;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint("/websocket")
public class WebSocketTest{
    // 当前在线连接数，线程安全
    private static int onlineCount = 0;

    // 存放每个客户端对应的WebSocket对象
    private static CopyOnWriteArraySet<WebSocketTest> websocketSet = new CopyOnWriteArraySet<WebSocketTest>();

    // 与某个客户端的连接会话，通过它给客户端发送数据
    private Session session;


    /**
     * 成功建立连接
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        websocketSet.add(this);
        addOnlineCount();       // 在线人数加一
        System.out.println("有新连接加入！当前在线人数为：" + getOnlineCount());
    }


    /**
     * 关闭连接
     */
    @OnClose
    public void onClose() {
        websocketSet.remove(this);
        subOnlineCount();   // 在线人数减一
        System.out.println("有一连接关闭！当前在线人数为：" + getOnlineCount());
    }


    /**
     * 收到客户端信息
     */
    @OnMessage
    public void onMessage(String message, Session session){
        System.out.println("来自客户端的消息：" + message);
        for(WebSocketTest item : websocketSet){
            try{
                item.sendMessage(message);
            } catch(IOException e){
                e.printStackTrace();
                continue;
            }
        }
    }


    /**
     * 发生错误
     * @param session
     * @param t
     */
    @OnError
    public void onError(Session session, Throwable t) {
        System.out.println("发送错误" + t.getMessage());
        t.printStackTrace();
    }



    /**
     * 自定义方法
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        this.session.getBasicRemote().sendText(message);
    }

    
    public static synchronized int getOnlineCount(){
        return onlineCount;
    }

    public static synchronized void addOnlineCount(){
        WebSocketTest.onlineCount ++;
    }

    public static synchronized void subOnlineCount(){
        WebSocketTest.onlineCount --;
    }

}
