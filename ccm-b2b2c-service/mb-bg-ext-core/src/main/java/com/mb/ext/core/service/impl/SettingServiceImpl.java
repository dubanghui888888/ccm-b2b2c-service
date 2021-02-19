
package com.mb.ext.core.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mb.ext.core.dao.GlobalSettingDAO;
import com.mb.ext.core.dao.MerchantDAO;
import com.mb.ext.core.dao.PrintSettingDAO;
import com.mb.ext.core.dao.SequenceDAO;
import com.mb.ext.core.dao.SettingDAO;
import com.mb.ext.core.dao.UserDAO;
import com.mb.ext.core.dao.point.PointSettingDAO;
import com.mb.ext.core.dao.point.SignSettingDAO;
import com.mb.ext.core.entity.GlobalSettingEntity;
import com.mb.ext.core.entity.PrintSettingEntity;
import com.mb.ext.core.entity.SettingEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.point.PointSettingEntity;
import com.mb.ext.core.entity.point.SignSettingEntity;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.SettingService;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.GlobalSettingDTO;
import com.mb.ext.core.service.spec.PrintSettingDTO;
import com.mb.ext.core.service.spec.SettingDTO;
import com.mb.ext.core.service.spec.SettingDTOList;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.point.PointSettingDTO;
import com.mb.ext.core.service.spec.point.SignSettingDTO;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.service.AbstractService;
import com.mb.framework.util.log.LogHelper;


@Service
@Qualifier("settingService")
public class SettingServiceImpl extends AbstractService implements SettingService<BodyDTO>
{
	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;
	
	@Autowired
	@Qualifier("merchantDAO")
	private MerchantDAO merchantDAO;
	
	@Autowired
	@Qualifier("sequenceDAO")
	private SequenceDAO sequenceDAO;
	
	@Autowired
	@Qualifier("settingDAO")
	private SettingDAO settingDAO;
	
	@Autowired
	@Qualifier("globalSettingDAO")
	private GlobalSettingDAO globalSettingDAO;
	
	@Autowired
	@Qualifier("printSettingDAO")
	private PrintSettingDAO printSettingDAO;
	
	@Autowired
	@Qualifier("signSettingDAO")
	private SignSettingDAO signSettingDAO;

	@Autowired
	@Qualifier("pointSettingDAO")
	private PointSettingDAO pointSettingDAO;
	
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	
	private void DTO2Entity(SettingDTO settingDTO, SettingEntity settingEntity){
		if(settingDTO != null && settingEntity != null){
			settingEntity.setCreateBy(settingDTO.getCreateBy());
			settingEntity.setUpdateBy(settingDTO.getUpdateBy());
			settingEntity.setName(settingDTO.getName());
			settingEntity.setValue(settingDTO.getValue());
		}
	}
	
	private void Entity2DTO(SettingEntity settingEntity, SettingDTO settingDTO){
		if(settingDTO != null && settingEntity != null){
			settingDTO.setCreateBy(settingEntity.getCreateBy());
			settingDTO.setUpdateBy(settingEntity.getUpdateBy());
			settingDTO.setName(settingEntity.getName());
			settingDTO.setValue(settingEntity.getValue());
			settingDTO.setDescription(settingEntity.getDescription());
		}
	}


