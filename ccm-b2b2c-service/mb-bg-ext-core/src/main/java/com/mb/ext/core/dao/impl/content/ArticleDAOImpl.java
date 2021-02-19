package com.mb.ext.core.dao.impl.content;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.content.ArticleDAO;
import com.mb.ext.core.entity.content.ArticleEntity;
import com.mb.ext.core.entity.content.TagEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("articleDAO")
@Qualifier("articleDAO")
public class ArticleDAOImpl extends AbstractBaseDAO<ArticleEntity> implements
		ArticleDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public ArticleDAOImpl() {
		super();
		this.setEntityClass(ArticleEntity.class);
	}

	@Override
	public ArticleEntity getArticleByUuid(String uuid) throws DAOException {
		ArticleEntity articleEntity = null;
		try {
			articleEntity = (ArticleEntity) em
					.createQuery(
							"select b from ArticleEntity b where b.articleUuid = :UUID and b.isDeleted=:isDeleted",
							ArticleEntity.class)
					.setParameter("UUID", uuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for article: " + uuid);
		}
		return articleEntity;
	}
	
	@Override
	public ArticleEntity getArticleByMediaId(String mediaId) throws DAOException {
		ArticleEntity articleEntity = null;
		try {
			articleEntity = (ArticleEntity) em
					.createQuery(
							"select b from ArticleEntity b where b.mediaId = :MEDIAID and b.isDeleted=:isDeleted",
							ArticleEntity.class)
					.setParameter("MEDIAID", mediaId)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for article: " + mediaId);
		}
		return articleEntity;
	}

	@Override
	public List<ArticleEntity> getArticlesByTag(TagEntity tagEntity) throws DAOException {
		List<ArticleEntity> articleEntityList = new ArrayList<ArticleEntity>();
		try {
			articleEntityList = em
					.createQuery(
							"select b from ArticleEntity b where b.tagEntity.tagUuid = :UUID and b.isDeleted=:isDeleted order by b.updateDate desc",
							ArticleEntity.class)
							.setParameter("UUID", tagEntity.getTagUuid())
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for article" );
		}
		return articleEntityList;
	}
	
	@Override
	public List<ArticleEntity> getArticlesByTagName(String tagName) throws DAOException {
		List<ArticleEntity> articleEntityList = new ArrayList<ArticleEntity>();
		try {
			articleEntityList = em
					.createQuery(
							"select b from ArticleEntity b where b.tagEntity.tagName = :TAGNAME and b.isDeleted=:isDeleted order by b.updateDate desc",
							ArticleEntity.class)
							.setParameter("TAGNAME", tagName)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for article" );
		}
		return articleEntityList;
	}
	
	@Override
	public List<ArticleEntity> getPublishedArticlesByTagName(String tagName) throws DAOException {
		List<ArticleEntity> articleEntityList = new ArrayList<ArticleEntity>();
		try {
			articleEntityList = em
					.createQuery(
							"select b from ArticleEntity b where b.isPublished = :PUBLISHED and b.tagEntity.tagName = :TAGNAME and b.isDeleted=:isDeleted order by b.updateDate desc",
							ArticleEntity.class)
							.setParameter("PUBLISHED", true)
							.setParameter("TAGNAME", tagName)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for article" );
		}
		return articleEntityList;
	}
	
	@Override
	public List<ArticleEntity> getArticlesByType(String articleType) throws DAOException {
		List<ArticleEntity> articleEntityList = new ArrayList<ArticleEntity>();
		try {
			articleEntityList = em
					.createQuery(
							"select b from ArticleEntity b where b.articleType = :ARTICLETYPE and b.isDeleted=:isDeleted order by b.updateDate desc",
							ArticleEntity.class)
							.setParameter("ARTICLETYPE", articleType)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for article" );
		}
		return articleEntityList;
	}
	
	@Override
	public List<ArticleEntity> getArticlesByTypes(List<String> articleTypeList) throws DAOException {
		List<ArticleEntity> articleEntityList = new ArrayList<ArticleEntity>();
		try {
			articleEntityList = em
					.createQuery(
							"select b from ArticleEntity b where b.articleType in :ARTICLETYPES and b.isDeleted=:isDeleted order by b.updateDate desc",
							ArticleEntity.class)
							.setParameter("ARTICLETYPES", articleTypeList)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for article" );
		}
		return articleEntityList;
	}
	
	@Override
	public List<ArticleEntity> getPublishedArticlesByType(String articleType) throws DAOException {
		List<ArticleEntity> articleEntityList = new ArrayList<ArticleEntity>();
		try {
			articleEntityList = em
					.createQuery(
							"select b from ArticleEntity b where b.isPublished = :PUBLISHED and b.articleType = :ARTICLETYPE and b.isDeleted=:isDeleted order by b.updateDate desc",
							ArticleEntity.class)
							.setParameter("ARTICLETYPE", articleType)
							.setParameter("PUBLISHED", true)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for article" );
		}
		return articleEntityList;
	}
	
	@Override
	public int getArticleCountByTagName(String tagName) throws DAOException {
		Long count = null;
		try {
			count = em
					.createQuery(
							"select count(b.articleUuid) from ArticleEntity b where b.tagEntity.tagName = :TAGNAME and b.isDeleted=:isDeleted order by b.updateDate desc",
							Long.class)
							.setParameter("TAGNAME", tagName)
							.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for article" );
		}
		return count.intValue();
	}
	
	@Override
	public int getArticleCountByType(String articleType) throws DAOException {
		Long count = null;
		try {
			count = em
					.createQuery(
							"select count(b.articleUuid) from ArticleEntity b where b.articleType = :ARTICLETYPE and b.isDeleted=:isDeleted order by b.updateDate desc",
							Long.class)
							.setParameter("ARTICLETYPE", articleType)
							.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for article" );
		}
		return count.intValue();
	}
	
	@Override
	public int getArticleCountByTypes(List<String> articleTypeList) throws DAOException {
		Long count = null;
		try {
			count = em
					.createQuery(
							"select count(b.articleUuid) from ArticleEntity b where b.articleType in :ARTICLETYPES and b.isDeleted=:isDeleted order by b.updateDate desc",
							Long.class)
							.setParameter("ARTICLETYPES", articleTypeList)
							.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for article" );
		}
		return count.intValue();
	}
	
	@Override
	public int getPublishedArticleCountByTagName(String tagName) throws DAOException {
		Long count = null;
		try {
			count = em
					.createQuery(
							"select count(b.articleUuid) from ArticleEntity b where b.tagEntity.tagName = :TAGNAME and b.isPublished = :PUBLISHED and b.isDeleted=:isDeleted order by b.updateDate desc",
							Long.class)
							.setParameter("TAGNAME", tagName)
							.setParameter("PUBLISHED", true)
							.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for article" );
		}
		return count.intValue();
	}
	
	@Override
	public int getPublishedArticleCountByType(String articleType) throws DAOException {
		Long count = null;
		try {
			count = em
					.createQuery(
							"select count(b.articleUuid) from ArticleEntity b where b.articleType = :ARTICLETYPE and b.isPublished = :PUBLISHED and b.isDeleted=:isDeleted order by b.updateDate desc",
							Long.class)
							.setParameter("ARTICLETYPE", articleType)
							.setParameter("PUBLISHED", true)
							.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for article" );
		}
		return count.intValue();
	}
	
	@Override
	public List<ArticleEntity> getArticlesByTagNamePage(String tagName,int startIndex, int pageSize) throws DAOException {
		List<ArticleEntity> articleEntityList = new ArrayList<ArticleEntity>();
		try {
			articleEntityList = em
					.createQuery(
							"select b from ArticleEntity b where b.tagEntity.tagName = :TAGNAME and b.isDeleted=:isDeleted order by b.updateDate desc",
							ArticleEntity.class)
							.setFirstResult(startIndex).setMaxResults(pageSize)
							.setParameter("TAGNAME", tagName)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for article" );
		}
		return articleEntityList;
	}
	
	@Override
	public List<ArticleEntity> getArticlesByTypePage(String articleType,int startIndex, int pageSize) throws DAOException {
		List<ArticleEntity> articleEntityList = new ArrayList<ArticleEntity>();
		try {
			articleEntityList = em
					.createQuery(
							"select b from ArticleEntity b where b.articleType = :ARTICLETYPE and b.isDeleted=:isDeleted order by b.updateDate desc",
							ArticleEntity.class)
							.setFirstResult(startIndex).setMaxResults(pageSize)
							.setParameter("ARTICLETYPE", articleType)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for article" );
		}
		return articleEntityList;
	}
	
	@Override
	public List<ArticleEntity> getArticlesByTypesPage(List<String> articleTypeList,int startIndex, int pageSize) throws DAOException {
		List<ArticleEntity> articleEntityList = new ArrayList<ArticleEntity>();
		try {
			articleEntityList = em
					.createQuery(
							"select b from ArticleEntity b where b.articleType in :ARTICLETYPES and b.isDeleted=:isDeleted order by b.updateDate desc",
							ArticleEntity.class)
							.setFirstResult(startIndex).setMaxResults(pageSize)
							.setParameter("ARTICLETYPES", articleTypeList)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for article" );
		}
		return articleEntityList;
	}
	
	@Override
	public List<ArticleEntity> getPublishedArticlesByTagNamePage(String tagName,int startIndex, int pageSize) throws DAOException {
		List<ArticleEntity> articleEntityList = new ArrayList<ArticleEntity>();
		try {
			articleEntityList = em
					.createQuery(
							"select b from ArticleEntity b where b.tagEntity.tagName = :TAGNAME and b.isPublished = :PUBLISHED and b.isDeleted=:isDeleted order by b.publishTime desc",
							ArticleEntity.class)
							.setFirstResult(startIndex).setMaxResults(pageSize)
							.setParameter("TAGNAME", tagName)
							.setParameter("PUBLISHED", true)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for article" );
		}
		return articleEntityList;
	}
	
	@Override
	public List<ArticleEntity> getPublishedArticlesByTypePage(String articleType,int startIndex, int pageSize) throws DAOException {
		List<ArticleEntity> articleEntityList = new ArrayList<ArticleEntity>();
		try {
			articleEntityList = em
					.createQuery(
							"select b from ArticleEntity b where b.articleType = :ARTICLETYPE and b.isPublished = :PUBLISHED and b.isDeleted=:isDeleted order by b.publishTime desc",
							ArticleEntity.class)
							.setFirstResult(startIndex).setMaxResults(pageSize)
							.setParameter("ARTICLETYPE", articleType)
							.setParameter("PUBLISHED", true)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for article" );
		}
		return articleEntityList;
	}
	
	@Override
	public List<ArticleEntity> getArticlesByPage(int startIndex, int pageSize) throws DAOException {
		List<ArticleEntity> articleEntityList = new ArrayList<ArticleEntity>();
		try {
			articleEntityList = em
					.createQuery(
							"select b from ArticleEntity b where b.isDeleted=:isDeleted order by b.updateDate desc",
							ArticleEntity.class).setFirstResult(startIndex).setMaxResults(pageSize)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for article" );
		}
		return articleEntityList;
	}
	
	@Override
	public List<ArticleEntity> getPublishedArticlesByPage(int startIndex, int pageSize) throws DAOException {
		List<ArticleEntity> articleEntityList = new ArrayList<ArticleEntity>();
		try {
			articleEntityList = em
					.createQuery(
							"select b from ArticleEntity b where b.isPublished = :PUBLISHED and b.isDeleted=:isDeleted order by b.publishTime desc",
							ArticleEntity.class).setFirstResult(startIndex).setMaxResults(pageSize)
							.setParameter("PUBLISHED", true)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for article" );
		}
		return articleEntityList;
	}
	
	@Override
	public List<ArticleEntity> getArticles() throws DAOException {
		List<ArticleEntity> articleEntityList = new ArrayList<ArticleEntity>();
		try {
			articleEntityList = em
					.createQuery(
							"select b from ArticleEntity b where b.isDeleted=:isDeleted order by b.updateDate desc",
							ArticleEntity.class)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for article" );
		}
		return articleEntityList;
	}
	
	@Override
	public List<ArticleEntity> getPublishedArticles() throws DAOException {
		List<ArticleEntity> articleEntityList = new ArrayList<ArticleEntity>();
		try {
			articleEntityList = em
					.createQuery(
							"select b from ArticleEntity b where b.isPublished = :PUBLISHED and b.isDeleted=:isDeleted order by b.updateDate desc",
							ArticleEntity.class)
							.setParameter("PUBLISHED", true)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for article" );
		}
		return articleEntityList;
	}
	
	@Override
	public int getArticleCount() throws DAOException {
		Long count = null;
		try {
			count = em
					.createQuery(
							"select count(b.articleUuid) from ArticleEntity b where b.isDeleted=:isDeleted order by b.updateDate desc",
							Long.class)
							.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for article" );
		}
		return count.intValue();
	}
	
	@Override
	public int getPublishedArticleCount() throws DAOException {
		Long count = null;
		try {
			count = em
					.createQuery(
							"select count(b.articleUuid) from ArticleEntity b where  b.isPublished = :PUBLISHED and b.isDeleted=:isDeleted order by b.updateDate desc",
							Long.class)
							.setParameter("isDeleted", Boolean.FALSE)
							.setParameter("PUBLISHED", true)
							.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for article" );
		}
		return count.intValue();
	}

	@Override
	public void addArticle(ArticleEntity articleEntity) throws DAOException {
		
		save(articleEntity);
		
	}


	@Override
	public void deleteArticle(ArticleEntity articleEntity) throws DAOException {
		
		deletePhysical(articleEntity);
		
	}

	@Override
	public void updateArticle(ArticleEntity articleEntity) throws DAOException {
		
		update(articleEntity);
		
	}
}
