package com.free4lab.monitorproxy.hbasetemp;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "beanIozone")
public class BeanIozone {

	private Integer id;// 云主机在mysql表里的id
	private Date createdTime;// 测试时间，long型
	private Integer fileSize;// 测试文件大小，单位:GB
	private Integer recordSize;// 传输的record size，单位：KB
	private Integer write;// 写测试的性能，单位KB/s
	private Integer rewrite;// 测试的性能，单位KB/s
	private Integer read;// 读测试的性能，单位KB/s
	private Integer reread;// 重读测试的性能，单位KB/s
	private Integer randomRead;// 随机读测试的性能，单位KB/s
	private Integer randomWrite;// 重写测试的性能，单位KB/s

	public BeanIozone() {

	}

	public BeanIozone(Integer id) {
		this.id = id;
	}

	public BeanIozone(Integer id, Date createdTime, Integer fileSize,
			Integer recordSize, Integer write, Integer rewrite, Integer read,
			Integer reread, Integer randomRead, Integer randomWrite) {
		this.id = id;
		this.createdTime = createdTime;
		this.fileSize = fileSize;
		this.recordSize = recordSize;
		this.write = write;
		this.rewrite = rewrite;
		this.read = read;
		this.reread = reread;
		this.randomRead = randomRead;
		this.randomWrite = randomWrite;
	}
	
	public String toString(){
    	return "id:" + id +"; createdTime：" + createdTime.getTime() + "; fileSize：" + fileSize + 
    			"; recordSize：" + recordSize + "; write：" + write + "; rewrite：" + rewrite + 
    			"; read：" + read + "; reread：" + reread + "; randomRead：" + randomRead + 
    			"; randomRead：" + randomRead + "; randomWrite：" + randomWrite;
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
	public Integer getFileSize() {
		return fileSize;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	@XmlElement
	public Integer getRecordSize() {
		return recordSize;
	}

	public void setRecordSize(Integer recordSize) {
		this.recordSize = recordSize;
	}

	@XmlElement
	public Integer getWrite() {
		return write;
	}

	public void setWrite(Integer write) {
		this.write = write;
	}

	@XmlElement
	public Integer getRewrite() {
		return rewrite;
	}

	public void setRewrite(Integer rewrite) {
		this.rewrite = rewrite;
	}

	@XmlElement
	public Integer getRead() {
		return read;
	}

	public void setRead(Integer read) {
		this.read = read;
	}

	@XmlElement
	public Integer getReread() {
		return reread;
	}

	public void setReread(Integer reread) {
		this.reread = reread;
	}

	@XmlElement
	public Integer getRandomRead() {
		return randomRead;
	}

	public void setRandomRead(Integer randomRead) {
		this.randomRead = randomRead;
	}

	@XmlElement
	public Integer getRandomWrite() {
		return randomWrite;
	}

	public void setRandomWrite(Integer randomWrite) {
		this.randomWrite = randomWrite;
	}

}
