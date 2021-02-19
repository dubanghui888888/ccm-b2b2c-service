
package com.mb.ext.core.service.spec.group;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class GroupBuyProductDTOList extends AbstractBaseDTO
{

	private static final long serialVersionUID = 1L;

	private List<GroupBuyProductDTO> groupBuys = new ArrayList<GroupBuyProductDTO>();
	
	public int getTotal() {
		return total;
	}

	public List<GroupBuyProductDTO> getGroupBuys() {
		return groupBuys;
	}

	public void setGroupBuys(List<GroupBuyProductDTO> groupBuys) {
		this.groupBuys = groupBuys;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	private int total;
}
