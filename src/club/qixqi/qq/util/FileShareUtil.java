package club.qixqi.qq.util;

import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

import javax.naming.NamingException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class FileShareUtil{

    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static Connection initConn() throws NamingException, SQLException{
        Context cxt = new InitialContext();
        DataSource ds = (DataSource) cxt.lookup("java:comp/env/jdbc/qq");
        Connection conn = ds.getConnection();
        return conn;
    }


    // 添加
    public static String add(int linkId,  String shareMask) throws NamingException, SQLException{
        String sharePass = createPass();
        String createShareTime = df.format(new Date());
        Connection conn = initConn();
        String sql = "insert into qqfile_share (linkId, shareMask, sharePass, createShareTime) values (?, ?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, linkId);
        pst.setString(2, shareMask);
        pst.setString(3, sharePass);
        pst.setString(4, createShareTime);
        pst.executeUpdate();
        pst.close();
        conn.close();
        return sharePass;
    }


    /**
     * 生成四位字母数字混合字符串
     * @return
     */
    private static String createPass(){
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<4; i++){
            int key = random.nextInt(36);
            if(key < 10){       // 产生数字
                builder.append(key);
            }else{              // 产生小写字母
                builder.append((char)(key+87));
            }
        }
        return builder.toString();
    }


    // 删除
    public static void delete(String shareMask) throws NamingException, SQLException{
        Connection conn = initConn();
        String sql = "delete from qqfile_share where shareMask = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, shareMask);
        pst.executeUpdate();
        pst.close();
        conn.close();
    }


    // 删除
    public static void delete(int linkId) throws NamingException, SQLException{
        Connection conn = initConn();
        String sql = "delete from qqfile_share where linkId = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, linkId);
        pst.executeUpdate();
        pst.close();
        conn.close();
    }


    // 查找
    public static int search(String shareMask, String sharePass) throws NamingException, SQLException{
        int linkId = -1;
        Connection conn = initConn();
        String sql = "select linkId from qqfile_share where shareMask = ? and sharePass = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, shareMask);
        pst.setString(2, sharePass);
        ResultSet rs = pst.executeQuery();
        if(rs.next()){
            linkId = rs.getInt("linkId");
        }
        rs.close();
        pst.close();
        conn.close();
        return linkId;
    }



    // 查找某个用户全部的分享链接
    public static List<String> searchAll(int userId) throws NamingException, SQLException{
        List<String> list = new ArrayList<>();
        Connection conn = initConn();
        String sql = "select qqfile_share.shareMask, qqfile_share.sharePass " + 
            "from qqfile_link, qqfile_share " + 
            "where qqfile_link.userId = ? and qqfile_link.linkId = qqfile_share.linkId";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, userId);
        ResultSet rs = pst.executeQuery();
        while(rs.next()){
            String shareMask = rs.getString(1);
            String sharePass = rs.getString(2);
            list.add(createShareLink(shareMask, sharePass));
        }
        rs.close();
        pst.close();
        conn.close();
        return list;
    }



    // 生成链接
    public static String createShareLink(String shareMask, String sharePass){
        String url = "https://www.ourvultr.club:8443/qq/FileShare?method=parse";
        StringBuilder builder = new StringBuilder(url);
        builder.append("&shareMask=");
        builder.append(shareMask);
        builder.append("&sharePass=");
        builder.append(sharePass);
        return builder.toString();
    }


    

}