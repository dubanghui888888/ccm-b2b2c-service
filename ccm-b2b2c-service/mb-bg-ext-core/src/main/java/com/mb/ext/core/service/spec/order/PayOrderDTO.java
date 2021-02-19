package com.mb.ext.core.service.spec.order;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.util.JsonDateMinuteDeserializer;
import com.mb.ext.core.util.JsonDateMinuteSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class PayOrderDTO extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private String payOrderUuid;

	private String orderNo;
	
	private String orderStatus;
	
	public BigDecimal getAsinfoPlatform() {
		return asinfoPlatform;
	}

	public void setAsinfoPlatform(BigDecimal asinfoPlatform) {
		this.asinfoPlatform = asinfoPlatform;
	}

	public int getAssignPoint() {
		return assignPoint;
	}

	public void setAssignPoint(int assignPoint) {
		this.assignPoint = assignPoint;
	}

	private BigDecimal productAmount;
	
	private BigDecimal asinfoPlatform;
	
	private BigDecimal asinfoMerchant;

	private BigDecimal actualAmount;
	
	private int assignPoint;
	
	private String code;
	
	private String openId;
	
	public BigDecimal getAsinfoMerchant() {
		return asinfoMerchant;
	}

	public void setAsinfoMerchant(BigDecimal asinfoMerchant) {
		this.asinfoMerchant = asinfoMerchant;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
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
	@JsonSerialize(using=JsonDateMinuteSerializer.class)
	public Date getPaymentTime() {
		return paymentTime;
	}
	@JsonDeserialize(using=JsonDateMinuteDeserializer.class)
	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}
	@JsonSerialize(using=JsonDateMinuteSerializer.class)
	public Date getOrderTime() {
		return orderTime;
	}
	@JsonDeserialize(using=JsonDateMinuteDeserializer.class)
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public int getRewardPoint() {
		return rewardPoint;
	}

	public void setRewardPoint(int rewardPoint) {
		this.rewardPoint = rewardPoint;
	}

	public boolean isRewardPointReceived() {
		return isRewardPointReceived;
	}

	public void setRewardPointReceived(boolean isRewardPointReceived) {
		this.isRewardPointReceived = isRewardPointReceived;
	}

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

	private String outTradeNo;
	
	private Date paymentTime;
	
	private Date orderTime;
	
	private int rewardPoint;
	
	private boolean isRewardPointReceived;
	
	private String wechatPayUuid;
	
	private String alipayUuid;
	
	private String paymentMethod;
	
	private UserDTO userDTO;
	
	private MerchantDTO merchantDTO;
	
}
