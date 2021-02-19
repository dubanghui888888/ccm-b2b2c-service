package com.mb.ext.core.dao.impl.content;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.content.ShareLikeDAO;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.content.ShareEntity;
import com.mb.ext.core.entity.content.ShareLikeEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("shareLikeDAO")
@Qualifier("shareLikeDAO")
public class ShareLikeDAOImpl extends AbstractBaseDAO<ShareLikeEntity> implements
		ShareLikeDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public ShareLikeDAOImpl() {
		super();
		this.setEntityClass(ShareLikeEntity.class);
	}

	@Override
	public ShareLikeEntity getShareLikeByUuid(String uuid) throws DAOException {
		ShareLikeEntity shareLikeEntity = null;
		try {
			shareLikeEntity = (ShareLikeEntity) em
					.createQuery(
							"select b from ShareLikeEntity b where b.shareLikeUuid = :UUID and b.isDeleted=:isDeleted",
							ShareLikeEntity.class)
					.setParameter("UUID", uuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for share: " + uuid);
		}
		return shareLikeEntity;
	}
	
	@Override
	public ShareLikeEntity getShareLike(ShareEntity shareEntity, UserEntity userEntity) throws DAOException {
		ShareLikeEntity shareLikeEntity = null;
		try {
			shareLikeEntity = (ShareLikeEntity) em
					.createQuery(
							"select b from ShareLikeEntity b where b.shareEntity.shareUuid = :SHAREUUID and b.userEntity.userUuid = :USERUUID and b.isDeleted=:isDeleted",
							ShareLikeEntity.class)
					.setParameter("SHAREUUID", shareEntity.getShareUuid())
					.setParameter("USERUUID", userEntity.getUserUuid())
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for share: ");
		}
		return shareLikeEntity;
	}

	@Override
	public List<ShareLikeEntity> getShareLikesByShare(ShareEntity shareEntity) throws DAOException {
		List<ShareLikeEntity> shareLikeEntityList = new ArrayList<ShareLikeEntity>();
		try {
			shareLikeEntityList = em
					.createQuery(
							"select b from ShareLikeEntity b where b.shareEntity.shareUuid = :UUID and b.isDeleted=:isDeleted order by b.updateDate desc",
							ShareLikeEntity.class)
							.setParameter("UUID", shareEntity.getShareUuid())
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for share like" );
		}
		return shareLikeEntityList;
	}

	@Override
	public void addShareLike(ShareLikeEntity shareLikeEntity) throws DAOException {
		
		save(shareLikeEntity);
		
	}


	@Override
	public void deleteShareLike(ShareLikeEntity shareLikeEntity) throws DAOException {
		
		deletePhysical(shareLikeEntity);
		
	}

	@Override
	public void updateShareLike(ShareLikeEntity shareLikeEntity) throws DAOException {
		
		update(shareLikeEntity);
		
	}
}
