
package com.mb.ext.core.service.spec;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class FunctionDTO extends AbstractBaseDTO
{



	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private String functionName;
	
	private String rootApplication;
	
	public String getRootApplication() {
		return rootApplication;
	}

	public void setRootApplication(String rootApplication) {
		this.rootApplication = rootApplication;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getFunctionCode() {
		return functionCode;
	}

	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}

	public String getApplicationCode() {
		return applicationCode;
	}

	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	private String functionCode;
	
	public String getFunctionDesc() {
		return functionDesc;
	}

	public void setFunctionDesc(String functionDesc) {
		this.functionDesc = functionDesc;
	}

	public String getApplicationDesc() {
		return applicationDesc;
	}

	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}

	private String functionDesc;
	
	private String applicationCode;
	
	private String applicationName;
	
	private String applicationDesc;



}
