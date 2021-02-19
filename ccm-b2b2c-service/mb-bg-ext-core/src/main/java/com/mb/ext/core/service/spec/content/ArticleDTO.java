
package com.mb.ext.core.service.spec.content;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.util.JsonDateMinuteDeserializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class ArticleDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private String articleUuid;
	
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
	
	public String getArticleUuid() {
		return articleUuid;
	}

	public void setArticleUuid(String articleUuid) {
		this.articleUuid = articleUuid;
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
	
	private String linkType;
	
	private Date publishTime;
	
	private boolean isPublished;
	
	private String articleType;
	
	private String[] articleTypeArray;
	
	public String[] getArticleTypeArray() {
		return articleTypeArray;
	}

	public void setArticleTypeArray(String[] articleTypeArray) {
		this.articleTypeArray = articleTypeArray;
	}

	public String getArticleType() {
		return articleType;
	}

	public void setArticleType(String articleType) {
		this.articleType = articleType;
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
	
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	public Date getPublishTime() {
		return publishTime;
	}
	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
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
	
	private int articleCountImage;
	
	public int getArticleCountImage() {
		return articleCountImage;
	}

	public void setArticleCountImage(int articleCountImage) {
		this.articleCountImage = articleCountImage;
	}

	public int getArticleCountAudio() {
		return articleCountAudio;
	}

	public void setArticleCountAudio(int articleCountAudio) {
		this.articleCountAudio = articleCountAudio;
	}

	public int getArticleCountVideo() {
		return articleCountVideo;
	}

	public void setArticleCountVideo(int articleCountVideo) {
		this.articleCountVideo = articleCountVideo;
	}

	private int articleCountAudio;
	
	private int articleCountVideo;
	

}
