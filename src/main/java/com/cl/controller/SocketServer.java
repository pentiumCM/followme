package com.cl.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cl.entity.GroupChatInfo;
import com.cl.entity.UserInfo;
import com.cl.service.GroupChatInfoService;
import com.cl.service.UserInfoService;
import com.cl.service.impl.GroupChatInfoServiceImpl;
import com.cl.service.impl.UserInfoServiceImpl;

@Controller
@ServerEndpoint(value="/chat/{userID}")
public class SocketServer {

	
	
	private Map<Integer, Session> sessionMap = new HashMap<Integer, Session>();
	
	private Integer userID = null; 
	
	
	
	  private static GroupChatInfoService groupChatInfoService;
	  
	  private static UserInfoService userInfoService;
	  
	  @Autowired public void setGroupChatInfoService(GroupChatInfoService
	  groupChatInfoService) { SocketServer.groupChatInfoService =
	  groupChatInfoService; }
	  
	  @Autowired public void setUserInfoService(UserInfoService userInfoService) {
	  SocketServer.userInfoService = userInfoService; }
	 
	
	/*
	 * @Autowired GroupChatInfoService groupChatInfoService;
	 * 
	 * 
	 * @Autowired UserInfoService userInfoService;
	 */
	
	
	/**
	 * 连接建立成功调用的方法
	 *
	 * @param session
	 *            可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 * @throws EncodeException
	 * @throws IOException
	 */
	@OnOpen
	public void onOpen(@PathParam(value = "userID") String param, Session session, EndpointConfig config)
			throws Exception {
		/**
		 * 开启时用户一定不会存在在sessionMap中
		 */
		System.out.println("账号标识" + param);
		userID = Integer.valueOf(param);
		sessionMap.put(userID, session);// 加入map中
	}

	
	/**
	 * 收到客户端消息后调用的方法
	 *
	 * @param message
	 *            客户端发送过来的消息
	 * @param session
	 *            可选的参数
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	@OnMessage
	public void onMessage(String message, Session session) throws IOException {
		JSONObject strj = new JSONObject(message);
		System.out.println("来自客户端的消息:" + strj);
		Timestamp contentDate = Timestamp.valueOf(strj.getString("contentDate"));
		String content = strj.getString("content");
		Integer groupChatID = Integer.valueOf(strj.getInt("groupChatID"));
		GroupChatInfo groupChatInfo = new GroupChatInfo();
		groupChatInfo.setUserID(userID);
		groupChatInfo.setContentDate(contentDate);
		groupChatInfo.setContent(content);
		groupChatInfo.setGroupChatID(groupChatID);
		//插入消息
		groupChatInfoService.insert(groupChatInfo);
		//
		//获取用户名和头像
		UserInfo userInfo = userInfoService.selectByUserID(userID);
		strj.put("username", userInfo.getUsername());
		strj.put("profilePicture", userInfo.getProfilePicture());
		
		//发送消息给前台
		sessionMap.get(userID).getBasicRemote().sendText(strj.toString());
		
	}
	
	
	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		System.out.println("socket关闭");
		sessionMap.remove(userID); // 从set中删除
	}
	
	/**
	 * 发生错误时调用
	 *
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("发生错误");
		error.printStackTrace();
	}
	
}
