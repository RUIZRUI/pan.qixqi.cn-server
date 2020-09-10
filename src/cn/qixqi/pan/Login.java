package cn.qixqi.pan;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.naming.NamingException;

import cn.qixqi.pan.entity.User;
import cn.qixqi.pan.util.UserUtil;

import com.alibaba.fastjson.JSON;


@WebServlet("/Login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 处理请求乱码
        request.setCharacterEncoding("utf-8");

        // 处理响应乱码
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();

        // 设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 接收请求数据
        if(request.getParameter("id")==null || request.getParameter("password")==null){
            out.println("请正确输入信息");
            return;
        }
        if(request.getParameter("id") == "" || request.getParameter("password") == ""){
            out.println("请正确输入信息");
            return;
        }
        int id = Integer.parseInt(request.getParameter("id"));
        String password = request.getParameter("password");

        try{
            // Context cxt = new InitialContext();
            // DataSource ds = (DataSource) cxt.lookup("java:comp/env/jdbc/qq");
            // Connection conn = ds.getConnection();

            // String sql = "select * from qquser where id = ? and password = ?";
            // PreparedStatement pst = conn.prepareStatement(sql);
            // pst.setString(1, id);
            // pst.setString(2, password);
            // ResultSet rs = pst.executeQuery();

            // if(rs.next()){      // 登录成功
            //     Date now = new Date();
            //     sql = "update qquser set last_login_time = ? where id = ? and password = ?";
            //     pst = conn.prepareStatement(sql);
            //     pst.setString(1, df.format(now));
            //     pst.setString(2, id);
            //     pst.setString(3, password);
            //     pst.executeUpdate();
            //     out.println("登录成功<br />");
            // }else{              // 登录失败
            //     out.println("登录失败<br />");
            // }
            
            User user = UserUtil.loginSearch(id, password);
            if(user != null){
                // 更新表中 last_login_time
                Map<String, String> map = new HashMap<String, String>();
                map.put("last_login_time", df.format(new Date()));
                boolean flag = UserUtil.edit(id, map);

                if(flag){
                    out.println(JSON.toJSONString(user));
                }else{
                    out.println("更新last_login_time失败");
                }
            }else{
                out.println("null");
            }


            // rs.close();
            // pst.close();
            // conn.close();
        } catch(NamingException ne){
            System.out.println("Login failed: NamingException " + ne.getMessage());
            // out.println("Login failed: NamingException " + ne.getMessage());
            out.println("error");
            ne.printStackTrace();
        } catch(SQLException se){
            System.out.println("Login failed: SQLException " + se.getMessage());
            // out.println("Login failed: SQLException " + se.getMessage());
            out.println("error");
            se.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}