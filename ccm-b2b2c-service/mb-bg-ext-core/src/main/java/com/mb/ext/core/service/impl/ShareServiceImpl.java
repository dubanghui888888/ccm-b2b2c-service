package com.mb.ext.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mb.ext.core.constant.ContentConstants;
import com.mb.ext.core.dao.SwiperDAO;
import com.mb.ext.core.dao.UserDAO;
import com.mb.ext.core.dao.content.MateriaDAO;
import com.mb.ext.core.dao.content.PosterDAO;
import com.mb.ext.core.dao.content.SceneDAO;
import com.mb.ext.core.dao.content.ShareCommentAtDAO;
import com.mb.ext.core.dao.content.ShareCommentDAO;
import com.mb.ext.core.dao.content.ShareDAO;
import com.mb.ext.core.dao.content.ShareImageDAO;
import com.mb.ext.core.dao.content.ShareLikeDAO;
import com.mb.ext.core.dao.content.TagDAO;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.content.ShareCommentAtEntity;
import com.mb.ext.core.entity.content.ShareCommentEntity;
import com.mb.ext.core.entity.content.ShareEntity;
import com.mb.ext.core.entity.content.ShareImageEntity;
import com.mb.ext.core.entity.content.ShareLikeEntity;
import com.mb.ext.core.entity.content.TagEntity;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.ShareService;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.content.ShareCommentAtDTO;
import com.mb.ext.core.service.spec.content.ShareCommentDTO;
import com.mb.ext.core.service.spec.content.ShareDTO;
import com.mb.ext.core.service.spec.content.ShareDTOList;
import com.mb.ext.core.service.spec.content.ShareImageDTO;
import com.mb.ext.core.service.spec.content.ShareLikeDTO;
import com.mb.ext.core.service.spec.content.TagDTO;
import com.mb.ext.core.util.MailSenderUtil;
import com.mb.ext.core.util.SMSSenderUtil;
import com.mb.ext.core.util.WXInitializeUtility;
import com.mb.ext.core.util.WXJSAPISDKUtility;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.service.AbstractService;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;
import com.mb.ext.msg.WXJSONNews;
import com.mb.ext.msg.WXJSONNewsArticle;

