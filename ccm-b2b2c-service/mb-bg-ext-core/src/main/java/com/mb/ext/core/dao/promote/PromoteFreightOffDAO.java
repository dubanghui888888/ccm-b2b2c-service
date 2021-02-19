package com.mb.ext.core.dao.promote;

import java.util.List;

import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.promote.PromoteFreightOffEntity;
import com.mb.framework.exception.DAOException;



public interface PromoteFreightOffDAO
{
	void createPromoteFreightOff(PromoteFreightOffEntity promoteFreightOffEntity) throws DAOException;
	
	void updatePromoteFreightOff(PromoteFreightOffEntity promoteFreightOffEntity) throws DAOException;
	
	void deletePromoteFreightOff(PromoteFreightOffEntity promoteFreightOffEntity) throws DAOException;
	
	List<PromoteFreightOffEntity> getPromoteFreightOffByMerchant(MerchantEntity merchantEntity) throws DAOException;
	
	PromoteFreightOffEntity getPromoteFreightOffByName(MerchantEntity merchantEntity, String name) throws DAOException;
	
	PromoteFreightOffEntity getPromoteFreightOffByUuid(String promoteFreightOffUuid) throws DAOException;
	
}
