package com.cl.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.render.Renderer;
import javax.json.Json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cl.constants.Constants;
import com.cl.entity.User;
import com.cl.mapper.UserMapper;
import com.cl.resp.CommonResp;
import com.cl.service.UserService;

/**
 * @author 陈敏：842679178@qq.com
 * @version 创建时间：2019年5月23日 上午9:13:19 类说明:
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public User selectByUserName(String userName) {
		return userMapper.selectByUserName(userName);
	}

	@Override
	public User selectByPhone(String phone) {
		return userMapper.selectByPhone(phone);
	}

	@Override
	public User selectByEmail(String email) {
		return userMapper.selectByEmail(email);
	}

	@Override
	public String insert(User user) {
		if (userMapper.insert(user) > 0) {
			return "success";
		} else {
			return "fail";
		}
	}

	@Override
	public String updateById(User user) {
		if (userMapper.updateById(user) > 0) {
			return "success";
		} else {
			return "fail";
		}
	}

	@Override
	public String selectUser(String username) {
		// TODO Auto-generated method stub
		User user = userMapper.selectByUserName(username);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", user);
		map.put("name", "zhangsan");
		CommonResp commonResp = new CommonResp(Constants.SUCCESS_CODE, "login success", map, null);
		
		return JSON.toJSONString(commonResp);
	}

}
