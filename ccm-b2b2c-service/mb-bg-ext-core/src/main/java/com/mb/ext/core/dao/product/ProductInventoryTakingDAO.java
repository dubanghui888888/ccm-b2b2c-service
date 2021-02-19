package com.mb.ext.core.dao.product;

import java.util.List;

import com.mb.ext.core.entity.product.ProductInventoryTakingEntity;
import com.mb.ext.core.service.spec.ProductSearchDTO;
import com.mb.framework.exception.DAOException;



public interface ProductInventoryTakingDAO
{
	void createProductInventoryTaking(ProductInventoryTakingEntity productInventoryTakingEntity) throws DAOException;
	
	ProductInventoryTakingEntity getInventoryTakingByUuid(String uuid) throws DAOException;
	
	List<ProductInventoryTakingEntity> searchProductInventoryTaking(ProductSearchDTO productSearchDTO) throws DAOException;
	
	int searchProductInventoryTakingTotal(ProductSearchDTO productSearchDTO) throws DAOException;

}
