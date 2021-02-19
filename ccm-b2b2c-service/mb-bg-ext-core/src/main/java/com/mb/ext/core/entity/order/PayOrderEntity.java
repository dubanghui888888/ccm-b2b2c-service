package com.mb.ext.core.entity.order;

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

import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the pay_order database table.
 * 
 */
@Entity
@Table(name = "pay_order")
@NamedQuery(name = "PayOrderEntity.findAll", query = "SELECT u FROM PayOrderEntity u")
public class PayOrderEntity extends AbstractBaseEntity
{
	

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public BigDecimal getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(BigDecimal productAmount) {
		this.productAmount = productAmount;
	}

	public BigDecimal getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
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

	public String getPayOrderUuid() {
		return payOrderUuid;
	}

	public void setPayOrderUuid(String payOrderUuid) {
		this.payOrderUuid = payOrderUuid;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "PAYORDER_UUID")
	@GenericGenerator(name = "PAYORDER_UUID", strategy = "uuid")
	@Column(name = "PAYORDER_UUID", nullable = false, length = 36)
	private String payOrderUuid;

	@Column(name = "ORDER_NO", nullable = false, length = 20)
	private String orderNo;
	
	@Column(name = "ORDER_STATUS")
	private String orderStatus;
	
	@Column(name = "PRODUCT_AMOUNT")
	private BigDecimal productAmount;
	
	public BigDecimal getAsinfoPlatform() {
		return asinfoPlatform;
	}

	public BigDecimal getAsinfoMerchant() {
		return asinfoMerchant;
	}

	public void setAsinfoMerchant(BigDecimal asinfoMerchant) {
		this.asinfoMerchant = asinfoMerchant;
	}

	public void setAsinfoPlatform(BigDecimal asinfoPlatform) {
		this.asinfoPlatform = asinfoPlatform;
	}

	@Column(name = "ASINFO_PLATFORM")
	private BigDecimal asinfoPlatform;
	
	@Column(name = "ASINFO_MERCHANT")
	private BigDecimal asinfoMerchant;

	@Column(name = "ACTUAL_AMOUNT")
	private BigDecimal actualAmount;
	
	@Column(name = "OUT_TRADE_NO")
	private String outTradeNo;
	
	@Column(name = "PAYMENT_TIME")
	private Date paymentTime;
	
	@Column(name = "ORDER_TIME")
	private Date orderTime;
	
	public String getWechatPayUuid() {
		return wechatPayUuid;
	}

	public void setWechatPayUuid(String wechatPayUuid) {
		this.wechatPayUuid = wechatPayUuid;
	}

	public String getAlipayUuid() {
		return alipayUuid;
	}

	public void setAlipayUuid(String alipayUuid) {
		this.alipayUuid = alipayUuid;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Column(name = "REWARD_POINT")
	private int rewardPoint;
	
	@Column(name = "IS_REWARD_POINT_RECEIVED")
	private boolean isRewardPointReceived;
	
	@Column(name = "WECHATPAY_UUID")
	private String wechatPayUuid;
	
	@Column(name = "ALIPAY_UUID")
	private String alipayUuid;
	
	@Column(name = "PAYMENT_METHOD")
	private String paymentMethod;
	

	public boolean isRewardPointReceived() {
		return isRewardPointReceived;
	}

	public void setRewardPointReceived(boolean isRewardPointReceived) {
		this.isRewardPointReceived = isRewardPointReceived;
	}

	public int getRewardPoint() {
		return rewardPoint;
	}

	public void setRewardPoint(int rewardPoint) {
		this.rewardPoint = rewardPoint;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_UUID")
	private UserEntity userEntity;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_UUID")
	private MerchantEntity merchantEntity;
	
}