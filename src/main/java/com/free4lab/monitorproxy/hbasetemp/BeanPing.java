package com.free4lab.monitorproxy.hbasetemp;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "beanPing")
public class BeanPing {

	private Integer id;// 云主机在mysql表里的id
	private Date createdTime;// 测试时间，long型
	private String destIp;// ping的目标ip/域名
	private float loss;// ping的丢包率，百分之
	private float avg;// ping的平均时延rtt，单位：ms

	public BeanPing() {

	}

	public BeanPing(Integer id) {
		this.id = id;
	}

	public BeanPing(Integer id, Date createdTime, String destIp, float loss,
			float avg) {
		this.id = id;
		this.createdTime = createdTime;
		this.destIp = destIp;
		this.loss = loss;
		this.avg = avg;
	}
	
	public String toString(){
    	return "id:" + id +"; createdTime：" + createdTime.getTime() + "; destIp：" + destIp + "; loss：" + loss + "; avg：" + avg;
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
	public String getDestIp() {
		return destIp;
	}

	public void setDestIp(String destIp) {
		this.destIp = destIp;
	}

	@XmlElement
	public float getLoss() {
		return loss;
	}

	public void setLoss(float loss) {
		this.loss = loss;
	}

	@XmlElement
	public float getAvg() {
		return avg;
	}

	public void setAvg(float avg) {
		this.avg = avg;
	}

}
