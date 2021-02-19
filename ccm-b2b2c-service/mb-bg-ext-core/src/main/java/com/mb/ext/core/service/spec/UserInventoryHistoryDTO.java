
package com.mb.ext.core.service.spec;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.util.JsonDateMinuteDeserializer;
import com.mb.ext.core.util.JsonDateMinuteSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class UserInventoryHistoryDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private int balanceBefore;
	
	private int balanceAfter;
	
	private int balancePouchBefore;
	
	private int balancePouchAfter;
	
	private String tranUnitValue;
	
	public int getBalancePouchBefore() {
		return balancePouchBefore;
	}
	public void setBalancePouchBefore(int balancePouchBefore) {
		this.balancePouchBefore = balancePouchBefore;
	}
	public int getBalancePouchAfter() {
		return balancePouchAfter;
	}
	public void setBalancePouchAfter(int balancePouchAfter) {
		this.balancePouchAfter = balancePouchAfter;
	}
	public String getTranUnitValue() {
		return tranUnitValue;
	}
	public void setTranUnitValue(String tranUnitValue) {
		this.tranUnitValue = tranUnitValue;
	}

	private int tranUnit;
	
	private String tranType;
	
	private String tranDesc;
	
	public String getTranDesc() {
		return tranDesc;
	}
	public void setTranDesc(String tranDesc) {
		this.tranDesc = tranDesc;
	}

	private boolean isIncrease;
	
	private String userInventoryHistoryUuid;
	
	private Date createDate;
	@JsonSerialize(using=JsonDateMinuteSerializer.class)
	public Date getCreateDate() {
		return createDate;
	}
	@JsonDeserialize(using=JsonDateMinuteDeserializer.class)
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setIncrease(boolean isIncrease) {
		this.isIncrease = isIncrease;
	}

	public int getBalanceBefore() {
		return balanceBefore;
	}

	public void setBalanceBefore(int balanceBefore) {
		this.balanceBefore = balanceBefore;
	}

	public int getBalanceAfter() {
		return balanceAfter;
	}

	public void setBalanceAfter(int balanceAfter) {
		this.balanceAfter = balanceAfter;
	}

	public int getTranUnit() {
		return tranUnit;
	}

	public void setTranUnit(int tranUnit) {
		this.tranUnit = tranUnit;
	}

	public String getTranType() {
		return tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	public boolean getIsIncrease() {
		return isIncrease;
	}

	public void setIsIncrease(boolean isIncrease) {
		this.isIncrease = isIncrease;
	}

	public String getUserInventoryHistoryUuid() {
		return userInventoryHistoryUuid;
	}

	public void setUserInventoryHistoryUuid(String userInventoryHistoryUuid) {
		this.userInventoryHistoryUuid = userInventoryHistoryUuid;
	}

}
