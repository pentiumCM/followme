package com.cl.controller;

import java.util.Properties;

import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cl.entity.User;
import com.cl.util.PropertiesUtil;

/**
* @author 陈敏：842679178@qq.com
* @version 创建时间：2019年5月23日 上午9:08:54
* 类说明:
*/
@RestController
public class UserController {

	@RequestMapping(value = "/login" , method = RequestMethod.POST)
	public String login(HttpRequest request,@RequestBody User user) throws Exception {
		
		return "";
	}
}
