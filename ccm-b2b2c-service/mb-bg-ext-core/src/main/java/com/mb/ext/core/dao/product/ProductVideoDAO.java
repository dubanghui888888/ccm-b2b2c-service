package com.mb.ext.core.dao.product;

import java.util.List;

import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.entity.product.ProductVideoEntity;
import com.mb.framework.exception.DAOException;



public interface ProductVideoDAO
{
	void createProductVideo(ProductVideoEntity productVideoEntity) throws DAOException;
	
	void updateProductVideo(ProductVideoEntity productVideoEntity) throws DAOException;
	
	void deleteProductVideo(ProductVideoEntity productVideoEntity) throws DAOException;
	
	List<ProductVideoEntity> getVideosByProduct(ProductEntity productEntity) throws DAOException;
	
	ProductVideoEntity getVideoByUuid(String uuid) throws DAOException;
	
}
