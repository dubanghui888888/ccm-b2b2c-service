package com.mb.ext.web.controller.merchant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.mb.ext.core.constant.OrderConstants;
import com.mb.ext.core.context.UserContext;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.LogService;
import com.mb.ext.core.service.MerchantService;
import com.mb.ext.core.service.OrderService;
import com.mb.ext.core.service.PrintService;
import com.mb.ext.core.service.ProductService;
import com.mb.ext.core.service.SettingService;
import com.mb.ext.core.service.UserService;
import com.mb.ext.core.service.spec.AdminDTO;
import com.mb.ext.core.service.spec.OrderSearchDTO;
import com.mb.ext.core.service.spec.PrintSettingDTO;
import com.mb.ext.core.service.spec.RequestDTO;
import com.mb.ext.core.service.spec.ResultDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.order.CourierDTO;
import com.mb.ext.core.service.spec.order.CourierResultDTO;
import com.mb.ext.core.service.spec.order.OrderDTO;
import com.mb.ext.core.service.spec.order.OrderDTOList;
import com.mb.ext.web.util.MessageHelper;
import com.mb.ext.web.util.TokenCheckUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;

/**商家订单处理类
 * @author B2B2C商城
 *
 */
@Controller
public class MerchantOrderController {

	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	
	@Autowired
	private UserService userService;

	@Autowired
	private PropertyRepository propertyRepository;
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private LogService logService;
	
	@Autowired
	private PrintService printService;
	
	@Autowired
	private SettingService settingService;
	
	@Autowired
	private TokenCheckUtil tokenCheckUtil;

