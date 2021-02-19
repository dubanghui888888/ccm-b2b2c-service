package com.mb.ext.core.service.spec.point;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class PointProductCateAttrDTO extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private PointProductCateDTO productCateDTO;
	
	public PointProductCateDTO getProductCateDTO() {
		return productCateDTO;
	}

	public void setProductCateDTO(PointProductCateDTO productCateDTO) {
		this.productCateDTO = productCateDTO;
	}

	private String productCateAttrUuid;
	
	private String attrName;
	
	public String[] getAttrValueArray() {
		return attrValueArray;
	}

	public void setAttrValueArray(String[] attrValueArray) {
		this.attrValueArray = attrValueArray;
	}

	private String[] attrValueArray;
	
	private boolean isSpuAttr;
	
	public boolean isSkuAttr() {
		return isSkuAttr;
	}

	public void setSkuAttr(boolean isSkuAttr) {
		this.isSkuAttr = isSkuAttr;
	}

	public boolean isSearchAttr() {
		return isSearchAttr;
	}

	public void setSearchAttr(boolean isSearchAttr) {
		this.isSearchAttr = isSearchAttr;
	}

	private boolean isSkuAttr;

	private boolean isSearchAttr;
	
	private boolean isMandatory;
	
	private boolean isMultiple;
	
	public String getProductCateAttrUuid() {
		return productCateAttrUuid;
	}

	public void setProductCateAttrUuid(String productCateAttrUuid) {
		this.productCateAttrUuid = productCateAttrUuid;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public boolean isSpuAttr() {
		return isSpuAttr;
	}

	public void setSpuAttr(boolean isSpuAttr) {
		this.isSpuAttr = isSpuAttr;
	}

	public boolean isMandatory() {
		return isMandatory;
	}

	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	public boolean isMultiple() {
		return isMultiple;
	}

	public void setMultiple(boolean isMultiple) {
		this.isMultiple = isMultiple;
	}

	public boolean isInput() {
		return isInput;
	}

	public void setInput(boolean isInput) {
		this.isInput = isInput;
	}

	private boolean isInput;
}
