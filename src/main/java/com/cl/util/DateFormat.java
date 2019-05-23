package com.cl.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormat {
	// 字符串转换为Date类型 yyyy-MM-dd
	private static final SimpleDateFormat parse = new SimpleDateFormat("yyyy-MM-dd");

	// Date类型数据转换为字符串yyyy-MM-dd
	private static final SimpleDateFormat formatDay = new SimpleDateFormat("yyyy-MM-dd");

	// Date类型数据转换为字符串yyyy-MM-dd HH:mm:ss
	private static final SimpleDateFormat formatSecond = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	// Date类型数据转换为字符串yyyy年MM月dd日
	private static final SimpleDateFormat formatChinese = new SimpleDateFormat("yyyy年MM月dd日");

	// Date类型数据转换为字符串yyyyMMddHHmmss(多用于文件编码)
	private static final SimpleDateFormat formatCode = new SimpleDateFormat("yyyyMMddHHmmss");

	// UTC时间转换成北京时间 iView的DatePicker控件时间为UTC时间，比北京时间少8小时
	private static final SimpleDateFormat gmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	/**
	 * UTC时间转换成北京时间 iView的DatePicker控件时间为UTC时间，比北京时间少8小时
	 * 
	 * @param time
	 * @return
	 */
	public static Date GMT(String time) {
		Date date = null;
		try {
			date = gmt.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 使用默认时区和语言环境获得一个日历
		Calendar calendar = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		calendar.setTime(date);
		// void set(int field, int value) 将给定的日历字段设置为给定值
		// int get(int field)返回给定日历字段的值
		// 北京时间比格林尼治时间（世界时）早8小时，即：北京时间=世界时+8小时。
		calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8);
		// calendar.getTime() 返回的是Date类型，也可以使用calendar.getTimeInMillis()获取时间戳
		return calendar.getTime();
	}

	/**
	 * 字符串转换为Date类型 yyyy-MM-dd
	 */
	public static Date parse(String time) {
		try {
			return parse.parse(time);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @param time yyyy-MM-dd HH-mm-ss格式的字符串
	 * @return yyyy-MM-dd的Date类型
	 */
	public static Date parseDay(String time) {
		try {
			// 注意java.sql.Date.valueOf()函数只能接受参数类型为yyyy-MM-dd类型的字符串
			return java.sql.Date.valueOf(formatDay.format(formatDay.parse(time)));
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 返回当前时间
	 * 
	 * @return yyyy-MM-dd HH-mm-ss[..]格式的Timestamp类型
	 */
	public static Timestamp getTimestamp() {
		// return new Timestamp(new Date().getTime());
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 将iview的日期控件，带时分秒的转成Timestamp
	 * 
	 * @param time yyyy-MM-dd HH-mm-ss格式的字符串
	 * @return yyyy-MM-dd HH-mm-ss[..]格式的Timestamp类型
	 */
	public static Timestamp stringToTimestamp(String time) {
		Timestamp ts = null;
		try {
			ts = Timestamp.valueOf(DateFormat.formatSecond(DateFormat.GMT(time)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ts;
	}

	/**
	 * 将string字符时间转换成Timestamp类型数据
	 * 
	 * @param time 前端js获取到的时间
	 * @return Timestamp
	 */
	public static Timestamp stringTimestamp(String time) {
		Timestamp ts = null;
		try {
			ts = Timestamp.valueOf(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ts;
	}

	/**
	 * Date类型数据转换为字符串yyyy-MM-dd
	 */
	public static String formatDay(Date date) {
		try {
			return formatDay.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Date类型数据转换为字符串yyyy-MM-dd HH:mm:ss
	 */
	public static String formatSecond(Date date) {
		try {
			return formatSecond.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Date类型数据转换为字符串yyyy年MM月dd日
	 */
	public static String formatChinese(Date date) {
		try {
			return formatChinese.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Date类型数据转换为字符串yyyyMMddHHmmss(多用于文件编码)
	 * 
	 * @param date Date类型时间
	 * @return yyyyMMddHHmmss格式字符串
	 */
	public static String formatCode(Date date) {
		return formatCode.format(date);
	}
}
