package org.sytes.data.server.platform;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sytes.data.db.log.LogAction;
import org.sytes.data.server.fengyun.dom.PlatformInfo;
import org.sytes.data.server.fengyun.mapper.PlatformInfoMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
@Singleton
public class PlatformManager {
	public static final Logger LOG = LoggerFactory.getLogger(PlatformManager.class);
	
	private PlatformInfoMapper platformInfoMapper;
	private PlatformInfo platformInfo;
	@Inject
	public PlatformManager(PlatformInfoMapper platformInfoMapper) {
		this.platformInfoMapper=platformInfoMapper;
	}
	@Inject
	public void init() {
		loadPlatformInfo();
	}
	private void loadPlatformInfo() {
		platformInfo=platformInfoMapper.selectPlatformInfo();
		if(platformInfo==null) {
			platformInfo=new PlatformInfo();
			platformInfo.setPlatformId(1);
			platformInfo.setCashOut(new AtomicInteger(0));
			platformInfo.setIncome(new AtomicInteger(0));
			platformInfoMapper.insert(platformInfo);
		}
		LOG.info("平台信息载入成功");
	}
	public void addIncome(int val, boolean log, LogAction action, String desc) {
		if (val == 0) {
			return;
		}
		AtomicInteger gold = platformInfo.getIncome();
		int beforeGold = gold.get();
		int afterGold = gold.addAndGet(val);
		platformInfoMapper.updateById(platformInfo);
		LOG.info("增加前[{}]增加后[{}]，增加值[{}]平台当前收入[{}]",beforeGold,afterGold,val,platformInfo.income());
	}
	public void addCashOut(int val, boolean log, LogAction action, String desc) {
		if (val == 0) {
			return;
		}
		AtomicInteger gold = platformInfo.getCashOut();
		int beforeGold = gold.get();
		int afterGold = gold.addAndGet(val);
		platformInfoMapper.updateById(platformInfo);
		LOG.info("提现前[{}]提现后[{}]，提现金额[{}]平台当前总提现[{}]",beforeGold,afterGold,val,platformInfo.income());
	}
	
	
}
