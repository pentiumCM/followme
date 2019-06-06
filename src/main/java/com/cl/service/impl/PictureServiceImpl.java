package com.cl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cl.entity.Picture;
import com.cl.mapper.PictureMapper;
import com.cl.service.PictureService;

@Service
public class PictureServiceImpl implements PictureService {

	@Autowired
	PictureMapper pictureMapper;
	
	@Override
	public void insert(List<Picture> pictures) {
		// TODO Auto-generated method stub
		pictureMapper.insert(pictures);
	}

}
