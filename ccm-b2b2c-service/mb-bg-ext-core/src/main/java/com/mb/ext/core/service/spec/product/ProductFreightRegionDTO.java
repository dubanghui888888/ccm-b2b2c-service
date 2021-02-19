package com.mb.ext.core.service.spec.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.AreaDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class ProductFreightRegionDTO extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private String productFreightRegionUuid;

	private ProductFreightDTO productFreightDTO;

	public BigDecimal getFirstWeight() {
		return firstWeight;
	}

	public void setFirstWeight(BigDecimal firstWeight) {
		this.firstWeight = firstWeight;
	}

	public BigDecimal getNextWeight() {
		return nextWeight;
	}

	public void setNextWeight(BigDecimal nextWeight) {
		this.nextWeight = nextWeight;
	}

	private List<AreaDTO> areaList;
	
	private int firstUnit;
	
	private int nextUnit;
	
	public List<String> getAreaIds() {
		List<String> areaIds = new ArrayList<String>();
		for (AreaDTO areaDTO : areaList) {
			areaIds.add(areaDTO.getAreaId());
		}
		return areaIds;
	}
	
	public void setAreaIds(List<String> areaIds) {
		
	}
	
	public List<AreaDTO> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<AreaDTO> areaList) {
		this.areaList = areaList;
	}

	private BigDecimal firstWeight;
	
	private BigDecimal nextWeight;

	private BigDecimal firstPrice;
	
	
	
	public BigDecimal getNextPrice() {
		return nextPrice;
	}

	public void setNextPrice(BigDecimal nextPrice) {
		this.nextPrice = nextPrice;
	}

	private BigDecimal nextPrice;
	
	public String getProductFreightRegionUuid() {
		return productFreightRegionUuid;
	}

	public void setProductFreightRegionUuid(String productFreightRegionUuid) {
		this.productFreightRegionUuid = productFreightRegionUuid;
	}

	public int getFirstUnit() {
		return firstUnit;
	}

	public ProductFreightDTO getProductFreightDTO() {
		return productFreightDTO;
	}

	public void setProductFreightDTO(ProductFreightDTO productFreightDTO) {
		this.productFreightDTO = productFreightDTO;
	}

	public void setFirstUnit(int firstUnit) {
		this.firstUnit = firstUnit;
	}

	public BigDecimal getFirstPrice() {
		return firstPrice;
	}

	public void setFirstPrice(BigDecimal firstPrice) {
		this.firstPrice = firstPrice;
	}

	public int getNextUnit() {
		return nextUnit;
	}

	public void setNextUnit(int nextUnit) {
		this.nextUnit = nextUnit;
	}
}
