
package com.mb.ext.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mb.ext.core.dao.AreaDAO;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.AreaService;
import com.mb.ext.core.service.spec.AreaDTO;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.service.AbstractService;


@Service
@Qualifier("AreaService")
public class AreaServiceImpl extends AbstractService implements AreaService<BodyDTO>
{
	@Autowired
	@Qualifier("areaDAO")
	private AreaDAO areaDAO;
	
	@Override
	public List<AreaDTO> getAreas() throws BusinessException {
		try {
			return areaDAO.getAreas();
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public List<AreaDTO> getCitys() throws BusinessException {
		try {
			return areaDAO.getCitys();
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
}






