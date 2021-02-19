package com.mb.ext.core.dao.impl.merchant;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.merchant.MerchantShopperDAO;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.merchant.MerchantShopperEntity;
import com.mb.ext.core.service.spec.MerchantSearchDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("merchantShopperDAO")
@Qualifier("merchantShopperDAO")
public class MerchantShopperDAOImpl extends AbstractBaseDAO<MerchantShopperEntity> implements MerchantShopperDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public MerchantShopperDAOImpl()
	{
		super();
		this.setEntityClass(MerchantShopperEntity.class);
	}

	@Override
	public void createMerchantShopper(MerchantShopperEntity merchantShopperEntity)
			throws DAOException {
		save(merchantShopperEntity);
		
	}

	@Override
	public void updateMerchantShopper(MerchantShopperEntity merchantShopperEntity)
			throws DAOException {
		update(merchantShopperEntity);
		
	}

	@Override
	public void deleteMerchantShopper(MerchantShopperEntity merchantShopperEntity)
			throws DAOException {
		deletePhysical(merchantShopperEntity);
		
	}
	
	@Override
	public MerchantShopperEntity getShopperById(String id) throws DAOException {
		MerchantShopperEntity shopperEntity = null;
		try {
			shopperEntity = em.createQuery("select b from MerchantShopperEntity b where b.merchantShopperUuid = :UUID and  b.isDeleted=:isDeleted",MerchantShopperEntity.class).setParameter("UUID",id).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchant shopper");
		}
		return shopperEntity;
	}

	@Override
	public List<MerchantShopperEntity> getShoppersByMerchant(MerchantEntity merchantEntity) throws DAOException {
		List<MerchantShopperEntity> shopperEntityList = null;
		try {
			shopperEntityList = em.createQuery("select b from MerchantShopperEntity b where b.merchantEntity.merchantUuid = :UUID and  b.isDeleted=:isDeleted",MerchantShopperEntity.class).setParameter("UUID", merchantEntity.getMerchantUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchant shopper");
		}
		return shopperEntityList;
	}

	@Override
	public List<MerchantShopperEntity> searchMerchantShopper(MerchantSearchDTO merchantSearchDTO) throws DAOException {
		List<MerchantShopperEntity> shopperEntityList = new ArrayList<MerchantShopperEntity>();
		String query = "select b from MerchantShopperEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = merchantSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (MerchantSearchDTO.KEY_MERCHANTUUID.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid = :MERCHANTUUID";
			} else if (MerchantSearchDTO.KEY_MERCHANTNAME.equals(key)) {
				query = query + " and b.merchantEntity.merchantName like :MERCHANTNAME";
			} else if (MerchantSearchDTO.KEY_NAME.equals(key)) {
				query = query + " and b.name = :NAME";
			} else if (MerchantSearchDTO.KEY_MOBILENO.equals(key)) {
				query = query + " and b.mobileNo = :MOBILENO";
			}
		}
		query = query + " order by b.merchantEntity.merchantName";
		try {
			TypedQuery typedQuery = em.createQuery(query, MerchantShopperEntity.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (MerchantSearchDTO.KEY_MERCHANTUUID.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							 merchantSearchDTO.getMerchantUuid());
				} else if (MerchantSearchDTO.KEY_MOBILENO.equals(key)) {
					typedQuery.setParameter("MOBILENO",
							merchantSearchDTO.getMobileNo());
				} else if (MerchantSearchDTO.KEY_NAME.equals(key)) {
					typedQuery.setParameter("NAME",
							merchantSearchDTO.getName());
				} else if (MerchantSearchDTO.KEY_MERCHANTNAME.equals(key)) {
					typedQuery.setParameter("MERCHANTNAME",
							"%" + merchantSearchDTO.getMerchantName() + "%");
				}
			}
			shopperEntityList = typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(merchantSearchDTO.getStartIndex()).setMaxResults(merchantSearchDTO.getPageSize())
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return shopperEntityList;
	}

	@Override
	public int searchMerchantShopperTotal(MerchantSearchDTO merchantSearchDTO) throws DAOException {
		Long total = Long.valueOf(0);
		String query = "select count(b) from MerchantShopperEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = merchantSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (MerchantSearchDTO.KEY_MERCHANTUUID.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid = :MERCHANTUUID";
			} else if (MerchantSearchDTO.KEY_MERCHANTNAME.equals(key)) {
				query = query + " and b.merchantEntity.merchantName like :MERCHANTNAME";
			} else if (MerchantSearchDTO.KEY_NAME.equals(key)) {
				query = query + " and b.name = :NAME";
			} else if (MerchantSearchDTO.KEY_MOBILENO.equals(key)) {
				query = query + " and b.mobileNo = :MOBILENO";
			}
		}
		query = query + " order by b.merchantEntity.merchantName";
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
				} else if (MerchantSearchDTO.KEY_NAME.equals(key)) {
					typedQuery.setParameter("NAME",
							merchantSearchDTO.getName());
				} else if (MerchantSearchDTO.KEY_MERCHANTNAME.equals(key)) {
					typedQuery.setParameter("MERCHANTNAME",
							"%" + merchantSearchDTO.getMerchantName() + "%");
				}
			}
			total = (Long)typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return total==null?0:total.intValue();
	}
}
