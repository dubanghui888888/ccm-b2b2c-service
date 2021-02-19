
package com.mb.ext.core.service.spec;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class PrintSettingDTO extends AbstractBaseDTO
{

	private static final long serialVersionUID = -5444408664751460073L;

	private String printSettingUuid;

	private String feieUser;
	
	private MerchantDTO merchantDTO;

	public MerchantDTO getMerchantDTO() {
		return merchantDTO;
	}

	public void setMerchantDTO(MerchantDTO merchantDTO) {
		this.merchantDTO = merchantDTO;
	}

	public String getPrintSettingUuid() {
		return printSettingUuid;
	}

	public void setPrintSettingUuid(String printSettingUuid) {
		this.printSettingUuid = printSettingUuid;
	}

	public String getFeieUser() {
		return feieUser;
	}

	public void setFeieUser(String feieUser) {
		this.feieUser = feieUser;
	}

	public String getFeieSn() {
		return feieSn;
	}

	public void setFeieSn(String feieSn) {
		this.feieSn = feieSn;
	}

	public String getFeieKey() {
		return feieKey;
	}

	public void setFeieKey(String feieKey) {
		this.feieKey = feieKey;
	}

	public String getFeieUkey() {
		return feieUkey;
	}

	public void setFeieUkey(String feieUkey) {
		this.feieUkey = feieUkey;
	}

	private String feieSn;	

	private String feieKey;

	private String feieUkey;

}
