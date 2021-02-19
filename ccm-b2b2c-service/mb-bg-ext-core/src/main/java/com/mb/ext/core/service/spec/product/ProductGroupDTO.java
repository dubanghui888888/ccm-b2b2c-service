package com.mb.ext.core.service.spec.product;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.GroupDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class ProductGroupDTO extends AbstractBaseDTO{
	
	private static final long serialVersionUID = 7516252502556396978L;

	private List<ProductDTO> productList;
	
	private GroupDTO groupDTO;
	
	public List<ProductDTO> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductDTO> productList) {
		this.productList = productList;
	}

	public GroupDTO getGroupDTO() {
		return groupDTO;
	}

	public void setGroupDTO(GroupDTO groupDTO) {
		this.groupDTO = groupDTO;
	}

	public String getProductGroupUuid() {
		return productGroupUuid;
	}

	public void setProductGroupUuid(String productGroupUuid) {
		this.productGroupUuid = productGroupUuid;
	}

	private String productGroupUuid;
	
}
