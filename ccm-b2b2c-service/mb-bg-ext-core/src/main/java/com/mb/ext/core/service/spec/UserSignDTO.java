
package com.mb.ext.core.service.spec;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSignDTO extends AbstractBaseDTO
{
	private static final long serialVersionUID = -5274244703032974051L;

	private String userSignUuid;
	
	private Date signTime;
	
	private boolean isSigned;
	
	public boolean isSigned() {
		return isSigned;
	}

	public void setSigned(boolean isSigned) {
		this.isSigned = isSigned;
	}

	public String getUserSignUuid() {
		return userSignUuid;
	}

	public void setUserSignUuid(String userSignUuid) {
		this.userSignUuid = userSignUuid;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	public Date getSignTime() {
		return signTime;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public int getSignPoint() {
		return signPoint;
	}

	public void setSignPoint(int signPoint) {
		this.signPoint = signPoint;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	private int signPoint;
	
	private UserDTO userDTO;
}
