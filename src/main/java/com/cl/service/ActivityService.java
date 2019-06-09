package com.cl.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cl.entity.Activity;
import com.cl.entity.Picture;
import com.cl.entity.Vedio;

public interface ActivityService {
	void insert(Activity activity, List<Picture> pictures , Vedio vedio);
	
	
	List<Activity> selectActivityByClubID(Integer clubID);
	
	
	Activity selectActivityByActID(Integer actID);
}