	/**查询订单
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/inquiryOrder", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryOrder(@RequestBody RequestDTO<OrderDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		OrderDTO orderDTO = requestDTO.getBody();
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
			if(!StringUtils.isEmpty(orderDTO.getOrderNo())){
				orderDTO = orderService.getOrderByOrderNo(orderDTO.getOrderNo());
				resultDTO.getBody().setData(orderDTO);
			}else{
				List<OrderDTO> orderDTOList = orderService.getOrderByMerchant(orderDTO.getMerchantDTO());
				OrderDTOList list = new OrderDTOList();
				list.setOrders(orderDTOList);
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
	
	/**分页查询订单
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/searchOrder", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchOrder(@RequestBody RequestDTO<OrderSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		OrderSearchDTO orderSearchDTO = requestDTO.getBody();
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

			List<OrderDTO> orderDTOList = orderService.searchOrders(orderSearchDTO, orderSearchDTO.getStartIndex(),
					orderSearchDTO.getPageSize());
			OrderDTOList list = new OrderDTOList();
			list.setOrders(orderDTOList);
			int total = orderService.searchOrderTotal(orderSearchDTO);
			int totalPoint = orderService.searchOrderTotalPoint(orderSearchDTO);
			BigDecimal totalAmount = orderService.searchOrderTotalAmount(orderSearchDTO);
			list.setTotal(total);
			list.setTotalPoint(totalPoint);
			list.setTotalAmount(totalAmount);
			
			String[] keyArray = orderSearchDTO.getKeyArray();
			List<String> keyList = new ArrayList<String>(Arrays.asList(orderSearchDTO.getKeyArray()));
			if(!keyList.contains(OrderSearchDTO.KEY_ORDERSTATUS)) {
				keyList.add(OrderSearchDTO.KEY_ORDERSTATUS);
				keyArray = keyList.toArray(keyArray);
				orderSearchDTO.setKeyArray(keyArray);
			}
			//待付款订单数量
			orderSearchDTO.setOrderStatus(OrderConstants.ORDER_STATUS_NOT_PAYED);
			int totalToPay = orderService.searchOrderTotal(orderSearchDTO);
			list.setTotalToPay(totalToPay);
			//待发货订单数量
			orderSearchDTO.setOrderStatus(OrderConstants.ORDER_STATUS_NOT_DELIVERIED);
			int totalToDeliver = orderService.searchOrderTotal(orderSearchDTO);
			list.setTotalToDeliver(totalToDeliver);
			//待收货订单数量
			orderSearchDTO.setOrderStatus(OrderConstants.ORDER_STATUS_DELIVERIED);
			int totalToConfirm = orderService.searchOrderTotal(orderSearchDTO);
			list.setTotalToConfirm(totalToConfirm);
			//待评价订单数量
			orderSearchDTO.setOrderStatus(OrderConstants.ORDER_STATUS_COMPLETED);
			int totalToEvaluate = orderService.searchOrderTotal(orderSearchDTO);
			list.setTotalToEvaluate(totalToEvaluate);
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
	
	/**查询订单数量
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/searchOrderTotal", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchOrderTotal(@RequestBody RequestDTO<OrderSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		OrderSearchDTO orderSearchDTO = requestDTO.getBody();
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
			OrderDTOList list = new OrderDTOList();
			int total = orderService.searchOrderTotal(orderSearchDTO);
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


	/**取消订单
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/cancelOrder", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO cancelOrder(@RequestBody RequestDTO<OrderDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		OrderDTO orderDTO = requestDTO.getBody();
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
			orderService.cancelOrder(orderDTO);
			orderDTO = orderService.getOrderByOrderUuid(orderDTO.getOrderUuid());
			try {
				logService.addSysLog(LogConstants.LOGCATEGORY_ORDER,
						"取消订单" + "(" + orderDTO.getOrderNo() + ")");
			} catch (Exception e) {
				logger.error("记录系统日志错误:" + e.getMessage());
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
	
	/**支付订单
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/payOrder", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO payOrder(@RequestBody RequestDTO<OrderDTOList> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		OrderDTOList orderDTOList = requestDTO.getBody();
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
			List<OrderDTO> list = orderDTOList.getOrders();
			for (Iterator<OrderDTO> iterator = list.iterator(); iterator.hasNext();) {
				OrderDTO orderDTO = iterator.next();
				orderService.payOrder(orderDTO);
				orderDTO = orderService.getOrderByOrderNo(orderDTO.getOrderNo());
				try {
					logService.addSysLog(LogConstants.LOGCATEGORY_ORDER,
							"支付订单" + "(" + orderDTO.getOrderNo() + ")");
				} catch (Exception e) {
					logger.error("记录系统日志错误:" + e.getMessage());
				}
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
	
	/**备注订单
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/commentOrder", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO commentOrder(@RequestBody RequestDTO<OrderDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		OrderDTO orderDTO = requestDTO.getBody();
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
			orderService.commentOrder(orderDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);

		} catch (Exception e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(BusinessErrorCode.COMMON_SYSTEM_ERROR);
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, BusinessErrorCode.COMMON_SYSTEM_ERROR);
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);

		}

		return resultDTO;
	}
	
	/**编辑订单(收货人地址及总金额)
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/editOrder", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO editOrder(@RequestBody RequestDTO<OrderDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		OrderDTO orderDTO = requestDTO.getBody();
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
			orderService.editOrder(orderDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);

		} catch (Exception e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(BusinessErrorCode.COMMON_SYSTEM_ERROR);
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, BusinessErrorCode.COMMON_SYSTEM_ERROR);
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);

		}

		return resultDTO;
	}
	
	/** 订单发货
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/deliverOrder", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO deliverOrder(@RequestBody RequestDTO<OrderDTOList> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		OrderDTOList orderDTOList = requestDTO.getBody();
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
			List<OrderDTO> list = orderDTOList.getOrders();
			for (Iterator<OrderDTO> iterator = list.iterator(); iterator.hasNext();) {
				OrderDTO orderDTO = iterator.next();
				orderService.deliverOrder(orderDTO);
			}
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);

		} catch (Exception e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(BusinessErrorCode.COMMON_SYSTEM_ERROR);
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, BusinessErrorCode.COMMON_SYSTEM_ERROR);
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);

		}

		return resultDTO;
	}
	/**获取商家打印机设置
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/getPrintSetting", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO getPrintSetting(@RequestBody RequestDTO<MerchantDTO> requestDTO) {

		MerchantDTO merchantDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		String tokenId = requestDTO.getHeader().getTokenId();
		try {
			if(!tokenCheckUtil.checkMerchantToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				MerchantDTO nMerchantDTO = merchantService.getMerchantDTOByTokenId(tokenId);
				merchantService.setMerchantProfile(nMerchantDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_MERCHANT);
			}
			PrintSettingDTO settingDTO = settingService.getPrintSetting(merchantDTO);
			resultDTO.getBody().setData(settingDTO);
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}
		return resultDTO;
	}
	/**更新商家打印机设置
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/updatePrintSetting", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO updatePrintSetting(@RequestBody RequestDTO<PrintSettingDTO> requestDTO) {

		PrintSettingDTO settingDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		String tokenId = requestDTO.getHeader().getTokenId();
		try {
			if(!tokenCheckUtil.checkMerchantToken(requestDTO, resultDTO)){
				return resultDTO;
			}else{
				MerchantDTO nMerchantDTO = merchantService.getMerchantDTOByTokenId(tokenId);
				merchantService.setMerchantProfile(nMerchantDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_MERCHANT);
			}
			settingService.updatePrintSetting(settingDTO);
			
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_SUCCESS);
		} catch (BusinessException e) {
			resultDTO.getBody().getStatus().setStatusCode(AuthenticationConstants.STATUS_CODE_ERROR);
			resultDTO.getBody().getStatus().setErrorCode(e.getErrorCode());
			String errorDesc = MessageHelper.getMessageByErrorId(messageSource, e.getErrorCode());
			resultDTO.getBody().getStatus().setErrorDesc(errorDesc);
		}
		return resultDTO;
	}
	/**打印订单
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/merchant/printOrder", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO printOrder(@RequestBody RequestDTO<OrderDTOList> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		OrderDTOList orderDTOList = requestDTO.getBody();
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
			//检查平台打印机设置, 未设置正确则抛出异常
			printService.checkPrintSetting(orderDTOList.getMerchantDTO());
			PrintSettingDTO printSettingDTO = printService.getPrintSetting(orderDTOList.getMerchantDTO());
			//批量打印订单
			List<OrderDTO> list = orderDTOList.getOrders();
			for (Iterator<OrderDTO> iterator = list.iterator(); iterator.hasNext();) {
				OrderDTO orderDTO = iterator.next();
				printService.printOrder(orderDTO.getOrderNo(),printSettingDTO);
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
	
	/*
	 * 查询快递物流信息
	 */
	@RequestMapping(value = "/merchant/getCourierInfo", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO getCourierInfo(@RequestBody RequestDTO<CourierResultDTO> requestDTO) {
		String tokenId = requestDTO.getHeader().getTokenId();
		CourierResultDTO courierResultDTO = requestDTO.getBody();
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
        	CourierDTO courierDTO = orderService.getCourierInfoByNo(courierResultDTO.getNumber(), courierResultDTO.getType());
        	resultDTO.getBody().setData(courierDTO);
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
