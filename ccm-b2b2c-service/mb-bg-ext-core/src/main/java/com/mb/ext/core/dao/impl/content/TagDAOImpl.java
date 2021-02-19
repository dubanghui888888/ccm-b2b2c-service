package com.mb.ext.core.dao.impl.content;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.content.TagDAO;
import com.mb.ext.core.entity.content.TagEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("tagDAO")
@Qualifier("tagDAO")
public class TagDAOImpl extends AbstractBaseDAO<TagEntity> implements
		TagDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public TagDAOImpl() {
		super();
		this.setEntityClass(TagEntity.class);
	}

	@Override
	public TagEntity getTagByUuid(String uuid) throws DAOException {
		TagEntity tagEntity = null;
		try {
			tagEntity = (TagEntity) em
					.createQuery(
							"select b from TagEntity b where b.tagUuid = :UUID and b.isDeleted=:isDeleted",
							TagEntity.class)
					.setParameter("UUID", uuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for tag: " + uuid);
		}
		return tagEntity;
	}
	
	@Override
	public TagEntity getTagByTypeName(String tagType, String tagName) throws DAOException {
		TagEntity tagEntity = null;
		try {
			tagEntity = (TagEntity) em
					.createQuery(
							"select b from TagEntity b where b.tagType = :TYPE and b.tagName = :NAME and b.isDeleted=:isDeleted",
							TagEntity.class)
					.setParameter("TYPE", tagType)
					.setParameter("NAME", tagName)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for tag: " + tagName);
		}
		return tagEntity;
	}

	@Override
	public List<TagEntity> getTagsByType(String tagType) throws DAOException {
		List<TagEntity> tagEntityList = new ArrayList<TagEntity>();
		try {
			tagEntityList = em
					.createQuery(
							"select b from TagEntity b where b.tagType = :TAGTYPE and b.isDeleted=:isDeleted",
							TagEntity.class)
							.setParameter("TAGTYPE", tagType)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for tag" );
		}
		return tagEntityList;
	}

	@Override
	public void addTag(TagEntity tagEntity) throws DAOException {
		
		save(tagEntity);
		
	}


	@Override
	public void deleteTag(TagEntity tagEntity) throws DAOException {
		
		deletePhysical(tagEntity);
		
	}

	@Override
	public void updateTag(TagEntity tagEntity) throws DAOException {
		
		update(tagEntity);
		
	}
}
