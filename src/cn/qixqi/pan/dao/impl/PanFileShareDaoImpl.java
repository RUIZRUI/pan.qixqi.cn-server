package cn.qixqi.pan.dao.impl;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.qixqi.pan.dao.BaseDao;
import cn.qixqi.pan.dao.PanFileShareDao;
import cn.qixqi.pan.entity.PanFileShare;

public class PanFileShareDaoImpl extends BaseDao implements PanFileShareDao {
	private Logger logger = LogManager.getLogger(PanFileShareDaoImpl.class.getName());

	@Override
	public int add(PanFileShare panFileShare) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int shareId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PanFileShare get(String shareMask, String sharePass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PanFileShare> gets(int uid) {
		// TODO Auto-generated method stub
		return null;
	}


}
