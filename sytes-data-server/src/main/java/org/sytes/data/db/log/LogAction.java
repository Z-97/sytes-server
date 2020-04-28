package org.sytes.data.db.log;

/**
 * 由系统和在该系统所做的操作构成
 * @author wang
 */
public enum LogAction {
	EXCHANGE_CASH(100, "兑换"),//兑换现金
	SPALCE_INCOME(200, "场地收入"),
	;
	
	//标识id
	public final int id;
	//描述
	public final String desc;
	
	private LogAction(int id, String desc) {
		this.id = id;
		this.desc = desc;
	}

    @Override
    public String toString() {
        return "" + id;
    }
}
