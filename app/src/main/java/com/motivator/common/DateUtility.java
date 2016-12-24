package com.motivator.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtility {
	
	public static final String E_MMM_dd_yyyy = "E MMM dd yyyy";
	
//	public static final String yyyyMMdd = "yyyy-MM-dd";
	
//	public static final String EddMMMyyhhmmA = "E dd MMM, yy hh:mm a";
//	public static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
//	public static final String ddMMMyyyyHHmmA = "dd MMM yyyy, HH:mm a";
//	public static final String yyyyMMdd = "yyyy-MM-dd";
	
	
	/**
	 * get date object fron time string in 12 hr format
	 * @param time
	 * @return
	 */
	public static Date getDateObject(String time, String dateFormat)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
	    Date dateObj = null;
	    try {
	        dateObj = sdf.parse(time);
	    } catch (ParseException e) {
	    }
	    return dateObj;
	}
	
	public static String getTimeIn24Format(String timeIn12Format)
	{
		String time24Format = timeIn12Format;
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
		try {
			time24Format = sdf2.format(sdf.parse(timeIn12Format));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time24Format;				 
	}
	
	public static String getTimeIn12Format(String time24Format)
	{
		String time = time24Format;
		try {
			 final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
			 final Date dateObj = sdf.parse(time);
			 time = new SimpleDateFormat("K:mm a").format(dateObj);
		 } catch (final ParseException e) {
			 e.printStackTrace();
		 }	
		return time;				 
	}
	
	/**
	 * 
	 * @param dateStr("yyyy-MM-dd")
	 * @return
	 */
	public static int getDateFromString(String dateStr) {
		int date = 0;
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date parsedDate = DATE_FORMAT.parse(dateStr);
			date = parsedDate.getDate();

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	
	public static long getDaysDiffFromToday(Date date1, Date date2, String format) 
	{
		long days = 0;
		long diff = date1.getTime() - date2.getTime();
		long seconds = diff / 1000;
		long minutes = seconds / 60;
		long hours = minutes / 60;
		days = hours / 24;
		/*Date today=null, selectedDateObj=null;
		 try {
			 SimpleDateFormat sdf = new SimpleDateFormat(format); 
			 today = sdf.parse(sdf.format(new Date()));
			 selectedDateObj = sdf.parse(dateSelected);
			 
			 long diff = today.getTime() - selectedDateObj.getTime();
			 long seconds = diff / 1000;
			 long minutes = seconds / 60;
			 long hours = minutes / 60;
			 days = hours / 24;
		 } 
		 catch (ParseException e) {
			 e.printStackTrace();
		 }*/
	     	
		return days;
	}
	
	/**
	 * 
	 * @param date
	 * @param reqDateFormat
	 * @return
	 */
	public static String formateDate(Date date, String reqDateFormat)
	{
		String dateString = "";
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(reqDateFormat); //"E, dd MMM yyyy"
			dateString = sdf.format(date);	 			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return dateString;
	}
	
	/**
	 * 
	 * @param dateStr
	 * @param actualFormat (for ex;- "yyyy-MM-dd HH:mm:ss")
	 * @param reqFormat (for ex;- "dd MMM yyyy, HH:mm a")
	 * @return
	 */
	public static String formateDateString(String dateStr, String actualFormat, String reqFormat)
	{
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(actualFormat);//"yyyy-MM-dd HH:mm:ss"
			Date date = sdf.parse(dateStr);
			//E MMM dd yyyy
			sdf = new SimpleDateFormat(reqFormat); //"dd MMM yyyy, HH:mm a"
 			String serviceDate = sdf.format(date);	 			
 			return serviceDate;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}
	/**
	 * 
	 * @param dateSelected
	 * @param format
	 * @return
	 */
	public static int compareDateWithToday(String dateSelected, String format)
	{
		Date today=null, selectedDateObj=null;
		 try {
			 SimpleDateFormat sdf = new SimpleDateFormat(format); 
			 today = sdf.parse(sdf.format(new Date()));
			 selectedDateObj = sdf.parse(dateSelected);
		 } 
		 catch (ParseException e) {
			 e.printStackTrace();
		 }
	     	
		 int result = today.compareTo(selectedDateObj);
		 return result;
	}
	
	/**
	 * compare date1 with date2
	 * result < 0 if this date1 is less than the specified date2,
	 *  0 if they are equal,
	 * and result > 0 if this date1 is greater than date2.
	 * @param dateString1
	 * @param dateString2
	 * @param format
	 * @return
	 */
	public static int compareTwoDate(String dateString1, String dateString2,String format)
	{
		Date date1=null, date2 = null;
		 try {
			 SimpleDateFormat sdf = new SimpleDateFormat(format); 
			 date1 = sdf.parse(dateString1);
			 date2 = sdf.parse(dateString2);
		 } 
		 catch (ParseException e) {
			 e.printStackTrace();
		 }
	     	
		 int result = date1.compareTo(date2);
		 return result;
	}

	public static String getPreviousDateString(int day, String dateFormat)
	{
		String prevoiusDate = "";
		try {
			 SimpleDateFormat sdf = new SimpleDateFormat(dateFormat); 
			 Calendar calendar = Calendar.getInstance();
			 calendar.add(Calendar.DATE , day);
			 prevoiusDate =  sdf.format(calendar.getTime());
		 } 
		 catch (Exception e) {
			 e.printStackTrace();
		 }
		 return prevoiusDate;		
	}
	
	public static Date getPreviousDate(int day, String dateFormat)
	{
		Date prevoiusDate = null;
		try {
			 SimpleDateFormat sdf = new SimpleDateFormat(dateFormat); 
			 Calendar calendar = Calendar.getInstance();
			 calendar.add(Calendar.DATE , day);
			 prevoiusDate =  sdf.parse(sdf.format(calendar.getTime()));
		 } 
		 catch (Exception e) {
			 e.printStackTrace();
		 }
		 return prevoiusDate;		
	}
	
	public static String getDayOfweek(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
	    int dayofWeek = cal.get(Calendar.DAY_OF_WEEK);
	    
		switch (dayofWeek) {
		case Calendar.SUNDAY:
			return "S";
		case Calendar.MONDAY:
			return "M";
		case Calendar.TUESDAY:
			return "T";
		case Calendar.WEDNESDAY:
			return "W";
		case Calendar.THURSDAY:
			return "T";
		case Calendar.FRIDAY:
			return "F";
		case Calendar.SATURDAY:
			return "S";
			
		default:
			return "";
		}
	}
	
	
	public static String getPreviousDayOfweek(int day)
	{
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE , day);
	    int dayofWeek = cal.get(Calendar.DAY_OF_WEEK);
	    
		switch (dayofWeek) {
		case Calendar.SUNDAY:
			return "S";
		case Calendar.MONDAY:
			return "M";
		case Calendar.TUESDAY:
			return "T";
		case Calendar.WEDNESDAY:
			return "W";
		case Calendar.THURSDAY:
			return "T";
		case Calendar.FRIDAY:
			return "F";
		case Calendar.SATURDAY:
			return "S";
			
		default:
			return "";
		}
	}
	
}
