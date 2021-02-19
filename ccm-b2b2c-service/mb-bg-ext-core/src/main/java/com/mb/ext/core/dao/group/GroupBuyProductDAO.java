package com.mb.ext.core.dao.group;

import java.util.List;

import com.mb.ext.core.entity.group.GroupBuyProductEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.framework.exception.DAOException;



public interface GroupBuyProductDAO
{
	
	void addGroupBuy(GroupBuyProductEntity groupBuyProductEntity) throws DAOException;
	
	void updateGroupBuy(GroupBuyProductEntity groupBuyProductEntity) throws DAOException;
	
	void deleteGroupBuy(GroupBuyProductEntity groupBuyProductEntity) throws DAOException;
	
	GroupBuyProductEntity getGroupBuyByUuid(String uuid) throws DAOException;
	
	List<GroupBuyProductEntity> getBeingGroupBuyProducts() throws DAOException;
	
	List<GroupBuyProductEntity> getComingGroupBuyProducts() throws DAOException;
	
	List<GroupBuyProductEntity> getAllGroupBuyProducts() throws DAOException;
	
	List<GroupBuyProductEntity> getGroupBuysByMerchant(MerchantEntity merchantEntity) throws DAOException;
	
	void decrStock(String groupBuyProductUuid, int unit) throws DAOException;
	
	void incrStock(String groupBuyProductUuid, int unit) throws DAOException;
}
