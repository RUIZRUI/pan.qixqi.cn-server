package club.qixqi.qq.util;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
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

import club.qixqi.qq.entity.FileLink;


/**
 * todo
 * 1. 删除文件链接时，linkId不存在，是否影响flag的判断
 */


public class FileLinkUtil{

    // private static Connection conn = null;   // 大错特错
    private static SimpleDateFormat simpleDf = new SimpleDateFormat("yyyy-MM-dd  HH:mm");

    /**
     * 获取连接池中的连接
     * @throws NamingException
     * @throws SQLException
     */
    private static Connection initConn() throws NamingException, SQLException{
        Context cxt = new InitialContext();
        DataSource ds = (DataSource) cxt.lookup("java:comp/env/jdbc/qq");
        Connection conn = ds.getConnection();
        return conn;
    }


    /**
     * 根据linkId判断文件链接是否存在
     * @param linkId
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static boolean isExist(int linkId) throws NamingException, SQLException{
        boolean flag = false;
        if(linkId < 1000000){
            return flag;
        }
        Connection conn = initConn();
        String sql = "select * from qqfile_link where linkId = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, linkId);
        ResultSet rs = pst.executeQuery();
        if(rs.next()){
            flag = true;
        }
        rs.close();
        pst.close();
        conn.close();
        return flag;
    }


    public static boolean isFolder(int linkId) throws NamingException, SQLException{
        boolean flag = false;
        // initConn();
        Connection conn = initConn();
        String sql = "select isFolder from qqfile_link where linkId = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, linkId);
        ResultSet rs = pst.executeQuery();
        if(rs.next()){
            char isfolder = rs.getString("isFolder").toCharArray()[0];
            if(isfolder == 'y'){
                flag = true;
            }
        }
        rs.close();
        pst.close();
        conn.close();
        return flag;
    }



    /**
     * 添加文件链接
     * @param fileLink
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public static boolean add(FileLink fileLink) throws SQLException, NamingException{
        boolean flag = false;
        if(fileLink == null){ 
            return flag;
        }
        Connection conn = initConn();
        String sql = "insert into qqfile_link (linkId, userId, fileId, fileName, fileType, fileSize, isFolder, folderName, fileList, folderList, isRoot, parent, createLinkTime) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, fileLink.getLinkId());
        pst.setInt(2, fileLink.getUserId());
        pst.setInt(3, fileLink.getFileId());
        pst.setString(4, fileLink.getFileName());
        pst.setString(5, fileLink.getFileType());
        pst.setLong(6, fileLink.getFileSize());
        pst.setString(7, Character.toString(fileLink.getIsFolder()));
        pst.setString(8, fileLink.getFolderName());
        pst.setString(9, fileLink.getFileList());
        pst.setString(10, fileLink.getFolderList());
        pst.setString(11, Character.toString(fileLink.getIsRoot()));
        pst.setInt(12, fileLink.getParent());
        pst.setString(13, fileLink.getCreateLinkTime());
        pst.executeUpdate();
        pst.close();
        conn.close();
        flag = true;
        return flag;
    }


    /**
     * 删除文件链接
     * @param linkId
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static boolean delete(int linkId) throws NamingException, SQLException{
        boolean flag = false;
        if(linkId < 1000000){
            return flag;
        }
        Connection conn = initConn();
        String sql = "delete from qqfile_link where linkId = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, linkId);
        pst.executeUpdate();
        pst.close();
        conn.close();
        flag = true;
        return flag;
    }



    /**
     * 根据linkId修改文件链接
     * @param linkId
     * @param map
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static boolean edit(int linkId, Map<String, String> map) throws NamingException, SQLException{
        boolean flag = false;
        if(linkId < 1000000){
            return flag;
        }
        Set<String> set = map.keySet();
        if(set.size() == 0){
            return flag;
        }
        Connection conn = initConn();
        StringBuilder builder = new StringBuilder("update qqfile_link set ");
        int counter = 0;
        for(String key : set){
            if(counter != 0){
                builder.append(", ");
            }
            builder.append(key);
            builder.append(" = ? ");
            counter ++;
        }
        builder.append("where linkId = ?");
        PreparedStatement pst = conn.prepareStatement(builder.toString());
        counter = 1;
        for(String key : set){
            pst.setString(counter++, map.get(key));
        }
        pst.setInt(counter, linkId);
        pst.executeUpdate();
        pst.close();
        conn.close();
        flag = true;
        return flag;
    }








    /**
     * 获取当前文件夹的子文件列表fileList
     * @param linkId
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static String getFileList(int linkId) throws NamingException, SQLException{
        String fileList = null;
        if(linkId < 1000000){
            return fileList;
        }
        Connection conn = initConn();
        String sql = "select fileList from qqfile_link where linkId = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, linkId);
        ResultSet rs = pst.executeQuery();
        if(rs.next()){
            fileList = rs.getString("fileList");
        }
        rs.close();
        pst.close();
        conn.close();
        return fileList;
    }


    /**
     * 获取当前文件夹的子文件夹列表folderList
     * @param linkId
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static String getFolderList(int linkId) throws NamingException, SQLException{
        String folderList = null;
        if(linkId < 1000000){
            return folderList;
        }
        Connection conn = initConn();
        String sql = "select folderList from qqfile_link where linkId = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, linkId);
        ResultSet rs = pst.executeQuery();
        if(rs.next()){
            folderList = rs.getString("folderList");
        }
        rs.close();
        pst.close();
        conn.close();
        return folderList;
    }



    /**
     * 根据userId获取根文件夹链接
     * @param userId
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static FileLink getRootFolder(int userId) throws NamingException, SQLException{
        FileLink fileLink = null;
        if(userId < 100000){
            return fileLink;
        }
        Connection conn = initConn();
        String sql = "select * from qqfile_link where userId = ? and isRoot = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, userId);
        pst.setString(2, Character.toString('y'));
        ResultSet rs = pst.executeQuery();
        if(rs.next()){
            int linkId = rs.getInt("linkId");
            int fileId = rs.getInt("fileId");
            String fileName = rs.getString("fileName");
            String fileType = rs.getString("fileType");
            long fileSize = rs.getLong("fileSize");
            char isFolder = rs.getString("isFolder").toCharArray()[0];
            String folderName = rs.getString("folderName");
            String fileList = rs.getString("fileList");
            String folderList = rs.getString("folderList");
            char isRoot = rs.getString("isRoot").toCharArray()[0];
            int parent = rs.getInt("parent");
            String createLinkTime = rs.getString("createLinkTime");
            fileLink = new FileLink(linkId, userId, fileId, fileName, fileType, fileSize, isFolder, folderName, fileList, folderList, isRoot, parent, createLinkTime);
        }
        rs.close();
        pst.close();
        conn.close();
        return fileLink;
    }



    /**
     * 根据linkId查找一条文件链接
     * @param linkId
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static FileLink SearchOne(int linkId) throws NamingException, SQLException{
        FileLink fileLink = null;
        if(linkId < 1000000){
            return fileLink;
        }
        Connection conn = initConn();
        String sql = "select * from qqfile_link where linkId = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, linkId);
        ResultSet rs = pst.executeQuery();
        if(rs.next()){
            int userId = rs.getInt("userId");
            int fileId = rs.getInt("fileId");
            String fileName = rs.getString("fileName");
            String fileType = rs.getString("fileType");
            long fileSize = rs.getLong("fileSize");
            char isFolder = rs.getString("isFolder").toCharArray()[0];
            String folderName = rs.getString("folderName");
            String fileList = rs.getString("fileList");
            String folderList = rs.getString("folderList");
            char isRoot = rs.getString("isRoot").toCharArray()[0];
            int parent = rs.getInt("parent");
            // String createLinkTime = rs.getString("createLinkTime");
            String createLinkTime = simpleDf.format(rs.getTimestamp("createLinkTime"));
            fileLink = new FileLink(linkId, userId, fileId, fileName, fileType, fileSize, isFolder, folderName, fileList, folderList, isRoot, parent, createLinkTime);
        }
        rs.close();
        pst.close();
        conn.close();
        return fileLink;
    }



    /**
     * 获取用户userId的所有文件链接
     * @param userId
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static List<FileLink> SearchAll(int userId) throws NamingException, SQLException{
        List<FileLink> fileLinks = new ArrayList<>();
        if(userId < 100000){
            return null;
        }
        Connection conn = initConn();
        String sql = "select * from qqfile_link where userId = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, userId);
        ResultSet rs = pst.executeQuery();
        while(rs.next()){
            int linkId = rs.getInt("linkId");
            int fileId = rs.getInt("fileId");
            String fileName = rs.getString("fileName");
            String fileType = rs.getString("fileType");
            long fileSize = rs.getLong("fileSize");
            char isFolder = rs.getString("isFolder").toCharArray()[0];
            String folderName = rs.getString("folderName");
            String fileList = rs.getString("fileList");
            String folderList = rs.getString("folderList");
            char isRoot = rs.getString("isRoot").toCharArray()[0];
            int parent = rs.getInt("parent");
            // String createLinkTime = rs.getString("createLinkTime");
            String createLinkTime = simpleDf.format(rs.getTimestamp("createLinkTime"));
            FileLink fileLink = new FileLink(linkId, userId, fileId, fileName, fileType, fileSize, isFolder, folderName, fileList, folderList, isRoot, parent, createLinkTime);
            fileLinks.add(fileLink);
        }
        rs.close();
        pst.close();
        conn.close();
        if(fileLinks.size() == 0){
            return null;
        }
        return fileLinks;
    }




    /**
     * 解析子文件链接列表
     * @param linkId
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static List<FileLink> parseFile(int linkId) throws NamingException, SQLException{
        List<FileLink> fileLinks = new ArrayList<>();
        if(linkId < 1000000 || getFileList(linkId)==null){
            return null;
        }
        String[] fileList = getFileList(linkId).split(";");
        if(fileList.length == 0){       // 没有文件
            return null;
        }
        Connection conn = initConn();
        StringBuilder builder = new StringBuilder("select * from qqfile_link where linkId = ? ");
        for(int i=1; i<fileList.length; i++){
            builder.append("or linkId = ? ");
        }
        PreparedStatement pst = conn.prepareStatement(builder.toString());
        for(int i=0; i<fileList.length; i++){
            pst.setString(i+1, fileList[i]);
        }
        ResultSet rs = pst.executeQuery();
        while(rs.next()){
            int linkId1 = rs.getInt("linkId");
            int userId = rs.getInt("userId");
            int fileId = rs.getInt("fileId");
            String fileName = rs.getString("fileName");
            String fileType = rs.getString("fileType");
            long fileSize = rs.getLong("fileSize");
            char isFolder = rs.getString("isFolder").toCharArray()[0];
            String folderName = rs.getString("folderName");
            String fileList1 = rs.getString("fileList");
            String folderList = rs.getString("folderList");
            char isRoot = rs.getString("isRoot").toCharArray()[0];
            int parent = rs.getInt("parent");
            // String createLinkTime = rs.getString("createLinkTime");
            String createLinkTime = simpleDf.format(rs.getTimestamp("createLinkTime"));
            FileLink fileLink = new FileLink(linkId1, userId, fileId, fileName, fileType, fileSize, isFolder, folderName, fileList1, folderList, isRoot, parent, createLinkTime);
            fileLinks.add(fileLink);
        }
        rs.close();
        pst.close();
        conn.close();
        if(fileLinks.size() == 0){
            return null;
        }
        return fileLinks;
    }



    /**
     * 解析子文件夹链接列表
     * @param linkId
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static List<FileLink> parseFolder(int linkId) throws NamingException, SQLException{
        List<FileLink> fileLinks = new ArrayList<>();
        if(linkId < 1000000 || getFolderList(linkId)==null){
            return null;
        }
        String[] folderList = getFolderList(linkId).split(";");
        if(folderList.length == 0){
            return null;
        }
        Connection conn = initConn();
        StringBuilder builder = new StringBuilder("select * from qqfile_link where linkId = ? ");
        for(int i=1; i<folderList.length; i++){
            builder.append("or linkId = ? ");
        }
        PreparedStatement pst = conn.prepareStatement(builder.toString());
        for(int i=0; i<folderList.length; i++){
            pst.setString(i+1, folderList[i]);
        } 
        ResultSet rs = pst.executeQuery();
        while(rs.next()){
            int linkId1 = rs.getInt("linkId");
            int userId = rs.getInt("userId");
            int fileId = rs.getInt("fileId");
            String fileName = rs.getString("fileName");
            String fileType = rs.getString("fileType");
            long fileSize = rs.getLong("fileSize");
            char isFolder = rs.getString("isFolder").toCharArray()[0];
            String folderName = rs.getString("folderName");
            String fileList = rs.getString("fileList");
            String folderList1 = rs.getString("folderList");
            char isRoot = rs.getString("isRoot").toCharArray()[0];
            int parent = rs.getInt("parent");
            // String createLinkTime = rs.getString("createLinkTime");
            String createLinkTime = simpleDf.format(rs.getTimestamp("createLinkTime"));
            FileLink fileLink = new FileLink(linkId1, userId, fileId, fileName, fileType, fileSize, isFolder, folderName, fileList, folderList1, isRoot, parent, createLinkTime);
            fileLinks.add(fileLink);
        }
        rs.close();
        pst.close();
        conn.close();
        if(fileLinks.size() == 0){
            return null;
        }
        return fileLinks;
    }



    /**
     * 添加子文件链接
     * @param linkId    当前文件夹
     * @param fileLinkId    子文件链接
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static boolean addChildFile(int linkId, int fileLinkId) throws NamingException, SQLException{
        boolean flag = false;
        if(linkId < 1000000 || fileLinkId < 1000000){
            return flag;
        }
        String fileList = getFileList(linkId);
        if(fileList == null){
            return flag;
        }
        Map<String, String>  map = new HashMap<String, String>();
        map.put("fileList", fileList+fileLinkId+";");
        flag = edit(linkId, map);
        return flag;
    }


    /**
     * 添加子文件夹链接
     * @param linkId
     * @param folderLinkId
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static boolean addChildFolder(int linkId, int folderLinkId) throws NamingException, SQLException{
        boolean flag = false;
        if(linkId < 1000000 || folderLinkId < 1000000){
            return flag;
        }
        String folderList = getFolderList(linkId);
        if(folderList == null){
            return flag;
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("folderList", folderList+folderLinkId+";");
        flag = edit(linkId, map);
        return flag;
    }


    /**
     * 删除子文件链接
     * @param linkId
     * @param fileLinkId
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static boolean deleteChildFile(int linkId, int fileLinkId) throws NamingException, SQLException{
        boolean flag = false;
        if(linkId < 1000000 || fileLinkId < 1000000){
            return flag;
        }
        String fileList = getFileList(linkId);
        if(fileList == null){
            return flag;
        }
        String[] fileLists = fileList.split(";");
        StringBuilder builder = new StringBuilder();
        String temp = Integer.toString(fileLinkId);
        for(int i=0; i<fileLists.length; i++){
            if(!temp.equals(fileLists[i])){
                builder.append(fileLists[i]);
                builder.append(";");
            }else{
                flag = true;
            }
        }
        if(flag){
            Map<String, String> map = new HashMap<String, String>();
            map.put("fileList", builder.toString());
            flag = edit(linkId, map);
        }
        return flag;
    }


    /**
     * 删除子文件夹链接
     * @param linkId
     * @param folderLinkId
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static boolean deleteChildFolder(int linkId, int folderLinkId) throws NamingException, SQLException{
        boolean flag = false;
        if(linkId < 1000000 || folderLinkId < 1000000){
            return flag;
        }
        String folderList = getFolderList(linkId);
        if(folderList == null){
            return flag;
        }
        String[] folderLists = folderList.split(";");
        StringBuilder builder = new StringBuilder();
        String temp = Integer.toString(folderLinkId);
        for(int i=0; i<folderLists.length; i++){
            if(!temp.equals(folderLists[i])){
                builder.append(folderLists[i]);
                builder.append(";");
            }else{
                flag = true;
            }
        }
        if(flag){
            Map<String, String> map = new HashMap<String, String>();
            map.put("folderList", builder.toString());
            flag = edit(linkId, map);
        }
        return flag;
    }



    /**
     * 根据获取下载的真实文件名或文件夹的子文件和子文件夹列表
     * @param linkId
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static List<String> preDownload(int linkId, int userId) throws NamingException, SQLException{
        List<String> list = new ArrayList<>();
        if(linkId < 1000000){
            return null;
        }
        Connection conn = initConn();
        PreparedStatement pst = null;
        ResultSet rs = null;
        if(!isFolder(linkId)){  // 文件
            String sql = "select qqfile.fileName, qqfile.fileId, qqfile_link.fileName " +
                "from qqfile, qqfile_link " +
                "where qqfile_link.linkId = ? and qqfile_link.userId = ? and qqfile.fileId = qqfile_link.fileId";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, linkId);
            pst.setInt(2, userId);
            rs = pst.executeQuery();
            if(rs.next()){
                String fileName = rs.getString(1);
                int fileId = rs.getInt(2);
                String nickName = rs.getString(3);
                int index = fileName.lastIndexOf(".");
                String trueName;
                if(index == -1){
                    trueName = Integer.toString(fileId);
                }else{
                    String suffix = fileName.substring(index);
                    trueName = Integer.toString(fileId) + suffix;
                }
                list.add(trueName);
                list.add(nickName);
            }
        }else{      // 文件夹
            String sql = "select fileList, folderList, folderName from qqfile_link where linkId = ? and userId = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, linkId);
            pst.setInt(2, userId);
            rs = pst.executeQuery();
            if(rs.next()){
                String fileList = rs.getString(1);
                String folderList = rs.getString(2);
                String folderName = rs.getString(3);
                list.add(fileList);
                list.add(folderList);
                list.add(folderName);
            }
        }
        rs.close();
        pst.close();
        conn.close();
        if(list.size() == 0){
            return null;
        }
        return list;
    }


    /**
     * todo
     * 1. 获取fileSize直接在qqfile_link表不行吗:  行
     * @param linkId
     * @param userId
     * @return
     * @throws NamingException
     * @throws SQLException
     */
    public static long getFileSize(int linkId) throws NamingException, SQLException{
        long size = -1;
        if(linkId < 1000000){
            return size;
        }
        Connection conn = initConn();
        PreparedStatement pst = null;
        ResultSet rs = null;
        if(!isFolder(linkId)){      // 文件
            String sql = "select fileSize " +
                "from qqfile_link " +
                "where linkId = ? ";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, linkId);
            rs = pst.executeQuery();
            if(rs.next()){
                size = rs.getLong(1);
            }
        }
        rs.close();
        pst.close();
        conn.close();
        return size;
    }




}