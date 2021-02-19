package com.mb.ext.core.dao.product;

import com.mb.ext.core.entity.product.ProductCateAttrEntity;
import com.mb.framework.exception.DAOException;



public interface ProductCateAttrDAO
{
	
	void addProductCateAttr(ProductCateAttrEntity productCateAttrEntity) throws DAOException;
	
	void updateProductCateAttr(ProductCateAttrEntity productCateAttrEntity) throws DAOException;
	
	void deleteProductCateAttr(ProductCateAttrEntity productCateAttrEntity) throws DAOException;
	
	ProductCateAttrEntity getProductCateAttrByUuid(String uuid) throws DAOException;
}
