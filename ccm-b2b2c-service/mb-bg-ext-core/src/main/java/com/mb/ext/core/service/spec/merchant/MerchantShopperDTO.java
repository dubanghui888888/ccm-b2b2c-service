
package com.mb.ext.core.service.spec.merchant;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MerchantShopperDTO extends AbstractBaseDTO
{
	
	private static final long serialVersionUID = 880171191309444789L;
	
	private String merchantShopperUuid;

	public String getMerchantShopperUuid() {
		return merchantShopperUuid;
	}

	public void setMerchantShopperUuid(String merchantShopperUuid) {
		this.merchantShopperUuid = merchantShopperUuid;
	}

	private MerchantDTO merchantDTO;
	
	private String name;
	
	private String sex;
	
	private String mobileNo;
	
	private String photo;
	
	public MerchantDTO getMerchantDTO() {
		return merchantDTO;
	}

	public void setMerchantDTO(MerchantDTO merchantDTO) {
		this.merchantDTO = merchantDTO;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	private boolean isEnabled;
}
