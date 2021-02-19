
package com.mb.ext.core.service.spec.merchant;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MerchantDTOList extends AbstractBaseDTO
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2812467474386139963L;
	private List<MerchantDTO> merchants = new ArrayList<MerchantDTO>();

	
	private int total;
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<MerchantDTO> getMerchants() {
		return merchants;
	}

	public void setMerchants(List<MerchantDTO> merchants) {
		this.merchants = merchants;
	}
}
