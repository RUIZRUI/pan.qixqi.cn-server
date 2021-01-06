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
import cn.qixqi.pan.dao.PanFileLinkDao;
import cn.qixqi.pan.entity.PanFileLink;

public class PanFileLinkDaoImpl extends BaseDao implements PanFileLinkDao {
	private Logger logger = LogManager.getLogger(PanFileLinkDaoImpl.class.getName());

	@Override
	public int add(PanFileLink panFileLink) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		int status = 100;
		String sql = "insert into pan_file_link (uid, file_id, link_name, parent) values (?, ?, ?, ?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, panFileLink.getUid());
			pst.setInt(2, panFileLink.getFileId());
			pst.setString(3, panFileLink.getLinkName());
			pst.setInt(4, panFileLink.getParent());
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				// 添加失败
				this.logger.error("pan_file_link 表插入失败：" + panFileLink.toString());
				status = 200;
			}
		} catch(SQLException se) {
			// 添加异常
			this.logger.error("pan_file_link 表插入异常：" + se.getMessage());
			status = 200;
		}
		closeAll(conn, pst, null);
		return status;
	}

	@Override
	public int delete(int linkId) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		int status = 100;
		String sql = "delete from pan_file_link where link_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, linkId);
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				// 删除失败
				this.logger.error("pan_file_link 表删除失败：linkId=" + linkId);
				status = 200;
			}
		} catch (SQLException se) {
			this.logger.error("pan_file_link 表删除异常：" + se.getMessage());
			status = 200;
		}
		closeAll(conn, pst, null);
		return status;
	}

	@Override
	public int update(int linkId, Map<String, Object> map) {
		// TODO Auto-generated method stub
		String table = "pan_file_link";
		String whereSql = " where link_id = " + linkId;
		return executeUpdate(table, whereSql, map);
	}

	@Override
	public long getFileSize(int linkId) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		long fileSize = -1;
		String sql = "select file_size " + 
				"from pan_file_link natural join pan_file " + 
				"where link_id = ?;";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, linkId);
			rs = pst.executeQuery();
			if (rs.next()) {
				fileSize = rs.getLong(1);
			} else {
				// 获取文件大小失败
				this.logger.error("pan_file_link 表获取文件大小失败：linkId=" + linkId);
				fileSize = -1;
			}
		} catch (SQLException se) {
			// 获取文件大小异常
			this.logger.error("pan_file_link 表获取文件大小异常：" + se.getMessage());
			fileSize = -1;
		}
		closeAll(conn, pst, rs);
		return fileSize;
	}

	@Override
	public List<PanFileLink> getChildFileLinks(int folderId) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<PanFileLink> linkList = new ArrayList<PanFileLink>();
		String sql = "select link_id, uid, file_id, link_name, file_type, file_size, create_link_time " + 
				"from pan_file_link natural join pan_file " + 
				"where parent = ?;";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, folderId);
			rs = pst.executeQuery();
			while (rs.next()) {
				int linkId = rs.getInt(1);
				int uid = rs.getInt(2);
				int fileId = rs.getInt(3);
				String linkName = rs.getString(4);
				String fileType = rs.getString(5);
				long fileSize = rs.getLong(6);
				Date createLinkTime = rs.getTimestamp(7);
				linkList.add(new PanFileLink(linkId, uid, fileId, linkName, fileType, fileSize, folderId, createLinkTime));
			}
		} catch (SQLException se) {
			// 获取子文件链接列表异常
			this.logger.error("pan_file_link 表获取子文件链接列表异常：" + se.getMessage());
			linkList = null;
		}
		closeAll(conn, pst, rs);
		return linkList;
	}


}
