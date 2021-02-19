package com.mb.ext.core.dao.point;

import java.util.List;

import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.point.PointProductCollectEntity;
import com.mb.ext.core.entity.point.PointProductEntity;
import com.mb.framework.exception.DAOException;



public interface PointProductCollectDAO
{
	
	void addProductCollect(PointProductCollectEntity productCollectEntity) throws DAOException;
	
	void deleteProductCollect(PointProductCollectEntity productCollectEntity) throws DAOException;
	
	List<PointProductCollectEntity> getProductCollectByUser(UserEntity userEntity) throws DAOException;
	
	PointProductCollectEntity getProductCollectByUserProduct(UserEntity userEntity, PointProductEntity pointProductEntity) throws DAOException;
	
}
