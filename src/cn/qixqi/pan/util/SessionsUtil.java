package cn.qixqi.pan.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

import javax.naming.NamingException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import cn.qixqi.pan.entity.PanSession;

public class SessionsUtil{
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
     * 通过chatId判断是否存在会话
     * @param chatId
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static boolean isExist(int chatId) throws NamingException, SQLException{
        boolean flag = false;
        if(chatId < 1000000){
            return flag;
        }
        initConn();
        String sql = "select * from qqsession where chatId = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, chatId);
        ResultSet rs = pst.executeQuery();
        if(rs.next()){
            flag = true;
        }
        rs.close();
        pst.close();
        conn.close();
        return flag;
    }


    /**
     * 通过userId1, userId2判断是否存在会话
     * @param userId1
     * @param userId2
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static boolean isExist(int userId1, int userId2) throws NamingException, SQLException{
        boolean flag = false;
        if(userId1 < 100000 || userId2 < 100000){
            return flag;
        }
        initConn();
        String sql = "select * from qqsession where (userId1 = ? and userId2 = ?) or (userId1 = ? and userId2 = ?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, userId1);
        pst.setInt(2, userId2);
        pst.setInt(3, userId2);
        pst.setInt(4, userId1);
        ResultSet rs = pst.executeQuery();
        if(rs.next()){
            flag = true;
        }
        rs.close();
        pst.close();
        conn.close();
        return flag;
    }


    /**
     * 新建会话
     * @param sessions
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static boolean add(PanSession sessions) throws NamingException, SQLException{
        boolean flag = false;
        if(sessions == null){
            return flag;
        }
        initConn();
        String sql = "insert into qqsession (userId, chatId, userId1, userId2, last_msg, last_username, last_time, last_msg_type, chat_type) " +
            "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, sessions.getUid());
        pst.setInt(2, sessions.getSessionId());
        pst.setInt(3, sessions.getUid1());
        pst.setInt(4, sessions.getUid2());
        pst.setString(5, sessions.getLastMsg());
        pst.setString(6, sessions.getLastMsgUsername());
        pst.setString(7, sessions.getLastMsgTime());
        pst.setString(8, Character.toString(sessions.getLastMsgType()));
        pst.setString(9, Character.toString(sessions.getSessionType()));
        // pst.setInt(10, sessions.getUnreadCount());
        pst.executeUpdate();
        pst.close();
        conn.close();
        flag = true;
        return flag;
    }


    /**
     * 根据chatId删除会话
     * @param chatId
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static boolean delete(int chatId) throws NamingException, SQLException{
        boolean flag = false;
        if(chatId < 1000000){
            return flag;
        }
        initConn();
        String sql = "delete from qqsession where chatId = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, chatId);
        pst.executeUpdate();
        pst.close();
        conn.close();
        flag = true;
        return flag;
    }


    /**
     * 根据userId1, userId2删除会话
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
        String sql = "delete from qqsession where (userId1 = ? and userId2 = ?) or (userId1 = ? and userId2 = ?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, userId1);
        pst.setInt(2, userId2);
        pst.setInt(3, userId2);
        pst.setInt(4, userId1);
        pst.executeUpdate();
        pst.close();
        conn.close();
        flag = true;
        return flag;
    }


    /**
     * 根据chatId更改会话
     * @param chatId
     * @param map
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static boolean edit(int chatId, Map<String, String> map) throws NamingException, SQLException{
        boolean flag = false;
        if(chatId < 1000000){
            return flag;
        }
        Set<String> set = map.keySet();
        if(set.size() == 0){
            return flag;
        }
        initConn();
        StringBuilder builder = new StringBuilder("update qqsession set ");
        int counter = 0;
        for(String key : set){
            if(counter != 0){
                builder.append(", ");
            }
            builder.append(key);
            builder.append(" = ? ");
            counter ++;
        }
        builder.append("where chatId = ?");
        PreparedStatement pst = conn.prepareStatement(builder.toString());
        counter = 1;
        for(String key : set){
            pst.setString(counter++, map.get(key));
        }
        pst.setInt(counter, chatId);
        pst.executeUpdate();
        pst.close();
        conn.close();
        flag = true;
        return flag;
    }


    /**
     * 根据userId1, userId2更改会话
     * @param userId1
     * @param userId2
     * @param map
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static boolean edit(int userId1, int userId2, Map<String, String> map) throws NamingException, SQLException{
        boolean flag = false;
        if(userId1 < 100000 || userId2 < 100000){
            return flag;
        }
        Set<String> set = map.keySet();
        if(set.size() == 0){
            return flag;
        }
        initConn();
        StringBuilder builder = new StringBuilder("update qqsession set ");
        int counter = 0;
        for(String key : set){
            if(counter != 0){
                builder.append(", ");
            }
            builder.append(key);
            builder.append(" = ? ");
            counter ++;
        }
        builder.append("where (userId1 = ? and userId2 = ?) or (userId1 = ? and userId2 = ?)");
        PreparedStatement pst = conn.prepareStatement(builder.toString());
        counter = 1;
        for(String key : set){
            pst.setString(counter++, map.get(key));
        }
        pst.setInt(counter++, userId1);
        pst.setInt(counter++, userId2);
        pst.setInt(counter++, userId2);
        pst.setInt(counter, userId1);
        pst.executeUpdate();
        pst.close();
        conn.close();
        flag = true;
        return flag;
    }


    /**
     * 获取用户的所有会话
     * @param userId
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static List<PanSession> searchAll(int userId) throws NamingException, SQLException{
        if(userId < 100000){
            return null;
        }
        initConn();
        List<PanSession> session_list = new ArrayList<>();
        String sql = "select userId, chatId, userId1, userId2, user1.username, user2.username, user1.icon, user2.icon, last_msg, last_username, last_time, last_msg_type, chat_type  " +
            "from qqsession, qquser user1, qquser user2 " +
            "where (userId1 = ? or userId2 = ?) and userId1 = user1.id and userId2 = user2.id";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, userId);
        pst.setInt(2, userId);
        ResultSet rs = pst.executeQuery();
        while(rs.next()){
            int _userId = rs.getInt("userId");
            int chatId = rs.getInt("chatId");
            int userId1 = rs.getInt("userId1");
            int userId2 = rs.getInt("userId2");
            String username1 = rs.getString(5);
            String username2 = rs.getString(6);
            String userIcon1 = rs.getString(7);
            String userIcon2 = rs.getString(8);
            String last_msg = rs.getString("last_msg");
            String last_username = rs.getString("last_username");
            // String last_time = rs.getString("last_time");
            String last_time = df.format(rs.getTimestamp("last_time"));
            char last_msg_type = rs.getString("last_msg_type").toCharArray()[0];
            char chat_type = rs.getString("chat_type").toCharArray()[0];
            // int unread_count = rs.getInt("unread_count");
            PanSession sessions = new PanSession(_userId, chatId, userId1, userId2, username1, username2, userIcon1, userIcon2, last_msg, last_username, last_time, last_msg_type, chat_type);
            session_list.add(sessions);
        }
        rs.close();
        pst.close();
        conn.close();
        if(session_list.size() != 0){
            return session_list;
        }
        return null;
    }


    /**
     * 根据chatId获取会话
     * @param chatId
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static PanSession search(int chatId) throws NamingException, SQLException{
        PanSession sessions = null;
        if(chatId < 1000000){
            return sessions;
        }
        initConn();
        String sql = "select userId, userId1, userId2, user1.username, user2.username, user1.icon, user2.icon, last_msg, last_username, last_time, last_msg_type, chat_type  " +
            "from qqsession, qquser user1, qquser user2 " +
            "where chatId = ? and userId1 = user1.id and userId2 = user2.id";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, chatId);
        ResultSet rs = pst.executeQuery();
        if(rs.next()){
            int _userId = rs.getInt("userId");
            int userId1 = rs.getInt("userId1");
            int userId2 = rs.getInt("userId2");
            String username1 = rs.getString(4);
            String username2 = rs.getString(5);
            String userIcon1 = rs.getString(6);
            String userIcon2 = rs.getString(7);
            String last_msg = rs.getString("last_msg");
            String last_username = rs.getString("last_username");
            // String last_time = rs.getString("last_time");
            String last_time = df.format(rs.getTimestamp("last_time"));
            char last_msg_type = rs.getString("last_msg_type").toCharArray()[0];
            char chat_type = rs.getString("chat_type").toCharArray()[0];
            // int unread_count = rs.getInt("unread_count");
            sessions = new PanSession(_userId, chatId, userId1, userId2, username1, username2, userIcon1, userIcon2, last_msg, last_username, last_time, last_msg_type, chat_type);
        }
        rs.close();
        pst.close();
        conn.close();
        return sessions;
    }


    /**
     * 根据useId1, userId2获取会话
     * @param useId1
     * @param userId2
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static PanSession search(int userId1, int userId2) throws NamingException, SQLException{
        PanSession sessions = null;
        if(userId1 < 100000 || userId2 < 100000){
            return sessions;
        }
        initConn();
        String sql = "select userId, chatId, userId1, userId2, user1.username, user2.username, user1.icon, user2.icon, last_msg, last_username, last_time, last_msg_type, chat_type  " +
            "from qqsession, qquser user1, qquser user2 " +
            "where ((userId1 = ? and userId2 = ?) or (userId1 = ? and userId2 = ?)) and userId1 = user1.id and userId2 = user2.id";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, userId1);
        pst.setInt(2, userId2);
        pst.setInt(3, userId2);
        pst.setInt(4, userId1);
        ResultSet rs = pst.executeQuery();
        if(rs.next()){
            int _userId = rs.getInt("userId");
            int chatId = rs.getInt("chatId");
            int _userId1 = rs.getInt("userId1");
            int _userId2 = rs.getInt("userId2");
            String username1 = rs.getString(5);
            String username2 = rs.getString(6);
            String userIcon1 = rs.getString(7);
            String userIcon2 = rs.getString(8);
            String last_msg = rs.getString("last_msg");
            String last_username = rs.getString("last_username");
            // String last_time = rs.getString("last_time");
            String last_time = df.format(rs.getTimestamp("last_time"));
            char last_msg_type = rs.getString("last_msg_type").toCharArray()[0];
            char chat_type = rs.getString("chat_type").toCharArray()[0];
            // int unread_count = rs.getInt("unread_count");
            sessions = new PanSession(_userId, chatId, _userId1, _userId2, username1, username2, userIcon1, userIcon2, last_msg, last_username, last_time, last_msg_type, chat_type);
        }
        rs.close();
        pst.close();
        conn.close();
        return sessions;
    }

}