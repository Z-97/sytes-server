package org.sytes.data.jr;

/**
 * 组员级别
 * 
 * @author wang
 *
 */
public enum GroupLevel {
	V2(1,1, "V2级"), 
	V1(2,1, "V1级"),
	A(3, 2,"A级"), 
	B(4, 4,"B级"), 
	C(5, 8,"C级"), 
	D(6, 16,"D级"),
	;
	
	/**
	 * 当前等级
	 */
	public final int level;
    /**
     * 本级数量
     */
	public final int num;
	
	// 描述
	public final String desc;

	private GroupLevel(int level,int num, String desc) {
		this.level = level;
		this.num=num;
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "" + level;
	}
}
