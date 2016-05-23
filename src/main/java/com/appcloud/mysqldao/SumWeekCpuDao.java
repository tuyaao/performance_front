package com.appcloud.mysqldao;

import com.appcloud.vm.action.dbentity.SumWeekCpu;

public class SumWeekCpuDao extends AbstractDao<SumWeekCpu> {
	
	@Override
	protected Class<SumWeekCpu> getEntityClass() {
		return SumWeekCpu.class;
	} 
	
}
