package cn.qixqi.pan.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseDao {
	private Logger logger = LogManager.getLogger(BaseDao.class.getName());
	
	/**
	 * 获取连接池连接
	 * @return
	 */
	public Connection getConnection() {
		try {
			Context cxt = new InitialContext();
			DataSource ds = (DataSource) cxt.lookup("java:comp/env/jdbc/pan");
			Connection conn = ds.getConnection();
			return conn;
		} catch(NamingException ne){
			this.logger.error(ne.getMessage());
			return null;
		} catch(SQLException se) {
			this.logger.error(se.getMessage());
			return null;
		}
	}
	
	
	/**
	 * 释放连接
	 * @param conn
	 * @param stmt
	 * @param rs
	 */
	public void closeAll(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch(SQLException se){
			this.logger.error(se.getMessage());
		}
	}
	
	/**
	 * 更新数据库
	 * @param table 表名
	 * @param whereSql 查询条件
	 * @param map 键值对
	 * @return
	 */
	public int executeUpdate(String table, String whereSql, Map<String, Object> map) {
		int status = 100;		// 成功标志
		if (map == null || map.size() == 0) {
			status = 200;		// 失败标志
			return status;
		}
		Connection conn = getConnection();
		PreparedStatement pst = null;
		try {
			StringBuilder sb = new StringBuilder();
			int size = map.size();
			int index = 0;
			sb.append("update ");
			sb.append(table);
			sb.append(" set ");
			for (String key : map.keySet()) {
				if (index == size - 1) {
					sb.append(key);
					sb.append(" = ? ");
				} else {
					sb.append(key);
					sb.append(" = ?, ");
				}
				index ++;
			}
			sb.append(whereSql);
			pst = conn.prepareStatement(sb.toString());
			index = 1;
			for (Object value : map.values()) {
				pst.setObject(index, value);
				index ++;
			}
			int rowCount = pst.executeUpdate();
			if (rowCount == 0) {
				this.logger.info("表" + table + "更新条数为0");		// 这里不设置status = 200，因为有时更新条数就是为0
			}
		} catch(SQLException se) {
			status = 200;	
			this.logger.error("表" + table + "更新失败：" + se.getMessage());
		}
		closeAll(conn, pst, null);
		return status;
	}
	
}
