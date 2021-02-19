package com.mb.ext.core.dao.point;

import java.util.List;

import com.mb.ext.core.entity.point.PointProductDescImageEntity;
import com.mb.ext.core.entity.point.PointProductEntity;
import com.mb.framework.exception.DAOException;



public interface PointProductDescImageDAO
{
	void createProductDescImage(PointProductDescImageEntity productDescImageEntity) throws DAOException;
	
	void updateProductDescImage(PointProductDescImageEntity productDescImageEntity) throws DAOException;
	
	void deleteProductDescImage(PointProductDescImageEntity productDescImageEntity) throws DAOException;
	
	List<PointProductDescImageEntity> getDescImagesByProduct(PointProductEntity productEntity) throws DAOException;
	
	PointProductDescImageEntity getDescImageByUuid(String uuid) throws DAOException;
	
}
