package org.sqlserver.util;

import java.sql.*;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;

public class SqlUtil {
	private static Connection conn = null;
	private static Statement st = null;
	private static ResultSet rs = null;

	/**
	 * ��ѯ���������
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public ResultSet getResult(String sql) throws SQLException {
		try {
			DbConn dbconn = new DbConn();
			conn = dbconn.getConntion();
			st = conn.createStatement();
			rs = st.executeQuery(sql);

		} catch (SQLException e) {
			conn.close();
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * ͨ���û���Ų�ѯ��ˮ��Ϣ
	 * 
	 * @param UserNum
	 * @return
	 * @throws SQLException
	 */
	public ResultSet getResult_Pro(Integer UserNum) throws SQLException {
		try {
			DbConn dbconn = new DbConn();
			conn = dbconn.getConntion();
			CallableStatement stmt = null;
			stmt = conn.prepareCall("{call up_QryUserBuy(?,?)}");
			stmt.setInt(1, UserNum);
			stmt.registerOutParameter(2, Types.VARCHAR);
			// ���ݿ��ѯ
			rs = stmt.executeQuery();

		} catch (SQLException e) {
			conn.close();
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * �������ݿ⺯��
	 * 
	 * @param sql
	 * @return 0�ɹ� ����ʧ��
	 * @throws SQLException
	 */

	public int updata(String sql) throws SQLException {
		int num = 0;
		DbConn dbconn = new DbConn();
		conn = dbconn.getConntion();
		try {
			st = conn.createStatement();
			num = st.executeUpdate(sql);
		} catch (SQLException e) {
			conn.close();
			e.printStackTrace();
		}
		return num;
	}

}