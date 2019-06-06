package com.cl.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.cl.constants.Constants;
import com.cl.entity.Club;
import com.cl.entity.Vedio;
import com.cl.resp.CommonResp;
import com.cl.service.VedioService;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/query")
public class QueryController {

	
	@Autowired
	private VedioService vedioService;
	
	@RequestMapping(value = "/queryGif", method = RequestMethod.POST)
	public String queryGif(HttpServletRequest request, HttpSession session) {
		CommonResp commonResp = null;
		/*
		 * Club club = (Club)session.getAttribute("club"); if (club == null) {
		 * commonResp = new CommonResp(Constants.ERROR_CODE, "no club login", null);
		 * return JSON.toJSONString(commonResp); }
		 */
		
		Integer pageNum = Integer.valueOf(request.getParameter("pageNum"));
		Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
		PageInfo<Vedio> pageInfo = vedioService.selectGif(pageNum, pageSize);
		commonResp = new CommonResp(Constants.SUCCESS_CODE, "query gif success", pageInfo);
		return JSON.toJSONString(commonResp);
	}
}
