package cn.qixqi.pan.dao.impl;

import java.util.Map;
import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.qixqi.pan.dao.BaseDao;
import cn.qixqi.pan.dao.PanUserDao;
import cn.qixqi.pan.entity.PanUser;

public class PanUserDaoImpl extends BaseDao implements PanUserDao {
	private Logger logger = LogManager.getLogger(PanUserDaoImpl.class.getName());

	@Override
	public int add(PanUser panUser) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		int status = 100;
		String sql = "insert into pan_user(username, password, email, phone_num) values (?, ?, ?, ?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, panUser.getUserName());
			pst.setString(2, panUser.getPassword());
			pst.setString(3, panUser.getEmail());
			pst.setString(4, panUser.getPhoneNum());
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				this.logger.error("用户注册失败：username=" + panUser.getUserName());
				status = 200;
			}
		} catch(SQLException se) {
			this.logger.error("用户注册出现异常：" + se.getMessage());
			status = 200;
		}
		closeAll(conn, pst, null);
		return status;
	}

	@Override
	public int delete(int uid) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		int status = 100;
		String sql = "delete from pan_user where uid = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, uid);
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				this.logger.error("用户注销账号失败：uid=" + uid);
				status = 200;
			}
		} catch(SQLException se) {
			this.logger.error("用户注销账号出现异常：" + se.getMessage());
			status = 200;
		}
		closeAll(conn, pst, null);
		return status;
	}

	@Override
	public int update(int uid, Map<String, Object> map) {
		// TODO Auto-generated method stub
		String table = "pan_user";
		String whereSql = " where uid = " + uid;
		return executeUpdate(table, whereSql, map);
	}

	@Override
	public int resetPass(int uid, String oldPass, String newPass) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		int status = 100;
		String sql = "update pan_user set password = ? where uid = ? and password = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, newPass);
			pst.setInt(2, uid);
			pst.setString(3, oldPass);
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				status = 201;
				this.logger.error("用户" + uid + " 更新密码失败：原密码错误");
			}
		} catch(SQLException se) {
			this.logger.error("用户" + uid + " 更新密码失败：" + se.getMessage());
			status = 200;
		}
		closeAll(conn, pst, null);
		return status;
	}

	@Override
	public PanUser get(String key, String password) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		PanUser panUser = null;
		String sql = "select uid, username, sex, email, phone_num, avatar, birthday, register_time, last_login_time from pan_user where (username = ? or email = ? or phone_num = ?) and password = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, key);
			pst.setString(2, key);
			pst.setString(3, key);
			pst.setString(4, password);
			rs = pst.executeQuery();
			if (rs.next()) {
				int uid = rs.getInt(1);
				String username = rs.getString(2);
				char sex = rs.getString(3).charAt(0);
				String email = rs.getString(4);
				String phoneNum = rs.getString(5);
				String avatar = rs.getString(6);
				Date birthday = rs.getDate(7);
				Date registerTime = rs.getTimestamp(8);
				Date lastLoginTime = rs.getTimestamp(9);
				panUser = new PanUser(uid, username, password, sex, email, phoneNum, avatar, birthday, registerTime, lastLoginTime);
				this.logger.info("key:" + key + " 登录成功");
			} else {
				this.logger.info("key:" + key + " 登录失败");
			}
		} catch(SQLException se) {
			this.logger.error("key:" + key + " 登录失败：" + se.getMessage());
			panUser = null;
		}
		closeAll(conn, pst, rs);
		return panUser;
	}

	@Override
	public PanUser get(int uid) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		PanUser panUser = null;
		String sql = "select username, password, sex, email, phone_num, avatar, birthday, register_time, last_login_time from pan_user where uid = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, uid);
			rs = pst.executeQuery();
			if (rs.next()) {
				String username = rs.getString(1);
				String password = rs.getString(2);
				char sex = rs.getString(3).charAt(0);
				String email = rs.getString(4);
				String phoneNum = rs.getString(5);
				String avatar = rs.getString(6);
				Date birthday = rs.getDate(7);
				Date registerTime = rs.getTimestamp(8);
				Date lastLoginTime = rs.getTimestamp(9);
				panUser = new PanUser(uid, username, password, sex, email, phoneNum, avatar, birthday, registerTime, lastLoginTime);
				this.logger.info("用户" + uid + " 的信息获取成功");
			} else {
				this.logger.info("用户" + uid + " 的信息获取失败");
			}
		} catch(SQLException se) {
			this.logger.error("用户" + uid + " 的信息获取失败：" + se.getMessage());
			panUser = null;
		}
		closeAll(conn, pst, rs);
		return panUser;
	}

	@Override
	public String getAvatar(int uid) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		String avatar = null;
		String sql = "select avatar from pan_user where uid = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, uid);
			rs = pst.executeQuery();
			if (rs.next()) {
				avatar = rs.getString(1);
				this.logger.info("用户" + uid + " 的头像链接获取成功");
			} else {
				this.logger.info("用户" + uid + " 的头像链接获取失败");
			}
		} catch(SQLException se) {
			this.logger.error("用户" + uid + " 的头像链接获取失败：" + se.getMessage());
			avatar = null;
		}
		closeAll(conn, pst, rs);
		return avatar;
	}

	@Override
	public PanUser getSimpleUser(int uid) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		PanUser panUser = null;
		String sql = "select username, sex, avatar, register_time from pan_user where uid = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, uid);
			rs = pst.executeQuery();
			if (rs.next()) {
				String username = rs.getString(1);
				char sex = rs.getString(2).charAt(0);
				String avatar = rs.getString(3);
				Date registerTime = rs.getTimestamp(4);
				panUser = new PanUser(uid, username, sex, avatar, registerTime);
				this.logger.info("用户" + uid + " 的简要信息获取成功");
			} else {
				this.logger.info("用户" + uid + " 的简要信息获取失败");
			}
		} catch(SQLException se) {
			this.logger.error("用户" + uid + " 的简要信息获取失败：" + se.getMessage());
			panUser = null;
		}
		closeAll(conn, pst, rs);
		return panUser;
	}


}
