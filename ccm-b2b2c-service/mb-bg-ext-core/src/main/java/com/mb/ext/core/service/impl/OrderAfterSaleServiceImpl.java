package com.mb.ext.core.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.mb.ext.core.constant.AlipayConstants;
import com.mb.ext.core.constant.FundConstants;
import com.mb.ext.core.constant.OrderConstants;
import com.mb.ext.core.dao.FileDAO;
import com.mb.ext.core.dao.MerchantDAO;
import com.mb.ext.core.dao.PartnerDAO;
import com.mb.ext.core.dao.SequenceDAO;
import com.mb.ext.core.dao.SettingDAO;
import com.mb.ext.core.dao.UserAuthDAO;
import com.mb.ext.core.dao.UserBalanceDAO;
import com.mb.ext.core.dao.UserDAO;
import com.mb.ext.core.dao.UserDeliveryAddressDAO;
import com.mb.ext.core.dao.UserInventoryDAO;
import com.mb.ext.core.dao.UserStatementDAO;
import com.mb.ext.core.dao.merchant.MerchantBalanceDAO;
import com.mb.ext.core.dao.merchant.MerchantStatementDAO;
import com.mb.ext.core.dao.merchant.PlatformBalanceDAO;
import com.mb.ext.core.dao.merchant.PlatformStatementDAO;
import com.mb.ext.core.dao.order.OrderAfterSaleDAO;
import com.mb.ext.core.dao.order.OrderAfterSaleImageDAO;
import com.mb.ext.core.dao.order.OrderDAO;
import com.mb.ext.core.dao.order.OrderProductDAO;
import com.mb.ext.core.dao.product.ProductDAO;
import com.mb.ext.core.dao.product.ProductSkuAttrValueDAO;
import com.mb.ext.core.dao.product.ProductSkuDAO;
import com.mb.ext.core.dao.shoppingcart.ShoppingCartDAO;
import com.mb.ext.core.entity.UserBalanceEntity;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.UserStatementEntity;
import com.mb.ext.core.entity.merchant.MerchantBalanceEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.merchant.MerchantStatementEntity;
import com.mb.ext.core.entity.order.OrderAfterSaleEntity;
import com.mb.ext.core.entity.order.OrderAfterSaleImageEntity;
import com.mb.ext.core.entity.order.OrderEntity;
import com.mb.ext.core.entity.order.OrderProductEntity;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.NoteService;
import com.mb.ext.core.service.OrderAfterSaleService;
import com.mb.ext.core.service.OrderService;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.OrderAfterSaleSearchDTO;
import com.mb.ext.core.service.spec.WechatRefundRequest;
import com.mb.ext.core.service.spec.WechatRefundResponse;
import com.mb.ext.core.service.spec.order.OrderAfterSaleDTO;
import com.mb.ext.core.service.spec.order.OrderDTO;
import com.mb.ext.core.util.PaymentUtil;
import com.mb.ext.core.util.SMSSenderUtil;
import com.mb.ext.core.util.WechatUtil;
import com.mb.ext.msg.WxSuscribeMessageSender;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.service.AbstractService;
import com.mb.framework.util.DateTimeUtil;
import com.mb.framework.util.log.LogHelper;

