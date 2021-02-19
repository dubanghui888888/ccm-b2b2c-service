package com.mb.ext.core.dao.group;

import java.util.List;

import com.mb.ext.core.entity.group.GroupBuyDefEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.service.spec.ProductSearchDTO;
import com.mb.framework.exception.DAOException;



public interface GroupBuyDefDAO
{
	
	void addGroupBuy(GroupBuyDefEntity groupBuyDefEntity) throws DAOException;
	
	void updateGroupBuy(GroupBuyDefEntity groupBuyDefEntity) throws DAOException;
	
	void deleteGroupBuy(GroupBuyDefEntity groupBuyDefEntity) throws DAOException;
	
	GroupBuyDefEntity getGroupBuyByUuid(String uuid) throws DAOException;
	
	List<GroupBuyDefEntity> getActiveGroupBuys() throws DAOException;
	
	List<GroupBuyDefEntity> getGroupBuysByMerchant(MerchantEntity merchantEntity) throws DAOException;
	
	List<GroupBuyDefEntity> getAllGroupBuys() throws DAOException;
	
	List<GroupBuyDefEntity> searchGroupBuy(ProductSearchDTO productSearchDTO) throws DAOException;
	
	int searchGroupBuyTotal(ProductSearchDTO productSearchDTO) throws DAOException;
	
}
