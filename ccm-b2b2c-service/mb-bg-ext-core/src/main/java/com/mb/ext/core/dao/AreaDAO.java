package com.mb.ext.core.dao;

import java.util.List;

import com.mb.ext.core.entity.AreaEntity;
import com.mb.ext.core.service.spec.AreaDTO;
import com.mb.framework.exception.DAOException;



public interface AreaDAO
{
	
	List<AreaDTO> getAreas() throws DAOException;
	
	List<AreaDTO> getCitys() throws DAOException;
	
	AreaEntity getAreaById(String areaId) throws DAOException;
	
	AreaEntity getAreaByNameAndDepth(String name, int depth) throws DAOException;
	
}
