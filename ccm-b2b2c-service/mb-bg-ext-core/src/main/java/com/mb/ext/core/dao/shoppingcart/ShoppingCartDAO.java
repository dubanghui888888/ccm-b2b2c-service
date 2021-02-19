package com.mb.ext.core.dao.shoppingcart;

import java.util.List;

import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.entity.product.ProductSkuEntity;
import com.mb.ext.core.entity.shoppingcart.ShoppingCartEntity;
import com.mb.framework.exception.DAOException;



public interface ShoppingCartDAO
{
	
	void addShoppingCart(ShoppingCartEntity shoppingCartEntity) throws DAOException;
	
	void updateShoppingCart(ShoppingCartEntity shoppingCartEntity) throws DAOException;
	
	void deleteShoppingCart(ShoppingCartEntity shoppingCartEntity) throws DAOException;
	
	void clearShoppingCart(UserEntity userEntity) throws DAOException;
	
	ShoppingCartEntity getShoppingCartByUuid(String uuid) throws DAOException;
	
	int getShoppingCartNumByUser(UserEntity userEntity) throws DAOException;
	
	List<ShoppingCartEntity> getShoppingCartByUser(UserEntity userEntity) throws DAOException;
	
	/*
	 * 获取商品会员购物车的某个商品(非多规格)
	 */
	ShoppingCartEntity getShoppingCartByUserProduct(UserEntity userEntity, ProductEntity productEntity) throws DAOException;
	/*
	 * 获取商品会员购物车的某个商品(多规格)
	 */
	ShoppingCartEntity getShoppingCartByUserProductSku(UserEntity userEntity, ProductSkuEntity productSkuEntity) throws DAOException;

	List<ShoppingCartEntity> getShoppingCartByProductUuid(ProductEntity productEntity) throws DAOException;

	
}
