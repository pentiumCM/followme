package com.cl.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

/**
 * 日志信息存储
 * 
 * @author: dylan
 * @date: 2019年4月4日 下午10:38:14
 */
public class LogUtil {

//	@Value("${LOG_PATH}")
	private static String log_path = "D://log//";

	private LogUtil() {
		System.out.println(log_path);
	}

	/**
	 * 写日志
	 * 
	 * @param title 日志文件标题
	 * @param sWord 要写入日志里的文本内容
	 */
	public static void logResult(String title, String sWord, String logPath) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(logPath + "log_" + DateFormat.formatCode(new Date()) + "_" + title + ".txt");
			writer.write(sWord);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * 写日志
	 * 
	 * @param title 日志文件标题
	 * @param sWord 要写入日志里的文本内容
	 */
	public static void logResult(String title, String sWord) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(log_path + "log_" + DateFormat.formatCode(new Date()) + "_" + title + ".txt");
			writer.write(sWord);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 写日志
	 * 
	 * @param sWord 要写入日志里的文本内容
	 */
	public static void logResult(String sWord) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(log_path + "log_" + DateFormat.formatCode(new Date()) + ".txt");
			writer.write(sWord);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 将异常写日志
	 * 
	 * @param title     日志文件标题
	 * @param exception
	 */
	public static void logResult(String title, Exception exception) {
		System.out.println("*******************************");
		System.out.println(log_path);
		FileWriter writer = null;
		try {
			writer = new FileWriter(
					log_path + "exception_log_" + DateFormat.formatCode(new Date()) + "_" + title + ".txt");
			writer.write(getErrorInfoFromException(exception));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String getErrorInfoFromException(Exception exception) {
		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			exception.printStackTrace(pw);
			return "\r\n" + sw.toString() + "\r\n";
		} catch (Exception e) {
			return "bad getErrorInfoFromException";
		}
	}

}
