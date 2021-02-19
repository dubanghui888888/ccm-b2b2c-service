package com.mb.ext.core.dao.content;

import java.util.List;

import com.mb.ext.core.entity.content.SceneEntity;
import com.mb.ext.core.entity.content.TagEntity;
import com.mb.framework.exception.DAOException;



public interface SceneDAO
{
	SceneEntity getSceneByUuid(String uuid) throws DAOException;
	
	List<SceneEntity> getScenesByTag(TagEntity tagEntity) throws DAOException;
	
	List<SceneEntity> getScenesByTagName(String tagName) throws DAOException;
	
	List<SceneEntity> getScenes() throws DAOException;
	
	void addScene(SceneEntity sceneEntity) throws DAOException;
	
	void updateScene(SceneEntity sceneEntity) throws DAOException;
	
	void deleteScene(SceneEntity sceneEntity) throws DAOException;
}
