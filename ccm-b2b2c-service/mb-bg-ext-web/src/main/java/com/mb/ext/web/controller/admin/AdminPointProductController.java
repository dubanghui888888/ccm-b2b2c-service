package com.mb.ext.web.controller.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import com.mb.ext.core.constant.LogConstants;
import com.mb.ext.core.context.UserContext;
import com.mb.ext.core.service.AdminService;
import com.mb.ext.core.service.LogService;
import com.mb.ext.core.service.PointService;
import com.mb.ext.core.service.spec.AdminDTO;
import com.mb.ext.core.service.spec.ProductCommentSearchDTO;
import com.mb.ext.core.service.spec.ProductSearchDTO;
import com.mb.ext.core.service.spec.RequestDTO;
import com.mb.ext.core.service.spec.ResultDTO;
import com.mb.ext.core.service.spec.point.PointProductCateDTO;
import com.mb.ext.core.service.spec.point.PointProductCateDTOList;
import com.mb.ext.core.service.spec.point.PointProductCommentDTO;
import com.mb.ext.core.service.spec.point.PointProductCommentDTOList;
import com.mb.ext.core.service.spec.point.PointProductDTO;
import com.mb.ext.core.service.spec.point.PointProductDTOList;
import com.mb.ext.web.util.MessageHelper;
import com.mb.ext.web.util.TokenCheckUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;

/**后台商品管理类
 * @author 社区团购
 *
 */
@Controller
public class AdminPointProductController {

	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	
	@Autowired
	private AdminService adminService;

	@Autowired
	private PropertyRepository propertyRepository;
	
	@Autowired
	private LogService logService;
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PointService pointService;
	
	@Autowired
	private TokenCheckUtil tokenCheckUtil;

