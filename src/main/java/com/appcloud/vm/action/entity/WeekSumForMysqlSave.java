package com.appcloud.vm.action.entity;

import java.sql.Timestamp;

public class WeekSumForMysqlSave {
	
//对应java中的java.sql.Timestamp类型（注意命名空间）。
//保存到数据库这样做：
//Timestamp.valueOf("时间");
//注意时间的格式为：yyyy-MM-dd hh:mm:ss
//从数据库取值用Timestamp保存即可。
	private int uuid = 0;
	private int cpu = 0;
	private int mem = 0;
	private int disk = 0;
	private int bandwidth = 0;
	private String ip = "未初始化";
	private String mac = "未初始化";
	private Timestamp createTime = Timestamp.valueOf("2000-01-01 00:00:00");
	private Timestamp updateTime = Timestamp.valueOf("2000-01-01 00:00:00");
	private String os = "未初始化";
	private int companyId = 0;
	private String companyName = "未初始化";
	
	private static final long serialVersionUID = 1L;
	
	public WeekSumForMysqlSave(){
		
	}

	public int getUuid() {
		return uuid;
	}

	public void setUuid(int uuid) {
		this.uuid = uuid;
	}

	public int getCpu() {
		return cpu;
	}

	public void setCpu(int cpu) {
		this.cpu = cpu;
	}

	public int getMem() {
		return mem;
	}

	public void setMem(int mem) {
		this.mem = mem;
	}

	public int getDisk() {
		return disk;
	}

	public void setDisk(int disk) {
		this.disk = disk;
	}

	public int getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(int bandwidth) {
		this.bandwidth = bandwidth;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String toString(){
		return  "uuid:" + uuid + " " +
				"cpu:" + cpu + " " +
				"mem:" + mem + " " +
				"disk:" + mem + " " +
				"bandwidth:" + bandwidth + " " +
				"ip:" + ip + " " +
				"mac:" + mac + " " +
				"createTime:" + createTime + " " +
				"updateTime:" + updateTime + " " +
				"os:" + os + " " +
				"companyId:" + companyId + " " +
				"companyName:" + companyName;
	}
	
}
