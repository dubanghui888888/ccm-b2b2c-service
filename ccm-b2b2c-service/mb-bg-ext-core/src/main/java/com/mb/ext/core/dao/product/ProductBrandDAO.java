package com.mb.ext.core.dao.product;

import java.util.List;

import com.mb.ext.core.entity.product.ProductBrandEntity;
import com.mb.ext.core.entity.product.ProductCateEntity;
import com.mb.ext.core.service.spec.ProductBrandSearchDTO;
import com.mb.framework.exception.DAOException;



public interface ProductBrandDAO
{
	void createProductBrand(ProductBrandEntity productBrandEntity) throws DAOException;
	
	void updateProductBrand(ProductBrandEntity productBrandEntity) throws DAOException;
	
	void deleteProductBrand(ProductBrandEntity productBrandEntity) throws DAOException;
	
	List<ProductBrandEntity> getBrands() throws DAOException;
	
	List<ProductBrandEntity> getProductBrandsByProductCate(ProductCateEntity productCateEntity) throws DAOException;
	
	ProductBrandEntity getBrandByUuid(String uuid) throws DAOException;
	
	List<ProductBrandEntity> searchBrand(ProductBrandSearchDTO searchDTO) throws DAOException;
	
	int searchBrandTotal(ProductBrandSearchDTO searchDTO) throws DAOException;
	
}
