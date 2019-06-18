package com.cl.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cl.entity.User;
import com.cl.service.UserService;
import com.cl.util.AES128;
import com.cl.util.CaptchaUtil;
import com.cl.util.JavaMailUtil;
import com.cl.util.PropertiesUtil;
import com.cl.util.QcloudSmsUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 账户模块:用户登录、注册、忘记密码、重置密码
 * 
 * @author: 王越
 * @date: 2019年4月18日 下午3:13:30
 */
@Slf4j
@RestController
@RequestMapping("/account/login")
public class LoginController {
	@Autowired
	private UserService userService;

	/**
	 * 利用验证码工具类 ,生成验证码，将图形返回到页面
	 * 
	 * @param codeType loginCode:登录图形验证码 registerCode:注册图形验证码 forgetPwdCode:忘记密码图形验码
	 */
	@RequestMapping("/{codeType}/findVerifyCode")
	public void findVerifyCode(@PathVariable String codeType, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 通知浏览器不要缓存
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "No-cache");
		request.getSession().removeAttribute(codeType);
		CaptchaUtil util = CaptchaUtil.Instance();
		String systemCode = util.getString();
		// 将对应验证码放进对应的session中
		request.getSession().setAttribute(codeType, systemCode);
		log.info(codeType + "验证码：" + request.getSession().getAttribute(codeType));
		// 输出打web页面
		ImageIO.write(util.getImage(), "jpg", response.getOutputStream());
	}

	/**
	 * 验证图形验证码
	 * 
	 * @param codeType loginCode:登录图形验证码 registerCode:注册图形验证码 forgetPwdCode:忘记密码图形验码
	 * @param picCode  用户输入的图形验证码
	 * @return success/fail
	 */
	@RequestMapping(value = "/{codeType}/comparePicCode", method = RequestMethod.POST)
	public Map<String, Object> comparePicCode(@PathVariable String codeType, @RequestBody String str,
			HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		JSONObject strj = new JSONObject(str);
		// 登录前先验证验证码输入是否正确,正确进行登录验证,不正确则重新输入,不进行登录验证
		if (strj.getString("picCode").equalsIgnoreCase((String) session.getAttribute(codeType))) {
			map.put("info", "success");
		} else {
			map.put("info", "fail");
		}
		return map;
	}

	/**
	 * 查找用户名是否存在
	 * 
	 * @param userName 用户名
	 * @return exist/null
	 * @throws Exception 
	 */
	/*
	 * @RequestMapping(value = "/checkByName", method = RequestMethod.POST) public
	 * Map<String, Object> checkByName(@RequestBody String str) { Map<String,
	 * Object> map = new HashMap<>(); JSONObject strj = new JSONObject(str); User
	 * user = userService.selectByUserName(strj.getString("userName")); if (user !=
	 * null) { map.put("info", "exist"); } else { map.put("info", "null"); } return
	 * map; }
	 */
	
	
	@RequestMapping(value = "/checkByName", method = RequestMethod.POST)
	public String checkByName(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String username = request.getParameter("username");
PropertiesUtil propertiesUtil = new PropertiesUtil();
		
		Properties properties = propertiesUtil.readProperties("jdbc.properties");
		String filepath = properties.getProperty("filepath");
		return  userService.selectUser(username);
	}

	/**
	 * 查看手机号码是否匹配用户名
	 * 
	 * @param userName 用户名
	 * @param phone    手机号
	 * 
	 * @return match/mismatch
	 */
	@RequestMapping(value = "/checkNameByPhone", method = RequestMethod.POST)
	public Map<String, Object> checkNameByPhone(@RequestBody String str) {
		Map<String, Object> map = new HashMap<>();
		JSONObject strj = new JSONObject(str);
		User user = userService.selectByUserName(strj.getString("userName"));
		if (user == null) {
			map.put("info", "mismatch");
		}
		if (user != null) {
			if (user.getPhone().equals(strj.getString("phone"))) {
				map.put("info", "match");
			} else {
				map.put("info", "mismatch");
			}
		}
		return map;
	}

	/**
	 * 查看邮箱是否匹配用户名
	 * 
	 * @param userName 用户名
	 * @param email    邮箱
	 * @return match/mismatch
	 */
	@RequestMapping(value = "/checkNameByEmail", method = RequestMethod.POST)
	public Map<String, Object> checkNameByEmail(@RequestBody String str) {
		Map<String, Object> map = new HashMap<>();
		JSONObject strj = new JSONObject(str);
		User user = userService.selectByUserName(strj.getString("userName"));
		if (user == null) {
			map.put("info", "mismatch");
		}
		if (user != null) {
			if (user.getEmail().equals(strj.getString("email"))) {
				map.put("info", "match");
			} else {
				map.put("info", "mismatch");
			}
		}
		return map;
	}

	/**
	 * 查找手机号是否存在
	 * 
	 * @param phone 手机号
	 * @return exist 存在 null 不存在
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPhone", method = RequestMethod.POST)
	public Map<String, Object> findByPhone(@RequestBody String str) {
		Map<String, Object> map = new HashMap<>();
		JSONObject strj = new JSONObject(str);
		User user = userService.selectByPhone(strj.getString("phone"));
		if (user != null) {
			map.put("info", "exist");
		} else {
			map.put("info", "null");
		}
		return map;
	}

	/**
	 * 查找邮箱是否存在
	 * 
	 * @param email 邮箱
	 * @return exist 存在 null 不存在
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByEmail", method = RequestMethod.POST)
	public Map<String, Object> findByEmail(@RequestBody String str) {
		Map<String, Object> map = new HashMap<>();
		JSONObject strj = new JSONObject(str);
		User user = userService.selectByPhone(strj.getString("email"));
		if (user != null) {
			map.put("info", "exist");
		} else {
			map.put("info", "null");
		}
		return map;
	}

	/**
	 * 账号登录
	 * 
	 * @param userName 账号
	 * @param password 密码
	 * @param request
	 * @return account 账户类 fail 失败 success 成功
	 */
	@RequestMapping(value = "/accountLogin", method = RequestMethod.POST)
	public Map<String, Object> accountLogin(@RequestBody String str, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		JSONObject strj = new JSONObject(str);
		// 获取用户输入的账户名和密码
		String username = strj.getString("userName");
		String password = strj.getString("password");
		// 将密码加密
		String encryptPassword = AES128.encrypt(password, strj.getString("userName"));
		// 通过用户名找到对应用户
		User user = userService.selectByUserName(username);
		if (user == null) {
			// 用户名不存在
			map.put("info", "fail");
			map.put("status", 500);
			return map;
		}
		// 用户名存在
		// 将数据库中的密码和当前用户输入的密码比较
		if (!user.getPassword().equals(encryptPassword)) {
			// 用户名密码不匹配
			map.put("info", "fail");
			map.put("status", 500);
			return map;
		}
		// 用户信息放入拦截器，权限管理
		session.setAttribute("USER_SESSION", user);
		// 设置session最大有效时间，单位为秒
		session.setMaxInactiveInterval(30 * 60);
		map.put("user", user);
		map.put("info", "success");
		map.put("status", 200);
		return map;
	}

	/**
	 * 手机登录
	 * 
	 * @param str
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/phoneLogin", method = RequestMethod.POST)
	public Map<String, Object> phoneLogin(@RequestBody String str, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		JSONObject strj = new JSONObject(str);
		// 获取注册时的时间 getTime()将当前时间变为毫秒 1秒=1000毫秒
		long loginTime = new Date().getTime();
		long createMsgCodeTime = (long) session.getAttribute("createMsgCodeTime");

		// 获取用户输入的账户名和密码
		String phone = strj.getString("phone");
		String msgCode = strj.getString("msgCode");

		// 判断用户提交的手机号码和获取短信的手机号码是否相同
		String smsPhone = (String) session.getAttribute("loginPhone");
		log.info("获取短信的手机号码:" + smsPhone);
		if (!smsPhone.equals(phone)) {
			map.put("info", "phoneError");
			map.put("status", 500);
			return map;
		}
		// 有效时间和正确的验证码
		String validMsgCode = (String) session.getAttribute("loginSmsCode");
		String validTime = (String) session.getAttribute("loginFailureTime");
		log.info("短信验证码有效时间:" + validTime);
		log.info("短信验证码:" + validMsgCode);

		// 转换短信验证码有效时间
		long qMsgFailureTime = Long.parseLong(validTime) * 60 * 1000;
		// 2，比较验证码时间是否超时
		if (createMsgCodeTime + qMsgFailureTime < loginTime) {
			map.put("info", "timeOut");
			map.put("status", 500);
		} else {
			// 3，比较验证码是否相同
			if (msgCode.equals(validMsgCode)) {
				map.put("info", "success");
				map.put("status", 200);
			} else {
				map.put("info", "msgCodeErr");
				map.put("status", 500);
			}
		}

		User user = userService.selectByPhone(phone);
		// 用户信息放入拦截器，权限管理
		session.setAttribute("USER_SESSION", user);
		// 设置session最大有效时间，单位为秒
		session.setMaxInactiveInterval(30 * 60);
		map.put("user", user);
		map.put("info", "success");
		map.put("status", 200);
		return map;
	}

	/**
	 * 注册
	 * 
	 * @param userName 账号
	 * @param password 密码
	 * @param phone    手机号
	 * @param email    邮箱
	 * @param type     账号类型（phone:手机）（B:邮箱） )
	 * 
	 * @return success/fail
	 */
	@RequestMapping(value = "/accountRegister", method = RequestMethod.POST)
	public Map<String, Object> accountRegister(@RequestBody String str, HttpSession session) throws Exception {
		Map<String, Object> map = new HashMap<>();
		JSONObject strj = new JSONObject(str);
		User user = new User();
		// 判断是手机注册还是邮箱注册
		if ("phone".equals(strj.getString("type"))) {
			// 判断用户提交的手机号码和获取短信的手机号码是否相同
			String smsPhone = (String) session.getAttribute("registerPhone");
			log.info("获取短信的手机号码:" + smsPhone);
			if (!smsPhone.equals(strj.getString("phone"))) {
				map.put("info", "phoneError");
				map.put("status", 500);
				return map;
			}
			user.setPhone(strj.getString("phone"));
		} else if ("email".equals(strj.getString("type"))) {
			// 判断用户提交的手机号码和获取短信的手机号码是否相同
			String email = (String) session.getAttribute("registerEmail");
			log.info("获取验证码的邮箱:" + email);
			if (!email.equals(strj.getString("email"))) {
				map.put("info", "emailError");
				map.put("status", 500);
				return map;
			}
			user.setEmail(strj.getString("email"));
		} else {
			map.put("info", "fail");
			map.put("status", 500);
			return map;
		}
		// 将密码加密
		String encryptPassword = AES128.encrypt(strj.getString("password"), strj.getString("userName"));
		user.setUserName(strj.getString("userName"));
		user.setPassword(encryptPassword);
		// 向数据库插入值
		map.put("info", userService.insert(user));
		map.put("status", 200);
		return map;
	}

	/**
	 * 忘记密码
	 * 
	 * @param phone    手机号
	 * @param userName 账号
	 * @param password 密码
	 * 
	 * @return success/fail
	 */
	@RequestMapping(value = "/forgetPwdByPhone", method = RequestMethod.POST)
	public Map<String, Object> forgetPwdByPhone(@RequestBody String str, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		JSONObject strj = new JSONObject(str);
		// 1,判断用户提交的手机号码和获取短信的手机号码是否相同
		String smsPhone = (String) session.getAttribute("forgetPwdPhone");
		if (!smsPhone.equals(strj.get("phone"))) {
			map.put("info", "phoneError");
			map.put("status", 500);
			return map;
		}
		// 通过用户名找到对应用户
		User user = userService.selectByUserName(strj.getString("userName"));
		// 使用MD5给密码用户输入的密码加密
		String encryptPassword = AES128.encrypt(strj.getString("password"), strj.getString("userName"));
		// 2，判断新密码与旧密码是否相同
		if (encryptPassword.equals(user.getPassword())) {
			map.put("info", "pwdError");
			map.put("status", 500);
			return map;
		}
		// 与旧密码不相同，重置密码
		user.setPassword(encryptPassword);
		// 3,修改密码,并返回
		map.put("info", userService.updateById(user));
		map.put("status", 200);
		return map;
	}

	/**
	 * 修改密码
	 * 
	 * @param password 新密码
	 * @return success/fail
	 */
	@RequestMapping(value = "/changePwd", method = RequestMethod.POST)
	public Map<String, Object> changePwd(@RequestBody String str, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		User user = (User) session.getAttribute("USER_SESSION");
		JSONObject strj = new JSONObject(str);
		// 使用MD5给密码用户输入的密码加密
		String encryptPassword = AES128.encrypt(strj.getString("password"), strj.getString("userName"));
		// 2，判断新密码与旧密码是否相同
		if (encryptPassword.equals(user.getPassword())) {
			map.put("info", "pwdError");
			map.put("status", 500);
			return map;
		}
		// 给用户输入的密码加密
		// 2，判断新密码与旧密码是否相同
		user.setPassword(strj.getString("password"));
		// 3,修改密码
		map.put("info", userService.updateById(user));
		map.put("status", 200);
		// 4,判断是否修改成功
		return map;
	}

	/**
	 * 发送手机验证码
	 * 
	 * @param phoneType loginPhone:登录短信验证码 registerPhone:注册短信验证码
	 *                  forgetPwdPhone:忘记密码短信验证码
	 * @param str       phone：手机号
	 * @param request
	 * @return 0:成功 other:失败
	 */
	@RequestMapping(value = "/{phoneType}/sendMessage", method = RequestMethod.POST)
	public Map<String, Object> sendMessage(@PathVariable String phoneType, @RequestBody String str, HttpSession session)
			throws Exception {
		Map<String, Object> map = new HashMap<>();
		JSONObject strj = new JSONObject(str);
		// 获取生成验证码的时间
		long createMsgCodeTime = new Date().getTime();
		// 将当前时间放到session中，与注册时获取的时间相比较
		session.setAttribute("createMsgCodeTime", createMsgCodeTime);
		// 短信工具类
		QcloudSmsUtil qSmsUtil = new QcloudSmsUtil();
		// 获取手机号码
		String phone = strj.getString("phone");
		// 将获取短信验证码的手机号放入session中
		session.setAttribute(phoneType, phone);
		String[] QcloudPhone = new String[] { phone };
		// 发送验证码
		// 腾讯云 返回值=0 成功
		map.put("info", qSmsUtil.sendQcloudSms(QcloudPhone, phoneType));
		map.put("status", 200);
		return map;
	}

	/**
	 * 验证手机验证码
	 * 
	 * @param phoneType loginPhone:登录短信验证码 registerPhone:注册短信验证码
	 *                  forgetPwdPhone:忘记密码短信验证码
	 * @param str       phone：手机号
	 * @param request
	 * @return msgCodeErr:短信验证码错误 timeOut:验证码超时 success:验证成功
	 */
	@RequestMapping(value = "/{phoneType}/compareSmsCode", method = RequestMethod.POST)
	public Map<String, Object> compareSmsCode(@PathVariable String phoneType, @RequestBody String str,
			HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		JSONObject strj = new JSONObject(str);
		// 获取注册时的时间 getTime()将当前时间变为毫秒 1秒=1000毫秒
		long registerTime = new Date().getTime();
		long createMsgCodeTime = (long) session.getAttribute("createMsgCodeTime");
		// 定义validMsgCode 放置用户接收得到得有效短信验证码
		String validMsgCode = "";
		// 定义stringTime 放置短信验证码有效时间
		String validTime = "";
		// 1，判断验证码类型
		if (phoneType.equals("registerPhone")) {
			// 从session中获取工具类生成的验证码,设置验证码有效时间
			validMsgCode = (String) session.getAttribute("registerSmsCode");
			validTime = (String) session.getAttribute("registerFailureTime");
		} else if (phoneType.equals("forgetPwdPhone")) {
			validMsgCode = (String) session.getAttribute("forgetPwdSmsCode");
			validTime = (String) session.getAttribute("forgetPwdFailureTime");
		} else {
			map.put("info", "msgCodeErr");
			map.put("status", 500);
		}
		log.info("短信验证码有效时间:" + validTime);
		log.info("短信验证码:" + validMsgCode);
		// 转换短信验证码有效时间
		long qMsgFailureTime = Long.parseLong(validTime) * 60 * 1000;
		// 获取从页面输入的手机验证码
		String msgCode = strj.getString("msgCode");
		// 2，比较验证码时间是否超时
		if (createMsgCodeTime + qMsgFailureTime < registerTime) {
			map.put("info", "timeOut");
			map.put("status", 500);
		} else {
			// 3，比较验证码是否相同
			if (msgCode.equals(validMsgCode)) {
				map.put("info", "success");
				map.put("status", 200);
			} else {
				map.put("info", "msgCodeErr");
				map.put("status", 500);
			}
		}
		return map;
	}

	/**
	 * 发送邮箱验证码
	 * 
	 * @param emailType 邮箱发送类型:registerEmail forgetPwdEmail loginEmail
	 * @param str
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{emailType}/sendEmail", method = RequestMethod.POST)
	public Map<String, Object> sendEmail(@PathVariable String emailType, @RequestBody String str, HttpSession session)
			throws Exception {
		Map<String, Object> map = new HashMap<>();
		JSONObject strj = new JSONObject(str);
		// 获取生成验证码的时间
		long createEmailCodeTime = new Date().getTime();
		// 将当前时间放到session中，与注册时获取的时间相比较
		session.setAttribute("createEmailCodeTime", createEmailCodeTime);
		// 邮箱工具类
		JavaMailUtil mailUtil = new JavaMailUtil();
		// 获取邮箱
		String email = strj.getString("email");
		// 将获取验证码的邮箱放入session中
		session.setAttribute(emailType, email);
		String[] receiveMailAccount = new String[] { email };
		map.put("info", mailUtil.sendMail(emailType, receiveMailAccount));
		map.put("status", 200);
		return map;
	}

	/**
	 * 验证邮箱验证码
	 * 
	 * @param emailType 短信发送类型:registerEmail forgetPwdEmail loginEmail
	 * @param str
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/{emailType}/compareEmailCode", method = RequestMethod.POST)
	public Map<String, Object> compareEmailCode(@PathVariable String emailType, @RequestBody String str,
			HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		JSONObject strj = new JSONObject(str);
		// 获取注册时的时间 getTime()将当前时间变为毫秒 1秒=1000毫秒
		long registerTime = new Date().getTime();
		long createEmailCodeTime = (long) session.getAttribute("createEmailCodeTime");
		// 定义validMsgCode 放置用户接收得到得有效短信验证码
		String validEmailCode = "";
		// 定义stringTime 放置短信验证码有效时间
		String validTime = "";
		// 1，判断验证码类型
		if (emailType.equals("registerEmail")) {
			// 从session中获取工具类生成的验证码,设置验证码有效时间
			validEmailCode = (String) session.getAttribute("registerEmailCode");
			validTime = (String) session.getAttribute("registerEmailTime");
		} else if (emailType.equals("forgetPwdEmail")) {
			validEmailCode = (String) session.getAttribute("forgetPwdEmailCode");
			validTime = (String) session.getAttribute("forgetPwdEmailTime");
		} else {
			map.put("info", "emailCodeErr");
			map.put("status", 500);
		}
		log.info("邮箱验证码有效时间:" + validTime);
		log.info("邮箱验证码:" + validEmailCode);
		// 转换验证码有效时间
		long emailFailureTime = Long.parseLong(validTime) * 60 * 1000;
		// 获取从页面输入的验证码
		String emailCode = strj.getString("emailCode");
		// 2，比较验证码时间是否超时
		if (createEmailCodeTime + emailFailureTime < registerTime) {
			map.put("info", "timeOut");
			map.put("status", 500);
		} else {
			// 3，比较验证码是否相同
			if (emailCode.equals(validEmailCode)) {
				map.put("info", "success");
				map.put("status", 200);
			} else {
				map.put("info", "emailCodeErr");
				map.put("status", 500);
			}
		}
		return map;
	}

	/**
	 * 清除session中的短信验证码
	 * 
	 * @param request session信息
	 */
	@RequestMapping(value = "/clearSession", method = RequestMethod.GET)
	public void clearSession(HttpServletRequest request) {
		request.getSession().removeAttribute("validMsgCode");
		request.getSession().removeAttribute("validTime");
	}

	/**
	 * 从session中获取用户信息
	 * 
	 * @param request session信息
	 * @return success/fail
	 */
	@RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
	public Map<String, Object> getUserInfo(HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		map.put("userInfo", session.getAttribute("USER_SESSION"));
		return map;
	}

	/**
	 * 用户退出
	 * 
	 * @param request session信息
	 * @return success/fail
	 */
	@RequestMapping(value = "/exit", method = RequestMethod.GET)
	public Map<String, Object> exit(HttpServletRequest request, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		// 移除session中用户信息
		session.removeAttribute("USER_SESSION");
		map.put("info", "success");
		return map;
	}
}
