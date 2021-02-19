package com.mb.ext.core.dao.product;

import java.util.List;

import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.entity.product.SupplierProductEntity;
import com.mb.ext.core.entity.supplier.SupplierEntity;
import com.mb.framework.exception.DAOException;



public interface SupplierProductDAO
{
	void createSupplierProduct(SupplierProductEntity supplierProductEntity) throws DAOException;
	
	void updateSupplierProduct(SupplierProductEntity supplierProductEntity) throws DAOException;
	
	void deleteSupplierProduct(SupplierProductEntity supplierProductEntity) throws DAOException;
	
	List<ProductEntity> getProductsBySupplier(SupplierEntity supplierEntity) throws DAOException;
	
	SupplierProductEntity getSupplierProduct(SupplierEntity supplierEntity, ProductEntity productEntity) throws DAOException;
	
	List<SupplierEntity> searchSupplierByProduct(ProductEntity productEntity, int startIndex, int pageSize) throws DAOException;
	
	int searchSupplierByProductTotal(ProductEntity productEntity) throws DAOException;
}
