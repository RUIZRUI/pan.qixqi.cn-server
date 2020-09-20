package cn.qixqi.pan.dao;

import java.util.Map;
import java.util.List;
import cn.qixqi.pan.entity.PanFolder;

public interface PanFolderDao {
	public int add(PanFolder panFolder);
	public int delete(int folderId);
	public int update(int folderId, Map<String, Object> map);
	public PanFolder getParent(int folderId);		// 查找父文件夹（根文件夹为null)
	public List<PanFolder> getChildFolders(int folderId);		// 查找子文件夹
}
