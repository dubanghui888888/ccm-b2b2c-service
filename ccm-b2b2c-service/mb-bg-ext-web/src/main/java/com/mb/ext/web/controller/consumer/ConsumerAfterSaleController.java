package com.mb.ext.web.controller.consumer;

import java.util.List;

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
import com.mb.ext.core.service.FundService;
import com.mb.ext.core.service.OrderAfterSaleService;
import com.mb.ext.core.service.OrderService;
import com.mb.ext.core.service.ProductService;
import com.mb.ext.core.service.ShoppingCartService;
import com.mb.ext.core.service.UserService;
import com.mb.ext.core.service.spec.OrderAfterSaleSearchDTO;
import com.mb.ext.core.service.spec.RequestDTO;
import com.mb.ext.core.service.spec.ResultDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.order.OrderAfterSaleDTO;
import com.mb.ext.core.service.spec.order.OrderAfterSaleDTOList;
import com.mb.ext.web.util.MessageHelper;
import com.mb.ext.web.util.TokenCheckUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;

/**会员售后类
 * @author B2B2C商城
 *
 */
@Controller
public class ConsumerAfterSaleController {

	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FundService fundService;

	@Autowired
	private PropertyRepository propertyRepository;
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderAfterSaleService orderAfterSaleService;
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private TokenCheckUtil tokenCheckUtil;

	/**申请订单售后
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/applyOrderAfterSale", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO applyOrderAfterSale(@RequestBody RequestDTO<OrderAfterSaleDTO> requestDTO) {
		OrderAfterSaleDTO afterSaleDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
			}
			orderAfterSaleService.applyOrderAfterSale(afterSaleDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**撤销订单售后
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/cancelOrderAfterSale", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO cancelOrderAfterSale(@RequestBody RequestDTO<OrderAfterSaleDTO> requestDTO) {
		OrderAfterSaleDTO afterSaleDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
			}
			orderAfterSaleService.cancelOrderAfterSale(afterSaleDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**更新订单售后
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/updateOrderAfterSale", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO updateOrderAfterSale(@RequestBody RequestDTO<OrderAfterSaleDTO> requestDTO) {
		OrderAfterSaleDTO afterSaleDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
			}
			orderAfterSaleService.updateOrderAfterSale(afterSaleDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**退货订单售后(已邮寄)
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/courierOrderAfterSale", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO courierOrderAfterSale(@RequestBody RequestDTO<OrderAfterSaleDTO> requestDTO) {
		OrderAfterSaleDTO afterSaleDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
			}
			orderAfterSaleService.courierOrderAfterSale(afterSaleDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**查询售后单详情
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/inquiryOrderAfterSaleDetail", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryOrderAfterSale(@RequestBody RequestDTO<OrderAfterSaleDTO> requestDTO) {
		OrderAfterSaleDTO afterSaleDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
			}
			afterSaleDTO = orderAfterSaleService.getOrderAfterSaleByAfterSaleNo(afterSaleDTO.getSaleNo());
			resultDTO.getBody().setData(afterSaleDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
			
		}

		return resultDTO;
	}
	
	/**分页查询售后单列表
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/searchOrderAfterSale", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchOrderAfterSale(@RequestBody RequestDTO<OrderAfterSaleSearchDTO> requestDTO) {
		OrderAfterSaleSearchDTO searchDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
			}
			List<OrderAfterSaleDTO> afterSaleDTOList = orderAfterSaleService.searchOrderAfterSale(searchDTO);
			int total = orderAfterSaleService.searchOrderAfterSaleTotal(searchDTO);
			OrderAfterSaleDTOList list = new OrderAfterSaleDTOList();
			list.setTotal(total);
			list.setAfterSaleList(afterSaleDTOList);
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
	
	/**查询售后单列表数量
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/consumer/searchOrderAfterSaleTotal", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchOrderAfterSaleTotal(@RequestBody RequestDTO<OrderAfterSaleSearchDTO> requestDTO) {
		OrderAfterSaleSearchDTO searchDTO = requestDTO.getBody();
		String tokenId = requestDTO.getHeader().getTokenId();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if(!tokenCheckUtil.checkToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				UserDTO nUserDTO = userService.getUserDTOByTokenId(tokenId);
				userService.setUserProfile(nUserDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_USER);
			}
			int total = orderAfterSaleService.searchOrderAfterSaleTotal(searchDTO);
			OrderAfterSaleDTOList list = new OrderAfterSaleDTOList();
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
