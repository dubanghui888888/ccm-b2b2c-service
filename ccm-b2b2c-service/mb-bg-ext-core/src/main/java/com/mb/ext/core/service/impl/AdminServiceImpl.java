package com.mb.ext.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mb.ext.core.constant.AuthenticationConstants;
import com.mb.ext.core.constant.OrderConstants;
import com.mb.ext.core.constant.SettingConstants;
import com.mb.ext.core.context.UserContext;
import com.mb.ext.core.dao.AdminDAO;
import com.mb.ext.core.dao.AdminRoleDAO;
import com.mb.ext.core.dao.AdminTokenDAO;
import com.mb.ext.core.dao.RoleDAO;
import com.mb.ext.core.dao.SequenceDAO;
import com.mb.ext.core.dao.SettingDAO;
import com.mb.ext.core.dao.SwiperDAO;
import com.mb.ext.core.dao.UserAuthDAO;
import com.mb.ext.core.entity.AdminEntity;
import com.mb.ext.core.entity.AdminRoleEntity;
import com.mb.ext.core.entity.AdminTokenEntity;
import com.mb.ext.core.entity.RoleEntity;
import com.mb.ext.core.entity.SettingEntity;
import com.mb.ext.core.entity.SwiperEntity;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.AdminService;
import com.mb.ext.core.service.NoteService;
import com.mb.ext.core.service.UserService;
import com.mb.ext.core.service.spec.AdminDTO;
import com.mb.ext.core.service.spec.AdminRoleDTO;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.RoleDTO;
import com.mb.ext.core.service.spec.SwiperDTO;
import com.mb.ext.core.util.MailSenderUtil;
import com.mb.ext.core.util.SMSSenderUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.service.AbstractService;
import com.mb.framework.util.SecurityUtil;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;

