package cn.qixqi.pan.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		Connection conn = getConnection();
		PreparedStatement pst = null;
		int status = 100;
		String sql = "insert into pan_file_share (uid, source_id, share_type, share_mask, share_pass, end_share_time) values (?, ?, ?, ?, ?, ?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, panFileShare.getUid());
			pst.setInt(2, panFileShare.getSourceId());
			pst.setInt(3, panFileShare.getShareType());
			pst.setString(4, panFileShare.getShareMask());
			pst.setString(5, panFileShare.getSharePass());
			pst.setTimestamp(6, new Timestamp(panFileShare.getEndShareTime().getTime()));
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				// 添加失败
				this.logger.error("pan_file_share 表插入失败：" + panFileShare.toString());
				status = 200;
			}
		} catch (SQLException se) {
			// 添加异常
			this.logger.error("pan_file_share 表插入异常：" + se.getMessage());
			status = 200;
		}
		closeAll(conn, pst, null);
		return status;
	}

	@Override
	public int delete(int shareId) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		int status = 100;
		String sql = "delete from pan_file_share where share_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, shareId);
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				// 删除失败
				this.logger.error("pan_file_share 表删除记录失败：shareId=" + shareId);
				status = 200;
			}
		} catch (SQLException se) {
			// 删除异常
			this.logger.error("pan_file_share 表删除记录异常：" + se.getMessage());
			status = 200;
		}
		closeAll(conn, pst, null);
		return status;
	}

	@Override
	public PanFileShare get(String shareMask, String sharePass) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		PanFileShare fileShare = null;
		String sql = "select share_id, uid, source_id, share_type, create_share_time, end_share_time from pan_file_share where share_mask = ? and share_pass = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, shareMask);
			pst.setString(2, sharePass);
			rs = pst.executeQuery();
			if (rs.next()) {
				int shareId = rs.getInt(1);
				int uid = rs.getInt(2);
				int sourceId = rs.getInt(3);
				int shareType = rs.getInt(4);
				Date createShareTime = rs.getTimestamp(5);
				Date endShareTime = rs.getTimestamp(6);
				fileShare = new PanFileShare(shareId, uid, sourceId, shareType, shareMask, sharePass, createShareTime, endShareTime);
			} else {
				// 查询失败
				this.logger.error("pan_file_share 表掩码密码查询失败：shareMask=" + shareMask + ", sharePass=" + sharePass);
				fileShare = null;
			}
		} catch (SQLException se) {
			// 查询异常
			this.logger.error("pan_file_share 表掩码密码查询异常：" + se.getMessage());
			fileShare = null;
		}
		closeAll(conn, pst, rs);
		return fileShare;
	}

	@Override
	public List<PanFileShare> gets(int uid) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<PanFileShare> shareList = new ArrayList<PanFileShare>();
		String sql = "select share_id, source_id, share_type, share_mask, share_pass, create_share_time, end_share_time from pan_file_share where uid = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, uid);
			rs = pst.executeQuery();
			while (rs.next()) {
				int shareId = rs.getInt(1);
				int sourceId = rs.getInt(2);
				int shareType = rs.getInt(3);
				String shareMask = rs.getString(4);
				String sharePass = rs.getString(5);
				Date createShareTime = rs.getTimestamp(6);
				Date endShareTime = rs.getTimestamp(7);
				shareList.add(new PanFileShare(shareId, uid, sourceId, shareType, shareMask, sharePass, createShareTime, endShareTime));
			}
		} catch (SQLException se) {
			// 查询 异常
			this.logger.error("pan_file_share 表查询分享列表异常：" + se.getMessage());
			shareList = null;
		}
		closeAll(conn, pst, rs);
		return shareList;
	}


}
