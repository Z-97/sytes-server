package org.sytes.gate.server.actor;

import org.sytes.core.actor.RemoteActorName;
import org.sytes.gate.server.ActorFactoy;
import org.sytes.gate.server.ApplicationContext;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class RechargeActor extends UntypedActor {

	@Override
	public void onReceive(Object msg) throws Exception {
		
		ActorFactoy actorFactoy = ApplicationContext.getInstance().getActorFactoy();
		ActorRef remoteActorRef = actorFactoy.getRemoteHandler(RemoteActorName.rechargeCallBackActor);
		remoteActorRef.forward(msg, getContext());
	}

}
