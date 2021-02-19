package com.mb.ext.core.dao.profit;

import java.util.List;

import com.mb.ext.core.entity.UserLevelEntity;
import com.mb.ext.core.entity.profit.ProfitTrainerEntity;
import com.mb.framework.exception.DAOException;


public interface ProfitTrainerDAO
{
	/**
	 * 
	 * This method is used to add user to enterprise.
	 * 
	 * @param profitTrainerEntity
	 */
	void addProfitTrainer(ProfitTrainerEntity profitTrainerEntity) throws DAOException;
	
	void updateProfitTrainer(ProfitTrainerEntity profitTrainerEntity) throws DAOException;
	
	void deleteProfitTrainer(ProfitTrainerEntity profitTrainerEntity) throws DAOException;
	
	public List<ProfitTrainerEntity> getProfitTrainer() throws DAOException;
	
	public List<ProfitTrainerEntity> getProfitTrainerByUserLevel(UserLevelEntity userLevel) throws DAOException;
	
}
