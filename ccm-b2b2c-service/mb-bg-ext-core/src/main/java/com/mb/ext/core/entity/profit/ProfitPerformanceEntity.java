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
 * The persistent class for the profit_performance database table.
 * 
 */
@Entity
@Table(name = "profit_performance")
@NamedQuery(name = "ProfitPerformanceEntity.findAll", query = "SELECT u FROM ProfitPerformanceEntity u")
public class ProfitPerformanceEntity extends AbstractBaseEntity
{
	public String getProfitPerformanceUuid() {
		return profitPerformanceUuid;
	}

	public void setProfitPerformanceUuid(String profitPerformanceUuid) {
		this.profitPerformanceUuid = profitPerformanceUuid;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "PROFIT_PERFORMANCE_UUID")
	@GenericGenerator(name = "PROFIT_PERFORMANCE_UUID", strategy = "uuid")
	@Column(name = "PROFIT_PERFORMANCE_UUID", nullable = false, length = 36)
	private String profitPerformanceUuid;

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