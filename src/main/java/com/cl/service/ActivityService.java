package com.cl.service;

import java.util.List;

import com.cl.entity.Activity;
import com.cl.entity.Picture;
import com.cl.entity.Vedio;

public interface ActivityService {
	void insert(Activity activity, List<Picture> pictures , Vedio vedio);
}
