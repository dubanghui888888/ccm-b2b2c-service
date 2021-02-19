
package com.mb.ext.core.service.spec.promote;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class PromoteFreightOffDTO extends AbstractBaseDTO
{



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String promoteFreightOffUuid;
	
	private boolean isActive;
	
	private List<ProductDTO> productList;

	public List<ProductDTO> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductDTO> productList) {
		this.productList = productList;
	}

	public MerchantDTO getMerchantDTO() {
		return merchantDTO;
	}

	public void setMerchantDTO(MerchantDTO merchantDTO) {
		this.merchantDTO = merchantDTO;
	}

	List<ProductDTO> benefitProductList;
	
	private MerchantDTO merchantDTO;
	
	public String getPromoteFreightOffUuid() {
		return promoteFreightOffUuid;
	}

	public void setPromoteFreightOffUuid(String promoteFreightOffUuid) {
		this.promoteFreightOffUuid = promoteFreightOffUuid;
	}

	public Date getValidStartDate() {
		return validStartDate;
	}

	public void setValidStartDate(Date validStartDate) {
		this.validStartDate = validStartDate;
	}

	public Date getValidEndDate() {
		return validEndDate;
	}

	public void setValidEndDate(Date validEndDate) {
		this.validEndDate = validEndDate;
	}

	public List<ProductDTO> getBenefitProductList() {
		return benefitProductList;
	}

	public void setBenefitProductList(List<ProductDTO> benefitProductList) {
		this.benefitProductList = benefitProductList;
	}

	private String name;
	

	private Date validStartDate;
	
	private Date validEndDate;
	

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getConditionAmount() {
		return conditionAmount;
	}

	public void setConditionAmount(BigDecimal conditionAmount) {
		this.conditionAmount = conditionAmount;
	}
	
	private BigDecimal conditionAmount;

}
