
package com.mb.ext.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.constant.NoteConstants;
import com.mb.ext.core.dao.NoteChannelDAO;
import com.mb.ext.core.dao.NoteDAO;
import com.mb.ext.core.dao.NoteDefDAO;
import com.mb.ext.core.dao.SequenceDAO;
import com.mb.ext.core.dao.UserDAO;
import com.mb.ext.core.entity.NoteChannelEntity;
import com.mb.ext.core.entity.NoteEntity;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.NoteService;
import com.mb.ext.core.service.UserService;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.NoteDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.util.MailSenderUtil;
import com.mb.ext.core.util.SMSSenderUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.service.AbstractService;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;


@Service
@Qualifier("NoteService")
public class NoteServiceImpl extends AbstractService implements NoteService<BodyDTO>
{
	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;

	@Autowired
	@Qualifier("noteChannelDAO")
	private NoteChannelDAO noteChannelDAO;
	
	@Autowired
	@Qualifier("sequenceDAO")
	private SequenceDAO sequenceDAO;
	
	@Autowired
	@Qualifier("noteDefDAO")
	private NoteDefDAO noteDefDAO;
	
	@Autowired
	@Qualifier("noteDAO")
	private NoteDAO noteDAO;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Autowired
	@Qualifier("propertyRepository")
	private PropertyRepository propertyRepository;
	
	@Autowired
	private MailSenderUtil mailSenderUtil;
	
