package com.mb.ext.core.dao.profit;

import java.util.List;

import com.mb.ext.core.entity.profit.ProfitRecruitEntity;
import com.mb.framework.exception.DAOException;


public interface ProfitRecruitDAO
{
	
	void addProfitRecruit(ProfitRecruitEntity profitRecruitEntity) throws DAOException;
	
	void updateProfitRecruit(ProfitRecruitEntity profitRecruitEntity) throws DAOException;
	
	void deleteProfitRecruit(ProfitRecruitEntity profitRecruitEntity) throws DAOException;
	
	public ProfitRecruitEntity getProfitRecruitByName(String name) throws DAOException;
	
	public ProfitRecruitEntity getProfitRecruitByUuid(String uuid) throws DAOException;
	
	public ProfitRecruitEntity getProfitRecruit(String recruitUserLevelUuid, String profitUserLevelUuid) throws DAOException;
	
	public List<ProfitRecruitEntity> getProfitRecruits() throws DAOException;
	
}
