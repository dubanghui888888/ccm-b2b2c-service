package com.mb.ext.core.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mb.ext.core.dao.merchant.PlatformAccountDAO;
import com.mb.ext.core.entity.merchant.PlatformAccountEntity;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.PlatformService;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.merchant.PlatformAccountDTO;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.service.AbstractService;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;

@Service
@Qualifier("PlatformService")
public class PlatformServiceImpl extends AbstractService implements
		PlatformService<BodyDTO> {
	@Autowired
	@Qualifier("platformAccountDAO")
	private PlatformAccountDAO platformAccountDAO;

	@Autowired
	private PropertyRepository propertyRepository;

	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	@Override
	public void addPlatformAccount(PlatformAccountDTO accountDTO) throws BusinessException {
		
		try {
				PlatformAccountEntity accountEntity = new PlatformAccountEntity();
				accountEntity.setAccountType(accountDTO.getAccountType());
				accountEntity.setAlipayId(accountDTO.getAlipayId());
				accountEntity.setAlipayQrCode(accountDTO.getAlipayQrCode());
				accountEntity.setBankAccountName(accountDTO.getBankAccountName());
				accountEntity.setBankAccountNo(accountDTO.getBankAccountNo());
				accountEntity.setBankCode(accountDTO.getBankCode());
				accountEntity.setBankName(accountDTO.getBankName());
				accountEntity.setBranchName(accountDTO.getBranchName());
				accountEntity.setWechatId(accountDTO.getWechatId());
				accountEntity.setWechatQrCode(accountDTO.getWechatQrCode());
				platformAccountDAO.createPlatformAccount(accountEntity);
		} catch (DAOException e) {
			logger.error(e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		
	}
	
	@Override
	public void updatePlatformAccount(PlatformAccountDTO accountDTO) throws BusinessException {
		
		try {
			PlatformAccountEntity accountEntity = platformAccountDAO.getAccountByUuid(accountDTO.getPlatformAccountUuid());
			accountEntity.setAccountType(accountDTO.getAccountType());
			accountEntity.setAlipayId(accountDTO.getAlipayId());
			accountEntity.setAlipayQrCode(accountDTO.getAlipayQrCode());
			accountEntity.setBankAccountName(accountDTO.getBankAccountName());
			accountEntity.setBankAccountNo(accountDTO.getBankAccountNo());
			accountEntity.setBankCode(accountDTO.getBankCode());
			accountEntity.setBankName(accountDTO.getBankName());
			accountEntity.setBranchName(accountDTO.getBranchName());
			accountEntity.setWechatId(accountDTO.getWechatId());
			accountEntity.setWechatQrCode(accountDTO.getWechatQrCode());
			platformAccountDAO.updatePlatformAccount(accountEntity);
		} catch (DAOException e) {
			logger.error(e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		
	}
	
	@Override
	public void deletePlatformAccount(PlatformAccountDTO accountDTO) throws BusinessException {
		
		try {
			PlatformAccountEntity accountEntity = platformAccountDAO.getAccountByUuid(accountDTO.getPlatformAccountUuid());
			platformAccountDAO.deletePlatformAccount(accountEntity);
		} catch (DAOException e) {
			logger.error(e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		
	}

	@Override
	public List<PlatformAccountDTO> inquiryPlatformAccount() throws BusinessException {
		
		List<PlatformAccountDTO> accountDTOList = new ArrayList<PlatformAccountDTO>();;
		try {
			List<PlatformAccountEntity> accountEntityList = platformAccountDAO.getPlatformAccount();
			for (Iterator<PlatformAccountEntity> iterator = accountEntityList.iterator(); iterator.hasNext();) {
				PlatformAccountEntity accountEntity = (PlatformAccountEntity) iterator.next();
				PlatformAccountDTO accountDTO = new PlatformAccountDTO();
				accountDTO.setPlatformAccountUuid(accountEntity.getPlatformAccountUuid());
				accountDTO.setAccountType(accountEntity.getAccountType());
				accountDTO.setAlipayId(accountEntity.getAlipayId());
				accountDTO.setAlipayQrCode(accountEntity.getAlipayQrCode());
				accountDTO.setBankAccountName(accountEntity.getBankAccountName());
				accountDTO.setBankAccountNo(accountEntity.getBankAccountNo());
				accountDTO.setBankCode(accountEntity.getBankCode());
				accountDTO.setBankName(accountEntity.getBankName());
				accountDTO.setBranchName(accountEntity.getBranchName());
				accountDTO.setWechatId(accountEntity.getWechatId());
				accountDTO.setWechatQrCode(accountEntity.getWechatQrCode());
				accountDTOList.add(accountDTO);
			}
		}catch (DAOException e) {
			logger.error(e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		return accountDTOList;
	}
	
	@Override
	public PlatformAccountDTO inquiryPlatformAccountByUid(String uid) throws BusinessException {
		
		try {
			PlatformAccountEntity accountEntity = platformAccountDAO.getAccountByUuid(uid);
			PlatformAccountDTO accountDTO = new PlatformAccountDTO();
			accountDTO.setPlatformAccountUuid(accountEntity.getPlatformAccountUuid());
			accountDTO.setAccountType(accountEntity.getAccountType());
			accountDTO.setAlipayId(accountEntity.getAlipayId());
			accountDTO.setAlipayQrCode(accountEntity.getAlipayQrCode());
			accountDTO.setBankAccountName(accountEntity.getBankAccountName());
			accountDTO.setBankAccountNo(accountEntity.getBankAccountNo());
			accountDTO.setBankCode(accountEntity.getBankCode());
			accountDTO.setBankName(accountEntity.getBankName());
			accountDTO.setBranchName(accountEntity.getBranchName());
			accountDTO.setWechatId(accountEntity.getWechatId());
			accountDTO.setWechatQrCode(accountEntity.getWechatQrCode());
			return accountDTO;
		}catch (DAOException e) {
			logger.error(e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}
}
