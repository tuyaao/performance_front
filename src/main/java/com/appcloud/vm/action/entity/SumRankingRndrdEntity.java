package com.appcloud.vm.action.entity;


public class SumRankingRndrdEntity extends SumRankingAbstractEntity<SumRankingRndrdEntity>{

	private float Rndrd;
	
	public SumRankingRndrdEntity(){
		super.subClassName = "SumRankingRndrdEntity";
	}
	
	public SumRankingRndrdEntity(Integer id, String cloudPlatformName){
		super( id, cloudPlatformName );
		super.subClassName = "SumRankingRndrdEntity";
	}
	
	@Override
	public int compareTo(SumRankingRndrdEntity o) {
		int result = 0; // 返回对比结果 0为相等，负整数为小于，正整数为大于
		if (((SumRankingRndrdEntity) o).getRndrd() > this.Rndrd) {
			return 1;
		} else if (((SumRankingRndrdEntity) o).getRndrd() < this.Rndrd) {
			return -1;
		}
		return result;
	}
	
	public float getRndrd() {
		return Rndrd;
	}

	public void setRndrd(float rndrd) {
		Rndrd = rndrd;
	}

}
