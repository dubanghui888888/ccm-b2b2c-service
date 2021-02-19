package com.mb.ext.core.entity.coupon;

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

import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.order.OrderEntity;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the user_voucher database table.
 * 
 */
@Entity
@Table(name = "user_voucher")
@NamedQuery(name = "UserVoucherEntity.findAll", query = "SELECT u FROM UserVoucherEntity u")
public class UserVoucherEntity extends AbstractBaseEntity
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "USER_VOUCHER_UUID")
	@GenericGenerator(name = "USER_VOUCHER_UUID", strategy = "uuid")
	@Column(name = "USER_VOUCHER_UUID", nullable = false, length = 36)
	private String userVoucherUuid;

	public String getUserVoucherUuid() {
		return userVoucherUuid;
	}

	public void setUserVoucherUuid(String userVoucherUuid) {
		this.userVoucherUuid = userVoucherUuid;
	}

	public Date getValidStartDate() {
		return validStartDate;
	}

	public void setValidStartDate(Date validStartDate) {
		this.validStartDate = validStartDate;
	}

	public Date getValidEndDate() {
		return validEndDate;
	}

	public void setValidEndDate(Date validEndDate) {
		this.validEndDate = validEndDate;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	public Date getUseTime() {
		return useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getVoucherCode() {
		return voucherCode;
	}

	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public MerchantEntity getMerchantEntity() {
		return merchantEntity;
	}

	public void setMerchantEntity(MerchantEntity merchantEntity) {
		this.merchantEntity = merchantEntity;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	@Column(name = "RECEIVE_TIME")
	private Date receiveTime;
	
	@Column(name = "VALID_START_DATE")
	private Date validStartDate;
	
	@Column(name = "VALID_END_DATE")
	private Date validEndDate;
	
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	@Column(name = "IS_USED")
	private boolean isUsed;
	
	@Column(name = "USE_TIME")
	private Date useTime;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "MEMO")
	private String memo;
	
	@Column(name = "VOUCHER_CODE")
	private String voucherCode;
	
	public String getQrCodeUrl() {
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

	public String getBarCodeUrl() {
		return barCodeUrl;
	}

	public void setBarCodeUrl(String barCodeUrl) {
		this.barCodeUrl = barCodeUrl;
	}

	@Column(name = "QR_CODE_URL")
	private String qrCodeUrl;
	
	@Column(name = "BAR_CODE_URL")
	private String barCodeUrl;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_UUID")
	private UserEntity userEntity;
	
	public OrderEntity getOrderEntity() {
		return orderEntity;
	}

	public void setOrderEntity(OrderEntity orderEntity) {
		this.orderEntity = orderEntity;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_UUID")
	private MerchantEntity merchantEntity;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_UUID")
	private OrderEntity orderEntity;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_UUID")
	private ProductEntity productEntity;

	public ProductEntity getProductEntity() {
		return productEntity;
	}

	public void setProductEntity(ProductEntity productEntity) {
		this.productEntity = productEntity;
	}
}