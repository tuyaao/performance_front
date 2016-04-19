package com.appcloud.mysqldao;

import com.appcloud.vm.action.dbentity.SumWeekPing;

public class SumWeekPingDao extends AbstractDao<SumWeekPing> {
	
	@Override
	protected Class<SumWeekPing> getEntityClass() {
		return SumWeekPing.class;
	} 
}
