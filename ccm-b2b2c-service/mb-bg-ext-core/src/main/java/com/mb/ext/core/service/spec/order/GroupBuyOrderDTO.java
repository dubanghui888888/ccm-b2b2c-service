package com.mb.ext.core.service.spec.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.group.GroupBuyProductDTO;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.ext.core.service.spec.product.ProductSkuDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class GroupBuyOrderDTO extends AbstractBaseDTO{
	
	/**
	 * 秒杀订单
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	String groupBuyUuid;
	
	public String getGroupBuyUuid() {
		return groupBuyUuid;
	}

	public void setGroupBuyUuid(String groupBuyUuid) {
		this.groupBuyUuid = groupBuyUuid;
	}

	GroupBuyProductDTO groupBuyProduct;
	
	OrderDTO orderDTO;
	
	boolean isOwner;
	
	public boolean isOwner() {
		return isOwner;
	}

	public void setOwner(boolean isOwner) {
		this.isOwner = isOwner;
	}

	public GroupBuyProductDTO getGroupBuyProduct() {
		return groupBuyProduct;
	}

	public void setGroupBuyProduct(GroupBuyProductDTO groupBuyProduct) {
		this.groupBuyProduct = groupBuyProduct;
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

	UserDTO userDTO; 
	
	ProductDTO productDTO;
	
	ProductSkuDTO productSkuDTO;
	
}
