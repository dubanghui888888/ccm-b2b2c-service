package com.mb.ext.core.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mb.ext.core.constant.WechatConstants;
import com.mb.ext.msg.HttpClientHelper;
import com.mb.framework.util.log.LogHelper;
import com.mb.ext.msg.WXJSONAccessToken;
import com.mb.ext.msg.WXJSONJSAPITicket;
import com.mb.ext.msg.WXJSONNews;
import com.mb.ext.msg.WXJSONText;

@Component
public class WXInitializeUtility {
	
	@Autowired
	private PaymentUtil paymentUtil;
	
	private final static LogHelper logger = LogHelper.getInstance(WXInitializeUtility.class
			.getName());

	// private Map tokenMap=new HashMap<String, String>();
	private static WXTokenInfo wxToken;

	/**
	 * 检查签名
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSignature(String signature, String timestamp,
			String nonce) {
		String[] arr = new String[] { WechatConstants.TOKEN, timestamp, nonce };
		Arrays.sort(arr);

		StringBuffer content = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}

		String encryptString = getSha1(content.toString());

		return encryptString.equals(signature);
	}

	/**
	 * 获取AccessToken
	 * 
	 * @return
	 */
	public String getAccessToken(String appId, String appSecret) {

		String r = HttpClientHelper.sendGet(WechatConstants.URL_ACCESSTOKEN.replace("APP_ID", appId).replace("APP_SECRET", appSecret), null, "UTF-8");

		logger.info("getAccessToken response:"+r);
		
		return r;
		
		
	}

	/**
	 * 解析AccessToken
	 * 
	 * @return
	 */
	public WXJSONAccessToken parseWXJSONAccessToken(String appId, String appSecret) {
		wxToken = new WXTokenInfo();
		wxToken.timestamp = new Date().getTime();

		String accessToken = getAccessToken(appId,appSecret);

		WXJSONAccessToken token = JSON.parseObject(accessToken,
				WXJSONAccessToken.class);
		wxToken.expiresin = token.getExpires_in();
		wxToken.token = token.getAccess_token();

		return token;

	}

	/**
	 * 获取票据对象
	 * @return 返回WXJSONJSAPITicket 对象
	 */
	public WXJSONJSAPITicket getJSAPITicket(String appId, String appSecret){
		String token=getEffectiveToken(appId, appSecret);

		String url=WechatConstants.URL_JSAPI_TICKET;
		url=url.replace("ACCESS_TOKEN", token);

		String r = HttpClientHelper.sendGet(url, null, "UTF-8");

		WXJSONJSAPITicket ticket=JSON.parseObject(r,WXJSONJSAPITicket.class);

		return ticket;
	}

	/**
	 * 得到当前有效的Token值
	 * 
	 * @return
	 */
	public String getEffectiveToken(String appId, String appSecret) {
		if (wxToken == null) {
			WXJSONAccessToken token = parseWXJSONAccessToken(appId, appSecret);
			return token.getAccess_token();
		}

		long timestamp = new Date().getTime();
		int interval = wxToken.expiresin * 1000; // timestamp是毫秒为单位
		// token中的时间以秒为单位  1秒=1000毫秒

		if (interval + wxToken.timestamp > timestamp) {
			return wxToken.getToken();
		} else {
			WXJSONAccessToken token = parseWXJSONAccessToken(appId, appSecret);
			return token.getAccess_token();
		}
	}

	/**
	 * sha1加密
	 * 
	 * @param str
	 * @return
	 */
	private static String getSha1(String str) {
		if (null == str || 0 == str.length()) {
			return null;
		}
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("UTF-8"));

			byte[] md = mdTemp.digest();
			int j = md.length;
			char[] buf = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(buf);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 初始化微信主菜单
	 *  修改历史：2017年6月3日 修改菜单地址
	 *  		 2017年6月17日 修改菜单地址
	 *  		 2017年9月5日 修改菜单地址
	 * @return 
	 * @throws Exception 
	 */
	/**
	 * 微信TOKEN信息
	 * 
	 * @author qiaojianhui
	 * 
	 */
	public static class WXTokenInfo {
		private String token;
		private long timestamp;
		private int expiresin;

		/**
		 * @return the token
		 */
		public String getToken() {
			return token;
		}

		/**
		 * @param token
		 *            the token to set
		 */
		public void setToken(String token) {
			this.token = token;
		}

		/**
		 * @return the timestamp
		 */
		public long getTimestamp() {
			return timestamp;
		}

		/**
		 * @param timestamp
		 *            the timestamp to set
		 */
		public void setTimestamp(long timestamp) {
			this.timestamp = timestamp;
		}

		public int getExpiresin() {
			return expiresin;
		}

		public void setExpiresin(int expiresin) {
			this.expiresin = expiresin;
		}

	}
	
	/*
	 * 发送客服图文消息
	 */
	public void sendMessageNews(WXJSONNews news,String openId) {
		
		String accessToken = getEffectiveToken(paymentUtil.getWechatAppIdOfficialAccount(),paymentUtil.getWechatAppSecretOfficialAccount());
		logger.info("ACCESS_TOKEN: "+accessToken);
		JSONObject req = new JSONObject();
		req.put("touser", openId);
		req.put("msgtype", "news");
		req.put("news", news);
		String requestStr = req.toJSONString();
		logger.info("Send Message News Request: " + requestStr);
		String res = HttpClientHelper.postJsonObject(WechatConstants.URL_WX_SEND_CUSTOMER_MSG.replace("ACCESS_TOKEN", accessToken), requestStr);
		logger.info("Send Message News Response: " + res);
	}
	
	/*
	 * 发送客服文本息
	 */
	public void sendMessageText(WXJSONText text,String openId) {
		
		String accessToken = getEffectiveToken(paymentUtil.getWechatAppIdOfficialAccount(),paymentUtil.getWechatAppSecretOfficialAccount());
		logger.info("ACCESS_TOKEN: "+accessToken);
		JSONObject req = new JSONObject();
		req.put("touser", openId);
		req.put("msgtype", "text");
		req.put("text", text);
		String requestStr = req.toJSONString();
		logger.info("Send Message News Request: " + requestStr);
		String res = HttpClientHelper.postJsonObject(WechatConstants.URL_WX_SEND_CUSTOMER_MSG.replace("ACCESS_TOKEN", accessToken), requestStr);
		logger.info("Send Message News Response: " + res);
	}

	/**
	 * @param args 
	 */
	public static void main(String[] args) {

	}
}
