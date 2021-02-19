package com.mb.ext.core.dao.product;

import java.util.List;

import com.mb.ext.core.entity.supplier.SupplierEntity;
import com.mb.ext.core.entity.product.ProductGroupEntity;
import com.mb.framework.exception.DAOException;



public interface ProductGroupDAO
{
	void createProductGroup(ProductGroupEntity productGroupEntity) throws DAOException;
	
	void updateProductGroup(ProductGroupEntity productGroupEntity) throws DAOException;
	
	void deleteProductGroup(ProductGroupEntity productGroupEntity) throws DAOException;
	
	List<ProductGroupEntity> getGroupBySupplier(SupplierEntity supplierEntity) throws DAOException;
	
	ProductGroupEntity getGroupByUuid(String uuid) throws DAOException;
	
	ProductGroupEntity getGroupByName(String name) throws DAOException;
	
}
