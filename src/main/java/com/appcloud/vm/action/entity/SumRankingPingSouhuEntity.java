package com.appcloud.vm.action.entity;


public class SumRankingPingSouhuEntity extends SumRankingAbstractEntity<SumRankingPingSouhuEntity>{
	
	private float pingSouhu;
	
	public SumRankingPingSouhuEntity(){
		super.subClassName = "SumRankingPingSouhuEntity";
	}
	
	public SumRankingPingSouhuEntity(Integer id, String cloudPlatformName){
		super( id, cloudPlatformName );
		super.subClassName = "SumRankingPingSouhuEntity";
	}
	
	@Override
	public int compareTo(SumRankingPingSouhuEntity o) {
		int result = 0; // 返回对比结果 0为相等，负整数为小于，正整数为大于
		if (((SumRankingPingSouhuEntity) o).getPingSouhu() > this.pingSouhu) {
			return 1;
		} else if (((SumRankingPingSouhuEntity) o).getPingSouhu() < this.pingSouhu) {
			return -1;
		}
		return result;
	}

	public float getPingSouhu() {
		return pingSouhu;
	}

	public void setPingSouhu(float pingSouhu) {
		this.pingSouhu = pingSouhu;
	}

}
