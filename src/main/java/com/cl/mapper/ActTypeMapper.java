package com.cl.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cl.entity.ActType;

public interface ActTypeMapper {

	List<ActType> selectActType();
	
	
	List<ActType> selectPicturesByActTypeID(@Param("actTypeID") Integer actTypeID);
	
}
