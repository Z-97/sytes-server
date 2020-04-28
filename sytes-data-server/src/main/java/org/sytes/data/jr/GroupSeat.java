package org.sytes.data.jr;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GroupSeat {
	private int uid;
	private String userName;
	private Date enterTime;
	/**
	 * 位置编号
	 */
	private int groupNumber;
	/**
	 * 上级位置编号
	 */
	private int groupSuperiorNumber;
	private String groupSuperiorName;
	/**
	 * 等级
	 */
	private GroupLevel level;
	/**
	 * 左节点
	 */
	private GroupSeat leftfGroupSeat;
	private GroupSeat rightGroupSeat;
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getEnterTime() {
		return enterTime;
	}
	public void setEnterTime(Date enterTime) {
		this.enterTime = enterTime;
	}
	public int getGroupNumber() {
		return groupNumber;
	}
	public void setGroupNumber(int groupNumber) {
		this.groupNumber = groupNumber;
	}
	public int getGroupSuperiorNumber() {
		return groupSuperiorNumber;
	}
	public void setGroupSuperiorNumber(int groupSuperiorNumber) {
		this.groupSuperiorNumber = groupSuperiorNumber;
	}
	public GroupLevel getLevel() {
		return level;
	}
	public void setLevel(GroupLevel level) {
		this.level = level;
	}
	private void reset() {
		this.uid = 0;
		this.userName = null;
		this.enterTime = null;
	}
	public GroupSeat getLeftfGroupSeat() {
		return leftfGroupSeat;
	}
	public void setLeftfGroupSeat(GroupSeat leftfGroupSeat) {
		this.leftfGroupSeat = leftfGroupSeat;
	}
	public GroupSeat getRightGroupSeat() {
		return rightGroupSeat;
	}
	public void setRightGroupSeat(GroupSeat rightGroupSeat) {
		this.rightGroupSeat = rightGroupSeat;
	}
	public String getGroupSuperiorName() {
		return groupSuperiorName;
	}
	public void setGroupSuperiorName(String groupSuperiorName) {
		this.groupSuperiorName = groupSuperiorName;
	}
	public String toUserDescString() {
		String uDesc="当前用户id: "+this.uid+" 等级:"+level.desc+"  位置:"+this.groupNumber ;
		String lowerDes="  ";
		if(this.leftfGroupSeat!=null&&leftfGroupSeat.uid!=0) {
			lowerDes+="id: "+leftfGroupSeat.uid+" 等级:"+leftfGroupSeat.level.desc+" 位置: "+leftfGroupSeat.groupNumber;;
		}
		if(this.rightGroupSeat!=null&&rightGroupSeat.uid!=0) {
			lowerDes+=";"+"id: "+rightGroupSeat.uid+" 等级:"+rightGroupSeat.level.desc+" 位置:"+rightGroupSeat.groupNumber;
		}
		return uDesc+"直接下级：{"+lowerDes+"}";
	}
	public String toDescString() {
		String lowerDes="";
		if(this.leftfGroupSeat!=null) {
			lowerDes+=leftfGroupSeat.level.desc+"位置:"+leftfGroupSeat.groupNumber;
		}
		if(this.rightGroupSeat!=null) {
			lowerDes+=rightGroupSeat.level.desc+"位置:"+rightGroupSeat.groupNumber;
		}
		return this.level.desc+"位置："+this.groupNumber +"直接下级：{"+lowerDes+"}";
	}
	public String toString() {
		return this.level.desc+"位置："+this.groupNumber ;
	}
	public List<GroupSeat> getGroupSeatByLevel(int level){
		List<GroupSeat> gsList=new ArrayList<>();
		//postOrder(this.getLeftfGroupSeat(),gsList,level);
		//postOrder(this.getRightGroupSeat(),gsList,level);
		return gsList;
		 
	}
	public List<Integer> getGroupUidSeatByLevel(int level){
		List<Integer> gsList=new ArrayList<>();
	    
		postOrder(this,gsList,level);
		return gsList;
		 
	}
	public void postOrder(GroupSeat current,List<Integer> gsList,int level) {
		if (current != null&&current.getLevel().level<=level) {
			if(current.getLevel().level==level) {
				gsList.add(current.getUid());
			}else {
				if(current.getLeftfGroupSeat()!=null) {
					
					postOrder(current.getLeftfGroupSeat(),gsList,level);
				}
			
				if(current.getRightGroupSeat()!=null) {
					postOrder(current.getRightGroupSeat(),gsList,level);
				}
			}
			
			
		}
		}
	/**
	 * 清除GroupSeat,仅在裂变时使用
	 * @throws ParseException 
	 */
	public void destroy() throws ParseException {
		if(this.groupNumber!=0) {
			 throw new ParseException(
                     "不支持对非V2GroupSeat销毁", 0);
		}
		reset();
		destroy(leftfGroupSeat);
		destroy(rightGroupSeat);
		
	}
    private void destroy(GroupSeat gs) {
    	if(gs!=null) {
    		gs.reset();
    		if(gs.getLeftfGroupSeat()!=null) {
        		destroy(gs.getLeftfGroupSeat());
        		
        	}
    		if(gs.getRightGroupSeat()!=null) {
        		destroy(gs.getRightGroupSeat());
        	}
    	}
    	
		
	}
	
}
