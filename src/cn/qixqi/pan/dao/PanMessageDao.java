package cn.qixqi.pan.dao;

import java.util.List;

import cn.qixqi.pan.entity.PanMessage;

public interface PanMessageDao {
	public int add(PanMessage panMessage);
	public int delete(int msgId);		// 撤回一条消息
	public int delete(int uid1, int uid2);	// 删除两个好友消息
	public List<PanMessage> gets(int uid1, int uid2);	// 获取好友间消息
	public PanMessage get(int msgId);		// 获取一条消息
}
