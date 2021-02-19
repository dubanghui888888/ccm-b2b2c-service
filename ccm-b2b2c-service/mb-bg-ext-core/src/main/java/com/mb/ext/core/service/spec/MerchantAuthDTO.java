
package com.mb.ext.core.service.spec;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.util.JsonDateTimeSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class MerchantAuthDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private String password;
	
	private Date lastLoginTime;
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public int getFailCount() {
		return failCount;
	}

	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}

	private int failCount;
	
}
