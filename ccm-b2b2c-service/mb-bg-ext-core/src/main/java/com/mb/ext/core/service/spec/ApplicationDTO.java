
package com.mb.ext.core.service.spec;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class ApplicationDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private String name;
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private String code;
	
	private String desc;
	
	private String rootApplication;
	
	public String getRootApplication() {
		return rootApplication;
	}

	public void setRootApplication(String rootApplication) {
		this.rootApplication = rootApplication;
	}

	private List<FunctionDTO> functionList = new ArrayList<FunctionDTO>();

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<FunctionDTO> getFunctionList() {
		return functionList;
	}

	public void setFunctionList(List<FunctionDTO> functionList) {
		this.functionList = functionList;
	}

	public void setName(String name) {
		this.name = name;
	}



}
