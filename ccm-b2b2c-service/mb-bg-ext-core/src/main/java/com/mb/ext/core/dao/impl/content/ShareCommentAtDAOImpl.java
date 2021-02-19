package com.mb.ext.core.dao.impl.content;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.content.ShareCommentAtDAO;
import com.mb.ext.core.entity.content.ShareCommentAtEntity;
import com.mb.ext.core.entity.content.ShareCommentEntity;
import com.mb.ext.core.entity.content.ShareEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("shareCommentAtDAO")
@Qualifier("shareCommentAtDAO")
public class ShareCommentAtDAOImpl extends AbstractBaseDAO<ShareCommentAtEntity> implements
		ShareCommentAtDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public ShareCommentAtDAOImpl() {
		super();
		this.setEntityClass(ShareCommentAtEntity.class);
	}

	@Override
	public ShareCommentAtEntity getShareCommentAtByUuid(String uuid) throws DAOException {
		ShareCommentAtEntity shareCommentAtEntity = null;
		try {
			shareCommentAtEntity = (ShareCommentAtEntity) em
					.createQuery(
							"select b from ShareCommentAtEntity b where b.shareCommentAtUuid = :UUID and b.isDeleted=:isDeleted",
							ShareCommentAtEntity.class)
					.setParameter("UUID", uuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for share: " + uuid);
		}
		return shareCommentAtEntity;
	}

	@Override
	public List<ShareCommentAtEntity> getShareCommentAtsByShareComment(ShareCommentEntity shareCommentEntity) throws DAOException {
		List<ShareCommentAtEntity> shareCommentAtEntityList = new ArrayList<ShareCommentAtEntity>();
		try {
			shareCommentAtEntityList = em
					.createQuery(
							"select b from ShareCommentAtEntity b where b.shareCommentEntity.shareCommentUuid = :UUID and b.isDeleted=:isDeleted order by b.updateDate desc",
							ShareCommentAtEntity.class)
							.setParameter("UUID", shareCommentEntity.getShareCommentUuid())
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for share comment" );
		}
		return shareCommentAtEntityList;
	}

	@Override
	public void addShareCommentAt(ShareCommentAtEntity shareCommentAtEntity) throws DAOException {
		
		save(shareCommentAtEntity);
		
	}


	@Override
	public void deleteShareCommentAt(ShareCommentAtEntity shareCommentAtEntity) throws DAOException {
		
		deletePhysical(shareCommentAtEntity);
		
	}

	@Override
	public void updateShareCommentAt(ShareCommentAtEntity shareCommentAtEntity) throws DAOException {
		
		update(shareCommentAtEntity);
		
	}
}
