package com.cl.service;

import com.cl.entity.UserInfo;

public interface UserInfoService {

	UserInfo selectByUserID(Integer userID);
}
