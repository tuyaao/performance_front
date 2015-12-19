package com.appcloud.vm.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StringUtil {
	
	public StringUtil(){
		
	}
	
	public String subStartAndEnd(String string){
		return string.substring(1, string.length() - 1);
	}
	
	/**Timestamp 转 Calendar*/
	public static Calendar timestamp2Calendar(Timestamp Timestamp){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(Timestamp);
		return calendar;
	}
	
	/**Calendar 转 Timestamp*/
	public static Timestamp calendar2Timestamp(Calendar calendar){
		return new Timestamp(calendar.getTimeInMillis()); 
	}
	
	/**Calendar 转 String*/
	public String cal2String(Calendar calendar){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String string = sdf.format(calendar.getTime());
		return string;
	}
	
	/**String 转 Calendar*/
	public Calendar String2cal(String string){
		Calendar calendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date;
			date = sdf.parse(string);
			calendar.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return calendar;
	}
	
	/**String 转 Calendar*/
	public static Calendar String2calYMD(String string){
		Calendar calendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd");
			Date date;
			date = sdf.parse(string);
			calendar.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return calendar;
	}
	
	/** String 转 Calendar*/
	
	/** Timestamp 转 String*/
	public static String timestamp2String(Timestamp timestamp){
		String Str = "";
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			Str = sdf.format(timestamp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Str;
	}
	
	/** Timestamp 转 String*/
	public static String timestamp2String(Timestamp timestamp, int type){
		//type: 0年月日
		String Str = "";
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {
			Str = sdf.format(timestamp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Str;
	}
	

}
