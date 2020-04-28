package org.sytes.data.server.fengyun.place;
/**
 * 场地订单状态
 * @author wang
 *
 */
public enum PlaceOrederState {

	APPLY(1, "申请"),
	OK(2, "已经批准"),
	COMPLETE(3, "完成 "),
	CANCEL(4, "取消"),
	;
	
	//标识id
	public final int id;
	//描述
	public final String desc;
	
	private PlaceOrederState(int id, String desc) {
		this.id = id;
		this.desc = desc;
	}

    @Override
    public String toString() {
        return "" + id;
    }
}
