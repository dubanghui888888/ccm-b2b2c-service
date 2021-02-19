package com.mb.ext.web.controller.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mb.ext.core.constant.AuthenticationConstants;
import com.mb.ext.core.constant.AuthorizationConstants;
import com.mb.ext.core.constant.ContentConstants;
import com.mb.ext.core.context.UserContext;
import com.mb.ext.core.dao.SettingDAO;
import com.mb.ext.core.service.AdminService;
import com.mb.ext.core.service.AuthenticationService;
import com.mb.ext.core.service.ContentService;
import com.mb.ext.core.service.LogService;
import com.mb.ext.core.service.OSSService;
import com.mb.ext.core.service.ShareService;
import com.mb.ext.core.service.UserService;
import com.mb.ext.core.service.spec.AdDTO;
import com.mb.ext.core.service.spec.AdDTOList;
import com.mb.ext.core.service.spec.AdminDTO;
import com.mb.ext.core.service.spec.RequestDTO;
import com.mb.ext.core.service.spec.ResultDTO;
import com.mb.ext.core.service.spec.SwiperDTO;
import com.mb.ext.core.service.spec.SwiperDTOList;
import com.mb.ext.core.service.spec.content.ArticleDTO;
import com.mb.ext.core.service.spec.content.ArticleDTOList;
import com.mb.ext.core.service.spec.content.MateriaDTO;
import com.mb.ext.core.service.spec.content.MateriaDTOList;
import com.mb.ext.core.service.spec.content.PosterDTO;
import com.mb.ext.core.service.spec.content.PosterDTOList;
import com.mb.ext.core.service.spec.content.QaDTO;
import com.mb.ext.core.service.spec.content.QaDTOList;
import com.mb.ext.core.service.spec.content.SceneDTO;
import com.mb.ext.core.service.spec.content.SceneDTOList;
import com.mb.ext.core.service.spec.content.ShareDTO;
import com.mb.ext.core.service.spec.content.ShareDTOList;
import com.mb.ext.core.service.spec.content.TagDTO;
import com.mb.ext.core.service.spec.content.TagDTOList;
import com.mb.ext.core.util.MailSenderUtil;
import com.mb.ext.web.util.MessageHelper;
import com.mb.ext.web.util.TokenCheckUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;


/**后台管理内容类
 * @author B2B2C商城
 *
 */
@Controller
public class AdminContentController {

	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private OSSService ossService;
	
	@Autowired
	private LogService logService;
	
	@Autowired
	private SettingDAO setttingDAO;
	
	@Autowired
	private ContentService contentService;
	
	@Autowired
	private ShareService shareService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MailSenderUtil mailSenderUtil;

	@Autowired
	private PropertyRepository propertyRepository;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private TokenCheckUtil tokenCheckUtil;

