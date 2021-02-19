package com.mb.ext.core.dao.impl.merchant;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.merchant.MerchantFollowDAO;
import com.mb.ext.core.dao.merchant.MerchantUserDAO;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.merchant.MerchantFollowEntity;
import com.mb.ext.core.entity.merchant.MerchantUserEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@Repository("merchantFollowDAO")
@Qualifier("merchantFollowDAO")
public class MerchantFollowDAOImpl extends AbstractBaseDAO<MerchantFollowEntity> implements MerchantFollowDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public MerchantFollowDAOImpl()
	{
		super();
		this.setEntityClass(MerchantFollowEntity.class);
	}

	@Override
	public void createMerchantFollow(MerchantFollowEntity merchantFollowEntity)
			throws DAOException {
		save(merchantFollowEntity);
		
	}

	@Override
	public void updateMerchantFollow(MerchantFollowEntity merchantFollowEntity)
			throws DAOException {
		update(merchantFollowEntity);
		
	}

	@Override
	public void deleteMerchantFollow(MerchantFollowEntity merchantFollowEntity)
			throws DAOException {
		deletePhysical(merchantFollowEntity);
		
	}

	@Override
	public List<UserEntity> getFollowsByMerchant(MerchantEntity merchantEntity) throws DAOException {
		List<UserEntity> userEntityList = null;
		try {
			userEntityList = em.createQuery("select b.userEntity from MerchantFollowEntity b where b.merchantEntity.merchantUuid = :UUID and  b.isDeleted=:isDeleted",UserEntity.class).setParameter("UUID", merchantEntity.getMerchantUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchant user");
		}
		return userEntityList;
	}

	@Override
	public List<MerchantEntity> getFollowedMerchantsByUser(UserEntity userEntity) throws DAOException {
		List<MerchantEntity> merchantEntityList = null;
		try {
			merchantEntityList = em.createQuery("select b.merchantEntity from MerchantFollowEntity b where b.userEntity.userUuid = :UUID and  b.isDeleted=:isDeleted",MerchantEntity.class).setParameter("UUID", userEntity.getUserUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchant");
		}
		return merchantEntityList;
	}
	
	@Override
	public List<MerchantEntity> searchMerchantByFollow(UserEntity userEntity, int startIndex, int pageSize) throws DAOException {
		List<MerchantEntity> merchantEntityList = null;
		try {
			merchantEntityList = em.createQuery("select b.merchantEntity from MerchantFollowEntity b where b.userEntity.userUuid = :UUID and  b.isDeleted=:isDeleted",MerchantEntity.class).setParameter("UUID", userEntity.getUserUuid()).setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(startIndex)
					.setMaxResults(pageSize)
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchant");
		}
		return merchantEntityList;
	}

	@Override
	public List<UserEntity> searchFollowByMerchant(MerchantEntity merchantEntity, int startIndex, int pageSize) throws DAOException {
		List<UserEntity> userEntityList = null;
		try {
			userEntityList = em.createQuery("select b.userEntity from MerchantFollowEntity b where b.merchantEntity.merchantUuid = :UUID and  b.isDeleted=:isDeleted",UserEntity.class).setParameter("UUID", merchantEntity.getMerchantUuid()).setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(startIndex)
					.setMaxResults(pageSize)
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user");
		}
		return userEntityList;
	}
	
	@Override
	public int searchMerchantByFollowTotal(UserEntity userEntity) throws DAOException {
		Long total = null;
		try {
			total = em.createQuery("select count(b.merchantEntity) from MerchantFollowEntity b where b.userEntity.userUuid = :UUID and  b.isDeleted=:isDeleted",Long.class).setParameter("UUID", userEntity.getUserUuid()).setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchant");
		}
		return total==null?0:total.intValue();
	}

	@Override
	public int searchFollowByMerchantTotal(MerchantEntity merchantEntity) throws DAOException {
		Long total = null;
		try {
			total = em.createQuery("select count(b.userEntity) from MerchantFollowEntity b where b.merchantEntity.merchantUuid = :UUID and  b.isDeleted=:isDeleted",Long.class).setParameter("UUID", merchantEntity.getMerchantUuid()).setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchant");
		}
		return total==null?0:total.intValue();
	}
	
	@Override
	public MerchantFollowEntity getMerchantFollow(MerchantEntity merchantEntity, UserEntity userEntity) throws DAOException {
		MerchantFollowEntity merchantFollowEntity = null;
		try {
			merchantFollowEntity = em.createQuery("select b from MerchantFollowEntity b where b.merchantEntity.merchantUuid = :MERCHANTUUID and b.userEntity.userUuid = :USERUUID and b.isDeleted=:isDeleted",MerchantFollowEntity.class).setParameter("MERCHANTUUID", merchantEntity.getMerchantUuid()).setParameter("USERUUID", userEntity.getUserUuid()).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchant user");
		}
		return merchantFollowEntity;
	}

}
