package com.mb.ext.core.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mb.ext.core.service.spec.SignatureDTO;
import com.mb.ext.msg.WXJSONJSAPITicket;

@Component
public class WXJSAPISDKUtility {

	@Autowired
	private PaymentUtil paymentUtil;
	
	@Autowired
	private WXInitializeUtility wxInitializeUtility;
	
    public Map<String, String> getWXJSSignature(String url, String appId, String appSecret) {
    	
    	WXJSONJSAPITicket ticketObject=wxInitializeUtility.getJSAPITicket(appId, appSecret);
    	if(ticketObject.getErrcode()!=0)
    		return null;
    	String jsapi_ticket=ticketObject.getTicket();
    	
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //ע��������������ȫ��Сд���ұ�������
        string1 = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
        System.out.println(string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }
    
    public SignatureDTO getWXPaySignature( String prepay_id) {
    	
    	
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String appid = paymentUtil.getWechatAppIdMiniProgram();
        String packageStr = "prepay_id=" + prepay_id;
        String signType = "MD5";
        String signature = "";

        String str = "appId=" + appid +
                  "&nonceStr=" + nonce_str +
                  "&package=" + packageStr +
                  "&signType=" + signType +
                  "&timeStamp=" + timestamp +
                  "&key=" + paymentUtil.getWechatApiKey();
        signature = encodeMd5(str).toUpperCase();
        
        SignatureDTO signatureDTO = new SignatureDTO();
        signatureDTO.setAppId(appid);
        signatureDTO.setNonceStr(nonce_str);
        signatureDTO.setPackageStr(packageStr);
        signatureDTO.setTimestamp(timestamp);
        signatureDTO.setSignature(signature);
        return signatureDTO;
    }
    
	public SignatureDTO getWXPaySignatureH5( String prepay_id) {
	    	
	    	
	        Map<String, String> ret = new HashMap<String, String>();
	        String nonce_str = create_nonce_str();
	        String timestamp = create_timestamp();
	        String appid = paymentUtil.getWechatAppIdOfficialAccount();
	        String packageStr = "prepay_id=" + prepay_id;
	        String signType = "MD5";
	        String signature = "";
	
	        String str = "appId=" + appid +
	                  "&nonceStr=" + nonce_str +
	                  "&package=" + packageStr +
	                  "&signType=" + signType +
	                  "&timeStamp=" + timestamp +
	                  "&key=" + paymentUtil.getWechatApiKey();
	        signature = encodeMd5(str).toUpperCase();
	        
	        SignatureDTO signatureDTO = new SignatureDTO();
	        signatureDTO.setAppId(appid);
	        signatureDTO.setNonceStr(nonce_str);
	        signatureDTO.setPackageStr(packageStr);
	        signatureDTO.setTimestamp(timestamp);
	        signatureDTO.setSignature(signature);
	        return signatureDTO;
	    }
	
	public SignatureDTO getWXPaySignatureApp( String prepay_id) {
    	
    	
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String appid = paymentUtil.getWechatAppIdOpen();
        String partnerId = paymentUtil.getWechatMerchantId();
        String packageStr = "Sign=WXPay";
        String signType = "MD5";
        String signature = "";

        String str = "appid=" + appid +
                  "&noncestr=" + nonce_str +
                  "&package=" + packageStr +
                  "&partnerid=" + partnerId +
                  "&prepayid=" + prepay_id +
                  "&timestamp=" + timestamp +
                  "&key=" + paymentUtil.getWechatApiKey();
        signature = encodeMd5(str).toUpperCase();
        
        SignatureDTO signatureDTO = new SignatureDTO();
        signatureDTO.setAppId(appid);
        signatureDTO.setNonceStr(nonce_str);
        signatureDTO.setPackageStr(packageStr);
        signatureDTO.setPartnerId(partnerId);
        signatureDTO.setPrepayId(prepay_id);
        signatureDTO.setTimestamp(timestamp);
        signatureDTO.setSignature(signature);
        return signatureDTO;
    }

    private String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
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
}
