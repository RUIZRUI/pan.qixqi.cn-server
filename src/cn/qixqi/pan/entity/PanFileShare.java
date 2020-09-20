package cn.qixqi.pan.entity;

import java.util.Date;

public class PanFileShare {
	private int shareId;
	private int sourceId;
	private int shareType;		// 分享类型（0文件分享/1文件夹分享）
	private String shareMask;
	private String sharePass;
	private Date createShareTime;
	private Date endShareTime;
	
	
	/**
	 * 生成分享链接
	 * @param sourceId
	 * @param shareMask
	 * @param sharePass
	 * @param endShareTime
	 */
	public PanFileShare(int sourceId, int shareType, String shareMask, String sharePass, Date endShareTime) {
		super();
		this.sourceId = sourceId;
		this.shareType = shareType;
		this.shareMask = shareMask;
		this.sharePass = sharePass;
		this.endShareTime = endShareTime;
	}
	
	/**
	 * 获取分享链接
	 * @param shareId
	 * @param sourceId
	 * @param shareMask
	 * @param sharePass
	 * @param createShareTime
	 * @param endShareTime
	 */
	public PanFileShare(int shareId, int sourceId, int shareType, String shareMask, String sharePass, Date createShareTime,
			Date endShareTime) {
		super();
		this.shareId = shareId;
		this.sourceId = sourceId;
		this.shareType = shareType;
		this.shareMask = shareMask;
		this.sharePass = sharePass;
		this.createShareTime = createShareTime;
		this.endShareTime = endShareTime;
	}

	public int getShareId() {
		return shareId;
	}

	public void setShareId(int shareId) {
		this.shareId = shareId;
	}

	public int getSourceId() {
		return sourceId;
	}

	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}
	
	public int getShareType() {
		return shareType;
	}
	
	public void setShareType(int shareType) {
		this.shareType = shareType;
	}

	public String getShareMask() {
		return shareMask;
	}

	public void setShareMask(String shareMask) {
		this.shareMask = shareMask;
	}

	public String getSharePass() {
		return sharePass;
	}

	public void setSharePass(String sharePass) {
		this.sharePass = sharePass;
	}

	public Date getCreateShareTime() {
		return createShareTime;
	}

	public void setCreateShareTime(Date createShareTime) {
		this.createShareTime = createShareTime;
	}

	public Date getEndShareTime() {
		return endShareTime;
	}

	public void setEndShareTime(Date endShareTime) {
		this.endShareTime = endShareTime;
	}

	@Override
	public String toString() {
		return "PanFileShare [shareId=" + shareId + ", sourceId=" + sourceId + ", shareType=" + shareType
				+ ", shareMask=" + shareMask + ", sharePass=" + sharePass + ", createShareTime=" + createShareTime
				+ ", endShareTime=" + endShareTime + "]";
	}
}
