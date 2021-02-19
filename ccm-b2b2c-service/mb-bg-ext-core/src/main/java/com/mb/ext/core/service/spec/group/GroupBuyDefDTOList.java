
package com.mb.ext.core.service.spec.group;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class GroupBuyDefDTOList extends AbstractBaseDTO
{

	private static final long serialVersionUID = 1L;

	private List<GroupBuyDefDTO> groupBuys = new ArrayList<GroupBuyDefDTO>();
	
	public int getTotal() {
		return total;
	}

	public List<GroupBuyDefDTO> getGroupBuys() {
		return groupBuys;
	}

	public void setGroupBuys(List<GroupBuyDefDTO> groupBuys) {
		this.groupBuys = groupBuys;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	private int total;
}
