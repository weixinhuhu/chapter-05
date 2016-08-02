package org.sqlserver.util;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbConfig {
	private static Properties prop = new Properties();
	private static Logger log = LoggerFactory.getLogger(DbConfig.class);
	static {
		try {
			// 加载dbconfig.properties配置文件
			prop
					.load(DbConfig.class
							.getResourceAsStream("/dbConfig.properties"));
		} catch (IOException e) {
			log.error("#ERROR# :系统加载sysconfig.properties配置文件异常，请检查！", e);
			e.printStackTrace();
		}
	}

	// 设置常量
	public static final String CLASS_NAME = prop.getProperty("CLASS_NAME");
	public static final String DATABASE_URL = prop.getProperty("DATABASE_URL");
	public static final String SERVER_IP = prop.getProperty("SERVER_IP");
	public static final String SERVER_PORT = prop.getProperty("SERVER_PORT");
	public static final String DATABASE_SID = prop.getProperty("DATABASE_SID");
	public static final String USERNAME = prop.getProperty("USERNAME");
	public static final String PASSWORD = prop.getProperty("PASSWORD");

}
