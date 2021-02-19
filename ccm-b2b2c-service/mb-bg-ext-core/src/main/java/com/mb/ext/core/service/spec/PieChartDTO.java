
package com.mb.ext.core.service.spec;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class PieChartDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	private String[] nameArray;
	
	private List<PieChartDataDTO> dataList;

	public List<PieChartDataDTO> getDataList() {
		return dataList;
	}

	public void setDataList(List<PieChartDataDTO> dataList) {
		this.dataList = dataList;
	}

	public String[] getNameArray() {
		return nameArray;
	}

	public void setNameArray(String[] nameArray) {
		this.nameArray = nameArray;
	}
	
	private int year;

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	
	
	

}
