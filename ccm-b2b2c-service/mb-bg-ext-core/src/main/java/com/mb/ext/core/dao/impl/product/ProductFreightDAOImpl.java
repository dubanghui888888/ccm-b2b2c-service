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
import com.mb.ext.core.dao.product.ProductFreightDAO;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.product.ProductFreightEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("productFreightDAO")
@Qualifier("productFreightDAO")
public class ProductFreightDAOImpl extends AbstractBaseDAO<ProductFreightEntity> implements ProductFreightDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public ProductFreightDAOImpl()
	{
		super();
		this.setEntityClass(ProductFreightEntity.class);
	}

	@Override
	public void createProductFreight(ProductFreightEntity productFreightEntity)
			throws DAOException {
		save(productFreightEntity);
		
	}

	@Override
	public void updateProductFreight(ProductFreightEntity productFreightEntity)
			throws DAOException {
		update(productFreightEntity);
		
	}

	@Override
	public void deleteProductFreight(ProductFreightEntity productFreightEntity)
			throws DAOException {
		delete(productFreightEntity);
		
	}

	@Override
	public List<ProductFreightEntity> getFreights() throws DAOException {
		List<ProductFreightEntity> freightEntityList = new ArrayList<ProductFreightEntity>();;
		try {
			freightEntityList = em.createQuery("select b from ProductFreightEntity b where  b.isDeleted=:isDeleted",ProductFreightEntity.class).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for supplier industry");
		}
		return freightEntityList;
	}
	
	@Override
	public List<ProductFreightEntity> getFreightsByMerchant(MerchantEntity merchantEntity) throws DAOException {
		List<ProductFreightEntity> freightEntityList = new ArrayList<ProductFreightEntity>();;
		try {
			freightEntityList = em.createQuery("select b from ProductFreightEntity b where b.merchantEntity.merchantUuid = :MERCHANTUUID and b.isDeleted=:isDeleted",ProductFreightEntity.class)
					.setParameter("isDeleted", Boolean.FALSE)
					.setParameter("MERCHANTUUID", merchantEntity.getMerchantUuid())
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for supplier industry");
		}
		return freightEntityList;
	}

	@Override
	public List<ProductFreightEntity> searchFreight(MerchantSearchDTO merchantSearchDTO) throws DAOException {
		List<ProductFreightEntity> freightEntityList = new ArrayList<ProductFreightEntity>();
		String query = "select b from ProductFreightEntity b where b.isDeleted=:isDeleted";
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
			TypedQuery typedQuery = em.createQuery(query, ProductFreightEntity.class);
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
			freightEntityList = typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(merchantSearchDTO.getStartIndex()).setMaxResults(merchantSearchDTO.getPageSize())
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return freightEntityList;
	}

	@Override
	public int searchFreightTotal(MerchantSearchDTO merchantSearchDTO) throws DAOException {
		Long total = Long.valueOf(0);
		String query = "select count(b) from ProductFreightEntity b where b.isDeleted=:isDeleted";
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
	public ProductFreightEntity getFreightByUuid(String uuid) throws DAOException {
		ProductFreightEntity freightEntity = new ProductFreightEntity();
		try {
			freightEntity = (ProductFreightEntity)em.createQuery("select b from ProductFreightEntity b where b.productFreightUuid = :UUID",ProductFreightEntity.class).setParameter("UUID", uuid).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for supplier industry");
		}
		return freightEntity;
	}

}
