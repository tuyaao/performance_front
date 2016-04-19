/**
 * @author TuYaao
 * @param  虚拟机实例
 *
 */

package com.appcloud.vm.action.dbentity;

public class SumWeekPing implements java.io.Serializable {

	/** field */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer uuid;
	private String companyname;
	private String time;
	private String destIp;
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
	public SumWeekPing(Integer id, Integer uuid, String companyname,
			String time, String destIp, float loss, float avg, Integer count) {
		this.id = id;
		this.uuid = uuid;
		this.companyname = companyname;
		this.time = time;
		this.destIp = destIp;
		this.loss = loss;
		this.avg = avg;
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

	public String getDestIp() {
		return destIp;
	}

	public void setDestIp(String destIp) {
		this.destIp = destIp;
	}

	public float getLoss() {
		return loss;
	}

	public void setLoss(float loss) {
		this.loss = loss;
	}

	public float getAvg() {
		return avg;
	}

	public void setAvg(float avg) {
		this.avg = avg;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
