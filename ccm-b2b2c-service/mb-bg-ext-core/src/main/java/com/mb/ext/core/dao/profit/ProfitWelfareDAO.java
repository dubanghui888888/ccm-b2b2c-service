package com.mb.ext.core.dao.profit;

import com.mb.ext.core.entity.profit.ProfitWelfareEntity;
import com.mb.framework.exception.DAOException;

import java.util.List;


public interface ProfitWelfareDAO
{
	
	void addProfitWelfare(ProfitWelfareEntity profitWelfareEntity) throws DAOException;
	
	void updateProfitWelfare(ProfitWelfareEntity profitWelfareEntity) throws DAOException;
	
	void deleteProfitWelfare(ProfitWelfareEntity profitWelfareEntity) throws DAOException;

	void deleteAllProfitWelfare() throws DAOException;
	
	public List<ProfitWelfareEntity> getProfitWelfareByType(String type) throws DAOException;
	
	public ProfitWelfareEntity getProfitWelfareByUuid(String uuid) throws DAOException;
	
	public List<ProfitWelfareEntity> getProfitWelfares() throws DAOException;
	
}
