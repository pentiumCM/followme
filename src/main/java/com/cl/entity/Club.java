package com.cl.entity;

public class Club {
	
	private Integer id;
	
	private String clubLogin;
	
	private String clubName;
	
	private String password;
	
	private String description;
	
	private Integer isDelete;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClubLogin() {
		return clubLogin;
	}

	public void setClubLogin(String clubLogin) {
		this.clubLogin = clubLogin;
	}

	public String getClubName() {
		return clubName;
	}

	public void setClubName(String clubName) {
		this.clubName = clubName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	
	
}
