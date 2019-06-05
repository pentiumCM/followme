package com.cl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cl.entity.Vedio;
import com.cl.mapper.VedioMapper;
import com.cl.resp.CommonResp;
import com.cl.service.VedioService;

@Service
public class VedioServiceImpl implements VedioService{

	@Autowired
	VedioMapper vedioMapper;
	@Override
	public void insert(Vedio vedio) {
		// TODO Auto-generated method stub
		vedioMapper.insert(vedio);
		
	}

}
