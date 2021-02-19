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
 * The persistent class for the admin_role database table.
 * 
 */
@Entity
@Table(name = "admin_role")
@NamedQuery(name = "AdminRoleEntity.findAll", query = "SELECT u FROM AdminRoleEntity u")
public class AdminRoleEntity extends AbstractBaseEntity
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "ADMINROLE_UUID")
	@GenericGenerator(name = "ADMINROLE_UUID", strategy = "uuid")
	@Column(name = "ADMINROLE_UUID", nullable = false, length = 36)
	private String adminRoleUuid;

	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ADMIN_UUID")
	private AdminEntity adminEntity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_UUID")
	private RoleEntity roleEntity;
	

	public String getAdminRoleUuid() {
		return adminRoleUuid;
	}

	public void setAdminRoleUuid(String adminRoleUuid) {
		this.adminRoleUuid = adminRoleUuid;
	}

	public RoleEntity getRoleEntity() {
		return roleEntity;
	}

	public void setRoleEntity(RoleEntity roleEntity) {
		this.roleEntity = roleEntity;
	}

	public AdminEntity getAdminEntity() {
		return adminEntity;
	}

	public void setAdminEntity(AdminEntity adminEntity) {
		this.adminEntity = adminEntity;
	}

}