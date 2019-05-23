package com.cl.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

/**
 * @Description: 日期格式化工具类
 */
public class DateUtil {

  /**
   * 获取当前时间戳的TimeStamp类型
   *
   * @return createTime
   */
  public static Timestamp getCurrentTimeStamp() {
    long time = System.currentTimeMillis() + 30 * 60 * 1000;
    long outDateLong = time / 1000 * 1000;
    Timestamp createTime = new Timestamp(outDateLong);
    return createTime;
  }

  /**
   * 获取精确到秒的时间戳
   */
  public static int getSecondTimestamp(Date date) {
    if (null == date) {
      return 0;
    }
    String timestamp = String.valueOf(date.getTime());
    int length = timestamp.length();
    if (length > 3) {
      return Integer.valueOf(timestamp.substring(0, length - 3));
    } else {
      return 0;
    }
  }

  /**
   * 获取当前Date型日期
   *
   * @return Date() 当前日期
   */
  public static Date getNowDate() {
    return new Date();
  }

  /**
   * 获取当前日期
   *
   * @return 返回当前日期(yyyy - MM - dd)
   */
  public static String getDate() {
    Calendar calendar = Calendar.getInstance();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    return dateFormat.format(calendar.getTime());
  }

  /**
   * 获取当前日期
   *
   * @return 返回当前日期(yyyyMMdd HH : mm : ss)
   */
  public static String getDateTime() {
    Calendar calendar = Calendar.getInstance();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    return dateFormat.format(calendar.getTime());
  }

  /**
   * 按特定的日期格式获取当前字符串型日期
   *
   * @param dateFormatType String，日期格式<br> 几种日期格式和测试的结果<br> "yyyy-MM-dd": 2012-08-02<br> "yyyy-MM-dd
   * hh:mm:ss": 2012-08-02 11:27:41<br> "yyyy-MM-dd hh:mm:ss EE": 2012-08-02 11:27:41 星期四<br>
   * "yyyy年MM月dd日 hh:mm:ss EE": 2012年08月02日 11:27:41 星期四<br>
   * @return String 当前字符串型日期
   */
  public static String getNowDateFormat(String dateFormatType) {
    SimpleDateFormat simformat = new SimpleDateFormat(dateFormatType);
    return simformat.format(getNowDate());
  }

  /**
   * 按特定的日期格式获取当前字符串型日期
   *
   * @param date 指定的时间
   * @param dateFormatType String，日期格式<br> 几种日期格式和测试的结果<br> "yyyy-MM-dd": 2012-08-02<br> "yyyy-MM-dd
   * hh:mm:ss": 2012-08-02 11:27:41<br> "yyyy-MM-dd hh:mm:ss EE": 2012-08-02 11:27:41 星期四<br>
   * "yyyy年MM月dd日 hh:mm:ss EE": 2012年08月02日 11:27:41 星期四<br>
   * @return String 当前字符串型日期
   */
  public static String getDateFormat(Date date, String dateFormatType) {
    SimpleDateFormat simformat = new SimpleDateFormat(dateFormatType);
    return simformat.format(date);
  }

  /**
   * 判断今天是不是周末
   *
   * @return true/false
   */
  public static boolean isTodayWeekend() {

    Calendar c = Calendar.getInstance();
    int day = c.get(Calendar.DAY_OF_WEEK);
    if (day == 7 || day == 1) {
      return true;
    }
    return false;
  }

  /**
   * 获得间隔日期（主要是间隔N周、间隔N天）
   *
   * @param specifiedStrDate 指定日期
   * @param dateForamtType 指定日期格式
   * @param intervalNum 间隔数（周或者天）
   * @param calenderParam 指定修改日期格式的属性 Calendar.WEEK_OF_MONTH（周）或者Calendar.DAY_OF_MONTH（天）
   */
  public static String getIntervalStrDate(String specifiedStrDate, String dateForamtType,
      int intervalNum,
      int calenderParam) {
    if (specifiedStrDate == null) {
      return null;
    }
    if (specifiedStrDate.trim().equals("")) {
      return null;
    }

    DateFormat df = new SimpleDateFormat(dateForamtType);

    Calendar cal = Calendar.getInstance();
    cal.setTime(DateUtil.turnStrDateToJavaUtilDate(specifiedStrDate, dateForamtType));
    cal.add(calenderParam, intervalNum);
    return df.format(cal.getTime());
  }

