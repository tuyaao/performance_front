package com.appcloud.vm.common;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.free4lab.utils.account.ConfigurationUtil;




public class Constants {
	//session
	public static final String UserID = "uid";
	public static final String UserEmail= "email";
	public static final String UserName = "username";
	public static final String UserAvatar = "useravatar";
	public static final String AccessToken = "accessToken";
	public static final String AccToken = "accToken";
	
	//app.properties
	public static final String SECRET_KEY;
	public static final String CUSTOM_ID;
	
	public static final String CLIENT_SECRET_KEY;
	public static final String CLIENT_ID;
	
	public static final String VNC_HOST;
	
	public static final String VNC_PORT;
		
	public static final String FRONT_URL;
	
	public static final String URL_ACCOUNT;
	
	public static final String WEBVNC_URL;
	
	public static final Integer DEFAULT_GROUP;
	
	public static final Integer DEFAULT_CHECKTIME;
	
	public static final Integer DEFAULT_HD_CHECKTIME;
	
	public static final Integer DEFAULT_CHECKTIMESLOT;
	
	public static final Integer LOG_COUNT;
	
	public static final String DOMAIN;
	
	public static final String CURVEINSTANCEMAPTIME = "CurveInstanceMapTime";
	
	public static final String CURVEINSTANCEMAPVALUE = "CurveInstanceMapValue";
	
	static {
		final Logger logger = Logger.getLogger("App configuration");
		logger.info("+++++++++++App configuration information++++++++++++");
		try {
			Properties p = new ConfigurationUtil().getPropertyFileConfiguration("app.properties");

			SECRET_KEY = p.getProperty("SECRET_KEY");
			logger.info("SECRET_KEY:" + SECRET_KEY);
			
            CUSTOM_ID = p.getProperty("CUSTOM_ID");
			logger.info("CUSTOM_ID:" + CUSTOM_ID);
			
			VNC_HOST = p.getProperty("VNC_HOST");
			logger.info("VNC_HOST:" + VNC_HOST);
			
			VNC_PORT = p.getProperty("VNC_PORT");
			logger.info("VNC_PORT:" + VNC_PORT);
			
			CLIENT_SECRET_KEY = p.getProperty("CLIENT_SECRET_KEY");
			logger.info("CLIENT_SECRET_KEY:" + CLIENT_SECRET_KEY);
			
            CLIENT_ID = p.getProperty("CLIENT_ID");
			logger.info("CLIENT_ID:" + CLIENT_ID);
			
			FRONT_URL = p.getProperty("FRONT_URL");
			logger.info("FRONT_URL:" + FRONT_URL);
			
			URL_ACCOUNT = p.getProperty("URL_ACCOUNT");
			logger.info("URL_ACCOUNT:" + URL_ACCOUNT);
			
			WEBVNC_URL = p.getProperty("WEBVNC_URL");
			logger.info("WEBVNC_URL:" + WEBVNC_URL);
			
			DEFAULT_GROUP = Integer.valueOf( p.getProperty("DEFAULT_GROUP"));
			logger.info("DEFAULT_GROUP:" + DEFAULT_GROUP);
			
			DEFAULT_CHECKTIME = Integer.valueOf( p.getProperty("DEFAULT_CHECKTIME"));
			logger.info("DEFAULT_CHECKTIME:" + DEFAULT_CHECKTIME);
			
			DEFAULT_HD_CHECKTIME = Integer.valueOf( p.getProperty("DEFAULT_HD_CHECKTIME"));
			logger.info("DEFAULT_HD_CHECKTIME:" + DEFAULT_HD_CHECKTIME);
			
			DEFAULT_CHECKTIMESLOT = Integer.valueOf( p.getProperty("DEFAULT_CHECKTIMESLOT"));
			logger.info("DEFAULT_CHECKTIMESLOT:" + DEFAULT_CHECKTIMESLOT);
			
			LOG_COUNT = Integer.valueOf(p.getProperty("LOG_COUNT"));
			logger.info("LOG_COUNT:" + LOG_COUNT);
			
			DOMAIN = p.getProperty("DOMAIN");
			logger.info("DOMAIN:" + DOMAIN);
        } catch (IOException e) {
        	logger.warn("Failed to init app configuration" + e.getMessage());
        	throw new RuntimeException("Failed to init app configuration", e);
        }
		logger.info("----------App configuration successfully----------");
    }
}
