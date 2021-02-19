package com.mb.ext.core.dao.product;

import java.util.List;

import com.mb.ext.core.entity.product.ProductBrandEntity;
import com.mb.ext.core.entity.product.ProductCateEntity;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.entity.product.ProductGroupEntity;
import com.mb.ext.core.entity.supplier.SupplierEntity;
import com.mb.ext.core.service.spec.ProductSearchDTO;
import com.mb.framework.exception.DAOException;



public interface ProductDAO
{
	
	void addProduct(ProductEntity productEntity) throws DAOException;
	
	void updateProduct(ProductEntity productEntity) throws DAOException;
	
	void deleteProduct(ProductEntity productEntity) throws DAOException;
	
	ProductEntity getProductByUuid(String uuid) throws DAOException;
	
	List<ProductEntity> getProductByCate(ProductCateEntity cateEntity) throws DAOException;
	
	List<ProductEntity> getProductByType(String productType) throws DAOException;
	
	List<ProductEntity> getProductByGrup(ProductGroupEntity groupEntity) throws DAOException;
	
	List<ProductEntity> searchProduct(ProductSearchDTO searchDTO) throws DAOException;
	
	int searchProductTotal(ProductSearchDTO searchDTO) throws DAOException;
	
	int getSoldUnitTotalBySupplier(SupplierEntity supplierEntity)throws DAOException;
	
	void deleteProductByBrand(ProductBrandEntity productBrandEntity)throws DAOException;
	
}
