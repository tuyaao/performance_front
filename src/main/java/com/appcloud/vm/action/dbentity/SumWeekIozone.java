/**
 * @author TuYaao
 * @param  虚拟机实例
 *
 */

package com.appcloud.vm.action.dbentity;

public class SumWeekIozone implements java.io.Serializable {

	/** field */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer uuid;
	private String companyname;
	private String time;
	private Integer fileSize;
	private Integer recordSize;
	private Integer write;
	private Integer rewrite;
	private Integer read;
	private Integer reread;
	private Integer randomRead;
	private Integer randomWrite;
	private Integer count;

	/** default constructor */
	public SumWeekIozone() {
	}

	/** minimal constructor */
	public SumWeekIozone(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public SumWeekIozone(Integer id, Integer uuid, String companyname,
			String time, Integer fileSize, Integer recordSize, Integer write,
			Integer rewrite, Integer read, Integer reread, Integer randomRead,
			Integer randomWrite, Integer count) {
		this.id = id;
		this.uuid = uuid;
		this.companyname = companyname;
		this.time = time;
		this.fileSize = fileSize;
		this.recordSize = recordSize;
		this.write = write;
		this.rewrite = rewrite;
		this.read = read;
		this.reread = reread;
		this.randomRead = randomRead;
		this.randomWrite = randomWrite;
		this.count = count;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUuid() {
		return uuid;
	}

	public void setUuid(Integer uuid) {
		this.uuid = uuid;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getFileSize() {
		return fileSize;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	public Integer getRecordSize() {
		return recordSize;
	}

	public void setRecordSize(Integer recordSize) {
		this.recordSize = recordSize;
	}

	public Integer getWrite() {
		return write;
	}

	public void setWrite(Integer write) {
		this.write = write;
	}

	public Integer getRewrite() {
		return rewrite;
	}

	public void setRewrite(Integer rewrite) {
		this.rewrite = rewrite;
	}

	public Integer getRead() {
		return read;
	}

	public void setRead(Integer read) {
		this.read = read;
	}

	public Integer getReread() {
		return reread;
	}

	public void setReread(Integer reread) {
		this.reread = reread;
	}

	public Integer getRandomRead() {
		return randomRead;
	}

	public void setRandomRead(Integer randomRead) {
		this.randomRead = randomRead;
	}

	public Integer getRandomWrite() {
		return randomWrite;
	}

	public void setRandomWrite(Integer randomWrite) {
		this.randomWrite = randomWrite;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
