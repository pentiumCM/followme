package com.cl.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cl.entity.UserAct;

public interface UserActMapper {

	List<UserAct> selectAllUsersByClubID(@Param("clubID") Integer clubID);
	
}
