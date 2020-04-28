package org.sytes.data.server.fengyun.place;

import org.sytes.message.match.CitySportPlaceReq;

import com.alibaba.fastjson.JSONObject;

import akka.actor.UntypedActor;

/**
 * 城市场地信息
 * 
 * @author wang
 *
 */
public class CitySportPlaceActor extends UntypedActor {
	private SportPlaceManager sportPlaceManager;
	public CitySportPlaceActor(SportPlaceManager sportPlaceManager) {
		super();
		this.sportPlaceManager = sportPlaceManager;
	}
	@Override
	public void onReceive(Object msg) throws Exception {

		if (msg instanceof String) {
			String text = (String) msg;
			CitySportPlaceReq req = JSONObject.parseObject(text, CitySportPlaceReq.class);
			sportPlaceManager.getSportPlaceByCity(req.getCityName(),getSender());
		}
	}

}
