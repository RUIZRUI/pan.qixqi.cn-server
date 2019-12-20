package club.qixqi.qq;

import java.io.PrintWriter;
import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import club.qixqi.qq.util.UserUtil;
import club.qixqi.qq.entity.User;
import club.qixqi.qq.entity.Friend;
import club.qixqi.qq.util.FriendsUtil;

import com.alibaba.fastjson.JSON;


/**
 * todo
 * 1. 陌生人搜索时首先判断是不是自己的好友
 */

@WebServlet("/Users")
public class Users extends HttpServlet{

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
            if("strangerSearch".equals(method)){
                String content = request.getParameter("content");
                if(content == null ||  "".equals(content)){
                    message = "请正确输入信息";
                }else{
                    User user = null;
                    if(content.length() == 11){
                        user = UserUtil.strangerSearch(content);
                        message = (user==null)? "null" : JSON.toJSONString(user) ;
                    }else if(content.length() == 6){
                        user = UserUtil.strangerSearch(Integer.parseInt(content));
                        message = (user==null)? "null" : JSON.toJSONString(user) ;
                    }else{
                        message = "请正确输入信息";
                    }
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
        }


    }




    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        doGet(request, response);
    }

}