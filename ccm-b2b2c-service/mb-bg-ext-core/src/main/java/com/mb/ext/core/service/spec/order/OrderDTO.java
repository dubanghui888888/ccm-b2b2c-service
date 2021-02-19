package com.mb.ext.core.service.spec.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.constant.OrderConstants;
import com.mb.ext.core.service.spec.UserAwardDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.coupon.UserCouponDTO;
import com.mb.ext.core.service.spec.group.GroupBuyDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.util.JsonDateMinuteDeserializer;
import com.mb.ext.core.util.JsonDateSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class OrderDTO extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	List<String> shoppingCartUuidList = new ArrayList<String>();
	
	List<UserAwardDTO> awardList = new ArrayList<UserAwardDTO>();
	
	List<String> suscribeMsgList = new ArrayList<String>();
	
	UserCouponDTO userCouponDTO;
	
	GroupBuyDTO groupBuyDTO;
	
	public GroupBuyDTO getGroupBuyDTO() {
		return groupBuyDTO;
	}

	public void setGroupBuyDTO(GroupBuyDTO groupBuyDTO) {
		this.groupBuyDTO = groupBuyDTO;
	}

	private boolean isAccounted;
	
	public List<UserAwardDTO> getAwardList() {
		return awardList;
	}

	public List<String> getSuscribeMsgList() {
		return suscribeMsgList;
	}

	public boolean isAccounted() {
		return isAccounted;
	}

	public void setAccounted(boolean isAccounted) {
		this.isAccounted = isAccounted;
	}

	public void setSuscribeMsgList(List<String> suscribeMsgList) {
		this.suscribeMsgList = suscribeMsgList;
	}

	public void setAwardList(List<UserAwardDTO> awardList) {
		this.awardList = awardList;
	}

	public BigDecimal getFreightAmount() {
		return freightAmount;
	}

	public void setFreightAmount(BigDecimal freightAmount) {
		this.freightAmount = freightAmount;
	}

	private BigDecimal freightAmount;
	
	private BigDecimal deliveryAmount;
	
	public UserCouponDTO getUserCouponDTO() {
		return userCouponDTO;
	}

	public void setUserCouponDTO(UserCouponDTO userCouponDTO) {
		this.userCouponDTO = userCouponDTO;
	}

	public BigDecimal getDeliveryAmount() {
		return deliveryAmount;
	}

	public void setDeliveryAmount(BigDecimal deliveryAmount) {
		this.deliveryAmount = deliveryAmount;
	}

	private String orderUuid;

	public List<String> getShoppingCartUuidList() {
		return shoppingCartUuidList;
	}

	public void setShoppingCartUuidList(List<String> shoppingCartUuidList) {
		this.shoppingCartUuidList = shoppingCartUuidList;
	}

	private String orderNo;
	
	private String pOrderNo;
	
	private String orderType;
	
	private String orderComment;
	
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	private String paymentMethod;
	
	private String transactionId;
	
	private Date cancelTime;
	
	private String cancelReason;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public Date getCancelTime() {
		return cancelTime;
	}
	@JsonDeserialize(using=JsonDateMinuteDeserializer.class)
	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	private String orderChannel;
	
	private String formId;
	
	public String getOrderType() {
		return orderType;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	private String code;
	
	private String openId;
	
	private String wechatPayUuid;

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
	
	public String getPaymentMethodDesc() {
		String paymentMethodDesc = "";
		if(OrderConstants.ORDER_PAYMENT_METHOD_WECHAT.equals(paymentMethod)) {
			paymentMethodDesc = "微信支付";
		}else if(OrderConstants.ORDER_PAYMENT_METHOD_ALIPAY.equals(paymentMethod)) {
			paymentMethodDesc = "支付宝支付";
		}else if(OrderConstants.ORDER_PAYMENT_METHOD_BALANCE.equals(paymentMethod)) {
			paymentMethodDesc = "余额支付";
		}
		return paymentMethodDesc;
	}
	
	public void setPaymentMethodDesc(String paymentMethodDesc) {
		
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getOrderComment() {
		return orderComment;
	}

	public void setOrderComment(String orderComment) {
		this.orderComment = orderComment;
	}

	public String getOrderChannel() {
		return orderChannel;
	}

	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}

	private String alipayUuid;
	
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private String orderStatus;
	
	private int productUnit;

	private BigDecimal productAmount;
	
	private BigDecimal deductAmount;
	
	public BigDecimal getDeductAmount() {
		return deductAmount;
	}

	public void setDeductAmount(BigDecimal deductAmount) {
		this.deductAmount = deductAmount;
	}

	private int productPoint;
	
	private BigDecimal productCostUnitAmount;
	
	private BigDecimal productCostAmount;
	
	public BigDecimal getProductCostUnitAmount() {
		return productCostUnitAmount;
	}

	public void setProductCostUnitAmount(BigDecimal productCostUnitAmount) {
		this.productCostUnitAmount = productCostUnitAmount;
	}

	public BigDecimal getProductCostAmount() {
		return productCostAmount;
	}

	public void setProductCostAmount(BigDecimal productCostAmount) {
		this.productCostAmount = productCostAmount;
	}

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

	private int actualPoint;
	
	private BigDecimal actualAmount;
	
	public String getpOrderNo() {
		return pOrderNo;
	}

	public void setpOrderNo(String pOrderNo) {
		this.pOrderNo = pOrderNo;
	}

	private String paymentChannel;
	
	private String outTradeNo;
	
	private Date paymentTime;
	
	private Date orderTime;
	
	private Date deliveryTime;
	
	private Date confirmTime;
	
	public String getDeliveryType() {
		return deliveryType;
	}
	
	public String getDeliveryTypeDesc() {
		if(OrderConstants.ORDER_DELIVERY_TYPE_EXPRESS.equals(deliveryType)) {
			return "快递配送";
		}else if(OrderConstants.ORDER_DELIVERY_TYPE_CITY.equals(deliveryType)) {
			return "同城配送";
		}else if(OrderConstants.ORDER_DELIVERY_TYPE_PICK.equals(deliveryType)) {
			return "门店自提";
		}else return "";
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}
	
	public void setDeliveryTypeDesc(String deliveryTypeDesc) {
		
	}

	private String deliveryType;
	
	private String memo;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public Date getConfirmTime() {
		return confirmTime;
	}
	@JsonDeserialize(using=JsonDateMinuteDeserializer.class)
	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	private UserDTO userDTO;
	
	private MerchantDTO merchantDTO;
	
	private List<OrderProductDTO> orderProductDTOList;
	
	private OrderLogisticsDTO orderLogisticsDTO;
	
	private UserDeliveryAddressDTO deliveryAddressDTO;
	
	public UserDeliveryAddressDTO getDeliveryAddressDTO() {
		return deliveryAddressDTO;
	}

	public void setDeliveryAddressDTO(UserDeliveryAddressDTO deliveryAddressDTO) {
		this.deliveryAddressDTO = deliveryAddressDTO;
	}

	public OrderLogisticsDTO getOrderLogisticsDTO() {
		return orderLogisticsDTO;
	}

	public void setOrderLogisticsDTO(OrderLogisticsDTO orderLogisticsDTO) {
		this.orderLogisticsDTO = orderLogisticsDTO;
	}

	public String getOrderUuid() {
		return orderUuid;
	}

	public void setOrderUuid(String orderUuid) {
		this.orderUuid = orderUuid;
	}

	public List<OrderProductDTO> getOrderProductDTOList() {
		return orderProductDTOList;
	}

	public void setOrderProductDTOList(List<OrderProductDTO> orderProductDTOList) {
		this.orderProductDTOList = orderProductDTOList;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

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

	public String getPaymentChannel() {
		return paymentChannel;
	}

	public void setPaymentChannel(String paymentChannel) {
		this.paymentChannel = paymentChannel;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public Date getPaymentTime() {
		return paymentTime;
	}
	@JsonDeserialize(using=JsonDateMinuteDeserializer.class)
	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public Date getOrderTime() {
		return orderTime;
	}
	@JsonDeserialize(using=JsonDateMinuteDeserializer.class)
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public Date getDeliveryTime() {
		return deliveryTime;
	}
	@JsonDeserialize(using=JsonDateMinuteDeserializer.class)
	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public MerchantDTO getMerchantDTO() {
		return merchantDTO;
	}

	public void setMerchantDTO(MerchantDTO merchantDTO) {
		this.merchantDTO = merchantDTO;
	}
	
	public String getMerchantAddress() {
		String merchantAddress = "";
		if(userDTO != null && userDTO.getMerchantDTO()!=null) {
			merchantAddress = userDTO.getMerchantDTO().getMerchantAddress();
		}
		return merchantAddress;
	}
	public void setMerchantAddress(String merchantAddress) {
		
	}
	
	private OrderReturnDTO orderReturnDTO;
	
	private List<OrderImageDTO> images = new ArrayList<OrderImageDTO>();;

	public List<OrderImageDTO> getImages() {
		return images;
	}

	public void setImages(List<OrderImageDTO> images) {
		this.images = images;
	}

	public OrderReturnDTO getOrderReturnDTO() {
		return orderReturnDTO;
	}


	public void setOrderReturnDTO(OrderReturnDTO orderReturnDTO) {
		this.orderReturnDTO = orderReturnDTO;
	}
	
	public String getUserName(){
		String userName = "";
		if(userDTO != null)
			userName = userDTO.getName();
		return userName;
	}
	
	public void setUserName(String userName){
		
	}
	
	public String getMerchantName(){
		String merchantName = "";
		if(merchantDTO != null)
			merchantName = merchantDTO.getMerchantName();
		return merchantName;
	}
	
	public void setMerchantName(String merchnatName){
		
	}
	
	public String getMerchantMobileNo(){
		String merchantMobileNo = "";
		if(merchantDTO != null)
			merchantMobileNo = merchantDTO.getMobileNo();
		return merchantMobileNo;
	}
	
	public void setMerchantMobileNo(String merchnatMobileNo){
		
	}
	
	public String getUserPersonalPhone(){
		String userPersonalPhone = "";
		if(userDTO != null)
			userPersonalPhone = userDTO.getPersonalPhone();
		return userPersonalPhone;
	}
	
	public void setUserPersonalPhone(String userPersonalPhone){
		
	}
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getRegisterDate(){
		Date registerDate = null;
		if(userDTO != null)
			registerDate = userDTO.getRegisterDate();
		return registerDate;
	}
	@JsonSerialize(using=JsonDateSerializer.class) 
	public void setRegisterDate(Date registerDate){
		
	}
	
	private String deliveryProvince;
	
	private String deliveryCity;
	
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

	public String getDeliveryZipcode() {
		return deliveryZipcode;
	}

	public void setDeliveryZipcode(String deliveryZipcode) {
		this.deliveryZipcode = deliveryZipcode;
	}

	private String deliveryStreet;
	
	private String deliveryName;
	
	private String deliveryContactNo;
	
	private String deliveryZipcode;
	
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

	private BigDecimal deliveryLongitude;
	
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

	public String getAfterSaleNo() {
		return afterSaleNo;
	}

	public void setAfterSaleNo(String afterSaleNo) {
		this.afterSaleNo = afterSaleNo;
	}

	public boolean isAfterSale() {
		return isAfterSale;
	}

	public void setAfterSale(boolean isAfterSale) {
		this.isAfterSale = isAfterSale;
	}

	private String courierName;
	
	private String courierNo;
	
	private boolean isAfterSale;
	
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

	private String afterSaleNo;
	
	private Date afterSaleDeadLineTime;
	
	private String refundId;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public Date getAfterSaleDeadLineTime() {
		return afterSaleDeadLineTime;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public void setAfterSaleDeadLineTime(Date afterSaleDeadLineTime) {
		this.afterSaleDeadLineTime = afterSaleDeadLineTime;
	}

	private String refundMsg;
	
	private String shopperName;
	
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

	private String shopperPhoto;
	
	private String shopperSex;
	
}
