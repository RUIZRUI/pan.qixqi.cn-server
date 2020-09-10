package cn.qixqi.pan.util;

import java.util.Map;
import java.util.Set;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import cn.qixqi.pan.entity.User;

/**
 * todo 
 * 1. userId 和 phoneNum 可不可以通过范型来统一
 */


public class UserUtil{
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat df_birthday = new SimpleDateFormat("yyyy-MM-dd");
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
     * 根据id判断用户是否存在
     * @param userId
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static boolean isExist(int userId) throws NamingException, SQLException{
        boolean flag = false;
        if(userId < 100000){
            return flag;
        } 
        initConn();
        String sql = "select * from qquser where id = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, userId);
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
     * 根据手机号phone_num 判断用户是否存在
     * @param phoneNum
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static boolean isExist(String phoneNum) throws NamingException, SQLException{
        boolean flag = false;
        // todo 
        // phoneNum长度小于11时模糊匹配
        if(phoneNum.length() > 11){
            return flag;
        }
        initConn();
        String sql = "select * from qquser where phone_num = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, phoneNum);
        ResultSet rs = pst.executeQuery();
        if(rs.next()){
            flag =  true;
        }
        pst.close();
        conn.close();
        return flag;
    }


    /**
     * 添加用户
     * @param user
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static boolean add(User user) throws NamingException, SQLException{
        boolean flag = false;
        if(user == null){
            return flag;
        }
        initConn();
        String sql = "insert into qquser(id, username, password, sex, phone_num, register_time, last_login_time) values (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, user.getUserId());
        pst.setString(2, user.getUserName());
        pst.setString(3, user.getPassword());
        pst.setString(4, Character.toString(user.getSex()));
        pst.setString(5, user.getPhoneNum());
        pst.setString(6, user.getRegisterTime());
        pst.setString(7, user.getLastLoginTime());
        pst.executeUpdate();
        pst.close();
        conn.close();
        flag = true;
        return flag;
    }


    /**
     * 根据id删除用户
     * @param userId
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static boolean delete(int userId) throws NamingException, SQLException{
        boolean flag = false;
        if(userId < 100000){
            return flag;
        }
        initConn();
        String sql = "delete from qquser where id = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, userId);
        pst.executeUpdate();
        pst.close();
        conn.close();
        flag = true;
        return flag;
    }


    /**
     * 根据手机号删除用户
     * @param phoneNum
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static boolean delete(String phoneNum) throws NamingException, SQLException{
        boolean flag = false;
        // todo
        // 手机号小于11位时模糊匹配
        if(phoneNum.length() > 11){
            return flag;
        }
        initConn();
        String sql = "delete from qquser where phone_num = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, phoneNum);
        pst.executeUpdate();
        pst.close();
        conn.close();
        flag = true;
        return flag;
    }


    /**
     * 根据id修改用户信息
     * @param userId
     * @param map
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static boolean edit(int userId, Map<String, String> map) throws NamingException, SQLException{
        boolean flag = false;
        if(userId < 100000){
            return flag;
        }
        Set<String> set = map.keySet();
        if(set.size() == 0){
            return flag;
        }
        initConn();
        // PreparedStatement不能设置表名、字段名
        // StringBuilder builder = new StringBuilder("update qquser set ? = ? ");
        // for(int i=1; i<set.size(); i++){
        //     builder.append(",? = ? ");
        // }
        StringBuilder builder = new StringBuilder("update qquser set ");
        int counter = 0;
        for(String key : set){
            if(counter != 0){
                builder.append(", ");
            }
            builder.append(key);
            builder.append(" = ? ");
            counter ++;
        }
        builder.append("where id = ?");
        // System.out.println("map = " + map);
        // System.out.println("builder = " + builder.toString());
        PreparedStatement pst = conn.prepareStatement(builder.toString());
        counter = 1;
        for(String key : set){
            // pst.setString(counter++, key);
            pst.setString(counter++, map.get(key));
        }
        pst.setInt(counter, userId);
        pst.executeUpdate();
        pst.close();
        conn.close();
        flag = true;
        return flag;
    }


    /**
     * 根据手机号更新用户信息
     * @param phoneNum
     * @param map
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static boolean edit(String phoneNum, Map<String, String> map) throws NamingException, SQLException{
        boolean flag = false;
        if(phoneNum.length() > 11){
            return flag;
        }
        Set<String> set = map.keySet();
        if(set.size() == 0){
            return flag;
        }
        initConn();
        // StringBuilder builder = new StringBuilder("update qquser set ? = ? ");
        // for(int i=1; i<set.size(); i++){
        //     builder.append(",? = ? ");
        // }
        StringBuilder builder = new StringBuilder("update qquser set ");
        int counter = 0;
        for(String key : set){
            if(counter != 0){
                builder.append(", ");
            }
            builder.append(key);
            builder.append(" = ? ");
            counter ++;
        }
        builder.append("where phone_num = ?");
        PreparedStatement pst = conn.prepareStatement(builder.toString());
        counter = 1;
        for(String key : set){
            // pst.setString(counter++, key);
            pst.setString(counter++, map.get(key));
        }
        pst.setString(counter, phoneNum);
        pst.executeUpdate();
        pst.close();
        conn.close();
        flag = true;
        return flag;
    }


    /**
     * 登录查找,只能通过账号和密码登录
     * @param userId
     * @param password
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static User loginSearch(int userId, String password) throws NamingException, SQLException{
        if(userId < 100000){
            return null;
        }
        initConn();
        String sql = "select * from qquser where id = ? and password = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, userId);
        pst.setString(2, password);
        ResultSet rs = pst.executeQuery();
        User user = null;
        if(rs.next()){
            String username = rs.getString("username");
            char sex = rs.getString("sex").toCharArray()[0];
            String phoneNum = rs.getString("phone_num");
            String icon = rs.getString("icon");
            String birthday = df_birthday.format(rs.getDate("birthday"));
            String registerTime = df.format(rs.getTimestamp("register_time"));
            String lastLoginTime = df.format(rs.getTimestamp("last_login_time"));
            user = new User(userId, username, password, sex, phoneNum, icon, birthday, registerTime, lastLoginTime);
        }
        rs.close();
        pst.close();
        conn.close();
        return user;
    }
    

    /**
     * 陌生人通过id搜索用户
     * @param userId
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static User strangerSearch(int userId) throws NamingException, SQLException{
        if(userId < 100000){
            return null;
        }
        initConn();
        String sql = "select username, icon, register_time from qquser where id = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, userId);
        ResultSet rs = pst.executeQuery();
        User user = null;
        if(rs.next()){
            String username = rs.getString("username");
            String icon = rs.getString("icon");
            String registerTime = df.format(rs.getTimestamp("register_time"));
            user = new User(userId, username, icon, registerTime);
        }
        rs.close();
        pst.close();
        conn.close();
        return user;
    }


    /**
     * 陌生人通过手机号搜索用户
     * @param phoneNum
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static User strangerSearch(String phoneNum) throws NamingException, SQLException{
        if(phoneNum.length() > 11){
            return null;
        }
        initConn();
        String sql = "select id, username, icon, register_time from qquser where phone_num = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, phoneNum);
        ResultSet rs = pst.executeQuery();
        User user = null;
        if(rs.next()){
            int userId = rs.getInt("id");
            String username = rs.getString("username");
            String icon = rs.getString("icon");
            String registerTime = df.format(rs.getTimestamp("register_time"));
            user = new User(userId, username, icon, registerTime);
        }
        rs.close();
        pst.close();
        conn.close();
        return user;
    }
}
