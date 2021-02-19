package com.mb.ext.core.dao.content;

import java.util.List;

import com.mb.ext.core.entity.content.QaEntity;
import com.mb.ext.core.entity.content.TagEntity;
import com.mb.framework.exception.DAOException;



public interface QaDAO
{
	QaEntity getQaByUuid(String uuid) throws DAOException;
	
	QaEntity getQaByMediaId(String mediaId) throws DAOException;
	
	List<QaEntity> getQasByTag(TagEntity tagEntity) throws DAOException;
	
	List<QaEntity> searchQa(String title) throws DAOException;
	
	int getQaCountByTagName(String tagName) throws DAOException;
	
	int getQaCountByType(String qaType) throws DAOException;
	
	int getPublishedQaCountByTagName(String tagName) throws DAOException;
	
	List<QaEntity> getHotQas() throws DAOException;
	
	List<QaEntity> getQasByTagName(String tagName) throws DAOException;
	
	List<QaEntity> getQasByType(String qaType) throws DAOException;
	
	List<QaEntity> getQasByTagNamePage(String tagName,int startIndex, int pageSize) throws DAOException;
	
	List<QaEntity> getQasByTypePage(String qaType,int startIndex, int pageSize) throws DAOException;
	
	List<QaEntity> getPublishedQasByTagName(String tagName) throws DAOException;
	
	List<QaEntity> getPublishedQasByTagNamePage(String tagName,int startIndex, int pageSize) throws DAOException;
	
	List<QaEntity> getQas() throws DAOException;
	
	List<QaEntity> getPublishedQas() throws DAOException;
	
	int getQaCount() throws DAOException;
	
	int getPublishedQaCount() throws DAOException;
	
	List<QaEntity> getQasByPage(int startIndex, int pageSize) throws DAOException;
	
	List<QaEntity> getPublishedQasByPage(int startIndex, int pageSize) throws DAOException;
	
	void addQa(QaEntity qaEntity) throws DAOException;
	
	void updateQa(QaEntity qaEntity) throws DAOException;
	
	void deleteQa(QaEntity qaEntity) throws DAOException;
}
