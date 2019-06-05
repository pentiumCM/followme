package com.cl.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.cl.constants.Constants;
import com.cl.entity.Activity;
import com.cl.entity.Club;
import com.cl.entity.Picture;
import com.cl.entity.Vedio;
import com.cl.resp.CommonResp;
import com.cl.service.ActivityService;
import com.cl.util.FileUploadUtils;

@RestController
@RequestMapping("/activity")
public class ActivityController {
	@Autowired
	ActivityService activityService;

	@RequestMapping(value = "/uploadActivity", method = RequestMethod.POST)
	public String upload(HttpServletRequest request,
			HttpSession session,
			@RequestParam(value = "vedio", required = true) MultipartFile vedioFile,
			@RequestParam(value = "pictures", required = true) MultipartFile[] pictures,
			@RequestParam(value = "actTime", required = true) String actTime,
			@RequestParam(value = "actTitle", required = true) String actTitle,
			@RequestParam(value = "actMaxPerson", required = true) String actMaxPerson,
			@RequestParam(value = "actCurPerson", required = true) String actCurPerson,
			@RequestParam(value = "beginCity", required = true) String beginCity,
			@RequestParam(value = "gatherPlace", required = true) String gatherPlace,
			@RequestParam(value = "beginTime", required = true) String beginTime,
			@RequestParam(value = "actCost", required = true) String actCost,
			@RequestParam(value = "introduction", required = true) String introduction,
			@RequestParam(value = "insurance", required = true) String insurance,
			@RequestParam(value = "actType", required = true) String actType,
			@RequestParam(value = "vedioName", required = true) String vedioName) {
		CommonResp commonResp = null;
		//只能上传jpg图片
		for (MultipartFile picture : pictures) {
			if (picture.getOriginalFilename().indexOf(".jpg") ==-1) {
				commonResp = new CommonResp(Constants.ERROR_CODE, "only .jpg can upload", null);
				return JSON.toJSONString(commonResp);
			}
		}
		
		//只能上传MP4视频
		if (vedioFile.getOriginalFilename().indexOf(".mp4") ==-1) {
			commonResp = new CommonResp(Constants.ERROR_CODE, "only .mp4 can upload", null);
			return JSON.toJSONString(commonResp);
		}
		
		Club club = new Club();
		club.setId(1);
		/*
		 * Club club = (Club)session.getAttribute("club"); if (club == null) {
		 * commonResp = new CommonResp(Constants.ERROR_CODE, "no club login", null);
		 * return JSON.toJSONString(commonResp); }
		 */
		String ip = request.getLocalAddr();
		String port = String.valueOf(request.getLocalPort());
		
		//先插入activity数据，根据返回的主键，再插入picture和vedio
		Activity activity = new Activity();
		activity.setActTime(actTime);
		activity.setActTitle(actTitle);
		activity.setActMaxPerson(Integer.valueOf(actMaxPerson));
		activity.setActCurPerson(Integer.valueOf(actCurPerson));
		activity.setBeginCity(beginCity);
		activity.setGatherPlace(gatherPlace);
		
		//注意timestamp格式转换
		activity.setBeginTime(Timestamp.valueOf(beginTime));
		activity.setActCost(actCost);
		activity.setIntroduction(introduction);
		activity.setActType(Integer.valueOf(actType));
		activity.setInsurance(Integer.valueOf(insurance));
		activity.setClubID(club.getId());
		activity.setIsDelete(0);
		
		//插入picture
		List<Picture> activityPictureList = new ArrayList<Picture>();
		for (MultipartFile picture : pictures) {
			Picture activityPicture = new Picture();
			activityPicture.setPictureUrl(FileUploadUtils.upload(ip, port, picture, Constants.JPG_TYPE));
			activityPictureList.add(activityPicture);
		}
		
		//插入vedio
		String vedioPath = "";
		String[] gifParam = new String[2];
		Vedio vedio = new Vedio();
		vedioPath = FileUploadUtils.upload(ip, port, vedioFile, Constants.VEDIO_TYPE);
		gifParam = FileUploadUtils.convert2GIF(ip, port, vedioPath, Constants.GIF_TYPE);
		vedio.setDescription(introduction);
		vedio.setDuration(Long.valueOf(gifParam[0]));
		vedio.setGifPath(gifParam[1]);
		vedio.setVedioName(vedioName);
		vedio.setVedioPath(vedioPath);
		
		activityService.insert(activity,activityPictureList,vedio);
		commonResp = new CommonResp(Constants.SUCCESS_CODE, "upload activity success", vedio);
		return JSON.toJSONString(commonResp);
	}

}