  /**
   * 按照指定格式将字符串日期转换为SQL需要的日期对象
   *
   * @param strDate String，欲转换的字符串型日期
   * @param dateFormateType String，指定的字符串日期格式
   * @return java.sql.Date 转换后的java.sql.Date型日期
   */
  public static java.sql.Date turnDateToSqlDate(String strDate, String dateFormateType) {
    if (strDate == null) {
      return null;
    }
    if (strDate.trim().equals("")) {
      return null;
    }

    return new java.sql.Date(turnStrDateToJavaUtilDate(strDate, dateFormateType).getTime());
  }

  /**
   * 判断两个字符串型日期是否指同一天
   *
   * @param strDate 字符串日期
   * @param anotherStrDate 另一个字符日期
   * @return boolean true/false
   */
  public static boolean isTheSameDay(String strDate, String anotherStrDate) {

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    Date firstStrDate = null;
    Date secondStrDate = null;
    try {
      firstStrDate = df.parse(strDate);
      secondStrDate = df.parse(anotherStrDate);
      return firstStrDate != null && secondStrDate != null
          && firstStrDate.getTime() == secondStrDate.getTime();
    } catch (ParseException e) {
      return false;
    }

  }

  /**
   * 按指定的字符串格式将字符串型日期转化为Date型日期
   *
   * @param dateFormatType "yyyy-MM-dd" 或者 "yyyy-MM-dd hh:mm:ss"
   * @return Date型日期
   * @Param dateStr 字符型日期
   */
  public static Date turnStrDateToJavaUtilDate(String strDate, String dateFormatType) {

    Date javaUtilDate = null;
    DateFormat df = new SimpleDateFormat(dateFormatType);
    if (strDate != null && (!"".equals(strDate)) && dateFormatType != null && (!""
        .equals(dateFormatType))) {

      try {

        javaUtilDate = df.parse(strDate);
      } catch (ParseException e) {

      }
    }
    return javaUtilDate;
  }

  /**
   * 将Date型日期转化指定格式的字符串型日期
   *
   * @param javaUtilDate Date,传入的Date型日期
   * @param dateFormatType "yyyy-MM-dd"或者<br> "yyyy-MM-dd hh:mm:ss EE"或者<br> "yyyy年MM月dd日 hh:mm:ss
   * EE" <br> (年月日 时:分:秒 星期 ，注意MM/mm大小写)
   * @return String 指定格式的字符串型日期
   */
  public static String turnJavaUtilDateToStrDate(Date javaUtilDate, String dateFormatType) {

    String strDate = "";
    if (javaUtilDate != null) {

      SimpleDateFormat sdf = new SimpleDateFormat(dateFormatType);
      strDate = sdf.format(javaUtilDate);
    }
    return strDate;
  }

  /**
   * 获取当年指定月份第一天的字符串日期
   *
   * @param specifiedMonth 指定月份
   * @param dateFormatType 日期格式
   * @return String 指定月份第一天的字符串日期
   * @throws CSException CSExceptionCode.EC_2000,CSExceptionCode.MSG_2000
   */
  public static String getTheFirstDayOfSpecifiedMonth(int specifiedMonth, String dateFormatType)
      throws Exception {

    Date currentJavaUtilDate = getNowDate();
    Calendar cal = Calendar.getInstance();
    cal.setTime(currentJavaUtilDate);
    cal.set(Calendar.MONTH, specifiedMonth - 1);
    cal.set(Calendar.DAY_OF_MONTH, 1);

    Date resultDate = cal.getTime();
    String result = turnJavaUtilDateToStrDate(resultDate, dateFormatType);
    return result;
  }

