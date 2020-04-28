package org.sytes.data.server.fengyun.place;

import org.sytes.data.server.fengyun.dom.SportPlace;

import com.alibaba.fastjson.JSONObject;

import akka.actor.UntypedActor;
/**
 * 添加场地actor
 * @author wang
 *
 */
public class AddSportPlaceActor extends UntypedActor {
	private SportPlaceManager sportPlaceManager;
	public AddSportPlaceActor(SportPlaceManager sportPlaceManager) {
		super();
		this.sportPlaceManager = sportPlaceManager;
	}

	
	
	@Override
	public void onReceive(Object message) throws Exception {
		if(message instanceof String) {
			String text=(String) message;
			SportPlace sportPlace=JSONObject.parseObject(text, SportPlace.class);
			sportPlaceManager.addSportPlace(sportPlace, getSender());
		}
	}

}
