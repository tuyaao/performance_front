package com.appcloud.vm.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//这个是返回比较结果的每个图表的每一条曲线的值
public class CompareResultInstance {
	
	private Integer companyId; //公司id
	private Integer instanceId; //虚拟机id
	private List<Map<String,String>> curve = new ArrayList<Map<String,String>>(); //map 为两对，一对为时间，一对为值
	
	public CompareResultInstance(){
		
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(Integer instanceId) {
		this.instanceId = instanceId;
	}

	public List<Map<String, String>> getCurve() {
		return curve;
	}

	public void setCurve(List<Map<String, String>> curve) {
		this.curve = curve;
	}
	
}
