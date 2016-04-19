package com.appcloud.mysqldao;

import com.appcloud.vm.action.dbentity.SumWeekCpu;

public class SumWeekCpuDao extends AbstractDao<SumWeekCpu> {
	
	@Override
	protected Class<SumWeekCpu> getEntityClass() {
		return SumWeekCpu.class;
	} 
	
//	private Integer id;
//	private Integer uuid;
//	private String companyname;
//	private String time;
//	private float totalTime;
//	private Integer count;
	
	public static void main(){
		SumWeekCpu test = new SumWeekCpu();
		test.setUuid(1);
		test.setCompanyname("test");
	}
}
