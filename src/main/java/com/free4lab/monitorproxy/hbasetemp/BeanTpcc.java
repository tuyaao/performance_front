package com.free4lab.monitorproxy.hbasetemp;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "beanTpcc")
public class BeanTpcc {

	private Integer id;// 云主机在mysql表里的id
	private Date createdTime;// 测试时间，long型
	private float tpmc;// 每秒钟处理事务数

	public BeanTpcc() {

	}

	public BeanTpcc(Integer id) {
		this.id = id;
	}

	public BeanTpcc(Integer id, Date createdTime, float tpmc) {
		this.id = id;
		this.createdTime = createdTime;
		this.tpmc = tpmc;
	}

	public String toString(){
    	return "id:" + id +"; createdTime：" + createdTime.getTime() + "; tpmc：" + tpmc;
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
	public float getTpmc() {
		return tpmc;
	}

	public void setTpmc(float tpmc) {
		this.tpmc = tpmc;
	}

}
