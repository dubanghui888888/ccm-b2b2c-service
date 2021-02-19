package com.mb.framework.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;

import com.mb.framework.constant.AppConstant;
import com.mb.framework.util.log.LogHelper;

public class DateTimeUtil
{
	private static LogHelper logger = LogHelper.getInstance(DateTimeUtil.class.getName());

	/**
	 * This method is used for getting the number corresponding to Calendar year
	 * 
	 * @param year
	 * @return
	 */
	public static int getYear(Date year)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(year);
		return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * This method is used for getting the number corresponding to Calendar year
	 * 
	 * @param year
	 * @return
	 */
	public static String getYear(String year)
	{

		int yr = 0;
		if (StringUtils.isNotEmpty(year))
		{
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(Long.valueOf(year));
			yr = calendar.get(Calendar.YEAR);
		}
		return String.valueOf(yr);
	}

	/**
	 * @return A number corresponding to Calendar days in the month
	 */
	public static int getDay(Date arg)
	{
		// Set Calendar
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(arg);

		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * This method is used for getting the description corresponding to Calendar
	 * day
	 * 
	 * @param arg
	 * @return
	 */
	public static String getDayDesc(Date arg)
	{
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("dd");
		return sdf.format(arg).toUpperCase();
	}

	/**
	 * @return A number corrosponding to Calendar months
	 */
	public static int getMonth(Date month)
	{
		// Set Calendar
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(month);
		return calendar.get(Calendar.MONTH);
	}

	/**
	 * This method is used for getting short description of month
	 * 
	 * @param month
	 * @return
	 */
	public static String getMonthShortDesc(Date month)
	{
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("MMM");
		return sdf.format(month).toUpperCase();
	}

	/**
	 * This method is used for getting month description
	 * 
	 * @param month
	 * @return
	 */
	public static String getMonthDesc(Date month)
	{
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("MM");
		return sdf.format(month).toUpperCase();
	}

	/**
	 * This method is used for getting short description of month by the number
	 * of month
	 * 
	 * @param mthNum
	 * @return
	 */
	public static String getMonthShortDesc(int mthNum)
	{
		String mthShortDesc;

		switch (mthNum)
		{
		case Calendar.JANUARY:
		{
			mthShortDesc = DateTimeConstants.SHORT_JANUARY;
			break;
		}
		case Calendar.FEBRUARY:
		{
			mthShortDesc = DateTimeConstants.SHORT_FEBRUARY;
			break;
		}
		case Calendar.MARCH:
		{
			mthShortDesc = DateTimeConstants.SHORT_MARCH;
			break;
		}
		case Calendar.APRIL:
		{
			mthShortDesc = DateTimeConstants.SHORT_APRIL;
			break;
		}
		case Calendar.MAY:
		{
			mthShortDesc = DateTimeConstants.SHORT_MAY;
			break;
		}
		case Calendar.JUNE:
		{
			mthShortDesc = DateTimeConstants.SHORT_JUNE;
			break;
		}
		case Calendar.JULY:
		{
			mthShortDesc = DateTimeConstants.SHORT_JULY;
			break;
		}
		case Calendar.AUGUST:
		{
			mthShortDesc = DateTimeConstants.SHORT_AUGUST;
			break;
		}
		case Calendar.SEPTEMBER:
		{
			mthShortDesc = DateTimeConstants.SHORT_SEPTEMBER;
			break;
		}
		case Calendar.OCTOBER:
		{
			mthShortDesc = DateTimeConstants.SHORT_OCTOBER;
			break;
		}
		case Calendar.NOVEMBER:
		{
			mthShortDesc = DateTimeConstants.SHORT_NOVEMBER;
			break;
		}
		case Calendar.DECEMBER:
		{
			mthShortDesc = DateTimeConstants.SHORT_DECEMBER;
			break;
		}
		default:
		{
			mthShortDesc = "";
			break;
		}
		}

		return mthShortDesc;
	}

	/**
	 * This method is used for getting day of week short description
	 * 
	 * @param dayOfWeek
	 * @return
	 */
	public static String getDayOfWeekShortDesc(int dayOfWeek)
	{
		String dayOfWeekShortDesc;

		switch (dayOfWeek)
		{
		case Calendar.SUNDAY:
		{
			dayOfWeekShortDesc = DateTimeConstants.SHORT_SUNDAY;
			break;
		}
		case Calendar.MONDAY:
		{
			dayOfWeekShortDesc = DateTimeConstants.SHORT_MONDAY;
			break;
		}
		case Calendar.TUESDAY:
		{
			dayOfWeekShortDesc = DateTimeConstants.SHORT_TUESDAY;
			break;
		}
		case Calendar.WEDNESDAY:
		{
			dayOfWeekShortDesc = DateTimeConstants.SHORT_WEDNESDAY;
			break;
		}
		case Calendar.THURSDAY:
		{
			dayOfWeekShortDesc = DateTimeConstants.SHORT_THURSDAY;
			break;
		}
		case Calendar.FRIDAY:
		{
			dayOfWeekShortDesc = DateTimeConstants.SHORT_FRIDAY;
			break;
		}
		case Calendar.SATURDAY:
		{
			dayOfWeekShortDesc = DateTimeConstants.SHORT_SATURDAY;
			break;
		}
		default:
		{
			dayOfWeekShortDesc = "";
			break;
		}
		}
		return dayOfWeekShortDesc;
	}

	/**
	 * This method is used for getting month long description
	 * 
	 * @param mthNum
	 * @return
	 */
	public static String getMonthLongDesc(int mthNum)
	{
		String mthLongDesc;

		switch (mthNum)
		{
		case Calendar.JANUARY:
		{
			mthLongDesc = DateTimeConstants.JANUARY;
			break;
		}
		case Calendar.FEBRUARY:
		{
			mthLongDesc = DateTimeConstants.FEBRUARY;
			break;
		}
		case Calendar.MARCH:
		{
			mthLongDesc = DateTimeConstants.MARCH;
			break;
		}
		case Calendar.APRIL:
		{
			mthLongDesc = DateTimeConstants.APRIL;
			break;
		}
		case Calendar.MAY:
		{
			mthLongDesc = DateTimeConstants.MAY;
			break;
		}
		case Calendar.JUNE:
		{
			mthLongDesc = DateTimeConstants.JUNE;
			break;
		}
		case Calendar.JULY:
		{
			mthLongDesc = DateTimeConstants.JULY;
			break;
		}
		case Calendar.AUGUST:
		{
			mthLongDesc = DateTimeConstants.AUGUST;
			break;
		}
		case Calendar.SEPTEMBER:
		{
			mthLongDesc = DateTimeConstants.SEPTEMBER;
			break;
		}
		case Calendar.OCTOBER:
		{
			mthLongDesc = DateTimeConstants.OCTOBER;
			break;
		}
		case Calendar.NOVEMBER:
		{
			mthLongDesc = DateTimeConstants.NOVEMBER;
			break;
		}
		case Calendar.DECEMBER:
		{
			mthLongDesc = DateTimeConstants.DECEMBER;
			break;
		}
		default:
		{
			mthLongDesc = "";
			break;
		}
		}

		return mthLongDesc;
	}

	/**
	 * This method is used for getting calendar number of month by month
	 * description
	 * 
	 * @param monthDescr
	 * @return
	 */
	public static int getMonthNum(String monthDescr)
	{
		int numOfMonth = -1;
		if (DateTimeConstants.JANUARY.equals(monthDescr))
		{
			numOfMonth = Calendar.JANUARY;
		}
		if (DateTimeConstants.FEBRUARY.equals(monthDescr))
		{
			numOfMonth = Calendar.FEBRUARY;
		}
		if (DateTimeConstants.MARCH.equals(monthDescr))
		{
			numOfMonth = Calendar.MARCH;
		}
		if (DateTimeConstants.APRIL.equals(monthDescr))
		{
			numOfMonth = Calendar.APRIL;
		}
		if (DateTimeConstants.MAY.equals(monthDescr))
		{
			numOfMonth = Calendar.MAY;
		}
		if (DateTimeConstants.JUNE.equals(monthDescr))
		{
			numOfMonth = Calendar.JUNE;
		}
		if (DateTimeConstants.JULY.equals(monthDescr))
		{
			numOfMonth = Calendar.JULY;
		}
		if (DateTimeConstants.AUGUST.equals(monthDescr))
		{
			numOfMonth = Calendar.AUGUST;
		}
		if (DateTimeConstants.SEPTEMBER.equals(monthDescr))
		{
			numOfMonth = Calendar.SEPTEMBER;
		}
		if (DateTimeConstants.OCTOBER.equals(monthDescr))
		{
			numOfMonth = Calendar.OCTOBER;
		}
		if (DateTimeConstants.NOVEMBER.equals(monthDescr))
		{
			numOfMonth = Calendar.NOVEMBER;
		}
		if (DateTimeConstants.DECEMBER.equals(monthDescr))
		{
			numOfMonth = Calendar.DECEMBER;
		}
		return numOfMonth;
	}

	/**
	 * This method is used for getting month long description
	 * 
	 * @param month
	 * @return
	 */
	public static String getMonthLongDesc(Date month)
	{
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("MMMMM");
		return sdf.format(month);
	}

	/**
	 * This method is used for getting year short description
	 * 
	 * @param year
	 * @return
	 */
	public static String getYearShortDesc(Date year)
	{
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yy");
		return sdf.format(year);
	}

	/**
	 * This method is used for getting year long description
	 * 
	 * @param year
	 * @return
	 */
	public static String getYearLongDesc(Date year)
	{
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy");
		return sdf.format(year);
	}

	/**
	 * This method is used for formatting date to ddMMyyyy format
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateToDDMMYYYY(Date date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("ddMMyyyy");
		return sdf.format(date);
	}

	/**
	 * This method is used for formatting date to dd/MM/yyyy
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateToDDMMYYYYWithSlash(Date date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("dd/MM/yyyy");
		return sdf.format(date);
	}

	/**
	 * This method is used for
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateToYYYYMMDD(Date date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyyMMdd");
		return sdf.format(date);
	}
	
	public static String formatDateToYYYYHMMHDD(Date date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy-MM-dd");
		return sdf.format(date);
	}

	/**
	 * This method is used for formatting date to yyyyMMddHHmmss
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateByYYYYMMDDHHmmss(Date date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyyMMddHHmmss");
		return sdf.format(date);
	}
	
	/**
	 * This method is used for formatting date to yyMMddHHmmss
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateByYYMMDDHHmmss(Date date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyMMddHHmmss");
		return sdf.format(date);
	}
	
	/**
	 * This method is used for formatting date to yyMMddHHmmssSSS
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateByYYMMDDHHmmssSSS(Date date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyMMddHHmmssSSS");
		return sdf.format(date);
	}
	
	/**
	 * This method is used for formatting date to yyMMddHHmm
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateByYYMMDDHHmm(Date date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyMMddHHmm");
		return sdf.format(date);
	}

	/**
	 * This method is used for getting the first day of the month
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getFirstDayOfMonth(Date date) throws ParseException
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, 1);
		return getDateWithoutTime(calendar.getTime());
	}

	/**
	 * This method is used for getting the last day of the month
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DATE, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}

	/**
	 * Returns the number of days difference
	 */
	public static long calculateNoOfDaysInBetween(Date start, Date end)
	{
		// String errorMessage = null;
		long startTime = 0L;
		long endTime = 0L;
		long diffTime = 0L;

		if (start != null && end != null)
		{
			// Start Calculation
			startTime = start.getTime();
			endTime = end.getTime();
			diffTime = (endTime - startTime) / DateTimeConstants.TIME_MS_DAY;
		}

		// if(start.compareTo(end) < 1) {
		// // Start Calculation
		// startTime = start.getTime();
		// endTime = end.getTime();
		// diffTime = (endTime - startTime) / TIME_MS_DAY;
		// } else
		// errorMessage = "Start Date cannot be after End Date";
		// } else
		// errorMessage = "Arguments cannot be null";

		// if(errorMessage != null) {
		// throw new IllegalArgumentException(errorMessage);
		// }

		return diffTime;
	}

	/**
	 * Returns the number of hour difference
	 */
	public static long calculateNoOfHoursInBetween(Date start, Date end)
	{
		long startTime = 0L;
		long endTime = 0L;
		long diffTime = 0L;
		if (start != null && end != null)
		{
			startTime = start.getTime();
			endTime = end.getTime();
			diffTime = (endTime - startTime) / DateTimeConstants.TIME_MS_HR;
		}
		return diffTime;
	}

	/**
	 * This method is used for calculating mins between start data and end date
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static long calculateNoOfMinsInBetween(Date start, Date end)
	{
		long startTime = 0L;
		long endTime = 0L;
		long diffTime = 0L;
		if (start != null && end != null)
		{
			startTime = start.getTime();
			endTime = end.getTime();
			diffTime = (endTime - startTime) / DateTimeConstants.TIME_MS_MIN;
		}
		return diffTime;
	}

	/**
	 * This method is used for converting format dd-MM-yyyy to Calendar
	 * 
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public static Calendar convertStringToCalendar(String strDate) throws ParseException
	{
		Calendar calReturn = Calendar.getInstance();
		calReturn.setTime(convertStringToDate(strDate));
		return calReturn;
	}

	/**
	 * This method is used for converting format dd-MM-yyyy to date
	 * 
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String strDate) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(DateTimeConstants.DATE_PATTERN_LONG_YEAR);
		return sdf.parse(strDate);
	}

	/**
	 * This method is used for converting string into date with specific pattern
	 * 
	 * @param strDate
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String strDate, String pattern) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(pattern);
		return sdf.parse(strDate);
	}
	
	/**
	 * This method is used for converting format dd-MM-yyyy to date
	 * 
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public static Timestamp convertStringToTimestamp(String strDate) throws ParseException
	{
		Calendar c = GregorianCalendar.getInstance();
		c.setTimeInMillis(Long.valueOf(strDate));
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(DateTimeConstants.TIMESTAMP_FORMAT);
		String ts = sdf.format(c.getTime());
		
		return Timestamp.valueOf(ts);
	}

	/**
	 * This method is used for converting to date by month and year
	 * 
	 * @param strMth
	 * @param strYear
	 * @return
	 * @throws ParseException
	 */
	public static Date convertMthYearStringToDate(String strMth, String strYear) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat(DateTimeConstants.DATE_PATTERN_SHORT_MTH_YEAR);
		String mthStrShortDesc = getMonthShortDesc(Integer.parseInt(strMth));

		return sdf.parse(mthStrShortDesc + "-" + strYear);
	}

