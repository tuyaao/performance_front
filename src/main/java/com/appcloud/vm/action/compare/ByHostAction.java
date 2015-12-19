package com.appcloud.vm.action.compare;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

public class ByHostAction extends ActionSupport{
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(ByHostAction.class);
//	private VMInstanceDao vMInstanceDao = new VMInstanceDao();
//	private List<VMInstance> list = vMInstanceDao.findAll();

	public String execute() throws Exception{	
		logger.error("按主机");
		return SUCCESS;
	}
	
	

}
