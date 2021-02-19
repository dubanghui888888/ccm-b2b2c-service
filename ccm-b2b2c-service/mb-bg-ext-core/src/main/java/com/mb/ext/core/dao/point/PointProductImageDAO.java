package com.mb.ext.core.dao.point;

import java.util.List;

import com.mb.ext.core.entity.point.PointProductEntity;
import com.mb.ext.core.entity.point.PointProductImageEntity;
import com.mb.framework.exception.DAOException;



public interface PointProductImageDAO
{
	void createProductImage(PointProductImageEntity productImageEntity) throws DAOException;
	
	void updateProductImage(PointProductImageEntity productImageEntity) throws DAOException;
	
	void deleteProductImage(PointProductImageEntity productImageEntity) throws DAOException;
	
	List<PointProductImageEntity> getImagesByProduct(PointProductEntity productEntity) throws DAOException;
	
	PointProductImageEntity getImageByUuid(String uuid) throws DAOException;
	
}
