package com.free4lab.monitorproxy.hbasetemp;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "beanCpu")
public class BeanCpu {

	private  Integer id;//云主机在mysql表里的id
	private  Date createdTime;//测试时间，long型
	private  float totalTime;//cpu测试的总时间
	
	public BeanCpu(){
		
	}
	
    public BeanCpu(Integer id){
    	this.id = id;
	}
	
    public BeanCpu(Integer id, Date createdTime, float totalTime){
    	this.id = id;
    	this.createdTime = createdTime;
    	this.totalTime = totalTime;
	}
    
    public String toString(){
    	return "id:" + id +"; createdTime：" + createdTime.getTime() + "; totalTime：" + totalTime;
    }
	
	@XmlElement(name = "id")
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@XmlElement
	public Date getCreatedTime() {
		return createdTime;
	}
	
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
	@XmlElement
	public float getTotalTime() {
		return totalTime;
	}
	
	public void setTotalTime(float totalTime) {
		this.totalTime = totalTime;
	}
 
	
}
