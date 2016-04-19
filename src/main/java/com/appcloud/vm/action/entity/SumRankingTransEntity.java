package com.appcloud.vm.action.entity;


public class SumRankingTransEntity extends SumRankingAbstractEntity<SumRankingTransEntity>{

	private float transaction;
	
	public SumRankingTransEntity(){
		super.subClassName = "SumRankingTransEntity";
	}
	
	public SumRankingTransEntity(Integer id, String cloudPlatformName){
		super( id, cloudPlatformName );
		super.subClassName = "SumRankingTransEntity";
	}

	@Override
	public int compareTo(SumRankingTransEntity o) {
		int result = 0; // 返回对比结果 0为相等，负整数为小于，正整数为大于
		if (((SumRankingTransEntity) o).getTransaction() > this.transaction) {
			return 1;
		} else if (((SumRankingTransEntity) o).getTransaction() < this.transaction) {
			return -1;
		}
		return result;
	} 
	
	public float getTransaction() {
		return transaction;
	}

	public void setTransaction(float transaction) {
		this.transaction = transaction;
	}

}
