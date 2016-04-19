package com.appcloud.vm.action.entity;

import java.sql.Timestamp;


public abstract class SumRankingAbstractEntity<T> implements Comparable<T>{
	
	protected Integer id;
	protected String cloudPlatformName;
	protected String subClassName = "";//这个是用于首页，本来是双层list,内层是不同公司的单个子类，外层是多个子类的集合。
	                                   //但是在填充数据时，虽然可以反射生成双层，但是没有办法在生成之后通过instancesof获得子类的类型来填充数据。
	                                   //所以只好添加subClassName
	protected Timestamp testTime; //测试时间
	
	public SumRankingAbstractEntity(){
	}
	
	public SumRankingAbstractEntity(Integer id, String cloudPlatformName){
		this.id = id;
		this.cloudPlatformName = cloudPlatformName;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCloudPlatformName() {
		return cloudPlatformName;
	}
	public void setCloudPlatformName(String cloudPlatformName) {
		this.cloudPlatformName = cloudPlatformName;
	}
	public Timestamp getTestTime() {
		return testTime;
	}
	public void setTestTime(Timestamp testTime) {
		this.testTime = testTime;
	}
	public String getSubClassName() {
		return subClassName;
	}
	public void setSubClassName(String subClassName) {
		this.subClassName = subClassName;
	}
	
}
