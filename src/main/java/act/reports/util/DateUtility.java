package act.reports.util;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

/* 
 * Converting Timestamp into simple date format("dd-MM-yyyy HH:mm:ss")
 */
public class DateUtility {
	
	protected static Logger logger = Logger.getLogger(DateUtility.class);
	
	private static final String MYSQL_DATE_FORMAT="yyyy-MM-dd";
	private static final String MYSQL_DATETIME = "yyyy-MM-dd HH:mm:ss";
	//private static final String DATE_FORMAT="MM-dd-yyyy";
	private static final String DATETIME_FORMAT = "MM-dd-yyyy HH:mm:ss";
	
	public static Timestamp convertDateTime(String dateStr)
	{
		SimpleDateFormat format=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		java.util.Date dt=new java.util.Date();
		Timestamp mysqlTS=null;		
		try {
			dt=format.parse(dateStr);
			mysqlTS=new Timestamp(dt.getTime());
			
		} catch (ParseException e) {			
			logger.error(e);
			e.printStackTrace();
		}
		return mysqlTS;		
	}
	
	public static String convertAsMySqlDateTime(String dateStr)
	{		
		//logger.info(dateStr);
		Date convertedDate = null;
		String convertedDateStr = "";
		SimpleDateFormat sdf = new SimpleDateFormat(MYSQL_DATE_FORMAT);
		SimpleDateFormat sdf1 = new SimpleDateFormat(MYSQL_DATETIME);		
		try{
			dateStr = convertAsMySqlDateString(dateStr);
			convertedDate = sdf.parse(dateStr);
			convertedDateStr = sdf1.format(convertedDate);
		} catch (ParseException e) {
			logger.error(e);
			e.printStackTrace();
		}
		//logger.info(convertedDateStr);
		return convertedDateStr;		
	}
	
