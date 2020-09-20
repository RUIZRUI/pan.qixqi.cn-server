package cn.qixqi.pan.dao;

import java.util.List;

import cn.qixqi.pan.entity.PanFriend;

public interface PanFriendDao {
	public int add(int uid1, int uid2);
	public int delete(int uid1, int uid2);
	public boolean isFriend(int uid1, int uid2);
	public List<PanFriend> gets(int uid);	// 获取用户好友列表
}
