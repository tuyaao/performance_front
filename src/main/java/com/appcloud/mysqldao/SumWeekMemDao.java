package com.appcloud.mysqldao;

import com.appcloud.vm.action.dbentity.SumWeekMem;

public class SumWeekMemDao extends AbstractDao<SumWeekMem> {
	
	@Override
	protected Class<SumWeekMem> getEntityClass() {
		return SumWeekMem.class;
	} 
}
