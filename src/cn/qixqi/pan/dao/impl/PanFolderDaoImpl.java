package cn.qixqi.pan.dao.impl;

import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.qixqi.pan.dao.BaseDao;
import cn.qixqi.pan.dao.PanFolderDao;
import cn.qixqi.pan.entity.PanFolder;

public class PanFolderDaoImpl extends BaseDao implements PanFolderDao {
	private Logger logger = LogManager.getLogger(PanFolderDaoImpl.class.getName());

	@Override
	public int add(PanFolder panFolder) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int folderId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(int folderId, Map<String, Object> map) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PanFolder getParent(int folderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PanFolder> getChildFolders(int folderId) {
		// TODO Auto-generated method stub
		return null;
	}

}
