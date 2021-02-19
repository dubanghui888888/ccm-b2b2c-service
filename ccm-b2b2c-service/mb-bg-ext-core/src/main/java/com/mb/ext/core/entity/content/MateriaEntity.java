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
 * The persistent class for the materia database table.
 * 
 */
@Entity
@Table(name = "materia")
@NamedQuery(name = "MateriaEntity.findAll", query = "SELECT u FROM MateriaEntity u")
public class MateriaEntity extends AbstractBaseEntity
{
	public String getMateriaUuid() {
		return materiaUuid;
	}

	public void setMateriaUuid(String materiaUuid) {
		this.materiaUuid = materiaUuid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "MATERIA_UUID")
	@GenericGenerator(name = "MATERIA_UUID", strategy = "uuid")
	@Column(name = "MATERIA_UUID", nullable = false, length = 36)
	private String materiaUuid;

	@Column(name = "URL")
	private String url;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "TYPE")
	private String type;
	
}