	/**增删改内容标签
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changeTag", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeTag(@RequestBody RequestDTO<TagDTO> requestDTO) {

		TagDTO tagDTO = requestDTO.getBody();
		String actionType = tagDTO.getActionType();
		String tokenId = requestDTO.getHeader().getTokenId();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			if("ADD".equals(actionType)){
				if(ContentConstants.TAG_TYPE_POSTER.equals(tagDTO.getTagType()))
					contentService.addPosterTag(tagDTO);
				else if(ContentConstants.TAG_TYPE_SCENE.equals(tagDTO.getTagType()))
					contentService.addSceneTag(tagDTO);
				else if(ContentConstants.TAG_TYPE_ARTICLE.equals(tagDTO.getTagType()))
					contentService.addArticleTag(tagDTO);
				else if(ContentConstants.TAG_TYPE_QA.equals(tagDTO.getTagType()))
					contentService.addQaTag(tagDTO);
			}else if("DELETE".equals(actionType)){
				if(ContentConstants.TAG_TYPE_POSTER.equals(tagDTO.getTagType()))
					contentService.deletePosterTag(tagDTO);
				else if(ContentConstants.TAG_TYPE_SCENE.equals(tagDTO.getTagType()))
					contentService.deleteSceneTag(tagDTO);
				else if(ContentConstants.TAG_TYPE_ARTICLE.equals(tagDTO.getTagType()))
					contentService.deleteArticleTag(tagDTO);
				else if(ContentConstants.TAG_TYPE_QA.equals(tagDTO.getTagType()))
					contentService.deleteQaTag(tagDTO);
			}
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}
		return resultDTO;
	}
	
	/**查询内容标签
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryTag", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryTag(@RequestBody RequestDTO<TagDTO> requestDTO) {

		TagDTO tagDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			List<TagDTO> tagDTOList = new ArrayList<TagDTO>();
			if(ContentConstants.TAG_TYPE_POSTER.equals(tagDTO.getTagType()))
				tagDTOList = contentService.getTagsByType(ContentConstants.TAG_TYPE_POSTER);
			else if(ContentConstants.TAG_TYPE_SCENE.equals(tagDTO.getTagType()))
				tagDTOList = contentService.getTagsByType(ContentConstants.TAG_TYPE_SCENE);
			else if(ContentConstants.TAG_TYPE_ARTICLE.equals(tagDTO.getTagType()))
				tagDTOList = contentService.getTagsByType(ContentConstants.TAG_TYPE_ARTICLE);
			else if(ContentConstants.TAG_TYPE_QA.equals(tagDTO.getTagType()))
				tagDTOList = contentService.getTagsByType(ContentConstants.TAG_TYPE_QA);
			TagDTOList list = new TagDTOList();
			list.setTags(tagDTOList);
			resultDTO.getBody().setData(list);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}
		return resultDTO;
	}
	
	/**增删改海报
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changePoster", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changePoster(@RequestBody RequestDTO<PosterDTO> requestDTO) {

		PosterDTO posterDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		String actionType = posterDTO.getActionType();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			if("ADD".equals(actionType)){
				contentService.addPoster(posterDTO);
			}else if("UPDATE".equals(actionType)){
				contentService.updatePoster(posterDTO);
			}else if("DELETE".equals(actionType)){
				contentService.deletePoster(posterDTO);
			}
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}
		return resultDTO;
	}
	
	/**增删改素材
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changeMateria", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeMateria(@RequestBody RequestDTO<MateriaDTO> requestDTO) {

		MateriaDTO materiaDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		String actionType = materiaDTO.getActionType();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			if("ADD".equals(actionType)){
				contentService.addMateria(materiaDTO);
			}else if("DELETE".equals(actionType)){
				contentService.deleteMateria(materiaDTO);
			}
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}
		return resultDTO;
	}
	
	/**增删改轮播图
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changeSwiper", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeSwiper(@RequestBody RequestDTO<SwiperDTO> requestDTO) {

		SwiperDTO swiperDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		String actionType = swiperDTO.getActionType();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			if("ADD".equals(actionType)){
				contentService.addSwiper(swiperDTO);
			}else if("MODIFY".equals(actionType)){
				contentService.updateSwiper(swiperDTO);
			}else if("DELETE".equals(actionType)){
				contentService.deleteSwiper(swiperDTO);
			}
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}
		return resultDTO;
	}
	
	/**查询广告
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryAd", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryAd(@RequestBody RequestDTO<AdDTO> requestDTO) {
		AdDTO adDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		String tokenId = requestDTO.getHeader().getTokenId();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			if(!StringUtils.isEmpty(adDTO.getAdUuid())){
				adDTO = contentService.getAdByUuid(adDTO.getAdUuid());
				resultDTO.getBody().setData(adDTO);
			}else if(!StringUtils.isEmpty(adDTO.getLocation())){
				List<AdDTO> adDTOList = contentService.getAdsByLocation(adDTO.getLocation());
				AdDTOList list = new AdDTOList();
				list.setAds(adDTOList);
				resultDTO.getBody().setData(list);
			}else if(adDTO.getProductCateDTO()!=null){
				List<AdDTO> adDTOList = contentService.getAdsByProductCate(adDTO.getProductCateDTO().getProductCateUuid());
				AdDTOList list = new AdDTOList();
				list.setAds(adDTOList);
				resultDTO.getBody().setData(list);
			}else{
				List<AdDTO> adDTOList = contentService.getAllAds();
				AdDTOList list = new AdDTOList();
				list.setAds(adDTOList);
				resultDTO.getBody().setData(list);
			}
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}
		return resultDTO;
	}
	
	/**增删改广告
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changeAd", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeAd(@RequestBody RequestDTO<AdDTO> requestDTO) {

		AdDTO adDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		String actionType = adDTO.getActionType();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			if("ADD".equals(actionType)){
				contentService.addAd(adDTO);
			}else if("MODIFY".equals(actionType)){
				contentService.updateAd(adDTO);
			}else if("DELETE".equals(actionType)){
				contentService.deleteAd(adDTO);
			}else if("ENABLE".equals(actionType)){
				contentService.enableAd(adDTO);
			}else if("DISABLE".equals(actionType)){
				contentService.disableAd(adDTO);
			}
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}
		return resultDTO;
	}
	
	/**查询场景
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryPoster", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryPoster(@RequestBody RequestDTO<PosterDTO> requestDTO) {

		PosterDTO posterDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			if(posterDTO.getTagDTO() != null && !StringUtils.isEmpty(posterDTO.getTagDTO().getTagName())){
				List<PosterDTO> posterDTOList = contentService.getPostersByTagName(posterDTO.getTagDTO().getTagName());
				PosterDTOList list = new PosterDTOList();
				list.setPosters(posterDTOList);
				resultDTO.getBody().setData(list);
			}else{
				List<PosterDTO> posterDTOList = contentService.getPosters();
				PosterDTOList list = new PosterDTOList();
				list.setPosters(posterDTOList);
				resultDTO.getBody().setData(list);
			}
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}
		return resultDTO;
	}
	
	/**查询素材
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryMateria", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryMateria(@RequestBody RequestDTO<MateriaDTO> requestDTO) {

		MateriaDTO materiaDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			if(!StringUtils.isEmpty(materiaDTO.getType())){
				List<MateriaDTO> materiaDTOList = contentService.getMateriasByType(materiaDTO.getType());
				MateriaDTOList list = new MateriaDTOList();
				list.setMaterias(materiaDTOList);
				resultDTO.getBody().setData(list);
			}else{
				List<MateriaDTO> materiaDTOList = contentService.getMaterias();
				MateriaDTOList list = new MateriaDTOList();
				list.setMaterias(materiaDTOList);
				resultDTO.getBody().setData(list);
			}
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}
		return resultDTO;
	}
	
	/**查询轮播图
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquirySwiper", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquirySwiper(@RequestBody RequestDTO<SwiperDTO> requestDTO) {
		SwiperDTO swiperDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!StringUtils.isEmpty(swiperDTO.getSwiperUuid())){
				swiperDTO = contentService.getSwiperByUuid(swiperDTO.getSwiperUuid());
				resultDTO.getBody().setData(swiperDTO);
			}else{
				List<SwiperDTO> swiperDTOList = contentService.getSwipers();
				SwiperDTOList list = new SwiperDTOList();
				list.setSwipers(swiperDTOList);
				resultDTO.getBody().setData(list);
			}
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}
		return resultDTO;
	}
	
	/**增删改场景
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changeScene", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeScene(@RequestBody RequestDTO<SceneDTO> requestDTO) {

		SceneDTO sceneDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		String actionType = sceneDTO.getActionType();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			if("ADD".equals(actionType)){
				contentService.addScene(sceneDTO);
			}else if("MODIFY".equals(actionType)){
				contentService.updateScene(sceneDTO);
			}else if("DELETE".equals(actionType)){
				contentService.deleteScene(sceneDTO);
			}
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}
		return resultDTO;
	}
	
	/**查询场景
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryScene", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryScene(@RequestBody RequestDTO<SceneDTO> requestDTO) {

		SceneDTO sceneDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			if(!StringUtils.isEmpty(sceneDTO.getSceneUuid())){
				sceneDTO = contentService.getSceneByUuid(sceneDTO.getSceneUuid());
				resultDTO.getBody().setData(sceneDTO);
			}
			else if(sceneDTO.getTagDTO() != null && !StringUtils.isEmpty(sceneDTO.getTagDTO().getTagName())){
				List<SceneDTO> sceneDTOList = contentService.getScenesByTagName(sceneDTO.getTagDTO().getTagName());
				SceneDTOList list = new SceneDTOList();
				list.setScenes(sceneDTOList);
				resultDTO.getBody().setData(list);
			}else{
				List<SceneDTO> sceneDTOList = contentService.getScenes();
				SceneDTOList list = new SceneDTOList();
				list.setScenes(sceneDTOList);
				resultDTO.getBody().setData(list);	
			}
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}
		return resultDTO;
	}
	
	/**增删改文案
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changeArticle", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeArticle(@RequestBody RequestDTO<ArticleDTO> requestDTO) {

		ArticleDTO articleDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		String actionType = articleDTO.getActionType();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			if("ADD".equals(actionType)){
				contentService.addArticle(articleDTO);
			}else if("PUBLISH".equals(actionType)){
				contentService.publishArticle(articleDTO);
			}else if("MODIFY".equals(actionType)){
				contentService.updateArticle(articleDTO);
			}else if("DELETE".equals(actionType)){
				articleDTO = contentService.getArticleByUuid(articleDTO.getArticleUuid());
				String articleType = articleDTO.getArticleType();
				contentService.deleteArticle(articleDTO);
			}
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}
		return resultDTO;
	}
	
	/**增删改常见问题
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changeQa", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeQa(@RequestBody RequestDTO<QaDTO> requestDTO) {

		QaDTO qaDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		String actionType = qaDTO.getActionType();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			if("ADD".equals(actionType)){
				contentService.addQa(qaDTO);
			}else if("PUBLISH".equals(actionType)){
				contentService.publishQa(qaDTO);
			}else if("MODIFY".equals(actionType)){
				contentService.updateQa(qaDTO);
			}else if("DELETE".equals(actionType)){
				qaDTO = contentService.getQaByUuid(qaDTO.getQaUuid());
				contentService.deleteQa(qaDTO);
			}else if("HOT".equals(actionType)){
				contentService.setQaHot(qaDTO);
			}else if("UNHOT".equals(actionType)){
				contentService.setQaNonHot(qaDTO);
			}
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}
		return resultDTO;
	}
	
	/**增删改朋友圈文案
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changeShare", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeShare(@RequestBody RequestDTO<ShareDTO> requestDTO) {

		ShareDTO shareDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		String actionType = shareDTO.getActionType();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			if("ADD".equals(actionType)){
				shareService.addShare(shareDTO);
			}else if("PUBLISH".equals(actionType)){
				shareService.publishShare(shareDTO);
			}else if("MODIFY".equals(actionType)){
				shareService.updateShare(shareDTO);
			}else if("DELETE".equals(actionType)){
				shareService.deleteShare(shareDTO);
			}
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}
		return resultDTO;
	}
	
	/**批量删除文案
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/delMultipleArticles", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO delMultipleArticles(@RequestBody RequestDTO<ArticleDTOList> requestDTO) {

		ArticleDTOList articleDTOList = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			contentService.deleteMultipleArticles(articleDTOList);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}
		return resultDTO;
	}
	
	/**批量删除常见问题
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/delMultipleQa", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO delMultipleQa(@RequestBody RequestDTO<QaDTOList> requestDTO) {

		QaDTOList qaDTOList = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			contentService.deleteMultipleQas(qaDTOList);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}
		return resultDTO;
	}
	
	/**查询文案
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryArticle", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryArticle(@RequestBody RequestDTO<ArticleDTO> requestDTO) {

		ArticleDTO articleDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			if(!StringUtils.isEmpty(articleDTO.getArticleUuid())){
				articleDTO = contentService.getArticleByUuid(articleDTO.getArticleUuid());
				resultDTO.getBody().setData(articleDTO);
			}
			else if(articleDTO.getTagDTO() != null && !StringUtils.isEmpty(articleDTO.getTagDTO().getTagName())){
				List<ArticleDTO> articleDTOList = contentService.getArticlesByTagName(articleDTO.getTagDTO().getTagName());
				ArticleDTOList list = new ArticleDTOList();
				list.setArticles(articleDTOList);
				resultDTO.getBody().setData(list);
			}else{
				List<ArticleDTO> articleDTOList = contentService.getArticles();
				ArticleDTOList list = new ArticleDTOList();
				list.setArticles(articleDTOList);
				resultDTO.getBody().setData(list);
			}
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}
		return resultDTO;
	}
	
	/**查询常见问题
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryQa", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryQa(@RequestBody RequestDTO<QaDTO> requestDTO) {

		QaDTO qaDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			if(!StringUtils.isEmpty(qaDTO.getQaUuid())){
				qaDTO = contentService.getQaByUuid(qaDTO.getQaUuid());
				resultDTO.getBody().setData(qaDTO);
			}
			else if(qaDTO.getTagDTO() != null && !StringUtils.isEmpty(qaDTO.getTagDTO().getTagName())){
				List<QaDTO> qaDTOList = contentService.getQasByTagName(qaDTO.getTagDTO().getTagName());
				QaDTOList list = new QaDTOList();
				list.setQas(qaDTOList);
				resultDTO.getBody().setData(list);
			}else{
				List<QaDTO> qaDTOList = contentService.getQas();
				QaDTOList list = new QaDTOList();
				list.setQas(qaDTOList);
				resultDTO.getBody().setData(list);
			}
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}
		return resultDTO;
	}
	
	/**查询朋友圈文案
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryShare", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryShare(@RequestBody RequestDTO<ShareDTO> requestDTO) {

		ShareDTO shareDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			if(!StringUtils.isEmpty(shareDTO.getShareUuid())){
				shareDTO = shareService.getShareByUuid(shareDTO.getShareUuid());
				resultDTO.getBody().setData(shareDTO);
			}
			else if(shareDTO.getTagDTO() != null && !StringUtils.isEmpty(shareDTO.getTagDTO().getTagName())){
				List<ShareDTO> shareDTOList = shareService.getSharesByTagName(shareDTO.getTagDTO().getTagName());
				ShareDTOList list = new ShareDTOList();
				list.setShares(shareDTOList);
				resultDTO.getBody().setData(list);
			}else{
				List<ShareDTO> shareDTOList = shareService.getShares();
				ShareDTOList list = new ShareDTOList();
				list.setShares(shareDTOList);
				resultDTO.getBody().setData(list);
			}
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}
		return resultDTO;
	}
	
	/**根据日期查询朋友圈文案
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryShareByDate", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryShareByDate(@RequestBody RequestDTO<ShareDTO> requestDTO) {

		ShareDTO shareDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			List<ShareDTO> shareDTOList = shareService.getSharesByCreateDate(shareDTO.getSearchDate());
			ShareDTOList list = new ShareDTOList();
			list.setShares(shareDTOList);
			resultDTO.getBody().setData(list);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}
		return resultDTO;
	}
	
	/**分页查询文案
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryArticlePagination", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryArticlePagination(@RequestBody RequestDTO<ArticleDTO> requestDTO) {

		ArticleDTO articleDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			if(articleDTO.getTagDTO() != null && !StringUtils.isEmpty(articleDTO.getTagDTO().getTagName())){
				String tagName = articleDTO.getTagDTO().getTagName();
				int totalCount = contentService.getArticleCountByTagName(tagName);
				int startIndex = articleDTO.getStartIndex();
				int pageSize = articleDTO.getPageSize();
				List<ArticleDTO> articleDTOList = contentService.getArticlesByTagNamePage(tagName, startIndex, pageSize);
				ArticleDTOList list = new ArticleDTOList();
				list.setArticles(articleDTOList);
				list.setTotalCount(totalCount);
				resultDTO.getBody().setData(list);
			}else if(!StringUtils.isEmpty(articleDTO.getArticleType())){
				String acticleType = articleDTO.getArticleType();
				int totalCount = contentService.getArticleCountByType(articleDTO.getArticleType());
				int startIndex = articleDTO.getStartIndex();
				int pageSize = articleDTO.getPageSize();
				List<ArticleDTO> articleDTOList = contentService.getArticlesByTypePage(acticleType, startIndex, pageSize);
				ArticleDTOList list = new ArticleDTOList();
				list.setArticles(articleDTOList);
				list.setTotalCount(totalCount);
				resultDTO.getBody().setData(list);
			}else if(articleDTO.getArticleTypeArray()!=null && articleDTO.getArticleTypeArray().length>0){
				List<String> acticleTypes = Arrays.asList(articleDTO.getArticleTypeArray());
				int totalCount = contentService.getArticleCountByTypes(acticleTypes);
				int startIndex = articleDTO.getStartIndex();
				int pageSize = articleDTO.getPageSize();
				List<ArticleDTO> articleDTOList = contentService.getArticlesByTypesPage(acticleTypes, startIndex, pageSize);
				ArticleDTOList list = new ArticleDTOList();
				list.setArticles(articleDTOList);
				list.setTotalCount(totalCount);
				resultDTO.getBody().setData(list);
			}else{
				int totalCount = contentService.getArticleCount();
				int startIndex = articleDTO.getStartIndex();
				int pageSize = articleDTO.getPageSize();
				List<ArticleDTO> articleDTOList = contentService.getArticlesByPage(startIndex, pageSize);
				ArticleDTOList list = new ArticleDTOList();
				list.setArticles(articleDTOList);
				list.setTotalCount(totalCount);
				resultDTO.getBody().setData(list);
			}
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}
		return resultDTO;
	}
	
	/**分页查询常见问题
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryQaPagination", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryQaPagination(@RequestBody RequestDTO<QaDTO> requestDTO) {

		QaDTO qaDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			if(qaDTO.getTagDTO() != null && !StringUtils.isEmpty(qaDTO.getTagDTO().getTagName())){
				String tagName = qaDTO.getTagDTO().getTagName();
				int totalCount = contentService.getQaCountByTagName(tagName);
				int startIndex = qaDTO.getStartIndex();
				int pageSize = qaDTO.getPageSize();
				List<QaDTO> qaDTOList = contentService.getQasByTagNamePage(tagName, startIndex, pageSize);
				QaDTOList list = new QaDTOList();
				list.setQas(qaDTOList);
				list.setTotalCount(totalCount);
				resultDTO.getBody().setData(list);
			}else{
				int totalCount = contentService.getQaCount();
				int startIndex = qaDTO.getStartIndex();
				int pageSize = qaDTO.getPageSize();
				List<QaDTO> qaDTOList = contentService.getQasByPage(startIndex, pageSize);
				QaDTOList list = new QaDTOList();
				list.setQas(qaDTOList);
				list.setTotalCount(totalCount);
				resultDTO.getBody().setData(list);
			}
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}
		return resultDTO;
	}
	
	}
