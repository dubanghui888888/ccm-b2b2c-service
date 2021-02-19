package com.mb.ext.core.dao.point;

import java.util.List;

import com.mb.ext.core.entity.point.PointProductEntity;
import com.mb.ext.core.entity.point.PointProductVideoEntity;
import com.mb.framework.exception.DAOException;



public interface PointProductVideoDAO
{
	void createProductVideo(PointProductVideoEntity productVideoEntity) throws DAOException;
	
	void updateProductVideo(PointProductVideoEntity productVideoEntity) throws DAOException;
	
	void deleteProductVideo(PointProductVideoEntity productVideoEntity) throws DAOException;
	
	List<PointProductVideoEntity> getVideosByProduct(PointProductEntity productEntity) throws DAOException;
	
	PointProductVideoEntity getVideoByUuid(String uuid) throws DAOException;
	
}
