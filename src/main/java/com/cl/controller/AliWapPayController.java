package com.cl.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.cl.constants.Constants;
import com.cl.resp.CommonResp;
import com.cl.util.AliWapPayUtil;
import com.cl.util.DefaultAlipayUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/aliWapPay")
public class AliWapPayController {

	/**
	 * alipay.trade.wap.pay(手机网站支付接口2.0)
	 * api:https://docs.open.alipay.com/api_1/alipay.trade.wap.pay
	 * demo:https://openclub.alipay.com/club/history/read/6003
	 * 
	 * @param model                手机网站支付请求参数类
	 * @param model.outTradeNo     商户订单号
	 * @param model.totalAmount    付款金额
	 * @param model.subject        订单名称
	 * @param model.body           商品描述
	 * @param model.timeoutExpress 订单允许的最晚付款时间
	 * @param model.quitUrl        用户付款中途退出返回商户网站的地址
	 * @return 支付宝提交表单脚本
	 */
	@ResponseBody
	@RequestMapping(value = "/wapPay", method = RequestMethod.POST)
	public String wapPay(@RequestBody AlipayTradeWapPayModel model) {
		log.info("*********************支付宝手机网站支付--Ajax***************************");
		String form = null;
		try {
			form = AliWapPayUtil.wapPay(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSON.toJSONString(new CommonResp(Constants.SUCCESS_CODE, "支付成功", form));
	}

	/**
	 * 页面跳转同步通知页面路径
	 * 
	 * @param returnPay
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "/wapReturn", method = RequestMethod.GET)
	public void wapReturn(HttpServletResponse response, HttpServletRequest request) throws IOException {
		response.setContentType("type=text/html;charset=UTF-8");
		log.info("****************************************支付宝的的手机网站同步回调函数被调用******************************");

		// 商户订单号
		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
		// 付款金额
		String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

		// 验签
		boolean verifyResult = DefaultAlipayUtil.checkSign(request, "wapReturn");
		if (verifyResult) {

			log.info("*******************************手机网站同步验签成功*****************************");
			log.info("***********************执行具体业务逻辑***********************");
			// 以下为具体业务逻辑

			// 页面重定向至成功页面
			/*
			 * String responseUrl = AliWapPayConfig.RESPONSE_UTL_WAP;
			 * response.sendRedirect(responseUrl);
			 */
		} else {
			log.info("*******************************手机网站同步验签失败*****************************");
			response.getWriter().write("failture");
		}

	}

	/**
	 * 支付宝的异步通知页面路径<br>
	 * 支付宝签约商户时有限制，必须在签约的域名上测试支付宝的支付结果。<br>
	 * 如果直接返回商户的则不需要服务器上调试，但是支付宝的异步通知，必须要在公网上才可以调试。
	 * 
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/wapNotify", method = RequestMethod.POST)
	public String wapNotify(HttpServletResponse response, HttpServletRequest request) {
		// 获取支付宝POST过来反馈信息
		log.info("****************************************支付宝的手机网站异步回调函数被调用******************************");

		/*
		 * 实际验证过程建议商户务必添加以下校验： 1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
		 * 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额）， 3、校验通知中的seller_id（或者seller_email)
		 * 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
		 * 4、验证app_id是否为该商户本身。
		 */
		// 验签
		boolean verifyResult = DefaultAlipayUtil.checkSign(request, "wapNotify");
		if (verifyResult) {// 验证成功
			log.info("*************************手机网站异步验签成功*******************************");
			try {
				// 获取需要的数据

				// 商户订单号
				String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
				// 付款金额
				String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
				// 支付宝交易号
				String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
				// 交易状态
				String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

				log.info("手机网站交易状态:" + trade_status);
				// 设置支付宝订单状态
				switch (trade_status) {
				case "TRADE_FINISHED": // 交易结束并不可退款
					break;
				case "TRADE_SUCCESS": // 交易支付成功
					break;
				case "TRADE_CLOSED": // 未付款交易超时关闭或支付完成后全额退款
					break;
				case "WAIT_BUYER_PAY": // 交易创建并等待买家付款
					break;
				default:
					break;
				}

				if (trade_status.equals("TRADE_FINISHED")) {
					// 判断该笔订单是否在商户网站中已经做过处理
					// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					// 如果有做过处理，不执行商户的业务程序

					// 注意：
					// 退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
				} else if (trade_status.equals("TRADE_SUCCESS")) {
					// 判断该笔订单是否在商户网站中已经做过处理
					// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					// 如果有做过处理，不执行商户的业务程序

					// 注意：
					// 付款完成后，支付宝系统发送该交易状态通知

					// 执行业务逻辑
					// 通过商户订单号查找订单，判断其当前的状态

					// 更新支付宝订单状态
//					String resultQuery = payUtil.aliQuery(out_trade_no, trade_no);
//					LogUtil.logResult("手机网站交易查询", resultQuery);

				} else {
					// 若是其他状态，则为失败
					return "fail";
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "fail";
			}
			return "success";
		} else {// 验证失败
			log.info("*************************手机网站异步验签失败*******************************");
			return "fail";
		}

	}

}
