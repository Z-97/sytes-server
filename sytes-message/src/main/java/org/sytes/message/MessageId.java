package org.sytes.message;
/**
 * 客户端协议
 * @author wang
 *
 */
public class MessageId {
	/**
	 * 返回码
	 */
	public static int error=0;
	public static int success=1;
	/**
	 * 登录
	 */
	public static String loginReq="/loginReq";
	/**
	 * 设置个人信息
	 */
	public static String updateUserReq="/UpdateUserReq";
	
	
	/**
	 *获取个人支付列表
	 */
	public static String payLogActor="/PayLogActor";
	/**
	 * 入住申请
	 */
	public static String sportPlaceApplyActorReq="/SportPlaceApplyActorReq";
	/**
	 * 获取城市的场地
	 */
	public static String getCitySportPlaceReq="/getCitySportPlaceReq";
	/**
	 * 发起比赛
	 */
	public static String startMatchActor="/startMatchActorReq";
	
	/**
	 * 获取用户信息
	 */
	public static int loadUserInfo=50001;
	public static String enterMatchReq="/enterMatchReq";
	public static String cancelMatchReq="/canelMatchReq";
	
	public static String rechargeReq="/rechargeReq";
	
}

