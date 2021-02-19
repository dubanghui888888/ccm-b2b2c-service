package com.mb.ext.core.dao.point;

import java.util.List;

import com.mb.ext.core.entity.point.PointProductEntity;
import com.mb.ext.core.entity.point.PointProductSkuAttrEntity;
import com.mb.framework.exception.DAOException;



public interface PointProductSkuAttrDAO
{
	
	void addProductAttr(PointProductSkuAttrEntity productAttrEntity) throws DAOException;
	
	void updateProductAttr(PointProductSkuAttrEntity productAttrEntity) throws DAOException;
	
	void deleteProductAttr(PointProductSkuAttrEntity productAttrEntity) throws DAOException;
	
	PointProductSkuAttrEntity getProductAttrByUuid(String uuid) throws DAOException;
	
	List<PointProductSkuAttrEntity> getProductAttrByProduct(PointProductEntity productEntity) throws DAOException;
	
}
