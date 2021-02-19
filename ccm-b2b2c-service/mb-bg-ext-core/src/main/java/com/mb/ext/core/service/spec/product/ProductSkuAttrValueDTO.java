package com.mb.ext.core.service.spec.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class ProductSkuAttrValueDTO extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private String productSkuAttrUuid;
	
	private String productSkuAttrValueUuid;
	
	private String skuAttrName;
	
	public String getProductSkuAttrUuid() {
		return productSkuAttrUuid;
	}

	public void setProductSkuAttrUuid(String productSkuAttrUuid) {
		this.productSkuAttrUuid = productSkuAttrUuid;
	}

	public String getProductSkuAttrValueUuid() {
		return productSkuAttrValueUuid;
	}

	public void setProductSkuAttrValueUuid(String productSkuAttrValueUuid) {
		this.productSkuAttrValueUuid = productSkuAttrValueUuid;
	}

	public String getSkuAttrName() {
		return skuAttrName;
	}

	public void setSkuAttrName(String skuAttrName) {
		this.skuAttrName = skuAttrName;
	}

	public String getSkuAttrValue() {
		return skuAttrValue;
	}

	public void setSkuAttrValue(String skuAttrValue) {
		this.skuAttrValue = skuAttrValue;
	}

	private String skuAttrValue;

	private String imageUrl;

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
