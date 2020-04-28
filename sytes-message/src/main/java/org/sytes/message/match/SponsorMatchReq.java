package org.sytes.message.match;

public class SponsorMatchReq {
	private long userId;
	private int sportPlaceId;
	private String startTime;
	private String endTime;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getSportPlaceId() {
		return sportPlaceId;
	}
	public void setSportPlaceId(int sportPlaceId) {
		this.sportPlaceId = sportPlaceId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	
}
