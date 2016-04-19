package com.appcloud.vm.action.entity;


public class SumRankingPingSinaEntity extends SumRankingAbstractEntity<SumRankingPingSinaEntity>{
	
	private float pingSina;
	
	public SumRankingPingSinaEntity(){
		super.subClassName = "SumRankingPingSinaEntity";
	}
	
	public SumRankingPingSinaEntity(Integer id, String cloudPlatformName){
		super( id, cloudPlatformName );
		super.subClassName = "SumRankingPingSinaEntity";
	}
	
	@Override
	public int compareTo(SumRankingPingSinaEntity o) {
		int result = 0; // 返回对比结果 0为相等，负整数为小于，正整数为大于
		if (((SumRankingPingSinaEntity) o).getPingSina() > this.pingSina) {
			return 1;
		} else if (((SumRankingPingSinaEntity) o).getPingSina() < this.pingSina) {
			return -1;
		}
		return result;
	}

	public float getPingSina() {
		return pingSina;
	}

	public void setPingSina(float pingSina) {
		this.pingSina = pingSina;
	}

}
