package com.mb.ext.core.service.spec.product;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class ProductSkuAttrDTO extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private String productSkuAttrUuid;

	private String skuAttrName;
	
	List<ProductSkuAttrValueDTO> productAttrValueList = new ArrayList<ProductSkuAttrValueDTO>();
	

	public List<ProductSkuAttrValueDTO> getProductAttrValueList() {
		return productAttrValueList;
	}

	public void setProductAttrValueList(
			List<ProductSkuAttrValueDTO> productAttrValueList) {
		this.productAttrValueList = productAttrValueList;
	}

	public String getSkuAttrName() {
		return skuAttrName;
	}

	public void setSkuAttrName(String skuAttrName) {
		this.skuAttrName = skuAttrName;
	}

	public boolean isCateAttr() {
		return isCateAttr;
	}

	public void setCateAttr(boolean isCateAttr) {
		this.isCateAttr = isCateAttr;
	}

	public ProductDTO getProductDTO() {
		return productDTO;
	}

	public void setProductDTO(ProductDTO productDTO) {
		this.productDTO = productDTO;
	}

	public ProductCateAttrDTO getProductCateAttrDTO() {
		return productCateAttrDTO;
	}

	public void setProductCateAttrDTO(ProductCateAttrDTO productCateAttrDTO) {
		this.productCateAttrDTO = productCateAttrDTO;
	}

	public String getProductSkuAttrUuid() {
		return productSkuAttrUuid;
	}

	public void setProductSkuAttrUuid(String productSkuAttrUuid) {
		this.productSkuAttrUuid = productSkuAttrUuid;
	}

	private boolean isCateAttr;

	private ProductDTO productDTO;
	
	private ProductCateAttrDTO productCateAttrDTO;
	
}
