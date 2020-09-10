package cn.qixqi.pan;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.qixqi.pan.entity.FileLink;
import cn.qixqi.pan.util.FileLinkUtil;
import cn.qixqi.pan.util.UserUtil;


/**
 * todo
 * 1. 提交的字段(userId, linkId)不是数字串时怎么处理
 */

@WebServlet("/FileDownload")
public class FileDownload extends HttpServlet{


    private static final long serialVersionUID = 1L;

    private static final String DIRECTORY = "/qixqi/upload/files/";


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        // 处理请求乱码
        request.setCharacterEncoding("utf-8");

        // 处理响应乱码
        // response.setHeader("content-disposition", "attachment;filename=qixqi");
        // response.setContentType("text/html; charset=utf-8");
        // response.setContentType("multipart/form-data; charset=utf-8");
        // PrintWriter out = response.getWriter();

        String message = "";

        if(request.getParameter("userId") == null || request.getParameter("password") == null || request.getParameter("linkId") == null){
            response.setContentType("text/html; charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println("请提交必要的字段");
            return;
        }

        
        try{
            String method = request.getParameter("method");
            int userId = Integer.parseInt(request.getParameter("userId"));
            String password = request.getParameter("password");
            int linkId = Integer.parseInt(request.getParameter("linkId"));
            if(UserUtil.loginSearch(userId, password) == null){
                message = "登录失败";
                System.out.println(message);
                return;
            }
            if("getSize".equals(method)){
                // 获取大小只能文件，文件夹没有此功能
                response.setContentType("text/html; charset=utf-8");
                PrintWriter out = response.getWriter();
                long fileSize = FileLinkUtil.getFileSize(linkId);
                message = Long.toString(fileSize);
                out.println(message);
            }else if("download".equals(method)){
                
                List<String> list = FileLinkUtil.preDownload(linkId, userId);
                if(list == null){
                    message = "提交的下载信息有误";
                } else if(list.size() == 2){       // 文件下载
                    response.setContentType("multipart/form-data; charset=utf-8");
                    String fileTrueName = list.get(0);
                    String nickName = list.get(1);
                    response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(nickName, "utf-8"));
                    System.out.println(DIRECTORY + fileTrueName);
                    InputStream inputStream = new FileInputStream(DIRECTORY+fileTrueName);  // 获取文件输入流
                    int len = 0;
                    byte[] buffer = new byte[1024];
                    OutputStream outputStream = response.getOutputStream();
                    while((len = inputStream.read(buffer)) > 0){
                        outputStream.write(buffer, 0, len);     // 将缓存区的数据输出到客户端浏览器
                    }
                    inputStream.close();
                } else if(list.size() == 3){       // 文件夹下载
                    response.setContentType("text/html; charset=utf-8");
                    PrintWriter out = response.getWriter();
                    message = "文件夹下载开发中...";
                    out.println(message);
                }
            }else{
                response.setContentType("text/html; charset=utf-8");
                PrintWriter out = response.getWriter();
                message = "你搞什么";
                out.println(message);
            }
            System.out.println(message);


        } catch(NamingException ne){
            System.out.println("操作失败: NamingException " + ne.getMessage());
            // out.println("<h3 align='center'>操作失败: NamingException " + ne.getMessage() + "</h3>\n");
            // out.println("error");
            ne.printStackTrace();
        } catch(SQLException se){
            System.out.println("操作失败: SQLException " + se.getMessage());
            // out.println("<h3 align='center'>操作失败: SQLException " + se.getMessage() + "</h3>\n");
            // out.println("error");
            se.printStackTrace();
        } catch(Exception e){
            System.out.println("异常：Exception" + e.getMessage());
            // out.println("error");
            e.printStackTrace();
        }

    }





    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doGet(request, response);
    }




}