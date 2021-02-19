package com.mb.ext.core.dao.impl.product;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.mb.ext.core.service.spec.MerchantSearchDTO;
import com.mb.ext.core.service.spec.UserSearchDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.product.ProductDeliveryDAO;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.product.ProductDeliveryEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("productDeliveryDAO")
@Qualifier("productDeliveryDAO")
public class ProductDeliveryDAOImpl extends AbstractBaseDAO<ProductDeliveryEntity> implements ProductDeliveryDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public ProductDeliveryDAOImpl()
	{
		super();
		this.setEntityClass(ProductDeliveryEntity.class);
	}

	@Override
	public void createProductDelivery(ProductDeliveryEntity productDeliveryEntity)
			throws DAOException {
		save(productDeliveryEntity);
		
	}

	@Override
	public void updateProductDelivery(ProductDeliveryEntity productDeliveryEntity)
			throws DAOException {
		update(productDeliveryEntity);
		
	}

	@Override
	public void deleteProductDelivery(ProductDeliveryEntity productDeliveryEntity)
			throws DAOException {
		delete(productDeliveryEntity);
		
	}

	@Override
	public List<ProductDeliveryEntity> getDeliverys() throws DAOException {
		List<ProductDeliveryEntity> deliveryEntityList = new ArrayList<ProductDeliveryEntity>();;
		try {
			deliveryEntityList = em.createQuery("select b from ProductDeliveryEntity b where  b.isDeleted=:isDeleted",ProductDeliveryEntity.class).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for supplier industry");
		}
		return deliveryEntityList;
	}
	
	@Override
	public List<ProductDeliveryEntity> getDeliverysByMerchant(MerchantEntity merchantEntity) throws DAOException {
		List<ProductDeliveryEntity> deliveryEntityList = new ArrayList<ProductDeliveryEntity>();;
		try {
			deliveryEntityList = em.createQuery("select b from ProductDeliveryEntity b where b.merchantEntity.merchantUuid = :MERCHANTUUID and b.isDeleted=:isDeleted",ProductDeliveryEntity.class)
					.setParameter("isDeleted", Boolean.FALSE)
					.setParameter("MERCHANTUUID", merchantEntity.getMerchantUuid())
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for supplier industry");
		}
		return deliveryEntityList;
	}

	@Override
	public List<ProductDeliveryEntity> searchDelivery(MerchantSearchDTO merchantSearchDTO) throws DAOException {
		List<ProductDeliveryEntity> deliveryEntityList = new ArrayList<ProductDeliveryEntity>();
		String query = "select b from ProductDeliveryEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = merchantSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (MerchantSearchDTO.KEY_MERCHANTUUID.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid = :MERCHANTUUID";
			} else if (MerchantSearchDTO.KEY_MOBILENO.equals(key)) {
				query = query + " and b.merchantEntity.mobileNo = :MOBILENO";
			} else if (MerchantSearchDTO.KEY_MERCHANTADDRESS.equals(key)) {
				query = query + " and b.merchantEntity.merchantAddress like :MERCHANTADDRESS";
			} else if (MerchantSearchDTO.KEY_MERCHANTNAME.equals(key)) {
				query = query + " and b.merchantEntity.merchantName like :MERCHANTNAME";
			} else if (MerchantSearchDTO.KEY_REFERRER.equals(key)) {
				query = query + " and b.merchantEntity.referrer = :REFERRER";
			} else if (UserSearchDTO.KEY_REGISTERDATE.equals(key)) {
				query = query
						+ " and b.merchantEntity.registerDate >= :REGISTERDATESTART and b.merchantEntity.registerDate <= :REGISTERDATEEND";
			}
		}
		query = query + " order by b.merchantEntity.merchantName";
		try {
			TypedQuery typedQuery = em.createQuery(query, ProductDeliveryEntity.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (MerchantSearchDTO.KEY_MERCHANTUUID.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							 merchantSearchDTO.getMerchantUuid());
				} else if (MerchantSearchDTO.KEY_MOBILENO.equals(key)) {
					typedQuery.setParameter("MOBILENO",
							merchantSearchDTO.getMobileNo());
				} else if (MerchantSearchDTO.KEY_MERCHANTADDRESS.equals(key)) {
					typedQuery.setParameter("MERCHANTADDRESS",
							"%" + merchantSearchDTO.getMerchantAddress() + "%");
				} else if (MerchantSearchDTO.KEY_REFERRER.equals(key)) {
					typedQuery.setParameter("REFERRER",
							merchantSearchDTO.getReferrer());
				} else if (MerchantSearchDTO.KEY_MERCHANTNAME.equals(key)) {
					typedQuery.setParameter("MERCHANTNAME",
							"%" + merchantSearchDTO.getMerchantName() + "%");
				} else if (UserSearchDTO.KEY_REGISTERDATE.equals(key)) {
					typedQuery.setParameter("REGISTERDATESTART",
							merchantSearchDTO.getRegisterDateStart());
					typedQuery.setParameter("REGISTERDATEEND",
							merchantSearchDTO.getRegisterDateEnd());
				}
			}
			deliveryEntityList = typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(merchantSearchDTO.getStartIndex()).setMaxResults(merchantSearchDTO.getPageSize())
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return deliveryEntityList;
	}

	@Override
	public int searchDeliveryTotal(MerchantSearchDTO merchantSearchDTO) throws DAOException {
		Long total = Long.valueOf(0);
		String query = "select count(b) from ProductDeliveryEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = merchantSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (MerchantSearchDTO.KEY_MERCHANTUUID.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid = :MERCHANTUUID";
			} else if (MerchantSearchDTO.KEY_MOBILENO.equals(key)) {
				query = query + " and b.merchantEntity.mobileNo = :MOBILENO";
			} else if (MerchantSearchDTO.KEY_MERCHANTADDRESS.equals(key)) {
				query = query + " and b.merchantEntity.merchantAddress like :MERCHANTADDRESS";
			} else if (MerchantSearchDTO.KEY_MERCHANTNAME.equals(key)) {
				query = query + " and b.merchantEntity.merchantName like :MERCHANTNAME";
			} else if (MerchantSearchDTO.KEY_REFERRER.equals(key)) {
				query = query + " and b.merchantEntity.referrer = :REFERRER";
			} else if (UserSearchDTO.KEY_REGISTERDATE.equals(key)) {
				query = query
						+ " and b.merchantEntity.registerDate >= :REGISTERDATESTART and b.merchantEntity.registerDate <= :REGISTERDATEEND";
			}
		}
		query = query + " order by b.createDate desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (MerchantSearchDTO.KEY_MERCHANTUUID.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							merchantSearchDTO.getMerchantUuid());
				} else if (MerchantSearchDTO.KEY_MOBILENO.equals(key)) {
					typedQuery.setParameter("MOBILENO",
							merchantSearchDTO.getMobileNo());
				} else if (MerchantSearchDTO.KEY_MERCHANTADDRESS.equals(key)) {
					typedQuery.setParameter("MERCHANTADDRESS",
							"%" + merchantSearchDTO.getMerchantAddress() + "%");
				} else if (MerchantSearchDTO.KEY_REFERRER.equals(key)) {
					typedQuery.setParameter("REFERRER",
							merchantSearchDTO.getReferrer());
				} else if (MerchantSearchDTO.KEY_MERCHANTNAME.equals(key)) {
					typedQuery.setParameter("MERCHANTNAME",
							"%" + merchantSearchDTO.getMerchantName() + "%");
				} else if (UserSearchDTO.KEY_REGISTERDATE.equals(key)) {
					typedQuery.setParameter("REGISTERDATESTART",
							merchantSearchDTO.getRegisterDateStart());
					typedQuery.setParameter("REGISTERDATEEND",
							merchantSearchDTO.getRegisterDateEnd());
				}
			}
			total = (Long)typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return total.intValue();
	}

	@Override
	public ProductDeliveryEntity getDeliveryByUuid(String uuid) throws DAOException {
		ProductDeliveryEntity deliveryEntity = new ProductDeliveryEntity();
		try {
			deliveryEntity = (ProductDeliveryEntity)em.createQuery("select b from ProductDeliveryEntity b where b.productDeliveryUuid = :UUID",ProductDeliveryEntity.class).setParameter("UUID", uuid).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for supplier industry");
		}
		return deliveryEntity;
	}

}
