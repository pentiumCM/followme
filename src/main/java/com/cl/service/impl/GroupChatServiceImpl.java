package com.cl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cl.entity.GroupChat;
import com.cl.mapper.GroupChatMapper;
import com.cl.service.GroupChatService;

@Service
public class GroupChatServiceImpl implements GroupChatService{

	@Autowired
	GroupChatMapper groupChatMapper;
	
	@Override
	public List<GroupChat> selectUserGroupChatByGroupChatID(List<Integer> groupChatIDList) {
		// TODO Auto-generated method stub
		return groupChatMapper.selectUserGroupChatByGroupChatID(groupChatIDList);
	}

}
