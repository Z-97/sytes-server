package org.sytes.data.server.fengyun.place;
import org.sytes.message.match.CancelMatchReq;
import com.alibaba.fastjson.JSONObject;
import akka.actor.UntypedActor;
public class CanelMatchActor extends UntypedActor {

	private SportPlaceManager sportPlaceManager;
	public CanelMatchActor(SportPlaceManager sportPlaceManager) {
		super();
		this.sportPlaceManager = sportPlaceManager;
	}
	@Override
	public void onReceive(Object message) throws Exception {
		if(message instanceof String) {
			String text=(String) message;
			CancelMatchReq req=JSONObject.parseObject(text, CancelMatchReq.class);
			sportPlaceManager.cancelMatch(req, getSender());
		}

	}

}
