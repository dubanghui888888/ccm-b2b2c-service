package com.mb.ext.core.entity;

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
 * The persistent class for the permission database table.
 * 
 */
@Entity
@Table(name = "permission")
@NamedQuery(name = "PermissionEntity.findAll", query = "SELECT u FROM PermissionEntity u")
public class PermissionEntity extends AbstractBaseEntity
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "PERMISSION_UUID")
	@GenericGenerator(name = "PERMISSION_UUID", strategy = "uuid")
	@Column(name = "PERMISSION_UUID", nullable = false, length = 36)
	private String permissionUuid;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_UUID")
	private RoleEntity roleEntity;
	
	public String getPermissionUuid() {
		return permissionUuid;
	}

	public void setPermissionUuid(String permissionUuid) {
		this.permissionUuid = permissionUuid;
	}

	public RoleEntity getRoleEntity() {
		return roleEntity;
	}

	public void setRoleEntity(RoleEntity roleEntity) {
		this.roleEntity = roleEntity;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FUNCTION_UUID")
	private FunctionEntity functionEntity;


	public FunctionEntity getFunctionEntity() {
		return functionEntity;
	}

	public void setFunctionEntity(FunctionEntity functionEntity) {
		this.functionEntity = functionEntity;
	}

	
}