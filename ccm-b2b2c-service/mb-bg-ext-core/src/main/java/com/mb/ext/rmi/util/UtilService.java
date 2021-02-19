package com.mb.ext.rmi.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.mb.framework.util.log.LogHelper;

public abstract class UtilService {
	
	private final LogHelper LOGGER = LogHelper.getInstance(this.getClass()
			.getName());

	public abstract boolean process(Map<String, Object> data);
	
	
	public Date retrieveDate(String date) {
		String METHOD_NAME = " retrieveDate(String date)";
		LOGGER.info("Entering : " + METHOD_NAME);
		String[] startDateArr = date.split("-");
		Calendar startCal = Calendar.getInstance();
		startCal.set(Calendar.YEAR, Integer.valueOf(startDateArr[2]));
		startCal.set(Calendar.MONTH, Integer.valueOf(startDateArr[1]) - 1);
		startCal.set(Calendar.DATE, Integer.valueOf(startDateArr[0]));
		LOGGER.info("Exiting : " + METHOD_NAME);
		return startCal.getTime();

	}
	
	
	public Date getStartDate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);    
		if(null!=date){
			cal.setTime(date);
		}
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
		
	}
	
	public Date getEndDate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);    
		if(null!=date){
			cal.setTime(date);
		}
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
		
	}
	
	
	public String formatDate(String format,Date date){
		String formattedDate = new SimpleDateFormat(format).format(date);
		return formattedDate;
	}
}
