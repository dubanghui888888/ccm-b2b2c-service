package com.mb.ext.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mb.ext.core.constant.MerchantConstants;
import com.mb.ext.core.context.UserContext;
import com.mb.ext.core.dao.FileDAO;
import com.mb.ext.core.dao.GlobalSettingDAO;
import com.mb.ext.core.dao.MerchantApplicationDAO;
import com.mb.ext.core.dao.MerchantAuthDAO;
import com.mb.ext.core.dao.MerchantDAO;
import com.mb.ext.core.dao.MerchantTokenDAO;
import com.mb.ext.core.dao.PartnerDAO;
import com.mb.ext.core.dao.SequenceDAO;
import com.mb.ext.core.dao.SettingDAO;
import com.mb.ext.core.dao.UserAuthDAO;
import com.mb.ext.core.dao.UserBalanceDAO;
import com.mb.ext.core.dao.UserDAO;
import com.mb.ext.core.dao.UserStatementDAO;
import com.mb.ext.core.dao.coupon.CouponDAO;
import com.mb.ext.core.dao.merchant.MerchantAssignDAO;
import com.mb.ext.core.dao.merchant.MerchantBalanceDAO;
import com.mb.ext.core.dao.merchant.MerchantChargeDAO;
import com.mb.ext.core.dao.merchant.MerchantFollowDAO;
import com.mb.ext.core.dao.merchant.MerchantShopperDAO;
import com.mb.ext.core.dao.merchant.MerchantStatementDAO;
import com.mb.ext.core.dao.merchant.MerchantUserDAO;
import com.mb.ext.core.dao.product.ProductDAO;
import com.mb.ext.core.entity.FileEntity;
import com.mb.ext.core.entity.MerchantAuthEntity;
import com.mb.ext.core.entity.MerchantTokenEntity;
import com.mb.ext.core.entity.UserBalanceEntity;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.UserInventoryEntity;
import com.mb.ext.core.entity.UserLevelEntity;
import com.mb.ext.core.entity.coupon.CouponEntity;
import com.mb.ext.core.entity.merchant.MerchantApplicationEntity;
import com.mb.ext.core.entity.merchant.MerchantAssignEntity;
import com.mb.ext.core.entity.merchant.MerchantBalanceEntity;
import com.mb.ext.core.entity.merchant.MerchantChargeEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.merchant.MerchantShopperEntity;
import com.mb.ext.core.entity.merchant.MerchantStatementEntity;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.DeliveryService;
import com.mb.ext.core.service.FundService;
import com.mb.ext.core.service.MerchantService;
import com.mb.ext.core.service.NoteService;
import com.mb.ext.core.service.OrderService;
import com.mb.ext.core.service.UserService;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.ChartDTO;
import com.mb.ext.core.service.spec.FileDTO;
import com.mb.ext.core.service.spec.MerchantAssignSearchDTO;
import com.mb.ext.core.service.spec.MerchantChargeSearchDTO;
import com.mb.ext.core.service.spec.MerchantSearchDTO;
import com.mb.ext.core.service.spec.MerchantStatementSearchDTO;
import com.mb.ext.core.service.spec.ProductSearchDTO;
import com.mb.ext.core.service.spec.UserBalanceDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.UserInventoryDTO;
import com.mb.ext.core.service.spec.UserLevelDTO;
import com.mb.ext.core.service.spec.UserSearchDTO;
import com.mb.ext.core.service.spec.coupon.CouponDTO;
import com.mb.ext.core.service.spec.merchant.MerchantApplicationDTO;
import com.mb.ext.core.service.spec.merchant.MerchantApplicationSearchDTO;
import com.mb.ext.core.service.spec.merchant.MerchantAssignDTO;
import com.mb.ext.core.service.spec.merchant.MerchantChargeDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.merchant.MerchantShopperDTO;
import com.mb.ext.core.service.spec.merchant.MerchantStatementDTO;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.ext.core.util.MailSenderUtil;
import com.mb.ext.core.util.PaymentUtil;
import com.mb.ext.core.util.SMSSenderUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.service.AbstractService;
import com.mb.framework.util.DateTimeUtil;
import com.mb.framework.util.SecurityUtil;
import com.mb.framework.util.StringUtil;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;

