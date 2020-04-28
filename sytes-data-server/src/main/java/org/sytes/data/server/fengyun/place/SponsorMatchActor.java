package org.sytes.data.server.fengyun.place;

import org.sytes.message.match.SponsorMatchReq;

import com.alibaba.fastjson.JSONObject;

import akka.actor.UntypedActor;

/**
 * 发起比赛
 * @author wang
 *
 */
public class SponsorMatchActor extends UntypedActor {
	private SportPlaceManager sportPlaceManager;
	public SponsorMatchActor(SportPlaceManager sportPlaceManager) {
		super();
		this.sportPlaceManager = sportPlaceManager;
	}
	@Override
	public void onReceive(Object message) throws Exception {
		if(message instanceof String) {
			String text=(String) message;
			SponsorMatchReq sponsorMatchReq=JSONObject.parseObject(text, SponsorMatchReq.class);
			sportPlaceManager.sponsorMatch(sponsorMatchReq, getSender());
		}

	}

}
