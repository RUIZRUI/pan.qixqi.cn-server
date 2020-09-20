package cn.qixqi.pan.dao.impl;

import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.qixqi.pan.dao.BaseDao;
import cn.qixqi.pan.dao.PanFileDao;
import cn.qixqi.pan.entity.PanFile;

public class PanFileDaoImpl extends BaseDao implements PanFileDao {
	private Logger logger = LogManager.getLogger(PanFileDaoImpl.class.getName());

	@Override
	public int add(PanFile file) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int fileId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(int fileId, Map<String, Object> map) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addLink(int fileId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteLink(int fileId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLinkNum(int fileId) {
		// TODO Auto-generated method stub
		return 0;
	}



}
