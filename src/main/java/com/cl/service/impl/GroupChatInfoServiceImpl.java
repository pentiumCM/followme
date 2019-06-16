package com.cl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cl.entity.GroupChatInfo;
import com.cl.mapper.GroupChatInfoMapper;
import com.cl.service.GroupChatInfoService;

@Service
public class GroupChatInfoServiceImpl implements GroupChatInfoService{

	@Autowired
	GroupChatInfoMapper groupChatInfoMapper;
	
	@Override
	public List<GroupChatInfo> selectByGroupChatID(Integer groupChatID) {
		// TODO Auto-generated method stub
		return groupChatInfoMapper.selectByGroupChatID(groupChatID);
	}

	@Override
	public void insert(GroupChatInfo groupChatInfo) {
		// TODO Auto-generated method stub
		groupChatInfoMapper.insert(groupChatInfo);
	}

}
