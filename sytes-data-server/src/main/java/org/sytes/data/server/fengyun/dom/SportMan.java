package org.sytes.data.server.fengyun.dom;
/**
 * 参赛人
 * @author wang
 *
 */
public class SportMan {
	private long userId;
	private String userName;
	private String baoMingDate;
	private int mySportLogId;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getBaoMingDate() {
		return baoMingDate;
	}
	public void setBaoMingDate(String baoMingDate) {
		this.baoMingDate = baoMingDate;
	}
	public int getMySportLogId() {
		return mySportLogId;
	}
	public void setMySportLogId(int mySportLogId) {
		this.mySportLogId = mySportLogId;
	}
}
