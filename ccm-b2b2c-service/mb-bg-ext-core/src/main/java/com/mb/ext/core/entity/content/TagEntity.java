package com.mb.ext.core.entity.content;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the tag database table.
 * 
 */
@Entity
@Table(name = "tag")
@NamedQuery(name = "TagEntity.findAll", query = "SELECT u FROM TagEntity u")
public class TagEntity extends AbstractBaseEntity
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "TAG_UUID")
	@GenericGenerator(name = "TAG_UUID", strategy = "uuid")
	@Column(name = "TAG_UUID", nullable = false, length = 36)
	private String tagUuid;

	@Column(name = "TAG_TYPE")
	private String tagType;

	public String getTagType() {
		return tagType;
	}

	public void setTagType(String tagType) {
		this.tagType = tagType;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	@Column(name = "TAG_NAME")
	private String tagName;

	public String getTagUuid() {
		return tagUuid;
	}

	public void setTagUuid(String tagUuid) {
		this.tagUuid = tagUuid;
	}
}