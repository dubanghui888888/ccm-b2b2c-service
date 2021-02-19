package com.mb.ext.core.dao.content;

import java.util.Date;
import java.util.List;

import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.content.ShareEntity;
import com.mb.ext.core.entity.content.TagEntity;
import com.mb.framework.exception.DAOException;



public interface ShareDAO
{
	ShareEntity getShareByUuid(String uuid) throws DAOException;
	
	List<ShareEntity> getSharesByTag(TagEntity tagEntity) throws DAOException;
	
	List<ShareEntity> getSharesByUser(UserEntity userEntity) throws DAOException;
	
	List<ShareEntity> getSharesByDay(Date createDate) throws DAOException;
	
	int getShareCountByTagName(String tagName) throws DAOException;
	
	int getPublishedShareCountByTagName(String tagName) throws DAOException;
	
	List<ShareEntity> getSharesByTagName(String tagName) throws DAOException;
	
	List<ShareEntity> getSharesByTagNamePage(String tagName,int startIndex, int pageSize) throws DAOException;
	
	List<ShareEntity> getPublishedSharesByTagNamePage(String tagName,int startIndex, int pageSize) throws DAOException;
	
	List<ShareEntity> getShares() throws DAOException;
	
	int getShareCount() throws DAOException;
	
	int getPublishedShareCount() throws DAOException;
	
	List<ShareEntity> getSharesByPage(int startIndex, int pageSize) throws DAOException;
	
	List<ShareEntity> getPublishedSharesByPage(int startIndex, int pageSize) throws DAOException;
	
	void addShare(ShareEntity shareEntity) throws DAOException;
	
	void updateShare(ShareEntity shareEntity) throws DAOException;
	
	void deleteShare(ShareEntity shareEntity) throws DAOException;
}
