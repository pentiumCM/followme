package com.cl.entity;

import lombok.Data;

@Data
public class UserAct {
	private Integer id;
	
	private Integer actID;
	
	private Integer userID;
	
	private String username;
	
	private String actTitle;
	
	private String beginCity;
	
	private String actCost;
	
	private String introduction;

}
