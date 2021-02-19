package com.mb.ext.core.dao;

import com.mb.ext.core.entity.OrgRelEntity;
import com.mb.framework.exception.DAOException;



public interface OrgRelDAO
{
	
	void addOrgRel(OrgRelEntity orgRelEntity) throws DAOException;
	
	void updateOrgRel(OrgRelEntity orgRelEntity) throws DAOException;
	
	void deleteOrgRel(OrgRelEntity orgRelEntity) throws DAOException;
}
