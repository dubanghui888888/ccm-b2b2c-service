package com.mb.ext.core.dao.impl.content;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.content.SceneDAO;
import com.mb.ext.core.entity.content.SceneEntity;
import com.mb.ext.core.entity.content.TagEntity;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("sceneDAO")
@Qualifier("sceneDAO")
public class SceneDAOImpl extends AbstractBaseDAO<SceneEntity> implements
		SceneDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	/**
	 * Initializing Entity.
	 */
	public SceneDAOImpl() {
		super();
		this.setEntityClass(SceneEntity.class);
	}

	@Override
	public SceneEntity getSceneByUuid(String uuid) throws DAOException {
		SceneEntity sceneEntity = null;
		try {
			sceneEntity = (SceneEntity) em
					.createQuery(
							"select b from SceneEntity b where b.sceneUuid = :UUID and b.isDeleted=:isDeleted",
							SceneEntity.class)
					.setParameter("UUID", uuid)
					.setParameter("isDeleted", Boolean.FALSE).getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for scene: " + uuid);
		}
		return sceneEntity;
	}

	@Override
	public List<SceneEntity> getScenesByTag(TagEntity tagEntity) throws DAOException {
		List<SceneEntity> sceneEntityList = new ArrayList<SceneEntity>();
		try {
			sceneEntityList = em
					.createQuery(
							"select b from SceneEntity b where b.tagEntity.tagUuid = :UUID and b.isDeleted=:isDeleted",
							SceneEntity.class)
							.setParameter("UUID", tagEntity.getTagUuid())
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for scene" );
		}
		return sceneEntityList;
	}
	
	@Override
	public List<SceneEntity> getScenesByTagName(String tagName) throws DAOException {
		List<SceneEntity> sceneEntityList = new ArrayList<SceneEntity>();
		try {
			sceneEntityList = em
					.createQuery(
							"select b from SceneEntity b where b.tagEntity.tagName = :NAME and b.isDeleted=:isDeleted",
							SceneEntity.class)
							.setParameter("NAME", tagName)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for scene" );
		}
		return sceneEntityList;
	}
	
	@Override
	public List<SceneEntity> getScenes() throws DAOException {
		List<SceneEntity> sceneEntityList = new ArrayList<SceneEntity>();
		try {
			sceneEntityList = em
					.createQuery(
							"select b from SceneEntity b where b.isDeleted=:isDeleted",
							SceneEntity.class)
							.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for scene" );
		}
		return sceneEntityList;
	}

	@Override
	public void addScene(SceneEntity sceneEntity) throws DAOException {
		
		save(sceneEntity);
		
	}


	@Override
	public void deleteScene(SceneEntity sceneEntity) throws DAOException {
		
		deletePhysical(sceneEntity);
		
	}

	@Override
	public void updateScene(SceneEntity sceneEntity) throws DAOException {
		
		update(sceneEntity);
		
	}
}
