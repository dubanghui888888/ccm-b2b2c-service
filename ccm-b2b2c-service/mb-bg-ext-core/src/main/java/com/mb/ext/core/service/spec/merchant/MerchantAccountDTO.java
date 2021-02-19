
package com.mb.ext.core.service.spec.merchant;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MerchantAccountDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	private String merchantAccountUuid;
	
	public String getMerchantAccountUuid() {
		return merchantAccountUuid;
	}

	public void setMerchantAccountUuid(String merchantAccountUuid) {
		this.merchantAccountUuid = merchantAccountUuid;
	}

	private MerchantDTO merchantDTO;

	private String accountType;
	
	private String bankName;
	
	private String bankCode;
	
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public MerchantDTO getMerchantDTO() {
		return merchantDTO;
	}

	public void setMerchantDTO(MerchantDTO merchantDTO) {
		this.merchantDTO = merchantDTO;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
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

	private String bankAccountNo;
	
	private String bankAccountName;

	private String alipayId;
	
	private String alipayQrCode;
	
	public String getAlipayQrCode() {
		return alipayQrCode;
	}

	public void setAlipayQrCode(String alipayQrCode) {
		this.alipayQrCode = alipayQrCode;
	}

	public String getWechatQrCode() {
		return wechatQrCode;
	}

	public void setWechatQrCode(String wechatQrCode) {
		this.wechatQrCode = wechatQrCode;
	}

	private String wechatId;
	
	private String wechatQrCode;
	
}
