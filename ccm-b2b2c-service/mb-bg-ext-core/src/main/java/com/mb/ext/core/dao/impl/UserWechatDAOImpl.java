package com.mb.ext.core.dao.impl;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.UserWechatDAO;
import com.mb.ext.core.entity.UserWechatEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("userWechatDAO")
@Qualifier("userWechatDAO")
public class UserWechatDAOImpl extends AbstractBaseDAO<UserWechatEntity> implements UserWechatDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public UserWechatDAOImpl()
	{
		super();
		this.setEntityClass(UserWechatEntity.class);
	}

	@Override
	public void addUserWechat(UserWechatEntity userWechatEntity) throws DAOException {
		
		save(userWechatEntity);
		
	}

	@Override
	public UserWechatEntity getUserWechatByOpenId(String openId)
			throws DAOException {
		UserWechatEntity userWechatEntity = null;
		try {
			userWechatEntity = (UserWechatEntity)em.createQuery("select b from UserWechatEntity b where b.openId = :OPENID and b.isDeleted=:isDeleted",UserWechatEntity.class).setParameter("OPENID", openId).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for enterprise: "+openId);
		}
		return userWechatEntity;
	}

	@Override
	public void updateUserWechat(UserWechatEntity userWechatEntity)
			throws DAOException {
		update(userWechatEntity);
		
	}

	@Override
	public void deleteUserWechat(UserWechatEntity userWechatEntity)
			throws DAOException {
		deletePhysical(userWechatEntity);
		
	}

	

}
