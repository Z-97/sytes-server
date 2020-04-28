package org.sytes.message.pay;
/**
 * 支付
 * @author wang
 *
 */
public class RechargeReq {

	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public int getMySprotlogId() {
		return mySprotlogId;
	}
	public void setMySprotlogId(int mySprotlogId) {
		this.mySprotlogId = mySprotlogId;
	}
	private long userId;
	private int gold;
	private int mySprotlogId;
}
