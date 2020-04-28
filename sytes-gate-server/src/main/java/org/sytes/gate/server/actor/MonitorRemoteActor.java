package org.sytes.gate.server.actor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sytes.core.actor.RemoteActorName;
import org.sytes.gate.server.ActorFactoy;

import akka.actor.ActorIdentity;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.Identify;
import akka.actor.Terminated;
import akka.actor.UntypedActor;

public class MonitorRemoteActor extends UntypedActor {
	private static final Logger LOG = LoggerFactory.getLogger(MonitorRemoteActor.class);
	private final ActorFactoy actorFactoy;
	public MonitorRemoteActor(ActorFactoy actorFactoy) {
		this.actorFactoy = actorFactoy;
	}
	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof String) {
			Field[] fields = RemoteActorName.class.getDeclaredFields();
			try {
				for (Field field : fields) {
					field.setAccessible(true);
					if (field.getType().toString().endsWith("java.lang.String")
							&& Modifier.isStatic(field.getModifiers())) {
						String remoteActorName = field.get(RemoteActorName.class).toString();
						if(!remoteActorName.endsWith(RemoteActorName.dataRootPath)) {
							ActorSelection selection = getContext()
									.actorSelection(RemoteActorName.dataRootPath + remoteActorName);
							selection.tell(new Identify(remoteActorName), getSelf());
						}
						
					}
				}
			} catch (Exception e) {
				LOG.error("初始化远程调用借口失败[{}]", e.getMessage());
			}
		} else if (msg instanceof ActorIdentity) {
			ActorIdentity aiy = (ActorIdentity) msg;
			String remoteActorName=aiy.correlationId().toString();
			
				if (aiy.getRef() == null) {
					LOG.warn("远程服务[{}]未开启", remoteActorName);
				} else {
					ActorRef remeoteActor = aiy.getRef();
					getContext().watch(remeoteActor);
					actorFactoy.setRemoteHandler(remoteActorName, remeoteActor);
					LOG.info("远程服务[{}]已经开启", remoteActorName);
				}
			
		} else if (msg instanceof Terminated) {
			Terminated terminated = (Terminated) msg;
			actorFactoy.removeRemoteHandler(terminated.getActor());
			LOG.info("远程服务[{}]已经移除，剩余服务[{}]个", terminated.getActor().path().toString(),
					actorFactoy.getRemoteHandlerSize());
		}else if(msg instanceof Exception) {
			LOG.info("远程服务[{}]异常", getSender());
		}
		
	}

}
