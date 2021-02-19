package com.mb.ext.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mb.ext.core.constant.DeliveryConstants;
import com.mb.ext.core.constant.InventoryConstants;
import com.mb.ext.core.dao.MerchantDAO;
import com.mb.ext.core.dao.SequenceDAO;
import com.mb.ext.core.dao.UserDAO;
import com.mb.ext.core.dao.UserDeliveryAddressDAO;
import com.mb.ext.core.dao.UserDeliveryDAO;
import com.mb.ext.core.dao.UserInventoryDAO;
import com.mb.ext.core.dao.UserInventoryHistoryDAO;
import com.mb.ext.core.dao.product.ProductDeliveryDAO;
import com.mb.ext.core.entity.UserDeliveryAddressEntity;
import com.mb.ext.core.entity.UserDeliveryEntity;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.UserInventoryEntity;
import com.mb.ext.core.entity.UserInventoryHistoryEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.product.ProductDeliveryEntity;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.DeliveryService;
import com.mb.ext.core.service.NoteService;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.MerchantSearchDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.UserDeliveryDTO;
import com.mb.ext.core.service.spec.UserDeliverySearchDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.order.UserDeliveryAddressDTO;
import com.mb.ext.core.service.spec.product.ProductDeliveryDTO;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.service.AbstractService;

