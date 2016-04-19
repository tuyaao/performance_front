package com.appcloud.vm.action.entity;


public class SumRankingPingQQEntity extends SumRankingAbstractEntity<SumRankingPingQQEntity>{
	
	private float pingQQ;
	
	public SumRankingPingQQEntity(){
		super.subClassName = "SumRankingPingQQEntity";
	}
	
	public SumRankingPingQQEntity(Integer id, String cloudPlatformName){
		super( id, cloudPlatformName );
		super.subClassName = "SumRankingPingQQEntity";
	}

	@Override
	public int compareTo(SumRankingPingQQEntity o) {
		int result = 0; // 返回对比结果 0为相等，负整数为小于，正整数为大于
		if (((SumRankingPingQQEntity) o).getPingQQ() > this.pingQQ) {
			return 1;
		} else if (((SumRankingPingQQEntity) o).getPingQQ() < this.pingQQ) {
			return -1;
		}
		return result;
	}
	
	public float getPingQQ() {
		return pingQQ;
	}

	public void setPingQQ(float pingQQ) {
		this.pingQQ = pingQQ;
	}

}
