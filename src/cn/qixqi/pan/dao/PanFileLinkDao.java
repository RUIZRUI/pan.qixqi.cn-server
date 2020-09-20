package cn.qixqi.pan.dao;

import java.util.List;
import java.util.Map;

import cn.qixqi.pan.entity.PanFileLink;

public interface PanFileLinkDao {
	public int add(PanFileLink panFileLink);
	public int delete(int linkId);
	public int update(int linkId, Map<String, Object> map);
	public long getFileSize(int linkId);		// 获取文件链接对应物理文件大小
	public List<PanFileLink> getChildFileLinks(int folderId);		// 获取一个文件夹的子文件列表
}
