package com.appcloud.vm.action.entity;


public class SumRankingRndwrEntity extends SumRankingAbstractEntity<SumRankingRndwrEntity>{

	private float Rndwr;
	
	public SumRankingRndwrEntity(){
		super.subClassName = "SumRankingRndwrEntity";
	}
	
	public SumRankingRndwrEntity(Integer id, String cloudPlatformName){
		super( id, cloudPlatformName );
		super.subClassName = "SumRankingRndwrEntity";
	}
	
	@Override
	public int compareTo(SumRankingRndwrEntity o) {
		int result = 0; // 返回对比结果 0为相等，负整数为小于，正整数为大于
		if (((SumRankingRndwrEntity) o).getRndwr() > this.Rndwr) {
			return 1;
		} else if (((SumRankingRndwrEntity) o).getRndwr() < this.Rndwr) {
			return -1;
		}
		return result;
	}
	
	public float getRndwr() {
		return Rndwr;
	}

	public void setRndwr(float rndwr) {
		Rndwr = rndwr;
	}

}
