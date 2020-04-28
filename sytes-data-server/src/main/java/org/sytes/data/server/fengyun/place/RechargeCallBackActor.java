package org.sytes.data.server.fengyun.place;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sytes.message.pay.RechargeReq;
import com.alibaba.fastjson.JSONObject;
import akka.actor.UntypedActor;
public class RechargeCallBackActor extends UntypedActor {
	private static final Logger LOG = LoggerFactory.getLogger(RechargeCallBackActor.class);
	private SportPlaceManager sportPlaceManager;
	public RechargeCallBackActor(SportPlaceManager sportPlaceManager) {
		super();
		this.sportPlaceManager = sportPlaceManager;
	}
	@Override
	public void onReceive(Object msg) throws Exception {
		LOG.info("充值[{}]",msg);
		if(msg instanceof String) {
			String text=(String) msg;
			RechargeReq req=JSONObject.parseObject(text, RechargeReq.class);
			sportPlaceManager.rechargeCallBack(req);
		}
	
	}

}
