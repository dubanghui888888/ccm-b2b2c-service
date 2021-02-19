package com.mb.ext.core.dao.product;

import java.math.BigDecimal;
import java.util.List;

import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.entity.product.ProductSkuEntity;
import com.mb.framework.exception.DAOException;



public interface ProductSkuDAO
{
	
	void addProductSku(ProductSkuEntity productSkuEntity) throws DAOException;
	
	void updateProductSku(ProductSkuEntity productSkuEntity) throws DAOException;
	
	void deleteProductSku(ProductSkuEntity productSkuEntity) throws DAOException;
	
	ProductSkuEntity getProductSkuByUuid(String uuid) throws DAOException;
	
	ProductSkuEntity getProductSkuByAttrValueUuids(ProductEntity productEntity, String attrValueuuids) throws DAOException;
	
	List<ProductSkuEntity> getProductSkuByProduct(ProductEntity productEntity) throws DAOException;
	
	int getMinUnitPointByProduct(ProductEntity productEntity) throws DAOException;
	
	BigDecimal getMinUnitPriceByProduct(ProductEntity productEntity) throws DAOException;
	
	int getMinUnitPointStandardByProduct(ProductEntity productEntity) throws DAOException;
	
	BigDecimal getMinUnitPriceStandardByProduct(ProductEntity productEntity) throws DAOException;
	
	int getMaxUnitPointByProduct(ProductEntity productEntity) throws DAOException;
	
	BigDecimal getMaxUnitPriceByProduct(ProductEntity productEntity) throws DAOException;
	
	int getMaxUnitPointStandardByProduct(ProductEntity productEntity) throws DAOException;
	
	BigDecimal getMaxUnitPriceStandardByProduct(ProductEntity productEntity) throws DAOException;
}
