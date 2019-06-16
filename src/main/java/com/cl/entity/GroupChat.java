package com.cl.entity;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class GroupChat {

	private Integer id;
	
	private Integer actID;
	
	private Timestamp createTime;
	
	private String groupChatName;
	
	private List<GroupChatInfo> groupChatInfoList;
	
	
}
