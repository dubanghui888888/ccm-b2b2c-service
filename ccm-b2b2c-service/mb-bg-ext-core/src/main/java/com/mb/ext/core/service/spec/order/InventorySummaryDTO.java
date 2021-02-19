package com.mb.ext.core.service.spec.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class InventorySummaryDTO extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private int merchantInventory;
	
	public int getMerchantInventory() {
		return merchantInventory;
	}

	public void setMerchantInventory(int merchantInventory) {
		this.merchantInventory = merchantInventory;
	}

	public int getDeliveryInventory() {
		return deliveryInventory;
	}

	public void setDeliveryInventory(int deliveryInventory) {
		this.deliveryInventory = deliveryInventory;
	}

	public int getUserInventory() {
		return userInventory;
	}

	public void setUserInventory(int userInventory) {
		this.userInventory = userInventory;
	}

	private int deliveryInventory;
	
	private int userInventory;
	
}
