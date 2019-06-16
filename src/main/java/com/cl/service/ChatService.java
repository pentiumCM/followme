package com.cl.service;

import java.util.List;

import com.cl.entity.Chat;

public interface ChatService {
	List<Chat> selectGroupChatIDByUserID(Integer userID);
}
