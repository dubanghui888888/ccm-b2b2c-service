package com.mb.ext.core.dao.merchant;

import java.math.BigDecimal;
import java.util.List;

import com.mb.ext.core.entity.merchant.MerchantBalanceEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.framework.exception.DAOException;



public interface MerchantBalanceDAO
{
	void createMerchantBalance(MerchantBalanceEntity merchantBalanceEntity) throws DAOException;
	
	void updateMerchantBalance(MerchantBalanceEntity merchantBalanceEntity) throws DAOException;
	
	void deleteMerchantBalance(MerchantBalanceEntity merchantBalanceEntity) throws DAOException;
	
	MerchantBalanceEntity getBalanceByMerchant(MerchantEntity merchantEntity) throws DAOException;
	
	List<MerchantBalanceEntity> getMerchantBalances() throws DAOException;
	//平台总资金余额
	BigDecimal getBalance() throws DAOException;
	
}
