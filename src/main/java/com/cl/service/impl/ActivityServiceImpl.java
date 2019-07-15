package com.cl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cl.entity.Activity;
import com.cl.entity.Picture;
import com.cl.entity.Vedio;
import com.cl.mapper.ActivityMapper;
import com.cl.mapper.PictureMapper;
import com.cl.mapper.VedioMapper;
import com.cl.service.ActivityService;

@Service
public class ActivityServiceImpl implements ActivityService{

	@Autowired
	ActivityMapper activityMapper;
	
	@Autowired
	PictureMapper pictureMapper;
	
	@Autowired
	VedioMapper vedioMapper;
	
	@Transactional
	@Override
	public void insert(Activity activity , List<Picture> pictures , Vedio vedio) {
		// TODO Auto-generated method stub
		activityMapper.insert(activity);
		for (Picture picture : pictures) {
			picture.setActID(activity.getId());
		}
		pictureMapper.insert(pictures);
		vedio.setActID(activity.getId());
		vedioMapper.insert(vedio);
	}

	@Override
	public List<Activity> selectActivityByClubID(Integer clubID) {
		// TODO Auto-generated method stub
		return activityMapper.selectActivityByClubID(clubID);
	}

	@Override
	public Activity selectActivityByActID(Integer actID) {
		// TODO Auto-generated method stub
		return activityMapper.selectActivityByActID(actID);
	}

	@Override
	public List<Activity> getAllActivities() {
		// TODO Auto-generated method stub
		return activityMapper.getAllActivities();
	}
	
}
