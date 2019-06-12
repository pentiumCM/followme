package com.cl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cl.entity.UserInfo;
import com.cl.mapper.UserInfoMapper;
import com.cl.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserInfoService{

	@Autowired
	UserInfoMapper userInfoMapper;
	@Override
	public UserInfo selectByUserID(Integer userID) {
		// TODO Auto-generated method stub
		return userInfoMapper.selectByUserID(userID);
	}

}
