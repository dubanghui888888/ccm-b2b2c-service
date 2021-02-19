package com.mb.ext.core.dao.product;

import java.util.List;

import com.mb.ext.core.entity.product.ProductCommentEntity;
import com.mb.ext.core.entity.product.ProductCommentImageEntity;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.framework.exception.DAOException;



public interface ProductCommentImageDAO
{
	
	void addProductCommentImage(ProductCommentImageEntity productCommentImageEntity) throws DAOException;
	
	void updateProductCommentImage(ProductCommentImageEntity productCommentImageEntity) throws DAOException;
	
	void deleteProductCommentImage(ProductCommentImageEntity productCommentImageEntity) throws DAOException;
	
	List<ProductCommentImageEntity> getProductCommentImagesByProduct(ProductEntity productEntity) throws DAOException;
	
	List<ProductCommentImageEntity> getProductCommentImagesByProductComment(ProductCommentEntity productCommentEntity) throws DAOException;
	
}
