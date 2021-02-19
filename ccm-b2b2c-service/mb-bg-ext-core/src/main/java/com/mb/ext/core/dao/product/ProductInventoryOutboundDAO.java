package com.mb.ext.core.dao.product;

import java.util.List;

import com.mb.ext.core.entity.product.ProductInventoryOutboundEntity;
import com.mb.ext.core.service.spec.ProductSearchDTO;
import com.mb.framework.exception.DAOException;



public interface ProductInventoryOutboundDAO
{
	
	void createProductInventoryOutbound(ProductInventoryOutboundEntity productInventoryOutboundEntity) throws DAOException;
	
	ProductInventoryOutboundEntity getInventoryOutboundByUuid(String uuid) throws DAOException;
	
	List<ProductInventoryOutboundEntity> searchProductInventoryOutbound(ProductSearchDTO productSearchDTO) throws DAOException;
	
	int searchProductInventoryOutboundTotal(ProductSearchDTO productSearchDTO) throws DAOException;
	
	
}
