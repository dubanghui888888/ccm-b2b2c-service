package com.mb.ext.core.dao.content;

import java.util.List;

import com.mb.ext.core.entity.content.ShareCommentAtEntity;
import com.mb.ext.core.entity.content.ShareCommentEntity;
import com.mb.framework.exception.DAOException;



public interface ShareCommentAtDAO
{
	ShareCommentAtEntity getShareCommentAtByUuid(String uuid) throws DAOException;
	
	List<ShareCommentAtEntity> getShareCommentAtsByShareComment(ShareCommentEntity shareCommentEntity) throws DAOException;
	
	void addShareCommentAt(ShareCommentAtEntity shareCommentAtEntity) throws DAOException;
	
	void updateShareCommentAt(ShareCommentAtEntity shareCommentAtEntity) throws DAOException;
	
	void deleteShareCommentAt(ShareCommentAtEntity shareCommentAtEntity) throws DAOException;
}
