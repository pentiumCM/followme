package com.cl.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cl.entity.GroupChat;

public interface GroupChatMapper {

	List<GroupChat> selectUserGroupChatByGroupChatID(@Param("groupChatIDList")List<Integer> groupChatIDList);
	
}
