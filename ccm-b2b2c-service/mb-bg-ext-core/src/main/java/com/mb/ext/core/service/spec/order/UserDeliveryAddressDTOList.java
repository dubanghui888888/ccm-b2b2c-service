package com.mb.ext.core.service.spec.order;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class UserDeliveryAddressDTOList  extends AbstractBaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<UserDeliveryAddressDTO> addresses = new ArrayList<UserDeliveryAddressDTO>();

	public List<UserDeliveryAddressDTO> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<UserDeliveryAddressDTO> addresses) {
		this.addresses = addresses;
	}	
	
	
}
