package com.cl.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
	 * @param clubLogin
	 * @param clubName
	 * @param password
	 * @param description
	 * @return
	 */
	@RequestMapping(value = "/registerClub", method = RequestMethod.POST)
	public String clubRegister(@RequestParam(value = "clubLogin", required = true) String clubLogin,
			@RequestParam(value = "clubName", required = true) String clubName,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "description", required = true) String description
			) {
		Club club = new Club();
		club.setClubLogin(clubLogin);
		club.setClubName(clubName);
		club.setPassword(AES128.encrypt(password, clubLogin));
		club.setDescription(description);
		club.setIsDelete(0);
		clubService.insert(club);
		CommonResp commonResp = new CommonResp(Constants.SUCCESS_CODE, "club register success", null);
		return JSON.toJSONString(commonResp);
	}
	
	
	/**
	 * club登陆接口
	 * @param clubLogin
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/loginClub", method = RequestMethod.POST)
	public String clubLogin(@RequestParam(value = "clubLogin", required = true) String clubLogin,
			@RequestParam(value = "password", required = true) String password
			) {
		Club club = clubService.selectClubByClubLogin(clubLogin);
		CommonResp commonResp = null;
		if (club == null) {
			commonResp = new CommonResp(Constants.ERROR_CODE, "no such club", null);
		}else if (club.getPassword().equals(AES128.encrypt(password, clubLogin))) {
			commonResp = new CommonResp(Constants.SUCCESS_CODE, "club login success", club);
		}else {
			commonResp = new CommonResp(Constants.ERROR_CODE, "password wrong", null);
		}
		return JSON.toJSONString(commonResp);
	}
	
}
