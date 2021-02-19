package com.mb.ext.core.dao;

import java.util.List;

import com.mb.ext.core.entity.GroupEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.framework.exception.DAOException;

public interface GroupDAO
{
	void addGroup(GroupEntity groupEntity) throws DAOException;
	
	void updateGroup(GroupEntity groupEntity) throws DAOException;
	
	void deleteGroup(GroupEntity groupEntity) throws DAOException;
	
	public GroupEntity getGroupByUuid(String uuid) throws DAOException;
	
	public GroupEntity getGroupByName(String name) throws DAOException;
	
	public GroupEntity getGroupForRegister() throws DAOException;
	
	public List<GroupEntity> getGroups() throws DAOException;
	
	public List<GroupEntity> getGroupsByMerchant(MerchantEntity merchantEntity) throws DAOException;
	
	public List<GroupEntity> getHomeGroups() throws DAOException;
}
