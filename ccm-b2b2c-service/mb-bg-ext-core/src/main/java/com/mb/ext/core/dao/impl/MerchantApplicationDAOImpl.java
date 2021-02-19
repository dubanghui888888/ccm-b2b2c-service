package com.mb.ext.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.constant.MerchantConstants;
import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.MerchantApplicationDAO;
import com.mb.ext.core.entity.merchant.MerchantApplicationEntity;
import com.mb.ext.core.service.spec.merchant.MerchantApplicationSearchDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("merchantApplicationDAO")
@Qualifier("merchantApplicationDAO")
public class MerchantApplicationDAOImpl extends AbstractBaseDAO<MerchantApplicationEntity> implements MerchantApplicationDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public MerchantApplicationDAOImpl()
	{
		super();
		this.setEntityClass(MerchantApplicationEntity.class);
	}

	@Override
	public void createMerchantApplication(
			MerchantApplicationEntity merchantApplicationEntity) throws DAOException {
		this.save(merchantApplicationEntity);
		
	}

	@Override
	public void updateMerchantApplication(
			MerchantApplicationEntity merchantApplicationEntity) throws DAOException {
		this.update(merchantApplicationEntity);
		
	}

	@Override
	public void deleteMerchantApplication(
			MerchantApplicationEntity merchantApplicationEntity) throws DAOException {
		this.deletePhysical(merchantApplicationEntity);
		
	}

	@Override
	public MerchantApplicationEntity getUpgradeApplicationByUuid(String uuid) throws DAOException {
		MerchantApplicationEntity applicationEntity = null;
		try {
			applicationEntity = em.createQuery("select b from MerchantApplicationEntity b where b.merchantApplicationUuid = :UUID and  b.isDeleted=:isDeleted",MerchantApplicationEntity.class).setParameter("UUID", uuid).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchant exchange rate application");
		}
		return applicationEntity;
	}

	@Override
	public List<MerchantApplicationEntity> getMerchantApplications(String mobileNo) throws DAOException {
		List<MerchantApplicationEntity> applicationEntityList = new ArrayList<MerchantApplicationEntity>();;
		try {
			applicationEntityList = em.createQuery("select b from MerchantApplicationEntity b where b.mobileNo = :MOBILENO and  b.isDeleted=:isDeleted ORDER BY b.applicationTime desc",MerchantApplicationEntity.class).setParameter("MOBILENO", mobileNo).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchant exchange rate application");
		}
		return applicationEntityList;
	}
	
	@Override
	public List<MerchantApplicationEntity> getOutstandingMerchantApplications(String mobileNo) throws DAOException {
		List<MerchantApplicationEntity> applicationEntityList = new ArrayList<MerchantApplicationEntity>();;
		try {
			applicationEntityList = em.createQuery("select b from MerchantApplicationEntity b where b.applicationStatus = :STATUS and b.mobileNo = :MOBILENO and  b.isDeleted=:isDeleted",MerchantApplicationEntity.class)
					.setParameter("STATUS", MerchantConstants.VERIFYSTATUS_PENDING)
					.setParameter("MOBILENO", mobileNo)
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchant exchange rate application");
		}
		return applicationEntityList;
	}

	@Override
	public List<MerchantApplicationEntity> searchMerchantApplication(MerchantApplicationSearchDTO searchDTO) throws DAOException {

		List<MerchantApplicationEntity> merchantApplicationEntityList = new ArrayList<MerchantApplicationEntity>();
		String query = "select b from MerchantApplicationEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = searchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (MerchantApplicationSearchDTO.KEY_MERCHANT_MOBILENO.equals(key)) {
				query = query + " and b.mobileNo like :MOBILENO";
			} else if (MerchantApplicationSearchDTO.KEY_MERCHANT_NAME.equals(key)) {
				query = query + " and b.merchantName like :MERCHANTNAME";
			} else if (MerchantApplicationSearchDTO.KEY_APPLICATION_DATE.equals(key)) {
				query = query + " and date(b.applicationTime) >= date(:APPLICATIONDATESTART) and date(b.applicationTime) <= date(:APPLICATIONDATEEND)";
			} else if(MerchantApplicationSearchDTO.KEY_STATUS.equals(key)){
				query = query + " and b.applicationStatus = :STATUS";
			}
		}
		 query = query + " order by b.applicationTime desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, MerchantApplicationEntity.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (MerchantApplicationSearchDTO.KEY_MERCHANT_MOBILENO.equals(key)) {
					typedQuery.setParameter("MOBILENO",
							"%" + searchDTO.getMerchantMobileNo() + "%");
				} else if (MerchantApplicationSearchDTO.KEY_MERCHANT_NAME.equals(key)) {
					typedQuery.setParameter("MERCHANTNAME",
							"%" + searchDTO.getMerchantName() + "%");
				} else if (MerchantApplicationSearchDTO.KEY_APPLICATION_DATE.equals(key)) {
					typedQuery.setParameter("APPLICATIONDATESTART", searchDTO.getApplicationDateStart());
					typedQuery.setParameter("APPLICATIONDATEEND", searchDTO.getApplicationDateEnd());
				} else if (MerchantApplicationSearchDTO.KEY_STATUS.equals(key)) {
					typedQuery.setParameter("STATUS", searchDTO.getStatus());
				}
			}
			merchantApplicationEntityList = typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(searchDTO.getStartIndex()).setMaxResults(searchDTO.getPageSize())
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return merchantApplicationEntityList;
	
	}

	@Override
	public int searchMerchantApplicationTotal(MerchantApplicationSearchDTO searchDTO)
			throws DAOException {
		Long total = Long.valueOf(0);
		String query = "select count(b) from MerchantApplicationEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = searchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (MerchantApplicationSearchDTO.KEY_MERCHANT_MOBILENO.equals(key)) {
				query = query + " and b.mobileNo like :MOBILENO";
			} else if (MerchantApplicationSearchDTO.KEY_MERCHANT_NAME.equals(key)) {
				query = query + " and b.merchantName like :MERCHANTNAME";
			} else if (MerchantApplicationSearchDTO.KEY_APPLICATION_DATE.equals(key)) {
				query = query + " and date(b.applicationTime) >= date(:APPLICATIONTIMESTART) and date(b.applicationTime) <= date(:APPLICATIONTIMEEND)";
			} else if(MerchantApplicationSearchDTO.KEY_STATUS.equals(key)){
				query = query + " and b.applicationStatus = :STATUS";
			}
		}
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (MerchantApplicationSearchDTO.KEY_MERCHANT_MOBILENO.equals(key)) {
					typedQuery.setParameter("MOBILENO",
							"%" + searchDTO.getMerchantMobileNo() + "%");
				} else if (MerchantApplicationSearchDTO.KEY_MERCHANT_NAME.equals(key)) {
					typedQuery.setParameter("MERCHANTNAME",
							"%" + searchDTO.getMerchantName() + "%");
				} else if (MerchantApplicationSearchDTO.KEY_APPLICATION_DATE.equals(key)) {
					typedQuery.setParameter("APPLICATIONTIMESTART", searchDTO.getApplicationDateStart());
					typedQuery.setParameter("APPLICATIONTIMEEND", searchDTO.getApplicationDateEnd());
				} else if (MerchantApplicationSearchDTO.KEY_STATUS.equals(key)) {
					typedQuery.setParameter("STATUS", searchDTO.getStatus());
				}
			}
			total = (Long)  typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return total==null?0:total.intValue();
	}

}