	public static String convertToDateTimeFormat(String dateStr)
	{		
		Date parsedDate = null;
		String convertedDateStr = "";
		SimpleDateFormat sdf = new SimpleDateFormat(MYSQL_DATETIME);
		SimpleDateFormat sdf1 = new SimpleDateFormat(DATETIME_FORMAT);		
		try{
			parsedDate = sdf.parse(dateStr);
			convertedDateStr = sdf1.format(parsedDate);
		//System.out.println("convertedDateStr = "+convertedDateStr);
		} catch (ParseException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return convertedDateStr;		
	}
	
	public static String getOneDayBeforeDate(String dateStr)
	{		
		SimpleDateFormat sdf = new SimpleDateFormat(MYSQL_DATE_FORMAT);
		Calendar cal = Calendar.getInstance();
		String oneDayBeforeDateStr = "";
		try{
			Date parsedDate = sdf.parse(dateStr);
			cal.setTime(parsedDate);
			cal.add(Calendar.DAY_OF_YEAR,-1);
			Date oneDayBeforeDate= cal.getTime();
			oneDayBeforeDateStr = sdf.format(oneDayBeforeDate);						
		} 
		catch (ParseException e){
			logger.error(e);
			e.printStackTrace();
		}
		return oneDayBeforeDateStr;		
	}
	
	public static int getDaysBetweenTwoDates(String d1, String d2) 
	{
		Calendar cal1 = new GregorianCalendar();
	    Calendar cal2 = new GregorianCalendar();
	    int diffDays = 0;
	    try{
	    	cal1.set(Integer.parseInt(d1.substring(0,4)), Integer.parseInt(d1.substring(5,7)), Integer.parseInt(d1.substring(8))); 
	       	cal2.set(Integer.parseInt(d2.substring(0,4)), Integer.parseInt(d2.substring(5,7)), Integer.parseInt(d2.substring(8)));
	       	Date date1 = cal1.getTime();
			Date date2 = cal2.getTime();
			diffDays = (int)( (date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24));
	    }
	    catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return diffDays;
	}
	
	public static Timestamp currentTimeStamp()
	{
		
		java.util.Date dt=new java.util.Date();
		Timestamp mysqlTS=null;
		mysqlTS=new Timestamp(dt.getTime());
		
		return mysqlTS;
		
	}
	
	public static String convertAsMySqlDateString(String dateString)
	{
		
		SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy");
		java.util.Date dt=new java.util.Date();
		
		try {
				dt=format.parse(dateString);
				dateString=getDisplayDate(new Date(dt.getTime()),MYSQL_DATE_FORMAT);				
		    }
		catch (ParseException e) {
			logger.error(e);
		}
		return dateString;

	}
	
	public static boolean isFormattedDate(String dateString, String dateFormat)
	{
		boolean status=false;
		if(isInDateFormat(dateString,"dd/MM/yyyy"))
		     status=true;
		else if(isInDateFormat(dateString,"dd/M/yyyy"))
		     status=true;
		else if(isInDateFormat(dateString,"d/MM/yyyy"))
		     status=true;
		
		return status;
	}
	
	private static String getDateFormat(String dateString)
	{
		String format="";
		if(isInDateFormat(dateString,"MM/dd/yyyy"))
			format="MM/dd/yyyy";
		if(isInDateFormat(dateString,"dd/MM/yyyy"))
			format="dd/MM/yyyy";
		else if(isInDateFormat(dateString,"dd/M/yyyy"))
			format="dd/M/yyyy";
		else if(isInDateFormat(dateString,"d/MM/yyyy"))
			format="d/MM/yyyy";
		else if(isInDateFormat(dateString,"dd-MM-yyyy"))
			format="dd-MM-yyyy";
		return format;
	}
	
	private static boolean isInDateFormat(String dateString,String dateFormat)
	{
		SimpleDateFormat format=new SimpleDateFormat(dateFormat);
		java.util.Date dt=new java.util.Date();
		boolean status=false;
		try 
		{
				dt=format.parse(dateString);
				status=true;
		}
		catch (ParseException e)
		{
			logger.error(e);
		}
		return status;
	}
	public static String getCurrentMysqlDateTime()
	{
		java.util.Date dt=new java.util.Date();
		DateFormat dateFormat = new SimpleDateFormat(MYSQL_DATETIME);
		return dateFormat.format(dt);
	}
	
	public static String getCurrentDate(String format)
	{
		SimpleDateFormat dateformat=new SimpleDateFormat(format);
		java.util.Date dt=new java.util.Date();
		return dateformat.format(dt);
	}
	public static String getDisplayDate(java.util.Date date, String format)
	{
		if(date!=null)
		{
			SimpleDateFormat dtFormat=new SimpleDateFormat(format);
			return dtFormat.format(date);
		}
		else 
			return null;		
	}
	
	public static String getDisplayTime(java.util.Calendar date, String format)
	{
		if(date!=null)
		{
			SimpleDateFormat dtFormat=new SimpleDateFormat(format);
			return dtFormat.format(date.getTime());
		}
		else return null;
		
	}
	public static Calendar getEstimateTimes(String duration, Calendar arrivalcal) {
		
		Calendar cal;
		String servHrs=duration;
		String arr[]=servHrs.split(" ");
		int days=0;
		int hours=0;
		int mins=0;	
		for(int i=1;i<arr.length;i+=2)
		{
			if(arr[i].toLowerCase().indexOf("days")!=-1||arr[i].toLowerCase().indexOf("day")!=-1)
			{
				days=Integer.parseInt(arr[i-1]);
			}
			else if(arr[i].toLowerCase().indexOf("hours")!=-1||arr[i].toLowerCase().indexOf("hours")!=-1)
			{
				hours=Integer.parseInt(arr[i-1]);
			}
			else if(arr[i].toLowerCase().indexOf("mins")!=-1||arr[i].toLowerCase().indexOf("min")!=-1)
			{
				mins=Integer.parseInt(arr[i-1]);
			}
			
		}
		if(arrivalcal == null){
		 cal=Calendar.getInstance();
		}
		else {
			cal = arrivalcal;
		}
		cal.add(Calendar.MINUTE, mins);
		cal.add(Calendar.HOUR, hours);
		cal.add(Calendar.DATE, days);
		
		return cal;
		//return getDisplayTime(cal, "hh:mm");
	}
	public static String getElapseTime(String dispatchTime) {
		String dateStart = dispatchTime;
		String elapseTime = "00:00";
		String hours;
		String mins;
		//HH converts hour in 24 hours format (0-23), day calculation
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
//		SimpleDateFormat format1 = new SimpleDateFormat("HH:mm:ss");
		Date d1 = null;
		Date d2 = new Date();
        String temp=format.format(d2);
		try {
			d1 =  format.parse(dateStart);
			d2 =  format.parse(temp);
 
			//in milliseconds
			long diff = d2.getTime() - d1.getTime();
 
			//long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			//long diffDays = diff / (24 * 60 * 60 * 1000);
 
			
			if(diffHours<10)
			{
				hours="0"+diffHours;
			}else {
				hours =""+diffHours;
			}
			if(diffMinutes<10)
			{
				mins="0"+diffMinutes;
			}
			else{
				mins=""+diffMinutes;
			}
			elapseTime = hours+":"+mins;
		}
		catch(Exception e){
			logger.error(e);
		}
		return elapseTime;

}
	public static String getDifferenceTime(String acceptTime, String dropoffTime)
	{
		String elapseTime = "00:00";
		String hours;
		String mins;
		//HH converts hour in 24 hours format (0-23), day calculation
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date d1 = new Date();
		Date d2 = new Date();
       
		try
		{
			d1 =  format.parse(acceptTime);
			d2 =  format.parse(dropoffTime);
 			//in milliseconds
			long diff = d2.getTime() - d1.getTime();
 			//long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			//long diffDays = diff / (24 * 60 * 60 * 1000);
 
			if(diffHours<10)
			{
				hours="0"+diffHours;
			}
			else
			{
				hours =""+diffHours;
			}
			if(diffMinutes<10)
			{
				mins="0"+diffMinutes;
			}
			else
			{
				mins=""+diffMinutes;
			}
			elapseTime = hours+":"+mins;
		}
		catch(Exception e){
			logger.error(e);
		}
		return elapseTime;
	}
}
