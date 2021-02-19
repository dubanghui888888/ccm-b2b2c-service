package com.mb.ext.core.dao.impl.content;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.content.ShareImageDAO;
import com.mb.ext.core.entity.content.ShareEntity;
import com.mb.ext.core.entity.content.ShareImageEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("shareImageDAO")
@Qualifier("shareImageDAO")
public class ShareImageDAOImpl extends AbstractBaseDAO<ShareImageEntity> implements
		ShareImageDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public ShareImageDAOImpl() {
		super();
		this.setEntityClass(ShareImageEntity.class);
	}

	@Override
	public ShareImageEntity getShareImageByUuid(String uuid) throws DAOException {
		ShareImageEntity shareImageEntity = null;
		try {
			shareImageEntity = (ShareImageEntity) em
					.createQuery(
							"select b from ShareImageEntity b where b.shareImageUuid = :UUID and b.isDeleted=:isDeleted",
							ShareImageEntity.class)
					.setParameter("UUID", uuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for share: " + uuid);
		}
		return shareImageEntity;
	}

	@Override
	public List<ShareImageEntity> getShareImagesByShare(ShareEntity shareEntity) throws DAOException {
		List<ShareImageEntity> shareImageEntityList = new ArrayList<ShareImageEntity>();
		try {
			shareImageEntityList = em
					.createQuery(
							"select b from ShareImageEntity b where b.shareEntity.shareUuid = :UUID and b.isDeleted=:isDeleted order by b.updateDate desc",
							ShareImageEntity.class)
							.setParameter("UUID", shareEntity.getShareUuid())
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for share image" );
		}
		return shareImageEntityList;
	}

	@Override
	public void addShareImage(ShareImageEntity shareImageEntity) throws DAOException {
		
		save(shareImageEntity);
		
	}


	@Override
	public void deleteShareImage(ShareImageEntity shareImageEntity) throws DAOException {
		
		deletePhysical(shareImageEntity);
		
	}

	@Override
	public void updateShareImage(ShareImageEntity shareImageEntity) throws DAOException {
		
		update(shareImageEntity);
		
	}
}
