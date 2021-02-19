package com.mb.ext.core.dao.group;

import java.util.List;

import com.mb.ext.core.entity.group.GroupBuyEntity;
import com.mb.ext.core.entity.group.GroupBuyUserEntity;
import com.mb.framework.exception.DAOException;



public interface GroupBuyUserDAO
{
	
	void addGroupBuyUser(GroupBuyUserEntity groupBuyUserEntity) throws DAOException;
	
	void updateGroupBuyUser(GroupBuyUserEntity groupBuyUserEntity) throws DAOException;
	
	void deleteGroupBuyUser(GroupBuyUserEntity groupBuyUserEntity) throws DAOException;
	
	GroupBuyUserEntity getGroupBuyUserByUuid(String uuid) throws DAOException;
	
	List<GroupBuyUserEntity> getUsersBuyGroupBuy(GroupBuyEntity groupBuyEntity) throws DAOException;
	
	GroupBuyUserEntity getGroupBuyUserByOrder(String orderNo) throws DAOException;
	
	int getGroupBuyUserCount(GroupBuyEntity groupBuyEntity) throws DAOException;
	
}
