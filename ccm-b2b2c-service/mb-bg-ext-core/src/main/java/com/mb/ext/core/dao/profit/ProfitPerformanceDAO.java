package com.mb.ext.core.dao.profit;

import java.util.List;

import com.mb.ext.core.entity.UserLevelEntity;
import com.mb.ext.core.entity.profit.ProfitPerformanceEntity;
import com.mb.framework.exception.DAOException;


public interface ProfitPerformanceDAO
{
	/**
	 * 
	 * This method is used to add user to enterprise.
	 * 
	 * @param profitPerformanceEntity
	 */
	void addProfitPerformance(ProfitPerformanceEntity profitPerformanceEntity) throws DAOException;
	
	void updateProfitPerformance(ProfitPerformanceEntity profitPerformanceEntity) throws DAOException;
	
	void deleteProfitPerformance(ProfitPerformanceEntity profitPerformanceEntity) throws DAOException;
	
	public List<ProfitPerformanceEntity> getProfitPerformance() throws DAOException;
	
	public List<ProfitPerformanceEntity> getProfitPerformanceByUserLevel(UserLevelEntity userLevel) throws DAOException;
	
}
