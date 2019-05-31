package com.cl.mapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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

}
