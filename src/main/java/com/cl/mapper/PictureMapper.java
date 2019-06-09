package com.cl.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cl.entity.Picture;

public interface PictureMapper {
	void insert(List<Picture> picture);
	
	List<Picture> selectPictureByActID(@Param("actID") Integer actID);
}
