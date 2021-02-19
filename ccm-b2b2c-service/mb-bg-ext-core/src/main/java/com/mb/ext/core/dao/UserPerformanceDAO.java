package com.mb.ext.core.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.UserPerformanceEntity;
import com.mb.ext.core.service.spec.PerformanceSearchDTO;
import com.mb.ext.core.service.spec.UserPerformanceDTO;
import com.mb.framework.exception.DAOException;



public interface UserPerformanceDAO
{
	void createUserPerformance(UserPerformanceEntity userPerformanceEntity) throws DAOException;
	
	void updateUserPerformance(UserPerformanceEntity userPerformanceEntity) throws DAOException;
	
	void deleteUserPerformance(UserPerformanceEntity userPerformanceEntity) throws DAOException;
	
	List<UserPerformanceEntity> getPerformanceByUser(UserEntity userEntity) throws DAOException;
	
	UserPerformanceEntity getPerformanceByUserDate(UserEntity userEntity, Date performanceDate) throws DAOException;
	
	BigDecimal getTotalPerformanceByUser(UserEntity userEntity) throws DAOException;
	
	BigDecimal getTotalPerformanceAwardByUser(UserEntity userEntity) throws DAOException;
	
	BigDecimal getTotalPerformanceAmountByDate(Date startDate, Date endDate) throws DAOException;
	
	BigDecimal getTotalPerformanceAwardByDate(Date startDate, Date endDate) throws DAOException;
	
	List<UserPerformanceEntity> getPerformanceByUserDateRange(UserEntity userEntity, Date startDate, Date endDate) throws DAOException;
	
	List<UserPerformanceEntity> getUserPerformances() throws DAOException;
	
	int getUserPerformanceTotal(Date performanceDate) throws DAOException;
	
	List<UserPerformanceEntity> getUserPerformancesPagination(Date performanceDate, int startIndex, int pageSize) throws DAOException;
	
	List<UserPerformanceEntity> searchUserPerformance(PerformanceSearchDTO performanceSearchDTO, int startIndex, int pageSize) throws DAOException;
	
	List<UserPerformanceDTO> searchUserTotalPerformance(PerformanceSearchDTO performanceSearchDTO, int startIndex, int pageSize) throws DAOException;
	
	BigDecimal searchUserTotalPerformanceAmount(PerformanceSearchDTO performanceSearchDTO) throws DAOException;
	
	BigDecimal searchUserTotalPerformanceAward(PerformanceSearchDTO performanceSearchDTO) throws DAOException;
	
	int searchUserPerformanceTotal(PerformanceSearchDTO performanceSearchDTO) throws DAOException;
	
	int searchUserTotalPerformanceTotal(PerformanceSearchDTO performanceSearchDTO) throws DAOException;
	
}
