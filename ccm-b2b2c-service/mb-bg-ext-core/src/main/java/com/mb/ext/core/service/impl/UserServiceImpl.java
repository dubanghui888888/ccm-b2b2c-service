
package com.mb.ext.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.mb.ext.core.dao.merchant.MerchantFollowDAO;
import com.mb.ext.core.entity.merchant.MerchantFollowEntity;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mb.ext.core.constant.MerchantConstants;
import com.mb.ext.core.constant.OrderConstants;
import com.mb.ext.core.context.UserContext;
import com.mb.ext.core.dao.FileDAO;
import com.mb.ext.core.dao.MerchantDAO;
import com.mb.ext.core.dao.NoteDAO;
import com.mb.ext.core.dao.OrgDAO;
import com.mb.ext.core.dao.PartnerDAO;
import com.mb.ext.core.dao.RoleDAO;
import com.mb.ext.core.dao.SequenceDAO;
import com.mb.ext.core.dao.SettingDAO;
import com.mb.ext.core.dao.UserAuthDAO;
import com.mb.ext.core.dao.UserAwardDAO;
import com.mb.ext.core.dao.UserBalanceDAO;
import com.mb.ext.core.dao.UserDAO;
import com.mb.ext.core.dao.UserInventoryDAO;
import com.mb.ext.core.dao.UserInventoryHistoryDAO;
import com.mb.ext.core.dao.UserLevelDAO;
import com.mb.ext.core.dao.UserPerformanceDAO;
import com.mb.ext.core.dao.UserStatementDAO;
import com.mb.ext.core.dao.UserTokenDAO;
import com.mb.ext.core.dao.UserTreeDAO;
import com.mb.ext.core.dao.UserWechatDAO;
import com.mb.ext.core.dao.coupon.CouponOrderDAO;
import com.mb.ext.core.dao.merchant.MerchantAssignDAO;
import com.mb.ext.core.dao.merchant.MerchantUserDAO;
import com.mb.ext.core.dao.order.OrderDAO;
import com.mb.ext.core.dao.product.ProductCollectDAO;
import com.mb.ext.core.dao.product.ProductDAO;
import com.mb.ext.core.entity.FileEntity;
import com.mb.ext.core.entity.UserAuthEntity;
import com.mb.ext.core.entity.UserBalanceEntity;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.UserInventoryEntity;
import com.mb.ext.core.entity.UserLevelEntity;
import com.mb.ext.core.entity.UserPerformanceEntity;
import com.mb.ext.core.entity.UserStatementEntity;
import com.mb.ext.core.entity.UserTreeEntity;
import com.mb.ext.core.entity.UserWechatEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.merchant.MerchantUserEntity;
import com.mb.ext.core.entity.product.ProductCollectEntity;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.DeliveryService;
import com.mb.ext.core.service.FundService;
import com.mb.ext.core.service.NoteService;
import com.mb.ext.core.service.OrderService;
import com.mb.ext.core.service.SettingService;
import com.mb.ext.core.service.UserService;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.ChartDTO;
import com.mb.ext.core.service.spec.FileDTO;
import com.mb.ext.core.service.spec.OrderSearchDTO;
import com.mb.ext.core.service.spec.UserAwardDTO;
import com.mb.ext.core.service.spec.UserBalanceDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.UserDeliveryDTO;
import com.mb.ext.core.service.spec.UserGivePointDTO;
import com.mb.ext.core.service.spec.UserInventoryDTO;
import com.mb.ext.core.service.spec.UserLevelDTO;
import com.mb.ext.core.service.spec.UserPerformanceDTO;
import com.mb.ext.core.service.spec.UserSearchDTO;
import com.mb.ext.core.service.spec.UserStatementDTO;
import com.mb.ext.core.service.spec.UserStatementSearchDTO;
import com.mb.ext.core.service.spec.UserWithdrawDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.order.OrderDTO;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.ext.core.util.MailSenderUtil;
import com.mb.ext.core.util.SMSSenderUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.service.AbstractService;
import com.mb.framework.util.SecurityUtil;
import com.mb.framework.util.StringUtil;
import com.mb.framework.util.log.LogHelper;

@Service
@Qualifier("userService")
public class UserServiceImpl extends AbstractService implements UserService<BodyDTO> {
	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;

	@Autowired
	@Qualifier("userTreeDAO")
	private UserTreeDAO userTreeDAO;

	@Autowired
	@Qualifier("userPerformanceDAO")
	private UserPerformanceDAO userPerformanceDAO;

	@Autowired
	@Qualifier("productDAO")
	private ProductDAO productDAO;

	@Autowired
	@Qualifier("productCollectDAO")
	private ProductCollectDAO productCollectDAO;

	@Autowired
	private SettingService settingService;

	@Autowired
	@Qualifier("orderDAO")
	private OrderDAO orderDAO;

	@Autowired
	@Qualifier("partnerDAO")
	private PartnerDAO partnerDAO;

	@Autowired
	@Qualifier("userLevelDAO")
	private UserLevelDAO userLevelDAO;

	@Autowired
	@Qualifier("userBalanceDAO")
	private UserBalanceDAO userBalanceDAO;

	@Autowired
	@Qualifier("merchantFollowDAO")
	private MerchantFollowDAO merchantFollowDAO;

	@Autowired
	@Qualifier("merchantAssignDAO")
	private MerchantAssignDAO merchantAssignDAO;

	@Autowired
	@Qualifier("userInventoryHistoryDAO")
	private UserInventoryHistoryDAO userInventoryHistoryDAO;

	@Autowired
	@Qualifier("userAwardDAO")
	private UserAwardDAO userAwardDAO;

	@Autowired
	@Qualifier("userInventoryDAO")
	private UserInventoryDAO userInventoryDAO;

	@Autowired
	@Qualifier("OrderService")
	private OrderService orderService;

	@Autowired
	@Qualifier("FundService")
	private FundService fundService;

	@Autowired
	@Qualifier("DeliveryService")
	private DeliveryService deliveryService;

	@Autowired
	@Qualifier("settingDAO")
	private SettingDAO settingDAO;

	@Autowired
	@Qualifier("userWechatDAO")
	private UserWechatDAO userWechatDAO;

	@Autowired
	@Qualifier("merchantDAO")
	private MerchantDAO merchantDAO;

	@Autowired
	@Qualifier("merchantUserDAO")
	private MerchantUserDAO merchantUserDAO;

	@Autowired
	@Qualifier("orgDAO")
	private OrgDAO orgDAO;

	@Autowired
	@Qualifier("roleDAO")
	private RoleDAO roleDAO;

	@Autowired
	@Qualifier("noteDAO")
	private NoteDAO noteDAO;

	@Autowired
	@Qualifier("userAuthDAO")
	private UserAuthDAO userAuthDAO;

	@Autowired
	@Qualifier("userTokenDAO")
	private UserTokenDAO userTokenDAO;

	@Autowired
	@Qualifier("fileDAO")
	private FileDAO fileDAO;

	@Autowired
	@Qualifier("sequenceDAO")
	private SequenceDAO sequenceDAO;

	@Autowired
	@Qualifier("NoteService")
	private NoteService noteService;

	@Autowired
	@Qualifier("userStatementDAO")
	private UserStatementDAO userStatementDAO;

	@Autowired
	@Qualifier("couponOrderDAO")
	private CouponOrderDAO couponOrderDAO;

	@Autowired
	private MailSenderUtil mailSenderUtil;

