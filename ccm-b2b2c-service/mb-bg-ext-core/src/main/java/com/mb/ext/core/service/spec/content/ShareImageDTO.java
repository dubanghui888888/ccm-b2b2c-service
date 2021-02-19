
package com.mb.ext.core.service.spec.content;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class ShareImageDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private String shareImageUuid;
	private String url;
	private ShareDTO shareDTO;
	public String getShareImageUuid() {
		return shareImageUuid;
	}
	public void setShareImageUuid(String shareImageUuid) {
		this.shareImageUuid = shareImageUuid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public ShareDTO getShareDTO() {
		return shareDTO;
	}
	public void setShareDTO(ShareDTO shareDTO) {
		this.shareDTO = shareDTO;
	}

}
