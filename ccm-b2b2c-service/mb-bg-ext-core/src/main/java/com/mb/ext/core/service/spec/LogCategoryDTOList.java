
package com.mb.ext.core.service.spec;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class LogCategoryDTOList extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	private String[] categories;

	public String[] getCategories() {
		return categories;
	}

	public void setCategories(String[] categories) {
		this.categories = categories;
	}
	
}