	@Override
	public void addSetting(String name, String value) throws BusinessException {
		
		SettingEntity settingEntity = new SettingEntity();
		settingEntity.setName(name);
		settingEntity.setValue(value);
		try{
			settingDAO.addSetting(settingEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
		
	}

	@Override
	public void updateSetting(String name, String value)
			throws BusinessException {
		
		try {
			SettingEntity settingEntity = settingDAO.getSettingByName(name);
			if(settingEntity == null){
				settingEntity = new SettingEntity();
				settingEntity.setName(name);
				settingEntity.setValue(value);
				settingDAO.addSetting(settingEntity);
			}else{
				settingEntity.setValue(value);
				settingDAO.updateSetting(settingEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}

	@Override
	public SettingDTO getSettingByName(String name) throws BusinessException {
		
		SettingDTO settingDTO = new SettingDTO();
		try {
			SettingEntity settingEntity = settingDAO.getSettingByName(name);
			if(settingEntity == null){
				return null;
			}
			Entity2DTO(settingEntity, settingDTO);
			
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return settingDTO;
	}

	@Override
	public List<SettingDTO> getSettings() throws BusinessException {
		
		List<SettingDTO> settingDTOList = new ArrayList<SettingDTO>();
		try{
			List<SettingEntity> settingEntityList =  settingDAO.getSettings();
			for (Iterator<SettingEntity> iter = settingEntityList.iterator(); iter.hasNext();) {
	           SettingEntity settingEntity = iter.next();
	           SettingDTO settingDTO = new SettingDTO();
	           Entity2DTO(settingEntity, settingDTO);
	           settingDTOList.add(settingDTO);
	        }
		}catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return settingDTOList;
		
	}

	@Override
	public void updateSettingList(SettingDTOList settingDTOList)
			throws BusinessException {
		List<SettingDTO> settings = settingDTOList.getSettings();
		for(Iterator<SettingDTO> iter = settings.iterator();iter.hasNext();){
			SettingDTO settingDTO = iter.next();
			updateSetting(settingDTO.getName(), settingDTO.getValue());
		}
		
	}

	@Override
	public List<SettingDTO> getSysSettings() throws BusinessException {
		List<SettingDTO> settingDTOList = new ArrayList<SettingDTO>();
		try{
			List<SettingEntity> settingEntityList =  settingDAO.getSettings();
			for (Iterator<SettingEntity> iter = settingEntityList.iterator(); iter.hasNext();) {
	           SettingEntity settingEntity = iter.next();
//		           if(SettingConstants.LOGIN_EXPIRE_DURATION.equals(settingEntity.getName())||
//		        	  SettingConstants.LOGIN_MAX_FAILED_COUNT.equals(settingEntity.getName())){
		           SettingDTO settingDTO = new SettingDTO();
		           Entity2DTO(settingEntity, settingDTO);
		           settingDTOList.add(settingDTO);
//	           }
	        }
		}catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return settingDTOList;
	}

	@Override
	public GlobalSettingDTO getGlobalApplicationSetting() throws BusinessException {
		try {
			GlobalSettingEntity applicationSettingEntity = globalSettingDAO.getGlobalSetting();
			if(applicationSettingEntity == null) {
				applicationSettingEntity = new GlobalSettingEntity();
				globalSettingDAO.addGlobalSetting(applicationSettingEntity);
			}
			GlobalSettingDTO applicationSettingDTO = new GlobalSettingDTO();
			applicationSettingDTO.setApplicationLogo(applicationSettingEntity.getApplicationLogo());
			applicationSettingDTO.setApplicationName(applicationSettingEntity.getApplicationName());
			applicationSettingDTO.setApplicationDesc(applicationSettingEntity.getApplicationDesc());
			applicationSettingDTO.setApplicationDomainName(applicationSettingEntity.getApplicationDomainName());
			applicationSettingDTO.setApplicationVersion(applicationSettingEntity.getApplicationVersion());
			applicationSettingDTO.setApplicationPublicRegisterEnabled(applicationSettingEntity.isApplicationPublicRegisterEnabled());
			applicationSettingDTO.setApplicationMerchantEnabled(applicationSettingEntity.isApplicationMerchantEnabled());
			applicationSettingDTO.setApplicationProductEnabled(applicationSettingEntity.isApplicationProductEnabled());
			applicationSettingDTO.setApplicationCityEnabled(applicationSettingEntity.isApplicationCityEnabled());
			applicationSettingDTO.setApplicationDeliveryCityEnabled(applicationSettingEntity.isApplicationDeliveryCityEnabled());
			applicationSettingDTO.setApplicationDeliveryExpressEnabled(applicationSettingEntity.isApplicationDeliveryExpressEnabled());
			applicationSettingDTO.setApplicationDeliveryPickEnabled(applicationSettingEntity.isApplicationDeliveryPickEnabled());
			return applicationSettingDTO;
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}

	@Override
	public void updateGlobalApplicationSetting(GlobalSettingDTO applicationSettingDTO) throws BusinessException {
		
		try {
			GlobalSettingEntity applicationSettingEntity = globalSettingDAO.getGlobalSetting();
			if(applicationSettingEntity != null) {
				applicationSettingEntity.setApplicationLogo(applicationSettingDTO.getApplicationLogo());
				applicationSettingEntity.setApplicationName(applicationSettingDTO.getApplicationName());
				applicationSettingEntity.setApplicationDesc(applicationSettingDTO.getApplicationDesc());
				applicationSettingEntity.setApplicationDomainName(applicationSettingDTO.getApplicationDomainName());
				applicationSettingEntity.setApplicationVersion(applicationSettingDTO.getApplicationVersion());
				applicationSettingEntity.setApplicationPublicRegisterEnabled(applicationSettingDTO.isApplicationPublicRegisterEnabled());
				applicationSettingEntity.setApplicationMerchantEnabled(applicationSettingDTO.isApplicationMerchantEnabled());
				applicationSettingEntity.setApplicationProductEnabled(applicationSettingDTO.isApplicationProductEnabled());
				applicationSettingEntity.setApplicationCityEnabled(applicationSettingDTO.isApplicationCityEnabled());
				applicationSettingEntity.setApplicationDeliveryCityEnabled(applicationSettingDTO.isApplicationDeliveryCityEnabled());
				applicationSettingEntity.setApplicationDeliveryExpressEnabled(applicationSettingDTO.isApplicationDeliveryExpressEnabled());
				applicationSettingEntity.setApplicationDeliveryPickEnabled(applicationSettingDTO.isApplicationDeliveryPickEnabled());
				globalSettingDAO.updateGlobalSetting(applicationSettingEntity);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public GlobalSettingDTO getGlobalWechatSetting() throws BusinessException {
		
		try {
			GlobalSettingEntity applicationSettingEntity = globalSettingDAO.getGlobalSetting();
			if(applicationSettingEntity == null) {
				applicationSettingEntity = new GlobalSettingEntity();
				globalSettingDAO.addGlobalSetting(applicationSettingEntity);
			}
			GlobalSettingDTO applicationSettingDTO = new GlobalSettingDTO();
			applicationSettingDTO.setWechatApiKey(applicationSettingEntity.getWechatApiKey());
			applicationSettingDTO.setWechatAppIdMiniProgram(applicationSettingEntity.getWechatAppIdMiniProgram());
			applicationSettingDTO.setWechatAppSecretMiniProgram(applicationSettingEntity.getWechatAppSecretMiniProgram());
			applicationSettingDTO.setWechatAppIdOfficialAccount(applicationSettingEntity.getWechatAppIdOfficialAccount());
			applicationSettingDTO.setWechatAppSecretOfficialAccount(applicationSettingEntity.getWechatAppSecretOfficialAccount());
			applicationSettingDTO.setWechatAppIdOpen(applicationSettingEntity.getWechatAppIdOpen());
			applicationSettingDTO.setWechatAppSecretOpen(applicationSettingEntity.getWechatAppSecretOpen());
			applicationSettingDTO.setWechatMerchantId(applicationSettingEntity.getWechatMerchantId());
			applicationSettingDTO.setWechatNotifyUrl(applicationSettingEntity.getWechatNotifyUrl());
			applicationSettingDTO.setWechatRefundNotifyUrl(applicationSettingEntity.getWechatRefundNotifyUrl());
			return applicationSettingDTO;
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public void updateGlobalWechatSetting(GlobalSettingDTO wechatSettingDTO) throws BusinessException {
		
		try {
			GlobalSettingEntity wechatSettingEntity = globalSettingDAO.getGlobalSetting();
			if(wechatSettingEntity != null) {
				wechatSettingEntity.setWechatApiKey(wechatSettingDTO.getWechatApiKey());
				wechatSettingEntity.setWechatAppIdMiniProgram(wechatSettingDTO.getWechatAppIdMiniProgram());
				wechatSettingEntity.setWechatAppSecretMiniProgram(wechatSettingDTO.getWechatAppSecretMiniProgram());
				wechatSettingEntity.setWechatAppIdOfficialAccount(wechatSettingDTO.getWechatAppIdOfficialAccount());
				wechatSettingEntity.setWechatAppSecretOfficialAccount(wechatSettingDTO.getWechatAppSecretOfficialAccount());
				wechatSettingEntity.setWechatAppIdOpen(wechatSettingDTO.getWechatAppIdOpen());
				wechatSettingEntity.setWechatAppSecretOpen(wechatSettingDTO.getWechatAppSecretOpen());
				wechatSettingEntity.setWechatMerchantId(wechatSettingDTO.getWechatMerchantId());
				wechatSettingEntity.setWechatNotifyUrl(wechatSettingDTO.getWechatNotifyUrl());
				wechatSettingEntity.setWechatRefundNotifyUrl(wechatSettingDTO.getWechatRefundNotifyUrl());
				globalSettingDAO.updateGlobalSetting(wechatSettingEntity);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
		
	}

	@Override
	public GlobalSettingDTO getGlobalAlipaySetting() throws BusinessException {
		
		try {
			GlobalSettingEntity alipaySettingEntity = globalSettingDAO.getGlobalSetting();
			if(alipaySettingEntity == null) {
				alipaySettingEntity = new GlobalSettingEntity();
				globalSettingDAO.addGlobalSetting(alipaySettingEntity);
			}
			GlobalSettingDTO alipaySettingDTO = new GlobalSettingDTO();
			alipaySettingDTO.setAlipayAppId(alipaySettingEntity.getAlipayAppId());
			alipaySettingDTO.setAlipayNotifyUrl(alipaySettingEntity.getAlipayNotifyUrl());
			alipaySettingDTO.setAlipayPrivateKey(alipaySettingEntity.getAlipayPrivateKey());
			alipaySettingDTO.setAlipayPublicKey(alipaySettingEntity.getAlipayPublicKey());
			alipaySettingDTO.setAlipayRefundNotifyUrl(alipaySettingEntity.getAlipayRefundNotifyUrl());
			alipaySettingDTO.setAlipayReturnUrl(alipaySettingEntity.getAlipayReturnUrl());
			return alipaySettingDTO;
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}

	@Override
	public void updateGlobalAlipaySetting(GlobalSettingDTO alipaySettingDTO) throws BusinessException {
		
		try {
			GlobalSettingEntity alipaySettingEntity = globalSettingDAO.getGlobalSetting();
			if(alipaySettingEntity != null) {
				alipaySettingEntity.setAlipayAppId(alipaySettingDTO.getAlipayAppId());
				alipaySettingEntity.setAlipayNotifyUrl(alipaySettingDTO.getAlipayNotifyUrl());
				alipaySettingEntity.setAlipayPrivateKey(alipaySettingDTO.getAlipayPrivateKey());
				alipaySettingEntity.setAlipayPublicKey(alipaySettingDTO.getAlipayPublicKey());
				alipaySettingEntity.setAlipayRefundNotifyUrl(alipaySettingDTO.getAlipayRefundNotifyUrl());
				alipaySettingEntity.setAlipayReturnUrl(alipaySettingDTO.getAlipayReturnUrl());
				globalSettingDAO.updateGlobalSetting(alipaySettingEntity);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public GlobalSettingDTO getGlobalOssSetting() throws BusinessException {
		
		try {
			GlobalSettingEntity ossSettingEntity = globalSettingDAO.getGlobalSetting();
			if(ossSettingEntity == null) {
				ossSettingEntity = new GlobalSettingEntity();
				globalSettingDAO.addGlobalSetting(ossSettingEntity);
			}
			GlobalSettingDTO ossSettingDTO = new GlobalSettingDTO();
			ossSettingDTO.setOssAccessId(ossSettingEntity.getOssAccessId());
			ossSettingDTO.setOssAccessKey(ossSettingEntity.getOssAccessKey());
			ossSettingDTO.setOssBucketName(ossSettingEntity.getOssBucketName());
			ossSettingDTO.setOssEndPoint(ossSettingEntity.getOssEndPoint());
			return ossSettingDTO;
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}

	@Override
	public void updateGlobalOssSetting(GlobalSettingDTO ossSettingDTO) throws BusinessException {
		
		try {
			GlobalSettingEntity ossSettingEntity = globalSettingDAO.getGlobalSetting();
			if(ossSettingEntity != null) {
				ossSettingEntity.setOssAccessId(ossSettingDTO.getOssAccessId());
				ossSettingEntity.setOssAccessKey(ossSettingDTO.getOssAccessKey());
				ossSettingEntity.setOssBucketName(ossSettingDTO.getOssBucketName());
				ossSettingEntity.setOssEndPoint(ossSettingDTO.getOssEndPoint());
				globalSettingDAO.updateGlobalSetting(ossSettingEntity);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public GlobalSettingDTO getGlobalSmsSetting() throws BusinessException {
		
		try {
			GlobalSettingEntity ossSettingEntity = globalSettingDAO.getGlobalSetting();
			if(ossSettingEntity == null) {
				ossSettingEntity = new GlobalSettingEntity();
				globalSettingDAO.addGlobalSetting(ossSettingEntity);
			}
			GlobalSettingDTO smsSettingDTO = new GlobalSettingDTO();
			smsSettingDTO.setSmsAccessId(ossSettingEntity.getSmsAccessId());
			smsSettingDTO.setSmsAccessKey(ossSettingEntity.getSmsAccessKey());
			smsSettingDTO.setSmsSignName(ossSettingEntity.getSmsSignName());
			smsSettingDTO.setSmsTemplateCode(ossSettingEntity.getSmsTemplateCode());
			smsSettingDTO.setSmsTemplateCodeApplicationApproved(ossSettingEntity.getSmsTemplateCodeApplicationApproved());
			smsSettingDTO.setSmsTemplateCodeApplicationRejected(ossSettingEntity.getSmsTemplateCodeApplicationRejected());
			return smsSettingDTO;
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}

	@Override
	public void updateGlobalSmsSetting(GlobalSettingDTO smsSettingDTO) throws BusinessException {
		
		try {
			GlobalSettingEntity smsSettingEntity = globalSettingDAO.getGlobalSetting();
			if(smsSettingEntity != null) {
				smsSettingEntity.setSmsAccessId(smsSettingDTO.getSmsAccessId());
				smsSettingEntity.setSmsAccessKey(smsSettingDTO.getSmsAccessKey());
				smsSettingEntity.setSmsSignName(smsSettingDTO.getSmsSignName());
				smsSettingEntity.setSmsTemplateCode(smsSettingDTO.getSmsTemplateCode());
				smsSettingEntity.setSmsTemplateCodeApplicationApproved(smsSettingDTO.getSmsTemplateCodeApplicationApproved());
				smsSettingEntity.setSmsTemplateCodeApplicationRejected(smsSettingDTO.getSmsTemplateCodeApplicationRejected());
				globalSettingDAO.updateGlobalSetting(smsSettingEntity);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public GlobalSettingDTO getGlobalMapSetting() throws BusinessException {
		try {
			GlobalSettingEntity mapSettingEntity = globalSettingDAO.getGlobalSetting();
			if(mapSettingEntity == null) {
				mapSettingEntity = new GlobalSettingEntity();
				globalSettingDAO.addGlobalSetting(mapSettingEntity);
			}
			GlobalSettingDTO mapSettingDTO = new GlobalSettingDTO();
			mapSettingDTO.setAmapWebJsapiKey(mapSettingEntity.getAmapWebJsapiKey());
			mapSettingDTO.setAmapWebServiceKey(mapSettingEntity.getAmapWebServiceKey());
			return mapSettingDTO;
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	@Override
	public void updateGlobalMapSetting(GlobalSettingDTO mapSettingDTO) throws BusinessException {
		try {
			GlobalSettingEntity mapSettingEntity = globalSettingDAO.getGlobalSetting();
			if(mapSettingEntity != null) {
				mapSettingEntity.setAmapWebJsapiKey(mapSettingDTO.getAmapWebJsapiKey());
				mapSettingEntity.setAmapWebServiceKey(mapSettingDTO.getAmapWebServiceKey());
				globalSettingDAO.updateGlobalSetting(mapSettingEntity);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	@Override
	public PrintSettingDTO getPrintSetting(MerchantDTO merchantDTO) throws BusinessException {
		
		try {
			PrintSettingEntity printSettingEntity = null;
			MerchantEntity merchantEntity = null;
			if(merchantDTO == null || StringUtils.isEmpty(merchantDTO.getMerchantUuid())) {
				printSettingEntity = printSettingDAO.getPrintSetting();
			}else {
				merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
				printSettingEntity = printSettingDAO.getPrintSettingByMerchant(merchantEntity);
			}
			if(printSettingEntity == null) {
				printSettingEntity = new PrintSettingEntity();
				printSettingEntity.setMerchantEntity(merchantEntity);
				printSettingDAO.addPrintSetting(printSettingEntity);
			}
			PrintSettingDTO printSettingDTO = new PrintSettingDTO();
			printSettingDTO.setFeieUser(printSettingEntity.getFeieUser());
			printSettingDTO.setFeieUkey(printSettingEntity.getFeieUkey());
			printSettingDTO.setFeieSn(printSettingEntity.getFeieSn());
			printSettingDTO.setFeieKey(printSettingEntity.getFeieKey());
			printSettingDTO.setPrintSettingUuid(printSettingEntity.getPrintSettingUuid());
			return printSettingDTO;
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}

	@Override
	public void updatePrintSetting(PrintSettingDTO printSettingDTO) throws BusinessException {
		
		try {
			PrintSettingEntity printSettingEntity = printSettingDAO.getPrintSettingByUuid(printSettingDTO.getPrintSettingUuid());
			if(printSettingEntity != null) {
				printSettingEntity.setFeieUser(printSettingDTO.getFeieUser());
				printSettingEntity.setFeieUkey(printSettingDTO.getFeieUkey());
				printSettingEntity.setFeieSn(printSettingDTO.getFeieSn());
				printSettingEntity.setFeieKey(printSettingDTO.getFeieKey());
				printSettingDAO.updatePrintSetting(printSettingEntity);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public SignSettingDTO getSignSetting() throws BusinessException {
		
		try {
			SignSettingEntity signSettingEntity = signSettingDAO.getSignSetting();
			if(signSettingEntity == null) {
				signSettingEntity = new SignSettingEntity();
				signSettingEntity.setCreateBy("admin");
				signSettingEntity.setUpdateBy("admin");
				signSettingDAO.addSignSetting(signSettingEntity);
			}
			SignSettingDTO signSettingDTO = new SignSettingDTO();
			signSettingDTO.setEnabled(signSettingEntity.isEnabled());
			signSettingDTO.setDay1Point(signSettingEntity.getDay1Point());
			signSettingDTO.setDay2Point(signSettingEntity.getDay2Point());
			signSettingDTO.setDay3Point(signSettingEntity.getDay3Point());
			signSettingDTO.setDay4Point(signSettingEntity.getDay4Point());
			signSettingDTO.setDay5Point(signSettingEntity.getDay5Point());
			signSettingDTO.setDay6Point(signSettingEntity.getDay6Point());
			signSettingDTO.setDay7Point(signSettingEntity.getDay7Point());
			signSettingDTO.setBackgroundUrl(signSettingEntity.getBackgroundUrl());
			signSettingDTO.setRuleUrl(signSettingEntity.getRuleUrl());
			return signSettingDTO;
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}

	@Override
	public void updateSignSetting(SignSettingDTO signSettingDTO) throws BusinessException {
		
		try {
			SignSettingEntity signSettingEntity = signSettingDAO.getSignSetting();
			if(signSettingEntity != null) {
				signSettingEntity.setEnabled(signSettingDTO.isEnabled());
				signSettingEntity.setDay1Point(signSettingDTO.getDay1Point());
				signSettingEntity.setDay2Point(signSettingDTO.getDay2Point());
				signSettingEntity.setDay3Point(signSettingDTO.getDay3Point());
				signSettingEntity.setDay4Point(signSettingDTO.getDay4Point());
				signSettingEntity.setDay5Point(signSettingDTO.getDay5Point());
				signSettingEntity.setDay6Point(signSettingDTO.getDay6Point());
				signSettingEntity.setDay7Point(signSettingDTO.getDay7Point());
				signSettingEntity.setBackgroundUrl(signSettingDTO.getBackgroundUrl());
				signSettingEntity.setRuleUrl(signSettingDTO.getRuleUrl());
				signSettingDAO.updateSignSetting(signSettingEntity);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public PointSettingDTO getPointSetting() throws BusinessException {

		try {
			PointSettingEntity pointSettingEntity = pointSettingDAO.getPointSetting();
			if(pointSettingEntity == null) {
				pointSettingEntity = new PointSettingEntity();
				pointSettingEntity.setCreateBy("admin");
				pointSettingEntity.setUpdateBy("admin");
				pointSettingDAO.addPointSetting(pointSettingEntity);
			}
			PointSettingDTO pointSettingDTO = new PointSettingDTO();
			pointSettingDTO.setEnabled(pointSettingEntity.isEnabled());
			pointSettingDTO.setAmount(pointSettingEntity.getAmount());
			pointSettingDTO.setPoint(pointSettingEntity.getPoint());
			return pointSettingDTO;
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}

	@Override
	public void updatePointSetting(PointSettingDTO pointSettingDTO) throws BusinessException {

		try {
			PointSettingEntity pointSettingEntity = pointSettingDAO.getPointSetting();
			if(pointSettingEntity != null) {
				pointSettingEntity.setEnabled(pointSettingDTO.isEnabled());
				pointSettingEntity.setAmount(pointSettingDTO.getAmount());
				pointSettingEntity.setPoint(pointSettingDTO.getPoint());
				pointSettingDAO.updatePointSetting(pointSettingEntity);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}

	}
}






