package org.sytes.message.match;
/**
 * 比赛报名
 * @author wang
 *
 */
public class EnrollMatchReq {

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
	private long userId;
	private int orederId;
}
