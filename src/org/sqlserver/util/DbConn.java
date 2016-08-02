package org.sqlserver.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbConn {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	/**
	 * 数据库连接
	 * 
	 * @return
	 */
	public Connection getConntion() {
		try {
			// 1: 加载连接驱动，Java反射原理
			Class.forName(DbConfig.CLASS_NAME);
			// 2:创建Connection接口对象，用于获取MySQL数据库的连接对象。三个参数：url连接字符串 账号 密码
			String url = DbConfig.DATABASE_URL + "://" + DbConfig.SERVER_IP
					+ ":" + DbConfig.SERVER_PORT + ";DatabaseName="
					+ DbConfig.DATABASE_SID;
			conn = DriverManager.getConnection(url, DbConfig.USERNAME,
					DbConfig.PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("连接数据库成功");
		return conn;
	}

	/**
	 * 关闭数据库的方法
	 */
	public void closeConn() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	

}
