package cn.qixqi.pan.entity;

import java.util.Date;

public class PanFolder {
	private int folderId;
	private int uid;
	private String folderName;
	private Integer parent;		// 可空
	private Date createFolderTime;
	
	/**
	 * 创建文件夹
	 * @param uid
	 * @param folderName
	 * @param parent
	 */
	public PanFolder(int uid, String folderName, Integer parent) {
		super();
		this.uid = uid;
		this.folderName = folderName;
		this.parent = parent;
	}

	/**
	 * 获取文件夹信息
	 * @param folderId
	 * @param uid
	 * @param folderName
	 * @param parent
	 * @param createFolderTime
	 */
	public PanFolder(int folderId, int uid, String folderName, Integer parent, Date createFolderTime) {
		super();
		this.folderId = folderId;
		this.uid = uid;
		this.folderName = folderName;
		this.parent = parent;
		this.createFolderTime = createFolderTime;
	}


	public int getFolderId() {
		return folderId;
	}


	public void setFolderId(int folderId) {
		this.folderId = folderId;
	}


	public int getUid() {
		return uid;
	}


	public void setUid(int uid) {
		this.uid = uid;
	}


	public String getFolderName() {
		return folderName;
	}


	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}


	public Integer getParent() {
		return parent;
	}


	public void setParent(Integer parent) {
		this.parent = parent;
	}


	public Date getCreateFolderTime() {
		return createFolderTime;
	}


	public void setCreateFolderTime(Date createFolderTime) {
		this.createFolderTime = createFolderTime;
	}


	@Override
	public String toString() {
		return "PanFolder [folderId=" + folderId + ", uid=" + uid + ", folderName=" + folderName + ", parent=" + parent
				+ ", createFolderTime=" + createFolderTime + "]";
	}
	
	
	
	
}
