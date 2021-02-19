
package com.mb.ext.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mb.ext.core.dao.MerchantDAO;
import com.mb.ext.core.dao.TrainerDAO;
import com.mb.ext.core.dao.UserDAO;
import com.mb.ext.core.entity.TrainerEntity;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.TrainerService;
import com.mb.ext.core.service.UserService;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.TrainerDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.UserSearchDTO;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.service.AbstractService;
import com.mb.framework.util.log.LogHelper;


@Service
@Qualifier("userService")
public class TrainerServiceImpl extends AbstractService implements TrainerService<BodyDTO>
{
	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;
	
	@Autowired
	@Qualifier("merchantDAO")
	private MerchantDAO merchantDAO;
	
	@Autowired
	@Qualifier("trainerDAO")
	private TrainerDAO trainerDAO;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	
	@Override
	public List<TrainerDTO> searchTrainers(UserSearchDTO searchDTO, int startIndex, int pageSize) throws BusinessException {
		List<TrainerDTO> trainerDTOList = new ArrayList<TrainerDTO>();
		try {
			List<TrainerEntity> trainerEntityList = trainerDAO.searchTrainers(searchDTO, startIndex, pageSize);
			if (trainerEntityList != null)
				for (Iterator<TrainerEntity> iterator = trainerEntityList.iterator(); iterator
						.hasNext();) {
					TrainerEntity trainerEntity = (TrainerEntity) iterator.next();
					TrainerDTO trainerDTO = new TrainerDTO();
					trainerEntity2DTO(trainerEntity, trainerDTO);
					trainerDTOList.add(trainerDTO);
				}
			return trainerDTOList;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} catch (Exception e2){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e2);
		}
	}
	
	@Override
	public int searchTrainerTotal(UserSearchDTO searchDTO) throws BusinessException {
		try {
			return trainerDAO.searchTrainerTotal(searchDTO);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} catch (Exception e2){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e2);
		}
	}

	@Override
	public void addTrainer(TrainerDTO trainerDTO) throws BusinessException {
		try {
			UserDTO userDTO = trainerDTO.getUserDTO();
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			TrainerEntity trainerEntity = new TrainerEntity();
			trainerEntity.setUserEntity(userEntity);
			trainerEntity.setEffectiveDate(trainerDTO.getEffectiveDate());
			trainerEntity.setTrainerLevel(trainerDTO.getTrainerLevel());
			if(trainerDTO.getParentTrainer() != null && !StringUtils.isEmpty(trainerDTO.getParentTrainer().getTrainerUuid())) {
				TrainerEntity parentTrainerEntity = trainerDAO.getTrainerByUuid(trainerDTO.getParentTrainer().getTrainerUuid());
				trainerEntity.setParentTrainerEntity(parentTrainerEntity);
			}
			trainerDAO.addTrainer(trainerEntity);
		}catch(DAOException e) {
			logger.error(e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}


	@Override
	public void deleteTrainer(TrainerDTO trainerDTO) throws BusinessException {
		try {
			TrainerEntity trainerEntity = trainerDAO.getTrainerByUuid(trainerDTO.getTrainerUuid());
			trainerDAO.deleteTrainer(trainerEntity);
		}catch(DAOException e) {
			logger.error(e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}


	@Override
	public TrainerDTO getTrainerByUuid(String trainerUuid) throws BusinessException {
		try {
			TrainerEntity trainerEntity = trainerDAO.getTrainerByUuid(trainerUuid);
			TrainerDTO trainerDTO = new TrainerDTO();
			trainerEntity2DTO(trainerEntity, trainerDTO);
			TrainerEntity parentTrainer = trainerEntity.getParentTrainerEntity();
			if(parentTrainer != null) {
				TrainerDTO parentTrainerDTO = new TrainerDTO();
				trainerEntity2DTO(parentTrainer, parentTrainerDTO);
				trainerDTO.setParentTrainer(parentTrainerDTO);
			}
			return trainerDTO;
		}catch(DAOException e) {
			logger.error(e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}


	@Override
	public TrainerDTO getTrainerByUser(UserDTO userDTO) throws BusinessException {
		TrainerDTO trainerDTO = null;
		try {
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			TrainerEntity trainerEntity = trainerDAO.getTrainerByUser(userEntity);
			if(trainerEntity != null) {
				trainerDTO = new TrainerDTO();
				trainerEntity2DTO(trainerEntity, trainerDTO);
				TrainerEntity parentTrainer = trainerEntity.getParentTrainerEntity();
				if(parentTrainer != null) {
					TrainerDTO parentTrainerDTO = new TrainerDTO();
					trainerEntity2DTO(parentTrainer, parentTrainerDTO);
					trainerDTO.setParentTrainer(parentTrainerDTO);
				}
			}
		}catch(DAOException e) {
			logger.error(e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return trainerDTO;
	}


	@Override
	public List<TrainerDTO> getTrainerByLevel(String trainerLevel) throws BusinessException {
		List<TrainerDTO> trainerDTOList = new ArrayList<TrainerDTO>();
		try {
			List<TrainerEntity> trainerEntityList = trainerDAO.getTrainerByLevel(trainerLevel);
			for (Iterator<TrainerEntity> iterator = trainerEntityList.iterator(); iterator.hasNext();) {
				TrainerEntity trainerEntity = iterator.next();
				TrainerDTO trainerDTO = new TrainerDTO();
				trainerEntity2DTO(trainerEntity, trainerDTO);
				TrainerEntity parentTrainer = trainerEntity.getParentTrainerEntity();
				if(parentTrainer != null) {
					TrainerDTO parentTrainerDTO = new TrainerDTO();
					trainerEntity2DTO(parentTrainer, parentTrainerDTO);
					trainerDTO.setParentTrainer(parentTrainerDTO);
				}
				trainerDTOList.add(trainerDTO);
			}
		}catch(DAOException e) {
			logger.error(e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return trainerDTOList;
	}


	@Override
	public int getTrainerCountByLevel(String trainerLevel) throws BusinessException {
		try {
			
			return trainerDAO.getTrainerCountByLevel(trainerLevel);
			
		}catch(DAOException e) {
			logger.error(e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	void trainerEntity2DTO(TrainerEntity trainerEntity, TrainerDTO trainerDTO){
		trainerDTO.setTrainerUuid(trainerEntity.getTrainerUuid());
		trainerDTO.setTrainerLevel(trainerEntity.getTrainerLevel());
		trainerDTO.setEffectiveDate(trainerEntity.getEffectiveDate());
		UserEntity userEntity = trainerEntity.getUserEntity();
		UserDTO userDTO = new UserDTO();
		userDTO.setUserUuid(userEntity.getUserUuid());
		userDTO.setName(userEntity.getName());
		userDTO.setPersonalPhone(userEntity.getPersonalPhone());
		userDTO.setPersonalPhoneCountryCode(userEntity.getPersonalPhoneCountryCode());
		userDTO.setIdCardNo(userEntity.getIdCardNo());
		userDTO.setId(userEntity.getId());
		trainerDTO.setUserDTO(userDTO);
		TrainerEntity parentTrainerEntity = trainerEntity.getParentTrainerEntity();
		if(parentTrainerEntity!=null) {
			TrainerDTO parentTrainer = new TrainerDTO();
			parentTrainer.setTrainerUuid(parentTrainerEntity.getTrainerUuid());
			UserEntity parentUserEntity = parentTrainerEntity.getUserEntity();
			UserDTO parentUserDTO = new UserDTO();
			parentUserDTO.setUserUuid(parentUserEntity.getUserUuid());
			parentUserDTO.setName(parentUserEntity.getName());
			parentTrainer.setUserDTO(parentUserDTO);
			trainerDTO.setParentTrainer(parentTrainer);
		}
		
	}


	@Override
	public void upgradeUserToTrainer(TrainerDTO trainerDTO) throws BusinessException {
		try {
			UserEntity userEntity  = userDAO.getUserByUuid(trainerDTO.getUserDTO().getUserUuid());
			TrainerEntity trainerEntity = trainerDAO.getTrainerByUser(userEntity);
			String trainerLevel = trainerDTO.getTrainerLevel();
			if(trainerEntity != null) {
				trainerEntity.setTrainerLevel(trainerLevel);
				trainerDAO.updateTrainer(trainerEntity);
			}else {
				if(!StringUtils.isEmpty(trainerLevel)) {
					trainerEntity = new TrainerEntity();
					trainerEntity.setUserEntity(userEntity);
					trainerEntity.setEffectiveDate(new Date());
					trainerEntity.setTrainerLevel(trainerLevel);
					trainerDAO.addTrainer(trainerEntity);
				}
			}
		}catch(DAOException e) {
			logger.error(e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void cancelUserFromTrainer(TrainerDTO trainerDTO) throws BusinessException {
		try {
			TrainerEntity trainerEntity = trainerDAO.getTrainerByUuid(trainerDTO.getTrainerUuid());
			if(trainerEntity != null) {
				trainerDAO.deleteTrainer(trainerEntity);
			}
		}catch(DAOException e) {
			logger.error(e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public void assignParentTrainer(TrainerDTO trainerDTO) throws BusinessException {
		
		try {
			TrainerEntity trainerEntity = trainerDAO.getTrainerByUuid(trainerDTO.getTrainerUuid());
			TrainerDTO parentTrainerDTO = trainerDTO.getParentTrainer();
			if(parentTrainerDTO != null) {
				TrainerEntity parentTrainerEntity = trainerDAO.getTrainerByUuid(parentTrainerDTO.getTrainerUuid());
				trainerEntity.setParentTrainerEntity(parentTrainerEntity);
				trainerDAO.updateTrainer(trainerEntity);
			}
		}catch(DAOException e) {
			logger.error(e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void assignUserToTrainer(UserDTO userDTO, TrainerDTO trainerDTO) throws BusinessException {
		
		try {
			TrainerEntity trainerEntity = trainerDAO.getTrainerByUuid(trainerDTO.getTrainerUuid());
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			userEntity.setTrainerEntity(trainerEntity);
			userDAO.updateUser(userEntity);
		}catch(DAOException e) {
			logger.error(e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void removeUserFromTrainer(UserDTO userDTO, TrainerDTO trainerDTO) throws BusinessException {
		
		try {
			TrainerEntity trainerEntity = trainerDAO.getTrainerByUuid(trainerDTO.getTrainerUuid());
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			userEntity.setTrainerEntity(null);
			userDAO.updateUser(userEntity);
		}catch(DAOException e) {
			logger.error(e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public List<UserDTO> searchTrainerUsers(TrainerDTO trainerDTO,int startIndex, int pageSize) throws BusinessException {
		List<UserDTO> userDTOList = new ArrayList<UserDTO>();
		try {
			TrainerEntity trainerEntity = trainerDAO.getTrainerByUuid(trainerDTO.getTrainerUuid());
			List<UserEntity> userEntityList = new ArrayList<UserEntity>();
				userEntityList = trainerDAO.searchTrainerUsers(trainerEntity, startIndex, pageSize);
				for (Iterator<UserEntity> iterator = userEntityList.iterator(); iterator
						.hasNext();) {
					UserEntity userEntity = (UserEntity) iterator.next();
					UserDTO userDTO = new UserDTO();
					userService.entity2DTO(userEntity, userDTO);
					userDTOList.add(userDTO);
				}
			return userDTOList;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} catch (Exception e2){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e2);
		}
	}
	
	@Override
	public int searchTrainerUsersTotal(TrainerDTO trainerDTO) throws BusinessException {
		try {
			TrainerEntity trainerEntity = trainerDAO.getTrainerByUuid(trainerDTO.getTrainerUuid());
			return trainerDAO.searchTrainerUsersTotal(trainerEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} catch (Exception e2){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e2);
		}
	}
}






