/**
 * @author TuYaao
 * @param  虚拟机实例
 *
 */

package com.appcloud.vm.action.dbentity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "file_io_test_result", catalog = "appcloud_performance")
public class FileIoTestResult implements java.io.Serializable{

	/** field */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer id;
	private Integer vmId;
	private Timestamp testTime;
	private String testMode;
	private float transferSpeed;
	private float totalTime;
	private Integer totalEvent;
	private float totalEventTime;
	private float responseTimeMin;
	private float responseTimeAvg;
	private float responseTimeMax;
	private float responseTimeApprox;
	private String threadsFairnessEvent;
	private String threadsFairnessExcTime;

	/** default constructor */
	public FileIoTestResult() {
	}

	/** minimal constructor */
	public FileIoTestResult(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public FileIoTestResult(Integer id, Integer vmId, Timestamp testTime,
			String testMode, float transferSpeed, float totalTime,Integer totalEvent, float totalEventTime,
			float responseTimeMin, float responseTimeAvg, float responseTimeMax,
	float responseTimeApprox, String threadsFairnessEvent,String threadsFairnessExcTime) {
		this.id = id;
		this.vmId = vmId;
		this.testTime = testTime;
		this.testMode = testMode;
		this.transferSpeed = transferSpeed;
		this.totalTime = totalTime;
		this.totalEvent = totalEvent;
		this.totalEventTime = totalEventTime;
		this.responseTimeMin = responseTimeMin;
		this.responseTimeAvg = responseTimeAvg;
		this.responseTimeMax = responseTimeMax;
		this.responseTimeApprox = responseTimeApprox;
		this.threadsFairnessEvent = threadsFairnessEvent;
		this.threadsFairnessExcTime = threadsFairnessExcTime;
	}

	/** property */
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "vm_id")
	public Integer getVmId() {
		return vmId;
	}

	public void setVmId(Integer vmId) {
		this.vmId = vmId;
	}

	@Column(name = "test_time")
	public Timestamp getTestTime() {
		return testTime;
	}

	public void setTestTime(Timestamp testTime) {
		this.testTime = testTime;
	}

	@Column(name = "test_mode")
	public String getTestMode() {
		return testMode;
	}

	public void setTestMode(String testMode) {
		this.testMode = testMode;
	}

	@Column(name = "transfer_speed")
	public float getTransferSpeed() {
		return transferSpeed;
	}

	public void setTransferSpeed(float transferSpeed) {
		this.transferSpeed = transferSpeed;
	}

	@Column(name = "total_time")
	public float getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(float totalTime) {
		this.totalTime = totalTime;
	}

	@Column(name = "total_event")
	public Integer getTotalEvent() {
		return totalEvent;
	}

	public void setTotalEvent(Integer totalEvent) {
		this.totalEvent = totalEvent;
	}

	@Column(name = "total_event_time")
	public float getTotalEventTime() {
		return totalEventTime;
	}

	public void setTotalEventTime(float totalEventTime) {
		this.totalEventTime = totalEventTime;
	}

	@Column(name = "response_time_min")
	public float getResponseTimeMin() {
		return responseTimeMin;
	}

	public void setResponseTimeMin(float responseTimeMin) {
		this.responseTimeMin = responseTimeMin;
	}

	@Column(name = "response_time_avg")
	public float getResponseTimeAvg() {
		return responseTimeAvg;
	}

	public void setResponseTimeAvg(float responseTimeAvg) {
		this.responseTimeAvg = responseTimeAvg;
	}

	@Column(name = "response_time_max")
	public float getResponseTimeMax() {
		return responseTimeMax;
	}

	public void setResponseTimeMax(float responseTimeMax) {
		this.responseTimeMax = responseTimeMax;
	}

	@Column(name = "response_time_approx")
	public float getResponseTimeApprox() {
		return responseTimeApprox;
	}

	public void setResponseTimeApprox(float responseTimeApprox) {
		this.responseTimeApprox = responseTimeApprox;
	}

	@Column(name = "threads_fairness_event")
	public String getThreadsFairnessEvent() {
		return threadsFairnessEvent;
	}

	public void setThreadsFairnessEvent(String threadsFairnessEvent) {
		this.threadsFairnessEvent = threadsFairnessEvent;
	}

	@Column(name = "threads_fairness_exc_time")
	public String getThreadsFairnessExcTime() {
		return threadsFairnessExcTime;
	}

	public void setThreadsFairnessExcTime(String threadsFairnessExcTime) {
		this.threadsFairnessExcTime = threadsFairnessExcTime;
	}

}
