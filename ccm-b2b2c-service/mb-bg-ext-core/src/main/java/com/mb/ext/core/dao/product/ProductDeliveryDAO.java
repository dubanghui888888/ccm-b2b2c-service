package com.mb.ext.core.dao.product;

import java.util.List;

import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.product.ProductDeliveryEntity;
import com.mb.ext.core.service.spec.MerchantSearchDTO;
import com.mb.framework.exception.DAOException;



public interface ProductDeliveryDAO
{
	void createProductDelivery(ProductDeliveryEntity productDeliveryEntity) throws DAOException;
	
	void updateProductDelivery(ProductDeliveryEntity productDeliveryEntity) throws DAOException;
	
	void deleteProductDelivery(ProductDeliveryEntity productDeliveryEntity) throws DAOException;
	
	List<ProductDeliveryEntity> getDeliverys() throws DAOException;
	
	List<ProductDeliveryEntity> getDeliverysByMerchant(MerchantEntity merchantEntity) throws DAOException;

	List<ProductDeliveryEntity> searchDelivery(MerchantSearchDTO merchantSearchDTO) throws DAOException;

	int searchDeliveryTotal(MerchantSearchDTO merchantSearchDTO) throws DAOException;
	
	ProductDeliveryEntity getDeliveryByUuid(String uuid) throws DAOException;
	
}
