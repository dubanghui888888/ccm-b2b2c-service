package com.mb.ext.core.entity.merchant;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the merchant_charge database table.
 * 
 */
@Entity
@Table(name = "merchant_charge")
@NamedQuery(name = "MerchantChargeEntity.findAll", query = "SELECT u FROM MerchantChargeEntity u")
public class MerchantChargeEntity extends AbstractBaseEntity
{

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


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "MERCHANTCHARGE_UUID")
	@GenericGenerator(name = "MERCHANTCHARGE_UUID", strategy = "uuid")
	@Column(name = "MERCHANTCHARGE_UUID", nullable = false, length = 36)
	private String merchantChargeUuid;

	
	public String getMerchantChargeUuid() {
		return merchantChargeUuid;
	}

	public void setMerchantChargeUuid(String merchantChargeUuid) {
		this.merchantChargeUuid = merchantChargeUuid;
	}


	public MerchantEntity getMerchantEntity() {
		return merchantEntity;
	}

	public void setMerchantEntity(MerchantEntity merchantEntity) {
		this.merchantEntity = merchantEntity;
	}


	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_UUID")
	private MerchantEntity merchantEntity;
	
	//充值金额
	@Column(name = "CHARGE_AMOUNT")
	private BigDecimal chargeAmount;
	
	//充值积分
	@Column(name = "CHARGE_POINT")
	private int chargePoint;
	
	//充值前积分
	@Column(name = "POINT_BEFORE")
	private int pointBefore;
	
	//充值后积分
	@Column(name = "POINT_AFTER")
	private int pointAfter;
	
	public int getChargePoint() {
		return chargePoint;
	}

	public void setChargePoint(int chargePoint) {
		this.chargePoint = chargePoint;
	}


	//提现状态
	@Column(name = "CHARGE_STATUS")
	private String chargeStatus;
	
	//提现编号
	@Column(name = "CHARGE_NO")
	private String chargeNo;
	
	//提现申请时间
	@Column(name = "CHARGE_TIME")
	private Date chargeTime;
	
	public Date getChargeTime() {
		return chargeTime;
	}

	public void setChargeTime(Date chargeTime) {
		this.chargeTime = chargeTime;
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

	public String getChargeNo() {
		return chargeNo;
	}

	public void setChargeNo(String chargeNo) {
		this.chargeNo = chargeNo;
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


	//提现失败原因
	@Column(name = "FAIL_REASON")
	private String failReason;

	//第三方支付返回的交易序列号
	@Column(name = "OUT_TRADE_NO")
	private String outTradeNo;
	
	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}


	//支付方式
	@Column(name = "PAYMENT_METHOD")
	private String paymentMethod;
	
	//提现至银行名称
	@Column(name = "BANKNAME")
	private String bankName;
	
	//提现至银行账号
	@Column(name = "BANKACCOUNTNO")
	private String bankAccountNo;
	
	//提现至银行账号名
	@Column(name = "BANKACCOUNTNAME")
	private String bankAccountName;

	//提现至支付宝账号
	@Column(name = "ALIPAY_ID")
	private String alipayId;
	
	//提现至微信账号
	@Column(name = "WECHAT_ID")
	private String wechatId;
}