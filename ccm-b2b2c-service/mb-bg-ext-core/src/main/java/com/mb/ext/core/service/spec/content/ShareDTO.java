
package com.mb.ext.core.service.spec.content;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.util.JsonDateSerializer;
import com.mb.ext.core.util.JsonDateTimeDeserializer;
import com.mb.ext.core.util.JsonDateTimeSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class ShareDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private String shareUuid;
	
	int startIndex;
	
	private Date searchDate;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getSearchDate() {
		return searchDate;
	}
	@JsonSerialize(using=JsonDateSerializer.class) 
	public void setSearchDate(Date searchDate) {
		this.searchDate = searchDate;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	int pageSize;
	
	public String getShareUuid() {
		return shareUuid;
	}

	public void setShareUuid(String shareUuid) {
		this.shareUuid = shareUuid;
	}

	private TagDTO tagDTO;
	
	public UserDTO getUserDTO() {
		return userDTO;
	}
	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	private UserDTO userDTO;


	public TagDTO getTagDTO() {
		return tagDTO;
	}

	public void setTagDTO(TagDTO tagDTO) {
		this.tagDTO = tagDTO;
	}

	private String content;
	
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}

	private int likes;
	
	private Date publishTime;
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getUpdateDate() {
		return updateDate;
	}
	
	@JsonDeserialize(using=JsonDateTimeDeserializer.class) 
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	private Date updateDate;
	
	private Date createDate;
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getCreateDate() {
		return createDate;
	}
	@JsonDeserialize(using=JsonDateTimeDeserializer.class)
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	private boolean isPublished;
	
	private List<ShareImageDTO> images;
	
	private List<ShareCommentDTO> comments;
	
	public List<ShareLikeDTO> getShareLikes() {
		return shareLikes;
	}
	public void setShareLikes(List<ShareLikeDTO> shareLikes) {
		this.shareLikes = shareLikes;
	}

	private List<ShareLikeDTO> shareLikes;
	
	public List<ShareCommentDTO> getComments() {
		return comments;
	}
	public void setComments(List<ShareCommentDTO> comments) {
		this.comments = comments;
	}
	public List<ShareImageDTO> getImages() {
		return images;
	}

	public void setImages(List<ShareImageDTO> images) {
		this.images = images;
	}

	public boolean isPublished() {
		return isPublished;
	}

	public void setPublished(boolean isPublished) {
		this.isPublished = isPublished;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JsonSerialize(using=JsonDateTimeSerializer.class)
	public Date getPublishTime() {
		return publishTime;
	}
	@JsonDeserialize(using=JsonDateTimeDeserializer.class)
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

}
