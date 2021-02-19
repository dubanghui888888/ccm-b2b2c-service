
package com.mb.ext.core.service.spec.content;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class QaDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private String qaUuid;
	
	int startIndex;
	
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
	
	public String getQaUuid() {
		return qaUuid;
	}

	public void setQaUuid(String qaUuid) {
		this.qaUuid = qaUuid;
	}

	private TagDTO tagDTO;


	public TagDTO getTagDTO() {
		return tagDTO;
	}

	public void setTagDTO(TagDTO tagDTO) {
		this.tagDTO = tagDTO;
	}
	
	private String title;
	
	private String digest;
	
	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	private String content;
	
	private String coverImageUrl;
	
	private String mediaId;
	
	private String previewUrl;
	
	private Date publishTime;
	
	private boolean isPublished;
	
	private boolean isHot;
	
	public boolean isHot() {
		return isHot;
	}

	public void setHot(boolean isHot) {
		this.isHot = isHot;
	}

	public boolean isPublished() {
		return isPublished;
	}

	public void setPublished(boolean isPublished) {
		this.isPublished = isPublished;
	}

	private int numberOfReading;
	
	public int getNumberOfReading() {
		return numberOfReading;
	}

	public void setNumberOfReading(int numberOfReading) {
		this.numberOfReading = numberOfReading;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCoverImageUrl() {
		return coverImageUrl;
	}

	public void setCoverImageUrl(String coverImageUrl) {
		this.coverImageUrl = coverImageUrl;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getPreviewUrl() {
		return previewUrl;
	}

	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public Date getPublishTime() {
		return publishTime;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public boolean isFromOfficialAccount() {
		return isFromOfficialAccount;
	}

	public void setFromOfficialAccount(boolean isFromOfficialAccount) {
		this.isFromOfficialAccount = isFromOfficialAccount;
	}

	private boolean isFromOfficialAccount;
	

}
