package club.qixqi.qq;

import java.io.PrintWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import club.qixqi.qq.entity.FileLink;
import club.qixqi.qq.util.FileLinkUtil;



@WebServlet("/Folders")
public class Folders extends HttpServlet{
    private static final long serialVersionUID = 1L;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        // 处理请求乱码
        request.setCharacterEncoding("utf-8");

        // 处理响应乱码
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();
        
        // String message;
        JSONObject message = new JSONObject();
        try{
            String method = request.getParameter("method");
            if("create".equals(method)){
                if(request.getParameter("userId") == null || request.getParameter("parent") == null || request.getParameter("folderName") == null){
                    message.put("error", "请提交完整信息");
                }else if("".equals(request.getParameter("userId")) || "".equals(request.getParameter("parent")) || "".equals(request.getParameter("folderName"))){
                    message.put("error", "请提交正确信息");
                }else{
                    int linkId = (int)((Math.random()*9+1)*1000000);
                    int userId = Integer.parseInt(request.getParameter("userId"));
                    int fileId = -1;
                    String fileName = null;
                    String fileType = null;
                    long fileSize = -1;
                    char isFolder = 'y';
                    String folderName = request.getParameter("folderName");
                    String fileList = "";
                    String folderList = "";
                    char isRoot = 'n';
                    int parent = Integer.parseInt(request.getParameter("parent"));
                    String createLinkTime = df.format(new Date());
                    FileLink fileLink = new FileLink(linkId, userId, fileId, fileName, fileType, fileSize, isFolder, folderName, fileList, folderList, isRoot, parent, createLinkTime);
                    FileLinkUtil.add(fileLink);

                    // 将 folderLinkId 添加到父文件夹下
                    FileLinkUtil.addChildFolder(parent, linkId);
                    message.put("response", "文件夹创建成功");
                }
            }else if("rename".equals(method)){
                if(request.getParameter("linkId") == null || request.getParameter("newName") == null){
                    message.put("error", "请提交完整信息");
                }else if("".equals(request.getParameter("linkId")) || "".equals(request.getParameter("newName"))){
                    message.put("error", "请提交正确信息");
                }else{
                    int linkId = Integer.parseInt(request.getParameter("linkId"));
                    String newName = request.getParameter("newName");
                    Map<String, String> map = new HashMap<String, String>();
                    if(FileLinkUtil.isFolder(linkId)){      // 文件夹重命名
                        map.put("folderName", newName);
                    }else{      // 文件重命名
                        map.put("fileName", newName);
                    }
                    if(FileLinkUtil.edit(linkId, map)){
                        message.put("response", "重命名成功");
                    }else{
                        message.put("error", "重命名失败");
                    }
                }
            }else{
                message.put("error", "你搞的什么操作");
            }
        } catch(NamingException ne){
            ne.printStackTrace();
            System.out.println(ne.getMessage());
            message.put("error", "error");
        } catch(SQLException se){
            se.printStackTrace();
            System.out.println(se.getMessage());
            message.put("error", "error");
        }
        out.println(message.toJSONString());
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doGet(request, response);
    }



}
