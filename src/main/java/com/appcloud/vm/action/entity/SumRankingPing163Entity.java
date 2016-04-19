package com.appcloud.vm.action.entity;


public class SumRankingPing163Entity extends SumRankingAbstractEntity<SumRankingPing163Entity>{

	private float ping163;
	
	public SumRankingPing163Entity(){
		super.subClassName = "SumRankingPing163Entity";
	}
	
	public SumRankingPing163Entity(Integer id, String cloudPlatformName){
		super( id, cloudPlatformName );
		super.subClassName = "SumRankingPing163Entity";
	}

	@Override
	public int compareTo(SumRankingPing163Entity o) {
		int result = 0; // 返回对比结果 0为相等，负整数为小于，正整数为大于
		if (((SumRankingPing163Entity) o).getPing163() > this.ping163) {
			return 1;
		} else if (((SumRankingPing163Entity) o).getPing163() < this.ping163) {
			return -1;
		}
		return result;
	}
	
	public float getPing163() {
		return ping163;
	}

	public void setPing163(float ping163) {
		this.ping163 = ping163;
	}

}
