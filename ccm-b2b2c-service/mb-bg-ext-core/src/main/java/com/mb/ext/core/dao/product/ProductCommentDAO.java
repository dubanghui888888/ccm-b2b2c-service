package com.mb.ext.core.dao.product;

import java.util.List;

import com.mb.ext.core.entity.product.ProductCommentEntity;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.service.spec.ProductCommentSearchDTO;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.framework.exception.DAOException;



public interface ProductCommentDAO
{
	
	void addProductComment(ProductCommentEntity productCommentEntity) throws DAOException;
	
	void updateProductComment(ProductCommentEntity productCommentEntity) throws DAOException;
	
	void deleteProductComment(ProductCommentEntity productCommentEntity) throws DAOException;
	
	ProductCommentEntity getProductCommentByUuid(String uuid) throws DAOException;
	
	List<ProductCommentEntity> getProductCommentByProduct(ProductEntity productEntity) throws DAOException;
	
	int getProductCommentTotal(ProductDTO productDTO) throws DAOException;
	
	List<ProductCommentEntity> searchProductComment(ProductCommentSearchDTO searchDTO,boolean isShowOnly)throws DAOException;
	
	int searchProductCommentTotal(ProductCommentSearchDTO searchDTO,boolean isShowOnly)throws DAOException;
	
}
