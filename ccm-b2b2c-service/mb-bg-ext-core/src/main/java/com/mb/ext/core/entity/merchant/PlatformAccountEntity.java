package com.mb.ext.core.entity.merchant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the platform_account database table.
 * 
 */
@Entity
@Table(name = "platform_account")
@NamedQuery(name = "PlatformAccountEntity.findAll", query = "SELECT u FROM PlatformAccountEntity u")
public class PlatformAccountEntity extends AbstractBaseEntity
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "PLATFORMACCOUNT_UUID")
	@GenericGenerator(name = "PLATFORMACCOUNT_UUID", strategy = "uuid")
	@Column(name = "PLATFORMACCOUNT_UUID", nullable = false, length = 36)
	private String platformAccountUuid;

	
	public String getPlatformAccountUuid() {
		return platformAccountUuid;
	}

	public void setPlatformAccountUuid(String platformAccountUuid) {
		this.platformAccountUuid = platformAccountUuid;
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
	
	//账户类型方
	@Column(name = "ACCOUNT_TYPE")
	private String accountType;
	
	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	//银行编码
	@Column(name = "BANKCODE")
	private String bankCode;
	
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}


	//银行名称
	@Column(name = "BANKNAME")
	private String bankName;
	
	//银行支行名称
	@Column(name = "BRANCHNAME")
	private String branchName;
	
	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	//银行账号
	@Column(name = "BANKACCOUNTNO")
	private String bankAccountNo;
	
	//银行账号名
	@Column(name = "BANKACCOUNTNAME")
	private String bankAccountName;

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


	//支付宝账号
	@Column(name = "ALIPAY_ID")
	private String alipayId;
	
	//支付宝收款码
	@Column(name = "ALIPAY_QRCODE")
	private String alipayQrCode;
	
	//微信账号
	@Column(name = "WECHAT_ID")
	private String wechatId;
	
	//微信收款码
	@Column(name = "WECHAT_QRCODE")
	private String wechatQrCode;
}