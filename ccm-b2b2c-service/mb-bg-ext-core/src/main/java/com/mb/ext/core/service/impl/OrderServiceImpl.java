package com.mb.ext.core.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.mb.ext.core.constant.AlipayConstants;
import com.mb.ext.core.constant.CouponConstants;
import com.mb.ext.core.constant.FundConstants;
import com.mb.ext.core.constant.GroupBuyConstants;
import com.mb.ext.core.constant.OrderConstants;
import com.mb.ext.core.constant.PointConstants;
import com.mb.ext.core.constant.ProductConstants;
import com.mb.ext.core.dao.FileDAO;
import com.mb.ext.core.dao.MerchantDAO;
import com.mb.ext.core.dao.PartnerDAO;
import com.mb.ext.core.dao.SequenceDAO;
import com.mb.ext.core.dao.SettingDAO;
import com.mb.ext.core.dao.UserAuthDAO;
import com.mb.ext.core.dao.UserAwardDAO;
import com.mb.ext.core.dao.UserBalanceDAO;
import com.mb.ext.core.dao.UserDAO;
import com.mb.ext.core.dao.UserDeliveryAddressDAO;
import com.mb.ext.core.dao.UserInventoryDAO;
import com.mb.ext.core.dao.UserInventoryHistoryDAO;
import com.mb.ext.core.dao.UserPointStatementDAO;
import com.mb.ext.core.dao.UserStatementDAO;
import com.mb.ext.core.dao.coupon.UserCouponDAO;
import com.mb.ext.core.dao.coupon.UserVoucherDAO;
import com.mb.ext.core.dao.group.GroupBuyDAO;
import com.mb.ext.core.dao.group.GroupBuyProductDAO;
import com.mb.ext.core.dao.group.GroupBuyUserDAO;
import com.mb.ext.core.dao.merchant.MerchantBalanceDAO;
import com.mb.ext.core.dao.merchant.MerchantStatementDAO;
import com.mb.ext.core.dao.merchant.MerchantUserDAO;
import com.mb.ext.core.dao.merchant.PlatformBalanceDAO;
import com.mb.ext.core.dao.merchant.PlatformStatementDAO;
import com.mb.ext.core.dao.order.OrderAfterSaleDAO;
import com.mb.ext.core.dao.order.OrderDAO;
import com.mb.ext.core.dao.order.OrderImageDAO;
import com.mb.ext.core.dao.order.OrderProductDAO;
import com.mb.ext.core.dao.point.PointProductDAO;
import com.mb.ext.core.dao.point.PointProductSkuAttrValueDAO;
import com.mb.ext.core.dao.point.PointProductSkuDAO;
import com.mb.ext.core.dao.point.PointSettingDAO;
import com.mb.ext.core.dao.product.ProductDAO;
import com.mb.ext.core.dao.product.ProductSkuAttrValueDAO;
import com.mb.ext.core.dao.product.ProductSkuDAO;
import com.mb.ext.core.dao.profit.ProfitDistributionDAO;
import com.mb.ext.core.dao.seckill.SecKillProductDAO;
import com.mb.ext.core.dao.shoppingcart.ShoppingCartDAO;
import com.mb.ext.core.entity.SettingEntity;
import com.mb.ext.core.entity.UserAwardEntity;
import com.mb.ext.core.entity.UserBalanceEntity;
import com.mb.ext.core.entity.UserDeliveryAddressEntity;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.UserPointStatementEntity;
import com.mb.ext.core.entity.UserStatementEntity;
import com.mb.ext.core.entity.coupon.UserCouponEntity;
import com.mb.ext.core.entity.coupon.UserVoucherEntity;
import com.mb.ext.core.entity.group.GroupBuyEntity;
import com.mb.ext.core.entity.group.GroupBuyProductEntity;
import com.mb.ext.core.entity.group.GroupBuyUserEntity;
import com.mb.ext.core.entity.merchant.MerchantBalanceEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.merchant.MerchantStatementEntity;
import com.mb.ext.core.entity.merchant.MerchantUserEntity;
import com.mb.ext.core.entity.order.OrderEntity;
import com.mb.ext.core.entity.order.OrderProductEntity;
import com.mb.ext.core.entity.point.PointProductEntity;
import com.mb.ext.core.entity.point.PointProductSkuAttrValueEntity;
import com.mb.ext.core.entity.point.PointProductSkuEntity;
import com.mb.ext.core.entity.point.PointSettingEntity;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.entity.product.ProductSkuAttrValueEntity;
import com.mb.ext.core.entity.product.ProductSkuEntity;
import com.mb.ext.core.entity.profit.ProfitDistributionEntity;
import com.mb.ext.core.entity.seckill.SecKillProductEntity;
import com.mb.ext.core.entity.shoppingcart.ShoppingCartEntity;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.AwardService;
import com.mb.ext.core.service.GroupBuyService;
import com.mb.ext.core.service.NoteService;
import com.mb.ext.core.service.OrderService;
import com.mb.ext.core.service.PointService;
import com.mb.ext.core.service.ProductService;
import com.mb.ext.core.service.ShoppingCartService;
import com.mb.ext.core.service.VoucherService;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.ChartDTO;
import com.mb.ext.core.service.spec.OrderSearchDTO;
import com.mb.ext.core.service.spec.ShoppingCartDTO;
import com.mb.ext.core.service.spec.UserAwardDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.WechatRefundRequest;
import com.mb.ext.core.service.spec.WechatRefundResponse;
import com.mb.ext.core.service.spec.group.GroupBuyDTO;
import com.mb.ext.core.service.spec.group.GroupBuyUserDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.order.CourierDTO;
import com.mb.ext.core.service.spec.order.GroupBuyOrderDTO;
import com.mb.ext.core.service.spec.order.OrderDTO;
import com.mb.ext.core.service.spec.order.OrderProductDTO;
import com.mb.ext.core.service.spec.order.PointOrderDTO;
import com.mb.ext.core.service.spec.order.SecKillOrderDTO;
import com.mb.ext.core.service.spec.order.UserDeliveryAddressDTO;
import com.mb.ext.core.service.spec.point.PointProductDTO;
import com.mb.ext.core.service.spec.point.PointProductSkuDTO;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.ext.core.service.spec.product.ProductSkuDTO;
import com.mb.ext.core.util.HttpUtils;
import com.mb.ext.core.util.PaymentUtil;
import com.mb.ext.core.util.RedisUtil;
import com.mb.ext.core.util.SMSSenderUtil;
import com.mb.ext.core.util.WechatUtil;
import com.mb.ext.msg.WxSuscribeMessageSender;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.service.AbstractService;
import com.mb.framework.util.DateTimeUtil;
import com.mb.framework.util.log.LogHelper;

