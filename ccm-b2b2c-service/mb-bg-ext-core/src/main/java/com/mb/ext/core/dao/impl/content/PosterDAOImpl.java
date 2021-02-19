package com.mb.ext.core.dao.impl.content;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.content.PosterDAO;
import com.mb.ext.core.entity.content.PosterEntity;
import com.mb.ext.core.entity.content.TagEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("posterDAO")
@Qualifier("posterDAO")
public class PosterDAOImpl extends AbstractBaseDAO<PosterEntity> implements
		PosterDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public PosterDAOImpl() {
		super();
		this.setEntityClass(PosterEntity.class);
	}

	@Override
	public PosterEntity getPosterByUuid(String uuid) throws DAOException {
		PosterEntity posterEntity = null;
		try {
			posterEntity = (PosterEntity) em
					.createQuery(
							"select b from PosterEntity b where b.posterUuid = :UUID and b.isDeleted=:isDeleted",
							PosterEntity.class)
					.setParameter("UUID", uuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for poster: " + uuid);
		}
		return posterEntity;
	}

	@Override
	public List<PosterEntity> getPostersByTag(TagEntity tagEntity) throws DAOException {
		List<PosterEntity> posterEntityList = new ArrayList<PosterEntity>();
		try {
			posterEntityList = em
					.createQuery(
							"select b from PosterEntity b where b.tagEntity.tagUuid = :UUID and b.isDeleted=:isDeleted",
							PosterEntity.class)
							.setParameter("UUID", tagEntity.getTagUuid())
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for poster" );
		}
		return posterEntityList;
	}
	
	@Override
	public List<PosterEntity> getPostersByTagName(String tagName) throws DAOException {
		List<PosterEntity> posterEntityList = new ArrayList<PosterEntity>();
		try {
			posterEntityList = em
					.createQuery(
							"select b from PosterEntity b where b.tagEntity.tagName = :NAME and b.isDeleted=:isDeleted",
							PosterEntity.class)
							.setParameter("NAME", tagName)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for poster" );
		}
		return posterEntityList;
	}
	
	@Override
	public List<PosterEntity> getPosters() throws DAOException {
		List<PosterEntity> posterEntityList = new ArrayList<PosterEntity>();
		try {
			posterEntityList = em
					.createQuery(
							"select b from PosterEntity b where b.isDeleted=:isDeleted",
							PosterEntity.class)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for poster" );
		}
		return posterEntityList;
	}

	@Override
	public void addPoster(PosterEntity posterEntity) throws DAOException {
		
		save(posterEntity);
		
	}


	@Override
	public void deletePoster(PosterEntity posterEntity) throws DAOException {
		
		deletePhysical(posterEntity);
		
	}

	@Override
	public void updatePoster(PosterEntity posterEntity) throws DAOException {
		
		update(posterEntity);
		
	}
}
