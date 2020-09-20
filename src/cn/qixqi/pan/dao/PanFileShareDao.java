package cn.qixqi.pan.dao;

import java.util.List;

import cn.qixqi.pan.entity.PanFileShare;

public interface PanFileShareDao {
	public int add(PanFileShare panFileShare);
	public int delete(int shareId);
	public PanFileShare get(String shareMask, String sharePass);	// 通过掩码和提取码获取分享链接
	public List<PanFileShare> gets(int uid);									// 获取用户发布的分享链接列表
}
