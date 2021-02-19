package com.mb.ext.msg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.mb.ext.core.constant.WechatConstants;
import com.mb.ext.core.dao.SettingDAO;
import com.mb.ext.core.entity.SettingEntity;
import com.mb.ext.core.util.WXInitializeUtility;
import com.mb.framework.util.log.LogHelper;
@Component
public class WxSuscribeMessageSender {
	
	@Autowired
	@Qualifier("settingDAO")
	private SettingDAO settingDAO;
	
	@Autowired
	private WXInitializeUtility wxInitializeUtility;
	
	
	private final static LogHelper logger = LogHelper.getInstance(WxSuscribeMessageSender.class.getName());

	public String sendMsg(String appId, String appSecret,String jsonStr){
		String token=	wxInitializeUtility.getEffectiveToken(appId, appSecret);

		String url=WechatConstants.URL_WX_SEND_SUSCRIBE_MSG;
		
		url=url.replace("ACCESS_TOKEN", token);
		
		logger.info("微信请求地址： "+url);

		String msg=	HttpClientHelper.postJsonObject(url, jsonStr);
		
		return msg;
	}
	
	public String sendOrderPayedMsg(String appId, String appSecret,String openId, String orderNo, String paymentTime, String productName, String productAmount, String memo){
		String msg = "";
		try {
			WXSuscribeMessage message = new WXSuscribeMessage();
			SettingEntity settingEntity = settingDAO.getSettingByName("WX_SUSCRIBE_MSG_ORDER_PAY");
			String templateId = settingEntity.getValue();
			message.setTemplate_id(templateId);
			message.setTouser(openId);
			
			WXSuscribeMessageDataOrderPayed data = new WXSuscribeMessageDataOrderPayed();
			WXSuscribeMessageDataValue orderValule = new WXSuscribeMessageDataValue();
			orderValule.setValue(orderNo);
			data.setCharacter_string1(orderValule);
			
			WXSuscribeMessageDataValue dateValule = new WXSuscribeMessageDataValue();
			dateValule.setValue(paymentTime);
			data.setDate2(dateValule);
			
			WXSuscribeMessageDataValue productValule = new WXSuscribeMessageDataValue();
			productValule.setValue(productName);
			data.setThing3(productValule);
			
			WXSuscribeMessageDataValue amountValule = new WXSuscribeMessageDataValue();
			amountValule.setValue(productAmount);
			data.setAmount4(amountValule);
			
			WXSuscribeMessageDataValue memoValule = new WXSuscribeMessageDataValue();
			memoValule.setValue(memo);
			data.setThing7(memoValule);
			
			message.setData(data);
			String jsonMsg=JSON.toJSONString(message);
	
			logger.info("发送订单支付订阅消息请求: "+jsonMsg);
			
			msg = sendMsg(appId, appSecret, jsonMsg);
			
			logger.info("发送订单支付订阅消息返回: "+msg);
			
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("发送订单支付成功模板消息发生异常： "+e.getMessage());
		}
		return msg;
	}
	
	public String sendOrderDeliveredMsg(String appId, String appSecret,String openId, String orderNo, String deliverTime, String productName, String courierName, String courierId){
		String msg = "";
		try {
			WXSuscribeMessage message = new WXSuscribeMessage();
			SettingEntity settingEntity = settingDAO.getSettingByName("WX_SUSCRIBE_MSG_ORDER_DELIVERED");
			String templateId = settingEntity.getValue();
			message.setTemplate_id(templateId);
			message.setTouser(openId);
			
			WXSuscribeMessageDataOrderDelivered data = new WXSuscribeMessageDataOrderDelivered();
			WXSuscribeMessageDataValue orderValule = new WXSuscribeMessageDataValue();
			orderValule.setValue(orderNo);
			data.setCharacter_string1(orderValule);
			
			WXSuscribeMessageDataValue dateValule = new WXSuscribeMessageDataValue();
			dateValule.setValue(deliverTime);
			data.setDate5(dateValule);
			
			WXSuscribeMessageDataValue productValule = new WXSuscribeMessageDataValue();
			productValule.setValue(productName);
			data.setThing2(productValule);
			
			WXSuscribeMessageDataValue courierNameValule = new WXSuscribeMessageDataValue();
			courierNameValule.setValue(courierName);
			data.setThing7(courierNameValule);
			
			WXSuscribeMessageDataValue courierIdValule = new WXSuscribeMessageDataValue();
			courierIdValule.setValue(courierId);
			data.setCharacter_string4(courierIdValule);
			
			message.setData(data);
			String jsonMsg=JSON.toJSONString(message);
	
			logger.info("发送订单发货订阅消息请求: "+jsonMsg);
			
			msg = sendMsg(appId, appSecret, jsonMsg);
			
			logger.info("发送订单发货订阅消息返回: "+msg);
			
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("发送订单发货订阅消息发生异常： "+e.getMessage());
		}
		return msg;
	}
	
