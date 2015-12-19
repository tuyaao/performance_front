/**
 * @author TuYaao
 * @param  虚拟机实例
 *
 */

package com.appcloud.vm.action.dbentity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "memory_test_result_week", catalog = "appcloud_performance")
public class MemoryTestResultWeek implements java.io.Serializable{

	/** field */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer id;
	private Integer vmId;
	private Timestamp testTime;
	private float transferSpeed;
	private float totalTime;
	private Long totalEvent;
	private float totalEventTime;
	private float responseTimeMin;
	private float responseTimeAvg;
	private float responseTimeMax;
	private float responseTimeApprox;
	private Integer count;

	/** default constructor */
	public MemoryTestResultWeek() {
	}

	/** minimal constructor */
	public MemoryTestResultWeek(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public MemoryTestResultWeek(Integer id, Integer vmId, Timestamp testTime, float transferSpeed, float totalTime,Long totalEvent, float totalEventTime,
			float responseTimeMin, float responseTimeAvg, float responseTimeMax,
	float responseTimeApprox, Integer count) {
		this.id = id;
		this.vmId = vmId;
		this.testTime = testTime;
		this.transferSpeed = transferSpeed;
		this.totalTime = totalTime;
		this.totalEvent = totalEvent;
		this.totalEventTime = totalEventTime;
		this.responseTimeMin = responseTimeMin;
		this.responseTimeAvg = responseTimeAvg;
		this.responseTimeMax = responseTimeMax;
		this.responseTimeApprox = responseTimeApprox;
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
	public Long getTotalEvent() {
		return totalEvent;
	}

	public void setTotalEvent(Long totalEvent) {
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

	@Column(name = "count")
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
