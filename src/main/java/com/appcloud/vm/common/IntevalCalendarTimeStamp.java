package com.appcloud.vm.common;

import java.sql.Timestamp;
import java.util.Calendar;

public class IntevalCalendarTimeStamp {
	private Calendar caSelectTimeStart;
	private Calendar caSelectTimeEnd;
	private Timestamp tsSelectTimeStart;
	private Timestamp tsSelectTimeEnd;
	private Integer hourNum;
	
	public IntevalCalendarTimeStamp(Calendar caSelectTimeStart, Calendar caSelectTimeEnd, Timestamp tsSelectTimeStart, Timestamp tsSelectTimeEnd, Integer hourNum){
		this.caSelectTimeStart = caSelectTimeStart;
		this.caSelectTimeEnd = caSelectTimeEnd;
		this.tsSelectTimeStart = tsSelectTimeStart;
		this.tsSelectTimeEnd = tsSelectTimeEnd;
		this.hourNum = hourNum;
		
	}
	
	public Calendar getCaSelectTimeStart() {
		return caSelectTimeStart;
	}

	public void setCaSelectTimeStart(Calendar caSelectTimeStart) {
		this.caSelectTimeStart = caSelectTimeStart;
	}

	public Calendar getCaSelectTimeEnd() {
		return caSelectTimeEnd;
	}

	public void setCaSelectTimeEnd(Calendar caSelectTimeEnd) {
		this.caSelectTimeEnd = caSelectTimeEnd;
	}

	public Timestamp getTsSelectTimeStart() {
		return tsSelectTimeStart;
	}

	public void setTsSelectTimeStart(Timestamp tsSelectTimeStart) {
		this.tsSelectTimeStart = tsSelectTimeStart;
	}

	public Timestamp getTsSelectTimeEnd() {
		return tsSelectTimeEnd;
	}

	public void setTsSelectTimeEnd(Timestamp tsSelectTimeEnd) {
		this.tsSelectTimeEnd = tsSelectTimeEnd;
	}

	public Integer getHourNum() {
		return hourNum;
	}

	public void setHourNum(Integer hourNum) {
		this.hourNum = hourNum;
	}

}
