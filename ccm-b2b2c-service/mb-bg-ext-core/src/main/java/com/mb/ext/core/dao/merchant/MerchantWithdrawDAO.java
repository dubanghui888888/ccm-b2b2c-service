package com.mb.ext.core.dao.merchant;

import java.math.BigDecimal;
import java.util.List;

import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.merchant.MerchantWithdrawEntity;
import com.mb.ext.core.service.spec.WithdrawSearchDTO;
import com.mb.framework.exception.DAOException;



public interface MerchantWithdrawDAO
{
	void createMerchantWithdraw(MerchantWithdrawEntity merchantWithdrawEntity) throws DAOException;
	
	void updateMerchantWithdraw(MerchantWithdrawEntity merchantWithdrawEntity) throws DAOException;
	
	void deleteMerchantWithdraw(MerchantWithdrawEntity merchantWithdrawEntity) throws DAOException;
	
	List<MerchantWithdrawEntity> getWithdrawByMerchant(MerchantEntity merchantEntity) throws DAOException;
	
	List<MerchantWithdrawEntity> getWithdrawByStatus(String status) throws DAOException;
	
	List<MerchantWithdrawEntity> getWithdraws() throws DAOException;
	
	MerchantWithdrawEntity getWithdrawByUuid(String uuid) throws DAOException;
	
	List<MerchantWithdrawEntity> searchMerchantWithdraw(WithdrawSearchDTO withdrawSearchDTO, int startIndex, int pageSize) throws DAOException;
	
	int searchMerchantWithdrawTotal(WithdrawSearchDTO withdrawSearchDTO) throws DAOException;
	
	BigDecimal searchMerchantWithdrawTotalAmount(WithdrawSearchDTO withdrawSearchDTO) throws DAOException;
	
}
