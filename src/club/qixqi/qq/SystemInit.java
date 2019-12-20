package club.qixqi.qq;

import java.sql.*;
import java.io.PrintWriter;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.naming.NamingException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


@WebServlet("/SystemInit")
public class SystemInit extends HttpServlet{
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        try{
            Context cxt = new InitialContext();
            DataSource ds = (DataSource) cxt.lookup("java:comp/env/jdbc/qq");
            Connection conn = ds.getConnection();

            // 创建表 qquser
            String sql = "create table if not exists `qquser`(" +
                "`id` int(11) not null check(len(`id`) > 5), "+
                "`username` varchar(255) not null, " +
                "`password` varchar(255) not null check(len(`password`) between 6 and 20), " + 
                "`sex` char(1) not null default 'u', " +        // m男  f女  u未知
                "`phone_num` char(11) unique, " + 
                "`icon` varchar(255) default 'icon_default.png'," +
                "`birthday` date default '1999-12-14'," +
                "`register_time` datetime not null, " + 
                "`last_login_time` datetime, " + 
                "primary key(`id`)" + 
                ") ENGINE=InnoDB default charset=utf8;" ;
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            out.println("<h3 align='center'>创建 qquser 表成功</h3>\n");

            // 创建表 qqfriends
            sql = "create table if not exists `qqfriends`(" +
                "`userId1` int(11)," + 
                "`userId2` int(11)," +
                "`relation_time` datetime not null," +
                "primary key(`userId1`, `userId2`)" +
                ") ENGINE=InnoDB default charset=utf8;";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            out.println("<h3 align='center'>创建 qqfriends 表成功</h3>\n");


            // 创建表 qqroom
            sql = "create table if not exists `qqroom`(" +
                "`roomId` int(11)," +
                "`roomName` varchar(255)," +
                "`roomIcon` int(11)," +
                "`create_time` datetime," +
                "`room_introduce` varchar(255)," +
                "`member_num` int(5)," +
                "`room_owner` int(11)," +
                "primary key(`roomId`)" +
                ") ENGINE=InnoDB default charset=utf8;";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            out.println("<h3 align='center'>创建 qqroom 表成功</h3>\n");


            // 创建表 qqroom_member
            sql = "create table if not exists `qqroom_member`(" +
                "`roomId` int(11) auto_increment," +
                "`userId` int(11)," +
                "`user_nick` varchar(255)," +
                "`user_role` char(1)," +
                "primary key(`roomId`, `userId`)" +
                ") ENGINE=InnoDB default charset=utf8;";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            out.println("<h3 align='center'>创建 qqroom_member 表成功</h3>\n");


            // 创建表 qqsession
            sql = "create table if not exists `qqsession`(" +
                "`userId` int(11)," +
                "`chatId` int(11) unique," +
                "`userId1` int(11)," +
                "`userId2` int(11)," +
                "`last_msg` varchar(255)," +
                "`last_username` varchar(255)," +
                "`last_time` datetime," +
                "`last_msg_type` char(1)," +
                "`chat_type` char(1)," +
                "primary key(`userId1`, `userId2`)" +
                ") ENGINE=InnoDB default charset=utf8;";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            out.println("<h3 align='center'>创建 qqsession 表成功</h3>\n");


            // 创建表 qqmessage
            sql = "create table if not exists `qqmessage`(" +
                "`id` int(11) auto_increment primary key," +
                "`msg_id` int(11)," +
                "`userId` int(11)," +
                "`userId1` int(11)," +
                "`username1` varchar(255)," +
                "`userIcon1` varchar(255)," +
                "`toId` int(11)," +
                "`chat_type` char(1)," +
                "`msg_type` char(1)," +
                "`msg` varchar(255)," +
                "`send_time` datetime," +
                "`send_status` char(1)" +
                ") ENGINE=InnoDB default charset=utf8;";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            out.println("<h3 align='center'>创建 qqmessage 表成功</h3>\n");

            // 创建表 qqfile
            sql = "create table if not exists `qqfile`( " +
                "`fileId` int(11) not null check(len(`fileId`) > 5), " +
                "`fileName` varchar(255) not null, " +
                "`fileType` varchar(25), " +
                "`fileSize` int(15), " +
                "`linkNum` int(11) default 0, " +
                "`createTime` datetime not null, " +
                "`lastUseTime` datetime default '1999-04-22 00:00:00', " +
                "primary key(`fileId`) " +
                ") ENGINE=InnoDB default charset=utf8;";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            out.println("<h3 align='center'>创建 qqfile 表成功</h3>\n");


            // 创建表 qqfile_link
            sql = "create table if not exists `qqfile_link`( " +
                "`linkId` int(11) auto_increment primary key, " +
                "`userId` int(11) not null, " +
                "`fileId` int(11), " +
                "`fileName` varchar(255) , " +
                "`fileType` varchar(25), " +
                "`fileSize` int(15), " +
                "`isFolder` char(1) default 'n', " +
                "`folderName` varchar(255) , " +
                "`fileList` varchar(255) default '', " +
                "`folderList` varchar(1000) default '', " +
                "`isRoot` char(1) default 'n', " +
                "`parent` int(11), " +
                "`createLinkTime` datetime " +
                ") ENGINE=InnoDB default charset=utf8;";
            pst = conn.prepareStatement(sql);
            pst.executeUpdate();
            out.println("<h3 align='center'>创建 qqfile_link 表成功</h3>\n");



            out.println("<h2 align='center'>QQ系统初始化成功</h2>\n");
            pst.close();
            conn.close();
        } catch(NamingException ne){
            out.println("NamingException: " + ne.getMessage());
            ne.printStackTrace();
        } catch(SQLException se){
            out.println("SQLException: " + se.getMessage());
            se.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}