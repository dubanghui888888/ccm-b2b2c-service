package com.mb.ext.core.service.spec.product;

import java.util.Date;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class ProductInventoryInboundDTO extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private String productInventoryInboundUuid;

	private String tranType;
	
	private Date tranTime;
	
	private int tranUnit;
	
	private String memo;
	
	private String skuName;
	
	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	private ProductDTO productDTO;
	
	private ProductSkuDTO productSkuDTO;
	
	public String getMerchantName() {
		String merchantName = "";
		if(productDTO != null && productDTO.getMerchantDTO() != null) {
			merchantName = productDTO.getMerchantDTO().getMerchantName();
		}
		return merchantName;
	}
	
	public String getProductName() {
		String productName = "";
		if(productDTO != null) {
			productName = productDTO.getProductName();
		}
		return productName;
	}

	public String getProductInventoryInboundUuid() {
		return productInventoryInboundUuid;
	}

	public void setProductInventoryInboundUuid(String productInventoryInboundUuid) {
		this.productInventoryInboundUuid = productInventoryInboundUuid;
	}

	public String getTranType() {
		return tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public Date getTranTime() {
		return tranTime;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public void setTranTime(Date tranTime) {
		this.tranTime = tranTime;
	}

	public int getTranUnit() {
		return tranUnit;
	}

	public void setTranUnit(int tranUnit) {
		this.tranUnit = tranUnit;
	}

	public ProductDTO getProductDTO() {
		return productDTO;
	}

	public void setProductDTO(ProductDTO productDTO) {
		this.productDTO = productDTO;
	}

	public ProductSkuDTO getProductSkuDTO() {
		return productSkuDTO;
	}

	public void setProductSkuDTO(ProductSkuDTO productSkuDTO) {
		this.productSkuDTO = productSkuDTO;
	}
	
	
	
}
