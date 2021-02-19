package com.mb.ext.core.dao.point;

import java.util.List;

import com.mb.ext.core.entity.point.PointProductCommentEntity;
import com.mb.ext.core.entity.point.PointProductEntity;
import com.mb.ext.core.service.spec.ProductCommentSearchDTO;
import com.mb.ext.core.service.spec.point.PointProductDTO;
import com.mb.framework.exception.DAOException;



public interface PointProductCommentDAO
{
	
	void addProductComment(PointProductCommentEntity productCommentEntity) throws DAOException;
	
	void updateProductComment(PointProductCommentEntity productCommentEntity) throws DAOException;
	
	void deleteProductComment(PointProductCommentEntity productCommentEntity) throws DAOException;
	
	PointProductCommentEntity getProductCommentByUuid(String uuid) throws DAOException;
	
	List<PointProductCommentEntity> getProductCommentByProduct(PointProductEntity productEntity) throws DAOException;
	
	int getProductCommentTotal(PointProductDTO productDTO) throws DAOException;
	
	List<PointProductCommentEntity> searchProductComment(ProductCommentSearchDTO searchDTO,boolean isShowOnly)throws DAOException;
	
	int searchProductCommentTotal(ProductCommentSearchDTO searchDTO,boolean isShowOnly)throws DAOException;
	
}
