
package com.mb.ext.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mb.ext.core.dao.FileDAO;
import com.mb.ext.core.dao.SequenceDAO;
import com.mb.ext.core.dao.UserDAO;
import com.mb.ext.core.entity.FileEntity;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.FileService;
import com.mb.ext.core.service.UserService;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.FileDTO;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.service.AbstractService;
import com.mb.framework.util.log.LogHelper;


@Service
@Qualifier("FileService")
public class FileServiceImpl extends AbstractService implements FileService<BodyDTO>
{
	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;

	
	@Autowired
	@Qualifier("sequenceDAO")
	private SequenceDAO sequenceDAO;
	
	@Autowired
	@Qualifier("fileDAO")
	private FileDAO fileDAO;
	
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	
	private void DTO2Entity(FileDTO fileDTO, FileEntity fileEntity){
		if(fileDTO != null && fileEntity != null){
			fileEntity.setCreateBy(fileDTO.getCreateBy());
			fileEntity.setUpdateBy(fileDTO.getUpdateBy());
			fileEntity.setFileName(fileDTO.getFileName());
			fileEntity.setSize(fileDTO.getSize());
			fileEntity.setOssKey(fileDTO.getOssKey());
			fileEntity.setUrl(fileDTO.getUrl());
		}
	}
	
	private void Entity2DTO(FileEntity fileEntity, FileDTO fileDTO){
		if(fileDTO != null && fileEntity != null){
			fileDTO.setFileName(fileEntity.getFileName());
			fileDTO.setSize(fileEntity.getSize());
			fileDTO.setOssKey(fileEntity.getOssKey());
			fileDTO.setUrl(fileEntity.getUrl());
			fileDTO.setFileUuid(fileEntity.getFileUuid());
		}
	}

	@Override
	public void createFile(FileDTO fileDTO) throws BusinessException {
		
		try{
			FileEntity fileEntity = new FileEntity();
			DTO2Entity(fileDTO, fileEntity);
			fileDAO.createFile(fileEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
		
	}

	@Override
	public void updateFile(FileDTO fileDTO) throws BusinessException {
		try{
			FileEntity fileEntity = fileDAO.getFileByUuid(fileDTO.getFileUuid());
			if(fileDTO.getFileName() != null){
				fileEntity.setFileName(fileDTO.getFileName());
			}
			if(fileDTO.getSize() != 0){
				fileEntity.setSize(fileDTO.getSize());
			}
			if(fileDTO.getOssKey() != null){
				fileEntity.setOssKey(fileDTO.getOssKey());
			}
			if(fileDTO.getUrl() != null){
				fileEntity.setUrl(fileDTO.getUrl());
			}
			
			fileDAO.updateFile(fileEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public void deleteFile(FileDTO fileDTO) throws BusinessException {
		try{
			FileEntity fileEntity = fileDAO.getFileByUuid(fileDTO.getFileUuid());
			fileDAO.deleteFile(fileEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public FileDTO getFileByUuid(String fileUuid) throws BusinessException {
		try{
			FileEntity fileEntity = fileDAO.getFileByUuid(fileUuid);
			FileDTO fileDTO = new FileDTO();
			Entity2DTO(fileEntity, fileDTO);
			return fileDTO;
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}

	
}






