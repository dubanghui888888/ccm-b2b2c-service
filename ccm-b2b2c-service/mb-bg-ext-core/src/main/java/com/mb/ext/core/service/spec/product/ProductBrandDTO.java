package com.mb.ext.core.service.spec.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class ProductBrandDTO extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private String productBrandUuid;

	private String name;
	
	private String description;
	
	private String logoUrl;
	
	private int sortNumber;

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public int getSortNumber() {
		return sortNumber;
	}

	public void setSortNumber(int sortNumber) {
		this.sortNumber = sortNumber;
	}

	public String getProductBrandUuid() {
		return productBrandUuid;
	}

	public void setProductBrandUuid(String productBrandUuid) {
		this.productBrandUuid = productBrandUuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
