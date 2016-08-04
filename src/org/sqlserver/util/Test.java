package org.sqlserver.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.ws.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {
	private static Logger log = LoggerFactory.getLogger(Test.class);

	private static String getResult(String usernum) throws SQLException {

		SqlUtil sqlutil = new SqlUtil();
		ResultSet rs = null;
		rs = sqlutil.getResult_Pro(Integer.parseInt(usernum));
		StringBuffer msg = new StringBuffer();
		try {
			while (rs.next()) {
				msg.append("\n" + "�˻���" + rs.getString("Income") + "Ԫ\n��ˮ���ţ�"
						+ rs.getString("CustomerId") + "\n��ˮ������"
						+ rs.getString("User_Name") + " \n�û���ַ��"
						+ rs.getString("Address") + "\n�������ڣ�"
						+ rs.getString("Fdate")
						+ "\n�˻����=��ֵ���-���������*��ˮ����(��ˮ����ÿ���˵�Ϊ׼)" + "\n\n\n�����ɷ�");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg.toString();
	}

	public static void main(String[] args) throws SQLException {
		String resmessage = "CX111";
		if (resmessage.toUpperCase().trim().contains("CX")) {
			if (resmessage.toUpperCase().trim().length() <= 2) {
				log.info("����CX�������Ҫ��ѯ���û��ţ�");
			} else {
				String usernum = ""; //
				usernum = resmessage.substring(2, resmessage.length());

				usernum = resmessage.replaceAll("[^0-9]", "");

				if (usernum == null) {
					log.info("������Ϸ�����ˮ���ţ�");

				} else {
					log.info(usernum);

					String SendMsg = "";
					SendMsg = getResult(usernum.trim());

					if (SendMsg.length() == 0 || SendMsg == null) {
						log.info("�Բ���û�ò鵽���û���Ϣ");
					} else {
						log.info(SendMsg);
					}
				}
			}
		} else {
			log.info("�Բ��� �Ҳ�֪������˵ʲô");
		}

	}
	
}
