package com.mb.ext.core.entity.merchant;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.ext.core.entity.MerchantAuthEntity;
import com.mb.ext.core.entity.MerchantTokenEntity;
import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the merchant database table.
 * 
 */
@Entity
@Table(name = "merchant")
@NamedQuery(name = "MerchantEntity.findAll", query = "SELECT u FROM MerchantEntity u")
public class MerchantEntity extends AbstractBaseEntity
{



	private static final long serialVersionUID = 1L;

	public String getMerchantAddress() {
		return merchantAddress;
	}

	public void setMerchantAddress(String merchantAddress) {
		this.merchantAddress = merchantAddress;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getMerchantUuid() {
		return merchantUuid;
	}

	public void setMerchantUuid(String merchantUuid) {
		this.merchantUuid = merchantUuid;
	}

	@Id
	@GeneratedValue(generator = "MERCHANT_UUID")
	@GenericGenerator(name = "MERCHANT_UUID", strategy = "uuid")
	@Column(name = "MERCHANT_UUID", nullable = false, length = 36)
	private String merchantUuid;

	@Column(name = "MOBILENO", length = 20)
	private String mobileNo;
	
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	@Column(name = "MERCHANT_ID",length = 45)
	private String merchantId;

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	@Column(name = "WEIGHT")
	private BigDecimal weight;
	
	@Column(name = "SCORE")
	private BigDecimal score;
	
	@Column(name = "SOLD_UNIT")
	private int soldUnit;
	
	public int getSoldUnit() {
		return soldUnit;
	}

	public void setSoldUnit(int soldUnit) {
		this.soldUnit = soldUnit;
	}

	public int getSaleUnit() {
		return saleUnit;
	}

	public void setSaleUnit(int saleUnit) {
		this.saleUnit = saleUnit;
	}

	@Column(name = "SALE_UNIT")
	private int saleUnit;
	
	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	@Column(name = "MERCHANTNAME",length = 100)
	private String merchantName;

	@Column(name = "MERCHANTDESCRIPTION",length = 200)
	private String merchantDescription;

	public String getMerchantDescription() {
		return merchantDescription;
	}

	public void setMerchantDescription(String merchantDescription) {
		this.merchantDescription = merchantDescription;
	}

	@Column(name = "MERCHANTADDRESS",length = 45)
	private String merchantAddress;
	
	@Column(name = "LOGO")
	private String logo;
	
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Column(name = "CONTACTNAME",length = 20)
	private String contactName;
	
	public boolean isClosed() {
		return isClosed;
	}

	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}



	@Column(name = "ISCLOSED")
	private boolean isClosed;
	
	@Column(name = "REGISTER_DATE")
	private Date registerDate;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "merchantEntity")
	private MerchantAuthEntity merchantAuthEntity;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "merchantEntity")
	private MerchantTokenEntity merchantTokenEntity;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "merchantEntity")
	private MerchantBalanceEntity merchantBalanceEntity;
	
	public Date getRegisterDate() {
		return registerDate;
	}

	public MerchantBalanceEntity getMerchantBalanceEntity() {
		return merchantBalanceEntity;
	}

	public void setMerchantBalanceEntity(MerchantBalanceEntity merchantBalanceEntity) {
		this.merchantBalanceEntity = merchantBalanceEntity;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public MerchantAuthEntity getMerchantAuthEntity() {
		return merchantAuthEntity;
	}

	public void setMerchantAuthEntity(MerchantAuthEntity merchantAuthEntity) {
		this.merchantAuthEntity = merchantAuthEntity;
	}

	public MerchantTokenEntity getMerchantTokenEntity() {
		return merchantTokenEntity;
	}

	public void setMerchantTokenEntity(MerchantTokenEntity merchantTokenEntity) {
		this.merchantTokenEntity = merchantTokenEntity;
	}

	@Column(name = "MEMO")
	private String memo;
	
	public String getReferrer() {
		return referrer;
	}

	public int getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(int exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

	@Column(name = "REFERRER")
	private String referrer;
	
	@Column(name = "EXCHANGE_RATE")
	private int exchangeRate;
	
	@Column(name = "PROVINCE")
	private String province;
	
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Column(name = "CITY")
	private String city;
	
	@Column(name = "DISTRICT")
	private String district;
	
	@Column(name = "LATITUDE")
	private Double latitude;
	
	@Column(name = "LONGITUDE")
	private Double longitude;

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}