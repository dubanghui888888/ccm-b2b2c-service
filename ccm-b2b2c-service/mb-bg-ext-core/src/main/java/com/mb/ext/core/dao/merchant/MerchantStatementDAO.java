package com.mb.ext.core.dao.merchant;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.merchant.MerchantStatementEntity;
import com.mb.ext.core.service.spec.ChartDTO;
import com.mb.ext.core.service.spec.MerchantStatementSearchDTO;
import com.mb.framework.exception.DAOException;



public interface MerchantStatementDAO
{
	void createMerchantStatement(MerchantStatementEntity merchantStatementEntity) throws DAOException;
	
	void updateMerchantStatement(MerchantStatementEntity merchantStatementEntity) throws DAOException;
	
	void deleteMerchantStatement(MerchantStatementEntity merchantStatementEntity) throws DAOException;
	
	/*
	 * 分页查询商家交易明细
	 */
	
	List<MerchantStatementEntity> searchMerchantStatement(MerchantStatementSearchDTO searchDTO) throws DAOException;
	/*
	 * 分页查询商家交易明细记录条数
	 */
	int searchMerchantStatementTotal(MerchantStatementSearchDTO searchDTO) throws DAOException;
	/*
	 * 分页查询交易明细总积分数
	 */
	int searchMerchantStatementPoint(MerchantStatementSearchDTO searchDTO) throws DAOException;
	
	/*
	 * 分页查询交易明细总金额
	 */
	BigDecimal searchMerchantStatementAmount(MerchantStatementSearchDTO searchDTO) throws DAOException;
	
	List<MerchantStatementEntity> getStatementByMerchant(MerchantEntity merchantEntity) throws DAOException;
	
	List<MerchantStatementEntity> getStatementByMerchantType(MerchantEntity merchantEntity, String transactionType) throws DAOException;
	
	List<MerchantStatementEntity> getStatementByDate(MerchantEntity merchantEntity, Date startDate, Date endDate) throws DAOException;
	
	BigDecimal getTranAmountByMerchantDate(MerchantEntity merchantEntity, Date startDate, Date endDate) throws DAOException;
	
	List<MerchantStatementEntity> getStatementByDateType(Date startDate, Date endDate, String tranType) throws DAOException;
	
	BigDecimal getTranAmountByMerchantDateType(MerchantEntity merchantEntity, Date startDate, Date endDate, String tranType) throws DAOException;
	
	BigDecimal getTranAmountByDateType(Date startDate, Date endDate, String tranType) throws DAOException;
	
	BigDecimal getTranAmountByDate(Date startDate, Date endDate) throws DAOException;
	
	List<ChartDTO> getIncrementTranAmountChart(Date startDate, Date endDate) throws DAOException;
	
	List<ChartDTO> getIncrementTranAmountChartByMerchant(MerchantEntity merchantEntity, Date startDate, Date endDate) throws DAOException;
	
}
