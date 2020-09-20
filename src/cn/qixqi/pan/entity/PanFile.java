package cn.qixqi.pan.entity;

import java.util.Date;

public class PanFile{
    private int fileId;     // 7位随机数
    private String fileName;
    private String fileType;
    private long fileSize;
    private int linkNum;
    private Date createTime;

    
    public PanFile(int fileId, String fileName, String fileType, long fileSize, int linkNum, Date createTime) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.linkNum = linkNum;
        this.createTime = createTime;
    }


    public PanFile(int fileId, String fileName, String fileType, long fileSize, Date createTime){
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.createTime = createTime;
    }

    

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public int getLinkNum() {
        return linkNum;
    }

    public void setLinkNum(int linkNum) {
        this.linkNum = linkNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Files [createTime=" + createTime + ", fileId=" + fileId + ", fileName=" + fileName + ", fileSize="
                + fileSize + ", fileType=" + fileType + ", linkNum=" + linkNum + "]";
    }

}