
package com.mb.ext.core.service.spec.content;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class PosterDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private String posterUuid;
	
	private String groupBuyUuid;
	
	public String getPosterUuid() {
		return posterUuid;
	}

	public String getGroupBuyUuid() {
		return groupBuyUuid;
	}

	public void setGroupBuyUuid(String groupBuyUuid) {
		this.groupBuyUuid = groupBuyUuid;
	}

	public void setPosterUuid(String posterUuid) {
		this.posterUuid = posterUuid;
	}

	private String url;
	
	private TagDTO tagDTO;

	private UserDTO userDTO;
	
	private String userUuid;
	
	public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	private String productUuid;
	
	private String shareHref;
	
	private String posterUrl;
	
	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	public String getProductUuid() {
		return productUuid;
	}

	public void setProductUuid(String productUuid) {
		this.productUuid = productUuid;
	}

	public String getShareHref() {
		return shareHref;
	}

	public void setShareHref(String shareHref) {
		this.shareHref = shareHref;
	}

	public int getShareType() {
		return shareType;
	}

	public void setShareType(int shareType) {
		this.shareType = shareType;
	}

	public int getHrefType() {
		return hrefType;
	}

	public void setHrefType(int hrefType) {
		this.hrefType = hrefType;
	}

	private int shareType;

	private int hrefType;
	
	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public TagDTO getTagDTO() {
		return tagDTO;
	}

	public void setTagDTO(TagDTO tagDTO) {
		this.tagDTO = tagDTO;
	}
	

}