@Service
@Qualifier("MerchantService")
public class MerchantServiceImpl extends AbstractService implements
		MerchantService<BodyDTO> {
	@Autowired
	@Qualifier("merchantDAO")
	private MerchantDAO merchantDAO;

	@Autowired
	@Qualifier("couponDAO")
	private CouponDAO couponDAO;

	@Autowired
	@Qualifier("merchantFollowDAO")
	private MerchantFollowDAO merchantFollowDAO;
	
	@Autowired
	@Qualifier("merchantApplicationDAO")
	private MerchantApplicationDAO merchantApplicationDAO;
	
	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;

	@Autowired
	@Qualifier("productDAO")
	private ProductDAO productDAO;
	
	@Autowired
	@Qualifier("userBalanceDAO")
	private UserBalanceDAO userBalanceDAO;
	
	@Autowired
	@Qualifier("userStatementDAO")
	private UserStatementDAO userStatementDAO;
	
	@Autowired
	@Qualifier("globalSettingDAO")
	private GlobalSettingDAO globalSettingDAO;
	
	@Autowired
	@Qualifier("merchantAuthDAO")
	private MerchantAuthDAO merchantAuthDAO;
	
	@Autowired
	@Qualifier("merchantUserDAO")
	private MerchantUserDAO merchantUserDAO;
	
	@Autowired
	@Qualifier("merchantShopperDAO")
	private MerchantShopperDAO merchantShopperDAO;
	
	@Autowired
	@Qualifier("merchantTokenDAO")
	private MerchantTokenDAO merchantTokenDAO;
	
	@Autowired
	@Qualifier("merchantBalanceDAO")
	private MerchantBalanceDAO merchantBalanceDAO;
	
	@Autowired
	@Qualifier("merchantChargeDAO")
	private MerchantChargeDAO merchantChargeDAO;
	
	@Autowired
	@Qualifier("merchantAssignDAO")
	private MerchantAssignDAO merchantAssignDAO;
	
	@Autowired
	@Qualifier("merchantStatementDAO")
	private MerchantStatementDAO merchantStatementDAO;
	
	@Autowired
	@Qualifier("fileDAO")
	private FileDAO fileDAO;

	@Autowired
	@Qualifier("sequenceDAO")
	private SequenceDAO sequenceDAO;

	@Autowired
	@Qualifier("userAuthDAO")
	private UserAuthDAO userAuthDAO;

	@Autowired
	@Qualifier("settingDAO")
	private SettingDAO settingDAO;
	
	@Autowired
	@Qualifier("partnerDAO")
	private PartnerDAO partnerDAO;

	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Autowired
	@Qualifier("OrderService")
	private OrderService orderService;
	
	@Autowired
	@Qualifier("DeliveryService")
	private DeliveryService deliveryService;
	
	@Autowired
	@Qualifier("FundService")
	private FundService fundService;

	@Autowired
	@Qualifier("NoteService")
	private NoteService noteService;

	@Autowired
	private MailSenderUtil mailSenderUtil;

	@Autowired
	private SMSSenderUtil smsSenderUtil;
	
	@Autowired
	private PaymentUtil paymentUtil;

	@Autowired
	private PropertyRepository propertyRepository;

	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	@Override
	public String login(MerchantDTO merchantDTO) throws BusinessException {
		
		String tokenNo = null;
		
		//1. Check whether it is valid user
		String mobileNo = merchantDTO.getMobileNo();
		try{
			
			MerchantEntity merchantEntity = null;
			
			if(!StringUtil.isEmtpy(mobileNo)){
				merchantEntity = merchantDAO.getMerchantByMobileNo(mobileNo);
			}
			
			if(merchantEntity == null){
				throw new BusinessException(BusinessErrorCode.LOGIN_ID_PASSWORD_INCORRECT);
			}
			
			if(merchantEntity.isClosed()){
				throw new BusinessException(BusinessErrorCode.MERCHANT_CLOSED);
			}
			
			MerchantAuthEntity merchantAuthEntity = merchantEntity.getMerchantAuthEntity();
			
			//1. Check whether password is valid
			String srcPassword = merchantDTO.getPassword();
			String srcEncryptedPassword = SecurityUtil.encryptMd5(srcPassword);
			String descPassword = merchantAuthEntity.getPassword();
			if(!srcEncryptedPassword.equals(descPassword)){
				//update failed count + 1
				int failedCount = merchantAuthEntity.getFailCount();
				merchantAuthEntity.setFailCount(failedCount+1);
				throw new BusinessException(BusinessErrorCode.LOGIN_ID_PASSWORD_INCORRECT);
			}
			
			//if already login, then stop here
			MerchantTokenEntity merchantTokenEntity = merchantEntity.getMerchantTokenEntity();
			if(merchantTokenEntity != null){
				MerchantDTO nMerchantDTO = getMerchantByMobileNo(mobileNo);
				setMerchantProfile(nMerchantDTO);
				return merchantEntity.getMerchantTokenEntity().getTokenId();
			}
			//2. Add record in UserToken table
			tokenNo = RandomStringUtils.randomAlphanumeric(32);
			merchantTokenEntity = new MerchantTokenEntity();
			merchantTokenEntity.setTokenId(tokenNo);
			Date loginTime = new Date();
			merchantTokenEntity.setMerchantEntity(merchantEntity);
			merchantTokenEntity.setLoginTime(loginTime);
			merchantTokenEntity.setCreateBy(mobileNo);
			merchantTokenEntity.setUpdateBy(mobileNo);
			merchantTokenDAO.addMerchantToken(merchantTokenEntity);
			
			//3. Set UserProfile to context
			MerchantDTO nMerchantDTO = getMerchantByMobileNo(mobileNo);
			setMerchantProfile(nMerchantDTO);
		}catch(DAOException e){
			throw new BusinessException (BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}catch(BusinessException e){
			throw e;
		}catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return tokenNo;
		
	}
	
	@Override
	public void logout(MerchantDTO merchantDTO) throws BusinessException {
		try{
			MerchantEntity merchantEntity = null;
			if(!StringUtils.isEmpty(merchantDTO.getMerchantUuid()))
				merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			else if(!StringUtils.isEmpty(merchantDTO.getMobileNo()))
				merchantEntity = merchantDAO.getMerchantByMobileNo(merchantDTO.getMobileNo());
			if(merchantEntity != null){
				MerchantTokenEntity merchantTokenEntity = merchantEntity.getMerchantTokenEntity();
				if(merchantTokenEntity != null){
					//to physically delete userToken, must clear the reference. Otherwise, physically delete will fail
					merchantEntity.setMerchantTokenEntity(null);
					merchantTokenDAO.deleteMerchantToken(merchantTokenEntity);
				}
			}
			UserContext.set("MerchantProfile", null);
		}catch(DAOException e){
			throw new BusinessException("SYSTEM_MESSAGE_0001",e);
		}
	}
	
	@Override
	public void logout(String tokenId) throws BusinessException {
		try {
			MerchantTokenEntity merchantTokenEntity = merchantTokenDAO
					.findByTokenId(tokenId);
			if (merchantTokenEntity != null) {
				merchantTokenDAO.deleteMerchantToken(merchantTokenEntity);
			}
			UserContext.set("MERCHANTPROFILE", null);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}

	}
	
	@Override
	public void changePassword(MerchantDTO merchantDTO) throws BusinessException {
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByMobileNo(merchantDTO.getMobileNo());
			if (merchantEntity == null) {
				throw new BusinessException(
						BusinessErrorCode.LOGIN_ID_PASSWORD_INCORRECT);
			}
			MerchantAuthEntity merchantAuthEntity = merchantEntity.getMerchantAuthEntity();
			String descPassword = merchantAuthEntity.getPassword();
			String srcPassword = merchantDTO.getPassword();
			String srcEncryptedPassword = SecurityUtil.encryptMd5(srcPassword);
			if (!srcEncryptedPassword.equals(descPassword)) {
				throw new BusinessException(
						BusinessErrorCode.LOGIN_ID_PASSWORD_INCORRECT);
			}

			String newPassword = merchantDTO.getNewPassword();
			merchantAuthEntity.setPassword(SecurityUtil.encryptMd5(newPassword));
			merchantAuthEntity.setFailCount(0);
			merchantAuthDAO.updateMerchantAuth(merchantAuthEntity);

		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}
	
	@Override
	public void forgetPassword(MerchantDTO merchantDTO) throws BusinessException {
		// TODO Auto-generated method stub
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByMobileNo(merchantDTO.getMobileNo());
			if (merchantEntity == null) {
				throw new BusinessException(
						BusinessErrorCode.MERCHANT_NOT_FOUND);
			}
			MerchantAuthEntity merchantAuthEntity = merchantEntity.getMerchantAuthEntity();
			String newPassword = merchantDTO.getNewPassword();
			merchantAuthEntity.setPassword(SecurityUtil.encryptMd5(newPassword));
			merchantAuthEntity.setFailCount(0);
			merchantAuthDAO.updateMerchantAuth(merchantAuthEntity);

		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}
	
	@Override
	public void validateTranPassword(MerchantDTO merchantDTO) throws BusinessException {
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByMobileNo(merchantDTO.getMobileNo());
			if (merchantEntity == null) {
				throw new BusinessException(
						BusinessErrorCode.MERCHANT_NOT_FOUND);
			}
			MerchantAuthEntity merchantAuthEntity = merchantEntity.getMerchantAuthEntity();
			String descPassword = merchantAuthEntity.getTranPassword();
			String srcPassword = merchantDTO.getTranPassword();
			String srcEncryptedPassword = SecurityUtil.encryptMd5(srcPassword);
			if (!srcEncryptedPassword.equals(descPassword)) {
				throw new BusinessException(
						BusinessErrorCode.TRAN_PASSWORD_INCORRECT);
			}
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}

	}
	
	@Override
	public void changeTranPassword(MerchantDTO merchantDTO) throws BusinessException {
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByMobileNo(merchantDTO.getMobileNo());
			if (merchantEntity == null) {
				throw new BusinessException(
						BusinessErrorCode.MERCHANT_NOT_FOUND);
			}
			MerchantAuthEntity merchantAuthEntity = merchantEntity.getMerchantAuthEntity();
			String descPassword = merchantAuthEntity.getPassword();
			String srcPassword = merchantDTO.getPassword();
			String srcEncryptedPassword = SecurityUtil.encryptMd5(srcPassword);
			if (!srcEncryptedPassword.equals(descPassword)) {
				throw new BusinessException(
						BusinessErrorCode.LOGIN_PASSWORD_INCORRECT);
			}

			String tranPassword = merchantDTO.getTranPassword();
			merchantAuthEntity.setTranPassword(SecurityUtil.encryptMd5(tranPassword));
			merchantAuthDAO.updateMerchantAuth(merchantAuthEntity);

		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}

	}

	@Override
	public void deleteMerchant(MerchantDTO merchantDTO) throws BusinessException {
		try {
			String mobileNo = merchantDTO.getMobileNo();
			String merchantUuid = merchantDTO.getMerchantUuid();
			MerchantEntity merchantEntity = null;
			if(!StringUtils.isEmpty(mobileNo))
				merchantEntity = merchantDAO.getMerchantByMobileNo(mobileNo);
			else if(!StringUtils.isEmpty(merchantUuid))
				merchantEntity = merchantDAO.getMerchantByUuid(merchantUuid);
			if (merchantEntity != null) {
				merchantDAO.deleteMerchant(merchantEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}

	}
	
	@Override
	public String createMerchant(MerchantDTO merchantDTO) throws BusinessException {
		try {
			String mobileNo = merchantDTO.getMobileNo();
			//联系电话不能重复
			MerchantEntity merchantEntity = merchantDAO.getMerchantByMobileNo(mobileNo);
			if (merchantEntity != null) {
				throw new BusinessException(BusinessErrorCode.MERCHANT_DUPLICATE_MOBILENO);
			}
			merchantEntity = new MerchantEntity();
			//创建商户
			merchantEntity.setMobileNo(merchantDTO.getMobileNo());
			merchantEntity.setMerchantName(merchantDTO.getMerchantName());
			merchantEntity.setMerchantAddress(merchantDTO.getMerchantAddress());
			merchantEntity.setProvince(merchantDTO.getProvince());
			merchantEntity.setCity(merchantDTO.getCity());
			merchantEntity.setDistrict(merchantDTO.getDistrict());
			merchantEntity.setLongitude(merchantDTO.getLongitude());
			merchantEntity.setLatitude(merchantDTO.getLatitude());
			merchantEntity.setMerchantDescription(merchantDTO.getMerchantDescription());
			merchantEntity.setContactName(merchantDTO.getContactName());
			merchantEntity.setReferrer(merchantDTO.getReferrer());
			merchantEntity.setRegisterDate(new Date());
			merchantEntity.setExchangeRate(MerchantConstants.DEFAULT_EXCHANGE_RATE);
			merchantEntity.setCreateBy(merchantDTO.getMobileNo());
			merchantEntity.setUpdateBy(merchantDTO.getMobileNo());
			merchantDAO.addMerchant(merchantEntity);
			
			//商家验证表
			String password = merchantDTO.getPassword();
			if(StringUtils.isEmpty(password)){
				password = RandomStringUtils.randomNumeric(8);
				merchantDTO.setPassword(password);
			}
			String encryptedPassword = SecurityUtil.encryptMd5(password);
			MerchantAuthEntity merchantAuthEntity = new MerchantAuthEntity();
			merchantAuthEntity.setPassword(encryptedPassword);
			merchantAuthEntity.setFailCount(0);
			merchantAuthEntity.setMerchantEntity(merchantEntity);
			merchantAuthEntity.setCreateBy(merchantEntity.getMobileNo());
			merchantAuthEntity.setUpdateBy(merchantEntity.getMobileNo());
			merchantAuthDAO.addMerchantAuth(merchantAuthEntity);
			
			//商家余额表
			MerchantBalanceEntity balanceEntity = new MerchantBalanceEntity();
			balanceEntity.setMerchantEntity(merchantEntity);
			balanceEntity.setAvailableBalance(BigDecimal.valueOf(0));
			balanceEntity.setTotalBalance(BigDecimal.valueOf(0));
			balanceEntity.setAvailablePoint(0);
			balanceEntity.setTotalPoint(0);
			balanceEntity.setCreateBy(merchantEntity.getMobileNo());
			balanceEntity.setUpdateBy(merchantEntity.getMobileNo());
			merchantBalanceDAO.createMerchantBalance(balanceEntity);
			
			return merchantEntity.getMerchantUuid();
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}

	}
	
	@Override
	public void updateMerchant(MerchantDTO merchantDTO) throws BusinessException {
		try {
			String mobileNo = merchantDTO.getMobileNo();
			String merchantUuid = merchantDTO.getMerchantUuid();
			MerchantEntity merchantEntity = merchantDAO.getMerchantByMobileNo(mobileNo);
			//更新商家时, 电话号码不能与其他商家重复重复
			if(merchantEntity != null && !merchantEntity.getMerchantUuid().equals(merchantUuid)) {
				throw new BusinessException(BusinessErrorCode.MERCHANT_DUPLICATE_MOBILENO);
			}
			merchantEntity = merchantDAO.getMerchantByUuid(merchantUuid);
			if (merchantEntity != null) {
				merchantEntity.setMerchantName(merchantDTO.getMerchantName());
				merchantEntity.setMobileNo(merchantDTO.getMobileNo());
				merchantEntity.setMerchantAddress(merchantDTO.getMerchantAddress());
				merchantEntity.setLogo(merchantDTO.getLogo());
				merchantEntity.setProvince(merchantDTO.getProvince());
				merchantEntity.setCity(merchantDTO.getCity());
				merchantEntity.setDistrict(merchantDTO.getDistrict());
				merchantEntity.setLongitude(merchantDTO.getLongitude());
				merchantEntity.setLatitude(merchantDTO.getLatitude());
				merchantEntity.setContactName(merchantDTO.getContactName());
				merchantEntity.setMerchantDescription(merchantDTO.getMerchantDescription());
				merchantEntity.setReferrer(merchantDTO.getReferrer());
				merchantEntity.setMerchantId(merchantDTO.getMerchantId());
				merchantEntity.setWeight(merchantDTO.getWeight());
				merchantDAO.updateMerchant(merchantEntity);
				
				MerchantBalanceEntity balanceEntity = merchantEntity.getMerchantBalanceEntity();
				if(balanceEntity != null) {
					balanceEntity.setAvailablePoint(merchantDTO.getAvailablePoint());
					balanceEntity.setTotalPoint(merchantDTO.getAvailablePoint());
					merchantBalanceDAO.updateMerchantBalance(balanceEntity);
				}
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}

	}
	
	@Override
	public void mUpdateMerchant(MerchantDTO merchantDTO) throws BusinessException {
		try {
			String mobileNo = merchantDTO.getMobileNo();
			String merchantUuid = merchantDTO.getMerchantUuid();
			MerchantEntity merchantEntity = merchantDAO.getMerchantByMobileNo(mobileNo);
			//更新商家时, 电话号码不能与其他商家重复重复
			if(merchantEntity != null && !merchantEntity.getMerchantUuid().equals(merchantUuid)) {
				throw new BusinessException(BusinessErrorCode.MERCHANT_DUPLICATE_MOBILENO);
			}
			merchantEntity = merchantDAO.getMerchantByUuid(merchantUuid);
			if (merchantEntity != null) {
				merchantEntity.setMerchantName(merchantDTO.getMerchantName());
				merchantEntity.setMobileNo(merchantDTO.getMobileNo());
				merchantEntity.setMerchantAddress(merchantDTO.getMerchantAddress());
				merchantEntity.setLogo(merchantDTO.getLogo());
				merchantEntity.setProvince(merchantDTO.getProvince());
				merchantEntity.setCity(merchantDTO.getCity());
				merchantEntity.setDistrict(merchantDTO.getDistrict());
				merchantEntity.setLongitude(merchantDTO.getLongitude());
				merchantEntity.setLatitude(merchantDTO.getLatitude());
				merchantEntity.setContactName(merchantDTO.getContactName());
				merchantEntity.setMerchantDescription(merchantDTO.getMerchantDescription());
				merchantEntity.setReferrer(merchantDTO.getReferrer());
				merchantDAO.updateMerchant(merchantEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}

	}
	
	@Override
	public void updateMerchantField(MerchantDTO merchantDTO, String fieldName) throws BusinessException {
		try {
			MerchantEntity merchantEntity = null;
			if(merchantDTO != null && !StringUtils.isEmpty(merchantDTO.getMobileNo())) {
				merchantEntity = merchantDAO.getMerchantByMobileNo(merchantDTO.getMobileNo());
			}else if(merchantDTO != null && !StringUtils.isEmpty(merchantDTO.getMerchantUuid())) {
				merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			}
			if (merchantEntity != null) {
				if("EXCHANGE_RATE".equals(fieldName)) {
					merchantEntity.setExchangeRate(merchantDTO.getExchangeRate());
				}
				else if("MEMO".equals(fieldName)) {
					merchantEntity.setMemo(merchantDTO.getMemo());
				}
				else if("MERCHANTID".equals(fieldName)) {
					merchantEntity.setMerchantId(merchantDTO.getMerchantId());
				}
				else if("LOGO".equals(fieldName)) {
					merchantEntity.setLogo(merchantDTO.getLogo());
				}
				merchantDAO.updateMerchant(merchantEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}
	
	
	@Override
	public void closeMerchant(MerchantDTO merchantDTO) throws BusinessException {
		try {
			String mobileNo = merchantDTO.getMobileNo();
			String merchantUuid = merchantDTO.getMerchantUuid();
			MerchantEntity merchantEntity = null;
			if(!StringUtils.isEmpty(mobileNo))
				merchantEntity = merchantDAO.getMerchantByMobileNo(mobileNo);
			else if(!StringUtils.isEmpty(merchantUuid))
				merchantEntity = merchantDAO.getMerchantByUuid(merchantUuid);
			if(merchantEntity != null){
				if(merchantEntity.isClosed()){
					merchantEntity.setClosed(false);
				}else{
					merchantEntity.setClosed(true);
				}
				merchantDAO.updateMerchant(merchantEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}

	
	@Override
	public MerchantDTO getMerchantByUuid(String uuid) throws BusinessException {
		MerchantDTO merchantDTO = null;
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(uuid);
			if (merchantEntity != null) {
				merchantDTO = new MerchantDTO();
				entity2DTO(merchantEntity, merchantDTO);
			}else
				throw new BusinessException(BusinessErrorCode.MERCHANT_NOT_FOUND);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		return merchantDTO;

	}
	
	@Override
	public MerchantDTO getMerchantByMobileNo(String mobileNo) throws BusinessException {
		MerchantDTO merchantDTO = null;
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByMobileNo(mobileNo);
			if (merchantEntity != null) {
				merchantDTO = new MerchantDTO();
				entity2DTO(merchantEntity, merchantDTO);
			}else
				throw new BusinessException(BusinessErrorCode.MERCHANT_NOT_FOUND);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		return merchantDTO;

	}

	@Override
	public List<MerchantDTO> getMerchants() throws BusinessException {
		List<MerchantDTO> merchantDTOList = new ArrayList<MerchantDTO>();
		try {
			List<MerchantEntity> merchantEntityList = merchantDAO.getMerchants();
			if (merchantEntityList != null && merchantEntityList.size() > 0) {
				for (Iterator<MerchantEntity> iter = merchantEntityList.iterator(); iter
						.hasNext();) {
					MerchantEntity merchantEntity = iter.next();
					MerchantDTO merchantDTO = new MerchantDTO();
					entity2DTO(merchantEntity, merchantDTO);
					merchantDTOList.add(merchantDTO);
				}
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		return merchantDTOList;
	}
	
	@Override
	public List<MerchantDTO> searchMerchants(MerchantSearchDTO searchDTO) throws BusinessException {
		List<MerchantDTO> merchantDTOList = new ArrayList<MerchantDTO>();
		try {
			List<MerchantEntity> merchantEntityList = new ArrayList<MerchantEntity>();
			if(!StringUtils.isEmpty(searchDTO.getSortBy())){
				List<Object> merchantUuidList = merchantDAO.searchMerchantRanking(searchDTO);
				for (int i = 0; i < merchantUuidList.size(); i++) {
					Object[] result = (Object[]) merchantUuidList.get(i);
					String merchantUuid = String.valueOf(result[0]);
					MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantUuid);
					MerchantDTO merchantDTO = new MerchantDTO();
					entity2DTO(merchantEntity, merchantDTO);
					merchantDTO.setRecharge(false);
					
					merchantDTO.setAssignPointTotal(Integer.parseInt(result[2].toString()));
					merchantDTO.setChargeTotal(new BigDecimal(result[1].toString()));
					if(new BigDecimal(result[1].toString()).compareTo(BigDecimal.ZERO)!=0){
						merchantDTO.setRecharge(true);
					}
					
					merchantDTOList.add(merchantDTO);
				}
			}else {
				merchantEntityList = merchantDAO.searchMerchants(searchDTO);
				if (merchantEntityList != null)
					for (Iterator<MerchantEntity> iterator = merchantEntityList.iterator(); iterator
							.hasNext();) {
						MerchantEntity merchantEntity = (MerchantEntity) iterator.next();
						MerchantDTO merchantDTO = new MerchantDTO();
						entity2DTO(merchantEntity, merchantDTO);
						merchantDTO.setRecharge(false);
						
						merchantDTO.setAssignPointTotal(merchantAssignDAO.getAssignPointTotal(merchantEntity));
						merchantDTO.setChargeTotal(merchantChargeDAO.getChargeTotalByMerchant(merchantEntity));
						if(merchantChargeDAO.getChargeTotalByMerchant(merchantEntity).compareTo(BigDecimal.ZERO)!=0){
							merchantDTO.setRecharge(true);
						}
						
						merchantDTOList.add(merchantDTO);
					}
			}
			return merchantDTOList;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} catch (Exception e2){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e2);
		}
	}
	@Override
	public List<MerchantDTO> searchPublicMerchants(MerchantSearchDTO searchDTO) throws BusinessException {
		List<MerchantDTO> merchantDTOList = new ArrayList<MerchantDTO>();
		try {
			List<MerchantEntity> merchantEntityList = merchantDAO.searchMerchants(searchDTO);
			for (MerchantEntity merchantEntity: merchantEntityList) {
				MerchantDTO merchantDTO = new MerchantDTO();
				//如果传入用户, 查询该用户是否关注该商家
				if(!StringUtils.isEmpty(searchDTO.getUserUuid())){
					UserEntity userEntity = new UserEntity();
					userEntity.setUserUuid(searchDTO.getUserUuid());
					if(merchantFollowDAO.getMerchantFollow(merchantEntity,userEntity) != null){
						merchantDTO.setFollowed(true);
					}
				}
				merchantDTO.setMerchantUuid(merchantEntity.getMerchantUuid());
				merchantDTO.setMerchantName(merchantEntity.getMerchantName());
				merchantDTO.setMerchantDescription(merchantEntity.getMerchantDescription());
				merchantDTO.setMobileNo(merchantEntity.getMobileNo());
				merchantDTO.setContactName(merchantEntity.getContactName());
				merchantDTO.setLatitude(merchantEntity.getLatitude());
				merchantDTO.setLongitude(merchantEntity.getLongitude());
				merchantDTO.setMerchantAddress(merchantEntity.getMerchantAddress());
				merchantDTO.setLogo(merchantEntity.getLogo());
				merchantDTO.setProvince(merchantEntity.getProvince());
				merchantDTO.setCity(merchantEntity.getCity());
				merchantDTO.setDistrict(merchantEntity.getDistrict());
				merchantDTO.setScore(merchantEntity.getScore());
				merchantDTO.setSoldUnit(merchantEntity.getSoldUnit());
				merchantDTO.setSaleUnit(merchantEntity.getSaleUnit());
				//关注数
				int followTotal = merchantFollowDAO.searchFollowByMerchantTotal(merchantEntity);
				merchantDTO.setFollowTotal(followTotal);
				//发布的商品(默认取8个)
				ProductSearchDTO productSearchDTO = new ProductSearchDTO();
				productSearchDTO.setKeyArray(new String[]{ProductSearchDTO.KEY_MERCHANT,ProductSearchDTO.KEY_ON_SALE});
				productSearchDTO.setMerchantUuid(merchantEntity.getMerchantUuid());
				productSearchDTO.setOnSale(true);
				productSearchDTO.setStartIndex(0);
				productSearchDTO.setPageSize(8);
				List<ProductEntity> productEntityList = productDAO.searchProduct(productSearchDTO);
				List<ProductDTO> productDTOList = new ArrayList<ProductDTO>();
				for (ProductEntity productEntity:productEntityList ) {
					ProductDTO productDTO = new ProductDTO();
					productDTO.setProductUuid(productEntity.getProductUuid());
					productDTO.setProductName(productEntity.getProductName());
					FileDTO mainImageDTO = new FileDTO();
					FileEntity mainImageEntity = productEntity.getProductMainImage();
					mainImageDTO.setUrl(mainImageEntity.getUrl());
					productDTO.setProductMainImage(mainImageDTO);
					productDTO.setUnitPrice(productEntity.getUnitPrice());
					productDTOList.add(productDTO);
				}
				merchantDTO.setProductList(productDTOList);
				merchantDTOList.add(merchantDTO);
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return merchantDTOList;
	}
	@Override
	public List<MerchantDTO> searchMerchantCoupons(MerchantSearchDTO searchDTO) throws BusinessException {
		List<MerchantDTO> merchantDTOList = new ArrayList<MerchantDTO>();
		try {
			List<MerchantEntity> merchantEntityList = merchantDAO.searchMerchants(searchDTO);
			for (MerchantEntity merchantEntity: merchantEntityList) {
				MerchantDTO merchantDTO = new MerchantDTO();
				merchantDTO.setMerchantUuid(merchantEntity.getMerchantUuid());
				merchantDTO.setMerchantName(merchantEntity.getMerchantName());
				merchantDTO.setMobileNo(merchantEntity.getMobileNo());
				merchantDTO.setMerchantAddress(merchantEntity.getMerchantAddress());
				//发布的优惠券
				List<CouponEntity> couponEntityList = couponDAO.getCouponByMerchant(merchantEntity);
				List<CouponDTO> couponDTOList = new ArrayList<CouponDTO>();
				for (CouponEntity couponEntity:couponEntityList ) {
					CouponDTO couponDTO = new CouponDTO();
					if(couponEntity.isActive()){
						couponDTO.setCouponUuid(couponEntity.getCouponUuid());
						couponDTO.setName(couponEntity.getName());
						couponDTO.setType(couponEntity.getType());
						couponDTO.setValidType(couponEntity.getValidType());
						couponDTO.setValidDays(couponEntity.getValidDays());
						couponDTO.setStartDate(couponEntity.getStartDate());
						couponDTO.setEndDate(couponEntity.getEndDate());
						couponDTO.setTotalCount(couponEntity.getTotalCount());
						couponDTO.setLimitPerUser(couponEntity.getLimitPerUser());
						couponDTO.setAvailableCount(couponEntity.getAvailableCount());
						couponDTO.setCouponCode(couponEntity.getCouponCode());
						couponDTO.setBenefitCash(couponEntity.getBenefitCash());
						couponDTO.setBenefitDiscount(couponEntity.getBenefitDiscount());
						couponDTO.setConditionAmount(couponEntity.getConditionAmount());
						couponDTO.setBenefitType(couponEntity.getBenefitType());
						couponDTO.setImageUrl(couponEntity.getImageUrl());
						couponDTOList.add(couponDTO);
					}

				}
				merchantDTO.setCouponList(couponDTOList);
				merchantDTOList.add(merchantDTO);
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return merchantDTOList;
	}
	@Override
	public List<MerchantDTO> searchMerchantByUser(UserDTO userDTO, int startIndex, int pageSize) throws BusinessException {
		List<MerchantDTO> merchantDTOList = new ArrayList<MerchantDTO>();
		try {
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			List<MerchantEntity> merchantEntityList = merchantUserDAO.searchMerchantByUser(userEntity, startIndex, pageSize);
			if (merchantEntityList != null)
				for (Iterator<MerchantEntity> iterator = merchantEntityList.iterator(); iterator
						.hasNext();) {
					MerchantEntity merchantEntity = (MerchantEntity) iterator.next();
					MerchantDTO merchantDTO = new MerchantDTO();
					entity2DTO(merchantEntity, merchantDTO);
					merchantDTOList.add(merchantDTO);
				}
			return merchantDTOList;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public int searchMerchantByUserTotal(UserDTO userDTO) throws BusinessException {
		try {
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			return merchantUserDAO.searchMerchantByUserTotal(userEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public int searchMerchantTotal(MerchantSearchDTO searchDTO) throws BusinessException {
		try {
			return merchantDAO.searchMerchantTotal(searchDTO);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} catch (Exception e2){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e2);
		}
	}
	
	@Override
	public MerchantDTO getMerchantDTOByTokenId(String tokenId)
			throws BusinessException {
		MerchantDTO merchantDTO = null;
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByTokenId(tokenId);
			if(merchantEntity != null){
				merchantDTO = new MerchantDTO();
				entity2DTO(merchantEntity, merchantDTO);
			}
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		return merchantDTO;
	}

	private void entity2DTO(MerchantEntity merchantEntity, MerchantDTO merchantDTO) {
		if (merchantEntity != null && merchantDTO != null) {
			merchantDTO.setMerchantUuid(merchantEntity.getMerchantUuid());
			merchantDTO.setReferrer(merchantEntity.getReferrer());
			merchantDTO.setMobileNo(merchantEntity.getMobileNo());
			merchantDTO.setMerchantName(merchantEntity.getMerchantName());
			merchantDTO.setMerchantAddress(merchantEntity.getMerchantAddress());
			merchantDTO.setLogo(merchantEntity.getLogo());
			merchantDTO.setMerchantId(merchantEntity.getMerchantId());
			merchantDTO.setProvince(merchantEntity.getProvince());
			merchantDTO.setCity(merchantEntity.getCity());
			merchantDTO.setDistrict(merchantEntity.getDistrict());
			merchantDTO.setLongitude(merchantEntity.getLongitude());
			merchantDTO.setLatitude(merchantEntity.getLatitude());
			merchantDTO.setContactName(merchantEntity.getContactName());
			merchantDTO.setMerchantDescription(merchantEntity.getMerchantDescription());
			merchantDTO.setRegisterDate(merchantEntity.getRegisterDate());
			merchantDTO.setClosed(merchantEntity.isClosed());
			merchantDTO.setExchangeRate(merchantEntity.getExchangeRate());
			merchantDTO.setMemo(merchantEntity.getMemo());
			merchantDTO.setWeight(merchantEntity.getWeight());
			merchantDTO.setScore(merchantEntity.getScore());
			merchantDTO.setSoldUnit(merchantEntity.getSoldUnit());
			merchantDTO.setSaleUnit(merchantEntity.getSaleUnit());
			
			MerchantBalanceEntity balanceEntity = merchantEntity.getMerchantBalanceEntity();
			if(balanceEntity != null) {
				merchantDTO.setAvailableBalance(balanceEntity.getAvailableBalance());
				merchantDTO.setTotalBalance(balanceEntity.getTotalBalance());
				merchantDTO.setAvailablePoint(balanceEntity.getAvailablePoint());
				merchantDTO.setTotalPoint(balanceEntity.getTotalPoint());
			}
			
			MerchantAuthEntity authEntity = merchantEntity.getMerchantAuthEntity();
			if(authEntity != null) {
				merchantDTO.setTranPassword(authEntity.getTranPassword());	//积分划拨密码, MD5加密
			}
		}
	}

	@Override
	public void setMerchantProfile(MerchantDTO merchantDTO) throws BusinessException {
		
		UserContext.set("MERCHANTPROFILE", merchantDTO);
		
	}

	@Override
	public MerchantDTO getMerchantProfile() throws BusinessException {
		return (MerchantDTO) UserContext.get("MERCHANTPROFILE");
	}
	
	@Override
	public int getMerchantCount()
			throws BusinessException {
		try {
			int merchantCount = merchantDAO.searchMerchantTotal(new MerchantSearchDTO());
			return merchantCount;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}
	
	@Override
	public List<UserDTO> searchMerchantUser(MerchantDTO merchantDTO, UserSearchDTO searchDTO) throws BusinessException {
		List<UserDTO> userDTOList = new ArrayList<UserDTO>();
		try {
			MerchantEntity merchantEntity = null;
			if(merchantDTO != null && !StringUtils.isEmpty(merchantDTO.getMobileNo())) {
				merchantEntity = merchantDAO.getMerchantByMobileNo(merchantDTO.getMobileNo());
			}else if(merchantDTO != null && !StringUtils.isEmpty(merchantDTO.getMerchantUuid())) {
				merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			}
			List<UserEntity> userEntityList = userDAO.searchMerchantUsers(merchantEntity, searchDTO, searchDTO.getStartIndex(), searchDTO.getPageSize());
			if (userEntityList != null)
				for (Iterator<UserEntity> iterator = userEntityList.iterator(); iterator
						.hasNext();) {
					UserEntity userEntity = (UserEntity) iterator.next();
					UserDTO userDTO = new UserDTO();
					entity2DTO(userEntity, userDTO);
					userDTOList.add(userDTO);
				}
			return userDTOList;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} catch (Exception e2){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e2);
		}
	}

	@Override
	public int getFollowsTotal(MerchantDTO merchantDTO) throws BusinessException{
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			return merchantFollowDAO.searchFollowByMerchantTotal(merchantEntity);

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}

	@Override
	public List<MerchantDTO> getFollowedMerchantByUser(UserDTO userDTO) throws BusinessException{
		List<MerchantDTO> merchantDTOList = new ArrayList<MerchantDTO>();
		try {
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			List<MerchantEntity> merchantEntityList = merchantFollowDAO.getFollowedMerchantsByUser(userEntity);
			for (MerchantEntity merchantEntity: merchantEntityList) {
				MerchantDTO merchantDTO = new MerchantDTO();
				merchantDTO.setFollowed(true);
				merchantDTO.setMerchantUuid(merchantEntity.getMerchantUuid());
				merchantDTO.setMerchantName(merchantEntity.getMerchantName());
				merchantDTO.setMerchantDescription(merchantEntity.getMerchantDescription());
				merchantDTO.setMobileNo(merchantEntity.getMobileNo());
				merchantDTO.setContactName(merchantEntity.getContactName());
				merchantDTO.setLatitude(merchantEntity.getLatitude());
				merchantDTO.setLongitude(merchantEntity.getLongitude());
				merchantDTO.setMerchantAddress(merchantEntity.getMerchantAddress());
				merchantDTO.setLogo(merchantEntity.getLogo());
				merchantDTO.setProvince(merchantEntity.getProvince());
				merchantDTO.setCity(merchantEntity.getCity());
				merchantDTO.setDistrict(merchantEntity.getDistrict());
				merchantDTO.setScore(merchantEntity.getScore());
				merchantDTO.setSoldUnit(merchantEntity.getSoldUnit());
				merchantDTO.setSaleUnit(merchantEntity.getSaleUnit());
				//关注数
				int followTotal = merchantFollowDAO.searchFollowByMerchantTotal(merchantEntity);
				merchantDTO.setFollowTotal(followTotal);
				//发布的商品(默认取8个)
				ProductSearchDTO productSearchDTO = new ProductSearchDTO();
				productSearchDTO.setKeyArray(new String[]{ProductSearchDTO.KEY_MERCHANT,ProductSearchDTO.KEY_ON_SALE});
				productSearchDTO.setMerchantUuid(merchantEntity.getMerchantUuid());
				productSearchDTO.setOnSale(true);
				productSearchDTO.setStartIndex(0);
				productSearchDTO.setPageSize(8);
				List<ProductEntity> productEntityList = productDAO.searchProduct(productSearchDTO);
				List<ProductDTO> productDTOList = new ArrayList<ProductDTO>();
				for (ProductEntity productEntity:productEntityList ) {
					ProductDTO productDTO = new ProductDTO();
					productDTO.setProductUuid(productEntity.getProductUuid());
					productDTO.setProductName(productEntity.getProductName());
					FileDTO mainImageDTO = new FileDTO();
					FileEntity mainImageEntity = productEntity.getProductMainImage();
					mainImageDTO.setUrl(mainImageEntity.getUrl());
					productDTO.setProductMainImage(mainImageDTO);
					productDTO.setUnitPrice(productEntity.getUnitPrice());
					productDTOList.add(productDTO);
				}
				merchantDTO.setProductList(productDTOList);
				merchantDTOList.add(merchantDTO);
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return merchantDTOList;
	}
	
	public void entity2DTO(UserEntity userEntity, UserDTO userDTO){
		if(userDTO != null && userEntity != null){
			userDTO.setUserUuid(userEntity.getUserUuid());
			userDTO.setCreateBy(userEntity.getCreateBy());
			userDTO.setUpdateBy(userEntity.getUpdateBy());
			userDTO.setId(userEntity.getId());
			userDTO.setName(userEntity.getName());
			userDTO.setPersonalEmail(userEntity.getPersonalEmail());
			userDTO.setPersonalPhoneCountryCode(userEntity.getPersonalPhoneCountryCode());
			userDTO.setPersonalPhone(userEntity.getPersonalPhone());
			userDTO.setPhotoUrl(userEntity.getPhotoUrl());
			userDTO.setMemo(userEntity.getMemo());
			userDTO.setSex(userEntity.getSex());
			userDTO.setRegisterDate(userEntity.getRegisterDate());
			userDTO.setUserUuid(userEntity.getUserUuid());
			userDTO.setCreateDate(userEntity.getCreateDate());
			userDTO.setOpenId(userEntity.getOpenId());
			userDTO.setActive(userEntity.isActive());
			
			
			UserInventoryEntity userInventoryEntity = userEntity.getUserInventoryEntity();
			if(userInventoryEntity != null){
				UserInventoryDTO userInventoryDTO = new UserInventoryDTO();
				userInventoryDTO.setBalance(userInventoryEntity.getBalance());
				userInventoryDTO.setBalancePouch(userInventoryEntity.getBalancePouch());
				userDTO.setUserInventoryDTO(userInventoryDTO);
			}
			
			UserBalanceEntity userBalanceEntity = userEntity.getUserBalanceEntity();
			if(userBalanceEntity != null){
				UserBalanceDTO userBalanceDTO = new UserBalanceDTO();
				userBalanceDTO.setAvailableBalance(userBalanceEntity.getAvailableBalance());
				userBalanceDTO.setAvailablePoint(userBalanceEntity.getAvailablePoint());
				userDTO.setUserBalanceDTO(userBalanceDTO);
			}

			UserLevelEntity userLevelEntity = userEntity.getUserLevelEntity();
			if (userLevelEntity != null) {
				UserLevelDTO userLevelDTO = new UserLevelDTO();
				userLevelDTO.setUserLevelUuid(userLevelEntity.getUserLevelUuid());
				userLevelDTO.setName(userLevelEntity.getName());
				userLevelDTO.setRecruitProfitEnabled(userLevelEntity.isRecruitProfitEnabled());
				userLevelDTO.setSaleProfitEnabled(userLevelEntity.isSaleProfitEnabled());
				userLevelDTO.setRequiredProductAmountDirect(userLevelEntity.getRequiredProductAmountDirect());
				userLevelDTO.setRequiredProductAmountTeam(userLevelEntity.getRequiredProductAmountTeam());
				userLevelDTO.setRequiredUserCountDirect(userLevelEntity.getRequiredUserCountDirect());
				userLevelDTO.setRequiredUserCountTeam(userLevelEntity.getRequiredUserCountTeam());
				userLevelDTO.setRequiredByUser(userLevelEntity.isRequiredByUser());
				userLevelDTO.setRequiredByUserSymbol(userLevelEntity.getRequiredByUserSymbol());
				userLevelDTO.setRequiredByAmount(userLevelEntity.isRequiredByAmount());
				userLevelDTO.setRequiredByAmountSymbol(userLevelEntity.getRequiredByAmountSymbol());
				userDTO.setUserLevelDTO(userLevelDTO);
			}
		}
	}
	
	
	@Override
	public int searchMerchantUserTotal(MerchantDTO merchantDTO, UserSearchDTO searchDTO) throws BusinessException {
		try {
			MerchantEntity merchantEntity = null;
			if(merchantDTO != null && !StringUtils.isEmpty(merchantDTO.getMobileNo())) {
				merchantEntity = merchantDAO.getMerchantByMobileNo(merchantDTO.getMobileNo());
			}else if(merchantDTO != null && !StringUtils.isEmpty(merchantDTO.getMerchantUuid())) {
				merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			}
			return userDAO.searchMerchantUserTotal(merchantEntity, searchDTO);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public List<MerchantStatementDTO> searchMerchantStatement(MerchantStatementSearchDTO statementSearchDTO) throws BusinessException {
		List<MerchantStatementDTO> statementDTOList = new ArrayList<MerchantStatementDTO>();
		try {
			List<MerchantStatementEntity> statementEntityList = merchantStatementDAO.searchMerchantStatement(statementSearchDTO);
			if (statementEntityList != null)
				for (Iterator<MerchantStatementEntity> iterator = statementEntityList.iterator(); iterator
						.hasNext();) {
					MerchantStatementEntity statementEntity = iterator.next();
					MerchantStatementDTO statementDTO = new MerchantStatementDTO();
					statementDTO.setTransactionTime(statementEntity.getTransactionTime());
					statementDTO.setTransactionAmount(statementEntity.getTransactionAmount());
					statementDTO.setTransactionCode(statementEntity.getTransactionCode());
					statementDTO.setTransactionDesc(statementEntity.getTransactionDesc());
					statementDTO.setTransactionPoint(statementEntity.getTransactionPoint());
					statementDTO.setPointBefore(statementEntity.getPointBefore());
					statementDTO.setPointAfter(statementEntity.getPointAfter());
					statementDTO.setBalanceBefore(statementEntity.getBalanceBefore());
					statementDTO.setBalanceAfter(statementEntity.getBalanceAfter());
					statementDTO.setTransactionType(statementEntity.getTransactionType());
					statementDTOList.add(statementDTO);
				}
			return statementDTOList;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public int searchMerchantStatementTotal(MerchantStatementSearchDTO searchDTO) throws BusinessException {
		try {
			return merchantStatementDAO.searchMerchantStatementTotal(searchDTO);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public int searchMerchantStatementTotalPoint(MerchantStatementSearchDTO searchDTO) throws BusinessException {
		try {
			return merchantStatementDAO.searchMerchantStatementPoint(searchDTO);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public BigDecimal searchMerchantStatementTotalAmount(MerchantStatementSearchDTO searchDTO) throws BusinessException {
		try {
			return merchantStatementDAO.searchMerchantStatementAmount(searchDTO);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public List<MerchantChargeDTO> searchMerchantCharge(MerchantChargeSearchDTO searchDTO) throws BusinessException {
		List<MerchantChargeDTO> chargeDTOList = new ArrayList<MerchantChargeDTO>();
		try {
			List<MerchantChargeEntity> chargeEntityList = merchantChargeDAO.searchMerchantCharge(searchDTO);
			if (chargeEntityList != null)
				for (Iterator<MerchantChargeEntity> iterator = chargeEntityList.iterator(); iterator
						.hasNext();) {
					MerchantChargeEntity chargeEntity = iterator.next();
					MerchantChargeDTO chargeDTO = new MerchantChargeDTO();
					chargeDTO.setChargeNo(chargeEntity.getChargeNo());
					chargeDTO.setChargeAmount(chargeEntity.getChargeAmount());
					chargeDTO.setChargePoint(chargeEntity.getChargePoint());
					chargeDTO.setChargeTime(chargeEntity.getChargeTime());
					chargeDTO.setPointBefore(chargeEntity.getPointBefore());
					chargeDTO.setPointAfter(chargeEntity.getPointAfter());
					chargeDTO.setPaymentMethod(chargeEntity.getPaymentMethod());
					MerchantEntity merchantEntity = chargeEntity.getMerchantEntity();
					MerchantDTO merchantDTO = new MerchantDTO();
					merchantDTO.setMerchantName(merchantEntity.getMerchantName());
					merchantDTO.setMobileNo(merchantEntity.getMobileNo());
					chargeDTO.setMerchantDTO(merchantDTO);
					chargeDTOList.add(chargeDTO);
				}
			return chargeDTOList;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public int searchMerchantChargeTotal(MerchantChargeSearchDTO searchDTO) throws BusinessException {
		try {
			return merchantChargeDAO.searchMerchantChargeTotal(searchDTO);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public int searchMerchantChargeTotalPoint(MerchantChargeSearchDTO searchDTO) throws BusinessException {
		try {
			return merchantChargeDAO.searchMerchantChargePoint(searchDTO);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public BigDecimal searchMerchantChargeTotalAmount(MerchantChargeSearchDTO searchDTO) throws BusinessException {
		try {
			return merchantChargeDAO.searchMerchantChargeAmount(searchDTO);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public List<MerchantAssignDTO> searchMerchantAssign(MerchantAssignSearchDTO searchDTO) throws BusinessException {
		List<MerchantAssignDTO> assignDTOList = new ArrayList<MerchantAssignDTO>();
		try {
			List<MerchantAssignEntity> assignEntityList = merchantAssignDAO.searchMerchantAssign(searchDTO);
			if (assignEntityList != null)
				for (Iterator<MerchantAssignEntity> iterator = assignEntityList.iterator(); iterator
						.hasNext();) {
					MerchantAssignEntity assignEntity = iterator.next();
					MerchantAssignDTO assignDTO = new MerchantAssignDTO();
					assignDTO.setAssignNo(assignEntity.getAssignNo());
					assignDTO.setAssignPoint(assignEntity.getAssignPoint());
					assignDTO.setAssignTime(assignEntity.getAssignTime());
					assignDTO.setTranAmount(assignEntity.getTranAmount());
					assignDTO.setMerchantPointBefore(assignEntity.getMerchantPointBefore());
					assignDTO.setMerchantPointAfter(assignEntity.getMerchantPointAfter());
					assignDTO.setUserPointBefore(assignEntity.getUserPointBefore());
					assignDTO.setUserPointAfter(assignEntity.getUserPointAfter());
					
					MerchantEntity merchantEntity = assignEntity.getMerchantEntity();
					MerchantDTO merchantDTO = new MerchantDTO();
					merchantDTO.setMerchantName(merchantEntity.getMerchantName());
					merchantDTO.setMobileNo(merchantEntity.getMobileNo());
					assignDTO.setMerchantDTO(merchantDTO);
					
					UserEntity userEntity = assignEntity.getUserEntity();
					UserDTO userDTO = new UserDTO();
					userDTO.setUserUuid(userEntity.getUserUuid());
					userDTO.setPersonalPhone(userEntity.getPersonalPhone());
					userDTO.setName(userEntity.getName());
					assignDTO.setUserDTO(userDTO);
					
					assignDTOList.add(assignDTO);
				}
			return assignDTOList;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public int searchMerchantAssignTotal(MerchantAssignSearchDTO searchDTO) throws BusinessException {
		try {
			return merchantAssignDAO.searchMerchantAssignTotal(searchDTO);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public int searchMerchantAssignTotalPoint(MerchantAssignSearchDTO searchDTO) throws BusinessException {
		try {
			return merchantAssignDAO.searchMerchantAssignPoint(searchDTO);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public BigDecimal searchMerchantAssignTotalAmount(MerchantAssignSearchDTO searchDTO) throws BusinessException {
		try {
			return merchantAssignDAO.searchMerchantAssignAmount(searchDTO);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public int getIncrementMerchantCountByDate(Date startDate, Date endDate) throws BusinessException{
		int count = 0;
		try{
			count = merchantDAO.getIncrementMerchantCountByDate(startDate, endDate);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}catch(Exception e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return count;
	}

	@Override
	public List<ChartDTO> getIncrementMerchantCountChartByDate(Date startDate,
			Date endDate) throws BusinessException {
		try{
			return merchantDAO.getIncrementMerchantChart(startDate, endDate);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}catch(Exception e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public String generateMerchantPaymentQrCode(MerchantDTO merchantDTO) throws BusinessException {
		try{
			if(!StringUtils.isEmpty(merchantDTO.getMerchantUuid())) {
				return "http://"+ paymentUtil.getDomainName()+"/pay?m_id="+merchantDTO.getMerchantUuid();
			}else {
				MerchantEntity merchantEntity = merchantDAO.getMerchantByMobileNo(merchantDTO.getMobileNo());
				String merchantUuid = merchantEntity.getMerchantUuid();
				return "http://"+ paymentUtil.getDomainName()+"/pay?m_id="+merchantUuid;
			}
		}catch(Exception e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public List<MerchantDTO> inquiryNearMerchant(MerchantDTO merchantDTO)
			throws BusinessException {
		try{
			return merchantDAO.getDistance(merchantDTO);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}catch(Exception e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public void applyMerchantApplication(MerchantApplicationDTO applicationDTO) throws BusinessException {
		
		try{
			MerchantEntity merchantEntity = merchantDAO.getMerchantByMobileNo(applicationDTO.getMobileNo());
			if(merchantEntity != null) {
				throw new BusinessException(BusinessErrorCode.MERCHANT_DUPLICATE_MOBILENO);
			}
			List<MerchantApplicationEntity> outstandingList =  merchantApplicationDAO.getOutstandingMerchantApplications(applicationDTO.getMobileNo());
			if(outstandingList!=null && outstandingList.size()>0) {
				throw new BusinessException(BusinessErrorCode.MERCHANT_APPLICATION_DUPLICATE);
			}
			MerchantApplicationEntity applicationEntity = new MerchantApplicationEntity();
			applicationEntity.setMobileNo(applicationDTO.getMobileNo());
			applicationEntity.setMerchantAddress(applicationDTO.getMerchantAddress());
			applicationEntity.setMerchantDescription(applicationDTO.getMerchantDescription());
			applicationEntity.setMerchantName(applicationDTO.getMerchantName());
			applicationEntity.setApplicationStatus(MerchantConstants.VERIFYSTATUS_PENDING);
			applicationEntity.setApplicationId(getApplicationId());
			applicationEntity.setApplicationTime(new Date());
			applicationEntity.setProvince(applicationDTO.getProvince());
			applicationEntity.setCity(applicationDTO.getCity());
			applicationEntity.setDistrict(applicationDTO.getDistrict());
			applicationEntity.setLatitude(applicationDTO.getLatitude());
			applicationEntity.setLongitude(applicationDTO.getLongitude());
			applicationEntity.setContactName(applicationDTO.getContactName());
			applicationEntity.setMemo(applicationDTO.getMemo());
			applicationEntity.setReferrer(applicationDTO.getReferrer());
			applicationEntity.setCreateBy(applicationDTO.getMobileNo());
			applicationEntity.setUpdateBy(applicationDTO.getMobileNo());
			merchantApplicationDAO.createMerchantApplication(applicationEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}catch(BusinessException e){
			throw e;
		}
		
	}

	@Override
	public void approveMerchantApplication(MerchantApplicationDTO applicationDTO) throws BusinessException {
		
		try{
			
			MerchantApplicationEntity applicationEntity = merchantApplicationDAO.getUpgradeApplicationByUuid(applicationDTO.getMerchantApplicationUuid());
			
			//创建商家
			MerchantDTO merchantDTO = new MerchantDTO();
			merchantDTO.setMerchantName(applicationEntity.getMerchantName());
			merchantDTO.setMerchantAddress(applicationEntity.getMerchantAddress());
			merchantDTO.setLongitude(applicationEntity.getLongitude());
			merchantDTO.setLatitude(applicationEntity.getLatitude());
			merchantDTO.setMobileNo(applicationEntity.getMobileNo());
			merchantDTO.setProvince(applicationEntity.getProvince());
			merchantDTO.setCity(applicationEntity.getCity());
			merchantDTO.setDistrict(applicationEntity.getDistrict());
			merchantDTO.setContactName(applicationEntity.getContactName());
			merchantDTO.setRegisterDate(new Date());
			merchantDTO.setClosed(false);
			merchantDTO.setReferrer(applicationEntity.getReferrer());
			this.createMerchant(merchantDTO);
			
			//更新入驻状态
			applicationEntity.setVerifyTime(new Date());
			applicationEntity.setApplicationStatus(MerchantConstants.VERIFYSTATUS_APPROVED);
			merchantApplicationDAO.updateMerchantApplication(applicationEntity);
			
			try {
				smsSenderUtil.merchantApplicationApproved(merchantDTO.getMerchantName(), globalSettingDAO.getGlobalSetting().getApplicationName(), merchantDTO.getMobileNo(), merchantDTO.getPassword());
			}catch(Exception e) {
				logger.error("发送商家入驻申请审核通过短信发生异常:"+merchantDTO.getMobileNo());
				logger.error(e.getMessage());
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		
	}

	@Override
	public void rejectMerchantApplication(MerchantApplicationDTO applicationDTO) throws BusinessException {
		
		try{
			MerchantApplicationEntity applicationEntity = merchantApplicationDAO.getUpgradeApplicationByUuid(applicationDTO.getMerchantApplicationUuid());
			applicationEntity.setVerifyTime(new Date());
			applicationEntity.setApplicationStatus(MerchantConstants.VERIFYSTATUS_REJECTED);
			applicationEntity.setMemo(applicationDTO.getMemo());
			merchantApplicationDAO.updateMerchantApplication(applicationEntity);
			
			try {
				smsSenderUtil.merchantApplicationRejected(applicationEntity.getMerchantName(), globalSettingDAO.getGlobalSetting().getApplicationName(), applicationEntity.getMobileNo(),applicationDTO.getMemo());
			}catch(Exception e) {
				logger.error("发送商家入驻申请审核失败短信发生异常:"+applicationEntity.getMobileNo());
				logger.error(e.getMessage());
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}catch(Exception e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		
	}

	@Override
	public MerchantApplicationDTO inquiryApplication(String uuid) throws BusinessException {
		
		try{
			MerchantApplicationEntity applicationEntity = merchantApplicationDAO.getUpgradeApplicationByUuid(uuid);
			MerchantApplicationDTO applicationDTO  = new MerchantApplicationDTO();
			applicationEntity2DTO(applicationEntity, applicationDTO);
			return applicationDTO;
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}catch(Exception e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		
	}

	@Override
	public List<MerchantApplicationDTO> inquiryApplicationByMerchant(String mobileNo)
			throws BusinessException {
		List<MerchantApplicationDTO> applicationDTOList = new ArrayList<MerchantApplicationDTO>();
		try{
			List<MerchantApplicationEntity> applicationEntityList = merchantApplicationDAO.getMerchantApplications(mobileNo);
			for (MerchantApplicationEntity applicationEntity : applicationEntityList) {
				MerchantApplicationDTO applicationDTO  = new MerchantApplicationDTO();
				applicationEntity2DTO(applicationEntity, applicationDTO);
				applicationDTOList.add(applicationDTO);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}catch(Exception e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return applicationDTOList;
	}

	@Override
	public List<MerchantApplicationDTO> searchApplication(
			MerchantApplicationSearchDTO searchDTO) throws BusinessException {
		List<MerchantApplicationDTO> applicationDTOList = new ArrayList<MerchantApplicationDTO>();
		try{
			List<MerchantApplicationEntity> applicationEntityList = merchantApplicationDAO.searchMerchantApplication(searchDTO);
			for (MerchantApplicationEntity applicationEntity : applicationEntityList) {
				MerchantApplicationDTO applicationDTO  = new MerchantApplicationDTO();
				applicationEntity2DTO(applicationEntity, applicationDTO);
				applicationDTOList.add(applicationDTO);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}catch(Exception e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return applicationDTOList;
	}

	@Override
	public int searchApplicationTotal(MerchantApplicationSearchDTO searchDTO)
			throws BusinessException {
		try{
			return merchantApplicationDAO.searchMerchantApplicationTotal(searchDTO);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}catch(Exception e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}
	
	@Override
	public void createMerchantShopper(MerchantShopperDTO shopperDTO) throws BusinessException {
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(shopperDTO.getMerchantDTO().getMerchantUuid());
			MerchantShopperEntity shopperEntity = new MerchantShopperEntity();
			shopperEntity.setMerchantEntity(merchantEntity);
			shopperEntity.setName(shopperDTO.getName());
			shopperEntity.setSex(shopperDTO.getSex());
			shopperEntity.setMobileNo(shopperDTO.getMobileNo());
			shopperEntity.setPhoto(shopperDTO.getPhoto());
			shopperEntity.setEnabled(true);
			merchantShopperDAO.createMerchantShopper(shopperEntity);
		} catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}
	@Override
	public void updateMerchantShopper(MerchantShopperDTO shopperDTO) throws BusinessException {
		try {
			MerchantShopperEntity shopperEntity = merchantShopperDAO.getShopperById(shopperDTO.getMerchantShopperUuid());
			shopperEntity.setName(shopperDTO.getName());
			shopperEntity.setSex(shopperDTO.getSex());
			shopperEntity.setMobileNo(shopperDTO.getMobileNo());
			shopperEntity.setPhoto(shopperDTO.getPhoto());
			merchantShopperDAO.updateMerchantShopper(shopperEntity);
		} catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}
	
	@Override
	public void deleteMerchantShopper(MerchantShopperDTO shopperDTO) throws BusinessException {
		try {
			MerchantShopperEntity shopperEntity = merchantShopperDAO.getShopperById(shopperDTO.getMerchantShopperUuid());
			merchantShopperDAO.deleteMerchantShopper(shopperEntity);
		} catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}
	
	@Override
	public void enableMerchantShopper(MerchantShopperDTO shopperDTO) throws BusinessException {
		try {
			MerchantShopperEntity shopperEntity = merchantShopperDAO.getShopperById(shopperDTO.getMerchantShopperUuid());
			shopperEntity.setEnabled(true);
			merchantShopperDAO.updateMerchantShopper(shopperEntity);
		} catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}
	
	@Override
	public void disableMerchantShopper(MerchantShopperDTO shopperDTO) throws BusinessException {
		try {
			MerchantShopperEntity shopperEntity = merchantShopperDAO.getShopperById(shopperDTO.getMerchantShopperUuid());
			shopperEntity.setEnabled(false);
			merchantShopperDAO.updateMerchantShopper(shopperEntity);
		} catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}
	
	@Override
	public List<MerchantShopperDTO> getShoppersByMerchant(MerchantDTO merchantDTO) throws BusinessException {
		List<MerchantShopperDTO> shopperDTOList = new ArrayList<MerchantShopperDTO>();
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			List<MerchantShopperEntity> shopperEntityList = merchantShopperDAO.getShoppersByMerchant(merchantEntity);
			for (Iterator<MerchantShopperEntity> iterator = shopperEntityList.iterator(); iterator.hasNext();) {
				MerchantShopperEntity shopperEntity = iterator.next();
				MerchantShopperDTO shopperDTO = new MerchantShopperDTO();
				shopperDTO.setMerchantShopperUuid(shopperEntity.getMerchantShopperUuid());
				shopperDTO.setEnabled(shopperEntity.isEnabled());
				shopperDTO.setName(shopperEntity.getName());
				shopperDTO.setSex(shopperEntity.getSex());
				shopperDTO.setMobileNo(shopperEntity.getMobileNo());
				shopperDTO.setPhoto(shopperEntity.getPhoto());
				shopperDTOList.add(shopperDTO);
			}
			
		} catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		return shopperDTOList;
	}
	
	@Override
	public MerchantShopperDTO getShopperByUuid(String uuid) throws BusinessException {
		try {
			MerchantShopperEntity shopperEntity = merchantShopperDAO.getShopperById(uuid);
			MerchantShopperDTO shopperDTO = new MerchantShopperDTO();
			shopperDTO.setMerchantShopperUuid(shopperEntity.getMerchantShopperUuid());
			shopperDTO.setEnabled(shopperEntity.isEnabled());
			shopperDTO.setName(shopperEntity.getName());
			shopperDTO.setSex(shopperEntity.getSex());
			shopperDTO.setMobileNo(shopperEntity.getMobileNo());
			shopperDTO.setPhoto(shopperEntity.getPhoto());
			MerchantEntity merchantEntity = shopperEntity.getMerchantEntity();
			if(merchantEntity != null) {
				MerchantDTO merchantDTO = new MerchantDTO();
				merchantDTO.setMerchantUuid(merchantEntity.getMerchantUuid());
				merchantDTO.setMerchantName(merchantEntity.getMerchantName());
				shopperDTO.setMerchantDTO(merchantDTO);
			}
			return shopperDTO;
			
		} catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}
	
	@Override
	public List<MerchantShopperDTO> searchMerchantShopper(MerchantSearchDTO merchantSearchDTO) throws BusinessException {
		List<MerchantShopperDTO> shopperDTOList = new ArrayList<MerchantShopperDTO>();
		try {
			List<MerchantShopperEntity> shopperEntityList = merchantShopperDAO.searchMerchantShopper(merchantSearchDTO);
			for (Iterator<MerchantShopperEntity> iterator = shopperEntityList.iterator(); iterator.hasNext();) {
				MerchantShopperEntity shopperEntity = iterator.next();
				MerchantShopperDTO shopperDTO = new MerchantShopperDTO();
				shopperDTO.setMerchantShopperUuid(shopperEntity.getMerchantShopperUuid());
				shopperDTO.setEnabled(shopperEntity.isEnabled());
				shopperDTO.setName(shopperEntity.getName());
				shopperDTO.setSex(shopperEntity.getSex());
				shopperDTO.setMobileNo(shopperEntity.getMobileNo());
				shopperDTO.setPhoto(shopperEntity.getPhoto());
				shopperDTOList.add(shopperDTO);
				MerchantEntity merchantEntity = shopperEntity.getMerchantEntity();
				if(merchantEntity != null) {
					MerchantDTO merchantDTO = new MerchantDTO();
					merchantDTO.setMerchantUuid(merchantEntity.getMerchantUuid());
					merchantDTO.setMerchantName(merchantEntity.getMerchantName());
					shopperDTO.setMerchantDTO(merchantDTO);
				}
			}
			
		} catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		return shopperDTOList;
	}
	
	@Override
	public int searchMerchantShopperTotal(MerchantSearchDTO merchantSearchDTO) throws BusinessException {
		try {
			return merchantShopperDAO.searchMerchantShopperTotal(merchantSearchDTO);
		} catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}
	
	public String getApplicationId() throws BusinessException {
		try {
			int orderNoSequence = sequenceDAO.nextByDate("MERCHANTAPPLICATIONID", new Date(),
					null);
			String orderNo = "MA" + DateTimeUtil.formatDateByYYMMDDHHmm(new Date())
					+ StringUtils.leftPad(String.valueOf(orderNoSequence), 4,
							"0");
			return orderNo;
		}catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}

	void applicationEntity2DTO(MerchantApplicationEntity applicationEntity, MerchantApplicationDTO applicationDTO) {
		applicationDTO.setMerchantApplicationUuid(applicationEntity.getMerchantApplicationUuid());
		applicationDTO.setMobileNo(applicationEntity.getMobileNo());
		applicationDTO.setContactName(applicationEntity.getContactName());
		applicationDTO.setMerchantAddress(applicationEntity.getMerchantAddress());
		applicationDTO.setMerchantDescription(applicationEntity.getMerchantDescription());
		applicationDTO.setMerchantName(applicationEntity.getMerchantName());
		applicationDTO.setApplicationStatus(applicationEntity.getApplicationStatus());
		applicationDTO.setApplicationId(applicationEntity.getApplicationId());
		applicationDTO.setApplicationTime(applicationEntity.getApplicationTime());
		applicationDTO.setVerifyTime(applicationEntity.getVerifyTime());
		applicationDTO.setProvince(applicationEntity.getProvince());
		applicationDTO.setCity(applicationEntity.getCity());
		applicationDTO.setDistrict(applicationEntity.getDistrict());
		applicationDTO.setLatitude(applicationEntity.getLatitude());
		applicationDTO.setLongitude(applicationEntity.getLongitude());
		applicationDTO.setContactName(applicationDTO.getContactName());
		applicationDTO.setMemo(applicationEntity.getMemo());
		applicationDTO.setReferrer(applicationEntity.getReferrer());
	}
	
}
