package com.mb.ext.core.entity.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.ext.core.entity.UserDeliveryAddressEntity;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.coupon.UserCouponEntity;
import com.mb.ext.core.entity.group.GroupBuyEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the order database table.
 * 
 */
@Entity
@Table(name = "`order`")
@NamedQuery(name = "OrderEntity.findAll", query = "SELECT u FROM OrderEntity u")
public class OrderEntity extends AbstractBaseEntity
{

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public int getProductUnit() {
		return productUnit;
	}

	public void setProductUnit(int productUnit) {
		this.productUnit = productUnit;
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

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public String getOrderUuid() {
		return orderUuid;
	}

	public void setOrderUuid(String orderUuid) {
		this.orderUuid = orderUuid;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "ORDER_UUID")
	@GenericGenerator(name = "ORDER_UUID", strategy = "uuid")
	@Column(name = "ORDER_UUID", nullable = false, length = 36)
	private String orderUuid;

	@Column(name = "ORDER_NO", nullable = false)
	private String orderNo;
	
	@Column(name = "P_ORDER_NO")
	private String pOrderNo;
	
	@Column(name = "ORDER_STATUS")
	private String orderStatus;
	
	@Column(name = "ORDER_TYPE")
	private String orderType;
	
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	@Column(name = "TRANSACTION_ID")
	private String transactionId;
	
	@Column(name = "IS_ACCOUNTED")
	private boolean isAccounted;

	public boolean isAccounted() {
		return isAccounted;
	}

	public void setAccounted(boolean isAccounted) {
		this.isAccounted = isAccounted;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	@Column(name = "PRODUCT_UNIT")
	private int productUnit;

	@Column(name = "PRODUCT_AMOUNT")
	private BigDecimal productAmount;
	
	public BigDecimal getDeductAmount() {
		return deductAmount;
	}

	public void setDeductAmount(BigDecimal deductAmount) {
		this.deductAmount = deductAmount;
	}

	@Column(name = "DEDUCT_AMOUNT")
	private BigDecimal deductAmount;
	
	@Column(name = "PRODUCT_POINT")
	private int productPoint;

	public int getProductPoint() {
		return productPoint;
	}

	public void setProductPoint(int productPoint) {
		this.productPoint = productPoint;
	}

	public int getActualPoint() {
		return actualPoint;
	}

	public void setActualPoint(int actualPoint) {
		this.actualPoint = actualPoint;
	}

	@Column(name = "ACTUAL_AMOUNT")
	private BigDecimal actualAmount;
	
	public String getpOrderNo() {
		return pOrderNo;
	}

	public void setpOrderNo(String pOrderNo) {
		this.pOrderNo = pOrderNo;
	}

	@Column(name = "ACTUAL_POINT")
	private int actualPoint;
	
	@Column(name = "ORDER_TIME")
	private Date orderTime;
	
	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	@Column(name = "CANCEL_TIME")
	private Date cancelTime;
	
	@Column(name = "CANCEL_REASON")
	private String cancelReason;
	
	@Column(name = "PAYMENT_TIME")
	private Date paymentTime;
	
	public String getOrderComment() {
		return orderComment;
	}

	public void setOrderComment(String orderComment) {
		this.orderComment = orderComment;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getOrderChannel() {
		return orderChannel;
	}

	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}
	
	@Column(name = "DELIVERY_TYPE")
	private String deliveryType;

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	@Column(name = "DELIVERY_TIME")
	private Date deliveryTime;
	
	@Column(name = "CONFIRM_TIME")
	private Date confirmTime;
	
	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	@Column(name = "MEMO")
	private String memo;

	@Column(name = "ORDER_COMMENT")
	private String orderComment;
	
	@Column(name = "PAYMENT_METHOD")
	private String paymentMethod;
	
	@Column(name = "ORDER_CHANNEL")
	private String orderChannel;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_UUID")
	private UserEntity userEntity;
	
	public MerchantEntity getMerchantEntity() {
		return merchantEntity;
	}

	public void setMerchantEntity(MerchantEntity merchantEntity) {
		this.merchantEntity = merchantEntity;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MERCHANT_UUID")
	private MerchantEntity merchantEntity;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_COUPON_UUID")
	private UserCouponEntity userCouponEntity;
	
	public GroupBuyEntity getGroupBuyEntity() {
		return groupBuyEntity;
	}

	public void setGroupBuyEntity(GroupBuyEntity groupBuyEntity) {
		this.groupBuyEntity = groupBuyEntity;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROUP_BUY_UUID")
	private GroupBuyEntity groupBuyEntity;
	
	public UserCouponEntity getUserCouponEntity() {
		return userCouponEntity;
	}

	public void setUserCouponEntity(UserCouponEntity userCouponEntity) {
		this.userCouponEntity = userCouponEntity;
	}

	@Column(name = "PRODUCT_COST_AMOUNT")
	private BigDecimal productCostAmount;
	
	@Column(name = "FREIGHT_AMOUNT")
	private BigDecimal freightAmount;
	
	public boolean isAfterSale() {
		return isAfterSale;
	}

	public void setAfterSale(boolean isAfterSale) {
		this.isAfterSale = isAfterSale;
	}

	public String getAfterSaleNo() {
		return afterSaleNo;
	}

	public void setAfterSaleNo(String afterSaleNo) {
		this.afterSaleNo = afterSaleNo;
	}

	@Column(name = "IS_AFTER_SALE")
	private boolean isAfterSale;
	
	@Column(name = "AFTER_SALE_DEADLINE_TIME")
	private Date afterSaleDeadLineTime;
	
	@Column(name = "AFTER_SALE_NO")
	private String afterSaleNo;
	
	public Date getAfterSaleDeadLineTime() {
		return afterSaleDeadLineTime;
	}

	public void setAfterSaleDeadLineTime(Date afterSaleDeadLineTime) {
		this.afterSaleDeadLineTime = afterSaleDeadLineTime;
	}

	public BigDecimal getProductCostAmount() {
		return productCostAmount;
	}

	public void setProductCostAmount(BigDecimal productCostAmount) {
		this.productCostAmount = productCostAmount;
	}

	public UserDeliveryAddressEntity getUserDeliveryAddressEntity() {
		return userDeliveryAddressEntity;
	}

	public void setUserDeliveryAddressEntity(UserDeliveryAddressEntity userDeliveryAddressEntity) {
		this.userDeliveryAddressEntity = userDeliveryAddressEntity;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DELIVERY_ADDRESS_UUID")
	private UserDeliveryAddressEntity userDeliveryAddressEntity;
	
	public BigDecimal getFreightAmount() {
		return freightAmount;
	}

	public void setFreightAmount(BigDecimal freightAmount) {
		this.freightAmount = freightAmount;
	}

	@Column(name = "DELIVERY_PROVINCE")
	private String deliveryProvince;
	
	@Column(name = "DELIVERY_CITY")
	private String deliveryCity;
	
	@Column(name = "DELIVERY_AREA")
	private String deliveryArea;

	public String getDeliveryProvince() {
		return deliveryProvince;
	}

	public void setDeliveryProvince(String deliveryProvince) {
		this.deliveryProvince = deliveryProvince;
	}

	public String getDeliveryCity() {
		return deliveryCity;
	}

	public void setDeliveryCity(String deliveryCity) {
		this.deliveryCity = deliveryCity;
	}

	public String getDeliveryArea() {
		return deliveryArea;
	}

	public void setDeliveryArea(String deliveryArea) {
		this.deliveryArea = deliveryArea;
	}

	public String getDeliveryStreet() {
		return deliveryStreet;
	}

	public void setDeliveryStreet(String deliveryStreet) {
		this.deliveryStreet = deliveryStreet;
	}

	public String getDeliveryName() {
		return deliveryName;
	}

	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}

	public String getDeliveryContactNo() {
		return deliveryContactNo;
	}

	public void setDeliveryContactNo(String deliveryContactNo) {
		this.deliveryContactNo = deliveryContactNo;
	}

	@Column(name = "DELIVERY_Street")
	private String deliveryStreet;
	
	@Column(name = "DELIVERY_NAME")
	private String deliveryName;
	
	@Column(name = "DELIVERY_LATITUDE")
	private BigDecimal deliveryLatitude;
	
	public BigDecimal getDeliveryLatitude() {
		return deliveryLatitude;
	}

	public void setDeliveryLatitude(BigDecimal deliveryLatitude) {
		this.deliveryLatitude = deliveryLatitude;
	}

	public BigDecimal getDeliveryLongitude() {
		return deliveryLongitude;
	}

	public void setDeliveryLongitude(BigDecimal deliveryLongitude) {
		this.deliveryLongitude = deliveryLongitude;
	}

	@Column(name = "DELIVERY_LONGITUDE")
	private BigDecimal deliveryLongitude;
	
	public String getDeliveryZipcode() {
		return deliveryZipcode;
	}

	public void setDeliveryZipcode(String deliveryZipcode) {
		this.deliveryZipcode = deliveryZipcode;
	}

	@Column(name = "DELIVERY_CONTACT_NO")
	private String deliveryContactNo;
	
	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getCourierName() {
		return courierName;
	}

	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}

	public String getCourierNo() {
		return courierNo;
	}

	public void setCourierNo(String courierNo) {
		this.courierNo = courierNo;
	}

	@Column(name = "DELIVERY_ZIPCODE")
	private String deliveryZipcode;
	
	@Column(name = "COURIER_NAME")
	private String courierName;
	
	@Column(name = "COURIER_NO")
	private String courierNo;
	
	public List<OrderProductEntity> getOrderProductList() {
		return orderProductList;
	}

	public void setOrderProductList(List<OrderProductEntity> orderProductList) {
		this.orderProductList = orderProductList;
	}

	@Column(name = "FORM_ID")
	private String formId;
	
	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public String getRefundMsg() {
		return refundMsg;
	}

	public void setRefundMsg(String refundMsg) {
		this.refundMsg = refundMsg;
	}

	@Column(name = "REFUND_ID")
	private String refundId;
	
	@Column(name = "REFUND_MSG")
	private String refundMsg;
	
	@Column(name = "SHOPPER_NAME")
	private String shopperName;
	
	@Column(name = "SHOPPER_MOBILENO")
	private String shopperMobileNo;
	
	public String getShopperName() {
		return shopperName;
	}

	public void setShopperName(String shopperName) {
		this.shopperName = shopperName;
	}

	public String getShopperMobileNo() {
		return shopperMobileNo;
	}

	public void setShopperMobileNo(String shopperMobileNo) {
		this.shopperMobileNo = shopperMobileNo;
	}

	public String getShopperPhoto() {
		return shopperPhoto;
	}

	public void setShopperPhoto(String shopperPhoto) {
		this.shopperPhoto = shopperPhoto;
	}

	public String getShopperSex() {
		return shopperSex;
	}

	public void setShopperSex(String shopperSex) {
		this.shopperSex = shopperSex;
	}

	@Column(name = "SHOPPER_PHOTO")
	private String shopperPhoto;
	
	@Column(name = "SHOPPER_SEX")
	private String shopperSex;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "orderEntity")
	private List<OrderProductEntity> orderProductList;
	
}