	@Autowired
	private SMSSenderUtil smsSenderUtil;
	
	
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());
	@Override
	public List<NoteDTO> getNotes(UserDTO userDTO) throws BusinessException {
		
		List<NoteDTO> noteDTOList = new ArrayList<NoteDTO>();
		try{
			
			UserEntity userEntity = userService.getUser(userDTO);
			List<NoteEntity> noteEntityList = noteDAO.getNotes(userEntity);
			for(Iterator<NoteEntity> iter = noteEntityList.iterator(); iter.hasNext();){
				NoteEntity noteEntity = iter.next();
				NoteDTO noteDTO = new NoteDTO();
				noteDTO.setAction(noteEntity.getAction());
				noteDTO.setContent(noteEntity.getContent());
				noteDTO.setNoteStatus(noteEntity.getStatus());
				noteDTO.setSendTime(noteEntity.getSendTime());
				noteDTO.setNoteUuid(noteEntity.getNoteUuid());
				noteDTO.setApplication(noteEntity.getApplication());
				noteDTOList.add(noteDTO);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return noteDTOList;
		
	}




	@Override
	public void markRead(NoteDTO noteDTO) throws BusinessException {
		try{
			String noteUuid = noteDTO.getNoteUuid();
			NoteEntity noteEntity = noteDAO.getNote(noteUuid);
			noteEntity.setStatus(NoteConstants.NOTE_STATUS_READ);
			noteDAO.updateNote(noteEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}




	@Override
	public void deleteNote(NoteDTO noteDTO) throws BusinessException {
		try{
			String noteUuid = noteDTO.getNoteUuid();
			NoteEntity noteEntity = noteDAO.getNote(noteUuid);
			noteDAO.deleteNote(noteEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}




	@Override
	public void sendNotification(UserDTO userDTO, String noteType, String[] params)
			throws BusinessException {
		
		String application = propertyRepository.getProperty("notification."+noteType+".application");
		String noteAction = propertyRepository.getProperty("notification."+noteType+".action");
		String mailSubject = propertyRepository.getProperty("notification."+noteType+".mail.subject");
		String mailBody = propertyRepository.getProperty("notification."+noteType+".mail.body");
		String smsBody = propertyRepository.getProperty("notification."+noteType+".sms.body");
		String webInboxBody = propertyRepository.getProperty("notification."+noteType+".webinbox.body");
		UserEntity userEntity = userService.getUser(userDTO);
		try{
			//send email
			/*if(isEmailEnabled(noteType)&&!StringUtils.isEmpty(mailSubject)){
				for(int i=0; i<params.length;i++){
					mailBody = mailBody.replace("{"+(i+1)+"}", params[i]);
				}
				if(userEntity != null && !StringUtils.isEmpty(userEntity.getPersonalEmail()));
					mailSenderUtil.sendMail(mailSubject, mailBody, userDTO.getPersonalEmail(), null, null);
			}*/
			//send SMS
			/*if(isSMSEnabled(noteType)&&!StringUtils.isEmpty(smsBody)){
				for(int i=0; i<params.length;i++){
					smsBody = smsBody.replace("{"+(i+1)+"}", params[i]);
				}
				if(userEntity != null && !StringUtils.isEmpty(userEntity.getPersonalPhone()));
					smsSenderUtil.sendSMS(smsBody, userEntity.getPersonalPhoneCountryCode(), userEntity.getPersonalPhone());
			}*/
			//send web inbox
			if(!StringUtils.isEmpty(webInboxBody)){
				NoteEntity noteEntity = new NoteEntity();
				noteEntity.setUserEntity(userEntity);
				noteEntity.setSendTime(new Date());
				noteEntity.setAction(noteAction);
				noteEntity.setStatus(NoteConstants.NOTE_STATUS_UNREAD);
				noteEntity.setApplication(application);
				noteEntity.setCreateBy("system");
				noteEntity.setUpdateBy("system");
				for(int i=0; i<params.length;i++){
					webInboxBody = webInboxBody.replace("{"+(i+1)+"}", params[i]);
				}
				noteEntity.setContent(webInboxBody);
				noteDAO.createNote(noteEntity);
			}
		}catch(DAOException e){
			logger.error(e.getMessage());
			logger.error("send notification error, notification type is : "+ noteType);
		}
	}




	@Override
	public int getUnreadNoteCount(UserDTO userDTO) throws BusinessException {
		try{
			UserEntity userEntity = userService.getUser(userDTO);
			return noteDAO.getUnreadNoteCount(userEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}




	@Override
	public boolean isSMSEnabled(String noteType) throws BusinessException {
		boolean isSMSEnabled = false;
		try{
			NoteChannelEntity channelEntity = noteChannelDAO.getNoteChannelByType(noteType);
			if(channelEntity != null && channelEntity.isSMSEnabled())
				isSMSEnabled = true;
		}catch(DAOException e){
			logger.error(e.getMessage());
			logger.error("send notification error, notification type is : "+ noteType);
		}
		return isSMSEnabled;
	}




	@Override
	public boolean isEmailEnabled(String noteType) throws BusinessException {
		boolean isEmailEnabled = false;
		try{
			NoteChannelEntity channelEntity = noteChannelDAO.getNoteChannelByType(noteType);
			if(channelEntity != null && channelEntity.isEmailEnabled())
				isEmailEnabled = true;
		}catch(DAOException e){
			logger.error(e.getMessage());
			logger.error("send notification error, notification type is : "+ noteType);
		}
		return isEmailEnabled;
	}




	@Override
	public boolean isWechatEnabled(String noteType) throws BusinessException {
		boolean isWechatEnabled = false;
		try{
			NoteChannelEntity channelEntity = noteChannelDAO.getNoteChannelByType(noteType);
			if(channelEntity != null && channelEntity.isWechatEnabled())
				isWechatEnabled = true;
		}catch(DAOException e){
			logger.error(e.getMessage());
			logger.error("send notification error, notification type is : "+ noteType);
		}
		return isWechatEnabled;
	}




	@Override
	@Transactional
	public void markUserNoteRead(UserDTO userDTO) throws BusinessException {
		try{
			UserEntity userEntity = new UserEntity();
			if(!StringUtils.isEmpty(userDTO.getUserUuid())){
				userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
				noteDAO.markUserNoteRead(userEntity);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}

	
}






