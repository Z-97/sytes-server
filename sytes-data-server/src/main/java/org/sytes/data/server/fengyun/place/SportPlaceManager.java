package org.sytes.data.server.fengyun.place;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sytes.data.db.log.LogAction;
import org.sytes.data.server.fengyun.dom.MySportMatchLog;
import org.sytes.data.server.fengyun.dom.SportMan;
import org.sytes.data.server.fengyun.dom.SportPlace;
import org.sytes.data.server.fengyun.dom.SportPlaceOrederState;
import org.sytes.data.server.fengyun.dom.UserInfo;
import org.sytes.data.server.fengyun.mapper.MySportMatchLogMapper;
import org.sytes.data.server.fengyun.mapper.SportPlaceMapper;
import org.sytes.data.server.fengyun.mapper.SportPlaceOrederStateMapper;
import org.sytes.data.server.fengyun.mapper.UserInfoMapper;
import org.sytes.data.server.fengyun.match.MatchManager;
import org.sytes.data.server.fengyun.user.UserManager;
import org.sytes.data.server.platform.PlatformManager;
import org.sytes.message.MessageId;
import org.sytes.message.Result;
import org.sytes.message.match.CancelMatchReq;
import org.sytes.message.match.EnrollMatchReq;
import org.sytes.message.match.SponsorMatchReq;
import org.sytes.message.pay.RechargeReq;

import com.alibaba.fastjson.JSONObject;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import akka.actor.ActorRef;

