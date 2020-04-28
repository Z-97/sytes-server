package org.sytes.core.actor;

public class RemoteActorName {

	public static String dataRootPath="akka.tcp://sys@127.0.0.1:2551/user/";
	/**
	 * 个人信息
	 */
	public static String userInfoActor="userInfoActor";
	public static String updateUserInfoActor="updateUserInfoActor";
	public static String myPayLogActor="myPayLogActor";
	
	
	/**
	 * 场地
	 */
	public static String addSportPlaceActor="addSportPlaceActor";
	/**
	 * 城市场地
	 */
	public static String citySportPlaceActor="citySportPlaceActor";
	/**
	 * 发起比赛
	 */
	public static String sponsorMatchActor="sponsorMatchActor";
	/**
	 * 报名
	 */
	public static String enrollActor="enrollActor";
	/**
	 * 充值
	 */
	public static String rechargeCallBackActor="rechargeCallBackActor";
	/**
	 * 取消比赛
	 */
	public static String canelMatchActor="canelMatchActor";
}
