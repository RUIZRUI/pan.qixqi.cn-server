package cn.qixqi.pan;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.naming.NamingException;

import com.alibaba.fastjson.JSON;

import cn.qixqi.pan.entity.PanMessage;
import cn.qixqi.pan.util.MessageUtil;


/**
 * todo
 * 1. 消息的内容msg应该扩大长度，或者更改数据类型
 */
@WebServlet("/Messages")
public class Messages extends HttpServlet{
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        // 处理请求乱码
        request.setCharacterEncoding("utf-8");

        // 处理响应乱码
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();

        String message;

        try{
            // 接收数据
            String method = request.getParameter("method");
            if("add".equals(method)){
                int msgId = (int)((Math.random()*9+1)*1000000);     // 7位随机数
                PanMessage addMessage = JSON.parseObject(request.getParameter("message"), PanMessage.class);
                addMessage.setMsgId(msgId);
                boolean flag = MessageUtil.add(addMessage);
                if(flag){
                    message = JSON.toJSONString(addMessage);
                }else{
                    message = "null";
                }
            
            }else if("searchAll".equals(method)){
                List<PanMessage> messageList;
                int userId1 = Integer.parseInt(request.getParameter("userId1"));
                if(request.getParameter("userId2") == null){
                    messageList = MessageUtil.searchAll(userId1);
                }else{
                    int userId2 = Integer.parseInt(request.getParameter("userId2"));
                    messageList = MessageUtil.searchAll(userId1, userId2);
                }
                if(messageList != null){
                    message = JSON.toJSONString(messageList);
                }else{
                    message = "empty";
                }
            }else{
                message = "你搞的什么操作，我不晓得";
            }
            out.println(message);
            System.out.println("message: " + message);      // 输出日志
        } catch(NamingException ne){
            System.out.println("操作失败: NamingException " + ne.getMessage());
            // out.println("<h3 align='center'>操作失败: NamingException " + ne.getMessage() + "</h3>\n");
            out.println("error");
            ne.printStackTrace();
        } catch(SQLException se){
            System.out.println("操作失败: SQLException " + se.getMessage());
            // out.println("<h3 align='center'>操作失败: SQLException " + se.getMessage() + "</h3>\n");
            out.println("error");
            se.printStackTrace();
        } catch(Exception e){
            System.out.println("操作失败: Exception" + e.getMessage());
            out.println("error");
            e.printStackTrace();
        }



    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        doGet(request, response);
    }

}   