package com.appcloud.vm.action.entity;


public class SumRankingPingBaiduEntity extends SumRankingAbstractEntity<SumRankingPingBaiduEntity>{
	
	private float pingBaidu;
	
	public SumRankingPingBaiduEntity(){
		super.subClassName = "SumRankingPingBaiduEntity";
	}
	
	
	public SumRankingPingBaiduEntity(Integer id, String cloudPlatformName){
		super( id, cloudPlatformName );
		super.subClassName = "SumRankingPingBaiduEntity";
	}
	
	@Override
	public int compareTo(SumRankingPingBaiduEntity o) {
		int result = 0; // 返回对比结果 0为相等，负整数为小于，正整数为大于
		if (((SumRankingPingBaiduEntity) o).getPingBaidu() > this.pingBaidu) {
			return 1;
		} else if (((SumRankingPingBaiduEntity) o).getPingBaidu() < this.pingBaidu) {
			return -1;
		}
		return result;
	}

	public float getPingBaidu() {
		return pingBaidu;
	}

	public void setPingBaidu(float pingBaidu) {
		this.pingBaidu = pingBaidu;
	}

}
