package club.qixqi.qq;

import java.io.PrintWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;


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
import club.qixqi.qq.util.FileShareUtil;


@WebServlet("/FileShare")
public class FileShare extends HttpServlet{

    private static final long serialVersionUID = 1L;

    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



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
            // 接收数据
            String method = request.getParameter("method");
            if("create".equals(method)){
                if(request.getParameter("linkId") == null || "".equals(request.getParameter("linkId").trim())){
                    message.put("error", "请正确输入信息");
                } else{
                    int linkId = Integer.parseInt(request.getParameter("linkId"));
                    String shareMask = UUID.randomUUID().toString().replaceAll("-", "");
                    String sharePass = FileShareUtil.add(linkId, shareMask);
                    message.put("response", FileShareUtil.createShareLink(shareMask, sharePass));
                }
            } else if("parse".equals(method)){
                if(request.getParameter("shareMask") == null || request.getParameter("sharePass") == null){
                    message.put("error", "请输入完整信息");
                } else if("".equals(request.getParameter("shareMask").trim()) || "".equals(request.getParameter("sharePass").trim())){
                    message.put("error", "请输入正确信息");
                } else{
                    String shareMask = request.getParameter("shareMask");
                    String sharePass = request.getParameter("sharePass");
                    int linkId = FileShareUtil.search(shareMask, sharePass);
                    if(linkId == -1){
                        message.put("error", "分享链接不存在或已失效");
                    } else{
                        FileLink fileLink = FileLinkUtil.SearchOne(linkId);
                        JSONObject shareFile = (JSONObject) JSON.toJSON(fileLink);
                        message.put("response", shareFile);
                    }
                }
            } else if("getShare".equals(method)){
                if(request.getParameter("linkId") == null || request.getParameter("userId") ==null || request.getParameter("parent") == null){
                    message.put("error", "请输入完整信息");
                } else if("".equals(request.getParameter("linkId").trim()) || "".equals(request.getParameter("userId").trim()) || "".equals(request.getParameter("parent"))){
                    message.put("error", "请输入正确信息");
                } else{
                    int linkId = Integer.parseInt(request.getParameter("linkId"));
                    int userId = Integer.parseInt(request.getParameter("userId"));
                    int parent = Integer.parseInt(request.getParameter("parent"));
                    FileLink shareFileLink = FileLinkUtil.SearchOne(linkId);
                    if(shareFileLink == null){
                        message.put("error", "没有此文件链接");
                    } else{
                        int linkId1 = (int)((Math.random()*9+1)*1000000);
                        int fileId1 = shareFileLink.getFileId();
                        String fileName1 = shareFileLink.getFileName();
                        String fileType1 = shareFileLink.getFileType();
                        long fileSize1 = shareFileLink.getFileSize();
                        char isFolder1 = shareFileLink.getIsFolder();
                        String folderName1 = shareFileLink.getFolderName();
                        String fileList1 = shareFileLink.getFileList();
                        String folderList1 = shareFileLink.getFolderList();
                        char isRoot1 = shareFileLink.getIsRoot();
                        String createLinkTime = df.format(new Date());
                        FileLink fileLink = new FileLink(linkId1, userId, fileId1, fileName1, fileType1, fileSize1, isFolder1, folderName1, fileList1, folderList1, isRoot1, parent, createLinkTime);
                        if(FileLinkUtil.add(fileLink)){
                            if(isFolder1 == 'y'){       // 父文件夹添加子文件夹
                                FileLinkUtil.addChildFolder(parent, linkId1);
                            } else{         // 父文件夹添加子文件
                                FileLinkUtil.addChildFile(parent, linkId1);
                            }
                            message.put("response", JSON.toJSONString(fileLink));
                        }else{
                            message.put("error", "创建新链失败");
                        }
                    }
                }
            } else if("delete".equals(method)){
                if(request.getParameter("shareMask") == null && (request.getParameter("linkId") == null || "".equals(request.getParameter("linkId").trim()))){
                    message.put("error", "请输入正确信息");
                } else if(request.getParameter("shareMask") != null){
                    String shareMask = request.getParameter("shareMask");
                    FileShareUtil.delete(shareMask);
                    message.put("response", "删除成功");
                } else{
                    int linkId = Integer.parseInt(request.getParameter("linkId"));
                    FileShareUtil.delete(linkId);
                    message.put("response", "删除成功");
                }
            } else if("searchAll".equals(method)){
                if(request.getParameter("userId") == null || "".equals(request.getParameter("userId").trim())){
                    message.put("error", "请输入正确信息");
                } else{
                    int userId = Integer.parseInt(request.getParameter("userId"));
                    List<String> list = FileShareUtil.searchAll(userId);
                    message.put("response", JSON.toJSONString(list));
                }                
            } else{
                message.put("error", "你搞的什么操作，我不晓得");
            }
            out.println(message.toJSONString());
        } catch(NamingException ne){
            System.out.println("操作失败: NamingException " + ne.getMessage());
            // out.println("<h3 align='center'>操作失败: NamingException " + ne.getMessage() + "</h3>\n");
            message.put("error", "error");
            out.println(message.toJSONString());
            ne.printStackTrace();
        } catch(SQLException se){
            System.out.println("操作失败: SQLException " + se.getMessage());
            // out.println("<h3 align='center'>操作失败: SQLException " + se.getMessage() + "</h3>\n");
            message.put("error", "error");
            out.println(message.toJSONString());
            se.printStackTrace();
        }
    }




    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doGet(request, response);
    }
}
