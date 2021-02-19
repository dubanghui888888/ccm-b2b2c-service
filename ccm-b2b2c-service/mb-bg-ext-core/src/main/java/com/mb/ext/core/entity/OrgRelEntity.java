package com.mb.ext.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the org_rel database table.
 * 
 */
@Entity
@Table(name = "org_rel")
@NamedQuery(name = "OrgRelEntity.findAll", query = "SELECT u FROM OrgRelEntity u")
public class OrgRelEntity extends AbstractBaseEntity
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "ORGREL_UUID")
	@GenericGenerator(name = "ORGREL_UUID", strategy = "uuid")
	@Column(name = "ORGREL_UUID", nullable = false, length = 36)
	private String orgrelUuid;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_PARENT_UUID")
	private OrgEntity parentOrgEntity;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_CHILD_UUID")
	private OrgEntity childOrgEntity;

	public String getOrgrelUuid() {
		return orgrelUuid;
	}

	public void setOrgrelUuid(String orgrelUuid) {
		this.orgrelUuid = orgrelUuid;
	}

	public OrgEntity getParentOrgEntity() {
		return parentOrgEntity;
	}

	public void setParentOrgEntity(OrgEntity parentOrgEntity) {
		this.parentOrgEntity = parentOrgEntity;
	}

	public OrgEntity getChildOrgEntity() {
		return childOrgEntity;
	}

	public void setChildOrgEntity(OrgEntity childOrgEntity) {
		this.childOrgEntity = childOrgEntity;
	}
	
}