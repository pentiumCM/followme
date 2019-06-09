package com.cl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cl.entity.ActType;
import com.cl.mapper.ActTypeMapper;
import com.cl.service.ActTypeService;

@Service
public class ActTypeServiceImpl implements ActTypeService{

	@Autowired
	ActTypeMapper actTypeMapper;
	
	@Override
	public List<ActType> selectActType() {
		// TODO Auto-generated method stub
		return actTypeMapper.selectActType();
	}

	@Override
	public List<ActType> selectPicturesByActTypeID(Integer actTypeID) {
		// TODO Auto-generated method stub
		return actTypeMapper.selectPicturesByActTypeID(actTypeID);
	}

}