  /**
   * 获取当年指定月份的最后一天字符串日期
   *
   * @param specifiedMonth 指定月份
   * @param dateFormatType 日期格式
   * @return String 时间字符串
   */
  public static String getTheLastDayOfSpecifiedMonth(int specifiedMonth, String dateFormatType)
      throws Exception {

    Date date = null;
    date = turnStrDateToJavaUtilDate(getTheFirstDayOfSpecifiedMonth(specifiedMonth, dateFormatType),
        dateFormatType);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.MONTH, 1);
    calendar.add(Calendar.DAY_OF_YEAR, -1);
    return turnJavaUtilDateToStrDate(calendar.getTime(), "yyyy-MM-dd");
  }

  /**
   * 获取当前月第一天的字符串日期
   *
   * @return String 当前月第一天的字符串日期。例如：当前日期是2012-08-2，则返回值为2012-08-1
   */
  public static String getTheFirstDayOfCurrentMonth() {
    // 获取当前日期
    Calendar cal = Calendar.getInstance();
    // 设置为1号,即是本月第一天
    cal.set(Calendar.DAY_OF_MONTH, 1);
    // 格式化
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    return df.format(cal.getTime());
  }

  /**
   * 获取当前月最后一天的字符串日期
   *
   * @return String 当前月最后一天的日期。 例如：当前日期是2012-08-2，则返回值为2012-08-31
   */
  public static String getTheLastDayOfCurrentMonth() {

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    // 获取当前日期，例如2012-08-02
    Calendar cal = Calendar.getInstance();
    // 转变为当前月的第一天，例如2012-08-01
    cal.set(Calendar.DAY_OF_MONTH, 1);
    // 转变为下个月的第一天，例如2012-09-01
    cal.add(Calendar.MONTH, 1);
    // 下个月第一天，倒数一天，即为当前月的最后一天。例如2012-08-31
    cal.add(Calendar.DAY_OF_MONTH, -1);
    return df.format(cal.getTime());
  }

  /**
   * 验证传入日期是否为当前月最后一天
   *
   * @param targetObj 传入日期可为字符串、Date
   * @param formtStr yyyy-MM-dd hh:mm:ss
   * @return true/false
   */
  public static boolean isTheLastDayOfCurrentMonth(Object targetObj, String formtStr) {

    boolean flag = false;
    if (targetObj == null) {
      // 如果传入日期参数为null，则返回false
      return flag;
    } else if ("".equals(targetObj.toString())) {
      // 如果传入日期参数为空字符串，则返回false
      return flag;
    }

    String srcDate = "";
    if (targetObj.getClass() == String.class) {
      srcDate = DateUtil.turnJavaUtilDateToStrDate(
          DateUtil.turnStrDateToJavaUtilDate(targetObj.toString(), "yyyy-MM-dd"), formtStr);
    } else if (targetObj.getClass() == Date.class) {
      srcDate = DateUtil.turnJavaUtilDateToStrDate((Date) targetObj, formtStr);
    } else {
      srcDate = DateUtil.turnJavaUtilDateToStrDate(getNowDate(), "yyyy-MM-dd");
    }
    if (srcDate.compareTo(DateUtil.getTheLastDayOfCurrentMonth())
        == 0) {
      // 和当前月最后一天比较的结果为0，则是当前月最后一天
      flag = true;
      return flag;
    } else {
      return flag;
    }
  }

  /**
   * 获取当前时间16位字符串 小富修改为时间16位+4位随机数2012091811320043154
   *
   * @return String e.g."2012082110290016"
   */
  public static String getCurrentDateTime16Str() {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");

    /* 生成随机数 */
    int[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    Random rand = new Random();
    for (int i = 10; i > 1; i--) {
      int index = rand.nextInt(i);
      int tmp = array[index];
      array[index] = array[i - 1];
      array[i - 1] = tmp;
    }
    int result = 0;
    for (int i = 0; i < 4; i++) {
      result = result * 10 + array[i];
    }

    return sdf.format(getNowDate()) + result;
  }

  /**
   * 指定日期、相加月数值、格式，得到相加日期 例如：2011-06-19 2 yyyy-MM-dd 结果：2011-08-19 2011-06-19 12 yyyy-MM-dd
   * 结果：2012-06-19
   *
   * @param date 指定日期
   * @param formtStr 格式
   * @param number 数组
   * @param calender 指定修改日期格式数组
   * @author leiyunshi
   */
  public static String tragetDate(String date, String formtStr, int number, int calender) {
    if (date == null) {
      return null;
    }

    if (date.trim().equals("")) {
      return null;
    }
    // "yyyy-MM-dd"
    DateFormat df = new SimpleDateFormat(formtStr);

    Calendar cal = Calendar.getInstance();
    cal.setTime(DateUtil.toDate(date));
    cal.add(calender, number);
    // System.out.println(df.format(cal.getTime()));
    return df.format(cal.getTime());
  }

  /**
   * 按照"yyyy-MM-dd"格式将字符串date转换为日期对象
   */
  public static Date toDate(String date) {
    if (date == null) {
      return null;
    }
    if (date.trim().equals("")) {
      return null;
    }
    SimpleDateFormat simformat = new SimpleDateFormat("yyyy-MM-dd");
    try {
      return simformat.parse(date);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 按照"yyyy-MM-dd"格式将字符串date转换为日期对象
   */
  public static Date toDate(String date, String format) {
    if (date == null) {
      return null;
    }
    if (date.trim().equals("")) {
      return null;
    }
    SimpleDateFormat simformat = new SimpleDateFormat(format);
    try {
      return simformat.parse(date);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String dateToStr(Date dateTime) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String dateString = formatter.format(dateTime);
    return dateString;
  }

  /**
   * 获取在当前时间基础加一个自然年度的时间字符串
   */
  public static String getNextYealDateStr() {
    Date date = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.YEAR, 1);
    return dateToStr(calendar.getTime());
  }

}
