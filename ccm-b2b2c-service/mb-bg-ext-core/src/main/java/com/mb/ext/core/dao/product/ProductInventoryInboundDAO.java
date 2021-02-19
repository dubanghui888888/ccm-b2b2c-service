package com.mb.ext.core.dao.product;

import java.util.List;

import com.mb.ext.core.entity.product.ProductInventoryInboundEntity;
import com.mb.ext.core.service.spec.ProductSearchDTO;
import com.mb.framework.exception.DAOException;



public interface ProductInventoryInboundDAO
{
	void createProductInventoryInbound(ProductInventoryInboundEntity productInventoryInboundEntity) throws DAOException;
	
	ProductInventoryInboundEntity getInventoryInboundByUuid(String uuid) throws DAOException;
	
	List<ProductInventoryInboundEntity> searchProductInventoryInbound(ProductSearchDTO productSearchDTO) throws DAOException;
	
	int searchProductInventoryInboundTotal(ProductSearchDTO productSearchDTO) throws DAOException;

}
