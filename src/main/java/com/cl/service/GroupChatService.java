package com.cl.service;

import java.util.List;

import com.cl.entity.GroupChat;

public interface GroupChatService {
	List<GroupChat> selectUserGroupChatByGroupChatID(List<Integer> groupChatIDList);
}
