package org.sytes.data.server.fengyun.user;

import org.sytes.message.login.LoginReq;

import com.alibaba.fastjson.JSONObject;

import akka.actor.UntypedActor;
/**
 * 获取个人信息
 * @author wang
 *
 */
public class UserInfoActor extends UntypedActor {
	private UserManager umgr;
	public UserInfoActor(UserManager umgr) {
		super();
		this.umgr = umgr;
	}
	
	@Override
	public void onReceive(Object msg) throws Exception {
		
		if(msg instanceof String) {
			String txt=(String) msg;
			LoginReq req=JSONObject.parseObject(txt, LoginReq.class);
			umgr.selectUserByUserName(req.getName(), req.getPwd(),getSender());
		}
		
	}

}
