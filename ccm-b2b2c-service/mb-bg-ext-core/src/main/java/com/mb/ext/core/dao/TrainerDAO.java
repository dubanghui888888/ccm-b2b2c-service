package com.mb.ext.core.dao;

import java.util.List;

import com.mb.ext.core.entity.TrainerEntity;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.service.spec.UserSearchDTO;
import com.mb.framework.exception.DAOException;


public interface TrainerDAO
{
	/**
	 * 
	 * This method is used to add trainer to enterprise.
	 * 
	 * @param trainerEntity
	 */
	List<TrainerEntity> searchTrainers(UserSearchDTO userSearchDTO, int startIndex, int pageSize) throws DAOException;
	
	int searchTrainerTotal(UserSearchDTO userSearchDTO) throws DAOException;
	
	void addTrainer(TrainerEntity trainerEntity) throws DAOException;
	
	void updateTrainer(TrainerEntity trainerEntity) throws DAOException;
	
	void deleteTrainer(TrainerEntity trainerEntity) throws DAOException;
	
	void deletePhysicalTrainer(TrainerEntity trainerEntity) throws DAOException;
	
	public TrainerEntity getTrainerByUuid(String trainerUuid) throws DAOException;
	
	public TrainerEntity getTrainerByUser(UserEntity userEntity) throws DAOException;
	
	public List<TrainerEntity> getTrainerByLevel(String trainerLevel) throws DAOException;
	
	public int getTrainerCountByLevel(String trainerLevel) throws DAOException;
	
	public List<TrainerEntity> getChildTrainers(TrainerEntity trainerEntity) throws DAOException;
	
	public int getChildTrainerCount(TrainerEntity trainerEntity) throws DAOException;
	
	List<UserEntity> searchTrainerUsers(TrainerEntity trainerEntity, int startIndex, int pageSize) throws DAOException;
	
	int searchTrainerUsersTotal(TrainerEntity trainerEntity) throws DAOException;
	
}
