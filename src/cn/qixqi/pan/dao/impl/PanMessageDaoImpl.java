package cn.qixqi.pan.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
		Connection conn = getConnection();
		PreparedStatement pst = null;
		int status = 100;
		String sql = "insert into pan_message (uid, uid1, receiver_id, session_type, msg, msg_type, msg_status) values (?, ?, ?, ?, ?, ?, ?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, panMessage.getUid());
			pst.setInt(2, panMessage.getUid1());
			pst.setInt(3, panMessage.getReceiverId());
			pst.setString(4, String.valueOf(panMessage.getSessionType()));
			pst.setString(5, panMessage.getMsg());
			pst.setString(6, String.valueOf(panMessage.getMsgType()));
			pst.setString(7, String.valueOf(panMessage.getMsgStatus()));
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				// 插入失败
				this.logger.error("pan_message 表插入失败：" + panMessage.toString());
				status = 200;
			}
		} catch (SQLException se) {
			this.logger.error("pan_message 表插入异常：" + se.getMessage());
			status = 200;
		}
		closeAll(conn, pst, null);
		return status;
	}

	@Override
	public int delete(int msgId) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		int status = 100;
		String sql = "delete from pan_message where msg_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, msgId);
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				// 删除失败
				this.logger.error("pan_message 表删除失败：msgId=" + msgId);
				status = 200;
			}
		} catch(SQLException se) {
			this.logger.error("pan_message 表删除异常：" + se.getMessage());
			status = 200;
		}
		closeAll(conn, pst, null);
		return status;
	}

	/**
	 * 清空 uid1 和 uid2 之间的消息
	 */
	@Override
	public int delete(int uid1, int uid2) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		int status = 100;
		String sql = "delete from pan_message where (uid1 = ? and receiver_id = ?) or (uid1 = ? and receiver_id = ?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, uid1);
			pst.setInt(2, uid2);
			pst.setInt(3, uid2);
			pst.setInt(4, uid1);
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				// 删除失败
				this.logger.error("pan_message 表删除失败：uid1=" + uid1 + ", uid2=" + uid2);
				status = 200;
			}
		} catch(SQLException se) {
			this.logger.error("pan_message 表删除异常：" + se.getMessage());
			status = 200;
		}
		closeAll(conn, pst, null);
		return status;
	}

	@Override
	public List<PanMessage> gets(int uid1, int uid2) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<PanMessage> messageList = new ArrayList<PanMessage>();
		String sql = "select m.msg_id, m.uid, m.uid1, u.username, u.avatar, m.receiver_id, m.session_type, m.msg, m.msg_type, m.msg_time, m.msg_status " + 
				"from pan_message as m join pan_user as u " + 
				"on m.uid1 = u.uid " + 
				"where (m.uid1 = ? and m.receiver_id = ?) or (m.uid1 = ? and m.receiver_id = ?);";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, uid1);
			pst.setInt(2, uid2);
			pst.setInt(3, uid2);
			pst.setInt(4, uid1);
			rs = pst.executeQuery();
			while (rs.next()) {
				int msgId = rs.getInt(1);
				int uid = rs.getInt(2);
				int Uid1 = rs.getInt(3);
				String username1 = rs.getString(4);
				String avatar1 = rs.getString(5);
				int receiverId = rs.getInt(6);
				char sessionType = rs.getString(7).charAt(0);
				String msg = rs.getString(8);
				char msgType = rs.getString(9).charAt(0);
				Date msgTime = rs.getTimestamp(10);
				char msgStatus = rs.getString(11).charAt(0);
				messageList.add(new PanMessage(msgId, uid, Uid1, username1, avatar1, receiverId, sessionType, msgType, msg, msgTime, msgStatus));
			}
		} catch(SQLException se) {
			// 查询异常
			this.logger.error("pan_message 获取消息列表异常：" + se.getMessage());
			messageList = null;
		}
		closeAll(conn, pst, rs);		
		return messageList;
	}

	@Override
	public PanMessage get(int msgId) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		PanMessage message = null;
		String sql = "select m.uid, m.uid1, u.username, u.avatar, m.receiver_id, m.session_type, m.msg, m.msg_type, m.msg_time, m.msg_status " + 
				"from pan_message as m join pan_user as u " + 
				"on m.uid1 = u.uid " + 
				"where m.msg_id = ?;";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, msgId);
			rs = pst.executeQuery();
			if (rs.next()) {
				int uid = rs.getInt(1);
				int uid1 = rs.getInt(2);
				String username1 = rs.getString(3);
				String avatar1 = rs.getString(4);
				int receiverId = rs.getInt(5);
				char sessionType = rs.getString(6).charAt(0);
				String msg = rs.getString(7);
				char msgType = rs.getString(8).charAt(0);
				Date msgTime = rs.getTimestamp(9);
				char msgStatus = rs.getString(10).charAt(0);
				message = new PanMessage(msgId, uid, uid1, username1, avatar1, receiverId, sessionType, msgType, msg, msgTime, msgStatus);
			} else {
				// 查询失败
				this.logger.error("pan_message 表查询失败：msgId=" + msgId);
				message = null;
			}
		} catch(SQLException se) {
			// 查询异常
			this.logger.error("pan_message 表获取消息异常：" + se.getMessage());
			message = null;
		}
		closeAll(conn, pst, rs);
		return message;
	}


	

}
