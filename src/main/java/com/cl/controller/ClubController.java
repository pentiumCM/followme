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
import com.cl.entity.Club;
import com.cl.resp.CommonResp;
import com.cl.service.ClubService;
import com.cl.util.AES128;

@RestController
@RequestMapping("/club")
public class ClubController {

	@Autowired
	ClubService clubService;

	
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
				commonResp = new CommonResp(Constants.SUCCESS_CODE, "club login success", club);
			} else {
				commonResp = new CommonResp(Constants.LOGIN_ERROR_CODE, "password wrong", null);
			}
		}
		return JSON.toJSONString(commonResp);
	}

}
