package com.cl.util;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.cl.config.AliWapPayConfig;
import com.cl.config.DefaultAlipayClientFactory;
import com.cl.config.DefaultAlipayConfig;

public class AliWapPayUtil {

	/**
	 * alipay.trade.wap.pay(手机网站支付接口2.0)
	 * 
	 * @param model 手机网站支付请求参数类
	 * @return 支付宝提交表单脚本
	 * @throws AlipayApiException
	 */
	public static String wapPay(AlipayTradeWapPayModel model) throws AlipayApiException {
		// 获得初始化的AlipayClient
		AlipayClient alipayClient = DefaultAlipayClientFactory.getAlipayClient();
		// 创建API对应的request
		AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
		// 同步通知地址：支付成功后页面自动跳转到传值地址中
		request.setReturnUrl(AliWapPayConfig.RETURN_URL_WAP);
		// 异步通知地址：支付成功生成之后会post返回订单信息详情到该地址，用于商家做业务逻辑处理
		request.setNotifyUrl(AliWapPayConfig.NOTIFY_URL_WAP);
		// 添加demo请求标示，用于标记是demo发出，添加到配置文件
		request.putOtherTextParam(AliWapPayConfig.ALIPAY_DEMO, AliWapPayConfig.ALIPAY_DEMO_VERSION);
		// 设置业务参数，alipayModel为前端发送的请求信息，开发者需要根据实际情况填充此类
		request.setBizModel(model);

		// 因为是页面跳转类服务，使用pageExecute方法得到form表单后输出给前端跳转
		AlipayTradeWapPayResponse alipayResponse = alipayClient.pageExecute(request);
		// 获取表单
		String form = alipayResponse.getBody();
		LogUtil.logResult("手机网站支付", form, DefaultAlipayConfig.LOG_PATH);
		return form;
	}

}
