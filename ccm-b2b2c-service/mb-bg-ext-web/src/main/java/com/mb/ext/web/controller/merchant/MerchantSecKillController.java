package com.mb.ext.web.controller.merchant;

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
import com.mb.ext.core.context.UserContext;
import com.mb.ext.core.service.AdminService;
import com.mb.ext.core.service.MerchantService;
import com.mb.ext.core.service.ProductService;
import com.mb.ext.core.service.SecKillService;
import com.mb.ext.core.service.spec.ProductSearchDTO;
import com.mb.ext.core.service.spec.RequestDTO;
import com.mb.ext.core.service.spec.ResultDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.seckill.SecKillProductDTO;
import com.mb.ext.core.service.spec.seckill.SecKillProductDTOList;
import com.mb.ext.web.util.MessageHelper;
import com.mb.ext.web.util.TokenCheckUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;

/**商家秒杀处理类
 * @author B2B2C商城
 *
 */
@Controller
public class MerchantSecKillController {

	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	
	@Autowired
	private AdminService adminService;

	@Autowired
	private PropertyRepository propertyRepository;
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private SecKillService secKillService;
	
	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private TokenCheckUtil tokenCheckUtil;
	
	/**查询秒杀商品
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/inquirySecKill", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquirySecKill(@RequestBody RequestDTO<SecKillProductDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		SecKillProductDTO secKillProductDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkMerchantToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				MerchantDTO nMerchantDTO = merchantService.getMerchantDTOByTokenId(tokenId);
				merchantService.setMerchantProfile(nMerchantDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_MERCHANT);
			}
			if(!StringUtils.isEmpty(secKillProductDTO.getSecKillProductUuid())){
				secKillProductDTO = secKillService.getSecKill(secKillProductDTO.getSecKillProductUuid());
				resultDTO.getBody().setData(secKillProductDTO);
			}else if(secKillProductDTO.getMerchantDTO() != null) {
				List<SecKillProductDTO> secKillDTOList = secKillService.getSecKillsByMerchant(secKillProductDTO.getMerchantDTO());
				SecKillProductDTOList list = new SecKillProductDTOList();
				list.setSecKills(secKillDTOList);
				resultDTO.getBody().setData(list);
			}else{
				List<SecKillProductDTO> secKillDTOList = secKillService.getSecKills();
				SecKillProductDTOList list = new SecKillProductDTOList();
				list.setSecKills(secKillDTOList);
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
	
	/**分页搜索秒杀商品
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/searchSecKill", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchSecKill(@RequestBody RequestDTO<ProductSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		ProductSearchDTO productSearchDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkMerchantToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				MerchantDTO nMerchantDTO = merchantService.getMerchantDTOByTokenId(tokenId);
				merchantService.setMerchantProfile(nMerchantDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_MERCHANT);
			}
			List<SecKillProductDTO> secKillDTOList = secKillService.searchSecKill(productSearchDTO);
			int total = secKillService.searchSecKillTotal(productSearchDTO);
			SecKillProductDTOList list = new SecKillProductDTOList();
			list.setSecKills(secKillDTOList);
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

	/**添加/修改/删除/上线/下线秒杀商品
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/changeSecKill", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO changeSecKill(@RequestBody RequestDTO<SecKillProductDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		SecKillProductDTO secKillProductDTO = requestDTO.getBody();
		String actionType = secKillProductDTO.getActionType();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkMerchantToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				MerchantDTO nMerchantDTO = merchantService.getMerchantDTOByTokenId(tokenId);
				merchantService.setMerchantProfile(nMerchantDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_MERCHANT);
			}
			if("ADD".equals(actionType)){
				secKillService.addSecKill(secKillProductDTO);
			}else if("MODIFY".equals(actionType)){
				secKillService.updateSecKill(secKillProductDTO);
			}else if("DELETE".equals(actionType)){
				secKillService.deleteSecKill(secKillProductDTO);
			}else if("DISABLE".equals(actionType)){
				secKillService.disableSecKill(secKillProductDTO);
			}else if("ENABLE".equals(actionType)){
				secKillService.enableSecKill(secKillProductDTO);
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
