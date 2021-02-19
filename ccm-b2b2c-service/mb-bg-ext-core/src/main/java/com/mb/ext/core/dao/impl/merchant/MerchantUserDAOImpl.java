package com.mb.ext.core.dao.impl.merchant;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.merchant.MerchantUserDAO;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.merchant.MerchantUserEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("merchantUserDAO")
@Qualifier("merchantUserDAO")
public class MerchantUserDAOImpl extends AbstractBaseDAO<MerchantUserEntity> implements MerchantUserDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public MerchantUserDAOImpl()
	{
		super();
		this.setEntityClass(MerchantUserEntity.class);
	}

	@Override
	public void createMerchantUser(MerchantUserEntity merchantUserEntity)
			throws DAOException {
		save(merchantUserEntity);
		
	}

	@Override
	public void updateMerchantUser(MerchantUserEntity merchantUserEntity)
			throws DAOException {
		update(merchantUserEntity);
		
	}

	@Override
	public void deleteMerchantUser(MerchantUserEntity merchantUserEntity)
			throws DAOException {
		deletePhysical(merchantUserEntity);
		
	}

	@Override
	public List<UserEntity> getUsersByMerchant(MerchantEntity merchantEntity) throws DAOException {
		List<UserEntity> userEntityList = null;
		try {
			userEntityList = em.createQuery("select b.userEntity from MerchantUserEntity b where b.merchantEntity.merchantUuid = :UUID and  b.isDeleted=:isDeleted",UserEntity.class).setParameter("UUID", merchantEntity.getMerchantUuid()).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchant user");
		}
		return userEntityList;
	}
	
	@Override
	public List<MerchantEntity> searchMerchantByUser(UserEntity userEntity, int startIndex, int pageSize) throws DAOException {
		List<MerchantEntity> merchantEntityList = null;
		try {
			merchantEntityList = em.createQuery("select b.merchantEntity from MerchantUserEntity b where b.userEntity.userUuid = :UUID and  b.isDeleted=:isDeleted",MerchantEntity.class).setParameter("UUID", userEntity.getUserUuid()).setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(startIndex)
					.setMaxResults(pageSize)
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for merchant");
		}
		return merchantEntityList;
	}
	
	@Override
	public int searchMerchantByUserTotal(UserEntity userEntity) throws DAOException {
		Long total = null;
		try {
			total = em.createQuery("select count(b.merchantEntity) from MerchantUserEntity b where b.userEntity.userUuid = :UUID and  b.isDeleted=:isDeleted",Long.class).setParameter("UUID", userEntity.getUserUuid()).setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchant");
		}
		return total==null?0:total.intValue();
	}
	
	@Override
	public MerchantUserEntity getMerchantUser(MerchantEntity merchantEntity, UserEntity userEntity) throws DAOException {
		MerchantUserEntity merchantUserEntity = null;
		try {
			merchantUserEntity = em.createQuery("select b from MerchantUserEntity b where b.merchantEntity.merchantUuid = :MERCHANTUUID and b.userEntity.userUuid = :USERUUID and b.isDeleted=:isDeleted",MerchantUserEntity.class).setParameter("MERCHANTUUID", merchantEntity.getMerchantUuid()).setParameter("USERUUID", userEntity.getUserUuid()).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for merchant user");
		}
		return merchantUserEntity;
	}

}
