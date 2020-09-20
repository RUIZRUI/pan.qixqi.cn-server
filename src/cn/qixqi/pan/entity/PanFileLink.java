package cn.qixqi.pan.entity;

import java.util.Date;

public class PanFileLink{

    private int linkId;         // 返回到父文件夹时用到
    private int uid;
    private int fileId;
    private String fileName;
    private String fileType;
    private long fileSize;
    private int parent;			// 不会为空，所以使用int
    private Date createLinkTime;

    /**
     * 网络传输的FileLink
     */
    public PanFileLink(int linkId, int uid, int fileId, String fileName, String fileType, long fileSize, int parent, Date createLinkTime) {
        this.linkId = linkId;
        this.uid = uid;
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.parent = parent;
        this.createLinkTime = createLinkTime;
    }

    public int getLinkId() {
        return linkId;
    }

    public void setLinkId(int linkId) {
        this.linkId = linkId;
    }


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public Date getCreateLinkTime() {
        return createLinkTime;
    }

    public void setCreateLinkTime(Date createLinkTime) {
        this.createLinkTime = createLinkTime;
    }

    @Override
    public String toString() {
        return "FileLink [createLinkTime=" + createLinkTime + ", fileId=" + fileId
                + ", fileName=" + fileName + ", fileSize=" + fileSize + ", fileType=" + fileType
                + ", linkId=" + linkId + ", parent=" + parent + ", uid=" + uid + "]";
    }






}