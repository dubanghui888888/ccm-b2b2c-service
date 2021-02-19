
package com.mb.ext.core.service.spec;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class ChartDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	private String valueDate;
	
	private int valueInt;
	
	private BigDecimal valueDecimal;
	
	public String getValueDate() {
		return valueDate;
	}

	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}

	public int getValueInt() {
		return valueInt;
	}

	public void setValueInt(int valueInt) {
		this.valueInt = valueInt;
	}

	public BigDecimal getValueDecimal() {
		return valueDecimal;
	}

	public void setValueDecimal(BigDecimal valueDecimal) {
		this.valueDecimal = valueDecimal;
	}

	public String getValueStr() {
		return valueStr;
	}

	public void setValueStr(String valueStr) {
		this.valueStr = valueStr;
	}

	private String valueStr;


}
