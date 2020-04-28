package org.sytes.data.server.fengyun.user;
import org.sytes.message.login.MyPayLogReq;
import com.alibaba.fastjson.JSONObject;
import akka.actor.UntypedActor;
/**
 * 我的付费记录
 * @author wang
 *
 */
public class MyPayLogActor extends UntypedActor {

	private UserManager umgr;
	public MyPayLogActor(UserManager umgr) {
		super();
		this.umgr = umgr;
	}
	@Override
	public void onReceive(Object msg) throws Exception {
		if(msg instanceof String) {
			String text=(String) msg;
			MyPayLogReq myPayLogReq=JSONObject.parseObject(text, MyPayLogReq.class);
			umgr.loadPaylog(myPayLogReq.getId(), getSender());
		}

		
	}

}
