package com.mb.ext.core.dao.impl.shoppingcart;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.aliyuncs.utils.StringUtils;
import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.shoppingcart.ShoppingCartDAO;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.entity.product.ProductSkuEntity;
import com.mb.ext.core.entity.shoppingcart.ShoppingCartEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("shoppingCartDAO")
@Qualifier("shoppingCartDAO")
public class ShoppingCartDAOImpl extends AbstractBaseDAO<ShoppingCartEntity> implements
		ShoppingCartDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public ShoppingCartDAOImpl() {
		super();
		this.setEntityClass(ShoppingCartEntity.class);
	}


	@Override
	public void addShoppingCart(ShoppingCartEntity shoppingCartEntity) throws DAOException {
		
		save(shoppingCartEntity);
		
	}


	@Override
	public void deleteShoppingCart(ShoppingCartEntity shoppingCartEntity) throws DAOException {
		
		deletePhysical(shoppingCartEntity);
		
	}
	
	@Override
	public void clearShoppingCart(UserEntity userEntity) throws DAOException {
		
		try {
			if(userEntity != null && !StringUtils.isEmpty(userEntity.getUserUuid())) {
				em.createNativeQuery("DELETE FROM shopping_cart WHERE USER_UUID = :USERUUID")
				.setParameter("USERUUID", userEntity.getUserUuid())
				.executeUpdate();
			}
		} catch (NoResultException e) {
			logger.info("删除购物车出错");
		}
		
	}

	@Override
	public void updateShoppingCart(ShoppingCartEntity shoppingCartEntity) throws DAOException {
		
		update(shoppingCartEntity);
		
	}

	@Override
	public ShoppingCartEntity getShoppingCartByUuid(String uuid)
			throws DAOException {
		ShoppingCartEntity shoppingCartEntity = null;
		try {
			shoppingCartEntity = (ShoppingCartEntity)em.createQuery("select b from ShoppingCartEntity b where  b.shoppingCartUuid = :UUID and b.isDeleted=:isDeleted",ShoppingCartEntity.class).setParameter("UUID", uuid).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for order : "+uuid);
		}
		return shoppingCartEntity;
	}
	
	@Override
	public int getShoppingCartNumByUser(UserEntity userEntity)
			throws DAOException {
		Long num = null;
		try {
			num = em.createQuery("select sum(b.unit) from ShoppingCartEntity b where  b.userEntity.userUuid = :USERUUID and b.isDeleted=:isDeleted",Long.class).setParameter("USERUUID", userEntity.getUserUuid()).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for order : "+userEntity.getUserUuid());
		}
		return num==null?0:num.intValue();
	}
	
	@Override
	public List<ShoppingCartEntity> getShoppingCartByUser(UserEntity userEntity)
			throws DAOException {
		List<ShoppingCartEntity> shoppingCartEntityList = new ArrayList<ShoppingCartEntity>();
		try {
			shoppingCartEntityList = em.createQuery("select b from ShoppingCartEntity b where  b.userEntity.userUuid = :USERUUID and b.isDeleted=:isDeleted order by b.updateDate desc",ShoppingCartEntity.class).setParameter("USERUUID", userEntity.getUserUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for order : "+userEntity.getUserUuid());
		}
		return shoppingCartEntityList;
	}
	@Override
	public ShoppingCartEntity getShoppingCartByUserProduct(UserEntity userEntity, ProductEntity productEntity)
			throws DAOException {
		ShoppingCartEntity shoppingCartEntity = null;
		try {
			shoppingCartEntity = em.createQuery("select b from ShoppingCartEntity b where  b.userEntity.userUuid = :USERUUID and b.productEntity.productUuid = :PRODUCTUUID and b.productSkuEntity is null and b.isDeleted=:isDeleted",ShoppingCartEntity.class).setParameter("USERUUID", userEntity.getUserUuid()).setParameter("PRODUCTUUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for order : "+userEntity.getUserUuid());
		}
		return shoppingCartEntity;
	}
	@Override
	public ShoppingCartEntity getShoppingCartByUserProductSku(UserEntity userEntity, ProductSkuEntity productSkuEntity)
			throws DAOException {
		ShoppingCartEntity shoppingCartEntity = null;
		try {
			shoppingCartEntity = em.createQuery("select b from ShoppingCartEntity b where  b.userEntity.userUuid = :USERUUID and b.productSkuEntity.productSkuUuid = :PRODUCTSKUUUID and b.isDeleted=:isDeleted",ShoppingCartEntity.class).setParameter("USERUUID", userEntity.getUserUuid()).setParameter("PRODUCTSKUUUID", productSkuEntity.getProductSkuUuid()).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for order : "+userEntity.getUserUuid());
		}
		return shoppingCartEntity;
	}


	@Override
	public List<ShoppingCartEntity> getShoppingCartByProductUuid(
			ProductEntity productEntity) throws DAOException {
		// TODO Auto-generated method stub
		List<ShoppingCartEntity> shoppingCartEntityList = new ArrayList<ShoppingCartEntity>();
		try {
			shoppingCartEntityList = em.createQuery("select b from ShoppingCartEntity b where  b.productEntity.productUuid = :UUID and b.isDeleted=:isDeleted",ShoppingCartEntity.class).setParameter("UUID", productEntity.getProductUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for product sku: "+productEntity.getProductUuid());
		}
		return shoppingCartEntityList;
	}




}
