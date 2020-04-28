package org.sytes.gate.server.http.fengyun.place;

import org.sytes.core.actor.RemoteActorName;
import org.sytes.gate.server.ActorFactoy;
import org.sytes.gate.server.ApplicationContext;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
/**
 * 取消报名
 * @author wang
 *
 */
public class CanceMatchActor extends UntypedActor{

	@Override
	public void onReceive(Object msg) throws Exception {
		ActorFactoy actorFactoy = ApplicationContext.getInstance().getActorFactoy();
		ActorRef remoteActorRef = actorFactoy.getRemoteHandler(RemoteActorName.canelMatchActor);
		remoteActorRef.forward(msg, getContext());
		
	}

}
