package com.mb.ext.core.service.spec.point;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class PointProductCollectDTO extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private String productCollectUuid;
	
	private PointProductDTO groupBuyProductDTO;
	
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

	public PointProductDTO getGroupBuyProductDTO() {
		return groupBuyProductDTO;
	}

	public void setGroupBuyProductDTO(PointProductDTO groupBuyProductDTO) {
		this.groupBuyProductDTO = groupBuyProductDTO;
	}

	private UserDTO userDTO;
	
	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	
}
