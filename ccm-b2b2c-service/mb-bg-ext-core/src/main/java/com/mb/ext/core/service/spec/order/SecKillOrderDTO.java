package com.mb.ext.core.service.spec.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.ext.core.service.spec.product.ProductSkuDTO;
import com.mb.ext.core.service.spec.seckill.SecKillProductDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class SecKillOrderDTO extends AbstractBaseDTO{
	
	/**
	 * 秒杀订单
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	SecKillProductDTO secKillProduct;
	
	OrderDTO orderDTO;
	
	public SecKillProductDTO getSecKillProduct() {
		return secKillProduct;
	}

	public void setSecKillProduct(SecKillProductDTO secKillProduct) {
		this.secKillProduct = secKillProduct;
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

	public String getQuanlificationId() {
		return quanlificationId;
	}

	public ProductDTO getProductDTO() {
		return productDTO;
	}

	public void setProductDTO(ProductDTO productDTO) {
		this.productDTO = productDTO;
	}

	public ProductSkuDTO getProductSkuDTO() {
		return productSkuDTO;
	}

	public void setProductSkuDTO(ProductSkuDTO productSkuDTO) {
		this.productSkuDTO = productSkuDTO;
	}

	public void setQuanlificationId(String quanlificationId) {
		this.quanlificationId = quanlificationId;
	}

	UserDTO userDTO; 
	
	ProductDTO productDTO;
	
	ProductSkuDTO productSkuDTO;
	
	String quanlificationId;
		
}
