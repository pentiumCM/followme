package com.cl.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.cl.constants.Constants;
import com.cl.entity.Activity;
import com.cl.entity.Club;
import com.cl.entity.UserAct;
import com.cl.resp.CommonResp;
import com.cl.service.ActivityService;
import com.cl.service.ClubService;
import com.cl.service.UserActService;
import com.cl.util.AES128;

@RestController
@RequestMapping("/club")
public class ClubController {

	@Autowired
	ClubService clubService;
	
	@Autowired
	ActivityService activityService;
	
	@Autowired
	UserActService userActService;

	
	/**
	 * club注冊接口
	 * 
	 * @param clubLogin
	 * @param clubName
	 * @param password
	 * @param description
	 * @return
	 */
	@RequestMapping(value = "/registerClub", method = RequestMethod.POST)
	public String clubRegister(@RequestBody String str,HttpServletRequest request) {
		CommonResp commonResp =null;
		JSONObject strj = new JSONObject(str);
		String clubLogin = strj.getString("clubLogin");
		String clubName = strj.getString("clubName");
		String password = strj.getString("password");
		String description = strj.getString("description");
		String picCode = strj.getString("picCode");
		
		String verifyString = (String) request.getSession().getAttribute("verify");
		//先校验验证码对不对
		if (!picCode.equalsIgnoreCase(verifyString)) {
			commonResp = new CommonResp(Constants.VERIFY_ERROR_CODE, "verify error", null);
			//校验此登录名是否已存在
		}else if (clubService.selectClubByClubLogin(clubLogin) != null) {
			commonResp = new CommonResp(Constants.CLUB_LOGIN_ERROR_CODE, "club name exists", null);
		}else {
			Club club = new Club();
			club.setClubLogin(clubLogin);
			club.setClubName(clubName);
			club.setPassword(AES128.encrypt(password, clubLogin));
			club.setDescription(description);
			club.setIsDelete(0);
			clubService.insert(club);
			commonResp = new CommonResp(Constants.SUCCESS_CODE, "club register success", null);
		}
		
		return JSON.toJSONString(commonResp);
	}
	

	/**
	 * club登陆接口
	 * 
	 * @param clubLogin
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/loginClub", method = RequestMethod.POST)
	public String clubLogin(HttpServletRequest request,@RequestBody String str) {
		CommonResp commonResp = null;
		JSONObject strj = new JSONObject(str);
		String clubLogin = strj.getString("username");
		String password = strj.getString("password");
		String verify = strj.getString("verify");

		String verifyString = (String) request.getSession().getAttribute("verify");
		if (!verify.equalsIgnoreCase(verifyString)) {
			commonResp = new CommonResp(Constants.VERIFY_ERROR_CODE, "verify error", null);
		} else {

			Club club = clubService.selectClubByClubLogin(clubLogin);
			if (club == null) {
				commonResp = new CommonResp(Constants.LOGIN_ERROR_CODE, "no such club", null);
			} else if (club.getPassword().equals(AES128.encrypt(password, clubLogin))) {
				request.getSession().setAttribute("LOGIN_CLUB", club);
				club.setPassword(null);
				commonResp = new CommonResp(Constants.SUCCESS_CODE, "club login success", club);
			} else {
				commonResp = new CommonResp(Constants.LOGIN_ERROR_CODE, "password wrong", null);
			}
		}
		return JSON.toJSONString(commonResp);
	}

	/**
	 * 根据俱乐部id查询此俱乐部信息接口
	 * @param clubID 俱乐部id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryClubActByClubID", method = RequestMethod.POST)
	public String queryClubActByClubID(HttpServletRequest request) {
		Club club = (Club)request.getSession().getAttribute("LOGIN_CLUB");
		CommonResp commonResp = null;
		if (club == null) {
			commonResp = new CommonResp(Constants.NOLOGIN_CODE, "no club login", null);
			return JSON.toJSONString(commonResp);
		}
		
		List<Activity> activityList = activityService.selectActivityByClubID(club.getId());
		if (activityList.isEmpty()) {
			commonResp = new CommonResp(Constants.ERROR_CODE, "this club has no activity", null);
		} else {
			commonResp = new CommonResp(Constants.SUCCESS_CODE, "query club activities success", activityList);
		}
		return JSON.toJSONString(commonResp);
	}
	
	/**
	 * 根据俱乐部id获取所有活动用户
	 * @param clubID 俱乐部id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryAllUsersByClubID", method = RequestMethod.POST)
	public String queryAllUsersByClubID(HttpServletRequest request) {
		Club club = (Club)request.getSession().getAttribute("LOGIN_CLUB");
		CommonResp commonResp = null;
		if (club == null) {
			commonResp = new CommonResp(Constants.NOLOGIN_CODE, "no club login", null);
			return JSON.toJSONString(commonResp);
		}
		List<UserAct> userActList = userActService.selectAllUsersByClubID(club.getId());
		if (userActList.isEmpty()) {
			commonResp = new CommonResp(Constants.ERROR_CODE, "no user join in this club's activity", null);
		} else {
			commonResp = new CommonResp(Constants.SUCCESS_CODE, "query club activities users success", userActList);
		}
		return JSON.toJSONString(commonResp);
	}
	
	
	/**
	 * 俱乐部登出
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logOut", method = RequestMethod.GET)
	public String logOut(HttpServletRequest request) {
		request.getSession().removeAttribute("LOGIN_CLUB");
		CommonResp commonResp = new CommonResp(Constants.SUCCESS_CODE, "logOut success", null);
		return JSON.toJSONString(commonResp);
	}
	
}
