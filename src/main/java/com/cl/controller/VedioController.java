package com.cl.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.cl.constants.Constants;
import com.cl.entity.Vedio;
import com.cl.resp.CommonResp;
import com.cl.service.VedioService;
import com.cl.util.FileUploadUtils;

@RestController
@RequestMapping("/vedio")
public class VedioController {
	@Autowired
	VedioService vedioService;

	@RequestMapping(value = "/uploadVedio", method = RequestMethod.POST)
	public String upload(HttpServletRequest request,
			@RequestParam(value = "myFile", required = true) MultipartFile[] files,
			@RequestParam(value = "description", required = true) String description,
			@RequestParam(value = "vedioName", required = true) String vedioName) {
		CommonResp commonResp = null;
		for (MultipartFile multipartFile : files) {
			if (multipartFile.getOriginalFilename().indexOf(".mp4") ==-1) {
				commonResp = new CommonResp(Constants.ERROR_CODE, "only .mp4 can upload", null);
				return JSON.toJSONString(commonResp);
			}
		}
		
		String vedioPath = "";
		String[] gifParam = new String[2];
		String ip = "";
		String port = "";
		Vedio vedio = new Vedio();
		ip = request.getLocalAddr();
		port = String.valueOf(request.getLocalPort());
		vedioPath = FileUploadUtils.upload(ip, port, files[0], Constants.VEDIO_TYPE);
		gifParam = FileUploadUtils.convert2GIF(ip, port, vedioPath, Constants.GIF_TYPE);
		vedio.setDescription(description);
		vedio.setDuration(Long.valueOf(gifParam[0]));
		vedio.setGifPath(gifParam[1]);
		vedio.setVedioName(vedioName);
		vedio.setVedioPath(vedioPath);
		vedioService.insert(vedio);
		commonResp = new CommonResp(Constants.SUCCESS_CODE, "upload vedio success", vedio);
		return JSON.toJSONString(commonResp);
	}

}
