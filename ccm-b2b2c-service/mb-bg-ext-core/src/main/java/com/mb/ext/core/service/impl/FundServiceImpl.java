package com.mb.ext.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mb.ext.core.constant.FundConstants;
import com.mb.ext.core.constant.OrderConstants;
import com.mb.ext.core.dao.FileDAO;
import com.mb.ext.core.dao.MerchantDAO;
import com.mb.ext.core.dao.SequenceDAO;
import com.mb.ext.core.dao.UserAwardDAO;
import com.mb.ext.core.dao.UserBalanceDAO;
import com.mb.ext.core.dao.UserDAO;
import com.mb.ext.core.dao.UserPerformanceDAO;
import com.mb.ext.core.dao.UserStatementDAO;
import com.mb.ext.core.dao.UserWithdrawDAO;
import com.mb.ext.core.dao.merchant.MerchantAccountDAO;
import com.mb.ext.core.dao.merchant.MerchantBalanceDAO;
import com.mb.ext.core.dao.merchant.MerchantChargeDAO;
import com.mb.ext.core.dao.merchant.MerchantStatementDAO;
import com.mb.ext.core.dao.merchant.MerchantWithdrawDAO;
import com.mb.ext.core.dao.merchant.PlatformBalanceDAO;
import com.mb.ext.core.dao.merchant.PlatformStatementDAO;
import com.mb.ext.core.dao.order.OrderDAO;
import com.mb.ext.core.dao.product.ProductBrandDAO;
import com.mb.ext.core.dao.product.ProductCateDAO;
import com.mb.ext.core.dao.product.ProductDAO;
import com.mb.ext.core.dao.product.ProductGroupDAO;
import com.mb.ext.core.entity.UserAwardEntity;
import com.mb.ext.core.entity.UserBalanceEntity;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.UserPerformanceEntity;
import com.mb.ext.core.entity.UserStatementEntity;
import com.mb.ext.core.entity.UserWithdrawEntity;
import com.mb.ext.core.entity.merchant.MerchantAccountEntity;
import com.mb.ext.core.entity.merchant.MerchantBalanceEntity;
import com.mb.ext.core.entity.merchant.MerchantChargeEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.merchant.MerchantStatementEntity;
import com.mb.ext.core.entity.merchant.MerchantWithdrawEntity;
import com.mb.ext.core.entity.merchant.PlatformBalanceEntity;
import com.mb.ext.core.entity.merchant.PlatformStatementEntity;
import com.mb.ext.core.entity.order.OrderEntity;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.FundService;
import com.mb.ext.core.service.MerchantService;
import com.mb.ext.core.service.NoteService;
import com.mb.ext.core.service.ProfitService;
import com.mb.ext.core.service.spec.AwardSearchDTO;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.ChartDTO;
import com.mb.ext.core.service.spec.PerformanceSearchDTO;
import com.mb.ext.core.service.spec.UserAwardDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.UserPerformanceDTO;
import com.mb.ext.core.service.spec.UserStatementDTO;
import com.mb.ext.core.service.spec.UserWithdrawDTO;
import com.mb.ext.core.service.spec.WithdrawSearchDTO;
import com.mb.ext.core.service.spec.merchant.MerchantAccountDTO;
import com.mb.ext.core.service.spec.merchant.MerchantChargeDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.merchant.MerchantStatementDTO;
import com.mb.ext.core.service.spec.merchant.MerchantWithdrawDTO;
import com.mb.ext.core.service.spec.merchant.PlatformStatementDTO;
import com.mb.ext.core.service.spec.order.OrderDTO;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.service.AbstractService;
import com.mb.framework.util.DateTimeUtil;

@Service
@Qualifier("FundService")
public class FundServiceImpl extends AbstractService implements FundService<BodyDTO> {

	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;

	@Autowired
	@Qualifier("fileDAO")
	private FileDAO fileDAO;

	@Autowired
	@Qualifier("orderDAO")
	private OrderDAO orderDAO;

	@Autowired
	@Qualifier("NoteService")
	private NoteService noteService;

	@Autowired
	@Qualifier("userBalanceDAO")
	private UserBalanceDAO userBalanceDAO;

	@Autowired
	@Qualifier("userStatementDAO")
	private UserStatementDAO userStatementDAO;

	@Autowired
	@Qualifier("userAwardDAO")
	private UserAwardDAO userAwardDAO;
	
	@Autowired
	@Qualifier("ProfitService")
	private ProfitService profitService;

	@Autowired
	@Qualifier("MerchantService")
	private MerchantService merchantService;

	@Autowired
	@Qualifier("userWithdrawDAO")
	private UserWithdrawDAO userWithdrawDAO;

	@Autowired
	@Qualifier("merchantAccountDAO")
	private MerchantAccountDAO merchantAccountDAO;

	@Autowired
	@Qualifier("platformStatementDAO")
	private PlatformStatementDAO platformStatementDAO;

	@Autowired
	@Qualifier("platformBalanceDAO")
	private PlatformBalanceDAO platformBalanceDAO;

	@Autowired
	@Qualifier("sequenceDAO")
	private SequenceDAO sequenceDAO;

	@Autowired
	@Qualifier("merchantDAO")
	private MerchantDAO merchantDAO;

	@Autowired
	@Qualifier("productCateDAO")
	private ProductCateDAO productCateDAO;

	@Autowired
	@Qualifier("productBrandDAO")
	private ProductBrandDAO productBrandDAO;

	@Autowired
	@Qualifier("productGroupDAO")
	private ProductGroupDAO productGroupDAO;

	@Autowired
	@Qualifier("merchantBalanceDAO")
	private MerchantBalanceDAO merchantBalanceDAO;

	@Autowired
	@Qualifier("merchantWithdrawDAO")
	private MerchantWithdrawDAO merchantWithdrawDAO;

	@Autowired
	@Qualifier("merchantChargeDAO")
	private MerchantChargeDAO merchantChargeDAO;

	@Autowired
	@Qualifier("merchantStatementDAO")
	private MerchantStatementDAO merchantStatementDAO;

	@Autowired
	@Qualifier("userPerformanceDAO")
	private UserPerformanceDAO userPerformanceDAO;

	@Autowired
	@Qualifier("productDAO")
	private ProductDAO productDAO;

	public String getUserWithdrawNo(String userId) throws BusinessException {
		
		try {
			//订单号 = 'UW'+时间戳+用户编号+4位任意数字
			String withdrawNo = OrderConstants.ORDER_TYPE_UW + DateTimeUtil.formatDateByYYMMDDHHmm(new Date())
			+ userId		
			+ RandomStringUtils.randomNumeric(4);
			return withdrawNo;
		}catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	
	}
	
	public String getMerchantWithdrawNo() throws BusinessException {
		
		try {
			//订单号 = 'MW'+时间戳+4位任意数字
			String withdrawNo = OrderConstants.ORDER_TYPE_MW + DateTimeUtil.formatDateByYYMMDDHHmmssSSS(new Date())
			+ RandomStringUtils.randomNumeric(4);
			return withdrawNo;
		}catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	
	}

