package com.appcloud.vm.utils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class TimeIntervalUtil {
	
	public TimeIntervalUtil(){
		
	}
	
	/**
	 * @param前端返回字符串截止时间   间隔时间默认为8天
	 * @return Calendar&Timestap格式的开始和结束
	 * */
	public IntevalCalendarTimeStamp getInteval(String timeEnd){
		IntevalCalendarTimeStamp inteval;
		Calendar caSelectTimeStart;
		Calendar caSelectTimeEnd;
		Timestamp tsSelectTimeStart;
		Timestamp tsSelectTimeEnd;
		if (timeEnd.equals("")) {
			caSelectTimeStart = Calendar.getInstance();
			caSelectTimeEnd = Calendar.getInstance();
		} else {
			caSelectTimeStart = Calendar.getInstance();
			caSelectTimeStart.set(Calendar.YEAR,
					Integer.valueOf(timeEnd.substring(6, 10)).intValue());
			caSelectTimeStart
					.set(Calendar.MONTH, (Integer.valueOf(timeEnd.substring(
							0, 2)).intValue()) - 1);
			caSelectTimeStart.set(Calendar.DATE,
					Integer.valueOf(timeEnd.substring(3, 5)).intValue());
			caSelectTimeEnd = Calendar.getInstance();
			caSelectTimeEnd.set(Calendar.YEAR,
					Integer.valueOf(timeEnd.substring(6, 10)).intValue());
			caSelectTimeEnd
					.set(Calendar.MONTH, (Integer.valueOf(timeEnd.substring(
							0, 2)).intValue()) - 1);
			caSelectTimeEnd.set(Calendar.DATE,
					Integer.valueOf(timeEnd.substring(3, 5)).intValue());
		}
		System.out.println("选择时间初始化" + caSelectTimeStart);
		System.out.println("选择时间初始化" + caSelectTimeEnd);

		Integer sub = -7;
		caSelectTimeStart.add(Calendar.DATE, sub);
		caSelectTimeStart.set(Calendar.HOUR_OF_DAY, 0);
		caSelectTimeStart.set(Calendar.MINUTE, 00);
		caSelectTimeStart.set(Calendar.SECOND, 00);
		caSelectTimeEnd.set(Calendar.HOUR_OF_DAY, 23);
		caSelectTimeEnd.set(Calendar.MINUTE, 59);
		caSelectTimeEnd.set(Calendar.SECOND, 59);
		System.out.println("选择时间处理" + caSelectTimeStart);
		System.out.println("选择时间处理" + caSelectTimeEnd);
		
		tsSelectTimeStart = new Timestamp(caSelectTimeStart.getTimeInMillis());
		tsSelectTimeEnd = new Timestamp(caSelectTimeEnd.getTimeInMillis());// Calendar->Date->TimeStamp
		if (timeEnd.equals("")) {
			tsSelectTimeStart.setMonth(caSelectTimeStart.get(Calendar.MONTH));
			tsSelectTimeEnd.setMonth(caSelectTimeEnd.get(Calendar.MONTH));
		}
		System.out.println("选择时间開始" + tsSelectTimeStart);
		System.out.println("选择时间結束" + tsSelectTimeEnd);
		
		inteval = new IntevalCalendarTimeStamp(caSelectTimeStart,caSelectTimeEnd,tsSelectTimeStart,tsSelectTimeEnd,24*8);
		return inteval;
	}
	
	/**
	 * @param 前端返回字符串截止时间   间隔时间
	 * @return Calendar&Timestap格式的开始和结束
	 * */
	public IntevalCalendarTimeStamp getInteval(String timeEnd, Integer intevalDay ){
		IntevalCalendarTimeStamp inteval;
		Calendar caSelectTimeStart;
		Calendar caSelectTimeEnd;
		Timestamp tsSelectTimeStart;
		Timestamp tsSelectTimeEnd;
		if (timeEnd.equals("")) {
			caSelectTimeStart = Calendar.getInstance();
			caSelectTimeEnd = Calendar.getInstance();
		} else {
			caSelectTimeStart = Calendar.getInstance();
			caSelectTimeStart.set(Calendar.YEAR,
					Integer.valueOf(timeEnd.substring(6, 10)).intValue());
			caSelectTimeStart
					.set(Calendar.MONTH, (Integer.valueOf(timeEnd.substring(
							0, 2)).intValue()) - 1);
			caSelectTimeStart.set(Calendar.DATE,
					Integer.valueOf(timeEnd.substring(3, 5)).intValue());
			caSelectTimeEnd = Calendar.getInstance();
			caSelectTimeEnd.set(Calendar.YEAR,
					Integer.valueOf(timeEnd.substring(6, 10)).intValue());
			caSelectTimeEnd
					.set(Calendar.MONTH, (Integer.valueOf(timeEnd.substring(
							0, 2)).intValue()) - 1);
			caSelectTimeEnd.set(Calendar.DATE,
					Integer.valueOf(timeEnd.substring(3, 5)).intValue());
		}
		System.out.println("选择时间初始化" + caSelectTimeStart);
		System.out.println("选择时间初始化" + caSelectTimeEnd);

		Integer sub = -intevalDay;
		caSelectTimeStart.add(Calendar.DATE, sub);
		caSelectTimeStart.set(Calendar.HOUR_OF_DAY, 0);
		caSelectTimeStart.set(Calendar.MINUTE, 00);
		caSelectTimeStart.set(Calendar.SECOND, 00);
		caSelectTimeEnd.set(Calendar.HOUR_OF_DAY, 23);
		caSelectTimeEnd.set(Calendar.MINUTE, 59);
		caSelectTimeEnd.set(Calendar.SECOND, 59);
		System.out.println("选择时间处理" + caSelectTimeStart);
		System.out.println("选择时间处理" + caSelectTimeEnd);
		
		tsSelectTimeStart = new Timestamp(caSelectTimeStart.getTimeInMillis());
		tsSelectTimeEnd = new Timestamp(caSelectTimeEnd.getTimeInMillis());// Calendar->Date->TimeStamp
		if (timeEnd.equals("")) {
			tsSelectTimeStart.setMonth(caSelectTimeStart.get(Calendar.MONTH));
			tsSelectTimeEnd.setMonth(caSelectTimeEnd.get(Calendar.MONTH));
		}
		System.out.println("选择时间開始" + tsSelectTimeStart);
		System.out.println("选择时间結束" + tsSelectTimeEnd);
		
		inteval = new IntevalCalendarTimeStamp(caSelectTimeStart,caSelectTimeEnd,tsSelectTimeStart,tsSelectTimeEnd,24*(intevalDay+1));

		return inteval;
	}
	
	/**
	 * @param 前端返回字符串开始时间   截至时间
	 * @return Calendar&Timestap格式的开始和结束，如果为空默认8天
	 * */
	public IntevalCalendarTimeStamp getInteval(String timeStart, String timeEnd ){
		IntevalCalendarTimeStamp inteval;
		Calendar caSelectTimeStart;
		Calendar caSelectTimeEnd;
		Timestamp tsSelectTimeStart;
		Timestamp tsSelectTimeEnd;
		if (timeStart.equals("") && timeEnd.equals("")) {
			caSelectTimeStart = Calendar.getInstance();
			caSelectTimeEnd = Calendar.getInstance();
			Integer sub = -7;
			caSelectTimeStart.add(Calendar.DATE, sub);
			caSelectTimeStart.set(Calendar.HOUR_OF_DAY, 0);
			caSelectTimeStart.set(Calendar.MINUTE, 00);
			caSelectTimeStart.set(Calendar.SECOND, 00);
			caSelectTimeEnd.set(Calendar.HOUR_OF_DAY, 23);
			caSelectTimeEnd.set(Calendar.MINUTE, 59);
			caSelectTimeEnd.set(Calendar.SECOND, 59);
			tsSelectTimeStart = new Timestamp(caSelectTimeStart.getTimeInMillis());
			tsSelectTimeEnd = new Timestamp(caSelectTimeEnd.getTimeInMillis());// Calendar->Date->TimeStamp
			inteval = new IntevalCalendarTimeStamp(caSelectTimeStart,caSelectTimeEnd,tsSelectTimeStart,tsSelectTimeEnd,24*8);
		} else if(timeStart.equals("") && !timeEnd.equals("")){
			caSelectTimeStart = Calendar.getInstance();
			caSelectTimeStart.set(Calendar.YEAR,
					Integer.valueOf(timeEnd.substring(6, 10)).intValue());
			caSelectTimeStart
					.set(Calendar.MONTH, (Integer.valueOf(timeEnd.substring(
							0, 2)).intValue()) - 1);
			caSelectTimeStart.set(Calendar.DATE,
					Integer.valueOf(timeEnd.substring(3, 5)).intValue());
			
			caSelectTimeEnd = Calendar.getInstance();
			caSelectTimeEnd.set(Calendar.YEAR,
					Integer.valueOf(timeEnd.substring(6, 10)).intValue());
			caSelectTimeEnd
					.set(Calendar.MONTH, (Integer.valueOf(timeEnd.substring(
							0, 2)).intValue()) - 1);
			caSelectTimeEnd.set(Calendar.DATE,
					Integer.valueOf(timeEnd.substring(3, 5)).intValue());
			Integer sub = -8;
			caSelectTimeStart.add(Calendar.DATE, sub);
			caSelectTimeStart.set(Calendar.HOUR_OF_DAY, Integer.valueOf(timeEnd.substring(11, 13)).intValue());
			caSelectTimeStart.set(Calendar.MINUTE, 00);
			caSelectTimeStart.set(Calendar.SECOND, 00);
			caSelectTimeEnd.set(Calendar.HOUR_OF_DAY, Integer.valueOf(timeEnd.substring(11, 13)).intValue());
			caSelectTimeEnd.set(Calendar.MINUTE, 59);
			caSelectTimeEnd.set(Calendar.SECOND, 59);
			tsSelectTimeStart = new Timestamp(caSelectTimeStart.getTimeInMillis());
			tsSelectTimeEnd = new Timestamp(caSelectTimeEnd.getTimeInMillis());// Calendar->Date->TimeStamp
			inteval = new IntevalCalendarTimeStamp(caSelectTimeStart,caSelectTimeEnd,tsSelectTimeStart,tsSelectTimeEnd,24*8);
		} else if(!timeStart.equals("") && timeEnd.equals("")){
			caSelectTimeStart = Calendar.getInstance();
			caSelectTimeStart.set(Calendar.YEAR,
					Integer.valueOf(timeStart.substring(6, 10)).intValue());
			caSelectTimeStart
					.set(Calendar.MONTH, (Integer.valueOf(timeStart.substring(
							0, 2)).intValue()) - 1);
			caSelectTimeStart.set(Calendar.DATE,
					Integer.valueOf(timeStart.substring(3, 5)).intValue());
			
			caSelectTimeEnd = Calendar.getInstance();
			caSelectTimeEnd.set(Calendar.YEAR,
					Integer.valueOf(timeStart.substring(6, 10)).intValue());
			caSelectTimeEnd
					.set(Calendar.MONTH, (Integer.valueOf(timeStart.substring(
							0, 2)).intValue()) - 1);
			caSelectTimeEnd.set(Calendar.DATE,
					Integer.valueOf(timeStart.substring(3, 5)).intValue());
			Integer sub = 8;
			caSelectTimeEnd.add(Calendar.DATE, sub);
			caSelectTimeStart.set(Calendar.HOUR_OF_DAY, Integer.valueOf(timeStart.substring(11, 13)).intValue());
			caSelectTimeStart.set(Calendar.MINUTE, 00);
			caSelectTimeStart.set(Calendar.SECOND, 00);
			caSelectTimeEnd.set(Calendar.HOUR_OF_DAY, Integer.valueOf(timeStart.substring(11, 13)).intValue());
			caSelectTimeEnd.set(Calendar.MINUTE, 59);
			caSelectTimeEnd.set(Calendar.SECOND, 59);
			tsSelectTimeStart = new Timestamp(caSelectTimeStart.getTimeInMillis());
			tsSelectTimeEnd = new Timestamp(caSelectTimeEnd.getTimeInMillis());// Calendar->Date->TimeStamp
			inteval = new IntevalCalendarTimeStamp(caSelectTimeStart,caSelectTimeEnd,tsSelectTimeStart,tsSelectTimeEnd,24*8);
		} else {
			caSelectTimeStart = Calendar.getInstance();
			caSelectTimeStart.set(Calendar.YEAR,
					Integer.valueOf(timeStart.substring(6, 10)).intValue());
			caSelectTimeStart
					.set(Calendar.MONTH, (Integer.valueOf(timeStart.substring(
							0, 2)).intValue()) - 1);
			caSelectTimeStart.set(Calendar.DATE,
					Integer.valueOf(timeStart.substring(3, 5)).intValue());
			
			
			caSelectTimeEnd = Calendar.getInstance();
			caSelectTimeEnd.set(Calendar.YEAR,
					Integer.valueOf(timeEnd.substring(6, 10)).intValue());
			caSelectTimeEnd
					.set(Calendar.MONTH, (Integer.valueOf(timeEnd.substring(
							0, 2)).intValue()) - 1);
			caSelectTimeEnd.set(Calendar.DATE,
					Integer.valueOf(timeEnd.substring(3, 5)).intValue());
			
			caSelectTimeStart.set(Calendar.HOUR_OF_DAY, Integer.valueOf(timeStart.substring(11, 13)).intValue());
			caSelectTimeStart.set(Calendar.MINUTE, 00);
			caSelectTimeStart.set(Calendar.SECOND, 00);
			caSelectTimeEnd.set(Calendar.HOUR_OF_DAY, Integer.valueOf(timeEnd.substring(11, 13)).intValue());
			caSelectTimeEnd.set(Calendar.MINUTE, 59);
			caSelectTimeEnd.set(Calendar.SECOND, 59);
			tsSelectTimeStart = new Timestamp(caSelectTimeStart.getTimeInMillis());
			tsSelectTimeEnd = new Timestamp(caSelectTimeEnd.getTimeInMillis());// Calendar->Date->TimeStamp
			int intevalHour = new Long((caSelectTimeEnd.getTimeInMillis()-caSelectTimeStart.getTimeInMillis())/(1000*60*60)).intValue();
			inteval = new IntevalCalendarTimeStamp(caSelectTimeStart,caSelectTimeEnd,tsSelectTimeStart,tsSelectTimeEnd,intevalHour);	
			System.out.println("选择时间处理" + caSelectTimeStart);
			System.out.println("选择时间处理" + caSelectTimeEnd);
			System.out.println("间隔时间：" + intevalHour);
		}
//		System.out.println("选择时间初始化" + caSelectTimeStart);
//		System.out.println("选择时间初始化" + caSelectTimeEnd);
//		System.out.println("选择时间处理" + caSelectTimeStart);
//		System.out.println("选择时间处理" + caSelectTimeEnd);
//		System.out.println("选择时间開始" + tsSelectTimeStart);
//		System.out.println("选择时间結束" + tsSelectTimeEnd);
		
		return inteval;
	}
	
	/**
	 * @param 时间戳
	 * @return 日历
	 * */
	public static Calendar timestamp2Calendar(Timestamp Timestamp){
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(Timestamp);
		return calendar;
	}
	
	/**
	 * @param 日历
	 * @return 时间戳
	 * */
	public Timestamp calendar2Timestamp(Calendar calendar){
		return new Timestamp(calendar.getTimeInMillis()); 
	}

}
