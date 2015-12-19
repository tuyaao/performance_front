package com.appcloud.vm.action.entity;

public class CloudPlatformEntity {
	
	private Integer id;
	private String name;
	private Integer containCompanyQuantity;
	
	public CloudPlatformEntity(){
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getContainCompanyQuantity() {
		return containCompanyQuantity;
	}

	public void setContainCompanyQuantity(Integer containCompanyQuantity) {
		this.containCompanyQuantity = containCompanyQuantity;
	}

	
}
