package com.cl.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;

public class PayUtil {

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public String APP_ID = "";
	// 应用私钥 您的PKCS8格式RSA2私钥
	public String RSA_PRIVATE_KEY = "";
	// 支付宝公钥
	public String ALIPAY_PUBLIC_KEY = "";

	// 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	// 在支付页面支付成功后，会（跳转）调用此路径方法,执行支付成功后的方法
	public String NOTIFY_URL = "";

	// 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	// 商户可以自定义同步跳转地址
	public String RETURN_URL = "";

	// 请求网关地址,支付宝网关
	public String URL = "";

	// 编码
	public String CHARSET = "";
	// 返回格式
	public String FORMAT = "";
	// 日志记录目录
	public String log_path = "";
	// 签名方式,RSA2
	public String SIGN_TYPE = "";

	PropertiesUtil pUtil = new PropertiesUtil();

	public PayUtil() {
		// 读取支付宝配置文件
		try {
			Properties smsProp = pUtil.readProperties("resource/alipay.properties");
			this.APP_ID = smsProp.getProperty("APP_ID");
			this.RSA_PRIVATE_KEY = smsProp.getProperty("RSA_PRIVATE_KEY");
			this.ALIPAY_PUBLIC_KEY = smsProp.getProperty("ALIPAY_PUBLIC_KEY");
			this.NOTIFY_URL = smsProp.getProperty("NOTIFY_URL");
			this.RETURN_URL = smsProp.getProperty("RETURN_URL");
			this.URL = smsProp.getProperty("URL");
			this.CHARSET = smsProp.getProperty("CHARSET");
			this.FORMAT = smsProp.getProperty("FORMAT");
			this.log_path = smsProp.getProperty("log_path");
			this.SIGN_TYPE = smsProp.getProperty("SIGN_TYPE");

			System.out.println(APP_ID);
			System.out.println(RSA_PRIVATE_KEY);
			System.out.println(ALIPAY_PUBLIC_KEY);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param outTradeNo  商户订单号，商户网站订单系统中唯一订单号，必填 对应缴费记录的orderNo
	 * @param totalAmount 付款金额，必填
	 * @param subject     主题
	 * @param body        商品描述，可空
	 * @return
	 */
	public String alipay(String outTradeNo, String totalAmount, String subject, String body) {
		// AlipayClient alipayClient = new DefaultAlipayClient(URL, APP_ID,
		// APP_PRIVATE_KEY, FORMAT, CHARSET,
		// ALIPAY_PUBLIC_KEY, SIGN_TYPE);

		// 获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(URL, APP_ID, RSA_PRIVATE_KEY, FORMAT, CHARSET,
				ALIPAY_PUBLIC_KEY, SIGN_TYPE);

		System.out.println(alipayClient);

		// 设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(RETURN_URL);
		alipayRequest.setNotifyUrl(NOTIFY_URL);

		AlipayTradePagePayResponse response;

		try {
			alipayRequest.setBizContent("{\"out_trade_no\":\"" + outTradeNo + "\"," + "\"total_amount\":\""
					+ totalAmount + "\"," + "\"subject\":\"" + subject + "\"," + "\"body\":\"" + body + "\","
					+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

			System.out.println(alipayRequest.getBizContent());

			response = alipayClient.pageExecute(alipayRequest);

			if (response.isSuccess()) {
				System.out.println("调用成功");
			} else {
				System.out.println("调用失败");
			}

			// 请求
			String result = response.getBody();

			System.out.println("*********************\n返回结果为：" + result);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 支付宝退款接口
	 * 
	 * @param outTradeNo
	 * @param tradeNo
	 * @param refundAmount
	 * @param refundReason
	 * @param out_request_no 标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
	 * @return
	 */
	public String aliRefund(String outTradeNo, String tradeNo, String refundAmount, String refundReason,
			String out_request_no) {
		// 获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(URL, APP_ID, RSA_PRIVATE_KEY, FORMAT, CHARSET,
				ALIPAY_PUBLIC_KEY, SIGN_TYPE);

		// 设置请求参数
		AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
		alipayRequest.setReturnUrl(RETURN_URL);
		alipayRequest.setNotifyUrl(NOTIFY_URL);
		try {
			alipayRequest.setBizContent("{\"out_trade_no\":\"" + outTradeNo + "\"," + "\"trade_no\":\"" + tradeNo
					+ "\"," + "\"refund_amount\":\"" + refundAmount + "\"," + "\"refund_reason\":\"" + refundReason
					+ "\"," + "\"out_request_no\":\"" + out_request_no + "\"}");

			// 请求
			String result;

			// 请求
			result = alipayClient.execute(alipayRequest).getBody();
			System.out.println("*********************\n返回结果为：" + result);
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 支付宝的验签方法
	 * 
	 * @param req
	 * @return
	 */
	public boolean checkSign(HttpServletRequest req) {
		Map<String, String[]> requestMap = req.getParameterMap();
		final Map<String, String> paramsMap = new HashMap<>();
		StringBuffer string = new StringBuffer();
		requestMap.forEach((key, values) -> {
			String strs = "";
			for (String value : values) {
				strs = strs + value;
			}
			string.append("key值为" + key + " value为：" + strs + "		");
			System.out.println(("key值为" + key + "			value为：" + strs));
			paramsMap.put(key, strs);
		});
		// 日志写入
//		logResult("验签", string.toString());

		// 调用SDK验证签名
		try {
			return AlipaySignature.rsaCheckV1(paramsMap, ALIPAY_PUBLIC_KEY, CHARSET, SIGN_TYPE);
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("*********************验签失败********************");
			return false;
		}
	}

}