@Singleton
public class SportPlaceManager {
	private static final Logger LOG = LoggerFactory.getLogger(SportPlaceManager.class);
	@Inject
	private SportPlaceMapper sportPlaceMapper;
	@Inject
	private SportPlaceOrederStateMapper sportPlaceOrederStateMapper;
	@Inject
	private UserInfoMapper userInfoMapper;
	@Inject
	private MySportMatchLogMapper mySportMatchLogMapper;
	@Inject
	private UserManager userManager;
	@Inject
	private PlatformManager platformManager;
	@Inject
	private MatchManager matchManager;
	/**
	 * 新增球场
	 * @param sportPlace
	 * @param actorRef
	 */
	public void addSportPlace(SportPlace sportPlace,ActorRef actorRef) {
		Result result=new Result();
		result.setStatus(MessageId.error);
		if(sportPlace.getPlaceOwner()==0) {
			result.setMsg("场主不存在");
			userManager.send(actorRef, result);
			return ;
		}
		if(sportPlace.getPlaceName()==null) {
			result.setMsg("场地名称不能为空");
			userManager.send(actorRef, result);
			return ;
		}
		if(sportPlace.getPlaceName().length()<4) {
			result.setMsg("场地名称少于2个字");
			userManager.send(actorRef, result);
			return ;
		}
		if(sportPlace.getPlaceName().length()>64) {
			result.setMsg("场地名称超过长度");
			userManager.send(actorRef, result);
			return ;
		}
		SportPlace oldSportPlace=sportPlaceMapper.selectByplaceName(sportPlace.getPlaceName());
		if(oldSportPlace!=null) {
			result.setMsg("场地名称已存在");
			userManager.send(actorRef, result);
			return ;
		}
		if(sportPlace.getPlaceAdress()==null) {
			result.setMsg("场地地址不能为空");
			userManager.send(actorRef, result);
			return ;
		}
		if(sportPlace.getPlaceType()==null||sportPlace.getPlaceType()<=0||sportPlace.getPlaceType()>5) {
			result.setMsg("场地类型不正确");
			userManager.send(actorRef, result);
			return ;
		}
		if(sportPlace.getLinkman()==null) {
			result.setMsg("场地联系人不能为空");
			userManager.send(actorRef, result);
			return ;
		}
		if(sportPlace.getLinkmanPhone()==null) {
			result.setMsg("场地电话不能为空");
			userManager.send(actorRef, result);
			return ;
		}
		if(sportPlace.getPlaceMax()==null||sportPlace.getPlaceMax()<=0) {
			result.setMsg("场地最大容纳人数不能为0或为负");
			userManager.send(actorRef, result);
			return ;
		}
		if(sportPlace.getPlaceMin()==null||sportPlace.getPlaceMin()<=0) {
			result.setMsg("场地预约人数不能为0或负");
			userManager.send(actorRef, result);
			return ;
		}
		if(sportPlace.getPlaceMin()>sportPlace.getPlaceMax()) {
			result.setMsg("预约人数不能大于场地容量");
			userManager.send(actorRef, result);
			return ;
		}
		
		
		if(sportPlace.getWholePrice()==null||sportPlace.getWholePrice()<0) {
			result.setMsg("场地包场价格不能为负");
			userManager.send(actorRef, result);
			return ;
		}

		if(sportPlace.getSinglePrice()==null||sportPlace.getSinglePrice()<0) {
			result.setMsg("场地单人价格不能为负");
			userManager.send(actorRef, result);
			return ;
		}

		if(sportPlace.getPromoterPrice()==null||sportPlace.getPromoterPrice()<0) {
			result.setMsg("场地发起人价格不能为负");
			userManager.send(actorRef, result);
			return ;
		}

		if(sportPlace.getCancelHours()==null||sportPlace.getCancelHours()<0) {
			result.setMsg("可取消时间不能为负");
			userManager.send(actorRef, result);
			return ;
		}

		if(sportPlace.getPlatformRate()==null||sportPlace.getPlatformRate()<0) {
			result.setMsg("平台抽成费率不能为负");
			userManager.send(actorRef, result);
			return ;
		}
		if(sportPlace.getStartTime()==null) {
			result.setMsg("开始时间不能为空");
			userManager.send(actorRef, result);
			return ;
		}
		//开放时间判断
		SimpleDateFormat stringToDateFormat = new SimpleDateFormat("HH:mm:ss");
		Date startToday=null;
		try {
			startToday = stringToDateFormat.parse(sportPlace.getStartTime());
			
		} catch (ParseException e) {
			result.setMsg("开始时间错误");
			userManager.send(actorRef, result);
			return ;
		}
		if(sportPlace.getEndTime()==null) {
			result.setMsg("结束时间不能为空");
			userManager.send(actorRef, result);
			return ;
		}
		Date endToday=null;
		try {
			endToday = stringToDateFormat.parse(sportPlace.getEndTime());
		} catch (ParseException e) {
			result.setMsg("结束时间错误");
			userManager.send(actorRef, result);
			return ;
		}
		LOG.info("开始时间[{}]结束时间[{}]",startToday,endToday);
		if(startToday.after(endToday)) {
			result.setMsg("开始时间不能早于结束时间");
			userManager.send(actorRef, result);
			return ;
		}
		if(sportPlace.getPlaceCity()==null||sportPlace.getPlaceCity().length()>128) {
			result.setMsg("所在城市不正确");
			userManager.send(actorRef, result);
			return ;
		}
		if(sportPlace.getPlaceProvince()==null||sportPlace.getPlaceProvince().length()>128) {
			result.setMsg("所在省份不正确");
			userManager.send(actorRef, result);
			return ;
		}
		int res=sportPlaceMapper.insert(sportPlace);
		if(res==1) {
			result.setMsg("添加场地成功！");
			result.setStatus(MessageId.success);
		}else {
			result.setMsg("添加场地失败！");
		}
		userManager.send(actorRef, result);
	}
	/**
	 * 根据城市获取场地信息
	 * @param cityName
	 * @param sender
	 */
	public void getSportPlaceByCity(String cityName, ActorRef actorRef) {
		Result result=new Result();
		if(cityName==null||cityName.length()>128) {
			result.setMsg("所在城市不正确");
			result.setStatus(MessageId.error);
			userManager.send(actorRef, result);
			return ;
		}
		result.setStatus(MessageId.success);
		 List<SportPlace> placeList=sportPlaceMapper.selectByCity(cityName);
		 result.setJsonData(JSONObject.toJSONString(placeList));
		 userManager.send(actorRef, result);
	}
	
