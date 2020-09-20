package cn.qixqi.pan.dao.impl;

import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.qixqi.pan.dao.BaseDao;
import cn.qixqi.pan.dao.PanSessionDao;
import cn.qixqi.pan.entity.PanSession;

public class PanSessionDaoImpl extends BaseDao implements PanSessionDao {
	private Logger logger = LogManager.getLogger(PanSessionDaoImpl.class.getName());

	@Override
	public boolean havingSession(int uid1, int uid2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int add(PanSession panSession) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int sessionId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(int sessionId, Map<String, Object> map) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<PanSession> gets(int uid) {
		// TODO Auto-generated method stub
		return null;
	}


	
}
