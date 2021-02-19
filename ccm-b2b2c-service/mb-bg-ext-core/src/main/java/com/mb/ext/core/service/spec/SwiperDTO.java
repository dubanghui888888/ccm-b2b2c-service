
package com.mb.ext.core.service.spec;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.product.ProductCateDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class SwiperDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private String swiperUuid;
	
	private String linkType;
	
	private String location;
	
	private ProductCateDTO productCateDTO;
	
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

	public String getSwiperUuid() {
		return swiperUuid;
	}

	public void setSwiperUuid(String swiperUuid) {
		this.swiperUuid = swiperUuid;
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
