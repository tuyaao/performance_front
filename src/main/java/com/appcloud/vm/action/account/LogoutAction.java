package com.appcloud.vm.action.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONException;





import com.appcloud.vm.action.base.BaseAction;
import com.appcloud.vm.common.Constants;
import com.appcloud.vm.common.SessionConstants;
import com.opensymphony.xwork2.ActionContext;


/**
 * @author Gong Lingpu
 * @2015年1月17日
 */
public class LogoutAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(LogoutAction.class);
	
	private String result;
	private String message;
	
	public String execute(){
		String accessToken = getSessionAccessToken();
		logger.info(accessToken);
		if( accessToken != null && ! "".equalsIgnoreCase(accessToken) && accessToken.trim().length() == 32){
			try {
				clearSession();
				result = "success";
				message = "logout success";
			} catch (Exception e) {
				// TODO: handle exception
				result = "fail";
				message = "logout fail, clearSession throws Exception";
				e.printStackTrace();
			}
			
		}else{
			result = "error";
			message = "accessTokenInSession is null or '' or length less than 32";
		}
		return SUCCESS;
	}

	private void clearSession(){	
		Map<String, Object> session = ActionContext.getContext().getSession();
		logger.info(session);
		session.remove(Constants.AccessToken);
		session.remove(Constants.UserID);
		logger.info(session);
	}
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

   

}