	public String sendOrderReturnMsg(String appId, String appSecret,String openId, String orderNo, String returnTime, String productName, String returnAmount, String returnStatus){
		String msg = "";
		try {
			WXSuscribeMessage message = new WXSuscribeMessage();
			SettingEntity settingEntity = settingDAO.getSettingByName("WX_SUSCRIBE_MSG_ORDER_RETURN");
			String templateId = settingEntity.getValue();
			message.setTemplate_id(templateId);
			message.setTouser(openId);
			
			WXSuscribeMessageDataOrderReturn data = new WXSuscribeMessageDataOrderReturn();
			WXSuscribeMessageDataValue orderValule = new WXSuscribeMessageDataValue();
			orderValule.setValue(orderNo);
			data.setCharacter_string4(orderValule);
			
			WXSuscribeMessageDataValue dateValule = new WXSuscribeMessageDataValue();
			dateValule.setValue(returnTime);
			data.setDate3(dateValule);
			
			WXSuscribeMessageDataValue productValule = new WXSuscribeMessageDataValue();
			productValule.setValue(productName);
			data.setThing5(productValule);
			
			WXSuscribeMessageDataValue amountValule = new WXSuscribeMessageDataValue();
			amountValule.setValue(returnAmount);
			data.setAmount2(amountValule);
			
			WXSuscribeMessageDataValue memoValule = new WXSuscribeMessageDataValue();
			memoValule.setValue(returnStatus);
			data.setThing6(memoValule);
			
			message.setData(data);
			String jsonMsg=JSON.toJSONString(message);
	
			logger.info("发送退款订阅消息请求: "+jsonMsg);
			
			msg = sendMsg(appId, appSecret, jsonMsg);
			
			logger.info("发送退款订阅消息返回: "+msg);
			
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("发送退款订阅消息发生异常： "+e.getMessage());
		}
		return msg;
	}
	
	public String sendAwardMsg(String appId, String appSecret,String openId, String awardType, String awardAmount, String awardDesc){
		String msg = "";
		try {
			WXSuscribeMessage message = new WXSuscribeMessage();
			SettingEntity settingEntity = settingDAO.getSettingByName("WX_SUSCRIBE_MSG_AWARD");
			String templateId = settingEntity.getValue();
			message.setTemplate_id(templateId);
			message.setTouser(openId);
			
			WXSuscribeMessageDataAward data = new WXSuscribeMessageDataAward();
			WXSuscribeMessageDataValue awardTypeValule = new WXSuscribeMessageDataValue();
			awardTypeValule.setValue(awardType);
			data.setThing1(awardTypeValule);
			
			WXSuscribeMessageDataValue awardAmountValule = new WXSuscribeMessageDataValue();
			awardAmountValule.setValue(awardAmount);
			data.setThing2(awardAmountValule);
			
			WXSuscribeMessageDataValue awardDescValule = new WXSuscribeMessageDataValue();
			awardDescValule.setValue(awardDesc);
			data.setThing3(awardDescValule);
			
			message.setData(data);
			String jsonMsg=JSON.toJSONString(message);
	
			logger.info("发送奖励订阅消息请求: "+jsonMsg);
			
			msg = sendMsg(appId, appSecret, jsonMsg);
			
			logger.info("发送奖励订阅消息返回: "+msg);
			
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("发送奖励订阅消息发生异常： "+e.getMessage());
		}
		return msg;
	}
}
