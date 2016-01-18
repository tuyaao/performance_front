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
	private float pingBaidu;
	private float ping163;
	private float pingSina;
	private float pingQQ;
	private float pingSouhu;
	private Timestamp testTime; //测试时间
	
	public CloudPlatformRanking(Integer id, String cloudPlatformName, float cpu, float mem, float Rndrd, float Rndwr, float transaction, Timestamp testTime, 
			                    float pingBaidu, float ping163,  float pingSina, float pingQQ, float pingSouhu){
		this.id = id;
		this.cloudPlatformName = cloudPlatformName;
		this.cpu = cpu;
		this.mem = mem;
		this.Rndrd = Rndrd;
		this.Rndwr = Rndwr;
		this.transaction = transaction;
		this.testTime = testTime;
		this.pingBaidu = pingBaidu;
		this.ping163 = ping163;
		this.pingSina = pingSina;
		this.pingQQ = pingQQ;
		this.pingSouhu = pingSouhu;
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

	public float getPingBaidu() {
		return pingBaidu;
	}

	public void setPingBaidu(float pingBaidu) {
		this.pingBaidu = pingBaidu;
	}

	public float getPing163() {
		return ping163;
	}

	public void setPing163(float ping163) {
		this.ping163 = ping163;
	}

	public float getPingSina() {
		return pingSina;
	}

	public void setPingSina(float pingSina) {
		this.pingSina = pingSina;
	}

	public float getPingQQ() {
		return pingQQ;
	}

	public void setPingQQ(float pingQQ) {
		this.pingQQ = pingQQ;
	}

	public float getPingSouhu() {
		return pingSouhu;
	}

	public void setPingSouhu(float pingSouhu) {
		this.pingSouhu = pingSouhu;
	}

}
