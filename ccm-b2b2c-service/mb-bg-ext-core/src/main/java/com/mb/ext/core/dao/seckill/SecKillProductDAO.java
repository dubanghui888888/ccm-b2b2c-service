package com.mb.ext.core.dao.seckill;

import java.util.List;

import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.seckill.SecKillProductEntity;
import com.mb.ext.core.service.spec.ProductSearchDTO;
import com.mb.framework.exception.DAOException;



public interface SecKillProductDAO
{
	
	void addSecKill(SecKillProductEntity secKillProductEntity) throws DAOException;
	
	void updateSecKill(SecKillProductEntity secKillProductEntity) throws DAOException;
	
	void deleteSecKill(SecKillProductEntity secKillProductEntity) throws DAOException;
	
	SecKillProductEntity getSecKillByUuid(String uuid) throws DAOException;
	
	List<SecKillProductEntity> getActiveSecKills() throws DAOException;
	
	List<SecKillProductEntity> getAllSecKills() throws DAOException;
	
	List<SecKillProductEntity> searchSecKill(ProductSearchDTO productSearchDTO) throws DAOException;
	
	int searchSecKillTotal(ProductSearchDTO productSearchDTO) throws DAOException;
	
	List<SecKillProductEntity> getSecKillsByMerchant(MerchantEntity merchantEntity) throws DAOException;
	
	void decrStock(String secKillProductUuid, int unit) throws DAOException;
	
	void incrStock(String secKillProductUuid, int unit) throws DAOException;
}
