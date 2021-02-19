
package com.mb.ext.core.service.spec;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.profit.ProfitPerformanceDTO;
import com.mb.ext.core.service.spec.profit.ProfitRecruitDTO;
import com.mb.ext.core.service.spec.profit.ProfitSaleDTO;
import com.mb.ext.core.service.spec.profit.ProfitTrainerDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfitDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	List<ProfitRecruitDTO> profitRecruitList = new ArrayList<ProfitRecruitDTO>();
	
	List<ProfitTrainerDTO> profitTrainerList = new ArrayList<ProfitTrainerDTO>();
	
	List<ProfitAwardDTO> profitAwardList = new ArrayList<ProfitAwardDTO>();
	
	List<ProfitUpgradeDTO> profitUpgradeList = new ArrayList<ProfitUpgradeDTO>();
	
	List<ProfitPerformanceDTO> profitPerformanceList = new ArrayList<ProfitPerformanceDTO>();
	
	ProfitSaleDTO profitSaleDTO;
	
	public List<ProfitTrainerDTO> getProfitTrainerList() {
		return profitTrainerList;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	public void setProfitTrainerList(List<ProfitTrainerDTO> profitTrainerList) {
		this.profitTrainerList = profitTrainerList;
	}

	int merchantAward;
	
	int partnerAward;
	
	int merchantAwardBaoDan;
	
	BigDecimal taxRate;

	public int getPartnerAward() {
		return partnerAward;
	}

	public void setPartnerAward(int partnerAward) {
		this.partnerAward = partnerAward;
	}

	public int getMerchantAward() {
		return merchantAward;
	}

	public List<ProfitPerformanceDTO> getProfitPerformanceList() {
		return profitPerformanceList;
	}

	public void setProfitPerformanceList(List<ProfitPerformanceDTO> profitPerformanceList) {
		this.profitPerformanceList = profitPerformanceList;
	}

	public int getMerchantAwardBaoDan() {
		return merchantAwardBaoDan;
	}

	public void setMerchantAwardBaoDan(int merchantAwardBaoDan) {
		this.merchantAwardBaoDan = merchantAwardBaoDan;
	}

	public void setMerchantAward(int merchantAward) {
		this.merchantAward = merchantAward;
	}

	public List<ProfitRecruitDTO> getProfitRecruitList() {
		return profitRecruitList;
	}

	public void setProfitRecruitList(List<ProfitRecruitDTO> profitRecruitList) {
		this.profitRecruitList = profitRecruitList;
	}

	public List<ProfitAwardDTO> getProfitAwardList() {
		return profitAwardList;
	}

	public void setProfitAwardList(List<ProfitAwardDTO> profitAwardList) {
		this.profitAwardList = profitAwardList;
	}

	public List<ProfitUpgradeDTO> getProfitUpgradeList() {
		return profitUpgradeList;
	}

	public void setProfitUpgradeList(List<ProfitUpgradeDTO> profitUpgradeList) {
		this.profitUpgradeList = profitUpgradeList;
	}

	public ProfitSaleDTO getProfitSaleDTO() {
		return profitSaleDTO;
	}

	public void setProfitSaleDTO(ProfitSaleDTO profitSaleDTO) {
		this.profitSaleDTO = profitSaleDTO;
	}
}
