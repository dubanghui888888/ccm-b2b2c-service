package com.mb.ext.core.dao;

import java.util.List;

import com.mb.ext.core.entity.OrgEntity;
import com.mb.framework.exception.DAOException;



public interface OrgDAO
{
	OrgEntity getOrgByName(String name) throws DAOException;
	
	OrgEntity getOrgByNameEntId(String name, String entId) throws DAOException;
	
	List<OrgEntity> getOrgs() throws DAOException;
	
	List<OrgEntity> getTopOrgs() throws DAOException;
	
	List<OrgEntity> getBottomOrgs() throws DAOException;
	
	OrgEntity getOrgByManager(String userUuid) throws DAOException;
	
	void addOrg(OrgEntity orgEntity) throws DAOException;
	
	void updateOrg(OrgEntity orgEntity) throws DAOException;
	
	void deleteOrg(OrgEntity orgEntity) throws DAOException;
}
