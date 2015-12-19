package com.appcloud.vm.action.base;

import com.appcloud.vm.common.Constants;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {
private static final long serialVersionUID = 1L;
	
	/*
	 * 取得session中存储的登录用户信息
	 */
	public String getSessionUserEmail(){
		return (String)ActionContext.getContext().getSession().get(Constants.UserEmail);
	}
	 
	public String getSessionAccessToken(){
		return (String)ActionContext.getContext().getSession().get(Constants.AccessToken);
	}
	    
	public Integer getSessionUserID(){
		return (Integer)ActionContext.getContext().getSession().get(Constants.UserID);
	}
}
