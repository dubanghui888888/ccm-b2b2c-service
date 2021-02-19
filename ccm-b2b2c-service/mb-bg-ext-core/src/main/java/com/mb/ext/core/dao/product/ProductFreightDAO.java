package com.mb.ext.core.dao.product;

import java.util.List;

import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.product.ProductFreightEntity;
import com.mb.ext.core.service.spec.MerchantSearchDTO;
import com.mb.framework.exception.DAOException;



public interface ProductFreightDAO
{
	void createProductFreight(ProductFreightEntity productFreightEntity) throws DAOException;
	
	void updateProductFreight(ProductFreightEntity productFreightEntity) throws DAOException;
	
	void deleteProductFreight(ProductFreightEntity productFreightEntity) throws DAOException;
	
	List<ProductFreightEntity> getFreights() throws DAOException;
	
	List<ProductFreightEntity> getFreightsByMerchant(MerchantEntity merchantEntity) throws DAOException;

	List<ProductFreightEntity> searchFreight(MerchantSearchDTO merchantSearchDTO) throws DAOException;

	int searchFreightTotal(MerchantSearchDTO merchantSearchDTO) throws DAOException;
	
	ProductFreightEntity getFreightByUuid(String uuid) throws DAOException;
	
}
