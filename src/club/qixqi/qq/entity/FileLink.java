package club.qixqi.qq.entity;


public class FileLink{

    private int linkId;         // 返回到父文件夹时用到
    private int userId;
    private int fileId;
    private String fileName;
    private String fileType;
    private long fileSize;
    private char isFolder;
    private String folderName;
    private String fileList;      // 监听子文件增加或删除
    private String folderList;      // 监听子文件夹增加、修改或删除
    private char isRoot;
    private int parent;
    private String createLinkTime;

    /**
     * 网络传输的FileLink
     */
    public FileLink(int linkId, int userId, int fileId, String fileName, String fileType, long fileSize, char isFolder,
            String folderName, String fileList, String folderList, char isRoot, int parent, String createLinkTime) {
        this.linkId = linkId;
        this.userId = userId;
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.isFolder = isFolder;
        this.folderName = folderName;
        this.fileList = fileList;
        this.folderList = folderList;
        this.isRoot = isRoot;
        this.parent = parent;
        this.createLinkTime = createLinkTime;
    }

    
    /**
     * 数据库表中存的FileLink
     * @param linkId
     * @param userId
     * @param fileId 
     * @param fileName 
     * @param fileType
     * @param isFolder
     * @param folderName
     * @param fileList
     * @param folderList
     * @param isRoot
     * @param parent
     * @param createLinkTime
     */
    /* public FileLink(int linkId, int userId, int fileId, String fileName, String fileType, char isFolder, String folderName, String fileList,
            String folderList, char isRoot, int parent, String createLinkTime) {
        this.linkId = linkId;
        this.userId = userId;
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.isFolder = isFolder;
        this.folderName = folderName;
        this.fileList = fileList;
        this.folderList = folderList;
        this.isRoot = isRoot;
        this.parent = parent;
        this.createLinkTime = createLinkTime;
    }*/


    public int getLinkId() {
        return linkId;
    }

    public void setLinkId(int linkId) {
        this.linkId = linkId;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public char getIsFolder() {
        return isFolder;
    }

    public void setIsFolder(char isFolder) {
        this.isFolder = isFolder;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFileList() {
        return fileList;
    }

    public void setFileList(String fileList) {
        this.fileList = fileList;
    }

    public String getFolderList() {
        return folderList;
    }

    public void setFolderList(String folderList) {
        this.folderList = folderList;
    }

    public char getIsRoot() {
        return isRoot;
    }

    public void setIsRoot(char isRoot) {
        this.isRoot = isRoot;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public String getCreateLinkTime() {
        return createLinkTime;
    }

    public void setCreateLinkTime(String createLinkTime) {
        this.createLinkTime = createLinkTime;
    }

    @Override
    public String toString() {
        return "FileLink [createLinkTime=" + createLinkTime + ", fileId=" + fileId + ", fileList=" + fileList
                + ", fileName=" + fileName + ", fileSize=" + fileSize + ", fileType=" + fileType + ", folderList="
                + folderList + ", folderName=" + folderName + ", isFolder=" + isFolder + ", isRoot=" + isRoot
                + ", linkId=" + linkId + ", parent=" + parent + ", userId=" + userId + "]";
    }






}