package cn.qixqi.pan.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		Connection conn = getConnection();
		PreparedStatement pst = null;
		int status = 100;
		String sql = "insert into pan_folder(uid, folder_name, parent) values (?, ?, ?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, panFolder.getUid());
			pst.setString(2, panFolder.getFolderName());
			pst.setObject(3, panFolder.getParent());
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				// 添加失败
				this.logger.error("pan_folder 表插入失败：" + panFolder.toString());
				status = 200;
			}
		} catch (SQLException se) {
			this.logger.error("pan_folder 表插入异常：" + se.getMessage());
			status = 200;
		}
		closeAll(conn, pst, null);
		return status;
	}

	@Override
	public int delete(int folderId) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		int status = 100;
		String sql = "delete from pan_folder where folder_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, folderId);
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				// 删除失败
				this.logger.error("pan_folder 表删除记录失败：folderId=" + folderId);
				status = 200;
			}
		} catch(SQLException se) {
			this.logger.error("pan_folder 表删除记录异常：" + se.getMessage());
			status =  200;
		}
		closeAll(conn, pst, null);
		return status;
	}

	@Override
	public int update(int folderId, Map<String, Object> map) {
		// TODO Auto-generated method stub
		String table = "pan_folder";
		String whereSql = " where folder_id = " + folderId;
		return executeUpdate(table, whereSql, map);
	}

	@Override
	public PanFolder getParent(int folderId) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		PanFolder panFolder = null;
		String sql = "select f2.folder_id, f2.uid, f2.folder_name, f2.parent, f2.create_folder_time " + 
				"from pan_folder as f1 join pan_folder as f2 " + 
				"on f1.parent = f2.folder_id " + 
				"where f1.folder_id = ?;";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, folderId);
			rs = pst.executeQuery();
			if (rs.next()) {
				// 查询成功
				// 父文件夹记录
				int FolderId = rs.getInt(1);
				int uid = rs.getInt(2);
				String folderName = rs.getString(3);
				Integer parent = (Integer)rs.getObject(4);		// 强制转换
				Date createFolderTime = rs.getTimestamp(5);
				panFolder = new PanFolder(FolderId, uid, folderName, parent, createFolderTime);
			} else {
				// 查询失败
				this.logger.error("pan_folder 表查找父文件夹失败：folderId=" + folderId);
				panFolder = null;
			}
		} catch (SQLException se) {
			// 查询异常
			this.logger.error("pan_folder 表查询父文件夹异常：" + se.getMessage());
			panFolder = null;
		}
		closeAll(conn, pst, rs);
		return panFolder;
	}

	@Override
	public List<PanFolder> getChildFolders(int folderId) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<PanFolder> folderList = new ArrayList<PanFolder>();
		String sql = "select f2.folder_id, f2.uid, f2.folder_name, f2.parent, f2.create_folder_time " + 
				"from pan_folder as f1 join pan_folder as f2 " + 
				"on f1.folder_id = f2.parent " + 
				"where f1.folder_id = ?;";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, folderId);
			rs = pst.executeQuery();
			while (rs.next()) {
				// 子文件夹记录
				int FolderId = rs.getInt(1);
				int uid = rs.getInt(2);
				String folderName = rs.getString(3);
				Integer parent = (Integer)rs.getObject(4);		// 强制转换
				Date createFolderTime = rs.getTimestamp(5);
				folderList.add(new PanFolder(FolderId, uid, folderName, parent, createFolderTime));				
			}
		} catch (SQLException se) {
			// 查询异常
			this.logger.error("pan_folder 表查询子文件夹列表异常：" + se.getMessage());
			folderList = null;
		}
		closeAll(conn, pst, rs);
		return folderList;
	}

}
