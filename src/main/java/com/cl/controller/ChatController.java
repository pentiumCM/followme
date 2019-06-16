package com.cl.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.cl.constants.Constants;
import com.cl.entity.Chat;
import com.cl.entity.GroupChat;
import com.cl.entity.User;
import com.cl.resp.CommonResp;
import com.cl.service.ChatService;
import com.cl.service.GroupChatService;

@RestController
@RequestMapping("/chat")
public class ChatController {

	@Autowired
	ChatService chatService;

	@Autowired
	GroupChatService groupChatService;


	/**
	 * 查询一个用户所在的所有聊天组，以及每个聊天组的所有聊天信息
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getUserAllChatLog", method = RequestMethod.POST)
	public String getUserAllChatLog(HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute("USER_SESSION");

		
		//登陆成功有session时，使用下面代码
		 Integer userID = user.getId();

		//Integer userID = Integer.valueOf(request.getParameter("userID"));

		
		//获取用户所在聊天组的id
		List<Chat> chatList = chatService.selectGroupChatIDByUserID(userID);
		List<Integer> groupChatIDList = new ArrayList<Integer>();
		for (Chat chat : chatList) {
			groupChatIDList.add(chat.getGroupChatID());
		}

		//根据聊天组的id，获取每个聊天组的聊天信息
		List<GroupChat> groupChatList = groupChatService.selectUserGroupChatByGroupChatID(groupChatIDList);
		CommonResp commonResp = new CommonResp(Constants.SUCCESS_CODE, "success", groupChatList);
		return JSON.toJSONString(commonResp);
	}

}
