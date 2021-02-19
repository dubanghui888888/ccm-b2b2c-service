
package com.mb.ext.core.service.spec;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class GlobalSettingDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private String globalSettingUuid;
	
	private String applicationLogo;
	
	private String applicationVersion;
	
	public boolean isApplicationMerchantEnabled() {
		return isApplicationMerchantEnabled;
	}

	public void setApplicationMerchantEnabled(boolean isApplicationMerchantEnabled) {
		this.isApplicationMerchantEnabled = isApplicationMerchantEnabled;
	}

	private boolean isApplicationPublicRegisterEnabled;
	
	private boolean isApplicationMerchantEnabled;	
	
	private boolean isApplicationCityEnabled;
	
	public boolean isApplicationCityEnabled() {
		return isApplicationCityEnabled;
	}

	public void setApplicationCityEnabled(boolean isApplicationCityEnabled) {
		this.isApplicationCityEnabled = isApplicationCityEnabled;
	}

	public boolean isApplicationDeliveryExpressEnabled() {
		return isApplicationDeliveryExpressEnabled;
	}

	public void setApplicationDeliveryExpressEnabled(boolean isApplicationDeliveryExpressEnabled) {
		this.isApplicationDeliveryExpressEnabled = isApplicationDeliveryExpressEnabled;
	}

	public boolean isApplicationDeliveryCityEnabled() {
		return isApplicationDeliveryCityEnabled;
	}

	public void setApplicationDeliveryCityEnabled(boolean isApplicationDeliveryCityEnabled) {
		this.isApplicationDeliveryCityEnabled = isApplicationDeliveryCityEnabled;
	}

	public boolean isApplicationDeliveryPickEnabled() {
		return isApplicationDeliveryPickEnabled;
	}

	public void setApplicationDeliveryPickEnabled(boolean isApplicationDeliveryPickEnabled) {
		this.isApplicationDeliveryPickEnabled = isApplicationDeliveryPickEnabled;
	}

	private boolean isApplicationProductEnabled;
	
	private boolean isApplicationDeliveryExpressEnabled;
	
	private boolean isApplicationDeliveryCityEnabled;
	
	private boolean isApplicationDeliveryPickEnabled;

	public boolean isApplicationProductEnabled() {
		return isApplicationProductEnabled;
	}

	public void setApplicationProductEnabled(boolean isApplicationProductEnabled) {
		this.isApplicationProductEnabled = isApplicationProductEnabled;
	}

	public boolean isApplicationPublicRegisterEnabled() {
		return isApplicationPublicRegisterEnabled;
	}

	public void setApplicationPublicRegisterEnabled(boolean isApplicationPublicRegisterEnabled) {
		this.isApplicationPublicRegisterEnabled = isApplicationPublicRegisterEnabled;
	}

	public String getGlobalSettingUuid() {
		return globalSettingUuid;
	}

	public String getApplicationVersion() {
		return applicationVersion;
	}

	public void setApplicationVersion(String applicationVersion) {
		this.applicationVersion = applicationVersion;
	}

	public void setGlobalSettingUuid(String globalSettingUuid) {
		this.globalSettingUuid = globalSettingUuid;
	}

	public String getApplicationLogo() {
		return applicationLogo;
	}

	public void setApplicationLogo(String applicationLogo) {
		this.applicationLogo = applicationLogo;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getApplicationDesc() {
		return applicationDesc;
	}

	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}

	public String getApplicationDomainName() {
		return applicationDomainName;
	}

	public void setApplicationDomainName(String applicationDomainName) {
		this.applicationDomainName = applicationDomainName;
	}

	public String getWechatMerchantId() {
		return wechatMerchantId;
	}

	public void setWechatMerchantId(String wechatMerchantId) {
		this.wechatMerchantId = wechatMerchantId;
	}

	public String getWechatAppIdOfficialAccount() {
		return wechatAppIdOfficialAccount;
	}

	public void setWechatAppIdOfficialAccount(String wechatAppIdOfficialAccount) {
		this.wechatAppIdOfficialAccount = wechatAppIdOfficialAccount;
	}

	public String getWechatAppSecretOfficialAccount() {
		return wechatAppSecretOfficialAccount;
	}

	public void setWechatAppSecretOfficialAccount(String wechatAppSecretOfficialAccount) {
		this.wechatAppSecretOfficialAccount = wechatAppSecretOfficialAccount;
	}

	public String getWechatAppIdMiniProgram() {
		return wechatAppIdMiniProgram;
	}

	public void setWechatAppIdMiniProgram(String wechatAppIdMiniProgram) {
		this.wechatAppIdMiniProgram = wechatAppIdMiniProgram;
	}

	public String getWechatAppSecretMiniProgram() {
		return wechatAppSecretMiniProgram;
	}

	public void setWechatAppSecretMiniProgram(String wechatAppSecretMiniProgram) {
		this.wechatAppSecretMiniProgram = wechatAppSecretMiniProgram;
	}

	public String getWechatApiKey() {
		return wechatApiKey;
	}

	public void setWechatApiKey(String wechatApiKey) {
		this.wechatApiKey = wechatApiKey;
	}

	public String getWechatNotifyUrl() {
		return wechatNotifyUrl;
	}

	public void setWechatNotifyUrl(String wechatNotifyUrl) {
		this.wechatNotifyUrl = wechatNotifyUrl;
	}

	public String getAlipayAppId() {
		return alipayAppId;
	}

	public void setAlipayAppId(String alipayAppId) {
		this.alipayAppId = alipayAppId;
	}

	public String getAlipayNotifyUrl() {
		return alipayNotifyUrl;
	}

	public void setAlipayNotifyUrl(String alipayNotifyUrl) {
		this.alipayNotifyUrl = alipayNotifyUrl;
	}

	public String getAlipayPrivateKey() {
		return alipayPrivateKey;
	}

	public void setAlipayPrivateKey(String alipayPrivateKey) {
		this.alipayPrivateKey = alipayPrivateKey;
	}

	public String getAlipayPublicKey() {
		return alipayPublicKey;
	}

	public void setAlipayPublicKey(String alipayPublicKey) {
		this.alipayPublicKey = alipayPublicKey;
	}

	public String getOssAccessId() {
		return ossAccessId;
	}

	public void setOssAccessId(String ossAccessId) {
		this.ossAccessId = ossAccessId;
	}

	public String getOssAccessKey() {
		return ossAccessKey;
	}

	public void setOssAccessKey(String ossAccessKey) {
		this.ossAccessKey = ossAccessKey;
	}

	public String getOssEndPoint() {
		return ossEndPoint;
	}

	public void setOssEndPoint(String ossEndPoint) {
		this.ossEndPoint = ossEndPoint;
	}

	public String getOssBucketName() {
		return ossBucketName;
	}

	public void setOssBucketName(String ossBucketName) {
		this.ossBucketName = ossBucketName;
	}

	private String applicationName;

	private String applicationDesc;	

	private String applicationDomainName;
	
	private String wechatMerchantId;
	
	private String wechatAppIdOfficialAccount;
	
	private String wechatAppSecretOfficialAccount;
	
	private String wechatAppIdMiniProgram;
	
	private String wechatAppSecretMiniProgram;
	
	private String wechatAppIdOpen;
	
	private String wechatAppSecretOpen;
	
	public String getWechatAppIdOpen() {
		return wechatAppIdOpen;
	}

	public void setWechatAppIdOpen(String wechatAppIdOpen) {
		this.wechatAppIdOpen = wechatAppIdOpen;
	}

	public String getWechatAppSecretOpen() {
		return wechatAppSecretOpen;
	}

	public void setWechatAppSecretOpen(String wechatAppSecretOpen) {
		this.wechatAppSecretOpen = wechatAppSecretOpen;
	}

	private String wechatApiKey;
	
	private String wechatNotifyUrl;
	
	private String wechatRefundNotifyUrl;
	
	public String getWechatRefundNotifyUrl() {
		return wechatRefundNotifyUrl;
	}

	public void setWechatRefundNotifyUrl(String wechatRefundNotifyUrl) {
		this.wechatRefundNotifyUrl = wechatRefundNotifyUrl;
	}

	private String alipayAppId;
	
	private String alipayNotifyUrl;
	
	private String alipayRefundNotifyUrl;
	
	private String alipayReturnUrl;
	
	public String getAlipayRefundNotifyUrl() {
		return alipayRefundNotifyUrl;
	}

	public void setAlipayRefundNotifyUrl(String alipayRefundNotifyUrl) {
		this.alipayRefundNotifyUrl = alipayRefundNotifyUrl;
	}

	public String getAlipayReturnUrl() {
		return alipayReturnUrl;
	}

	public void setAlipayReturnUrl(String alipayReturnUrl) {
		this.alipayReturnUrl = alipayReturnUrl;
	}
	
	private String alipayPrivateKey;
	
	private String alipayPublicKey;
	
	private String ossAccessId;
	
	private String ossAccessKey;
	
	private String ossEndPoint;
	
	private String ossBucketName;
	
	public String getSmsAccessId() {
		return smsAccessId;
	}

	public void setSmsAccessId(String smsAccessId) {
		this.smsAccessId = smsAccessId;
	}

	public String getSmsAccessKey() {
		return smsAccessKey;
	}

	public void setSmsAccessKey(String smsAccessKey) {
		this.smsAccessKey = smsAccessKey;
	}

	public String getSmsSignName() {
		return smsSignName;
	}

	public void setSmsSignName(String smsSignName) {
		this.smsSignName = smsSignName;
	}

	public String getSmsTemplateCode() {
		return smsTemplateCode;
	}

	public void setSmsTemplateCode(String smsTemplateCode) {
		this.smsTemplateCode = smsTemplateCode;
	}

	private String smsAccessId;
	
	private String smsAccessKey;
	
	public String getSmsTemplateCodeApplicationApproved() {
		return smsTemplateCodeApplicationApproved;
	}

	public void setSmsTemplateCodeApplicationApproved(String smsTemplateCodeApplicationApproved) {
		this.smsTemplateCodeApplicationApproved = smsTemplateCodeApplicationApproved;
	}

	public String getSmsTemplateCodeApplicationRejected() {
		return smsTemplateCodeApplicationRejected;
	}

	public void setSmsTemplateCodeApplicationRejected(String smsTemplateCodeApplicationRejected) {
		this.smsTemplateCodeApplicationRejected = smsTemplateCodeApplicationRejected;
	}

	private String smsSignName;
	
	public String getAmapWebJsapiKey() {
		return amapWebJsapiKey;
	}

	public void setAmapWebJsapiKey(String amapWebJsapiKey) {
		this.amapWebJsapiKey = amapWebJsapiKey;
	}

	public String getAmapWebServiceKey() {
		return amapWebServiceKey;
	}

	public void setAmapWebServiceKey(String amapWebServiceKey) {
		this.amapWebServiceKey = amapWebServiceKey;
	}

	private String smsTemplateCode;
	
	private String smsTemplateCodeApplicationApproved;
	
	private String smsTemplateCodeApplicationRejected;
	
	private String amapWebJsapiKey;
	
	private String amapWebServiceKey;
}