@Service
@Qualifier("ShareService")
public class ShareServiceImpl extends AbstractService implements
		ShareService<BodyDTO> {
	

	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;
	
	@Autowired
	@Qualifier("tagDAO")
	private TagDAO tagDAO;
	
	@Autowired
	@Qualifier("materiaDAO")
	private MateriaDAO materiaDAO;
	
	@Autowired
	@Qualifier("shareCommentDAO")
	private ShareCommentDAO shareCommentDAO;
	
	@Autowired
	@Qualifier("shareLikeDAO")
	private ShareLikeDAO shareLikeDAO;
	
	@Autowired
	@Qualifier("shareCommentAtDAO")
	private ShareCommentAtDAO shareCommentAtDAO;
	
	@Autowired
	@Qualifier("posterDAO")
	private PosterDAO posterDAO;
		

	@Autowired
	@Qualifier("swiperDAO")
	private SwiperDAO swiperDAO;
	
	@Autowired
	@Qualifier("sceneDAO")
	private SceneDAO sceneDAO;
	
	@Autowired
	@Qualifier("shareDAO")
	private ShareDAO shareDAO;
	
	@Autowired
	@Qualifier("shareImageDAO")
	private ShareImageDAO shareImageDAO;

	@Autowired
	private MailSenderUtil mailSenderUtil;

	@Autowired
	private SMSSenderUtil smsSenderUtil;
	
	@Autowired
	private WXJSAPISDKUtility wxJsApiSdkUtility;
	
	@Autowired
	private WXInitializeUtility wxInitializeUtility;

	@Autowired
	private PropertyRepository propertyRepository;

	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());


	@Override
	public String addShare(ShareDTO shareDTO) throws BusinessException {
		
		try{
			ShareEntity shareEntity = new ShareEntity();
			shareEntity.setContent(shareDTO.getContent());
//			shareEntity.setPublishTime(new Date());
			shareEntity.setPublished(false);
			if(shareDTO.getTagDTO() != null){
				TagEntity tagEntity = tagDAO.getTagByTypeName(ContentConstants.TAG_TYPE_ARTICLE, shareDTO.getTagDTO().getTagName());
				shareEntity.setTagEntity(tagEntity);
			}
			if(shareDTO.getUserDTO() != null){
				UserEntity userEntity = userDAO.getUserByUuid(shareDTO.getUserDTO().getUserUuid());
				shareEntity.setUserEntity(userEntity);
			}
			shareEntity.setCreateBy("Admin");
			shareEntity.setUpdateBy("Admin");
			shareDAO.addShare(shareEntity);
			
			List<ShareImageDTO> images = shareDTO.getImages();
			if(images != null){
				for(Iterator<ShareImageDTO> iter = images.iterator();iter.hasNext();){
					ShareImageDTO imageDTO = iter.next();
					ShareImageEntity imageEntity = new ShareImageEntity();
					imageEntity.setUrl(imageDTO.getUrl());
					imageEntity.setShareEntity(shareEntity);
					imageEntity.setCreateBy("Admin");
					imageEntity.setUpdateBy("Admin");
					shareImageDAO.addShareImage(imageEntity);
				}
			}
			return shareEntity.getShareUuid();
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void updateShare(ShareDTO shareDTO) throws BusinessException {
		
		try{
			ShareEntity shareEntity = shareDAO.getShareByUuid(shareDTO.getShareUuid());
			shareEntity.setContent(shareDTO.getContent());
//			shareEntity.setPublishTime(shareDTO.getPublishTime());
			if(shareDTO.getTagDTO() != null){
				TagEntity tagEntity = tagDAO.getTagByTypeName(ContentConstants.TAG_TYPE_ARTICLE, shareDTO.getTagDTO().getTagName());
				shareEntity.setTagEntity(tagEntity);
			}
			shareDAO.updateShare(shareEntity);
			
			List<ShareImageEntity> imageEntityList = shareImageDAO.getShareImagesByShare(shareEntity);
			if(imageEntityList != null){
				for(Iterator<ShareImageEntity> iter = imageEntityList.iterator();iter.hasNext();){
					ShareImageEntity imageEntity = iter.next();
					shareImageDAO.deleteShareImage(imageEntity);
				}
			}
			
			List<ShareImageDTO> images = shareDTO.getImages();
			if(images != null){
				for(Iterator<ShareImageDTO> iter = images.iterator();iter.hasNext();){
					ShareImageDTO imageDTO = iter.next();
					ShareImageEntity imageEntity = new ShareImageEntity();
					imageEntity.setUrl(imageDTO.getUrl());
					imageEntity.setShareEntity(shareEntity);
					shareImageDAO.addShareImage(imageEntity);
				}
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void commentShare(ShareCommentDTO shareCommentDTO) throws BusinessException {
		
		try{
			ShareDTO shareDTO = shareCommentDTO.getShareDTO();
			UserDTO userDTO = shareCommentDTO.getUserDTO();
			ShareEntity shareEntity = shareDAO.getShareByUuid(shareDTO.getShareUuid());
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			ShareCommentEntity shareComment = new ShareCommentEntity();
			shareComment.setShareEntity(shareEntity);
			shareComment.setUserEntity(userEntity);
			shareComment.setComment(shareCommentDTO.getComment());
			shareComment.setCommentTime(new Date());
			shareCommentDAO.addShareComment(shareComment);
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void atShareComment(ShareCommentAtDTO shareCommentAtDTO) throws BusinessException {
		
		try{
			ShareCommentDTO shareCommentDTO = shareCommentAtDTO.getShareCommentDTO();
			UserDTO userDTO = shareCommentAtDTO.getUserDTO();
			ShareCommentEntity shareCommentEntity = shareCommentDAO.getShareCommentByUuid(shareCommentDTO.getShareCommentUuid());
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			ShareCommentAtEntity shareCommentAt = new ShareCommentAtEntity();
			shareCommentAt.setShareCommentEntity(shareCommentEntity);
			shareCommentAt.setUserEntity(userEntity);
			shareCommentAt.setComment(shareCommentAtDTO.getComment());
			shareCommentAt.setCommentTime(new Date());
			shareCommentAtDAO.addShareCommentAt(shareCommentAt);
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void likeShare(ShareDTO shareDTO) throws BusinessException {
		
		try{
			ShareEntity shareEntity = shareDAO.getShareByUuid(shareDTO.getShareUuid());
			UserDTO userDTO = shareDTO.getUserDTO();
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			
			if(shareLikeDAO.getShareLike(shareEntity, userEntity)==null){
				shareEntity.setLikes(shareEntity.getLikes()+1);
				shareDAO.updateShare(shareEntity);
				
				ShareLikeEntity shareLikeEntity = new ShareLikeEntity();
				shareLikeEntity.setShareEntity(shareEntity);
				shareLikeEntity.setUserEntity(userEntity);
				shareLikeDAO.addShareLike(shareLikeEntity);
			}
			
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public void cancelLikeShare(ShareDTO shareDTO) throws BusinessException {
		
		try{
			ShareEntity shareEntity = shareDAO.getShareByUuid(shareDTO.getShareUuid());
			UserDTO userDTO = shareDTO.getUserDTO();
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			
			ShareLikeEntity shareLikeEntity = shareLikeDAO.getShareLike(shareEntity, userEntity);
			if(shareLikeEntity != null){
				shareLikeDAO.deleteShareLike(shareLikeEntity);
				shareEntity.setLikes(shareEntity.getLikes()-1);
				shareDAO.updateShare(shareEntity);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	@Override
	public void publishShare(ShareDTO shareDTO) throws BusinessException {
		
		try{
			ShareEntity shareEntity = shareDAO.getShareByUuid(shareDTO.getShareUuid());
			if(!shareEntity.isPublished()){	
				shareEntity.setPublishTime(new Date());
				shareEntity.setPublished(true);
				shareDAO.updateShare(shareEntity);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void deleteShare(ShareDTO shareDTO) throws BusinessException {
		
		try{
			ShareEntity shareEntity = shareDAO.getShareByUuid(shareDTO.getShareUuid());
			
			List<ShareImageEntity> imageEntityList = shareImageDAO.getShareImagesByShare(shareEntity);
			for(Iterator<ShareImageEntity> iter = imageEntityList.iterator();iter.hasNext();){
				ShareImageEntity imageEntity = iter.next();
				shareImageDAO.deleteShareImage(imageEntity);
			}
			
			List<ShareCommentEntity> commentEntityList = shareCommentDAO.getShareCommentsByShare(shareEntity);
			for(Iterator<ShareCommentEntity> iter = commentEntityList.iterator();iter.hasNext();){
				ShareCommentEntity commentEntity = iter.next();
				
				List<ShareCommentAtEntity> atEntityList = shareCommentAtDAO.getShareCommentAtsByShareComment(commentEntity);
				for (Iterator<ShareCommentAtEntity> iterator = atEntityList.iterator(); iterator
						.hasNext();) {
					ShareCommentAtEntity shareCommentAtEntity = iterator
							.next();
					shareCommentAtDAO.deleteShareCommentAt(shareCommentAtEntity);
					
				}
				shareCommentDAO.deleteShareComment(commentEntity);
			}
			
			shareDAO.deleteShare(shareEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void deleteMultipleShares(ShareDTOList shareDTOList) throws BusinessException {
		
		try{
			List<ShareDTO> list = shareDTOList.getShares();
			for(Iterator<ShareDTO> iter = list.iterator();iter.hasNext();){
				ShareDTO shareDTO = iter.next();
				deleteShare(shareDTO);
			}
			
		}catch(Exception e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public List<ShareDTO> getSharesByCreateDate(Date createDate)
			throws BusinessException {
		List<ShareDTO> shareDTOList = new ArrayList<ShareDTO>(); 
		try{
			List<ShareEntity> shareEntityList = shareDAO.getSharesByDay(createDate);
			for(Iterator<ShareEntity> iter = shareEntityList.iterator();iter.hasNext();){
				ShareEntity shareEntity = iter.next();
				ShareDTO shareDTO = new ShareDTO();
				shareEntity2DTO(shareEntity, shareDTO);
				shareDTOList.add(shareDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return shareDTOList;
	}

	@Override
	public List<ShareDTO> getSharesByTagName(String tagName)
			throws BusinessException {
		List<ShareDTO> shareDTOList = new ArrayList<ShareDTO>(); 
		try{
			List<ShareEntity> shareEntityList = shareDAO.getSharesByTagName(tagName);
			for(Iterator<ShareEntity> iter = shareEntityList.iterator();iter.hasNext();){
				ShareEntity shareEntity = iter.next();
				ShareDTO shareDTO = new ShareDTO();
				shareEntity2DTO(shareEntity, shareDTO);
				shareDTOList.add(shareDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return shareDTOList;
	}
	
	@Override
	public List<ShareDTO> getSharesByUser(UserDTO userDTO)
			throws BusinessException {
		List<ShareDTO> shareDTOList = new ArrayList<ShareDTO>(); 
		try{
			if(userDTO != null) {
				UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
				List<ShareEntity> shareEntityList = shareDAO.getSharesByUser(userEntity);
				for(Iterator<ShareEntity> iter = shareEntityList.iterator();iter.hasNext();){
					ShareEntity shareEntity = iter.next();
					ShareDTO shareDTO = new ShareDTO();
					shareEntity2DTO(shareEntity, shareDTO);
					shareDTOList.add(shareDTO);
				}
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return shareDTOList;
	}
	
	@Override
	public List<ShareDTO> getSharesByTagNamePage(String tagName, int startIndex, int pageSize)
			throws BusinessException {
		List<ShareDTO> shareDTOList = new ArrayList<ShareDTO>(); 
		try{
			List<ShareEntity> shareEntityList = shareDAO.getSharesByTagNamePage(tagName, startIndex, pageSize);
			for(Iterator<ShareEntity> iter = shareEntityList.iterator();iter.hasNext();){
				ShareEntity shareEntity = iter.next();
				ShareDTO shareDTO = new ShareDTO();
				shareEntity2DTO(shareEntity, shareDTO);
				shareDTOList.add(shareDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return shareDTOList;
	}
	
	@Override
	public List<ShareDTO> getPublishedSharesByTagNamePage(String tagName, int startIndex, int pageSize)
			throws BusinessException {
		List<ShareDTO> shareDTOList = new ArrayList<ShareDTO>(); 
		try{
			List<ShareEntity> shareEntityList = shareDAO.getPublishedSharesByTagNamePage(tagName, startIndex, pageSize);
			for(Iterator<ShareEntity> iter = shareEntityList.iterator();iter.hasNext();){
				ShareEntity shareEntity = iter.next();
				ShareDTO shareDTO = new ShareDTO();
				shareEntity2DTO(shareEntity, shareDTO);
				shareDTOList.add(shareDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return shareDTOList;
	}

	@Override
	public List<ShareDTO> getShares() throws BusinessException {
		List<ShareDTO> shareDTOList = new ArrayList<ShareDTO>(); 
		try{
			List<ShareEntity> shareEntityList = shareDAO.getShares();
			for(Iterator<ShareEntity> iter = shareEntityList.iterator();iter.hasNext();){
				ShareEntity shareEntity = iter.next();
				ShareDTO shareDTO = new ShareDTO();
				shareEntity2DTO(shareEntity, shareDTO);
				shareDTOList.add(shareDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return shareDTOList;
	}

	@Override
	public List<ShareDTO> getSharesByPage(int startIndex, int pageSize) throws BusinessException {
		List<ShareDTO> shareDTOList = new ArrayList<ShareDTO>(); 
		try{
			List<ShareEntity> shareEntityList = shareDAO.getSharesByPage(startIndex, pageSize);
			for(Iterator<ShareEntity> iter = shareEntityList.iterator();iter.hasNext();){
				ShareEntity shareEntity = iter.next();
				ShareDTO shareDTO = new ShareDTO();
				shareEntity2DTO(shareEntity, shareDTO);
				shareDTOList.add(shareDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return shareDTOList;
	}
	
	@Override
	public List<ShareDTO> getPublishedSharesByPage(int startIndex, int pageSize) throws BusinessException {
		List<ShareDTO> shareDTOList = new ArrayList<ShareDTO>(); 
		try{
			List<ShareEntity> shareEntityList = shareDAO.getPublishedSharesByPage(startIndex, pageSize);
			for(Iterator<ShareEntity> iter = shareEntityList.iterator();iter.hasNext();){
				ShareEntity shareEntity = iter.next();
				ShareDTO shareDTO = new ShareDTO();
				shareEntity2DTO(shareEntity, shareDTO);
				shareDTOList.add(shareDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return shareDTOList;
	}
	
	@Override
	public int getShareCountByTagName(String tagName) throws BusinessException {
		try {
			return shareDAO.getShareCountByTagName(tagName);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public int getPublishedShareCountByTagName(String tagName) throws BusinessException {
		try {
			return shareDAO.getPublishedShareCountByTagName(tagName);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public int getShareCount() throws BusinessException {
		try {
			return shareDAO.getShareCount();
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public int getPublishedShareCount() throws BusinessException {
		try {
			return shareDAO.getPublishedShareCount();
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public ShareDTO getShareByUuid(String uuid) throws BusinessException {
		try{
			ShareEntity shareEntity = shareDAO.getShareByUuid(uuid);
			ShareDTO shareDTO = new ShareDTO();
			shareEntity2DTO(shareEntity, shareDTO);
			return shareDTO;
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	void shareEntity2DTO(ShareEntity shareEntity, ShareDTO shareDTO) throws DAOException{
		shareDTO.setShareUuid(shareEntity.getShareUuid());
		shareDTO.setContent(shareEntity.getContent());
		shareDTO.setPublishTime(shareEntity.getPublishTime());
		shareDTO.setPublished(shareEntity.isPublished());
		shareDTO.setLikes(shareEntity.getLikes());
		shareDTO.setUpdateDate(shareEntity.getUpdateDate());
		shareDTO.setCreateDate(shareEntity.getCreateDate());
		TagEntity tagEntity = shareEntity.getTagEntity();
		if(tagEntity != null){
			TagDTO tagDTO = new TagDTO();
			tagDTO.setTagUuid(tagEntity.getTagUuid());
			tagDTO.setTagName(tagEntity.getTagName());
			tagDTO.setTagType(tagEntity.getTagType());
			shareDTO.setTagDTO(tagDTO);
		}
		UserEntity userEntity = shareEntity.getUserEntity();
		if(userEntity != null){
			UserDTO userDTO = new UserDTO();
			userDTO.setUserUuid(userEntity.getUserUuid());
			userDTO.setName(userEntity.getName());
			userDTO.setPersonalPhone(userEntity.getPersonalPhone());
			userDTO.setPhotoUrl(userEntity.getPhotoUrl());
			shareDTO.setUserDTO(userDTO);
		}
		List<ShareImageEntity> imageEntityList = shareImageDAO.getShareImagesByShare(shareEntity);
		if(imageEntityList != null){
			List<ShareImageDTO> imageDTOList = new ArrayList<ShareImageDTO>();
			for(Iterator<ShareImageEntity> iter = imageEntityList.iterator();iter.hasNext();){
				ShareImageEntity imageEntity = iter.next();
				ShareImageDTO imageDTO = new ShareImageDTO();
				imageDTO.setUrl(imageEntity.getUrl());
				imageDTOList.add(imageDTO);
			}
			shareDTO.setImages(imageDTOList);
		}
		
		List<ShareCommentEntity> commentEntityList = shareCommentDAO.getShareCommentsByShare(shareEntity);
		if(commentEntityList != null){
			List<ShareCommentDTO> commentDTOList = new ArrayList<ShareCommentDTO>();
			for(Iterator<ShareCommentEntity> iter = commentEntityList.iterator();iter.hasNext();){
				ShareCommentEntity commentEntity = iter.next();
				ShareCommentDTO commentDTO = new ShareCommentDTO();
				commentDTO.setShareCommentUuid(commentEntity.getShareCommentUuid());
				commentDTO.setComment(commentEntity.getComment());
				UserEntity commentUserEntity = commentEntity.getUserEntity();
				if(commentUserEntity != null){
					UserDTO commentUserDTO = new UserDTO();
					commentUserDTO.setUserUuid(commentUserEntity.getUserUuid());
					commentUserDTO.setName(commentUserEntity.getName());
					commentUserDTO.setPersonalPhone(commentUserEntity.getPersonalPhone());
					commentUserDTO.setPhotoUrl(commentUserEntity.getPhotoUrl());
					commentDTO.setUserDTO(commentUserDTO);
				}
				List<ShareCommentAtDTO> ats = new ArrayList<ShareCommentAtDTO>();
				List<ShareCommentAtEntity> atEntityList = shareCommentAtDAO.getShareCommentAtsByShareComment(commentEntity);
				for (Iterator<ShareCommentAtEntity> iterator = atEntityList.iterator(); iterator
						.hasNext();) {
					ShareCommentAtEntity shareCommentAtEntity = iterator
							.next();
					ShareCommentAtDTO shareCommentAtDTO = new ShareCommentAtDTO();
					shareCommentAtDTO.setComment(shareCommentAtEntity.getComment());
					shareCommentAtDTO.setCommentTime(shareCommentAtEntity.getCommentTime());
					UserEntity shareCommentAtUserEntity = shareCommentAtEntity.getUserEntity();
					UserDTO shareCommentAtUserDTO = new UserDTO();  
					shareCommentAtUserDTO.setUserUuid(shareCommentAtUserEntity.getUserUuid());
					shareCommentAtUserDTO.setName(shareCommentAtUserEntity.getName());
					shareCommentAtDTO.setUserDTO(shareCommentAtUserDTO);
					ats.add(shareCommentAtDTO);
				}
				commentDTO.setAts(ats);
				commentDTOList.add(commentDTO);
			}
			shareDTO.setComments(commentDTOList);
		}
		List<ShareLikeEntity> shareLikeEntityList = shareLikeDAO.getShareLikesByShare(shareEntity);
		List<ShareLikeDTO> shareLikeDTOList = new ArrayList<ShareLikeDTO>(); 
		for (Iterator<ShareLikeEntity> iterator = shareLikeEntityList.iterator(); iterator
				.hasNext();) {
			ShareLikeEntity shareLikeEntity = (ShareLikeEntity) iterator.next();
			ShareLikeDTO shareLikeDTO = new ShareLikeDTO();
			UserEntity likeUserEntity = shareLikeEntity.getUserEntity();
			UserDTO likeUserDTO = new UserDTO();
			likeUserDTO.setUserUuid(likeUserEntity.getUserUuid());
			likeUserDTO.setName(likeUserEntity.getName());
			shareLikeDTO.setUserDTO(likeUserDTO);
			shareLikeDTOList.add(shareLikeDTO);
		}
		shareDTO.setShareLikes(shareLikeDTOList);
	}

	
	class SendMsgNewsThread implements Runnable {
		
		private final LogHelper logger = LogHelper.getInstance(this.getClass()
				.getName());

		private ShareDTO shareDTO;
		
		public ShareDTO getShareDTO() {
			return shareDTO;
		}

		public void setShareDTO(ShareDTO shareDTO) {
			this.shareDTO = shareDTO;
		}

		@Override
		public void run() {
			List<UserEntity> userEntityList;
			try {
				userEntityList = userDAO.getAllUsers();
				for(Iterator<UserEntity> iter = userEntityList.iterator();iter.hasNext();){
		    		UserEntity userEntity = iter.next();
		    		String openId = userEntity.getOpenId();
		    		if(!StringUtils.isEmpty(openId)){
		    			WXJSONNews news = new WXJSONNews();
		    			List<WXJSONNewsArticle> shares = new ArrayList<WXJSONNewsArticle>();
		    			WXJSONNewsArticle share = new WXJSONNewsArticle(); 
		    			share.setTitle("亲爱的云集家人, 为您准备了新的素材, 赶快发到朋友圈吧 !");
		    			share.setDescription(shareDTO.getContent());
		    			share.setUrl("http://yunji.qinghuiyang.com/scrm-web/#/Share?uid="+userEntity.getUserUuid());
		    			List<ShareImageDTO> imageDTOList = shareDTO.getImages();
		    			if(imageDTOList != null && imageDTOList.size()>0){
		    				share.setPicurl(imageDTOList.get(0).getUrl());
		    			}
		    			shares.add(share);
		    			news.setArticles(shares);
		    			wxInitializeUtility.sendMessageNews(news, openId);
		    		}
		    	}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
	    	

		}
	}

}
