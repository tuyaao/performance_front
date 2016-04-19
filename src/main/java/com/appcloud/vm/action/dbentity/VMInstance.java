/**
 * @author TuYaao
 * @param  虚拟机实例
 *
 */

package com.appcloud.vm.action.dbentity;

import java.sql.Timestamp;

//使一个POJO类成为一个可持久化的Entity类还需要注意以下几点。
//·实体类必须有一个默认的无参数的构造方法，并且无参数的构造方法必须是public或protected的。
//·若要使实体类能够支持序列化，例如支持远程调用，可以实现Serializable接口。实现该接口是可选的，但建议实体类都实现这个接口。

public class VMInstance implements java.io.Serializable {

	/** field */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String ip;
	private String mac;
	private Integer cloudPlatformId;
	private String name;
	private String description;
	private Timestamp createTime;
	private Timestamp updateTime;
	private String status;
	private String os;
	private Integer hardware;

	/** default constructor */
	public VMInstance() {
	}

	/** minimal constructor */
	public VMInstance(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public VMInstance(Integer id, String ip, String mac,
			Integer cloudPlatformId, String name, String description,
			Timestamp createTime, Timestamp updateTime, String status,
			String os, Integer hardware) {
		this.id = id;
		this.ip = ip;
		this.mac = mac;
		this.cloudPlatformId = cloudPlatformId;
		this.name = name;
		this.description = description;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.status = status;
		this.os = os;
		this.hardware = hardware;

	}

	/** property */

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public Integer getCloudPlatformId() {
		return cloudPlatformId;
	}

	public void setCloudPlatformId(Integer cloudPlatformId) {
		this.cloudPlatformId = cloudPlatformId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public Integer getHardware() {
		return hardware;
	}

	public void setHardware(Integer hardware) {
		this.hardware = hardware;
	}

}
