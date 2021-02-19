package com.mb.ext.core.dao.promote;

import java.util.List;

import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.promote.PromoteMoneyOffEntity;
import com.mb.framework.exception.DAOException;



public interface PromoteMoneyOffDAO
{
	void createPromoteMoneyOff(PromoteMoneyOffEntity promoteMoneyOffEntity) throws DAOException;
	
	void updatePromoteMoneyOff(PromoteMoneyOffEntity promoteMoneyOffEntity) throws DAOException;
	
	void deletePromoteMoneyOff(PromoteMoneyOffEntity promoteMoneyOffEntity) throws DAOException;
	
	List<PromoteMoneyOffEntity> getPromoteMoneyOffByMerchant(MerchantEntity merchantEntity) throws DAOException;
	
	PromoteMoneyOffEntity getPromoteMoneyOffByName(MerchantEntity merchantEntity, String name) throws DAOException;
	
	PromoteMoneyOffEntity getPromoteMoneyOffByUuid(String promoteMoneyOffUuid) throws DAOException;
	
}
