package com.mb.ext.core.dao;

import java.util.List;

import com.mb.ext.core.entity.SwiperEntity;
import com.mb.framework.exception.DAOException;



public interface SwiperDAO
{
	SwiperEntity getSwiperByUuid(String uuid) throws DAOException;
	
	List<SwiperEntity> getSwipers() throws DAOException;
	
	void addSwiper(SwiperEntity swiperEntity) throws DAOException;
	
	void updateSwiper(SwiperEntity swiperEntity) throws DAOException;
	
	void deleteSwiper(SwiperEntity swiperEntity) throws DAOException;
}
