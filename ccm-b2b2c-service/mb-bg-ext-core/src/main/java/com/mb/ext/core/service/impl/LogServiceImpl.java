
package com.mb.ext.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mb.ext.core.constant.LogConstants;
import com.mb.ext.core.context.UserContext;
import com.mb.ext.core.dao.SysLogDAO;
import com.mb.ext.core.entity.SysLogEntity;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.LogService;
import com.mb.ext.core.service.spec.AdminDTO;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.SysLogDTO;
import com.mb.ext.core.service.spec.SysLogSearchDTO;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.service.AbstractService;
import com.mb.framework.util.log.LogHelper;


@Service
@Qualifier("LogService")
public class LogServiceImpl extends AbstractService implements LogService<BodyDTO>
{
	
	@Autowired
	@Qualifier("sysLogDAO")
	private SysLogDAO sysLogDAO;
	
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());
	
	@Override
	public void addSysLog(String logCategory, String logDetail) throws BusinessException {
		try{
			
			SysLogEntity logEntity = new SysLogEntity();
			logEntity.setLogDate(new Date());
			logEntity.setLogCategory(logCategory);
			logEntity.setLogType(LogConstants.LOGTYPE_SYS);
			
			AdminDTO admdinDTO = (AdminDTO) UserContext.get("ADMINPROFILE");
			logEntity.setLogUserId(admdinDTO.getId());

			logEntity.setLogDetail(logDetail);
			sysLogDAO.addSysLog(logEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void addMannualLog(SysLogDTO sysLogDTO) throws BusinessException {
		try{
			
			SysLogEntity logEntity = new SysLogEntity();
			logEntity.setLogDate(new Date());
			logEntity.setLogCategory(sysLogDTO.getLogCategory());
			logEntity.setLogType(LogConstants.LOGTYPE_MANNUAL);
			
			AdminDTO admdinDTO = (AdminDTO) UserContext.get("ADMINPROFILE");
			logEntity.setLogUserId(admdinDTO.getId());

			logEntity.setLogDetail(sysLogDTO.getLogDetail());
			sysLogDAO.addSysLog(logEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public void deleteSysLog(SysLogDTO sysLogDTO) throws BusinessException {
		try{
			
			SysLogEntity logEntity = sysLogDAO.getSysLogByUuid(sysLogDTO.getSysLogUuid());
			sysLogDAO.deleteSysLog(logEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public List<SysLogDTO> searchSysLog(SysLogSearchDTO searchDTO,
			int startIndex, int pageSize) throws BusinessException {
		List<SysLogDTO> dtoList = new ArrayList<SysLogDTO>();
		try{
			List<SysLogEntity> entityList = sysLogDAO.searchSysLog(searchDTO, startIndex, pageSize);
			for (Iterator<SysLogEntity> iterator = entityList.iterator(); iterator.hasNext();) {
				SysLogEntity sysLogEntity = iterator.next();
				SysLogDTO sysLogDTO = new SysLogDTO();
				logEntity2DTO(sysLogEntity, sysLogDTO);
				dtoList.add(sysLogDTO);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return dtoList;
	}

	@Override
	public int searchSysLogTotal(SysLogSearchDTO searchDTO)
			throws BusinessException {
		try{
			return sysLogDAO.searchSysLogTotal(searchDTO);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	void logEntity2DTO(SysLogEntity logEntity, SysLogDTO logDTO){
		logDTO.setSysLogUuid(logEntity.getSysLogUuid());
		logDTO.setLogCategory(logEntity.getLogCategory());
		logDTO.setLogType(logEntity.getLogType());
		logDTO.setLogDetail(logEntity.getLogDetail());
		logDTO.setLogUserId(logEntity.getLogUserId());
		logDTO.setLogDate(logEntity.getLogDate());
	}
	
}






