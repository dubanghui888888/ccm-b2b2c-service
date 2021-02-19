package com.mb.ext.core.service.spec.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class AlipayFormDTO extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private String alipayForm;

	public String getAlipayForm() {
		return alipayForm;
	}

	public void setAlipayForm(String alipayForm) {
		this.alipayForm = alipayForm;
	}	
}
