package org.sytes.message.match;

public class CancelMatchReq {
	private long userId;
	private int orederId;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getOrederId() {
		return orederId;
	}
	public void setOrederId(int orederId) {
		this.orederId = orederId;
	}
	
}
