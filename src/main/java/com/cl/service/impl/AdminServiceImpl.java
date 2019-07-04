package com.cl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cl.entity.Admin;
import com.cl.mapper.AdminMapper;
import com.cl.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	AdminMapper adminMapper;
	
	@Override
	public Admin selectAdmin(String login, String password) {
		// TODO Auto-generated method stub
		return adminMapper.selectAdmin(login, password);
	}

}
