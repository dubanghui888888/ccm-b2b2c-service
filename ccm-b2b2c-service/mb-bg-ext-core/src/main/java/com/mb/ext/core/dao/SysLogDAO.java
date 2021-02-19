package com.mb.ext.core.dao;

import java.util.List;

import com.mb.ext.core.entity.SysLogEntity;
import com.mb.ext.core.service.spec.SysLogSearchDTO;
import com.mb.framework.exception.DAOException;


public interface SysLogDAO
{
	/**
	 * 
	 * 
	 * @param sysLogEntity
	 */
	void addSysLog(SysLogEntity sysLogEntity) throws DAOException;
	
	void updateSysLog(SysLogEntity sysLogEntity) throws DAOException;
	
	void deleteSysLog(SysLogEntity sysLogEntity) throws DAOException;
	
	public List<SysLogEntity> searchSysLog(SysLogSearchDTO searchDTO, int startIndex, int pageSize) throws DAOException;
	
	public int searchSysLogTotal(SysLogSearchDTO searchDTO) throws DAOException;
	
	public List<SysLogEntity> getSysLogs() throws DAOException;
	
	public SysLogEntity getSysLogByUuid(String uuid) throws DAOException;
	
}
