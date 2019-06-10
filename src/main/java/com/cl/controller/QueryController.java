package com.cl.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.cl.constants.Constants;
import com.cl.entity.ActType;
import com.cl.entity.Activity;
import com.cl.entity.Club;
import com.cl.entity.Vedio;
import com.cl.resp.CommonResp;
import com.cl.service.ActTypeService;
import com.cl.service.ActivityService;
import com.cl.service.ClubService;
import com.cl.service.VedioService;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/query")
public class QueryController {

	public static Log log = LogFactory.getLog(QueryController.class);
	
	@Autowired
	private VedioService vedioService;
	
	@Autowired
	private ActTypeService actTypeService;
	
	@Autowired
	private ClubService clubService;
	
	@Autowired
	private ActivityService activityService;
	
	/**
	 * 首页查询推荐活动接口
	 * @param request
	 * @param pageNum 查询页数
	 * @param pageSize 每页数目
	 * @return
	 */
	@RequestMapping(value = "/queryGif", method = RequestMethod.POST)
	public String queryGif(HttpServletRequest request) {
		log.info("读取首页活动推荐页面");
		CommonResp commonResp = null;
		Integer pageNum = Integer.valueOf(request.getParameter("pageNum"));
		Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
		PageInfo<Vedio> pageInfo = vedioService.selectGif(pageNum, pageSize);
		if (pageInfo == null || pageInfo.getList().isEmpty()) {
			commonResp = new CommonResp(Constants.ERROR_CODE, "no activity gif",null);
		} else {
			commonResp = new CommonResp(Constants.SUCCESS_CODE, "query gif success", pageInfo);
		}
		return JSON.toJSONString(commonResp);
	}
	
	
	/**
	 * 首页查询活动分类接口
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryActivityType", method = RequestMethod.POST)
	public String queryActivityType(HttpServletRequest request) {
		log.info("读取首页活动分类页面");
		CommonResp commonResp = null;
		List<ActType> actTypeList = actTypeService.selectActType();
		if (actTypeList.isEmpty()) {
			commonResp = new CommonResp(Constants.ERROR_CODE, "no such activityType", null);
		} else {
			commonResp = new CommonResp(Constants.SUCCESS_CODE, "query activityType success", actTypeList);
		}
		
		return JSON.toJSONString(commonResp);
	}
	
	/**
	 * 根据活动id查询此活动图片接口
	 * @param actTypeID 活动类型id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryActivityTypePictures", method = RequestMethod.POST)
	public String queryActivityTypePictures(HttpServletRequest request) {
		Integer actTypeID = Integer.valueOf(request.getParameter("actTypeID"));
		log.info("读取活动所有图片页面");
		CommonResp commonResp = null;
		List<ActType> picturesList = actTypeService.selectPicturesByActTypeID(actTypeID);
		if (picturesList.isEmpty()) {
			commonResp = new CommonResp(Constants.ERROR_CODE, "this activity type has no pictures", null);
		} else {
			commonResp = new CommonResp(Constants.SUCCESS_CODE, "query activity pictures success", picturesList);
		}
		
		return JSON.toJSONString(commonResp);
	}
	
	
	/**
	 * 根据活动id查询此活动图片接口
	 * @param actID 活动id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryActivityVedio", method = RequestMethod.POST)
	public String queryActivityVedio(HttpServletRequest request) {
		Integer actID = Integer.valueOf(request.getParameter("actID"));
		log.info("读取活动视频页面");
		CommonResp commonResp = null;
		Vedio vedio = vedioService.selectVedioByActID(actID);
		if (vedio == null) {
			commonResp = new CommonResp(Constants.ERROR_CODE, "this activity has no vedio", null);
		} else {
			commonResp = new CommonResp(Constants.SUCCESS_CODE, "query activity vedio success", vedio);
		}
		
		return JSON.toJSONString(commonResp);
	}
	
	/**
	 * 根据活动id查询此俱乐部信息接口
	 * @param actID 活动id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryClubInfoByActID", method = RequestMethod.POST)
	public String queryClubInfoByActID(HttpServletRequest request) {
		Integer actID = Integer.valueOf(request.getParameter("actID"));
		log.info("读取发布此活动的俱乐部页面");
		CommonResp commonResp = null;
		Club club = clubService.selectClubByActID(actID);
		if (club == null) {
			commonResp = new CommonResp(Constants.ERROR_CODE, "no such club", null);
		} else {
			commonResp = new CommonResp(Constants.SUCCESS_CODE, "query club infomation by activity id success", club);
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
		Integer clubID = Integer.valueOf(request.getParameter("clubID"));
		log.info("读取此俱乐部所有活动页面");
		CommonResp commonResp = null;
		List<Activity> activityList = activityService.selectActivityByClubID(clubID);
		if (activityList.isEmpty()) {
			commonResp = new CommonResp(Constants.ERROR_CODE, "this club has no activity", null);
		} else {
			commonResp = new CommonResp(Constants.SUCCESS_CODE, "query club activities success", activityList);
		}
		
		return JSON.toJSONString(commonResp);
	}
	
	/**
	 * 根据活动id查询活动信息接口
	 * @param actID 活动id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryActivityByActID", method = RequestMethod.POST)
	public String queryActivityByActID(HttpServletRequest request) {
		Integer actID = Integer.valueOf(request.getParameter("actID"));
		log.info("读取此活动信息页面");
		CommonResp commonResp = null;
		Activity activity = activityService.selectActivityByActID(actID);
		if (activity == null) {
			commonResp = new CommonResp(Constants.ERROR_CODE, "no activity", null);
		} else {
			commonResp = new CommonResp(Constants.SUCCESS_CODE, "query this activity success", activity);
		}
		
		return JSON.toJSONString(commonResp);
	}
	
}
