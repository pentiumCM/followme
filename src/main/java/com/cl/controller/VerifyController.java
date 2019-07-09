package com.cl.controller;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cl.util.CaptchaUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/verify")
public class VerifyController {

	/**
	 * 利用验证码工具类 ,生成验证码，将图形返回到页面
	 * 
	 * @param codeType loginCode:登录图形验证码 registerCode:注册图形验证码 forgetPwdCode:忘记密码图形验码
	 */
	@RequestMapping("/getVerifyCode")
	public void getVerifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 通知浏览器不要缓存
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "No-cache");
		request.getSession().removeAttribute("verify");
		CaptchaUtil util = CaptchaUtil.Instance();
		String systemCode = util.getString();
		// 将对应验证码放进对应的session中
		request.getSession().setAttribute("verify", systemCode);
		log.info("verify" + "验证码：" + request.getSession().getAttribute("verify"));
		// 输出打web页面
		ImageIO.write(util.getImage(), "jpg", response.getOutputStream());
	}

}
