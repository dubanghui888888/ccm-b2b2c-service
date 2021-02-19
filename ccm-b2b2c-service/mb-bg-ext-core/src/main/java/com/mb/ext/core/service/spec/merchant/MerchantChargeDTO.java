
package com.mb.ext.core.service.spec.merchant;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.util.JsonDateMinuteDeserializer;
import com.mb.ext.core.util.JsonDateMinuteSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MerchantChargeDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	private String merchantChargeUuid;
	
	private MerchantDTO merchantDTO;
	
	private String outTradeNo;
	
	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	private String code;
	
	private String openId;
	
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMerchantChargeUuid() {
		return merchantChargeUuid;
	}

	public void setMerchantChargeUuid(String merchantChargeUuid) {
		this.merchantChargeUuid = merchantChargeUuid;
	}

	public MerchantDTO getMerchantDTO() {
		return merchantDTO;
	}

	public void setMerchantDTO(MerchantDTO merchantDTO) {
		this.merchantDTO = merchantDTO;
	}

	public BigDecimal getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(BigDecimal chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public String getChargeStatus() {
		return chargeStatus;
	}

	public void setChargeStatus(String chargeStatus) {
		this.chargeStatus = chargeStatus;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getAlipayId() {
		return alipayId;
	}

	public void setAlipayId(String alipayId) {
		this.alipayId = alipayId;
	}

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	private BigDecimal chargeAmount;
	
	private int chargePoint;
	
	public int getPointBefore() {
		return pointBefore;
	}

	public void setPointBefore(int pointBefore) {
		this.pointBefore = pointBefore;
	}

	public int getPointAfter() {
		return pointAfter;
	}

	public void setPointAfter(int pointAfter) {
		this.pointAfter = pointAfter;
	}

	private int pointBefore;
	
	private int pointAfter;
	
	public int getChargePoint() {
		return chargePoint;
	}

	public void setChargePoint(int chargePoint) {
		this.chargePoint = chargePoint;
	}

	private String chargeStatus;
	
	private String chargeNo;
	
	private String failReason;
	
	public String getChargeNo() {
		return chargeNo;
	}

	public void setChargeNo(String chargeNo) {
		this.chargeNo = chargeNo;
	}
	
	private Date chargeTime;
	@JsonSerialize(using=JsonDateMinuteSerializer.class)
	public Date getChargeTime() {
		return chargeTime;
	}
	@JsonDeserialize(using=JsonDateMinuteDeserializer.class)
	public void setChargeTime(Date chargeTime) {
		this.chargeTime = chargeTime;
	}


	private String paymentMethod;
	
	private String bankName;
	
	private String bankAccountNo;
	
	private String bankAccountName;

	private String alipayId;
	
	private String wechatId;
	
}
