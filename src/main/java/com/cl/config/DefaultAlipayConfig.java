package com.cl.config;

import java.util.Properties;

import org.springframework.context.annotation.Configuration;

import com.cl.util.PropertiesUtil;

@Configuration // 表明这是一个配置类
public class DefaultAlipayConfig {
	/**
	 * 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	 */
	public static String APP_ID;

	/**
	 * 商户私钥 您的PKCS8格式RSA2私钥
	 */
	public static String RSA_PRIVATE_KEY;

	/**
	 * 支付宝公钥
	 */
	public static String ALIPAY_PUBLIC_KEY;

	/**
	 * 请求网关地址,支付宝网关<br>
	 * public static String URL = "https://openapi.alipay.com/gateway.do";<br>
	 * 沙箱网关
	 */
	public static String URL;

	/**
	 * 编码
	 */
	public static String CHARSET;

	/**
	 * 返回格式
	 */
	public static String FORMAT;

	/**
	 * 签名方式,RSA2
	 */
	public static String SIGN_TYPE;

	/**
	 * 支付宝日志文件目录
	 */
	public static String LOG_PATH;

	/**
	 * 资源文件路径
	 */
	private static String AliPAY_PROPERTIES = "alipay.properties";

	public DefaultAlipayConfig() throws Exception {
		Properties prop = PropertiesUtil.readProperties(AliPAY_PROPERTIES);
		DefaultAlipayConfig.APP_ID = prop.getProperty("APP_ID");
		DefaultAlipayConfig.RSA_PRIVATE_KEY = prop.getProperty("RSA_PRIVATE_KEY");
		DefaultAlipayConfig.ALIPAY_PUBLIC_KEY = prop.getProperty("ALIPAY_PUBLIC_KEY");
		DefaultAlipayConfig.URL = prop.getProperty("URL");
		DefaultAlipayConfig.CHARSET = prop.getProperty("CHARSET");
		DefaultAlipayConfig.FORMAT = prop.getProperty("FORMAT");
		DefaultAlipayConfig.SIGN_TYPE = prop.getProperty("SIGN_TYPE");
		DefaultAlipayConfig.LOG_PATH = prop.getProperty("LOG_PATH");
	}

}
