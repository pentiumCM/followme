package com.cl.mapper;

import org.apache.ibatis.annotations.Param;

import com.cl.entity.Admin;

public interface AdminMapper {

	Admin selectAdmin(@Param("login") String login,@Param("password") String password);
	
}