	@Override
	public void applyMerchantWithdraw(MerchantWithdrawDTO withdrawDTO) throws BusinessException {
		try {
			// 查看商户资金余额
			MerchantDTO merchantDTO = withdrawDTO.getMerchantDTO();
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			MerchantBalanceEntity merchantBalanceEntity = merchantBalanceDAO.getBalanceByMerchant(merchantEntity);
			if (merchantBalanceEntity == null) {
				merchantBalanceEntity = new MerchantBalanceEntity();
				merchantBalanceEntity.setMerchantEntity(merchantEntity);
				merchantBalanceEntity.setAvailableBalance(new BigDecimal(0));
				merchantBalanceEntity.setTotalBalance(new BigDecimal(0));
				merchantBalanceEntity.setAvailablePoint(0);
				merchantBalanceEntity.setTotalPoint(0);
				merchantBalanceDAO.createMerchantBalance(merchantBalanceEntity);
			}

			// 检查余额是否充足
			if (merchantBalanceEntity.getAvailableBalance().compareTo(withdrawDTO.getWithdrawAmount()) < 0) {
				throw new BusinessException(BusinessErrorCode.FUND_WITHDRAW_INSUFFICIENT);
			}

			MerchantWithdrawEntity withDrawEntity = new MerchantWithdrawEntity();
			BigDecimal withDrawAmount = withdrawDTO.getWithdrawAmount();
			withDrawEntity.setWithdrawAmount(withDrawAmount);
			if (withDrawAmount != null) {
				BigDecimal taxRate = profitService.getMerchantTaxRate();
				BigDecimal taxAmount = BigDecimal.valueOf(0);
				if(taxRate != null) {
					taxAmount = withDrawAmount.multiply(taxRate.divide(BigDecimal.valueOf(100)));//综合税费
				}
				BigDecimal paymentAmount = withDrawAmount.subtract(taxAmount);
				withDrawEntity.setTaxAmount(taxAmount);
				withDrawEntity.setPaymentAmount(paymentAmount);
			}

			withDrawEntity.setWithdrawStatus(FundConstants.WITHDRAW_STATUS_APPLICATED);
			withDrawEntity.setWithdrawNo(getMerchantWithdrawNo());
			withDrawEntity.setWithdrawTime(new Date());
			withDrawEntity.setMerchantEntity(merchantEntity);
			String paymentMethod = withdrawDTO.getPaymentMethod();
			withDrawEntity.setPaymentMethod(paymentMethod);
			if (FundConstants.WITHDRAW_PAYMENT_METHOD_BANK.equals(paymentMethod)) {
				withDrawEntity.setBankName(withdrawDTO.getBankName());
				withDrawEntity.setBankAccountNo(withdrawDTO.getBankAccountNo());
				withDrawEntity.setBankAccountName(withdrawDTO.getBankAccountName());
				merchantWithdrawDAO.createMerchantWithdraw(withDrawEntity);
			} else if (FundConstants.WITHDRAW_PAYMENT_METHOD_ALIPAY.equals(paymentMethod)) {
				withDrawEntity.setAlipayId(withdrawDTO.getAlipayId());
				merchantWithdrawDAO.createMerchantWithdraw(withDrawEntity);
			} else if (FundConstants.WITHDRAW_PAYMENT_METHOD_WECHAT.equals(paymentMethod)) {
				withDrawEntity.setWechatId(withdrawDTO.getWechatId());
				merchantWithdrawDAO.createMerchantWithdraw(withDrawEntity);
			}

			merchantBalanceEntity.setAvailableBalance(
					merchantBalanceEntity.getAvailableBalance().subtract(withDrawEntity.getWithdrawAmount()));
			merchantBalanceEntity.setTotalBalance(
					merchantBalanceEntity.getTotalBalance().subtract(withDrawEntity.getWithdrawAmount()));
			merchantBalanceDAO.updateMerchantBalance(merchantBalanceEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}

	}
	
	@Override
	public void applyUserWithdraw(UserWithdrawDTO withdrawDTO)
			throws BusinessException {
		try{
			//查看商户资金余额
			UserDTO userDTO = withdrawDTO.getUserDTO();
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			UserBalanceEntity userBalanceEntity = userBalanceDAO.getBalanceByUser(userEntity);
			
			//检查余额是否充足
			if(userBalanceEntity.getAvailableBalance().compareTo(withdrawDTO.getWithdrawAmount())<0){
				throw new BusinessException(BusinessErrorCode.FUND_WITHDRAW_INSUFFICIENT);
			}
			
			UserWithdrawEntity withDrawEntity = new UserWithdrawEntity();
			BigDecimal withDrawAmount = withdrawDTO.getWithdrawAmount();
			withDrawEntity.setWithdrawAmount(withDrawAmount);
			if(withDrawAmount != null){
				BigDecimal taxRate = profitService.getTaxRate();
				BigDecimal taxAmount = BigDecimal.valueOf(0);
				if(taxRate != null) {
					taxAmount = withDrawAmount.multiply(taxRate.divide(BigDecimal.valueOf(100)));//综合税费
				}
				BigDecimal paymentAmount = withDrawAmount.subtract(taxAmount);
				withDrawEntity.setTaxAmount(taxAmount);
				withDrawEntity.setPaymentAmount(paymentAmount);
			}
			withDrawEntity.setWithdrawStatus(FundConstants.WITHDRAW_STATUS_APPLICATED);
			withDrawEntity.setWithdrawNo(getUserWithdrawNo(userEntity.getId()));
			withDrawEntity.setWithdrawTime(new Date());
			withDrawEntity.setUserEntity(userEntity);
			String paymentMethod = withdrawDTO.getPaymentMethod();
			withDrawEntity.setPaymentMethod(paymentMethod);
			if(FundConstants.WITHDRAW_PAYMENT_METHOD_BANK.equals(paymentMethod)){
				withDrawEntity.setBankName(withdrawDTO.getBankName());
				withDrawEntity.setBankAccountNo(withdrawDTO.getBankAccountNo());
				withDrawEntity.setBankAccountName(withdrawDTO.getBankAccountName());
				userWithdrawDAO.createUserWithdraw(withDrawEntity);
			}else if(FundConstants.WITHDRAW_PAYMENT_METHOD_ALIPAY.equals(paymentMethod)){
				withDrawEntity.setAlipayId(withdrawDTO.getAlipayId());
				userWithdrawDAO.createUserWithdraw(withDrawEntity);
			}else if(FundConstants.WITHDRAW_PAYMENT_METHOD_WECHAT.equals(paymentMethod)){
				withDrawEntity.setWechatId(withdrawDTO.getWechatId());
				userWithdrawDAO.createUserWithdraw(withDrawEntity);
			}
			
			//更新资金余额
			userBalanceEntity.setAvailableBalance(userBalanceEntity.getAvailableBalance().subtract(withDrawEntity.getWithdrawAmount()));
			userBalanceDAO.updateUserBalance(userBalanceEntity)	;
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		
	}

	@Override
	public void approveMerchantWithdraw(MerchantWithdrawDTO withdrawDTO) throws BusinessException {
		try {
			MerchantWithdrawEntity withDrawEntity = merchantWithdrawDAO
					.getWithdrawByUuid(withdrawDTO.getMerchantWithdrawUuid());
			withDrawEntity.setWithdrawStatus(FundConstants.WITHDRAW_STATUS_APPROVED);
			withDrawEntity.setVerifyTime(new Date());
			merchantWithdrawDAO.updateMerchantWithdraw(withDrawEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}

	}
	
	@Override
	public void approveUserWithdraw(UserWithdrawDTO withdrawDTO)
			throws BusinessException {
		try{
			UserWithdrawEntity withDrawEntity = userWithdrawDAO.getWithdrawByUuid(withdrawDTO.getUserWithdrawUuid());
			withDrawEntity.setWithdrawStatus(FundConstants.WITHDRAW_STATUS_APPROVED);
			withDrawEntity.setVerifyTime(new Date());
			userWithdrawDAO.updateUserWithdraw(withDrawEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		
	}

	@Override
	public void rejectMerchantWithdraw(MerchantWithdrawDTO withdrawDTO) throws BusinessException {
		try {
			MerchantWithdrawEntity withDrawEntity = merchantWithdrawDAO
					.getWithdrawByUuid(withdrawDTO.getMerchantWithdrawUuid());
			if (FundConstants.WITHDRAW_STATUS_REJECTED.equals(withDrawEntity.getWithdrawStatus())) {
				return;
			}
			withDrawEntity.setWithdrawStatus(FundConstants.WITHDRAW_STATUS_REJECTED);
			withDrawEntity.setRejectReason(withdrawDTO.getFailReason());
			withDrawEntity.setVerifyTime(new Date());
			merchantWithdrawDAO.updateMerchantWithdraw(withDrawEntity);

			// 更新商户资金余额
			MerchantEntity merchantEntity = withDrawEntity.getMerchantEntity();
			MerchantBalanceEntity merchantBalanceEntity = merchantBalanceDAO.getBalanceByMerchant(merchantEntity);
			if (merchantBalanceEntity == null) {
				merchantBalanceEntity = new MerchantBalanceEntity();
				merchantBalanceEntity.setMerchantEntity(merchantEntity);
				merchantBalanceEntity.setAvailableBalance(new BigDecimal(0));
				merchantBalanceEntity.setTotalBalance(new BigDecimal(0));
				merchantBalanceEntity.setAvailablePoint(0);
				merchantBalanceEntity.setTotalPoint(0);
				merchantBalanceDAO.createMerchantBalance(merchantBalanceEntity);
			}
			merchantBalanceEntity.setAvailableBalance(
					merchantBalanceEntity.getAvailableBalance().add(withDrawEntity.getWithdrawAmount()));
			merchantBalanceEntity
					.setTotalBalance(merchantBalanceEntity.getTotalBalance().add(withDrawEntity.getWithdrawAmount()));
			merchantBalanceDAO.updateMerchantBalance(merchantBalanceEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}

	}
	
	@Override
	public void rejectUserWithdraw(UserWithdrawDTO withdrawDTO)
			throws BusinessException {
		try{
			UserWithdrawEntity withDrawEntity = userWithdrawDAO.getWithdrawByUuid(withdrawDTO.getUserWithdrawUuid());
			if(FundConstants.WITHDRAW_STATUS_REJECTED.equals(withDrawEntity.getWithdrawStatus())){
				return;
			}
			withDrawEntity.setWithdrawStatus(FundConstants.WITHDRAW_STATUS_REJECTED);
			withDrawEntity.setRejectReason(withdrawDTO.getFailReason());
			withDrawEntity.setVerifyTime(new Date());
			userWithdrawDAO.updateUserWithdraw(withDrawEntity);
			
			//更新商户资金余额
			UserEntity userEntity = withDrawEntity.getUserEntity();
			UserBalanceEntity userBalanceEntity = userBalanceDAO.getBalanceByUser(userEntity);
			if(userBalanceEntity == null){
				userBalanceEntity = new UserBalanceEntity();
				userBalanceEntity.setUserEntity(userEntity);
				userBalanceEntity.setAvailableBalance(new BigDecimal(0));
				userBalanceEntity.setLedgerBalance(new BigDecimal(0));
				userBalanceDAO.createUserBalance(userBalanceEntity);
			}
			userBalanceEntity.setAvailableBalance(userBalanceEntity.getAvailableBalance().add(withDrawEntity.getWithdrawAmount()));
			userBalanceDAO.updateUserBalance(userBalanceEntity);
			
			//发送提现失败
			UserDTO userDTO = new UserDTO();
			userDTO.setUserUuid(userEntity.getUserUuid());
			noteService.sendNotification(userDTO, "withdrawfail", new String[]{String.valueOf(withDrawEntity.getWithdrawAmount()),withdrawDTO.getRejectReason()});
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		
	}

	@Override
	public void successMerchantWithdraw(MerchantWithdrawDTO withdrawDTO) throws BusinessException {
		try {
			MerchantWithdrawEntity withDrawEntity = merchantWithdrawDAO
					.getWithdrawByUuid(withdrawDTO.getMerchantWithdrawUuid());
			withDrawEntity.setWithdrawStatus(FundConstants.WITHDRAW_STATUS_SUCCESS);
			withDrawEntity.setCompleteTime(new Date());
			merchantWithdrawDAO.updateMerchantWithdraw(withDrawEntity);

			// 更新商户资金余额, 目前资金余额在申请时已扣除, 在审核未通过或打款失败时会返还
			MerchantEntity merchantEntity = withDrawEntity.getMerchantEntity();
			MerchantBalanceEntity merchantBalanceEntity = merchantBalanceDAO.getBalanceByMerchant(merchantEntity);

			// 更新对账单
			MerchantStatementEntity statementEntity = new MerchantStatementEntity();
			statementEntity.setMerchantEntity(merchantEntity);
			statementEntity.setTransactionAmount(new BigDecimal(0).subtract(withDrawEntity.getWithdrawAmount()));
			statementEntity.setTransactionType(FundConstants.MERCHANT_STATEMENT_TRANSACTION_TYPE_WITHDRAW);
			statementEntity.setTransactionCode(withDrawEntity.getWithdrawNo());
			statementEntity.setTransactionTime(new Date());
			statementEntity.setBalanceBefore(merchantBalanceEntity.getAvailableBalance().add(withDrawEntity.getWithdrawAmount()));
			statementEntity.setBalanceAfter(merchantBalanceEntity.getAvailableBalance());
			merchantStatementDAO.createMerchantStatement(statementEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}

	}
	
	@Override
	public void successUserWithdraw(UserWithdrawDTO withdrawDTO)
			throws BusinessException {
		try{
			UserWithdrawEntity withDrawEntity = userWithdrawDAO.getWithdrawByUuid(withdrawDTO.getUserWithdrawUuid());
			withDrawEntity.setWithdrawStatus(FundConstants.WITHDRAW_STATUS_SUCCESS);
			withDrawEntity.setCompleteTime(new Date());
			userWithdrawDAO.updateUserWithdraw(withDrawEntity);
			
			//更新资金余额, 目前资金余额在申请时已扣除, 在审核未通过或打款失败时会返还; 打款成功时不再更新资金余额
			UserEntity userEntity = withDrawEntity.getUserEntity();

			
			//更新对账单
			BigDecimal balanceAfter = userEntity.getUserBalanceEntity().getAvailableBalance();	//余额在申请时已经扣减
			BigDecimal balanceBefore = userEntity.getUserBalanceEntity().getAvailableBalance().add(withDrawEntity.getWithdrawAmount());
			UserStatementEntity statementEntity = new UserStatementEntity();
			statementEntity.setUserEntity(userEntity);
			statementEntity.setTransactionAmount(withDrawEntity.getWithdrawAmount());
			statementEntity.setTransactionType(FundConstants.USER_STATEMENT_TRANSACTION_TYPE_WITHDRAW);
			statementEntity.setTransactionCode(withDrawEntity.getWithdrawNo());
			statementEntity.setTransactionTime(new Date());
			statementEntity.setBalanceBefore(balanceBefore);
			statementEntity.setBalanceAfter(balanceAfter);
			userStatementDAO.createUserStatement(statementEntity);
			
			//发送提现成功
			UserDTO userDTO = new UserDTO();
			userDTO.setUserUuid(userEntity.getUserUuid());
			noteService.sendNotification(userDTO, "withdrawsuccess", new String[]{String.valueOf(withDrawEntity.getWithdrawAmount())});
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		
	}

	@Override
	public void failMerchantWithdraw(MerchantWithdrawDTO withdrawDTO) throws BusinessException {
		try {
			MerchantWithdrawEntity withDrawEntity = merchantWithdrawDAO
					.getWithdrawByUuid(withdrawDTO.getMerchantWithdrawUuid());
			if (FundConstants.WITHDRAW_STATUS_FAIL.equals(withDrawEntity.getWithdrawStatus())) {
				return;
			}
			withDrawEntity.setWithdrawStatus(FundConstants.WITHDRAW_STATUS_FAIL);
			withDrawEntity.setFailReason(withdrawDTO.getFailReason());
			withDrawEntity.setCompleteTime(new Date());
			merchantWithdrawDAO.updateMerchantWithdraw(withDrawEntity);

			// 更新商户资金余额
			MerchantEntity merchantEntity = withDrawEntity.getMerchantEntity();
			MerchantBalanceEntity merchantBalanceEntity = merchantBalanceDAO.getBalanceByMerchant(merchantEntity);
			if (merchantBalanceEntity == null) {
				merchantBalanceEntity = new MerchantBalanceEntity();
				merchantBalanceEntity.setMerchantEntity(merchantEntity);
				merchantBalanceEntity.setAvailableBalance(new BigDecimal(0));
				merchantBalanceEntity.setTotalBalance(new BigDecimal(0));
				merchantBalanceEntity.setAvailablePoint(0);
				merchantBalanceEntity.setTotalPoint(0);
				merchantBalanceDAO.createMerchantBalance(merchantBalanceEntity);
			}
			merchantBalanceEntity.setAvailableBalance(
					merchantBalanceEntity.getAvailableBalance().add(withDrawEntity.getWithdrawAmount()));
			merchantBalanceEntity
					.setTotalBalance(merchantBalanceEntity.getTotalBalance().add(withDrawEntity.getWithdrawAmount()));
			merchantBalanceDAO.updateMerchantBalance(merchantBalanceEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}

	}
	
	@Override
	public void commentMerchantWithdraw(MerchantWithdrawDTO withdrawDTO)
			throws BusinessException {
		try{
			MerchantWithdrawEntity withDrawEntity = merchantWithdrawDAO.getWithdrawByUuid(withdrawDTO.getMerchantWithdrawUuid());
			withDrawEntity.setMemo(withdrawDTO.getMemo());
			merchantWithdrawDAO.updateMerchantWithdraw(withDrawEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		
	}
	
	@Override
	public List<MerchantWithdrawDTO> searchMerchantWithdraw(WithdrawSearchDTO withdrawSearchDTO, int startIndex, int pageSize)
			throws BusinessException {
		List<MerchantWithdrawDTO> dtoList = new ArrayList<MerchantWithdrawDTO>();
		try {
			List<MerchantWithdrawEntity> entityList = merchantWithdrawDAO.searchMerchantWithdraw(withdrawSearchDTO, startIndex,
					pageSize);
			for (Iterator iterator = entityList.iterator(); iterator.hasNext();) {
				MerchantWithdrawEntity entity = (MerchantWithdrawEntity) iterator.next();
				MerchantWithdrawDTO dto = new MerchantWithdrawDTO();
				merchantWithdrawEntity2DTO(entity, dto);
				dtoList.add(dto);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return dtoList;
	}
	
	@Override
	public int searchMerchantWithdrawTotal(WithdrawSearchDTO withdrawSearchDTO) throws BusinessException {
		try {
			return merchantWithdrawDAO.searchMerchantWithdrawTotal(withdrawSearchDTO);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}
	
	@Override
	public BigDecimal searchMerchantWithdrawTotalAmount(WithdrawSearchDTO withdrawSearchDTO) throws BusinessException {
		try {
			return merchantWithdrawDAO.searchMerchantWithdrawTotalAmount(withdrawSearchDTO);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}
	
	@Override
	public void failUserWithdraw(UserWithdrawDTO withdrawDTO)
			throws BusinessException {
		try{
			UserWithdrawEntity withDrawEntity = userWithdrawDAO.getWithdrawByUuid(withdrawDTO.getUserWithdrawUuid());
			if(FundConstants.WITHDRAW_STATUS_FAIL.equals(withDrawEntity.getWithdrawStatus())){
				return;
			}
			withDrawEntity.setWithdrawStatus(FundConstants.WITHDRAW_STATUS_FAIL);
			withDrawEntity.setFailReason(withdrawDTO.getFailReason());
			withDrawEntity.setCompleteTime(new Date());
			userWithdrawDAO.updateUserWithdraw(withDrawEntity);
			
			//更新商户资金余额
			UserEntity userEntity = withDrawEntity.getUserEntity();
			UserBalanceEntity userBalanceEntity = userBalanceDAO.getBalanceByUser(userEntity);
			if(userBalanceEntity == null){
				userBalanceEntity = new UserBalanceEntity();
				userBalanceEntity.setUserEntity(userEntity);
				userBalanceEntity.setAvailableBalance(new BigDecimal(0));
				userBalanceEntity.setLedgerBalance(new BigDecimal(0));
				userBalanceDAO.createUserBalance(userBalanceEntity);
			}
			userBalanceEntity.setAvailableBalance(userBalanceEntity.getAvailableBalance().add(withDrawEntity.getWithdrawAmount()));
			userBalanceDAO.updateUserBalance(userBalanceEntity);
			
			//发送提现失败
			UserDTO userDTO = new UserDTO();
			userDTO.setUserUuid(userEntity.getUserUuid());
			noteService.sendNotification(userDTO, "withdrawfail", new String[]{String.valueOf(withDrawEntity.getWithdrawAmount()),withdrawDTO.getFailReason()});
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		
	}
	
	@Override
	public void commentUserWithdraw(UserWithdrawDTO withdrawDTO)
			throws BusinessException {
		try{
			UserWithdrawEntity withDrawEntity = userWithdrawDAO.getWithdrawByUuid(withdrawDTO.getUserWithdrawUuid());
			withDrawEntity.setMemo(withdrawDTO.getMemo());
			userWithdrawDAO.updateUserWithdraw(withDrawEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		
	}

	@Override
	public List<MerchantWithdrawDTO> getMerchantWithdrawByMerchant(MerchantDTO merchantDTO) throws BusinessException {
		List<MerchantWithdrawDTO> withDrawDTOList = new ArrayList<MerchantWithdrawDTO>();
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			List<MerchantWithdrawEntity> withDrawEntityList = merchantWithdrawDAO.getWithdrawByMerchant(merchantEntity);
			for (Iterator<MerchantWithdrawEntity> iter = withDrawEntityList.iterator(); iter.hasNext();) {
				MerchantWithdrawEntity withdrawEntity = iter.next();
				MerchantWithdrawDTO withdrawDTO = new MerchantWithdrawDTO();
				withdrawEntity2DTO(withdrawEntity, withdrawDTO);
				withDrawDTOList.add(withdrawDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		return withDrawDTOList;
	}

	@Override
	public List<UserWithdrawDTO> getUserWithdrawByUser(UserDTO userDTO) throws BusinessException {
		List<UserWithdrawDTO> withDrawDTOList = new ArrayList<UserWithdrawDTO>();
		try {
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			List<UserWithdrawEntity> withDrawEntityList = userWithdrawDAO.getWithdrawByUser(userEntity);
			for (Iterator<UserWithdrawEntity> iter = withDrawEntityList.iterator(); iter.hasNext();) {
				UserWithdrawEntity withdrawEntity = iter.next();
				UserWithdrawDTO withdrawDTO = new UserWithdrawDTO();
				userWithdrawEntity2DTO(withdrawEntity, withdrawDTO);
				withDrawDTOList.add(withdrawDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		return withDrawDTOList;
	}

	@Override
	public List<MerchantChargeDTO> getMerchantChargeByMerchant(MerchantDTO merchantDTO) throws BusinessException {
		List<MerchantChargeDTO> chargeDTOList = new ArrayList<MerchantChargeDTO>();
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			List<MerchantChargeEntity> chargeEntityList = merchantChargeDAO.getChargeByMerchant(merchantEntity);
			for (Iterator<MerchantChargeEntity> iter = chargeEntityList.iterator(); iter.hasNext();) {
				MerchantChargeEntity chargeEntity = iter.next();
				MerchantChargeDTO chargeDTO = new MerchantChargeDTO();
				chargeEntity2DTO(chargeEntity, chargeDTO);
				chargeDTOList.add(chargeDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		return chargeDTOList;
	}

	@Override
	public List<MerchantChargeDTO> getMerchantCharges() throws BusinessException {
		List<MerchantChargeDTO> chargeDTOList = new ArrayList<MerchantChargeDTO>();
		try {
			List<MerchantChargeEntity> chargeEntityList = merchantChargeDAO.getCharges();
			for (Iterator<MerchantChargeEntity> iter = chargeEntityList.iterator(); iter.hasNext();) {
				MerchantChargeEntity chargeEntity = iter.next();
				MerchantChargeDTO chargeDTO = new MerchantChargeDTO();
				chargeEntity2DTO(chargeEntity, chargeDTO);
				chargeDTOList.add(chargeDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		return chargeDTOList;
	}

	@Override
	public MerchantChargeDTO getMerchantChargeByChargeNo(String chargeNo) throws BusinessException {
		try {
			MerchantChargeEntity chargeEntity = merchantChargeDAO.getChargeByNo(chargeNo);
			MerchantChargeDTO chargeDTO = new MerchantChargeDTO();
			chargeEntity2DTO(chargeEntity, chargeDTO);
			return chargeDTO;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
	}

	@Override
	public MerchantChargeDTO getMerchantChargeByUuid(String uuid) throws BusinessException {
		try {
			MerchantChargeEntity chargeEntity = merchantChargeDAO.getChargeByUuid(uuid);
			MerchantChargeDTO chargeDTO = new MerchantChargeDTO();
			chargeEntity2DTO(chargeEntity, chargeDTO);
			return chargeDTO;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
	}

	@Override
	public List<MerchantWithdrawDTO> getMerchantWithdraws() throws BusinessException {
		List<MerchantWithdrawDTO> withDrawDTOList = new ArrayList<MerchantWithdrawDTO>();
		try {
			List<MerchantWithdrawEntity> withDrawEntityList = merchantWithdrawDAO.getWithdraws();
			for (Iterator<MerchantWithdrawEntity> iter = withDrawEntityList.iterator(); iter.hasNext();) {
				MerchantWithdrawEntity withdrawEntity = iter.next();
				MerchantWithdrawDTO withdrawDTO = new MerchantWithdrawDTO();
				withdrawEntity2DTO(withdrawEntity, withdrawDTO);
				withDrawDTOList.add(withdrawDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		return withDrawDTOList;
	}

	@Override
	public List<UserWithdrawDTO> getUserWithdraws() throws BusinessException {
		List<UserWithdrawDTO> withDrawDTOList = new ArrayList<UserWithdrawDTO>();
		try {
			List<UserWithdrawEntity> withDrawEntityList = userWithdrawDAO.getWithdraws();
			for (Iterator<UserWithdrawEntity> iter = withDrawEntityList.iterator(); iter.hasNext();) {
				UserWithdrawEntity withdrawEntity = iter.next();
				UserWithdrawDTO withdrawDTO = new UserWithdrawDTO();
				userWithdrawEntity2DTO(withdrawEntity, withdrawDTO);
				withDrawDTOList.add(withdrawDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		return withDrawDTOList;
	}

	@Override
	public List<MerchantWithdrawDTO> getPendingVerifyMerchantWithdraw() throws BusinessException {
		List<MerchantWithdrawDTO> withDrawDTOList = new ArrayList<MerchantWithdrawDTO>();
		try {
			List<MerchantWithdrawEntity> withDrawEntityList = merchantWithdrawDAO
					.getWithdrawByStatus(FundConstants.WITHDRAW_STATUS_APPLICATED);
			for (Iterator<MerchantWithdrawEntity> iter = withDrawEntityList.iterator(); iter.hasNext();) {
				MerchantWithdrawEntity withdrawEntity = iter.next();
				MerchantWithdrawDTO withdrawDTO = new MerchantWithdrawDTO();
				withdrawEntity2DTO(withdrawEntity, withdrawDTO);
				withDrawDTOList.add(withdrawDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		return withDrawDTOList;
	}

	@Override
	public List<UserWithdrawDTO> getPendingVerifyUserWithdraw() throws BusinessException {
		List<UserWithdrawDTO> withDrawDTOList = new ArrayList<UserWithdrawDTO>();
		try {
			List<UserWithdrawEntity> withDrawEntityList = userWithdrawDAO
					.getWithdrawByStatus(FundConstants.WITHDRAW_STATUS_APPLICATED);
			for (Iterator<UserWithdrawEntity> iter = withDrawEntityList.iterator(); iter.hasNext();) {
				UserWithdrawEntity withdrawEntity = iter.next();
				UserWithdrawDTO withdrawDTO = new UserWithdrawDTO();
				userWithdrawEntity2DTO(withdrawEntity, withdrawDTO);
				withDrawDTOList.add(withdrawDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		return withDrawDTOList;
	}

	@Override
	public List<MerchantWithdrawDTO> getPendingCompleteMerchantWithdraw() throws BusinessException {
		List<MerchantWithdrawDTO> withDrawDTOList = new ArrayList<MerchantWithdrawDTO>();
		try {
			List<MerchantWithdrawEntity> withDrawEntityList = merchantWithdrawDAO
					.getWithdrawByStatus(FundConstants.WITHDRAW_STATUS_APPROVED);
			for (Iterator<MerchantWithdrawEntity> iter = withDrawEntityList.iterator(); iter.hasNext();) {
				MerchantWithdrawEntity withdrawEntity = iter.next();
				MerchantWithdrawDTO withdrawDTO = new MerchantWithdrawDTO();
				withdrawEntity2DTO(withdrawEntity, withdrawDTO);
				withDrawDTOList.add(withdrawDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		return withDrawDTOList;
	}

	@Override
	public List<UserWithdrawDTO> getPendingCompleteUserWithdraw() throws BusinessException {
		List<UserWithdrawDTO> withDrawDTOList = new ArrayList<UserWithdrawDTO>();
		try {
			List<UserWithdrawEntity> withDrawEntityList = userWithdrawDAO
					.getWithdrawByStatus(FundConstants.WITHDRAW_STATUS_APPROVED);
			for (Iterator<UserWithdrawEntity> iter = withDrawEntityList.iterator(); iter.hasNext();) {
				UserWithdrawEntity withdrawEntity = iter.next();
				UserWithdrawDTO withdrawDTO = new UserWithdrawDTO();
				userWithdrawEntity2DTO(withdrawEntity, withdrawDTO);
				withDrawDTOList.add(withdrawDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		return withDrawDTOList;
	}

	@Override
	public List<MerchantWithdrawDTO> getSuccessMerchantWithdraw() throws BusinessException {
		List<MerchantWithdrawDTO> withDrawDTOList = new ArrayList<MerchantWithdrawDTO>();
		try {
			List<MerchantWithdrawEntity> withDrawEntityList = merchantWithdrawDAO
					.getWithdrawByStatus(FundConstants.WITHDRAW_STATUS_SUCCESS);
			for (Iterator<MerchantWithdrawEntity> iter = withDrawEntityList.iterator(); iter.hasNext();) {
				MerchantWithdrawEntity withdrawEntity = iter.next();
				MerchantWithdrawDTO withdrawDTO = new MerchantWithdrawDTO();
				withdrawEntity2DTO(withdrawEntity, withdrawDTO);
				withDrawDTOList.add(withdrawDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		return withDrawDTOList;
	}

	@Override
	public List<UserWithdrawDTO> getSuccessUserWithdraw() throws BusinessException {
		List<UserWithdrawDTO> withDrawDTOList = new ArrayList<UserWithdrawDTO>();
		try {
			List<UserWithdrawEntity> withDrawEntityList = userWithdrawDAO
					.getWithdrawByStatus(FundConstants.WITHDRAW_STATUS_SUCCESS);
			for (Iterator<UserWithdrawEntity> iter = withDrawEntityList.iterator(); iter.hasNext();) {
				UserWithdrawEntity withdrawEntity = iter.next();
				UserWithdrawDTO withdrawDTO = new UserWithdrawDTO();
				userWithdrawEntity2DTO(withdrawEntity, withdrawDTO);
				withDrawDTOList.add(withdrawDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		return withDrawDTOList;
	}

	@Override
	public List<MerchantWithdrawDTO> getFailMerchantWithdraw() throws BusinessException {
		List<MerchantWithdrawDTO> withDrawDTOList = new ArrayList<MerchantWithdrawDTO>();
		try {
			List<MerchantWithdrawEntity> withDrawEntityList = merchantWithdrawDAO
					.getWithdrawByStatus(FundConstants.WITHDRAW_STATUS_FAIL);
			for (Iterator<MerchantWithdrawEntity> iter = withDrawEntityList.iterator(); iter.hasNext();) {
				MerchantWithdrawEntity withdrawEntity = iter.next();
				MerchantWithdrawDTO withdrawDTO = new MerchantWithdrawDTO();
				withdrawEntity2DTO(withdrawEntity, withdrawDTO);
				withDrawDTOList.add(withdrawDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		return withDrawDTOList;
	}

	@Override
	public List<UserWithdrawDTO> getFailUserWithdraw() throws BusinessException {
		List<UserWithdrawDTO> withDrawDTOList = new ArrayList<UserWithdrawDTO>();
		try {
			List<UserWithdrawEntity> withDrawEntityList = userWithdrawDAO
					.getWithdrawByStatus(FundConstants.WITHDRAW_STATUS_FAIL);
			for (Iterator<UserWithdrawEntity> iter = withDrawEntityList.iterator(); iter.hasNext();) {
				UserWithdrawEntity withdrawEntity = iter.next();
				UserWithdrawDTO withdrawDTO = new UserWithdrawDTO();
				userWithdrawEntity2DTO(withdrawEntity, withdrawDTO);
				withDrawDTOList.add(withdrawDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		return withDrawDTOList;
	}

	@Override
	public List<MerchantWithdrawDTO> getRejectMerchantWithdraw() throws BusinessException {
		List<MerchantWithdrawDTO> withDrawDTOList = new ArrayList<MerchantWithdrawDTO>();
		try {
			List<MerchantWithdrawEntity> withDrawEntityList = merchantWithdrawDAO
					.getWithdrawByStatus(FundConstants.WITHDRAW_STATUS_REJECTED);
			for (Iterator<MerchantWithdrawEntity> iter = withDrawEntityList.iterator(); iter.hasNext();) {
				MerchantWithdrawEntity withdrawEntity = iter.next();
				MerchantWithdrawDTO withdrawDTO = new MerchantWithdrawDTO();
				withdrawEntity2DTO(withdrawEntity, withdrawDTO);
				withDrawDTOList.add(withdrawDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		return withDrawDTOList;
	}

	@Override
	public List<UserWithdrawDTO> getRejectUserWithdraw() throws BusinessException {
		List<UserWithdrawDTO> withDrawDTOList = new ArrayList<UserWithdrawDTO>();
		try {
			List<UserWithdrawEntity> withDrawEntityList = userWithdrawDAO
					.getWithdrawByStatus(FundConstants.WITHDRAW_STATUS_REJECTED);
			for (Iterator<UserWithdrawEntity> iter = withDrawEntityList.iterator(); iter.hasNext();) {
				UserWithdrawEntity withdrawEntity = iter.next();
				UserWithdrawDTO withdrawDTO = new UserWithdrawDTO();
				userWithdrawEntity2DTO(withdrawEntity, withdrawDTO);
				withDrawDTOList.add(withdrawDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		return withDrawDTOList;
	}

	@Override
	public MerchantWithdrawDTO getMerchantWithdrawByUuid(String uuid) throws BusinessException {
		try {
			MerchantWithdrawEntity withdrawEntity = merchantWithdrawDAO.getWithdrawByUuid(uuid);
			MerchantWithdrawDTO withdrawDTO = new MerchantWithdrawDTO();
			withdrawEntity2DTO(withdrawEntity, withdrawDTO);
			return withdrawDTO;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
	}

	@Override
	public UserWithdrawDTO getUserWithdrawByUuid(String uuid) throws BusinessException {
		try {
			UserWithdrawEntity withdrawEntity = userWithdrawDAO.getWithdrawByUuid(uuid);
			UserWithdrawDTO withdrawDTO = new UserWithdrawDTO();
			userWithdrawEntity2DTO(withdrawEntity, withdrawDTO);
			return withdrawDTO;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
	}

	void withdrawEntity2DTO(MerchantWithdrawEntity withdrawEntity, MerchantWithdrawDTO withdrawDTO) {

		withdrawDTO.setAlipayId(withdrawEntity.getAlipayId());
		withdrawDTO.setBankAccountName(withdrawEntity.getBankAccountName());
		withdrawDTO.setBankAccountNo(withdrawEntity.getBankAccountNo());
		withdrawDTO.setBankName(withdrawEntity.getBankName());
		withdrawDTO.setCompleteTime(withdrawEntity.getCompleteTime());
		withdrawDTO.setFailReason(withdrawEntity.getFailReason());
		withdrawDTO.setMerchantWithdrawUuid(withdrawEntity.getMerchantWithdrawUuid());
		withdrawDTO.setPaymentMethod(withdrawEntity.getPaymentMethod());
		withdrawDTO.setRejectReason(withdrawEntity.getRejectReason());
		withdrawDTO.setVerifyTime(withdrawEntity.getVerifyTime());
		withdrawDTO.setWechatId(withdrawEntity.getWechatId());
		withdrawDTO.setWithdrawAmount(withdrawEntity.getWithdrawAmount());
		withdrawDTO.setPaymentAmount(withdrawEntity.getPaymentAmount());
		withdrawDTO.setTaxAmount(withdrawEntity.getTaxAmount());
		withdrawDTO.setWithdrawNo(withdrawEntity.getWithdrawNo());
		withdrawDTO.setWithdrawStatus(withdrawEntity.getWithdrawStatus());
		withdrawDTO.setWithdrawTime(withdrawEntity.getWithdrawTime());
		MerchantEntity merchantEntity = withdrawEntity.getMerchantEntity();
		if (merchantEntity != null) {
			MerchantDTO merchantDTO = new MerchantDTO();
			merchantDTO.setMerchantUuid(merchantEntity.getMerchantUuid());
			merchantDTO.setMerchantName(merchantEntity.getMerchantName());
			withdrawDTO.setMerchantDTO(merchantDTO);
		}
	}

	void userWithdrawEntity2DTO(UserWithdrawEntity withdrawEntity, UserWithdrawDTO withdrawDTO) {

		withdrawDTO.setAlipayId(withdrawEntity.getAlipayId());
		withdrawDTO.setBankAccountName(withdrawEntity.getBankAccountName());
		withdrawDTO.setBankAccountNo(withdrawEntity.getBankAccountNo());
		withdrawDTO.setBankName(withdrawEntity.getBankName());
		withdrawDTO.setCompleteTime(withdrawEntity.getCompleteTime());
		withdrawDTO.setFailReason(withdrawEntity.getFailReason());
		withdrawDTO.setUserWithdrawUuid(withdrawEntity.getUserWithdrawUuid());
		withdrawDTO.setPaymentMethod(withdrawEntity.getPaymentMethod());
		withdrawDTO.setRejectReason(withdrawEntity.getRejectReason());
		withdrawDTO.setVerifyTime(withdrawEntity.getVerifyTime());
		withdrawDTO.setWechatId(withdrawEntity.getWechatId());
		withdrawDTO.setWithdrawAmount(withdrawEntity.getWithdrawAmount());
		withdrawDTO.setPaymentAmount(withdrawEntity.getPaymentAmount());
		withdrawDTO.setTaxAmount(withdrawEntity.getTaxAmount());
		withdrawDTO.setWithdrawNo(withdrawEntity.getWithdrawNo());
		withdrawDTO.setWithdrawStatus(withdrawEntity.getWithdrawStatus());
		withdrawDTO.setWithdrawTime(withdrawEntity.getWithdrawTime());
		withdrawDTO.setMemo(withdrawEntity.getMemo());
		UserEntity userEntity = withdrawEntity.getUserEntity();
		if (userEntity != null) {
			UserDTO userDTO = new UserDTO();
			userDTO.setUserUuid(userEntity.getUserUuid());
			userDTO.setName(userEntity.getName());
			userDTO.setId(userEntity.getId());
			withdrawDTO.setUserDTO(userDTO);
		}
	}
	
	void merchantWithdrawEntity2DTO(MerchantWithdrawEntity withdrawEntity, MerchantWithdrawDTO withdrawDTO) {

		withdrawDTO.setAlipayId(withdrawEntity.getAlipayId());
		withdrawDTO.setBankAccountName(withdrawEntity.getBankAccountName());
		withdrawDTO.setBankAccountNo(withdrawEntity.getBankAccountNo());
		withdrawDTO.setBankName(withdrawEntity.getBankName());
		withdrawDTO.setCompleteTime(withdrawEntity.getCompleteTime());
		withdrawDTO.setFailReason(withdrawEntity.getFailReason());
		withdrawDTO.setMerchantWithdrawUuid(withdrawEntity.getMerchantWithdrawUuid());
		withdrawDTO.setPaymentMethod(withdrawEntity.getPaymentMethod());
		withdrawDTO.setRejectReason(withdrawEntity.getRejectReason());
		withdrawDTO.setVerifyTime(withdrawEntity.getVerifyTime());
		withdrawDTO.setWechatId(withdrawEntity.getWechatId());
		withdrawDTO.setWithdrawAmount(withdrawEntity.getWithdrawAmount());
		withdrawDTO.setPaymentAmount(withdrawEntity.getPaymentAmount());
		withdrawDTO.setTaxAmount(withdrawEntity.getTaxAmount());
		withdrawDTO.setWithdrawNo(withdrawEntity.getWithdrawNo());
		withdrawDTO.setWithdrawStatus(withdrawEntity.getWithdrawStatus());
		withdrawDTO.setWithdrawTime(withdrawEntity.getWithdrawTime());
		withdrawDTO.setMemo(withdrawEntity.getMemo());
		MerchantEntity merchantEntity = withdrawEntity.getMerchantEntity();
		if (merchantEntity != null) {
			MerchantDTO merchantDTO = new MerchantDTO();
			merchantDTO.setMerchantUuid(merchantEntity.getMerchantUuid());
			merchantDTO.setMerchantName(merchantEntity.getMerchantName());
			merchantDTO.setMobileNo(merchantEntity.getMobileNo());
			withdrawDTO.setMerchantDTO(merchantDTO);
		}
	}

	void userAwardEntity2DTO(UserAwardEntity awardEntity, UserAwardDTO awardDTO) {

		awardDTO.setTransactionAmount(awardEntity.getTransactionAmount());
		awardDTO.setTransactionCode(awardEntity.getTransactionCode());
		awardDTO.setTransactionTime(awardEntity.getTransactionTime());
		awardDTO.setTransactionType(awardEntity.getTransactionType());
		awardDTO.setTransactionDesc(awardEntity.getTransactionDesc());
		awardDTO.setMemo(awardEntity.getMemo());
		awardDTO.setUserAwardUuid(awardEntity.getUserAwardUuid());
		UserEntity userEntity = awardEntity.getUserEntity();
		if (userEntity != null) {
			UserDTO userDTO = new UserDTO();
			userDTO.setUserUuid(userEntity.getUserUuid());
			userDTO.setName(userEntity.getName());
			userDTO.setPersonalPhone(userEntity.getPersonalPhone());
			awardDTO.setUserDTO(userDTO);
		}
	}

	void chargeEntity2DTO(MerchantChargeEntity chargeEntity, MerchantChargeDTO chargeDTO) {

		chargeDTO.setAlipayId(chargeEntity.getAlipayId());
		chargeDTO.setBankAccountName(chargeEntity.getBankAccountName());
		chargeDTO.setBankAccountNo(chargeEntity.getBankAccountNo());
		chargeDTO.setBankName(chargeEntity.getBankName());
		chargeDTO.setFailReason(chargeEntity.getFailReason());
		chargeDTO.setMerchantChargeUuid(chargeEntity.getMerchantChargeUuid());
		chargeDTO.setPaymentMethod(chargeEntity.getPaymentMethod());
		chargeDTO.setWechatId(chargeEntity.getWechatId());
		chargeDTO.setChargeAmount(chargeEntity.getChargeAmount());
		chargeDTO.setChargePoint(chargeEntity.getChargePoint());
		chargeDTO.setChargeNo(chargeEntity.getChargeNo());
		chargeDTO.setChargeStatus(chargeEntity.getChargeStatus());
		chargeDTO.setChargeTime(chargeEntity.getChargeTime());
		chargeDTO.setOutTradeNo(chargeEntity.getOutTradeNo());
		MerchantEntity merchantEntity = chargeEntity.getMerchantEntity();
		if (merchantEntity != null) {
			MerchantDTO merchantDTO = new MerchantDTO();
			merchantDTO.setMerchantUuid(merchantEntity.getMerchantUuid());
			merchantDTO.setMerchantName(merchantEntity.getMerchantName());
			merchantDTO.setMobileNo(merchantEntity.getMobileNo());
			chargeDTO.setMerchantDTO(merchantDTO);
		}
	}

	@Override
	public MerchantDTO getMerchantBalance(MerchantDTO merchantDTO) throws BusinessException {
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			MerchantBalanceEntity balanceEntity = merchantBalanceDAO.getBalanceByMerchant(merchantEntity);
//			merchantDTO.setAvailableBalance(balanceEntity.getAvailableBalance());
//			merchantDTO.setTotalBalance(balanceEntity.getTotalBalance());
//			merchantDTO.setAvailableIntegral(balanceEntity.getAvailableIntegral());
//			merchantDTO.setTotalIntegral(balanceEntity.getTotalIntegral());
			return merchantDTO;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
	}

	@Override
	public List<MerchantStatementDTO> getMerchantStatement(MerchantDTO merchantDTO) throws BusinessException {
		List<MerchantStatementDTO> list = new ArrayList<MerchantStatementDTO>();
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			List<MerchantStatementEntity> statementEntityList = merchantStatementDAO
					.getStatementByMerchant(merchantEntity);
			for (Iterator<MerchantStatementEntity> iter = statementEntityList.iterator(); iter.hasNext();) {
				MerchantStatementEntity statementEntity = iter.next();
				MerchantStatementDTO statementDTO = new MerchantStatementDTO();
				statementDTO.setTransactionAmount(statementEntity.getTransactionAmount());
				statementDTO.setTransactionCode(statementEntity.getTransactionCode());
				statementDTO.setTransactionTime(statementEntity.getTransactionTime());
				statementDTO.setTransactionType(statementEntity.getTransactionType());
				list.add(statementDTO);
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		return list;
	}

	@Override
	public List<UserAwardDTO> getUserAward(UserDTO userDTO) throws BusinessException {
		List<UserAwardDTO> list = new ArrayList<UserAwardDTO>();
		try {
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			List<UserAwardEntity> awardEntityList = userAwardDAO.getAwardByUser(userEntity);
			for (Iterator<UserAwardEntity> iter = awardEntityList.iterator(); iter.hasNext();) {
				UserAwardEntity awardEntity = iter.next();
				UserAwardDTO awardDTO = new UserAwardDTO();
				awardDTO.setUserAwardUuid(awardEntity.getUserAwardUuid());
				awardDTO.setTransactionAmount(awardEntity.getTransactionAmount());
				awardDTO.setTransactionCode(awardEntity.getTransactionCode());
				awardDTO.setTransactionTime(awardEntity.getTransactionTime());
				awardDTO.setTransactionType(awardEntity.getTransactionType());
				awardDTO.setTransactionDesc(awardEntity.getTransactionDesc());
				list.add(awardDTO);
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		return list;
	}

	@Override
	public UserAwardDTO getUserAwardByUuid(String uuid) throws BusinessException {
		try {
			UserAwardEntity awardEntity = userAwardDAO.getAwardByUuid(uuid);
			UserAwardDTO awardDTO = new UserAwardDTO();
			awardDTO.setUserAwardUuid(awardEntity.getUserAwardUuid());
			awardDTO.setTransactionAmount(awardEntity.getTransactionAmount());
			awardDTO.setTransactionCode(awardEntity.getTransactionCode());
			awardDTO.setTransactionTime(awardEntity.getTransactionTime());
			awardDTO.setTransactionType(awardEntity.getTransactionType());
			awardDTO.setTransactionDesc(awardEntity.getTransactionDesc());
			return awardDTO;

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
	}

	@Override
	public List<UserStatementDTO> getUserStatement(UserDTO userDTO) throws BusinessException {
		List<UserStatementDTO> list = new ArrayList<UserStatementDTO>();
		try {
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			List<UserStatementEntity> statementEntityList = userStatementDAO.getStatementByUser(userEntity);
			for (Iterator<UserStatementEntity> iter = statementEntityList.iterator(); iter.hasNext();) {
				UserStatementEntity statementEntity = iter.next();
				UserStatementDTO statementDTO = new UserStatementDTO();
				statementDTO.setTransactionAmount(statementEntity.getTransactionAmount());
				statementDTO.setTransactionCode(statementEntity.getTransactionCode());
				statementDTO.setTransactionTime(statementEntity.getTransactionTime());
				statementDTO.setTransactionType(statementEntity.getTransactionType());
				statementDTO.setPointBefore(statementEntity.getPointBefore());
				list.add(statementDTO);
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		return list;
	}

	@Override
	public List<MerchantStatementDTO> getMerchantStatementByType(MerchantDTO merchantDTO, String transactionType)
			throws BusinessException {
		List<MerchantStatementDTO> list = new ArrayList<MerchantStatementDTO>();
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			List<MerchantStatementEntity> statementEntityList = merchantStatementDAO
					.getStatementByMerchantType(merchantEntity, transactionType);
			for (Iterator<MerchantStatementEntity> iter = statementEntityList.iterator(); iter.hasNext();) {
				MerchantStatementEntity statementEntity = iter.next();
				MerchantStatementDTO statementDTO = new MerchantStatementDTO();
				statementDTO.setTransactionAmount(statementEntity.getTransactionAmount());
				statementDTO.setTransactionCode(statementEntity.getTransactionCode());
				statementDTO.setTransactionTime(statementEntity.getTransactionTime());
				statementDTO.setTransactionType(statementEntity.getTransactionType());
				statementDTO.setPointBefore(statementEntity.getPointBefore());
				statementDTO.setPointAfter(statementEntity.getPointAfter());
				// 返佣需要找到订单
				if (FundConstants.MERCHANT_STATEMENT_TRANSACTION_TYPE_MONEY_RETURN
						.equals(statementEntity.getTransactionType())) {
					String orderNo = statementEntity.getTransactionCode();
					if (!StringUtils.isEmpty(orderNo)) {
						OrderEntity orderEntity = orderDAO.getOrderByOrderNo(orderNo);
						if (orderEntity != null) {
							OrderDTO orderDTO = new OrderDTO();
							orderDTO.setOrderNo(orderEntity.getOrderNo());
							orderDTO.setProductAmount(orderEntity.getProductAmount());
							orderDTO.setActualAmount(orderEntity.getActualAmount());
							orderDTO.setOrderUuid(orderEntity.getOrderUuid());
							statementDTO.setOrderDTO(orderDTO);
						}

					}

				}
				list.add(statementDTO);
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		return list;
	}

	@Override
	public List<UserStatementDTO> getUserStatementByType(UserDTO userDTO, String transactionType)
			throws BusinessException {
		List<UserStatementDTO> list = new ArrayList<UserStatementDTO>();
		try {
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			List<UserStatementEntity> statementEntityList = userStatementDAO.getStatementByUserType(userEntity,
					transactionType);
			for (Iterator<UserStatementEntity> iter = statementEntityList.iterator(); iter.hasNext();) {
				UserStatementEntity statementEntity = iter.next();
				UserStatementDTO statementDTO = new UserStatementDTO();
				statementDTO.setTransactionAmount(statementEntity.getTransactionAmount());
				statementDTO.setTransactionCode(statementEntity.getTransactionCode());
				statementDTO.setTransactionTime(statementEntity.getTransactionTime());
				statementDTO.setTransactionType(statementEntity.getTransactionType());
				statementDTO.setPointBefore(statementEntity.getPointBefore());
				// 返佣需要找到订单
				if (FundConstants.MERCHANT_STATEMENT_TRANSACTION_TYPE_MONEY_RETURN
						.equals(statementEntity.getTransactionType())) {
					String orderNo = statementEntity.getTransactionCode();
					if (!StringUtils.isEmpty(orderNo)) {
						OrderEntity orderEntity = orderDAO.getOrderByOrderNo(orderNo);
						if (orderEntity != null) {
							OrderDTO orderDTO = new OrderDTO();
							orderDTO.setOrderNo(orderEntity.getOrderNo());
							orderDTO.setProductAmount(orderEntity.getProductAmount());
							orderDTO.setActualAmount(orderEntity.getActualAmount());
							orderDTO.setOrderUuid(orderEntity.getOrderUuid());
							statementDTO.setOrderDTO(orderDTO);
						}

					}

				}
				list.add(statementDTO);
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		return list;
	}

	@Override
	public List<UserAwardDTO> getUserAwardByType(UserDTO userDTO, String transactionType) throws BusinessException {
		List<UserAwardDTO> list = new ArrayList<UserAwardDTO>();
		try {
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			List<UserAwardEntity> awardEntityList = userAwardDAO.getAwardByUserType(userEntity, transactionType);
			for (Iterator<UserAwardEntity> iter = awardEntityList.iterator(); iter.hasNext();) {
				UserAwardEntity awardEntity = iter.next();
				UserAwardDTO awardDTO = new UserAwardDTO();
				awardDTO.setTransactionAmount(awardEntity.getTransactionAmount());
				awardDTO.setTransactionCode(awardEntity.getTransactionCode());
				awardDTO.setTransactionTime(awardEntity.getTransactionTime());
				awardDTO.setTransactionType(awardEntity.getTransactionType());
				awardDTO.setTransactionDesc(awardEntity.getTransactionDesc());
				list.add(awardDTO);
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		return list;
	}

	@Override
	public List<MerchantStatementDTO> getMerchantStatementByDate(MerchantDTO merchantDTO, Date startDate, Date endDate)
			throws BusinessException {
		List<MerchantStatementDTO> list = new ArrayList<MerchantStatementDTO>();
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			List<MerchantStatementEntity> statementEntityList = merchantStatementDAO.getStatementByDate(merchantEntity,
					startDate, endDate);
			for (Iterator<MerchantStatementEntity> iter = statementEntityList.iterator(); iter.hasNext();) {
				MerchantStatementEntity statementEntity = iter.next();
				MerchantStatementDTO statementDTO = new MerchantStatementDTO();
				statementDTO.setTransactionAmount(statementEntity.getTransactionAmount());
				statementDTO.setTransactionCode(statementEntity.getTransactionCode());
				statementDTO.setTransactionTime(statementEntity.getTransactionTime());
				statementDTO.setTransactionType(statementEntity.getTransactionType());
				statementDTO.setPointBefore(statementEntity.getPointBefore());
				statementDTO.setPointAfter(statementEntity.getPointAfter());
				list.add(statementDTO);
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		return list;
	}

	@Override
	public List<UserStatementDTO> getUserStatementByDate(UserDTO userDTO, Date startDate, Date endDate)
			throws BusinessException {
		List<UserStatementDTO> list = new ArrayList<UserStatementDTO>();
		try {
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			List<UserStatementEntity> statementEntityList = userStatementDAO.getStatementByDate(userEntity, startDate,
					endDate);
			for (Iterator<UserStatementEntity> iter = statementEntityList.iterator(); iter.hasNext();) {
				UserStatementEntity statementEntity = iter.next();
				UserStatementDTO statementDTO = new UserStatementDTO();
				statementDTO.setTransactionAmount(statementEntity.getTransactionAmount());
				statementDTO.setTransactionCode(statementEntity.getTransactionCode());
				statementDTO.setTransactionTime(statementEntity.getTransactionTime());
				statementDTO.setTransactionType(statementEntity.getTransactionType());
				statementDTO.setPointBefore(statementEntity.getPointBefore());
				statementDTO.setPointAfter(statementEntity.getPointAfter());
				list.add(statementDTO);
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		return list;
	}

	@Override
	public List<UserAwardDTO> getUserAwardByDate(UserDTO userDTO, Date startDate, Date endDate)
			throws BusinessException {
		List<UserAwardDTO> list = new ArrayList<UserAwardDTO>();
		try {
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			List<UserAwardEntity> awardEntityList = userAwardDAO.getAwardByDate(userEntity, startDate, endDate);
			for (Iterator<UserAwardEntity> iter = awardEntityList.iterator(); iter.hasNext();) {
				UserAwardEntity awardEntity = iter.next();
				UserAwardDTO awardDTO = new UserAwardDTO();
				awardDTO.setTransactionAmount(awardEntity.getTransactionAmount());
				awardDTO.setTransactionCode(awardEntity.getTransactionCode());
				awardDTO.setTransactionTime(awardEntity.getTransactionTime());
				awardDTO.setTransactionType(awardEntity.getTransactionType());
				awardDTO.setTransactionDesc(awardEntity.getTransactionDesc());
				list.add(awardDTO);
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		return list;
	}

	@Override
	public List<PlatformStatementDTO> getPlatformStatementByDate(Date startDate, Date endDate)
			throws BusinessException {
		List<PlatformStatementDTO> list = new ArrayList<PlatformStatementDTO>();
		try {
			List<PlatformStatementEntity> statementEntityList = platformStatementDAO.getStatementByDate(startDate,
					endDate);
			for (Iterator<PlatformStatementEntity> iter = statementEntityList.iterator(); iter.hasNext();) {
				PlatformStatementEntity statementEntity = iter.next();
				PlatformStatementDTO statementDTO = new PlatformStatementDTO();
				statementDTO.setTransactionAmount(statementEntity.getTransactionAmount());
				statementDTO.setTransactionCode(statementEntity.getTransactionCode());
				statementDTO.setTransactionTime(statementEntity.getTransactionTime());
				statementDTO.setTransactionType(statementEntity.getTransactionType());
				statementDTO.setBalance(statementEntity.getBalance());

				String orderNo = statementDTO.getTransactionCode();
				OrderEntity orderEntity = orderDAO.getOrderByOrderNo(orderNo);
				if (orderEntity != null) {
					OrderDTO orderDTO = new OrderDTO();
					orderDTO.setOrderNo(orderEntity.getOrderNo());
					orderDTO.setOrderUuid(orderEntity.getOrderUuid());
					orderDTO.setProductAmount(orderEntity.getProductAmount());
					orderDTO.setActualAmount(orderEntity.getActualAmount());
					orderDTO.setOrderTime(orderEntity.getOrderTime());
					statementDTO.setOrderDTO(orderDTO);
				}
				list.add(statementDTO);
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		return list;
	}

	@Override
	public List<MerchantStatementDTO> getMerchantStatementByTypeDate(String type, Date startDate, Date endDate)
			throws BusinessException {
		List<MerchantStatementDTO> list = new ArrayList<MerchantStatementDTO>();
		try {
			List<MerchantStatementEntity> statementEntityList = merchantStatementDAO.getStatementByDateType(startDate,
					endDate, type);
			for (Iterator<MerchantStatementEntity> iter = statementEntityList.iterator(); iter.hasNext();) {
				MerchantStatementEntity statementEntity = iter.next();
				MerchantStatementDTO statementDTO = new MerchantStatementDTO();
				statementDTO.setTransactionAmount(statementEntity.getTransactionAmount());
				statementDTO.setTransactionCode(statementEntity.getTransactionCode());
				statementDTO.setTransactionTime(statementEntity.getTransactionTime());
				statementDTO.setTransactionType(statementEntity.getTransactionType());
				statementDTO.setBalanceBefore(statementEntity.getBalanceBefore());
				statementDTO.setBalanceAfter(statementEntity.getBalanceAfter());
				statementDTO.setPointBefore(statementEntity.getPointBefore());
				statementDTO.setPointAfter(statementEntity.getPointAfter());
				MerchantEntity merchantEntity = statementEntity.getMerchantEntity();
				MerchantDTO merchantDTO = new MerchantDTO();
				merchantDTO.setMerchantUuid(merchantEntity.getMerchantUuid());
				merchantDTO.setMerchantName(merchantEntity.getMerchantName());
				statementDTO.setMerchantDTO(merchantDTO);

				String orderNo = statementDTO.getTransactionCode();
				OrderEntity orderEntity = orderDAO.getOrderByOrderNo(orderNo);
				if (orderEntity != null) {
					OrderDTO orderDTO = new OrderDTO();
					orderDTO.setOrderNo(orderEntity.getOrderNo());
					orderDTO.setOrderUuid(orderEntity.getOrderUuid());
					orderDTO.setProductAmount(orderEntity.getProductAmount());
					orderDTO.setActualAmount(orderEntity.getActualAmount());
					orderDTO.setOrderTime(orderEntity.getOrderTime());
					statementDTO.setOrderDTO(orderDTO);
				}

				list.add(statementDTO);
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		return list;
	}

	@Override
	public List<UserStatementDTO> getUserStatementByTypeDate(String type, Date startDate, Date endDate)
			throws BusinessException {
		List<UserStatementDTO> list = new ArrayList<UserStatementDTO>();
		try {
			List<UserStatementEntity> statementEntityList = userStatementDAO.getStatementByDateType(startDate, endDate,
					type);
			for (Iterator<UserStatementEntity> iter = statementEntityList.iterator(); iter.hasNext();) {
				UserStatementEntity statementEntity = iter.next();
				UserStatementDTO statementDTO = new UserStatementDTO();
				statementDTO.setTransactionAmount(statementEntity.getTransactionAmount());
				statementDTO.setTransactionCode(statementEntity.getTransactionCode());
				statementDTO.setTransactionTime(statementEntity.getTransactionTime());
				statementDTO.setTransactionType(statementEntity.getTransactionType());
				statementDTO.setPointBefore(statementEntity.getPointBefore());
				statementDTO.setPointAfter(statementEntity.getPointAfter());
				UserEntity userEntity = statementEntity.getUserEntity();
				UserDTO userDTO = new UserDTO();
				userDTO.setUserUuid(userEntity.getUserUuid());
				userDTO.setName(userEntity.getName());
				statementDTO.setUserDTO(userDTO);

				String orderNo = statementDTO.getTransactionCode();
				OrderEntity orderEntity = orderDAO.getOrderByOrderNo(orderNo);
				if (orderEntity != null) {
					OrderDTO orderDTO = new OrderDTO();
					orderDTO.setOrderNo(orderEntity.getOrderNo());
					orderDTO.setOrderUuid(orderEntity.getOrderUuid());
					orderDTO.setProductAmount(orderEntity.getProductAmount());
					orderDTO.setActualAmount(orderEntity.getActualAmount());
					orderDTO.setOrderTime(orderEntity.getOrderTime());
					statementDTO.setOrderDTO(orderDTO);
				}

				list.add(statementDTO);
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		return list;
	}

	@Override
	public BigDecimal getAvailableBalanceByMerchant(MerchantDTO merchantDTO) throws BusinessException {

		BigDecimal balance = new BigDecimal(0);
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			MerchantBalanceEntity merchantBalanceEntity = merchantBalanceDAO.getBalanceByMerchant(merchantEntity);
			if (merchantBalanceEntity != null) {
				balance = merchantBalanceEntity.getAvailableBalance();
			}
			return balance;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
	}

	@Override
	public BigDecimal getAvailableBalance() throws BusinessException {

		BigDecimal balance = null;
		try {
			balance = merchantBalanceDAO.getBalance();
			return balance;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
	}

	@Override
	public BigDecimal getTotalUserAvailableBalance() throws BusinessException {

		BigDecimal balance = null;
		try {
			balance = userBalanceDAO.getTotalAvailableBalance();
			return balance;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
	}

	@Override
	public BigDecimal getTranAmountByMerchantDate(MerchantDTO merchantDTO, Date startDate, Date endDate)
			throws BusinessException {

		BigDecimal tranAmount = null;
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			tranAmount = merchantStatementDAO.getTranAmountByMerchantDate(merchantEntity, startDate, endDate);
			return tranAmount;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
	}

	@Override
	public BigDecimal getPlatformTranAmountByDate(Date startDate, Date endDate) throws BusinessException {

		BigDecimal tranAmount = null;
		try {
			tranAmount = platformStatementDAO.getTranAmountByDate(startDate, endDate);
			return tranAmount;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
	}

	@Override
	public BigDecimal getPlatformBalance() throws BusinessException {

		BigDecimal tranAmount = null;
		try {
			PlatformBalanceEntity balanceEntity = platformBalanceDAO.getPlatformBalance();
			if (balanceEntity != null)
				tranAmount = balanceEntity.getTotalBalance();
			return tranAmount;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
	}

	@Override
	public BigDecimal getTranAmountByMerchantDateType(MerchantDTO merchantDTO, Date startDate, Date endDate,
			String tranType) throws BusinessException {

		BigDecimal tranAmount = null;
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			tranAmount = merchantStatementDAO.getTranAmountByMerchantDateType(merchantEntity, startDate, endDate,
					tranType);
			return tranAmount;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
	}

	@Override
	public BigDecimal getTranAmountByDateType(Date startDate, Date endDate, String tranType) throws BusinessException {

		BigDecimal tranAmount = null;
		try {
			tranAmount = merchantStatementDAO.getTranAmountByDateType(startDate, endDate, tranType);
			return tranAmount;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
	}

	@Override
	public BigDecimal getTranAmountByDate(Date startDate, Date endDate) throws BusinessException {

		BigDecimal tranAmount = null;
		try {
			tranAmount = merchantStatementDAO.getTranAmountByDate(startDate, endDate);
			return tranAmount;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
	}

	@Override
	public List<ChartDTO> getTranAmountChartByMerchantDate(MerchantDTO merchantDTO, Date startDate, Date endDate)
			throws BusinessException {
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			return merchantStatementDAO.getIncrementTranAmountChartByMerchant(merchantEntity, startDate, endDate);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
	}

	@Override
	public List<ChartDTO> getTranAmountChartByDate(Date startDate, Date endDate) throws BusinessException {
		try {
			return merchantStatementDAO.getIncrementTranAmountChart(startDate, endDate);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
	}

	@Override
	public List<ChartDTO> getAwardAmountChartByDate(Date startDate, Date endDate) throws BusinessException {
		try {
			return userAwardDAO.getIncrementTranAmountChart(startDate, endDate);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
	}

	@Override
	public void addMerchantAccount(MerchantAccountDTO accountDTO) throws BusinessException {
		try {
			MerchantEntity merchantEntity = merchantDAO
					.getMerchantByUuid(accountDTO.getMerchantDTO().getMerchantUuid());
			MerchantAccountEntity accountEntity = new MerchantAccountEntity();
			accountEntity.setMerchantEntity(merchantEntity);
			accountEntity.setAccountType(accountDTO.getAccountType());
			accountEntity.setBankCode(accountDTO.getBankCode());
			accountEntity.setBankName(accountDTO.getBankName());
			accountEntity.setBankAccountNo(accountDTO.getBankAccountNo());
			accountEntity.setBankAccountName(accountDTO.getBankAccountName());
			accountEntity.setAlipayId(accountDTO.getAlipayId());
			accountEntity.setAlipayQrCode(accountDTO.getAlipayQrCode());
			accountEntity.setWechatId(accountDTO.getWechatId());
			accountEntity.setWechatQrCode(accountDTO.getWechatQrCode());
			merchantAccountDAO.createMerchantAccount(accountEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}

	}

	@Override
	public void updateMerchantAccount(MerchantAccountDTO accountDTO) throws BusinessException {
		try {
			MerchantAccountEntity accountEntity = merchantAccountDAO
					.getAccountByUuid(accountDTO.getMerchantAccountUuid());
			accountEntity.setAccountType(accountDTO.getAccountType());
			accountEntity.setBankCode(accountDTO.getBankCode());
			accountEntity.setBankName(accountDTO.getBankName());
			accountEntity.setBankAccountNo(accountDTO.getBankAccountNo());
			accountEntity.setBankAccountName(accountDTO.getBankAccountName());
			accountEntity.setAlipayId(accountDTO.getAlipayId());
			accountEntity.setAlipayQrCode(accountDTO.getAlipayQrCode());
			accountEntity.setWechatId(accountDTO.getWechatId());
			accountEntity.setWechatQrCode(accountDTO.getWechatQrCode());
			merchantAccountDAO.updateMerchantAccount(accountEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}

	}

	@Override
	public void deleteMerchantAccount(MerchantAccountDTO accountDTO) throws BusinessException {
		try {
			MerchantAccountEntity accountEntity = merchantAccountDAO
					.getAccountByUuid(accountDTO.getMerchantAccountUuid());
			merchantAccountDAO.deleteMerchantAccount(accountEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}

	}

	@Override
	public List<MerchantAccountDTO> getMerchantAccount(MerchantDTO merchantDTO) throws BusinessException {
		List<MerchantAccountDTO> accountDTOList = new ArrayList<MerchantAccountDTO>();
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			List<MerchantAccountEntity> accountEntityList = merchantAccountDAO.getAccountByMerchant(merchantEntity);
			for (Iterator<MerchantAccountEntity> iter = accountEntityList.iterator(); iter.hasNext();) {
				MerchantAccountEntity accountEntity = iter.next();
				MerchantAccountDTO accountDTO = new MerchantAccountDTO();
				accountDTO.setMerchantAccountUuid(accountEntity.getMerchantAccountUuid());
				accountDTO.setAccountType(accountEntity.getAccountType());
				accountDTO.setBankCode(accountEntity.getBankCode());
				accountDTO.setBankName(accountEntity.getBankName());
				accountDTO.setBankAccountNo(accountEntity.getBankAccountNo());
				accountDTO.setBankAccountName(accountEntity.getBankAccountName());
				accountDTO.setAlipayId(accountEntity.getAlipayId());
				accountDTO.setAlipayQrCode(accountEntity.getAlipayQrCode());
				accountDTO.setWechatId(accountEntity.getWechatId());
				accountDTO.setWechatQrCode(accountEntity.getWechatQrCode());
				accountDTOList.add(accountDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		return accountDTOList;
	}

	@Override
	public MerchantAccountDTO getMerchantAccountByUuid(String uuid) throws BusinessException {
		try {
			MerchantAccountEntity accountEntity = merchantAccountDAO.getAccountByUuid(uuid);
			MerchantAccountDTO accountDTO = new MerchantAccountDTO();
			accountDTO.setMerchantAccountUuid(accountEntity.getMerchantAccountUuid());
			accountDTO.setAccountType(accountEntity.getAccountType());
			accountDTO.setBankCode(accountEntity.getBankCode());
			accountDTO.setBankName(accountEntity.getBankName());
			accountDTO.setBankAccountNo(accountEntity.getBankAccountNo());
			accountDTO.setBankAccountName(accountEntity.getBankAccountName());
			accountDTO.setAlipayId(accountEntity.getAlipayId());
			accountDTO.setWechatId(accountEntity.getWechatId());
			accountDTO.setAlipayQrCode(accountEntity.getAlipayQrCode());
			accountDTO.setWechatQrCode(accountEntity.getWechatQrCode());
			return accountDTO;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
	}

	@Override
	public List<UserWithdrawDTO> searchUserWithdraw(WithdrawSearchDTO withdrawSearchDTO, int startIndex, int pageSize)
			throws BusinessException {
		List<UserWithdrawDTO> dtoList = new ArrayList<UserWithdrawDTO>();
		try {
			List<UserWithdrawEntity> entityList = userWithdrawDAO.searchUserWithdraw(withdrawSearchDTO, startIndex,
					pageSize);
			for (Iterator iterator = entityList.iterator(); iterator.hasNext();) {
				UserWithdrawEntity entity = (UserWithdrawEntity) iterator.next();
				UserWithdrawDTO dto = new UserWithdrawDTO();
				userWithdrawEntity2DTO(entity, dto);
				dtoList.add(dto);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return dtoList;
	}

	@Override
	public List<UserAwardDTO> searchUserAward(AwardSearchDTO awardSearchDTO, int startIndex, int pageSize)
			throws BusinessException {
		List<UserAwardDTO> dtoList = new ArrayList<UserAwardDTO>();
		try {
			List<UserAwardEntity> entityList = userAwardDAO.searchUserAward(awardSearchDTO, startIndex, pageSize);
			for (Iterator iterator = entityList.iterator(); iterator.hasNext();) {
				UserAwardEntity entity = (UserAwardEntity) iterator.next();
				UserAwardDTO dto = new UserAwardDTO();
				userAwardEntity2DTO(entity, dto);
				dtoList.add(dto);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return dtoList;
	}

	@Override
	public int searchUserWithdrawTotal(WithdrawSearchDTO withdrawSearchDTO) throws BusinessException {
		try {
			return userWithdrawDAO.searchUserWithdrawTotal(withdrawSearchDTO);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public int searchUserAwardTotal(AwardSearchDTO awardSearchDTO) throws BusinessException {
		try {
			return userAwardDAO.searchUserAwardTotal(awardSearchDTO);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public BigDecimal searchUserAwardAmount(AwardSearchDTO awardSearchDTO) throws BusinessException {
		try {
			return userAwardDAO.searchUserAwardAmount(awardSearchDTO);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public BigDecimal getAwardAmountByDateType(Date startDate, Date endDate, String transactionType)
			throws BusinessException {
		try {
			return userAwardDAO.getTranAmountByDateType(startDate, endDate, transactionType);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public BigDecimal getAwardAmountByDateTypeAndMerchant(MerchantDTO merchantDTO, Date startDate, Date endDate,
			String transactionType) throws BusinessException {
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			return userAwardDAO.getTranAmountByDateTypeAndMerchant(merchantEntity, startDate, endDate, transactionType);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public BigDecimal getAwardAmountByDate(Date startDate, Date endDate) throws BusinessException {
		try {
			return userAwardDAO.getTranAmountByDate(startDate, endDate);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public BigDecimal getAwardAmountByDateAndMerchant(MerchantDTO merchantDTO, Date startDate, Date endDate)
			throws BusinessException {
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			return userAwardDAO.getTranAmountByDateAndMerchant(merchantEntity, startDate, endDate);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public void commentUserAward(UserAwardDTO userAwardDTO) throws BusinessException {
		try {
			UserAwardEntity awardEntity = userAwardDAO.getAwardByUuid(userAwardDTO.getUserAwardUuid());
			awardEntity.setMemo(userAwardDTO.getMemo());
			userAwardDAO.updateUserAward(awardEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public List<UserPerformanceDTO> searchUserTotalPerformance(PerformanceSearchDTO performanceSearchDTO,
			int startIndex, int pageSize) throws BusinessException {
		List<UserPerformanceDTO> dtoList = new ArrayList<UserPerformanceDTO>();
		try {
			dtoList = userPerformanceDAO.searchUserTotalPerformance(performanceSearchDTO, startIndex, pageSize);
			for (Iterator<UserPerformanceDTO> iterator = dtoList.iterator(); iterator.hasNext();) {
				UserPerformanceDTO userPerformanceDTO = (UserPerformanceDTO) iterator.next();
				UserEntity userEntity = new UserEntity();
				userEntity.setUserUuid(userPerformanceDTO.getUserDTO().getUserUuid());
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return dtoList;
	}

	@Override
	public BigDecimal searchUserTotalPerformanceAmount(PerformanceSearchDTO performanceSearchDTO)
			throws BusinessException {
		try {
			return userPerformanceDAO.searchUserTotalPerformanceAmount(performanceSearchDTO);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public BigDecimal searchUserTotalPerformanceAward(PerformanceSearchDTO performanceSearchDTO)
			throws BusinessException {
		try {
			return userPerformanceDAO.searchUserTotalPerformanceAward(performanceSearchDTO);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public int searchUserTotalPerformanceTotal(PerformanceSearchDTO performanceSearchDTO) throws BusinessException {
		try {
			return userPerformanceDAO.searchUserTotalPerformanceTotal(performanceSearchDTO);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public List<UserPerformanceDTO> searchUserPerformance(PerformanceSearchDTO performanceSearchDTO, int startIndex,
			int pageSize) throws BusinessException {
		List<UserPerformanceDTO> dtoList = new ArrayList<UserPerformanceDTO>();
		try {
			List<UserPerformanceEntity> entityList = userPerformanceDAO.searchUserPerformance(performanceSearchDTO,
					startIndex, pageSize);
			for (Iterator iterator = entityList.iterator(); iterator.hasNext();) {
				UserPerformanceEntity entity = (UserPerformanceEntity) iterator.next();
				UserPerformanceDTO dto = new UserPerformanceDTO();
				userPerformanceEntity2DTO(entity, dto);
				dtoList.add(dto);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return dtoList;
	}

	@Override
	public int searchUserPerformanceTotal(PerformanceSearchDTO performanceSearchDTO) throws BusinessException {
		try {
			return userPerformanceDAO.searchUserPerformanceTotal(performanceSearchDTO);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	void userPerformanceEntity2DTO(UserPerformanceEntity performanceEntity, UserPerformanceDTO performanceDTO) {

		performanceDTO.setPerformanceDate(performanceEntity.getPerformanceDate());
		performanceDTO.setPerformanceAward(performanceEntity.getPerformanceAward());
		performanceDTO.setPerformanceAmount(performanceEntity.getPerformanceAmount());
		UserEntity userEntity = performanceEntity.getUserEntity();
		if (userEntity != null) {
			UserDTO userDTO = new UserDTO();
			userDTO.setUserUuid(userEntity.getUserUuid());
			userDTO.setName(userEntity.getName());
			performanceDTO.setUserDTO(userDTO);
		}
	}
}
