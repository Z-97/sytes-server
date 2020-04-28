package org.sytes.data.server.fengyun.user;
import java.util.List;

import org.sytes.data.server.fengyun.dom.MyPayLog;
import org.sytes.data.server.fengyun.dom.UserInfo;
import org.sytes.data.server.fengyun.mapper.MyPayLogMapper;
import org.sytes.data.server.fengyun.mapper.UserInfoMapper;
import org.sytes.message.CommMessage;
import org.sytes.message.MessageId;
import org.sytes.message.Result;
import org.sytes.message.CommMessage.ServerComm;
import org.sytes.message.login.LoginMessage;
import org.sytes.message.login.LoginMessage.UserPro;

import com.alibaba.fastjson.JSONObject;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.protobuf.ByteString;

import akka.actor.ActorRef;

@Singleton
public class UserManager {
	private UserInfoMapper userInfoMapper;
	private MyPayLogMapper myPayLogMapper;
	@Inject
	public UserManager(UserInfoMapper userInfoMapper,MyPayLogMapper myPayLogMapper) {
		this.userInfoMapper=userInfoMapper;
		this.myPayLogMapper=myPayLogMapper;
	}
	
	public void selectById(long id,ActorRef actorRef) {
		UserInfo user=userInfoMapper.selectById(id);
		if(user!=null) {
		}
		
	}
	public void selectUserByUserName(String userName,String pwd,ActorRef actorRef) {
		UserInfo user=userInfoMapper.selectByUserName(userName);
		Result result=new Result();
		if(user==null) {
			result.setStatus(MessageId.error);
		}else {
			result.setStatus(MessageId.success);
			result.setJsonData(JSONObject.toJSONString(user));
		}
		
	
		
	
		actorRef.tell(JSONObject.toJSONString(result), null);
	}
	public void selectByPhone(String phone,ActorRef actorRef) {
		
	}
	/**
	 * 更新用户个人信息
	 * @param user
	 * @param actorRef
	 */
	public void updateUserInfo(UserInfo user,ActorRef actorRef) {
		int res=userInfoMapper.updateById(user);
		Result result=new Result();
		result.setStatus(MessageId.success);
		if(res==0) {
			result.setStatus(MessageId.error);
			
		}else {
			result.setStatus(MessageId.success);
			
		}
		actorRef.tell(JSONObject.toJSONString(result), null);
	}
	
	public void loadPaylog(long id,ActorRef actorRef) {
		List<MyPayLog> payLogs=myPayLogMapper.selectByUserId(id);
		Result result=new Result();
		result.setStatus(MessageId.success);
		result.setJsonData(JSONObject.toJSONString(payLogs));
		actorRef.tell(JSONObject.toJSONString(result), null);
	}
	
	
	public ByteString getUser(UserInfo user) {
		UserPro.Builder userPro =LoginMessage.UserPro.newBuilder();
		userPro.setUserId(user.getId());
		userPro.setUserName(user.getUserName());
		userPro.setPhone(user.getPhone());
		userPro.setProvince(user.getProvince());
		userPro.setCity(user.getCity());
		return userPro.build().toByteString();
	}
	
	
	public ServerComm getServerCommMessage(int mid,String jsonData) {
		ServerComm.Builder msg=CommMessage.ServerComm.newBuilder();
		msg.setServerMessageId(mid);
        msg.setJosnData(jsonData);
        return msg.build();
	}
	
	public void send(ActorRef actorRef,Result result) {
		actorRef.tell(JSONObject.toJSONString(result), null);
	}
}