	/**
	 * This method is used for converting to date by month and year
	 * 
	 * @param mm
	 * @param yyyy
	 * @param lastday
	 * @return
	 * @throws ParseException
	 */
	public static Date convertMMYYYToDate(String mm, String yyyy, boolean lastday) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat(DateTimeConstants.DATE_PATTERN_LONG_YEAR_SMALL_MTH);
		Date d = sdf.parse(yyyy + mm);
		if (lastday)
		{
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			c.add(Calendar.MONTH, 1);
			c.add(Calendar.SECOND, -1);
			d = c.getTime();
		}
		return d;
	}

	/**
	 * This method is used for converting to date by month and year
	 * 
	 * @param mm
	 * @param yyyy
	 * @return
	 * @throws ParseException
	 */
	public static Date convertMMYYYToDate(int mm, int yyyy) throws ParseException
	{
		Date d = null;
		Calendar c = Calendar.getInstance();
		c.set(yyyy, mm - 1, 1);
		d = c.getTime();
		d = getDateWithoutTime(d);
		return d;
	}

	/**
	 * This method is used for converting date to string with format dd-MM-yyyy
	 * 
	 * @param dateArg
	 * @return
	 */
	public static String convertDateToString(Date dateArg)
	{
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(DateTimeConstants.DATE_PATTERN_LONG_YEAR);
		return sdf.format(dateArg);
	}

	/**
	 * This method is used for date to time stamp with format dd-MM-yyyy
	 * hh:mm:ss aaa
	 * 
	 * @param dateArg
	 * @return
	 */
	public static String convertTimestampToString(Date dateArg)
	{
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(DateTimeConstants.TIMESTAMP_PATTERN_LONG);
		return sdf.format(dateArg);
	}

	/**
	 * This method is used for converting date to string with specific pattern
	 * 
	 * @param dateArg
	 * @param pattern
	 * @return
	 */
	public static String convertDateToString(Date dateArg, String pattern)
	{
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(pattern);
		return sdf.format(dateArg);
	}

	/**
	 * This method is used for getting current time stamp
	 * 
	 * @return
	 */
	public static long getCurrentTimeStamp()
	{
		return Calendar.getInstance().getTimeInMillis();
	}

	/**
	 * This method is used for converting date to time stamp in long
	 * 
	 * @param date
	 * @return
	 */
	public static long convertDateToTimeStamp(Date date)
	{
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar.getTimeInMillis();
	}

	public static long convertDateToTime(Date date)
	{
		Calendar calendar = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try{
		String formattedDT = sdf.format(date);
		
		calendar.setTime(sdf.parse(formattedDT));
		}catch(ParseException e){
			logger.error("Error occurred in parsing date",e);
		}
		return calendar.getTimeInMillis();
	}

	/**
	 * This method is used for getting current date
	 * 
	 * @return
	 */
	public static Date getCurrentDate()
	{
		return Calendar.getInstance().getTime();
	}

	/**
	 * This method is used for get date without time
	 * 
	 * @param myDate
	 * @return
	 * @throws ParseException
	 */
	public static Date getDateWithoutTime(Date myDate) throws ParseException
	{
		Date today = null;

		String strDate = DateTimeUtil.convertDateToString(myDate);
		today = DateTimeUtil.convertStringToDate(strDate);

		return today;
	}

	/**
	 * This method is used for checking whether the target date is weekend
	 * 
	 * @param tgtDate
	 * @return
	 */
	public static boolean isWeekEnd(Date tgtDate)
	{
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(tgtDate);

		return (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ? true : false);
	}

	/**
	 * This method is used for checking whether the target date is the end of
	 * month
	 * 
	 * @param tgtDate
	 * @return
	 */
	public static boolean isMonthEnd(Date tgtDate)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(tgtDate);
		int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
		int monthEnd = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		return (currentDay == monthEnd);
	}

	/**
	 * This method is used for checking whether the date is the previous month
	 * 
	 * @param prevMonthDate
	 * @param today
	 * @return
	 * @throws ParseException
	 */
	public static boolean isPreviousMonth(Date prevMonthDate, Date today) throws ParseException
	{
		Date tempPrevMonthDate = DateTimeUtil.getFirstDayOfMonth(prevMonthDate);
		Date tempDate = DateTimeUtil.addMonthsToDate(today, -1);
		tempDate = DateTimeUtil.getFirstDayOfMonth(tempDate);
		return tempPrevMonthDate.equals(tempDate);
	}

	/**
	 * This method is used for checking whether the target date is in current
	 * month
	 * 
	 * @param tgtDate
	 * @param today
	 * @return
	 */
	public static boolean isCurrentMonth(Date tgtDate, Date today)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(tgtDate);
		int currMonth = calendar.get(Calendar.MONTH);
		calendar.setTime(today);
		return (calendar.get(Calendar.MONTH) == currMonth);
	}

	/**
	 * This method is used for checking whether the target date is Saturday
	 * 
	 * @param tgtDate
	 * @return
	 */
	public static boolean isSaturday(Date tgtDate)
	{
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(tgtDate);

		return (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ? true : false);
	}

	/**
	 * This method is used for checking whether the target date is Sunday
	 * 
	 * @param tgtDate
	 * @return
	 */
	public static boolean isSunday(Date tgtDate)
	{
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(tgtDate);

		return (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ? true : false);
	}

	/**
	 * This method is used for getting the day of week
	 * 
	 * @param dateArg
	 * @return
	 */
	public static int getDayOfWeek(Date dateArg)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateArg);

		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * This method is used for adding days into target date
	 * 
	 * @param mydate
	 * @param days
	 * @return
	 */
	public static Date addDaysToDate(Date mydate, int days)
	{
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(mydate);
		calendar.add(Calendar.DATE, days);
		return calendar.getTime();
	}

	/**
	 * This method is used for adding month into target date
	 * 
	 * @param mydate
	 * @param months
	 * @return
	 */
	public static Date addMonthsToDate(Date mydate, int months)
	{
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(mydate);
		calendar.add(Calendar.MONTH, months);
		Date datePlusMonths = calendar.getTime();
		return datePlusMonths;
	}

	/**
	 * This method is used for adding minutes into target date
	 * 
	 * @param mydate
	 * @param min
	 * @return
	 */
	public static Date addMinToDate(Date mydate, int min)
	{
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(mydate);
		calendar.add(Calendar.MINUTE, min);
		Date datePlusMin = calendar.getTime();
		return datePlusMin;
	}

	/**
	 * This method is used for adding years into target date
	 * 
	 * @param curDate
	 * @param years
	 * @return
	 */
	public static Date addYearToDate(Date curDate, int years)
	{
		Calendar calStart = new GregorianCalendar();
		calStart.setTime(curDate);
		calStart.add(GregorianCalendar.YEAR, years);

		return calStart.getTime();
	}

	/**
	 * This method is used for getting
	 * 
	 * @param date
	 * @return
	 */
	public static String getTimeOfDate(Date date)
	{
		Calendar calStart = new GregorianCalendar();
		calStart.setTime(date);
		String hour = String.valueOf(calStart.get(Calendar.HOUR_OF_DAY));
		String zero = "0";
		if (hour.length() < 2)
		{
			hour = zero.concat(hour);
		}
		String min = String.valueOf(calStart.get(Calendar.MINUTE));
		if (min.length() < 2)
		{
			min = zero.concat(min);
		}
		String sec = String.valueOf(calStart.get(Calendar.SECOND));
		if (sec.length() < 2)
		{
			sec = zero.concat(sec);
		}

		return hour.concat(min.concat(sec));
	}

	/**
	 * This method is used for getting time of date with colon
	 * 
	 * @param date
	 * @return
	 */
	public static String getTimeOfDateWithColon(Date date)
	{
		Calendar calStart = new GregorianCalendar();
		calStart.setTime(date);
		String hour = String.valueOf(calStart.get(Calendar.HOUR_OF_DAY));
		String zero = "0";
		if (hour.length() < 2)
		{
			hour = zero.concat(hour);
		}
		String min = String.valueOf(calStart.get(Calendar.MINUTE));
		if (min.length() < 2)
		{
			min = zero.concat(min);
		}
		String sec = String.valueOf(calStart.get(Calendar.SECOND));
		if (sec.length() < 2)
		{
			sec = zero.concat(sec);
		}

		return hour.concat(":").concat(min.concat(":").concat(sec));

	}

	/**
	 * This method is used for getting hour and minutes of the date with colon
	 * 
	 * @param date
	 * @return
	 */
	public static String getHrMiOfDateWithColon(Date date)
	{
		Calendar calStart = new GregorianCalendar();
		calStart.setTime(date);
		String hour = String.valueOf(calStart.get(Calendar.HOUR_OF_DAY));
		String zero = "0";
		if (hour.length() < 2)
		{
			hour = zero.concat(hour);
		}
		String min = String.valueOf(calStart.get(Calendar.MINUTE));
		if (min.length() < 2)
		{
			min = zero.concat(min);
		}
		String sec = String.valueOf(calStart.get(Calendar.SECOND));
		if (sec.length() < 2)
		{
			sec = zero.concat(sec);
		}

		return hour.concat(":").concat(min);
	}

	/**
	 * This method is used for setting date time to 23:59:59
	 * 
	 * @param mydate
	 * @return
	 * @throws ParseException
	 */
	public static Date setDateTimeTo2359(Date mydate) throws ParseException
	{
		Date tempDate = getDateWithoutTime(mydate);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(tempDate);
		calendar.add(Calendar.DATE, 1); // Add one day
		calendar.add(Calendar.MILLISECOND, -1); // Minus 1 millisecond
		return calendar.getTime();
	}

	/**
	 * This method returns number of days between date1 and date2, including
	 * date1 and date2 regardless of the date time. Eg 1 Jan 06 2000hrs to 3 Jan
	 * 06 1900hrs will return as 3 days. This method is used for
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getNumDaysDiffExclTime(Date date1, Date date2)
	{
		int result = 0;
		Calendar cal = new GregorianCalendar();
		cal.setTime(date1);
		while (!cal.getTime().after(date2))
		{
			cal.add(Calendar.DAY_OF_MONTH, 1);
			result++;
		}
		return result;
	}

	/**
	 * This method is used for checking whether the target date is holiday
	 * 
	 * @param calendarDay
	 * @param holidays
	 * @return
	 * @throws ParseException
	 */
	public static boolean isHolidayWeekend(Date calendarDay, List<Date> holidays) throws ParseException
	{
		if (isWeekEnd(calendarDay)) return true;

		boolean isHolidayWeek = false;
		Date tempCalendarDay = getDateWithoutTime(calendarDay);
		if (holidays != null)
		{
			for (Date holiday : holidays)
			{
				if (tempCalendarDay.equals(holiday))
				{
					isHolidayWeek = true;
					break;
				}
			}
		}
		return isHolidayWeek;
	}

	/**
	 * This method is used for calculating how many working day in the target
	 * day's month
	 * 
	 * @param calendarDay
	 * @param holidays
	 * @return
	 * @throws ParseException
	 */
	public static int getWorkDayofMonth(Date calendarDay, List<Date> holidays) throws ParseException
	{
		if (isHolidayWeekend(calendarDay, holidays))
		{
			return -1;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(calendarDay);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		Date temp = getFirstDayOfMonth(calendarDay);

		int workDay = 0;
		for (int i = 1; i <= day; i++)
		{
			if (!isHolidayWeekend(temp, holidays))
			{
				workDay++;
			}
			temp = addDaysToDate(temp, 1);
		}
		if (workDay == 0)
		{
			workDay = -1;
		}

		return workDay;
	}

	/**
	 * This method is used for getting calendar date of month
	 * 
	 * @param calendarDay
	 * @return
	 */
	public static int getCalDayofMonth(Date calendarDay)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(calendarDay);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Method called to get the value for a given date field in the format of
	 * YYYYMMDD.
	 * 
	 * @param datestr
	 *            the date to be retrieved, must be in YYYYMMDD format.
	 * @param what
	 *            type of the date element to be retrieved, i.e. DAY, MONTH,
	 *            YEAR respectively.
	 * @return the value for the given time field.
	 */
	public static int getDateElement(String datestr, String what)
	{
		if (datestr == null || what == null)
		{
			logger.info("\n**********DateTimeUtil.getDateElement()-->Error: Illegal date fields");
		}

		// Processing value (s)
		int ret = -1;
		String tmp = null;

		if (what.equals(DateTimeConstants.YEAR))
		{
			tmp = datestr.substring(0, 4).trim();

			try
			{
				ret = Integer.parseInt(tmp);
			}
			catch (Exception e)
			{
				logger.error("encounting error while get date element.", e);
			}
		}
		else if (what.equals(DateTimeConstants.MONTH))
		{
			tmp = datestr.substring(4, 6).trim();

			try
			{
				ret = Integer.parseInt(tmp);
			}
			catch (Exception e)
			{
				logger.error("encounting error while get date element.", e);
			}
		}
		else if (what.equals(DateTimeConstants.DAY))
		{
			tmp = datestr.substring(6, 8).trim();

			try
			{
				ret = Integer.parseInt(tmp);

			}
			catch (Exception e)
			{
				logger.error("encounting error while get date element.", e);
			}
		}

		return ret;
	}

	/**
	 * Method called to convert a date field of the format of YYYYMMDD to a
	 * GregorianCalendar object.
	 * 
	 * @param datestr
	 *            the date to be converted, must be in YYYYMMDD format.
	 * @return GregorianCalendar object created based on the date field.
	 */
	public static GregorianCalendar convertStringToGregorian(String datestr)
	{
		GregorianCalendar anotherDt = new GregorianCalendar();

		try
		{
			anotherDt.set(Calendar.DAY_OF_MONTH, getDateElement(datestr, DateTimeConstants.DAY));
			anotherDt.set(Calendar.MONTH, DateTimeUtil.getDateElement(datestr, DateTimeConstants.MONTH));
			anotherDt.set(Calendar.YEAR, DateTimeUtil.getDateElement(datestr, DateTimeConstants.YEAR));
		}
		catch (NumberFormatException e)
		{
			logger.error("encounting error while convert string to Gregorian date.", e);
		}

		return anotherDt;
	}

	/**
	 * Method called to convert a GregorianCalendar object to a date field of
	 * the format of YYYYMMDD.
	 * 
	 * @param anotherDt
	 *            GregorianCalendar object to be converted.
	 * @return Date created, must be in YYYYMMDD format.
	 */
	public static String convertGregorianToString(GregorianCalendar anotherDt)
	{
		StringBuffer buffy = new StringBuffer();
		String sDate = null;

		try
		{
			String day = String.valueOf(anotherDt.get(Calendar.DAY_OF_MONTH));
			String month = String.valueOf(anotherDt.get(Calendar.MONTH));
			String year = String.valueOf(anotherDt.get(Calendar.YEAR));
			buffy.append(year);
			if (month.length() == 0)
			{
				month = "00";
			}
			else if (month.length() == 1)
			{
				month = "0" + month;
			}

			buffy.append(month);
			if (day.length() == 0)
			{
				day = "00";
			}
			else if (day.length() == 1)
			{
				day = "0" + month;
			}
			buffy.append(day);
			sDate = buffy.toString();
		}
		catch (NumberFormatException e)
		{
			logger.error("encounting error while convert Gregorian date to string .", e);
		}

		return sDate;
	}

	/**
	 * Method called to check whether the specified date is before the current
	 * date.
	 * 
	 * @param firstDt
	 *            the Calendar to be compared with the specified Calendar
	 * @param secondDt
	 *            the Calendar to be compared with the specified Calendar
	 * @return <code>true</code> if firstDt is before secondDt
	 *         <code>false</code> otherwise
	 */
	public static boolean isBeforeDate(GregorianCalendar firstDt, GregorianCalendar secondDt)
	{
		if (!isEqualDate(firstDt, secondDt))
		{
			return firstDt.before(secondDt);
		}

		return false;
	}

	/**
	 * This method is used for checking whether the first date is before the
	 * second
	 * 
	 * @param firstDt
	 * @param secondDt
	 * @return
	 */
	public static boolean isBeforeDateTime(GregorianCalendar firstDt, GregorianCalendar secondDt)
	{
		return firstDt.before(secondDt);
	}

	/**
	 * Method called to check whether the specified date is the current date.
	 * 
	 * @param firstDt
	 *            the Calendar to be compared with the specified Calendar
	 * @param secondDt
	 *            the Calendar to be compared with the specified Calendar
	 * @return <code>true</code> if firstDt specified is the secondDt
	 *         <code>false</code> otherwise
	 */
	public static boolean isEqualDate(GregorianCalendar firstDt, GregorianCalendar secondDt)
	{
		boolean booEqual = false;
		booEqual = convertGregorianToString(firstDt).equals(convertGregorianToString(secondDt));

		return booEqual;
	}

	/**
	 * Method called to check whether the specified date is after the current
	 * date.
	 * 
	 * @param firstDt
	 *            the Calendar to be compared with the specified Calendar
	 * @param secondDt
	 *            the Calendar to be compared with the specified Calendar
	 * @return <code>true</code> if firstDt is after secondDt <code>false</code>
	 *         otherwise
	 */
	public static boolean isAfterDate(GregorianCalendar firstDt, GregorianCalendar secondDt)
	{
		boolean isAfterDate = false;
		if (!isEqualDate(firstDt, secondDt))
		{
			isAfterDate = firstDt.after(secondDt);
		}

		return isAfterDate;
	}

	/**
	 * This method is used for checking the target date is in AM
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isAM(Date date)
	{
		boolean isAM = false;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (Calendar.AM == calendar.get(Calendar.AM_PM)) isAM = true;

		return isAM;
	}

	/**
	 * This method is used for
	 * 
	 * @param date
	 * @return
	 */
	public static int getHour(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR);
	}

	/**
	 * This method is used for getting number of day of month
	 * 
	 * @param currentMonth
	 * @return
	 */
	public static int getNumOfDaysOfMonth(Date currentMonth)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentMonth);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * This method is used for convert Milli seconds into Date
	 * 
	 * @param milliSeconds
	 * @return Date
	 * @throws ParseException
	 */
	public static Date convertMilliSecIntoDate(String milliSeconds) throws ParseException
	{
		String dateStr = null;
		Date d = null;
		TimeZone.setDefault(TimeZone.getTimeZone(AppConstant.DEFAULT_TIMEZONE));

		if (null != milliSeconds)
		{
			long milliSecondsLong = Long.parseLong(milliSeconds);

			logger.info("milliSecondsLong: " + milliSecondsLong);

			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(milliSecondsLong);

			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// formatter1.setTimeZone(TimeZone.getTimeZone(AppConstant.DEFAULT_TIMEZONE));
			dateStr = formatter1.format(calendar.getTime());

			d = formatter1.parse(dateStr);

			logger.info("date in default time zone: " + d);

		}

		return d;
	}

	/**
	 * This method is used for convert date into another time zone.
	 * 
	 * @param date
	 * @param timeZone
	 * @return Date
	 * @throws ParseException
	 */
	public static Date convertTimeZoneOfDate(Date date, String timeZone) throws ParseException
	{
		String dateStr = null;
		Date d = null;

		if (null != date && null != timeZone)
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
			dateStr = formatter.format(date);

			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			d = formatter1.parse(dateStr);
		}
		return d;
	}

	/**
	 * This method is used for convert Date into Milli seconds
	 * 
	 * @param Date
	 * @return String
	 * @throws ParseException
	 */
	public static String convertDateIntoMilliSec(Date date) throws ParseException
	{
		String milliSec = null;
		TimeZone.setDefault(TimeZone.getTimeZone(AppConstant.DEFAULT_TIMEZONE));

		if (null != date)
		{
			milliSec = String.valueOf(date.getTime());
		}
		return milliSec;
	}

	/**
	 * This method is used for getting the last day of the week
	 * 
	 * @param date
	 * @return date
	 */
	public static Date getLastDayOfWeek(Date date)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DATE, c.getActualMaximum(Calendar.DAY_OF_WEEK));
		return c.getTime();
	}

	/**
	 * 
	 * This method is used for getting the date/time of next 24 hrs .
	 * 
	 * @param date
	 * @return
	 */
	public static Date getNextDayTime(long date)
	{
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date);
		;
		c.add(Calendar.HOUR, 24);
		return c.getTime();
	}

	/**
	 * 
	 * This method is used to calculate Age.
	 * 
	 * @param birthDate
	 * @return int.
	 */
	public static int calculateAge(Date birthDate)
	{
		int age = 0;
		Calendar c = Calendar.getInstance();
		c.setTime(birthDate);

		age = Calendar.getInstance().get(Calendar.YEAR) - c.get(Calendar.YEAR);

		// Also need compare month and day
		if (Calendar.getInstance().get(Calendar.MONTH) < c.get(Calendar.MONTH))
		{
			age--;
		}
		else if (Calendar.getInstance().get(Calendar.MONTH) == c.get(Calendar.MONTH) && Calendar.getInstance().get(Calendar.DAY_OF_MONTH) < c.get(Calendar.DAY_OF_MONTH))
		{
			age--;
		}

		return age;
	}

	/**
	 * This method is used for converting to date to 1st day of year from given
	 * year.
	 * 
	 * @param year
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static long convertFirstDayInMilli(int year) throws ParseException
	{
		long d = 0;
		Calendar c = Calendar.getInstance();
		c.set(year, Calendar.JANUARY, 1, 00, 00, 00);
		d = c.getTimeInMillis();

		return d;
	}

	/**
	 * This method is used for converting to date to last day of year from given
	 * year.
	 * 
	 * @param year
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static long convertLastDayInMilli(int year) throws ParseException
	{
		long d = 0;
		Calendar c = Calendar.getInstance();
		c.set(year, Calendar.DECEMBER, 31, 11, 59, 59);
		d = c.getTimeInMillis();

		return d;
	}

	/**
	 * 
	 * This method is used to get the no of days difference between current day
	 * and
	 * 
	 * @param dt
	 * @return
	 */
	public static boolean isDue(String dt) throws ParseException
	{
		boolean isDue = false;
		Calendar dueDt = Calendar.getInstance();
		dueDt.setTimeInMillis(Long.valueOf(dt));
		Calendar today = Calendar.getInstance();

		if (daysDiff(dueDt.getTime(), today.getTime()) <= 2)
		{
			isDue = true;
		}

		return isDue;
	}

	public static long daysDiff(Date dueDate, Date today)
	{
		Long daysDiff = new Long(0);
		if (dueDate.after(today))
		{
			daysDiff = (dueDate.getTime() - today.getTime()) / (1000 * 60 * 60 * 24);
		}
		return daysDiff.intValue();
	}

	public static boolean isPriority(String dt) throws ParseException
	{
		boolean isDue = false;
		long dtMillis = Long.valueOf(dt);

		Calendar dueDt = Calendar.getInstance();
		dueDt.add(Calendar.DATE, 2);
		dueDt.set(Calendar.HOUR, 0);
		dueDt.set(Calendar.MINUTE, 0);
		dueDt.set(Calendar.SECOND, 0);
		long dueDtMillis = dueDt.getTimeInMillis();
		if (dueDtMillis - dtMillis > 0)
		{
			isDue = true;
		}

		return isDue;
	}

	/**
	 * 
	 * This method is used to check whether the date is before current Date.
	 * 
	 * @param dt
	 * @return
	 */
	public static boolean isDateExpired(String dt)
	{
		boolean isExpired = false;
		Calendar expDt = Calendar.getInstance();
		expDt.setTimeInMillis(Long.valueOf(dt));

		Calendar today = Calendar.getInstance();
		if (expDt.before(today))
		{
			isExpired = true;
		}

		return isExpired;
	}
	
	public static Date getToday(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		String todayTime = sdf.format(new Date());
		Date today = null;
		try {
			today = sdf.parse(todayTime);
		} catch (ParseException e) {
			logger.error("encounting error while parsing date.", e);
		}
		return today;
	}

}