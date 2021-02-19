package com.mb.ext.core.dao.content;

import java.util.List;

import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.content.ShareEntity;
import com.mb.ext.core.entity.content.ShareLikeEntity;
import com.mb.framework.exception.DAOException;



public interface ShareLikeDAO
{
	ShareLikeEntity getShareLikeByUuid(String uuid) throws DAOException;
	
	ShareLikeEntity getShareLike(ShareEntity shareEntity, UserEntity userEntity) throws DAOException;
	
	List<ShareLikeEntity> getShareLikesByShare(ShareEntity shareEntity) throws DAOException;
	
	void addShareLike(ShareLikeEntity shareLikeEntity) throws DAOException;
	
	void updateShareLike(ShareLikeEntity shareLikeEntity) throws DAOException;
	
	void deleteShareLike(ShareLikeEntity shareLikeEntity) throws DAOException;
}
