package com.mb.ext.core.dao;

import java.util.List;

import com.mb.ext.core.entity.AdEntity;
import com.mb.framework.exception.DAOException;



public interface AdDAO
{
	AdEntity getAdByUuid(String uuid) throws DAOException;
	
	List<AdEntity> getAdsByLocation(String location) throws DAOException;
	
	List<AdEntity> getAdsByProductCate(String productCateUuid) throws DAOException;
	
	List<AdEntity> getAdsByLocationProductCate(String location, String productCateUuid) throws DAOException;
	
	List<AdEntity> getAllAds() throws DAOException;
	
	List<AdEntity> getActiveAds() throws DAOException;
	
	void addAd(AdEntity adEntity) throws DAOException;
	
	void updateAd(AdEntity adEntity) throws DAOException;
	
	void deleteAd(AdEntity adEntity) throws DAOException;
}
