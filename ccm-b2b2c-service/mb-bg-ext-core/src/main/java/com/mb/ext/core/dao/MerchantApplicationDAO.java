package com.mb.ext.core.dao;

import java.util.List;

import com.mb.ext.core.entity.merchant.MerchantApplicationEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.service.spec.merchant.MerchantApplicationSearchDTO;
import com.mb.framework.exception.DAOException;



public interface MerchantApplicationDAO
{
	void createMerchantApplication(MerchantApplicationEntity merchantApplicationEntity) throws DAOException;
	
	void updateMerchantApplication(MerchantApplicationEntity merchantApplicationEntity) throws DAOException;
	
	void deleteMerchantApplication(MerchantApplicationEntity merchantApplicationEntity) throws DAOException;
	
	MerchantApplicationEntity getUpgradeApplicationByUuid(String uuid) throws DAOException;
	
	List<MerchantApplicationEntity> getMerchantApplications(String mobileNo) throws DAOException;
	/*
	 * 正在审核中的申请
	 */
	List<MerchantApplicationEntity> getOutstandingMerchantApplications(String mobileNo) throws DAOException;
	
	List<MerchantApplicationEntity> searchMerchantApplication(MerchantApplicationSearchDTO searchDTO) throws DAOException;
	
	int searchMerchantApplicationTotal(MerchantApplicationSearchDTO searchDTO) throws DAOException;
	
}
