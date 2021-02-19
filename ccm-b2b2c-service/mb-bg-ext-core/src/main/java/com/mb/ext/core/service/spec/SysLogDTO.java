
package com.mb.ext.core.service.spec;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.util.JsonDateMinuteDeserializer;
import com.mb.ext.core.util.JsonDateMinuteSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SysLogDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	public String getSysLogUuid() {
		return sysLogUuid;
	}

	public void setSysLogUuid(String sysLogUuid) {
		this.sysLogUuid = sysLogUuid;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public Date getLogDate() {
		return logDate;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public String getLogUserId() {
		return logUserId;
	}

	public void setLogUserId(String logUserId) {
		this.logUserId = logUserId;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getLogCategory() {
		return logCategory;
	}

	public void setLogCategory(String logCategory) {
		this.logCategory = logCategory;
	}

	public String getLogDetail() {
		return logDetail;
	}

	public void setLogDetail(String logDetail) {
		this.logDetail = logDetail;
	}

	private String sysLogUuid;

	private Date logDate;
	
	private String logUserId;
	
	private String logType;
	
	private String logCategory;
	
	private String logDetail;
	
}
