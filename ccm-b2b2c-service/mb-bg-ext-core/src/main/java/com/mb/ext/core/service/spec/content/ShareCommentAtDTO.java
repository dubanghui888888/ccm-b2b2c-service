
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
public class ShareCommentAtDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private String shareCommentAtUuid;
	private String comment;
	private Date commentTime;
	private ShareCommentDTO shareCommentDTO;
	private UserDTO userDTO;
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getShareCommentAtUuid() {
		return shareCommentAtUuid;
	}
	public void setShareCommentAtUuid(String shareCommentAtUuid) {
		this.shareCommentAtUuid = shareCommentAtUuid;
	}
	public ShareCommentDTO getShareCommentDTO() {
		return shareCommentDTO;
	}
	public void setShareCommentDTO(ShareCommentDTO shareCommentDTO) {
		this.shareCommentDTO = shareCommentDTO;
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
}
