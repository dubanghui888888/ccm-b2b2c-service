package com.mb.ext.core.entity.content;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the poster database table.
 * 
 */
@Entity
@Table(name = "poster")
@NamedQuery(name = "PosterEntity.findAll", query = "SELECT u FROM PosterEntity u")
public class PosterEntity extends AbstractBaseEntity
{
	public String getPosterUuid() {
		return posterUuid;
	}

	public void setPosterUuid(String posterUuid) {
		this.posterUuid = posterUuid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public TagEntity getTagEntity() {
		return tagEntity;
	}

	public void setTagEntity(TagEntity tagEntity) {
		this.tagEntity = tagEntity;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "POSTER_UUID")
	@GenericGenerator(name = "POSTER_UUID", strategy = "uuid")
	@Column(name = "POSTER_UUID", nullable = false, length = 36)
	private String posterUuid;

	@Column(name = "URL")
	private String url;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TAG_UUID")
	private TagEntity tagEntity;
}