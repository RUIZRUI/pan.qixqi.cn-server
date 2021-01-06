package cn.qixqi.pan.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.qixqi.pan.dao.BaseDao;
import cn.qixqi.pan.dao.PanSessionDao;
import cn.qixqi.pan.entity.PanSession;

public class PanSessionDaoImpl extends BaseDao implements PanSessionDao {
	private Logger logger = LogManager.getLogger(PanSessionDaoImpl.class.getName());

	/**
	 * 用户 uid1 和 uid2 是否存在会话
	 */
	@Override
	public boolean havingSession(int uid1, int uid2) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		boolean result = true;
		String sql = "select * from pan_session where (uid1 = ? and uid2 = ?) or (uid1 = ? and uid2 = ?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, uid1);
			pst.setInt(2, uid2);
			pst.setInt(3, uid2);
			pst.setInt(4, uid1);
			rs = pst.executeQuery();
			if (!rs.next()) {
				// 不存在 会话
				result = false;
			}
		} catch(SQLException se) {
			this.logger.error("判断用户之间是否存在 会话异常：" + se.getMessage());
			result = false;
		}
		closeAll(conn, pst, rs);
		return result;
	}

	@Override
	public int add(PanSession panSession) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		int status = 100;
		String sql = "insert into pan_session(uid, uid1, uid2, last_msg, last_msg_username, last_msg_time, last_msg_type, session_type) values (?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, panSession.getUid());
			pst.setInt(2, panSession.getUid1());
			pst.setInt(3, panSession.getUid2());
			pst.setString(4, panSession.getLastMsg());
			pst.setString(5, panSession.getLastMsgUsername());
			pst.setTimestamp(6, new Timestamp(panSession.getLastMsgTime().getTime()));
			pst.setString(7, String.valueOf(panSession.getLastMsgType()));
			pst.setString(8,  String.valueOf(panSession.getSessionType()));
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				// 添加会话失败
				this.logger.error("添加会话失败：uid1=" + panSession.getUid1() + ", uid2=" + panSession.getUid2());
				status = 200;
			}
		} catch(SQLException se) {
			this.logger.error("添加会话异常：" + se.getMessage());
			status = 200;
		}
		closeAll(conn, pst, null);
		return status;
	}

	@Override
	public int delete(int sessionId) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		int status = 100;
		String sql = "delete from pan_session where session_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, sessionId);
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				// 删除会话失败
				this.logger.error("删除会话失败：sessionId=" + sessionId);
				status = 200;
			}
		} catch(SQLException se) {
			this.logger.error("删除会话异常：" + se.getMessage());
			status = 200;
		}
		closeAll(conn, pst, null);
		return status;
	}

	@Override
	public int update(int sessionId, Map<String, Object> map) {
		// TODO Auto-generated method stub
		String table = "pan_session";
		String whereSql = " where session_id = " + sessionId;
		return executeUpdate(table, whereSql, map);
	}

	@Override
	public List<PanSession> gets(int uid) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<PanSession> sessionList = new ArrayList<PanSession>();
		String sql = "select s.session_id, s.uid1, s.uid2, u1.username, u2.username, u1.avatar, u2.avatar, s.last_msg, s.last_msg_username, last_msg_time, last_msg_type, session_type " + 
				"from pan_session as s " + 
				"join pan_user as u1 on s.uid1 = u1.uid " + 
				"join pan_user as u2 on s.uid2 = u2.uid " + 
				"where s.uid = ?;";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, uid);
			rs = pst.executeQuery();
			while (rs.next()) {
				int sessionId = rs.getInt(1);
				int uid1 = rs.getInt(2);
				int uid2 = rs.getInt(3);
				String username1 = rs.getString(4);
				String username2 = rs.getString(5);
				String avatar1 = rs.getString(6);
				String avatar2 = rs.getString(7);
				String lastMsg = rs.getString(8);
				String lastMsgUsername = rs.getString(9);
				Date lastMsgTime = rs.getTimestamp(10);
				char lastMsgType = rs.getString(11).charAt(0);
				char sessionType = rs.getString(12).charAt(0);
				sessionList.add(new PanSession(uid, sessionId, uid1, uid2, username1, username2, avatar1, avatar2, lastMsg, lastMsgUsername, lastMsgTime, lastMsgType, sessionType));
			}
		} catch(SQLException se) {
			this.logger.error("用户" + uid + " 的会话列表获取失败：" + se.getMessage());
			sessionList = null;
		}
		closeAll(conn, pst, rs);
		return sessionList;
	}


	
}
