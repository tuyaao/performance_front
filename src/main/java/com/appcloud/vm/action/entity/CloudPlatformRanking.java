package com.appcloud.vm.action.entity;

import java.sql.Timestamp;

public class CloudPlatformRanking {
	
	private Integer id;
	private String cloudPlatformName;
	private float cpu;
	private float mem;
	private float Rndrd;
	private float Rndwr;
	private float transaction; //每秒可以执行的事务数
	private float ping;
	private Timestamp testTime; //测试时间
	
	public CloudPlatformRanking(Integer id, String cloudPlatformName, float cpu, float mem, float Rndrd, float Rndwr, float transaction, Timestamp testTime, float ping){
		this.id = id;
		this.cloudPlatformName = cloudPlatformName;
		this.cpu = cpu;
		this.mem = mem;
		this.Rndrd = Rndrd;
		this.Rndwr = Rndwr;
		this.transaction = transaction;
		this.testTime = testTime;
		this.ping = ping;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public float getTransaction() {
		return transaction;
	}

	
	public float getCpu() {
		return cpu;
	}

	public void setCpu(float cpu) {
		this.cpu = cpu;
	}

	public float getMem() {
		return mem;
	}

	public void setMem(float mem) {
		this.mem = mem;
	}

	public float getRndrd() {
		return Rndrd;
	}

	public void setRndrd(float rndrd) {
		Rndrd = rndrd;
	}

	public float getRndwr() {
		return Rndwr;
	}

	public void setRndwr(float rndwr) {
		Rndwr = rndwr;
	}

	public void setTransaction(float transaction) {
		this.transaction = transaction;
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

	public float getPing() {
		return ping;
	}

	public void setPing(float ping) {
		this.ping = ping;
	}
	
}
