package com.appcloud.mysqldao;

import com.appcloud.vm.action.dbentity.SumWeekTpcc;

public class SumWeekTpccDao extends AbstractDao<SumWeekTpcc> {
	
	@Override
	protected Class<SumWeekTpcc> getEntityClass() {
		return SumWeekTpcc.class;
	} 
}
