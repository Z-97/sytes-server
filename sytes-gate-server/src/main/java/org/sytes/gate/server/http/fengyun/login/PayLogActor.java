package org.sytes.gate.server.http.fengyun.login;

import org.sytes.core.actor.RemoteActorName;
import org.sytes.gate.server.ActorFactoy;
import org.sytes.gate.server.ApplicationContext;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class PayLogActor extends UntypedActor {

	@Override
	public void onReceive(Object arg0) throws Exception {
		
		ActorFactoy actorFactoy=ApplicationContext.getInstance().getActorFactoy();
		ActorRef remoteActorRef=actorFactoy.getRemoteHandler(RemoteActorName.myPayLogActor);
		remoteActorRef.forward(arg0, getContext());
	}

}
