package com.appcloud.mysqldao;

import com.appcloud.vm.action.dbentity.SumWeekIozone;

public class SumWeekIozoneDao extends AbstractDao<SumWeekIozone> {
	
	@Override
	protected Class<SumWeekIozone> getEntityClass() {
		return SumWeekIozone.class;
	} 
}
