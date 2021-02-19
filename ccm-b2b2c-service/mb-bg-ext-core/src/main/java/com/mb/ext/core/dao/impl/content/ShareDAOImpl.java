package com.mb.ext.core.dao.impl.content;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.content.ShareDAO;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.content.ShareEntity;
import com.mb.ext.core.entity.content.TagEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("shareDAO")
@Qualifier("shareDAO")
public class ShareDAOImpl extends AbstractBaseDAO<ShareEntity> implements
		ShareDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public ShareDAOImpl() {
		super();
		this.setEntityClass(ShareEntity.class);
	}

	@Override
	public ShareEntity getShareByUuid(String uuid) throws DAOException {
		ShareEntity shareEntity = null;
		try {
			shareEntity = (ShareEntity) em
					.createQuery(
							"select b from ShareEntity b where b.shareUuid = :UUID and b.isDeleted=:isDeleted",
							ShareEntity.class)
					.setParameter("UUID", uuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for share: " + uuid);
		}
		return shareEntity;
	}

	@Override
	public List<ShareEntity> getSharesByTag(TagEntity tagEntity) throws DAOException {
		List<ShareEntity> shareEntityList = new ArrayList<ShareEntity>();
		try {
			shareEntityList = em
					.createQuery(
							"select b from ShareEntity b where b.tagEntity.tagUuid = :UUID and b.isDeleted=:isDeleted order by b.updateDate desc",
							ShareEntity.class)
							.setParameter("UUID", tagEntity.getTagUuid())
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for share" );
		}
		return shareEntityList;
	}
	
	@Override
	public List<ShareEntity> getSharesByUser(UserEntity userEntity) throws DAOException {
		List<ShareEntity> shareEntityList = new ArrayList<ShareEntity>();
		try {
			shareEntityList = em
					.createQuery(
							"select b from ShareEntity b where b.userEntity.userUuid = :UUID and b.isDeleted=:isDeleted order by b.updateDate desc",
							ShareEntity.class)
							.setParameter("UUID", userEntity.getUserUuid())
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for share" );
		}
		return shareEntityList;
	}
	
	@Override
	public List<ShareEntity> getSharesByDay(Date createDate) throws DAOException {
		List<ShareEntity> shareEntityList = new ArrayList<ShareEntity>();
		try {
			shareEntityList = em
					.createQuery(
							"select b from ShareEntity b where date(b.createDate) = date(:CREATEDATE) and b.isDeleted=:isDeleted order by b.createDate desc",
							ShareEntity.class)
							.setParameter("CREATEDATE", createDate)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for share" );
		}
		return shareEntityList;
	}
	
	@Override
	public List<ShareEntity> getSharesByTagName(String tagName) throws DAOException {
		List<ShareEntity> shareEntityList = new ArrayList<ShareEntity>();
		try {
			shareEntityList = em
					.createQuery(
							"select b from ShareEntity b where b.tagEntity.tagName = :TAGNAME and b.isDeleted=:isDeleted order by b.createDate desc",
							ShareEntity.class)
							.setParameter("TAGNAME", tagName)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for share" );
		}
		return shareEntityList;
	}
	
	@Override
	public int getShareCountByTagName(String tagName) throws DAOException {
		Long count = null;
		try {
			count = em
					.createQuery(
							"select count(b.shareUuid) from ShareEntity b where b.tagEntity.tagName = :TAGNAME and b.isDeleted=:isDeleted order by b.createDate desc",
							Long.class)
							.setParameter("TAGNAME", tagName)
							.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for share" );
		}
		return count.intValue();
	}
	
	@Override
	public int getPublishedShareCountByTagName(String tagName) throws DAOException {
		Long count = null;
		try {
			count = em
					.createQuery(
							"select count(b.shareUuid) from ShareEntity b where b.tagEntity.tagName = :TAGNAME and b.isPublished = :PUBLISHED and b.isDeleted=:isDeleted order by b.createDate desc",
							Long.class)
							.setParameter("TAGNAME", tagName)
							.setParameter("PUBLISHED", true)
							.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for share" );
		}
		return count.intValue();
	}
	
	@Override
	public List<ShareEntity> getSharesByTagNamePage(String tagName,int startIndex, int pageSize) throws DAOException {
		List<ShareEntity> shareEntityList = new ArrayList<ShareEntity>();
		try {
			shareEntityList = em
					.createQuery(
							"select b from ShareEntity b where b.tagEntity.tagName = :TAGNAME and b.isDeleted=:isDeleted order by b.createDate desc",
							ShareEntity.class)
							.setFirstResult(startIndex).setMaxResults(pageSize)
							.setParameter("TAGNAME", tagName)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for share" );
		}
		return shareEntityList;
	}
	
	@Override
	public List<ShareEntity> getPublishedSharesByTagNamePage(String tagName,int startIndex, int pageSize) throws DAOException {
		List<ShareEntity> shareEntityList = new ArrayList<ShareEntity>();
		try {
			shareEntityList = em
					.createQuery(
							"select b from ShareEntity b where b.tagEntity.tagName = :TAGNAME and b.isPublished = :PUBLISHED and b.isDeleted=:isDeleted order by b.publishTime desc",
							ShareEntity.class)
							.setFirstResult(startIndex).setMaxResults(pageSize)
							.setParameter("TAGNAME", tagName)
							.setParameter("PUBLISHED", true)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for share" );
		}
		return shareEntityList;
	}
	
	@Override
	public List<ShareEntity> getSharesByPage(int startIndex, int pageSize) throws DAOException {
		List<ShareEntity> shareEntityList = new ArrayList<ShareEntity>();
		try {
			shareEntityList = em
					.createQuery(
							"select b from ShareEntity b where b.isDeleted=:isDeleted order by b.updateDate desc",
							ShareEntity.class).setFirstResult(startIndex).setMaxResults(pageSize)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for share" );
		}
		return shareEntityList;
	}
	
	@Override
	public List<ShareEntity> getPublishedSharesByPage(int startIndex, int pageSize) throws DAOException {
		List<ShareEntity> shareEntityList = new ArrayList<ShareEntity>();
		try {
			shareEntityList = em
					.createQuery(
							"select b from ShareEntity b where b.isPublished = :PUBLISHED and b.isDeleted=:isDeleted order by b.publishTime desc",
							ShareEntity.class).setFirstResult(startIndex).setMaxResults(pageSize)
							.setParameter("PUBLISHED", true)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for share" );
		}
		return shareEntityList;
	}
	
	@Override
	public List<ShareEntity> getShares() throws DAOException {
		List<ShareEntity> shareEntityList = new ArrayList<ShareEntity>();
		try {
			shareEntityList = em
					.createQuery(
							"select b from ShareEntity b where b.isDeleted=:isDeleted order by b.updateDate desc",
							ShareEntity.class)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for share" );
		}
		return shareEntityList;
	}
	
	@Override
	public int getShareCount() throws DAOException {
		Long count = null;
		try {
			count = em
					.createQuery(
							"select count(b.shareUuid) from ShareEntity b where b.isDeleted=:isDeleted order by b.updateDate desc",
							Long.class)
							.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for share" );
		}
		return count.intValue();
	}
	
	@Override
	public int getPublishedShareCount() throws DAOException {
		Long count = null;
		try {
			count = em
					.createQuery(
							"select count(b.shareUuid) from ShareEntity b where  b.isPublished = :PUBLISHED and b.isDeleted=:isDeleted order by b.updateDate desc",
							Long.class)
							.setParameter("isDeleted", Boolean.FALSE)
							.setParameter("PUBLISHED", true)
							.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for share" );
		}
		return count.intValue();
	}

	@Override
	public void addShare(ShareEntity shareEntity) throws DAOException {
		
		save(shareEntity);
		
	}


	@Override
	public void deleteShare(ShareEntity shareEntity) throws DAOException {
		
		deletePhysical(shareEntity);
		
	}

	@Override
	public void updateShare(ShareEntity shareEntity) throws DAOException {
		
		update(shareEntity);
		
	}
}
