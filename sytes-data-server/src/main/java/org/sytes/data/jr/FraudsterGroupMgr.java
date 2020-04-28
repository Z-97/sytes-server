package org.sytes.data.jr;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class FraudsterGroupMgr {

	private ConcurrentHashMap<Integer, FraudsterGroup> fraudsterGroupList = new ConcurrentHashMap<>();
	private ScheduledThreadPoolExecutor  scheduled = new ScheduledThreadPoolExecutor(1);
	
	private List<Integer> userList=new ArrayList<>();
	private List<Integer> waitingUserList=new ArrayList<>();
	private static final AtomicInteger FraudsterGroupSEED = new AtomicInteger(0);
	/**
	 * 检查创建第一个分组
	 */
	public void init() {
		for(int i=0;i<96;i++) {
			userList.add(IdGenerator.nextId());
			
		}
		if (fraudsterGroupList.size() == 0) {                  
			crateFraudsterGroup( 0);                                 
			
		}

		scheduled.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				cheackFraudsterGroupfission();
				
			}
			
		} , 0,3, TimeUnit.SECONDS);
		
		scheduled.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				
				addUser();
			}
	
		} , 0,100, TimeUnit.MILLISECONDS);
		scheduled.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				monitorFraudsterGroup();
			}
	
		} , 0,60, TimeUnit.SECONDS);
	}
	private void addUser() {
		if(userList.size()>0) {
			//int userSize=random.nextInt(userList.size());
			int uid=userList.get(0);
			FraudsterGroup fg=this.getFraudsterGroupNotFull();
			if(fg!=null) {
					boolean add=fg.addGroupMember(uid);
					if(add) {
						userList.remove(0);
					}
			}
			
		}
		
	}

	/**
	 * 1.移除的V2可能还在分组，
	 * 2.分组不紧密
	 * 3.检查分组V2的数量;
	 */
	private void monitorFraudsterGroup() {
		long freeM= Runtime.getRuntime().freeMemory() / 1024  / 1024;
        long totalM= Runtime.getRuntime().totalMemory() / 1024  / 1024; 
        
		System.out.println("总分组:"+fraudsterGroupList.size()+"  待选用户数量"+this.userList.size()+" 共同赢得队列:"+waitingUserList.size());
		System.out.println("总内存:"+totalM+"M 已经使用"+(totalM-freeM)+"M--------------分组监控信息--------------------------");
		for(FraudsterGroup fg:fraudsterGroupList.values()) {
			System.out.println("分组名:"+fg.getGropName());
//			System.out.println("分组号:"+fg.getGroupId());
//			System.out.println("裂变来源分组好:"+fg.getBaseGroupId());
			System.out.println("当前人数:"+fg.groupMemberNum());
//			System.out.println("成员信息:");
//			for(int f=0;f<fg.memberNum;f++) {
//				GroupSeat tmp=fg.getGroupSeatByNumber(f);
//				if(tmp.getUid()>0) {
//					System.out.println(tmp.toUserDescString());
//				}
//				
//			}
		}
	}
	private void fraudsterGroupinfo(FraudsterGroup fg,String msg) {
	      	System.out.println(msg+"------------------------------");
			System.out.println("分组名:"+fg.getGropName());
			System.out.println("分组号:"+fg.getGroupId());
			System.out.println("裂变来源分组好:"+fg.getBaseGroupId());
			System.out.println("当前人数:"+fg.groupMemberNum());
			System.out.println("成员信息:");
			for(int f=0;f<fg.memberNum;f++) {
				GroupSeat tmp=fg.getGroupSeatByNumber(f);
				if(tmp.getUid()>0) {
					System.out.println(tmp.toUserDescString());
				}
				
			}
		
	}
	private FraudsterGroup getFraudsterGroupNotFull() {
		for(FraudsterGroup fg:fraudsterGroupList.values()) {
			if(!fg.isFull()) {
				return fg;
			}
		}
		
		
		return null;
	}
	/**
	 * 裂变检查
	 */
	private void cheackFraudsterGroupfission() {
		boolean isMore=true;
		FraudsterGroup moreFg = null;
		for(FraudsterGroup fg:fraudsterGroupList.values()) {
			moreFg=fg;
			if(!fg.isFull()) {
				isMore=false;
			}
		}
		if(isMore&&moreFg!=null&&userList.size()>0) {
			fraudsterGroupfission(moreFg);
		}
		//System.out.println(Thread.currentThread().getName()+"------------>"+fraudsterGroupList.size()+"fg:"+fg.getGropName()+" "+fg.groupMemberNum());
	
	}
	/**
	 * 创建空组
	 * @param gropName
	 * @param groupId
	 * @param baseGroupId
	 * @return
	 */
	private FraudsterGroup crateFraudsterGroup(int baseGroupId) {
	
		long startTime=System.currentTimeMillis();
		FraudsterGroup fg = new FraudsterGroup();
		fg.setCrateTime(new Date());
		fg.setGroupId(FraudsterGroupSEED.incrementAndGet());
		fg.setGropName("默认"+fg.getGroupId());
		fg.setBaseGroupId(baseGroupId);
		GroupSeat rootGroupSeat = null;
		for (GroupLevel groupLevel : GroupLevel.values()) {
			if (rootGroupSeat == null) {
				rootGroupSeat = new GroupSeat();
				rootGroupSeat.setLevel(groupLevel);
				rootGroupSeat.setGroupNumber(fg.getGroupSeatNumber());
				fg.setRootGroupSeat(rootGroupSeat);
				fg.add(rootGroupSeat);
			} else {
				initGroupSeat(fg,rootGroupSeat, groupLevel);
			}

		}
		
		//postOrder(rootGroupSeat);
		//System.out.println(fg.getGsList().size());
		for(int f=0;f<fg.memberNum;f++) {
			GroupSeat tmp=fg.getGroupSeatByNumber(f);
			//System.out.println(tmp.toDescString());
		}
//		GroupSeat tmp1=fg.getGroupSeatByNumber(2);
//		 List<GroupSeat> gsArr=tmp1.getGroupSeatByLevel(GroupLevel.C.level);
//		 if(!gsArr.isEmpty()) {
//			 for(GroupSeat t:gsArr) {
//				 //System.out.println(tmp1.toString()+ "|"+t.toString());
//			 }
//		 }
		//
		fraudsterGroupList.put(fg.getGroupId(), fg);
		System.out.println("创建分组成功:"+fg.getGropName()+"分组总数:"+fraudsterGroupList.size()+"用时"+(System.currentTimeMillis()-startTime));
		return fg;
	}

	
	private void initGroupSeat(FraudsterGroup fg,GroupSeat currentGroupSeat, GroupLevel groupLevel) {
		if (currentGroupSeat != null) {
			initGroupSeat(fg,currentGroupSeat.getLeftfGroupSeat(), groupLevel);
			initGroupSeat(fg,currentGroupSeat.getRightGroupSeat(), groupLevel);
			if (groupLevel.level - currentGroupSeat.getLevel().level == 1) {
				if (groupLevel.num <= 1) {
					if (currentGroupSeat.getLeftfGroupSeat() == null) {
						GroupSeat leftfGroupSeat = new GroupSeat();
						leftfGroupSeat.setLevel(groupLevel);
						leftfGroupSeat.setGroupNumber(fg.getGroupSeatNumber());
						leftfGroupSeat.setGroupSuperiorName(
								currentGroupSeat.getLevel().desc + currentGroupSeat.getGroupNumber());
						currentGroupSeat.setLeftfGroupSeat(leftfGroupSeat);
						fg.add(leftfGroupSeat);
					}

				} else {
					if (currentGroupSeat.getLeftfGroupSeat() == null) {
						GroupSeat leftfGroupSeat = new GroupSeat();
						leftfGroupSeat.setLevel(groupLevel);
						leftfGroupSeat.setGroupNumber(fg.getGroupSeatNumber());
						leftfGroupSeat.setGroupSuperiorName(
								currentGroupSeat.getLevel().desc + currentGroupSeat.getGroupNumber());
						currentGroupSeat.setLeftfGroupSeat(leftfGroupSeat);
						fg.add(leftfGroupSeat);

					}
					if (currentGroupSeat.getRightGroupSeat() == null) {
						GroupSeat rightGroupSeat = new GroupSeat();
						rightGroupSeat.setLevel(groupLevel);
						rightGroupSeat.setGroupNumber(fg.getGroupSeatNumber());
						rightGroupSeat.setGroupSuperiorName(currentGroupSeat.getLevel().desc + currentGroupSeat.getGroupNumber());
						currentGroupSeat.setRightGroupSeat(rightGroupSeat);
						fg.add(rightGroupSeat);
					}

				}
			}

		}

	}

	/**
	 * 分组裂变
	 * @param fraudsterGroup
	 */
	private void fraudsterGroupfission(FraudsterGroup fraudsterGroup) {
		if (!fraudsterGroup.isFull()) {
			return;
		}
		//fraudsterGroupinfo(fraudsterGroup,"分组前");
		// V2d等级
		GroupSeat rootGroupSeat = fraudsterGroup.getRootGroupSeat();
		waitingUserList.add(rootGroupSeat.getUid());
		//创建新的分组数据
		List<Integer> A1List=new ArrayList<>();
		List<Integer> A2List=new ArrayList<>();
		GroupSeat V1GroupSeat=rootGroupSeat.getLeftfGroupSeat();
		A1List.add(V1GroupSeat.getUid());
		A2List.add(V1GroupSeat.getUid());
	
		GroupSeat A1GroupSeat=V1GroupSeat.getLeftfGroupSeat();
		GroupSeat A2GroupSeat=V1GroupSeat.getRightGroupSeat();
		for (GroupLevel groupLevel : GroupLevel.values()) {
			 List<Integer> A1Uid=A1GroupSeat.getGroupUidSeatByLevel(groupLevel.level);
			 A1List.addAll(A1Uid);
			 List<Integer> A2Uid=A2GroupSeat.getGroupUidSeatByLevel(groupLevel.level);
			 A2List.addAll(A2Uid);
		}
		//清除旧数据
		fraudsterGroup.reset();
		FraudsterGroup newFraudsterGroup=crateFraudsterGroup(fraudsterGroup.getGroupId());
	
		fraudsterGroup.addGroupMember(A1List);
		newFraudsterGroup.addGroupMember(A2List);
		//System.out.println(Thread.currentThread().getName()+fraudsterGroup.getGropName()+":+"+fraudsterGroup.groupMemberNum());
		//System.out.println(Thread.currentThread().getName()+newFraudsterGroup.getGropName()+":+"+newFraudsterGroup.groupMemberNum());
		//fraudsterGroupinfo(fraudsterGroup,"新分组");
		//fraudsterGroupinfo(newFraudsterGroup,"新分组");
	}

	int anum = 0;
	int bnum = 0;
	int cnum = 0;
	int dnum = 0;
	int v1num = 0;
	int v2num = 0;

	// 后序遍历
	public void postOrder(GroupSeat current) {

		if (current != null) {

			if (current.getLevel().level == GroupLevel.B.level) {
				bnum++;
			}
			if (current.getLevel().level == GroupLevel.A.level) {
				anum++;
			}
			if (current.getLevel().level == GroupLevel.C.level) {
				cnum++;
			}
			if (current.getLevel().level == GroupLevel.D.level) {
				dnum++;
			}
			if (current.getLevel().level == GroupLevel.V1.level) {
				v1num++;
			}
			if (current.getLevel().level == GroupLevel.V2.level) {
				v2num++;
			}
			postOrder(current.getLeftfGroupSeat());
			postOrder(current.getRightGroupSeat());
			System.out.println(current.getLevel().desc + "" + current.getGroupNumber() + "----->"
					+ current.getGroupSuperiorName());
		}
		// System.out.println(GroupLevel.V2.desc+":"+v2num);
		// System.out.println(GroupLevel.V1.desc+":"+v1num);
		// System.out.println(GroupLevel.A.desc+":"+anum);
		// System.out.println(GroupLevel.B.desc+":"+bnum);
		// System.out.println(GroupLevel.C.desc+":"+cnum);
		// System.out.println(GroupLevel.D.desc+":"+dnum);
	}

}
