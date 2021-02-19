package com.mb.ext.core.dao.point;

import java.util.List;

import com.mb.ext.core.entity.point.PointProductEntity;
import com.mb.ext.core.entity.point.PointProductSkuAttrEntity;
import com.mb.ext.core.entity.point.PointProductSkuAttrValueEntity;
import com.mb.framework.exception.DAOException;



public interface PointProductSkuAttrValueDAO
{
	
	void addProductAttrValue(PointProductSkuAttrValueEntity productAttrValueEntity) throws DAOException;
	
	void updateProductAttrValue(PointProductSkuAttrValueEntity productAttrValueEntity) throws DAOException;
	
	void deleteProductAttrValue(PointProductSkuAttrValueEntity productAttrValueEntity) throws DAOException;
	
	PointProductSkuAttrValueEntity getProductSkuAttrValueByUuid(String uuid) throws DAOException;
	
	List<PointProductSkuAttrValueEntity> getProductSkuAttrValuesByAttr(PointProductSkuAttrEntity attrEntity) throws DAOException;
	
	List<PointProductSkuAttrValueEntity> getProductSkuAttrValuesByProduct(PointProductEntity productEntity) throws DAOException;
	
	PointProductSkuAttrValueEntity getProductSkuAttrValuesByNameValue(PointProductEntity productEntity,String name, String value) throws DAOException;
	
	PointProductSkuAttrValueEntity getProductSkuAttrValuesByAttrNameUuidAndValue(PointProductEntity productEntity,String uuid, String value) throws DAOException;
}
