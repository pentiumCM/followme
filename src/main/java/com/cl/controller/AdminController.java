package com.cl.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.cl.constants.Constants;
import com.cl.entity.Admin;
import com.cl.resp.CommonResp;
import com.cl.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminService;

	/**
	 * admin 登录
	 * 
	 * @param login
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/loginAdmin", method = RequestMethod.POST)
	public String adminLogin(HttpServletRequest request, @RequestBody String str) {
		JSONObject strj = new JSONObject(str);
		String login = strj.getString("username");
		String password = strj.getString("password");
		String verify = strj.getString("verify");
		String verifyString = (String) request.getSession().getAttribute("verify");
		CommonResp commonResp = null;
		if (!verify.equalsIgnoreCase(verifyString)) {
			commonResp = new CommonResp(Constants.VERIFY_ERROR_CODE, "verify error", null);
		} else {
			Admin admin = adminService.selectAdmin(login, password);
			if (admin == null) {
				commonResp = new CommonResp(Constants.LOGIN_ERROR_CODE, "password wrong", null);
			} else {
				commonResp = new CommonResp(Constants.SUCCESS_CODE, "admin login success", null);
			}
		}
		return JSON.toJSONString(commonResp);
	}
}
