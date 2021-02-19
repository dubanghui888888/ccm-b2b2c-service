package com.mb.ext.core.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the user_performance database table.
 * 
 */
@Entity
@Table(name = "user_performance")
@NamedQuery(name = "UserPerformanceEntity.findAll", query = "SELECT u FROM UserPerformanceEntity u")
public class UserPerformanceEntity extends AbstractBaseEntity
{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "USER_PERFORMANCE_UUID")
	@GenericGenerator(name = "USER_PERFORMANCE_UUID", strategy = "uuid")
	@Column(name = "USER_PERFORMANCE_UUID", nullable = false, length = 36)
	private String userPerformanceUuid;

	
	public Date getPerformanceDate() {
		return performanceDate;
	}

	public void setPerformanceDate(Date performanceDate) {
		this.performanceDate = performanceDate;
	}

	public BigDecimal getPerformanceAward() {
		return performanceAward;
	}

	public void setPerformanceAward(BigDecimal performanceAward) {
		this.performanceAward = performanceAward;
	}

	public String getUserPerformanceUuid() {
		return userPerformanceUuid;
	}

	public void setUserPerformanceUuid(String userPerformanceUuid) {
		this.userPerformanceUuid = userPerformanceUuid;
	}


	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}


	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USER_UUID")
	private UserEntity userEntity;
	
	
	@Column(name = "PERFORMANCE_DATE")
	private Date performanceDate;
	

	public BigDecimal getPerformanceAmount() {
		return performanceAmount;
	}

	public void setPerformanceAmount(BigDecimal performanceAmount) {
		this.performanceAmount = performanceAmount;
	}

	//业绩总额
	@Column(name = "PERFORMANCE_AMOUNT")
	private BigDecimal performanceAmount;
	
	//业绩奖金
	@Column(name = "PERFORMANCE_AWARD")
	private BigDecimal performanceAward;
	
}