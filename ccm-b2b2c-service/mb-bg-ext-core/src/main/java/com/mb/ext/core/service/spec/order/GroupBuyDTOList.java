package com.mb.ext.core.service.spec.order;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.group.GroupBuyDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class GroupBuyDTOList extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private List<GroupBuyDTO> groupBuys = new ArrayList<GroupBuyDTO>();

	int total;

	public List<GroupBuyDTO> getGroupBuys() {
		return groupBuys;
	}

	public void setGroupBuys(List<GroupBuyDTO> groupBuys) {
		this.groupBuys = groupBuys;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
}
