package com.mb.ext.core.dao.point;

import java.util.List;

import com.mb.ext.core.entity.point.PointProductCateEntity;
import com.mb.framework.exception.DAOException;



public interface PointProductCateDAO
{
	
	void addProductCate(PointProductCateEntity productCateEntity) throws DAOException;
	
	void updateProductCate(PointProductCateEntity productCateEntity) throws DAOException;
	
	void deleteProductCate(PointProductCateEntity productCateEntity) throws DAOException;
	
	PointProductCateEntity getProductCateByUuid(String uuid) throws DAOException;
	
	List<PointProductCateEntity> getRootProductCate() throws DAOException;
	
	List<PointProductCateEntity> getLeafProductCate() throws DAOException;
	
	List<PointProductCateEntity> getHomeProductCate() throws DAOException;
	
	List<PointProductCateEntity> getChildProductCate(PointProductCateEntity productCateEntity) throws DAOException;
}
