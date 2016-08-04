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
	 * ���ݿ�����
	 * 
	 * @return
	 */
	public Connection getConntion() {
		try {
			// 1: ��������������Java����ԭ��
			Class.forName(DbConfig.CLASS_NAME);
			// 2:����Connection�ӿڶ������ڻ�ȡMySQL���ݿ�����Ӷ�������������url�����ַ��� �˺� ����
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
		System.out.println("�������ݿ�ɹ�");
		return conn;
	}

	/**
	 * �ر����ݿ�ķ���
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
