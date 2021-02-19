package com.mb.ext.core.dao.product;

import java.util.List;

import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.entity.product.ProductSkuAttrEntity;
import com.mb.framework.exception.DAOException;



public interface ProductSkuAttrDAO
{
	
	void addProductAttr(ProductSkuAttrEntity productAttrEntity) throws DAOException;
	
	void updateProductAttr(ProductSkuAttrEntity productAttrEntity) throws DAOException;
	
	void deleteProductAttr(ProductSkuAttrEntity productAttrEntity) throws DAOException;
	
	ProductSkuAttrEntity getProductAttrByUuid(String uuid) throws DAOException;
	
	List<ProductSkuAttrEntity> getProductAttrByProduct(ProductEntity productEntity) throws DAOException;
	
}
