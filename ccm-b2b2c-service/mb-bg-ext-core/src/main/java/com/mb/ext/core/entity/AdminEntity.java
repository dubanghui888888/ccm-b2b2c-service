package com.mb.ext.core.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the admin database table.
 * 
 */
@Entity
@Table(name = "admin")
@NamedQuery(name = "AdminEntity.findAll", query = "SELECT u FROM AdminEntity u")
public class AdminEntity extends AbstractBaseEntity
{



	private static final long serialVersionUID = 1L;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	public int getFailCount() {
		return failCount;
	}

	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	public void setAdminTokenEntity(AdminTokenEntity adminTokenEntity) {
		this.adminTokenEntity = adminTokenEntity;
	}

	public String getAdminUuid() {
		return adminUuid;
	}

	public void setAdminUuid(String adminUuid) {
		this.adminUuid = adminUuid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	@Id
	@GeneratedValue(generator = "ADMIN_UUID")
	@GenericGenerator(name = "ADMIN_UUID", strategy = "uuid")
	@Column(name = "ADMIN_UUID", nullable = false, length = 36)
	private String adminUuid;

	@Column(name = "EMAIL", length = 40)
	private String email;
	
	@Column(name = "MOBILENO", length = 20)
	private String mobileNo;
	
	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}



	@Column(name = "ID",length = 8)
	private String id;
	
	@Column(name = "PASSWORD",length = 100)
	private String password;
	
	@Column(name = "LASTLOGINTIME")
	private Date lastLoginTime;
	
	@Column(name = "SUCCESSCOUNT")
	private int successCount;
	
	@Column(name = "FAILCOUNT")
	private int failCount;
	
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "ISACTIVE")
	private boolean isActive;
	
	@Column(name = "ISLOCKED")
	private boolean isLocked;
	

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "adminEntity")
	private AdminTokenEntity adminTokenEntity;
	

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "adminEntity")
	private List<AdminRoleEntity> adminRoleList;
	

	public List<AdminRoleEntity> getAdminRoleList() {
		return adminRoleList;
	}

	public void setAdminRoleList(List<AdminRoleEntity> adminRoleList) {
		this.adminRoleList = adminRoleList;
	}


	public AdminTokenEntity getAdminTokenEntity() {
		return adminTokenEntity;
	}

	public void setUseTokenEntity(AdminTokenEntity adminTokenEntity) {
		this.adminTokenEntity = adminTokenEntity;
	}

	
}