@Service
@Qualifier("OrderAfterSaleService")
public class OrderAfterSaleServiceImpl extends AbstractService implements
		OrderAfterSaleService<BodyDTO> {

	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;
	
	@Autowired
	@Qualifier("userInventoryDAO")
	private UserInventoryDAO userInventoryDAO;
	
	@Autowired
	@Qualifier("userStatementDAO")
	private UserStatementDAO userStatementDAO;
	
	@Autowired
	private SMSSenderUtil smsSenderUtil;
	
	@Autowired
	private WxSuscribeMessageSender wxSuscribeMessageSender;
	
	@Autowired
	private PaymentUtil paymentUtil;
	
	@Autowired
	private WechatUtil wechatUtil;
	
	@Autowired
	@Qualifier("userAuthDAO")
	private UserAuthDAO userAuthDAO;
	
	@Autowired
	@Qualifier("userBalanceDAO")
	private UserBalanceDAO userBalanceDAO;
	
	@Autowired
	@Qualifier("fileDAO")
	private FileDAO fileDAO;
	
	@Autowired
	@Qualifier("settingDAO")
	private SettingDAO settingDAO;
	
	@Autowired
	@Qualifier("partnerDAO")
	private PartnerDAO partnerDAO;
	

	@Autowired
	@Qualifier("sequenceDAO")
	private SequenceDAO sequenceDAO;

	@Autowired
	@Qualifier("merchantDAO")
	private MerchantDAO merchantDAO;
	
	@Autowired
	@Qualifier("orderAfterSaleDAO")
	private OrderAfterSaleDAO orderAfterSaleDAO;
	
	@Autowired
	@Qualifier("orderAfterSaleImageDAO")
	private OrderAfterSaleImageDAO orderAfterSaleImageDAO;

	@Autowired
	@Qualifier("orderDAO")
	private OrderDAO orderDAO;
	
	@Autowired
	@Qualifier("OrderService")
	private OrderService orderService;
	
	@Autowired
	@Qualifier("orderProductDAO")
	private OrderProductDAO orderProductDAO;

	@Autowired
	@Qualifier("userDeliveryAddressDAO")
	private UserDeliveryAddressDAO userDeliveryAddressDAO;
	
	@Autowired
	@Qualifier("merchantBalanceDAO")
	private MerchantBalanceDAO merchantBalanceDAO;
	
	@Autowired
	@Qualifier("merchantStatementDAO")
	private MerchantStatementDAO merchantStatementDAO;
	
	@Autowired
	@Qualifier("platformBalanceDAO")
	private PlatformBalanceDAO platformBalanceDAO;
	
	@Autowired
	@Qualifier("platformStatementDAO")
	private PlatformStatementDAO platformStatementDAO;
	
	@Autowired
	@Qualifier("shoppingCartDAO")
	private ShoppingCartDAO shoppingCartDAO;
	
	@Autowired
	@Qualifier("productDAO")
	private ProductDAO productDAO;
	
	@Autowired
	@Qualifier("productSkuDAO")
	private ProductSkuDAO productSkuDAO;
	
	@Autowired
	@Qualifier("productSkuAttrValueDAO")
	private ProductSkuAttrValueDAO productSkuAttrValueDAO;
	
	@Autowired
	@Qualifier("NoteService")
	private NoteService noteService;

	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	@Override
	public void applyOrderAfterSale(OrderAfterSaleDTO afterSaleDTO) throws BusinessException {
		
		try {
			OrderDTO orderDTO = afterSaleDTO.getOrderDTO();
			//检查是否已经提交过售后申请, 不能重复提交
			OrderAfterSaleEntity afterSaleEntity = orderAfterSaleDAO.getOrderAfterSaleByOrderNo(orderDTO.getOrderNo());
			if(afterSaleEntity != null) {
				throw new BusinessException(BusinessErrorCode.ORDER_AFTER_SALE_DUPLICATE_APPLICATION);
			}
			//获取售后订单
			OrderEntity orderEntity = orderDAO.getOrderByOrderNo(orderDTO.getOrderNo());
			afterSaleEntity = new OrderAfterSaleEntity();
			afterSaleEntity.setOrderEntity(orderEntity);
			//获取售后其他信息
			afterSaleEntity.setSaleNo(getSaleNo(orderEntity.getUserEntity().getId()));
			afterSaleEntity.setAfterSaleAmount(afterSaleDTO.getAfterSaleAmount());
			afterSaleEntity.setAfterSaleDescription(afterSaleDTO.getAfterSaleDescription());
			afterSaleEntity.setAfterSaleType(afterSaleDTO.getAfterSaleType());
			afterSaleEntity.setStatus(OrderConstants.ORDER_AFTER_SALE_STATUS_CREATE);
			afterSaleEntity.setTimeApplication(new Date());
			afterSaleEntity.setMerchantEntity(orderEntity.getMerchantEntity());
			//保存售后单
			orderAfterSaleDAO.addOrderAfterSale(afterSaleEntity);
			
			//保存售后单相关图片
			List<String> imageUrlList = afterSaleDTO.getImageUrlList();
			for (Iterator<String> iterator = imageUrlList.iterator(); iterator.hasNext();) {
				String url = iterator.next();
				OrderAfterSaleImageEntity imageEntity = new OrderAfterSaleImageEntity();
				imageEntity.setUrl(url);
				imageEntity.setOrderAfterSaleEntity(afterSaleEntity);
				orderAfterSaleImageDAO.createOrderAfterSaleImage(imageEntity);
			}
			//标记订单已申请售后
			orderEntity.setAfterSale(true);
			orderEntity.setAfterSaleNo(afterSaleEntity.getSaleNo());
			orderDAO.updateOrder(orderEntity);
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public void updateOrderAfterSale(OrderAfterSaleDTO afterSaleDTO) throws BusinessException {
		
		try {
			
			//获取售后订单
			OrderAfterSaleEntity afterSaleEntity = orderAfterSaleDAO.getOrderAfterSaleBySaleNo(afterSaleDTO.getSaleNo());
			
			//获取售后其他信息
			afterSaleEntity.setAfterSaleAmount(afterSaleDTO.getAfterSaleAmount());
			afterSaleEntity.setAfterSaleDescription(afterSaleDTO.getAfterSaleDescription());
			afterSaleEntity.setAfterSaleType(afterSaleDTO.getAfterSaleType());
			//客户售后单时状态重置为申请中
			afterSaleEntity.setStatus(OrderConstants.ORDER_AFTER_SALE_STATUS_CREATE);
			
			//更新售后单
			orderAfterSaleDAO.updateOrderAfterSale(afterSaleEntity);
			
			//更新售后单相关图片
			List<OrderAfterSaleImageEntity> imageEntityList = afterSaleEntity.getOrderAfterSaleList();
			afterSaleEntity.setOrderAfterSaleList(null);
			for (OrderAfterSaleImageEntity orderAfterSaleImageEntity : imageEntityList) {
				orderAfterSaleImageDAO.deleteOrderAfterSaleImage(orderAfterSaleImageEntity);
			}
			List<String> imageUrlList = afterSaleDTO.getImageUrlList();
			for (Iterator<String> iterator = imageUrlList.iterator(); iterator.hasNext();) {
				String url = iterator.next();
				OrderAfterSaleImageEntity imageEntity = new OrderAfterSaleImageEntity();
				imageEntity.setUrl(url);
				imageEntity.setOrderAfterSaleEntity(afterSaleEntity);
				orderAfterSaleImageDAO.createOrderAfterSaleImage(imageEntity);
			}
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public void approveOrderAfterSale(OrderAfterSaleDTO afterSaleDTO) throws BusinessException {
		
		try {
			OrderAfterSaleEntity afterSaleEntity = orderAfterSaleDAO.getOrderAfterSaleBySaleNo(afterSaleDTO.getSaleNo());
			//仅退款
			if(OrderConstants.ORDER_AFTER_SALE_TYPE_REFUND.equals(afterSaleEntity.getAfterSaleType())) {
				afterSaleEntity.setStatus(OrderConstants.ORDER_AFTER_SALE_STATUS_COMPLETED);
				//退款完成, 资金退还
				orderAfterSaleRefund(afterSaleEntity.getSaleNo());
			}else {
				afterSaleEntity.setStatus(OrderConstants.ORDER_AFTER_SALE_STATUS_APPROVED);
			}
			afterSaleEntity.setTimeOperation(new Date());
			orderAfterSaleDAO.updateOrderAfterSale(afterSaleEntity);
			
			OrderEntity orderEntity = afterSaleEntity.getOrderEntity();
			
			//发送退款审核通过模板消息
			String productName = "";
			List<OrderProductEntity> productList =  orderEntity.getOrderProductList();
			for (OrderProductEntity orderProductEntity : productList) {
				productName = productName + orderProductEntity.getProductName();
			}
			productName = productName.length()>20?productName.substring(0, 16)+"...":productName;
			String returnStatus = "卖家已同意退款";
			
			wxSuscribeMessageSender.sendOrderReturnMsg(paymentUtil.getWechatAppIdMiniProgram(), paymentUtil.getWechatAppSecretMiniProgram(), orderEntity.getUserEntity().getOpenId(), orderEntity.getOrderNo(),new SimpleDateFormat("yyyy年MM月dd日").format(afterSaleEntity.getTimeApplication()), productName, String.valueOf(afterSaleEntity.getAfterSaleAmount()), returnStatus);
		}catch(BusinessException e) {
			throw e;
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void cancelOrderAfterSale(OrderAfterSaleDTO afterSaleDTO) throws BusinessException {
		
		try {
			OrderAfterSaleEntity afterSaleEntity = orderAfterSaleDAO.getOrderAfterSaleBySaleNo(afterSaleDTO.getSaleNo());
			//删除售后单相关图片
			List<OrderAfterSaleImageEntity> imageEntityList = afterSaleEntity.getOrderAfterSaleList();
			afterSaleEntity.setOrderAfterSaleList(null);
			for (OrderAfterSaleImageEntity orderAfterSaleImageEntity : imageEntityList) {
				orderAfterSaleImageDAO.deleteOrderAfterSaleImage(orderAfterSaleImageEntity);
			}
			//删除售后单
			orderAfterSaleDAO.deleteOrderAfterSale(afterSaleEntity);
			
			//更新订单售后状态
			OrderEntity orderEntity = afterSaleEntity.getOrderEntity();
			orderEntity.setAfterSale(false);
			orderEntity.setAfterSaleNo("");
			orderDAO.updateOrder(orderEntity);
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void courierOrderAfterSale(OrderAfterSaleDTO afterSaleDTO) throws BusinessException {
		
		try {
			OrderAfterSaleEntity afterSaleEntity = orderAfterSaleDAO.getOrderAfterSaleBySaleNo(afterSaleDTO.getSaleNo());
			afterSaleEntity.setStatus(OrderConstants.ORDER_AFTER_SALE_STATUS_WAITING_CONFIRM_COURIER);
			afterSaleEntity.setCourierName(afterSaleDTO.getCourierName());
			afterSaleEntity.setCourierNo(afterSaleDTO.getCourierNo());
			afterSaleEntity.setTimeCouirer(new Date());
			orderAfterSaleDAO.updateOrderAfterSale(afterSaleEntity);
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public void rejectOrderAfterSale(OrderAfterSaleDTO afterSaleDTO) throws BusinessException {
		
		try {
			OrderAfterSaleEntity afterSaleEntity = orderAfterSaleDAO.getOrderAfterSaleBySaleNo(afterSaleDTO.getSaleNo());
			afterSaleEntity.setStatus(OrderConstants.ORDER_AFTER_SALE_STATUS_REJECTED);
			afterSaleEntity.setRejectReason(afterSaleDTO.getRejectReason());
			afterSaleEntity.setTimeOperation(new Date());
			orderAfterSaleDAO.updateOrderAfterSale(afterSaleEntity);
			OrderEntity orderEntity = afterSaleEntity.getOrderEntity();
			//发送退款审核通过模板消息
			String productName = "";
			List<OrderProductEntity> productList =  orderEntity.getOrderProductList();
			for (OrderProductEntity orderProductEntity : productList) {
				productName = productName + orderProductEntity.getProductName();
			}
			productName = productName.length()>20?productName.substring(0, 16)+"...":productName;
			String returnStatus = "卖家不同意退款,"+afterSaleEntity.getRejectReason();
			
			wxSuscribeMessageSender.sendOrderReturnMsg(paymentUtil.getWechatAppIdMiniProgram(), paymentUtil.getWechatAppSecretMiniProgram(), orderEntity.getUserEntity().getOpenId(), orderEntity.getOrderNo(),new SimpleDateFormat("yyyy年MM月dd日").format(afterSaleEntity.getTimeApplication()), productName, String.valueOf(afterSaleEntity.getAfterSaleAmount()), returnStatus);
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void confirmOrderAfterSale(OrderAfterSaleDTO afterSaleDTO) throws BusinessException {
		
		try {
			OrderAfterSaleEntity afterSaleEntity = orderAfterSaleDAO.getOrderAfterSaleBySaleNo(afterSaleDTO.getSaleNo());
			afterSaleEntity.setStatus(OrderConstants.ORDER_AFTER_SALE_STATUS_COMPLETED);
			afterSaleEntity.setTimeConfirm(new Date());
			//退款完成, 资金退还
			orderAfterSaleRefund(afterSaleEntity.getSaleNo());
			
			orderAfterSaleDAO.updateOrderAfterSale(afterSaleEntity);
			
			OrderEntity orderEntity = afterSaleEntity.getOrderEntity();
			//发送退款审核通过模板消息
			String productName = "";
			List<OrderProductEntity> productList =  orderEntity.getOrderProductList();
			for (OrderProductEntity orderProductEntity : productList) {
				productName = productName + orderProductEntity.getProductName();
			}
			productName = productName.length()>20?productName.substring(0, 16)+"...":productName;
			String returnStatus = "退款完成，金额将原路退回到你的账户";
			
			wxSuscribeMessageSender.sendOrderReturnMsg(paymentUtil.getWechatAppIdMiniProgram(), paymentUtil.getWechatAppSecretMiniProgram(), orderEntity.getUserEntity().getOpenId(), orderEntity.getOrderNo(),new SimpleDateFormat("yyyy年MM月dd日").format(afterSaleEntity.getTimeApplication()), productName, String.valueOf(afterSaleEntity.getAfterSaleAmount()), returnStatus);
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public OrderAfterSaleDTO getOrderAfterSaleByAfterSaleNo(String afterSaleNo) throws BusinessException {
		try {
			OrderAfterSaleEntity afterSaleEntity = orderAfterSaleDAO.getOrderAfterSaleBySaleNo(afterSaleNo);
			OrderAfterSaleDTO afterSaleDTO = new OrderAfterSaleDTO();
			afterSaleEntity2DTO(afterSaleEntity, afterSaleDTO);
			return afterSaleDTO;
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}

	@Override
	public OrderAfterSaleDTO getOrderAfterSaleByOrderNo(String orderNo) throws BusinessException {
		try {
			OrderAfterSaleEntity afterSaleEntity = orderAfterSaleDAO.getOrderAfterSaleByOrderNo(orderNo);
			OrderAfterSaleDTO afterSaleDTO = new OrderAfterSaleDTO();
			afterSaleEntity2DTO(afterSaleEntity, afterSaleDTO);
			return afterSaleDTO;
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}

	@Override
	public List<OrderAfterSaleDTO> searchOrderAfterSale(OrderAfterSaleSearchDTO searchDTO) throws BusinessException {
		List<OrderAfterSaleDTO> afterSaleDTOList = new ArrayList<OrderAfterSaleDTO>();
		try {
			List<OrderAfterSaleEntity> afterSaleEntityList = orderAfterSaleDAO.searchOrderAfterSale(searchDTO);
			for (OrderAfterSaleEntity orderAfterSaleEntity : afterSaleEntityList) {
				OrderAfterSaleDTO orderAfterSaleDTO = new OrderAfterSaleDTO();
				afterSaleEntity2DTO(orderAfterSaleEntity, orderAfterSaleDTO);
				afterSaleDTOList.add(orderAfterSaleDTO);
			}
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return afterSaleDTOList;
	}

	@Override
	public int searchOrderAfterSaleTotal(OrderAfterSaleSearchDTO searchDTO) throws BusinessException {
		try {
			return orderAfterSaleDAO.searchOrderAfterSaleTotal(searchDTO);
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public BigDecimal searchOrderAfterSaleAmount(OrderAfterSaleSearchDTO searchDTO) throws BusinessException {
		try {
			return orderAfterSaleDAO.searchOrderAfterSaleTotalAmount(searchDTO);
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public void orderAfterSaleRefund(String afterSaleNo) throws BusinessException {
		
		try {
			OrderAfterSaleEntity afterSaleEntity = orderAfterSaleDAO.getOrderAfterSaleBySaleNo(afterSaleNo);
			OrderEntity orderEntity = afterSaleEntity.getOrderEntity();
			MerchantEntity merchantEntity = orderEntity.getMerchantEntity();
			if(OrderConstants.ORDER_PAYMENT_METHOD_WECHAT.equals(orderEntity.getPaymentMethod())){
				wechatRefund(afterSaleNo);
			}else if(OrderConstants.ORDER_PAYMENT_METHOD_ALIPAY.equals(orderEntity.getPaymentMethod())){
				alipayRefund(afterSaleNo);
			}
			else if(OrderConstants.ORDER_PAYMENT_METHOD_BALANCE.equals(orderEntity.getPaymentMethod())){
				balanceRefund(afterSaleNo);
			}
			//订单金额已经入账商家的请款下才退回
			if(merchantEntity != null && orderEntity.isAccounted()) {
				MerchantBalanceEntity merchantBalanceEntity = merchantBalanceDAO.getBalanceByMerchant(merchantEntity);
				if (merchantBalanceEntity == null) {
					merchantBalanceEntity = new MerchantBalanceEntity();
					merchantBalanceEntity.setMerchantEntity(merchantEntity);
					merchantBalanceEntity.setAvailableBalance(new BigDecimal(0));
					merchantBalanceEntity.setTotalBalance(new BigDecimal(0));
					merchantBalanceEntity.setAvailablePoint(0);
					merchantBalanceEntity.setTotalPoint(0);
					merchantBalanceEntity.setCreateBy(orderEntity.getUserEntity().getId());
					merchantBalanceEntity.setUpdateBy(orderEntity.getUserEntity().getId());
					merchantBalanceDAO.createMerchantBalance(merchantBalanceEntity);
				}
				// 更新商户资金余额
				BigDecimal availableBalanceBefore = merchantBalanceEntity.getAvailableBalance();
				BigDecimal totalBalanceBefore = merchantBalanceEntity.getTotalBalance();
				BigDecimal availableBalanceAfter = availableBalanceBefore.subtract(orderEntity.getActualAmount());
				BigDecimal totalBalanceAfter = totalBalanceBefore.subtract(orderEntity.getActualAmount());
				merchantBalanceEntity.setAvailableBalance(availableBalanceAfter);
				merchantBalanceEntity.setTotalBalance(totalBalanceAfter);
				merchantBalanceEntity.setUpdateBy(orderEntity.getUserEntity().getPersonalPhone());
				merchantBalanceDAO.updateMerchantBalance(merchantBalanceEntity);
				
				// 更新商户对账单
				MerchantStatementEntity statementEntity = new MerchantStatementEntity();
				statementEntity.setMerchantEntity(merchantEntity);
				statementEntity.setTransactionAmount(orderEntity.getActualAmount());
				statementEntity.setTransactionType(FundConstants.MERCHANT_STATEMENT_TRANSACTION_TYPE_ORDER);
				statementEntity.setTransactionCode(orderEntity.getOrderNo());
				statementEntity.setTransactionTime(new Date());
				statementEntity.setBalanceBefore(availableBalanceBefore);
				statementEntity.setBalanceAfter(availableBalanceAfter);
				statementEntity.setCreateBy(orderEntity.getUserEntity().getId());
				statementEntity.setUpdateBy(orderEntity.getUserEntity().getId());
				merchantStatementDAO.createMerchantStatement(statementEntity);
			}
			
		}catch(BusinessException e) {
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		
	}
	
	@Override
	public void wechatRefund(String afterSaleNo) throws BusinessException {
		
		try {
			OrderAfterSaleEntity afterSaleEntity = orderAfterSaleDAO.getOrderAfterSaleBySaleNo(afterSaleNo);
			OrderEntity orderEntity = afterSaleEntity.getOrderEntity();
			WechatRefundRequest refundRequest = new WechatRefundRequest();
			refundRequest.setAppid(paymentUtil.getWechatAppIdMiniProgram());
			refundRequest.setMch_id(paymentUtil.getWechatMerchantId());
			refundRequest.setNonce_str(RandomStringUtils.randomAlphanumeric(32));
			refundRequest.setTransaction_id(orderEntity.getTransactionId());
			refundRequest.setOut_refund_no(orderEntity.getOrderNo());
			BigDecimal actualAmount = orderEntity.getActualAmount();
			//针对合并支付的订单, 需传入合并支付的总金额而不是拆分后单个订单的金额
			if(!StringUtils.isEmpty(orderEntity.getpOrderNo())) {
				actualAmount = orderDAO.getActualAmountByPOrderNo(orderEntity.getpOrderNo());
			}
			refundRequest.setTotal_fee(actualAmount.multiply(new BigDecimal(100))
					.intValue());
			refundRequest.setRefund_fee(afterSaleEntity.getAfterSaleAmount().multiply(new BigDecimal(100))
					.intValue());
			refundRequest.setNotify_url(paymentUtil.getWechatRefundNotifyUrl());
			refundRequest.setSign(wechatUtil.getRefundSign(refundRequest));
			String requestXML = wechatUtil.toXML(refundRequest);
			requestXML = new String(requestXML.getBytes("utf-8"),"iso-8859-1");
			logger.info("Wechat refund request: "+requestXML);
			//下单
			String wechatResponseStr = wechatUtil.postWechatRefund(requestXML);
			logger.info("Wechat refund Response: "+wechatResponseStr);
			WechatRefundResponse refundResponse = (WechatRefundResponse)wechatUtil.fromXML2WechatResponse(WechatRefundResponse.class,wechatResponseStr);
			if("SUCCESS".equals(refundResponse.getReturn_code()) && "SUCCESS".equals(refundResponse.getResult_code())){
				afterSaleEntity.setRefundId(refundResponse.getRefund_id());
				afterSaleEntity.setRefundMsg("已提交微信退款申请");
				orderAfterSaleDAO.updateOrderAfterSale(afterSaleEntity);
			}else{
				logger.error("退款申请error code: " + refundResponse.getErr_code());
				logger.error("退款申请error desc: " + refundResponse.getErr_code_des());
				throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
			}
		}catch(BusinessException e) {
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		
	}
	
	@Override
	public void alipayRefund(String afterSaleNo) throws BusinessException {
		
		try {
			OrderAfterSaleEntity afterSaleEntity = orderAfterSaleDAO.getOrderAfterSaleBySaleNo(afterSaleNo);
			OrderEntity orderEntity = afterSaleEntity.getOrderEntity();
			AlipayClient alipayClient = new DefaultAlipayClient(AlipayConstants.ALIPAY_URL, paymentUtil.getAlipayAppId(),paymentUtil.getAlipayPrivateKey(), AlipayConstants.ALIPAY_FORMAT,AlipayConstants.ALIPAY_CHARSET, paymentUtil.getAlipayPublicKey(), AlipayConstants.ALIPAY_SIGNTYPE);
			AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
			String orderNo = orderEntity.getOrderNo();
			String pOrderNo = orderEntity.getpOrderNo();
			String out_trade_no = (StringUtils.isEmpty(pOrderNo)?orderNo:pOrderNo);
			request.setBizContent("{" +
					"\"out_trade_no\":\""+out_trade_no+"\"," +
					"\"trade_no\":\""+orderEntity.getTransactionId()+"\"," +
					"\"refund_amount\":"+afterSaleEntity.getAfterSaleAmount().setScale(2)+"," +
					"\"refund_currency\":\"CNY\"," +
					"\"out_request_no\":\""+RandomStringUtils.randomAlphanumeric(10).toUpperCase()+"\"" +
					"  }");
			AlipayTradeRefundResponse response = alipayClient.execute(request);
			if(response.isSuccess()){
				logger.info("支付宝退款申请成功" + ",code="+response.getCode() +",msg=" + response.getMsg());
				afterSaleEntity.setRefundId(response.getCode());
				afterSaleEntity.setRefundMsg(response.getMsg());
				orderAfterSaleDAO.updateOrderAfterSale(afterSaleEntity);
			} else {
				logger.error("支付宝退款申请失败" + ",code="+response.getCode() +",msg=" + response.getMsg() + ",subCode="+response.getSubCode() +",subMsg=" + response.getSubMsg());
				logger.error("支付宝退款申请error desc: " + response.getSubMsg());
				throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
			}
		}catch(BusinessException e) {
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		
	}
	
	@Override
	public void balanceRefund(String afterSaleNo) throws BusinessException {
		
		try {
			//初始化微信统一下单接口
			OrderAfterSaleEntity afterSaleEntity = orderAfterSaleDAO.getOrderAfterSaleBySaleNo(afterSaleNo);
			OrderEntity orderEntity = afterSaleEntity.getOrderEntity();
			UserEntity userEntity = orderEntity.getUserEntity();
			UserBalanceEntity userBalanceEntity = userEntity.getUserBalanceEntity();
			
			//更新资金余额
			BigDecimal availableBalanceBefore = userBalanceEntity.getAvailableBalance();
			BigDecimal availableBalanceAfter = availableBalanceBefore.add(afterSaleEntity.getAfterSaleAmount());
			userBalanceEntity.setAvailableBalance(availableBalanceAfter);
			userBalanceDAO.updateUserBalance(userBalanceEntity)	;
			
			//更新对账单
			UserStatementEntity statementEntity = new UserStatementEntity();
			statementEntity.setUserEntity(userEntity);
			statementEntity.setTransactionAmount(afterSaleEntity.getAfterSaleAmount());
			statementEntity.setTransactionType(FundConstants.USER_STATEMENT_TRANSACTION_TYPE_REFUND);
			statementEntity.setTransactionCode(afterSaleNo);
			statementEntity.setTransactionTime(new Date());
			statementEntity.setBalanceBefore(availableBalanceBefore);
			statementEntity.setBalanceAfter(availableBalanceAfter);
			statementEntity.setCreateBy(userEntity.getId());
			statementEntity.setUpdateBy(userEntity.getId());
			userStatementDAO.createUserStatement(statementEntity);
		}catch(DAOException e) {
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		
	}
	
	@Override
	public void updateRefundStatus(String afterSaleNo, String refundStatus) throws BusinessException {
		
		try {
			OrderAfterSaleEntity afterSaleEntity = orderAfterSaleDAO.getOrderAfterSaleBySaleNo(afterSaleNo);
			afterSaleEntity.setRefundMsg(refundStatus);
			orderAfterSaleDAO.updateOrderAfterSale(afterSaleEntity);
		}catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		
	}

	void afterSaleEntity2DTO(OrderAfterSaleEntity entity, OrderAfterSaleDTO dto) {
		dto.setOrderAfterSaleUuid(entity.getOrderAfterSaleUuid());
		dto.setAfterSaleAmount(entity.getAfterSaleAmount());
		dto.setAfterSaleDescription(entity.getAfterSaleDescription());
		dto.setAfterSaleType(entity.getAfterSaleType());
		dto.setSaleNo(entity.getSaleNo());
		dto.setRefundId(entity.getRefundId());
		dto.setRefundMsg(entity.getRefundMsg());
		dto.setTimeApplication(entity.getTimeApplication());
		dto.setTimeOperation(entity.getTimeOperation());
		dto.setStatus(entity.getStatus());
		dto.setRejectReason(entity.getRejectReason());
		dto.setCourierName(entity.getCourierName());
		dto.setCourierNo(entity.getCourierNo());
		dto.setTimeCouirer(entity.getTimeCouirer());
		dto.setTimeConfirm(entity.getTimeConfirm());
		List<String> imageUrlList = new ArrayList<String>();
		List<OrderAfterSaleImageEntity> imageEntityList = entity.getOrderAfterSaleList();
		for (OrderAfterSaleImageEntity orderAfterSaleImageEntity : imageEntityList) {
			imageUrlList.add(orderAfterSaleImageEntity.getUrl());
		}
		dto.setImageUrlList(imageUrlList);
		OrderEntity orderEntity = entity.getOrderEntity();
		OrderDTO orderDTO = new OrderDTO();
		orderService.orderEntity2DTO(orderEntity, orderDTO);
		dto.setOrderDTO(orderDTO);
	}
	
	protected String getSaleNo(String userId) throws BusinessException {
		
		try {
			//订单号 = 'AF'+时间戳+用户编号+4位任意数字
			String orderNo = OrderConstants.ORDER_TYPE_AF + DateTimeUtil.formatDateByYYMMDDHHmm(new Date())
			+ userId		
			+ RandomStringUtils.randomNumeric(4);
			return orderNo;
		}catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	
	}

}
