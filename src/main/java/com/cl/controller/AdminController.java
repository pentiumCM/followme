package com.cl.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.cl.entity.Activity;
import com.cl.entity.Admin;
import com.cl.entity.Club;
import com.cl.entity.User;
import com.cl.entity.UserAct;
import com.cl.resp.CommonResp;
import com.cl.service.ActivityService;
import com.cl.service.AdminService;
import com.cl.service.ClubService;
import com.cl.service.UserActService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminService;
	
	@Autowired
	ClubService clubService;
	
	@Autowired
	ActivityService activityService;
	
	@Autowired
	UserActService userActService;

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
				request.getSession().setAttribute("LOGIN ADMIN", admin);
				commonResp = new CommonResp(Constants.SUCCESS_CODE, "admin login success", null);
			}
		}
		return JSON.toJSONString(commonResp);
	}
	
	
	/**
	    *    获取所有club
	 * 
	 * @param request
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/getAllClubs", method = RequestMethod.POST)
	public String getAllClubs(HttpServletRequest request) {
		CommonResp commonResp =null;
		List<Club> clubList = new ArrayList<Club>();
		Admin admin = (Admin)request.getSession().getAttribute("LOGIN ADMIN");
		if (admin == null) {
			commonResp = new CommonResp(Constants.NOLOGIN_CODE, "no admin login",null);
		}else {
			clubList = clubService.getAllClubs();
			commonResp = new CommonResp(Constants.SUCCESS_CODE, "query all club success", clubList);
		}
		return JSON.toJSONString(commonResp);
	}
	
	/**
	    *    获取所有活动
	 * 
	 * @param request
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/getAllActivities", method = RequestMethod.POST)
	public String getAllActivities(HttpServletRequest request) {
		CommonResp commonResp =null;
		List<Activity> activityList = new ArrayList<Activity>();
		Admin admin = (Admin)request.getSession().getAttribute("LOGIN ADMIN");
		if (admin == null) {
			commonResp = new CommonResp(Constants.NOLOGIN_CODE, "no admin login",null);
		}else {
			activityList = activityService.getAllActivities();
			commonResp = new CommonResp(Constants.SUCCESS_CODE, "query all activities success", activityList);
		}
		return JSON.toJSONString(commonResp);
	}
	
	/**
	    *    获取所有活动用户
	 * 
	 * @param request
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/getAllUsers", method = RequestMethod.POST)
	public String getAllUsers(HttpServletRequest request) {
		CommonResp commonResp =null;
		List<UserAct> userActList = new ArrayList<UserAct>();
		Admin admin = (Admin)request.getSession().getAttribute("LOGIN ADMIN");
		if (admin == null) {
			commonResp = new CommonResp(Constants.NOLOGIN_CODE, "no admin login",null);
		}else {
			userActList = userActService.getAllUsers();
			commonResp = new CommonResp(Constants.SUCCESS_CODE, "query all activities success", userActList);
		}
		return JSON.toJSONString(commonResp);
	}
	
	/**
	 * 管理员登出
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logOut", method = RequestMethod.GET)
	public String logOut(HttpServletRequest request) {
		request.getSession().removeAttribute("LOGIN ADMIN");
		CommonResp commonResp = new CommonResp(Constants.SUCCESS_CODE, "logOut success", null);
		return JSON.toJSONString(commonResp);
	}
}
