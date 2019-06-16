package com.cl.service;

import java.util.List;

import com.cl.entity.GroupChatInfo;

public interface GroupChatInfoService {
	List<GroupChatInfo> selectByGroupChatID(Integer groupChatID);

	void insert(GroupChatInfo groupChatInfo);
}
