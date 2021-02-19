
package com.mb.ext.core.service.spec;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.util.JsonDateSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class PartnerDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private String partnerUuid;
	
	private String partnerLevel;
	
	public String getPartnerUuid() {
		return partnerUuid;
	}

	public void setPartnerUuid(String partnerUuid) {
		this.partnerUuid = partnerUuid;
	}

	public String getPartnerLevel() {
		return partnerLevel;
	}

	public void setPartnerLevel(String partnerLevel) {
		this.partnerLevel = partnerLevel;
	}

	@DateTimeFormat(pattern="yyyy-MM-dd") 
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public MerchantDTO getMerchantDTO() {
		return merchantDTO;
	}

	public void setMerchantDTO(MerchantDTO merchantDTO) {
		this.merchantDTO = merchantDTO;
	}

	@JsonSerialize(using=JsonDateSerializer.class) 
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	private MerchantDTO merchantDTO;
	
	private Date effectiveDate;
	
	public String getContactName() {
		String contactName = "";
		if(merchantDTO != null) {
			contactName = merchantDTO.getContactName();
		}
		return contactName;
	}
	public void setContactName(String contactName) {
		
	}
	public String getMobileNo() {
		String mobileNo = "";
		if(merchantDTO != null) {
			mobileNo = merchantDTO.getMobileNo();
		}
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		
	}
	public String getMerchantAddress() {
		String merchantAddress = "";
		if(merchantDTO != null) {
			merchantAddress = merchantDTO.getMerchantAddress();
		}
		return merchantAddress;
	}
	public void setMerchantAddress(String merchantAddress) {
		
	}
}
