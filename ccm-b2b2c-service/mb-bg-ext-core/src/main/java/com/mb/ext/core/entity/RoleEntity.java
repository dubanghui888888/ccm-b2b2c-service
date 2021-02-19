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
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the role database table.
 * 
 */
@Entity
@Table(name = "role")
@NamedQuery(name = "RoleEntity.findAll", query = "SELECT u FROM RoleEntity u")
public class RoleEntity extends AbstractBaseEntity
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "ROLE_UUID")
	@GenericGenerator(name = "ROLE_UUID", strategy = "uuid")
	@Column(name = "ROLE_UUID", nullable = false, length = 36)
	private String roleUuid;

	@Column(name = "ROLE_NAME", nullable = false, length = 20)
	private String name;
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Column(name = "ROLE_DESC", length = 200)
	private String desc;
	
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public List<PermissionEntity> getPermissionEntityList() {
		return permissionEntityList;
	}

	public void setPermissionEntityList(List<PermissionEntity> permissionEntityList) {
		this.permissionEntityList = permissionEntityList;
	}

	public List<AdminRoleEntity> getUserRoleEntityList() {
		return userRoleEntityList;
	}

	public void setUserRoleEntityList(List<AdminRoleEntity> userRoleEntityList) {
		this.userRoleEntityList = userRoleEntityList;
	}

	@Column(name = "ISACTIVE")
	private boolean isActive;


	public String getRoleUuid() {
		return roleUuid;
	}

	public void setRoleUuid(String roleUuid) {
		this.roleUuid = roleUuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "roleEntity")
	private List<PermissionEntity> permissionEntityList;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "roleEntity")
	private List<AdminRoleEntity> userRoleEntityList;

}