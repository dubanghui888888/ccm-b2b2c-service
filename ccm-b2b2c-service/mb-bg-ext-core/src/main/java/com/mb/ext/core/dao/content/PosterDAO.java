package com.mb.ext.core.dao.content;

import java.util.List;

import com.mb.ext.core.entity.content.PosterEntity;
import com.mb.ext.core.entity.content.TagEntity;
import com.mb.framework.exception.DAOException;



public interface PosterDAO
{
	PosterEntity getPosterByUuid(String uuid) throws DAOException;
	
	List<PosterEntity> getPostersByTag(TagEntity tagEntity) throws DAOException;
	
	List<PosterEntity> getPostersByTagName(String tagName) throws DAOException;
	
	List<PosterEntity> getPosters() throws DAOException;
	
	void addPoster(PosterEntity posterEntity) throws DAOException;
	
	void updatePoster(PosterEntity posterEntity) throws DAOException;
	
	void deletePoster(PosterEntity posterEntity) throws DAOException;
}
