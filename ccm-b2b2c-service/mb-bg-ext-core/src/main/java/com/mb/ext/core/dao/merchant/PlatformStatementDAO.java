package com.mb.ext.core.dao.merchant;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.mb.ext.core.entity.merchant.PlatformStatementEntity;
import com.mb.ext.core.service.spec.ChartDTO;
import com.mb.framework.exception.DAOException;



public interface PlatformStatementDAO
{
	void createPlatformStatement(PlatformStatementEntity merchantStatementEntity) throws DAOException;
	
	void updatePlatformStatement(PlatformStatementEntity merchantStatementEntity) throws DAOException;
	
	void deletePlatformStatement(PlatformStatementEntity merchantStatementEntity) throws DAOException;
	
	List<PlatformStatementEntity> getStatement() throws DAOException;
	
	List<PlatformStatementEntity> getStatementByDate(Date startDate, Date endDate) throws DAOException;
	
	BigDecimal getTranAmountByDate(Date startDate, Date endDate) throws DAOException;
	
	BigDecimal getTranAmountByDateType(Date startDate, Date endDate, String tranType) throws DAOException;
	
	List<ChartDTO> getIncrementTranAmountChart(Date startDate, Date endDate) throws DAOException;
	
	
}
