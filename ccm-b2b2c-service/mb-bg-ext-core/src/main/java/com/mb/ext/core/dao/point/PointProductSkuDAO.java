package com.mb.ext.core.dao.point;

import java.math.BigDecimal;
import java.util.List;

import com.mb.ext.core.entity.point.PointProductEntity;
import com.mb.ext.core.entity.point.PointProductSkuEntity;
import com.mb.framework.exception.DAOException;



public interface PointProductSkuDAO
{
	
	void addProductSku(PointProductSkuEntity productSkuEntity) throws DAOException;
	
	void updateProductSku(PointProductSkuEntity productSkuEntity) throws DAOException;
	
	void deleteProductSku(PointProductSkuEntity productSkuEntity) throws DAOException;
	
	PointProductSkuEntity getProductSkuByUuid(String uuid) throws DAOException;
	
	PointProductSkuEntity getProductSkuByAttrValueUuids(PointProductEntity productEntity, String attrValueuuids) throws DAOException;
	
	List<PointProductSkuEntity> getProductSkuByProduct(PointProductEntity productEntity) throws DAOException;
	
	int getMinUnitPointByProduct(PointProductEntity productEntity) throws DAOException;
	
	BigDecimal getMinUnitPriceByProduct(PointProductEntity productEntity) throws DAOException;
	
	int getMinUnitPointStandardByProduct(PointProductEntity productEntity) throws DAOException;
	
	BigDecimal getMinUnitPriceStandardByProduct(PointProductEntity productEntity) throws DAOException;
	
	int getMaxUnitPointByProduct(PointProductEntity productEntity) throws DAOException;
	
	BigDecimal getMaxUnitPriceByProduct(PointProductEntity productEntity) throws DAOException;
	
	int getMaxUnitPointStandardByProduct(PointProductEntity productEntity) throws DAOException;
	
	BigDecimal getMaxUnitPriceStandardByProduct(PointProductEntity productEntity) throws DAOException;
}
