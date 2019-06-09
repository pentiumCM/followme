package com.cl.mapper;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cl.entity.Activity;
import com.cl.entity.User;

public class UserMapperTest extends BaseTest {

	@Autowired
	private UserMapper userMapper;

	@Test
	public void selectByUserName() {
		User user = userMapper.selectByUserName("dylan");
		System.out.println(user);
		logger.debug(user.toString());
	}
	
	
	@Autowired
	private ActivityMapper activityMapper;

	@Test
	public void test1() {
		List<Activity> activityList = activityMapper.selectActivityByClubID(1);
		for (Activity activity : activityList) {
			
			System.out.println(activity);
		}
	}
	
	

}
