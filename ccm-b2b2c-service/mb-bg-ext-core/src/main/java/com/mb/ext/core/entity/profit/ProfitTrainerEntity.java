package com.mb.ext.core.entity.profit;

import java.math.BigDecimal;

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

import com.mb.ext.core.entity.UserLevelEntity;
import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the profit_trainer database table.
 * 
 */
@Entity
@Table(name = "profit_trainer")
@NamedQuery(name = "ProfitTrainerEntity.findAll", query = "SELECT u FROM ProfitTrainerEntity u")
public class ProfitTrainerEntity extends AbstractBaseEntity
{
	public String getProfitTrainerUuid() {
		return profitTrainerUuid;
	}

	public void setProfitTrainerUuid(String profitTrainerUuid) {
		this.profitTrainerUuid = profitTrainerUuid;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "PROFIT_TRAINER_UUID")
	@GenericGenerator(name = "PROFIT_TRAINER_UUID", strategy = "uuid")
	@Column(name = "PROFIT_TRAINER_UUID", nullable = false, length = 36)
	private String profitTrainerUuid;

	@Column(name = "AMOUNT")
	private BigDecimal amount;
	
	@Column(name = "RATING")
	private BigDecimal rating;
	
	public UserLevelEntity getProfitUserLevelEntity() {
		return profitUserLevelEntity;
	}

	public void setProfitUserLevelEntity(UserLevelEntity profitUserLevelEntity) {
		this.profitUserLevelEntity = profitUserLevelEntity;
	}


	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROFIT_USER_LEVEL_UUID")
	private UserLevelEntity profitUserLevelEntity;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getRating() {
		return rating;
	}

	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}


	
}