package com.appcloud.vm.action.sum;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.appcloud.vm.action.dbentity.CloudPlatform;
import com.appcloud.vm.common.InitializeListener;
import com.opensymphony.xwork2.ActionSupport;

public class ToCompareAction extends ActionSupport{

	
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(ToCompareAction.class);
	private List<CloudPlatform> cloudPlatformList = InitializeListener.getCloudPlatformList();//云平台集合
	private List<String> cloudPlatformNameList = new ArrayList<String>();//所有的云平台名字集合 ;
	private List<String> cloudPlatformIdList = new ArrayList<String>();//所有的云平台id集合 ;
	
	public String execute() throws Exception{	
		System.out.println("进入按厂商之前的Action");
		
		for(int i = 0; i < cloudPlatformList.size(); i++){
			cloudPlatformNameList.add(cloudPlatformList.get(i).getName());
			cloudPlatformIdList.add(cloudPlatformList.get(i).getId().toString());
		}
		
		return SUCCESS;
	}

	
	public List<String> getCloudPlatformNameList() {
		return cloudPlatformNameList;
	}


	public void setCloudPlatformNameList(List<String> cloudPlatformNameList) {
		this.cloudPlatformNameList = cloudPlatformNameList;
	}


	public List<String> getCloudPlatformIdList() {
		return cloudPlatformIdList;
	}


	public void setCloudPlatformIdList(List<String> cloudPlatformIdList) {
		this.cloudPlatformIdList = cloudPlatformIdList;
	}
	

}
