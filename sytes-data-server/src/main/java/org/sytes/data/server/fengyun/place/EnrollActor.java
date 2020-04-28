package org.sytes.data.server.fengyun.place;
import org.sytes.message.match.EnrollMatchReq;
import com.alibaba.fastjson.JSONObject;
import akka.actor.UntypedActor;
/**
 * 报名比赛
 * @author wang
 *
 */
public class EnrollActor extends UntypedActor {
	private SportPlaceManager sportPlaceManager;
	public EnrollActor(SportPlaceManager sportPlaceManager) {
		super();
		this.sportPlaceManager = sportPlaceManager;
	}
	@Override
	public void onReceive(Object message) throws Exception {
		if(message instanceof String) {
			String text=(String) message;
			EnrollMatchReq sponsorMatchReq=JSONObject.parseObject(text, EnrollMatchReq.class);
			sportPlaceManager.enrollMatch(sponsorMatchReq, getSender());
		}

	}

}
