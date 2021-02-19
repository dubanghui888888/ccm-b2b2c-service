package com.mb.ext.core.dao.point;

import java.util.List;

import com.mb.ext.core.entity.point.PointProductAttrValueEntity;
import com.mb.ext.core.entity.point.PointProductEntity;
import com.mb.framework.exception.DAOException;



public interface PointProductAttrValueDAO
{
	
	void addProductAttr(PointProductAttrValueEntity productAttrEntity) throws DAOException;
	
	void updateProductAttr(PointProductAttrValueEntity productAttrEntity) throws DAOException;
	
	void deleteProductAttr(PointProductAttrValueEntity productAttrEntity) throws DAOException;
	
	PointProductAttrValueEntity getProductAttrByUuid(String uuid) throws DAOException;
	
	List<PointProductAttrValueEntity> getProductAttrByProduct(PointProductEntity productEntity) throws DAOException;
	
}
