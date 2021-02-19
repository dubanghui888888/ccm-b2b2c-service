package com.mb.ext.core.service.spec.seckill;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class SecKillProductDTO extends AbstractBaseDTO{
	
	private static final long serialVersionUID = 7516252502556396978L;
	
	private String secKillProductUuid;

	private ProductDTO productDTO;
	
	private MerchantDTO merchantDTO;
	
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public MerchantDTO getMerchantDTO() {
		return merchantDTO;
	}

	public void setMerchantDTO(MerchantDTO merchantDTO) {
		this.merchantDTO = merchantDTO;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	private int stock;
	
	public int getSoldUnit() {
		return soldUnit;
	}

	public void setSoldUnit(int soldUnit) {
		this.soldUnit = soldUnit;
	}

	private int soldUnit;
	
	private BigDecimal unitPrice;
	
	public String getSecKillProductUuid() {
		return secKillProductUuid;
	}

	public void setSecKillProductUuid(String secKillProductUuid) {
		this.secKillProductUuid = secKillProductUuid;
	}

	public ProductDTO getProductDTO() {
		return productDTO;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartTime() {
		return startTime;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	private String status;
	
	private Date startTime;
	
	private Date endTime;
	
}
