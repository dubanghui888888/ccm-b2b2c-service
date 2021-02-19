package com.mb.ext.core.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.URL;
import java.security.KeyStore;
import java.security.MessageDigest;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mb.ext.core.constant.LocationConstants;
import com.mb.ext.core.constant.WechatConstants;
import com.mb.ext.core.service.spec.WechatLiveRoomsResponse;
import com.mb.ext.core.service.spec.WechatPayRequest;
import com.mb.ext.core.service.spec.WechatRefundRequest;
import com.mb.ext.msg.HttpClientHelper;
import com.mb.ext.msg.WXJSONAccessToken;
import com.mb.ext.msg.WXJSONUserInfo;
import com.mb.framework.util.log.LogHelper;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
@Component
public class WechatUtil {
	
	@Autowired
	private PaymentUtil paymentUtil;
	
	@Autowired
	private WXInitializeUtility wxInitializeUtility;
	
	private static final LogHelper logger = LogHelper.getInstance("BuyUtil"
			);
	
	public String getSign(WechatPayRequest payRequest) {
		String signTemp = "appid=" + payRequest.getAppid() + "&attach=" + payRequest.getAttach()
				+"&body="
				+ payRequest.getBody() + "&mch_id=" + payRequest.getMch_id()
				+ "&nonce_str=" + payRequest.getNonce_str() + "&notify_url="
				+ payRequest.getNotify_url() + "&openid="
				+ payRequest.getOpenid() + "&out_trade_no="
				+ payRequest.getOut_trade_no() + "&product_id="
				+ payRequest.getProduct_id() + "&spbill_create_ip="
				+ payRequest.getSpbill_create_ip() + "&total_fee="
				+ payRequest.getTotal_fee() + "&trade_type="
				+ payRequest.getTrade_type() + "&key=" + paymentUtil.getWechatApiKey(); 
		String sign = encodeMd5(signTemp).toUpperCase();
		return sign;
	}
	public String getAppSign(WechatPayRequest payRequest) {
		String signTemp = "appid=" + payRequest.getAppid() + "&attach=" + payRequest.getAttach()
				+"&body="
				+ payRequest.getBody() + "&mch_id=" + payRequest.getMch_id()
				+ "&nonce_str=" + payRequest.getNonce_str() + "&notify_url="
				+ payRequest.getNotify_url() +  "&out_trade_no="
				+ payRequest.getOut_trade_no() + "&product_id="
				+ payRequest.getProduct_id() + "&spbill_create_ip="
				+ payRequest.getSpbill_create_ip() + "&total_fee="
				+ payRequest.getTotal_fee() + "&trade_type="
				+ payRequest.getTrade_type() + "&key=" + paymentUtil.getWechatApiKey(); 
		String sign = encodeMd5(signTemp).toUpperCase();
		return sign;
	}
	public String getRefundSign(WechatRefundRequest refundRequest) {
		String signTemp = "appid=" + refundRequest.getAppid() + "&mch_id=" + refundRequest.getMch_id() + "&nonce_str="
				+ refundRequest.getNonce_str() + "&notify_url=" + refundRequest.getNotify_url() + "&out_refund_no="
				+ refundRequest.getOut_refund_no() + "&refund_fee=" + refundRequest.getRefund_fee() + "&total_fee="
				+ refundRequest.getTotal_fee() + "&transaction_id=" + refundRequest.getTransaction_id() + "&key="
				+ paymentUtil.getWechatApiKey();
		String sign = encodeMd5(signTemp).toUpperCase();
		return sign;
	}
	public String toXML(Object request) {
		XStream xstreamReq = new XStream(new XppDriver());
		xstreamReq.alias("xml", request.getClass());
		String requestXML = xstreamReq.toXML(request).replace("\n", "").replace("__", "_");
		return requestXML;
	}
	public Object fromXML2WechatResponse(Class responseClass, String responseXML) {
		XStream xstreamRes = new XStream(new XppDriver() {
			public HierarchicalStreamWriter createWriter(Writer out) {
				return new PrettyPrintWriter(out) {
					// ����CDATA���
					boolean cdata = true;

					@SuppressWarnings("rawtypes")
					public void startNode(String name, Class clazz) {
						super.startNode(name, clazz);
					}

					protected void writeText(QuickWriter writer, String text) {
						if (cdata) {
							writer.write("<![CDATA[");
							writer.write(text);
							writer.write("]]>");
						} else {
							writer.write(text);
						}
					}
				};
			}
		});
		xstreamRes.alias("xml", responseClass);
//		WechatPayResponse payResponse = (WechatPayResponse) xstreamRes.fromXML(responseXML);
		Object response = xstreamRes.fromXML(responseXML);
		return response;
	}

	public Object fromXML2WechatResult(Class resultClass,String responseXML, String root) {
		XStream xstreamRes = new XStream(new XppDriver() {
			public HierarchicalStreamWriter createWriter(Writer out) {
				return new PrettyPrintWriter(out) {
					// ����CDATA���
					boolean cdata = true;

					@SuppressWarnings("rawtypes")
					public void startNode(String name, Class clazz) {
						super.startNode(name, clazz);
					}

					protected void writeText(QuickWriter writer, String text) {
						if (cdata) {
							writer.write("<![CDATA[");
							writer.write(text);
							writer.write("]]>");
						} else {
							writer.write(text);
						}
					}
				};
			}
		});
		xstreamRes.alias(root, resultClass);
//		WechatPayResult payResponse = (WechatPayResult) xstreamRes.fromXML(responseXML);
		Object response =  xstreamRes.fromXML(responseXML);
		return response;
	}
	
