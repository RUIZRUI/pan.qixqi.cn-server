package cn.qixqi.pan.util;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import cn.qixqi.pan.entity.Files;

/**
 * todo
 * 1. conn 是类内静态变量，需要上锁吗？
 * 2. 将数据库连接与逻辑代码分离
 */


public class FilesUtil{
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
     * 根据fileId判断文件是否存在
     * @param fileId
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static boolean isExist(int fileId) throws NamingException, SQLException{
        boolean flag = false;
        if(fileId < 1000000){
            return flag;
        }
        initConn();
        String sql = "select * from qqfile where fileId = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, fileId);
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
     * 添加文件
     * @param files
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static boolean add(Files files) throws NamingException, SQLException{
        boolean flag = false;
        if(files == null){
            return flag;
        }
        initConn();
        String sql = "insert into qqfile(fileId, fileName, fileType, fileSize, createTime) values (?, ?, ?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, files.getFileId());
        pst.setString(2, files.getFileName());
        pst.setString(3, files.getFileType());
        pst.setLong(4, files.getFileSize());
        pst.setString(5, files.getCreateTime());
        pst.executeUpdate();
        pst.close();
        conn.close();
        flag = true;
        return flag;
    }


    /**
     * 根据fileId删除文件
     * @param fileId
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static boolean delete(int fileId) throws NamingException, SQLException{
        boolean flag = false;
        if(fileId < 1000000){
            return flag;
        }
        initConn();
        String sql = "delete from qqfile where fileId = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, fileId);
        pst.executeUpdate();
        pst.close();
        conn.close();
        flag = true;
        return flag;
    }


    /**
     * 根据fileId修改表
     * @param fileId
     * @param map
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static boolean edit(int fileId, Map<String, String> map) throws NamingException, SQLException{
        boolean flag = false;
        if(fileId < 1000000){
            return flag;
        }
        Set<String> set = map.keySet();
        if(set.size() == 0){
            return flag;
        }
        initConn();
        StringBuilder builder = new StringBuilder("update qqfile set ");
        int counter = 0;
        for(String key : set){
            if(counter != 0){
                builder.append(", ");
            }
            builder.append(key);
            builder.append(" = ? ");
            counter ++;
        }
        builder.append("where fileId = ?");
        PreparedStatement pst = conn.prepareStatement(builder.toString());
        counter = 1;
        for(String key : set){
            pst.setString(counter++, map.get(key));
        }
        pst.setInt(counter, fileId);
        pst.executeUpdate();
        pst.close();
        conn.close();
        flag = true;
        return flag;
    }


    
    /**
     * 添加文件链接
     * @param fileId
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static boolean addLink(int fileId) throws NamingException, SQLException{
        boolean flag = false;
        int linkNum = getLinkNum(fileId);
        if(fileId < 1000000 || linkNum <= -1){
            return flag;
        }
        // initConn();
        Map<String, String> map = new HashMap<String, String>();
        map.put("linkNum", Integer.toString(linkNum+1));
        map.put("lastUseTime", df.format(new Date()));
        flag = edit(fileId, map);
        return flag;
    }


    public static boolean deleteLink(int fileId) throws NamingException, SQLException{
        boolean flag = false;
        int linkNum = getLinkNum(fileId);
        if(fileId < 1000000 || linkNum <= 0){
            return flag;
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("linkNum", Integer.toString(linkNum-1));
        map.put("lastUseTime", df.format(new Date()));
        flag = edit(fileId, map);
        return flag;
    }



    /**
     * 根据fileId获取当前文件链接数量
     * @param fileId
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static int getLinkNum(int fileId) throws NamingException, SQLException{
        int linkNum = -1;
        if(fileId < 1000000){
            return linkNum;
        }
        initConn();
        String sql = "select linkNum from qqfile where fileId = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, fileId);
        ResultSet rs = pst.executeQuery();
        if(rs.next()){
            linkNum = rs.getInt("linkNum");
        }
        rs.close();
        pst.close();
        conn.close();
        return linkNum;
    }





    /**
     * 真实的文件表是不能随意查找的
     */

}