
package com.mb.ext.core.service.spec;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
import com.mb.framework.service.spec.StatusDTO;
import com.mb.framework.util.StringUtil;
@JsonInclude(Include.NON_NULL)
public class BodyDTO implements Serializable
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7903714034275674368L;

	private StatusDTO status = new StatusDTO();
	
	private AbstractBaseDTO data;
	
	
	public AbstractBaseDTO getData() {
		return data;
	}

	public void setData(AbstractBaseDTO data) {
		this.data = data;
	}

	public StatusDTO getStatus() {
		return status;
	}

	public void setStatus(StatusDTO status) {
		this.status = status;
	}

	public String toString()
	{
		return StringUtil.objectToString(this);
	}

}
