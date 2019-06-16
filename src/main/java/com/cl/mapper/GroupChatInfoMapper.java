package com.cl.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cl.entity.GroupChatInfo;

public interface GroupChatInfoMapper {

	List<GroupChatInfo> selectByGroupChatID(@Param("groupChatID")Integer groupChatID);
	
	void insert(GroupChatInfo groupChatInfo);
	
}
