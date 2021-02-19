package com.mb.ext.core.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.UserPointStatementEntity;
import com.mb.ext.core.service.spec.ChartDTO;
import com.mb.ext.core.service.spec.UserStatementSearchDTO;
import com.mb.framework.exception.DAOException;



public interface UserPointStatementDAO
{
	void createUserPointStatement(UserPointStatementEntity userPointStatementEntity) throws DAOException;
	
	void updateUserPointStatement(UserPointStatementEntity userPointStatementEntity) throws DAOException;
	
	void deleteUserPointStatement(UserPointStatementEntity userPointStatementEntity) throws DAOException;
	
	List<UserPointStatementEntity> getStatementByUser(UserEntity userEntity) throws DAOException;
	
	List<UserPointStatementEntity> getStatementByUserType(UserEntity userEntity, String transactionType) throws DAOException;
	
	List<UserPointStatementEntity> getStatementByDate(UserEntity userEntity, Date startDate, Date endDate) throws DAOException;
	
	BigDecimal getTranAmountByUserDate(UserEntity userEntity, Date startDate, Date endDate) throws DAOException;
	
	List<UserPointStatementEntity> getStatementByDateType(Date startDate, Date endDate, String tranType) throws DAOException;
	
	BigDecimal getTranAmountByUserDateType(UserEntity userEntity, Date startDate, Date endDate, String tranType) throws DAOException;
	
	BigDecimal getTranAmountByDateType(Date startDate, Date endDate, String tranType) throws DAOException;
	
	BigDecimal getTranAmountByDate(Date startDate, Date endDate) throws DAOException;
	
	List<ChartDTO> getIncrementTranAmountChart(Date startDate, Date endDate) throws DAOException;
	
	List<ChartDTO> getIncrementTranAmountChartByUser(UserEntity userEntity, Date startDate, Date endDate) throws DAOException;
	
	/*
	 * 分页查询会员交易明细
	 */
	
	List<UserPointStatementEntity> searchUserPointStatement(UserStatementSearchDTO searchDTO) throws DAOException;
	/*
	 * 分页查询会员交易明细记录条数
	 */
	int searchUserPointStatementTotal(UserStatementSearchDTO searchDTO) throws DAOException;
	/*
	 * 分页查询交易明细总积分数
	 */
	int searchUserPointStatementPoint(UserStatementSearchDTO searchDTO) throws DAOException;
	
	/*
	 * 分页查询交易明细总金额
	 */
	BigDecimal searchUserPointStatementAmount(UserStatementSearchDTO searchDTO) throws DAOException;
}