@Service
@Qualifier("AdminService")
public class AdminServiceImpl extends AbstractService implements
		AdminService<BodyDTO> {
	@Autowired
	@Qualifier("adminDAO")
	private AdminDAO adminDAO;

	@Autowired
	@Qualifier("adminRoleDAO")
	private AdminRoleDAO adminRoleDAO;

	@Autowired
	@Qualifier("roleDAO")
	private RoleDAO roleDAO;

	@Autowired
	@Qualifier("sequenceDAO")
	private SequenceDAO sequenceDAO;

	@Autowired
	@Qualifier("adminTokenDAO")
	private AdminTokenDAO adminTokenDAO;

	@Autowired
	@Qualifier("userAuthDAO")
	private UserAuthDAO userAuthDAO;

	@Autowired
	@Qualifier("settingDAO")
	private SettingDAO settingDAO;
	
	@Autowired
	@Qualifier("swiperDAO")
	private SwiperDAO swiperDAO;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("NoteService")
	private NoteService noteService;

	@Autowired
	private MailSenderUtil mailSenderUtil;

	@Autowired
	private SMSSenderUtil smsSenderUtil;

	@Autowired
	private PropertyRepository propertyRepository;

	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	@Override
	public String login(AdminDTO adminDTO) throws BusinessException {
		String tokenNo;
		try {

			AdminEntity adminEntity = adminDAO.getAdminById(adminDTO.getId());
			if (adminEntity == null) {
				throw new BusinessException(
						BusinessErrorCode.LOGIN_ID_PASSWORD_INCORRECT);
			}

			// Check PLATFORM service is due or not, 改为在激活员工账户时, 访问各功能模块时检查
			/*
			 * ServiceDueDTO serviceDueDTO =
			 * buyService.getServiceDue(userEntity.getEntEntity().getId(),
			 * ServiceConstants.SERVICE_TYPE_PLATFORM); if(serviceDueDTO != null
			 * && serviceDueDTO.getDueDate().before(new Date())){ throw new
			 * BusinessException(BusinessErrorCode.ENT_PLATFORM_EXPIRED); }
			 */

			// if user locked, then stop here and throw exception
			if (adminEntity.isLocked()) {
				throw new BusinessException(BusinessErrorCode.LOGIN_ID_LOCKED);
			}
			// 2. Check whether password is valid
			String srcPassword = adminDTO.getPassword();
			String srcEncryptedPassword = SecurityUtil.encryptMd5(srcPassword);
			String descPassword = adminEntity.getPassword();
			if (!srcEncryptedPassword.equals(descPassword)) {
				// update failed count + 1

				int failedCount = adminEntity.getFailCount();
				String maxFailedCount = settingDAO.getSettingByName(
						SettingConstants.LOGIN_MAX_FAILED_COUNT).getValue();
				if (failedCount + 1 >= new Integer(maxFailedCount))
					adminEntity.setLocked(true);
				adminEntity.setFailCount(failedCount + 1);

				throw new BusinessException(
						BusinessErrorCode.LOGIN_ID_PASSWORD_INCORRECT);
			}

			// if already login, then stop here
			if (adminEntity.getAdminTokenEntity() != null) {
				// Token expired, then delete the expired token
				if (new Date().after(adminEntity.getAdminTokenEntity()
						.getExpireTime())) {
					AdminTokenEntity adminTokenEntity = adminEntity
							.getAdminTokenEntity();
					adminEntity.setAdminTokenEntity(null);
					adminTokenDAO.deleteAdminToken(adminTokenEntity);
				} else {
					// Considering success login
					int successCount = adminEntity.getSuccessCount();
					adminEntity.setSuccessCount(successCount + 1);
					adminDAO.updateAdmin(adminEntity);
					AdminDTO nAdminDTO = new AdminDTO();
					nAdminDTO.setId(adminEntity.getId());
					UserContext.set("ADMINPROFILE", nAdminDTO);
					return adminEntity.getAdminTokenEntity().getTokenId();
				}
			}
			// 3. Add record in UserToken table
			tokenNo = RandomStringUtils.randomAlphanumeric(32);
			AdminTokenEntity adminTokenEntity = new AdminTokenEntity();
			adminTokenEntity.setTokenId(tokenNo);
			Date loginTime = new Date();
			adminTokenEntity.setLoginTime(loginTime);
			adminTokenEntity.setCreateBy("Admin");
			adminTokenEntity.setUpdateBy("Admin");

			int loginExpireDuration = new Integer(settingDAO.getSettingByName(
					SettingConstants.LOGIN_EXPIRE_DURATION).getValue());
			Date expireTime = DateUtils.addMinutes(loginTime,
					loginExpireDuration);
			adminTokenEntity.setExpireTime(expireTime);
			adminTokenEntity.setAdminEntity(adminEntity);

			adminTokenDAO.addAdminToken(adminTokenEntity);

			// 4. Update success count
			int successCount = adminEntity.getSuccessCount();
			adminEntity.setSuccessCount(successCount + 1);
			adminDAO.updateAdmin(adminEntity);

			// 5. Set UserProfile to context
			AdminDTO nAdminDTO = new AdminDTO();
			nAdminDTO.setId(adminEntity.getId());
			UserContext.set("ADMINPROFILE", nAdminDTO);

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		return tokenNo;

	}

	@Override
	public void logout(AdminDTO adminDTO) throws BusinessException {

		try {
			AdminEntity adminEntity = adminDAO.getAdminById(adminDTO.getId());
			if (adminEntity != null) {
				AdminTokenEntity adminTokenEntity = adminEntity
						.getAdminTokenEntity();
				if (adminTokenEntity != null) {
					// to physically delete userToken, must clear the reference.
					// Otherwise, physically delete will fail
					adminEntity.setAdminTokenEntity(null);
					adminTokenDAO.deleteAdminToken(adminTokenEntity);
				}
			}

			UserContext.set("AdminProfile", null);
		} catch (DAOException e) {
			throw new BusinessException("SYSTEM_MESSAGE_0001", e);
		}

	}

	@Override
	public void resetPassword(AdminDTO adminDTO) throws BusinessException {
		try {
			AdminEntity adminEntity = adminDAO.getAdminById(adminDTO.getId());
			// String newPassword = RandomStringUtils.randomAlphanumeric(8);
			String newPassword = adminDTO.getNewPassword();
			adminEntity.setPassword(SecurityUtil.encryptMd5(newPassword));
			adminEntity.setLocked(false);
			adminEntity.setFailCount(0);
			adminDAO.updateAdmin(adminEntity);
			// send email and SMS to notify
			// noteService.sendNotification(userDTO, "resetpassword.success",
			// new String[]{newPassword});
			/*
			 * if(!StringUtil.isEmtpy(userEntity.getPersonalEmail())){ String
			 * emailSubject =
			 * propertyRepository.getProperty("mail.resetpassword.subject");
			 * String emailBody =
			 * propertyRepository.getProperty("mail.resetpassword.body"); String
			 * replacedEmailBody = emailBody.replace("{1}", newPassword); String
			 * sentTo = userEntity.getPersonalEmail();
			 * mailSenderUtil.sendMail(emailSubject, replacedEmailBody, sentTo,
			 * "", null); }
			 * if(!StringUtil.isEmtpy(userEntity.getPersonalPhone())){ String
			 * smsBody =
			 * propertyRepository.getProperty("sms.resetpassword.body"); String
			 * replacedSMSBody = smsBody.replace("{1}", newPassword); String
			 * countryCode = userEntity.getPersonalPhoneCountryCode(); String
			 * mobileNo = userEntity.getPersonalPhone();
			 * smsSenderUtil.sendSMS(replacedSMSBody, countryCode, mobileNo); }
			 */

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}

	}

	@Override
	public void changePassword(AdminDTO adminDTO) throws BusinessException {
		try {
			AdminEntity adminEntity = adminDAO.getAdminById(adminDTO.getId());
			if (adminEntity == null) {
				throw new BusinessException(
						BusinessErrorCode.LOGIN_ID_PASSWORD_INCORRECT);
			}
			String descPassword = adminEntity.getPassword();
			String srcPassword = adminDTO.getPassword();
			String srcEncryptedPassword = SecurityUtil.encryptMd5(srcPassword);
			if (!srcEncryptedPassword.equals(descPassword)) {
				throw new BusinessException(
						BusinessErrorCode.LOGIN_ID_PASSWORD_INCORRECT);
			}

			String newPassword = adminDTO.getNewPassword();
			adminEntity.setPassword(SecurityUtil.encryptMd5(newPassword));
			adminEntity.setLocked(false);
			adminEntity.setFailCount(0);
			adminDAO.updateAdmin(adminEntity);

		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}

	}

	@Override
	public void logout(String tokenId) throws BusinessException {
		try {
			AdminTokenEntity adminTokenEntity = adminTokenDAO
					.findByTokenId(tokenId);
			if (adminTokenEntity != null) {
				adminTokenDAO.deleteAdminToken(adminTokenEntity);
			}
			UserContext.set("AdminProfile", null);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}

	}

	@Override
	public void createAdmin(AdminDTO adminDTO) throws BusinessException {
		try {
			String id = adminDTO.getId();
			AdminEntity adminEntity = adminDAO.getAdminById(id);
			if (adminEntity != null) {
				throw new BusinessException(BusinessErrorCode.USER_DUPLICATE);
			}
			adminEntity = new AdminEntity();
			adminEntity.setId(id);
			adminEntity.setPassword(SecurityUtil.encryptMd5(AuthenticationConstants.DEFAULT_ADMIN_PASSWORD));
			adminEntity.setEmail(adminDTO.getEmail());
			adminEntity.setMobileNo(adminDTO.getMobileNo());
			adminEntity.setLocked(false);
			adminEntity.setFailCount(0);
			adminEntity.setSuccessCount(0);
			adminEntity.setCreateBy("Admin");
			adminEntity.setUpdateBy("Admin");
			adminDAO.addAdmin(adminEntity);

		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}

	}

	@Override
	public void deleteAdmin(AdminDTO adminDTO) throws BusinessException {
		try {
			String id = adminDTO.getId();
			AdminEntity adminEntity = adminDAO.getAdminById(id);
			if (adminEntity != null) {
				AdminTokenEntity tokenEntity = adminTokenDAO.findByAdminId(id);
				if (tokenEntity != null) {
					adminTokenDAO.deleteAdminToken(tokenEntity);
				}
				adminDAO.deleteAdmin(adminEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}

	}

	@Override
	public void updateAdmin(AdminDTO adminDTO) throws BusinessException {
		try {
			String id = adminDTO.getId();
			AdminEntity adminEntity = adminDAO.getAdminByUuid(adminDTO
					.getAdminUuid());
			if (adminEntity != null) {
				adminEntity.setId(id);
				adminEntity.setEmail(adminDTO.getEmail());
				adminEntity.setMobileNo(adminDTO.getMobileNo());
				adminDAO.updateAdmin(adminEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}

	}

	@Override
	public void enableAdmin(AdminDTO adminDTO) throws BusinessException {
		try {
			String id = adminDTO.getId();
			AdminEntity adminEntity = adminDAO.getAdminById(id);
			if (adminEntity != null) {
				adminEntity.setActive(true);
				adminEntity.setLocked(false);
				adminEntity.setFailCount(0);
				adminEntity.setSuccessCount(0);
				adminDAO.updateAdmin(adminEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}

	}

	@Override
	public void disableAdmin(AdminDTO adminDTO) throws BusinessException {
		try {
			String id = adminDTO.getId();
			AdminEntity adminEntity = adminDAO.getAdminById(id);
			if (adminEntity != null) {
				adminEntity.setActive(false);
				adminDAO.updateAdmin(adminEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}

	}

	@Override
	public void unlockAdmin(AdminDTO adminDTO) throws BusinessException {
		try {
			String id = adminDTO.getId();
			AdminEntity adminEntity = adminDAO.getAdminById(id);
			if (adminEntity != null) {
				adminEntity.setActive(true);
				adminEntity.setLocked(false);
				adminEntity.setFailCount(0);
				adminEntity.setSuccessCount(0);
				adminDAO.updateAdmin(adminEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}

	}

	@Override
	public void lockAdmin(AdminDTO adminDTO) throws BusinessException {
		try {
			String id = adminDTO.getId();
			AdminEntity adminEntity = adminDAO.getAdminById(id);
			if (adminEntity != null) {
				adminEntity.setLocked(true);
				adminDAO.updateAdmin(adminEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}

	}

	@Override
	public AdminDTO getAdminById(String id) throws BusinessException {
		AdminDTO adminDTO = null;
		try {
			AdminEntity adminEntity = adminDAO.getAdminById(id);
			if (adminEntity != null) {
				adminDTO = new AdminDTO();
				entity2DTO(adminEntity, adminDTO);

			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		return adminDTO;

	}

	@Override
	public List<AdminDTO> getAdmins() throws BusinessException {
		List<AdminDTO> adminDTOList = new ArrayList<AdminDTO>();
		;
		try {
			List<AdminEntity> adminEntityList = adminDAO.getAdmins();
			if (adminEntityList != null && adminEntityList.size() > 0) {
				for (Iterator<AdminEntity> iter = adminEntityList.iterator(); iter
						.hasNext();) {
					AdminEntity adminEntity = iter.next();
					AdminDTO adminDTO = new AdminDTO();
					entity2DTO(adminEntity, adminDTO);
					adminDTOList.add(adminDTO);
				}
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		return adminDTOList;
	}

	@Override
	public void changeAdminRole(AdminDTO adminDTO) throws BusinessException {
		try {
			String id = adminDTO.getId();
			AdminEntity adminEntity = adminDAO.getAdminById(id);
			if (adminEntity != null) {
				List<AdminRoleEntity> adminRoleEntityList = adminEntity
						.getAdminRoleList();
				adminEntity.setAdminRoleList(null);
				for (Iterator<AdminRoleEntity> iter = adminRoleEntityList
						.iterator(); iter.hasNext();) {
					adminRoleDAO.deleteAdminRole(iter.next());
				}

				String loginId = getAdminProfile().getId();
				
				List<AdminRoleDTO> adminRoleDTOList = adminDTO
						.getAdminRoleList();
				for (Iterator<AdminRoleDTO> iter = adminRoleDTOList.iterator(); iter
						.hasNext();) {
					AdminRoleDTO adminRoleDTO = iter.next();
					AdminRoleEntity adminRoleEntity = new AdminRoleEntity();
					adminRoleEntity.setAdminEntity(adminEntity);
					RoleDTO roleDTO = adminRoleDTO.getRoleDTO();
					RoleEntity roleEntity = roleDAO.getRoleByName(roleDTO
							.getName());
					adminRoleEntity.setRoleEntity(roleEntity);
					adminRoleEntity.setUpdateBy(loginId);
					adminRoleEntity.setCreateBy(loginId);
					adminRoleDAO.addAdminRole(adminRoleEntity);
				}
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}

	}

	@Override
	public AdminDTO getAdminDTOByTokenId(String tokenId)
			throws BusinessException {
		AdminDTO adminDTO = new AdminDTO();
		try {
			AdminEntity adminEntity = adminDAO.getAdminByTokenId(tokenId);
			entity2DTO(adminEntity, adminDTO);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		return adminDTO;
	}

	private void entity2DTO(AdminEntity adminEntity, AdminDTO adminDTO) {

		if (adminEntity != null && adminDTO != null) {
			adminDTO.setAdminUuid(adminEntity.getAdminUuid());
			adminDTO.setId(adminEntity.getId());
			adminDTO.setEmail(adminEntity.getEmail());
			adminDTO.setMobileNo(adminEntity.getMobileNo());
			adminDTO.setActive(adminEntity.isActive());
			adminDTO.setLocked(adminEntity.isLocked());
			adminDTO.setFailCount(adminEntity.getFailCount());
			adminDTO.setSuccessCount(adminEntity.getSuccessCount());

			List<AdminRoleDTO> adminRoleDTOList = new ArrayList<AdminRoleDTO>();
			List<AdminRoleEntity> adminRoleEntityList = adminEntity
					.getAdminRoleList();
			if (adminRoleEntityList.size() > 0) {
				for (Iterator<AdminRoleEntity> iter = adminRoleEntityList
						.iterator(); iter.hasNext();) {
					AdminRoleEntity adminRoleEntity = iter.next();
					RoleEntity roleEntity = adminRoleEntity.getRoleEntity();
					RoleDTO roleDTO = new RoleDTO();
					roleDTO.setName(roleEntity.getName());
					roleDTO.setDesc(roleEntity.getDesc());
					AdminRoleDTO adminRoleDTO = new AdminRoleDTO();
					adminRoleDTO.setRoleDTO(roleDTO);
					adminRoleDTOList.add(adminRoleDTO);
				}
				adminDTO.setAdminRoleList(adminRoleDTOList);
			}
		}

	}

	@Override
	public void setAdminProfile(AdminDTO adminDTO) throws BusinessException {
		
		UserContext.set("ADMINPROFILE", adminDTO);
		
	}

	@Override
	public AdminDTO getAdminProfile() throws BusinessException {
		return (AdminDTO) UserContext.get("ADMINPROFILE");
	}
}
