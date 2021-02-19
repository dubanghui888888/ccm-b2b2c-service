package com.mb.ext.core.dao.product;

import java.util.List;

import com.mb.ext.core.entity.product.ProductAttrValueEntity;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.framework.exception.DAOException;



public interface ProductAttrValueDAO
{
	
	void addProductAttr(ProductAttrValueEntity productAttrEntity) throws DAOException;
	
	void updateProductAttr(ProductAttrValueEntity productAttrEntity) throws DAOException;
	
	void deleteProductAttr(ProductAttrValueEntity productAttrEntity) throws DAOException;
	
	ProductAttrValueEntity getProductAttrByUuid(String uuid) throws DAOException;
	
	List<ProductAttrValueEntity> getProductAttrByProduct(ProductEntity productEntity) throws DAOException;
	
}
