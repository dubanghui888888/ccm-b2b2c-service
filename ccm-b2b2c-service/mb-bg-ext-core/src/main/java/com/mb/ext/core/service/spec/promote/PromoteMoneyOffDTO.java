
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
public class PromoteMoneyOffDTO extends AbstractBaseDTO
{



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String promoteMoneyOffUuid;
	
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
	
	public String getPromoteMoneyOffUuid() {
		return promoteMoneyOffUuid;
	}

	public void setPromoteMoneyOffUuid(String promoteMoneyOffUuid) {
		this.promoteMoneyOffUuid = promoteMoneyOffUuid;
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

	public BigDecimal getBenefitCash() {
		return benefitCash;
	}

	public void setBenefitCash(BigDecimal benefitCash) {
		this.benefitCash = benefitCash;
	}

	public BigDecimal getConditionAmount() {
		return conditionAmount;
	}

	public void setConditionAmount(BigDecimal conditionAmount) {
		this.conditionAmount = conditionAmount;
	}
	
	private BigDecimal benefitCash;
	
	private BigDecimal conditionAmount;

	private BigDecimal benefitCashL2;
	
	private BigDecimal conditionAmountL2;
	
	private BigDecimal benefitCashL3;
	
	private BigDecimal conditionAmountL3;

	public BigDecimal getBenefitCashL2() {
		return benefitCashL2;
	}

	public void setBenefitCashL2(BigDecimal benefitCashL2) {
		this.benefitCashL2 = benefitCashL2;
	}

	public BigDecimal getConditionAmountL2() {
		return conditionAmountL2;
	}

	public void setConditionAmountL2(BigDecimal conditionAmountL2) {
		this.conditionAmountL2 = conditionAmountL2;
	}

	public BigDecimal getBenefitCashL3() {
		return benefitCashL3;
	}

	public void setBenefitCashL3(BigDecimal benefitCashL3) {
		this.benefitCashL3 = benefitCashL3;
	}

	public BigDecimal getConditionAmountL3() {
		return conditionAmountL3;
	}

	public void setConditionAmountL3(BigDecimal conditionAmountL3) {
		this.conditionAmountL3 = conditionAmountL3;
	}
}
