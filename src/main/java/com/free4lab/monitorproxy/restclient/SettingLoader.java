package com.free4lab.monitorproxy.restclient;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;


public class SettingLoader {
	
	private static Logger logger = Logger.getLogger(SettingLoader.class);
	private final static String SERVER_INFO = "server-info.properties";
	
	public static String SERVER_URL;
	//静态方法在初次加载类的时候执行
	static {
		Properties p = new Properties();
		InputStream is = SettingLoader.class.getClassLoader().getResourceAsStream(SERVER_INFO);

		try {
			p.load(is);
			
			SERVER_URL = p.getProperty("SERVER_URL");
			
		} catch (Exception e) {
			SERVER_URL = "http://localhost:2222/monitor-dbproxy/performancedbproxy/";
			logger.warn("Invalid" + SERVER_INFO + "found", e);
		}
		
	}

}
