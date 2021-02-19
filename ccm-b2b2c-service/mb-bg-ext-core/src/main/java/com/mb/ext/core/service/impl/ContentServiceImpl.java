package com.mb.ext.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mb.ext.core.constant.AdConstants;
import com.mb.ext.core.constant.ContentConstants;
import com.mb.ext.core.dao.AdDAO;
import com.mb.ext.core.dao.GroupDAO;
import com.mb.ext.core.dao.SwiperDAO;
import com.mb.ext.core.dao.UserDAO;
import com.mb.ext.core.dao.content.ArticleDAO;
import com.mb.ext.core.dao.content.MateriaDAO;
import com.mb.ext.core.dao.content.PosterDAO;
import com.mb.ext.core.dao.content.QaDAO;
import com.mb.ext.core.dao.content.SceneDAO;
import com.mb.ext.core.dao.content.TagDAO;
import com.mb.ext.core.dao.product.ProductCateDAO;
import com.mb.ext.core.dao.product.ProductDAO;
import com.mb.ext.core.entity.AdEntity;
import com.mb.ext.core.entity.GroupEntity;
import com.mb.ext.core.entity.SwiperEntity;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.content.ArticleEntity;
import com.mb.ext.core.entity.content.MateriaEntity;
import com.mb.ext.core.entity.content.PosterEntity;
import com.mb.ext.core.entity.content.QaEntity;
import com.mb.ext.core.entity.content.SceneEntity;
import com.mb.ext.core.entity.content.TagEntity;
import com.mb.ext.core.entity.product.ProductCateEntity;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.ContentService;
import com.mb.ext.core.service.spec.AdDTO;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.GroupDTO;
import com.mb.ext.core.service.spec.SwiperDTO;
import com.mb.ext.core.service.spec.content.ArticleDTO;
import com.mb.ext.core.service.spec.content.ArticleDTOList;
import com.mb.ext.core.service.spec.content.MateriaDTO;
import com.mb.ext.core.service.spec.content.PosterDTO;
import com.mb.ext.core.service.spec.content.QaDTO;
import com.mb.ext.core.service.spec.content.QaDTOList;
import com.mb.ext.core.service.spec.content.SceneDTO;
import com.mb.ext.core.service.spec.content.TagDTO;
import com.mb.ext.core.service.spec.product.ProductCateDTO;
import com.mb.ext.core.service.spec.product.ProductDTO;
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
@Qualifier("ContentService")
public class ContentServiceImpl extends AbstractService implements
		ContentService<BodyDTO> {
	

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
	@Qualifier("qaDAO")
	private QaDAO qaDAO;
	
	@Autowired
	@Qualifier("posterDAO")
	private PosterDAO posterDAO;
		
	@Autowired
	@Qualifier("adDAO")
	private AdDAO adDAO;
	
	@Autowired
	@Qualifier("groupDAO")
	private GroupDAO groupDAO;
	
	@Autowired
	@Qualifier("swiperDAO")
	private SwiperDAO swiperDAO;
	
	@Autowired
	@Qualifier("sceneDAO")
	private SceneDAO sceneDAO;
	
	@Autowired
	@Qualifier("articleDAO")
	private ArticleDAO articleDAO;
	
	@Autowired
	@Qualifier("productCateDAO")
	private ProductCateDAO productCateDAO;
	
	@Autowired
	@Qualifier("productDAO")
	private ProductDAO productDAO;
	
	@Autowired
	private WXJSAPISDKUtility wxJsApiSdkUtility;
	
	@Autowired
	private WXInitializeUtility wxInitializeUtility;

	@Autowired
	private MailSenderUtil mailSenderUtil;

	@Autowired
	private SMSSenderUtil smsSenderUtil;

	@Autowired
	private PropertyRepository propertyRepository;

	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	@Override
	public void addPosterTag(TagDTO tagDTO) throws BusinessException {
		try{
			String tagType = tagDTO.getTagType();
			String tagName = tagDTO.getTagName();
			if(tagDAO.getTagByTypeName(tagType, tagName)==null){
				TagEntity tagEntity = new TagEntity();
				tagEntity.setTagType(ContentConstants.TAG_TYPE_POSTER);
				tagEntity.setTagName(tagName);
				tagDAO.addTag(tagEntity);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void addSceneTag(TagDTO tagDTO) throws BusinessException {
		try{
			String tagType = tagDTO.getTagType();
			String tagName = tagDTO.getTagName();
			if(tagDAO.getTagByTypeName(tagType, tagName)==null){
				TagEntity tagEntity = new TagEntity();
				tagEntity.setTagType(ContentConstants.TAG_TYPE_SCENE);
				tagEntity.setTagName(tagName);
				tagDAO.addTag(tagEntity);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void addArticleTag(TagDTO tagDTO) throws BusinessException {
		try{
			String tagType = tagDTO.getTagType();
			String tagName = tagDTO.getTagName();
			if(tagDAO.getTagByTypeName(tagType, tagName)==null){
				TagEntity tagEntity = new TagEntity();
				tagEntity.setTagType(ContentConstants.TAG_TYPE_ARTICLE);
				tagEntity.setTagName(tagName);
				tagDAO.addTag(tagEntity);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void addQaTag(TagDTO tagDTO) throws BusinessException {
		try{
			String tagType = tagDTO.getTagType();
			String tagName = tagDTO.getTagName();
			if(tagDAO.getTagByTypeName(tagType, tagName)==null){
				TagEntity tagEntity = new TagEntity();
				tagEntity.setTagType(ContentConstants.TAG_TYPE_QA);
				tagEntity.setTagName(tagName);
				tagDAO.addTag(tagEntity);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public void deletePosterTag(TagDTO tagDTO) throws BusinessException {
		try{
			String tagName = tagDTO.getTagName();
			String tagUuid = tagDTO.getTagUuid();
			if(!StringUtils.isEmpty(tagUuid)){
				TagEntity tagEntity = tagDAO.getTagByUuid(tagUuid);
				if(tagEntity != null){
					List<PosterEntity> posterEntityList = posterDAO.getPostersByTag(tagEntity);
					for(Iterator<PosterEntity> iter = posterEntityList.iterator();iter.hasNext();){
						PosterEntity posterEntity = iter.next();
						posterEntity.setTagEntity(null);
						posterDAO.updatePoster(posterEntity);
					}
					tagDAO.deleteTag(tagEntity);
				}
			}else{
				TagEntity tagEntity = tagDAO.getTagByTypeName(ContentConstants.TAG_TYPE_POSTER, tagName);
				if(tagEntity != null){
					List<PosterEntity> posterEntityList = posterDAO.getPostersByTag(tagEntity);
					for(Iterator<PosterEntity> iter = posterEntityList.iterator();iter.hasNext();){
						PosterEntity posterEntity = iter.next();
						posterEntity.setTagEntity(null);
						posterDAO.updatePoster(posterEntity);
						tagDAO.deleteTag(tagEntity);
					}
				}
					
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void deleteSceneTag(TagDTO tagDTO) throws BusinessException {
		try{
			String tagName = tagDTO.getTagName();
			String tagUuid = tagDTO.getTagUuid();
			if(!StringUtils.isEmpty(tagUuid)){
				TagEntity tagEntity = tagDAO.getTagByUuid(tagUuid);
				if(tagEntity != null){
					List<SceneEntity> sceneEntityList = sceneDAO.getScenesByTag(tagEntity);
					for(Iterator<SceneEntity> iter = sceneEntityList.iterator();iter.hasNext();){
						SceneEntity sceneEntity = iter.next();
						sceneEntity.setTagEntity(null);
						sceneDAO.updateScene(sceneEntity);
					}
					tagDAO.deleteTag(tagEntity);
				}
				
			}else{
				TagEntity tagEntity = tagDAO.getTagByTypeName(ContentConstants.TAG_TYPE_SCENE, tagName);
				if(tagEntity != null){
					List<SceneEntity> sceneEntityList = sceneDAO.getScenesByTag(tagEntity);
					for(Iterator<SceneEntity> iter = sceneEntityList.iterator();iter.hasNext();){
						SceneEntity sceneEntity = iter.next();
						sceneEntity.setTagEntity(null);
						sceneDAO.updateScene(sceneEntity);
					}
					tagDAO.deleteTag(tagEntity);
				}
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void deleteArticleTag(TagDTO tagDTO) throws BusinessException {
		try{
			String tagName = tagDTO.getTagName();
			String tagUuid = tagDTO.getTagUuid();
			if(!StringUtils.isEmpty(tagUuid)){
				TagEntity tagEntity = tagDAO.getTagByUuid(tagUuid);
				if(tagEntity != null){
					List<ArticleEntity> articleEntityList = articleDAO.getArticlesByTag(tagEntity);
					for(Iterator<ArticleEntity> iter = articleEntityList.iterator();iter.hasNext();){
						ArticleEntity articleEntity = iter.next();
						articleEntity.setTagEntity(null);
						articleDAO.updateArticle(articleEntity);
					}
					tagDAO.deleteTag(tagEntity);
				}
				
			}else{
				TagEntity tagEntity = tagDAO.getTagByTypeName(ContentConstants.TAG_TYPE_ARTICLE, tagName);
				if(tagEntity != null){
					List<ArticleEntity> articleEntityList = articleDAO.getArticlesByTag(tagEntity);
					for(Iterator<ArticleEntity> iter = articleEntityList.iterator();iter.hasNext();){
						ArticleEntity articleEntity = iter.next();
						articleEntity.setTagEntity(null);
						articleDAO.updateArticle(articleEntity);
					}
					tagDAO.deleteTag(tagEntity);
				}
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void deleteQaTag(TagDTO tagDTO) throws BusinessException {
		try{
			String tagName = tagDTO.getTagName();
			String tagUuid = tagDTO.getTagUuid();
			if(!StringUtils.isEmpty(tagUuid)){
				TagEntity tagEntity = tagDAO.getTagByUuid(tagUuid);
				if(tagEntity != null){
					List<QaEntity> qaEntityList = qaDAO.getQasByTag(tagEntity);
					for(Iterator<QaEntity> iter = qaEntityList.iterator();iter.hasNext();){
						QaEntity qaEntity = iter.next();
						qaEntity.setTagEntity(null);
						qaDAO.updateQa(qaEntity);
					}
					tagDAO.deleteTag(tagEntity);
				}
				
			}else{
				TagEntity tagEntity = tagDAO.getTagByTypeName(ContentConstants.TAG_TYPE_QA, tagName);
				if(tagEntity != null){
					List<QaEntity> qaEntityList = qaDAO.getQasByTag(tagEntity);
					for(Iterator<QaEntity> iter = qaEntityList.iterator();iter.hasNext();){
						QaEntity qaEntity = iter.next();
						qaEntity.setTagEntity(null);
						qaDAO.updateQa(qaEntity);
					}
					tagDAO.deleteTag(tagEntity);
				}
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public TagDTO getTagByUuid(String uuid) throws BusinessException {
		try{
			TagEntity tagEntity = tagDAO.getTagByUuid(uuid);
			TagDTO tagDTO = new TagDTO();
			tagDTO.setTagName(tagEntity.getTagName());
			tagDTO.setTagType(tagEntity.getTagType());
			tagDTO.setTagUuid(tagEntity.getTagUuid());
			return tagDTO;
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}

	@Override
	public TagDTO getTagByTypeName(String tagType, String tagName)
			throws BusinessException {
		try{
			TagEntity tagEntity = tagDAO.getTagByTypeName(tagType, tagName);
			TagDTO tagDTO = new TagDTO();
			tagDTO.setTagName(tagEntity.getTagName());
			tagDTO.setTagType(tagEntity.getTagType());
			tagDTO.setTagUuid(tagEntity.getTagUuid());
			return tagDTO;
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}

	@Override
	public List<TagDTO> getTagsByType(String type) throws BusinessException {
		List<TagDTO> tagDTOList = new ArrayList<TagDTO>(); 
		try{
			List<TagEntity> tagEntityList = tagDAO.getTagsByType(type);
			for(Iterator<TagEntity> iter = tagEntityList.iterator();iter.hasNext();){
				TagEntity tagEntity = iter.next();
				TagDTO tagDTO = new TagDTO();
				tagDTO.setTagName(tagEntity.getTagName());
				tagDTO.setTagType(tagEntity.getTagType());
				tagDTO.setTagUuid(tagEntity.getTagUuid());
				tagDTOList.add(tagDTO);
			}
			
			return tagDTOList;
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}

	@Override
	public void addPoster(PosterDTO posterDTO) throws BusinessException {
		
		try{
			PosterEntity posterEntity = new PosterEntity();
			posterEntity.setUrl(posterDTO.getUrl());
			if(posterDTO.getTagDTO() != null){
				TagEntity tagEntity = tagDAO.getTagByTypeName(ContentConstants.TAG_TYPE_POSTER, posterDTO.getTagDTO().getTagName());
				posterEntity.setTagEntity(tagEntity);
			}
			posterDAO.addPoster(posterEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void updatePoster(PosterDTO posterDTO) throws BusinessException {
		
		try{
			PosterEntity posterEntity = posterDAO.getPosterByUuid(posterDTO.getPosterUuid());
			posterEntity.setUrl(posterDTO.getUrl());
			if(posterDTO.getTagDTO() != null){
				TagEntity tagEntity = tagDAO.getTagByTypeName(ContentConstants.TAG_TYPE_POSTER, posterDTO.getTagDTO().getTagName());
				posterEntity.setTagEntity(tagEntity);
			}
			posterDAO.updatePoster(posterEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public void deletePoster(PosterDTO posterDTO) throws BusinessException {
		
		try{
			PosterEntity posterEntity = posterDAO.getPosterByUuid(posterDTO.getPosterUuid());
			posterDAO.deletePoster(posterEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void publishPoster(PosterDTO posterDTO) throws BusinessException {
		
		try{
			PosterEntity posterEntity = posterDAO.getPosterByUuid(posterDTO.getPosterUuid());
			
			//启动新线程发送客服消息, 通知有新的文案
			SendPosterMsgNewsThread t = new SendPosterMsgNewsThread();
			posterDTO.setUrl(posterEntity.getUrl());
			t.setPosterDTO(posterDTO);
			t.run();
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public List<PosterDTO> getPostersByTagName(String tagName)
			throws BusinessException {
		List<PosterDTO> posterDTOList = new ArrayList<PosterDTO>(); 
		try{
			List<PosterEntity> posterEntityList = posterDAO.getPostersByTagName(tagName);
			for(Iterator<PosterEntity> iter = posterEntityList.iterator();iter.hasNext();){
				PosterEntity posterEntity = iter.next();
				PosterDTO posterDTO = new PosterDTO();
				posterDTO.setUrl(posterEntity.getUrl());
				posterDTO.setPosterUuid(posterEntity.getPosterUuid());
				TagEntity tagEntity = posterEntity.getTagEntity();
				if(tagEntity != null){
					TagDTO tagDTO = new TagDTO();
					tagDTO.setTagUuid(tagEntity.getTagUuid());
					tagDTO.setTagType(tagEntity.getTagType());
					tagDTO.setTagName(tagEntity.getTagName());
					posterDTO.setTagDTO(tagDTO);
				}
				posterDTOList.add(posterDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return posterDTOList;
	}

	@Override
	public List<PosterDTO> getPosters() throws BusinessException {
		List<PosterDTO> posterDTOList = new ArrayList<PosterDTO>(); 
		try{
			List<PosterEntity> posterEntityList = posterDAO.getPosters();
			for(Iterator<PosterEntity> iter = posterEntityList.iterator();iter.hasNext();){
				PosterEntity posterEntity = iter.next();
				PosterDTO posterDTO = new PosterDTO();
				posterDTO.setUrl(posterEntity.getUrl());
				posterDTO.setPosterUuid(posterEntity.getPosterUuid());
				TagEntity tagEntity = posterEntity.getTagEntity();
				if(tagEntity != null){
					TagDTO tagDTO = new TagDTO();
					tagDTO.setTagUuid(tagEntity.getTagUuid());
					tagDTO.setTagType(tagEntity.getTagType());
					tagDTO.setTagName(tagEntity.getTagName());
					posterDTO.setTagDTO(tagDTO);
				}
				posterDTOList.add(posterDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return posterDTOList;
	}
	
	@Override
	public PosterDTO getPosterByUuid(String uuid) throws BusinessException {
		try{
				PosterEntity posterEntity = posterDAO.getPosterByUuid(uuid);
				PosterDTO posterDTO = new PosterDTO();
				posterDTO.setUrl(posterEntity.getUrl());
				posterDTO.setPosterUuid(posterEntity.getPosterUuid());
				TagEntity tagEntity = posterEntity.getTagEntity();
				if(tagEntity != null){
					TagDTO tagDTO = new TagDTO();
					tagDTO.setTagUuid(tagEntity.getTagUuid());
					tagDTO.setTagType(tagEntity.getTagType());
					tagDTO.setTagName(tagEntity.getTagName());
					posterDTO.setTagDTO(tagDTO);
				}
				return posterDTO;
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}

	@Override
	public void addScene(SceneDTO sceneDTO) throws BusinessException {
		
		try{
			SceneEntity sceneEntity = new SceneEntity();
			sceneEntity.setTitle(sceneDTO.getTitle());
			sceneEntity.setContent(sceneDTO.getContent());
			if(sceneDTO.getTagDTO() != null){
				TagEntity tagEntity = tagDAO.getTagByTypeName(ContentConstants.TAG_TYPE_SCENE, sceneDTO.getTagDTO().getTagName());
				sceneEntity.setTagEntity(tagEntity);
			}
			sceneDAO.addScene(sceneEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void updateScene(SceneDTO sceneDTO) throws BusinessException {
		
		try{
			SceneEntity sceneEntity = sceneDAO.getSceneByUuid(sceneDTO.getSceneUuid());
			sceneEntity.setTitle(sceneDTO.getTitle());
			sceneEntity.setContent(sceneDTO.getContent());
			if(sceneDTO.getTagDTO() != null){
				TagEntity tagEntity = tagDAO.getTagByTypeName(ContentConstants.TAG_TYPE_SCENE, sceneDTO.getTagDTO().getTagName());
				sceneEntity.setTagEntity(tagEntity);
			}
			sceneDAO.updateScene(sceneEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public void deleteScene(SceneDTO sceneDTO) throws BusinessException {
		
		try{
			SceneEntity sceneEntity = sceneDAO.getSceneByUuid(sceneDTO.getSceneUuid());
			sceneDAO.deleteScene(sceneEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public List<SceneDTO> getScenesByTagName(String tagName)
			throws BusinessException {
		List<SceneDTO> sceneDTOList = new ArrayList<SceneDTO>(); 
		try{
			List<SceneEntity> sceneEntityList = sceneDAO.getScenesByTagName(tagName);
			for(Iterator<SceneEntity> iter = sceneEntityList.iterator();iter.hasNext();){
				SceneEntity sceneEntity = iter.next();
				SceneDTO sceneDTO = new SceneDTO();
				sceneDTO.setSceneUuid(sceneEntity.getSceneUuid());
				sceneDTO.setTitle(sceneEntity.getTitle());
				sceneDTO.setContent(sceneEntity.getContent());
				sceneDTOList.add(sceneDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return sceneDTOList;
	}
	
	@Override
	public SceneDTO getSceneByUuid(String uuid)
			throws BusinessException {
		try{
			SceneEntity sceneEntity = sceneDAO.getSceneByUuid(uuid);
			SceneDTO sceneDTO = new SceneDTO();
			sceneDTO.setSceneUuid(sceneEntity.getSceneUuid());
			sceneDTO.setTitle(sceneEntity.getTitle());
			sceneDTO.setContent(sceneEntity.getContent());
			sceneDTO.setUpdateDate(sceneEntity.getUpdateDate());
			TagEntity tagEntity = sceneEntity.getTagEntity();
			if(tagEntity != null){
				TagDTO tagDTO = new TagDTO();
				tagDTO.setTagUuid(tagEntity.getTagUuid());
				tagDTO.setTagName(tagEntity.getTagName());
				tagDTO.setTagType(tagEntity.getTagType());
				sceneDTO.setTagDTO(tagDTO);
			}
			return sceneDTO;
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}

	@Override
	public List<SceneDTO> getScenes() throws BusinessException {
		List<SceneDTO> sceneDTOList = new ArrayList<SceneDTO>(); 
		try{
			List<SceneEntity> sceneEntityList = sceneDAO.getScenes();
			for(Iterator<SceneEntity> iter = sceneEntityList.iterator();iter.hasNext();){
				SceneEntity sceneEntity = iter.next();
				SceneDTO sceneDTO = new SceneDTO();
				sceneDTO.setSceneUuid(sceneEntity.getSceneUuid());
				sceneDTO.setTitle(sceneEntity.getTitle());
				sceneDTO.setContent(sceneEntity.getContent());
				TagEntity tagEntity = sceneEntity.getTagEntity();
				if(tagEntity != null){
					TagDTO tagDTO = new TagDTO();
					tagDTO.setTagUuid(tagEntity.getTagUuid());
					tagDTO.setTagName(tagEntity.getTagName());
					tagDTO.setTagType(tagEntity.getTagType());
					sceneDTO.setTagDTO(tagDTO);
				}
				sceneDTOList.add(sceneDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return sceneDTOList;
	}

	@Override
	public void addArticle(ArticleDTO articleDTO) throws BusinessException {
		
		try{
			ArticleEntity articleEntity = new ArticleEntity();
			articleEntity.setTitle(articleDTO.getTitle());
			articleEntity.setContent(articleDTO.getContent());
			articleEntity.setMediaId(articleDTO.getMediaId());
			articleEntity.setCoverImageUrl(articleDTO.getCoverImageUrl());
			articleEntity.setFromOfficialAccount(false);
//			articleEntity.setPublishTime(new Date());
			articleEntity.setPublished(false);
			articleEntity.setLinkType(articleDTO.getLinkType());
			articleEntity.setArticleType(articleDTO.getArticleType());
			if(articleDTO.getTagDTO() != null){
				TagEntity tagEntity = tagDAO.getTagByTypeName(ContentConstants.TAG_TYPE_ARTICLE, articleDTO.getTagDTO().getTagName());
				articleEntity.setTagEntity(tagEntity);
			}
			articleDAO.addArticle(articleEntity);
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void addQa(QaDTO qaDTO) throws BusinessException {
		
		try{
			QaEntity qaEntity = new QaEntity();
			qaEntity.setTitle(qaDTO.getTitle());
			qaEntity.setContent(qaDTO.getContent());
			qaEntity.setMediaId(qaDTO.getMediaId());
			qaEntity.setCoverImageUrl(qaDTO.getCoverImageUrl());
			qaEntity.setFromOfficialAccount(false);
//			articleEntity.setPublishTime(new Date());
			qaEntity.setPublished(false);
			qaEntity.setPreviewUrl(qaDTO.getPreviewUrl());
			if(qaDTO.getTagDTO() != null){
				TagEntity tagEntity = tagDAO.getTagByTypeName(ContentConstants.TAG_TYPE_QA, qaDTO.getTagDTO().getTagName());
				qaEntity.setTagEntity(tagEntity);
			}
			qaDAO.addQa(qaEntity);
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void importArticle(ArticleDTO articleDTO) throws BusinessException {
		
		try{
			//查看该公众号文章是否已经导入, 已经导入过的忽略
			if(articleDAO.getArticleByMediaId(articleDTO.getMediaId())==null){
				ArticleEntity articleEntity = new ArticleEntity();
				articleEntity.setTitle(articleDTO.getTitle());
				articleEntity.setContent(articleDTO.getContent());
				articleEntity.setMediaId(articleDTO.getMediaId());
				articleEntity.setCoverImageUrl(articleDTO.getCoverImageUrl());
				articleEntity.setFromOfficialAccount(true);
				articleEntity.setPublishTime(new Date());
				articleEntity.setLinkType(articleDTO.getLinkType());
				articleDAO.addArticle(articleEntity);
			}
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		
	}

	
	@Override
	public void updateArticle(ArticleDTO articleDTO) throws BusinessException {
		
		try{
			ArticleEntity articleEntity = articleDAO.getArticleByUuid(articleDTO.getArticleUuid());
			articleEntity.setTitle(articleDTO.getTitle());
			articleEntity.setContent(articleDTO.getContent());
			articleEntity.setCoverImageUrl(articleDTO.getCoverImageUrl());
//			articleEntity.setPublishTime(articleDTO.getPublishTime());
			articleEntity.setLinkType(articleDTO.getLinkType());
			if(articleDTO.getTagDTO() != null){
				TagEntity tagEntity = tagDAO.getTagByTypeName(ContentConstants.TAG_TYPE_ARTICLE, articleDTO.getTagDTO().getTagName());
				articleEntity.setTagEntity(tagEntity);
			}
			articleDAO.updateArticle(articleEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void updateQa(QaDTO qaDTO) throws BusinessException {
		
		try{
			QaEntity qaEntity = qaDAO.getQaByUuid(qaDTO.getQaUuid());
			qaEntity.setTitle(qaDTO.getTitle());
			qaEntity.setContent(qaDTO.getContent());
			qaEntity.setCoverImageUrl(qaDTO.getCoverImageUrl());
//			articleEntity.setPublishTime(articleDTO.getPublishTime());
			qaEntity.setPreviewUrl(qaDTO.getPreviewUrl());
			if(qaDTO.getTagDTO() != null){
				TagEntity tagEntity = tagDAO.getTagByTypeName(ContentConstants.TAG_TYPE_QA, qaDTO.getTagDTO().getTagName());
				qaEntity.setTagEntity(tagEntity);
			}
			qaDAO.updateQa(qaEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void setQaHot(QaDTO qaDTO) throws BusinessException {
		
		try{
			QaEntity qaEntity = qaDAO.getQaByUuid(qaDTO.getQaUuid());
			qaEntity.setHot(true);
			qaDAO.updateQa(qaEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void setQaNonHot(QaDTO qaDTO) throws BusinessException {
		
		try{
			QaEntity qaEntity = qaDAO.getQaByUuid(qaDTO.getQaUuid());
			qaEntity.setHot(false);
			qaDAO.updateQa(qaEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void publishArticle(ArticleDTO articleDTO) throws BusinessException {
		
		try{
			ArticleEntity articleEntity = articleDAO.getArticleByUuid(articleDTO.getArticleUuid());
			if(!articleEntity.isPublished()){	
				articleEntity.setPublishTime(new Date());
				articleEntity.setPublished(true);
				articleDAO.updateArticle(articleEntity);
				
				//启动新线程发送客服消息, 通知有新的文案
//				SendMsgNewsThread t = new SendMsgNewsThread();
//				articleDTO.setTitle(articleEntity.getTitle());
//				articleDTO.setCoverImageUrl(articleEntity.getCoverImageUrl());
//				t.setArticleDTO(articleDTO);
//				t.run();
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void publishQa(QaDTO qaDTO) throws BusinessException {
		
		try{
			QaEntity qaEntity = qaDAO.getQaByUuid(qaDTO.getQaUuid());
			if(!qaEntity.isPublished()){	
				qaEntity.setPublishTime(new Date());
				qaEntity.setPublished(true);
				qaDAO.updateQa(qaEntity);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void deleteArticle(ArticleDTO articleDTO) throws BusinessException {
		
		try{
			ArticleEntity articleEntity = articleDAO.getArticleByUuid(articleDTO.getArticleUuid());
			articleDAO.deleteArticle(articleEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void deleteQa(QaDTO qaDTO) throws BusinessException {
		
		try{
			QaEntity qaEntity = qaDAO.getQaByUuid(qaDTO.getQaUuid());
			qaDAO.deleteQa(qaEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void deleteMultipleArticles(ArticleDTOList articleDTOList) throws BusinessException {
		
		try{
			List<ArticleDTO> list = articleDTOList.getArticles();
			for(Iterator<ArticleDTO> iter = list.iterator();iter.hasNext();){
				ArticleDTO articleDTO = iter.next();
				deleteArticle(articleDTO);
			}
			
		}catch(Exception e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void deleteMultipleQas(QaDTOList qaDTOList) throws BusinessException {
		
		try{
			List<QaDTO> list = qaDTOList.getQas();
			for(Iterator<QaDTO> iter = list.iterator();iter.hasNext();){
				QaDTO qaDTO = iter.next();
				deleteQa(qaDTO);
			}
			
		}catch(Exception e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public List<ArticleDTO> getArticlesByTagName(String tagName)
			throws BusinessException {
		List<ArticleDTO> articleDTOList = new ArrayList<ArticleDTO>(); 
		try{
			List<ArticleEntity> articleEntityList = articleDAO.getArticlesByTagName(tagName);
			for(Iterator<ArticleEntity> iter = articleEntityList.iterator();iter.hasNext();){
				ArticleEntity articleEntity = iter.next();
				ArticleDTO articleDTO = new ArticleDTO();
				articleDTO.setArticleUuid(articleEntity.getArticleUuid());
				articleDTO.setTitle(articleEntity.getTitle());
				articleDTO.setDigest(articleEntity.getDigest());
				articleDTO.setContent(articleEntity.getContent());
				articleDTO.setMediaId(articleEntity.getMediaId());
				articleDTO.setCoverImageUrl(articleEntity.getCoverImageUrl());
				articleDTO.setFromOfficialAccount(articleEntity.isFromOfficialAccount());
				articleDTO.setPublishTime(articleEntity.getPublishTime());
				articleDTO.setLinkType(articleEntity.getLinkType());
				articleDTO.setNumberOfReading(articleEntity.getNumberOfReading());
				articleDTO.setPublished(articleEntity.isPublished());
				articleDTO.setArticleType(articleEntity.getArticleType());
				TagEntity tagEntity = articleEntity.getTagEntity();
				if(tagEntity != null){
					TagDTO tagDTO = new TagDTO();
					tagDTO.setTagUuid(tagEntity.getTagUuid());
					tagDTO.setTagName(tagEntity.getTagName());
					tagDTO.setTagType(tagEntity.getTagType());
					articleDTO.setTagDTO(tagDTO);
				}
				articleDTOList.add(articleDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return articleDTOList;
	}
	
	@Override
	public List<QaDTO> getQasByTagName(String tagName)
			throws BusinessException {
		List<QaDTO> qaDTOList = new ArrayList<QaDTO>(); 
		try{
			List<QaEntity> qaEntityList = qaDAO.getQasByTagName(tagName);
			for(Iterator<QaEntity> iter = qaEntityList.iterator();iter.hasNext();){
				QaEntity qaEntity = iter.next();
				QaDTO qaDTO = new QaDTO();
				qaDTO.setQaUuid(qaEntity.getQaUuid());
				qaDTO.setTitle(qaEntity.getTitle());
				qaDTO.setDigest(qaEntity.getDigest());
				qaDTO.setContent(qaEntity.getContent());
				qaDTO.setMediaId(qaEntity.getMediaId());
				qaDTO.setCoverImageUrl(qaEntity.getCoverImageUrl());
				qaDTO.setFromOfficialAccount(qaEntity.isFromOfficialAccount());
				qaDTO.setPublishTime(qaEntity.getPublishTime());
				qaDTO.setPreviewUrl(qaEntity.getPreviewUrl());
				qaDTO.setNumberOfReading(qaEntity.getNumberOfReading());
				qaDTO.setPublished(qaEntity.isPublished());
				qaDTO.setHot(qaEntity.isHot());
				TagEntity tagEntity = qaEntity.getTagEntity();
				if(tagEntity != null){
					TagDTO tagDTO = new TagDTO();
					tagDTO.setTagUuid(tagEntity.getTagUuid());
					tagDTO.setTagName(tagEntity.getTagName());
					tagDTO.setTagType(tagEntity.getTagType());
					qaDTO.setTagDTO(tagDTO);
				}
				qaDTOList.add(qaDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return qaDTOList;
	}
	
	@Override
	public List<ArticleDTO> getPublishedArticlesByTagName(String tagName)
			throws BusinessException {
		List<ArticleDTO> articleDTOList = new ArrayList<ArticleDTO>(); 
		try{
			List<ArticleEntity> articleEntityList = articleDAO.getPublishedArticlesByTagName(tagName);
			for(Iterator<ArticleEntity> iter = articleEntityList.iterator();iter.hasNext();){
				ArticleEntity articleEntity = iter.next();
				ArticleDTO articleDTO = new ArticleDTO();
				articleDTO.setArticleUuid(articleEntity.getArticleUuid());
				articleDTO.setTitle(articleEntity.getTitle());
				articleDTO.setDigest(articleEntity.getDigest());
				articleDTO.setContent(articleEntity.getContent());
				articleDTO.setMediaId(articleEntity.getMediaId());
				articleDTO.setCoverImageUrl(articleEntity.getCoverImageUrl());
				articleDTO.setFromOfficialAccount(articleEntity.isFromOfficialAccount());
				articleDTO.setPublishTime(articleEntity.getPublishTime());
				articleDTO.setLinkType(articleEntity.getLinkType());
				articleDTO.setNumberOfReading(articleEntity.getNumberOfReading());
				articleDTO.setPublished(articleEntity.isPublished());
				articleDTO.setArticleType(articleEntity.getArticleType());
				TagEntity tagEntity = articleEntity.getTagEntity();
				if(tagEntity != null){
					TagDTO tagDTO = new TagDTO();
					tagDTO.setTagUuid(tagEntity.getTagUuid());
					tagDTO.setTagName(tagEntity.getTagName());
					tagDTO.setTagType(tagEntity.getTagType());
					articleDTO.setTagDTO(tagDTO);
				}
				articleDTOList.add(articleDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return articleDTOList;
	}
	
	@Override
	public List<QaDTO> getPublishedQaByTagName(String tagName)
			throws BusinessException {
		List<QaDTO> qaDTOList = new ArrayList<QaDTO>(); 
		try{
			List<QaEntity> qaEntityList = qaDAO.getPublishedQasByTagName(tagName);
			for(Iterator<QaEntity> iter = qaEntityList.iterator();iter.hasNext();){
				QaEntity qaEntity = iter.next();
				QaDTO qaDTO = new QaDTO();
				qaDTO.setQaUuid(qaEntity.getQaUuid());
				qaDTO.setTitle(qaEntity.getTitle());
				qaDTO.setDigest(qaEntity.getDigest());
				qaDTO.setContent(qaEntity.getContent());
				qaDTO.setMediaId(qaEntity.getMediaId());
				qaDTO.setCoverImageUrl(qaEntity.getCoverImageUrl());
				qaDTO.setFromOfficialAccount(qaEntity.isFromOfficialAccount());
				qaDTO.setPublishTime(qaEntity.getPublishTime());
				qaDTO.setPreviewUrl(qaEntity.getPreviewUrl());
				qaDTO.setNumberOfReading(qaEntity.getNumberOfReading());
				qaDTO.setPublished(qaEntity.isPublished());
				qaDTO.setHot(qaEntity.isHot());
				TagEntity tagEntity = qaEntity.getTagEntity();
				if(tagEntity != null){
					TagDTO tagDTO = new TagDTO();
					tagDTO.setTagUuid(tagEntity.getTagUuid());
					tagDTO.setTagName(tagEntity.getTagName());
					tagDTO.setTagType(tagEntity.getTagType());
					qaDTO.setTagDTO(tagDTO);
				}
				qaDTOList.add(qaDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return qaDTOList;
	}
	
	@Override
	public List<ArticleDTO> getArticlesByType(String articleType)
			throws BusinessException {
		List<ArticleDTO> articleDTOList = new ArrayList<ArticleDTO>(); 
		try{
			List<ArticleEntity> articleEntityList = articleDAO.getArticlesByType(articleType);
			for(Iterator<ArticleEntity> iter = articleEntityList.iterator();iter.hasNext();){
				ArticleEntity articleEntity = iter.next();
				ArticleDTO articleDTO = new ArticleDTO();
				articleDTO.setArticleUuid(articleEntity.getArticleUuid());
				articleDTO.setTitle(articleEntity.getTitle());
				articleDTO.setDigest(articleEntity.getDigest());
				articleDTO.setContent(articleEntity.getContent());
				articleDTO.setMediaId(articleEntity.getMediaId());
				articleDTO.setCoverImageUrl(articleEntity.getCoverImageUrl());
				articleDTO.setFromOfficialAccount(articleEntity.isFromOfficialAccount());
				articleDTO.setPublishTime(articleEntity.getPublishTime());
				articleDTO.setLinkType(articleEntity.getLinkType());
				articleDTO.setNumberOfReading(articleEntity.getNumberOfReading());
				articleDTO.setPublished(articleEntity.isPublished());
				articleDTO.setArticleType(articleEntity.getArticleType());
				TagEntity tagEntity = articleEntity.getTagEntity();
				if(tagEntity != null){
					TagDTO tagDTO = new TagDTO();
					tagDTO.setTagUuid(tagEntity.getTagUuid());
					tagDTO.setTagName(tagEntity.getTagName());
					tagDTO.setTagType(tagEntity.getTagType());
					articleDTO.setTagDTO(tagDTO);
				}
				articleDTOList.add(articleDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return articleDTOList;
	}
	
	@Override
	public List<QaDTO> getHotQas()
			throws BusinessException {
		List<QaDTO> qaDTOList = new ArrayList<QaDTO>(); 
		try{
			List<QaEntity> qaEntityList = qaDAO.getHotQas();
			for(Iterator<QaEntity> iter = qaEntityList.iterator();iter.hasNext();){
				QaEntity qaEntity = iter.next();
				QaDTO qaDTO = new QaDTO();
				qaDTO.setQaUuid(qaEntity.getQaUuid());
				qaDTO.setTitle(qaEntity.getTitle());
				qaDTO.setDigest(qaEntity.getDigest());
				qaDTO.setContent(qaEntity.getContent());
				qaDTO.setMediaId(qaEntity.getMediaId());
				qaDTO.setCoverImageUrl(qaEntity.getCoverImageUrl());
				qaDTO.setFromOfficialAccount(qaEntity.isFromOfficialAccount());
				qaDTO.setPublishTime(qaEntity.getPublishTime());
				qaDTO.setPreviewUrl(qaEntity.getPreviewUrl());
				qaDTO.setNumberOfReading(qaEntity.getNumberOfReading());
				qaDTO.setPublished(qaEntity.isPublished());
				qaDTO.setHot(qaEntity.isHot());
				TagEntity tagEntity = qaEntity.getTagEntity();
				if(tagEntity != null){
					TagDTO tagDTO = new TagDTO();
					tagDTO.setTagUuid(tagEntity.getTagUuid());
					tagDTO.setTagName(tagEntity.getTagName());
					tagDTO.setTagType(tagEntity.getTagType());
					qaDTO.setTagDTO(tagDTO);
				}
				qaDTOList.add(qaDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return qaDTOList;
	}
	
	@Override
	public List<ArticleDTO> getPublishedArticlesByType(String articleType)
			throws BusinessException {
		List<ArticleDTO> articleDTOList = new ArrayList<ArticleDTO>(); 
		try{
			List<ArticleEntity> articleEntityList = articleDAO.getPublishedArticlesByType(articleType);
			for(Iterator<ArticleEntity> iter = articleEntityList.iterator();iter.hasNext();){
				ArticleEntity articleEntity = iter.next();
				ArticleDTO articleDTO = new ArticleDTO();
				articleDTO.setArticleUuid(articleEntity.getArticleUuid());
				articleDTO.setTitle(articleEntity.getTitle());
				articleDTO.setDigest(articleEntity.getDigest());
				articleDTO.setContent(articleEntity.getContent());
				articleDTO.setMediaId(articleEntity.getMediaId());
				articleDTO.setCoverImageUrl(articleEntity.getCoverImageUrl());
				articleDTO.setFromOfficialAccount(articleEntity.isFromOfficialAccount());
				articleDTO.setPublishTime(articleEntity.getPublishTime());
				articleDTO.setLinkType(articleEntity.getLinkType());
				articleDTO.setNumberOfReading(articleEntity.getNumberOfReading());
				articleDTO.setPublished(articleEntity.isPublished());
				articleDTO.setArticleType(articleEntity.getArticleType());
				TagEntity tagEntity = articleEntity.getTagEntity();
				if(tagEntity != null){
					TagDTO tagDTO = new TagDTO();
					tagDTO.setTagUuid(tagEntity.getTagUuid());
					tagDTO.setTagName(tagEntity.getTagName());
					tagDTO.setTagType(tagEntity.getTagType());
					articleDTO.setTagDTO(tagDTO);
				}
				articleDTOList.add(articleDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return articleDTOList;
	}
	
	@Override
	public List<ArticleDTO> getArticlesByTypePage(String articleType, int startIndex, int pageSize)
			throws BusinessException {
		List<ArticleDTO> articleDTOList = new ArrayList<ArticleDTO>(); 
		try{
			List<ArticleEntity> articleEntityList = articleDAO.getArticlesByTypePage(articleType, startIndex, pageSize);
			for(Iterator<ArticleEntity> iter = articleEntityList.iterator();iter.hasNext();){
				ArticleEntity articleEntity = iter.next();
				ArticleDTO articleDTO = new ArticleDTO();
				articleDTO.setArticleUuid(articleEntity.getArticleUuid());
				articleDTO.setTitle(articleEntity.getTitle());
				articleDTO.setDigest(articleEntity.getDigest());
				articleDTO.setContent(articleEntity.getContent());
				articleDTO.setMediaId(articleEntity.getMediaId());
				articleDTO.setCoverImageUrl(articleEntity.getCoverImageUrl());
				articleDTO.setFromOfficialAccount(articleEntity.isFromOfficialAccount());
				articleDTO.setPublishTime(articleEntity.getPublishTime());
				articleDTO.setLinkType(articleEntity.getLinkType());
				articleDTO.setNumberOfReading(articleEntity.getNumberOfReading());
				articleDTO.setPublished(articleEntity.isPublished());
				articleDTO.setArticleType(articleEntity.getArticleType());
				TagEntity tagEntity = articleEntity.getTagEntity();
				if(tagEntity != null){
					TagDTO tagDTO = new TagDTO();
					tagDTO.setTagUuid(tagEntity.getTagUuid());
					tagDTO.setTagName(tagEntity.getTagName());
					tagDTO.setTagType(tagEntity.getTagType());
					articleDTO.setTagDTO(tagDTO);
				}
				articleDTOList.add(articleDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return articleDTOList;
	}
	
	@Override
	public List<ArticleDTO> getArticlesByTypesPage(List<String> articleTypeList, int startIndex, int pageSize)
			throws BusinessException {
		List<ArticleDTO> articleDTOList = new ArrayList<ArticleDTO>(); 
		try{
			List<ArticleEntity> articleEntityList = articleDAO.getArticlesByTypesPage(articleTypeList, startIndex, pageSize);
			for(Iterator<ArticleEntity> iter = articleEntityList.iterator();iter.hasNext();){
				ArticleEntity articleEntity = iter.next();
				ArticleDTO articleDTO = new ArticleDTO();
				articleDTO.setArticleUuid(articleEntity.getArticleUuid());
				articleDTO.setTitle(articleEntity.getTitle());
				articleDTO.setDigest(articleEntity.getDigest());
				articleDTO.setContent(articleEntity.getContent());
				articleDTO.setMediaId(articleEntity.getMediaId());
				articleDTO.setCoverImageUrl(articleEntity.getCoverImageUrl());
				articleDTO.setFromOfficialAccount(articleEntity.isFromOfficialAccount());
				articleDTO.setPublishTime(articleEntity.getPublishTime());
				articleDTO.setLinkType(articleEntity.getLinkType());
				articleDTO.setNumberOfReading(articleEntity.getNumberOfReading());
				articleDTO.setPublished(articleEntity.isPublished());
				articleDTO.setArticleType(articleEntity.getArticleType());
				TagEntity tagEntity = articleEntity.getTagEntity();
				if(tagEntity != null){
					TagDTO tagDTO = new TagDTO();
					tagDTO.setTagUuid(tagEntity.getTagUuid());
					tagDTO.setTagName(tagEntity.getTagName());
					tagDTO.setTagType(tagEntity.getTagType());
					articleDTO.setTagDTO(tagDTO);
				}
				articleDTOList.add(articleDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return articleDTOList;
	}
	
	@Override
	public List<ArticleDTO> getArticlesByTagNamePage(String tagName, int startIndex, int pageSize)
			throws BusinessException {
		List<ArticleDTO> articleDTOList = new ArrayList<ArticleDTO>(); 
		try{
			List<ArticleEntity> articleEntityList = articleDAO.getArticlesByTagNamePage(tagName, startIndex, pageSize);
			for(Iterator<ArticleEntity> iter = articleEntityList.iterator();iter.hasNext();){
				ArticleEntity articleEntity = iter.next();
				ArticleDTO articleDTO = new ArticleDTO();
				articleDTO.setArticleUuid(articleEntity.getArticleUuid());
				articleDTO.setTitle(articleEntity.getTitle());
				articleDTO.setDigest(articleEntity.getDigest());
				articleDTO.setContent(articleEntity.getContent());
				articleDTO.setMediaId(articleEntity.getMediaId());
				articleDTO.setCoverImageUrl(articleEntity.getCoverImageUrl());
				articleDTO.setFromOfficialAccount(articleEntity.isFromOfficialAccount());
				articleDTO.setPublishTime(articleEntity.getPublishTime());
				articleDTO.setLinkType(articleEntity.getLinkType());
				articleDTO.setNumberOfReading(articleEntity.getNumberOfReading());
				articleDTO.setPublished(articleEntity.isPublished());
				articleDTO.setArticleType(articleEntity.getArticleType());
				TagEntity tagEntity = articleEntity.getTagEntity();
				if(tagEntity != null){
					TagDTO tagDTO = new TagDTO();
					tagDTO.setTagUuid(tagEntity.getTagUuid());
					tagDTO.setTagName(tagEntity.getTagName());
					tagDTO.setTagType(tagEntity.getTagType());
					articleDTO.setTagDTO(tagDTO);
				}
				articleDTOList.add(articleDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return articleDTOList;
	}
	
	@Override
	public List<QaDTO> getQasByTagNamePage(String tagName, int startIndex, int pageSize)
			throws BusinessException {
		List<QaDTO> qaDTOList = new ArrayList<QaDTO>(); 
		try{
			List<QaEntity> qaEntityList = qaDAO.getQasByTagNamePage(tagName, startIndex, pageSize);
			for(Iterator<QaEntity> iter = qaEntityList.iterator();iter.hasNext();){
				QaEntity qaEntity = iter.next();
				QaDTO qaDTO = new QaDTO();
				qaDTO.setQaUuid(qaEntity.getQaUuid());
				qaDTO.setTitle(qaEntity.getTitle());
				qaDTO.setDigest(qaEntity.getDigest());
				qaDTO.setContent(qaEntity.getContent());
				qaDTO.setMediaId(qaEntity.getMediaId());
				qaDTO.setCoverImageUrl(qaEntity.getCoverImageUrl());
				qaDTO.setFromOfficialAccount(qaEntity.isFromOfficialAccount());
				qaDTO.setPublishTime(qaEntity.getPublishTime());
				qaDTO.setPreviewUrl(qaEntity.getPreviewUrl());
				qaDTO.setNumberOfReading(qaEntity.getNumberOfReading());
				qaDTO.setPublished(qaEntity.isPublished());
				qaDTO.setHot(qaEntity.isHot());
				TagEntity tagEntity = qaEntity.getTagEntity();
				if(tagEntity != null){
					TagDTO tagDTO = new TagDTO();
					tagDTO.setTagUuid(tagEntity.getTagUuid());
					tagDTO.setTagName(tagEntity.getTagName());
					tagDTO.setTagType(tagEntity.getTagType());
					qaDTO.setTagDTO(tagDTO);
				}
				qaDTOList.add(qaDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return qaDTOList;
	}
	
	@Override
	public List<ArticleDTO> getPublishedArticlesByTagNamePage(String tagName, int startIndex, int pageSize)
			throws BusinessException {
		List<ArticleDTO> articleDTOList = new ArrayList<ArticleDTO>(); 
		try{
			List<ArticleEntity> articleEntityList = articleDAO.getPublishedArticlesByTagNamePage(tagName, startIndex, pageSize);
			for(Iterator<ArticleEntity> iter = articleEntityList.iterator();iter.hasNext();){
				ArticleEntity articleEntity = iter.next();
				ArticleDTO articleDTO = new ArticleDTO();
				articleDTO.setArticleUuid(articleEntity.getArticleUuid());
				articleDTO.setTitle(articleEntity.getTitle());
				articleDTO.setDigest(articleEntity.getDigest());
				articleDTO.setContent(articleEntity.getContent());
				articleDTO.setMediaId(articleEntity.getMediaId());
				articleDTO.setCoverImageUrl(articleEntity.getCoverImageUrl());
				articleDTO.setFromOfficialAccount(articleEntity.isFromOfficialAccount());
				articleDTO.setPublishTime(articleEntity.getPublishTime());
				articleDTO.setLinkType(articleEntity.getLinkType());
				articleDTO.setNumberOfReading(articleEntity.getNumberOfReading());
				articleDTO.setPublished(articleEntity.isPublished());
				articleDTO.setArticleType(articleEntity.getArticleType());
				TagEntity tagEntity = articleEntity.getTagEntity();
				if(tagEntity != null){
					TagDTO tagDTO = new TagDTO();
					tagDTO.setTagUuid(tagEntity.getTagUuid());
					tagDTO.setTagName(tagEntity.getTagName());
					tagDTO.setTagType(tagEntity.getTagType());
					articleDTO.setTagDTO(tagDTO);
				}
				articleDTOList.add(articleDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return articleDTOList;
	}
	
	@Override
	public List<QaDTO> getPublishedQasByTagNamePage(String tagName, int startIndex, int pageSize)
			throws BusinessException {
		List<QaDTO> qaDTOList = new ArrayList<QaDTO>(); 
		try{
			List<QaEntity> qaEntityList = qaDAO.getPublishedQasByTagNamePage(tagName, startIndex, pageSize);
			for(Iterator<QaEntity> iter = qaEntityList.iterator();iter.hasNext();){
				QaEntity qaEntity = iter.next();
				QaDTO qaDTO = new QaDTO();
				qaDTO.setQaUuid(qaEntity.getQaUuid());
				qaDTO.setTitle(qaEntity.getTitle());
				qaDTO.setDigest(qaEntity.getDigest());
				qaDTO.setContent(qaEntity.getContent());
				qaDTO.setMediaId(qaEntity.getMediaId());
				qaDTO.setCoverImageUrl(qaEntity.getCoverImageUrl());
				qaDTO.setFromOfficialAccount(qaEntity.isFromOfficialAccount());
				qaDTO.setPublishTime(qaEntity.getPublishTime());
				qaDTO.setPreviewUrl(qaEntity.getPreviewUrl());
				qaDTO.setNumberOfReading(qaEntity.getNumberOfReading());
				qaDTO.setPublished(qaEntity.isPublished());
				qaDTO.setHot(qaEntity.isHot());
				TagEntity tagEntity = qaEntity.getTagEntity();
				if(tagEntity != null){
					TagDTO tagDTO = new TagDTO();
					tagDTO.setTagUuid(tagEntity.getTagUuid());
					tagDTO.setTagName(tagEntity.getTagName());
					tagDTO.setTagType(tagEntity.getTagType());
					qaDTO.setTagDTO(tagDTO);
				}
				qaDTOList.add(qaDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return qaDTOList;
	}
	
	@Override
	public List<ArticleDTO> getPublishedArticlesByTypePage(String articleType, int startIndex, int pageSize)
			throws BusinessException {
		List<ArticleDTO> articleDTOList = new ArrayList<ArticleDTO>(); 
		try{
			List<ArticleEntity> articleEntityList = articleDAO.getPublishedArticlesByTypePage(articleType, startIndex, pageSize);
			for(Iterator<ArticleEntity> iter = articleEntityList.iterator();iter.hasNext();){
				ArticleEntity articleEntity = iter.next();
				ArticleDTO articleDTO = new ArticleDTO();
				articleDTO.setArticleUuid(articleEntity.getArticleUuid());
				articleDTO.setTitle(articleEntity.getTitle());
				articleDTO.setDigest(articleEntity.getDigest());
				articleDTO.setContent(articleEntity.getContent());
				articleDTO.setMediaId(articleEntity.getMediaId());
				articleDTO.setCoverImageUrl(articleEntity.getCoverImageUrl());
				articleDTO.setFromOfficialAccount(articleEntity.isFromOfficialAccount());
				articleDTO.setPublishTime(articleEntity.getPublishTime());
				articleDTO.setLinkType(articleEntity.getLinkType());
				articleDTO.setNumberOfReading(articleEntity.getNumberOfReading());
				articleDTO.setPublished(articleEntity.isPublished());
				articleDTO.setArticleType(articleEntity.getArticleType());
				TagEntity tagEntity = articleEntity.getTagEntity();
				if(tagEntity != null){
					TagDTO tagDTO = new TagDTO();
					tagDTO.setTagUuid(tagEntity.getTagUuid());
					tagDTO.setTagName(tagEntity.getTagName());
					tagDTO.setTagType(tagEntity.getTagType());
					articleDTO.setTagDTO(tagDTO);
				}
				articleDTOList.add(articleDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return articleDTOList;
	}

	@Override
	public List<ArticleDTO> getArticles() throws BusinessException {
		List<ArticleDTO> articleDTOList = new ArrayList<ArticleDTO>(); 
		try{
			List<ArticleEntity> articleEntityList = articleDAO.getArticles();
			for(Iterator<ArticleEntity> iter = articleEntityList.iterator();iter.hasNext();){
				ArticleEntity articleEntity = iter.next();
				ArticleDTO articleDTO = new ArticleDTO();
				articleDTO.setArticleUuid(articleEntity.getArticleUuid());
				articleDTO.setTitle(articleEntity.getTitle());
				articleDTO.setDigest(articleEntity.getDigest());
				articleDTO.setContent(articleEntity.getContent());
				articleDTO.setCoverImageUrl(articleEntity.getCoverImageUrl());
				articleDTO.setMediaId(articleEntity.getMediaId());
				articleDTO.setFromOfficialAccount(articleEntity.isFromOfficialAccount());
				articleDTO.setPublishTime(articleEntity.getPublishTime());
				articleDTO.setLinkType(articleEntity.getLinkType());
				articleDTO.setNumberOfReading(articleEntity.getNumberOfReading());
				articleDTO.setPublished(articleEntity.isPublished());
				articleDTO.setArticleType(articleEntity.getArticleType());
				TagEntity tagEntity = articleEntity.getTagEntity();
				if(tagEntity != null){
					TagDTO tagDTO = new TagDTO();
					tagDTO.setTagUuid(tagEntity.getTagUuid());
					tagDTO.setTagName(tagEntity.getTagName());
					tagDTO.setTagType(tagEntity.getTagType());
					articleDTO.setTagDTO(tagDTO);
				}
				articleDTOList.add(articleDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return articleDTOList;
	}
	
	@Override
	public List<QaDTO> getQas() throws BusinessException {
		List<QaDTO> qaDTOList = new ArrayList<QaDTO>(); 
		try{
			List<QaEntity> qaEntityList = qaDAO.getQas();
			for(Iterator<QaEntity> iter = qaEntityList.iterator();iter.hasNext();){
				QaEntity qaEntity = iter.next();
				QaDTO qaDTO = new QaDTO();
				qaDTO.setQaUuid(qaEntity.getQaUuid());
				qaDTO.setTitle(qaEntity.getTitle());
				qaDTO.setDigest(qaEntity.getDigest());
//				qaDTO.setContent(qaEntity.getContent());
				qaDTO.setCoverImageUrl(qaEntity.getCoverImageUrl());
				qaDTO.setMediaId(qaEntity.getMediaId());
				qaDTO.setFromOfficialAccount(qaEntity.isFromOfficialAccount());
				qaDTO.setPublishTime(qaEntity.getPublishTime());
				qaDTO.setPreviewUrl(qaEntity.getPreviewUrl());
				qaDTO.setNumberOfReading(qaEntity.getNumberOfReading());
				qaDTO.setPublished(qaEntity.isPublished());
				qaDTO.setHot(qaEntity.isHot());
				TagEntity tagEntity = qaEntity.getTagEntity();
				if(tagEntity != null){
					TagDTO tagDTO = new TagDTO();
					tagDTO.setTagUuid(tagEntity.getTagUuid());
					tagDTO.setTagName(tagEntity.getTagName());
					tagDTO.setTagType(tagEntity.getTagType());
					qaDTO.setTagDTO(tagDTO);
				}
				qaDTOList.add(qaDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return qaDTOList;
	}
	
	@Override
	public List<QaDTO> searchQas(String title) throws BusinessException {
		List<QaDTO> qaDTOList = new ArrayList<QaDTO>(); 
		try{
			List<QaEntity> qaEntityList = qaDAO.searchQa(title);
			for(Iterator<QaEntity> iter = qaEntityList.iterator();iter.hasNext();){
				QaEntity qaEntity = iter.next();
				QaDTO qaDTO = new QaDTO();
				qaDTO.setQaUuid(qaEntity.getQaUuid());
				qaDTO.setTitle(qaEntity.getTitle());
				qaDTO.setDigest(qaEntity.getDigest());
//				qaDTO.setContent(qaEntity.getContent());
				qaDTO.setCoverImageUrl(qaEntity.getCoverImageUrl());
				qaDTO.setMediaId(qaEntity.getMediaId());
				qaDTO.setFromOfficialAccount(qaEntity.isFromOfficialAccount());
				qaDTO.setPublishTime(qaEntity.getPublishTime());
				qaDTO.setPreviewUrl(qaEntity.getPreviewUrl());
				qaDTO.setNumberOfReading(qaEntity.getNumberOfReading());
				qaDTO.setPublished(qaEntity.isPublished());
				qaDTO.setHot(qaEntity.isHot());
				TagEntity tagEntity = qaEntity.getTagEntity();
				if(tagEntity != null){
					TagDTO tagDTO = new TagDTO();
					tagDTO.setTagUuid(tagEntity.getTagUuid());
					tagDTO.setTagName(tagEntity.getTagName());
					tagDTO.setTagType(tagEntity.getTagType());
					qaDTO.setTagDTO(tagDTO);
				}
				qaDTOList.add(qaDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return qaDTOList;
	}
	
	@Override
	public List<ArticleDTO> getPublishedArticles() throws BusinessException {
		List<ArticleDTO> articleDTOList = new ArrayList<ArticleDTO>(); 
		try{
			List<ArticleEntity> articleEntityList = articleDAO.getPublishedArticles();
			for(Iterator<ArticleEntity> iter = articleEntityList.iterator();iter.hasNext();){
				ArticleEntity articleEntity = iter.next();
				ArticleDTO articleDTO = new ArticleDTO();
				articleDTO.setArticleUuid(articleEntity.getArticleUuid());
				articleDTO.setTitle(articleEntity.getTitle());
				articleDTO.setDigest(articleEntity.getDigest());
				articleDTO.setContent(articleEntity.getContent());
				articleDTO.setCoverImageUrl(articleEntity.getCoverImageUrl());
				articleDTO.setMediaId(articleEntity.getMediaId());
				articleDTO.setFromOfficialAccount(articleEntity.isFromOfficialAccount());
				articleDTO.setPublishTime(articleEntity.getPublishTime());
				articleDTO.setLinkType(articleEntity.getLinkType());
				articleDTO.setNumberOfReading(articleEntity.getNumberOfReading());
				articleDTO.setPublished(articleEntity.isPublished());
				articleDTO.setArticleType(articleEntity.getArticleType());
				TagEntity tagEntity = articleEntity.getTagEntity();
				if(tagEntity != null){
					TagDTO tagDTO = new TagDTO();
					tagDTO.setTagUuid(tagEntity.getTagUuid());
					tagDTO.setTagName(tagEntity.getTagName());
					tagDTO.setTagType(tagEntity.getTagType());
					articleDTO.setTagDTO(tagDTO);
				}
				articleDTOList.add(articleDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return articleDTOList;
	}
	
	@Override
	public List<QaDTO> getPublishedQas() throws BusinessException {
		List<QaDTO> qaDTOList = new ArrayList<QaDTO>(); 
		try{
			List<QaEntity> qaEntityList = qaDAO.getPublishedQas();
			for(Iterator<QaEntity> iter = qaEntityList.iterator();iter.hasNext();){
				QaEntity qaEntity = iter.next();
				QaDTO qaDTO = new QaDTO();
				qaDTO.setQaUuid(qaEntity.getQaUuid());
				qaDTO.setTitle(qaEntity.getTitle());
				qaDTO.setDigest(qaEntity.getDigest());
//				qaDTO.setContent(qaEntity.getContent());
				qaDTO.setCoverImageUrl(qaEntity.getCoverImageUrl());
				qaDTO.setMediaId(qaEntity.getMediaId());
				qaDTO.setFromOfficialAccount(qaEntity.isFromOfficialAccount());
				qaDTO.setPublishTime(qaEntity.getPublishTime());
				qaDTO.setPreviewUrl(qaEntity.getPreviewUrl());
				qaDTO.setNumberOfReading(qaEntity.getNumberOfReading());
				qaDTO.setPublished(qaEntity.isPublished());
				qaDTO.setHot(qaEntity.isHot());
				TagEntity tagEntity = qaEntity.getTagEntity();
				if(tagEntity != null){
					TagDTO tagDTO = new TagDTO();
					tagDTO.setTagUuid(tagEntity.getTagUuid());
					tagDTO.setTagName(tagEntity.getTagName());
					tagDTO.setTagType(tagEntity.getTagType());
					qaDTO.setTagDTO(tagDTO);
				}
				qaDTOList.add(qaDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return qaDTOList;
	}

	@Override
	public List<ArticleDTO> getArticlesByPage(int startIndex, int pageSize) throws BusinessException {
		List<ArticleDTO> articleDTOList = new ArrayList<ArticleDTO>(); 
		try{
			List<ArticleEntity> articleEntityList = articleDAO.getArticlesByPage(startIndex, pageSize);
			for(Iterator<ArticleEntity> iter = articleEntityList.iterator();iter.hasNext();){
				ArticleEntity articleEntity = iter.next();
				ArticleDTO articleDTO = new ArticleDTO();
				articleDTO.setArticleUuid(articleEntity.getArticleUuid());
				articleDTO.setTitle(articleEntity.getTitle());
				articleDTO.setDigest(articleEntity.getDigest());
				articleDTO.setContent(articleEntity.getContent());
				articleDTO.setCoverImageUrl(articleEntity.getCoverImageUrl());
				articleDTO.setMediaId(articleEntity.getMediaId());
				articleDTO.setFromOfficialAccount(articleEntity.isFromOfficialAccount());
				articleDTO.setPublishTime(articleEntity.getPublishTime());
				articleDTO.setLinkType(articleEntity.getLinkType());
				articleDTO.setNumberOfReading(articleEntity.getNumberOfReading());
				articleDTO.setPublished(articleEntity.isPublished());
				articleDTO.setArticleType(articleEntity.getArticleType());
				TagEntity tagEntity = articleEntity.getTagEntity();
				if(tagEntity != null){
					TagDTO tagDTO = new TagDTO();
					tagDTO.setTagUuid(tagEntity.getTagUuid());
					tagDTO.setTagName(tagEntity.getTagName());
					tagDTO.setTagType(tagEntity.getTagType());
					articleDTO.setTagDTO(tagDTO);
				}
				articleDTOList.add(articleDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return articleDTOList;
	}
	
	@Override
	public List<QaDTO> getQasByPage(int startIndex, int pageSize) throws BusinessException {
		List<QaDTO> qaDTOList = new ArrayList<QaDTO>(); 
		try{
			List<QaEntity> qaEntityList = qaDAO.getQasByPage(startIndex, pageSize);
			for(Iterator<QaEntity> iter = qaEntityList.iterator();iter.hasNext();){
				QaEntity qaEntity = iter.next();
				QaDTO qaDTO = new QaDTO();
				qaDTO.setQaUuid(qaEntity.getQaUuid());
				qaDTO.setTitle(qaEntity.getTitle());
				qaDTO.setDigest(qaEntity.getDigest());
//				qaDTO.setContent(qaEntity.getContent());
				qaDTO.setCoverImageUrl(qaEntity.getCoverImageUrl());
				qaDTO.setMediaId(qaEntity.getMediaId());
				qaDTO.setFromOfficialAccount(qaEntity.isFromOfficialAccount());
				qaDTO.setPublishTime(qaEntity.getPublishTime());
				qaDTO.setPreviewUrl(qaEntity.getPreviewUrl());
				qaDTO.setNumberOfReading(qaEntity.getNumberOfReading());
				qaDTO.setPublished(qaEntity.isPublished());
				qaDTO.setHot(qaEntity.isHot());
				TagEntity tagEntity = qaEntity.getTagEntity();
				if(tagEntity != null){
					TagDTO tagDTO = new TagDTO();
					tagDTO.setTagUuid(tagEntity.getTagUuid());
					tagDTO.setTagName(tagEntity.getTagName());
					tagDTO.setTagType(tagEntity.getTagType());
					qaDTO.setTagDTO(tagDTO);
				}
				qaDTOList.add(qaDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return qaDTOList;
	}
	
	@Override
	public List<ArticleDTO> getPublishedArticlesByPage(int startIndex, int pageSize) throws BusinessException {
		List<ArticleDTO> articleDTOList = new ArrayList<ArticleDTO>(); 
		try{
			List<ArticleEntity> articleEntityList = articleDAO.getPublishedArticlesByPage(startIndex, pageSize);
			for(Iterator<ArticleEntity> iter = articleEntityList.iterator();iter.hasNext();){
				ArticleEntity articleEntity = iter.next();
				ArticleDTO articleDTO = new ArticleDTO();
				articleDTO.setArticleUuid(articleEntity.getArticleUuid());
				articleDTO.setTitle(articleEntity.getTitle());
				articleDTO.setDigest(articleEntity.getDigest());
				articleDTO.setContent(articleEntity.getContent());
				articleDTO.setCoverImageUrl(articleEntity.getCoverImageUrl());
				articleDTO.setMediaId(articleEntity.getMediaId());
				articleDTO.setFromOfficialAccount(articleEntity.isFromOfficialAccount());
				articleDTO.setPublishTime(articleEntity.getPublishTime());
				articleDTO.setLinkType(articleEntity.getLinkType());
				articleDTO.setNumberOfReading(articleEntity.getNumberOfReading());
				articleDTO.setPublished(articleEntity.isPublished());
				articleDTO.setArticleType(articleEntity.getArticleType());
				TagEntity tagEntity = articleEntity.getTagEntity();
				if(tagEntity != null){
					TagDTO tagDTO = new TagDTO();
					tagDTO.setTagUuid(tagEntity.getTagUuid());
					tagDTO.setTagName(tagEntity.getTagName());
					tagDTO.setTagType(tagEntity.getTagType());
					articleDTO.setTagDTO(tagDTO);
				}
				articleDTOList.add(articleDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return articleDTOList;
	}
	
	@Override
	public List<QaDTO> getPublishedQasByPage(int startIndex, int pageSize) throws BusinessException {
		List<QaDTO> qaDTOList = new ArrayList<QaDTO>(); 
		try{
			List<QaEntity> qaEntityList = qaDAO.getPublishedQasByPage(startIndex, pageSize);
			for(Iterator<QaEntity> iter = qaEntityList.iterator();iter.hasNext();){
				QaEntity qaEntity = iter.next();
				QaDTO qaDTO = new QaDTO();
				qaDTO.setQaUuid(qaEntity.getQaUuid());
				qaDTO.setTitle(qaEntity.getTitle());
				qaDTO.setDigest(qaEntity.getDigest());
//				qaDTO.setContent(qaEntity.getContent());
				qaDTO.setCoverImageUrl(qaEntity.getCoverImageUrl());
				qaDTO.setMediaId(qaEntity.getMediaId());
				qaDTO.setFromOfficialAccount(qaEntity.isFromOfficialAccount());
				qaDTO.setPublishTime(qaEntity.getPublishTime());
				qaDTO.setPreviewUrl(qaEntity.getPreviewUrl());
				qaDTO.setNumberOfReading(qaEntity.getNumberOfReading());
				qaDTO.setPublished(qaEntity.isPublished());
				qaDTO.setHot(qaEntity.isHot());
				TagEntity tagEntity = qaEntity.getTagEntity();
				if(tagEntity != null){
					TagDTO tagDTO = new TagDTO();
					tagDTO.setTagUuid(tagEntity.getTagUuid());
					tagDTO.setTagName(tagEntity.getTagName());
					tagDTO.setTagType(tagEntity.getTagType());
					qaDTO.setTagDTO(tagDTO);
				}
				qaDTOList.add(qaDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return qaDTOList;
	}
	
	@Override
	public int getArticleCountByTagName(String tagName) throws BusinessException {
		try {
			return articleDAO.getArticleCountByTagName(tagName);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public int getQaCountByTagName(String tagName) throws BusinessException {
		try {
			return qaDAO.getQaCountByTagName(tagName);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public int getArticleCountByType(String articleType) throws BusinessException {
		try {
			return articleDAO.getArticleCountByType(articleType);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public int getArticleCountByTypes(List<String> articleTypeList) throws BusinessException {
		try {
			return articleDAO.getArticleCountByTypes(articleTypeList);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public int getPublishedArticleCountByTagName(String tagName) throws BusinessException {
		try {
			return articleDAO.getPublishedArticleCountByTagName(tagName);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public int getPublishedQaCountByTagName(String tagName) throws BusinessException {
		try {
			return qaDAO.getPublishedQaCountByTagName(tagName);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public int getPublishedArticleCountByType(String articleType) throws BusinessException {
		try {
			return articleDAO.getPublishedArticleCountByType(articleType);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public int getArticleCount() throws BusinessException {
		try {
			return articleDAO.getArticleCount();
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public int getQaCount() throws BusinessException {
		try {
			return qaDAO.getQaCount();
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public int getPublishedArticleCount() throws BusinessException {
		try {
			return articleDAO.getPublishedArticleCount();
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public int getPublishedQaCount() throws BusinessException {
		try {
			return qaDAO.getPublishedQaCount();
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public ArticleDTO getArticleByUuid(String uuid) throws BusinessException {
		try{
			ArticleEntity articleEntity = articleDAO.getArticleByUuid(uuid);
			ArticleDTO articleDTO = new ArticleDTO();
			articleDTO.setArticleUuid(articleEntity.getArticleUuid());
			articleDTO.setTitle(articleEntity.getTitle());
			articleDTO.setDigest(articleEntity.getDigest());
			articleDTO.setContent(articleEntity.getContent());
			articleDTO.setCoverImageUrl(articleEntity.getCoverImageUrl());
			articleDTO.setMediaId(articleEntity.getMediaId());
			articleDTO.setFromOfficialAccount(articleEntity.isFromOfficialAccount());
			articleDTO.setPublishTime(articleEntity.getPublishTime());
			articleDTO.setLinkType(articleEntity.getLinkType());
			articleDTO.setNumberOfReading(articleEntity.getNumberOfReading());
			articleDTO.setPublished(articleEntity.isPublished());
			articleDTO.setArticleType(articleEntity.getArticleType());
			TagEntity tagEntity = articleEntity.getTagEntity();
			if(tagEntity != null){
				TagDTO tagDTO = new TagDTO();
				tagDTO.setTagUuid(tagEntity.getTagUuid());
				tagDTO.setTagName(tagEntity.getTagName());
				tagDTO.setTagType(tagEntity.getTagType());
				articleDTO.setTagDTO(tagDTO);
			}
			//阅读量+1
			articleEntity.setNumberOfReading(articleEntity.getNumberOfReading()+1);
			return articleDTO;
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public QaDTO getQaByUuid(String uuid) throws BusinessException {
		try{
			QaEntity qaEntity = qaDAO.getQaByUuid(uuid);
			QaDTO qaDTO = new QaDTO();
			qaDTO.setQaUuid(qaEntity.getQaUuid());
			qaDTO.setTitle(qaEntity.getTitle());
			qaDTO.setDigest(qaEntity.getDigest());
			qaDTO.setContent(qaEntity.getContent());
			qaDTO.setCoverImageUrl(qaEntity.getCoverImageUrl());
			qaDTO.setMediaId(qaEntity.getMediaId());
			qaDTO.setFromOfficialAccount(qaEntity.isFromOfficialAccount());
			qaDTO.setPublishTime(qaEntity.getPublishTime());
			qaDTO.setPreviewUrl(qaEntity.getPreviewUrl());
			qaDTO.setNumberOfReading(qaEntity.getNumberOfReading());
			qaDTO.setPublished(qaEntity.isPublished());
			qaDTO.setHot(qaEntity.isHot());
			TagEntity tagEntity = qaEntity.getTagEntity();
			if(tagEntity != null){
				TagDTO tagDTO = new TagDTO();
				tagDTO.setTagUuid(tagEntity.getTagUuid());
				tagDTO.setTagName(tagEntity.getTagName());
				tagDTO.setTagType(tagEntity.getTagType());
				qaDTO.setTagDTO(tagDTO);
			}
			//阅读量+1
			qaEntity.setNumberOfReading(qaEntity.getNumberOfReading()+1);
			return qaDTO;
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}

	@Override
	public void addSwiper(SwiperDTO swiperDTO) throws BusinessException {
		
		try{
			SwiperEntity swiperEntity = new SwiperEntity();
			swiperEntity.setUrl(swiperDTO.getUrl());
			swiperEntity.setContent(swiperDTO.getContent());
			swiperEntity.setLinkType(swiperDTO.getLinkType());
			swiperEntity.setLocation(swiperDTO.getLocation());
			ProductCateDTO productCateDTO = swiperDTO.getProductCateDTO();
			if(productCateDTO!=null){
				ProductCateEntity productCateEntity = productCateDAO.getProductCateByUuid(productCateDTO.getProductCateUuid());
				swiperEntity.setProductCateEntity(productCateEntity);
			}
			swiperDAO.addSwiper(swiperEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void addAd(AdDTO adDTO) throws BusinessException {
		
		try{
			AdEntity adEntity = new AdEntity();
			adEntity.setUrl(adDTO.getUrl());
			adEntity.setContent(adDTO.getContent());
			adEntity.setLinkType(adDTO.getLinkType());
			adEntity.setLocation(adDTO.getLocation());
			adEntity.setActive(false);
			ProductCateDTO productCateDTO = adDTO.getProductCateDTO();
			if(productCateDTO!=null){
				ProductCateEntity productCateEntity = productCateDAO.getProductCateByUuid(productCateDTO.getProductCateUuid());
				adEntity.setProductCateEntity(productCateEntity);
			}
			adDAO.addAd(adEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void updateSwiper(SwiperDTO swiperDTO) throws BusinessException {
		
		try{
			SwiperEntity swiperEntity = swiperDAO.getSwiperByUuid(swiperDTO.getSwiperUuid());
			swiperEntity.setUrl(swiperDTO.getUrl());
			swiperEntity.setContent(swiperDTO.getContent());
			swiperEntity.setLinkType(swiperDTO.getLinkType());
			swiperEntity.setLocation(swiperDTO.getLocation());
			swiperDAO.updateSwiper(swiperEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void updateAd(AdDTO adDTO) throws BusinessException {
		
		try{
			AdEntity adEntity = adDAO.getAdByUuid(adDTO.getAdUuid());
			adEntity.setUrl(adDTO.getUrl());
			adEntity.setContent(adDTO.getContent());
			adEntity.setLinkType(adDTO.getLinkType());
			adEntity.setLocation(adDTO.getLocation());
			ProductCateDTO productCateDTO = adDTO.getProductCateDTO();
			if(productCateDTO!=null){
				ProductCateEntity productCateEntity = productCateDAO.getProductCateByUuid(productCateDTO.getProductCateUuid());
				adEntity.setProductCateEntity(productCateEntity);
			}
			adDAO.updateAd(adEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void enableAd(AdDTO adDTO) throws BusinessException {
		
		try{
			AdEntity adEntity = adDAO.getAdByUuid(adDTO.getAdUuid());
			adEntity.setActive(true);
			adDAO.updateAd(adEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void disableAd(AdDTO adDTO) throws BusinessException {
		
		try{
			AdEntity adEntity = adDAO.getAdByUuid(adDTO.getAdUuid());
			adEntity.setActive(false);
			adDAO.updateAd(adEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public void deleteSwiper(SwiperDTO swiperDTO) throws BusinessException {
		
		try{
			SwiperEntity swiperEntity = swiperDAO.getSwiperByUuid(swiperDTO.getSwiperUuid());
			swiperDAO.deleteSwiper(swiperEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void deleteAd(AdDTO adDTO) throws BusinessException {
		
		try{
			AdEntity adEntity = adDAO.getAdByUuid(adDTO.getAdUuid());
			adDAO.deleteAd(adEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public List<SwiperDTO> getSwipers() throws BusinessException {
		List<SwiperDTO> dtoList = new ArrayList<SwiperDTO>();
		try{
			List<SwiperEntity> swiperEntityList = swiperDAO.getSwipers();
			for(Iterator<SwiperEntity> iter = swiperEntityList.iterator();iter.hasNext();){
				SwiperEntity swiperEntity = iter.next();
				SwiperDTO swiperDTO = new SwiperDTO();
				swiperDTO.setUrl(swiperEntity.getUrl());
				swiperDTO.setContent(swiperEntity.getContent());
				swiperDTO.setLinkType(swiperEntity.getLinkType());
				swiperDTO.setLocation(swiperEntity.getLocation());
				swiperDTO.setSwiperUuid(swiperEntity.getSwiperUuid());
				
				ProductCateEntity productCateEntity = swiperEntity.getProductCateEntity();
				if(productCateEntity!=null){
					ProductCateDTO productCateDTO = new ProductCateDTO();
					productCateDTO.setProductCateUuid(productCateEntity.getProductCateUuid());
					productCateDTO.setCateName(productCateEntity.getCateName());
					swiperDTO.setProductCateDTO(productCateDTO);
				}
				dtoList.add(swiperDTO);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return dtoList;
	}
	
	@Override
	public List<AdDTO> getAllAds() throws BusinessException {
		List<AdDTO> dtoList = new ArrayList<AdDTO>();
		try{
			List<AdEntity> adEntityList = adDAO.getAllAds();
			for(Iterator<AdEntity> iter = adEntityList.iterator();iter.hasNext();){
				AdEntity adEntity = iter.next();
				AdDTO adDTO = new AdDTO();
				adDTO.setUrl(adEntity.getUrl());
				adDTO.setContent(adEntity.getContent());
				adDTO.setLinkType(adEntity.getLinkType());
				adDTO.setLocation(adEntity.getLocation());
				adDTO.setAdUuid(adEntity.getAdUuid());
				adDTO.setActive(adEntity.isActive());
				
				if(AdConstants.LINK_TYPE_PRODUCT.equals(adEntity.getLinkType())){
					String productUuid = adEntity.getContent();
					ProductEntity productEntity = productDAO.getProductByUuid(productUuid);
					if(productEntity != null) {
						ProductDTO productDTO = new ProductDTO();
						productDTO.setProductUuid(productEntity.getProductUuid());
						productDTO.setProductName(productEntity.getProductName());
						adDTO.setProductDTO(productDTO);
					}
					
				}
				
				if(AdConstants.LINK_TYPE_PRODUCT_GROUP.equals(adEntity.getLinkType())){
					String groupUuid = adEntity.getContent();
					GroupEntity groupEntity = groupDAO.getGroupByUuid(groupUuid);
					if(groupEntity != null) {
						GroupDTO groupDTO = new GroupDTO();
						groupDTO.setGroupUuid(groupEntity.getGroupUuid());
						groupDTO.setGroupName(groupEntity.getGroupName());
						adDTO.setGroupDTO(groupDTO);
					}
					
				}
				
				ProductCateEntity productCateEntity = adEntity.getProductCateEntity();
				if(productCateEntity!=null){
					ProductCateDTO productCateDTO = new ProductCateDTO();
					productCateDTO.setProductCateUuid(productCateEntity.getProductCateUuid());
					productCateDTO.setCateName(productCateEntity.getCateName());
					adDTO.setProductCateDTO(productCateDTO);
				}
				dtoList.add(adDTO);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return dtoList;
	}
	
	@Override
	public List<AdDTO> getActiveAds() throws BusinessException {
		List<AdDTO> dtoList = new ArrayList<AdDTO>();
		try{
			List<AdEntity> adEntityList = adDAO.getActiveAds();
			for(Iterator<AdEntity> iter = adEntityList.iterator();iter.hasNext();){
				AdEntity adEntity = iter.next();
				AdDTO adDTO = new AdDTO();
				adDTO.setUrl(adEntity.getUrl());
				adDTO.setContent(adEntity.getContent());
				adDTO.setLinkType(adEntity.getLinkType());
				adDTO.setLocation(adEntity.getLocation());
				adDTO.setAdUuid(adEntity.getAdUuid());
				adDTO.setActive(adEntity.isActive());
				
				if(AdConstants.LINK_TYPE_PRODUCT.equals(adEntity.getLinkType())){
					String productUuid = adEntity.getContent();
					ProductEntity productEntity = productDAO.getProductByUuid(productUuid);
					if(productEntity != null) {
						ProductDTO productDTO = new ProductDTO();
						productDTO.setProductUuid(productEntity.getProductUuid());
						productDTO.setProductName(productEntity.getProductName());
						adDTO.setProductDTO(productDTO);
					}
					
				}
				
				ProductCateEntity productCateEntity = adEntity.getProductCateEntity();
				if(productCateEntity!=null){
					ProductCateDTO productCateDTO = new ProductCateDTO();
					productCateDTO.setProductCateUuid(productCateEntity.getProductCateUuid());
					productCateDTO.setCateName(productCateEntity.getCateName());
					adDTO.setProductCateDTO(productCateDTO);
				}
				dtoList.add(adDTO);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return dtoList;
	}
	
	@Override
	public List<AdDTO> getAdsByLocation(String location) throws BusinessException {
		List<AdDTO> dtoList = new ArrayList<AdDTO>();
		try{
			List<AdEntity> adEntityList = adDAO.getAdsByLocation(location);
			for(Iterator<AdEntity> iter = adEntityList.iterator();iter.hasNext();){
				AdEntity adEntity = iter.next();
				AdDTO adDTO = new AdDTO();
				adDTO.setUrl(adEntity.getUrl());
				adDTO.setContent(adEntity.getContent());
				adDTO.setLinkType(adEntity.getLinkType());
				adDTO.setLocation(adEntity.getLocation());
				adDTO.setAdUuid(adEntity.getAdUuid());
				adDTO.setActive(adEntity.isActive());
				
				if(AdConstants.LINK_TYPE_PRODUCT.equals(adEntity.getLinkType())){
					String productUuid = adEntity.getContent();
					ProductEntity productEntity = productDAO.getProductByUuid(productUuid);
					if(productEntity != null) {
						ProductDTO productDTO = new ProductDTO();
						productDTO.setProductUuid(productEntity.getProductUuid());
						productDTO.setProductName(productEntity.getProductName());
						adDTO.setProductDTO(productDTO);
					}
					
				}
				
				if(AdConstants.LINK_TYPE_PRODUCT_GROUP.equals(adEntity.getLinkType())){
					String groupUuid = adEntity.getContent();
					GroupEntity groupEntity = groupDAO.getGroupByUuid(groupUuid);
					if(groupEntity != null) {
						GroupDTO groupDTO = new GroupDTO();
						groupDTO.setGroupUuid(groupEntity.getGroupUuid());
						groupDTO.setGroupName(groupEntity.getGroupName());
						adDTO.setGroupDTO(groupDTO);
					}
					
				}
				
				ProductCateEntity productCateEntity = adEntity.getProductCateEntity();
				if(productCateEntity!=null){
					ProductCateDTO productCateDTO = new ProductCateDTO();
					productCateDTO.setProductCateUuid(productCateEntity.getProductCateUuid());
					productCateDTO.setCateName(productCateEntity.getCateName());
					adDTO.setProductCateDTO(productCateDTO);
				}
				dtoList.add(adDTO);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return dtoList;
	}
	
	@Override
	public List<AdDTO> getAdsByProductCate(String productCateUuid) throws BusinessException {
		List<AdDTO> dtoList = new ArrayList<AdDTO>();
		try{
			List<AdEntity> adEntityList = adDAO.getAdsByProductCate(productCateUuid);
			for(Iterator<AdEntity> iter = adEntityList.iterator();iter.hasNext();){
				AdEntity adEntity = iter.next();
				AdDTO adDTO = new AdDTO();
				adDTO.setUrl(adEntity.getUrl());
				adDTO.setContent(adEntity.getContent());
				adDTO.setLinkType(adEntity.getLinkType());
				adDTO.setLocation(adEntity.getLocation());
				adDTO.setAdUuid(adEntity.getAdUuid());
				adDTO.setActive(adEntity.isActive());
				
				if(AdConstants.LINK_TYPE_PRODUCT.equals(adEntity.getLinkType())){
					String productUuid = adEntity.getContent();
					ProductEntity productEntity = productDAO.getProductByUuid(productUuid);
					if(productEntity != null) {
						ProductDTO productDTO = new ProductDTO();
						productDTO.setProductUuid(productEntity.getProductUuid());
						productDTO.setProductName(productEntity.getProductName());
						adDTO.setProductDTO(productDTO);
					}
					
				}
				
				if(AdConstants.LINK_TYPE_PRODUCT_GROUP.equals(adEntity.getLinkType())){
					String groupUuid = adEntity.getContent();
					GroupEntity groupEntity = groupDAO.getGroupByUuid(groupUuid);
					if(groupEntity != null) {
						GroupDTO groupDTO = new GroupDTO();
						groupDTO.setGroupUuid(groupEntity.getGroupUuid());
						groupDTO.setGroupName(groupEntity.getGroupName());
						adDTO.setGroupDTO(groupDTO);
					}
					
				}
				
				ProductCateEntity productCateEntity = adEntity.getProductCateEntity();
				if(productCateEntity!=null){
					ProductCateDTO productCateDTO = new ProductCateDTO();
					productCateDTO.setProductCateUuid(productCateEntity.getProductCateUuid());
					productCateDTO.setCateName(productCateEntity.getCateName());
					adDTO.setProductCateDTO(productCateDTO);
				}
				dtoList.add(adDTO);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return dtoList;
	}
	
	@Override
	public List<AdDTO> getAdsByLocationProductCate(String location,String productCateUuid) throws BusinessException {
		List<AdDTO> dtoList = new ArrayList<AdDTO>();
		try{
			List<AdEntity> adEntityList = adDAO.getAdsByLocationProductCate(location,productCateUuid);
			for(Iterator<AdEntity> iter = adEntityList.iterator();iter.hasNext();){
				AdEntity adEntity = iter.next();
				AdDTO adDTO = new AdDTO();
				adDTO.setUrl(adEntity.getUrl());
				adDTO.setContent(adEntity.getContent());
				adDTO.setLinkType(adEntity.getLinkType());
				adDTO.setLocation(adEntity.getLocation());
				adDTO.setAdUuid(adEntity.getAdUuid());
				adDTO.setActive(adEntity.isActive());
				
				if(AdConstants.LINK_TYPE_PRODUCT.equals(adEntity.getLinkType())){
					String productUuid = adEntity.getContent();
					ProductEntity productEntity = productDAO.getProductByUuid(productUuid);
					if(productEntity != null) {
						ProductDTO productDTO = new ProductDTO();
						productDTO.setProductUuid(productEntity.getProductUuid());
						productDTO.setProductName(productEntity.getProductName());
						adDTO.setProductDTO(productDTO);
					}
					
				}
				
				if(AdConstants.LINK_TYPE_PRODUCT_GROUP.equals(adEntity.getLinkType())){
					String groupUuid = adEntity.getContent();
					GroupEntity groupEntity = groupDAO.getGroupByUuid(groupUuid);
					if(groupEntity != null) {
						GroupDTO groupDTO = new GroupDTO();
						groupDTO.setGroupUuid(groupEntity.getGroupUuid());
						groupDTO.setGroupName(groupEntity.getGroupName());
						adDTO.setGroupDTO(groupDTO);
					}
					
				}
				
				ProductCateEntity productCateEntity = adEntity.getProductCateEntity();
				if(productCateEntity!=null){
					ProductCateDTO productCateDTO = new ProductCateDTO();
					productCateDTO.setProductCateUuid(productCateEntity.getProductCateUuid());
					productCateDTO.setCateName(productCateEntity.getCateName());
					adDTO.setProductCateDTO(productCateDTO);
				}
				dtoList.add(adDTO);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return dtoList;
	}
	
	@Override
	public SwiperDTO getSwiperByUuid(String uuid) throws BusinessException {
		try{
			SwiperEntity swiperEntity = swiperDAO.getSwiperByUuid(uuid);
			SwiperDTO swiperDTO = new SwiperDTO();
			swiperDTO.setUrl(swiperEntity.getUrl());
			swiperDTO.setContent(swiperEntity.getContent());
			swiperDTO.setLinkType(swiperEntity.getLinkType());
			swiperDTO.setSwiperUuid(swiperEntity.getSwiperUuid());
			ProductCateEntity productCateEntity = swiperEntity.getProductCateEntity();
			if(productCateEntity!=null){
				ProductCateDTO productCateDTO = new ProductCateDTO();
				productCateDTO.setProductCateUuid(productCateEntity.getProductCateUuid());
				productCateDTO.setCateName(productCateEntity.getCateName());
				swiperDTO.setProductCateDTO(productCateDTO);
			}
			return swiperDTO;
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public AdDTO getAdByUuid(String uuid) throws BusinessException {
		try{
			AdEntity adEntity = adDAO.getAdByUuid(uuid);
			AdDTO adDTO = new AdDTO();
			adDTO.setUrl(adEntity.getUrl());
			adDTO.setContent(adEntity.getContent());
			adDTO.setLinkType(adEntity.getLinkType());
			adDTO.setAdUuid(adEntity.getAdUuid());
			adDTO.setLocation(adEntity.getLocation());
			adDTO.setActive(adEntity.isActive());
			
			if(AdConstants.LINK_TYPE_PRODUCT.equals(adEntity.getLinkType())){
				String productUuid = adEntity.getContent();
				ProductEntity productEntity = productDAO.getProductByUuid(productUuid);
				if(productEntity != null) {
					ProductDTO productDTO = new ProductDTO();
					productDTO.setProductUuid(productEntity.getProductUuid());
					productDTO.setProductName(productEntity.getProductName());
					adDTO.setProductDTO(productDTO);
				}
				
			}
			
			if(AdConstants.LINK_TYPE_PRODUCT_GROUP.equals(adEntity.getLinkType())){
				String groupUuid = adEntity.getContent();
				GroupEntity groupEntity = groupDAO.getGroupByUuid(groupUuid);
				if(groupEntity != null) {
					GroupDTO groupDTO = new GroupDTO();
					groupDTO.setGroupUuid(groupEntity.getGroupUuid());
					groupDTO.setGroupName(groupEntity.getGroupName());
					adDTO.setGroupDTO(groupDTO);
				}
				
			}
			
			ProductCateEntity productCateEntity = adEntity.getProductCateEntity();
			if(productCateEntity!=null){
				ProductCateDTO productCateDTO = new ProductCateDTO();
				productCateDTO.setProductCateUuid(productCateEntity.getProductCateUuid());
				productCateDTO.setCateName(productCateEntity.getCateName());
				adDTO.setProductCateDTO(productCateDTO);
			}
			return adDTO;
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	class SendMsgNewsThread implements Runnable {
		
		private final LogHelper logger = LogHelper.getInstance(this.getClass()
				.getName());

		private ArticleDTO articleDTO;
		
		public ArticleDTO getArticleDTO() {
			return articleDTO;
		}

		public void setArticleDTO(ArticleDTO articleDTO) {
			this.articleDTO = articleDTO;
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
		    			List<WXJSONNewsArticle> articles = new ArrayList<WXJSONNewsArticle>();
		    			WXJSONNewsArticle article = new WXJSONNewsArticle(); 
		    			article.setTitle(articleDTO.getTitle());
		    			article.setDescription("优享云集微站会员权益, 分享该文可植入你的个人及云集店铺二维码, 推广你的个人微站以获取更多VIP和店主");
		    			article.setUrl("http://yunji.qinghuiyang.com/scrm-web/#/ArticleDetail?uid="+userEntity.getUserUuid()+"&a_id="+articleDTO.getArticleUuid());
		    			article.setPicurl(articleDTO.getCoverImageUrl());
		    			articles.add(article);
		    			news.setArticles(articles);
		    			wxInitializeUtility.sendMessageNews(news, openId);
		    		}
		    	}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
	    	

		}
	}
	
	class SendPosterMsgNewsThread implements Runnable {
		
		private final LogHelper logger = LogHelper.getInstance(this.getClass()
				.getName());

		private PosterDTO posterDTO;
		
		public PosterDTO getPosterDTO() {
			return posterDTO;
		}

		public void setPosterDTO(PosterDTO posterDTO) {
			this.posterDTO = posterDTO;
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
		    			List<WXJSONNewsArticle> articles = new ArrayList<WXJSONNewsArticle>();
		    			WXJSONNewsArticle article = new WXJSONNewsArticle(); 
		    			article.setTitle("亲爱的云集家人, 为您准备了新的二维码早晚安励志海报, 快去下载并发到朋友圈吧 !");
		    			article.setUrl("http://yunji.qinghuiyang.com/scrm-web/#/GeneratePoster?uid="+userEntity.getUserUuid()+"&p_id="+posterDTO.getPosterUuid());
		    			article.setPicurl(posterDTO.getUrl());
		    			articles.add(article);
		    			news.setArticles(articles);
		    			wxInitializeUtility.sendMessageNews(news, openId);
		    		}
		    	}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
	    	

		}
	}

	@Override
	public void addMateria(MateriaDTO materiaDTO) throws BusinessException {
		try{
			MateriaEntity materiaEntity = new MateriaEntity();
			materiaEntity.setUrl(materiaDTO.getUrl());
			materiaEntity.setType(materiaDTO.getType());
			materiaDAO.addMateria(materiaEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public void deleteMateria(MateriaDTO materiaDTO) throws BusinessException {
		try{
			MateriaEntity materiaEntity = materiaDAO.getMateriaByUuid(materiaDTO.getMateriaUuid());
			materiaDAO.deleteMateria(materiaEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public List<MateriaDTO> getMateriasByType(String type)
			throws BusinessException {

		List<MateriaDTO> materiaDTOList = new ArrayList<MateriaDTO>(); 
		try{
			List<MateriaEntity> materiaEntityList = materiaDAO.getMateriasByType(type);
			for(Iterator<MateriaEntity> iter = materiaEntityList.iterator();iter.hasNext();){
				MateriaEntity materiaEntity = iter.next();
				MateriaDTO materiaDTO = new MateriaDTO();
				materiaDTO.setUrl(materiaEntity.getUrl());
				materiaDTO.setType(materiaEntity.getType());
				materiaDTO.setMateriaUuid(materiaEntity.getMateriaUuid());
				materiaDTOList.add(materiaDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return materiaDTOList;
	
	}

	@Override
	public List<MateriaDTO> getMaterias() throws BusinessException {

		List<MateriaDTO> materiaDTOList = new ArrayList<MateriaDTO>(); 
		try{
			List<MateriaEntity> materiaEntityList = materiaDAO.getMaterias();
			for(Iterator<MateriaEntity> iter = materiaEntityList.iterator();iter.hasNext();){
				MateriaEntity materiaEntity = iter.next();
				MateriaDTO materiaDTO = new MateriaDTO();
				materiaDTO.setUrl(materiaEntity.getUrl());
				materiaDTO.setType(materiaEntity.getType());
				materiaDTO.setMateriaUuid(materiaEntity.getMateriaUuid());
				materiaDTOList.add(materiaDTO);
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return materiaDTOList;
	
	}

	@Override
	public MateriaDTO getMateriaByUuid(String uuid) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}
}
