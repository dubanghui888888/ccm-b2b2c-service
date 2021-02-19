package com.mb.ext.core.dao.product;

import java.util.List;

import com.mb.ext.core.entity.product.ProductCateEntity;
import com.mb.framework.exception.DAOException;



public interface ProductCateDAO
{
	
	void addProductCate(ProductCateEntity productCateEntity) throws DAOException;
	
	void updateProductCate(ProductCateEntity productCateEntity) throws DAOException;
	
	void deleteProductCate(ProductCateEntity productCateEntity) throws DAOException;
	
	ProductCateEntity getProductCateByUuid(String uuid) throws DAOException;
	
	List<ProductCateEntity> getRootProductCate() throws DAOException;
	
	List<ProductCateEntity> getLeafProductCate() throws DAOException;
	
	List<ProductCateEntity> getHomeProductCate() throws DAOException;
	
	List<ProductCateEntity> getChildProductCate(ProductCateEntity productCateEntity) throws DAOException;
}
