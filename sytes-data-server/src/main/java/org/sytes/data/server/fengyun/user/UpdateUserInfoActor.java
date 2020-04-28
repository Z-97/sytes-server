package org.sytes.data.server.fengyun.user;

import org.sytes.data.server.fengyun.dom.UserInfo;
import com.alibaba.fastjson.JSONObject;

import akka.actor.UntypedActor;

/**
 * 更新个人信息
 * @author wang
 *
 */
public class UpdateUserInfoActor extends UntypedActor {

	private UserManager umgr;
	public UpdateUserInfoActor(UserManager umgr) {
		super();
		this.umgr = umgr;
	}
	@Override
	public void onReceive(Object msg) throws Exception {
		if(msg instanceof String) {
			String txt=(String) msg;
			UserInfo user=JSONObject.parseObject(txt, UserInfo.class);
			umgr.updateUserInfo(user, getSender());
		}

	}

}
