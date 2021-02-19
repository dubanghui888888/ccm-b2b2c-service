package com.mb.ext.core.dao;

import java.util.Date;
import java.util.List;

import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.service.spec.ChartDTO;
import com.mb.ext.core.service.spec.MerchantSearchDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.framework.exception.DAOException;


public interface MerchantDAO
{
	/**
	 * 
	 * This method is used to add merchant to enterprise.
	 * 
	 * @param merchantEntity
	 */
	void addMerchant(MerchantEntity merchantEntity) throws DAOException;
	
	void updateMerchant(MerchantEntity merchantEntity) throws DAOException;
	
	void deleteMerchant(MerchantEntity merchantEntity) throws DAOException;
	
	void closeMerchant(MerchantEntity merchantEntity) throws DAOException;
	
	public MerchantEntity getMerchantByMobileNo(String mobileNo) throws DAOException;
	
	public MerchantEntity getMerchantByUuid(String uuid) throws DAOException;
	
	public List<MerchantEntity> getMerchants() throws DAOException;
	
	public MerchantEntity getMerchantByTokenId(String tokenId) throws DAOException;
	
	List<MerchantEntity> searchMerchants(MerchantSearchDTO merchantSearchDTO) throws DAOException;
	
	int searchMerchantTotal(MerchantSearchDTO merchantSearchDTO) throws DAOException;
	
	public int getIncrementMerchantCountByDate(Date startDate, Date endDate) throws DAOException;
	
	List<ChartDTO> getIncrementMerchantChart(Date startDate, Date endDate) throws DAOException;
	
	List<MerchantDTO> getDistance(MerchantDTO merchantDTO) throws DAOException;
	
	List<Object> searchMerchantRanking(MerchantSearchDTO merchantSearchDTO) throws DAOException;
	
	public void executeInsertUpdateNativeSQL(String sql) throws DAOException;
}
