package com.cl.entity;

public class Vedio {
	
	private Integer id;

	private Integer actID;
	
	private String vedioPath;

	private String description;

	private Long duration;

	private String vedioName;
	
	private String gifPath;
	
	private String clubName;
	
	private String actCost;
	

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

}
