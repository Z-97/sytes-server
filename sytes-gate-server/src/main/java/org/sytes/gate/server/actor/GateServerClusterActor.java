package org.sytes.gate.server.actor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.ClusterEvent.MemberEvent;
import akka.cluster.ClusterEvent.MemberUp;
import akka.cluster.ClusterEvent.UnreachableMember;
/**
 * 集群节点状态
 * @author wang
 *
 */
public class GateServerClusterActor extends UntypedActor {
	private static final Logger LOG = LoggerFactory.getLogger(GateServerClusterActor.class);
	private Cluster cluster = Cluster.get(getContext().system());

	/**
	 * 取消订阅
	 */
	@Override
	public void postStop() throws Exception {
		cluster.unsubscribe(getSelf());
	}
	/**
	 * 订阅集群成员事件
	 */
	@Override
	public void preStart() throws Exception {
		cluster.subscribe(getSelf(), ClusterEvent.initialStateAsEvents(), UnreachableMember.class, MemberEvent.class);
	}
	@Override
	public void onReceive(Object msg) throws Exception {
		if(msg instanceof MemberUp) {
			MemberUp mp=(MemberUp) msg;
			LOG.info("服务节点[{}]角色[{}]已经加入集群，等待确认服务确认",mp.member().address(),mp.member().roles());
		}else if(msg instanceof UnreachableMember) {
			UnreachableMember mp=(UnreachableMember) msg;
			LOG.info("服务节点[{}]角色[{}]已经被移除当前集群",mp.member().address(),mp.member().roles());
		}

	}

}
