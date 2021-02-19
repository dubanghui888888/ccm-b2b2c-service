package com.mb.ext.core.service.spec.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class ProductCollectDTO extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private String productCollectUuid;
	
	private ProductDTO productDTO;
	
	public boolean isProductCollected() {
		return isProductCollected;
	}

	public void setProductCollected(boolean isProductCollected) {
		this.isProductCollected = isProductCollected;
	}

	boolean isProductCollected;
	
	public String getProductCollectUuid() {
		return productCollectUuid;
	}

	public void setProductCollectUuid(String productCollectUuid) {
		this.productCollectUuid = productCollectUuid;
	}

	public ProductDTO getProductDTO() {
		return productDTO;
	}

	public void setProductDTO(ProductDTO productDTO) {
		this.productDTO = productDTO;
	}

	private UserDTO userDTO;
	
	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	
}
