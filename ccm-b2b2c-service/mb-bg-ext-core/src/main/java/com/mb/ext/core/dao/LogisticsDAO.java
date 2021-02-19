package com.mb.ext.core.dao;

import java.util.List;

import com.mb.ext.core.entity.LogisticsEntity;
import com.mb.framework.exception.DAOException;



public interface LogisticsDAO
{
	
	List<LogisticsEntity> getLogistics() throws DAOException;
	
	LogisticsEntity getLogisticsByUuid(String uuid) throws DAOException;
	
}
