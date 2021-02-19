package com.mb.ext.core.dao.product;

import java.util.List;

import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.entity.product.ProductImageEntity;
import com.mb.framework.exception.DAOException;



public interface ProductImageDAO
{
	void createProductImage(ProductImageEntity productImageEntity) throws DAOException;
	
	void updateProductImage(ProductImageEntity productImageEntity) throws DAOException;
	
	void deleteProductImage(ProductImageEntity productImageEntity) throws DAOException;
	
	List<ProductImageEntity> getImagesByProduct(ProductEntity productEntity) throws DAOException;
	
	ProductImageEntity getImageByUuid(String uuid) throws DAOException;
	
}
