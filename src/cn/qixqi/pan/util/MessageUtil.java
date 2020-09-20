package cn.qixqi.pan.util;

import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.NamingException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import cn.qixqi.pan.entity.PanMessage;

public class MessageUtil{

    private static Connection conn = null;
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

    public static boolean isExist(int msgId) throws NamingException, SQLException{
        boolean flag = false;
        if(msgId < 1000000){
            return flag;
        }
        initConn();
        String sql = "select * from qqmessage where msg_id = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, msgId);
        ResultSet rs =  pst.executeQuery();
        if(rs.next()){
            flag = true;
        }
        rs.close();
        pst.close();
        conn.close();
        return flag;
    } 


    /**
     * 添加消息
     * @param message
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static boolean add(PanMessage message) throws NamingException, SQLException{
        boolean flag = false;
        if(message == null){
            return flag;
        }
        initConn();
        String sql = "insert into qqmessage(msg_id, userId, userId1, username1, userIcon1, toId, chat_type, msg_type, msg, send_time, send_status) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, message.getMsgId());
        pst.setInt(2, message.getUid());
        pst.setInt(3, message.getUid1());
        pst.setString(4, message.getUsername1());
        pst.setString(5, message.getAvatar1());
        pst.setInt(6, message.getReceiverId());
        pst.setString(7, Character.toString(message.getSessionType()));
        pst.setString(8, Character.toString(message.getMsgType()));
        pst.setString(9, message.getMsg());
        pst.setString(10, message.getMsgTime());
        pst.setString(11, Character.toString(message.getMsgStatus()));
        pst.executeUpdate();
        flag = true;
        pst.close();
        conn.close();
        return flag;
    }


    /**
     * 删除一条消息(用户撤回消息)
     * @param msgId
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static boolean delete(int msgId) throws NamingException, SQLException{
        boolean flag = false;
        if(msgId < 1000000){
            return flag;
        }
        initConn();
        String sql = "delete from qqmessage where msg_id = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, msgId);
        pst.executeUpdate();
        flag = true;
        pst.close();
        conn.close();
        return flag;
    }


    /**
     * 删除两个用户之间的消息(双方删除好友)
     * @param userId1
     * @param userId2
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static boolean delete(int userId1, int userId2) throws NamingException, SQLException{
        boolean flag = false;
        if(userId1 < 100000 || userId2 < 100000){
            return flag;
        }
        initConn();
        String sql = "delete from qqmessage where (userId1 = ? and toId = ?) or (userId1 = ? and toId = ?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, userId1);
        pst.setInt(2, userId2);
        pst.setInt(3, userId2);
        pst.setInt(4, userId1);
        pst.executeUpdate();
        flag = true;
        pst.close();
        conn.close();
        return flag;
    }


    /**
     * 删除某个用户所有的记录(注销账户)
     * @param userId
     * @param check
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static boolean delete(int userId, boolean check) throws NamingException, SQLException{
        boolean flag = false;
        if(userId < 100000 || !check){
            return flag;
        }
        initConn();
        String sql = "delete from qqmessage where userId1 = ? or toId = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, userId);
        pst.setInt(2, userId);
        pst.executeUpdate();
        flag = true;
        pst.close();
        conn.close();
        return flag;
    }


    /**
     * 消息不可修改
     */



    /**
     * 获取某个用户的所有消息
     * @param userId
     * @param check
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static List<PanMessage> searchAll(int userId) throws NamingException, SQLException{
        if(userId < 100000){
            return null;
        }
        initConn();
        List<PanMessage> messageList = new ArrayList<>();
        String sql = "select msg_id, userId, userId1, username1, userIcon1, toId, chat_type, msg_type, msg, send_time, send_status from qqmessage where userId1 = ? or toId = ? ";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, userId);
        pst.setInt(2, userId);
        ResultSet rs = pst.executeQuery();
        while(rs.next()){
            int msg_id = rs.getInt("msg_id");
            int _userId = rs.getInt("userId");
            int userId1 = rs.getInt("userId1");
            String username1 = rs.getString("username1");
            String userIcon1 = rs.getString("userIcon1");
            int toId = rs.getInt("toId");
            char chat_type = rs.getString("chat_type").toCharArray()[0];
            char msg_type = rs.getString("msg_type").toCharArray()[0];
            String msg = rs.getString("msg");
            String send_time = df.format(rs.getTimestamp("send_time"));
            char send_status = rs.getString("send_status").toCharArray()[0];
            PanMessage message = new PanMessage(msg_id, _userId, userId1, username1, userIcon1, toId, chat_type, msg_type, msg, send_time, send_status);
            messageList.add(message);
        }
        rs.close();
        pst.close();
        conn.close();
        if(messageList.size() != 0){
            return messageList;
        }else{
            return null;
        }
    }



    /**
     * 获取两个用户之间的所有消息
     * @param userId1
     * @param userId2
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static List<PanMessage> searchAll(int userId1, int userId2) throws NamingException, SQLException{
        if(userId1 < 100000 || userId2 < 100000){
            return null;
        }
        initConn();
        List<PanMessage> messageList = new ArrayList<>();
        String sql = "select msg_id, userId, userId1, username1, userIcon1, toId, chat_type, msg_type, msg, send_time, send_status from qqmessage where (userId1 = ? and toId = ?) or (userId1 = ? and toId = ?) ";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, userId1);
        pst.setInt(2, userId2);
        pst.setInt(3, userId2);
        pst.setInt(4, userId1);
        ResultSet rs = pst.executeQuery();
        while(rs.next()){
            int msg_id = rs.getInt("msg_id");
            int _userId = rs.getInt("userId");
            int _userId1 = rs.getInt("userId1");
            String username1 = rs.getString("username1");
            String userIcon1 = rs.getString("userIcon1");
            int toId = rs.getInt("toId");
            char chat_type = rs.getString("chat_type").toCharArray()[0];
            char msg_type = rs.getString("msg_type").toCharArray()[0];
            String msg = rs.getString("msg");
            String send_time = df.format(rs.getTimestamp("send_time"));
            char send_status = rs.getString("send_status").toCharArray()[0];
            PanMessage message = new PanMessage(msg_id, _userId, _userId1, username1, userIcon1, toId, chat_type, msg_type, msg, send_time, send_status);
            messageList.add(message);
        }
        rs.close();
        pst.close();
        conn.close();
        if(messageList.size() != 0){
            return messageList;
        }else{
            return null;
        }
    }


    /**
     * 根据msgId获取一条消息
     * @param msgId
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static PanMessage search(int msgId) throws NamingException, SQLException{
        PanMessage message = null;
        if(msgId < 1000000){
            return message;
        }
        initConn();
        String sql = "select userId, userId1, username1, userIcon1, toId, chat_type, msg_type, msg, send_time, send_status from qqmessage where msg_id = ?  ";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, msgId);
        ResultSet rs = pst.executeQuery();
        if(rs.next()){
            int _userId = rs.getInt("userId");
            int userId1 = rs.getInt("userId1");
            String username1 = rs.getString("username1");
            String userIcon1 = rs.getString("userIcon1");
            int toId = rs.getInt("toId");
            char chat_type = rs.getString("chat_type").toCharArray()[0];
            char msg_type = rs.getString("msg_type").toCharArray()[0];
            String msg = rs.getString("msg");
            String send_time = df.format(rs.getTimestamp("send_time"));
            char send_status = rs.getString("send_status").toCharArray()[0];
            message = new PanMessage(msgId, _userId, userId1, username1, userIcon1, toId, chat_type, msg_type, msg, send_time, send_status);
        }
        rs.close();
        pst.close();
        conn.close();
        return message;
    }

}