package cn.qixqi.pan.dao;

import java.util.List;
import java.util.Map;

import cn.qixqi.pan.entity.PanSession;

public interface PanSessionDao {
	public boolean havingSession(int uid1, int uid2);	// 判断好友间是否有会话
	public int add(PanSession panSession);
	public int delete(int sessionId);
	public int update(int sessionId, Map<String, Object> map);
	public List<PanSession> gets(int uid);				// 获取用户会话列表
}
