package com.appcloud.vm.action.sum;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import com.appcloud.vm.action.entity.CloudPlatformEntity;
import com.appcloud.vm.common.InitializeListener;
import com.opensymphony.xwork2.ActionSupport;

public class SumGetCompanyAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(SumGetCompanyAction.class);
	private List<CloudPlatformEntity> cloudPlatformEntityList = InitializeListener.getCloudPlatformEntityList();// 云平台的id,名字,含有几台虚拟机的实体类集合
	
	public String execute() {	
		System.out.println("进入查询所有公司相关的数据的ACTION");	
		
		Collections.sort(cloudPlatformEntityList, new SortByName());
		
		return SUCCESS;
	}

	private class SortByName implements Comparator {
		 public int compare(Object o1, Object o2) {
			 CloudPlatformEntity s1 = (CloudPlatformEntity) o1;
			 CloudPlatformEntity s2 = (CloudPlatformEntity) o2;
			 //取得比较对象的汉字编码，并将其转换成字符串  
			 String st1 = "";
			 String st2 = "";
			try {
				st1 = new String(s1.getName().getBytes("GB2312"), "ISO-8859-1");
				st2 = new String(s2.getName().getBytes("GB2312"), "ISO-8859-1");  
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}  
			 // 运用String类的 compareTo()方法对两对象进行比较  
			 return st1.compareTo(st2);  
		 }
		}
	
	public List<CloudPlatformEntity> getCloudPlatformEntityList() {
		return cloudPlatformEntityList;
	}

	public void setCloudPlatformEntityList(
			List<CloudPlatformEntity> cloudPlatformEntityList) {
		this.cloudPlatformEntityList = cloudPlatformEntityList;
	}
	
}
