package com.mb.ext.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the print_setting database table.
 * 
 */
@Entity
@Table(name = "print_setting")
@NamedQuery(name = "PrintSettingEntity.findAll", query = "SELECT u FROM PrintSettingEntity u")
public class PrintSettingEntity extends AbstractBaseEntity
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "PRINT_SETTING_UUID")
	@GenericGenerator(name = "PRINT_SETTING_UUID", strategy = "uuid")
	@Column(name = "PRINT_SETTING_UUID", nullable = false, length = 36)
	private String printSettingUuid;

	@Column(name = "FEIE_USER")
	private String feieUser;

	public String getPrintSettingUuid() {
		return printSettingUuid;
	}

	public void setPrintSettingUuid(String printSettingUuid) {
		this.printSettingUuid = printSettingUuid;
	}

	public String getFeieUser() {
		return feieUser;
	}

	public void setFeieUser(String feieUser) {
		this.feieUser = feieUser;
	}

	public String getFeieSn() {
		return feieSn;
	}

	public void setFeieSn(String feieSn) {
		this.feieSn = feieSn;
	}

	public String getFeieKey() {
		return feieKey;
	}

	public void setFeieKey(String feieKey) {
		this.feieKey = feieKey;
	}

	public String getFeieUkey() {
		return feieUkey;
	}

	public void setFeieUkey(String feieUkey) {
		this.feieUkey = feieUkey;
	}

	@Column(name = "FEIE_SN")
	private String feieSn;	

	@Column(name = "FEIE_KEY")
	private String feieKey;

	@Column(name = "FEIE_UKEY")
	private String feieUkey;
	
	public MerchantEntity getMerchantEntity() {
		return merchantEntity;
	}

	public void setMerchantEntity(MerchantEntity merchantEntity) {
		this.merchantEntity = merchantEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_UUID")
	private MerchantEntity merchantEntity;
	
}