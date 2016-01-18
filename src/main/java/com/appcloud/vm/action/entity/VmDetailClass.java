package com.appcloud.vm.action.entity;

public class VmDetailClass {
	
	private Integer id;
	private String comName;
	private Integer comId;
	private Integer cpu;
	private Integer mem;
	private Integer disk;
	private String os;
	
	public VmDetailClass() {
		
	}
	
    public VmDetailClass(Integer id, String comName, Integer comId, Integer cpu, Integer mem, Integer disk, String os) {
    	this.id = id;
    	this.comName = comName;
    	this.comId = comId;
    	this.cpu = cpu;
    	this.mem = mem;
    	this.disk = disk;
    	this.os = os;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCpu() {
		return cpu;
	}
	public void setCpu(Integer cpu) {
		this.cpu = cpu;
	}
	public Integer getMem() {
		return mem;
	}
	public void setMem(Integer mem) {
		this.mem = mem;
	}
	public Integer getDisk() {
		return disk;
	}
	public void setDisk(Integer disk) {
		this.disk = disk;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public Integer getComId() {
		return comId;
	}

	public void setComId(Integer comId) {
		this.comId = comId;
	}
	
	public String toString(){
		return "id:"+id+"comName:"+comName+"comId:"+comId+"cpu:"+cpu+"mem:"+mem+"disk"+disk+"os:"+os;
	}

}
