package cn.qixqi.pan.dao.impl;

import java.util.Map;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		Connection conn = getConnection();
		PreparedStatement pst = null;
		int status = 100;
		String sql = "insert into pan_file (file_name, file_type, file_size) values (?, ?, ?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, file.getFileName());
			pst.setString(2, file.getFileType());
			pst.setLong(3, file.getFileSize());
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				// 插入失败
				this.logger.error("pan_file 表插入失败，file: " + file.toString());
				status = 200;
			}
		} catch(SQLException se) {
			// 插入异常
			this.logger.error("pan_file 表插入异常：" + se.getMessage());
			status = 200;
		}
		closeAll(conn, pst, null);
		return status;
	}

	@Override
	public int delete(int fileId) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		int status = 100;
		String sql = "delete from pan_file where file_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, fileId);
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				// 删除失败
				this.logger.error("pan_file 表删除失败：fileId=" + fileId);
				status = 200;
			}
		} catch(SQLException se) {
			this.logger.error("pan_file 表删除异常：" + se.getMessage());
			status = 200;
		}
		closeAll(conn, pst, null);
		return status;
	}

	@Override
	public int update(int fileId, Map<String, Object> map) {
		// TODO Auto-generated method stub
		String table = "pan_file";
		String whereSql = " where file_id = " + fileId;
		return executeUpdate(table, whereSql, map);
	}

	@Override
	public int addLink(int fileId, int count) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		int status = 100;
		String sql = "update pan_file set link_num = ( " + 
				"	select link_num + ? " + 
				"	from pan_file " + 
				"	where file_id = ? " + 
				") where file_id = ?;";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, count);
			pst.setInt(2, fileId);
			pst.setInt(3, fileId);
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				// 添加链接数失败
				this.logger.error("pan_file 表link_num增加失败, fileId=" + fileId);
				status = 200;
			}
		} catch(SQLException se) {
			// 添加链接数异常
			this.logger.error("pan_file 表link_num增加异常：" + se.getMessage());
			status = 200;
		}
		closeAll(conn, pst, null);
		return status;
	}

	@Override
	public int deleteLink(int fileId, int count) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		int status = 100;
		String sql = "update pan_file set link_num = ( " + 
				"	select link_num - ? " + 
				"	from pan_file " + 
				"	where file_id = ? " + 
				") where file_id = ?;";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, count);
			pst.setInt(2, fileId);
			pst.setInt(3, fileId);
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				// 减少链接数失败
				this.logger.error("pan_file 表link_num减少失败, fileId=" + fileId);
				status = 200;
			}
		} catch(SQLException se) {
			// 减少链接数异常
			this.logger.error("pan_file 表link_num减少异常：" + se.getMessage());
			status = 200;
		}
		closeAll(conn, pst, null);
		return status;
	}

	@Override
	public int getLinkNum(int fileId) {
		// TODO Auto-generated method stub
		Connection conn = getConnection();
		PreparedStatement pst = null;
		ResultSet rs = null;
		int linkNum = -1;
		String sql = "select link_num from pan_file where file_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, fileId);
			rs = pst.executeQuery();
			if (rs.next()) {
				linkNum = rs.getInt(1);
			} else {
				// 查询链接数失败
				this.logger.error("pan_file 表查询link_num 失败，fileId=" + fileId);
				linkNum = -1;
			}
		} catch(SQLException se) {
			// 查询链接数异常
			this.logger.error("pan_file 表查询link_num 异常：" + se.getMessage());
			linkNum = -1;
		}
		closeAll(conn, pst, rs);
		return linkNum;
	}



}
