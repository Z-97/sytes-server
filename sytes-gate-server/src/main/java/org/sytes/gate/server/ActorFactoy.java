
package org.sytes.gate.server;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sytes.core.actor.RemoteActorName;
import org.sytes.gate.server.actor.GateServerClusterActor;
import org.sytes.gate.server.actor.MonitorRemoteActor;
import org.sytes.gate.server.actor.RechargeActor;
import org.sytes.gate.server.http.fengyun.login.LoginActor;
import org.sytes.gate.server.http.fengyun.login.PayLogActor;
import org.sytes.gate.server.http.fengyun.login.SetUserInfoActor;
import org.sytes.gate.server.http.fengyun.login.SportPlaceApplyActor;
import org.sytes.gate.server.http.fengyun.place.CanceMatchActor;
import org.sytes.gate.server.http.fengyun.place.EnterMatchActor;
import org.sytes.gate.server.http.fengyun.place.GetCityPlaceActor;
import org.sytes.gate.server.http.fengyun.place.StartMatchActor;
import org.sytes.message.MessageId;
import com.google.inject.Singleton;
import com.typesafe.config.ConfigFactory;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
@Singleton
public class ActorFactoy {
	private static final Logger LOG = LoggerFactory.getLogger(ActorFactoy.class);
	private static final String SERVER_CFG_FILE = "gate_server_actor.conf";
	private final ActorSystem system;
	private final ActorRef clusterActor;
	//远程服务器监控
	private final ActorRef monitorRemoteActor;
	private Map<String,ActorRef> actorMap=new HashMap<>();
	private Map<String,ActorRef> remoteActorMap=new HashMap<>();
	public ActorFactoy() {
	  
		system = ActorSystem.create("sys", ConfigFactory.load(SERVER_CFG_FILE));
		clusterActor = system.actorOf(Props.create(GateServerClusterActor.class), "clusterActor");
		monitorRemoteActor=system.actorOf(Props.create(MonitorRemoteActor.class,this), "monitorRemoteActor");
		monitorRemoteActor.tell("serch", monitorRemoteActor);
		actorMap.put(MessageId.loginReq, system.actorOf(Props.create(LoginActor.class)));
		actorMap.put(MessageId.updateUserReq, system.actorOf(Props.create(SetUserInfoActor.class)));
		actorMap.put(MessageId.payLogActor, system.actorOf(Props.create(PayLogActor.class)));
	
		actorMap.put(MessageId.sportPlaceApplyActorReq, system.actorOf(Props.create(SportPlaceApplyActor.class)));
		actorMap.put(MessageId.getCitySportPlaceReq, system.actorOf(Props.create(GetCityPlaceActor.class)));
		actorMap.put(MessageId.startMatchActor, system.actorOf(Props.create(StartMatchActor.class)));
		actorMap.put(MessageId.enterMatchReq, system.actorOf(Props.create(EnterMatchActor.class)));
		actorMap.put(MessageId.cancelMatchReq, system.actorOf(Props.create(CanceMatchActor.class)));
		actorMap.put(MessageId.rechargeReq, system.actorOf(Props.create(RechargeActor.class)));
	}
	
	
	public ActorRef getHandler(String messageId) {
		ActorRef handler = null;
		if(actorMap.containsKey(messageId)) {
			return actorMap.get(messageId);
		}
		
		return handler;
	}

	public ActorRef getRemoteHandler(String path) {
		ActorRef handler = null;
		if(remoteActorMap.containsKey(RemoteActorName.dataRootPath+path)) {
			return remoteActorMap.get(RemoteActorName.dataRootPath+path);
		}
		return handler;
	}
	public void setRemoteHandler(String actorPatch, ActorRef remeoteActor) {
		remoteActorMap.put(RemoteActorName.dataRootPath+actorPatch, remeoteActor);
		
	}
	public void removeRemoteHandler(ActorRef actor) {
		remoteActorMap.remove(actor.path().toString());
		LOG.warn("删除[{}]",actor.path().toString());
	}
	public ActorSystem getSystem() {
		return system;
	}
	public int getRemoteHandlerSize() {
		return remoteActorMap.size();
	}
}
