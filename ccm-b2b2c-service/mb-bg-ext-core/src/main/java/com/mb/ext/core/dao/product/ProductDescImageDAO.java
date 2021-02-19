package com.mb.ext.core.dao.product;

import java.util.List;

import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.entity.product.ProductDescImageEntity;
import com.mb.framework.exception.DAOException;



public interface ProductDescImageDAO
{
	void createProductDescImage(ProductDescImageEntity productDescImageEntity) throws DAOException;
	
	void updateProductDescImage(ProductDescImageEntity productDescImageEntity) throws DAOException;
	
	void deleteProductDescImage(ProductDescImageEntity productDescImageEntity) throws DAOException;
	
	List<ProductDescImageEntity> getDescImagesByProduct(ProductEntity productEntity) throws DAOException;
	
	ProductDescImageEntity getDescImageByUuid(String uuid) throws DAOException;
	
}
