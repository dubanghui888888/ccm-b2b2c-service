package com.mb.ext.core.dao.merchant;

import java.util.List;

import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.merchant.MerchantShopperEntity;
import com.mb.ext.core.service.spec.MerchantSearchDTO;
import com.mb.framework.exception.DAOException;



public interface MerchantShopperDAO
{
	void createMerchantShopper(MerchantShopperEntity merchantShopperEntity) throws DAOException;
	
	void updateMerchantShopper(MerchantShopperEntity merchantShopperEntity) throws DAOException;
	
	void deleteMerchantShopper(MerchantShopperEntity merchantShopperEntity) throws DAOException;
	
	List<MerchantShopperEntity> getShoppersByMerchant(MerchantEntity merchantEntity) throws DAOException;
	
	MerchantShopperEntity getShopperById(String id) throws DAOException;
	
	List<MerchantShopperEntity> searchMerchantShopper(MerchantSearchDTO merchantSearchDTO) throws DAOException;
	
	int searchMerchantShopperTotal(MerchantSearchDTO merchantSearchDTO) throws DAOException;
	
}
