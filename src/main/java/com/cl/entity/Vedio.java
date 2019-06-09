package com.cl.entity;

import java.sql.Timestamp;

public class Vedio {
	
	private Integer id;

	private Integer actID;
	
	private Integer actType;
	
	private String vedioPath;

	private String description;

	private Long duration;

	private String vedioName;
	
	private String gifPath;
	
	private String clubName;
	
	private String actCost;
	
	private Timestamp beginTime;
	
	private String beginCity;

	public Integer getActType() {
		return actType;
	}

	public void setActType(Integer actType) {
		this.actType = actType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getActID() {
		return actID;
	}

	public void setActID(Integer actID) {
		this.actID = actID;
	}

	public String getVedioPath() {
		return vedioPath;
	}

	public void setVedioPath(String vedioPath) {
		this.vedioPath = vedioPath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public String getVedioName() {
		return vedioName;
	}

	public void setVedioName(String vedioName) {
		this.vedioName = vedioName;
	}

	public String getGifPath() {
		return gifPath;
	}

	public void setGifPath(String gifPath) {
		this.gifPath = gifPath;
	}

	public String getClubName() {
		return clubName;
	}

	public void setClubName(String clubName) {
		this.clubName = clubName;
	}

	public String getActCost() {
		return actCost;
	}

	public void setActCost(String actCost) {
		this.actCost = actCost;
	}

	public Timestamp getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Timestamp beginTime) {
		this.beginTime = beginTime;
	}

	public String getBeginCity() {
		return beginCity;
	}

	public void setBeginCity(String beginCity) {
		this.beginCity = beginCity;
	}
	

}
