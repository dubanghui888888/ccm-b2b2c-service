package com.mb.ext.core.dao.promote;

import java.util.List;

import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.entity.promote.ProductPromoteEntity;
import com.mb.framework.exception.DAOException;



public interface ProductPromoteDAO
{
	void createProductPromote(ProductPromoteEntity productPromoteEntity) throws DAOException;
	
	void updateProductPromote(ProductPromoteEntity productPromoteEntity) throws DAOException;
	
	void deleteProductPromote(ProductPromoteEntity productPromoteEntity) throws DAOException;
	
	List<ProductPromoteEntity> getPromoteByProduct(ProductEntity productEntity) throws DAOException;
	
	ProductPromoteEntity getPromoteByUuid(String uuid) throws DAOException;
	
	List<ProductPromoteEntity> getPromoteByType(ProductEntity productEntity, String promoteType) throws DAOException;
	
	ProductPromoteEntity getPromoteByTypeAndUuid(ProductEntity productEntity, String promoteType, String uuid) throws DAOException;
	
}
