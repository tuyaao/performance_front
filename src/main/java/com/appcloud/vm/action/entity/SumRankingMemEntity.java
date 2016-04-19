package com.appcloud.vm.action.entity;


public class SumRankingMemEntity extends SumRankingAbstractEntity<SumRankingMemEntity>{

	private float mem;
	
	public SumRankingMemEntity(){
		super.subClassName = "SumRankingMemEntity";
	}
	
	public SumRankingMemEntity(Integer id, String cloudPlatformName){
		super( id, cloudPlatformName );
		super.subClassName = "SumRankingMemEntity";
	}

	@Override
	public int compareTo(SumRankingMemEntity o) {
		int result = 0; // 返回对比结果 0为相等，负整数为小于，正整数为大于
		if (((SumRankingMemEntity) o).getMem() > this.mem) {
			return 1;
		} else if (((SumRankingMemEntity) o).getMem() < this.mem) {
			return -1;
		}
		return result;
	}

	public float getMem() {
		return mem;
	}

	public void setMem(float mem) {
		this.mem = mem;
	}
	
}
