package org.sqlserver.util;

import java.sql.*;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;

public class SqlUtil {
	private static Connection conn = null;
	private static Statement st = null;
	private static ResultSet rs = null;

	/**
	 * 查询结果集函数
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
	 * 通过用户编号查询用水信息
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
			// 数据库查询
			rs = stmt.executeQuery();

		} catch (SQLException e) {
			conn.close();
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * 操作数据库函数
	 * 
	 * @param sql
	 * @return 0成功 其他失败
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