	/**
	 * 发起比赛
	 * @param userId
	 * @param sprotPlaceId
	 * @param startTime
	 * @param endTime
	 */
	public void sponsorMatch(SponsorMatchReq sponsorMatchReq,ActorRef actorRef) {
		Result result=new Result();
		result.setStatus(MessageId.error);
		if(sponsorMatchReq.getUserId()==0) {
			result.setMsg("用户id不正确");
			userManager.send(actorRef, result);
			return ;
		}
		
		UserInfo userInfo =userInfoMapper.selectById(sponsorMatchReq.getUserId());
		if(userInfo==null) {
			result.setMsg("用户不存在");
			userManager.send(actorRef, result);
			return ;
		}
		if(sponsorMatchReq.getStartTime()==null) {
			result.setMsg("比赛开始时间不正确");
			userManager.send(actorRef, result);
			return ;
		}
		Date nowTime=new Date();
		SimpleDateFormat stringToDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startTime=null;
		try {
			startTime = stringToDateFormat.parse(sponsorMatchReq.getStartTime());
		} catch (ParseException e) {
			result.setMsg("开始时间错误");
			userManager.send(actorRef, result);
			return ;
		}
		
		if(startTime.before(nowTime)) {
			result.setMsg("选择开始时间错误");
			userManager.send(actorRef, result);
			return ;
		}
		
		if(sponsorMatchReq.getEndTime()==null) {
			result.setMsg("结束时间错误");
			userManager.send(actorRef, result);
			return ;
		}
		
		Date endTime=null;
		try {
			endTime = stringToDateFormat.parse(sponsorMatchReq.getEndTime());
		} catch (ParseException e) {
			result.setMsg("结束时间不正确");
			userManager.send(actorRef, result);
			return ;
		}
		if(!endTime.after(startTime)) {
			result.setMsg("结束时间选择不正确");
			userManager.send(actorRef, result);
			return ;
		}
		
		if(sponsorMatchReq.getSportPlaceId()<=0) {
			result.setMsg("场地id不正确");
			userManager.send(actorRef, result);
			return ;
		}
		//判读赛事是否与存在的比赛有时间冲突
		SportPlace place = sportPlaceMapper.selectById(sponsorMatchReq.getSportPlaceId());
		if (place == null) {
			result.setMsg("场地不存在");
			userManager.send(actorRef, result);
			return;
		}
		//检查场地开放时间
		List<SportPlaceOrederState> orederList=sportPlaceOrederStateMapper.selectByPlaceId(place.getPlaceId());
		if(orederList!=null) {
			for(SportPlaceOrederState spss:orederList) {
				if(spss.getState()==PlaceOrederState.OK.id&&!startTime.after(spss.getEndTime())) {
					result.setMsg("场地时间冲突");
					userManager.send(actorRef, result);
					return;
				}
			}
		}
		
		//增加我的赛事日志
		MySportMatchLog mySportMatchLog=new MySportMatchLog();
		mySportMatchLog.setUserId(sponsorMatchReq.getUserId());
		mySportMatchLog.setIsPromoter(true);
		mySportMatchLog.setMathState(0);
		mySportMatchLog.setPlaceId(place.getPlaceId());
		mySportMatchLog.setPlaceName(place.getPlaceName());
		mySportMatchLog.setSportAdress(place.getPlaceAdress());
		mySportMatchLog.setEndTime(endTime);
		mySportMatchLog.setStartTime(startTime);
		mySportMatchLog.setUpdateTime(new Date());
		System.out.println(mySportMatchLog.getStartTime());
  	    int res=mySportMatchLogMapper.insert (mySportMatchLog);
		if (res > 0) {
			result.setStatus(MessageId.success);
			result.setMsg("发起比赛成功，等待支付");
			
		}else {
			result.setMsg("发起比赛失败");
		}
		userManager.send(actorRef, result);


	}
	/**
	 * 报名
	 * @param req
	 * @param actorRef
	 */
	public void enrollMatch(EnrollMatchReq req,ActorRef actorRef) {
		Result result=new Result();
		result.setStatus(MessageId.error);
		if(req.getUserId()==0) {
			result.setMsg("用户id不正确");
			userManager.send(actorRef, result);
			return ;
		}
		UserInfo userInfo =userInfoMapper.selectById(req.getUserId());
		if(userInfo==null) {
			result.setMsg("用户不存在");
			userManager.send(actorRef, result);
			return ;
		}
		if(req.getOrederId()==0) {
			result.setMsg("比赛ID不正确");
			userManager.send(actorRef, result);
			return ;
		}
		SportPlaceOrederState sps=sportPlaceOrederStateMapper.selectById(req.getOrederId());
		if(sps==null) {
			result.setMsg("比赛不存在");
			userManager.send(actorRef, result);
			return ;
		}
		
		if(sps.getState()!=1) {
			result.setMsg("比赛已经取消或已结束");
			userManager.send(actorRef, result);
			return ;
		}
		
		//检查是否重复报名
		if(sps.getPromoterId()==req.getUserId()) {
			result.setMsg("不能重复报名");
			userManager.send(actorRef, result);
			return ;
		}
		if(sps.getSportMember()==null) {
			result.setMsg("比赛成员不存在");
			userManager.send(actorRef, result);
			return ;
		}
		
//		for(SportMan sportMan :sps.getSportMember()) {
//			if(sportMan.getUserId()==req.getUserId()) {
//				result.setMsg("重复报名");
//				userManager.send(actorRef, result);
//				return ;
//			}
//		}
		
		// 增加我的赛事日志
		MySportMatchLog mySportMatchLog = new MySportMatchLog();
		mySportMatchLog.setUserId(req.getUserId());
		mySportMatchLog.setIsPromoter(false);
		mySportMatchLog.setMathState(0);
		mySportMatchLog.setPlaceId(sps.getPlaceId());
		mySportMatchLog.setPlaceName(sps.getPlaceName());
		mySportMatchLog.setSportAdress(sps.getPlaceAdress());
		mySportMatchLog.setEndTime(sps.getEndTime());
		mySportMatchLog.setStartTime(sps.getStartTime());
		mySportMatchLog.setUpdateTime(new Date());
		mySportMatchLog.setOrederId(sps.getId());
		mySportMatchLogMapper.insert(mySportMatchLog);
		result.setStatus(MessageId.success);
		result.setMsg("报名成功,等待支付");
		userManager.send(actorRef, result);
		LOG.info("用户[{}][{}]报名场地[{}]比赛申请成功",req.getUserId(),userInfo.getUserName(),sps.getPlaceName());
	}
	
