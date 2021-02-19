package com.mb.ext.core.entity;

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
 * The persistent class for the trainer database table.
 * 
 */
@Entity
@Table(name = "trainer")
@NamedQuery(name = "TrainerEntity.findAll", query = "SELECT u FROM TrainerEntity u")
public class TrainerEntity extends AbstractBaseEntity
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "TRAINER_UUID")
	@GenericGenerator(name = "TRAINER_UUID", strategy = "uuid")
	@Column(name = "TRAINER_UUID", nullable = false, length = 36)
	private String trainerUuid;

	
	@Column(name = "TRAINER_LEVEL")
	private String trainerLevel;
	
	@Column(name = "EFFECTIVE_DATE")
	private Date effectiveDate;

	public String getTrainerUuid() {
		return trainerUuid;
	}

	public void setTrainerUuid(String trainerUuid) {
		this.trainerUuid = trainerUuid;
	}

	public String getTrainerLevel() {
		return trainerLevel;
	}

	public void setTrainerLevel(String trainerLevel) {
		this.trainerLevel = trainerLevel;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public TrainerEntity getParentTrainerEntity() {
		return parentTrainerEntity;
	}

	public void setParentTrainerEntity(TrainerEntity parentTrainerEntity) {
		this.parentTrainerEntity = parentTrainerEntity;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_UUID")
	private UserEntity userEntity;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_TRAINER_UUID")
	private TrainerEntity parentTrainerEntity;

	
}