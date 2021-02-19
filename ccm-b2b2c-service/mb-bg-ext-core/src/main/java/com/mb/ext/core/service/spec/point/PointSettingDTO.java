
package com.mb.ext.core.service.spec.point;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;

import java.math.BigDecimal;

@JsonInclude(Include.NON_NULL)
public class PointSettingDTO extends AbstractBaseDTO
{

	private static final long serialVersionUID = -5444408664751460073L;

	private String pointSettingUuid;

	private boolean isEnabled;
	
	private BigDecimal amount;
	
	private int point;

	public String getPointSettingUuid() {
		return pointSettingUuid;
	}

	public void setPointSettingUuid(String pointSettingUuid) {
		this.pointSettingUuid = pointSettingUuid;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean enabled) {
		isEnabled = enabled;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}
}
