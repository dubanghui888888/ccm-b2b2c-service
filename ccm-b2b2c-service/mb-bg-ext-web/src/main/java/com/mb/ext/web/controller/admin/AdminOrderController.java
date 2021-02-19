package com.mb.ext.web.controller.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.mb.ext.core.constant.OrderConstants;
import com.mb.ext.core.context.UserContext;
import com.mb.ext.core.dao.SettingDAO;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.AdminService;
import com.mb.ext.core.service.AuthenticationService;
import com.mb.ext.core.service.DeliveryService;
import com.mb.ext.core.service.FundService;
import com.mb.ext.core.service.LogService;
import com.mb.ext.core.service.MerchantService;
import com.mb.ext.core.service.OSSService;
import com.mb.ext.core.service.OrderService;
import com.mb.ext.core.service.PlatformService;
import com.mb.ext.core.service.PrintService;
import com.mb.ext.core.service.ProductService;
import com.mb.ext.core.service.SettingService;
import com.mb.ext.core.service.SupplierService;
import com.mb.ext.core.service.UserService;
import com.mb.ext.core.service.spec.AdminDTO;
import com.mb.ext.core.service.spec.ChartDTO;
import com.mb.ext.core.service.spec.OrderSearchDTO;
import com.mb.ext.core.service.spec.PrintSettingDTO;
import com.mb.ext.core.service.spec.RequestDTO;
import com.mb.ext.core.service.spec.ResultDTO;
import com.mb.ext.core.service.spec.order.CourierDTO;
import com.mb.ext.core.service.spec.order.CourierResultDTO;
import com.mb.ext.core.service.spec.order.OrderDTO;
import com.mb.ext.core.service.spec.order.OrderDTOList;
import com.mb.ext.core.service.spec.order.OrderSummaryDTO;
import com.mb.ext.core.util.MailSenderUtil;
import com.mb.ext.web.util.MessageHelper;
import com.mb.ext.web.util.TokenCheckUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;

/**后台订单管理类
 * @author B2B2C商城
 *
 */
@Controller
public class AdminOrderController {

	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	@Autowired
	private AdminService adminService;

	@Autowired
	private PlatformService platformService;

	@Autowired
	private ProductService productService;

	@Autowired
	private OSSService ossService;

	@Autowired
	private SettingService settingService;

	@Autowired
	private SettingDAO setttingDAO;

	@Autowired
	private LogService logService;

	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private PrintService printService;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private DeliveryService deliveryService;

	@Autowired
	private FundService fundService;

	@Autowired
	private OrderService orderService;

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

