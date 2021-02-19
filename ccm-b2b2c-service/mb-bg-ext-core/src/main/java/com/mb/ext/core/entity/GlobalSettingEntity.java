package com.mb.ext.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the global_setting database table.
 * 
 */
@Entity
@Table(name = "global_setting")
@NamedQuery(name = "GlobalSettingEntity.findAll", query = "SELECT u FROM GlobalSettingEntity u")
public class GlobalSettingEntity extends AbstractBaseEntity
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "GLOBAL_SETTING_UUID")
	@GenericGenerator(name = "GLOBAL_SETTING_UUID", strategy = "uuid")
	@Column(name = "GLOBAL_SETTING_UUID", nullable = false, length = 36)
	private String globalSettingUuid;
	
	@Column(name = "APPLICATION_LOGO")
	private String applicationLogo;

	@Column(name = "APPLICATION_NAME")
	private String applicationName;

	@Column(name = "APPLICATION_DESC")
	private String applicationDesc;	
	
	@Column(name = "APPLICATION_PUBLIC_REGISTER_ENABLED")
	private boolean isApplicationPublicRegisterEnabled;	
	
	public boolean isApplicationMerchantEnabled() {
		return isApplicationMerchantEnabled;
	}

	public void setApplicationMerchantEnabled(boolean isApplicationMerchantEnabled) {
		this.isApplicationMerchantEnabled = isApplicationMerchantEnabled;
	}

	@Column(name = "APPLICATION_MERCHANT_ENABLED")
	private boolean isApplicationMerchantEnabled;
	
	@Column(name = "APPLICATION_PRODUCT_ENABLED")
	private boolean isApplicationProductEnabled;
	
	@Column(name = "APPLICATION_CITY_ENABLED")
	private boolean isApplicationCityEnabled;
	
	public boolean isApplicationCityEnabled() {
		return isApplicationCityEnabled;
	}

	public void setApplicationCityEnabled(boolean isApplicationCityEnabled) {
		this.isApplicationCityEnabled = isApplicationCityEnabled;
	}

	@Column(name = "APPLICATION_DELIVERY_EXPRESS_ENABLED")
	private boolean isApplicationDeliveryExpressEnabled;
	
	@Column(name = "APPLICATION_DELIVERY_CITY_ENABLED")
	private boolean isApplicationDeliveryCityEnabled;
	
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

	@Column(name = "APPLICATION_DELIVERY_PICK_ENABLED")
	private boolean isApplicationDeliveryPickEnabled;

	public boolean isApplicationProductEnabled() {
		return isApplicationProductEnabled;
	}

	public void setApplicationProductEnabled(boolean isApplicationProductEnabled) {
		this.isApplicationProductEnabled = isApplicationProductEnabled;
	}

	public String getApplicationVersion() {
		return applicationVersion;
	}

	public boolean isApplicationPublicRegisterEnabled() {
		return isApplicationPublicRegisterEnabled;
	}

	public void setApplicationPublicRegisterEnabled(boolean isApplicationPublicRegisterEnabled) {
		this.isApplicationPublicRegisterEnabled = isApplicationPublicRegisterEnabled;
	}

	public void setApplicationVersion(String applicationVersion) {
		this.applicationVersion = applicationVersion;
	}

	@Column(name = "APPLICATION_DOMAIN_NAME")
	private String applicationDomainName;
	
	@Column(name = "APPLICATION_VERSION")
	private String applicationVersion;
	
	@Column(name = "WECHAT_MERCHANT_ID")
	private String wechatMerchantId;
	
	@Column(name = "WECHAT_APP_ID_OFFICIAL_ACCOUNT")
	private String wechatAppIdOfficialAccount;
	
	@Column(name = "WECHAT_APP_SECRET_OFFICIAL_ACCOUNT")
	private String wechatAppSecretOfficialAccount;
	
	@Column(name = "WECHAT_APP_ID_MINI_PROGRAM")
	private String wechatAppIdMiniProgram;
	
	@Column(name = "WECHAT_APP_SECRET_MINI_PROGRAM")
	private String wechatAppSecretMiniProgram;
	
	@Column(name = "WECHAT_APP_ID_OPEN")
	private String wechatAppIdOpen;
	
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

	@Column(name = "WECHAT_APP_SECRET_OPEN")
	private String wechatAppSecretOpen;
	
	@Column(name = "WECHAT_API_KEY")
	private String wechatApiKey;
	
	@Column(name = "WECHAT_NOTIFY_URL")
	private String wechatNotifyUrl;
	
	@Column(name = "WECHAT_REFUND_NOTIFY_URL")
	private String wechatRefundNotifyUrl;
	
	public String getWechatRefundNotifyUrl() {
		return wechatRefundNotifyUrl;
	}

	public void setWechatRefundNotifyUrl(String wechatRefundNotifyUrl) {
		this.wechatRefundNotifyUrl = wechatRefundNotifyUrl;
	}

	@Column(name = "ALIPAY_APP_ID")
	private String alipayAppId;
	
	@Column(name = "ALIPAY_NOTIFY_URL")
	private String alipayNotifyUrl;
	
	@Column(name = "ALIPAY_REFUND_NOTIFY_URL")
	private String alipayRefundNotifyUrl;
	
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

	@Column(name = "ALIPAY_RETURN_URL")
	private String alipayReturnUrl;
	
	public String getGlobalSettingUuid() {
		return globalSettingUuid;
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
	
	@Column(name = "ALIPAY_PRIVATE_KEY")
	private String alipayPrivateKey;
	
	@Column(name = "ALIPAY_PUBLIC_KEY")
	private String alipayPublicKey;
	
	@Column(name = "OSS_ACCESS_ID")
	private String ossAccessId;
	
	@Column(name = "OSS_ACCESS_KEY")
	private String ossAccessKey;
	
	@Column(name = "OSS_END_POINT")
	private String ossEndPoint;
	
	@Column(name = "OSS_BUCKET_NAME")
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

	@Column(name = "SMS_ACCESSKEY_ID")
	private String smsAccessId;
	
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

	@Column(name = "SMS_ACCESSKEY_SECRET")
	private String smsAccessKey;
	
	@Column(name = "SMS_SIGN_NAME")
	private String smsSignName;
	
	@Column(name = "SMS_TEMPLATE_CODE")
	private String smsTemplateCode;
	
	@Column(name = "SMS_TEMPLATE_CODE_APPLICATION_APPROVED")
	private String smsTemplateCodeApplicationApproved;
	
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

	@Column(name = "SMS_TEMPLATE_CODE_APPLICATION_REJECTED")
	private String smsTemplateCodeApplicationRejected;
	
	@Column(name = "AMAP_WEB_JSAPI_KEY")
	private String amapWebJsapiKey;
	
	@Column(name = "AMAP_WEB_SERVICE_KEY")
	private String amapWebServiceKey;
	
}