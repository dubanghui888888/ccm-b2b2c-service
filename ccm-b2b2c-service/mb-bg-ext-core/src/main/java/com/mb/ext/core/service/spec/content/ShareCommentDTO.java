
package com.mb.ext.core.service.spec.content;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.util.JsonDateTimeDeserializer;
import com.mb.ext.core.util.JsonDateTimeSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class ShareCommentDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private String shareCommentUuid;
	private String comment;
	private Date commentTime;
	private ShareDTO shareDTO;
	private UserDTO userDTO;
	private List<ShareCommentAtDTO> ats = new ArrayList<ShareCommentAtDTO>();
	public String getShareCommentUuid() {
		return shareCommentUuid;
	}
	public List<ShareCommentAtDTO> getAts() {
		return ats;
	}
	public void setAts(List<ShareCommentAtDTO> ats) {
		this.ats = ats;
	}
	public void setShareCommentUuid(String shareCommentUuid) {
		this.shareCommentUuid = shareCommentUuid;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getCommentTime() {
		return commentTime;
	}
	@JsonDeserialize(using=JsonDateTimeDeserializer.class)
	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
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
