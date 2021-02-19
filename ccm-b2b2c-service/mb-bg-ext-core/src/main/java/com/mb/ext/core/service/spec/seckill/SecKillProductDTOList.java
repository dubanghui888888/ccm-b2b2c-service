
package com.mb.ext.core.service.spec.seckill;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class SecKillProductDTOList extends AbstractBaseDTO
{

	private static final long serialVersionUID = 1L;

	private List<SecKillProductDTO> secKills = new ArrayList<SecKillProductDTO>();
	
	public int getTotal() {
		return total;
	}

	public List<SecKillProductDTO> getSecKills() {
		return secKills;
	}

	public void setSecKills(List<SecKillProductDTO> secKills) {
		this.secKills = secKills;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	private int total;
}
