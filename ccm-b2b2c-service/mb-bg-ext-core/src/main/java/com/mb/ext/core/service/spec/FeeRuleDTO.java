
package com.mb.ext.core.service.spec;

import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeeRuleDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	private String userLevelName;
	
	//购买该会员等级所需产品箱数
	private int requiredProductUnit;
	
	//购买该会员等级所需金额
	private BigDecimal requiredProductAmount;
	
	public String getUserLevelName() {
		return userLevelName;
	}

	public void setUserLevelName(String userLevelName) {
		this.userLevelName = userLevelName;
	}

	public int getRequiredProductUnit() {
		return requiredProductUnit;
	}

	public void setRequiredProductUnit(int requiredProductUnit) {
		this.requiredProductUnit = requiredProductUnit;
	}

	public BigDecimal getRequiredProductAmount() {
		return requiredProductAmount;
	}

	public void setRequiredProductAmount(BigDecimal requiredProductAmount) {
		this.requiredProductAmount = requiredProductAmount;
	}

	public BigDecimal getProductUnitPrice() {
		return productUnitPrice;
	}

	public void setProductUnitPrice(BigDecimal productUnitPrice) {
		this.productUnitPrice = productUnitPrice;
	}

	//该会员等级复购时产品单价
	private BigDecimal productUnitPrice;
	
}
