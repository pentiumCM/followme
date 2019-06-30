package com.cl.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultAlipayClientFactory {

	// 私有化构造函数
	private DefaultAlipayClientFactory() {
	}

	// 私有化实例对象，指令重排序volatile
	private volatile static AlipayClient alipayClient = null;

	/**
	 * 返回AlipayClient对象
	 * 
	 * @return AlipayClient
	 */
	public static AlipayClient getAlipayClient() {
		// 第一个检查锁，对象不为空直接返回已有的实例对象
		if (alipayClient == null) {
			// 使用同步锁。若两个线程同时到此处，就会创建两个对象
			synchronized (AlipayClient.class) {
				// 第二个检查锁，再次判断
				if (alipayClient == null) {
					alipayClient = new DefaultAlipayClient(DefaultAlipayConfig.URL, DefaultAlipayConfig.APP_ID,
							DefaultAlipayConfig.RSA_PRIVATE_KEY, DefaultAlipayConfig.FORMAT,
							DefaultAlipayConfig.CHARSET, DefaultAlipayConfig.ALIPAY_PUBLIC_KEY,
							DefaultAlipayConfig.SIGN_TYPE);
				}
			}
		}
//		log.info("支付宝客户端:" + alipayClient);
		return alipayClient;
	}
}
