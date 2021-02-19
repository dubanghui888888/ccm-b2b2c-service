package com.mb.ext.core.dao.product;

import java.util.List;

import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.product.ProductCollectEntity;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.framework.exception.DAOException;



public interface ProductCollectDAO
{
	
	void addProductCollect(ProductCollectEntity productCollectEntity) throws DAOException;
	
	void deleteProductCollect(ProductCollectEntity productCollectEntity) throws DAOException;
	
	List<ProductCollectEntity> getProductCollectByUser(UserEntity userEntity) throws DAOException;
	
	ProductCollectEntity getProductCollectByUserProduct(UserEntity userEntity, ProductEntity productEntity) throws DAOException;
	
}
