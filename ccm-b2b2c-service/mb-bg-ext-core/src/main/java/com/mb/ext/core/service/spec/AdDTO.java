
package com.mb.ext.core.service.spec;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.product.ProductCateDTO;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class AdDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private String adUuid;
	
	private String linkType;
	
	private String location;
	
	private boolean isActive;
	
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		this.isActive = active;
	}

	private ProductCateDTO productCateDTO;
	
	public ProductDTO getProductDTO() {
		return productDTO;
	}

	public void setProductDTO(ProductDTO productDTO) {
		this.productDTO = productDTO;
	}

	public GroupDTO getGroupDTO() {
		return groupDTO;
	}

	public void setGroupDTO(GroupDTO groupDTO) {
		this.groupDTO = groupDTO;
	}

	private ProductDTO productDTO;
	
	private GroupDTO groupDTO;
	
	public ProductCateDTO getProductCateDTO() {
		return productCateDTO;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setProductCateDTO(ProductCateDTO productCateDTO) {
		this.productCateDTO = productCateDTO;
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public String getAdUuid() {
		return adUuid;
	}

	public void setAdUuid(String adUuid) {
		this.adUuid = adUuid;
	}

	private String url;
	
	private String content;
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
