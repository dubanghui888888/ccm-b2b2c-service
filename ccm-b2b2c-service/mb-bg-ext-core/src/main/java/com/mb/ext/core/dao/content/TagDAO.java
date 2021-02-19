package com.mb.ext.core.dao.content;

import java.util.List;

import com.mb.ext.core.entity.content.TagEntity;
import com.mb.framework.exception.DAOException;



public interface TagDAO
{
	TagEntity getTagByUuid(String uuid) throws DAOException;
	
	TagEntity getTagByTypeName(String tagType, String tagName) throws DAOException;
	
	List<TagEntity> getTagsByType(String tagType) throws DAOException;
	
	void addTag(TagEntity tagEntity) throws DAOException;
	
	void updateTag(TagEntity tagEntity) throws DAOException;
	
	void deleteTag(TagEntity tagEntity) throws DAOException;
}
