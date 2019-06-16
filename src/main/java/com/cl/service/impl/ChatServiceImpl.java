package com.cl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cl.entity.Chat;
import com.cl.mapper.ChatMapper;
import com.cl.service.ChatService;

@Service
public class ChatServiceImpl implements ChatService{

	@Autowired
	ChatMapper chatMapper;
	
	@Override
	public List<Chat> selectGroupChatIDByUserID(Integer userID) {
		// TODO Auto-generated method stub
		return chatMapper.selectGroupChatIDByUserID(userID);
	}

}
