package cn.hust.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {
	private static DateTime instance = new DateTime();

	public static String DATE_FORMAT = "yyyy-MM-dd";
	public static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static String TIME_FORMAT = "HH:mm:ss";
	public static String FOLDER_FORMAT = "yyyy/MM";
	public static String FILE_FORMAT = "ddHHmmss";
	
	private DateTime(){}
	
	public static DateTime getInstance(){
		return instance;
	}

	public String date(Object time){
		if(time == null) return null;
		
		return date(Integer.parseInt(time.toString()));
	}
	public String date(int time){
		return date((long)time * 1000);
	}
	
	public String date(long time){
		if(time == 0) return null;
		
		Date d = new Date();
		d.setTime(time);
		return new SimpleDateFormat(DATE_FORMAT).format(d);
	}
	

	public String datetime(long time){
		Date d = new Date();
		d.setTime(time);
		return new SimpleDateFormat(DATETIME_FORMAT).format(d);
	}
	

	public String time(long time){
		Date d = new Date();
		d.setTime(time);
		return new SimpleDateFormat(TIME_FORMAT).format(d);
	}
	
	public String now(){
		return new SimpleDateFormat(DATETIME_FORMAT).format(new Date());
	}
	
	public String folder(){
		return new SimpleDateFormat(FOLDER_FORMAT).format(new Date());
	}
	public String file(){
		return new SimpleDateFormat(FILE_FORMAT).format(new Date());
	}
	public String file(String ext){
		return file()+"."+ext;
	}
	
	public int date2time(String dts){
		int dt = 0;
		try {
			long dl = new SimpleDateFormat(DATE_FORMAT).parse(dts).getTime();
			dt = (int)(dl/1000);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dt;
	}
	
	public int datetime2time(String dts){
		int dt = 0;
		try {
			dt = (int)new SimpleDateFormat(DATETIME_FORMAT).parse(dts).getTime()/1000;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dt;
	}
	
	/**
	 * 当前时间戳
	 * @return
	 */
	public int timestap(){
		return (int)(System.currentTimeMillis()/1000);
	}
}
