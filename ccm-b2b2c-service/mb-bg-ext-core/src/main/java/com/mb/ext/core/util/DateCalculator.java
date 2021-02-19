package com.mb.ext.core.util;

import java.util.Calendar;
import java.util.Date;

public class DateCalculator {

	public static  int GetWeekendDays(int year, int month) {

		// 排除周末
		int count = 0;

		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR, year);

		cal.set(Calendar.MONTH, month - 1);

		cal.set(Calendar.DATE, 1);
		//单独处理12月
		if(month==12){
			while (cal.get(Calendar.MONTH) != 0) {

				int day = cal.get(Calendar.DAY_OF_WEEK);

				if (day == Calendar.SUNDAY || day == Calendar.SATURDAY) {

					count++;

				}

				cal.add(Calendar.DATE, 1);

			}
		}
		//1 - 11月
		else{
			while (cal.get(Calendar.MONTH) < month) {
	
				int day = cal.get(Calendar.DAY_OF_WEEK);
	
				if (day == Calendar.SUNDAY || day == Calendar.SATURDAY) {
	
					count++;
	
				}
	
				cal.add(Calendar.DATE, 1);
	
			}
		}
		// 排除节假日
		return count;
	}

	public static synchronized int GetTotalDays(int year, int month) {
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR, year);

		cal.set(Calendar.MONTH, month - 1);

		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	//获取某月第一天
	public static synchronized Date GetFirstDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR, year);

		cal.set(Calendar.MONTH, month - 1);
 
		int days = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
		
		cal.set(Calendar.DAY_OF_MONTH, days);
		
		return cal.getTime();
	}
	//获取某季度第一天
	public static synchronized Date getFirstDayOfQuarter(int year, int quarter) {
		if(quarter == 1){
			return GetFirstDayOfMonth(year, 1);
		}
		else if(quarter == 2){
			return GetFirstDayOfMonth(year, 4);
		}
		else if(quarter == 3){
			return GetFirstDayOfMonth(year, 7);
		}
		else if(quarter == 4){
			return GetFirstDayOfMonth(year, 10);
		}
		return null;
	}
	//获取某月最后一天
	public static synchronized Date GetLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR, year);

		cal.set(Calendar.MONTH, month - 1);
 
		int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		cal.set(Calendar.DAY_OF_MONTH, days);
		
		return cal.getTime();
	}
	//获取某季度最后一天
	public static synchronized Date getLastDayOfQuarter(int year, int quarter) {
		if(quarter == 1){
			return GetLastDayOfMonth(year, 3);
		}
		else if(quarter == 2){
			return GetLastDayOfMonth(year, 6);
		}
		else if(quarter == 3){
			return GetLastDayOfMonth(year, 9);
		}
		else if(quarter == 4){
			return GetLastDayOfMonth(year, 12);
		}
		return null;
	}
	
	//获取几个月前,后的第一天
	public static synchronized Date getFirstDayOfMonthDiff(int diff){
		 Calendar calendar = Calendar.getInstance();
		 calendar.add(Calendar.MONTH, diff);
		 calendar.set(Calendar.DAY_OF_MONTH,1);
	     return calendar.getTime();
	}
	//获取几个月前,后的最后一天
	public static synchronized Date getLastDayOfMonthDiff(int diff){
		 Calendar calendar = Calendar.getInstance();
		 calendar.add(Calendar.MONTH, diff+1);
		 calendar.set(Calendar.DAY_OF_MONTH,0);
	     return calendar.getTime();
	}
	
	//获取某年第一天
	public static synchronized Date getFirstDayOfYear(int year){
		 Calendar calendar = Calendar.getInstance();
		 calendar.set(Calendar.YEAR, year);
		 calendar.set(Calendar.MONTH, 0);
		 calendar.set(Calendar.DAY_OF_MONTH,1);
	     return calendar.getTime();
	}
	
	//获取某年最后一天
	public static synchronized Date getLastDayOfYear(int year){
		 Calendar calendar = Calendar.getInstance();
		 calendar.set(Calendar.YEAR, year);
		 calendar.set(Calendar.MONTH, 11);
		 calendar.set(Calendar.DAY_OF_MONTH,31);
	     return calendar.getTime();
	}
	//计算两个日期相差天数
	public static synchronized int daysBetween(Date date1,Date date2)  
    {  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date1);  
        long time1 = cal.getTimeInMillis();               
        cal.setTime(date2);  
        long time2 = cal.getTimeInMillis();       
        long between_days=(time2-time1)/(1000*3600*24);  
          
       return Integer.parseInt(String.valueOf(between_days));         
    }

	public static void main(String[] args) {
		System.out.println(getFirstDayOfMonthDiff(-1));
		System.out.println(getLastDayOfMonthDiff(-1));
		System.out.println(getFirstDayOfYear(2017));
		System.out.print(getLastDayOfYear(2017));
	}

}
