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
 * The persistent class for the profit_sale database table.
 * 
 */
@Entity
@Table(name = "profit_sale")
@NamedQuery(name = "ProfitSaleEntity.findAll", query = "SELECT u FROM ProfitSaleEntity u")
public class ProfitSaleEntity extends AbstractBaseEntity
{
	public String getProfitSaleUuid() {
		return profitSaleUuid;
	}

	public void setProfitSaleUuid(String profitSaleUuid) {
		this.profitSaleUuid = profitSaleUuid;
	}


	public UserLevelEntity getProfitUserLevelEntity() {
		return profitUserLevelEntity;
	}

	public void setProfitUserLevelEntity(UserLevelEntity profitUserLevelEntity) {
		this.profitUserLevelEntity = profitUserLevelEntity;
	}

	private static final long serialVersionUID = 1L;

	public BigDecimal getProfitRate() {
		return profitRate;
	}

	public void setProfitRate(BigDecimal profitRate) {
		this.profitRate = profitRate;
	}

	@Id
	@GeneratedValue(generator = "PROFIT_SALE_UUID")
	@GenericGenerator(name = "PROFIT_SALE_UUID", strategy = "uuid")
	@Column(name = "PROFIT_SALE_UUID", nullable = false, length = 36)
	private String profitSaleUuid;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROFIT_USER_LEVEL_UUID")
	private UserLevelEntity profitUserLevelEntity;
	
	@Column(name = "PROFIT_RATE")
	private BigDecimal profitRate;
	
}