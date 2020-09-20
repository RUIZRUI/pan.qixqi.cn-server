package cn.qixqi.pan.dao;

import java.util.Map;

import cn.qixqi.pan.entity.PanUser;

public interface PanUserDao {
	public int add(PanUser panUser);
	public int delete(int uid);
	public int update(int uid, Map<String, Object> map);
	public int resetPass(int uid, String oldPass, String newPass);
	public PanUser get(String key, String password);
	public PanUser get(int uid);
	public String getAvatar(int uid);
	public PanUser getSimpleUser(int uid);			// 获取陌生人用户信息
}
