package com.duolanjian.java.eagle.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.duolanjian.java.eagle.exception.OperationNotAllowedException;
/*
 * @author chenlisong
 */
public class DateTool {

	public static final SimpleDateFormat standardSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static Date getWorldBegin() {
		Date date = null;
		try {
			date = standardSdf.parse("1970-01-01 08:00:00");
		} catch (ParseException e) {
			throw new OperationNotAllowedException("parse date error.", e);
		}
		return date;
	}
	
	public static Date addHours(Date date,int hours) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, hours);
		date = calendar.getTime();
		return date;
	}
	
	public static Date addDays(Date date,int days) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, days);
		date = calendar.getTime();
		return date;
	}
	
	public static Date addYears(Date date,int years) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, years);
		date = calendar.getTime();
		return date;
	}
	
	public static Date addMonth(Date date,int months) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, months);
		date = calendar.getTime();
		return date;
	}
	
	/**
	 * 获取一天中开始的时间
	 * @return
	 */
	public static Date getBegin(Date time){
		Calendar calendar = new GregorianCalendar();   
		calendar.setTime(time);
		calendar.set(Calendar.HOUR_OF_DAY, 0);  
		calendar.set(Calendar.MINUTE, 0);  
		calendar.set(Calendar.SECOND, 0);  
		
		return calendar.getTime();
	}
	
	/**
	 * 获取一天中结束的时间
	 * @return
	 */
	public static Date getEnd(Date time){
		Calendar calendar = new GregorianCalendar();   
		calendar.setTime(time);
		calendar.set(Calendar.HOUR_OF_DAY, 23);  
		calendar.set(Calendar.MINUTE, 59);  
		calendar.set(Calendar.SECOND, 59);  
		
		return calendar.getTime();
	}
	
	/**
	 * 获取一月开始的时间
	 * @return
	 */
	public static Date getMonthBegin(Date time){
		Calendar calendar = new GregorianCalendar();   
		calendar.setTime(time);
		calendar.set(Calendar.DAY_OF_MONTH,1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);  
		calendar.set(Calendar.MINUTE, 0);  
		calendar.set(Calendar.SECOND, 0);  
		return calendar.getTime();
	}
	
	/**
	 * 获取一月结束的时间
	 * @return
	 */
	public static Date getMonthEnd(Date time){
		Calendar calendar = new GregorianCalendar();   
		calendar.setTime(time);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE)); 
		calendar.set(Calendar.HOUR_OF_DAY, 23);  
		calendar.set(Calendar.MINUTE, 59);  
		calendar.set(Calendar.SECOND, 59);  
		return calendar.getTime();
	}
	
	/**
	 * 
	 * @param begin	开始时间
	 * @param end	结束时间
	 * @return		0:小于一小时  1：小时级别（大于1h）   2：天级别（大于1天）  3：月级别（大于1月）
	 */
	public static int getTimeType(Date begin,Date end){
		
		long monthtime = 1000l * 60 * 60 * 24 * 30;
		long daytime = 1000l * 60 * 60 * 24;
		long hourtime = 1000l * 60 * 60;
		
		long time = end.getTime() - begin.getTime();
		if(time >= monthtime){
			return 3;
		}else if(time >= daytime){
			return 2;
		}else if(time >= hourtime){
			return 1;
		}
		return 0;
	}
	
	public static List<Date> getUnknowDate(List<Date> existDates,Date begin, Date end) {
		List<Date> list = new ArrayList<Date>();
		int timetype = getTimeType(begin, end);
		
		Calendar beginC = Calendar.getInstance();
		beginC.setTime(begin);
		
		Calendar endC = Calendar.getInstance();
		endC.setTime(end);
		
		int beginIndex = 0;
		int endIndex = 0;
		int field = 0;
		if(timetype == 1) {
			beginIndex = beginC.get(Calendar.HOUR_OF_DAY);
			endIndex = endC.get(Calendar.HOUR_OF_DAY);
			field = Calendar.HOUR_OF_DAY;
		}else if(timetype == 2) {
			beginIndex = beginC.get(Calendar.DAY_OF_MONTH);
			endIndex = endC.get(Calendar.DAY_OF_MONTH);
			field = Calendar.DAY_OF_MONTH;
		}else if(timetype == 3) {
			beginIndex = beginC.get(Calendar.MONTH);
			endIndex = endC.get(Calendar.MONTH);
			field = Calendar.MONTH;
		}

		List<Integer> existIndex = new ArrayList<Integer>();
		for(Date date : existDates) {
			endC.setTime(date);
			int value = endC.get(field);
			existIndex.add(value);
		}
		
		for(int i = beginIndex; i <= endIndex ; i++) {
			if(!existIndex.contains(i)){
				beginC.set(field, i);
				list.add(beginC.getTime());
			}
		}
		return list;
	}
	
	/**
	 * 检测是否是同一年的数据
	 * @param begin
	 * @param end
	 * @return
	 */
	public static boolean checkOneYear(Date begin,Date end) {
		if(begin == null || end == null) {
			throw new IllegalArgumentException("INVALID_PARAM");
		}
		
		Calendar cbegin = Calendar.getInstance();
		cbegin.setTime(begin);
		Calendar cend = Calendar.getInstance();
		cend.setTime(end);
		return cend.get(Calendar.YEAR) == cbegin.get(Calendar.YEAR);
	}

	public static void main(String[] args) {
//		System.out.println(new Date());
//		System.out.println(getBegin(new Date()));
//		System.out.println(getEnd(new Date()));
//		System.out.println(getMonthBegin(new Date()));
//		System.out.println(getMonthEnd(new Date()));
//		System.out.println(addMonth(new Date(), -1));
//		System.out.println(addDays(new Date(), -1));
//		
//		System.out.println("---------");
//		Date now = DateTool.addMonth(new Date(), -1);
//    	Date begin = DateTool.getMonthBegin(now);
//    	Date end = DateTool.getMonthEnd(now);
//    	System.out.println(begin);
//    	System.out.println(end);
//    	System.out.println(DateTool.getMonthEnd(new Date()));
//    	System.out.println(DateTool.addYears(new Date(), 30));
//
//		System.out.println("---------");
//		Date d1 = new Date();
//		Date d2 = addDays(d1, 1);
//		Date d5 = addDays(d1, 4);
//		System.out.println(d5.getTime() - d1.getTime());
//		System.out.println(standardSdf.format(d1));
//		System.out.println(standardSdf.format(d5));
//		List<Date> exist = new ArrayList<Date>();
//		exist.add(d2);
//		System.out.println(getUnknowDate(exist, d1, d5));
		
	}

}
