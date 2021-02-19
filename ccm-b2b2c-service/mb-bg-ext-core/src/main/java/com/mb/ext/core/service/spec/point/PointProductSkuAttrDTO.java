package com.mb.ext.core.service.spec.point;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class PointProductSkuAttrDTO extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private String productSkuAttrUuid;

	private String skuAttrName;
	
	List<PointProductSkuAttrValueDTO> productAttrValueList = new ArrayList<PointProductSkuAttrValueDTO>();
	

	public List<PointProductSkuAttrValueDTO> getProductAttrValueList() {
		return productAttrValueList;
	}

	public void setProductAttrValueList(
			List<PointProductSkuAttrValueDTO> productAttrValueList) {
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

	public String getProductSkuAttrUuid() {
		return productSkuAttrUuid;
	}

	public void setProductSkuAttrUuid(String productSkuAttrUuid) {
		this.productSkuAttrUuid = productSkuAttrUuid;
	}

	private boolean isCateAttr;

	private PointProductDTO productDTO;
	
	private PointProductCateAttrDTO productCateAttrDTO;
	
}
