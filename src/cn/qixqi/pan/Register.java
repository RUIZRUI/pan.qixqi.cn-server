package cn.qixqi.pan;

import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.naming.NamingException;

import cn.qixqi.pan.entity.User;
import cn.qixqi.pan.util.UserUtil;
import cn.qixqi.pan.entity.FileLink;
import cn.qixqi.pan.util.FileLinkUtil;


import com.alibaba.fastjson.JSON;


@WebServlet("/Register")
public class Register extends HttpServlet{
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        // 定义日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 处理请求乱码
        request.setCharacterEncoding("utf-8");

        //  处理响应乱码
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();

        // 接收请求数据
        int id = (int)((Math.random()*9+1)*100000);
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String sex = request.getParameter("sex");
        String phone_num = request.getParameter("phone_num");
        Date now = new Date();
        // 处理null
        if(username == null || password == null || sex == null || phone_num == null){
            out.println("请正确输入信息");
            return;
        }
        // 处理空串
        if(username =="" || password == "" || sex == "" || phone_num == ""){
            out.println("请正确输入信息");
            return;
        }
        try{
            // Context cxt = new InitialContext();
            // DataSource ds = (DataSource) cxt.lookup("java:comp/env/jdbc/qq");
            // Connection conn = ds.getConnection();
            
            // // 插入数据
            // String sql = "insert into qquser(id, username, password, sex, phone_num, register_time, last_login_time) values (?, ?, ?, ?, ?, ?, ?)";
            // PreparedStatement pst = conn.prepareStatement(sql);
            
            // out.println("3");
            
            // pst.setInt(1, id);
            // pst.setString(2, username);
            // pst.setString(3, password);
            // pst.setString(4, sex);
            // pst.setString(5, phone_num);
            // pst.setString(6, df.format(now));
            // pst.setString(7, df.format(now));
            // pst.executeUpdate();


            User user = new User(id, username, password, sex.toCharArray()[0], phone_num, df.format(now), df.format(now));
            if(UserUtil.add(user)){
                // out.println("success");
                User newUser = UserUtil.loginSearch(id, password);
                if(newUser != null){ 
                    // 注册登录成功后，创建用户的根文件夹
                    int linkId = (int)((Math.random()*9+1)*1000000);    // 7位随机整数
                    String createLinkTime = df.format(new Date());
                    FileLink rootFolder = new FileLink(linkId, id, -1, null, null, -1, 'y', username, "", "", 'y', -1, createLinkTime);
                    if(FileLinkUtil.add(rootFolder)){   // 新建根文件夹成功
                        out.println(JSON.toJSONString(newUser));        
                    }else{  // 新建根文件夹失败
                        out.println("error");
                        System.out.println("新建根文件夹失败");
                    }
                }else{
                    out.println("null");
                }
            }else{
                out.println("error");
            }
        } catch (NamingException ne){
            System.out.println("Register failed: NamingException " + ne.getMessage());
            // out.println("Register failed: NamingException " + ne.getMessage());
            out.println("error");
            ne.printStackTrace();
        } catch (SQLException se){
            System.out.println("Register failed: SQLException " + se.getMessage());
            // out.println("Register failed: SQLException " + se.getMessage());
            out.println("error");
            se.printStackTrace();
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doGet(request, response);
    }
}