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
@Table(name = "ping_test_result_newweek", catalog = "appcloud_performance")
public class SumWeekPing implements java.io.Serializable {

	/** field */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;
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
	
	private Timestamp sumTime;
	private String destIP;
	private float loss;
	private float avg;
	private Integer count;

	/** default constructor */
	public SumWeekPing() {
	}

	/** minimal constructor */
	public SumWeekPing(Integer id) {
		this.id = id;
	}

	/** full constructor */
    //TODO

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "uuid")
	public int getUuid() {
		return uuid;
	}

	public void setUuid(int uuid) {
		this.uuid = uuid;
	}

	@Column(name = "cpu")
	public int getCpu() {
		return cpu;
	}

	public void setCpu(int cpu) {
		this.cpu = cpu;
	}

	@Column(name = "mem")
	public int getMem() {
		return mem;
	}

	public void setMem(int mem) {
		this.mem = mem;
	}

	@Column(name = "disk")
	public int getDisk() {
		return disk;
	}

	public void setDisk(int disk) {
		this.disk = disk;
	}

	@Column(name = "bandwidth")
	public int getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(int bandwidth) {
		this.bandwidth = bandwidth;
	}

	@Column(name = "ip")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "mac")
	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	@Column(name = "create_time")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "update_time")
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "os")
	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	@Column(name = "company_id")
	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	@Column(name = "company_name")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column(name = "sum_time")
	public Timestamp getSumTime() {
		return sumTime;
	}

	public void setSumTime(Timestamp sumTime) {
		this.sumTime = sumTime;
	}

	@Column(name = "dest_ip")
	public String getDestIP() {
		return destIP;
	}

	public void setDestIP(String destIP) {
		this.destIP = destIP;
	}

	@Column(name = "loss")
	public float getLoss() {
		return loss;
	}

	public void setLoss(float loss) {
		this.loss = loss;
	}

	@Column(name = "avg")
	public float getAvg() {
		return avg;
	}

	public void setAvg(float avg) {
		this.avg = avg;
	}

	@Column(name = "count")
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
