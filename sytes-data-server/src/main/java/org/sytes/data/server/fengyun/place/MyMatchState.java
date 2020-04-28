package org.sytes.data.server.fengyun.place;

/**
 * 0未支付1报名中2完成3已结退费
 * 
 * @author wang
 *
 */
public enum MyMatchState {

	WAIT_PAY(1, "申请"), OK(2, "已经报名"), COMPLETE(3, "完成 "), CANCEL(4, "取消"),;
	// 标识id
	public final int state;
	// 描述
	public final String desc;

	private MyMatchState(int state, String desc) {
		this.state = state;
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "" + state;
	}
}
