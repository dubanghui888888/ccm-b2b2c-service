
package com.mb.ext.core.service.spec;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCartDTOList extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	public List<ShoppingCartDTO> getCarts() {
		return carts;
	}

	public void setCarts(List<ShoppingCartDTO> carts) {
		this.carts = carts;
	}

	private List<ShoppingCartDTO> carts = new ArrayList<ShoppingCartDTO>();
	
}
