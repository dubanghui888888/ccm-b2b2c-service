
package com.mb.ext.core.service.spec.merchant;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MerchantShopperDTOList extends AbstractBaseDTO
{

	private static final long serialVersionUID = 2812467474386139963L;
	
	private List<MerchantShopperDTO> shoppers = new ArrayList<MerchantShopperDTO>();
	
	private int total;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<MerchantShopperDTO> getShoppers() {
		return shoppers;
	}

	public void setShoppers(List<MerchantShopperDTO> shoppers) {
		this.shoppers = shoppers;
	}

}
