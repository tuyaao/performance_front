/**
 * @author TuYaao
 * @param  虚拟机实例
 *
 */

package com.appcloud.vm.action.dbentity;

public class VMhardware implements java.io.Serializable {

	/** field */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String uuid;
	private Integer cpu;
	private Integer memory;
	private Integer disk;
	private Integer bandwidth;

	/** default constructor */
	public VMhardware() {
	}

	/** minimal constructor */
	public VMhardware(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public VMhardware(Integer id, String uuid, Integer cpu, Integer memory,
			Integer disk, Integer bandwidth) {
		this.id = id;
		this.uuid = uuid;
		this.cpu = cpu;
		this.memory = memory;
		this.disk = disk;
		this.bandwidth = bandwidth;
	}

	/** property */

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getCpu() {
		return cpu;
	}

	public void setCpu(Integer cpu) {
		this.cpu = cpu;
	}

	public Integer getMemory() {
		return memory;
	}

	public void setMemory(Integer memory) {
		this.memory = memory;
	}

	public Integer getDisk() {
		return disk;
	}

	public void setDisk(Integer disk) {
		this.disk = disk;
	}

	public Integer getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(Integer bandwidth) {
		this.bandwidth = bandwidth;
	}

}
