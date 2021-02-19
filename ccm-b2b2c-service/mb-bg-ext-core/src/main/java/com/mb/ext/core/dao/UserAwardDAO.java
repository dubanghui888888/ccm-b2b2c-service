package com.mb.ext.core.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.mb.ext.core.entity.UserAwardEntity;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.service.spec.AwardSearchDTO;
import com.mb.ext.core.service.spec.ChartDTO;
import com.mb.framework.exception.DAOException;



public interface UserAwardDAO
{
	void createUserAward(UserAwardEntity userAwardEntity) throws DAOException;
	
	void updateUserAward(UserAwardEntity userAwardEntity) throws DAOException;
	
	void deleteUserAward(UserAwardEntity userAwardEntity) throws DAOException;
	
	UserAwardEntity getAwardByUuid(String uuid) throws DAOException;
	
	List<UserAwardEntity> getAwardByUser(UserEntity userEntity) throws DAOException;
	
	List<UserAwardEntity> getAwardByUserType(UserEntity userEntity, String transactionType) throws DAOException;
	
	List<UserAwardEntity> getAwardByTranCode(String tranCode) throws DAOException;
	
	List<UserAwardEntity> getAwardByDate(UserEntity userEntity, Date startDate, Date endDate) throws DAOException;
	
	List<UserAwardEntity> searchUserAward(AwardSearchDTO awardSearchDTO, int startIndex, int pageSize) throws DAOException;
	
	BigDecimal searchUserAwardAmount(AwardSearchDTO awardSearchDTO) throws DAOException;
	
	int searchUserAwardTotal(AwardSearchDTO awardSearchDTO) throws DAOException;
	
	BigDecimal getTranAmountByUserDate(UserEntity userEntity, Date startDate, Date endDate) throws DAOException;
	
	List<UserAwardEntity> getAwardByDateType(Date startDate, Date endDate, String tranType) throws DAOException;
	
	BigDecimal getTranAmountByUserDateType(UserEntity userEntity, Date startDate, Date endDate, String tranType) throws DAOException;
	
	BigDecimal getTranAmountByDateType(Date startDate, Date endDate, String tranType) throws DAOException;
	
	BigDecimal getTranAmountByDate(Date startDate, Date endDate) throws DAOException;
	
	BigDecimal getTranAmountByDateAndMerchant(MerchantEntity merchantEntity, Date startDate, Date endDate) throws DAOException;
	
	BigDecimal getTranAmountByDateTypeAndMerchant(MerchantEntity merchantEntity, Date startDate, Date endDate, String transactionType) throws DAOException;
	
	List<ChartDTO> getIncrementTranAmountChart(Date startDate, Date endDate) throws DAOException;
	
	List<ChartDTO> getIncrementTranAmountChartByUser(UserEntity userEntity, Date startDate, Date endDate) throws DAOException;
	
}
