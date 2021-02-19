package com.mb.ext.core.dao.point;

import com.mb.ext.core.entity.point.PointProductCateAttrEntity;
import com.mb.framework.exception.DAOException;



public interface PointProductCateAttrDAO
{
	
	void addProductCateAttr(PointProductCateAttrEntity productCateAttrEntity) throws DAOException;
	
	void updateProductCateAttr(PointProductCateAttrEntity productCateAttrEntity) throws DAOException;
	
	void deleteProductCateAttr(PointProductCateAttrEntity productCateAttrEntity) throws DAOException;
	
	PointProductCateAttrEntity getProductCateAttrByUuid(String uuid) throws DAOException;
}
