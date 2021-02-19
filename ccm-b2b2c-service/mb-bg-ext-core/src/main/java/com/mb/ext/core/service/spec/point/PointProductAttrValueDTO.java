package com.mb.ext.core.service.spec.point;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class PointProductAttrValueDTO extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private String productAttrValueUuid;

	private String productAttrName;

	private String productAttrValue;
	
	public String getProductAttrValueUuid() {
		return productAttrValueUuid;
	}

	public void setProductAttrValueUuid(String productAttrValueUuid) {
		this.productAttrValueUuid = productAttrValueUuid;
	}

	public String getProductAttrName() {
		return productAttrName;
	}

	public void setProductAttrName(String productAttrName) {
		this.productAttrName = productAttrName;
	}

	public String getProductAttrValue() {
		return productAttrValue;
	}

	public void setProductAttrValue(String productAttrValue) {
		this.productAttrValue = productAttrValue;
	}

	public boolean isCateAttr() {
		return isCateAttr;
	}

	public void setCateAttr(boolean isCateAttr) {
		this.isCateAttr = isCateAttr;
	}

	public PointProductDTO getProductDTO() {
		return productDTO;
	}

	public void setProductDTO(PointProductDTO productDTO) {
		this.productDTO = productDTO;
	}

	public PointProductCateAttrDTO getProductCateAttrDTO() {
		return productCateAttrDTO;
	}

	public void setProductCateAttrDTO(PointProductCateAttrDTO productCateAttrDTO) {
		this.productCateAttrDTO = productCateAttrDTO;
	}

	private boolean isCateAttr;

	private PointProductDTO productDTO;
	
	private PointProductCateAttrDTO productCateAttrDTO;
	
}
