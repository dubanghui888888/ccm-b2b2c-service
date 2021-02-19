
package com.mb.ext.core.service.spec.merchant;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.constant.FundConstants;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MerchantWithdrawDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	private String merchantWithdrawUuid;
	
	private MerchantDTO merchantDTO;

	public String getMerchantName() {
		String merchantName = "";
		if(merchantDTO != null) {
			merchantName = merchantDTO.getMerchantName();
		}
		return merchantName;
	}
	
	public String getMerchantWithdrawUuid() {
		return merchantWithdrawUuid;
	}

	public void setMerchantWithdrawUuid(String merchantWithdrawUuid) {
		this.merchantWithdrawUuid = merchantWithdrawUuid;
	}

	public MerchantDTO getMerchantDTO() {
		return merchantDTO;
	}

	public void setMerchantDTO(MerchantDTO merchantDTO) {
		this.merchantDTO = merchantDTO;
	}

	public void setUserName(String userName) {
		
	}

	public BigDecimal getWithdrawAmount() {
		return withdrawAmount;
	}

	public void setWithdrawAmount(BigDecimal withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}

	public String getWithdrawStatus() {
		return withdrawStatus;
	}
	
	public String getAuditStatus() {
		String auditStatus = "";
		if(FundConstants.WITHDRAW_STATUS_APPLICATED.equals(withdrawStatus)) {
			auditStatus = "待审核";
		}
		else if(FundConstants.WITHDRAW_STATUS_REJECTED.equals(withdrawStatus)) {
			auditStatus = "未通过";
		}else {
			auditStatus = "已通过";
		}
		return auditStatus;
	}
	
	public String getPaymentStatus() {
		String paymentStatus = "";
		if(FundConstants.WITHDRAW_STATUS_SUCCESS.equals(withdrawStatus)) {
			paymentStatus = "提现成功";
		}else if(FundConstants.WITHDRAW_STATUS_FAIL.equals(withdrawStatus)) {
			paymentStatus = "打款失败";
		}else if(FundConstants.WITHDRAW_STATUS_REJECTED.equals(withdrawStatus)) {
			paymentStatus = "审核未通过";
		}else {
			paymentStatus = "处理中";
		}
		return paymentStatus;
	}
	
	public void setAuditStatus(String auditStatus) {
		
	}

	public void setWithdrawStatus(String withdrawStatus) {
		this.withdrawStatus = withdrawStatus;
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
	
	public String getPaymentMethodStr() {
		String paymentMethodStr = "";
		if(FundConstants.WITHDRAW_PAYMENT_METHOD_BANK.equals(paymentMethod))
			paymentMethodStr = "银行账户";
		else if(FundConstants.WITHDRAW_PAYMENT_METHOD_ALIPAY.equals(paymentMethod))
			paymentMethodStr = "支付宝";
		return paymentMethodStr;
	}

	public void getPaymentMethodStr(String paymentMethodStr) {
		
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
	
	public String getAccountNo() {
		String accountNo = "";
		if(FundConstants.WITHDRAW_PAYMENT_METHOD_BANK.equals(paymentMethod))
			accountNo = bankAccountNo;
		else if(FundConstants.WITHDRAW_PAYMENT_METHOD_ALIPAY.equals(paymentMethod))
			accountNo = alipayId;
		return accountNo;
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

	private BigDecimal withdrawAmount;
	
	private BigDecimal paymentAmount;
	
	private BigDecimal taxAmount;
	
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

	private String withdrawStatus;
	
	private String withdrawNo;
	
	private String failReason;
	
	private String rejectReason;
	
	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getWithdrawNo() {
		return withdrawNo;
	}

	public void setWithdrawNo(String withdrawNo) {
		this.withdrawNo = withdrawNo;
	}
	
	private Date withdrawTime;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public Date getWithdrawTime() {
		return withdrawTime;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public void setWithdrawTime(Date withdrawTime) {
		this.withdrawTime = withdrawTime;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public Date getVerifyTime() {
		return verifyTime;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public Date getCompleteTime() {
		return completeTime;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}

	private Date verifyTime;

	private Date completeTime;

	private String paymentMethod;
	
	private String bankName;
	
	private String bankAccountNo;
	
	private String bankAccountName;

	private String alipayId;
	
	private String wechatId;
	
	private String memo;

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}
