package com.mb.ext.core.dao.impl.content;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.content.QaDAO;
import com.mb.ext.core.entity.content.QaEntity;
import com.mb.ext.core.entity.content.TagEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("qaDAO")
@Qualifier("qaDAO")
public class QaDAOImpl extends AbstractBaseDAO<QaEntity> implements
		QaDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public QaDAOImpl() {
		super();
		this.setEntityClass(QaEntity.class);
	}

	@Override
	public QaEntity getQaByUuid(String uuid) throws DAOException {
		QaEntity qaEntity = null;
		try {
			qaEntity = (QaEntity) em
					.createQuery(
							"select b from QaEntity b where b.qaUuid = :UUID and b.isDeleted=:isDeleted",
							QaEntity.class)
					.setParameter("UUID", uuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for qa: " + uuid);
		}
		return qaEntity;
	}
	
	@Override
	public QaEntity getQaByMediaId(String mediaId) throws DAOException {
		QaEntity qaEntity = null;
		try {
			qaEntity = (QaEntity) em
					.createQuery(
							"select b from QaEntity b where b.mediaId = :MEDIAID and b.isDeleted=:isDeleted",
							QaEntity.class)
					.setParameter("MEDIAID", mediaId)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for qa: " + mediaId);
		}
		return qaEntity;
	}

	@Override
	public List<QaEntity> getQasByTag(TagEntity tagEntity) throws DAOException {
		List<QaEntity> qaEntityList = new ArrayList<QaEntity>();
		try {
			qaEntityList = em
					.createQuery(
							"select b from QaEntity b where b.tagEntity.tagUuid = :UUID and b.isDeleted=:isDeleted order by b.updateDate desc",
							QaEntity.class)
							.setParameter("UUID", tagEntity.getTagUuid())
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for qa" );
		}
		return qaEntityList;
	}
	
	@Override
	public List<QaEntity> searchQa(String title) throws DAOException {
		List<QaEntity> qaEntityList = new ArrayList<QaEntity>();
		try {
			qaEntityList = em
					.createQuery(
							"select b from QaEntity b where b.title like  :TITLE and b.isDeleted=:isDeleted order by b.updateDate desc",
							QaEntity.class)
							.setParameter("TITLE", "%"+title+"%")
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for qa" );
		}
		return qaEntityList;
	}
	
	@Override
	public List<QaEntity> getQasByTagName(String tagName) throws DAOException {
		List<QaEntity> qaEntityList = new ArrayList<QaEntity>();
		try {
			qaEntityList = em
					.createQuery(
							"select b from QaEntity b where b.tagEntity.tagName = :TAGNAME and b.isDeleted=:isDeleted order by b.updateDate desc",
							QaEntity.class)
							.setParameter("TAGNAME", tagName)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for qa" );
		}
		return qaEntityList;
	}
	
	@Override
	public List<QaEntity> getHotQas() throws DAOException {
		List<QaEntity> qaEntityList = new ArrayList<QaEntity>();
		try {
			qaEntityList = em
					.createQuery(
							"select b from QaEntity b where b.isHot = :ISHOT and b.isDeleted=:isDeleted order by b.updateDate desc",
							QaEntity.class)
							.setParameter("ISHOT", true)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for qa" );
		}
		return qaEntityList;
	}
	
	@Override
	public List<QaEntity> getPublishedQasByTagName(String tagName) throws DAOException {
		List<QaEntity> qaEntityList = new ArrayList<QaEntity>();
		try {
			qaEntityList = em
					.createQuery(
							"select b from QaEntity b where b.isPublished = :PUBLISHED and b.tagEntity.tagName = :TAGNAME and b.isDeleted=:isDeleted order by b.updateDate desc",
							QaEntity.class)
							.setParameter("PUBLISHED", true)
							.setParameter("TAGNAME", tagName)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for qa" );
		}
		return qaEntityList;
	}
	
	@Override
	public List<QaEntity> getQasByType(String qaType) throws DAOException {
		List<QaEntity> qaEntityList = new ArrayList<QaEntity>();
		try {
			qaEntityList = em
					.createQuery(
							"select b from QaEntity b where b.qaType = :QATYPE and b.isDeleted=:isDeleted order by b.updateDate desc",
							QaEntity.class)
							.setParameter("QATYPE", qaType)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for qa" );
		}
		return qaEntityList;
	}
	
	@Override
	public int getQaCountByTagName(String tagName) throws DAOException {
		Long count = null;
		try {
			count = em
					.createQuery(
							"select count(b.qaUuid) from QaEntity b where b.tagEntity.tagName = :TAGNAME and b.isDeleted=:isDeleted order by b.updateDate desc",
							Long.class)
							.setParameter("TAGNAME", tagName)
							.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for qa" );
		}
		return count.intValue();
	}
	
	@Override
	public int getQaCountByType(String qaType) throws DAOException {
		Long count = null;
		try {
			count = em
					.createQuery(
							"select count(b.qaUuid) from QaEntity b where b.qaType = :QATYPE and b.isDeleted=:isDeleted order by b.updateDate desc",
							Long.class)
							.setParameter("QATYPE", qaType)
							.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for qa" );
		}
		return count.intValue();
	}
	
	@Override
	public int getPublishedQaCountByTagName(String tagName) throws DAOException {
		Long count = null;
		try {
			count = em
					.createQuery(
							"select count(b.qaUuid) from QaEntity b where b.tagEntity.tagName = :TAGNAME and b.isPublished = :PUBLISHED and b.isDeleted=:isDeleted order by b.updateDate desc",
							Long.class)
							.setParameter("TAGNAME", tagName)
							.setParameter("PUBLISHED", true)
							.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for qa" );
		}
		return count.intValue();
	}
	
	@Override
	public List<QaEntity> getQasByTagNamePage(String tagName,int startIndex, int pageSize) throws DAOException {
		List<QaEntity> qaEntityList = new ArrayList<QaEntity>();
		try {
			qaEntityList = em
					.createQuery(
							"select b from QaEntity b where b.tagEntity.tagName = :TAGNAME and b.isDeleted=:isDeleted order by b.updateDate desc",
							QaEntity.class)
							.setFirstResult(startIndex).setMaxResults(pageSize)
							.setParameter("TAGNAME", tagName)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for qa" );
		}
		return qaEntityList;
	}
	
	@Override
	public List<QaEntity> getQasByTypePage(String qaType,int startIndex, int pageSize) throws DAOException {
		List<QaEntity> qaEntityList = new ArrayList<QaEntity>();
		try {
			qaEntityList = em
					.createQuery(
							"select b from QaEntity b where b.qaType = :QATYPE and b.isDeleted=:isDeleted order by b.updateDate desc",
							QaEntity.class)
							.setFirstResult(startIndex).setMaxResults(pageSize)
							.setParameter("QATYPE", qaType)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for qa" );
		}
		return qaEntityList;
	}
	
	@Override
	public List<QaEntity> getPublishedQasByTagNamePage(String tagName,int startIndex, int pageSize) throws DAOException {
		List<QaEntity> qaEntityList = new ArrayList<QaEntity>();
		try {
			qaEntityList = em
					.createQuery(
							"select b from QaEntity b where b.tagEntity.tagName = :TAGNAME and b.isPublished = :PUBLISHED and b.isDeleted=:isDeleted order by b.publishTime desc",
							QaEntity.class)
							.setFirstResult(startIndex).setMaxResults(pageSize)
							.setParameter("TAGNAME", tagName)
							.setParameter("PUBLISHED", true)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for qa" );
		}
		return qaEntityList;
	}
	
	@Override
	public List<QaEntity> getQasByPage(int startIndex, int pageSize) throws DAOException {
		List<QaEntity> qaEntityList = new ArrayList<QaEntity>();
		try {
			qaEntityList = em
					.createQuery(
							"select b from QaEntity b where b.isDeleted=:isDeleted order by b.updateDate desc",
							QaEntity.class).setFirstResult(startIndex).setMaxResults(pageSize)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for qa" );
		}
		return qaEntityList;
	}
	
	@Override
	public List<QaEntity> getPublishedQasByPage(int startIndex, int pageSize) throws DAOException {
		List<QaEntity> qaEntityList = new ArrayList<QaEntity>();
		try {
			qaEntityList = em
					.createQuery(
							"select b from QaEntity b where b.isPublished = :PUBLISHED and b.isDeleted=:isDeleted order by b.publishTime desc",
							QaEntity.class).setFirstResult(startIndex).setMaxResults(pageSize)
							.setParameter("PUBLISHED", true)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for qa" );
		}
		return qaEntityList;
	}
	
	@Override
	public List<QaEntity> getQas() throws DAOException {
		List<QaEntity> qaEntityList = new ArrayList<QaEntity>();
		try {
			qaEntityList = em
					.createQuery(
							"select b from QaEntity b where b.isDeleted=:isDeleted order by b.updateDate desc",
							QaEntity.class)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for qa" );
		}
		return qaEntityList;
	}
	
	@Override
	public List<QaEntity> getPublishedQas() throws DAOException {
		List<QaEntity> qaEntityList = new ArrayList<QaEntity>();
		try {
			qaEntityList = em
					.createQuery(
							"select b from QaEntity b where b.isPublished = :PUBLISHED and b.isDeleted=:isDeleted order by b.updateDate desc",
							QaEntity.class)
							.setParameter("PUBLISHED", true)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for qa" );
		}
		return qaEntityList;
	}
	
	@Override
	public int getQaCount() throws DAOException {
		Long count = null;
		try {
			count = em
					.createQuery(
							"select count(b.qaUuid) from QaEntity b where b.isDeleted=:isDeleted order by b.updateDate desc",
							Long.class)
							.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for qa" );
		}
		return count.intValue();
	}
	
	@Override
	public int getPublishedQaCount() throws DAOException {
		Long count = null;
		try {
			count = em
					.createQuery(
							"select count(b.qaUuid) from QaEntity b where  b.isPublished = :PUBLISHED and b.isDeleted=:isDeleted order by b.updateDate desc",
							Long.class)
							.setParameter("isDeleted", Boolean.FALSE)
							.setParameter("PUBLISHED", true)
							.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for qa" );
		}
		return count.intValue();
	}

	@Override
	public void addQa(QaEntity qaEntity) throws DAOException {
		
		save(qaEntity);
		
	}


	@Override
	public void deleteQa(QaEntity qaEntity) throws DAOException {
		
		deletePhysical(qaEntity);
		
	}

	@Override
	public void updateQa(QaEntity qaEntity) throws DAOException {
		
		update(qaEntity);
		
	}
}
