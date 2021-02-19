
package com.mb.ext.core.service.spec.content;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.util.JsonDateTimeDeserializer;
import com.mb.ext.core.util.JsonDateTimeSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class ShareLikeDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private String shareLikeUuid;
	private Date likeTime;
	private ShareDTO shareDTO;
	private UserDTO userDTO;
	public String getShareLikeUuid() {
		return shareLikeUuid;
	}
	public void setShareLikeUuid(String shareLikeUuid) {
		this.shareLikeUuid = shareLikeUuid;
	}
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getLikeTime() {
		return likeTime;
	}
	@JsonDeserialize(using=JsonDateTimeDeserializer.class)
	public void setLikeTime(Date likeTime) {
		this.likeTime = likeTime;
	}
	public UserDTO getUserDTO() {
		return userDTO;
	}
	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
	public ShareDTO getShareDTO() {
		return shareDTO;
	}
	public void setShareDTO(ShareDTO shareDTO) {
		this.shareDTO = shareDTO;
	}

}
