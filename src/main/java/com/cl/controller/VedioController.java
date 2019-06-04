package com.cl.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cl.constants.Constants;
import com.cl.util.FileUploadUtils;

@RestController
@RequestMapping("/vedio")
public class VedioController {

	@RequestMapping(value = "/uploadVedio",method = RequestMethod.POST)
	public String upload(HttpServletRequest request , @RequestParam(value = "myFile", required = false) MultipartFile[] files) {
		String vedioPath = "";
		String ip = "";
		String port = "";
		try {
			ip = request.getLocalAddr();
			port = String.valueOf(request.getLocalPort());
			vedioPath = FileUploadUtils.upload(ip,port,files[0],Constants.VEDIO_TYPE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	
}
