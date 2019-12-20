package club.qixqi.qq;

import java.io.PrintWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import club.qixqi.qq.entity.Sessions;
import club.qixqi.qq.util.SessionsUtil;

import com.alibaba.fastjson.JSON;


@WebServlet("/SessionServlet")
public class SessionServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        // 定义时间格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 处理请求乱码
        request.setCharacterEncoding("utf-8");

        // 处理响应乱码
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();

        String message;

        try{
            // 接收数据
            String method = request.getParameter("method");
            if("create".equals(method)){
                // todo 获取userId1和userId2判断
                int userId1 = Integer.parseInt(request.getParameter("userId1"));
                String username1 = request.getParameter("username1");
                int userId2 = Integer.parseInt(request.getParameter("userId2"));
                int chatId = (int)((Math.random()*9+1)*1000000);    // 7位随机数
                Sessions sessions = new Sessions(userId1, chatId, userId1, userId2, "我们已经是好友了，快来聊天吧！", username1, 
                    df.format(new Date()), 'w', 'f');
                boolean flag = SessionsUtil.add(sessions);
                if(flag){
                    message = JSON.toJSONString(sessions);
                }else{
                    message = "null";
                }
            }else if("searchAll".equals(method)){
                // todo 获取userId判断
                int userId = Integer.parseInt(request.getParameter("userId"));
                List<Sessions> sessionsList = SessionsUtil.searchAll(userId);
                if(sessionsList != null){
                    message = JSON.toJSONString(sessionsList);
                }else{
                    message = "empty";
                }
            }else{
                message = "你搞的什么操作，我不晓得";
            }
            out.println(message);
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
