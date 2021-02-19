package com.mb.ext.core.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the org database table.
 * 
 */
@Entity
@Table(name = "org")
@NamedQuery(name = "OrgEntity.findAll", query = "SELECT u FROM OrgEntity u")
public class OrgEntity extends AbstractBaseEntity
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "ORG_UUID")
	@GenericGenerator(name = "ORG_UUID", strategy = "uuid")
	@Column(name = "ORG_UUID", nullable = false, length = 36)
	private String orgUuid;

	@Column(name = "NAME", nullable = false, length = 60)
	private String name;
	
	@Column(name = "DESCRIPTION", length = 200)
	private String desc;
	

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<OrgRelEntity> getChildOrgRelList() {
		return childOrgRelList;
	}

	public void setChildOrgRelList(List<OrgRelEntity> childOrgRelList) {
		this.childOrgRelList = childOrgRelList;
	}

	public OrgRelEntity getParentOrgRelEntity() {
		return parentOrgRelEntity;
	}

	public void setParentOrgRelEntity(OrgRelEntity parentOrgRelEntity) {
		this.parentOrgRelEntity = parentOrgRelEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MANAGER_UUID")
	private UserEntity managerUserEntity;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "parentOrgEntity")
	private List<OrgRelEntity> childOrgRelList;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "childOrgEntity")
	private OrgRelEntity parentOrgRelEntity;
	
	public String getOrgUuid() {
		return orgUuid;
	}

	public void setOrgUuid(String orgUuid) {
		this.orgUuid = orgUuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserEntity getManagerUserEntity() {
		return managerUserEntity;
	}

	public void setManagerUserEntity(UserEntity managerUserEntity) {
		this.managerUserEntity = managerUserEntity;
	}
}