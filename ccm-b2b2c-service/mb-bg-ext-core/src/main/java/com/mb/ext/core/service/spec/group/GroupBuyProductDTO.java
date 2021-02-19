package com.mb.ext.core.service.spec.group;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class GroupBuyProductDTO extends AbstractBaseDTO{
	
	private static final long serialVersionUID = 7516252502556396978L;
	
	private String groupBuyProductUuid;

	private ProductDTO productDTO;
	
	private int stock;
	
	private int soldUnit;
	
	public int getSoldUnit() {
		return soldUnit;
	}

	public void setSoldUnit(int soldUnit) {
		this.soldUnit = soldUnit;
	}

	private BigDecimal unitPrice;
	
	public int getMinUserCount() {
		return minUserCount;
	}

	public void setMinUserCount(int minUserCount) {
		this.minUserCount = minUserCount;
	}

	public int getMaxTranDays() {
		return maxTranDays;
	}

	public void setMaxTranDays(int maxTranDays) {
		this.maxTranDays = maxTranDays;
	}

	private int minUserCount;
	
	private int maxTranDays;
	
	private MerchantDTO merchantDTO;
	
	private GroupBuyDefDTO groupBuyDefDTO;
	
	public MerchantDTO getMerchantDTO() {
		return merchantDTO;
	}

	public void setMerchantDTO(MerchantDTO merchantDTO) {
		this.merchantDTO = merchantDTO;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public ProductDTO getProductDTO() {
		return productDTO;
	}

	public String getGroupBuyProductUuid() {
		return groupBuyProductUuid;
	}

	public GroupBuyDefDTO getGroupBuyDefDTO() {
		return groupBuyDefDTO;
	}

	public void setGroupBuyDefDTO(GroupBuyDefDTO groupBuyDefDTO) {
		this.groupBuyDefDTO = groupBuyDefDTO;
	}

	public void setGroupBuyProductUuid(String groupBuyProductUuid) {
		this.groupBuyProductUuid = groupBuyProductUuid;
	}

	public void setProductDTO(ProductDTO productDTO) {
		this.productDTO = productDTO;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

}
