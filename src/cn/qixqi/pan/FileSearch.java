package cn.qixqi.pan;

import java.io.PrintWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.qixqi.pan.entity.PanFileLink;
import cn.qixqi.pan.util.FileLinkUtil;


/**
 * todo
 * 1. 应该添加验证信息
 */
@WebServlet("/FileSearch")
public class FileSearch extends HttpServlet{

    private static final long serialVersionUID = 1L;

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
            if("searchFolder".equals(method)){
                if(request.getParameter("linkId") == null || "".equals(request.getParameter("linkId"))){
                    message.put("error", "请正确输入信息");
                }else{
                    int linkId = Integer.parseInt(request.getParameter("linkId"));
                    if(!FileLinkUtil.isFolder(linkId)){
                        message.put("error", "要查找的不是文件夹");
                    }else{
                        List<PanFileLink> folderList = FileLinkUtil.parseFolder(linkId);
                        List<PanFileLink> fileList = FileLinkUtil.parseFile(linkId);
                        // JSONObject folderJson = (JSONObject) JSON.toJSON(folderList);
                        // JSONObject fileJson = (JSONObject) JSON.toJSON(fileList);
                        JSONArray folderJson = JSONArray.parseArray(JSON.toJSONString(folderList));
                        JSONArray fileJson = JSONArray.parseArray(JSON.toJSONString(fileList));
                        message.put("folderList", folderJson);
                        message.put("fileList", fileJson);
                    }
                }
            }else if("getRootFolder".equals(method)){
                if(request.getParameter("userId") == null || "".equals(request.getParameter("userId"))){
                    message.put("error", "请正确输入信息");
                }else{
                    int userId = Integer.parseInt(request.getParameter("userId"));
                    PanFileLink fileLink = FileLinkUtil.getRootFolder(userId);
                    // System.out.println(fileLink.toString());
                    JSONObject rootFolder = (JSONObject) JSON.toJSON(fileLink);
                    // System.out.println(rootFolder.toJSONString());
                    message.put("rootFolder", rootFolder);
                }
            }else{
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