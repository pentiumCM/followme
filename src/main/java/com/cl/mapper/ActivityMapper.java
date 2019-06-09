package com.cl.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cl.entity.Activity;

public interface ActivityMapper {

	void insert(Activity activity);
	
	List<Activity> selectActivityByClubID(@Param("clubID") Integer clubID);
	
	Activity selectActivityByActID(@Param("actID") Integer actID);
}
