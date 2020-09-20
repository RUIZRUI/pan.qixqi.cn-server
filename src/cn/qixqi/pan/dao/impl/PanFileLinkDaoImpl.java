package cn.qixqi.pan.dao.impl;

import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.qixqi.pan.dao.BaseDao;
import cn.qixqi.pan.dao.PanFileLinkDao;
import cn.qixqi.pan.entity.PanFileLink;

public class PanFileLinkDaoImpl extends BaseDao implements PanFileLinkDao {
	private Logger logger = LogManager.getLogger(PanFileLinkDaoImpl.class.getName());

	@Override
	public int add(PanFileLink panFileLink) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int linkId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(int linkId, Map<String, Object> map) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getFileSize(int linkId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<PanFileLink> getChildFileLinks(int folderId) {
		// TODO Auto-generated method stub
		return null;
	}


}
