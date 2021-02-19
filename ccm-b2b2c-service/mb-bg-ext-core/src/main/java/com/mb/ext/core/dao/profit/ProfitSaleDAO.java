package com.mb.ext.core.dao.profit;

import java.util.List;

import com.mb.ext.core.entity.profit.ProfitSaleEntity;
import com.mb.framework.exception.DAOException;


public interface ProfitSaleDAO
{
	
	void addProfitSale(ProfitSaleEntity profitSaleEntity) throws DAOException;
	
	void updateProfitSale(ProfitSaleEntity profitSaleEntity) throws DAOException;
	
	void deleteProfitSale(ProfitSaleEntity profitSaleEntity) throws DAOException;
	
	public ProfitSaleEntity getProfitSaleByUuid(String uuid) throws DAOException;
	
	public ProfitSaleEntity getProfitSaleByUserLevel(String userLevelUuid) throws DAOException;
	
	public List<ProfitSaleEntity> getProfitSales() throws DAOException;
	
}
