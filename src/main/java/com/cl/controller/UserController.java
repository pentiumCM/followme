package com.cl.controller;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.cl.constants.Constants;
import com.cl.entity.User;
import com.cl.entity.UserInfo;
import com.cl.resp.CommonResp;
import com.cl.service.UserInfoService;

/**
* @author 陈敏：842679178@qq.com
* @version 创建时间：2019年5月23日 上午9:08:54
* 类说明:
*/
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserInfoService userInfoService;

	
	/**
	 * 根据已登录用户，获取用户信息
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getUserInfo" , method = RequestMethod.POST)
	public String login(HttpServletRequest request,HttpSession session) throws Exception {
		User user = (User)session.getAttribute("USER_SESSION");
		/*
		 * User user = new User(); user.setId(27);
		 */
		UserInfo userInfo = null;
		CommonResp commonResp = null;
		if (user == null) {
			commonResp = new CommonResp(Constants.NOLOGIN_CODE, "no user login", null);
		}else {
			userInfo = userInfoService.selectByUserID(user.getId());
			commonResp = new CommonResp(Constants.SUCCESS_CODE, "query user information success", userInfo);
		}
		return JSON.toJSONString(commonResp);
	}
}
