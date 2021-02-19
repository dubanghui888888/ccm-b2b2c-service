package com.mb.ext.core.service.spec.group;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class GroupBuyDefDTO extends AbstractBaseDTO{
	
	private static final long serialVersionUID = 7516252502556396978L;
	
	private String groupBuyDefUuid;

	public String getGroupBuyDefUuid() {
		return groupBuyDefUuid;
	}

	public void setGroupBuyDefUuid(String groupBuyDefUuid) {
		this.groupBuyDefUuid = groupBuyDefUuid;
	}

	public int getMinUserCount() {
		return minUserCount;
	}

	public void setMinUserCount(int minUserCount) {
		this.minUserCount = minUserCount;
	}

	public int getMaxTranDays() {
		return maxTranDays;
	}

	public void setMaxTranDays(int maxTranDays) {
		this.maxTranDays = maxTranDays;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private String status;
	
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private int minUserCount;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartTime() {
		return startTime;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndTime() {
		return endTime;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public List<GroupBuyProductDTO> getGroupBuyProductList() {
		return groupBuyProductList;
	}

	public void setGroupBuyProductList(List<GroupBuyProductDTO> groupBuyProductList) {
		this.groupBuyProductList = groupBuyProductList;
	}

	private int maxTranDays;
	
	private Date startTime;
	
	public MerchantDTO getMerchantDTO() {
		return merchantDTO;
	}

	public void setMerchantDTO(MerchantDTO merchantDTO) {
		this.merchantDTO = merchantDTO;
	}

	private Date endTime;
	
	private List<GroupBuyProductDTO> groupBuyProductList;
	
	private MerchantDTO merchantDTO;
	
}
