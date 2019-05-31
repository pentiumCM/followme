package com.cl.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * 创建资源文件读取工具<br>
 * 采用懒汉单例模式创建Properties对象，实现对资源文件的读取
 * 
 */
public class PropertiesUtil {

	// **********************************************
	// 私有化构造函数
//	private PropertiesUtil() {
//	}
	// **********************************************

	// 实例对象,指令重排序volatile
	private static volatile Properties instance = null;

	/**
	 * 返回实例对象<br>
	 * 双重锁
	 * 
	 * @return 实例对象
	 */
	public static Properties getInstance() {
		// 第一个检查锁
		if (instance == null) {
			// 若两个线程同时到此处，就会创建两个对象
			synchronized (Properties.class) {
				// 再次判断,第二个检查锁
				if (instance == null) {
					instance = new Properties();
				}
			}
		}
		return instance;
	}

	public Properties readProperties(String propertiesName) throws Exception {
		InputStream ins = this.getClass().getClassLoader().getResourceAsStream(propertiesName);
		Properties p = getInstance();
		p.load(ins);
		return p;
	}
}
