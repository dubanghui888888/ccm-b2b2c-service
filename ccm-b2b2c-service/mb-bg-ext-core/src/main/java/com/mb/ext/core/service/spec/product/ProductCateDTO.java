package com.mb.ext.core.service.spec.product;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.AdDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class ProductCateDTO extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private String productCateUuid;

	private String cateName;
	
	private String catePath;
	
	public boolean isDisplayedHome() {
		return isDisplayedHome;
	}

	public void setDisplayedHome(boolean isDisplayedHome) {
		this.isDisplayedHome = isDisplayedHome;
	}

	private String catePicUrl;

	private int sortNumber;
	
	private boolean isDisplayedHome;
	
	private ProductCateDTO parentCateDTO;

	public ProductCateDTO getParentCateDTO() {
		return parentCateDTO;
	}

	public void setParentCateDTO(ProductCateDTO parentCateDTO) {
		this.parentCateDTO = parentCateDTO;
	}

	public List<ProductCateAttrDTO> getAttrList() {
		return attrList;
	}

	public void setAttrList(List<ProductCateAttrDTO> attrList) {
		this.attrList = attrList;
	}

	public List<ProductCateDTO> getChildList() {
		return childList;
	}

	public void setChildList(List<ProductCateDTO> childList) {
		this.childList = childList;
	}

	private boolean isLeaf;
	
	public List<ProductBrandDTO> getBrandList() {
		return brandList;
	}

	public void setBrandList(List<ProductBrandDTO> brandList) {
		this.brandList = brandList;
	}

	public List<AdDTO> getAdList() {
		return adList;
	}

	public void setAdList(List<AdDTO> adList) {
		this.adList = adList;
	}

	private List<ProductCateAttrDTO> attrList = new ArrayList<ProductCateAttrDTO>();
	
	private List<ProductCateDTO> childList = new ArrayList<ProductCateDTO>();
	
	private List<ProductBrandDTO> brandList = new ArrayList<ProductBrandDTO>();
	
	private List<AdDTO> adList = new ArrayList<AdDTO>();

	public String getProductCateUuid() {
		return productCateUuid;
	}

	public void setProductCateUuid(String productCateUuid) {
		this.productCateUuid = productCateUuid;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public String getCatePath() {
		return catePath;
	}

	public void setCatePath(String catePath) {
		this.catePath = catePath;
	}

	public String getCatePicUrl() {
		return catePicUrl;
	}

	public void setCatePicUrl(String catePicUrl) {
		this.catePicUrl = catePicUrl;
	}

	public int getSortNumber() {
		return sortNumber;
	}

	public void setSortNumber(int sortNumber) {
		this.sortNumber = sortNumber;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	
}
