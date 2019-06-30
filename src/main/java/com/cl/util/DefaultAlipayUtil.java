package com.cl.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.cl.config.DefaultAlipayConfig;

public class DefaultAlipayUtil {
	/**
	 * 支付宝的验签方法
	 * 
	 * @param req
	 * @param type         验签类型
	 * @param alipayConfig 支付宝配置类
	 * @return 验签是否成功
	 */
	public static boolean checkSign(HttpServletRequest request, String type) {
		// 将异步通知中收到的所有参数都存放到map中
		Map<String, String[]> requestMap = request.getParameterMap();
		// 用以存放转化后的参数集合
		final Map<String, String> paramsMap = new HashMap<>();
		// 日志文本
		StringBuffer string = new StringBuffer();
		// 转换map类型
		requestMap.forEach((key, values) -> {
			String strs = "";
			for (String value : values) {
				strs = strs + value;
			}
			string.append("key:" + key + "		value：" + strs + "		\r\n");
//			System.out.println(("key:" + key + "			value：" + strs));
			paramsMap.put(key, strs);
		});
		// 日志写入
		String logTitle = "日志文件标题";
		switch (type) {
		case "pageReturn":
			logTitle = "电脑网站同步验签";
			break;
		case "pageNotify":
			logTitle = "电脑网站异步验签";
			break;
		case "wapReturn":
			logTitle = "手机网页同步验签";
			break;
		case "wapNotify":
			logTitle = "手机网页异步验签";
			break;
		default:
			break;
		}
		LogUtil.logResult(logTitle, string.toString(), DefaultAlipayConfig.LOG_PATH);
		// 调用SDK验证签名
		try {
			boolean verifyResult = AlipaySignature.rsaCheckV1(paramsMap, DefaultAlipayConfig.ALIPAY_PUBLIC_KEY,
					DefaultAlipayConfig.CHARSET, DefaultAlipayConfig.SIGN_TYPE);
			return verifyResult;
		} catch (AlipayApiException e) {
			e.printStackTrace();
			// 此处需要自定义异常方法
			System.out.println("*********************验签失败********************");
		}
		return false;
	}

}