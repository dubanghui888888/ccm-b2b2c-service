
package com.mb.ext.core.service.spec.content;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.util.JsonDateMinuteDeserializer;
import com.mb.ext.core.util.JsonDateMinuteSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class SceneDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private String sceneUuid;
	

	public String getSceneUuid() {
		return sceneUuid;
	}

	public void setSceneUuid(String sceneUuid) {
		this.sceneUuid = sceneUuid;
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

	private String title;
	
	private String content;
	
	private TagDTO tagDTO;

	public TagDTO getTagDTO() {
		return tagDTO;
	}

	public void setTagDTO(TagDTO tagDTO) {
		this.tagDTO = tagDTO;
	}
	
	private Date updateDate;

	@JsonSerialize(using=JsonDateMinuteSerializer.class)
	public Date getUpdateDate() {
		return updateDate;
	}
	@JsonDeserialize(using=JsonDateMinuteDeserializer.class)
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
