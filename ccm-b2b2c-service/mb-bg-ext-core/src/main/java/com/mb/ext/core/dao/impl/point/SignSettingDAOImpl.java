package com.mb.ext.core.dao.impl.point;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.point.SignSettingDAO;
import com.mb.ext.core.entity.point.SignSettingEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("signSettingDAO")
@Qualifier("signSettingDAO")
public class SignSettingDAOImpl extends AbstractBaseDAO<SignSettingEntity> implements SignSettingDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public SignSettingDAOImpl()
	{
		super();
		this.setEntityClass(SignSettingEntity.class);
	}

	@Override
	public void addSignSetting(SignSettingEntity settingEntity) throws DAOException {
		
		save(settingEntity);
		
	}
	
	@Override
	public void updateSignSetting(SignSettingEntity settingEntity) throws DAOException {

		update(settingEntity);
		
	}

	@Override
	public SignSettingEntity getSignSetting() throws DAOException {
		
		SignSettingEntity signSettingEntity = null;
		try {
			signSettingEntity = (SignSettingEntity)em.createQuery("select b from SignSettingEntity b where b.isDeleted=:isDeleted",SignSettingEntity.class).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for sign setting");
		}
		return signSettingEntity;
	}

}
