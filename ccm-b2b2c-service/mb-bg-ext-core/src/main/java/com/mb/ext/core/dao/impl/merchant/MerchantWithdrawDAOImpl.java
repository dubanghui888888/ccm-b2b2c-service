package com.mb.ext.core.dao.impl.merchant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.merchant.MerchantWithdrawDAO;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.merchant.MerchantWithdrawEntity;
import com.mb.ext.core.service.spec.WithdrawSearchDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("merchantWithdrawDAO")
@Qualifier("merchantWithdrawDAO")
public class MerchantWithdrawDAOImpl extends AbstractBaseDAO<MerchantWithdrawEntity> implements MerchantWithdrawDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public MerchantWithdrawDAOImpl()
	{
		super();
		this.setEntityClass(MerchantWithdrawEntity.class);
	}

	@Override
	public void createMerchantWithdraw(MerchantWithdrawEntity merchantWithdrawEntity)
			throws DAOException {
		save(merchantWithdrawEntity);
		
	}

	@Override
	public void updateMerchantWithdraw(MerchantWithdrawEntity merchantWithdrawEntity)
			throws DAOException {
		update(merchantWithdrawEntity);
		
	}

	@Override
	public void deleteMerchantWithdraw(MerchantWithdrawEntity merchantWithdrawEntity)
			throws DAOException {
		deletePhysical(merchantWithdrawEntity);
		
	}

	@Override
	public List<MerchantWithdrawEntity> getWithdrawByMerchant(MerchantEntity merchantEntity) throws DAOException {
		List<MerchantWithdrawEntity> withdrawEntityList = new ArrayList<MerchantWithdrawEntity>();
		try {
			withdrawEntityList = em.createQuery("select b from MerchantWithdrawEntity b where b.merchantEntity.merchantUuid = :UUID and  b.isDeleted=:isDeleted order by b.createDate desc",MerchantWithdrawEntity.class).setParameter("UUID", merchantEntity.getMerchantUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return withdrawEntityList;
	}
	
	@Override
	public List<MerchantWithdrawEntity> getWithdrawByStatus(String status) throws DAOException {
		List<MerchantWithdrawEntity> withdrawEntityList = new ArrayList<MerchantWithdrawEntity>();
		try {
			withdrawEntityList = em.createQuery("select b from MerchantWithdrawEntity b where b.withdrawStatus = :STATUS and  b.isDeleted=:isDeleted order by b.createDate desc",MerchantWithdrawEntity.class).setParameter("STATUS", status).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return withdrawEntityList;
	}
	
	@Override
	public List<MerchantWithdrawEntity> getWithdraws() throws DAOException {
		List<MerchantWithdrawEntity> withdrawEntityList = new ArrayList<MerchantWithdrawEntity>();
		try {
			withdrawEntityList = em.createQuery("select b from MerchantWithdrawEntity b where  b.isDeleted=:isDeleted order by b.createDate desc",MerchantWithdrawEntity.class).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return withdrawEntityList;
	}
	
	@Override
	public MerchantWithdrawEntity getWithdrawByUuid(String uuid) throws DAOException {
		MerchantWithdrawEntity withdrawEntity = new MerchantWithdrawEntity();
		try {
			withdrawEntity = em.createQuery("select b from MerchantWithdrawEntity b where b.merchantWithdrawUuid = :UUID and  b.isDeleted=:isDeleted",MerchantWithdrawEntity.class).setParameter("UUID", uuid).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchant industry");
		}
		return withdrawEntity;
	}
	
	@Override
	public List<MerchantWithdrawEntity> searchMerchantWithdraw(WithdrawSearchDTO withdrawSearchDTO,
			int startIndex, int pageSize) throws DAOException {
		List<MerchantWithdrawEntity> merchantWithdrawEntityList = new ArrayList<MerchantWithdrawEntity>();
		String query = "select b from MerchantWithdrawEntity b where  b.isDeleted=:isDeleted";
		String[] keyArray = withdrawSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (WithdrawSearchDTO.KEY_STATUS.equals(key)) {
				query = query + " and b.withdrawStatus in :WITHDRAWSTATUS";
			}else if (WithdrawSearchDTO.KEY_NAME.equals(key)) {
				query = query + " and b.merchantEntity.merchantName like :NAME";
			}else if (WithdrawSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid = :MERCHANTUUID";
			}else if (WithdrawSearchDTO.KEY_WITHDRAWDATE.equals(key)) {
				query = query
						+ " and date(b.withdrawTime)>= date(:WITHDRAWDATESTART) and date(b.withdrawTime) <= date(:WITHDRAWDATEEND)";
			}
		}
		query = query + " order by b.withdrawTime desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, MerchantWithdrawEntity.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (WithdrawSearchDTO.KEY_STATUS.equals(key)) {
					List<String> statusList = Arrays.asList(withdrawSearchDTO.getWithdrawStatusArray());
					typedQuery.setParameter("WITHDRAWSTATUS",
							statusList);
				}else if (WithdrawSearchDTO.KEY_NAME.equals(key)) {
					typedQuery.setParameter("NAME",
							"%"+withdrawSearchDTO.getName()+"%");
				}else if (WithdrawSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							withdrawSearchDTO.getMerchantUuid());
				} else if (WithdrawSearchDTO.KEY_WITHDRAWDATE.equals(key)) {
					typedQuery.setParameter("WITHDRAWDATESTART",
							withdrawSearchDTO.getWithdrawDateStart());
					typedQuery.setParameter("WITHDRAWDATEEND",
							withdrawSearchDTO.getWithdrawDateEnd());
				}
			}
			merchantWithdrawEntityList = typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(startIndex).setMaxResults(pageSize)
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return merchantWithdrawEntityList;
	}
	
	@Override
	public int searchMerchantWithdrawTotal(WithdrawSearchDTO withdrawSearchDTO) throws DAOException {
		Long total = new Long(0);
		String query = "select count(b) from MerchantWithdrawEntity b where  b.isDeleted=:isDeleted";
		String[] keyArray = withdrawSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (WithdrawSearchDTO.KEY_STATUS.equals(key)) {
				query = query + " and b.withdrawStatus in :WITHDRAWSTATUS";
			}else if (WithdrawSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid = :MERCHANTUUID";
			} else if (WithdrawSearchDTO.KEY_NAME.equals(key)) {
				query = query + " and b.merchantEntity.merchantName like :NAME";
			} else if (WithdrawSearchDTO.KEY_WITHDRAWDATE.equals(key)) {
				query = query
						+ " and date(b.withdrawTime) >= date(:WITHDRAWDATESTART) and date(b.withdrawTime) <= date(:WITHDRAWDATEEND)";
			}
		}
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (WithdrawSearchDTO.KEY_STATUS.equals(key)) {
					List<String> statusList = Arrays.asList(withdrawSearchDTO.getWithdrawStatusArray());
					typedQuery.setParameter("WITHDRAWSTATUS",
							statusList);
				} else if (WithdrawSearchDTO.KEY_NAME.equals(key)) {
					typedQuery.setParameter("NAME",
							"%"+withdrawSearchDTO.getName()+"%");
				}else if (WithdrawSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							withdrawSearchDTO.getMerchantUuid());
				} else if (WithdrawSearchDTO.KEY_WITHDRAWDATE.equals(key)) {
					typedQuery.setParameter("WITHDRAWDATESTART",
							withdrawSearchDTO.getWithdrawDateStart());
					typedQuery.setParameter("WITHDRAWDATEEND",
							withdrawSearchDTO.getWithdrawDateEnd());
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
	public BigDecimal searchMerchantWithdrawTotalAmount(WithdrawSearchDTO withdrawSearchDTO) throws DAOException {
		BigDecimal total = null;
		String query = "select sum(b.withdrawAmount) from MerchantWithdrawEntity b where  b.isDeleted=:isDeleted";
		String[] keyArray = withdrawSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (WithdrawSearchDTO.KEY_STATUS.equals(key)) {
				query = query + " and b.withdrawStatus in :WITHDRAWSTATUS";
			}else if (WithdrawSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid = :MERCHANTUUID";
			} else if (WithdrawSearchDTO.KEY_NAME.equals(key)) {
				query = query + " and b.merchantEntity.merchantName like :NAME";
			} else if (WithdrawSearchDTO.KEY_WITHDRAWDATE.equals(key)) {
				query = query
						+ " and date(b.withdrawTime) >= date(:WITHDRAWDATESTART) and date(b.withdrawTime) <= date(:WITHDRAWDATEEND)";
			}
		}
		try {
			TypedQuery typedQuery = em.createQuery(query, BigDecimal.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (WithdrawSearchDTO.KEY_STATUS.equals(key)) {
					List<String> statusList = Arrays.asList(withdrawSearchDTO.getWithdrawStatusArray());
					typedQuery.setParameter("WITHDRAWSTATUS",
							statusList);
				} else if (WithdrawSearchDTO.KEY_NAME.equals(key)) {
					typedQuery.setParameter("NAME",
							"%"+withdrawSearchDTO.getName()+"%");
				}else if (WithdrawSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							withdrawSearchDTO.getMerchantUuid());
				} else if (WithdrawSearchDTO.KEY_WITHDRAWDATE.equals(key)) {
					typedQuery.setParameter("WITHDRAWDATESTART",
							withdrawSearchDTO.getWithdrawDateStart());
					typedQuery.setParameter("WITHDRAWDATEEND",
							withdrawSearchDTO.getWithdrawDateEnd());
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
}
