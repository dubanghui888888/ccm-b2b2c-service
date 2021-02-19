package com.mb.ext.core.service.spec.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.point.PointProductDTO;
import com.mb.ext.core.service.spec.point.PointProductSkuDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class PointOrderDTO extends AbstractBaseDTO{
	
	/**
	 * 秒杀订单
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	PointProductDTO productDTO;
	
	OrderDTO orderDTO;
	
	boolean isOwner;

	public boolean isOwner() {
		return isOwner;
	}

	public void setOwner(boolean isOwner) {
		this.isOwner = isOwner;
	}

	public OrderDTO getOrderDTO() {
		return orderDTO;
	}

	public void setOrderDTO(OrderDTO orderDTO) {
		this.orderDTO = orderDTO;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public PointProductDTO getProductDTO() {
		return productDTO;
	}

	public void setProductDTO(PointProductDTO productDTO) {
		this.productDTO = productDTO;
	}

	public PointProductSkuDTO getProductSkuDTO() {
		return productSkuDTO;
	}

	public void setProductSkuDTO(PointProductSkuDTO productSkuDTO) {
		this.productSkuDTO = productSkuDTO;
	}

	UserDTO userDTO; 
	
	PointProductSkuDTO productSkuDTO;
	
}
