package com.mb.ext.core.dao.content;

import java.util.List;

import com.mb.ext.core.entity.content.ShareEntity;
import com.mb.ext.core.entity.content.ShareImageEntity;
import com.mb.framework.exception.DAOException;



public interface ShareImageDAO
{
	ShareImageEntity getShareImageByUuid(String uuid) throws DAOException;
	
	List<ShareImageEntity> getShareImagesByShare(ShareEntity shareEntity) throws DAOException;
	
	void addShareImage(ShareImageEntity shareImageEntity) throws DAOException;
	
	void updateShareImage(ShareImageEntity shareImageEntity) throws DAOException;
	
	void deleteShareImage(ShareImageEntity shareImageEntity) throws DAOException;
}
