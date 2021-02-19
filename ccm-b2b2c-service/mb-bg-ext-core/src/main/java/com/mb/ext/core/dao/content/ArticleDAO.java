package com.mb.ext.core.dao.content;

import java.util.List;

import com.mb.ext.core.entity.content.ArticleEntity;
import com.mb.ext.core.entity.content.TagEntity;
import com.mb.framework.exception.DAOException;



public interface ArticleDAO
{
	ArticleEntity getArticleByUuid(String uuid) throws DAOException;
	
	ArticleEntity getArticleByMediaId(String mediaId) throws DAOException;
	
	List<ArticleEntity> getArticlesByTag(TagEntity tagEntity) throws DAOException;
	
	int getArticleCountByTagName(String tagName) throws DAOException;
	
	int getArticleCountByType(String articleType) throws DAOException;
	
	int getArticleCountByTypes(List<String> articleTypeList) throws DAOException;
	
	int getPublishedArticleCountByTagName(String tagName) throws DAOException;
	
	int getPublishedArticleCountByType(String articleType) throws DAOException;
	
	List<ArticleEntity> getArticlesByTagName(String tagName) throws DAOException;
	
	List<ArticleEntity> getArticlesByType(String articleType) throws DAOException;
	
	List<ArticleEntity> getArticlesByTypes(List<String> articleTypeList) throws DAOException;
	
	List<ArticleEntity> getArticlesByTagNamePage(String tagName,int startIndex, int pageSize) throws DAOException;
	
	List<ArticleEntity> getArticlesByTypePage(String articleType,int startIndex, int pageSize) throws DAOException;
	
	List<ArticleEntity> getArticlesByTypesPage(List<String> articleTypeList,int startIndex, int pageSize) throws DAOException;
	
	List<ArticleEntity> getPublishedArticlesByTagName(String tagName) throws DAOException;
	
	List<ArticleEntity> getPublishedArticlesByType(String articleType) throws DAOException;
	
	List<ArticleEntity> getPublishedArticlesByTagNamePage(String tagName,int startIndex, int pageSize) throws DAOException;
	
	List<ArticleEntity> getPublishedArticlesByTypePage(String articleType,int startIndex, int pageSize) throws DAOException;
	
	List<ArticleEntity> getArticles() throws DAOException;
	
	List<ArticleEntity> getPublishedArticles() throws DAOException;
	
	int getArticleCount() throws DAOException;
	
	int getPublishedArticleCount() throws DAOException;
	
	List<ArticleEntity> getArticlesByPage(int startIndex, int pageSize) throws DAOException;
	
	List<ArticleEntity> getPublishedArticlesByPage(int startIndex, int pageSize) throws DAOException;
	
	void addArticle(ArticleEntity articleEntity) throws DAOException;
	
	void updateArticle(ArticleEntity articleEntity) throws DAOException;
	
	void deleteArticle(ArticleEntity articleEntity) throws DAOException;
}
