package com.mb.ext.core.dao.impl.point;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.point.PointSettingDAO;
import com.mb.ext.core.entity.point.PointSettingEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

@Repository("pointSettingDAO")
@Qualifier("pointSettingDAO")
public class PointSettingDAOImpl extends AbstractBaseDAO<PointSettingEntity> implements PointSettingDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public PointSettingDAOImpl()
	{
		super();
		this.setEntityClass(PointSettingEntity.class);
	}

	@Override
	public void addPointSetting(PointSettingEntity settingEntity) throws DAOException {
		
		save(settingEntity);
		
	}
	
	@Override
	public void updatePointSetting(PointSettingEntity settingEntity) throws DAOException {

		update(settingEntity);
		
	}

	@Override
	public PointSettingEntity getPointSetting() throws DAOException {
		
		PointSettingEntity pointSettingEntity = null;
		try {
			pointSettingEntity = (PointSettingEntity)em.createQuery("select b from PointSettingEntity b where b.isDeleted=:isDeleted",PointSettingEntity.class).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for point setting");
		}
		return pointSettingEntity;
	}

}
