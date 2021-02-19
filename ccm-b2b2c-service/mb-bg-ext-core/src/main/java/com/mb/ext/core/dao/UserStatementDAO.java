package com.mb.ext.core.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.UserStatementEntity;
import com.mb.ext.core.service.spec.ChartDTO;
import com.mb.ext.core.service.spec.MerchantStatementSearchDTO;
import com.mb.ext.core.service.spec.UserStatementSearchDTO;
import com.mb.framework.exception.DAOException;



public interface UserStatementDAO
{
	void createUserStatement(UserStatementEntity userStatementEntity) throws DAOException;
	
	void updateUserStatement(UserStatementEntity userStatementEntity) throws DAOException;
	
	void deleteUserStatement(UserStatementEntity userStatementEntity) throws DAOException;
	
	List<UserStatementEntity> getStatementByUser(UserEntity userEntity) throws DAOException;
	
	List<UserStatementEntity> getStatementByUserType(UserEntity userEntity, String transactionType) throws DAOException;
	
	List<UserStatementEntity> getStatementByDate(UserEntity userEntity, Date startDate, Date endDate) throws DAOException;
	
	BigDecimal getTranAmountByUserDate(UserEntity userEntity, Date startDate, Date endDate) throws DAOException;
	
	List<UserStatementEntity> getStatementByDateType(Date startDate, Date endDate, String tranType) throws DAOException;
	
	BigDecimal getTranAmountByUserDateType(UserEntity userEntity, Date startDate, Date endDate, String tranType) throws DAOException;
	
	BigDecimal getTranAmountByDateType(Date startDate, Date endDate, String tranType) throws DAOException;
	
	BigDecimal getTranAmountByDate(Date startDate, Date endDate) throws DAOException;
	
	List<ChartDTO> getIncrementTranAmountChart(Date startDate, Date endDate) throws DAOException;
	
	List<ChartDTO> getIncrementTranAmountChartByUser(UserEntity userEntity, Date startDate, Date endDate) throws DAOException;
	
	/*
	 * 分页查询会员交易明细
	 */
	
	List<UserStatementEntity> searchUserStatement(UserStatementSearchDTO searchDTO) throws DAOException;
	/*
	 * 分页查询会员交易明细记录条数
	 */
	int searchUserStatementTotal(UserStatementSearchDTO searchDTO) throws DAOException;
	/*
	 * 分页查询交易明细总积分数
	 */
	int searchUserStatementPoint(UserStatementSearchDTO searchDTO) throws DAOException;
	
	/*
	 * 分页查询交易明细总金额
	 */
	BigDecimal searchUserStatementAmount(UserStatementSearchDTO searchDTO) throws DAOException;
}
