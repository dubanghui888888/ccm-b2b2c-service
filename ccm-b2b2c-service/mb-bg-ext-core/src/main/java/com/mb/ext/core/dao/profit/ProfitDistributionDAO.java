package com.mb.ext.core.dao.profit;

import com.mb.ext.core.entity.profit.ProfitDistributionEntity;
import com.mb.framework.exception.DAOException;


public interface ProfitDistributionDAO
{
	
	void addProfitDistribution(ProfitDistributionEntity profitDistributionEntity) throws DAOException;
	
	void updateProfitDistribution(ProfitDistributionEntity profitDistributionEntity) throws DAOException;
	
	public ProfitDistributionEntity getProfitDistribution() throws DAOException;
	
}
