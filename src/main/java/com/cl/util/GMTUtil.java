package com.cl.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * UTC时间转换成北京时间 iView的DatePicker控件时间为UTC时间，比北京时间少8小时
 */
public class GMTUtil {

	/**
	 * 
	 * @param UTCTime
	 *            iView的DatePicker控件时间
	 * @return 转化后的北京时间
	 * @throws ParseException
	 */
	public static Date change(String UTCTime) throws ParseException {
		// 格式化日期
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		Date date = df.parse(UTCTime);
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
}
