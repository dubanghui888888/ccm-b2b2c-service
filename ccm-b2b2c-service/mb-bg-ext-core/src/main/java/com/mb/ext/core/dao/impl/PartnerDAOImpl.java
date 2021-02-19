package com.mb.ext.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.PartnerDAO;
import com.mb.ext.core.entity.PartnerEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.service.spec.UserSearchDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("partnerDAO")
@Qualifier("partnerDAO")
public class PartnerDAOImpl extends AbstractBaseDAO<PartnerEntity> implements PartnerDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public PartnerDAOImpl() {
		super();
		this.setEntityClass(PartnerEntity.class);
	}

	/**
	 * This method is used for inserting partner information.
	 * 
	 * @param partner
	 */
	
	@Override
	public List<PartnerEntity> searchPartners(UserSearchDTO userSearchDTO,
			int startIndex, int pageSize) throws DAOException {
		List<PartnerEntity> partnerEntityList = new ArrayList<PartnerEntity>();
		String query = "select b from PartnerEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = userSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (UserSearchDTO.KEY_NAME.equals(key)) {
				query = query + " and b.merchantEntity.contactName like :NAME";
			} else if (UserSearchDTO.KEY_PERSONALPHONE.equals(key)) {
				query = query + " and b.merchantEntity.mobileNo like :PERSONALPHONE";
			} else if (UserSearchDTO.KEY_EFFECTIVEDATE.equals(key)) {
				query = query
						+ " and b.effectiveDate >= :EFFECTIVESTART and b.effectiveDate <= :EFFECTIVEEND";
			}
		}
		try {
			TypedQuery typedQuery = em.createQuery(query, PartnerEntity.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (UserSearchDTO.KEY_NAME.equals(key)) {
					typedQuery.setParameter("NAME",
							"%" + userSearchDTO.getName() + "%");
				} else if (UserSearchDTO.KEY_PERSONALPHONE.equals(key)) {
					typedQuery.setParameter("PERSONALPHONE", "%"
							+ userSearchDTO.getPersonalPhone() + "%");
				}
			}
			partnerEntityList = typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(startIndex).setMaxResults(pageSize)
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return partnerEntityList;
	}
	
	@Override
	public int searchPartnerTotal(UserSearchDTO userSearchDTO) throws DAOException {
		Long total = Long.valueOf(0);
		String query = "select count(b) from PartnerEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = userSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (UserSearchDTO.KEY_NAME.equals(key)) {
				query = query + " and b.merchantEntity.contactName like :NAME";
			} else if (UserSearchDTO.KEY_PERSONALPHONE.equals(key)) {
				query = query + " and b.merchantEntity.mobileNo like :PERSONALPHONE";
			} else if (UserSearchDTO.KEY_EFFECTIVEDATE.equals(key)) {
				query = query
						+ " and b.effectiveDate >= :EFFECTIVESTART and b.effectiveDate <= :EFFECTIVEEND";
			}
		}
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (UserSearchDTO.KEY_NAME.equals(key)) {
					typedQuery.setParameter("NAME",
							"%" + userSearchDTO.getName() + "%");
				} else if (UserSearchDTO.KEY_PERSONALPHONE.equals(key)) {
					typedQuery.setParameter("PERSONALPHONE", "%"
							+ userSearchDTO.getPersonalPhone() + "%");
				}
			}
			total = (Long) typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return total.intValue();
	}
	
	@Override
	public void addPartner(PartnerEntity partnerEntity) throws DAOException {
		save(partnerEntity);
	}

	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */

	@Override
	public void updatePartner(PartnerEntity partnerEntity) throws DAOException {
		this.update(partnerEntity);
		
	}

	@Override
	public void deletePartner(PartnerEntity partnerEntity) throws DAOException {
		this.deletePhysical(partnerEntity);
		
	}

	@Override
	public void deletePhysicalPartner(PartnerEntity partnerEntity) throws DAOException {
		this.deletePhysical(partnerEntity);
		
	}

	@Override
	public PartnerEntity getPartnerByUuid(String partnerUuid) throws DAOException {
		PartnerEntity partnerEntity = null;
		try {
			partnerEntity = (PartnerEntity) em
					.createQuery(
							"select b from PartnerEntity b where b.partnerUuid = :UUID and b.isDeleted=:isDeleted",
							PartnerEntity.class).setParameter("UUID", partnerUuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for partner: " + partnerUuid);
		}
		return partnerEntity;
	}

	@Override
	public PartnerEntity getPartnerByMerchant(MerchantEntity merchantEntity) throws DAOException {
		PartnerEntity partnerEntity = null;
		try {
			partnerEntity = (PartnerEntity) em
					.createQuery(
							"select b from PartnerEntity b where b.merchantEntity.merchantUuid = :UUID and b.isDeleted=:isDeleted",
							PartnerEntity.class).setParameter("UUID", merchantEntity.getMerchantUuid())
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for partner: " + merchantEntity.getMerchantUuid());
		}
		return partnerEntity;
	}

	@Override
	public List<PartnerEntity> getPartnerByLevel(String partnerLevel) throws DAOException {
		List<PartnerEntity> partnerEntityList = new ArrayList<PartnerEntity>();;
		try {
			partnerEntityList = em
					.createQuery(
							"select b from PartnerEntity b where b.partnerLevel = :LEVEL and b.isDeleted=:isDeleted",
							PartnerEntity.class).setParameter("LEVEL", partnerLevel)
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for partner: " + partnerLevel);
		}
		return partnerEntityList;
	}
	
	@Override
	public int getPartnerCount() throws DAOException {
		Long partnerCount = new Long(0);
		try {
			partnerCount = em
					.createQuery(
							"select count(b.partnerUuid) from PartnerEntity b where b.isDeleted=:isDeleted",
							Long.class)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for partner: ");
		}
		return partnerCount.intValue();
	}

	@Override
	public int getPartnerCountByLevel(String partnerLevel) throws DAOException {
		Long partnerCount = new Long(0);
		try {
			partnerCount = em
					.createQuery(
							"select count(b.partnerUuid) from PartnerEntity b where b.partnerLevel = :LEVEL and b.isDeleted=:isDeleted",
							Long.class).setParameter("LEVEL", partnerLevel)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for partner: " + partnerLevel);
		}
		return partnerCount.intValue();
	}
}
