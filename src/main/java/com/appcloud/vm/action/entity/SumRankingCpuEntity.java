package com.appcloud.vm.action.entity;

public class SumRankingCpuEntity extends
		SumRankingAbstractEntity<SumRankingCpuEntity> {

	private float cpu = 0.0f;

	public SumRankingCpuEntity() {
		super.subClassName = "SumRankingCpuEntity";
	}

	public SumRankingCpuEntity(Integer id, String cloudPlatformName) {
		super(id, cloudPlatformName);
		super.subClassName = "SumRankingCpuEntity";
	}

	public float getCpu() {
		return cpu;
	}

	public void setCpu(float cpu) {
		this.cpu = cpu;
	}

	@Override
	public int compareTo(SumRankingCpuEntity o) {
		int result = 0; // 返回对比结果 0为相等，负整数为小于，正整数为大于
		if (((SumRankingCpuEntity) o).getCpu() > this.cpu) {
			return 1;
		} else if (((SumRankingCpuEntity) o).getCpu() < this.cpu) {
			return -1;
		}
		return result;
	}

}
