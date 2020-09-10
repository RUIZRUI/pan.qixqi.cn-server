package cn.qixqi.pan;

import java.util.List;
import java.io.PrintWriter;
import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import cn.qixqi.pan.util.FriendsUtil;
import cn.qixqi.pan.entity.Friend;

import com.alibaba.fastjson.JSON;



@WebServlet("/Friends")
public class Friends extends HttpServlet{
    private static final long serialVersionUId = 1L;

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
            int userId1 = -1;
            int userId2 = -1;
            boolean flag = false;
            if(! "searchAll".equals(method)){
                if(request.getParameter("userId1") != null){
                    userId1 = Integer.parseInt(request.getParameter("userId1"));
                }
                if(request.getParameter("userId2") != null){
                    userId2 = Integer.parseInt(request.getParameter("userId2"));
                }
                if("add".equals(method)){
                    flag = FriendsUtil.add(userId1, userId2);
                    message = (flag) ? "yes":"no";
                }else if("delete".equals(method)){
                    flag = FriendsUtil.delete(userId1, userId2);
                    message = (flag) ? "yes":"no";
                }else if("search".equals(method)){
                    flag = FriendsUtil.search(userId1, userId2);
                    message = (flag) ? "yes":"no";
                }else{
                    // out.println("<h2 align='center'>你搞得神马鬼操作，我不晓得呀</h2>\n");
                    message = "你搞得神马鬼操作，我不晓得呀";
                }
            }else{
                if(request.getParameter("userId1") != null){
                    userId1 = Integer.parseInt(request.getParameter("userId1"));
                }
                List<Friend> friend_list = FriendsUtil.searchAll(userId1);
                if(friend_list.isEmpty()){
                    message = "empty";
                }else{
                    message = JSON.toJSONString(friend_list);
                }
            }
            // out.println("<h2 align='center'>" + message + "</h2>");
            out.println(message);
        } catch(NamingException ne){
            System.out.println("操作失败: NamingException " + ne.getMessage());
            out.println("<h3 align='center'>操作失败: NamingException " + ne.getMessage() + "</h3>\n");
            ne.printStackTrace();
        } catch(SQLException se){
            System.out.println("操作失败: SQLException " + se.getMessage());
            out.println("<h3 align='center'>操作失败: SQLException " + se.getMessage() + "</h3>\n");
            se.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        doGet(request, response);
    }

}