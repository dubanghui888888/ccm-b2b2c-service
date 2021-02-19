package com.mb.ext.core.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.aliyun.oss.OSSClient;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.mb.ext.core.constant.CouponConstants;
import com.mb.ext.core.dao.FileDAO;
import com.mb.ext.core.dao.MerchantDAO;
import com.mb.ext.core.dao.SequenceDAO;
import com.mb.ext.core.dao.UserDAO;
import com.mb.ext.core.dao.coupon.CouponDAO;
import com.mb.ext.core.dao.coupon.CouponProductDAO;
import com.mb.ext.core.dao.coupon.UserCouponDAO;
import com.mb.ext.core.dao.product.ProductDAO;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.coupon.CouponEntity;
import com.mb.ext.core.entity.coupon.CouponProductEntity;
import com.mb.ext.core.entity.coupon.UserCouponEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.CouponService;
import com.mb.ext.core.service.OSSService;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.coupon.CouponDTO;
import com.mb.ext.core.service.spec.coupon.CouponSearchDTO;
import com.mb.ext.core.service.spec.coupon.UserCouponDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.ext.core.util.OSSUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.service.AbstractService;
import com.mb.framework.util.log.LogHelper;



@Service
@Qualifier("CouponService")
public class CouponServiceImpl extends AbstractService implements
		CouponService<BodyDTO> {
	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;

	@Autowired
	@Qualifier("fileDAO")
	private FileDAO fileDAO;
	
	@Autowired
	private OSSUtil ossUtil;

	@Autowired
	@Qualifier("sequenceDAO")
	private SequenceDAO sequenceDAO;

	@Autowired
	@Qualifier("merchantDAO")
	private MerchantDAO merchantDAO;
	
	@Autowired
	@Qualifier("OSSService")
	private OSSService ossService;
	
	@Autowired
	@Qualifier("productDAO")
	private ProductDAO productDAO;
	
	@Autowired
	@Qualifier("couponDAO")
	private CouponDAO couponDAO;
	
	@Autowired
	@Qualifier("userCouponDAO")
	private UserCouponDAO userCouponDAO;
	
	@Autowired
	@Qualifier("couponProductDAO")
	private CouponProductDAO couponProductDAO;
	
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());
	
	@Override
	public void addCoupon(CouponDTO couponDTO) throws BusinessException {
		try {

			CouponEntity couponEntity = new CouponEntity();
			couponDTO2Entity(couponDTO, couponEntity);
			couponEntity.setActive(couponDTO.isActive());
			couponEntity.setTotalCount(couponDTO.getTotalCount());
			
			String type = couponDTO.getType();
			//现金券
			if(CouponConstants.COUPON_TYPE_CASH.equals(type)){
				couponEntity.setBenefitCash(couponDTO.getBenefitCash());
			}
			//折扣券
			else if(CouponConstants.COUPON_TYPE_DISCOUNT.equals(type)){
				couponEntity.setBenefitDiscount(couponDTO.getBenefitDiscount());
			}
			
			MerchantDTO merchantDTO = couponDTO.getMerchantDTO();
			if(merchantDTO != null){
				MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
				couponEntity.setMerchantEntity(merchantEntity);
			}
			couponDAO.createCoupon(couponEntity);
			
			if(CouponConstants.BENEFIT_TYPE_PRODUCT.equals(couponDTO.getBenefitType())){
				List<ProductDTO> productDTOList = couponDTO.getBenefitProductList();
				for(Iterator<ProductDTO> iter = productDTOList.iterator();iter.hasNext();){
					ProductDTO productDTO = iter.next();
					ProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
					CouponProductEntity couponProductEntity = new CouponProductEntity();
					couponProductEntity.setProductEntity(productEntity);
					couponProductEntity.setCouponEntity(couponEntity);
					couponProductDAO.createCouponProduct(couponProductEntity);
				}
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		
	}

	@Override
	public void updateCoupon(CouponDTO couponDTO) throws BusinessException {
		try {
			CouponEntity couponEntity = couponDAO.getCouponByUuid(couponDTO.getCouponUuid());
			couponDTO2Entity(couponDTO, couponEntity);
			couponEntity.setTotalCount(couponDTO.getTotalCount());
			
			String type = couponDTO.getType();
			//现金券
			if(CouponConstants.COUPON_TYPE_CASH.equals(type)){
				couponEntity.setBenefitCash(couponDTO.getBenefitCash());
			}
			//折扣券
			else if(CouponConstants.COUPON_TYPE_DISCOUNT.equals(type)){
				couponEntity.setBenefitDiscount(couponDTO.getBenefitDiscount());
			}
			
			MerchantDTO merchantDTO = couponDTO.getMerchantDTO();
			if(merchantDTO != null){
				MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
				couponEntity.setMerchantEntity(merchantEntity);
			}
			
			couponDAO.updateCoupon(couponEntity);
			
			//重新指定适用商品
			List<CouponProductEntity> productEntityList = couponEntity.getBenefitProductList();
			couponEntity.setBenefitProductList(null);
			for (Iterator<CouponProductEntity> iterator = productEntityList.iterator(); iterator.hasNext();) {
				CouponProductEntity couponProductEntity = iterator.next();
				couponProductDAO.deleteCouponProduct(couponProductEntity);
			}
			
			if(CouponConstants.BENEFIT_TYPE_PRODUCT.equals(couponDTO.getBenefitType())){
				List<ProductDTO> productDTOList = couponDTO.getBenefitProductList();
				for(Iterator<ProductDTO> iter = productDTOList.iterator();iter.hasNext();){
					ProductDTO productDTO = iter.next();
					ProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
					CouponProductEntity couponProductEntity = new CouponProductEntity();
					couponProductEntity.setProductEntity(productEntity);
					couponProductEntity.setCouponEntity(couponEntity);
					couponProductDAO.createCouponProduct(couponProductEntity);
				}
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		
	}

	@Override
	public void deleteCoupon(CouponDTO couponDTO) throws BusinessException {
		try {
			CouponEntity couponEntity = couponDAO.getCouponByUuid(couponDTO.getCouponUuid());
			if(couponEntity != null){
				couponDAO.deleteCoupon(couponEntity);
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		
	}

	@Override
	public void enableCoupon(String couponUuid) throws BusinessException {
		try {
			CouponEntity couponEntity = couponDAO.getCouponByUuid(couponUuid);
			if(couponEntity != null){
				couponEntity.setActive(true);
				couponDAO.updateCoupon(couponEntity);
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		
	}

	@Override
	public void disableCoupon(String couponUuid) throws BusinessException {
		try {
			CouponEntity couponEntity = couponDAO.getCouponByUuid(couponUuid);
			if(couponEntity != null){
				couponEntity.setActive(false);
				couponDAO.updateCoupon(couponEntity);
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		
	}

	@Override
	public void receiveCoupon(String couponUuid, UserDTO userDTO, String channel)
			throws BusinessException {
		try {
			CouponEntity couponEntity = couponDAO.getCouponByUuid(couponUuid);
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			if(couponEntity != null){
				//检查该券是否依然有效
				if(!couponEntity.isActive()){
					throw new BusinessException(BusinessErrorCode.COUPON_INACTIVE);
				}
				
				List<UserCouponEntity> userCouponEntityList = userCouponDAO.getUserCouponByUserCoupon(userEntity,couponEntity);
				int limitPerUser = couponEntity.getLimitPerUser();
				if(userCouponEntityList.size()>=limitPerUser){
						throw new BusinessException(BusinessErrorCode.COUPON_EXCEED_LIMIT);
				}else{
					UserCouponEntity userCouponEntity = new UserCouponEntity();
					userCouponEntity.setUserEntity(userEntity);
					userCouponEntity.setCouponEntity(couponEntity);
					userCouponEntity.setReceiveTime(new Date());
					userCouponEntity.setReceiveChannel(channel);
					if(CouponConstants.VALID_TYPE_FIXDATE.equals(couponEntity.getValidType())) {
						userCouponEntity.setStartDate(couponEntity.getStartDate());
						userCouponEntity.setEndDate(couponEntity.getEndDate());
					}else if(CouponConstants.VALID_TYPE_RECEIVE_AFTER.equals(couponEntity.getValidType())) {
						userCouponEntity.setStartDate(new Date());
						userCouponEntity.setEndDate(DateUtils.addDays(new Date(), couponEntity.getValidDays()));
					}
					userCouponDAO.createUserCoupon(userCouponEntity);
				}
				couponEntity.setAvailableCount(couponEntity.getAvailableCount()-1);
				couponDAO.updateCoupon(couponEntity);
				
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		
	}

	@Override
	public void useCoupon(UserCouponDTO userCouponDTO)
			throws BusinessException {
		try {
			UserCouponEntity userCouponEntity = userCouponDAO.getUserCouponByUuid(userCouponDTO.getUserCouponUuid());
			if(userCouponEntity.isUsed()){
				throw new BusinessException(BusinessErrorCode.COUPON_USED);
			}
			CouponEntity couponEntity = userCouponEntity.getCouponEntity();
			if(couponEntity != null){
				//检查该券是否依然有效
				if(!couponEntity.isActive()){
					throw new BusinessException(BusinessErrorCode.COUPON_INACTIVE);
				}
				//检查该券是否已过有效期
				if(CouponConstants.VALID_TYPE_FIXDATE.equals(couponEntity.getValidType())){
					Date currentDate = new Date();
					if(currentDate.after(couponEntity.getEndDate())){
						throw new BusinessException(BusinessErrorCode.COUPON_INACTIVE);
					}
					if(currentDate.before(couponEntity.getStartDate())){
						throw new BusinessException(BusinessErrorCode.COUPON_NOT_STARTED);
					}
				}else if(CouponConstants.VALID_TYPE_RECEIVE_AFTER.equals(couponEntity.getValidType())){
					Date currentDate = new Date();
					Date receiveDate = userCouponEntity.getReceiveTime();
					int validDays = couponEntity.getValidDays();
					if(currentDate.after(DateUtils.addDays(receiveDate, validDays))){
						throw new BusinessException(BusinessErrorCode.COUPON_INACTIVE);
					}
				}
				UserEntity userEntity = userCouponEntity.getUserEntity();
				if(userEntity == null){
					throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
				}
					
				userCouponEntity.setUsed(true);
				userCouponEntity.setUseTime(new Date());
				userCouponEntity.setUseChannel(userCouponDTO.getUseChannel());
				userCouponDAO.updateUserCoupon(userCouponEntity);
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		
	}

	@Override
	public CouponDTO getCoupon(String couponUuid)
			throws BusinessException {
		try{
			CouponEntity couponEntity = couponDAO.getCouponByUuid(couponUuid);
			CouponDTO couponDTO = new CouponDTO();
			couponEntity2DTO(couponEntity, couponDTO);
			return couponDTO;
			
		}catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}
	
	@Override
	public UserCouponDTO getUserCouponByUuid(String userCouponUuid)
			throws BusinessException {
		try{
			UserCouponEntity userCouponEntity = userCouponDAO.getUserCouponByUuid(userCouponUuid);
			CouponEntity couponEntity = userCouponEntity.getCouponEntity();
			UserEntity userEntity = userCouponEntity.getUserEntity();
			UserCouponDTO userCouponDTO = new UserCouponDTO(); 
			CouponDTO couponDTO = new CouponDTO();
			couponEntity2DTO(couponEntity, couponDTO);
			UserDTO userDTO = new UserDTO();
			userDTO.setUserUuid(userEntity.getUserUuid());
			userDTO.setName(userEntity.getName());
			userCouponDTO.setUserDTO(userDTO);
			userCouponDTO.setCouponDTO(couponDTO);
			userCouponDTO.setUserCouponUuid(userCouponEntity.getUserCouponUuid());
			userCouponDTO.setUsed(userCouponEntity.isUsed());
			userCouponDTO.setReceiveChannel(userCouponEntity.getReceiveChannel());
			userCouponDTO.setReceiveTime(userCouponEntity.getReceiveTime());
			userCouponDTO.setUseChannel(userCouponEntity.getUseChannel());
			userCouponDTO.setUseTime(userCouponEntity.getUseTime());
			return userCouponDTO;
			
		}catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}
	
	@Override
	public List<CouponDTO> getCouponByMerchant(MerchantDTO merchantDTO)
			throws BusinessException {
		try{
			List<CouponDTO> listDTO = new ArrayList<CouponDTO>();
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			List<CouponEntity> entityList = couponDAO.getCouponByMerchant(merchantEntity);
			for(Iterator<CouponEntity> iter = entityList.iterator();iter.hasNext();){
				CouponEntity couponEntity = iter.next();
				CouponDTO couponDTO = new CouponDTO();
				couponEntity2DTO(couponEntity, couponDTO);
				listDTO.add(couponDTO);
			}
			return listDTO;
		}catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}
	
	@Override
	public List<CouponDTO> getCoupons()
			throws BusinessException {
		try{
			List<CouponDTO> listDTO = new ArrayList<CouponDTO>();
			List<CouponEntity> entityList = couponDAO.getAllCoupons();
			for(Iterator<CouponEntity> iter = entityList.iterator();iter.hasNext();){
				CouponEntity couponEntity = iter.next();
				CouponDTO couponDTO = new CouponDTO();
				couponEntity2DTO(couponEntity, couponDTO);
				listDTO.add(couponDTO);
			}
			return listDTO;
		}catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}

	@Override
	public List<CouponDTO> getActiveCoupons()
			throws BusinessException {
		try{
			List<CouponDTO> listDTO = new ArrayList<CouponDTO>();
			List<CouponEntity> entityList = couponDAO.getActiveCoupons();
			for(Iterator<CouponEntity> iter = entityList.iterator();iter.hasNext();){
				CouponEntity couponEntity = iter.next();
				CouponDTO couponDTO = new CouponDTO();
				couponEntity2DTO(couponEntity, couponDTO);
				listDTO.add(couponDTO);
			}
			return listDTO;
		}catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}
	
	@Override
	public List<UserCouponDTO> getCouponByUserMerchant(UserDTO userDTO, MerchantDTO merchantDTO)
			throws BusinessException {
		try{
			List<UserCouponDTO> listDTO = new ArrayList<UserCouponDTO>();
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			List<UserCouponEntity> entityList = userCouponDAO.getCouponByUserMerchant(userEntity, merchantEntity);
			for(Iterator<UserCouponEntity> iter = entityList.iterator();iter.hasNext();){
				UserCouponEntity userCouponEntity = iter.next();
				CouponEntity couponEntity = new CouponEntity();
				UserCouponDTO userCouponDTO = new UserCouponDTO();
				CouponDTO couponDTO = new CouponDTO();
				couponEntity2DTO(couponEntity, couponDTO);
				userCouponDTO.setCouponDTO(couponDTO);
				userCouponDTO.setReceiveChannel(userCouponEntity.getReceiveChannel());
				userCouponDTO.setReceiveTime(userCouponEntity.getReceiveTime());
				userCouponDTO.setStartDate(userCouponEntity.getStartDate());
				userCouponDTO.setEndDate(userCouponEntity.getEndDate());
				listDTO.add(userCouponDTO);
			}
			return listDTO;
		}catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}
	
	@Override
	public List<UserCouponDTO> getUserCouponByCoupon(CouponDTO tCouponDTO)
			throws BusinessException {
		List<UserCouponDTO> listDTO = new ArrayList<UserCouponDTO>();
		try{
			CouponEntity couponEntity = couponDAO.getCouponByUuid(tCouponDTO.getCouponUuid());
			List<UserCouponEntity> entityList = userCouponDAO.getUserCouponByCoupon(couponEntity);
			for(Iterator<UserCouponEntity> iter = entityList.iterator();iter.hasNext();){
				UserCouponEntity userCouponEntity = iter.next();
				UserCouponDTO userCouponDTO = new UserCouponDTO();
				userCouponDTO.setReceiveChannel(userCouponEntity.getReceiveChannel());
				userCouponDTO.setReceiveTime(userCouponEntity.getReceiveTime());
				userCouponDTO.setUsed(userCouponEntity.isUsed());
				userCouponDTO.setUseTime(userCouponEntity.getUseTime());
				userCouponDTO.setStartDate(userCouponEntity.getStartDate());
				userCouponDTO.setEndDate(userCouponEntity.getEndDate());
				UserEntity userEntity = userCouponEntity.getUserEntity();
				UserDTO userDTO = new UserDTO();
				userDTO.setUserUuid(userEntity.getUserUuid());
				userDTO.setPersonalPhone(userEntity.getPersonalPhone());
				userDTO.setName(userEntity.getName());
				userDTO.setPhotoUrl(userEntity.getPhotoUrl());
				userCouponDTO.setUserDTO(userDTO);
				listDTO.add(userCouponDTO);
			}
			
		}catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		return listDTO;
	}
	
	@Override
	public List<UserCouponDTO> searchUserCoupon(CouponSearchDTO couponSearchDTO)
			throws BusinessException {
		List<UserCouponDTO> listDTO = new ArrayList<UserCouponDTO>();
		try{
			List<UserCouponEntity> entityList = userCouponDAO.searchUserCoupon(couponSearchDTO);
			for(Iterator<UserCouponEntity> iter = entityList.iterator();iter.hasNext();){
				UserCouponEntity userCouponEntity = iter.next();
				UserCouponDTO userCouponDTO = new UserCouponDTO();
				userCouponDTO.setUserCouponUuid(userCouponEntity.getUserCouponUuid());
				userCouponDTO.setReceiveChannel(userCouponEntity.getReceiveChannel());
				userCouponDTO.setReceiveTime(userCouponEntity.getReceiveTime());
				userCouponDTO.setUsed(userCouponEntity.isUsed());
				userCouponDTO.setUseTime(userCouponEntity.getUseTime());
				userCouponDTO.setStartDate(userCouponEntity.getStartDate());
				userCouponDTO.setEndDate(userCouponEntity.getEndDate());
				UserEntity userEntity = userCouponEntity.getUserEntity();
				UserDTO userDTO = new UserDTO();
				userDTO.setUserUuid(userEntity.getUserUuid());
				userDTO.setPersonalPhone(userEntity.getPersonalPhone());
				userDTO.setName(userEntity.getName());
				userDTO.setPhotoUrl(userEntity.getPhotoUrl());
				userCouponDTO.setUserDTO(userDTO);
				CouponEntity couponEntity = userCouponEntity.getCouponEntity();
				CouponDTO couponDTO = new CouponDTO();
				couponEntity2DTO(couponEntity, couponDTO);
				userCouponDTO.setCouponDTO(couponDTO);
				listDTO.add(userCouponDTO);
			}
			
		}catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		return listDTO;
	}
	
	@Override
	public int searchUserCouponTotal(CouponSearchDTO couponSearchDTO)
			throws BusinessException {
		try{
			return userCouponDAO.searchUserCouponTotal(couponSearchDTO);
			
		}catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}
	
	
	@Override
	public List<UserCouponDTO> getValidCouponByUserMerchant(UserDTO userDTO, MerchantDTO merchantDTO)
			throws BusinessException {
		try{
			List<UserCouponDTO> listDTO = new ArrayList<UserCouponDTO>();
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			List<UserCouponEntity> entityList = userCouponDAO.getCouponByUserMerchant(userEntity, merchantEntity);
			Date currDate = new Date();
			for(Iterator<UserCouponEntity> iter = entityList.iterator();iter.hasNext();){
				UserCouponEntity userCouponEntity = iter.next();
				if(userCouponEntity.isUsed()){
					continue;
				}
				CouponEntity couponEntity = userCouponEntity.getCouponEntity();
				if(CouponConstants.VALID_TYPE_FIXDATE.equals(couponEntity.getValidType())){
					if(currDate.before(couponEntity.getStartDate()) || currDate.after(couponEntity.getEndDate())){
						continue;
					}
				}else if(CouponConstants.VALID_TYPE_RECEIVE_AFTER.equals(couponEntity.getValidType())){
					if(currDate.after(DateUtils.addDays(userCouponEntity.getReceiveTime(), couponEntity.getValidDays()))){
						continue;
					}
				}
				UserCouponDTO userCouponDTO = new UserCouponDTO();
				CouponDTO couponDTO = new CouponDTO();
				couponEntity2DTO(couponEntity, couponDTO);
				userCouponDTO.setCouponDTO(couponDTO);
				userCouponDTO.setReceiveChannel(userCouponEntity.getReceiveChannel());
				userCouponDTO.setReceiveTime(userCouponEntity.getReceiveTime());
				userCouponDTO.setUserCouponUuid(userCouponEntity.getUserCouponUuid());
				userCouponDTO.setStartDate(userCouponEntity.getStartDate());
				userCouponDTO.setEndDate(userCouponEntity.getEndDate());
				listDTO.add(userCouponDTO);
			}
			return listDTO;
		}catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}
	
	@Override
	public List<UserCouponDTO> getUserCoupon(UserDTO userDTO)
			throws BusinessException {
		try{
			List<UserCouponDTO> listDTO = new ArrayList<UserCouponDTO>();
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			List<UserCouponEntity> entityList = userCouponDAO.getCouponByUser(userEntity);
			for(Iterator<UserCouponEntity> iter = entityList.iterator();iter.hasNext();){
				UserCouponEntity entity = iter.next();
				CouponEntity couponEntity = entity.getCouponEntity();
				UserCouponDTO userCouponDTO = new UserCouponDTO(); 
				CouponDTO couponDTO = new CouponDTO();
				couponEntity2DTO(couponEntity, couponDTO);
				userCouponDTO.setCouponDTO(couponDTO);
				userCouponDTO.setUserCouponUuid(entity.getUserCouponUuid());
				userCouponDTO.setUsed(entity.isUsed());
				userCouponDTO.setReceiveChannel(entity.getReceiveChannel());
				userCouponDTO.setReceiveTime(entity.getReceiveTime());
				userCouponDTO.setUseChannel(entity.getUseChannel());
				userCouponDTO.setUseTime(entity.getUseTime());
				userCouponDTO.setStartDate(entity.getStartDate());
				userCouponDTO.setEndDate(entity.getEndDate());
				listDTO.add(userCouponDTO);
			}
			return listDTO;
		}catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}
	
	void couponDTO2Entity(CouponDTO couponDTO, CouponEntity couponEntity) {
		couponEntity.setName(couponDTO.getName());
		couponEntity.setMemo(couponDTO.getMemo());
		couponEntity.setType(couponDTO.getType());
		couponEntity.setValidType(couponDTO.getValidType());
		couponEntity.setValidDays(couponDTO.getValidDays());
		couponEntity.setStartDate(couponDTO.getStartDate());
		couponEntity.setEndDate(couponDTO.getEndDate());
		couponEntity.setLimitPerUser(couponDTO.getLimitPerUser());
		couponEntity.setConditionAmount(couponDTO.getConditionAmount());
		couponEntity.setBenefitType(couponDTO.getBenefitType());
		couponEntity.setImageUrl(couponDTO.getImageUrl());
		couponEntity.setMemo(couponDTO.getMemo());
		couponEntity.setCouponCode(couponDTO.getCouponCode());
	}
	
	void couponEntity2DTO(CouponEntity couponEntity, CouponDTO couponDTO) {
		couponDTO.setName(couponEntity.getName());
		couponDTO.setMemo(couponEntity.getMemo());
		couponDTO.setType(couponEntity.getType());
		couponDTO.setValidType(couponEntity.getValidType());
		couponDTO.setValidDays(couponEntity.getValidDays());
		couponDTO.setStartDate(couponEntity.getStartDate());
		couponDTO.setEndDate(couponEntity.getEndDate());
		couponDTO.setTotalCount(couponEntity.getTotalCount());
		couponDTO.setLimitPerUser(couponEntity.getLimitPerUser());
		couponDTO.setActive(couponEntity.isActive());
		couponDTO.setAvailableCount(couponEntity.getAvailableCount());
		couponDTO.setCouponCode(couponEntity.getCouponCode());
		couponDTO.setCouponUuid(couponEntity.getCouponUuid());
		couponDTO.setBenefitCash(couponEntity.getBenefitCash());
		couponDTO.setBenefitDiscount(couponEntity.getBenefitDiscount());
		couponDTO.setConditionAmount(couponEntity.getConditionAmount());
		couponDTO.setBenefitType(couponEntity.getBenefitType());
		couponDTO.setImageUrl(couponEntity.getImageUrl());
		
		MerchantEntity merchantEntity = couponEntity.getMerchantEntity();
		if(merchantEntity != null){
			MerchantDTO merchantDTO = new MerchantDTO();
			merchantDTO.setMerchantUuid(merchantEntity.getMerchantUuid());
			merchantDTO.setMerchantName(merchantEntity.getMerchantName());
			merchantDTO.setMerchantAddress(merchantEntity.getMerchantAddress());
			couponDTO.setMerchantDTO(merchantDTO);
		}
		
		List<CouponProductEntity> benefitProductEntityList = couponEntity.getBenefitProductList();
		List<ProductDTO> benefitProductDTOList = new ArrayList<ProductDTO>();
		if(benefitProductEntityList != null) {
			for (Iterator<CouponProductEntity> iterator = benefitProductEntityList.iterator(); iterator.hasNext();) {
				CouponProductEntity benefitCouponProductEntity = iterator.next();
				ProductEntity productEntity = benefitCouponProductEntity.getProductEntity();
				ProductDTO productDTO = new ProductDTO();
				productDTO.setProductUuid(productEntity.getProductUuid());
				productDTO.setProductName(productEntity.getProductName());
				productDTO.setProductDesc(productEntity.getProductDesc());
				benefitProductDTOList.add(productDTO);
			}
		}
		couponDTO.setBenefitProductList(benefitProductDTOList);
		
	}

	@Override
	public String generateCouponBarCode(String userCouponUuid)
			throws BusinessException {
		InputStream is = null;
		ByteArrayOutputStream os = null;
		int width=400;
		int height=400;
		String format="png";
		Hashtable hints=new Hashtable();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		hints.put(EncodeHintType.MARGIN, 2);
		try {
			BitMatrix bitMatrix=new MultiFormatWriter().encode(userCouponUuid, BarcodeFormat.QR_CODE, width, height,hints);
			os = new ByteArrayOutputStream();  
			MatrixToImageWriter.writeToStream(bitMatrix, format, os);
			is = new ByteArrayInputStream(os.toByteArray());
			OSSClient client = new OSSClient(ossUtil.getOssEndPoint(), ossUtil.getOssAccessId(), ossUtil.getOssAccessKey()); 
            String key = "couponBarcode/"+userCouponUuid;
            client.putObject(ossUtil.getOssBucketName(), key, is);
            String url = ossService.getUrl(key);
            client.shutdown(); 
            return url;
		} catch (Exception e) {
			logger.error("生成优惠券条形码失败");
		}finally{
			try{
				if(is != null)
					is.close();
				if(os != null)
					os.close();
			}catch(Exception e){
				logger.error(e.getMessage());
			}
		}
		return null;
	}

	@Override
	public List<CouponDTO> searchCoupon(CouponSearchDTO couponSearchDTO) throws BusinessException {
		List<CouponDTO> couponDTOList = new ArrayList<CouponDTO>();
		try{
			List<CouponEntity> couponEntityList = couponDAO.searchCoupon(couponSearchDTO);
			for(Iterator<CouponEntity> iter = couponEntityList.iterator();iter.hasNext();){
				CouponEntity couponEntity = iter.next();
				CouponDTO couponDTO = new CouponDTO();
				couponEntity2DTO(couponEntity, couponDTO);
				couponDTOList.add(couponDTO);
			}
		}catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		return couponDTOList;
	}

	@Override
	public int searchCouponTotal(CouponSearchDTO couponSearchDTO) throws BusinessException {
		try{
			return couponDAO.searchCouponTotal(couponSearchDTO);
		}catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}
}
