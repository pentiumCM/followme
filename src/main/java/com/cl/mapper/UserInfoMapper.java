package com.cl.mapper;

import org.apache.ibatis.annotations.Param;

import com.cl.entity.UserInfo;

public interface UserInfoMapper {

	UserInfo selectByUserID(@Param("userID") Integer userID);
}