	public String postWechatUnifiedOrder(String requestXML) throws Exception{
		BufferedReader reader = null;
		InputStream is = null;
		DataOutputStream out = null;
		StringBuffer sbf = new StringBuffer();
		String wechatResponseStr = "";
		try {
			URL url = new URL(WechatConstants.WECHAT_UNIFIED_ORDER_URL);
			HttpsURLConnection connection = (HttpsURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			out = new DataOutputStream(connection.getOutputStream()); 
			out.writeBytes(requestXML);
			out.flush();
			is = connection.getInputStream();

			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			wechatResponseStr = sbf.toString();
		} finally {
			try {
				reader.close();
				is.close();
				out.close();
			} catch (IOException e) {
				
			}
		}
		return wechatResponseStr;
	}
	
	public String postWechatRefund(String requestXML) throws Exception{
		//指定读取证书格式为PKCS12(注意PKCS12证书 是从微信商户平台-》账户设置-》 API安全 中下载的)
		KeyStore keyStore = KeyStore.getInstance("PKCS12"); 
		String fileName = LocationConstants.WECHAT_API_PKCS12; //文件名
		FileInputStream instream = new FileInputStream(new File(fileName));
		try { 
			//指定PKCS12的密码(商户ID) 
			keyStore.load(instream, paymentUtil.getWechatMerchantId().toCharArray()); 
			} finally { 
				instream.close(); 
			} 
		SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, paymentUtil.getWechatMerchantId().toCharArray()).build(); 
		//指定TLS版本 
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory( sslcontext,new String[] { "TLSv1"},null,SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER); 
		//设置httpclient的SSLSocketFactory 
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		try {
			HttpPost httpost = new HttpPost(WechatConstants.WECHAT_REFUND_URL); // 设置响应头信息
			httpost.addHeader("Connection", "keep-alive");
			httpost.addHeader("Accept", "*/*");
			httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			httpost.addHeader("Host", "api.mch.weixin.qq.com");
			httpost.addHeader("X-Requested-With", "XMLHttpRequest");
			httpost.addHeader("Cache-Control", "max-age=0");
			httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
			httpost.setEntity(new StringEntity(requestXML, "UTF-8"));
			CloseableHttpResponse response = httpclient.execute(httpost);
			try {
				HttpEntity entity = response.getEntity();
				String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
				EntityUtils.consume(entity);
				return jsonStr;
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
	}
	
	public String encodeMd5(String inStr) {
		StringBuffer hexValue = new StringBuffer();
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");

			byte[] byteArray = inStr.getBytes("UTF-8");
			byte[] md5Bytes = md5.digest(byteArray);

			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16) {
					hexValue.append("0");
				}
				hexValue.append(Integer.toHexString(val));
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		return hexValue.toString();
	}
	
	public WXJSONAccessToken getOpenId(String code) {
		String url = paymentUtil.getWechatOpenId().replace("CODE", code);
		String r = HttpClientHelper.sendGet(url, null, "UTF-8");
		logger.info("get open id result: "+r);
		WXJSONAccessToken token = JSON.parseObject(r,
				WXJSONAccessToken.class);
		return token;
	}
	
	public WXJSONAccessToken getOpenIdByCode4OfficialAccount(String code) {
		String url = paymentUtil.getWechatAccessTokenOfficialAccount().replace("CODE", code);
		String r = HttpClientHelper.sendGet(url, null, "UTF-8");
		logger.info("get open id result: "+r);
		WXJSONAccessToken token = JSON.parseObject(r,
				WXJSONAccessToken.class);
		return token;
	}
	
	public WXJSONUserInfo getWechatUserInfo(String accessToken,String openId) {
		String url = WechatConstants.URL_WX_USER_INFO.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		String r = HttpClientHelper.sendGet(url, null, "UTF-8");
		logger.info("get wechat user info result: "+r);
		WXJSONUserInfo userInfo = JSON.parseObject(r,
				WXJSONUserInfo.class);
		return userInfo;
	}
	
	public WechatLiveRoomsResponse getLiveRooms(int start, int limit) {
		String accessToken = wxInitializeUtility.getEffectiveToken(paymentUtil.getWechatAppIdMiniProgram(),paymentUtil.getWechatAppSecretMiniProgram());
		logger.info("ACCESS_TOKEN: "+accessToken);
		JSONObject req = new JSONObject();
		req.put("start", start);
		req.put("limit", limit);
		String requestStr = req.toJSONString();
		logger.info("获取微信直播间请求: " + requestStr);
		String res = HttpClientHelper.postJsonObject(WechatConstants.URL_LIVE_ROOMS.replace("ACCESS_TOKEN", accessToken), requestStr);
		logger.info("获取微信直播间返回: " + res);
		WechatLiveRoomsResponse response = JSONObject.parseObject(res, WechatLiveRoomsResponse.class);
		return response;
	}
}