	public void cancelMatch(CancelMatchReq req, ActorRef actorRef) {
		Result result=new Result();
		result.setStatus(MessageId.error);
		if(req.getUserId()==0) {
			result.setMsg("用户id不正确");
			userManager.send(actorRef, result);
			return ;
		}
		UserInfo userInfo =userInfoMapper.selectById(req.getUserId());
		if(userInfo==null) {
			result.setMsg("用户不存在");
			userManager.send(actorRef, result);
			return ;
		}
		if(req.getOrederId()==0) {
			result.setMsg("比赛ID不正确");
			userManager.send(actorRef, result);
			return ;
		}
		SportPlaceOrederState sps=sportPlaceOrederStateMapper.selectById(req.getOrederId());
		if(sps==null) {
			result.setMsg("比赛不存在");
			userManager.send(actorRef, result);
			return ;
		}
		SportPlace place = sportPlaceMapper.selectById(sps.getPlaceId());
		if (place == null) {
			result.setMsg("场地不存在");
			userManager.send(actorRef, result);
			return;
		}
		if(!(sps.getState()!=PlaceOrederState.APPLY.id||sps.getState()!=PlaceOrederState.OK.id)) {
			result.setMsg("比赛已经取消或已结束");
			userManager.send(actorRef, result);
			return ;
		}
		Date now=new Date();
		if(now.after(sps.getStartTime())) {
			result.setMsg("比赛已经过期");
			userManager.send(actorRef, result);
			return ;
		}

		long between = (sps.getStartTime().getTime() - now.getTime())/1000;
		long cancelTime=place.getCancelHours()*3600;
		if(between<cancelTime) {
			result.setMsg("比赛时间不能取消");
			userManager.send(actorRef, result);
			return ;
		}
		
		
		if(sps.getSportMember()==null) {
			result.setMsg("比赛成员不存在");
			userManager.send(actorRef, result);
			return ;
		}
		SportMan mySportMan = null;
		for(SportMan sportMan :sps.getSportMember()) {
			if(sportMan.getUserId()==req.getUserId()) {
				mySportMan=sportMan;
				break ;
			}
		}
		if(mySportMan==null) {
			result.setMsg("没有报名不能取消");
			userManager.send(actorRef, result);
			return;
		}
		
		CopyOnWriteArrayList<SportMan> sportMember = sps.getSportMember();
		if(mySportMan.getUserId()==sps.getPromoterId()) {
			//发起者取消整个比赛
			sps.setState(PlaceOrederState.CANCEL.id);
		}else {
			sps.setSportNum(sps.getSportNum() - 1);
			sportMember.remove(mySportMan);
			sps.setSportMember(sportMember);
			
		}
		//更新场地订单状
		sportPlaceOrederStateMapper.updateById(sps);
		MySportMatchLog mySportMatchLog =mySportMatchLogMapper.selectById(mySportMan.getMySportLogId());
		if(mySportMatchLog!=null) {
			mySportMatchLog.setMathState(MyMatchState.CANCEL.state);
			mySportMatchLogMapper.updateById(mySportMatchLog);
		}
		//更新我的比赛状态
		result.setMsg("报名取消成功");
		userManager.send(actorRef, result);
		//退费
	}
	/**
	 * 充值成功回调
	 * @param req
	 */
	public void rechargeCallBack(RechargeReq req) {
		LOG.info("充值记录[{}][{}][{}]",req.getUserId(),req.getMySprotlogId(),req.getGold());
		if(req.getUserId()==0) {
			return ;
		}

		if(req.getGold()<=0) {
			return;
		}
		if(req.getMySprotlogId()<=0) {
			return;
		}
		//插入充值订单,检查重复
		platformManager.addIncome(req.getGold(), true, LogAction.SPALCE_INCOME, LogAction.SPALCE_INCOME.desc);
		UserInfo userInfo =userInfoMapper.selectById(req.getUserId());
		if(userInfo==null) {
			return ;
		}
		
		MySportMatchLog mySportMatchLog =mySportMatchLogMapper.selectById(req.getMySprotlogId());
		if(mySportMatchLog==null) {
			LOG.error("[{}]报名的[{}]金额的[{}]比赛不存在",req.getUserId(),req.getMySprotlogId(),req.getGold());
		  return;
		}
		if(mySportMatchLog.getMathState()!=0) {
			LOG.error("[{}]报名的[{}]金额的[{}]比赛已经支付",req.getUserId(),req.getMySprotlogId(),req.getGold());
			  return;
		}
		SportPlace place = sportPlaceMapper.selectById(mySportMatchLog.getPlaceId());
		if (place == null) {
			LOG.error("场地[{}]不存在",mySportMatchLog.getPlaceId());
			return;
		}
		
		
		
		//创建预约订单
		SportPlaceOrederState sps;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(new Date());
		//增加报名人信息
		SportMan sportMan = new SportMan();
		sportMan.setUserId(userInfo.getId());
		sportMan.setBaoMingDate(dateString);
		sportMan.setUserName(userInfo.getUserName());
		sportMan.setMySportLogId(mySportMatchLog.getId());
		//自己是发起者，
		if(mySportMatchLog.getIsPromoter()) {
			sps=new SportPlaceOrederState();
			sps.setPlaceId(mySportMatchLog.getPlaceId());
			sps.setPlaceName(mySportMatchLog.getPlaceName());
			sps.setPlaceCity(place.getPlaceCity());
			sps.setStartTime(mySportMatchLog.getStartTime());
			sps.setEndTime(mySportMatchLog.getEndTime());
			sps.setPromoterId(mySportMatchLog.getUserId());
			sps.setPromoterName(userInfo.getUserName());
			sps.setPlaceAdress(place.getPlaceAdress());
			sps.setState(0);
			CopyOnWriteArrayList<SportMan> sportMember = sps.getSportMember();
			sps.setSportNum(1);
			sportMember.add(sportMan);
			sps.setSportMember(sportMember);
			sportPlaceOrederStateMapper.insert(sps);
		}else {
			
			sps=sportPlaceOrederStateMapper.selectById(mySportMatchLog.getOrederId());
			if(sps.getState()!=1) {
				LOG.error("[{}]报名的[{}]金额的[{}]比赛, 比赛id[{}]已经取消或结束",req.getUserId(),req.getMySprotlogId(),req.getGold(),sps.getId());
				return;
			}
			// 更新预约比较状态
			CopyOnWriteArrayList<SportMan> sportMember = sps.getSportMember();
			sps.setSportNum(sps.getSportNum() + 1);
			sportMember.add(sportMan);
			sps.setSportMember(sportMember);
			sportPlaceOrederStateMapper.updateById(sps);
			
		}
		
		
		mySportMatchLog.setMathState(1);
		mySportMatchLog.setOrederId(sps.getId());
		mySportMatchLogMapper.updateById(mySportMatchLog);
		matchManager.addSportPlaceOrederState(sps);
		LOG.info("[{}]报名成功[{}][{}]比赛",mySportMatchLog.getUserId(),mySportMatchLog.getPlaceName(),mySportMatchLog.getStartTime());		
	}
	
}
