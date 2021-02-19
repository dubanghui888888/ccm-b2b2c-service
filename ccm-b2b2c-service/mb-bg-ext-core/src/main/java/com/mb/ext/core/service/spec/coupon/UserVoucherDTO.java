
package com.mb.ext.core.service.spec.coupon;

import java.util.Date;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.order.OrderDTO;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class UserVoucherDTO extends AbstractBaseDTO
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 728847382963145123L;

	private String userVoucherUuid;
	
	private Date receiveTime;
	
	private Date validStartDate;
	
	public String getUserVoucherUuid() {
		return userVoucherUuid;
	}

	public void setUserVoucherUuid(String userVoucherUuid) {
		this.userVoucherUuid = userVoucherUuid;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReceiveTime() {
		return receiveTime;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	public Date getValidStartDate() {
		return validStartDate;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	public void setValidStartDate(Date validStartDate) {
		this.validStartDate = validStartDate;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	public Date getValidEndDate() {
		return validEndDate;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	public void setValidEndDate(Date validEndDate) {
		this.validEndDate = validEndDate;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}
	
	public boolean isExpired() {
		return validEndDate.before(new Date());
	}
	
	public void setExpired(boolean isExpired) {
		
	}
	
	public boolean isStarted() {
		return validStartDate.before(new Date());
	}
	
	public void setStarted(boolean isStarted) {
		
	}
	
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUseTime() {
		return useTime;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
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

	private Date validEndDate;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	public Date getNewValidEndDate() {
		return newValidEndDate;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	public void setNewValidEndDate(Date newValidEndDate) {
		this.newValidEndDate = newValidEndDate;
	}

	private Date newValidEndDate;
	
	private boolean isUsed;
	
	private Date useTime;
	
	private String name;
	
	private String memo;
	
	public OrderDTO getOrderDTO() {
		return orderDTO;
	}

	public void setOrderDTO(OrderDTO orderDTO) {
		this.orderDTO = orderDTO;
	}

	public ProductDTO getProductDTO() {
		return productDTO;
	}

	public void setProductDTO(ProductDTO productDTO) {
		this.productDTO = productDTO;
	}

	private String voucherCode;
	
	private String qrCodeUrl;
	
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

	private String barCodeUrl;
	
	private UserDTO userDTO;
	
	private MerchantDTO merchantDTO;
	
	private OrderDTO orderDTO;
	
	private ProductDTO productDTO;

}
