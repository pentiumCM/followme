package com.cl.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
	public String adminLogin(@RequestParam(value = "login", required = true) String login,
			@RequestParam(value = "password", required = true) String password) {
		Admin admin = adminService.selectAdmin(login, password);
		CommonResp commonResp = null;
		if (admin == null) {
			commonResp = new CommonResp(Constants.ERROR_CODE, "password wrong", null);
		} else {
			commonResp = new CommonResp(Constants.SUCCESS_CODE, "admin login success", null);
		}
		return JSON.toJSONString(commonResp);
	}
}
