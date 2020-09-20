package cn.qixqi.pan;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.naming.NamingException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.qixqi.pan.entity.PanUser;
import cn.qixqi.pan.util.UserUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


@Controller
public class Login  {
    private static final long serialVersionUID = 1L;
    private Logger logger = LogManager.getLogger(Login.class.getName());
    
    // 设置日期格式
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping("login.do")
    public void login(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (session.getAttribute("user") != null) {
        	session.removeAttribute("user");
        	this.logger.info("切换账号，清空session");
        }
    	// 接收请求数据
		response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        if(request.getParameter("id")==null || request.getParameter("password")==null){
            out.println("请正确输入信息");
            return;
        }
        out.println("query: " + request.getParameter("query"));
        if(request.getParameter("id") == "" || request.getParameter("password") == ""){
            out.println("请正确输入信息");
            return;
        }
        int id = Integer.parseInt(request.getParameter("id"));
        String password = request.getParameter("password");

        /*try{            
            /*User user = UserUtil.loginSearch(id, password);
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
            }*/
        out.println("测试中文");


            // rs.close();
            // pst.close();
            // conn.close();
        /*} catch(NamingException ne){
            System.out.println("Login failed: NamingException " + ne.getMessage());
            // out.println("Login failed: NamingException " + ne.getMessage());
            out.println("error");
            ne.printStackTrace();
        } catch(SQLException se){
            System.out.println("Login failed: SQLException " + se.getMessage());
            // out.println("Login failed: SQLException " + se.getMessage());
            out.println("error");
            se.printStackTrace();
        }*/
    }

}