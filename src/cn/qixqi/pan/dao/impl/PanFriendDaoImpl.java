package cn.qixqi.pan.dao.impl;

import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.qixqi.pan.dao.BaseDao;
import cn.qixqi.pan.dao.PanFriendDao;
import cn.qixqi.pan.entity.PanFriend;

public class PanFriendDaoImpl extends BaseDao implements PanFriendDao {
	private Logger logger = LogManager.getLogger(PanFriendDaoImpl.class.getName());

	@Override
	public int add(int uid1, int uid2) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		int status = 100;
		String sql = "insert into pan_friend(uid1, uid2) values (?, ?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, uid1);
			pst.setInt(2, uid2);
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				status = 200;
				this.logger.error("uid1=" + uid1 + ", uid2=" + uid2 + " 添加好友失败");
			} else {
				this.logger.info("uid1=" + uid1 + ", uid2=" + uid2 + " 添加好友成功");
			}
		} catch(SQLException se) {
			status = 200;
			this.logger.error("uid1=" + uid1 + ", uid2=" + uid2 + " 添加好友失败：" + se.getMessage());
		}
		closeAll(conn, pst, null);
		return status;
	}

	@Override
	public int delete(int uid1, int uid2) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		int status = 100;
		String sql = "delete from pan_friend where uid1 = ? and uid2 = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, uid1);
			pst.setInt(2, uid2);
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				status = 200;
				this.logger.error("uid1=" + uid1 + ", uid2=" + uid2 + " 删除好友失败");
			} else {
				this.logger.info("uid1=" + uid1 + ", uid2=" + uid2 + " 删除好友成功");
			}
		} catch(SQLException se) {
			status = 200;
			this.logger.error("uid1=" + uid1 + ", uid2=" + uid2 + " 删除好友失败：" + se.getMessage());
		}
		closeAll(conn, pst, null);
		return status;
	}

	@Override
	public boolean isFriend(int uid1, int uid2) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		boolean flag = true;
		String sql = "select * from pan_friend where uid1 = ? and uid2 = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, uid1);
			pst.setInt(2, uid2);
			rs = pst.executeQuery();
			if (!rs.next()) {
				flag = false;
			}
		} catch(SQLException se) {
			this.logger.error("uid1=" + uid1 + ", uid2=" + uid2 + " 判断是否好友出现异常：" + se.getMessage());
			flag = false;
		}
		closeAll(conn, pst, rs);
		return flag;
	}

	@Override
	public List<PanFriend> gets(int uid) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<PanFriend> friendList = new ArrayList<PanFriend>();
		String sql = "select u.uid, u.username, u.sex, u.phone_num, u.avatar, u.birthday, u.register_time, u.last_login_time, f.relation_time " + 
				"from pan_user as u join pan_friend as f " + 
				"on u.uid = f.uid2 " + 
				"where f.uid1 = ? " + 
				"union all " + 
				"select u2.uid, u2.username, u2.sex, u2.phone_num, u2.avatar, u2.birthday, u2.register_time, u2.last_login_time, f2.relation_time " + 
				"from pan_user as u2 join pan_friend as f2 " + 
				"on u2.uid = f2.uid1 " + 
				"where f2.uid2 = ?;";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, uid);
			pst.setInt(2, uid);
			rs = pst.executeQuery();
			while(rs.next()) {
				int Uid = rs.getInt(1);
				String username = rs.getString(2);
				char sex = rs.getString(3).charAt(0);
				String phoneNum = rs.getString(4);
				String avatar = rs.getString(5);
				Date birthday = rs.getDate(6);
				Date registerTime = rs.getTimestamp(7);
				Date lastLoginTime = rs.getTimestamp(8);
				Date relationTime = rs.getTimestamp(9);
				friendList.add(new PanFriend(Uid, username, sex, phoneNum, avatar, birthday, registerTime, lastLoginTime, relationTime));
			}
		} catch(SQLException se) {
			this.logger.error("用户" + uid + " 的好友列表获取失败：" + se.getMessage());
			friendList = null;
		}
		closeAll(conn, pst, rs);
		return friendList;
	}


}
