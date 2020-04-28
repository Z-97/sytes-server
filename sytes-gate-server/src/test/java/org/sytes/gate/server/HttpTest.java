package org.sytes.gate.server;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.ContentType;
import org.junit.Test;
import org.sytes.gate.server.http.HttpRequestUtil;
import org.sytes.message.MessageId;
import org.sytes.message.login.LoginReq;
import org.sytes.message.login.MyPayLogReq;

import com.alibaba.fastjson.JSONObject;

public class HttpTest {
	@Test
	public void login() throws ClientProtocolException, IOException {
		LoginReq req=new LoginReq();
		req.setMssageId(1);
		req.setName("wang");
		req.setPwd("123456qqq");
		String jsonString = JSONObject.toJSONString(req);
		System.out.println("返回："+HttpRequestUtil.post("http://127.0.0.1:8848/loginReq", ContentType.APPLICATION_JSON, jsonString));
	}
	
	@Test
	public void updateUser() throws ClientProtocolException, IOException {
		JSONObject json=new JSONObject();
		json.put("id", 2);
		json.put("userName", "wangfengfeng");
		json.put("province", "四川");
		json.put("phone", "13555653327");
		String jsonString = json.toJSONString();
		System.out.println("返回："+HttpRequestUtil.post("http://127.0.0.1:8848/UpdateUserReq", ContentType.APPLICATION_JSON, jsonString));
	}
	@Test
	public void loadPayLog() throws ClientProtocolException, IOException {
		MyPayLogReq myPayLogReq=new MyPayLogReq();
		myPayLogReq.setId(1);
		String jsonString = JSONObject.toJSONString(myPayLogReq);
		System.out.println("返回："+HttpRequestUtil.post("http://127.0.0.1:8848"+MessageId.payLogActor, ContentType.APPLICATION_JSON, jsonString));
	}
	@Test
	public void addPlace() throws ClientProtocolException, IOException {
		
			JSONObject json=new JSONObject();
			json.put("placeOwner", 1);
			json.put("placeName", "wangfengfeng");
			json.put("placeAdress", "四川省成都市高新区东市路18号");
			json.put("placeType", 1);
			json.put("linkman", "张费费哦");
			json.put("linkmanPhone", "13555653327");
			json.put("placeMax", 13333);
			json.put("placeMin", 565);
			json.put("wholePrice", 1355565);
			json.put("singlePrice", 3555);
			json.put("cancelHours", 3555);
			json.put("promoterPrice", 3555);
			json.put("platformRate", 3555);
			json.put("startTime", "12:34:32");
			json.put("endTime", "19:34:32");
			json.put("placeCity", "成都");
			json.put("placeProvince", "四川");
			
			String jsonString = json.toJSONString();
			System.out.println("返回："+HttpRequestUtil.post("http://127.0.0.1:8848"+MessageId.sportPlaceApplyActorReq, ContentType.APPLICATION_JSON, jsonString));
	}
	@Test
	public void getCityPlace() throws ClientProtocolException, IOException {
		
			JSONObject json=new JSONObject();
			json.put("cityName", "成都");
			
			String jsonString = json.toJSONString();
			System.out.println("返回："+HttpRequestUtil.post("http://127.0.0.1:8848"+MessageId.getCitySportPlaceReq, ContentType.APPLICATION_JSON, jsonString));
	}
	@Test
	public void startGame() throws ClientProtocolException, IOException {
			JSONObject json=new JSONObject();
			json.put("userId", 1);
			json.put("sportPlaceId", 8);
			json.put("startTime", "2019-06-10 18:59:06");
			json.put("endTime", "2019-06-10 19:59:06");
			String jsonString = json.toJSONString();
			System.out.println("返回："+HttpRequestUtil.post("http://127.0.0.1:8848"+MessageId.startMatchActor, ContentType.APPLICATION_JSON, jsonString));
	}
	
	@Test
	public void baoming() throws ClientProtocolException, IOException {
			JSONObject json=new JSONObject();
			json.put("userId", 2);
			json.put("orederId", 1);
			
			
			String jsonString = json.toJSONString();
			System.out.println("返回："+HttpRequestUtil.post("http://127.0.0.1:8848"+MessageId.enterMatchReq, ContentType.APPLICATION_JSON, jsonString));
	}
	@Test
	public void cancelBaoming() throws ClientProtocolException, IOException {
			JSONObject json=new JSONObject();
			json.put("userId", 1);
			json.put("orederId", 8);
			String jsonString = json.toJSONString();
			System.out.println("返回："+HttpRequestUtil.post("http://127.0.0.1:8848"+MessageId.cancelMatchReq, ContentType.APPLICATION_JSON, jsonString));
	}
	@Test
	public void rechargeCall() throws ClientProtocolException, IOException {
			JSONObject json=new JSONObject();
			json.put("userId", 1);
			json.put("mySprotlogId", 17);
			json.put("gold", 400);
			json.put("startTime", "2019-06-09 11:41:06");
			json.put("endTime", "2019-06-09 13:41:06");
			
			String jsonString = json.toJSONString();
			System.out.println("返回："+HttpRequestUtil.post("http://127.0.0.1:8848"+MessageId.rechargeReq, ContentType.APPLICATION_JSON, jsonString));
	}
}
