package com.mb.ext.core.dao.product;

import java.util.List;

import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.entity.product.ProductSkuAttrEntity;
import com.mb.ext.core.entity.product.ProductSkuAttrValueEntity;
import com.mb.framework.exception.DAOException;



public interface ProductSkuAttrValueDAO
{
	
	void addProductAttrValue(ProductSkuAttrValueEntity productAttrValueEntity) throws DAOException;
	
	void updateProductAttrValue(ProductSkuAttrValueEntity productAttrValueEntity) throws DAOException;
	
	void deleteProductAttrValue(ProductSkuAttrValueEntity productAttrValueEntity) throws DAOException;
	
	ProductSkuAttrValueEntity getProductSkuAttrValueByUuid(String uuid) throws DAOException;
	
	List<ProductSkuAttrValueEntity> getProductSkuAttrValuesByAttr(ProductSkuAttrEntity attrEntity) throws DAOException;
	
	List<ProductSkuAttrValueEntity> getProductSkuAttrValuesByProduct(ProductEntity productEntity) throws DAOException;
	
	ProductSkuAttrValueEntity getProductSkuAttrValuesByNameValue(ProductEntity productEntity,String name, String value) throws DAOException;
	
	ProductSkuAttrValueEntity getProductSkuAttrValuesByAttrNameUuidAndValue(ProductEntity productEntity,String uuid, String value) throws DAOException;
}
