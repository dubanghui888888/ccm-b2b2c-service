package com.mb.ext.core.service.spec.product;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class ProductInventoryDTOList extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private int total;	
	
	private List<ProductInventoryInboundDTO> inboundList;
	
	private List<ProductInventoryOutboundDTO> outboundList;
	
	private List<ProductInventoryTakingDTO> takingList;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<ProductInventoryTakingDTO> getTakingList() {
		return takingList;
	}

	public void setTakingList(List<ProductInventoryTakingDTO> takingList) {
		this.takingList = takingList;
	}

	public List<ProductInventoryInboundDTO> getInboundList() {
		return inboundList;
	}

	public void setInboundList(List<ProductInventoryInboundDTO> inboundList) {
		this.inboundList = inboundList;
	}

	public List<ProductInventoryOutboundDTO> getOutboundList() {
		return outboundList;
	}

	public void setOutboundList(List<ProductInventoryOutboundDTO> outboundList) {
		this.outboundList = outboundList;
	}
	
}
