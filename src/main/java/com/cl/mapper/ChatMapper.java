package com.cl.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cl.entity.Chat;

public interface ChatMapper {

	List<Chat> selectGroupChatIDByUserID(@Param("userID") Integer userID);
	
}
