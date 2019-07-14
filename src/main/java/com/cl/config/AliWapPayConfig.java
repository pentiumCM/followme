package com.cl.config;

import java.util.Properties;

import org.springframework.context.annotation.Configuration;

import com.cl.util.PropertiesUtil;

@Configuration // 表明这是一个配置类，
public class AliWapPayConfig {

	/**
	 * 手机网站页面跳转同步通知页面路径<br>
	 * 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问<br>
	 * 商户可以自定义同步跳转地址
	 */
	public static String RETURN_URL_WAP;

	/**
	 * 手机网站服务器异步通知页面路径<br>
	 * 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问<br>
	 */
	public static String NOTIFY_URL_WAP;

	/**
	 * 手机网站支付成功跳转页面
	 */
	public static String RESPONSE_UTL_WAP;

	/**
	 * 销售产品码，商家和支付宝签约的产品码
	 */
	public static String PRODUCT_CODE_WAP;

	/**
	 * demo请求标示，用于标记是demo发出
	 */
	public static String ALIPAY_DEMO;

	/**
	 * demo请求标示，用于标记是demo发出
	 */
	public static String ALIPAY_DEMO_VERSION;

	/**
	 * 资源文件路径
	 */
	private static String ALIPAY_PROPERTIES = "resource/alipay.properties";

	public AliWapPayConfig() throws Exception {
		Properties prop = PropertiesUtil.readProperties(ALIPAY_PROPERTIES);
		AliWapPayConfig.RETURN_URL_WAP = prop.getProperty("RETURN_URL_WAP");
		AliWapPayConfig.NOTIFY_URL_WAP = prop.getProperty("NOTIFY_URL_WAP");
		AliWapPayConfig.RESPONSE_UTL_WAP = prop.getProperty("RESPONSE_UTL_WAP");
		AliWapPayConfig.PRODUCT_CODE_WAP = prop.getProperty("PRODUCT_CODE_WAP");
		AliWapPayConfig.ALIPAY_DEMO = prop.getProperty("ALIPAY_DEMO");
		AliWapPayConfig.ALIPAY_DEMO_VERSION = prop.getProperty("ALIPAY_DEMO_VERSION");
		System.out.println("AliWapPayConfig.ALIPAY_DEMO:" + AliWapPayConfig.ALIPAY_DEMO);
	}

}
