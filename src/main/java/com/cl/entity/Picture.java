package com.cl.entity;

public class Picture {
	
	private Integer id;
	
	private String pictureUrl;
	
	private Integer actID;

	private Integer actType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public Integer getActID() {
		return actID;
	}

	public void setActID(Integer actID) {
		this.actID = actID;
	}

	public Integer getActType() {
		return actType;
	}

	public void setActType(Integer actType) {
		this.actType = actType;
	}
	
	
	
}
