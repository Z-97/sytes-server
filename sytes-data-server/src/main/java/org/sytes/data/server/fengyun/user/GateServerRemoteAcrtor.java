package org.sytes.data.server.fengyun.user;

import akka.actor.UntypedActor;
import akka.remote.AssociationErrorEvent;

public class GateServerRemoteAcrtor extends UntypedActor {

	
	@Override
	public void preStart() throws Exception {
		this.getContext().system().eventStream().subscribe(getSelf(), AssociationErrorEvent.class);
		//this.getContext().system().eventStream().subscribe(getSelf(),RemotingListenEvent.class);
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		System.out.println(msg);
	}

}
