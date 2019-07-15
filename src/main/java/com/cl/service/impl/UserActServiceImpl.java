package com.cl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cl.entity.UserAct;
import com.cl.mapper.UserActMapper;
import com.cl.service.UserActService;

@Service
public class UserActServiceImpl implements UserActService{

	@Autowired
	UserActMapper userActMapper;
	
	@Override
	public List<UserAct> selectAllUsersByClubID(Integer clubID) {
		// TODO Auto-generated method stub
		return userActMapper.selectAllUsersByClubID(clubID);
	}

	@Override
	public List<UserAct> getAllUsers() {
		// TODO Auto-generated method stub
		return userActMapper.getAllUsers();
	}

}
