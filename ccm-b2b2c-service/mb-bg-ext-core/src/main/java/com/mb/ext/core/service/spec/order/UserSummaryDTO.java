package com.mb.ext.core.service.spec.order;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.ChartDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class UserSummaryDTO extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private int userCount;
	
	private int userCountL1;
	
	private int userCountL2;
	
	private int userCountL3;
	
	private int userCountL4;
	
	private List<ChartDTO> userCountChart;

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public int getUserCountL1() {
		return userCountL1;
	}

	public void setUserCountL1(int userCountL1) {
		this.userCountL1 = userCountL1;
	}

	public int getUserCountL2() {
		return userCountL2;
	}

	public void setUserCountL2(int userCountL2) {
		this.userCountL2 = userCountL2;
	}

	public int getUserCountL3() {
		return userCountL3;
	}

	public void setUserCountL3(int userCountL3) {
		this.userCountL3 = userCountL3;
	}

	public int getUserCountL4() {
		return userCountL4;
	}

	public void setUserCountL4(int userCountL4) {
		this.userCountL4 = userCountL4;
	}

	public List<ChartDTO> getUserCountChart() {
		return userCountChart;
	}

	public void setUserCountChart(List<ChartDTO> userCountChart) {
		this.userCountChart = userCountChart;
	}
	
}
