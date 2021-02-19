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
 * The persistent class for the scene database table.
 * 
 */
@Entity
@Table(name = "scene")
@NamedQuery(name = "SceneEntity.findAll", query = "SELECT u FROM SceneEntity u")
public class SceneEntity extends AbstractBaseEntity
{
	public String getSceneUuid() {
		return sceneUuid;
	}

	public void setSceneUuid(String sceneUuid) {
		this.sceneUuid = sceneUuid;
	}

	public TagEntity getTagEntity() {
		return tagEntity;
	}

	public void setTagEntity(TagEntity tagEntity) {
		this.tagEntity = tagEntity;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "SCENE_UUID")
	@GenericGenerator(name = "SCENE_UUID", strategy = "uuid")
	@Column(name = "SCENE_UUID", nullable = false, length = 36)
	private String sceneUuid;

	@Column(name = "TITLE")
	private String title;
	
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

	@Column(name = "CONTENT")
	private String content;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TAG_UUID")
	private TagEntity tagEntity;
}