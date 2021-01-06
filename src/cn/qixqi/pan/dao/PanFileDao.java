package cn.qixqi.pan.dao;

import java.util.Map;

import cn.qixqi.pan.entity.PanFile;

public interface PanFileDao {
	public int add(PanFile file);
	public int delete(int fileId);
	public int update(int fileId, Map<String, Object> map);
	public int addLink(int fileId, int count);
	public int deleteLink(int fileId, int count);
	public int getLinkNum(int fileId);
}
