package org.sytes.data.jr;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 分组
 * @author wang
 *
 */
public class FraudsterGroup {
	private int groupId;
	/**
	 * 裂变祖id,初始第一个组为0;
	 */
	private int baseGroupId;
	private String gropName;
	private Date crateTime;
	private int number = 0;
	private List<GroupSeat> gsList=new ArrayList<>();
	public final int memberNum=32;
	/**
	 * 位置数据
	 */
	private GroupSeat rootGroupSeat;
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getBaseGroupId() {
		return baseGroupId;
	}
	public void setBaseGroupId(int baseGroupId) {
		this.baseGroupId = baseGroupId;
	}
	public String getGropName() {
		return gropName;
	}
	public void setGropName(String gropName) {
		this.gropName = gropName;
	}
	public Date getCrateTime() {
		return crateTime;
	}
	public void setCrateTime(Date crateTime) {
		this.crateTime = crateTime;
	}
	public List<GroupSeat> getGsList() {
		return gsList;
	}
	public void setGsList(List<GroupSeat> gsList) {
		this.gsList = gsList;
	}
	/**
	 * 是否满员
	 * @return
	 */
	public boolean isFull() {
		for(GroupSeat gs:gsList) {
			if(gs.getUid()==0) {
				return false;
			}
		}
		return true;
	}
	public GroupSeat getRootGroupSeat() {
		return rootGroupSeat;
	}
	public void setRootGroupSeat(GroupSeat rootGroupSeat) {
		this.rootGroupSeat = rootGroupSeat;
	}
	public void reset() {
		this.number=0;
		try {
			rootGroupSeat.destroy();
		} catch (ParseException e) {
			
		}
	}
	public void setNumber(int number) {
		this.number = number;
	}

	public int getGroupSeatNumber() {
		return number++;
	}
	public void add(GroupSeat groupSeat) {
		gsList.add(groupSeat);
		
	}
	
	public GroupSeat getGroupSeatByNumber(int number) {
		GroupSeat groupSeat = null;
		for(GroupSeat gs:gsList) {
			if(gs.getGroupNumber()==number) {
				return gs;
			}
		}
		return groupSeat;
		
	}
	/**
	 * 添加成员
	 * @param a1List
	 */
	public void addGroupMember(List<Integer>a1List ) {
		int size=a1List.size();
		for(int i=0;i<size;i++) {
			this.addGroupMember(a1List.get(i));
		}
		
	}
	public boolean addGroupMember(int  uid) {
		if(isFull()) {
			return false;
		}
		for(int i=0;i<this.memberNum;i++) {
			GroupSeat gs=getGroupSeatByNumber(i);
			if(gs.getUid()==0) {
				gs.setEnterTime(new Date());
				gs.setUid(uid);
				return true;
			}
		}
		return false;
	}
	
	public int groupMemberNum() {
		int groupMemberNum=0;
		for(int i=0;i<this.memberNum;i++) {
			GroupSeat gs=getGroupSeatByNumber(i);
			if(gs.getUid()>0) {
				groupMemberNum++;
			}
		}
		return groupMemberNum;
	}
}
