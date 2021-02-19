package com.mb.ext.core.dao.group;

import java.util.List;

import com.mb.ext.core.entity.group.GroupBuyEntity;
import com.mb.ext.core.service.spec.GroupBuySearchDTO;
import com.mb.ext.core.service.spec.group.GroupBuyDTO;
import com.mb.framework.exception.DAOException;



public interface GroupBuyDAO
{
	
	void addGroupBuy(GroupBuyEntity groupBuyEntity) throws DAOException;
	
	void updateGroupBuy(GroupBuyEntity groupBuyEntity) throws DAOException;
	
	void deleteGroupBuy(GroupBuyEntity groupBuyEntity) throws DAOException;
	
	GroupBuyEntity getGroupBuyByUuid(String uuid) throws DAOException;
	
	List<GroupBuyEntity> getActiveGroupBuys() throws DAOException;

	List<GroupBuyEntity> getExpiredGroupBuys() throws DAOException;
	
	List<GroupBuyEntity> getAllGroupBuys() throws DAOException;
	
	List<GroupBuyEntity> searchGroupBuy(GroupBuySearchDTO groupBuySearchDTO) throws DAOException;
	
	int searchGroupBuyTotal(GroupBuySearchDTO groupBuySearchDTO) throws DAOException;

	List<GroupBuyDTO> getActiveGroupBuysByGroupBuyProduct(String groupBuyProductUuid) throws DAOException;
	
}
