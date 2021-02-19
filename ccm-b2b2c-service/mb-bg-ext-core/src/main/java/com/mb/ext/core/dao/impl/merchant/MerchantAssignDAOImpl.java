package com.mb.ext.core.dao.impl.merchant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.merchant.MerchantAssignDAO;
import com.mb.ext.core.entity.merchant.MerchantAssignEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.service.spec.MerchantAssignSearchDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("merchantAssignDAO")
@Qualifier("merchantAssignDAO")
public class MerchantAssignDAOImpl extends AbstractBaseDAO<MerchantAssignEntity> implements MerchantAssignDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public MerchantAssignDAOImpl()
	{
		super();
		this.setEntityClass(MerchantAssignEntity.class);
	}

	@Override
	public void createMerchantAssign(MerchantAssignEntity merchantAssignEntity)
			throws DAOException {
		save(merchantAssignEntity);
		
	}

	@Override
	public void updateMerchantAssign(MerchantAssignEntity merchantAssignEntity)
			throws DAOException {
		update(merchantAssignEntity);
		
	}

	@Override
	public void deleteMerchantAssign(MerchantAssignEntity merchantAssignEntity)
			throws DAOException {
		deletePhysical(merchantAssignEntity);
			
	}

	@Override
	public MerchantAssignEntity getMerchantAssignByAssignNo(String assignNo) throws DAOException {
		MerchantAssignEntity assignEntity = null;
		try {
			assignEntity = em.createQuery("select b from MerchantAssignEntity b where b.assignNo = :ASSIGNNO and  b.isDeleted=:isDeleted",MerchantAssignEntity.class).setParameter("ASSIGNNO", assignNo).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchant assign");
		}
		return assignEntity;
	}
	
	@Override
	public MerchantAssignEntity getMerchantAssignByUuid(String uuid) throws DAOException {
		MerchantAssignEntity assignEntity = null;
		try {
			assignEntity = em.createQuery("select b from MerchantAssignEntity b where b.merchantAssignUuid = :UUID and  b.isDeleted=:isDeleted",MerchantAssignEntity.class).setParameter("UUID", uuid).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchant assign");
		}
		return assignEntity;
	}
	
	@Override
	public List<MerchantAssignEntity> searchMerchantAssign(MerchantAssignSearchDTO merchantAssignSearchDTO) throws DAOException {
		List<MerchantAssignEntity> merchantAssignEntityList = new ArrayList<MerchantAssignEntity>();
		String query = "select b from MerchantAssignEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = merchantAssignSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (MerchantAssignSearchDTO.KEY_MOBILENO.equals(key)) {
				query = query + " and b.merchantEntity.mobileNo like :MOBILENO";
			} else if (MerchantAssignSearchDTO.KEY_USER.equals(key)) {
				query = query + " and b.userEntity.userUuid = :USERUUID";
			} else if (MerchantAssignSearchDTO.KEY_USER_NAME.equals(key)) {
				query = query + " and b.userEntity.name like :USERNAME";
			} else if (MerchantAssignSearchDTO.KEY_USER_PERSONALPHONE.equals(key)) {
				query = query + " and b.userEntity.personalPhone like :USERPERSONALPHONE";
			} else if (MerchantAssignSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid = :MERCHANTUUID";
			} else if (MerchantAssignSearchDTO.KEY_MERCHANTNAME.equals(key)) {
				query = query + " and b.merchantEntity.merchantName like :MERCHANTNAME";
			} else if (MerchantAssignSearchDTO.KEY_ASSIGNDATE.equals(key)) {
				query = query + " and date(b.assignTime) >= date(:ASSIGNTIMESTART) and date(b.assignTime) <= date(:ASSIGNTIMEEND)";
			} else if (MerchantAssignSearchDTO.KEY_AMOUNT.equals(key)) {
				query = query + " and b.tranAmount >= :MINAMOUNT and b.tranAmount <= :MAXAMOUNT";
			} else if (MerchantAssignSearchDTO.KEY_POINT.equals(key)) {
				query = query + " and b.assignPoint >= :MINPOINT and b.assignPoint <= :MAXPOINT";
			}
		}
		 query = query + " order by b.assignTime desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, MerchantAssignEntity.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (MerchantAssignSearchDTO.KEY_MOBILENO.equals(key)) {
					typedQuery.setParameter("MOBILENO",
							"%" + merchantAssignSearchDTO.getMobileNo() + "%");
				} else if (MerchantAssignSearchDTO.KEY_USER.equals(key)) {
					typedQuery.setParameter("USERUUID",
							merchantAssignSearchDTO.getUserUuid());
				} else if (MerchantAssignSearchDTO.KEY_USER_NAME.equals(key)) {
					typedQuery.setParameter("USERNAME",
							"%"+merchantAssignSearchDTO.getUserName()+"%");
				} else if (MerchantAssignSearchDTO.KEY_USER_PERSONALPHONE.equals(key)) {
					typedQuery.setParameter("USERPERSONALPHONE",
							"%"+merchantAssignSearchDTO.getUserPersonalPhone()+"%");
				} else if (MerchantAssignSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							merchantAssignSearchDTO.getMerchantUuid());
				} else if (MerchantAssignSearchDTO.KEY_MERCHANTNAME.equals(key)) {
					typedQuery.setParameter("MERCHANTNAME",
							"%" + merchantAssignSearchDTO.getMerchantName() + "%");
				} else if (MerchantAssignSearchDTO.KEY_ASSIGNDATE.equals(key)) {
					typedQuery.setParameter("ASSIGNTIMESTART", merchantAssignSearchDTO.getAssignDateStart());
					typedQuery.setParameter("ASSIGNTIMEEND", merchantAssignSearchDTO.getAssignDateEnd());
				} else if (MerchantAssignSearchDTO.KEY_AMOUNT.equals(key)) {
					typedQuery.setParameter("MINAMOUNT", merchantAssignSearchDTO.getMinAmount());
					typedQuery.setParameter("MAXAMOUNT", merchantAssignSearchDTO.getMaxAmount());
				}
			}
			merchantAssignEntityList = typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(merchantAssignSearchDTO.getStartIndex()).setMaxResults(merchantAssignSearchDTO.getPageSize())
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return merchantAssignEntityList;
	}
	
	@Override
	public int searchMerchantAssignTotal(MerchantAssignSearchDTO merchantAssignSearchDTO) throws DAOException {
		Long total = Long.valueOf(0);
		String query = "select count(b) from MerchantAssignEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = merchantAssignSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (MerchantAssignSearchDTO.KEY_MOBILENO.equals(key)) {
				query = query + " and b.merchantEntity.mobileNo like :MOBILENO";
			} else if (MerchantAssignSearchDTO.KEY_USER.equals(key)) {
				query = query + " and b.userEntity.userUuid = :USERUUID";
			} else if (MerchantAssignSearchDTO.KEY_USER_NAME.equals(key)) {
				query = query + " and b.userEntity.name like :USERNAME";
			} else if (MerchantAssignSearchDTO.KEY_USER_PERSONALPHONE.equals(key)) {
				query = query + " and b.userEntity.personalPhone = :USERPERSONALPHONE";
			} else if (MerchantAssignSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid = :MERCHANTUUID";
			} else if (MerchantAssignSearchDTO.KEY_MERCHANTNAME.equals(key)) {
				query = query + " and b.merchantEntity.merchantName like :MERCHANTNAME";
			} else if (MerchantAssignSearchDTO.KEY_ASSIGNDATE.equals(key)) {
				query = query + " and date(b.assignTime) >= date(:ASSIGNTIMESTART) and date(b.assignTime) <= date(:ASSIGNTIMEEND)";
			}  else if (MerchantAssignSearchDTO.KEY_AMOUNT.equals(key)) {
				query = query + " and b.tranAmount >= :MINAMOUNT and b.tranAmount <= :MAXAMOUNT";
			} else if (MerchantAssignSearchDTO.KEY_POINT.equals(key)) {
				query = query + " and b.assignPoint >= :MINPOINT and b.assignPoint <= :MAXPOINT";
			}
		}
		 query = query + " order by b.assignTime desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (MerchantAssignSearchDTO.KEY_MOBILENO.equals(key)) {
					typedQuery.setParameter("MOBILENO",
							"%" + merchantAssignSearchDTO.getMobileNo() + "%");
				} else if (MerchantAssignSearchDTO.KEY_USER.equals(key)) {
					typedQuery.setParameter("USERUUID",
							merchantAssignSearchDTO.getUserUuid());
				} else if (MerchantAssignSearchDTO.KEY_USER_NAME.equals(key)) {
					typedQuery.setParameter("USERNAME",
							"%"+merchantAssignSearchDTO.getUserName()+"%");
				} else if (MerchantAssignSearchDTO.KEY_USER_PERSONALPHONE.equals(key)) {
					typedQuery.setParameter("USERPERSONALPHONE",
							merchantAssignSearchDTO.getUserPersonalPhone());
				} else if (MerchantAssignSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							merchantAssignSearchDTO.getMerchantUuid());
				} else if (MerchantAssignSearchDTO.KEY_MERCHANTNAME.equals(key)) {
					typedQuery.setParameter("MERCHANTNAME",
							"%" + merchantAssignSearchDTO.getMerchantName() + "%");
				} else if (MerchantAssignSearchDTO.KEY_ASSIGNDATE.equals(key)) {
					typedQuery.setParameter("ASSIGNTIMESTART", merchantAssignSearchDTO.getAssignDateStart());
					typedQuery.setParameter("ASSIGNTIMEEND", merchantAssignSearchDTO.getAssignDateEnd());
				}  else if (MerchantAssignSearchDTO.KEY_AMOUNT.equals(key)) {
					typedQuery.setParameter("MINAMOUNT", merchantAssignSearchDTO.getMinAmount());
					typedQuery.setParameter("MAXAMOUNT", merchantAssignSearchDTO.getMaxAmount());
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
	public int searchMerchantAssignPoint(MerchantAssignSearchDTO merchantAssignSearchDTO) throws DAOException {
		Long total = Long.valueOf(0);
		String query = "select sum(b.assignPoint) from MerchantAssignEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = merchantAssignSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (MerchantAssignSearchDTO.KEY_MOBILENO.equals(key)) {
				query = query + " and b.merchantEntity.mobileNo like :MOBILENO";
			} else if (MerchantAssignSearchDTO.KEY_USER.equals(key)) {
				query = query + " and b.userEntity.userUuid = :USERUUID";
			} else if (MerchantAssignSearchDTO.KEY_USER_NAME.equals(key)) {
				query = query + " and b.userEntity.name like :USERNAME";
			} else if (MerchantAssignSearchDTO.KEY_USER_PERSONALPHONE.equals(key)) {
				query = query + " and b.userEntity.personalPhone = :USERPERSONALPHONE";
			} else if (MerchantAssignSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid = :MERCHANTUUID";
			} else if (MerchantAssignSearchDTO.KEY_MERCHANTNAME.equals(key)) {
				query = query + " and b.merchantEntity.merchantName like :MERCHANTNAME";
			} else if (MerchantAssignSearchDTO.KEY_ASSIGNDATE.equals(key)) {
				query = query + " and date(b.assignTime) >= date(:ASSIGNTIMESTART) and date(b.assignTime) <= date(:ASSIGNTIMEEND)";
			}  else if (MerchantAssignSearchDTO.KEY_AMOUNT.equals(key)) {
				query = query + " and b.tranAmount >= :MINAMOUNT and b.tranAmount <= :MAXAMOUNT";
			} else if (MerchantAssignSearchDTO.KEY_POINT.equals(key)) {
				query = query + " and b.assignPoint >= :MINPOINT and b.assignPoint <= :MAXPOINT";
			}
		}
		 query = query + " order by b.assignTime desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (MerchantAssignSearchDTO.KEY_MOBILENO.equals(key)) {
					typedQuery.setParameter("MOBILENO",
							"%" + merchantAssignSearchDTO.getMobileNo() + "%");
				} else if (MerchantAssignSearchDTO.KEY_USER.equals(key)) {
					typedQuery.setParameter("USERUUID",
							merchantAssignSearchDTO.getUserUuid());
				} else if (MerchantAssignSearchDTO.KEY_USER_NAME.equals(key)) {
					typedQuery.setParameter("USERNAME",
							"%"+merchantAssignSearchDTO.getUserName()+"%");
				} else if (MerchantAssignSearchDTO.KEY_USER_PERSONALPHONE.equals(key)) {
					typedQuery.setParameter("USERPERSONALPHONE",
							merchantAssignSearchDTO.getUserPersonalPhone());
				} else if (MerchantAssignSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							merchantAssignSearchDTO.getMerchantUuid() );
				} else if (MerchantAssignSearchDTO.KEY_MERCHANTNAME.equals(key)) {
					typedQuery.setParameter("MERCHANTNAME",
							"%" + merchantAssignSearchDTO.getMerchantName() + "%");
				} else if (MerchantAssignSearchDTO.KEY_ASSIGNDATE.equals(key)) {
					typedQuery.setParameter("ASSIGNTIMESTART", merchantAssignSearchDTO.getAssignDateStart());
					typedQuery.setParameter("ASSIGNTIMEEND", merchantAssignSearchDTO.getAssignDateEnd());
				} else if (MerchantAssignSearchDTO.KEY_AMOUNT.equals(key)) {
					typedQuery.setParameter("MINAMOUNT", merchantAssignSearchDTO.getMinAmount());
					typedQuery.setParameter("MAXAMOUNT", merchantAssignSearchDTO.getMaxAmount());
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
	public BigDecimal searchMerchantAssignAmount(MerchantAssignSearchDTO merchantAssignSearchDTO) throws DAOException {
		BigDecimal total = BigDecimal.valueOf(0);
		String query = "select sum(b.tranAmount) from MerchantAssignEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = merchantAssignSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (MerchantAssignSearchDTO.KEY_MOBILENO.equals(key)) {
				query = query + " and b.merchantEntity.mobileNo like :MOBILENO";
			} else if (MerchantAssignSearchDTO.KEY_USER.equals(key)) {
				query = query + " and b.userEntity.userUuid = :USERUUID";
			} else if (MerchantAssignSearchDTO.KEY_USER_NAME.equals(key)) {
				query = query + " and b.userEntity.name like :USERNAME";
			} else if (MerchantAssignSearchDTO.KEY_USER_PERSONALPHONE.equals(key)) {
				query = query + " and b.userEntity.personalPhone = :USERPERSONALPHONE";
			} else if (MerchantAssignSearchDTO.KEY_MERCHANT.equals(key)) {
				query = query + " and b.merchantEntity.merchantUuid = :MERCHANTUUID";
			} else if (MerchantAssignSearchDTO.KEY_MERCHANTNAME.equals(key)) {
				query = query + " and b.merchantEntity.merchantName like :MERCHANTNAME";
			} else if (MerchantAssignSearchDTO.KEY_ASSIGNDATE.equals(key)) {
				query = query + " and date(b.assignTime) >= date(:ASSIGNTIMESTART) and date(b.assignTime) <= date(:ASSIGNTIMEEND)";
			} else if (MerchantAssignSearchDTO.KEY_AMOUNT.equals(key)) {
				query = query + " and b.tranAmount >= :MINAMOUNT and b.tranAmount <= :MAXAMOUNT";
			} else if (MerchantAssignSearchDTO.KEY_POINT.equals(key)) {
				query = query + " and b.assignPoint >= :MINPOINT and b.assignPoint <= :MAXPOINT";
			}
		}
		 query = query + " order by b.assignTime desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, BigDecimal.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (MerchantAssignSearchDTO.KEY_MOBILENO.equals(key)) {
					typedQuery.setParameter("MOBILENO",
							"%" + merchantAssignSearchDTO.getMobileNo() + "%");
				} else if (MerchantAssignSearchDTO.KEY_USER.equals(key)) {
					typedQuery.setParameter("USERUUID",
							merchantAssignSearchDTO.getUserUuid());
				} else if (MerchantAssignSearchDTO.KEY_USER_NAME.equals(key)) {
					typedQuery.setParameter("USERNAME",
							"%"+merchantAssignSearchDTO.getUserName()+"%");
				} else if (MerchantAssignSearchDTO.KEY_USER_PERSONALPHONE.equals(key)) {
					typedQuery.setParameter("USERPERSONALPHONE",
							merchantAssignSearchDTO.getUserPersonalPhone());
				} else if (MerchantAssignSearchDTO.KEY_MERCHANT.equals(key)) {
					typedQuery.setParameter("MERCHANTUUID",
							merchantAssignSearchDTO.getMerchantUuid() );
				} else if (MerchantAssignSearchDTO.KEY_MERCHANTNAME.equals(key)) {
					typedQuery.setParameter("MERCHANTNAME",
							"%" + merchantAssignSearchDTO.getMerchantName() + "%");
				} else if (MerchantAssignSearchDTO.KEY_ASSIGNDATE.equals(key)) {
					typedQuery.setParameter("ASSIGNTIMESTART", merchantAssignSearchDTO.getAssignDateStart());
					typedQuery.setParameter("ASSIGNTIMEEND", merchantAssignSearchDTO.getAssignDateEnd());
				} else if (MerchantAssignSearchDTO.KEY_AMOUNT.equals(key)) {
					typedQuery.setParameter("MINAMOUNT", merchantAssignSearchDTO.getMinAmount());
					typedQuery.setParameter("MAXAMOUNT", merchantAssignSearchDTO.getMaxAmount());
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
	public List<MerchantAssignEntity> getMerchantAssignByMerchant(
			MerchantEntity merchantEntity) throws DAOException {
		List<MerchantAssignEntity> merchantAssignEntityList = new ArrayList<MerchantAssignEntity>();;
		try {
			merchantAssignEntityList = em.createQuery("select b from MerchantAssignEntity b where b.merchantEntity.merchantUuid = :MERCHANTUUID and b.isDeleted=:isDeleted",MerchantAssignEntity.class).setParameter("MERCHANTUUID", merchantEntity.getMerchantUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchantAssign");
		}
		return merchantAssignEntityList;
	}

	@Override
	public int getAssignPointTotal(MerchantEntity merchantEntity)
			throws DAOException {
		Long total = null;
		try {
			total = em.createQuery("select sum(assignPoint) from MerchantAssignEntity b where b.merchantEntity.merchantUuid = :UUID and b.isDeleted = :isDeleted group by b.merchantEntity.merchantUuid",Long.class)
					.setParameter("UUID", merchantEntity.getMerchantUuid())
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchantAssign : "+merchantEntity.getMerchantUuid());
		}
		return total==null?0:total.intValue();
	}
}
