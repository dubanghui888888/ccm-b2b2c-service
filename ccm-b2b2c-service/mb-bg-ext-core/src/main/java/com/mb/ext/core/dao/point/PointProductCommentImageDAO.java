package com.mb.ext.core.dao.point;

import java.util.List;

import com.mb.ext.core.entity.point.PointProductCommentEntity;
import com.mb.ext.core.entity.point.PointProductCommentImageEntity;
import com.mb.ext.core.entity.point.PointProductEntity;
import com.mb.framework.exception.DAOException;



public interface PointProductCommentImageDAO
{
	
	void addProductCommentImage(PointProductCommentImageEntity productCommentImageEntity) throws DAOException;
	
	void updateProductCommentImage(PointProductCommentImageEntity productCommentImageEntity) throws DAOException;
	
	void deleteProductCommentImage(PointProductCommentImageEntity productCommentImageEntity) throws DAOException;
	
	List<PointProductCommentImageEntity> getProductCommentImagesByProduct(PointProductEntity productEntity) throws DAOException;
	
	List<PointProductCommentImageEntity> getProductCommentImagesByProductComment(PointProductCommentEntity productCommentEntity) throws DAOException;
	
}