	@Autowired
	private SMSSenderUtil smsSenderUtil;

	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	@Override
	public void registerUser(UserDTO userDTO) throws BusinessException {
		UserEntity userEntity = null;
		try {
			String countryCode = userDTO.getPersonalPhoneCountryCode();
			String personalPhone = userDTO.getPersonalPhone();
			if (!StringUtil.isEmtpy(personalPhone)) {
				userEntity = userDAO.getUserByMobileNo(countryCode, personalPhone);
				if (userEntity != null)
					throw new BusinessException(BusinessErrorCode.USER_DUPLICATE_PHONE);
			}
			userEntity = new UserEntity();
			DTO2Entity(userDTO, userEntity);
			Date registerDate = userDTO.getRegisterDate();
			if (registerDate == null)
				registerDate = new Date();
			userEntity.setRegisterDate(registerDate);
			userEntity.setActive(true);

			int userId = sequenceDAO.next("USERID", null);
			String userIdStr = String.valueOf(userId);
			userEntity.setId(userIdStr);
			userEntity.setCreateBy("Consumer");
			userEntity.setUpdateBy("Consumer");

			// 用户等级
			UserLevelEntity userLevelEntity = userLevelDAO.getDefaultUserLevel();
			userEntity.setUserLevelEntity(userLevelEntity);

			// 会员上级
			UserDTO supervisorL1DTO = userDTO.getSupervisorL1();

			// 检查是否开启公开注册, 如未开启公开注册且邀请人信息为空则报出错误
			if (!settingService.getGlobalApplicationSetting().isApplicationPublicRegisterEnabled()
					&& supervisorL1DTO == null) {
				throw new BusinessException(BusinessErrorCode.USER_NO_INVITATION);
			}
			if (supervisorL1DTO != null) {
				UserEntity invitationUser = userDAO.getUserByUuid(supervisorL1DTO.getUserUuid());
				if (invitationUser != null) {
					// 邀请会员(邀请关系建立)
					userEntity.setInvitationUser(invitationUser);
					// 既无推广提成又无销售提成的会员不能作为上级会员存在，一直向上追溯具有推广或销售提成的会员作为其上级会员
					UserEntity supervisorL1Entity = userDAO.getUserByUuid(supervisorL1DTO.getUserUuid());
					;
					while (supervisorL1Entity != null) {
						UserLevelEntity supervisorLevel = supervisorL1Entity.getUserLevelEntity();
						if (supervisorLevel != null && (supervisorLevel.isRecruitProfitEnabled()
								|| supervisorLevel.isSaleProfitEnabled())) {
							break;
						} else {
							supervisorL1Entity = supervisorL1Entity.getSupervisorL1();
						}

					}
					userEntity.setSupervisorL1(supervisorL1Entity);
				}
			}

			userDAO.addUser(userEntity);

			// 会员关系树建立
			UserTreeEntity userUserEntity = new UserTreeEntity();
			userUserEntity.setUserEntity(userEntity);
			userUserEntity.setAncestorEntity(userEntity);
			userUserEntity.setLevel(0);
			userUserEntity.setCreateBy("Consumer");
			userUserEntity.setUpdateBy("Consumer");
			userTreeDAO.addUserTree(userUserEntity);

			int level = 1;
			UserEntity ancestorEntity = userEntity.getSupervisorL1();
			while (ancestorEntity != null) {
				UserTreeEntity userTreeEntity = new UserTreeEntity();
				userTreeEntity.setUserEntity(userEntity);
				userTreeEntity.setAncestorEntity(ancestorEntity);
				userTreeEntity.setLevel(level++);
				userTreeEntity.setCreateBy("Consumer");
				userTreeEntity.setUpdateBy("Consumer");
				userTreeDAO.addUserTree(userTreeEntity);
				ancestorEntity = ancestorEntity.getSupervisorL1();
			}

			// Insert into UserAuth table
			String password = userDTO.getPassword();
			if (StringUtils.isEmpty(password)) {
				password = MerchantConstants.DEFAULT_PASSWORD;
			}
			String encryptedPassword = SecurityUtil.encryptMd5(password);
			UserAuthEntity userAuthEntity = new UserAuthEntity();
			userAuthEntity.setPassword(encryptedPassword);
			userAuthEntity.setFailCount(0);
			userAuthEntity.setLocked(false);
			userAuthEntity.setActivated(true);
			userAuthEntity.setUserEntity(userEntity);
			userAuthEntity.setCreateBy("Consumer");
			userAuthEntity.setUpdateBy("Consumer");
			userAuthDAO.addUserAuth(userAuthEntity);

			// Insert into MerchantUser table
			MerchantDTO merchantDTO = userDTO.getMerchantDTO();
			if (merchantDTO != null && !StringUtils.isEmpty(merchantDTO.getMobileNo())) {
				MerchantEntity merchantEntity = merchantDAO.getMerchantByMobileNo(merchantDTO.getMobileNo());
				MerchantUserEntity merchantUserEntity = new MerchantUserEntity();
				merchantUserEntity.setMerchantEntity(merchantEntity);
				merchantUserEntity.setUserEntity(userEntity);
				merchantUserEntity.setCreateBy("Consumer");
				merchantUserEntity.setUpdateBy("Consumer");
				merchantUserDAO.createMerchantUser(merchantUserEntity);
			}

			// Insert into UserBalance tale
			UserBalanceEntity userBalanceEntity = new UserBalanceEntity();
			userBalanceEntity.setUserEntity(userEntity);
			userBalanceEntity.setAvailableBalance(BigDecimal.valueOf(0));
			userBalanceEntity.setLedgerBalance(BigDecimal.valueOf(0));
			userBalanceEntity.setAvailablePoint(0);
			userBalanceEntity.setLedgerPoint(0);
			userBalanceEntity.setCreateBy("Consumer");
			userBalanceEntity.setUpdateBy("Consumer");
			userBalanceDAO.createUserBalance(userBalanceEntity);

			/*
			 * send email and SMS to notify noteService.sendNotification(userDTO, "adduser",
			 * new String[]{password}); String entName = "";
			 * if(!StringUtil.isEmtpy(userEntity.getPersonalEmail())){ String emailSubject =
			 * propertyRepository.getProperty("notification.adduser.mail.subject"); String
			 * replacedEmailSubject = emailSubject.replace("{1}", entName); String emailBody
			 * = propertyRepository.getProperty("notification.adduser.mail.body"); String
			 * replacedEmailBody = emailBody.replace("{1}", entName).replace("{2}",
			 * password); String sentTo = userEntity.getPersonalEmail();
			 * mailSenderUtil.sendMail(replacedEmailSubject, replacedEmailBody, sentTo, "",
			 * null); } if(!StringUtil.isEmtpy(userEntity.getPersonalPhone())){ String
			 * smsBody = propertyRepository.getProperty("notification.adduser.sms.body");
			 * String replacedSMSBody = smsBody.replace("{1}", entName).replace("{2}",
			 * password); String countryCode = userEntity.getPersonalPhoneCountryCode();
			 * String mobileNo = userEntity.getPersonalPhone();
			 * smsSenderUtil.sendSMS(replacedSMSBody, countryCode, mobileNo); }
			 */

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e1) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e1);
		}
	}

	@Override
	public void importUser(UserDTO userDTO) throws BusinessException {
		UserEntity userEntity = null;
		try {
			String countryCode = userDTO.getPersonalPhoneCountryCode();
			String personalPhone = userDTO.getPersonalPhone();
			String idCardNo = userDTO.getIdCardNo();
			if (!StringUtil.isEmtpy(personalPhone)) {
				userEntity = userDAO.getUserByMobileNo(countryCode, personalPhone);
				if (userEntity != null)
					throw new BusinessException(BusinessErrorCode.USER_DUPLICATE_PHONE);
			}
			if (!StringUtil.isEmtpy(idCardNo)) {
				userEntity = userDAO.getUserByIdCardNo(idCardNo);
				if (userEntity != null)
					throw new BusinessException(BusinessErrorCode.USER_DUPLICATE_IDCARDNO);
			}
			userEntity = new UserEntity();
			DTO2Entity(userDTO, userEntity);
			userEntity.setRegisterDate(userDTO.getRegisterDate());
			int userId = sequenceDAO.next("USERID", null);
			String userIdStr = String.valueOf(userId);
			userEntity.setId(userIdStr);
			userEntity.setActive(true);

			userEntity.setCreateBy("Admin");
			userEntity.setUpdateBy("Admin");

			// 会员等级
			if (userDTO.getUserLevelDTO() != null) {
				String userLevelUuid = userDTO.getUserLevelDTO().getUserLevelUuid();
				if (!StringUtils.isEmpty(userLevelUuid)) {
					UserLevelEntity userLevelEntity = userLevelDAO.getUserLevelByUuid(userLevelUuid);
					userEntity.setUserLevelEntity(userLevelEntity);
				}
			}

			// 会员上级
			UserDTO supervisorL1DTO = userDTO.getSupervisorL1();
			if (supervisorL1DTO != null) {
				UserEntity supervisorL1Entity = userDAO.getUserByUuid(supervisorL1DTO.getUserUuid());
				if (supervisorL1Entity != null) {
					userEntity.setSupervisorL1(supervisorL1Entity);
				}
			}
			userDAO.addUser(userEntity);

			// Insert into UserAuth table
			String password = userDTO.getPassword();
			if (StringUtils.isEmpty(password)) {
				password = MerchantConstants.DEFAULT_PASSWORD;
			}
			String encryptedPassword = SecurityUtil.encryptMd5(password);
			UserAuthEntity userAuthEntity = new UserAuthEntity();
			userAuthEntity.setPassword(encryptedPassword);
			userAuthEntity.setFailCount(0);
			userAuthEntity.setLocked(false);
			userAuthEntity.setActivated(true);
			userAuthEntity.setUserEntity(userEntity);
			userAuthEntity.setCreateBy(userDTO.getId());
			userAuthEntity.setUpdateBy(userDTO.getId());
			userAuthDAO.addUserAuth(userAuthEntity);

			// 会员关系树建立
			UserTreeEntity userUserEntity = new UserTreeEntity();
			userUserEntity.setUserEntity(userEntity);
			userUserEntity.setAncestorEntity(userEntity);
			userUserEntity.setLevel(0);
			userUserEntity.setCreateBy("Admin");
			userUserEntity.setUpdateBy("Admin");
			userTreeDAO.addUserTree(userUserEntity);

			int level = 1;
			UserEntity ancestorEntity = userEntity.getSupervisorL1();
			while (ancestorEntity != null) {
				UserTreeEntity userTreeEntity = new UserTreeEntity();
				userTreeEntity.setUserEntity(userEntity);
				userTreeEntity.setAncestorEntity(ancestorEntity);
				userTreeEntity.setLevel(level++);
				userTreeEntity.setCreateBy("Admin");
				userTreeEntity.setUpdateBy("Admin");
				userTreeDAO.addUserTree(userTreeEntity);
				ancestorEntity = ancestorEntity.getSupervisorL1();
			}

			// Insert into MerchantUser table
			MerchantDTO merchantDTO = userDTO.getMerchantDTO();
			if (merchantDTO != null && !StringUtils.isEmpty(merchantDTO.getMobileNo())) {
				MerchantEntity merchantEntity = merchantDAO.getMerchantByMobileNo(merchantDTO.getMobileNo());
				MerchantUserEntity merchantUserEntity = new MerchantUserEntity();
				merchantUserEntity.setMerchantEntity(merchantEntity);
				merchantUserEntity.setUserEntity(userEntity);
				merchantUserEntity.setCreateBy(userDTO.getId());
				merchantUserEntity.setUpdateBy(userDTO.getId());
				merchantUserDAO.createMerchantUser(merchantUserEntity);
			}

			// 资金余额
			UserBalanceEntity balanceEntity = new UserBalanceEntity();
			balanceEntity.setUserEntity(userEntity);
			balanceEntity.setAvailableBalance(BigDecimal.valueOf(0));
			balanceEntity.setLedgerBalance(BigDecimal.valueOf(0));
			balanceEntity.setAvailablePoint(0);
			balanceEntity.setLedgerPoint(0);
			balanceEntity.setCreateBy("Admin");
			balanceEntity.setUpdateBy("Admin");
			userBalanceDAO.createUserBalance(balanceEntity);

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e1) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e1);
		}
	}

	private void DTO2Entity(UserDTO userDTO, UserEntity userEntity) {
		if (userDTO != null && userEntity != null) {
			userEntity.setCreateBy(userDTO.getCreateBy());
			userEntity.setUpdateBy(userDTO.getUpdateBy());
			userEntity.setName(userDTO.getName());
			userEntity.setPersonalEmail(userDTO.getPersonalEmail());
			userEntity.setPersonalPhone(userDTO.getPersonalPhone());
			userEntity.setPersonalPhoneCountryCode(userDTO.getPersonalPhoneCountryCode());
			userEntity.setPhotoUrl(userDTO.getPhotoUrl());
			userEntity.setSex(userDTO.getSex());
			userEntity.setOpenId(userDTO.getOpenId());
		}
	}

	@Override
	public void entity2DTO(UserEntity userEntity, UserDTO userDTO) {
		if (userDTO != null && userEntity != null) {
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
			userDTO.setNotificationEnabled(userEntity.isNotificationEnabled());
			userDTO.setProfitWelfareReceived(userEntity.isProfitWelfareReceived());
			UserInventoryEntity userInventoryEntity = userEntity.getUserInventoryEntity();
			if (userInventoryEntity != null) {
				UserInventoryDTO userInventoryDTO = new UserInventoryDTO();
				userInventoryDTO.setBalance(userInventoryEntity.getBalance());
				userInventoryDTO.setBalancePouch(userInventoryEntity.getBalancePouch());
				userDTO.setUserInventoryDTO(userInventoryDTO);
			}

			UserBalanceEntity userBalanceEntity = userEntity.getUserBalanceEntity();
			if (userBalanceEntity != null) {
				UserBalanceDTO userBalanceDTO = new UserBalanceDTO();
				userBalanceDTO.setAvailableBalance(userBalanceEntity.getAvailableBalance());
				userBalanceDTO.setLedgerBalance(userBalanceEntity.getLedgerBalance());
				userBalanceDTO.setAvailablePoint(userBalanceEntity.getAvailablePoint());
				userBalanceDTO.setLedgerPoint(userBalanceEntity.getLedgerPoint());
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

			UserEntity supervisorL1Entity = userEntity.getSupervisorL1();
			if (supervisorL1Entity != null) {
				UserDTO supervisorL1DTO = new UserDTO();
				supervisorL1DTO.setUserUuid(supervisorL1Entity.getUserUuid());
				supervisorL1DTO.setName(supervisorL1Entity.getName());
				supervisorL1DTO.setPersonalPhone(supervisorL1Entity.getPersonalPhone());
				userDTO.setSupervisorL1(supervisorL1DTO);
			}

			UserEntity supervisorL2Entity = null;
			if (supervisorL1Entity != null) {
				supervisorL2Entity = supervisorL1Entity.getSupervisorL1();
			}
			if (supervisorL2Entity != null) {
				UserDTO supervisorL2DTO = new UserDTO();
				supervisorL2DTO.setUserUuid(supervisorL2Entity.getUserUuid());
				supervisorL2DTO.setName(supervisorL2Entity.getName());
				supervisorL2DTO.setPersonalPhone(supervisorL2Entity.getPersonalPhone());
				userDTO.setSupervisorL2(supervisorL2DTO);
			}

			UserEntity supervisorL3Entity = null;
			if (supervisorL2Entity != null) {
				supervisorL3Entity = supervisorL2Entity.getSupervisorL1();
			}
			if (supervisorL3Entity != null) {
				UserDTO supervisorL3DTO = new UserDTO();
				supervisorL3DTO.setUserUuid(supervisorL3Entity.getUserUuid());
				supervisorL3DTO.setName(supervisorL3Entity.getName());
				supervisorL3DTO.setPersonalPhone(supervisorL3Entity.getPersonalPhone());
				userDTO.setSupervisorL3(supervisorL3DTO);
			}
		}
	}

	@Override
	public UserDTO getUserById(String id) throws BusinessException {
		UserDTO userDTO = null;
		try {
			UserEntity userEntity = userDAO.getUserById(id);
			if (userEntity != null) {
				userDTO = new UserDTO();
				entity2DTO(userEntity, userDTO);
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e2) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e2);
		}
		return userDTO;
	}

	@Override
	public List<UserDTO> getUserByMerchant(MerchantDTO merchantDTO) throws BusinessException {
		List<UserDTO> userDTOList = new ArrayList<UserDTO>();
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			List<UserEntity> userEntityList = userDAO.getUserByMerchant(merchantEntity);
			if (userEntityList == null)
				throw new BusinessException(BusinessErrorCode.USER_NOT_FOUND);
			for (Iterator<UserEntity> iterator = userEntityList.iterator(); iterator.hasNext();) {
				UserEntity userEntity = (UserEntity) iterator.next();
				UserDTO userDTO = new UserDTO();
				entity2DTO(userEntity, userDTO);
				userDTOList.add(userDTO);
			}
			return userDTOList;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (BusinessException e1) {
			throw e1;
		} catch (Exception e2) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e2);
		}
	}

	@Override
	public void followMerchant(MerchantDTO merchantDTO, UserDTO userDTO) throws BusinessException {

		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			MerchantFollowEntity followEntity = merchantFollowDAO.getMerchantFollow(merchantEntity,userEntity);
			if(followEntity == null){
				followEntity = new MerchantFollowEntity();
				followEntity.setMerchantEntity(merchantEntity);
				followEntity.setUserEntity(userEntity);
				merchantFollowDAO.createMerchantFollow(followEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public void cancelFollowMerchant(MerchantDTO merchantDTO, UserDTO userDTO) throws BusinessException {

		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			MerchantFollowEntity followEntity = merchantFollowDAO.getMerchantFollow(merchantEntity,userEntity);
			if(followEntity != null){
				merchantFollowDAO.deleteMerchantFollow(followEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public boolean isMerchantFollowed(MerchantDTO merchantDTO, UserDTO userDTO) throws BusinessException{
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			MerchantFollowEntity followEntity = merchantFollowDAO.getMerchantFollow(merchantEntity,userEntity);
			return followEntity != null;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public UserDTO getUserByMobileNo(String countryCode, String mobileNo) throws BusinessException {
		UserDTO userDTO = null;
		try {
			UserEntity userEntity = userDAO.getUserByMobileNo(countryCode, mobileNo);
			if (userEntity != null) {
				userDTO = new UserDTO();
				entity2DTO(userEntity, userDTO);
				/*
				 * List<OrderDTO> orderList = orderService.getOrderByUser(userDTO);
				 * userDTO.setOrderList(orderList); List<UserInventoryHistoryDTO>
				 * inventoryHistoryList =
				 * inventoryService.inquiryInventoryHistoryByUser(userDTO);
				 * userDTO.setInventoryHistoryList(inventoryHistoryList); List<UserWithdrawDTO>
				 * withdrawList = fundService.getUserWithdrawByUser(userDTO);
				 * userDTO.setWithdrawList(withdrawList);
				 */
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e2) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e2);
		}
		return userDTO;
	}

	@Override
	public List<UserDTO> getL1ChildUsers(UserDTO userDTO) throws BusinessException {
		List<UserDTO> userDTOList = new ArrayList<UserDTO>();
		try {
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			List<UserEntity> userEntityList = userDAO.getL1ChildUsers(userEntity);
			if (userEntityList == null)
				throw new BusinessException(BusinessErrorCode.USER_NOT_FOUND);
			for (Iterator<UserEntity> iterator = userEntityList.iterator(); iterator.hasNext();) {
				UserEntity childEntity = (UserEntity) iterator.next();
				UserDTO childDTO = new UserDTO();
				entity2DTO(childEntity, childDTO);
				userDTOList.add(childDTO);
			}
			return userDTOList;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (BusinessException e1) {
			throw e1;
		} catch (Exception e2) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e2);
		}
	}

	@Override
	public List<UserDTO> getL1AndL2ChildUsers(UserDTO userDTO) throws BusinessException {
		List<UserDTO> userDTOList = new ArrayList<UserDTO>();
		try {
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			List<UserEntity> userEntityList = userDAO.getL1AndL2ChildUsers(userEntity);
			if (userEntityList == null)
				throw new BusinessException(BusinessErrorCode.USER_NOT_FOUND);
			for (Iterator<UserEntity> iterator = userEntityList.iterator(); iterator.hasNext();) {
				UserEntity childEntity = (UserEntity) iterator.next();
				UserDTO childDTO = new UserDTO();
				entity2DTO(childEntity, childDTO);
				userDTOList.add(childDTO);
			}
			return userDTOList;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (BusinessException e1) {
			throw e1;
		} catch (Exception e2) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e2);
		}
	}

	@Override
	public int getInvitedUserCount(UserDTO userDTO) throws BusinessException {
		try {
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			List<UserLevelEntity> levels = userLevelDAO.getUserLevels();
			/* 获取会员直邀数(具有推广和销售收益的会员) */
			List<String> uuids = new ArrayList<String>();
			for (UserLevelEntity level : levels) {
				if (level.isRecruitProfitEnabled() || level.isSaleProfitEnabled()) {
					uuids.add(level.getUserLevelUuid());
				}
			}
			if(uuids.size()==0)	return 0;
			int userCountDirect = userDAO.getInvitedUserCountByLevelDate(userEntity, uuids,
					DateUtils.addYears(new Date(), -10), new Date());
			return userCountDirect;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}
	
	@Override
	public List<UserDTO> getInvitedUsers(UserDTO userDTO) throws BusinessException {
		List<UserDTO> userDTOList = new ArrayList<UserDTO>();
		try {
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			List<UserLevelEntity> levels = userLevelDAO.getUserLevels();
			List<String> uuids = new ArrayList<String>();
			for (UserLevelEntity level : levels) {
				uuids.add(level.getUserLevelUuid());
			}
			List<UserEntity> userList = userDAO.getInvitedUsersByLevelDate(userEntity, uuids,
					DateUtils.addYears(new Date(), -10), new Date());
			for (UserEntity nUserEntity : userList) {
				UserDTO nUserDTO = new UserDTO();
				entity2DTO(nUserEntity, nUserDTO);
				userDTOList.add(nUserDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return userDTOList;
	}
	
	@Override
	public int getTeamUserCount(UserDTO userDTO) throws BusinessException {
		try {
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			return userDAO.getAllChildUserCount(userEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}
	
	@Override
	public BigDecimal getPersonalSaleAmount(UserDTO userDTO) throws BusinessException {
		try {
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			OrderSearchDTO orderSearchDTO = new OrderSearchDTO();
			orderSearchDTO.setKeyArray(new String[] { "ORDER_STATUS_LIST", "USER" });
			orderSearchDTO.setUserUuid(userEntity.getUserUuid());
			List<String> payedOrderStatusList = new ArrayList<String>();
			payedOrderStatusList.add(OrderConstants.ORDER_STATUS_NOT_DELIVERIED);
			payedOrderStatusList.add(OrderConstants.ORDER_STATUS_EVALUATED);
			payedOrderStatusList.add(OrderConstants.ORDER_STATUS_DELIVERIED);
			payedOrderStatusList.add(OrderConstants.ORDER_STATUS_COMPLETED);
			orderSearchDTO.setOrderStatusList(payedOrderStatusList);
			BigDecimal productAmountDirect = orderDAO.searchOrdersTotalAmount(orderSearchDTO);
			return productAmountDirect;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}
	@Override
	public BigDecimal getTeamSaleAmount(UserDTO userDTO) throws BusinessException {
		try {
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			return userPerformanceDAO.getTotalPerformanceByUser(userEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}


	@Override
	public UserEntity getUser(UserDTO userDTO) throws BusinessException {
		UserEntity userEntity = null;
		try {
			String userId = userDTO.getId();
			String userUuid = userDTO.getUserUuid();
			String mobileNo = userDTO.getPersonalPhone();
			String countryCode = userDTO.getPersonalPhoneCountryCode();

			if (!StringUtils.isEmpty(userUuid))
				userEntity = userDAO.getUserByUuid(userUuid);
			else if (!StringUtil.isEmtpy(userId)) {
				userEntity = userDAO.getUserById(userId);
			} else if (!StringUtil.isEmtpy(mobileNo)) {
				userEntity = userDAO.getUserByMobileNo(countryCode, mobileNo);
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

		return userEntity;
	}

	@Override
	public void updateUser(UserDTO userDTO) throws BusinessException {
		try {
			UserEntity userEntity = getUser(userDTO);
			if (userDTO.getId() != null) {
				if (!StringUtils.isEmpty(userDTO.getId()) && !userDTO.getId().equals(userEntity.getId())) {
					UserEntity pUserEntity = userDAO.getUserById(userDTO.getId());
					if (pUserEntity != null)
						throw new BusinessException(BusinessErrorCode.USER_DUPLICATE_ID);
				}
				userEntity.setId(userDTO.getId());
			}
			if (userDTO.getName() != null)
				userEntity.setName(userDTO.getName());

			if (userDTO.getPersonalPhone() != null) {
				if (!userDTO.getPersonalPhone().equals(userEntity.getPersonalPhone())) {
					UserEntity pUserEntity = userDAO.getUserByMobileNo(userDTO.getPersonalPhoneCountryCode(),
							userDTO.getPersonalPhone());
					if (pUserEntity != null)
						throw new BusinessException(BusinessErrorCode.USER_DUPLICATE_PHONE);
				}
				userEntity.setPersonalPhone(userDTO.getPersonalPhone());
			}
			if (userDTO.getPhotoUrl() != null)
				userEntity.setPhotoUrl(userDTO.getPhotoUrl());
			if (userDTO.getSex() != null)
				userEntity.setSex(userDTO.getSex());
			userDAO.updateUser(userEntity);

		} catch (DAOException e) {
			logger.error(e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (BusinessException e) {
			logger.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void updateUserPoint(UserDTO userDTO) throws BusinessException {
		try {
			UserEntity userEntity = getUser(userDTO);
			UserBalanceEntity userBalanceEntity = userEntity.getUserBalanceEntity();
			if (userBalanceEntity != null) {
				int availablePoint = userDTO.getUserBalanceDTO().getAvailablePoint();
				int ledgerPoint = userDTO.getUserBalanceDTO().getLedgerPoint();
				userBalanceEntity.setAvailablePoint(availablePoint);
				userBalanceEntity.setLedgerPoint(ledgerPoint);
				userBalanceDAO.updateUserBalance(userBalanceEntity);
			}
		} catch (DAOException e) {
			logger.error(e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (BusinessException e) {
			logger.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void updateUserBalance(UserDTO userDTO) throws BusinessException {
		try {
			UserEntity userEntity = getUser(userDTO);
			UserBalanceEntity userBalanceEntity = userEntity.getUserBalanceEntity();
			if (userBalanceEntity != null) {
				userBalanceEntity.setAvailableBalance(userDTO.getUserBalanceDTO().getAvailableBalance());
				userBalanceEntity.setLedgerBalance(userDTO.getUserBalanceDTO().getLedgerBalance());
				userBalanceEntity.setAvailablePoint(userDTO.getUserBalanceDTO().getAvailablePoint());
				userBalanceEntity.setLedgerPoint(userDTO.getUserBalanceDTO().getLedgerPoint());
				userBalanceDAO.updateUserBalance(userBalanceEntity);
			}
		} catch (DAOException e) {
			logger.error(e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (BusinessException e) {
			logger.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void updateUserField(UserDTO userDTO, String fieldName) throws BusinessException {
		try {
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			if ("NAME".equals(fieldName)) {
				userEntity.setName(userDTO.getName());
			} else if ("SEX".equals(fieldName)) {
				userEntity.setSex(userDTO.getSex());
			} else if ("PERSONALPHONE".equals(fieldName)) {
				// 检查手机号码不能重复
				UserEntity nUserEntity = userDAO.getUserByMobileNo("86", userDTO.getPersonalPhone());
				if (nUserEntity != null && !nUserEntity.getUserUuid().equals(userEntity.getUserUuid())) {
					throw new BusinessException(BusinessErrorCode.USER_DUPLICATE_PHONE);
				}
				userEntity.setPersonalPhone(userDTO.getPersonalPhone());
				if(StringUtils.isEmpty(userEntity.getPersonalPhoneCountryCode())) {
					userEntity.setPersonalPhoneCountryCode(userDTO.getPersonalPhoneCountryCode());
				}
			} else if ("PHOTOURL".equals(fieldName)) {
				userEntity.setPhotoUrl(userDTO.getPhotoUrl());
			} else if ("USERLEVEL".equals(fieldName)) {
				UserLevelEntity userLevelEntity = userLevelDAO
						.getUserLevelByUuid(userDTO.getUserLevelDTO().getUserLevelUuid());
				userEntity.setUserLevelEntity(userLevelEntity);
			}
			userDAO.updateUser(userEntity);
		} catch (DAOException e) {
			logger.error(e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void enableUserNotification(UserDTO userDTO) throws BusinessException {
		try {
			UserEntity userEntity = getUser(userDTO);
			if (userEntity != null) {
				userEntity.setNotificationEnabled(true);
				userDAO.updateUser(userEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void disableUserNotification(UserDTO userDTO) throws BusinessException {
		try {
			UserEntity userEntity = getUser(userDTO);
			if (userEntity != null) {
				userEntity.setNotificationEnabled(false);
				userDAO.updateUser(userEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void enableUser(UserDTO userDTO) throws BusinessException {
		try {
			UserEntity userEntity = getUser(userDTO);
			if (userEntity != null) {
				userEntity.setActive(true);
				userDAO.updateUser(userEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void memoUser(UserDTO userDTO) throws BusinessException {
		try {
			UserEntity userEntity = getUser(userDTO);
			if (userEntity != null) {
				userEntity.setMemo(userDTO.getMemo());
				userDAO.updateUser(userEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void disableUser(UserDTO userDTO) throws BusinessException {
		try {
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			if (userEntity != null) {
				userEntity.setActive(false);
				userDAO.updateUser(userEntity);
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void changeSupervisor(UserDTO userDTO, UserDTO supervisorDTO) throws BusinessException {
		try {
			UserEntity userEntity = null;
			UserEntity supervisorEntity = null;
			if (!StringUtils.isEmpty(userDTO.getUserUuid())) {
				userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			} else if (!StringUtils.isEmpty(userDTO.getPersonalPhone())) {
				userEntity = userDAO.getUserByMobileNo("86", userDTO.getPersonalPhone());
			}
			if (!StringUtils.isEmpty(supervisorDTO.getUserUuid())) {
				supervisorEntity = userDAO.getUserByUuid(supervisorDTO.getUserUuid());
			} else if (!StringUtils.isEmpty(supervisorDTO.getPersonalPhone())) {
				supervisorEntity = userDAO.getUserByMobileNo("86", supervisorDTO.getPersonalPhone());
			}
			if (userEntity != null && supervisorEntity != null) {
				userEntity.setSupervisorL1(supervisorEntity);
				userDAO.updateUser(userEntity);
			}
			buildChildUserTree(userEntity);

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public UserDTO getUserDTO(UserDTO userDTO) throws BusinessException {

		try {
			UserEntity userEntity = getUser(userDTO);
			if (userEntity == null) {
				throw new BusinessException(BusinessErrorCode.USER_NOT_FOUND);
			}
			userDTO = new UserDTO();
			entity2DTO(userEntity, userDTO);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return userDTO;

	}

	@Override
	public UserDTO getUserDetail(UserDTO userDTO) throws BusinessException {

		try {
			UserEntity userEntity = getUser(userDTO);
			if (userEntity == null) {
				throw new BusinessException(BusinessErrorCode.USER_NOT_FOUND);
			}
			userDTO = new UserDTO();
			entity2DTO(userEntity, userDTO);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return userDTO;

	}

	@Override
	public UserDTO getUserDTOByTokenId(String tokenId) throws BusinessException {
		UserDTO userDTO = new UserDTO();
		try {
			UserEntity userEntity = userDAO.getUserByTokenId(tokenId);
			entity2DTO(userEntity, userDTO);
			/* 本月销售额 */
			Date currDate = new Date();
			currDate.setDate(1);
			UserPerformanceEntity userPerformanceEntity = userPerformanceDAO.getPerformanceByUserDate(userEntity,
					currDate);
			UserPerformanceDTO userPerformanceDTO = new UserPerformanceDTO();
			if(userPerformanceEntity != null) {
				userPerformanceDTO.setPerformanceAmount(userPerformanceEntity.getPerformanceAmount());
				userPerformanceDTO.setPerformanceAward(userPerformanceEntity.getPerformanceAward());
			}else {
				userPerformanceDTO.setPerformanceAmount(BigDecimal.valueOf(0));
				userPerformanceDTO.setPerformanceAward(BigDecimal.valueOf(0));
			}
			userDTO.setUserPerformanceDTO(userPerformanceDTO);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return userDTO;
	}

	@Override
	public void setUserProfile(UserDTO userDTO) throws BusinessException {

		UserContext.set("USERPROFILE", userDTO);

	}

	@Override
	public List<UserDTO> searchUsers(UserSearchDTO searchDTO, int startIndex, int pageSize) throws BusinessException {
		List<UserDTO> userDTOList = new ArrayList<UserDTO>();
		try {
			List<UserEntity> userEntityList = new ArrayList<UserEntity>();
			userEntityList = userDAO.searchUsers(searchDTO, startIndex, pageSize);
			for (Iterator<UserEntity> iterator = userEntityList.iterator(); iterator.hasNext();) {
				UserEntity userEntity = (UserEntity) iterator.next();
				UserDTO userDTO = new UserDTO();
				entity2DTO(userEntity, userDTO);
				int orderPoint = orderDAO.getOrderActualPoint(userEntity);
				int couponOrderPoint = couponOrderDAO.getCouponOrderPointByUser(userEntity);
				int rankingTotalPoint = orderPoint + couponOrderPoint;
				userDTO.setOrderPoint(orderPoint);
				userDTO.setCouponOrderPoint(couponOrderPoint);
				userDTO.setRankingTotalPoint(rankingTotalPoint);
				userDTOList.add(userDTO);
			}
			return userDTOList;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e2) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e2);
		}
	}

	@Override
	public int searchUserTotal(UserSearchDTO searchDTO) throws BusinessException {
		try {
			return userDAO.searchUserTotal(searchDTO);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e2) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e2);
		}
	}

	@Override
	public UserDTO getUserByUuid(String userUuid) throws BusinessException {
		UserDTO userDTO = null;
		try {
			UserEntity userEntity = userDAO.getUserByUuid(userUuid);
			boolean isMerchant = false;
			if (userEntity != null) {
				userDTO = new UserDTO();
				entity2DTO(userEntity, userDTO);

				int totalL1 = userDAO.getL1ChildUserCount(userEntity);
				userDTO.setTotalL1(totalL1);
				int total = userDAO.getAllChildUserCount(userEntity);
				userDTO.setTotal(total);

				Date endDate = new Date();
				Date startDate = DateUtils.addYears(endDate, -5);
				int productUnit = orderService.getOrderProductUnitTotalByUser(startDate, endDate, userDTO);
				userDTO.setProductUnit(productUnit);
				BigDecimal productAmount = orderService.getOrderProductAmountTotalByUser(startDate, endDate, userDTO);
				userDTO.setProductAmount(productAmount);
				BigDecimal awardAmount = userAwardDAO.getTranAmountByUserDate(userEntity, startDate, endDate);
				awardAmount = awardAmount == null ? new BigDecimal(0) : awardAmount;
				userDTO.setAwardAmount(awardAmount);

				List<OrderDTO> orderList = orderService.getOrderByUser(userDTO);
				userDTO.setOrderList(orderList);
				List<UserDeliveryDTO> deliveryList = deliveryService.getDeliveryByUser(userDTO);
				userDTO.setDeliveryList(deliveryList);
				List<UserWithdrawDTO> withdrawList = fundService.getUserWithdrawByUser(userDTO);
				userDTO.setWithdrawList(withdrawList);
				List<UserAwardDTO> awardList = fundService.getUserAward(userDTO);
				userDTO.setAwardList(awardList);

				/* 本月销售额 */
				Date currDate = new Date();
				currDate.setDate(1);
				UserPerformanceEntity userPerformanceEntity = userPerformanceDAO.getPerformanceByUserDate(userEntity,
						currDate);
				UserPerformanceDTO userPerformanceDTO = new UserPerformanceDTO();
				if(userPerformanceEntity != null) {
					userPerformanceDTO.setPerformanceAmount(userPerformanceEntity.getPerformanceAmount());
					userPerformanceDTO.setPerformanceAward(userPerformanceEntity.getPerformanceAward());
				}else {
					userPerformanceDTO.setPerformanceAmount(BigDecimal.valueOf(0));
					userPerformanceDTO.setPerformanceAward(BigDecimal.valueOf(0));
				}
				userDTO.setUserPerformanceDTO(userPerformanceDTO);
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return userDTO;
	}

	@Override
	public List<UserDTO> getInactiveUsers() throws BusinessException {
		List<UserDTO> userDTOList = new ArrayList<UserDTO>();
		try {
			List<UserEntity> userEntityList = userDAO.getInactiveUsers();
			if (userEntityList != null)
				for (Iterator<UserEntity> iterator = userEntityList.iterator(); iterator.hasNext();) {
					UserEntity userEntity = (UserEntity) iterator.next();
					UserDTO userDTO = new UserDTO();
					entity2DTO(userEntity, userDTO);
					userDTOList.add(userDTO);
				}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e2) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e2);
		}
		return userDTOList;
	}

	@Override
	public void bindWechat(UserDTO userDTO) throws BusinessException {

		try {
			String openId = userDTO.getOpenId();
			if (StringUtils.isEmpty(openId)) {
				throw new BusinessException(BusinessErrorCode.USER_BIND_WECHAT);
			}
			UserEntity userEntity = getUser(userDTO);
			if (userEntity == null)
				throw new BusinessException(BusinessErrorCode.USER_NOT_FOUND);
			UserWechatEntity wechatEntity = userEntity.getUserWechatEntity();
			if (wechatEntity != null) {
				userEntity.setUserWechatEntity(null);
				userWechatDAO.deleteUserWechat(wechatEntity);
			}

			UserWechatEntity userWechatEntity = userWechatDAO.getUserWechatByOpenId(openId);
			if (userWechatEntity != null) {
				userWechatDAO.deleteUserWechat(userWechatEntity);
			}

			UserWechatEntity nEntity = new UserWechatEntity();
			nEntity.setUserEntity(userEntity);
			nEntity.setOpenId(openId);
			nEntity.setCreateBy(userEntity.getId());
			nEntity.setUpdateBy(userEntity.getId());
			userWechatDAO.addUserWechat(nEntity);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void unbindWechat(String openId) throws BusinessException {

		try {
			UserEntity userEntity = userWechatDAO.getUserWechatByOpenId(openId).getUserEntity();
			if (userEntity == null)
				throw new BusinessException(BusinessErrorCode.USER_NOT_FOUND);

			UserWechatEntity wechatEntity = userEntity.getUserWechatEntity();
			if (wechatEntity != null) {
				userEntity.setUserWechatEntity(null);
				userWechatDAO.deleteUserWechat(wechatEntity);
			}

		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public String getOpenIdByUser(UserDTO userDTO) throws BusinessException {

		String openId = null;
		try {
			UserEntity userEntity = getUser(userDTO);
			if (userEntity == null)
				throw new BusinessException(BusinessErrorCode.USER_NOT_FOUND);

			UserWechatEntity wechatEntity = userEntity.getUserWechatEntity();
			if (wechatEntity != null) {
				openId = wechatEntity.getOpenId();
			}

		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return openId;

	}

	@Override
	public UserDTO getUserByOpenId(String openId) throws BusinessException {
		UserDTO userDTO = null;
		try {
//			UserWechatEntity userWechatEntity = userWechatDAO.getUserWechatByOpenId(openId);
//			if (userWechatEntity == null)
//				throw new BusinessException(BusinessErrorCode.USER_WECHAT_NOT_BIND);
			UserEntity userEntity = userDAO.getUserByOpenId(openId);
			if(userEntity != null) {
				userDTO = new UserDTO();
				entity2DTO(userEntity, userDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return userDTO;
	}

	@Override
	public int getUserCountByMerchant(MerchantDTO merchantDTO) throws BusinessException {
		int count = 0;
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			count = userDAO.getUserCountByMerchant(merchantEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return count;

	}

	@Override
	public int getUserCount() throws BusinessException {
		int count = 0;
		try {
			count = userDAO.getUserCount();
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return count;

	}

	@Override
	public int getIncrementUserCountByMerchantDate(MerchantDTO merchantDTO, Date startDate, Date endDate)
			throws BusinessException {
		int count = 0;
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			count = userDAO.getIncrementUserCountByMerchantDate(merchantEntity, startDate, endDate);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return count;

	}

	@Override
	public int getIncrementUserCountByMerchantDateLevel(MerchantDTO merchantDTO, Date startDate, Date endDate,
			String userLevelName) throws BusinessException {
		int count = 0;
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			count = userDAO.getIncrementUserCountByMerchantDateLevel(merchantEntity, startDate, endDate, userLevelName);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return count;

	}

	@Override
	public int getIncrementUserCountByDate(Date startDate, Date endDate) throws BusinessException {
		int count = 0;
		try {
			count = userDAO.getIncrementUserCountByDate(startDate, endDate);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return count;

	}

	@Override
	public List<ChartDTO> getIncrementUserCountChartByDate(Date startDate, Date endDate) throws BusinessException {
		try {
			return userDAO.getIncrementUserChart(startDate, endDate);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public List<ChartDTO> getIncrementUserCountChartByMerchantDate(MerchantDTO merchantDTO, Date startDate,
			Date endDate) throws BusinessException {
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			return userDAO.getIncrementUserChartByMerchant(merchantEntity, startDate, endDate);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public List<UserStatementDTO> searchStatement(UserStatementSearchDTO searchDTO) throws BusinessException {
		// TODO Auto-generated method stub
		List<UserStatementDTO> userStatementDTOList = new ArrayList<UserStatementDTO>();

		try {
			List<UserStatementEntity> userStatementEntityList = userStatementDAO.searchUserStatement(searchDTO);
			if (userStatementEntityList != null)
				for (Iterator<UserStatementEntity> iterator = userStatementEntityList.iterator(); iterator.hasNext();) {
					UserStatementEntity userStatementEntity = iterator.next();
					UserStatementDTO userStatementDTO = new UserStatementDTO();
					userStatementDTO.setPointBefore(userStatementEntity.getPointBefore());
					userStatementDTO.setBalanceAfter(userStatementEntity.getBalanceAfter());
					userStatementDTO.setBalanceBefore(userStatementEntity.getBalanceBefore());
					userStatementDTO.setCreateBy(userStatementEntity.getCreateBy());
					userStatementDTO.setPointAfter(userStatementEntity.getPointAfter());
					userStatementDTO.setPointBefore(userStatementEntity.getPointBefore());
					userStatementDTO.setTransactionAmount(userStatementEntity.getTransactionAmount());
					userStatementDTO.setTransactionCode(userStatementEntity.getTransactionCode());
					userStatementDTO.setTransactionDesc(userStatementEntity.getTransactionDesc());
					userStatementDTO.setTransactionPoint(userStatementEntity.getTransactionPoint());
					userStatementDTO.setTransactionTime(userStatementEntity.getTransactionTime());
					userStatementDTO.setTransactionType(userStatementEntity.getTransactionType());
					userStatementDTO.setUpdateBy(userStatementEntity.getUpdateBy());
					userStatementDTOList.add(userStatementDTO);
				}
			return userStatementDTOList;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public int searchUserStatementTotal(UserStatementSearchDTO searchDTO) throws BusinessException {
		try {
			return userStatementDAO.searchUserStatementTotal(searchDTO);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public void givePoint(UserGivePointDTO userGivePointDTO) throws BusinessException {

		UserEntity nUserEntity = null;
		UserEntity userEntity = null;
		try {
			userEntity = userDAO.getUserByMobileNo("86", userGivePointDTO.getPhone());
			if (userEntity == null) {
				throw new BusinessException(BusinessErrorCode.USER_NOT_FOUND);
			}
			int point = userGivePointDTO.getPoint();
			// 积分更新
			UserDTO nUserDTO = userGivePointDTO.getUserDTO();
			nUserEntity = userDAO.getUserByUuid(nUserDTO.getUserUuid());
			UserBalanceEntity nBalanceEntity = nUserEntity.getUserBalanceEntity();
			int beforeAvailablePoint = nBalanceEntity.getAvailablePoint();
			if (nBalanceEntity.getAvailablePoint() < point) {
				throw new BusinessException(BusinessErrorCode.USER_POINT_INSUFFICIENT);
			}
			int afterAvailablePoint = beforeAvailablePoint - point;
			nBalanceEntity.setAvailablePoint(afterAvailablePoint);
			userBalanceDAO.updateUserBalance(nBalanceEntity);

			UserBalanceEntity balanceEntity = userEntity.getUserBalanceEntity();
			int beforeAvailablePoint1 = balanceEntity.getAvailablePoint();
			int afterAvailablePoint1 = beforeAvailablePoint1 + point;
			balanceEntity.setAvailablePoint(afterAvailablePoint1);
			userBalanceDAO.updateUserBalance(balanceEntity);

			// 创建会员积分明细记录
			UserStatementEntity nUserStatementEntity = new UserStatementEntity();
			nUserStatementEntity.setUserEntity(nUserEntity);
			nUserStatementEntity.setTransactionTime(new Date());
			nUserStatementEntity.setTransactionType(MerchantConstants.TRAN_TYPE_GIVE_POINT);
			nUserStatementEntity.setTransactionDesc("赠送" + userEntity.getPersonalPhone() + "用户" + point + "积分");
			nUserStatementEntity.setTransactionPoint(point);
			nUserStatementEntity.setPointBefore(beforeAvailablePoint);
			nUserStatementEntity.setPointAfter(afterAvailablePoint);
			userStatementDAO.createUserStatement(nUserStatementEntity);

			UserStatementEntity userStatementEntity = new UserStatementEntity();
			userStatementEntity.setUserEntity(userEntity);
			userStatementEntity.setTransactionTime(new Date());
			userStatementEntity.setTransactionType(MerchantConstants.TRAN_TYPE_OBTAIN_POINT);
			userStatementEntity.setTransactionDesc("获得由" + nUserEntity.getPersonalPhone() + "用户赠送的" + point + "积分");
			userStatementEntity.setTransactionPoint(point);
			userStatementEntity.setPointBefore(beforeAvailablePoint1);
			userStatementEntity.setPointAfter(afterAvailablePoint1);
			userStatementDAO.createUserStatement(userStatementEntity);

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (BusinessException e) {
			throw e;
		}

	}

	@Override
	public void collectProduct(UserDTO userDTO, ProductDTO productDTO) throws BusinessException {

		try {
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			ProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
			if (productCollectDAO.getProductCollectByUserProduct(userEntity, productEntity) == null) {
				ProductCollectEntity collectEntity = new ProductCollectEntity();
				collectEntity.setProductEntity(productEntity);
				collectEntity.setUserEntity(userEntity);
				productCollectDAO.addProductCollect(collectEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void cancelCollectProduct(UserDTO userDTO, ProductDTO productDTO) throws BusinessException {

		try {
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			ProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
			ProductCollectEntity collectEntity = productCollectDAO.getProductCollectByUserProduct(userEntity,
					productEntity);
			if (collectEntity != null) {
				productCollectDAO.deleteProductCollect(collectEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public List<ProductDTO> getCollectProductsByUser(UserDTO userDTO) throws BusinessException {
		List<ProductDTO> productList = new ArrayList<ProductDTO>();
		try {
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			List<ProductCollectEntity> collectEntityList = productCollectDAO.getProductCollectByUser(userEntity);
			for (Iterator<ProductCollectEntity> iterator = collectEntityList.iterator(); iterator.hasNext();) {
				ProductCollectEntity productCollectEntity = iterator.next();
				ProductEntity productEntity = productCollectEntity.getProductEntity();
				ProductDTO productDTO = new ProductDTO();
				productDTO.setProductUuid(productEntity.getProductUuid());
				productDTO.setProductName(productEntity.getProductName());
				productDTO.setSoldUnit(productEntity.getSoldUnit());
				productDTO.setTotalUnit(productEntity.getTotalUnit());
				productDTO.setUnitPoint(productEntity.getUnitPoint());
				productDTO.setUnitPrice(productEntity.getUnitPrice());
				productDTO.setUnitPointStandard(productEntity.getUnitPointStandard());
				productDTO.setUnitPriceStandard(productEntity.getUnitPriceStandard());
				FileEntity mainImage = productEntity.getProductMainImage();
				if (mainImage != null) {
					FileDTO fileDTO = new FileDTO();
					fileDTO.setUrl(mainImage.getUrl());
					productDTO.setProductMainImage(fileDTO);
					productList.add(productDTO);
				}
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return productList;
	}

	@Override
	public boolean isProductCollectedByUser(UserDTO userDTO, ProductDTO productDTO) throws BusinessException {
		try {
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			ProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
			ProductCollectEntity collectEntity = productCollectDAO.getProductCollectByUserProduct(userEntity,
					productEntity);
			return collectEntity != null;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public void addUserLevel(UserLevelDTO levelDTO) throws BusinessException {

		try {
			UserLevelEntity levelEntity = new UserLevelEntity();
			levelEntity.setName(levelDTO.getName());
			levelEntity.setProductDiscount(levelDTO.getProductDiscount());
			levelEntity.setDefault(levelDTO.isDefault());
			levelEntity.setMemo(levelDTO.getMemo());
			levelEntity.setRecruitProfitEnabled(levelDTO.isRecruitProfitEnabled());
			levelEntity.setSaleProfitEnabled(levelDTO.isSaleProfitEnabled());
			levelEntity.setRequiredProductAmountDirect(levelDTO.getRequiredProductAmountDirect());
			levelEntity.setRequiredProductAmountTeam(levelDTO.getRequiredProductAmountTeam());
			levelEntity.setRequiredUserCountDirect(levelDTO.getRequiredUserCountDirect());
			levelEntity.setRequiredUserCountTeam(levelDTO.getRequiredUserCountTeam());
			levelEntity.setRequiredByUser(levelDTO.isRequiredByUser());
			levelEntity.setRequiredByUserSymbol(levelDTO.getRequiredByUserSymbol());
			levelEntity.setRequiredByAmount(levelDTO.isRequiredByAmount());
			levelEntity.setRequiredByAmountSymbol(levelDTO.getRequiredByAmountSymbol());
			levelEntity.setDepth(levelDTO.getDepth());
			userLevelDAO.addUserLevel(levelEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void updateUserLevel(UserLevelDTO levelDTO) throws BusinessException {

		try {
			UserLevelEntity levelEntity = userLevelDAO.getUserLevelByUuid(levelDTO.getUserLevelUuid());
			levelEntity.setName(levelDTO.getName());
			levelEntity.setDefault(levelDTO.isDefault());
			levelEntity.setProductDiscount(levelDTO.getProductDiscount());
			levelEntity.setMemo(levelDTO.getMemo());
			levelEntity.setRecruitProfitEnabled(levelDTO.isRecruitProfitEnabled());
			levelEntity.setSaleProfitEnabled(levelDTO.isSaleProfitEnabled());
			levelEntity.setRequiredProductAmountDirect(levelDTO.getRequiredProductAmountDirect());
			levelEntity.setRequiredProductAmountTeam(levelDTO.getRequiredProductAmountTeam());
			levelEntity.setRequiredUserCountDirect(levelDTO.getRequiredUserCountDirect());
			levelEntity.setRequiredUserCountTeam(levelDTO.getRequiredUserCountTeam());
			levelEntity.setRequiredByUser(levelDTO.isRequiredByUser());
			levelEntity.setRequiredByUserSymbol(levelDTO.getRequiredByUserSymbol());
			levelEntity.setRequiredByAmount(levelDTO.isRequiredByAmount());
			levelEntity.setRequiredByAmountSymbol(levelDTO.getRequiredByAmountSymbol());
			levelEntity.setDepth(levelDTO.getDepth());
			userLevelDAO.addUserLevel(levelEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void deleteUserLevel(UserLevelDTO levelDTO) throws BusinessException {

		try {
			UserLevelEntity levelEntity = userLevelDAO.getUserLevelByUuid(levelDTO.getUserLevelUuid());
			userLevelDAO.deleteUserLevel(levelEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void defaultUserLevel(UserLevelDTO levelDTO) throws BusinessException {

		try {
			List<UserLevelEntity> entityList = userLevelDAO.getUserLevels();
			for (UserLevelEntity userLevelEntity : entityList) {
				userLevelEntity.setDefault(false);
				userLevelDAO.updateUserLevel(userLevelEntity);
			}
			UserLevelEntity levelEntity = userLevelDAO.getUserLevelByUuid(levelDTO.getUserLevelUuid());
			levelEntity.setDefault(true);
			userLevelDAO.updateUserLevel(levelEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void cancelDefaultUserLevel(UserLevelDTO levelDTO) throws BusinessException {

		try {
			UserLevelEntity levelEntity = userLevelDAO.getUserLevelByUuid(levelDTO.getUserLevelUuid());
			levelEntity.setDefault(false);
			userLevelDAO.updateUserLevel(levelEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public UserLevelDTO getUserLevelByUuid(String uuid) throws BusinessException {
		try {
			UserLevelEntity levelEntity = userLevelDAO.getUserLevelByUuid(uuid);
			UserLevelDTO levelDTO = new UserLevelDTO();
			levelDTO.setUserLevelUuid(levelEntity.getUserLevelUuid());
			levelDTO.setName(levelEntity.getName());
			levelDTO.setDefault(levelEntity.isDefault());
			levelDTO.setProductDiscount(levelEntity.getProductDiscount());
			levelDTO.setMemo(levelEntity.getMemo());
			levelDTO.setRecruitProfitEnabled(levelEntity.isRecruitProfitEnabled());
			levelDTO.setSaleProfitEnabled(levelEntity.isSaleProfitEnabled());
			levelDTO.setRequiredProductAmountDirect(levelEntity.getRequiredProductAmountDirect());
			levelDTO.setRequiredProductAmountTeam(levelEntity.getRequiredProductAmountTeam());
			levelDTO.setRequiredUserCountDirect(levelEntity.getRequiredUserCountDirect());
			levelDTO.setRequiredUserCountTeam(levelEntity.getRequiredUserCountTeam());
			levelDTO.setRequiredByUser(levelEntity.isRequiredByUser());
			levelDTO.setRequiredByUserSymbol(levelEntity.getRequiredByUserSymbol());
			levelDTO.setRequiredByAmount(levelEntity.isRequiredByAmount());
			levelDTO.setRequiredByAmountSymbol(levelEntity.getRequiredByAmountSymbol());
			levelDTO.setDepth(levelEntity.getDepth());
			return levelDTO;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public UserLevelDTO getDefaultUserLevel() throws BusinessException {
		UserLevelDTO userLevelDTO = null;
		try {
			UserLevelEntity levelEntity = userLevelDAO.getDefaultUserLevel();
			if (levelEntity != null) {
				UserLevelDTO levelDTO = new UserLevelDTO();
				levelDTO.setUserLevelUuid(levelEntity.getUserLevelUuid());
				levelDTO.setName(levelEntity.getName());
				levelDTO.setDefault(levelEntity.isDefault());
				levelDTO.setProductDiscount(levelEntity.getProductDiscount());
				levelDTO.setMemo(levelEntity.getMemo());
				levelDTO.setRecruitProfitEnabled(levelEntity.isRecruitProfitEnabled());
				levelDTO.setSaleProfitEnabled(levelEntity.isSaleProfitEnabled());
				levelDTO.setRequiredProductAmountDirect(levelEntity.getRequiredProductAmountDirect());
				levelDTO.setRequiredProductAmountTeam(levelEntity.getRequiredProductAmountTeam());
				levelDTO.setRequiredUserCountDirect(levelEntity.getRequiredUserCountDirect());
				levelDTO.setRequiredUserCountTeam(levelEntity.getRequiredUserCountTeam());
				levelDTO.setRequiredByUser(levelEntity.isRequiredByUser());
				levelDTO.setRequiredByUserSymbol(levelEntity.getRequiredByUserSymbol());
				levelDTO.setRequiredByAmount(levelEntity.isRequiredByAmount());
				levelDTO.setRequiredByAmountSymbol(levelEntity.getRequiredByAmountSymbol());
				levelDTO.setDepth(levelEntity.getDepth());
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return userLevelDTO;
	}

	@Override
	public UserLevelDTO getUserLevelByName(String name) throws BusinessException {

		try {
			UserLevelEntity levelEntity = userLevelDAO.getUserLevelByName(name);
			UserLevelDTO levelDTO = new UserLevelDTO();
			levelDTO.setUserLevelUuid(levelEntity.getUserLevelUuid());
			levelDTO.setName(levelEntity.getName());
			levelDTO.setDefault(levelEntity.isDefault());
			levelDTO.setProductDiscount(levelEntity.getProductDiscount());
			levelDTO.setMemo(levelEntity.getMemo());
			levelDTO.setRecruitProfitEnabled(levelEntity.isRecruitProfitEnabled());
			levelDTO.setSaleProfitEnabled(levelEntity.isSaleProfitEnabled());
			levelDTO.setRequiredProductAmountDirect(levelEntity.getRequiredProductAmountDirect());
			levelDTO.setRequiredProductAmountTeam(levelEntity.getRequiredProductAmountTeam());
			levelDTO.setRequiredUserCountDirect(levelEntity.getRequiredUserCountDirect());
			levelDTO.setRequiredUserCountTeam(levelEntity.getRequiredUserCountTeam());
			levelDTO.setRequiredByUser(levelEntity.isRequiredByUser());
			levelDTO.setRequiredByUserSymbol(levelEntity.getRequiredByUserSymbol());
			levelDTO.setRequiredByAmount(levelEntity.isRequiredByAmount());
			levelDTO.setRequiredByAmountSymbol(levelEntity.getRequiredByAmountSymbol());
			levelDTO.setDepth(levelEntity.getDepth());
			return levelDTO;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public UserLevelDTO getUserLevelByDepth(int depth) throws BusinessException {
		UserLevelDTO levelDTO = null;
		try {
			UserLevelEntity levelEntity = userLevelDAO.getUserLevelByDepth(depth);
			if (levelEntity != null) {
				levelDTO = new UserLevelDTO();
				levelDTO.setUserLevelUuid(levelEntity.getUserLevelUuid());
				levelDTO.setName(levelEntity.getName());
				levelDTO.setDefault(levelEntity.isDefault());
				levelDTO.setProductDiscount(levelEntity.getProductDiscount());
				levelDTO.setMemo(levelEntity.getMemo());
				levelDTO.setRecruitProfitEnabled(levelEntity.isRecruitProfitEnabled());
				levelDTO.setSaleProfitEnabled(levelEntity.isSaleProfitEnabled());
				levelDTO.setRequiredProductAmountDirect(levelEntity.getRequiredProductAmountDirect());
				levelDTO.setRequiredProductAmountTeam(levelEntity.getRequiredProductAmountTeam());
				levelDTO.setRequiredUserCountDirect(levelEntity.getRequiredUserCountDirect());
				levelDTO.setRequiredUserCountTeam(levelEntity.getRequiredUserCountTeam());
				levelDTO.setRequiredByUser(levelEntity.isRequiredByUser());
				levelDTO.setRequiredByUserSymbol(levelEntity.getRequiredByUserSymbol());
				levelDTO.setRequiredByAmount(levelEntity.isRequiredByAmount());
				levelDTO.setRequiredByAmountSymbol(levelEntity.getRequiredByAmountSymbol());
				levelDTO.setDepth(levelEntity.getDepth());
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return levelDTO;
	}

	@Override
	public UserLevelDTO getParentUserLevel(UserLevelDTO userLevelDTO) throws BusinessException {
		UserLevelDTO levelDTO = null;
		try {
			UserLevelEntity childUserLevelEntity = userLevelDAO.getUserLevelByUuid(userLevelDTO.getUserLevelUuid());
			UserLevelEntity levelEntity = userLevelDAO.getParentUserLevel(childUserLevelEntity);
			if (levelEntity != null) {
				levelDTO = new UserLevelDTO();
				levelDTO.setUserLevelUuid(levelEntity.getUserLevelUuid());
				levelDTO.setName(levelEntity.getName());
				levelDTO.setDefault(levelEntity.isDefault());
				levelDTO.setProductDiscount(levelEntity.getProductDiscount());
				levelDTO.setMemo(levelEntity.getMemo());
				levelDTO.setRecruitProfitEnabled(levelEntity.isRecruitProfitEnabled());
				levelDTO.setSaleProfitEnabled(levelEntity.isSaleProfitEnabled());
				levelDTO.setRequiredProductAmountDirect(levelEntity.getRequiredProductAmountDirect());
				levelDTO.setRequiredProductAmountTeam(levelEntity.getRequiredProductAmountTeam());
				levelDTO.setRequiredUserCountDirect(levelEntity.getRequiredUserCountDirect());
				levelDTO.setRequiredUserCountTeam(levelEntity.getRequiredUserCountTeam());
				levelDTO.setRequiredByUser(levelEntity.isRequiredByUser());
				levelDTO.setRequiredByUserSymbol(levelEntity.getRequiredByUserSymbol());
				levelDTO.setRequiredByAmount(levelEntity.isRequiredByAmount());
				levelDTO.setRequiredByAmountSymbol(levelEntity.getRequiredByAmountSymbol());
				levelDTO.setDepth(levelEntity.getDepth());
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return levelDTO;
	}

	@Override
	public UserLevelDTO getChildUserLevel(UserLevelDTO userLevelDTO) throws BusinessException {
		UserLevelDTO levelDTO = null;
		try {
			UserLevelEntity parentUserLevelEntity = userLevelDAO.getUserLevelByUuid(userLevelDTO.getUserLevelUuid());
			UserLevelEntity levelEntity = userLevelDAO.getChildUserLevel(parentUserLevelEntity);
			if (levelEntity != null) {
				levelDTO = new UserLevelDTO();
				levelDTO.setUserLevelUuid(levelEntity.getUserLevelUuid());
				levelDTO.setName(levelEntity.getName());
				levelDTO.setDefault(levelEntity.isDefault());
				levelDTO.setProductDiscount(levelEntity.getProductDiscount());
				levelDTO.setMemo(levelEntity.getMemo());
				levelDTO.setRecruitProfitEnabled(levelEntity.isRecruitProfitEnabled());
				levelDTO.setSaleProfitEnabled(levelEntity.isSaleProfitEnabled());
				levelDTO.setRequiredProductAmountDirect(levelEntity.getRequiredProductAmountDirect());
				levelDTO.setRequiredProductAmountTeam(levelEntity.getRequiredProductAmountTeam());
				levelDTO.setRequiredUserCountDirect(levelEntity.getRequiredUserCountDirect());
				levelDTO.setRequiredUserCountTeam(levelEntity.getRequiredUserCountTeam());
				levelDTO.setRequiredByUser(levelEntity.isRequiredByUser());
				levelDTO.setRequiredByUserSymbol(levelEntity.getRequiredByUserSymbol());
				levelDTO.setRequiredByAmount(levelEntity.isRequiredByAmount());
				levelDTO.setRequiredByAmountSymbol(levelEntity.getRequiredByAmountSymbol());
				levelDTO.setDepth(levelEntity.getDepth());
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return levelDTO;
	}

	@Override
	public List<UserLevelDTO> getUserLevels() throws BusinessException {
		List<UserLevelDTO> levelDTOList = new ArrayList<UserLevelDTO>();
		try {
			List<UserLevelEntity> levelEntityList = userLevelDAO.getUserLevels();
			for (UserLevelEntity levelEntity : levelEntityList) {
				UserLevelDTO levelDTO = new UserLevelDTO();
				levelDTO.setUserLevelUuid(levelEntity.getUserLevelUuid());
				levelDTO.setName(levelEntity.getName());
				levelDTO.setDefault(levelEntity.isDefault());
				levelDTO.setProductDiscount(levelEntity.getProductDiscount());
				levelDTO.setMemo(levelEntity.getMemo());
				levelDTO.setRecruitProfitEnabled(levelEntity.isRecruitProfitEnabled());
				levelDTO.setSaleProfitEnabled(levelEntity.isSaleProfitEnabled());
				levelDTO.setRequiredProductAmountDirect(levelEntity.getRequiredProductAmountDirect());
				levelDTO.setRequiredProductAmountTeam(levelEntity.getRequiredProductAmountTeam());
				levelDTO.setRequiredUserCountDirect(levelEntity.getRequiredUserCountDirect());
				levelDTO.setRequiredUserCountTeam(levelEntity.getRequiredUserCountTeam());
				levelDTO.setRequiredByUser(levelEntity.isRequiredByUser());
				levelDTO.setRequiredByUserSymbol(levelEntity.getRequiredByUserSymbol());
				levelDTO.setRequiredByAmount(levelEntity.isRequiredByAmount());
				levelDTO.setRequiredByAmountSymbol(levelEntity.getRequiredByAmountSymbol());
				levelDTO.setDepth(levelEntity.getDepth());
				levelDTOList.add(levelDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return levelDTOList;
	}

	@Override
	public List<UserDTO> getParentUsers(UserDTO userDTO) throws BusinessException {
		List<UserDTO> userDTOList = new ArrayList<UserDTO>();
		try {
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			List<UserEntity> userEntityList = userTreeDAO.getParentUsers(userEntity);
			if (userEntityList == null)
				throw new BusinessException(BusinessErrorCode.USER_NOT_FOUND);
			for (Iterator<UserEntity> iterator = userEntityList.iterator(); iterator.hasNext();) {
				UserEntity childEntity = (UserEntity) iterator.next();
				UserDTO childDTO = new UserDTO();
				entity2DTO(childEntity, childDTO);
				userDTOList.add(childDTO);
			}
			return userDTOList;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (BusinessException e1) {
			throw e1;
		} catch (Exception e2) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e2);
		}
	}

	@Override
	public List<UserDTO> getChildUsers(UserDTO userDTO) throws BusinessException {
		List<UserDTO> userDTOList = new ArrayList<UserDTO>();
		try {
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			List<UserEntity> userEntityList = userTreeDAO.getChildUsers(userEntity);
			if (userEntityList == null)
				throw new BusinessException(BusinessErrorCode.USER_NOT_FOUND);
			for (Iterator<UserEntity> iterator = userEntityList.iterator(); iterator.hasNext();) {
				UserEntity childEntity = (UserEntity) iterator.next();
				UserDTO childDTO = new UserDTO();
				entity2DTO(childEntity, childDTO);
				userDTOList.add(childDTO);
			}
			return userDTOList;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (BusinessException e1) {
			throw e1;
		} catch (Exception e2) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e2);
		}
	}

	private void buildChildUserTree(UserEntity userEntity) throws BusinessException {
		try {
			List<UserEntity> childUserEntityList = userTreeDAO.getChildUsers(userEntity);
			for (Iterator<UserEntity> iterator = childUserEntityList.iterator(); iterator.hasNext();) {
				UserEntity childUserEntity = (UserEntity) iterator.next();
				this.buildUserTree(childUserEntity);
			}

		} catch (BusinessException e) {
			logger.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void buildUserTree(UserEntity userEntity) throws BusinessException {
		try {
			String userTreeSql = "DELETE FROM user_tree WHERE user_tree.USER_UUID = '{USERUUID}'";
			userTreeSql = userTreeSql.replace("{USERUUID}", userEntity.getUserUuid());
			userTreeDAO.executeInsertUpdateNativeSQL(userTreeSql);

			UserTreeEntity userUserEntity = new UserTreeEntity();
			userUserEntity.setUserEntity(userEntity);
			userUserEntity.setAncestorEntity(userEntity);
			userUserEntity.setLevel(0);
			userUserEntity.setCreateBy("Consumer");
			userUserEntity.setUpdateBy("Consumer");
			userTreeDAO.addUserTree(userUserEntity);

			int level = 1;
			UserEntity ancestorEntity = userEntity.getSupervisorL1();
			while (ancestorEntity != null) {
				UserTreeEntity userTreeEntity = new UserTreeEntity();
				userTreeEntity.setUserEntity(userEntity);
				userTreeEntity.setAncestorEntity(ancestorEntity);
				userTreeEntity.setLevel(level++);
				userTreeEntity.setCreateBy("Consumer");
				userTreeEntity.setUpdateBy("Consumer");
				userTreeDAO.addUserTree(userTreeEntity);
				ancestorEntity = ancestorEntity.getSupervisorL1();
			}
		} catch (DAOException e) {
			logger.error(e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}
}
