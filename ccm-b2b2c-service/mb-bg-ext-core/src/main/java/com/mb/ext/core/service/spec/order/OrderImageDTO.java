
package com.mb.ext.core.service.spec.order;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderImageDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	private String orderImageUuid;
	
	private OrderDTO orderDTO;

	private String imageUrl;

	public String getOrderImageUuid() {
		return orderImageUuid;
	}

	public void setOrderImageUuid(String orderImageUuid) {
		this.orderImageUuid = orderImageUuid;
	}

	public OrderDTO getOrderDTO() {
		return orderDTO;
	}

	public void setOrderDTO(OrderDTO orderDTO) {
		this.orderDTO = orderDTO;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
}