@Service
@Qualifier("DeliveryService")
public class DeliveryServiceImpl extends AbstractService implements
		DeliveryService<BodyDTO> {

	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;

	@Autowired
	@Qualifier("NoteService")
	private NoteService noteService;
	
	@Autowired
	@Qualifier("userDeliveryDAO")
	private UserDeliveryDAO userDeliveryDAO;

	@Autowired
	@Qualifier("userInventoryDAO")
	private UserInventoryDAO userInventoryDAO;
	
	@Autowired
	@Qualifier("userInventoryHistoryDAO")
	private UserInventoryHistoryDAO userInventoryHistoryDAO;
	
	@Autowired
	@Qualifier("userDeliveryAddressDAO")
	private UserDeliveryAddressDAO userDeliveryAddressDAO;
	
	@Autowired
	@Qualifier("sequenceDAO")
	private SequenceDAO sequenceDAO;

	@Autowired
	@Qualifier("merchantDAO")
	private MerchantDAO merchantDAO;

	@Autowired
	@Qualifier("productDeliveryDAO")
	private ProductDeliveryDAO productDeliveryDAO;


	@Override
	public void updateUserDeliveryCourier(UserDeliveryDTO userDeliveryDTO)
			throws BusinessException {
		try{
			UserDeliveryEntity userDeliveryEntity = userDeliveryDAO.findByUuid(userDeliveryDTO.getUserDeliveryUuid());
			userDeliveryEntity.setCourierName(userDeliveryDTO.getCourierName());
			userDeliveryEntity.setCourierNo(userDeliveryDTO.getCourierNo());
			userDeliveryDAO.updateUserDelivery(userDeliveryEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public void sendUserDelivery(UserDeliveryDTO userDeliveryDTO)
			throws BusinessException {
		try{
			UserDeliveryEntity userDeliveryEntity = userDeliveryDAO.findByUuid(userDeliveryDTO.getUserDeliveryUuid());
			//不能重复发货
			if(DeliveryConstants.DELIVERY_STATUS_SENT.equals(userDeliveryEntity.getDeliveryStatus())){
				return;
			}
			userDeliveryEntity.setDeliveryStatus(DeliveryConstants.DELIVERY_STATUS_SENT);
			userDeliveryEntity.setDeliveryTime(new Date());
			//对于自提的情况, 客户可在任意驿站提货, 不一定是所属驿站
			if(DeliveryConstants.DELIVERY_TYPE_ZITI.equals(userDeliveryEntity.getDeliveryType())){
				MerchantDTO merchantDTO = userDeliveryDTO.getMerchantDTO();
				MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
				userDeliveryEntity.setMerchantEntity(merchantEntity);
			}
			userDeliveryDAO.updateUserDelivery(userDeliveryEntity);
			
			UserEntity userEntity = userDeliveryEntity.getUserEntity();
			UserInventoryEntity inventoryEntity = userEntity.getUserInventoryEntity();
			//添加会员库存明细
			UserInventoryHistoryEntity inventoryHistoryEntity = new UserInventoryHistoryEntity();
			inventoryHistoryEntity.setBalanceBefore(inventoryEntity.getBalance()+userDeliveryEntity.getDeliveryQuantity());
			inventoryHistoryEntity.setBalanceAfter(inventoryEntity.getBalance());
			inventoryHistoryEntity.setUserEntity(userEntity);
			inventoryHistoryEntity.setTranType(InventoryConstants.TRANTYPE_TIHUO);
			inventoryHistoryEntity.setTranUnit(userDeliveryEntity.getDeliveryQuantity());
			inventoryHistoryEntity.setIncrease(false);
			userInventoryHistoryDAO.addUserInventoryHistory(inventoryHistoryEntity);
			
			//购买成功, 更新驿站库存ledger balance, 提货成功更新available balance
			MerchantEntity merchantEntity = userDeliveryEntity.getMerchantEntity();
			
			//发送提货
			UserDTO userDTO = new UserDTO();
			userDTO.setUserUuid(userEntity.getUserUuid());
			noteService.sendNotification(userDTO, "deliverysuccess", new String[]{String.valueOf(userDeliveryEntity.getDeliveryQuantity())});
			
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void commentUserDelivery(UserDeliveryDTO userDeliveryDTO)
			throws BusinessException {
		try{
			UserDeliveryEntity userDeliveryEntity = userDeliveryDAO.findByUuid(userDeliveryDTO.getUserDeliveryUuid());
			userDeliveryEntity.setMemo(userDeliveryDTO.getMemo());
			userDeliveryDAO.updateUserDelivery(userDeliveryEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public List<UserDeliveryDTO> getDeliveryByUser(UserDTO userDTO)
			throws BusinessException {
		List<UserDeliveryDTO> dtoList = new ArrayList<UserDeliveryDTO>();
		try{
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			List<UserDeliveryEntity> entityList =  userDeliveryDAO.findByUser(userEntity);
			for (Iterator<UserDeliveryEntity> iterator = entityList.iterator(); iterator.hasNext();) {
				UserDeliveryEntity userDeliveryEntity = (UserDeliveryEntity) iterator
						.next();
				UserDeliveryDTO userDeliveryDTO = new UserDeliveryDTO();
				deliveryEntity2DTO(userDeliveryEntity, userDeliveryDTO);
				dtoList.add(userDeliveryDTO);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return dtoList;
	}
	
	@Override
	public List<UserDeliveryDTO> getDeliveryByMerchant(MerchantDTO merchantDTO)
			throws BusinessException {
		List<UserDeliveryDTO> dtoList = new ArrayList<UserDeliveryDTO>();
		try{
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			List<UserDeliveryEntity> entityList =  userDeliveryDAO.findByMerchant(merchantEntity);
			for (Iterator<UserDeliveryEntity> iterator = entityList.iterator(); iterator.hasNext();) {
				UserDeliveryEntity userDeliveryEntity = (UserDeliveryEntity) iterator
						.next();
				UserDeliveryDTO userDeliveryDTO = new UserDeliveryDTO();
				deliveryEntity2DTO(userDeliveryEntity, userDeliveryDTO);
				dtoList.add(userDeliveryDTO);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return dtoList;
	}
	
	@Override
	public UserDeliveryDTO getDeliveryByUuid(String uuid)
			throws BusinessException {
		try{
				UserDeliveryEntity userDeliveryEntity = userDeliveryDAO.findByUuid(uuid);
				UserDeliveryDTO userDeliveryDTO = new UserDeliveryDTO();
				deliveryEntity2DTO(userDeliveryEntity, userDeliveryDTO);
				return userDeliveryDTO;
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}

	@Override
	public List<UserDeliveryDTO> searchUserDelivery(
			UserDeliverySearchDTO userDeliverySearchDTO, int startIndex, int pageSize)
			throws BusinessException {
		List<UserDeliveryDTO> dtoList = new ArrayList<UserDeliveryDTO>();
		try{
			List<UserDeliveryEntity> entityList =  userDeliveryDAO.searchUserDelivery(userDeliverySearchDTO, startIndex, pageSize);
			for (Iterator<UserDeliveryEntity> iterator = entityList.iterator(); iterator.hasNext();) {
				UserDeliveryEntity userDeliveryEntity = (UserDeliveryEntity) iterator
						.next();
				UserDeliveryDTO userDeliveryDTO = new UserDeliveryDTO();
				deliveryEntity2DTO(userDeliveryEntity, userDeliveryDTO);
				dtoList.add(userDeliveryDTO);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return dtoList;
		
	}
	
	@Override
	public int searchUserDeliveryTotal(
			UserDeliverySearchDTO userDeliverySearchDTO)
			throws BusinessException {
		try{
			return userDeliveryDAO.searchUserDeliveryTotal(userDeliverySearchDTO);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	void deliveryEntity2DTO(UserDeliveryEntity deliveryEntity, UserDeliveryDTO deliveryDTO){
		
		deliveryDTO.setUserDeliveryUuid(deliveryEntity.getUserDeliveryUuid());
		deliveryDTO.setApplicationTime(deliveryEntity.getApplicationTime());
		deliveryDTO.setCourierName(deliveryEntity.getCourierName());
		deliveryDTO.setCourierNo(deliveryEntity.getCourierNo());
		deliveryDTO.setDeliveryAddress(deliveryEntity.getDeliveryAddress());
		deliveryDTO.setDeliveryPostCode(deliveryEntity.getDeliveryPostCode());
		deliveryDTO.setDeliveryQuantity(deliveryEntity.getDeliveryQuantity());
		deliveryDTO.setDeliveryStatus(deliveryEntity.getDeliveryStatus());
		deliveryDTO.setDeliveryTime(deliveryEntity.getDeliveryTime());
		deliveryDTO.setDeliveryType(deliveryEntity.getDeliveryType());
		deliveryDTO.setDeliveryName(deliveryEntity.getDeliveryName());
		deliveryDTO.setDeliveryContactNo(deliveryEntity.getDeliveryContactNo());
		deliveryDTO.setMemo(deliveryEntity.getMemo());
		UserEntity userEntity = deliveryEntity.getUserEntity();
		UserDTO userDTO = new UserDTO();
		userDTO.setUserUuid(userEntity.getUserUuid());
		userDTO.setName(userEntity.getName());
		userDTO.setPersonalPhone(userEntity.getPersonalPhone());
		deliveryDTO.setUserDTO(userDTO);
		MerchantEntity merchantEntity = deliveryEntity.getMerchantEntity();
		if(merchantEntity != null){
			MerchantDTO merchantDTO = new MerchantDTO();
			merchantDTO.setMerchantUuid(merchantEntity.getMerchantUuid());
			merchantDTO.setMerchantAddress(merchantEntity.getMerchantAddress());
			deliveryDTO.setMerchantDTO(merchantDTO);
		}
	}
	
	void deliveryAddressEntity2DTO(UserDeliveryAddressEntity addressEntity, UserDeliveryAddressDTO addressDTO){
		
		addressDTO.setUserDeliveryAddressUuid(addressEntity.getUserDeliveryAddressUuid());
		addressDTO.setProvince(addressEntity.getProvince());
		addressDTO.setCity(addressEntity.getCity());
		addressDTO.setArea(addressEntity.getArea());
		addressDTO.setStreet(addressEntity.getStreet());
		addressDTO.setDefault(addressEntity.isDefault());
		addressDTO.setName(addressEntity.getName());
		addressDTO.setTelephone(addressEntity.getTelephone());
		addressDTO.setTelephone2(addressEntity.getTelephone2());
		addressDTO.setZipcode(addressEntity.getZipcode());
		UserEntity userEntity = addressEntity.getUserEntity();
		UserDTO userDTO = new UserDTO();
		userDTO.setUserUuid(userEntity.getUserUuid());
		userDTO.setName(userEntity.getName());
		userDTO.setPersonalPhone(userEntity.getPersonalPhone());
		addressDTO.setUserDTO(userDTO);
	}

	@Override
	public void addUserDeliveryAddress(UserDeliveryAddressDTO addressDTO)
			throws BusinessException {
		try{
			UserDeliveryAddressEntity userDeliveryAddressEntity = new UserDeliveryAddressEntity();
			UserDTO userDTO = addressDTO.getUserDTO();
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			userDeliveryAddressEntity.setUserEntity(userEntity);
			userDeliveryAddressEntity.setProvince(addressDTO.getProvince());
			userDeliveryAddressEntity.setCity(addressDTO.getCity());
			userDeliveryAddressEntity.setArea(addressDTO.getArea());
			userDeliveryAddressEntity.setStreet(addressDTO.getStreet());
			userDeliveryAddressEntity.setName(addressDTO.getName());
			userDeliveryAddressEntity.setTelephone(addressDTO.getTelephone());
			userDeliveryAddressEntity.setTelephone2(addressDTO.getTelephone2());
			userDeliveryAddressEntity.setDefault(false);
			userDeliveryAddressEntity.setZipcode(addressDTO.getZipcode());
			userDeliveryAddressEntity.setZipcode(addressDTO.getZipcode());
			userDeliveryAddressDAO.addUserDeliveryAddress(userDeliveryAddressEntity);
			if(addressDTO.isDefault()) {
				addressDTO.setUserDeliveryAddressUuid(userDeliveryAddressEntity.getUserDeliveryAddressUuid());
				setDefaultUserDeliveryAddress(addressDTO);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public void updateUserDeliveryAddress(UserDeliveryAddressDTO addressDTO)
			throws BusinessException {
		try{
			UserDeliveryAddressEntity userDeliveryAddressEntity = userDeliveryAddressDAO.getDeliveryAddressByUuid(addressDTO.getUserDeliveryAddressUuid());
			userDeliveryAddressEntity.setProvince(addressDTO.getProvince());
			userDeliveryAddressEntity.setCity(addressDTO.getCity());
			userDeliveryAddressEntity.setArea(addressDTO.getArea());
			userDeliveryAddressEntity.setStreet(addressDTO.getStreet());
			userDeliveryAddressEntity.setName(addressDTO.getName());
			userDeliveryAddressEntity.setTelephone(addressDTO.getTelephone());
			userDeliveryAddressEntity.setTelephone2(addressDTO.getTelephone2());
			userDeliveryAddressEntity.setDefault(false);
			userDeliveryAddressEntity.setZipcode(addressDTO.getZipcode());
			userDeliveryAddressEntity.setZipcode(addressDTO.getZipcode());
			userDeliveryAddressDAO.updateUserDeliveryAddress(userDeliveryAddressEntity);
			if(addressDTO.isDefault()) {
				setDefaultUserDeliveryAddress(addressDTO);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public void deleteUserDeliveryAddress(UserDeliveryAddressDTO addressDTO)
			throws BusinessException {
		try{
			UserDeliveryAddressEntity userDeliveryAddressEntity = userDeliveryAddressDAO.getDeliveryAddressByUuid(addressDTO.getUserDeliveryAddressUuid());
			userDeliveryAddressDAO.deleteUserDeliveryAddress(userDeliveryAddressEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public void setDefaultUserDeliveryAddress(UserDeliveryAddressDTO addressDTO)
			throws BusinessException {
		try{
			UserDeliveryAddressEntity defaultDeliveryAddressEntity = userDeliveryAddressDAO.getDeliveryAddressByUuid(addressDTO.getUserDeliveryAddressUuid());
			UserEntity userEntity = defaultDeliveryAddressEntity.getUserEntity();
			List<UserDeliveryAddressEntity> entityList = userDeliveryAddressDAO.getDeliveryAddressByUser(userEntity);
			for (Iterator<UserDeliveryAddressEntity> iterator = entityList.iterator(); iterator.hasNext();) {
				UserDeliveryAddressEntity userDeliveryAddressEntity2 = iterator.next();
				if(!userDeliveryAddressEntity2.getUserDeliveryAddressUuid().equals(defaultDeliveryAddressEntity.getUserDeliveryAddressUuid())) {
					userDeliveryAddressEntity2.setDefault(false);
					userDeliveryAddressDAO.updateUserDeliveryAddress(userDeliveryAddressEntity2);
				}
			}
			defaultDeliveryAddressEntity.setDefault(true);
			userDeliveryAddressDAO.updateUserDeliveryAddress(defaultDeliveryAddressEntity);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}

	@Override
	public List<UserDeliveryAddressDTO> inquiryUserDeliveryAddress(
			UserDTO userDTO) throws BusinessException {
		List<UserDeliveryAddressDTO> list = new ArrayList<UserDeliveryAddressDTO>();
		try{
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			List<UserDeliveryAddressEntity> entityList = userDeliveryAddressDAO.getDeliveryAddressByUser(userEntity);
			for (Iterator<UserDeliveryAddressEntity> iterator = entityList.iterator(); iterator.hasNext();) {
				UserDeliveryAddressEntity userDeliveryAddressEntity = (UserDeliveryAddressEntity) iterator
						.next();
				UserDeliveryAddressDTO userDeliveryAddressDTO = new UserDeliveryAddressDTO();
				deliveryAddressEntity2DTO(userDeliveryAddressEntity, userDeliveryAddressDTO);
				if(userDeliveryAddressEntity.isDefault())
					list.add(0,userDeliveryAddressDTO);
				else
					list.add(userDeliveryAddressDTO);
			}
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return list;
	}

	@Override
	public UserDeliveryAddressDTO inquiryUserDeliveryAddressByUuid(String uuid)
			throws BusinessException {
		try{
				UserDeliveryAddressEntity userDeliveryAddressEntity = userDeliveryAddressDAO.getDeliveryAddressByUuid(uuid);
				UserDeliveryAddressDTO userDeliveryAddressDTO = new UserDeliveryAddressDTO();
				deliveryAddressEntity2DTO(userDeliveryAddressEntity, userDeliveryAddressDTO);
				return userDeliveryAddressDTO;
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public void addProductDelivery(ProductDeliveryDTO deliveryDTO) throws BusinessException {
		try {
			ProductDeliveryEntity deliveryEntity = new ProductDeliveryEntity();
			productDeliveryDTO2Entity(deliveryEntity, deliveryDTO);
			deliveryEntity.setActive(true);
			deliveryEntity.setDefault(false);
			// 商家
			MerchantDTO merchantDTO = deliveryDTO.getMerchantDTO();
			if (merchantDTO != null) {
				MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
				deliveryEntity.setMerchantEntity(merchantEntity);
			}
			productDeliveryDAO.createProductDelivery(deliveryEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}
	
	@Override
	public void updateProductDelivery(ProductDeliveryDTO deliveryDTO) throws BusinessException {
		try {
			ProductDeliveryEntity deliveryEntity = productDeliveryDAO.getDeliveryByUuid(deliveryDTO.getProductDeliveryUuid());
			productDeliveryDTO2Entity(deliveryEntity, deliveryDTO);
			productDeliveryDAO.updateProductDelivery(deliveryEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}
	
	@Override
	public void deleteProductDelivery(ProductDeliveryDTO deliveryDTO) throws BusinessException {
		try {
			ProductDeliveryEntity deliveryEntity = productDeliveryDAO.getDeliveryByUuid(deliveryDTO.getProductDeliveryUuid());
			productDeliveryDAO.deleteProductDelivery(deliveryEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}
	
	@Override
	public void enableProductDelivery(ProductDeliveryDTO deliveryDTO) throws BusinessException {
		try {
			ProductDeliveryEntity deliveryEntity = productDeliveryDAO.getDeliveryByUuid(deliveryDTO.getProductDeliveryUuid());
			deliveryEntity.setActive(true);
			productDeliveryDAO.updateProductDelivery(deliveryEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}
	
	@Override
	public void disableProductDelivery(ProductDeliveryDTO deliveryDTO) throws BusinessException {
		try {
			ProductDeliveryEntity deliveryEntity = productDeliveryDAO.getDeliveryByUuid(deliveryDTO.getProductDeliveryUuid());
			deliveryEntity.setActive(false);
			productDeliveryDAO.updateProductDelivery(deliveryEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}
	
	@Override
	public void defaultProductDelivery(ProductDeliveryDTO deliveryDTO) throws BusinessException {
		try {
			ProductDeliveryEntity deliveryEntity = productDeliveryDAO
					.getDeliveryByUuid(deliveryDTO.getProductDeliveryUuid());
			List<ProductDeliveryEntity> deliveryEntityList = productDeliveryDAO.getDeliverysByMerchant(deliveryEntity.getMerchantEntity());
			for (ProductDeliveryEntity productDeliveryEntity : deliveryEntityList) {
				//将其他同城配送模板设置为非默认
				if(!productDeliveryEntity.getProductDeliveryUuid().equals(deliveryEntity.getProductDeliveryUuid())){
					productDeliveryEntity.setDefault(false);
					productDeliveryDAO.updateProductDelivery(productDeliveryEntity);
				}
			}
			deliveryEntity.setDefault(true);
			productDeliveryDAO.updateProductDelivery(deliveryEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}
	
	@Override
	public void cancelDefaultProductDelivery(ProductDeliveryDTO deliveryDTO) throws BusinessException {
		try {
			ProductDeliveryEntity deliveryEntity = productDeliveryDAO
					.getDeliveryByUuid(deliveryDTO.getProductDeliveryUuid());
			deliveryEntity.setDefault(false);
			productDeliveryDAO.updateProductDelivery(deliveryEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}
	
	@Override
	public List<ProductDeliveryDTO> getProductDeliveries() throws BusinessException{
		List<ProductDeliveryDTO> deliveryDTOList = new ArrayList<ProductDeliveryDTO>();
		try {
			List<ProductDeliveryEntity> deliveryEntityList = productDeliveryDAO.getDeliverys();
			for (ProductDeliveryEntity deliveryEntity : deliveryEntityList) {
				ProductDeliveryDTO deliveryDTO = new ProductDeliveryDTO();
				productDeliveryEntity2DTO(deliveryEntity, deliveryDTO);
				deliveryDTOList.add(deliveryDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return deliveryDTOList;
	}
	
	@Override
	public List<ProductDeliveryDTO> getProductDeliverysByMerchant(MerchantDTO merchantDTO) throws BusinessException{
		List<ProductDeliveryDTO> deliveryDTOList = new ArrayList<ProductDeliveryDTO>();
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			List<ProductDeliveryEntity> deliveryEntityList = productDeliveryDAO.getDeliverysByMerchant(merchantEntity);
			for (ProductDeliveryEntity deliveryEntity : deliveryEntityList) {
				ProductDeliveryDTO deliveryDTO = new ProductDeliveryDTO();
				productDeliveryEntity2DTO(deliveryEntity, deliveryDTO);
				deliveryDTOList.add(deliveryDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return deliveryDTOList;
	}
	
	@Override
	public List<ProductDeliveryDTO> searchProductDelivery(MerchantSearchDTO merchantSearchDTO) throws BusinessException{
		List<ProductDeliveryDTO> deliveryDTOList = new ArrayList<ProductDeliveryDTO>();
		try {
			List<ProductDeliveryEntity> deliveryEntityList = productDeliveryDAO.searchDelivery(merchantSearchDTO);
			for (ProductDeliveryEntity deliveryEntity : deliveryEntityList) {
				ProductDeliveryDTO deliveryDTO = new ProductDeliveryDTO();
				productDeliveryEntity2DTO(deliveryEntity, deliveryDTO);
				deliveryDTOList.add(deliveryDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return deliveryDTOList;
	}
	
	@Override
	public int searchProductDeliveryTotal(MerchantSearchDTO merchantSearchDTO) throws BusinessException{
		try {
			return productDeliveryDAO.searchDeliveryTotal(merchantSearchDTO);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}
	
	@Override
	public ProductDeliveryDTO getProductDeliveryByUuid(String uuid) throws BusinessException{
		try {
			ProductDeliveryEntity deliveryEntity = productDeliveryDAO.getDeliveryByUuid(uuid);
			ProductDeliveryDTO deliveryDTO = new ProductDeliveryDTO();
			productDeliveryEntity2DTO(deliveryEntity, deliveryDTO);
			return deliveryDTO;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}
	
	void productDeliveryEntity2DTO(ProductDeliveryEntity deliveryEntity, ProductDeliveryDTO deliveryDTO){
		deliveryDTO.setProductDeliveryUuid(deliveryEntity.getProductDeliveryUuid());
		deliveryDTO.setName(deliveryEntity.getName());
		deliveryDTO.setDescription(deliveryEntity.getDescription());
		deliveryDTO.setActive(deliveryEntity.isActive());
		deliveryDTO.setDefault(deliveryEntity.isDefault());
		deliveryDTO.setDeliveryConditionAmount(deliveryEntity.getDeliveryConditionAmount());
		deliveryDTO.setDeliveryConditionDistance(deliveryEntity.getDeliveryConditionDistance());
		deliveryDTO.setDeliveryFromAddress(deliveryEntity.getDeliveryFromAddress());
		deliveryDTO.setDeliveryFromCity(deliveryEntity.getDeliveryFromCity());
		deliveryDTO.setDeliveryFromDetail(deliveryEntity.getDeliveryFromDetail());
		deliveryDTO.setDeliveryFromDistrict(deliveryEntity.getDeliveryFromDistrict());
		deliveryDTO.setDeliveryFromProvince(deliveryEntity.getDeliveryFromProvince());
		deliveryDTO.setDeliveryFromLatitude(deliveryEntity.getDeliveryFromLatitude());
		deliveryDTO.setDeliveryFromLongitude(deliveryEntity.getDeliveryFromLongitude());
		deliveryDTO.setFirstDistance(deliveryEntity.getFirstDistance());
		deliveryDTO.setFirstPrice(deliveryEntity.getFirstPrice());
		deliveryDTO.setNextDistance(deliveryEntity.getNextDistance());
		deliveryDTO.setNextPrice(deliveryEntity.getNextPrice());
		MerchantEntity merchantEntity = deliveryEntity.getMerchantEntity();
		if(merchantEntity != null) {
			MerchantDTO merchantDTO = new MerchantDTO();
			merchantDTO.setMerchantUuid(merchantEntity.getMerchantUuid());
			merchantDTO.setMerchantName(merchantEntity.getMerchantName());
			deliveryDTO.setMerchantDTO(merchantDTO);
		}
	}
	
	void productDeliveryDTO2Entity(ProductDeliveryEntity deliveryEntity, ProductDeliveryDTO deliveryDTO){
		deliveryEntity.setName(deliveryDTO.getName());
		deliveryEntity.setDescription(deliveryDTO.getDescription());
		deliveryEntity.setDeliveryConditionAmount(deliveryDTO.getDeliveryConditionAmount());
		deliveryEntity.setDeliveryConditionDistance(deliveryDTO.getDeliveryConditionDistance());
		deliveryEntity.setDeliveryFromAddress(deliveryDTO.getDeliveryFromAddress());
		deliveryEntity.setDeliveryFromCity(deliveryDTO.getDeliveryFromCity());
		deliveryEntity.setDeliveryFromDetail(deliveryDTO.getDeliveryFromDetail());
		deliveryEntity.setDeliveryFromDistrict(deliveryDTO.getDeliveryFromDistrict());
		deliveryEntity.setDeliveryFromProvince(deliveryDTO.getDeliveryFromProvince());
		deliveryEntity.setDeliveryFromLatitude(deliveryDTO.getDeliveryFromLatitude());
		deliveryEntity.setDeliveryFromLongitude(deliveryDTO.getDeliveryFromLongitude());
		deliveryEntity.setFirstDistance(deliveryDTO.getFirstDistance());
		deliveryEntity.setFirstPrice(deliveryDTO.getFirstPrice());
		deliveryEntity.setNextDistance(deliveryDTO.getNextDistance());
		deliveryEntity.setNextPrice(deliveryDTO.getNextPrice());
	}
	
}
