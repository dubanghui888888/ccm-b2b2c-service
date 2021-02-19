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
 * The persistent class for the merchant_withdraw database table.
 * 
 */
@Entity
@Table(name = "merchant_withdraw")
@NamedQuery(name = "MerchantWithdrawEntity.findAll", query = "SELECT u FROM MerchantWithdrawEntity u")
public class MerchantWithdrawEntity extends AbstractBaseEntity
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "MERCHANTWITHDRAW_UUID")
	@GenericGenerator(name = "MERCHANTWITHDRAW_UUID", strategy = "uuid")
	@Column(name = "MERCHANTWITHDRAW_UUID", nullable = false, length = 36)
	private String merchantWithdrawUuid;

	
	public String getMerchantWithdrawUuid() {
		return merchantWithdrawUuid;
	}

	public void setMerchantWithdrawUuid(String merchantWithdrawUuid) {
		this.merchantWithdrawUuid = merchantWithdrawUuid;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_UUID")
	private MerchantEntity merchantEntity;
	
	//提现金额
	@Column(name = "WITHDRAW_AMOUNT")
	private BigDecimal withdrawAmount;
	
	public MerchantEntity getMerchantEntity() {
		return merchantEntity;
	}

	public void setMerchantEntity(MerchantEntity merchantEntity) {
		this.merchantEntity = merchantEntity;
	}

	//实付金额
	@Column(name = "PAYMENT_AMOUNT")
	private BigDecimal paymentAmount;
	
	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}


	//税额
	@Column(name = "TAX_AMOUNT")
	private BigDecimal taxAmount;
	
	//提现状态
	@Column(name = "WITHDRAW_STATUS")
	private String withdrawStatus;
	
	//提现编号
	@Column(name = "WITHDRAW_NO")
	private String withdrawNo;
	
	//提现申请时间
	@Column(name = "WITHDRAW_TIME")
	private Date withdrawTime;
	
	public Date getWithdrawTime() {
		return withdrawTime;
	}

	public void setWithdrawTime(Date withdrawTime) {
		this.withdrawTime = withdrawTime;
	}

	public Date getVerifyTime() {
		return verifyTime;
	}

	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
	}

	public Date getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}


	//提现审核时间
	@Column(name = "VERIFY_TIME")
	private Date verifyTime;

	//打款成功/失败时间
	@Column(name = "COMPLETE_TIME")
	private Date completeTime;
	
	public BigDecimal getWithdrawAmount() {
		return withdrawAmount;
	}

	public void setWithdrawAmount(BigDecimal withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}

	public String getWithdrawStatus() {
		return withdrawStatus;
	}

	public void setWithdrawStatus(String withdrawStatus) {
		this.withdrawStatus = withdrawStatus;
	}

	public String getWithdrawNo() {
		return withdrawNo;
	}

	public void setWithdrawNo(String withdrawNo) {
		this.withdrawNo = withdrawNo;
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
	
	//提现审核未通过原因
	@Column(name = "REJECT_REASON")
	private String rejectReason;
	
	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
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
	
	@Column(name = "MEMO")
	private String memo;


	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}