	/**查询商品类目
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryPointProductCate", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryProductCate(@RequestBody RequestDTO<PointProductCateDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		PointProductCateDTO productCateDTO = requestDTO.getBody();
		String actionType = productCateDTO.getActionType();
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
			if("SINGLE".equals(actionType)){
				productCateDTO = pointService.getProductCateByUuid(productCateDTO.getProductCateUuid());
				resultDTO.getBody().setData(productCateDTO);
			}else if("ROOT".equals(actionType)){
				List<PointProductCateDTO> productCateDTOList = pointService.getRootProductCate();
				PointProductCateDTOList list = new PointProductCateDTOList();
				list.setCates(productCateDTOList);
				resultDTO.getBody().setData(list);
			}else if("LEAF".equals(actionType)){
				List<PointProductCateDTO> productCateDTOList = pointService.getLeafProductCate();
				PointProductCateDTOList list = new PointProductCateDTOList();
				list.setCates(productCateDTOList);
				resultDTO.getBody().setData(list);
			}else if("CHILD".equals(actionType)){
				List<PointProductCateDTO> productCateDTOList = pointService.getChildProductCate(productCateDTO);
				PointProductCateDTOList list = new PointProductCateDTOList();
				list.setCates(productCateDTOList);
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
	
	/**增删改商品类目
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changePointProductCate", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeProductCate(@RequestBody RequestDTO<PointProductCateDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		PointProductCateDTO productCateDTO = requestDTO.getBody();
		String actionType = productCateDTO.getActionType();
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
				pointService.addProductCate(productCateDTO);
			}else if("MODIFY".equals(actionType)){
				pointService.updateProductCate(productCateDTO);
			}else if("DELETE".equals(actionType)){
				pointService.deleteProductCate(productCateDTO);
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
	
	
	/**增删改商品
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/changePointProduct", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeProduct(@RequestBody RequestDTO<PointProductDTO> requestDTO) {

		PointProductDTO productDTO = requestDTO.getBody();
		String actionType = productDTO.getActionType();
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
				try{
					logService.addSysLog(LogConstants.LOGCATEGORY_PRODUCT, adminDTO.getId()+"在"+new Date()+"修改了商品:"+productDTO.getProductName());
				}catch(Exception e){
					logger.error("记录系统日志错误:"+e.getMessage());
				}
			}
			
			if("ADD".equals(actionType))	
				pointService.addProduct(productDTO);
			else if("MODIFY".equals(actionType))	
				pointService.updateProduct(productDTO);
			else if("DELETE".equals(actionType))
				pointService.deleteProduct(productDTO);
			else if("ENABLE".equals(actionType))	//上架
				pointService.enableProduct(productDTO);
			else if("DISABLE".equals(actionType))	//下架
				pointService.disableProduct(productDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	
	/**查询商品
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryPointProduct", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryProduct(@RequestBody RequestDTO<PointProductDTO> requestDTO) {

		PointProductDTO productDTO = requestDTO.getBody();
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
			
			if(!StringUtils.isEmpty(productDTO.getProductUuid())){
				productDTO = pointService.getProductByUuid(productDTO.getProductUuid());
				resultDTO.getBody().setData(productDTO);
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
	
	/** 根据多个商品ID查出商品详情
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryMultiplePointProduct", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryMultipleProduct(@RequestBody RequestDTO<PointProductDTOList> requestDTO) {

		PointProductDTOList productDTOList = requestDTO.getBody();
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
			List<PointProductDTO> resultList = new ArrayList<PointProductDTO>();
			List<PointProductDTO> productList = productDTOList.getProducts();
			for (Iterator<PointProductDTO> iterator = productList.iterator(); iterator.hasNext();) {
				PointProductDTO productDTO = iterator.next();
				productDTO = pointService.getProductByUuid(productDTO.getProductUuid());
				resultList.add(productDTO);
			}
			productDTOList.setProducts(resultList);
			resultDTO.getBody().setData(productDTOList);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**分页查询商品
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/searchPointProduct", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchProduct(@RequestBody RequestDTO<ProductSearchDTO> requestDTO) {

		ProductSearchDTO productSearchDTO = requestDTO.getBody();
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
			List<PointProductDTO> dtoList = pointService.searchProduct(productSearchDTO);
			int total = pointService.searchProductTotal(productSearchDTO);
			PointProductDTOList list = new PointProductDTOList();
			list.setProducts(dtoList);
			list.setTotal(total);
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

	
	
	/**回复商品评论
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/replayPointProductComment", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO replayProductComment(@RequestBody RequestDTO<PointProductCommentDTO> requestDTO){
		PointProductCommentDTO productCommentDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		String actionType = productCommentDTO.getActionType();
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
			if("ADD".equals(actionType))	//回复评论
				pointService.replayProductComment(productCommentDTO);
			else if("MODIFY".equals(actionType))	//修改回复评论
				pointService.updateProductCommentReply(productCommentDTO);
			else if("DELETE".equals(actionType))	//删除评论
				pointService.deleteProductComment(productCommentDTO);
			else if("SHOW".equals(actionType))	//删除评论
				pointService.setProductCommentShowStatus(productCommentDTO,true);
			else if("HIDE".equals(actionType))	//删除评论
				pointService.setProductCommentShowStatus(productCommentDTO,false);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**查询商品评论
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryPointProductComment", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryProductComment(@RequestBody RequestDTO<PointProductDTO> requestDTO){
		PointProductDTO productDTO = requestDTO.getBody();
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
			List<PointProductCommentDTO> dtoList = pointService.getProductComments(productDTO);
			int total = pointService.getProductCommentTotal(productDTO);
			PointProductCommentDTOList list = new PointProductCommentDTOList();
			list.setCommentList(dtoList);
			list.setTotal(total);
			
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
	
	/**分页查询商品评论
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/searchPointProductComment", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchProductComment(@RequestBody RequestDTO<ProductCommentSearchDTO> requestDTO){
		ProductCommentSearchDTO searchDTO = requestDTO.getBody();
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
			
			List<PointProductCommentDTO> dtoList = pointService.searchProductComment(searchDTO,false);
			int total = pointService.searchProductCommentTotal(searchDTO,false);
			PointProductCommentDTOList list = new PointProductCommentDTOList();
			list.setCommentList(dtoList);
			list.setTotal(total);
			
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
	
	
}
