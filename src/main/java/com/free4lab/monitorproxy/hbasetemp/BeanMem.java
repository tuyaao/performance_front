package com.free4lab.monitorproxy.hbasetemp;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "beanMem")
public class BeanMem {

	private Integer id;// 云主机在mysql表里的id    
	private Date createdTime;// 测试时间，long型    
	private float transferSpeed;// 内存的传输速度    

	public BeanMem() {

	}

	public BeanMem(Integer id) {
		this.id = id;
	}

	public BeanMem(Integer id, Date createdTime, float transferSpeed) {
		this.id = id;
		this.createdTime = createdTime;
		this.transferSpeed = transferSpeed;
	}
	
	public String toString(){
    	return "id:" + id +"; createdTime：" + createdTime.getTime() + "; transferSpeed：" + transferSpeed;
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
	public float getTransferSpeed() {
		return transferSpeed;
	}

	public void setTransferSpeed(float transferSpeed) {
		this.transferSpeed = transferSpeed;
	}

}