@Service
@Qualifier("OrderService")
public class OrderServiceImpl extends AbstractService implements
		OrderService<BodyDTO> {

	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;

	@Autowired
	@Qualifier("pointSettingDAO")
	private PointSettingDAO pointSettingDAO;
	
	@Autowired
	private UserInventoryDAO userInventoryDAO;
	
	@Autowired
	private WxSuscribeMessageSender wxSuscribeMessageSender;
	
	@Autowired
	@Qualifier("userStatementDAO")
	private UserStatementDAO userStatementDAO;
	
	@Autowired
	@Qualifier("userPointStatementDAO")
	private UserPointStatementDAO userPointStatementDAO;
	
	@Autowired
	private SMSSenderUtil smsSenderUtil;
	
	@Autowired
	private VoucherService voucherService;
	
	@Autowired
	@Qualifier("userAuthDAO")
	private UserAuthDAO userAuthDAO;
	
	@Autowired
	@Qualifier("merchantUserDAO")
	private MerchantUserDAO merchantUserDAO;
	
	@Autowired
	@Qualifier("userCouponDAO")
	private UserCouponDAO userCouponDAO;
	
	@Autowired
	@Qualifier("userVoucherDAO")
	private UserVoucherDAO userVoucherDAO;
	
	@Autowired
	@Qualifier("userInventoryHistoryDAO")
	private UserInventoryHistoryDAO userInventoryHistoryDAO;
	
	@Autowired
	@Qualifier("userAwardDAO")
	private UserAwardDAO userAwardDAO;
	
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
	@Qualifier("orderImageDAO")
	private OrderImageDAO orderImageDAO;

	@Autowired
	@Qualifier("orderDAO")
	private OrderDAO orderDAO;
	
	@Autowired
	@Qualifier("orderProductDAO")
	private OrderProductDAO orderProductDAO;
	
	@Autowired
	@Qualifier("secKillProductDAO")
	private SecKillProductDAO secKillProductDAO;
	
	@Autowired
	@Qualifier("groupBuyProductDAO")
	private GroupBuyProductDAO groupBuyProductDAO;
	
	@Autowired
	@Qualifier("groupBuyDAO")
	private GroupBuyDAO groupBuyDAO;
	
	@Autowired
	@Qualifier("groupBuyUserDAO")
	private GroupBuyUserDAO groupBuyUserDAO;
	
	@Autowired
	@Qualifier("orderAfterSaleDAO")
	private OrderAfterSaleDAO orderAfterSaleDAO;

	@Autowired
	@Qualifier("userDeliveryAddressDAO")
	private UserDeliveryAddressDAO userDeliveryAddressDAO;
	
	@Autowired
	@Qualifier("profitDistributionDAO")
	private ProfitDistributionDAO profitDistributionDAO;
	
	@Autowired
	@Qualifier("ShoppingCartService")
	private ShoppingCartService shoppingCartService;
	
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
	@Qualifier("pointProductDAO")
	private PointProductDAO pointProductDAO;
	
	@Autowired
	@Qualifier("pointProductSkuDAO")
	private PointProductSkuDAO pointProductSkuDAO;
	
	@Autowired
	@Qualifier("pointProductSkuAttrValueDAO")
	private PointProductSkuAttrValueDAO pointProductSkuAttrValueDAO;
	
	@Autowired
	@Qualifier("NoteService")
	private NoteService noteService;
	
	@Autowired
	@Qualifier("GroupBuyService")
	private GroupBuyService groupBuyService;
	
	@Autowired
	@Qualifier("ProductService")
	private ProductService<BodyDTO> productService;
	
	@Autowired
	@Qualifier("PointService")
	private PointService<BodyDTO> pointService;
	
	@Autowired
	@Qualifier("AwardService")
	private AwardService awardService;
	
	@Autowired
	private PaymentUtil paymentUtil;
	
	@Autowired
	private WechatUtil wechatUtil;
	
	@Autowired
	private RedisUtil redisUtil;
	
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	public String getOrderNo(String userId) throws BusinessException {
		try {
			//订单号 = 'GM'+时间戳+用户编号+4位任意数字
			String orderNo = OrderConstants.ORDER_TYPE_GM + DateTimeUtil.formatDateByYYMMDDHHmm(new Date())
			+ userId		
			+ RandomStringUtils.randomNumeric(4);
			return orderNo;
		}catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}
	
	public String getpOrderNo(String userId) throws BusinessException {
		try {
			//主订单号 = 'MN'+时间戳+用户编号+4位任意数字
			String orderNo = OrderConstants.ORDER_TYPE_MN + DateTimeUtil.formatDateByYYMMDDHHmm(new Date())
			+ userId		
			+ RandomStringUtils.randomNumeric(4);
			return orderNo;
		}catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}
	
	@Override
	public void commentOrder(OrderDTO orderDTO) throws BusinessException {
		try{
			OrderEntity orderEntity = orderDAO.getOrderByOrderNo(orderDTO.getOrderNo());
			orderEntity.setOrderComment(orderDTO.getOrderComment());
			orderDAO.updateOrder(orderEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}catch(Exception e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
			
	}
	
	@Override
	public void payOrder(OrderDTO orderDTO) throws BusinessException {
		try{
			
			OrderEntity orderEntity = orderDAO.getOrderByOrderNo(orderDTO.getOrderNo());
			//检查订单状态是否已经支付
			if(!OrderConstants.ORDER_STATUS_NOT_PAYED.equals(orderEntity.getOrderStatus())) {
				throw new BusinessException(BusinessErrorCode.ORDER_PAY_DUPLICATE);
			}
			
			//如果是余额支付, 检查余额是否足够，并更新用户余额
			BigDecimal actualAmount = orderEntity.getActualAmount();
			if(OrderConstants.ORDER_PAYMENT_METHOD_BALANCE.equals(orderDTO.getPaymentMethod())) {
				UserBalanceEntity balanceEntity = orderEntity.getUserEntity().getUserBalanceEntity();
				if(balanceEntity == null || balanceEntity.getAvailableBalance().compareTo(actualAmount)<0) {
					throw new BusinessException(BusinessErrorCode.USER_BALANCE_INSUFFICIENT);
				}
				BigDecimal balanceBefore = balanceEntity.getAvailableBalance();
				BigDecimal balanceAfter = balanceBefore.subtract(actualAmount);
				balanceEntity.setAvailableBalance(balanceAfter);
				userBalanceDAO.updateUserBalance(balanceEntity);
				
				UserStatementEntity userStatementEntity = new UserStatementEntity();
				userStatementEntity.setUserEntity(orderEntity.getUserEntity());
				userStatementEntity.setBalanceBefore(balanceBefore);
				userStatementEntity.setBalanceAfter(balanceAfter);
				userStatementEntity.setTransactionCode(orderEntity.getOrderNo());
				userStatementEntity.setTransactionAmount(actualAmount);
				userStatementEntity.setTransactionDesc("购买商品余额支付");
				userStatementEntity.setTransactionTime(new Date());
				userStatementEntity.setTransactionType(FundConstants.USER_STATEMENT_TRANSACTION_TYPE_ORDER_PAY_BALANCE);
				userStatementEntity.setCreateBy(orderEntity.getUserEntity().getId());
				userStatementEntity.setUpdateBy(orderEntity.getUserEntity().getId());
				userStatementDAO.createUserStatement(userStatementEntity);
			}
			//快递配送
			if(OrderConstants.ORDER_DELIVERY_TYPE_EXPRESS.equals(orderEntity.getDeliveryType()))
				orderEntity.setOrderStatus(OrderConstants.ORDER_STATUS_NOT_DELIVERIED);
			//同城配送
			else if(OrderConstants.ORDER_DELIVERY_TYPE_CITY.equals(orderEntity.getDeliveryType()))
				orderEntity.setOrderStatus(OrderConstants.ORDER_STATUS_NOT_DELIVERIED);
			//门店自提
			else if(OrderConstants.ORDER_DELIVERY_TYPE_PICK.equals(orderEntity.getDeliveryType()))
				orderEntity.setOrderStatus(OrderConstants.ORDER_STATUS_NOT_DELIVERIED);
			else
				orderEntity.setOrderStatus(OrderConstants.ORDER_STATUS_NOT_DELIVERIED);
			orderEntity.setPaymentMethod(orderDTO.getPaymentMethod());
			orderEntity.setPaymentTime(new Date());
			orderEntity.setTransactionId(orderDTO.getTransactionId());
			orderDAO.updateOrder(orderEntity);
			
			
			//如该订单使用优惠券, 标记优惠券状态为已使用
			UserCouponEntity userCouponEntity = orderEntity.getUserCouponEntity();
			if(userCouponEntity != null) {
				userCouponEntity.setUsed(true);
				userCouponEntity.setUseTime(new Date());
				userCouponDAO.updateUserCoupon(userCouponEntity);
			}
			
			//普通订单, 电子卡券商品订单生成卡券(当前电子卡券不参与拼团)
			if("0".equals(orderEntity.getOrderType())) {
				afterPayVoucherOrder(orderEntity);
			}
			
			//如果是团购订单, 处理拼团逻辑
			if("2".equals(orderEntity.getOrderType())) {
				afterPayGroupBuyOrder(orderDTO.getOrderNo());
			}
			
			//如果是积分订单, 处理积分逻辑
			if("3".equals(orderEntity.getOrderType())) {
				deductUserPoint(orderEntity.getUserEntity(), orderEntity.getActualPoint(), orderEntity.getOrderNo());
			}
			if(OrderConstants.ORDER_PAYMENT_METHOD_WECHAT.equals(orderDTO.getPaymentMethod())) {
				String productName = "";
				List<OrderProductEntity> productList =  orderEntity.getOrderProductList();
				for (OrderProductEntity orderProductEntity : productList) {
					productName = productName + orderProductEntity.getProductName();
				}
				productName = productName.length()>20?productName.substring(0, 16)+"...":productName;
				
				//发送订单支付模板消息
				wxSuscribeMessageSender.sendOrderPayedMsg(paymentUtil.getWechatAppIdMiniProgram(), paymentUtil.getWechatAppSecretMiniProgram(), orderEntity.getUserEntity().getOpenId(), orderEntity.getOrderNo(),new SimpleDateFormat("yyyy年MM月dd日").format(new Date()), productName, String.valueOf(orderEntity.getActualAmount()), org.apache.commons.lang3.StringUtils.isEmpty(orderEntity.getMemo())?" ":orderEntity.getMemo());
			}
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}catch(BusinessException e){
			throw e;
		}
			
	}
	
	private void afterPayGroupBuyOrder(String orderNo) throws DAOException {
		GroupBuyUserEntity groupBuyUserEntity = groupBuyUserDAO.getGroupBuyUserByOrder(orderNo);
		GroupBuyEntity groupBuyEntity = groupBuyUserEntity.getGroupBuyEntity();
		int minUserCount = groupBuyEntity.getGroupBuyProductEntity().getGroupBuyDefEntity().getMinUserCount();
		int userCount = groupBuyUserDAO.getGroupBuyUserCount(groupBuyEntity);
		if(userCount>=minUserCount) {
			groupBuyEntity.setStatus(GroupBuyConstants.STATUS_COMPLETED);
			groupBuyDAO.updateGroupBuy(groupBuyEntity);
		}
	}
	
	private void afterPayVoucherOrder(OrderEntity orderEntity) throws BusinessException {
		try {
			List<OrderProductEntity> productList = orderEntity.getOrderProductList();
			//订单为电子卡券商品(每个订单只包含一种商品类型)
			if(ProductConstants.PRODUCT_TYPE_VOUCHER.equals(productList.get(0).getProductEntity().getProductType())) {
				for (OrderProductEntity orderProductEntity : productList) {
					ProductEntity productEntity = orderProductEntity.getProductEntity();
					UserEntity userEntity = orderEntity.getUserEntity();
					int productUnit = orderProductEntity.getProductUnit();
					for(int i=1;i<=productUnit;i++) {
						UserVoucherEntity userVoucherEntity = new UserVoucherEntity();
						userVoucherEntity.setUserEntity(userEntity);
						userVoucherEntity.setOrderEntity(orderEntity);
						userVoucherEntity.setProductEntity(productEntity);
						userVoucherEntity.setMerchantEntity(productEntity.getMerchantEntity());
						userVoucherEntity.setName(productEntity.getProductName());
						userVoucherEntity.setMemo(productEntity.getProductDesc());
						userVoucherEntity.setReceiveTime(new Date());
						userVoucherEntity.setUsed(false);
						 //券码时间(秒)+会员ID+序列号
						String voucherCode = DateTimeUtil.formatDateByYYMMDDHHmmss(new Date())+userEntity.getId()+i;
						userVoucherEntity.setVoucherCode(voucherCode);
						if(CouponConstants.VALID_TYPE_FIXDATE.equals(productEntity.getValidType())) {
							userVoucherEntity.setValidStartDate(productEntity.getValidStartDate());
							userVoucherEntity.setValidEndDate(productEntity.getValidEndDate());
						}else if(CouponConstants.VALID_TYPE_RECEIVE_AFTER.equals(productEntity.getValidType())) {
							userVoucherEntity.setValidStartDate(new Date());
							userVoucherEntity.setValidEndDate(DateUtils.addDays(new Date(), productEntity.getValidDays()));
						}
						//生成二维码
						String qrCodeUrl = voucherService.generateVoucherBarCode(voucherCode);
						userVoucherEntity.setQrCodeUrl(qrCodeUrl);
						userVoucherEntity.setCreateBy(orderEntity.getCreateBy());
						userVoucherEntity.setUpdateBy(orderEntity.getUpdateBy());
						userVoucherDAO.createUserVoucher(userVoucherEntity);
					}
				}
				OrderDTO orderDTO = new OrderDTO();
				orderDTO.setOrderNo(orderEntity.getOrderNo());
				confirmOrder(orderDTO);
			}
		}catch(DAOException e) {
			logger.error(e.getMessage());
			throw new BusinessException(e);
		}catch(BusinessException e) {
			throw e;
		}
	}
	
	@Override
	public void confirmOrder(OrderDTO orderDTO) throws BusinessException {
		try{
			OrderEntity orderEntity = orderDAO.getOrderByOrderNo(orderDTO.getOrderNo());
			orderEntity.setOrderStatus(OrderConstants.ORDER_STATUS_COMPLETED);
			orderEntity.setConfirmTime(new Date());
			//更新售后期最晚时间
			SettingEntity settingEntity = settingDAO.getSettingByName("ORDER_AFTERSALE_PERIOD");
			int afterSalePeriod = Integer.valueOf(settingEntity.getValue());
			orderEntity.setAfterSaleDeadLineTime(DateUtils.addDays(orderEntity.getConfirmTime(),afterSalePeriod));
			orderDAO.updateOrder(orderEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}catch(Exception e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public void accountOrder(OrderDTO orderDTO) throws BusinessException {
		try{
			OrderEntity orderEntity = orderDAO.getOrderByOrderNo(orderDTO.getOrderNo());
			//订单已结算
			orderEntity.setAccounted(true);
			orderDAO.updateOrder(orderEntity);
			
			MerchantEntity merchantEntity = orderEntity.getMerchantEntity();
			if(merchantEntity != null) {
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
				BigDecimal availableBalanceAfter = availableBalanceBefore.add(orderEntity.getActualAmount());
				BigDecimal totalBalanceAfter = totalBalanceBefore.add(orderEntity.getActualAmount());
				merchantBalanceEntity.setAvailableBalance(availableBalanceAfter);
				merchantBalanceEntity.setTotalBalance(totalBalanceAfter);
				merchantBalanceEntity.setUpdateBy(orderEntity.getUserEntity().getId());
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
			
			//检查是否开启分销
			ProfitDistributionEntity profitDistributionEntity = profitDistributionDAO.getProfitDistribution();
			if(profitDistributionEntity != null && profitDistributionEntity.isDistributionEnabled()) {
				//开启三级分销则使用三级分销计算佣金
				awardService.processDistribution(orderDTO.getOrderNo());
			}else {
				//未开启三级分销则按会员等级的逻辑计算佣金
				awardService.processAward(orderDTO.getOrderNo());
			}

			//检查是否赠送积分
			PointSettingEntity pointSettingEntity = pointSettingDAO.getPointSetting();
			if(pointSettingEntity != null && pointSettingEntity.isEnabled()){
				//没多少金额赠送多少积分
				BigDecimal amount = pointSettingEntity.getAmount();
				int point = pointSettingEntity.getPoint();
				int givenPoint = 0;
				BigDecimal actualAmount = orderEntity.getActualAmount();
				BigDecimal decimalTimes = actualAmount.divide(amount, 2, BigDecimal.ROUND_DOWN);
				int intTimes = decimalTimes.intValue();
				givenPoint = intTimes * point;
				//更新会员积分余额
				if(givenPoint > 0) {
					UserEntity userEntity = orderEntity.getUserEntity();
					UserBalanceEntity userBalanceEntity = userEntity.getUserBalanceEntity();
					int pointBefore = userBalanceEntity.getAvailablePoint();
					int pointAfter = pointBefore + givenPoint;
					userBalanceEntity.setAvailablePoint(pointAfter);
					userBalanceDAO.updateUserBalance(userBalanceEntity);
					//更新会员积分明细
					UserPointStatementEntity userStatementEntity = new UserPointStatementEntity();
					userStatementEntity.setUserEntity(userEntity);
					userStatementEntity.setTransactionTime(new Date());
					userStatementEntity.setTransactionType(PointConstants.TRAN_TYPE_ORDER);
					userStatementEntity.setTransactionDesc("消费"+actualAmount+"元获取" + givenPoint + "积分");
					userStatementEntity.setTransactionPoint(givenPoint);
					userStatementEntity.setPointBefore(pointBefore);
					userStatementEntity.setPointAfter(pointAfter);
					userStatementEntity.setCreateBy(userEntity.getId());
					userStatementEntity.setUpdateBy(userEntity.getId());
					userPointStatementDAO.createUserPointStatement(userStatementEntity);
				}
			}
			
			//会员升级处理
			UserDTO userDTO = new UserDTO();
			userDTO.setUserUuid(orderEntity.getUserEntity().getUserUuid());
			awardService.processUpgrade(userDTO);
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}catch(Exception e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public void editOrder(OrderDTO orderDTO) throws BusinessException {
		try{
			OrderEntity orderEntity = orderDAO.getOrderByOrderNo(orderDTO.getOrderNo());
			orderEntity.setDeliveryProvince(orderDTO.getDeliveryProvince());
			orderEntity.setDeliveryCity(orderDTO.getDeliveryCity());
			orderEntity.setDeliveryArea(orderDTO.getDeliveryArea());
			orderEntity.setDeliveryStreet(orderDTO.getDeliveryStreet());
			orderEntity.setDeliveryName(orderDTO.getDeliveryName());
			orderEntity.setDeliveryContactNo(orderDTO.getDeliveryContactNo());
			orderEntity.setDeliveryZipcode(orderDTO.getDeliveryZipcode());
			orderEntity.setActualPoint(orderDTO.getActualPoint());
			orderEntity.setActualAmount(orderDTO.getActualAmount());
			orderDAO.updateOrder(orderEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}catch(Exception e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public void deliverOrder(OrderDTO orderDTO) throws BusinessException {
		try{
			OrderEntity orderEntity = orderDAO.getOrderByOrderNo(orderDTO.getOrderNo());
			String orderType = orderEntity.getOrderType();
			//团购订单, 拼团未成功不能发货
			if("2".equals(orderType)) {
				GroupBuyEntity groupBuyEntity = orderEntity.getGroupBuyEntity();
				if(groupBuyEntity != null) {
					if(!GroupBuyConstants.STATUS_COMPLETED.equals(groupBuyEntity.getStatus())){
						throw new BusinessException(BusinessErrorCode.ORDER_GROUP_BUY_NOT_COMPLETED);
					}
				}
			}
			orderEntity.setOrderStatus(OrderConstants.ORDER_STATUS_DELIVERIED);
			orderEntity.setDeliveryTime(new Date());
			String deliveryType = orderEntity.getDeliveryType();
			if(OrderConstants.ORDER_DELIVERY_TYPE_EXPRESS.equals(deliveryType)) {
				orderEntity.setCourierName(orderDTO.getCourierName());
				orderEntity.setCourierNo(orderDTO.getCourierNo());
			}else if(OrderConstants.ORDER_DELIVERY_TYPE_CITY.equals(deliveryType)) {
				orderEntity.setShopperName(orderDTO.getShopperName());
				orderEntity.setShopperMobileNo(orderDTO.getShopperMobileNo());
				orderEntity.setShopperSex(orderDTO.getShopperSex());
				orderEntity.setShopperPhoto(orderDTO.getShopperPhoto());
			}
			orderDAO.updateOrder(orderEntity);
			
			//发送快递订单发货模板消息
			if(OrderConstants.ORDER_DELIVERY_TYPE_EXPRESS.equals(deliveryType)) {
				String productName = "";
				List<OrderProductEntity> productList =  orderEntity.getOrderProductList();
				for (OrderProductEntity orderProductEntity : productList) {
					productName = productName + orderProductEntity.getProductName();
				}
				productName = productName.length()>20?productName.substring(0, 16)+"...":productName;
				wxSuscribeMessageSender.sendOrderDeliveredMsg(paymentUtil.getWechatAppIdMiniProgram(), paymentUtil.getWechatAppSecretMiniProgram(), orderEntity.getUserEntity().getOpenId(), orderEntity.getOrderNo(),new SimpleDateFormat("yyyy年MM月dd日").format(new Date()), productName, orderEntity.getCourierName(), orderEntity.getCourierNo());
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public void cancelOrder(OrderDTO orderDTO) throws BusinessException {
		try{
			
			OrderEntity orderEntity = orderDAO.getOrderByOrderNo(orderDTO.getOrderNo());
			orderEntity.setCancelTime(new Date());
			//订单已经是取消状态, 直接返回
			if(OrderConstants.ORDER_STATUS_CANCELLED.equals(orderEntity.getOrderStatus())) {
				return;
			}
			//订单未付款, 可以直接取消
			else if(OrderConstants.ORDER_STATUS_NOT_PAYED.equals(orderEntity.getOrderStatus())) {
				orderEntity.setOrderStatus(OrderConstants.ORDER_STATUS_CANCELLED);
				orderDAO.updateOrder(orderEntity);
			}
			//订单已付款, 未发货
			else if(OrderConstants.ORDER_STATUS_NOT_DELIVERIED.equals(orderEntity.getOrderStatus())) {
				//支付金额原路退回
				orderCancelRefund(orderEntity.getOrderNo());
				orderEntity.setOrderStatus(OrderConstants.ORDER_STATUS_CANCELLED);
				orderDAO.updateOrder(orderEntity);
			}
			//订单已完成(评价或未评价)
			else if(OrderConstants.ORDER_STATUS_COMPLETED.equals(orderEntity.getOrderStatus())
					||OrderConstants.ORDER_STATUS_EVALUATED.equals(orderEntity.getOrderStatus())) {
				throw new BusinessException(BusinessErrorCode.ORDER_COMPLETED_CANCEL);
			}
			//订单已发货
			else if(OrderConstants.ORDER_STATUS_DELIVERIED.equals(orderEntity.getOrderStatus())) {
				throw new BusinessException(BusinessErrorCode.ORDER_NOT_CANCEL);
			}
        	
			
			//商品库存更新
			List<OrderProductEntity> orderProductEntityList = orderEntity.getOrderProductList();
			for (Iterator<OrderProductEntity> iterator = orderProductEntityList.iterator(); iterator.hasNext();) {
				OrderProductEntity orderProductEntity = iterator.next();
				ProductEntity productEntity = orderProductEntity.getProductEntity();
				int unit = orderProductEntity.getProductUnit();
				//普通订单
				if("0".equals(orderEntity.getOrderType())) {
					productEntity.setTotalUnit(productEntity.getTotalUnit()+unit);
					productEntity.setSoldUnit(productEntity.getSoldUnit()-unit);
					productDAO.updateProduct(productEntity);
					ProductSkuEntity productSkuEntity = orderProductEntity.getProductSkuEntity();
					if(productEntity.isSkuEnabled()) {
						productSkuEntity.setSkuTotalUnit(productSkuEntity.getSkuTotalUnit()+unit);
						productSkuEntity.setSkuSoldUnit(productSkuEntity.getSkuSoldUnit()-unit);
						productSkuDAO.updateProductSku(productSkuEntity);
					}
				}
				//秒杀订单
				else if("1".equals(orderEntity.getOrderType())) {
					secKillProductDAO.incrStock(orderProductEntity.getSecKillProductEntity().getSecKillProductUuid(), unit);
				}
				//团购订单
				else if("2".equals(orderEntity.getOrderType())) {
					groupBuyProductDAO.incrStock(orderProductEntity.getGroupBuyProductEntity().getGroupBuyProductUuid(), unit);
				}
			}
			
			//取消订单则标记订单优惠券为未使用
			UserCouponEntity couponEntity = orderEntity.getUserCouponEntity();
			if(couponEntity != null) {
				//将优惠券从订单移除
				orderEntity.setUserCouponEntity(null);
				orderDAO.updateOrder(orderEntity);
				//标记优惠券为未使用
				couponEntity.setUsed(false);
				userCouponDAO.updateUserCoupon(couponEntity);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public void updateRefundStatus(String orderNo, String refundStatus) throws BusinessException {
		
		try {
			OrderEntity orderEntity = orderDAO.getOrderByOrderNo(orderNo);
			orderEntity.setRefundMsg(refundStatus);
			orderDAO.updateOrder(orderEntity);
		}catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		
	}
	
	public void orderCancelRefund(String orderNo) throws BusinessException {
		
		try {
			OrderEntity orderEntity = orderDAO.getOrderByOrderNo(orderNo);
			if(OrderConstants.ORDER_PAYMENT_METHOD_WECHAT.equals(orderEntity.getPaymentMethod())){
				wechatRefund(orderNo);
			}else if(OrderConstants.ORDER_PAYMENT_METHOD_ALIPAY.equals(orderEntity.getPaymentMethod())){
				alipayRefund(orderNo);
			}
			else if(OrderConstants.ORDER_PAYMENT_METHOD_BALANCE.equals(orderEntity.getPaymentMethod())){
				balanceRefund(orderNo);
			}
		}catch(BusinessException e) {
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		
	}
	
	public void alipayRefund(String orderNo) throws BusinessException {
		
		try {
			OrderEntity orderEntity = orderDAO.getOrderByOrderNo(orderNo);
			AlipayClient alipayClient = new DefaultAlipayClient(AlipayConstants.ALIPAY_URL, paymentUtil.getAlipayAppId(),paymentUtil.getAlipayPrivateKey(), AlipayConstants.ALIPAY_FORMAT,AlipayConstants.ALIPAY_CHARSET, paymentUtil.getAlipayPublicKey(), AlipayConstants.ALIPAY_SIGNTYPE);
			AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
			String pOrderNo = orderEntity.getpOrderNo();
			String out_trade_no = (StringUtils.isEmpty(pOrderNo)?orderNo:pOrderNo);
			request.setBizContent("{" +
					"\"out_trade_no\":\""+out_trade_no+"\"," +
					"\"trade_no\":\""+orderEntity.getTransactionId()+"\"," +
					"\"refund_amount\":"+orderEntity.getActualAmount().setScale(2)+"," +
					"\"refund_currency\":\"CNY\"," +
					"\"out_request_no\":\""+RandomStringUtils.randomAlphanumeric(10).toUpperCase()+"\"" +
					"  }");
			AlipayTradeRefundResponse response = alipayClient.execute(request);
			if(response.isSuccess()){
				logger.info("支付宝退款申请成功" + ",code="+response.getCode() +",msg=" + response.getMsg());
				orderEntity.setRefundId(response.getCode());
				orderEntity.setRefundMsg(response.getMsg());
				orderDAO.updateOrder(orderEntity);
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
	
	public void wechatRefund(String orderNo) throws BusinessException {
		
		try {
			OrderEntity orderEntity = orderDAO.getOrderByOrderNo(orderNo);
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
			refundRequest.setRefund_fee(orderEntity.getActualAmount().multiply(new BigDecimal(100)).intValue());
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
				orderEntity.setRefundId(refundResponse.getRefund_id());
				orderEntity.setRefundMsg("已提交微信退款申请");
				orderDAO.updateOrder(orderEntity);
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
	
	public void balanceRefund(String orderNo) throws BusinessException {
		
		try {
			OrderEntity orderEntity = orderDAO.getOrderByOrderNo(orderNo);
			UserEntity userEntity = orderEntity.getUserEntity();
			UserBalanceEntity userBalanceEntity = userEntity.getUserBalanceEntity();
			
			//更新资金余额
			BigDecimal availableBalanceBefore = userBalanceEntity.getAvailableBalance();
			BigDecimal availableBalanceAfter = availableBalanceBefore.add(orderEntity.getActualAmount());
			userBalanceEntity.setAvailableBalance(availableBalanceAfter);
			userBalanceDAO.updateUserBalance(userBalanceEntity)	;
			
			//更新对账单
			UserStatementEntity statementEntity = new UserStatementEntity();
			statementEntity.setUserEntity(userEntity);
			statementEntity.setTransactionAmount(orderEntity.getActualAmount());
			statementEntity.setTransactionType(FundConstants.USER_STATEMENT_TRANSACTION_TYPE_REFUND);
			statementEntity.setTransactionCode(orderNo);
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
	public void deleteOrder(OrderDTO orderDTO) throws BusinessException {
		try{
			
			OrderEntity orderEntity = orderDAO.getOrderByOrderNo(orderDTO.getOrderNo());
			//只有已取消和已完成的订单可以删除
			if(!OrderConstants.ORDER_STATUS_CANCELLED.equals(orderEntity.getOrderStatus())
					&& !OrderConstants.ORDER_STATUS_EVALUATED.equals(orderEntity.getOrderStatus())) {
				throw new BusinessException(BusinessErrorCode.ORDER_NOT_DELETE);
			}
			List<OrderProductEntity> orderProductList = orderEntity.getOrderProductList();
			orderEntity.setOrderProductList(null);
			for (Iterator<OrderProductEntity> iterator = orderProductList.iterator(); iterator.hasNext();) {
				OrderProductEntity orderProductEntity = iterator.next();
				orderProductDAO.deleteOrderProduct(orderProductEntity);
			}
			orderDAO.deleteOrder(orderEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}catch(Exception e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	/*
	 * 更新物流信息
	 */
	@Override
	public void importLogistics(OrderDTO orderDTO) throws BusinessException {
		// TODO Auto-generated method stub
		try {
			OrderEntity orderEntity = orderDAO.getOrderByOrderNo(orderDTO.getOrderNo());
			orderEntity.setOrderStatus(OrderConstants.ORDER_STATUS_DELIVERIED);
			orderEntity.setCourierName(orderDTO.getCourierName());
			orderEntity.setCourierNo(orderDTO.getCourierNo());
			
//			String productName = orderEntity.getProductName();
			UserEntity userEntity = orderEntity.getUserEntity();
			UserDTO userDTO = new UserDTO();
			userDTO.setUserUuid(userEntity.getUserUuid());
			
//			noteService.sendNotification(userDTO, "delivered", new String[]{productName});
			
			orderDAO.updateOrder(orderEntity);
		} catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}catch(Exception e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public List<OrderDTO> splitOrder(OrderDTO orderDTO) throws BusinessException {
		List<OrderDTO> orderList = new ArrayList<OrderDTO>();
		UserDTO userDTO = orderDTO.getUserDTO();
		try {
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			if(userEntity == null) {
				throw new BusinessException(BusinessErrorCode.ORDER_USER_ERROR);
			}
			List<ShoppingCartDTO> shoppingCartDTOList = shoppingCartService.getShoppingCartByUuids(orderDTO.getShoppingCartUuidList()); 
			//将商品按商家分类, 分开计算运费
			Map<String, List<String>> merchantProductMap = new HashMap<String, List<String>>();
			for (ShoppingCartDTO shoppingCartDTO : shoppingCartDTOList) {
				String merchantUuid = shoppingCartDTO.getProductDTO().getMerchantDTO().getMerchantUuid();
				List<String> shoppinCartList = merchantProductMap.get(merchantUuid);
				if(shoppinCartList == null) {
					shoppinCartList = new ArrayList<String>();
				}
				shoppinCartList.add(shoppingCartDTO.getShoppingCartUuid());
				merchantProductMap.put(merchantUuid, shoppinCartList);
			}
			for (String key:merchantProductMap.keySet()) {
				List<String> list = merchantProductMap.get(key);
				OrderDTO sOrderDTO = new OrderDTO();
				sOrderDTO.setShoppingCartUuidList(list);
				orderList.add(sOrderDTO);
			}
			//拆分后主订单号
			String pOrderNo = getpOrderNo(userEntity.getId());
			orderDTO.setpOrderNo(pOrderNo);
		}catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		} catch (BusinessException e) {
			throw e;
		}
		return orderList;
	}
	
	@Override
	public String addOrder(OrderDTO orderDTO) throws BusinessException {
		try {
			OrderEntity orderEntity = new OrderEntity();
			orderEntity.setOrderType("0");	//普通订单
			//获取下单会员
			UserDTO userDTO = orderDTO.getUserDTO();
			if(userDTO == null) {
				throw new BusinessException(BusinessErrorCode.ORDER_USER_EMPTY);
			}
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			orderEntity.setUserEntity(userEntity);
			if(userEntity == null) {
				throw new BusinessException(BusinessErrorCode.ORDER_USER_ERROR);
			}
			//验证收货地址
			validateDeliveryAddress(orderDTO);
			
			//获取购物车ID列表, 通过购物车ID取到相关的订单商品
			List<String> shoppingCartUuidList = orderDTO.getShoppingCartUuidList();
			
			//校验订单数据完整性
			validateOrder(shoppingCartUuidList, orderEntity);
			
			//订单收件人数据
			populateAddressData(orderEntity, orderDTO);
			
			//优惠券优惠金额
			orderEntity.setDeductAmount(BigDecimal.valueOf(0));
			if(orderDTO.getUserCouponDTO() != null) {
				BigDecimal deductAmount = shoppingCartService.calculateOrderCouponAmount(shoppingCartUuidList, orderDTO.getUserCouponDTO().getCouponDTO().getCouponUuid());
				orderEntity.setDeductAmount(deductAmount);
				
				UserCouponEntity userCouponEntity = userCouponDAO.getUserCouponByUuid(orderDTO.getUserCouponDTO().getUserCouponUuid());
				orderEntity.setUserCouponEntity(userCouponEntity);
			}
			
			//运费金额
			BigDecimal freightAmount = BigDecimal.valueOf(0);
			//选择快递配送运费
			if(OrderConstants.ORDER_DELIVERY_TYPE_EXPRESS.equals(orderDTO.getDeliveryType()))
				freightAmount = shoppingCartService.calculateOrderFreightAmount(shoppingCartUuidList,orderEntity.getDeliveryProvince(),orderEntity.getDeliveryCity(),orderEntity.getDeliveryArea());
			//选择同城配送运费
			else if(OrderConstants.ORDER_DELIVERY_TYPE_CITY.equals(orderDTO.getDeliveryType()))
				freightAmount = shoppingCartService.calculateOrderDeliveryAmount(shoppingCartUuidList,orderDTO.getDeliveryLatitude(),orderDTO.getDeliveryLongitude());
			orderEntity.setFreightAmount(freightAmount==null?BigDecimal.valueOf(0):freightAmount);
			orderEntity.setActualAmount(orderEntity.getProductAmount().subtract(orderEntity.getDeductAmount()).add(orderEntity.getFreightAmount()));
			
			//订单其他数据
			populateOtherInfo(orderDTO, orderEntity);
			
			//创建订单
			orderDAO.addOrder(orderEntity);

			//创建订单商品关系
			addOrderProduct(orderEntity, shoppingCartUuidList);
			
			//商品库存更新
			updateProductStock(shoppingCartUuidList);
			
			//清空购物车
			clearCart(shoppingCartUuidList);
			
			//建立商家与会员的管理
			MerchantUserEntity merchantUserEntity = merchantUserDAO.getMerchantUser(orderEntity.getMerchantEntity(), userEntity);
			if(merchantUserEntity == null) {
				merchantUserEntity = new MerchantUserEntity();
				merchantUserEntity.setUserEntity(userEntity);
				merchantUserEntity.setMerchantEntity(orderEntity.getMerchantEntity());
				merchantUserDAO.createMerchantUser(merchantUserEntity);
			}
			
			//先将优惠券标记为已使用(如取消订单则重新标记为未使用)
			UserCouponEntity couponEntity = orderEntity.getUserCouponEntity();
			if(couponEntity != null) {
				couponEntity.setUsed(true);
				userCouponDAO.updateUserCoupon(couponEntity);
			}
			
			return orderEntity.getOrderNo();
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		} catch (BusinessException e) {
			throw e;
		}
	}
	
	
	@Override
	public String addSecKillOrder(SecKillOrderDTO orderDTO) throws BusinessException {
		try {
			OrderEntity orderEntity = new OrderEntity();
			orderEntity.setOrderType("1");	//秒杀订单
			//获取下单会员
			UserDTO userDTO = orderDTO.getOrderDTO().getUserDTO();
			if(userDTO == null) {
				throw new BusinessException(BusinessErrorCode.ORDER_USER_EMPTY);
			}
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			orderEntity.setUserEntity(userEntity);
			if(userEntity == null) {
				throw new BusinessException(BusinessErrorCode.ORDER_USER_ERROR);
			}
			//验证收货地址
			validateDeliveryAddress(orderDTO.getOrderDTO());
			
			//获取秒杀商品及规格等数据
			SecKillProductEntity secKillProduct = secKillProductDAO.getSecKillByUuid(orderDTO.getSecKillProduct().getSecKillProductUuid());
			ProductEntity productEntity = productDAO.getProductByUuid(orderDTO.getProductDTO().getProductUuid());
			ProductDTO productDTO = productService.getProductByUuid(orderDTO.getProductDTO().getProductUuid());
			ProductSkuEntity productSkuEntity = null;
			if(orderDTO.getProductSkuDTO() != null && !StringUtils.isEmpty(orderDTO.getProductSkuDTO().getProductSkuUuid())) {
				productSkuEntity = productSkuDAO.getProductSkuByUuid(orderDTO.getProductSkuDTO().getProductSkuUuid());
			}
			orderEntity.setMerchantEntity(productEntity.getMerchantEntity());
			
			//校验库存
			if(orderDTO.getOrderDTO().getProductUnit()>secKillProduct.getStock()) {
				throw new BusinessException(BusinessErrorCode.PRODUCT_STOCK_INSUFFICIENT);
			}
			
			//校验秒杀订单资格ID
			Object quanlificationId = redisUtil.get(userEntity.getUserUuid()+"-"+orderDTO.getSecKillProduct().getSecKillProductUuid());
			if(quanlificationId == null || !orderDTO.getQuanlificationId().equals((String)quanlificationId)){
				throw new BusinessException(BusinessErrorCode.SECKILL_ORDER_QUANLIFICATION_INVALID);
			}
			
			//订单收件人数据
			populateAddressData(orderEntity, orderDTO.getOrderDTO());
			
			//设置金额及商品数量
			orderEntity.setProductAmount(secKillProduct.getUnitPrice());
			orderEntity.setProductUnit(orderDTO.getOrderDTO().getProductUnit());
			orderEntity.setDeductAmount(BigDecimal.valueOf(0));
			
			//运费金额
			BigDecimal freightAmount = BigDecimal.valueOf(0);
			//选择快递配送(非门店自提)
			if(OrderConstants.ORDER_DELIVERY_TYPE_EXPRESS.equals(orderDTO.getOrderDTO().getDeliveryType()))
				freightAmount = shoppingCartService.calculateProductFreightAmount(productDTO, secKillProduct.getUnitPrice(), 1, orderEntity.getDeliveryProvince(),orderEntity.getDeliveryCity(),orderEntity.getDeliveryArea());
			orderEntity.setFreightAmount(freightAmount==null?BigDecimal.valueOf(0):freightAmount.setScale(2, BigDecimal.ROUND_HALF_DOWN));
			orderEntity.setActualAmount(orderEntity.getProductAmount().subtract(orderEntity.getDeductAmount()).add(orderEntity.getFreightAmount()).setScale(2,BigDecimal.ROUND_HALF_DOWN));
			
			//订单其他数据
			populateOtherInfo(orderDTO.getOrderDTO(), orderEntity);
			
			//创建订单
			orderDAO.addOrder(orderEntity);

			//创建订单商品关系
			addSingleOrderProduct(orderEntity, productEntity, productSkuEntity, null, secKillProduct, orderDTO.getOrderDTO().getProductUnit(), secKillProduct.getUnitPrice(), secKillProduct.getUnitPrice());
			
			//秒杀商品库存更新
			secKillProductDAO.decrStock(secKillProduct.getSecKillProductUuid(),orderDTO.getOrderDTO().getProductUnit());
			
			//先将优惠券标记为已使用(如取消订单则重新标记为未使用)
			UserCouponEntity couponEntity = orderEntity.getUserCouponEntity();
			if(couponEntity != null) {
				couponEntity.setUsed(true);
				userCouponDAO.updateUserCoupon(couponEntity);
			}
			
			return orderEntity.getOrderNo();
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}
	
	@Override
	public String addGroupBuyOrder(GroupBuyOrderDTO orderDTO) throws BusinessException {
		try {
			OrderEntity orderEntity = new OrderEntity();
			orderEntity.setOrderType("2");	//团购订单
			//获取下单会员
			UserDTO userDTO = orderDTO.getOrderDTO().getUserDTO();
			if(userDTO == null) {
				throw new BusinessException(BusinessErrorCode.ORDER_USER_EMPTY);
			}
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			orderEntity.setUserEntity(userEntity);
			if(userEntity == null) {
				throw new BusinessException(BusinessErrorCode.ORDER_USER_ERROR);
			}
			//验证收货地址
			validateDeliveryAddress(orderDTO.getOrderDTO());
			
			
			//获取团购商品及规格等数据
			GroupBuyProductEntity groupBuyProduct = groupBuyProductDAO.getGroupBuyByUuid(orderDTO.getGroupBuyProduct().getGroupBuyProductUuid());
			ProductEntity productEntity = productDAO.getProductByUuid(orderDTO.getProductDTO().getProductUuid());
			ProductDTO productDTO = productService.getProductByUuid(orderDTO.getProductDTO().getProductUuid());
			ProductSkuEntity productSkuEntity = null;
			if(orderDTO.getProductSkuDTO() != null && !StringUtils.isEmpty(orderDTO.getProductSkuDTO().getProductSkuUuid())) {
				productSkuEntity = productSkuDAO.getProductSkuByUuid(orderDTO.getProductSkuDTO().getProductSkuUuid());
			}
			orderEntity.setMerchantEntity(productEntity.getMerchantEntity());
			
			//校验库存
			if(orderDTO.getOrderDTO().getProductUnit()>groupBuyProduct.getStock()) {
				throw new BusinessException(BusinessErrorCode.PRODUCT_STOCK_INSUFFICIENT);
			}
			
			//订单收件人数据
			populateAddressData(orderEntity, orderDTO.getOrderDTO());
			
			//设置金额及商品数量
			orderEntity.setProductAmount(groupBuyProduct.getUnitPrice().multiply(BigDecimal.valueOf(orderDTO.getOrderDTO().getProductUnit())));
			orderEntity.setProductUnit(orderDTO.getOrderDTO().getProductUnit());
			orderEntity.setDeductAmount(BigDecimal.valueOf(0));
			
			//运费金额
			BigDecimal freightAmount = BigDecimal.valueOf(0);
			//选择快递配送(非门店自提)
			if(OrderConstants.ORDER_DELIVERY_TYPE_EXPRESS.equals(orderDTO.getOrderDTO().getDeliveryType()))
				freightAmount = shoppingCartService.calculateProductFreightAmount(productDTO, groupBuyProduct.getUnitPrice(), orderDTO.getOrderDTO().getProductUnit(), orderEntity.getDeliveryProvince(),orderEntity.getDeliveryCity(),orderEntity.getDeliveryArea());
			//选择同城配送运费
			else if(OrderConstants.ORDER_DELIVERY_TYPE_CITY.equals(orderDTO.getOrderDTO().getDeliveryType()))
				freightAmount = shoppingCartService.calculateProductDeliveryAmount(productDTO,groupBuyProduct.getUnitPrice(),orderDTO.getOrderDTO().getProductUnit(),orderDTO.getOrderDTO().getDeliveryLatitude(),orderDTO.getOrderDTO().getDeliveryLongitude());
			orderEntity.setFreightAmount(freightAmount==null?BigDecimal.valueOf(0):freightAmount.setScale(2, BigDecimal.ROUND_HALF_DOWN));
			orderEntity.setActualAmount(orderEntity.getProductAmount().subtract(orderEntity.getDeductAmount()).add(orderEntity.getFreightAmount()).setScale(2,BigDecimal.ROUND_HALF_DOWN));
			
			//订单其他数据
			populateOtherInfo(orderDTO.getOrderDTO(), orderEntity);
			
			//创建订单
			orderDAO.addOrder(orderEntity);

			//创建订单商品关系
			addSingleOrderProduct(orderEntity, productEntity, productSkuEntity, groupBuyProduct, null, orderDTO.getOrderDTO().getProductUnit(), groupBuyProduct.getUnitPrice(), groupBuyProduct.getUnitPrice());
			
			//团购商品库存更新
			groupBuyProductDAO.decrStock(groupBuyProduct.getGroupBuyProductUuid(),orderDTO.getOrderDTO().getProductUnit());
			
			//团购单逻辑
			GroupBuyUserDTO groupBuyUserDTO = new GroupBuyUserDTO();
			OrderDTO gOrderDTO = new OrderDTO();
			gOrderDTO.setOrderNo(orderEntity.getOrderNo());
			groupBuyUserDTO.setOrderDTO(gOrderDTO);
			GroupBuyDTO groupBuyDTO = new GroupBuyDTO();
			groupBuyDTO.setGroupBuyProductDTO(orderDTO.getGroupBuyProduct());
			groupBuyDTO.setGroupBuyUuid(orderDTO.getGroupBuyUuid());
			groupBuyUserDTO.setGroupBuyDTO(groupBuyDTO);
			groupBuyUserDTO.setUserDTO(userDTO);
			String groupBuyUuid = null;
			if(orderDTO.isOwner()) {
				groupBuyUuid = groupBuyService.addGroupBuy(groupBuyUserDTO);
			}
			else {
				groupBuyUuid = groupBuyService.joinGroupBuy(groupBuyUserDTO);
			}
			if(!StringUtils.isEmpty(groupBuyUuid)) {
				GroupBuyEntity groupBuyEntity = groupBuyDAO.getGroupBuyByUuid(groupBuyUuid);
				orderEntity.setGroupBuyEntity(groupBuyEntity);
				orderDAO.updateOrder(orderEntity);
			}
			
			//先将优惠券标记为已使用(如取消订单则重新标记为未使用)
			UserCouponEntity couponEntity = orderEntity.getUserCouponEntity();
			if(couponEntity != null) {
				couponEntity.setUsed(true);
				userCouponDAO.updateUserCoupon(couponEntity);
			}
			
			return orderEntity.getOrderNo();
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}
	
	@Override
	public String addPointOrder(PointOrderDTO pointOrderDTO) throws BusinessException {
		try {
			PointProductDTO pointProductDTO = pointOrderDTO.getProductDTO();
			PointProductSkuDTO pointProductSkuDTO = pointOrderDTO.getProductSkuDTO();
			int unit = pointOrderDTO.getOrderDTO().getProductUnit();
			OrderEntity orderEntity = new OrderEntity();
			orderEntity.setOrderType("3");	//积分订单
			//获取下单会员
			UserDTO userDTO = pointOrderDTO.getOrderDTO().getUserDTO();
			if(userDTO == null) {
				throw new BusinessException(BusinessErrorCode.ORDER_USER_EMPTY);
			}
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			orderEntity.setUserEntity(userEntity);
			if(userEntity == null) {
				throw new BusinessException(BusinessErrorCode.ORDER_USER_ERROR);
			}
			
			//获取积分商品及规格等数据
			PointProductEntity pointProduct = pointProductDAO.getProductByUuid(pointProductDTO.getProductUuid());
			BigDecimal unitPrice = pointProduct.getUnitPrice();
			int unitPoint = pointProduct.getUnitPoint();
			PointProductSkuEntity productSkuEntity = null;
			if(pointProductSkuDTO != null && !StringUtils.isEmpty(pointProductSkuDTO.getProductSkuUuid())) {
				productSkuEntity = pointProductSkuDAO.getProductSkuByUuid(pointProductSkuDTO.getProductSkuUuid());
				unitPrice = productSkuEntity.getSkuUnitPrice();
				unitPoint = productSkuEntity.getSkuUnitPoint();
			}
			if(unitPrice == null)	unitPrice = BigDecimal.valueOf(0);
			
			//检查会员积分是否足够
			int productPoint = unitPoint * unit;
			if(userEntity.getUserBalanceEntity().getAvailablePoint()<productPoint)
				throw new BusinessException(BusinessErrorCode.USER_POINT_INSUFFICIENT);
			
			
			//订单收件人数据
			populateAddressData(orderEntity, pointOrderDTO.getOrderDTO());
			
			//设置金额及商品数量
			orderEntity.setProductAmount(unitPrice.multiply(BigDecimal.valueOf(unit)));
			orderEntity.setProductPoint(productPoint);
			orderEntity.setProductUnit(pointOrderDTO.getOrderDTO().getProductUnit());
			orderEntity.setDeductAmount(BigDecimal.valueOf(0));
			
			//运费金额
			BigDecimal freightAmount = BigDecimal.valueOf(0);
			//选择快递配送(非门店自提)
			if(OrderConstants.ORDER_DELIVERY_TYPE_EXPRESS.equals(pointOrderDTO.getOrderDTO().getDeliveryType())) {
				pointProductDTO = pointService.getProductByUuid(pointProductDTO.getProductUuid());
				freightAmount = shoppingCartService.calculatePointProductFreightAmount(pointProductDTO,unitPrice,unit,orderEntity.getDeliveryProvince(),orderEntity.getDeliveryCity(),orderEntity.getDeliveryArea());
			}
				
			orderEntity.setFreightAmount(freightAmount==null?BigDecimal.valueOf(0):freightAmount);
			orderEntity.setActualAmount(orderEntity.getProductAmount().subtract(orderEntity.getDeductAmount()).add(orderEntity.getFreightAmount()));
			orderEntity.setActualPoint(orderEntity.getProductPoint());
			
			//订单其他数据
			populateOtherInfo(pointOrderDTO.getOrderDTO(), orderEntity);
			
			//创建订单
			orderDAO.addOrder(orderEntity);

			//创建订单商品关系
			addSingleOrderPointProduct(orderEntity, pointProduct, productSkuEntity,pointOrderDTO.getOrderDTO().getProductUnit(), unitPrice, unitPrice);
			
			//如果积分订单不需要支付余额(这里主要是运费), 则直接扣除会员积分并完成该订单
			if(orderEntity.getActualAmount().compareTo(BigDecimal.valueOf(0))==0) {
				deductUserPoint(userEntity, productPoint, orderEntity.getOrderNo());
				orderEntity.setOrderStatus(OrderConstants.ORDER_STATUS_NOT_DELIVERIED);
				orderDAO.updateOrder(orderEntity);
			}
			
			return orderEntity.getOrderNo();
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		} catch (BusinessException e) {
			throw e;
		}
	}
	
	void deductUserPoint(UserEntity userEntity, int point, String orderNo) throws BusinessException{
		
		try {
			UserBalanceEntity balanceEntity = userEntity.getUserBalanceEntity();
			//当前积分
			int pointBefore = balanceEntity.getAvailablePoint();
			//获取后积分
			int pointAfter = pointBefore - point;
			if(pointAfter<0) pointAfter = 0;
			balanceEntity.setAvailablePoint(pointAfter);
			balanceEntity.setUpdateBy(userEntity.getId());
			userBalanceDAO.updateUserBalance(balanceEntity);
			
			//积分明细
			UserPointStatementEntity userStatementEntity = new UserPointStatementEntity();
			userStatementEntity.setUserEntity(userEntity);
			userStatementEntity.setTransactionTime(new Date());
			userStatementEntity.setTransactionCode(orderNo);
			userStatementEntity.setTransactionType(PointConstants.TRAN_TYPE_BUY);
			userStatementEntity.setTransactionDesc("兑换商品扣除" + point+ "积分");
			userStatementEntity.setTransactionPoint(point);
			userStatementEntity.setPointBefore(pointBefore);
			userStatementEntity.setPointAfter(pointAfter);
			userStatementEntity.setCreateBy(userEntity.getId());
			userStatementEntity.setUpdateBy(userEntity.getId());
			userPointStatementDAO.createUserPointStatement(userStatementEntity);
		}catch(DAOException e) {
			logger.error("扣除会员积分时发生异常:"+e.getMessage());
			throw new BusinessException(e);
		}
		
	}

	private void validateDeliveryAddress(OrderDTO orderDTO) throws BusinessException, DAOException {
		UserDeliveryAddressDTO addressDTO = orderDTO.getDeliveryAddressDTO();
		String deliveryType = orderDTO.getDeliveryType();
		if(OrderConstants.ORDER_DELIVERY_TYPE_EXPRESS.equals(deliveryType)) {
			if(addressDTO == null) {
				throw new BusinessException(BusinessErrorCode.ORDER_ADDRESS_EMPTY);
			}
			UserDeliveryAddressEntity addressEntity = userDeliveryAddressDAO.getDeliveryAddressByUuid(addressDTO.getUserDeliveryAddressUuid());
			if(addressEntity == null) {
				throw new BusinessException(BusinessErrorCode.ORDER_ADDRESS_ERROR);
			}
		}else if(OrderConstants.ORDER_DELIVERY_TYPE_CITY.equals(deliveryType)){
			if(StringUtils.isEmpty(orderDTO.getDeliveryStreet())) {
				throw new BusinessException(BusinessErrorCode.ORDER_ADDRESS_ERROR);
			}
		}
	}

	private void clearCart(List<String> shoppingCartUuidList) throws DAOException {
		for (Iterator<String> iterator = shoppingCartUuidList.iterator(); iterator.hasNext();) {
			String shoppingCartUuid = iterator.next();
			ShoppingCartEntity cartEntity = shoppingCartDAO.getShoppingCartByUuid(shoppingCartUuid);
			shoppingCartDAO.deleteShoppingCart(cartEntity);
		}
	}

	private void updateProductStock(List<String> shoppingCartUuidList) throws DAOException {
		for (Iterator<String> iterator = shoppingCartUuidList.iterator(); iterator.hasNext();) {
			String shoppingCartUuid = iterator.next();
			ShoppingCartEntity cartEntity = shoppingCartDAO.getShoppingCartByUuid(shoppingCartUuid);
			ProductEntity productEntity = cartEntity.getProductEntity();
			ProductSkuEntity productSkuEntity = cartEntity.getProductSkuEntity();
			int unit = cartEntity.getUnit();
			productEntity.setSoldUnit(productEntity.getSoldUnit()+unit);
			productEntity.setTotalUnit(productEntity.getTotalUnit()-unit);
			productDAO.updateProduct(productEntity);
			if(productEntity.isSkuEnabled()) {
				productSkuEntity.setSkuTotalUnit(productSkuEntity.getSkuTotalUnit()-unit);
				productSkuEntity.setSkuSoldUnit(productSkuEntity.getSkuSoldUnit()+unit);
				productSkuDAO.updateProductSku(productSkuEntity);
			}
		}
	}

	private void addOrderProduct(OrderEntity orderEntity, List<String> shoppingCartUuidList) throws DAOException {
		for (Iterator<String> iterator = shoppingCartUuidList.iterator(); iterator.hasNext();) {
			String shoppingCartUuid = iterator.next();
			ShoppingCartEntity cartEntity = shoppingCartDAO.getShoppingCartByUuid(shoppingCartUuid);
			ProductEntity productEntity = cartEntity.getProductEntity();
			ProductSkuEntity productSkuEntity = cartEntity.getProductSkuEntity();
			int unit = cartEntity.getUnit();
			//订单商品数据
			addSingleOrderProduct(orderEntity, productEntity, productSkuEntity, null, null, unit, productEntity.getUnitPrice(),productSkuEntity==null?BigDecimal.valueOf(0):productSkuEntity.getSkuUnitPrice());
		}
	}

	private void addSingleOrderProduct(OrderEntity orderEntity, ProductEntity productEntity,
			ProductSkuEntity productSkuEntity, GroupBuyProductEntity groupBuyProductEntity, SecKillProductEntity secKillProductEntity, int unit, BigDecimal productUnitPrice, BigDecimal productSkuUnitPrice) throws DAOException {
		OrderProductEntity orderProductEntity = new OrderProductEntity();
		orderProductEntity.setOrderEntity(orderEntity);
		orderProductEntity.setProductEntity(productEntity);
		orderProductEntity.setProductSkuEntity(productSkuEntity);
		orderProductEntity.setGroupBuyProductEntity(groupBuyProductEntity);
		orderProductEntity.setSecKillProductEntity(secKillProductEntity);
		orderProductEntity.setProductUnit(unit);
		int productPoint = 0;
		BigDecimal productAmount = BigDecimal.valueOf(0);
		if(productEntity.isSkuEnabled()) {
			productPoint = productSkuEntity.getSkuUnitPoint()*unit;
			orderProductEntity.setProductUnitPoint(productSkuEntity.getSkuUnitPoint());
			productAmount = productSkuUnitPrice.multiply(BigDecimal.valueOf(unit));
			orderProductEntity.setProductUnitPrice(productSkuUnitPrice);
		}else {
			productPoint = productEntity.getUnitPoint()*unit;
			orderProductEntity.setProductUnitPoint(productEntity.getUnitPoint());
			productAmount = productUnitPrice.multiply(BigDecimal.valueOf(unit));
			orderProductEntity.setProductUnitPrice(productUnitPrice);
		}
		
		orderProductEntity.setProductPoint(productPoint);
		orderProductEntity.setProductAmount(productAmount);
		orderProductEntity.setActualPoint(productPoint);
		orderProductEntity.setActualAmount(productAmount);
		orderProductEntity.setProductUnit(unit);
		orderProductEntity.setProductName(productEntity.getProductName());
		orderProductEntity.setProductCode(productEntity.getProductCode());
		orderProductEntity.setProductImageUrl(productEntity.getProductMainImage().getUrl());
		//销售属性格式：属性名称:属性值;属性名称:属性值;（如：码数:XL;颜色:黑色;）
		if(productEntity.isSkuEnabled()) {
			String skuAttrValueUuids = productSkuEntity.getSkuAttrValueUuids();
			orderProductEntity.setProductCode(productSkuEntity.getSkuCode());
			String tSkuAttrValueUuids = skuAttrValueUuids.replace("[", "").replace("]", "");
			String productSkuDesc = "";
			String[] skuAttrValueUuidArray = tSkuAttrValueUuids.split(ProductConstants.DELIMETER+" ");
			for(int i=0; i<skuAttrValueUuidArray.length;i++){
				String skuAttrValueUuid = skuAttrValueUuidArray[i];
				try {
					ProductSkuAttrValueEntity skuAttrValueEntity = productSkuAttrValueDAO.getProductSkuAttrValueByUuid(skuAttrValueUuid);
					String attrName = skuAttrValueEntity.getProductSkuAttrEntity().getSkuAttrName();
					String attrValue = skuAttrValueEntity.getSkuAttrValue();
					String attrPair = attrName+":"+attrValue;
					if(i==0) {
						productSkuDesc = attrPair;
					}else {
						productSkuDesc = productSkuDesc + ";"+attrPair;
					}
				}catch(DAOException e) {
					logger.error("获取skuAttrValueUuid属性值失败:"+skuAttrValueUuid);
				}
			}
			orderProductEntity.setProductSkuDesc(productSkuDesc);
		}
		orderProductDAO.addOrderProduct(orderProductEntity);
	}
	
	private void addSingleOrderPointProduct(OrderEntity orderEntity, PointProductEntity productEntity,
			PointProductSkuEntity productSkuEntity, int unit, BigDecimal productUnitPrice, BigDecimal productSkuUnitPrice) throws DAOException {
		OrderProductEntity orderProductEntity = new OrderProductEntity();
		orderProductEntity.setOrderEntity(orderEntity);
		orderProductEntity.setPointProductEntity(productEntity);
		orderProductEntity.setPointProductSkuEntity(productSkuEntity);
		orderProductEntity.setProductUnit(unit);
		int productPoint = 0;
		BigDecimal productAmount = BigDecimal.valueOf(0);
		if(productEntity.isSkuEnabled()) {
			productPoint = productSkuEntity.getSkuUnitPoint()*unit;
			orderProductEntity.setProductUnitPoint(productSkuEntity.getSkuUnitPoint());
			productAmount = productSkuUnitPrice.multiply(BigDecimal.valueOf(unit));
			orderProductEntity.setProductUnitPrice(productSkuUnitPrice);
		}else {
			productPoint = productEntity.getUnitPoint()*unit;
			orderProductEntity.setProductUnitPoint(productEntity.getUnitPoint());
			productAmount = productUnitPrice.multiply(BigDecimal.valueOf(unit));
			orderProductEntity.setProductUnitPrice(productUnitPrice);
		}
		
		orderProductEntity.setProductPoint(productPoint);
		orderProductEntity.setProductAmount(productAmount);
		orderProductEntity.setActualPoint(productPoint);
		orderProductEntity.setActualAmount(productAmount);
		orderProductEntity.setProductUnit(unit);
		orderProductEntity.setProductName(productEntity.getProductName());
		orderProductEntity.setProductCode(productEntity.getProductCode());
		orderProductEntity.setProductImageUrl(productEntity.getProductMainImage().getUrl());
		//销售属性格式：属性名称:属性值;属性名称:属性值;（如：码数:XL;颜色:黑色;）
		if(productEntity.isSkuEnabled()) {
			String skuAttrValueUuids = productSkuEntity.getSkuAttrValueUuids();
			orderProductEntity.setProductCode(productSkuEntity.getSkuCode());
			String tSkuAttrValueUuids = skuAttrValueUuids.replace("[", "").replace("]", "");
			String productSkuDesc = "";
			String[] skuAttrValueUuidArray = tSkuAttrValueUuids.split(ProductConstants.DELIMETER+" ");
			for(int i=0; i<skuAttrValueUuidArray.length;i++){
				String skuAttrValueUuid = skuAttrValueUuidArray[i];
				try {
					PointProductSkuAttrValueEntity skuAttrValueEntity = pointProductSkuAttrValueDAO.getProductSkuAttrValueByUuid(skuAttrValueUuid);
					String attrName = skuAttrValueEntity.getProductSkuAttrEntity().getSkuAttrName();
					String attrValue = skuAttrValueEntity.getSkuAttrValue();
					String attrPair = attrName+":"+attrValue;
					if(i==0) {
						productSkuDesc = attrPair;
					}else {
						productSkuDesc = productSkuDesc + ";"+attrPair;
					}
				}catch(DAOException e) {
					logger.error("获取skuAttrValueUuid属性值失败:"+skuAttrValueUuid);
				}
			}
			orderProductEntity.setProductSkuDesc(productSkuDesc);
		}
		orderProductDAO.addOrderProduct(orderProductEntity);
	}

	private void populateOtherInfo(OrderDTO orderDTO, OrderEntity orderEntity) throws BusinessException {
		orderEntity.setOrderTime(new Date());
		orderEntity.setOrderNo(getOrderNo(orderEntity.getUserEntity().getId()));
		orderEntity.setFormId(orderDTO.getFormId());
		orderEntity.setOrderStatus(OrderConstants.ORDER_STATUS_NOT_PAYED);
		orderEntity.setpOrderNo(orderDTO.getpOrderNo());
		orderEntity.setMemo(orderDTO.getMemo());
	}

	private void populateAddressData(OrderEntity orderEntity, OrderDTO orderDTO) throws BusinessException{
		UserDeliveryAddressDTO addressDTO = orderDTO.getDeliveryAddressDTO();
		String deliveryType = orderDTO.getDeliveryType();
		orderEntity.setDeliveryType(deliveryType);
		try {
			if(OrderConstants.ORDER_DELIVERY_TYPE_EXPRESS.equals(deliveryType)) {
				UserDeliveryAddressEntity addressEntity = userDeliveryAddressDAO.getDeliveryAddressByUuid(addressDTO.getUserDeliveryAddressUuid());
				orderEntity.setUserDeliveryAddressEntity(addressEntity);
				orderEntity.setDeliveryProvince(addressEntity.getProvince());
				orderEntity.setDeliveryCity(addressEntity.getCity());
				orderEntity.setDeliveryArea(addressEntity.getArea());
				orderEntity.setDeliveryStreet(addressEntity.getStreet());
				orderEntity.setDeliveryName(addressEntity.getName());
				orderEntity.setDeliveryContactNo(addressEntity.getTelephone());
				orderEntity.setDeliveryZipcode(addressEntity.getZipcode());
			}else if(OrderConstants.ORDER_DELIVERY_TYPE_CITY.equals(deliveryType)) {
				orderEntity.setDeliveryStreet(orderDTO.getDeliveryStreet());
				orderEntity.setDeliveryLatitude(orderDTO.getDeliveryLatitude());
				orderEntity.setDeliveryLongitude(orderDTO.getDeliveryLongitude());
				orderEntity.setDeliveryName(orderDTO.getDeliveryName());
				orderEntity.setDeliveryContactNo(orderDTO.getDeliveryContactNo());
			}else if(OrderConstants.ORDER_DELIVERY_TYPE_PICK.equals(deliveryType)) {
				orderEntity.setDeliveryStreet(orderEntity.getMerchantEntity().getMerchantAddress());
				orderEntity.setDeliveryLatitude(BigDecimal.valueOf(orderEntity.getMerchantEntity().getLatitude()));
				orderEntity.setDeliveryLongitude(BigDecimal.valueOf(orderEntity.getMerchantEntity().getLongitude()));
				orderEntity.setDeliveryName(orderDTO.getDeliveryName());
				orderEntity.setDeliveryContactNo(orderDTO.getDeliveryContactNo());
			}
		}catch(DAOException e) {
			throw new BusinessException(e);
		}
		
		
	}

	private void validateOrder(List<String> shoppingCartUuidList, OrderEntity orderEntity)
			throws BusinessException, DAOException {
		
		if(shoppingCartUuidList.size()==0) {
			throw new BusinessException(BusinessErrorCode.ORDER_PRODUCT_EMPTY);
		}
		//库存及用户积分验证
		int totalPoint = 0;	//订单所需积分
		BigDecimal totalAmount = BigDecimal.valueOf(0);	//订单金额
		int totalUnit = 0;
		String productNames = "";
		//检查库存
		for (Iterator<String> iterator = shoppingCartUuidList.iterator(); iterator.hasNext();) {
			String shoppingCartUuid = iterator.next();
			ShoppingCartEntity cartEntity = shoppingCartDAO.getShoppingCartByUuid(shoppingCartUuid);
			ProductEntity productEntity = cartEntity.getProductEntity();
			orderEntity.setMerchantEntity(productEntity.getMerchantEntity());//设置订单所属商家
			ProductSkuEntity productSkuEntity = cartEntity.getProductSkuEntity();
			int productUnit = cartEntity.getUnit();
			if(productUnit==0) {
				throw new BusinessException(BusinessErrorCode.ORDER_PRODUCT_UNIT_EMPTY);
			}
			int stock = 0;
			productNames = productNames + "["+productEntity.getProductName()+"(X"+productUnit+")"+"]";
			if(productEntity.isSkuEnabled()) {
				stock = productSkuEntity.getSkuTotalUnit();
				totalPoint = totalPoint + productSkuEntity.getSkuUnitPoint()*productUnit;
				totalAmount= totalAmount.add(productSkuEntity.getSkuUnitPrice().multiply(BigDecimal.valueOf(productUnit)));
			}else {
				stock = productEntity.getTotalUnit();
				totalPoint = totalPoint + productEntity.getUnitPoint()*productUnit;
				totalAmount= totalAmount.add(productEntity.getUnitPrice().multiply(BigDecimal.valueOf(productUnit)));
			}
			//库存不足
			if(productUnit > stock) {
				throw new BusinessException(BusinessErrorCode.PRODUCT_STOCK_INSUFFICIENT);
			}
			totalUnit = totalUnit + productUnit;
		}
		
		
		orderEntity.setProductPoint(totalPoint);
		orderEntity.setActualPoint(totalPoint);
		orderEntity.setProductAmount(totalAmount);
		orderEntity.setActualAmount(totalAmount);
		orderEntity.setProductUnit(totalUnit);
	}
	
	
	@Override
	public OrderDTO getOrderByOrderNo(String orderNo) throws BusinessException {
		try {
			OrderEntity orderEntity = orderDAO.getOrderByOrderNo(orderNo);
			if(orderEntity != null){
				OrderDTO orderDTO = new OrderDTO();
				orderDetailEntity2DTO(orderEntity, orderDTO);
				List<UserAwardDTO> awardDTOList = new ArrayList<UserAwardDTO>();
				List<UserAwardEntity> awardEntityList =  userAwardDAO.getAwardByTranCode(orderNo);
				for (Iterator iterator = awardEntityList.iterator(); iterator
						.hasNext();) {
					UserAwardEntity userAwardEntity = (UserAwardEntity) iterator
							.next();
					UserAwardDTO userAwardDTO = new UserAwardDTO();
					userAwardDTO.setTransactionAmount(userAwardEntity.getTransactionAmount());
					userAwardDTO.setTransactionCode(userAwardEntity.getTransactionCode());
					userAwardDTO.setTransactionTime(userAwardEntity.getTransactionTime());
					userAwardDTO.setTransactionType(userAwardEntity.getTransactionType());
					UserEntity userEntity = userAwardEntity.getUserEntity();
					UserDTO userDTO = new UserDTO();
					userDTO.setUserUuid(userEntity.getUserUuid());
					userDTO.setName(userEntity.getName());
					userDTO.setPersonalPhone(userEntity.getPersonalPhone());
					userAwardDTO.setUserDTO(userDTO);
					awardDTOList.add(userAwardDTO);
				}
				orderDTO.setAwardList(awardDTOList);
				return orderDTO;
			}else return null;
			
		}catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}
	@Override
	public OrderDTO getOrderByOrderUuid(String orderUuid) throws BusinessException {
		
		try {
			OrderEntity orderEntity = orderDAO.getOrderByUuid(orderUuid);
			if(orderEntity != null){
				OrderDTO orderDTO = new OrderDTO();
				orderDetailEntity2DTO(orderEntity, orderDTO);
				return orderDTO;
			}else return null;
			
		}catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}
	
	public List<OrderDTO> getOrderByMerchant(MerchantDTO merchantDTO) throws BusinessException {
		List<OrderDTO> orderDTOList = new ArrayList<OrderDTO>();
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			List<OrderEntity> orderEntityList = orderDAO.getOrdersByMerchant(merchantEntity);
			for(Iterator<OrderEntity> iter = orderEntityList.iterator();iter.hasNext();){
				OrderEntity orderEntity = iter.next();
				OrderDTO orderDTO = new OrderDTO();
				orderEntity2DTO(orderEntity, orderDTO);
				orderDTOList.add(orderDTO);
			}
			
		}catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		return orderDTOList;
	}
	
	public List<OrderDTO> getOrderByUser(UserDTO userDTO) throws BusinessException {
		List<OrderDTO> orderDTOList = new ArrayList<OrderDTO>();
		try {
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			List<OrderEntity> orderEntityList = orderDAO.getOrdersByUser(userEntity);
			for(Iterator<OrderEntity> iter = orderEntityList.iterator();iter.hasNext();){
				OrderEntity orderEntity = iter.next();
				OrderDTO orderDTO = new OrderDTO();
				orderEntity2DTO(orderEntity, orderDTO);
				orderDTOList.add(orderDTO);
			}
			
		}catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		return orderDTOList;
	}
	
	public List<OrderDTO> getOrderByPOrderNo(String pOrderNo) throws BusinessException {
		List<OrderDTO> orderDTOList = new ArrayList<OrderDTO>();
		try {
			List<OrderEntity> orderEntityList = orderDAO.getOrdersByPOrderNo(pOrderNo);
			for(Iterator<OrderEntity> iter = orderEntityList.iterator();iter.hasNext();){
				OrderEntity orderEntity = iter.next();
				OrderDTO orderDTO = new OrderDTO();
				orderEntity2DTO(orderEntity, orderDTO);
				orderDTOList.add(orderDTO);
			}
			
		}catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		return orderDTOList;
	}
	
	public BigDecimal getActualAmountByPOrderNo(String pOrderNo) throws BusinessException {
		try {
			return orderDAO.getActualAmountByPOrderNo(pOrderNo);
		}catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}

	private void orderDetailEntity2DTO(OrderEntity orderEntity,
			OrderDTO orderDTO) {
		orderEntity2DTO(orderEntity, orderDTO);
	}
	@Override
	public void orderEntity2DTO(OrderEntity orderEntity,
			OrderDTO orderDTO) {
		orderDTO.setOrderUuid(orderEntity.getOrderUuid());
		orderDTO.setOrderType(orderEntity.getOrderType());
		orderDTO.setOrderNo(orderEntity.getOrderNo());
		orderDTO.setpOrderNo(orderEntity.getpOrderNo());
		orderDTO.setAccounted(orderEntity.isAccounted());
		orderDTO.setActualAmount(orderEntity.getActualAmount());
		orderDTO.setDeductAmount(orderEntity.getDeductAmount());
		orderDTO.setActualPoint(orderEntity.getActualPoint());
		orderDTO.setDeliveryTime(orderEntity.getDeliveryTime());
		orderDTO.setMemo(orderEntity.getMemo());
		orderDTO.setOrderStatus(orderEntity.getOrderStatus());
		orderDTO.setOrderTime(orderEntity.getOrderTime());
		orderDTO.setProductAmount(orderEntity.getProductAmount());
		orderDTO.setProductPoint(orderEntity.getProductPoint());
		orderDTO.setProductUnit(orderEntity.getProductUnit());
		orderDTO.setMemo(orderEntity.getMemo());
		orderDTO.setOrderComment(orderEntity.getOrderComment());
		orderDTO.setOrderChannel(orderEntity.getOrderChannel());
		orderDTO.setPaymentMethod(orderEntity.getPaymentMethod());
		orderDTO.setPaymentTime(orderEntity.getPaymentTime());
		orderDTO.setCancelReason(orderEntity.getCancelReason());
		orderDTO.setCancelTime(orderEntity.getCancelTime());
		orderDTO.setAfterSale(orderEntity.isAfterSale());
		orderDTO.setAfterSaleNo(orderEntity.getAfterSaleNo());
		orderDTO.setAfterSaleDeadLineTime(orderEntity.getAfterSaleDeadLineTime());
		orderDTO.setConfirmTime(orderEntity.getConfirmTime());
		orderDTO.setDeliveryType(orderEntity.getDeliveryType());
		orderDTO.setDeliveryName(orderEntity.getDeliveryName());
		orderDTO.setDeliveryContactNo(orderEntity.getDeliveryContactNo());
		orderDTO.setDeliveryStreet(orderEntity.getDeliveryStreet());
		orderDTO.setDeliveryLatitude(orderEntity.getDeliveryLatitude());
		orderDTO.setDeliveryLongitude(orderEntity.getDeliveryLongitude());
		orderDTO.setShopperName(orderEntity.getShopperName());
		orderDTO.setShopperMobileNo(orderEntity.getShopperMobileNo());
		orderDTO.setShopperPhoto(orderEntity.getShopperPhoto());
		orderDTO.setShopperSex(orderEntity.getShopperSex());
		
		orderDTO.setProductCostAmount(orderEntity.getProductCostAmount());
		orderDTO.setFreightAmount(orderEntity.getFreightAmount());
		UserEntity userEntity = orderEntity.getUserEntity();
		if(userEntity != null){
			UserDTO userDTO = new UserDTO();
			userDTO.setUserUuid(userEntity.getUserUuid());
			userDTO.setName(userEntity.getName());
			userDTO.setPersonalPhone(userEntity.getPersonalPhone());
			orderDTO.setUserDTO(userDTO);
		}
		//订单商品数据
		List<OrderProductDTO> orderProductDTOList =new ArrayList<OrderProductDTO>();
		List<OrderProductEntity> orderProductEntityList = orderEntity.getOrderProductList();
		for (Iterator<OrderProductEntity> iterator = orderProductEntityList.iterator(); iterator.hasNext();) {
			OrderProductEntity orderProductEntity = iterator.next();
			ProductEntity productEntity = orderProductEntity.getProductEntity();
			ProductSkuEntity productSkuEntity = orderProductEntity.getProductSkuEntity();
			PointProductEntity pointProductEntity = orderProductEntity.getPointProductEntity();
			PointProductSkuEntity pointProductSkuEntity = orderProductEntity.getPointProductSkuEntity();
			OrderProductDTO orderProductDTO = new OrderProductDTO();
			if(productEntity != null) {
				ProductDTO productDTO = new ProductDTO();
				productDTO.setMemo(productEntity.getMemo());
				productDTO.setProductName(productEntity.getProductName());
				productDTO.setProductType(productEntity.getProductType());
				productDTO.setProductCode(productEntity.getProductCode());
				productDTO.setProductUuid(productEntity.getProductUuid());
				productDTO.setUnitPoint(productEntity.getUnitPoint());
				productDTO.setUnitPointStandard(productEntity.getUnitPointStandard());
				productDTO.setUnitPrice(productEntity.getUnitPrice());
				productDTO.setUnitPriceStandard(productEntity.getUnitPriceStandard());
				orderProductDTO.setProductDTO(productDTO);
			}
			if(pointProductEntity != null) {
				PointProductDTO pointProductDTO = new PointProductDTO();
				pointProductDTO.setMemo(pointProductEntity.getMemo());
				pointProductDTO.setProductName(pointProductEntity.getProductName());
				pointProductDTO.setProductCode(pointProductEntity.getProductCode());
				pointProductDTO.setProductUuid(pointProductEntity.getProductUuid());
				pointProductDTO.setUnitPoint(pointProductEntity.getUnitPoint());
				pointProductDTO.setUnitPointStandard(pointProductEntity.getUnitPointStandard());
				pointProductDTO.setUnitPrice(pointProductEntity.getUnitPrice());
				pointProductDTO.setUnitPriceStandard(pointProductEntity.getUnitPriceStandard());
				pointProductDTO.setSkuEnabled(pointProductEntity.isSkuEnabled());
				orderProductDTO.setPointProductDTO(pointProductDTO);
			}
			if(productSkuEntity != null) {
				ProductSkuDTO productSkuDTO = new ProductSkuDTO();
				productSkuDTO.setSkuCode(productSkuEntity.getSkuCode());
				productSkuDTO.setProductSkuUuid(productSkuEntity.getProductSkuUuid());
				orderProductDTO.setProductSkuDTO(productSkuDTO);
			}
			if(pointProductSkuEntity != null) {
				PointProductSkuDTO pointProductSkuDTO = new PointProductSkuDTO();
				pointProductSkuDTO.setSkuCode(pointProductSkuEntity.getSkuCode());
				pointProductSkuDTO.setProductSkuUuid(pointProductSkuEntity.getProductSkuUuid());
				pointProductSkuDTO.setSkuUnitPoint(pointProductSkuEntity.getSkuUnitPoint());
				pointProductSkuDTO.setSkuUnitPointStandard(pointProductSkuEntity.getSkuUnitPointStandard());
				pointProductSkuDTO.setSkuUnitPrice(pointProductSkuEntity.getSkuUnitPrice());
				pointProductSkuDTO.setSkuUnitPriceStandard(pointProductSkuEntity.getSkuUnitPriceStandard());
				orderProductDTO.setPointProductSkuDTO(pointProductSkuDTO);
			}
			orderProductDTO.setActualAmount(orderProductEntity.getActualAmount());
			orderProductDTO.setActualPoint(orderProductEntity.getActualPoint());
			orderProductDTO.setProductAmount(orderProductEntity.getProductAmount());
			orderProductDTO.setProductCode(orderProductEntity.getProductCode());
			orderProductDTO.setProductImageUrl(orderProductEntity.getProductImageUrl());
			orderProductDTO.setProductName(orderProductEntity.getProductName());
			orderProductDTO.setProductPoint(orderProductEntity.getProductPoint());
			orderProductDTO.setProductSkuDesc(orderProductEntity.getProductSkuDesc());
			orderProductDTO.setProductUnit(orderProductEntity.getProductUnit());
			orderProductDTO.setProductUnitPoint(orderProductEntity.getProductUnitPoint());
			orderProductDTO.setProductUnitPrice(orderProductEntity.getProductUnitPrice());
			orderProductDTOList.add(orderProductDTO);
		}
		orderDTO.setOrderProductDTOList(orderProductDTOList);
		
		
		//订单收件人数据
		orderDTO.setDeliveryProvince(orderEntity.getDeliveryProvince());
		orderDTO.setDeliveryCity(orderEntity.getDeliveryCity());
		orderDTO.setDeliveryArea(orderEntity.getDeliveryArea());
		orderDTO.setDeliveryStreet(orderEntity.getDeliveryStreet());
		orderDTO.setDeliveryName(orderEntity.getDeliveryName());
		orderDTO.setDeliveryContactNo(orderEntity.getDeliveryContactNo());
		orderDTO.setDeliveryZipcode(orderEntity.getDeliveryZipcode());
		orderDTO.setDeliveryTime(orderEntity.getDeliveryTime());
		
		//商家
		MerchantEntity merchantEntity = orderEntity.getMerchantEntity();
		if(merchantEntity != null) {
			MerchantDTO merchantDTO = new MerchantDTO();
			merchantDTO.setMerchantUuid(merchantEntity.getMerchantUuid());
			merchantDTO.setMerchantName(merchantEntity.getMerchantName());
			merchantDTO.setMerchantAddress(merchantEntity.getMerchantAddress());
			merchantDTO.setMobileNo(merchantEntity.getMobileNo());
			merchantDTO.setLatitude(merchantEntity.getLatitude());
			merchantDTO.setLongitude(merchantEntity.getLongitude());
			orderDTO.setMerchantDTO(merchantDTO);
		}
		
		//订单对应的团购单
		GroupBuyEntity groupBuyEntity = orderEntity.getGroupBuyEntity();
		if(groupBuyEntity != null) {
			GroupBuyDTO groupBuyDTO = new GroupBuyDTO();
			groupBuyDTO.setGroupBuyUuid(groupBuyEntity.getGroupBuyUuid());
			groupBuyDTO.setStatus(groupBuyEntity.getStatus());
			groupBuyDTO.setStartTime(groupBuyEntity.getStartTime());
			groupBuyDTO.setEndTime(groupBuyEntity.getEndTime());
			orderDTO.setGroupBuyDTO(groupBuyDTO);
		}
		
		//订单物流
		orderDTO.setCourierName(orderEntity.getCourierName());
		orderDTO.setCourierNo(orderEntity.getCourierNo());
		orderDTO.setShopperName(orderEntity.getShopperName());
		orderDTO.setShopperMobileNo(orderEntity.getShopperMobileNo());
		orderDTO.setShopperSex(orderEntity.getShopperSex());
		orderDTO.setShopperPhoto(orderEntity.getShopperPhoto());
		
		//订单其他数据
		orderDTO.setOrderTime(orderEntity.getOrderTime());
		orderDTO.setOrderNo(orderEntity.getOrderNo());
		orderDTO.setFormId(orderEntity.getFormId());
		orderDTO.setOrderStatus(orderEntity.getOrderStatus());
	}
	
	
	@Override
	public List<OrderDTO> searchOrders(OrderSearchDTO orderSearchDTO, int startIndex, int pageSize) throws BusinessException {
		List<OrderDTO> orderDTOList = new ArrayList<OrderDTO>();
		try {
			List<OrderEntity> orderEntityList = orderDAO.searchOrders(orderSearchDTO, startIndex, pageSize);
			if (orderEntityList != null)
				for (Iterator<OrderEntity> iterator = orderEntityList.iterator(); iterator
						.hasNext();) {
					OrderEntity orderEntity = (OrderEntity) iterator.next();
					OrderDTO orderDTO = new OrderDTO();
					orderEntity2DTO(orderEntity, orderDTO);
					orderDTOList.add(orderDTO);
				}
			return orderDTOList;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} catch (Exception e2){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e2);
		}
	}
	
	@Override
	public int searchOrderTotal(OrderSearchDTO searchDTO) throws BusinessException {
		try {
			return orderDAO.searchOrdersTotal(searchDTO);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} catch (Exception e2){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e2);
		}
	}
	
	@Override
	public int searchOrderTotalPoint(OrderSearchDTO searchDTO) throws BusinessException {
		try {
			return orderDAO.searchOrdersTotalPoint(searchDTO);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} catch (Exception e2){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e2);
		}
	}
	
	@Override
	public BigDecimal searchOrderTotalAmount(OrderSearchDTO searchDTO) throws BusinessException {
		try {
			return orderDAO.searchOrdersTotalAmount(searchDTO);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} catch (Exception e2){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e2);
		}
	}

	public static void main(String[] args) {
		String amount = "0.01";
		BigDecimal count = new BigDecimal(0.02).divide(new BigDecimal(amount),0,BigDecimal.ROUND_HALF_DOWN);
		System.out.print(100 * count.intValue());
	}

	@Override
	public int getOrderProductUnitTotal(Date startDate, Date endDate) throws BusinessException {
		try {
			return orderDAO.getOrderProductUnitTotal(startDate, endDate);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} catch (Exception e2){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e2);
		}
	}
	
	@Override
	public int getOrderProductUnitTotalByMerchant(MerchantDTO merchantDTO, Date startDate, Date endDate) throws BusinessException {
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			return orderDAO.getOrderProductUnitTotalByMerchant(merchantEntity, startDate, endDate);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} catch (Exception e2){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e2);
		}
	}
	
	@Override
	public int getOrderProductUnitTotalByUser(Date startDate, Date endDate, UserDTO userDTO) throws BusinessException {
		try {
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			return orderDAO.getOrderProductUnitTotalByUser(startDate, endDate, userEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} catch (Exception e2){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e2);
		}
	}

	@Override
	public int getOrderProductUnitTotalByOrderType(Date startDate, Date endDate, String orderType)
			throws BusinessException {
		try {
			return orderDAO.getOrderProductUnitTotalByOrderType(startDate, endDate, orderType);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} catch (Exception e2){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e2);
		}
	}
	
	@Override
	public int getOrderProductUnitTotalByOrderTypeAndMerchant(MerchantDTO merchantDTO, Date startDate, Date endDate, String orderType)
			throws BusinessException {
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			return orderDAO.getOrderProductUnitTotalByOrderTypeAndMerchant(merchantEntity, startDate, endDate, orderType);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} catch (Exception e2){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e2);
		}
	}

	@Override
	public BigDecimal getOrderProductAmountTotal(Date startDate, Date endDate) throws BusinessException {
		try {
			return orderDAO.getOrderProductAmountTotal(startDate, endDate);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} catch (Exception e2){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e2);
		}
	}
	
	@Override
	public BigDecimal getOrderProductAmountTotalByMerchant(MerchantDTO merchantDTO, Date startDate, Date endDate) throws BusinessException {
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			return orderDAO.getOrderProductAmountTotalByMerchant(merchantEntity, startDate, endDate);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} catch (Exception e2){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e2);
		}
	}
	
	@Override
	public BigDecimal getOrderProductAmountTotalByUser(Date startDate, Date endDate, UserDTO userDTO) throws BusinessException {
		try {
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			return orderDAO.getOrderProductAmountTotalByUser(startDate, endDate, userEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} catch (Exception e2){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e2);
		}
	}
	
	@Override
	public int getOrderProductPointTotalByUser(Date startDate, Date endDate, UserDTO userDTO) throws BusinessException {
		try {
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			return orderDAO.getOrderProductPointTotalByUser(startDate, endDate, userEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} catch (Exception e2){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e2);
		}
	}

	@Override
	public BigDecimal getOrderProductAmountTotalByOrderType(Date startDate, Date endDate, String orderType)
			throws BusinessException {
		try {
			return orderDAO.getOrderProductAmountTotalByOrderType(startDate, endDate, orderType);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} catch (Exception e2){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e2);
		}
	}
	
	@Override
	public BigDecimal getOrderProductAmountTotalByOrderTypeAndMerchant(MerchantDTO merchantDTO, Date startDate, Date endDate, String orderType)
			throws BusinessException {
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			return orderDAO.getOrderProductAmountTotalByOrderTypeAndMerchant(merchantEntity, startDate, endDate, orderType);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} catch (Exception e2){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e2);
		}
	}

	@Override
	public List<ChartDTO> getIncrementOrderAmountChart(Date startDate,
			Date endDate) throws BusinessException {
		try {
			return orderDAO.getIncrementOrderAmountChart(startDate, endDate);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} catch (Exception e2){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e2);
		}
	}
	
	@Override
	public List<ChartDTO> getIncrementOrderAmountChartByMerchant(MerchantDTO merchantDTO, Date startDate,
			Date endDate) throws BusinessException {
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			return orderDAO.getIncrementOrderAmountChartByMerchant(merchantEntity, startDate, endDate);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} catch (Exception e2){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e2);
		}
	}
	
	@Override
	public List<ChartDTO> getIncrementOrderCountChart(Date startDate,
			Date endDate) throws BusinessException {
		try {
			return orderDAO.getIncrementOrderCountChart(startDate, endDate);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} catch (Exception e2){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e2);
		}
	}
	
	@Override
	public List<ChartDTO> getIncrementOrderCountChartByMerchant(MerchantDTO merchantDTO, Date startDate,
			Date endDate) throws BusinessException {
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			return orderDAO.getIncrementOrderCountChartByMerchant(merchantEntity, startDate, endDate);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} catch (Exception e2){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e2);
		}
	}
	
	@Override
	public List<ChartDTO> getIncrementOrderUnitChart(Date startDate,
			Date endDate) throws BusinessException {
		try {
			return orderDAO.getIncrementOrderUnitChart(startDate, endDate);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} catch (Exception e2){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e2);
		}
	}
	
	@Override
	public List<ChartDTO> getIncrementOrderPointChart(Date startDate,
			Date endDate) throws BusinessException {
		try {
			return orderDAO.getIncrementOrderPointChart(startDate, endDate);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} catch (Exception e2){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e2);
		}
	}
	
	int getMerchcantAward(){
		int award = 0;
		try{
			SettingEntity settingEntity = settingDAO.getSettingByName("MERCHANT_AWARD");
			if(settingEntity != null){
				award = Integer.valueOf(settingEntity.getValue());
			}
		}catch(DAOException e){
			logger.error(e.getMessage());
		}
		return award;
	}
	//每购买一箱产品, 赠送多少包产品, 产品用于线下试用推广, 试用完后可到驿站兑换
	int getGiftAward(){
		int award = 0;
		try{
			SettingEntity settingEntity = settingDAO.getSettingByName("GIFT_AWARD");
			if(settingEntity != null){
				award = Integer.valueOf(settingEntity.getValue());
			}
		}catch(DAOException e){
			logger.error(e.getMessage());
		}
		return award;
	}
	
	/*
	 * 
	 */
	void updateUserAward(UserEntity userEntity, BigDecimal award, Date tranDate, String tranCode, String tranType, String tranDesc) throws BusinessException{

		// 更新奖金总额和可提现余额
		try {
			UserBalanceEntity userBalance = userBalanceDAO.getBalanceByUser(userEntity);
			if(userBalance == null) {
				userBalance = new UserBalanceEntity();
				userBalance.setUserEntity(userEntity);
				userBalance.setAvailableBalance(BigDecimal.valueOf(0l));
				userBalance.setCreateBy("system");
				userBalanceDAO.createUserBalance(userBalance);
			}else {
				userBalance.setAvailableBalance(award.add(userBalance.getAvailableBalance()));
				userBalance.setUpdateBy("system");
				userBalanceDAO.updateUserBalance(userBalance);
			}
			// 添加奖金明细
			UserAwardEntity awardEntity = new UserAwardEntity();
			awardEntity.setTransactionType(tranType);
			awardEntity.setTransactionCode(tranCode);
			awardEntity.setTransactionTime(tranDate);
			awardEntity.setTransactionAmount(award);
			awardEntity.setTransactionDesc(tranDesc);
			awardEntity.setUserEntity(userEntity);
			awardEntity.setCreateBy("system");
			awardEntity.setUpdateBy("system");
			userAwardDAO.createUserAward(awardEntity);
			
			//发送奖励通知订阅消息
			wxSuscribeMessageSender.sendAwardMsg(paymentUtil.getWechatAppIdMiniProgram(), paymentUtil.getWechatAppSecretMiniProgram(), userEntity.getOpenId(), awardEntity.getTransactionType(),String.valueOf(awardEntity.getTransactionAmount()),awardEntity.getTransactionDesc());
		}catch(DAOException e) {
			logger.error("更新用户奖金发生异常: "+e.getMessage());
			throw new BusinessException(e);
		}
	}

	@Override
	public List<OrderProductDTO> getTopxProductByAmount(Date startDate, Date endDate, int x) throws BusinessException {
		try {
			return orderProductDAO.getTopxProductByAmount(startDate, endDate, x);
		}catch(DAOException e) {
			logger.error("获取爆款商品失败: "+e.getMessage());
			throw new BusinessException(e);
		}
	}
	
	@Override
	public List<OrderProductDTO> getTopxMerchantProductByAmount(MerchantDTO merchantDTO, Date startDate, Date endDate, int x) throws BusinessException {
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			return orderProductDAO.getTopxMerchantProductByAmount(merchantEntity, startDate, endDate, x);
		}catch(DAOException e) {
			logger.error("获取爆款商品失败: "+e.getMessage());
			throw new BusinessException(e);
		}
	}

	@Override
	public List<OrderProductDTO> getTopxProductByUnit(Date startDate, Date endDate, int x) throws BusinessException {
		try {
			return orderProductDAO.getTopxProductByUnit(startDate, endDate, x);
		}catch(DAOException e) {
			logger.error("获取爆款商品失败: "+e.getMessage());
			throw new BusinessException(e);
		}
	}

	@Override
	public List<OrderProductDTO> getTopxMerchantProductByUnit(MerchantDTO merchantDTO, Date startDate, Date endDate, int x) throws BusinessException {
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			return orderProductDAO.getTopxMerchantProductByUnit(merchantEntity, startDate, endDate, x);
		}catch(DAOException e) {
			logger.error("获取爆款商品失败: "+e.getMessage());
			throw new BusinessException(e);
		}
	}
	
	@Override
	public List<MerchantDTO> searchTopxMerchantByUnit(OrderSearchDTO searchDTO) throws BusinessException {
		try {
			return orderProductDAO.getTopxMerchantByUnit(searchDTO);
		}catch(DAOException e) {
			logger.error("获取爆款商品失败: "+e.getMessage());
			throw new BusinessException(e);
		}
	}
	
	@Override
	public List<MerchantDTO> searchTopxMerchantByAmount(OrderSearchDTO searchDTO) throws BusinessException {
		try {
			
			return orderProductDAO.getTopxMerchantByAmount(searchDTO);
		}catch(DAOException e) {
			logger.error("获取爆款商品失败: "+e.getMessage());
			throw new BusinessException(e);
		}
	}
	
	@Override
	public CourierDTO getCourierInfoByNo(String no, String type) throws BusinessException {
	 	String host = "https://wuliu.market.alicloudapi.com";
        String path = "/kdi";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("no", no);// 
        if(!StringUtils.isEmpty(type))
        	querys.put("type", type);
        CourierDTO courierDTO = null;
        try {
        	SettingEntity settingEntity = settingDAO.getSettingByName("COURIER_APP_CODE");
        	if(settingEntity != null && !StringUtils.isEmpty(settingEntity.getValue())) {
        		String appcode = settingEntity.getValue();  //阿里云市场购买快递借口后获取的接口调用码
        		headers.put("Authorization", "APPCODE " + appcode); //格式为:Authorization:APPCODE 83359fd73fe11248385f570e3c139xxx
        		HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            	String statusCode = String.valueOf(response.getStatusLine().getStatusCode());
            	if("200".equals(statusCode)) {
            		HttpEntity httpEntity = response.getEntity();
            		String entityStr = EntityUtils.toString(httpEntity);
            		courierDTO = JSONObject.parseObject(entityStr,CourierDTO.class);
            	}
        	}
        }catch(Exception e) {
        	logger.error(e.getMessage());
        	throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
        }
        return courierDTO;
	}
}
