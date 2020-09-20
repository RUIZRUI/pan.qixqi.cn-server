package cn.qixqi.pan.dao.impl;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.qixqi.pan.dao.BaseDao;
import cn.qixqi.pan.dao.PanMessageDao;
import cn.qixqi.pan.entity.PanMessage;

public class PanMessageDaoImpl extends BaseDao implements PanMessageDao {
	private Logger logger = LogManager.getLogger(PanMessageDaoImpl.class.getName());

	@Override
	public int add(PanMessage panMessage) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int msgId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int uid1, int uid2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<PanMessage> gets(int uid1, int uid2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PanMessage get(int msgId) {
		// TODO Auto-generated method stub
		return null;
	}


	

}
