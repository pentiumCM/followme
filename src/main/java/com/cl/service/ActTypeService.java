package com.cl.service;

import java.util.List;

import com.cl.entity.ActType;

public interface ActTypeService {

	List<ActType> selectActType();
	
	List<ActType> selectPicturesByActTypeID(Integer actTypeID);
	
}
