package org.sytes.data.jr;
/**
 * 成员信息
 * @author wang
 *
 */
public class GroupMember {
	/**
	 * ID
	 */
	private int id;
	/**
	 * 邀请人
	 */
	private int Inviter;
	/**
	 * 邀请人数，有限制
	 */
	private int InviterNum;
	/**
	 * 分组所在位置
	 */
	private GroupLevel level;
	/**
	 * 分组id
	 */
	private int groupId;
	/**
	 * 分组上级id;
	 */
	private int groupSuperiorID;
	/**
	 * 位置编号
	 */
	private int groupNumber;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getInviter() {
		return Inviter;
	}
	public void setInviter(int inviter) {
		Inviter = inviter;
	}
	public int getInviterNum() {
		return InviterNum;
	}
	public void setInviterNum(int inviterNum) {
		InviterNum = inviterNum;
	}
	public GroupLevel getLevel() {
		return level;
	}
	public void setLevel(GroupLevel level) {
		this.level = level;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getGroupSuperiorID() {
		return groupSuperiorID;
	}
	public void setGroupSuperiorID(int groupSuperiorID) {
		this.groupSuperiorID = groupSuperiorID;
	}
	public int getGroupNumber() {
		return groupNumber;
	}
	public void setGroupNumber(int groupNumber) {
		this.groupNumber = groupNumber;
	}
}
