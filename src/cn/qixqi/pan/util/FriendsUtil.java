package cn.qixqi.pan.util;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.naming.NamingException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import cn.qixqi.pan.entity.PanFriend;

/**
 * 1. 删除时如果表中没有此条目应该返回 false
 * 2. 删除后添加返回 SQLException Duplicate entry '414119-985827' for key 'PRIMARY' ，但是数据库中确实添加成功？？？
 * 3. 数据库中的时间直接获取为 String,会出现2019-12-01 22:42:51.0 的格式
 */


/**
 * 好友操作工具类
 * 处理添加好友、删除好友、查找好友功能
 */
public class FriendsUtil{
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Connection conn = null;


    /**
     * 获取连接池中的连接
     * @throws NamingException
     * @throws SQLException
     */ 
    private static void initConn() throws NamingException, SQLException{
        Context cxt = new InitialContext();
        DataSource ds = (DataSource) cxt.lookup("java:comp/env/jdbc/pan");
        conn = ds.getConnection();
    }

    /**
     * 添加好友
     * @param userId1
     * @param userId2
     * userId1 < userId2
     */
    public static boolean add(int userId1, int userId2) throws NamingException, SQLException{
        if(userId1 == userId2){
            return false;
        }
        initConn();
        String sql = "insert into qqfriends(userId1, userId2, relation_time) values (?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        if(userId1 < userId2){
            pst.setInt(1, userId1);
            pst.setInt(2, userId2);
        }else{
            pst.setInt(1, userId2);
            pst.setInt(2, userId1);
        }
        pst.setString(3, df.format(new Date()));
        pst.executeUpdate();
        pst.close();
        conn.close();
        return true;
    }

    /**
     * 删除好友
     * @param userId1
     * @param userId2
     * userId1 < userId2
     */
    public static boolean delete(int userId1, int userId2) throws NamingException, SQLException{
        if(userId1 == userId2){
            return false;
        }
        initConn();
        String sql = "delete from qqfriends where userId1 = ? and userId2 = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        if(userId1 < userId2){
            pst.setInt(1, userId1);
            pst.setInt(2, userId2);
        }else{
            pst.setInt(1, userId2);
            pst.setInt(2, userId1);
        }
        pst.executeUpdate();
        pst.close();
        conn.close();
        return true;
    }

    /**
     * 查看是否是好友
     * @param userId1
     * @param userId2
     * userId1 < userId2
     * @return
     */
    public static boolean search(int userId1, int userId2) throws NamingException, SQLException{
        if(userId1 == userId2){
            return false;
        }
        initConn();
        String sql = "select * from qqfriends where userId1 = ? and userId2 = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        if(userId1 < userId2){
            pst.setInt(1, userId1);
            pst.setInt(2, userId2);
        }else{
            pst.setInt(1, userId2);
            pst.setInt(2, userId1);
        }
        ResultSet rs = pst.executeQuery();
        if(rs.next()){
            return true;
        }else{
            return false;
        }
    }

    
    /**
     * 查找用户所有好友
     * @param userId
     */
    public static List<PanFriend> searchAll(int userId) throws NamingException, SQLException{
        initConn();
        List<PanFriend> friendList = new ArrayList<>();
        String sql = "select u.id, u.username, u.sex, u.phone_num, u.icon, u.birthday, u.register_time, u.last_login_time, f.relation_time " + 
            "from qquser as u join qqfriends as f " +
            "on u.id = f.userId2 " +
            "where f.userId1 = ? " +
            "union " +
            "select u2.id, u2.username, u2.sex, u2.phone_num, u2.icon, u2.birthday, u2.register_time, u2.last_login_time, f2.relation_time " +
            "from qquser as u2 join qqfriends as f2 " +
            "on u2.id = f2.userId1 " +
            "where f2.userId2 = ? ";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, userId);
        pst.setInt(2, userId);
        ResultSet rs = pst.executeQuery();
        while(rs.next()){
            // int id = rs.getInt("u.id");
            // String username = rs.getString("u.username");
            // char sex = rs.getString("u.sex").charAt(0);
            // String phone_num = rs.getString("u.phone_num");
            // String register_time = rs.getString("u.register_time");
            // String last_login_time = rs.getString("u.last_login_time");
            // String relation_time = rs.getString("u.relation_time");
            int id = rs.getInt(1);
            String username = rs.getString(2);
            char sex = rs.getString(3).charAt(0);
            String phone_num = rs.getString(4);
            String icon = rs.getString(5);
            String birthday = rs.getString(6);
            Date register_time = rs.getTimestamp(7);
            // System.out.println("register_time = " + register_time);
            Date last_login_time = rs.getTimestamp(8);
            Date relation_time = rs.getTimestamp(9);
            PanFriend friend = new PanFriend(id, username, sex, phone_num, icon, birthday, df.format(register_time), df.format(last_login_time), df.format(relation_time));
            friendList.add(friend);
        }
        return friendList;
    }
}
