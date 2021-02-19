
package com.mb.ext.core.service.spec.merchant;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MerchantAccountDTOList extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	private List<MerchantAccountDTO> accounts = new ArrayList<MerchantAccountDTO>();

	public List<MerchantAccountDTO> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<MerchantAccountDTO> accounts) {
		this.accounts = accounts;
	}

	
}