	/**备注订单
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/commentOrder", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO commentOrder(@RequestBody RequestDTO<OrderDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		OrderDTO orderDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
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
	@RequestMapping(value = "/admin/editOrder", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO editOrder(@RequestBody RequestDTO<OrderDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		OrderDTO orderDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
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
	
	/**订单发货
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/deliverOrder", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO deliverOrder(@RequestBody RequestDTO<OrderDTOList> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		OrderDTOList orderDTOList = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
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
	
	/**订单资金结算
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/accountOrder", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO accountOrder(@RequestBody RequestDTO<OrderDTOList> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		OrderDTOList orderDTOList = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			List<OrderDTO> list = orderDTOList.getOrders();
			for (Iterator<OrderDTO> iterator = list.iterator(); iterator.hasNext();) {
				OrderDTO orderDTO = iterator.next();
				orderService.accountOrder(orderDTO);
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

	/**分页查询订单
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/searchOrder", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO searchOrder(@RequestBody RequestDTO<OrderSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		OrderSearchDTO orderSearchDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
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

	/** 查询订单
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryOrder", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryOrder(@RequestBody RequestDTO<OrderDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		OrderDTO orderDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			if (!StringUtils.isEmpty(orderDTO.getOrderNo())) {
				orderDTO = orderService.getOrderByOrderNo(orderDTO.getOrderNo());
				resultDTO.getBody().setData(orderDTO);
			} else if (orderDTO.getUserDTO() != null) {
				List<OrderDTO> orderDTOList = orderService.getOrderByUser(orderDTO.getUserDTO());
				OrderDTOList list = new OrderDTOList();
				list.setOrders(orderDTOList);
				resultDTO.getBody().setData(list);
			} else if (orderDTO.getMerchantDTO() != null) {
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

	/**取消订单
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/cancelOrder", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO cancelOrder(@RequestBody RequestDTO<OrderDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		OrderDTO orderDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
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
	@RequestMapping(value = "/admin/payOrder", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO payOrder(@RequestBody RequestDTO<OrderDTOList> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		OrderDTOList orderDTOList = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
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
	
	/**打印订单
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/printOrder", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO printOrder(@RequestBody RequestDTO<OrderDTOList> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		OrderDTOList orderDTOList = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}
			//检查平台打印机设置, 未设置正确则抛出异常
			printService.checkPrintSetting(null);
			PrintSettingDTO printSettingDTO = printService.getPrintSetting(null);
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
	@RequestMapping(value = "/admin/getCourierInfo", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO getCourierInfo(@RequestBody RequestDTO<CourierResultDTO> requestDTO) {
		String tokenId = requestDTO.getHeader().getTokenId();
		CourierResultDTO courierResultDTO = requestDTO.getBody();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
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

	/**订单数据概览
	 * @param requestDTO
	 * @return
	 */
	@RequestMapping(value = "/admin/inquiryOrderSummary", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResultDTO inquiryOrderSummary(@RequestBody RequestDTO<OrderSearchDTO> requestDTO) {

		String tokenId = requestDTO.getHeader().getTokenId();
		OrderSearchDTO orderSearchDTO = requestDTO.getBody();
		Date startDate = orderSearchDTO.getOrderDateStart();
		Date endDate = orderSearchDTO.getOrderDateEnd();
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setHeader(requestDTO.getHeader());
		try {
			if (!tokenCheckUtil.checkAdminToken(requestDTO, resultDTO)) {
				return resultDTO;
			} else {
				AdminDTO adminDTO = adminService.getAdminDTOByTokenId(tokenId);
				adminService.setAdminProfile(adminDTO);
				UserContext.set(AuthorizationConstants.APPCHANNEL, AuthorizationConstants.APPCHANNEL_ADMIN);
			}

			OrderSummaryDTO summaryDTO = new OrderSummaryDTO();
			// 销售产品数
			int productUnitCount = orderService.getOrderProductUnitTotal(startDate, endDate);
			summaryDTO.setProductUnit(productUnitCount);
			BigDecimal productAmount = orderService.getOrderProductAmountTotal(startDate, endDate);
			summaryDTO.setProductAmount(productAmount);

			// 订单总数量，总积分
			orderSearchDTO.setKeyArray(new String[] { "ORDERDATE" });
			int orderRecord = orderService.searchOrderTotal(orderSearchDTO);
			summaryDTO.setOrderRecord(orderRecord);
			int orderPoint = orderService.searchOrderTotalPoint(orderSearchDTO);
			summaryDTO.setOrderPoint(orderPoint);

			// 订单未发货总数量，总积分
			orderSearchDTO.setKeyArray(new String[] { "ORDERDATE", "ORDERSTATUS" });
			orderSearchDTO.setOrderStatus(OrderConstants.ORDER_STATUS_NOT_DELIVERIED);
			int orderRecordNotDelivered = orderService.searchOrderTotal(orderSearchDTO);
			summaryDTO.setOrderRecordNotDelivered(orderRecordNotDelivered);
			int orderPointNotDelivered = orderService.searchOrderTotalPoint(orderSearchDTO);
			summaryDTO.setOrderPointNotDelivered(orderPointNotDelivered);

			// 订单已发货总数量，总积分
			orderSearchDTO.setKeyArray(new String[] { "ORDERDATE", "ORDERSTATUS" });
			orderSearchDTO.setOrderStatus(OrderConstants.ORDER_STATUS_DELIVERIED);
			int orderRecordDelivered = orderService.searchOrderTotal(orderSearchDTO);
			summaryDTO.setOrderRecordDelivered(orderRecordDelivered);
			int orderPointDelivered = orderService.searchOrderTotalPoint(orderSearchDTO);
			summaryDTO.setOrderPointDelivered(orderPointDelivered);

			// 订单已取消总数量，总积分
			orderSearchDTO.setKeyArray(new String[] { "ORDERDATE", "ORDERSTATUS" });
			orderSearchDTO.setOrderStatus(OrderConstants.ORDER_STATUS_CANCELLED);
			int orderRecordCancelled = orderService.searchOrderTotal(orderSearchDTO);
			summaryDTO.setOrderRecordCancelled(orderRecordCancelled);
			int orderPointCancelled = orderService.searchOrderTotalPoint(orderSearchDTO);
			summaryDTO.setOrderPointCancelled(orderPointCancelled);

			// 订单已收货总数量，总积分
			orderSearchDTO.setKeyArray(new String[] { "ORDERDATE", "ORDERSTATUS" });
			orderSearchDTO.setOrderStatus(OrderConstants.ORDER_STATUS_COMPLETED);
			int orderRecordCompleted1 = orderService.searchOrderTotal(orderSearchDTO);
			int orderPointCompleted1 = orderService.searchOrderTotalPoint(orderSearchDTO);

			orderSearchDTO.setKeyArray(new String[] { "ORDERDATE", "ORDERSTATUS" });
			orderSearchDTO.setOrderStatus(OrderConstants.ORDER_STATUS_EVALUATED);
			int orderRecordCompleted2 = orderService.searchOrderTotal(orderSearchDTO);
			int orderPointCompleted2 = orderService.searchOrderTotalPoint(orderSearchDTO);
			summaryDTO.setOrderRecordCompleted(orderRecordCompleted1 + orderRecordCompleted2);
			summaryDTO.setOrderPointCompleted(orderPointCompleted1 + orderPointCompleted2);

			// 获取最近七日销售增长曲线
			List<ChartDTO> orderRecordChart = orderService.getIncrementOrderCountChart(startDate, endDate);
			summaryDTO.setOrderRecordChart(orderRecordChart);
			List<ChartDTO> productPointChart = orderService.getIncrementOrderPointChart(startDate, endDate);
			summaryDTO.setOrderPointChart(productPointChart);

			resultDTO.getBody().setData(summaryDTO);
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
