package com.mb.ext.core.dao.content;

import java.util.List;

import com.mb.ext.core.entity.content.ShareEntity;
import com.mb.ext.core.entity.content.ShareCommentEntity;
import com.mb.framework.exception.DAOException;



public interface ShareCommentDAO
{
	ShareCommentEntity getShareCommentByUuid(String uuid) throws DAOException;
	
	List<ShareCommentEntity> getShareCommentsByShare(ShareEntity shareEntity) throws DAOException;
	
	void addShareComment(ShareCommentEntity shareCommentEntity) throws DAOException;
	
	void updateShareComment(ShareCommentEntity shareCommentEntity) throws DAOException;
	
	void deleteShareComment(ShareCommentEntity shareCommentEntity) throws DAOException;
}
