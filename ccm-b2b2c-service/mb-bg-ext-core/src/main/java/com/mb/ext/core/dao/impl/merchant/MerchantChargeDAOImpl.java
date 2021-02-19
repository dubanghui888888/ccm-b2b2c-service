package com.mb.ext.core.dao.impl.merchant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.merchant.MerchantChargeDAO;
import com.mb.ext.core.entity.merchant.MerchantChargeEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.service.spec.MerchantChargeSearchDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("merchantChargeDAO")
@Qualifier("merchantChargeDAO")
public class MerchantChargeDAOImpl extends AbstractBaseDAO<MerchantChargeEntity> implements MerchantChargeDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public MerchantChargeDAOImpl()
	{
		super();
		this.setEntityClass(MerchantChargeEntity.class);
	}

	@Override
	public void createMerchantCharge(MerchantChargeEntity merchantChargeEntity)
			throws DAOException {
		save(merchantChargeEntity);
		
	}

	@Override
	public void updateMerchantCharge(MerchantChargeEntity merchantChargeEntity)
			throws DAOException {
		update(merchantChargeEntity);
		
	}

	@Override
	public void deleteMerchantCharge(MerchantChargeEntity merchantChargeEntity)
			throws DAOException {
		deletePhysical(merchantChargeEntity);
		
	}

	@Override
	public List<MerchantChargeEntity> getChargeByMerchant(MerchantEntity merchantEntity) throws DAOException {
		List<MerchantChargeEntity> chargeEntityList = new ArrayList<MerchantChargeEntity>();
		try {
			chargeEntityList = em.createQuery("select b from MerchantChargeEntity b where b.merchantEntity.merchantUuid = :UUID and b.chargeStatus = :CHARGESTATUS and  b.isDeleted=:isDeleted order by b.createDate desc",MerchantChargeEntity.class).setParameter("UUID", merchantEntity.getMerchantUuid()).setParameter("CHARGESTATUS", "1").setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return chargeEntityList;
	}
	
	@Override
	public List<MerchantChargeEntity> getChargeByStatus(String status) throws DAOException {
		List<MerchantChargeEntity> chargeEntityList = new ArrayList<MerchantChargeEntity>();
		try {
			chargeEntityList = em.createQuery("select b from MerchantChargeEntity b where b.chargeStatus = :STATUS and  b.isDeleted=:isDeleted order by b.createDate desc",MerchantChargeEntity.class).setParameter("STATUS", status).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return chargeEntityList;
	}
	
	@Override
	public List<MerchantChargeEntity> getCharges() throws DAOException {
		List<MerchantChargeEntity> chargeEntityList = new ArrayList<MerchantChargeEntity>();
		try {
			chargeEntityList = em.createQuery("select b from MerchantChargeEntity b where  b.isDeleted=:isDeleted order by b.createDate desc",MerchantChargeEntity.class).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return chargeEntityList;
	}
	
	@Override
	public MerchantChargeEntity getChargeByUuid(String uuid) throws DAOException {
		MerchantChargeEntity chargeEntity = null;
		try {
			chargeEntity = em.createQuery("select b from MerchantChargeEntity b where b.merchantChargeUuid = :UUID and  b.isDeleted=:isDeleted",MerchantChargeEntity.class).setParameter("UUID", uuid).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return chargeEntity;
	}
	
	@Override
	public MerchantChargeEntity getChargeByNo(String no) throws DAOException {
		MerchantChargeEntity chargeEntity = null;
		try {
			chargeEntity = em.createQuery("select b from MerchantChargeEntity b where b.chargeNo = :CHARGENO and  b.isDeleted=:isDeleted",MerchantChargeEntity.class).setParameter("CHARGENO", no).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return chargeEntity;
	}
	
	@Override
	public List<MerchantChargeEntity> searchMerchantCharge(MerchantChargeSearchDTO merchantChargeSearchDTO) throws DAOException {
		List<MerchantChargeEntity> merchantChargeEntityList = new ArrayList<MerchantChargeEntity>();
		String query = "select b from MerchantChargeEntity b where b.chargeStatus = '1' and b.isDeleted=:isDeleted";
		String[] keyArray = merchantChargeSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (MerchantChargeSearchDTO.KEY_MOBILENO.equals(key)) {
				query = query + " and b.merchantEntity.mobileNo like :MOBILENO";
			} else if (MerchantChargeSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid = :MERCHANTUUID";
			} else if (MerchantChargeSearchDTO.KEY_MERCHANTNAME.equals(key)) {
				query = query + " and b.merchantEntity.merchantName like :MERCHANTNAME";
			} else if (MerchantChargeSearchDTO.KEY_CHARGEDATE.equals(key)) {
				query = query + " and date(b.chargeTime) >= date(:CHARGETIMESTART) and date(b.chargeTime) <= date(:CHARGETIMEEND)";
			} else if (MerchantChargeSearchDTO.KEY_AMOUNT.equals(key)) {
				query = query + " and b.chargeAmount >= :MINAMOUNT and b.chargeAmount <= :MAXAMOUNT";
			} else if (MerchantChargeSearchDTO.KEY_POINT.equals(key)) {
				query = query + " and b.chargePoint >= :MINPOINT and b.chargePoint <= :MAXPOINT";
			} else if(MerchantChargeSearchDTO.KEY_REFERRER.equals(key)){
				query = query + " and b.merchantEntity.referrer = :REFERRER";
			}
		}
		 query = query + " order by b.chargeTime desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, MerchantChargeEntity.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (MerchantChargeSearchDTO.KEY_MOBILENO.equals(key)) {
					typedQuery.setParameter("MOBILENO",
							"%" + merchantChargeSearchDTO.getMobileNo() + "%");
				} else if (MerchantChargeSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							merchantChargeSearchDTO.getMerchantUuid());
				} else if (MerchantChargeSearchDTO.KEY_MERCHANTNAME.equals(key)) {
					typedQuery.setParameter("MERCHANTNAME",
							"%" + merchantChargeSearchDTO.getMerchantName() + "%");
				} else if (MerchantChargeSearchDTO.KEY_CHARGEDATE.equals(key)) {
					typedQuery.setParameter("CHARGETIMESTART", merchantChargeSearchDTO.getChargeDateStart());
					typedQuery.setParameter("CHARGETIMEEND", merchantChargeSearchDTO.getChargeDateEnd());
				} else if (MerchantChargeSearchDTO.KEY_AMOUNT.equals(key)) {
					typedQuery.setParameter("MINAMOUNT", merchantChargeSearchDTO.getMinAmount());
					typedQuery.setParameter("MAXAMOUNT", merchantChargeSearchDTO.getMaxAmount());
				} else if (MerchantChargeSearchDTO.KEY_REFERRER.equals(key)) {
					typedQuery.setParameter("REFERRER", merchantChargeSearchDTO.getReferrer());
				}
			}
			merchantChargeEntityList = typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(merchantChargeSearchDTO.getStartIndex()).setMaxResults(merchantChargeSearchDTO.getPageSize())
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return merchantChargeEntityList;
	}
	
	@Override
	public int searchMerchantChargeTotal(MerchantChargeSearchDTO merchantChargeSearchDTO) throws DAOException {
		Long total = Long.valueOf(0);
		String query = "select count(b) from MerchantChargeEntity b where b.chargeStatus = '1' and b.isDeleted=:isDeleted";
		String[] keyArray = merchantChargeSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (MerchantChargeSearchDTO.KEY_MOBILENO.equals(key)) {
				query = query + " and b.merchantEntity.mobileNo like :MOBILENO";
			} else if (MerchantChargeSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid = :MERCHANTUUID";
			} else if (MerchantChargeSearchDTO.KEY_MERCHANTNAME.equals(key)) {
				query = query + " and b.merchantEntity.merchantName like :MERCHANTNAME";
			} else if (MerchantChargeSearchDTO.KEY_CHARGEDATE.equals(key)) {
				query = query + " and date(b.chargeTime) >= date(:CHARGETIMESTART) and date(b.chargeTime) <= date(:CHARGETIMEEND)";
			} else if (MerchantChargeSearchDTO.KEY_AMOUNT.equals(key)) {
				query = query + " and b.chargeAmount >= :MINAMOUNT and b.chargeAmount <= :MAXAMOUNT";
			} else if (MerchantChargeSearchDTO.KEY_POINT.equals(key)) {
				query = query + " and b.chargePoint >= :MINPOINT and b.chargePoint <= :MAXPOINT";
			} else if(MerchantChargeSearchDTO.KEY_REFERRER.equals(key)){
				query = query + " and b.merchantEntity.referrer = :REFERRER";
			}
		}
		 query = query + " order by b.chargeTime desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (MerchantChargeSearchDTO.KEY_MOBILENO.equals(key)) {
					typedQuery.setParameter("MOBILENO",
							"%" + merchantChargeSearchDTO.getMobileNo() + "%");
				} else if (MerchantChargeSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							merchantChargeSearchDTO.getMerchantUuid());
				} else if (MerchantChargeSearchDTO.KEY_MERCHANTNAME.equals(key)) {
					typedQuery.setParameter("MERCHANTNAME",
							"%" + merchantChargeSearchDTO.getMerchantName() + "%");
				} else if (MerchantChargeSearchDTO.KEY_CHARGEDATE.equals(key)) {
					typedQuery.setParameter("CHARGETIMESTART", merchantChargeSearchDTO.getChargeDateStart());
					typedQuery.setParameter("CHARGETIMEEND", merchantChargeSearchDTO.getChargeDateEnd());
				} else if (MerchantChargeSearchDTO.KEY_AMOUNT.equals(key)) {
					typedQuery.setParameter("MINAMOUNT", merchantChargeSearchDTO.getMinAmount());
					typedQuery.setParameter("MAXAMOUNT", merchantChargeSearchDTO.getMaxAmount());
				} else if (MerchantChargeSearchDTO.KEY_REFERRER.equals(key)) {
					typedQuery.setParameter("REFERRER", merchantChargeSearchDTO.getReferrer());
				}
			}
			total = (Long) typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return total==null?0:total.intValue();
	}
	
	@Override
	public int searchMerchantChargePoint(MerchantChargeSearchDTO merchantChargeSearchDTO) throws DAOException {
		Long total = Long.valueOf(0);
		String query = "select sum(b.chargePoint) from MerchantChargeEntity b where b.chargeStatus = '1' and b.isDeleted=:isDeleted";
		String[] keyArray = merchantChargeSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (MerchantChargeSearchDTO.KEY_MOBILENO.equals(key)) {
				query = query + " and b.merchantEntity.mobileNo like :MOBILENO";
			} else if (MerchantChargeSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid = :MERCHANTUUID";
			} else if (MerchantChargeSearchDTO.KEY_MERCHANTNAME.equals(key)) {
				query = query + " and b.merchantEntity.merchantName like :MERCHANTNAME";
			} else if (MerchantChargeSearchDTO.KEY_CHARGEDATE.equals(key)) {
				query = query + " and date(b.chargeTime) >= date(:CHARGETIMESTART) and date(b.chargeTime) <= date(:CHARGETIMEEND)";
			} else if (MerchantChargeSearchDTO.KEY_AMOUNT.equals(key)) {
				query = query + " and b.chargeAmount >= :MINAMOUNT and b.chargeAmount <= :MAXAMOUNT";
			} else if (MerchantChargeSearchDTO.KEY_POINT.equals(key)) {
				query = query + " and b.chargePoint >= :MINPOINT and b.chargePoint <= :MAXPOINT";
			} else if(MerchantChargeSearchDTO.KEY_REFERRER.equals(key)){
				query = query + " and b.merchantEntity.referrer = :REFERRER";
			}
		}
		 query = query + " order by b.chargeTime desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (MerchantChargeSearchDTO.KEY_MOBILENO.equals(key)) {
					typedQuery.setParameter("MOBILENO",
							"%" + merchantChargeSearchDTO.getMobileNo() + "%");
				} else if (MerchantChargeSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							merchantChargeSearchDTO.getMerchantUuid());
				} else if (MerchantChargeSearchDTO.KEY_MERCHANTNAME.equals(key)) {
					typedQuery.setParameter("MERCHANTNAME",
							"%" + merchantChargeSearchDTO.getMerchantName() + "%");
				} else if (MerchantChargeSearchDTO.KEY_CHARGEDATE.equals(key)) {
					typedQuery.setParameter("CHARGETIMESTART", merchantChargeSearchDTO.getChargeDateStart());
					typedQuery.setParameter("CHARGETIMEEND", merchantChargeSearchDTO.getChargeDateEnd());
				} else if (MerchantChargeSearchDTO.KEY_AMOUNT.equals(key)) {
					typedQuery.setParameter("MINAMOUNT", merchantChargeSearchDTO.getMinAmount());
					typedQuery.setParameter("MAXAMOUNT", merchantChargeSearchDTO.getMaxAmount());
				} else if (MerchantChargeSearchDTO.KEY_REFERRER.equals(key)) {
					typedQuery.setParameter("REFERRER", merchantChargeSearchDTO.getReferrer());
				}
			}
			total = (Long) typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return total==null?0:total.intValue();
	}
	
	@Override
	public BigDecimal searchMerchantChargeAmount(MerchantChargeSearchDTO merchantChargeSearchDTO) throws DAOException {
		BigDecimal total = BigDecimal.valueOf(0);
		String query = "select sum(b.chargeAmount) from MerchantChargeEntity b where b.chargeStatus = '1' and b.isDeleted=:isDeleted";
		String[] keyArray = merchantChargeSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (MerchantChargeSearchDTO.KEY_MOBILENO.equals(key)) {
				query = query + " and b.merchantEntity.mobileNo like :MOBILENO";
			} else if (MerchantChargeSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid = :MERCHANTUUID";
			} else if (MerchantChargeSearchDTO.KEY_MERCHANTNAME.equals(key)) {
				query = query + " and b.merchantEntity.merchantName like :MERCHANTNAME";
			} else if (MerchantChargeSearchDTO.KEY_CHARGEDATE.equals(key)) {
				query = query + " and date(b.chargeTime) >= date(:CHARGETIMESTART) and date(b.chargeTime) <= date(:CHARGETIMEEND)";
			} else if (MerchantChargeSearchDTO.KEY_AMOUNT.equals(key)) {
				query = query + " and b.chargeAmount >= :MINAMOUNT and b.chargeAmount <= :MAXAMOUNT";
			} else if (MerchantChargeSearchDTO.KEY_POINT.equals(key)) {
				query = query + " and b.chargePoint >= :MINPOINT and b.chargePoint <= :MAXPOINT";
			} else if (MerchantChargeSearchDTO.KEY_MERCHANTCHARGEDATE.equals(key)) {
				query = query + " and date(b.chargeTime) >= date(:CHARGETIMESTART) and date(b.chargeTime) <= date(:CHARGETIMEEND) and "
						+ "b.merchantEntity.merchantUuid = :MERCHANTUUID";
			} else if(MerchantChargeSearchDTO.KEY_REFERRER.equals(key)){
				query = query + " and b.merchantEntity.referrer = :REFERRER";
			}
		}
		 query = query + " order by b.chargeTime desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, BigDecimal.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (MerchantChargeSearchDTO.KEY_MOBILENO.equals(key)) {
					typedQuery.setParameter("MOBILENO",
							"%" + merchantChargeSearchDTO.getMobileNo() + "%");
				} else if (MerchantChargeSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							merchantChargeSearchDTO.getMerchantUuid());
				} else if (MerchantChargeSearchDTO.KEY_MERCHANTNAME.equals(key)) {
					typedQuery.setParameter("MERCHANTNAME",
							"%" + merchantChargeSearchDTO.getMerchantName() + "%");
				} else if (MerchantChargeSearchDTO.KEY_CHARGEDATE.equals(key)) {
					typedQuery.setParameter("CHARGETIMESTART", merchantChargeSearchDTO.getChargeDateStart());
					typedQuery.setParameter("CHARGETIMEEND", merchantChargeSearchDTO.getChargeDateEnd());
				} else if (MerchantChargeSearchDTO.KEY_AMOUNT.equals(key)) {
					typedQuery.setParameter("MINAMOUNT", merchantChargeSearchDTO.getMinAmount());
					typedQuery.setParameter("MAXAMOUNT", merchantChargeSearchDTO.getMaxAmount());
				} else if (MerchantChargeSearchDTO.KEY_MERCHANTCHARGEDATE.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",merchantChargeSearchDTO.getMerchantUuid());
					typedQuery.setParameter("CHARGETIMESTART", merchantChargeSearchDTO.getChargeDateStart());
					typedQuery.setParameter("CHARGETIMEEND", merchantChargeSearchDTO.getChargeDateEnd());
				} else if (MerchantChargeSearchDTO.KEY_REFERRER.equals(key)) {
					typedQuery.setParameter("REFERRER", merchantChargeSearchDTO.getReferrer());
				}
			}
			total = (BigDecimal) typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return total == null?BigDecimal.valueOf(0):total;
	}

	@Override
	public BigDecimal getChargeTotalByMerchant(MerchantEntity merchantEntity)
			throws DAOException {
		BigDecimal total = BigDecimal.valueOf(0);
		try {
			total = em.createQuery("select sum(chargeAmount) from MerchantChargeEntity b where b.merchantEntity.merchantUuid = :UUID and b.chargeStatus =:STATUS and b.isDeleted = :isDeleted group by b.merchantEntity.merchantUuid",BigDecimal.class)
					.setParameter("UUID", merchantEntity.getMerchantUuid())
					.setParameter("STATUS", "1")
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for charge : "+merchantEntity.getMerchantUuid());
		}
		return total == null?BigDecimal.valueOf(0):total;
	}

}
