package com.mb.ext.core.dao.impl.content;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.content.ShareCommentDAO;
import com.mb.ext.core.entity.content.ShareCommentEntity;
import com.mb.ext.core.entity.content.ShareEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("shareCommentDAO")
@Qualifier("shareCommentDAO")
public class ShareCommentDAOImpl extends AbstractBaseDAO<ShareCommentEntity> implements
		ShareCommentDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public ShareCommentDAOImpl() {
		super();
		this.setEntityClass(ShareCommentEntity.class);
	}

	@Override
	public ShareCommentEntity getShareCommentByUuid(String uuid) throws DAOException {
		ShareCommentEntity shareCommentEntity = null;
		try {
			shareCommentEntity = (ShareCommentEntity) em
					.createQuery(
							"select b from ShareCommentEntity b where b.shareCommentUuid = :UUID and b.isDeleted=:isDeleted",
							ShareCommentEntity.class)
					.setParameter("UUID", uuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for share: " + uuid);
		}
		return shareCommentEntity;
	}

	@Override
	public List<ShareCommentEntity> getShareCommentsByShare(ShareEntity shareEntity) throws DAOException {
		List<ShareCommentEntity> shareCommentEntityList = new ArrayList<ShareCommentEntity>();
		try {
			shareCommentEntityList = em
					.createQuery(
							"select b from ShareCommentEntity b where b.shareEntity.shareUuid = :UUID and b.isDeleted=:isDeleted order by b.updateDate desc",
							ShareCommentEntity.class)
							.setParameter("UUID", shareEntity.getShareUuid())
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for share comment" );
		}
		return shareCommentEntityList;
	}

	@Override
	public void addShareComment(ShareCommentEntity shareCommentEntity) throws DAOException {
		
		save(shareCommentEntity);
		
	}


	@Override
	public void deleteShareComment(ShareCommentEntity shareCommentEntity) throws DAOException {
		
		deletePhysical(shareCommentEntity);
		
	}

	@Override
	public void updateShareComment(ShareCommentEntity shareCommentEntity) throws DAOException {
		
		update(shareCommentEntity);
		
	}
}
