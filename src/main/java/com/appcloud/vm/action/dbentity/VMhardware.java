/**
 * @author TuYaao
 * @param  虚拟机实例
 *
 */

package com.appcloud.vm.action.dbentity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "vm_hardware", catalog = "appcloud_performance")
public class VMhardware implements java.io.Serializable{

	/** field */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer id;
	private String uuid;
	private Integer cpu;
	private Integer memory;
	private Integer disk;
	private Integer bandwidth;;

	/** default constructor */
	public VMhardware() {
	}

	/** minimal constructor */
	public VMhardware(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public VMhardware(Integer id, String uuid, Integer cpu,
			Integer memory, Integer disk,Integer bandwidth) {
		this.id = id;
		this.uuid = uuid;
		this.cpu = cpu;
		this.memory = memory;
		this.disk = disk;
		this.bandwidth = bandwidth;
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
    
	@Column(name = "uuid")
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "cpu")
	public Integer getCpu() {
		return cpu;
	}

	public void setCpu(Integer cpu) {
		this.cpu = cpu;
	}
    
	@Column(name = "memory")
	public Integer getMemory() {
		return memory;
	}

	public void setMemory(Integer memory) {
		this.memory = memory;
	}
    
	@Column(name = "disk")
	public Integer getDisk() {
		return disk;
	}

	public void setDisk(Integer disk) {
		this.disk = disk;
	}
    
	@Column(name = "bandwidth")
	public Integer getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(Integer bandwidth) {
		this.bandwidth = bandwidth;
	}
	
	

}
