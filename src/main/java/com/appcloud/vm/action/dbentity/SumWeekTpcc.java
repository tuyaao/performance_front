/**
 * @author TuYaao
 * @param  虚拟机实例
 *
 */

package com.appcloud.vm.action.dbentity;

public class SumWeekTpcc implements java.io.Serializable {

	/** field */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer uuid;
	private String companyname;
	private String time;
	private float tpmc;
	private Integer count;

	/** default constructor */
	public SumWeekTpcc() {
	}

	/** minimal constructor */
	public SumWeekTpcc(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public SumWeekTpcc(Integer id, Integer uuid, String companyname,
			String time, float tpmc, Integer count) {
		this.id = id;
		this.uuid = uuid;
		this.companyname = companyname;
		this.time = time;
		this.tpmc = tpmc;
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

	public float getTpmc() {
		return tpmc;
	}

	public void setTpmc(float tpmc) {
		this.tpmc = tpmc;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
