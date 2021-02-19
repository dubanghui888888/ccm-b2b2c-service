package com.mb.ext.core.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.mb.ext.core.service.SettingService;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;

@Component
public class PaymentUtil {
	
	@Autowired
	@Qualifier("settingService")
	private SettingService settingService;
	
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());
	
	
    public String getWechatAppIdMiniProgram() {
    	try {
    		return settingService.getGlobalWechatSetting().getWechatAppIdMiniProgram();
    	}catch(BusinessException e) {
    		logger.error("获取支付参数发生异常: "+e.getMessage());
    		return "";
    	}
    }
    
    public String getWechatAppIdOfficialAccount() {
    	try {
    		return settingService.getGlobalWechatSetting().getWechatAppIdOfficialAccount();
    	}catch(BusinessException e) {
    		logger.error("获取支付参数发生异常: "+e.getMessage());
    		return "";
    	}
    }
    
    public String getWechatAppIdOpen() {
    	try {
    		return settingService.getGlobalWechatSetting().getWechatAppIdOpen();
    	}catch(BusinessException e) {
    		logger.error("获取支付参数发生异常: "+e.getMessage());
    		return "";
    	}
    }
    
    public String getWechatApiKey() {
    	try {
    		return settingService.getGlobalWechatSetting().getWechatApiKey();
    	}catch(BusinessException e) {
    		logger.error("获取支付参数发生异常: "+e.getMessage());
    		return "";
    	}
    }
    
    public String getWechatAppSecretMiniProgram() {
    	try {
    		return settingService.getGlobalWechatSetting().getWechatAppSecretMiniProgram();
    	}catch(BusinessException e) {
    		logger.error("获取支付参数发生异常: "+e.getMessage());
    		return "";
    	}
    }
    
    public String getWechatAppSecretOfficialAccount() {
    	try {
    		return settingService.getGlobalWechatSetting().getWechatAppSecretOfficialAccount();
    	}catch(BusinessException e) {
    		logger.error("获取支付参数发生异常: "+e.getMessage());
    		return "";
    	}
    }
    
    public String getWechatMerchantId() {
    	try {
    		return settingService.getGlobalWechatSetting().getWechatMerchantId();
    	}catch(BusinessException e) {
    		logger.error("获取支付参数发生异常: "+e.getMessage());
    		return "";
    	}
    }
    
    public String getWechatNotifyUrl() {
    	try {
    		return settingService.getGlobalWechatSetting().getWechatNotifyUrl();
    	}catch(BusinessException e) {
    		logger.error("获取支付参数发生异常: "+e.getMessage());
    		return "";
    	}
    }
    
    public String getWechatRefundNotifyUrl() {
    	try {
    		return settingService.getGlobalWechatSetting().getWechatRefundNotifyUrl();
    	}catch(BusinessException e) {
    		logger.error("获取支付参数发生异常: "+e.getMessage());
    		return "";
    	}
    }
    
    public String getAlipayAppId() {
    	try {
    		return settingService.getGlobalAlipaySetting().getAlipayAppId();
    	}catch(BusinessException e) {
    		logger.error("获取支付参数发生异常: "+e.getMessage());
    		return "";
    	}
    }

    public String getAlipayPrivateKey() {
    	try {
    		return settingService.getGlobalAlipaySetting().getAlipayPrivateKey();
    	}catch(BusinessException e) {
    		logger.error("获取支付参数发生异常: "+e.getMessage());
    		return "";
    	}
    }
    
    public String getAlipayPublicKey() {
    	try {
    		return settingService.getGlobalAlipaySetting().getAlipayPublicKey();
    	}catch(BusinessException e) {
    		logger.error("获取支付参数发生异常: "+e.getMessage());
    		return "";
    	}
    }
    
    public String getAlipayNotifyUrl() {
    	try {
    		return settingService.getGlobalAlipaySetting().getAlipayNotifyUrl();
    	}catch(BusinessException e) {
    		logger.error("获取支付参数发生异常: "+e.getMessage());
    		return "";
    	}
    }
    
    public String getAlipayRefundNotifyUrl() {
    	try {
    		return settingService.getGlobalAlipaySetting().getAlipayRefundNotifyUrl();
    	}catch(BusinessException e) {
    		logger.error("获取支付参数发生异常: "+e.getMessage());
    		return "";
    	}
    }
    
    public String getAlipayReturnUrl() {
    	try {
    		return settingService.getGlobalAlipaySetting().getAlipayReturnUrl();
    	}catch(BusinessException e) {
    		logger.error("获取支付参数发生异常: "+e.getMessage());
    		return "";
    	}
    }
	
	/**
	 * 小程序通过code换取open id
	 */
	public String getWechatOpenId() {
		return "https://api.weixin.qq.com/sns/jscode2session?appid="+getWechatAppIdMiniProgram()+"&secret="+getWechatAppSecretMiniProgram()+"&js_code="+"CODE"+"&grant_type=authorization_code";	
	}
	
	/**
	 * 微信公众号授权跳转地址(获取code)
	 */
	public String getWechatOauth2Redirect() {
		return "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+getWechatAppIdOfficialAccount()+
		           "&redirect_uri=REDIRECTURL&response_type=code&scope=snsapi_base&state=qjh#wechat_redirect";	
	}
	
	/**
	 * 公众号通过code换取open id
	 */
	public String getWechatAccessTokenOfficialAccount() {
		return "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+getWechatAppIdOfficialAccount()+"&secret="+getWechatAppSecretOfficialAccount()+"&code="+"CODE"+"&grant_type=authorization_code";	
	}
	
	public String getDomainName() {
		try {
    		return settingService.getGlobalApplicationSetting().getApplicationDomainName();
    	}catch(BusinessException e) {
    		logger.error("获取支付参数发生异常: "+e.getMessage());
    		return "";
    	}
	}
}
