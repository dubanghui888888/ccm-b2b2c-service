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
 * The persistent class for the profit_recruit database table.
 * 
 */
@Entity
@Table(name = "profit_recruit")
@NamedQuery(name = "ProfitRecruitEntity.findAll", query = "SELECT u FROM ProfitRecruitEntity u")
public class ProfitRecruitEntity extends AbstractBaseEntity
{
	public String getProfitRecruitUuid() {
		return profitRecruitUuid;
	}

	public void setProfitRecruitUuid(String profitRecruitUuid) {
		this.profitRecruitUuid = profitRecruitUuid;
	}


	public UserLevelEntity getRecruitUserLevelEntity() {
		return recruitUserLevelEntity;
	}

	public void setRecruitUserLevelEntity(UserLevelEntity recruitUserLevelEntity) {
		this.recruitUserLevelEntity = recruitUserLevelEntity;
	}

	public UserLevelEntity getProfitUserLevelEntity() {
		return profitUserLevelEntity;
	}

	public void setProfitUserLevelEntity(UserLevelEntity profitUserLevelEntity) {
		this.profitUserLevelEntity = profitUserLevelEntity;
	}

	public BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "PROFIT_RECRUIT_UUID")
	@GenericGenerator(name = "PROFIT_RECRUIT_UUID", strategy = "uuid")
	@Column(name = "PROFIT_RECRUIT_UUID", nullable = false, length = 36)
	private String profitRecruitUuid;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RECRUIT_USER_LEVEL_UUID")
	private UserLevelEntity recruitUserLevelEntity;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROFIT_USER_LEVEL_UUID")
	private UserLevelEntity profitUserLevelEntity;
	
	@Column(name = "PROFIT")
	private BigDecimal profit;
	
}