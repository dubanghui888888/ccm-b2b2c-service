package com.mb.ext.core.dao.impl.product;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.product.ProductInventoryTakingDAO;
import com.mb.ext.core.entity.product.ProductInventoryTakingEntity;
import com.mb.ext.core.service.spec.ProductSearchDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("productInventoryTakingDAO")
@Qualifier("productInventoryTakingDAO")
public class ProductInventoryTakingDAOImpl extends AbstractBaseDAO<ProductInventoryTakingEntity> implements ProductInventoryTakingDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public ProductInventoryTakingDAOImpl()
	{
		super();
		this.setEntityClass(ProductInventoryTakingEntity.class);
	}

	@Override
	public void createProductInventoryTaking(ProductInventoryTakingEntity productInventoryTakingEntity)
			throws DAOException {
		this.save(productInventoryTakingEntity);
		
	}

	@Override
	public ProductInventoryTakingEntity getInventoryTakingByUuid(String uuid) throws DAOException {

		ProductInventoryTakingEntity takingEntity = null;
		try {
			takingEntity = (ProductInventoryTakingEntity)em.createQuery("select b from ProductInventoryTakingEntity b where b.productInventoryTakingUuid = :UUID",ProductInventoryTakingEntity.class).setParameter("UUID", uuid).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product inventory taking");
		}
		return takingEntity;
	
	}

	@Override
	public List<ProductInventoryTakingEntity> searchProductInventoryTaking(ProductSearchDTO productSearchDTO)
			throws DAOException {

		List<ProductInventoryTakingEntity> takingEntityList = new ArrayList<ProductInventoryTakingEntity>();
		String query = "select b from ProductInventoryTakingEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = productSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (ProductSearchDTO.KEY_PRODUCT_NAME.equals(key)) {
				query = query + " and b.productEntity.productName like :PRODUCTNAME";
			}else if (ProductSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.productEntity.merchantEntity.merchantUuid = :MERCHANTUUID";
			}else if (ProductSearchDTO.KEY_TRAN_TYPE.equals(key)) {
				query = query + " and b.tranType = :TRANTYPE";
			} else if (ProductSearchDTO.KEY_TRAN_TIME.equals(key)) {
				query = query + " and date(b.tranTime) >= date(:TRANTIMESTART) and date(b.tranTime) <= date(:TRANTIMEEND)";
			}
		}
		if(!StringUtils.isEmpty(productSearchDTO.getSortBy())) {
			query = query + " order by b."+productSearchDTO.getSortBy()+" "+productSearchDTO.getSort()+"";
		}else {
			query = query + " order by b.tranTime desc";
		}
		try {
			TypedQuery typedQuery = em.createQuery(query, ProductInventoryTakingEntity.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (ProductSearchDTO.KEY_PRODUCT_NAME.equals(key)) {
					typedQuery.setParameter("PRODUCTNAME",
							"%" + productSearchDTO.getProductName() + "%");
				}else if (ProductSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							productSearchDTO.getMerchantUuid());
				}else if (ProductSearchDTO.KEY_TRAN_TYPE.equals(key)) {
					typedQuery.setParameter("TRANTYPE",
							productSearchDTO.getTranType());
				} else if (ProductSearchDTO.KEY_TRAN_TIME.equals(key)) {
					typedQuery.setParameter("TRANTIMESTART",
							productSearchDTO.getTranTimeStart());
					typedQuery.setParameter("TRANTIMEEND",
							productSearchDTO.getTranTimeEnd());
				}
			}
			takingEntityList = typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(productSearchDTO.getStartIndex()).setMaxResults(productSearchDTO.getPageSize())
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for product inventory taking");
		}
		return takingEntityList;
	
	
	}

	@Override
	public int searchProductInventoryTakingTotal(ProductSearchDTO productSearchDTO) throws DAOException {


		Long total = null;
		String query = "select count(b) from ProductInventoryTakingEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = productSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (ProductSearchDTO.KEY_PRODUCT_NAME.equals(key)) {
				query = query + " and b.productEntity.productName like :PRODUCTNAME";
			}else if (ProductSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.productEntity.merchantEntity.merchantUuid = :MERCHANTUUID";
			}else if (ProductSearchDTO.KEY_TRAN_TYPE.equals(key)) {
				query = query + " and b.tranType = :TRANTYPE";
			} else if (ProductSearchDTO.KEY_TRAN_TIME.equals(key)) {
				query = query + " and date(b.tranTime) >= date(:TRANTIMESTART) and date(b.tranTime) <= date(:TRANTIMEEND)";
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
				}else if (ProductSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							productSearchDTO.getMerchantUuid());
				}else if (ProductSearchDTO.KEY_TRAN_TYPE.equals(key)) {
					typedQuery.setParameter("TRANTYPE",
							productSearchDTO.getTranType());
				} else if (ProductSearchDTO.KEY_TRAN_TIME.equals(key)) {
					typedQuery.setParameter("TRANTIMESTART",
							productSearchDTO.getTranTimeStart());
					typedQuery.setParameter("TRANTIMEEND",
							productSearchDTO.getTranTimeEnd());
				}
			}
			total = (Long)typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product inventory taking");
		}
		return total==null?0:total.intValue();
	
	
	
	}


}
