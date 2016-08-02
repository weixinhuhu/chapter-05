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
				msg.append("\n" + "账户余额：" + rs.getString("Income") + "元\n用水户号："
						+ rs.getString("CustomerId") + "\n用水户名："
						+ rs.getString("User_Name") + " \n用户地址："
						+ rs.getString("Address") + "\n更新日期："
						+ rs.getString("Fdate")
						+ "\n账户余额=充值金额-表端已用量*用水单价(用水量以每月账单为准)" + "\n\n\n立即缴费");
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
				log.info("请在CX后添加需要查询的用户号！");
			} else {
				String usernum = ""; //
				usernum = resmessage.substring(2, resmessage.length());

				usernum = resmessage.replaceAll("[^0-9]", "");

				if (usernum == null) {
					log.info("请输入合法的用水户号！");

				} else {
					log.info(usernum);

					String SendMsg = "";
					SendMsg = getResult(usernum.trim());

					if (SendMsg.length() == 0 || SendMsg == null) {
						log.info("对不起，没用查到此用户信息");
					} else {
						log.info(SendMsg);
					}
				}
			}
		} else {
			log.info("对不起 我不知道你在说什么");
		}

	}
	
}
