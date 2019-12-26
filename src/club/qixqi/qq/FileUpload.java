package club.qixqi.qq;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.util.List;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import club.qixqi.qq.entity.Files;
import club.qixqi.qq.util.FilesUtil;
import club.qixqi.qq.entity.FileLink;
import club.qixqi.qq.util.FileLinkUtil;


@WebServlet("/FileUpload")
public class FileUpload extends HttpServlet{

    private static final long serialVersionUID = 1L;

    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 上传文件存储目录
    private static final String UPLOAD_DIRECTORY = "upload/files";

    // 上传配置
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;    // 3M
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40;   // 40M
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50M

    public FileUpload(){
        super();
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        // 处理请求乱码
        request.setCharacterEncoding("utf-8");

        // 处理响应乱码
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();

        // String message = "";
        JSONObject message = new JSONObject();

        // 检测是否上传为多媒体文件
        if(!ServletFileUpload.isMultipartContent(request)){
            message.put("error", "提交文件必须为多媒体文件");
            // request.setAttribute("message", message);
        }else{
            // 获取userId
            // if(request.getParameter("userId") == null || request.getParameter("parent") == null){
                // out.println("获取不到用户信息");
                // return;
            // }
            // int userId = Integer.parseInt(request.getParameter("userId"));
            // int parent = Integer.parseInt(request.getParameter("parent"));


            // 配置上传参数
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // 设置内存临界值，超过生成临时文件
            factory.setSizeThreshold(MEMORY_THRESHOLD);
            // 设置临时存储目录
            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
            ServletFileUpload upload = new ServletFileUpload(factory);
            // 设置最大文件上传值
            upload.setFileSizeMax(MAX_FILE_SIZE);
            // 设置最大请求值（文件 + 表单数据）
            upload.setSizeMax(MAX_REQUEST_SIZE);
            // 中文处理
            upload.setHeaderEncoding("UTF-8");
            // 存储路径
            // String uploadPath = request.getServletContext().getRealPath(".") + File.separator + UPLOAD_DIRECTORY;
            String uploadPath = "/qixqi/upload/files";
            // 如果目录不存在则创建
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            try{
                // 提取文件数据
                @SuppressWarnings("unchecked")
                // List<FileItem> formItems = upload.parseRequest(request);
                List<?> formItems = upload.parseRequest(request);
                if(formItems != null && formItems.size() > 0){
                    // 迭代
                    // for(FileItem item : formItems)

                    int userId = -1;
                    int parent = -1;

                    // 获取普通表单字段
                    Iterator iter1 = formItems.iterator();
                    while(iter1.hasNext()){
                        FileItem item = (FileItem) iter1.next();
                        if(item.isFormField()){
                            if("userId".equals(item.getFieldName())){
                                userId = Integer.parseInt(item.getString());
                            }else if("parent".equals(item.getFieldName())){
                                parent = Integer.parseInt(item.getString());
                            }
                        }
                    }

                    // System.out.println(userId + "  " + parent);

                    // 获取文件字段
                    Iterator iter2 = formItems.iterator();
                    while(iter2.hasNext()){
                        FileItem item = (FileItem) iter2.next();

                        if(!item.isFormField()){
                            
                            // 文件字段
                            // 新建Files对象
                            int fileId = (int)((Math.random()*9+1)*1000000);
                            String fileName = new File(item.getName()).getName();
                            // String fileType = item.getContentType();
                            String fileType = getFileType(fileName);
                            long fileSize = item.getSize();     // 字节
                            int linkNum = 0;
                            String createTime = df.format(new Date());
                            String lastUseTime = null;
                            Files files = new Files(fileId, fileName, fileType, fileSize, linkNum, createTime, lastUseTime);
                            // System.out.println("fileType: " + fileType);
                            FilesUtil.add(files);
                            System.out.println(files.toString());
                        
                            // 新建FileLink对象
                            int linkId = (int)((Math.random()*9+1)*1000000);
                            FileLink fileLink = new FileLink(linkId, userId, fileId, fileName, fileType, fileSize, 'n', null, null, null, 'n', parent, createTime);
                            FileLinkUtil.add(fileLink);

                            // 父文件夹添加子文件链接
                            FileLinkUtil.addChildFile(parent, linkId);

                        
                            String newName = fileId + fileName.substring(fileName.lastIndexOf("."));
                            // System.out.println("newName: " + newName);
                            String filePath = uploadPath + File.separator + newName;
                            // System.out.println("filePath: " + filePath);
                            File storeFile = new File(filePath);
                            // 文件已存在
                            if(storeFile.exists()){
                                message.put("error", "文件已存在，重新覆盖");
                            }
                            item.write(storeFile);
                            // message.put("response", "文件上传成功");
                            message.put("response", JSON.toJSONString(fileLink));
                        }
                    }
                }
            } catch(Exception e){
                message.put("error", "error");
                e.printStackTrace();
                System.out.println("Exception: " + e.getMessage());
            }
        }
        out.println(message.toJSONString());
    }

    private String getFileType(String fileName){
        String fileType = "unknown";
        int index = fileName.lastIndexOf(".");
        if(index == -1){    // 文件没有后缀
            return fileType;
        }
        String suffix = fileName.substring(index+1);    // 获取后缀
        if("jpg".equalsIgnoreCase(suffix) || "png".equalsIgnoreCase(suffix) || "pdf".equalsIgnoreCase(suffix) || "svn".equalsIgnoreCase(suffix) || "tiff".equalsIgnoreCase(suffix) || "swf".equalsIgnoreCase(suffix)){
            fileType = "picture";       // 图片格式
        }else if("flv".equalsIgnoreCase(suffix) || "mp4".equalsIgnoreCase(suffix) || "mvb".equalsIgnoreCase(suffix) || "rmvb".equalsIgnoreCase(suffix)){
            fileType = "video";         // 视频格式
        }else if("rar".equalsIgnoreCase(suffix) || "zip".equalsIgnoreCase(suffix) || "7z".equalsIgnoreCase(suffix) || "gz".equalsIgnoreCase(suffix) || "tar".equalsIgnoreCase(suffix)){
            fileType = "rar";           // 压缩包格式
        }else if("txt".equalsIgnoreCase(suffix) || "doc".equalsIgnoreCase(suffix) || "docx".equalsIgnoreCase(suffix) || "ppt".equalsIgnoreCase(suffix) || "pptx".equalsIgnoreCase(suffix) || "xls".equalsIgnoreCase(suffix) || "csv".equalsIgnoreCase(suffix)){
            fileType = "document";      // 文档
        }else if("mp3".equalsIgnoreCase(suffix) || "flac".equalsIgnoreCase(suffix) || "mid".equalsIgnoreCase(suffix) || "wma".equalsIgnoreCase(suffix) || "oga".equalsIgnoreCase(suffix) || "cda".equalsIgnoreCase(suffix) || "wav".equalsIgnoreCase(suffix)){ 
            fileType = "audio";     // 音频文件
        }else if("apk".equalsIgnoreCase(suffix)){      
            fileType = "apk";        // apk文件
        }
        return fileType;
    }
}