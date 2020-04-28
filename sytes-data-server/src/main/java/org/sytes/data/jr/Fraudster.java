package org.sytes.data.jr;
public class Fraudster {

	public static void main(String[] args) {
		
//		FraudsterGroupMgr fraudsterGroupMgr=new FraudsterGroupMgr();
//		fraudsterGroupMgr.init();
		
		for(GroupLevel groupLevel :GroupLevel.values()) {
			System.out.println(""+(groupLevel.desc.hashCode() & 15));
		}
		
	}

}
