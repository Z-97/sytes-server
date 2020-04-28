package org.sytes.data.server.fengyun.match;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sytes.data.server.fengyun.dom.SportPlaceOrederState;
import org.sytes.data.server.fengyun.mapper.SportPlaceOrederStateMapper;
import org.sytes.data.server.fengyun.place.SportPlaceManager;
import org.sytes.data.server.fengyun.schedule.manager.ScheduleMgr;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class MatchManager {
	@Inject
	private SportPlaceOrederStateMapper sportPlaceOrederStateMapper;
	@Inject
	private ScheduleMgr scheduleMgr;
	private static final Logger LOG = LoggerFactory.getLogger(MatchManager.class);
	@Inject
	public void init() {
	    List<SportPlaceOrederState> spsList = sportPlaceOrederStateMapper.selectOpenSportPlaceOreder();
	
		for (SportPlaceOrederState sps : spsList) {
			addSportPlaceOrederState(sps);
		}
	}

	public void addSportPlaceOrederState(SportPlaceOrederState sps) {
		Date now = new Date();
		if (sps.getState() == 0) {
			if (sps.getStartTime().after(now)) {
				long delay = sps.getStartTime().getTime() - now.getTime();
				scheduleMgr.schedule(new Runnable() {
					@Override
					public void run() {
						startSportPlaceOrederState(sps);
					}

				}, delay, TimeUnit.MILLISECONDS);
			}
		}
		LOG.info("场地[{}]可以开始报名：[{}]--[{}]比赛时间：[{}]--[{}]",sps.getPlaceName(),now,sps.getStartTime(),sps.getStartTime(),sps.getEndTime());
	}
	/**
	 * 比赛开始调用
	 * 
	 * @param sps
	 */
	private void startSportPlaceOrederState(SportPlaceOrederState sps) {
		sps.setState(1);
		sportPlaceOrederStateMapper.updateById(sps);
		Date now = new Date();
		long delay = sps.getEndTime().getTime() - now.getTime();
		scheduleMgr.schedule(new Runnable() {

			@Override
			public void run() {
				endSportPlaceOrederState(sps);
			}

		}, delay, TimeUnit.MILLISECONDS);
		LOG.info("场地[{}]比赛开始：[{}]--[{}]",sps.getPlaceName(),sps.getStartTime(),sps.getEndTime());
	}

	private void endSportPlaceOrederState(SportPlaceOrederState sps) {
		sps.setState(2);
		sportPlaceOrederStateMapper.updateById(sps);
		LOG.info("场地[{}]比赛结束：[{}]--[{}]",sps.getPlaceName(),sps.getStartTime(),sps.getEndTime());
	}

}
