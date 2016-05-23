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
@Table(name = "file_io_test_result_newweek", catalog = "appcloud_performance")
public class SumWeekIozone implements java.io.Serializable {

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
	private double  fileSize;
	private double  recordSize;
	private double  write;
	private double  rewrite;
	private double  read;
	private double  reread;
	private double  randomRead;
	private double  randomWrite;
	private Integer count;

	/** default constructor */
	public SumWeekIozone() {
	}

	/** minimal constructor */
	public SumWeekIozone(int id) {
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

	@Column(name = "file_size")
	public double getFileSize() {
		return fileSize;
	}

	public void setFileSize(double fileSize) {
		this.fileSize = fileSize;
	}

	@Column(name = "record_size")
	public double getRecordSize() {
		return recordSize;
	}

	public void setRecordSize(double recordSize) {
		this.recordSize = recordSize;
	}

	@Column(name = "write")
	public double getWrite() {
		return write;
	}

	public void setWrite(double write) {
		this.write = write;
	}

	@Column(name = "rewrite")
	public double getRewrite() {
		return rewrite;
	}

	public void setRewrite(double rewrite) {
		this.rewrite = rewrite;
	}

	@Column(name = "read")
	public double getRead() {
		return read;
	}

	public void setRead(double read) {
		this.read = read;
	}

	@Column(name = "reread")
	public double getReread() {
		return reread;
	}

	public void setReread(double reread) {
		this.reread = reread;
	}

	@Column(name = "random_read")
	public double getRandomRead() {
		return randomRead;
	}

	public void setRandomRead(double randomRead) {
		this.randomRead = randomRead;
	}

	@Column(name = "random_write")
	public double getRandomWrite() {
		return randomWrite;
	}

	public void setRandomWrite(double randomWrite) {
		this.randomWrite = randomWrite;
	}

	@Column(name = "count")
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	public String toString(){
		return "uuid：" + uuid + "  " + 
			   "cpu：" + cpu + "  " + 
			   "mem：" + mem + "  " + 
			   "disk：" + disk + "  " + 
			   "bandwidth：" + bandwidth + "  " + 
			   "ip：" + ip + "  " + 
			   "mac：" + mac + "  " + 
			   "os：" + os + "  " + 
			   "companyId：" + companyId + "  " + 
			   "companyName：" + companyName + "  " + 
			   "fileSize：" + fileSize + "  " + 
			   "recordSize：" + recordSize + "  " + 
			   "write：" + write + "  " + 
			   "rewrite：" + rewrite + "  " + 
			   "read：" + read + "  " + 
			   "reread：" + reread + "  " + 
			   "randomRead：" + randomRead + "  " + 
			   "randomWrite：" + randomWrite + "  " + 
			   "count：" + count;
	}

}
