package com.mb.ext.core.dao.impl.seckill;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.seckill.SecKillProductDAO;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.seckill.SecKillProductEntity;
import com.mb.ext.core.service.spec.ProductSearchDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("secKillProductDAO")
@Qualifier("secKillProductDAO")
public class SecKillProductDAOImpl extends AbstractBaseDAO<SecKillProductEntity> implements SecKillProductDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public SecKillProductDAOImpl() {
		super();
		this.setEntityClass(SecKillProductEntity.class);
	}

	@Override
	public void addSecKill(SecKillProductEntity secKillProductEntity) throws DAOException {

		this.save(secKillProductEntity);

	}

	@Override
	public void updateSecKill(SecKillProductEntity secKillProductEntity) throws DAOException {

		this.update(secKillProductEntity);

	}

	@Override
	public void deleteSecKill(SecKillProductEntity secKillProductEntity) throws DAOException {

		this.delete(secKillProductEntity);

	}

	@Override
	public SecKillProductEntity getSecKillByUuid(String uuid) throws DAOException {
		SecKillProductEntity secKillProductEntity = null;
		try {
			secKillProductEntity = (SecKillProductEntity) em.createQuery(
					"select b from SecKillProductEntity b where  b.secKillProductUuid = :UUID and b.isDeleted=:isDeleted",
					SecKillProductEntity.class).setParameter("UUID", uuid).setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product : " + uuid);
		}
		return secKillProductEntity;
	}

	@Override
	public List<SecKillProductEntity> getActiveSecKills() throws DAOException {
		List<SecKillProductEntity> secKillProductEntityList = new ArrayList<SecKillProductEntity>();
		try {
			secKillProductEntityList = em.createQuery(
					"select b from SecKillProductEntity b where b.status = :STATUS and b.isDeleted=:isDeleted order by b.startTime",
					SecKillProductEntity.class)
					.setParameter("STATUS", "1")
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for sec kill");
		}
		return secKillProductEntityList;
	}

	@Override
	public List<SecKillProductEntity> getAllSecKills() throws DAOException {
		List<SecKillProductEntity> secKillProductEntityList = new ArrayList<SecKillProductEntity>();
		try {
			secKillProductEntityList = em.createQuery("select b from SecKillProductEntity b where  b.isDeleted=:isDeleted order by b.startTime",SecKillProductEntity.class).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for sec kill");
		}
		return secKillProductEntityList;
	}
	
	@Override
	public List<SecKillProductEntity> searchSecKill(ProductSearchDTO productSearchDTO) throws DAOException {
		List<SecKillProductEntity> secKillProductEntityList = new ArrayList<SecKillProductEntity>();
		String query = "select b from SecKillProductEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = productSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (ProductSearchDTO.KEY_PRODUCT_NAME.equals(key)) {
				query = query + " and b.productEntity.productName like :PRODUCTNAME";
			}else if (ProductSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.productEntity.merchantEntity.merchantUuid = :MERCHANTUUID";
			}
		}
		if(!StringUtils.isEmpty(productSearchDTO.getSortBy())) {
			query = query + " order by b."+productSearchDTO.getSortBy()+" "+productSearchDTO.getSort()+"";
		}else {
			query = query + " order by b.createDate desc";
		}
		try {
			TypedQuery typedQuery = em.createQuery(query, SecKillProductEntity.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (ProductSearchDTO.KEY_PRODUCT_NAME.equals(key)) {
					typedQuery.setParameter("PRODUCTNAME",
							"%" + productSearchDTO.getProductName() + "%");
				} else if (ProductSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							productSearchDTO.getMerchantUuid());
				}
			}
			secKillProductEntityList = typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(productSearchDTO.getStartIndex()).setMaxResults(productSearchDTO.getPageSize())
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for product");
		}
		return secKillProductEntityList;
	}
	
	@Override
	public int searchSecKillTotal(ProductSearchDTO productSearchDTO) throws DAOException {
		Long total = null;
		String query = "select count(b) from SecKillProductEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = productSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (ProductSearchDTO.KEY_PRODUCT_NAME.equals(key)) {
				query = query + " and b.productEntity.productName like :PRODUCTNAME";
			}else if (ProductSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.productEntity.merchantEntity.merchantUuid = :MERCHANTUUID";
			}
		}
		if(!StringUtils.isEmpty(productSearchDTO.getSortBy())) {
			query = query + " order by b."+productSearchDTO.getSortBy()+" "+productSearchDTO.getSort()+"";
		}else {
			query = query + " order by b.createDate desc";
		}
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (ProductSearchDTO.KEY_PRODUCT_NAME.equals(key)) {
					typedQuery.setParameter("PRODUCTNAME",
							"%" + productSearchDTO.getProductName() + "%");
				} else if (ProductSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							productSearchDTO.getMerchantUuid());
				}
			}
			total = (Long) typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product");
		}
		return total==null?0:total.intValue();
	}
	
	@Override
	public List<SecKillProductEntity> getSecKillsByMerchant(MerchantEntity merchantEntity) throws DAOException {
		List<SecKillProductEntity> secKillProductEntityList = new ArrayList<SecKillProductEntity>();
		try {
			secKillProductEntityList = em.createQuery("select b from SecKillProductEntity b where b.merchantEntity.merchantUuid = :MERCHANTUUID and b.isDeleted=:isDeleted order by b.startTime",SecKillProductEntity.class)
					.setParameter("MERCHANTUUID", merchantEntity.getMerchantUuid())
					.setParameter("isDeleted", Boolean.FALSE)
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for sec kill");
		}
		return secKillProductEntityList;
	}

	@Override
	public void decrStock(String secKillProductUuid, int unit) throws DAOException {
		try {
			String nativeSql = "update sec_kill_product set stock = stock - "+unit+", sold_unit = sold_unit + "+unit+" where sec_kill_product_uuid = '" + secKillProductUuid + "'";
			em.createNativeQuery(nativeSql).executeUpdate();
		} catch (Exception e) {
			throw new DAOException("更新秒杀商品库存失败");
		}
	}
	
	@Override
	public void incrStock(String secKillProductUuid, int unit) throws DAOException {
		try {
			String nativeSql = "update sec_kill_product set stock = stock + "+unit+", sold_unit = sold_unit - "+unit+" where sec_kill_product_uuid = '" + secKillProductUuid + "'";
			em.createNativeQuery(nativeSql).executeUpdate();
		} catch (Exception e) {
			throw new DAOException("更新秒杀商品库存失败");
		}
	}
}
