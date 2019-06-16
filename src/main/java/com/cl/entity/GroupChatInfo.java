package com.cl.entity;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class GroupChatInfo {

	private Integer id;
	
	private Integer groupChatID;
	
	private Integer userID;
	
	private Timestamp contentDate;
	
	private String content;
	
	private String username;
	
	private String profilePicture;
	
	
}
