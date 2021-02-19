package com.mb.ext.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mb.ext.core.constant.GroupBuyConstants;
import com.mb.ext.core.constant.GroupConstants;
import com.mb.ext.core.constant.OrderConstants;
import com.mb.ext.core.dao.FileDAO;
import com.mb.ext.core.dao.MerchantDAO;
import com.mb.ext.core.dao.SequenceDAO;
import com.mb.ext.core.dao.UserDAO;
import com.mb.ext.core.dao.group.GroupBuyDAO;
import com.mb.ext.core.dao.group.GroupBuyDefDAO;
import com.mb.ext.core.dao.group.GroupBuyProductDAO;
import com.mb.ext.core.dao.group.GroupBuyUserDAO;
import com.mb.ext.core.dao.order.OrderDAO;
import com.mb.ext.core.dao.product.ProductDAO;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.group.GroupBuyDefEntity;
import com.mb.ext.core.entity.group.GroupBuyEntity;
import com.mb.ext.core.entity.group.GroupBuyProductEntity;
import com.mb.ext.core.entity.group.GroupBuyUserEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.order.OrderEntity;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.GroupBuyService;
import com.mb.ext.core.service.ProductService;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.GroupBuySearchDTO;
import com.mb.ext.core.service.spec.ProductSearchDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.group.GroupBuyDTO;
import com.mb.ext.core.service.spec.group.GroupBuyDefDTO;
import com.mb.ext.core.service.spec.group.GroupBuyProductDTO;
import com.mb.ext.core.service.spec.group.GroupBuyUserDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.order.OrderDTO;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.service.AbstractService;
import com.mb.framework.util.log.LogHelper;



@Service
@Qualifier("GroupBuyService")
public class GroupBuyServiceImpl extends AbstractService implements
		GroupBuyService<BodyDTO> {
	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;

	@Autowired
	@Qualifier("fileDAO")
	private FileDAO fileDAO;

	@Autowired
	@Qualifier("sequenceDAO")
	private SequenceDAO sequenceDAO;
	
	@Autowired
	@Qualifier("orderDAO")
	private OrderDAO orderDAO;

	@Autowired
	@Qualifier("ProductService")
	private ProductService productService;
	
	@Autowired
	@Qualifier("groupBuyProductDAO")
	private GroupBuyProductDAO groupProductDAO;
	
	@Autowired
	@Qualifier("groupBuyDefDAO")
	private GroupBuyDefDAO groupBuyDefDAO;
	
	@Autowired
	@Qualifier("groupBuyDAO")
	private GroupBuyDAO groupBuyDAO;
	
	@Autowired
	@Qualifier("groupBuyUserDAO")
	private GroupBuyUserDAO groupBuyUserDAO;
	
	@Autowired
	@Qualifier("productDAO")
	private ProductDAO productDAO;
	
	@Autowired
	@Qualifier("merchantDAO")
	private MerchantDAO merchantDAO;
	
	@Autowired
	@Qualifier("ProductService")
	private ProductService ProductService;
	
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	@Override
	public void addGroupBuyDef(GroupBuyDefDTO groupBuyDefDTO) throws BusinessException {
		try {
			GroupBuyDefEntity entity = new GroupBuyDefEntity();
			List<GroupBuyProductDTO> productBuyProductDTOList = groupBuyDefDTO.getGroupBuyProductList();
			entity.setMinUserCount(groupBuyDefDTO.getMinUserCount());
			entity.setMaxTranDays(groupBuyDefDTO.getMaxTranDays());
			entity.setStartTime(groupBuyDefDTO.getStartTime());
			entity.setEndTime(groupBuyDefDTO.getEndTime());
			entity.setStatus(GroupConstants.STATUS_INACTIVE);
			entity.setName(groupBuyDefDTO.getName());
			
			MerchantDTO merchantDTO = groupBuyDefDTO.getMerchantDTO();
			if(merchantDTO != null) {
				MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
				entity.setMerchantEntity(merchantEntity);
			}
			groupBuyDefDAO.addGroupBuy(entity);
			
			for (GroupBuyProductDTO groupBuyProductDTO : productBuyProductDTOList) {
				ProductDTO productDTO = groupBuyProductDTO.getProductDTO();
				ProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
				GroupBuyProductEntity groupBuyProductEntity = new GroupBuyProductEntity();
				groupBuyProductEntity.setProductEntity(productEntity);
				groupBuyProductEntity.setUnitPrice(groupBuyProductDTO.getUnitPrice());
				groupBuyProductEntity.setStock(groupBuyProductDTO.getStock());
				groupBuyProductEntity.setGroupBuyDefEntity(entity);
				groupBuyProductEntity.setMerchantEntity(entity.getMerchantEntity());
				groupProductDAO.addGroupBuy(groupBuyProductEntity);
			}
		}catch(DAOException e) {
			logger.error("添加团购活动发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
				
		
	}

	@Override
	public void updateGroupBuyDef(GroupBuyDefDTO groupBuyDefDTO) throws BusinessException {
		try {
			GroupBuyDefEntity entity = groupBuyDefDAO.getGroupBuyByUuid(groupBuyDefDTO.getGroupBuyDefUuid());
			List<GroupBuyProductDTO> productBuyProductDTOList = groupBuyDefDTO.getGroupBuyProductList();
			entity.setMinUserCount(groupBuyDefDTO.getMinUserCount());
			entity.setMaxTranDays(groupBuyDefDTO.getMaxTranDays());
			entity.setStartTime(groupBuyDefDTO.getStartTime());
			entity.setEndTime(groupBuyDefDTO.getEndTime());
			entity.setName(groupBuyDefDTO.getName());
			groupBuyDefDAO.updateGroupBuy(entity);
			
			
			List<GroupBuyProductEntity> groupBuyProductEntityList = entity.getGroupBuyProductList();
			entity.setGroupBuyProductList(null);
			for (GroupBuyProductEntity groupBuyProductEntity : groupBuyProductEntityList) {
				groupProductDAO.deleteGroupBuy(groupBuyProductEntity);
			}
			for (GroupBuyProductDTO groupBuyProductDTO : productBuyProductDTOList) {
				ProductDTO productDTO = groupBuyProductDTO.getProductDTO();
				ProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
				GroupBuyProductEntity groupBuyProductEntity = new GroupBuyProductEntity();
				groupBuyProductEntity.setProductEntity(productEntity);
				groupBuyProductEntity.setUnitPrice(groupBuyProductDTO.getUnitPrice());
				groupBuyProductEntity.setStock(groupBuyProductDTO.getStock());
				groupBuyProductEntity.setGroupBuyDefEntity(entity);
				groupBuyProductEntity.setMerchantEntity(entity.getMerchantEntity());
				groupProductDAO.addGroupBuy(groupBuyProductEntity);
			}
		}catch(DAOException e) {
			logger.error("更新团购活动发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public void deleteGroupBuyDef(GroupBuyDefDTO groupBuyDefDTO) throws BusinessException {
		
		try {
			GroupBuyDefEntity entity = groupBuyDefDAO.getGroupBuyByUuid(groupBuyDefDTO.getGroupBuyDefUuid());
			if(entity != null) {
				groupBuyDefDAO.deleteGroupBuy(entity);
			}
		}catch(DAOException e) {
			logger.error("删除团购活动发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public void enableGroupBuyDef(GroupBuyDefDTO groupBuyDefDTO) throws BusinessException {
		
		try {
			GroupBuyDefEntity entity = groupBuyDefDAO.getGroupBuyByUuid(groupBuyDefDTO.getGroupBuyDefUuid());
			if(entity != null) {
				entity.setStatus(GroupConstants.STATUS_ACTIVE);
				groupBuyDefDAO.updateGroupBuy(entity);
			}
		}catch(DAOException e) {
			logger.error("上线团购商品发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public void disableGroupBuyDef(GroupBuyDefDTO groupBuyDefDTO) throws BusinessException {
		
		try {
			GroupBuyDefEntity entity = groupBuyDefDAO.getGroupBuyByUuid(groupBuyDefDTO.getGroupBuyDefUuid());
			if(entity != null) {
				entity.setStatus(GroupConstants.STATUS_INACTIVE);
				groupBuyDefDAO.updateGroupBuy(entity);
			}
		}catch(DAOException e) {
			logger.error("下线团购商品发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public GroupBuyDefDTO getGroupBuyDef(String uuid) throws BusinessException {
		try {
			GroupBuyDefEntity entity = groupBuyDefDAO.getGroupBuyByUuid(uuid);
			if(entity != null) {
				GroupBuyDefDTO dto = new GroupBuyDefDTO();
				groupBuyDefDetailEntity2DTO(entity, dto);
				return dto;
			}
		}catch(DAOException e) {
			logger.error("根据ID查询团购活动发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return null;
	}

	@Override
	public List<GroupBuyDefDTO> getGroupBuyDefs() throws BusinessException {
		List<GroupBuyDefDTO> list = new ArrayList<GroupBuyDefDTO>();
		try {
			List<GroupBuyDefEntity> entityList = groupBuyDefDAO.getAllGroupBuys();
			for (Iterator<GroupBuyDefEntity> iterator = entityList.iterator(); iterator.hasNext();) {
				GroupBuyDefEntity entity = iterator.next();
					GroupBuyDefDTO dto = new GroupBuyDefDTO();
					groupBuyDefListEntity2DTO(entity, dto);
					list.add(dto);
			}
		}catch(DAOException e) {
			logger.error("查询所有团购活动发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return list;
	}

	@Override
	public List<GroupBuyDefDTO> getActiveGroupBuyDefs() throws BusinessException {
		List<GroupBuyDefDTO> list = new ArrayList<GroupBuyDefDTO>();
		try {
			List<GroupBuyDefEntity> entityList = groupBuyDefDAO.getActiveGroupBuys();
			for (Iterator<GroupBuyDefEntity> iterator = entityList.iterator(); iterator.hasNext();) {
				GroupBuyDefEntity entity = iterator.next();
					GroupBuyDefDTO dto = new GroupBuyDefDTO();
					groupBuyDefListEntity2DTO(entity, dto);
					list.add(dto);
			}
		}catch(DAOException e) {
			logger.error("查询上线团购活动发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return list;
	}
	
	@Override
	public List<GroupBuyDefDTO> searchGroupBuyDef(ProductSearchDTO productSearchDTO) throws BusinessException {
		List<GroupBuyDefDTO> list = new ArrayList<GroupBuyDefDTO>();
		try {
			List<GroupBuyDefEntity> entityList = groupBuyDefDAO.searchGroupBuy(productSearchDTO);
			for (Iterator<GroupBuyDefEntity> iterator = entityList.iterator(); iterator.hasNext();) {
				GroupBuyDefEntity entity = iterator.next();
					GroupBuyDefDTO dto = new GroupBuyDefDTO();
					groupBuyDefListEntity2DTO(entity, dto);
					list.add(dto);
			}
		}catch(DAOException e) {
			logger.error("查询上线团购活动发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return list;
	}
	
	@Override
	public int searchGroupBuyDefTotal(ProductSearchDTO productSearchDTO) throws BusinessException {
		try {
			return groupBuyDefDAO.searchGroupBuyTotal(productSearchDTO);
		}catch(DAOException e) {
			logger.error("查询上线团购活动发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public GroupBuyProductDTO getGroupBuyProduct(String uuid) throws BusinessException {
		try {
			GroupBuyProductEntity groupBuyProductEntity = groupProductDAO.getGroupBuyByUuid(uuid);
			GroupBuyProductDTO dto = new GroupBuyProductDTO();
			groupBuyProductDetailEntity2DTO(groupBuyProductEntity, dto);
			return dto;
		}catch(DAOException e) {
			logger.error("查询团购商品发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public List<GroupBuyDefDTO> getGroupBuyDefsByMerchant(MerchantDTO merchantDTO) throws BusinessException {
		List<GroupBuyDefDTO> list = new ArrayList<GroupBuyDefDTO>();
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			List<GroupBuyDefEntity> entityList = groupBuyDefDAO.getGroupBuysByMerchant(merchantEntity);
			for (Iterator<GroupBuyDefEntity> iterator = entityList.iterator(); iterator.hasNext();) {
				GroupBuyDefEntity entity = iterator.next();
				GroupBuyDefDTO dto = new GroupBuyDefDTO();
				groupBuyDefListEntity2DTO(entity, dto);
				list.add(dto);
			}
		}catch(DAOException e) {
			logger.error("查询所有团购商品发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return list;
	}

	@Override
	public List<GroupBuyDTO> getActiveGroupBuysByGroupBuyProduct(String groupBuyProductUuid) throws BusinessException{
		try {
			return groupBuyDAO.getActiveGroupBuysByGroupBuyProduct(groupBuyProductUuid);
		}catch(DAOException e) {
			logger.error("查询正在进行中的团购活动发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	void groupBuyDefListEntity2DTO(GroupBuyDefEntity entity, GroupBuyDefDTO dto){
		dto.setGroupBuyDefUuid(entity.getGroupBuyDefUuid());
		dto.setName(entity.getName());
		dto.setStatus(entity.getStatus());
		dto.setStartTime(entity.getStartTime());
		dto.setEndTime(entity.getEndTime());
		dto.setMaxTranDays(entity.getMaxTranDays());
		dto.setMinUserCount(entity.getMinUserCount());
		List<GroupBuyProductEntity> groupBuyProductEntityList = entity.getGroupBuyProductList();
		List<GroupBuyProductDTO> groupBuyProductDTOList = new ArrayList<GroupBuyProductDTO>();
		for (GroupBuyProductEntity groupBuyProductEntity : groupBuyProductEntityList) {
			GroupBuyProductDTO groupBuyProductDTO = new GroupBuyProductDTO();
			groupBuyProductListEntity2DTO(groupBuyProductEntity,groupBuyProductDTO);
			groupBuyProductDTOList.add(groupBuyProductDTO);
		}
		dto.setGroupBuyProductList(groupBuyProductDTOList);
		MerchantEntity merchantEntity = entity.getMerchantEntity();
		if(merchantEntity != null) {
			MerchantDTO merchantDTO = new MerchantDTO();
			merchantDTO.setMerchantUuid(merchantEntity.getMerchantUuid());
			merchantDTO.setMerchantName(merchantEntity.getMerchantName());
			merchantDTO.setMerchantAddress(merchantEntity.getMerchantAddress());
			merchantDTO.setMobileNo(merchantEntity.getMobileNo());
			merchantDTO.setContactName(merchantEntity.getContactName());
			dto.setMerchantDTO(merchantDTO);
		}
	}
	
	void groupBuyDefDetailEntity2DTO(GroupBuyDefEntity entity, GroupBuyDefDTO dto){
		dto.setGroupBuyDefUuid(entity.getGroupBuyDefUuid());
		dto.setName(entity.getName());
		dto.setStatus(entity.getStatus());
		dto.setStartTime(entity.getStartTime());
		dto.setEndTime(entity.getEndTime());
		dto.setMaxTranDays(entity.getMaxTranDays());
		dto.setMinUserCount(entity.getMinUserCount());
		List<GroupBuyProductEntity> groupBuyProductEntityList = entity.getGroupBuyProductList();
		List<GroupBuyProductDTO> groupBuyProductDTOList = new ArrayList<GroupBuyProductDTO>();
		for (GroupBuyProductEntity groupBuyProductEntity : groupBuyProductEntityList) {
			GroupBuyProductDTO groupBuyProductDTO = new GroupBuyProductDTO();
			groupBuyProductDetailEntity2DTO(groupBuyProductEntity,groupBuyProductDTO);
			groupBuyProductDTOList.add(groupBuyProductDTO);
		}
		dto.setGroupBuyProductList(groupBuyProductDTOList);
		MerchantEntity merchantEntity = entity.getMerchantEntity();
		if(merchantEntity != null) {
			MerchantDTO merchantDTO = new MerchantDTO();
			merchantDTO.setMerchantUuid(merchantEntity.getMerchantUuid());
			merchantDTO.setMerchantName(merchantEntity.getMerchantName());
			merchantDTO.setMerchantAddress(merchantEntity.getMerchantAddress());
			merchantDTO.setMobileNo(merchantEntity.getMobileNo());
			merchantDTO.setContactName(merchantEntity.getContactName());
			dto.setMerchantDTO(merchantDTO);
		}
	}

	
	void groupBuyProductDetailEntity2DTO(GroupBuyProductEntity entity, GroupBuyProductDTO dto){
		dto.setGroupBuyProductUuid(entity.getGroupBuyProductUuid());
		dto.setStock(entity.getStock());
		dto.setUnitPrice(entity.getUnitPrice());
		dto.setMinUserCount(entity.getGroupBuyDefEntity().getMinUserCount());
		dto.setMaxTranDays(entity.getGroupBuyDefEntity().getMaxTranDays());
		ProductEntity productEntity = entity.getProductEntity();
		ProductDTO productDTO = new ProductDTO();
		ProductService.productDetailEntity2DTO(productEntity, productDTO);
		dto.setProductDTO(productDTO);
		MerchantEntity merchantEntity = entity.getMerchantEntity();
		if(merchantEntity != null) {
			MerchantDTO merchantDTO = new MerchantDTO();
			merchantDTO.setMerchantUuid(merchantEntity.getMerchantUuid());
			merchantDTO.setMerchantName(merchantEntity.getMerchantName());
			merchantDTO.setMerchantAddress(merchantEntity.getMerchantAddress());
			merchantDTO.setMobileNo(merchantEntity.getMobileNo());
			merchantDTO.setContactName(merchantEntity.getContactName());
			dto.setMerchantDTO(merchantDTO);
		}
	}
	
	void groupBuyProductListEntity2DTO(GroupBuyProductEntity entity, GroupBuyProductDTO dto){
		dto.setGroupBuyProductUuid(entity.getGroupBuyProductUuid());
		dto.setStock(entity.getStock());
		dto.setSoldUnit(entity.getSoldUnit());
		dto.setUnitPrice(entity.getUnitPrice());
		dto.setMinUserCount(entity.getGroupBuyDefEntity().getMinUserCount());
		dto.setMaxTranDays(entity.getGroupBuyDefEntity().getMaxTranDays());
		ProductEntity productEntity = entity.getProductEntity();
		ProductDTO productDTO = new ProductDTO();
		ProductService.productEntity2DTO(productEntity, productDTO);
		dto.setProductDTO(productDTO);
		MerchantEntity merchantEntity = entity.getMerchantEntity();
		if(merchantEntity != null) {
			MerchantDTO merchantDTO = new MerchantDTO();
			merchantDTO.setMerchantUuid(merchantEntity.getMerchantUuid());
			merchantDTO.setMerchantName(merchantEntity.getMerchantName());
			merchantDTO.setMerchantAddress(merchantEntity.getMerchantAddress());
			merchantDTO.setMobileNo(merchantEntity.getMobileNo());
			merchantDTO.setContactName(merchantEntity.getContactName());
			dto.setMerchantDTO(merchantDTO);
		}
	}

	@Override
	public String addGroupBuy(GroupBuyUserDTO groupUserDTO) throws BusinessException {
		
		try {
			GroupBuyEntity groupBuyEntity = new GroupBuyEntity(); 
			UserDTO ownerDTO = groupUserDTO.getUserDTO();
			UserEntity ownerEntity = userDAO.getUserByUuid(ownerDTO.getUserUuid());
			groupBuyEntity.setOwnerEntity(ownerEntity);
			GroupBuyProductDTO groupBuyProductDTO = groupUserDTO.getGroupBuyDTO().getGroupBuyProductDTO();
			GroupBuyProductEntity groupBuyProductEntity = groupProductDAO. getGroupBuyByUuid(groupBuyProductDTO.getGroupBuyProductUuid());
			groupBuyEntity.setGroupBuyProductEntity(groupBuyProductEntity);
			groupBuyEntity.setStartTime(new Date());
			groupBuyEntity.setEndTime(DateUtils.addDays(groupBuyEntity.getStartTime(),groupBuyProductEntity.getGroupBuyDefEntity().getMaxTranDays()));
			groupBuyEntity.setStatus(GroupBuyConstants.STATUS_IN_PROGRESS);
			groupBuyDAO.addGroupBuy(groupBuyEntity);
			
			GroupBuyUserEntity groupBuyUserEntity = new GroupBuyUserEntity();
			groupBuyUserEntity.setGroupBuyEntity(groupBuyEntity);
			groupBuyUserEntity.setUserEntity(ownerEntity);
			groupBuyUserEntity.setOwner(true);
			groupBuyUserEntity.setJoinTime(groupBuyEntity.getStartTime());
			
			OrderDTO orderDTO = groupUserDTO.getOrderDTO();
			OrderEntity orderEntity = orderDAO.getOrderByOrderNo(orderDTO.getOrderNo());
			groupBuyUserEntity.setOrderEntity(orderEntity);
			
			groupBuyUserDAO.addGroupBuyUser(groupBuyUserEntity);
			
			return groupBuyEntity.getGroupBuyUuid();
		}catch(DAOException e) {
			logger.error("发起拼团发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public String joinGroupBuy(GroupBuyUserDTO groupUserDTO) throws BusinessException {
		
		try {
			GroupBuyEntity groupBuyEntity = groupBuyDAO.getGroupBuyByUuid(groupUserDTO.getGroupBuyDTO().getGroupBuyUuid()); 
			
			UserDTO userDTO = groupUserDTO.getUserDTO();
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			
			GroupBuyUserEntity groupBuyUserEntity = new GroupBuyUserEntity();
			groupBuyUserEntity.setGroupBuyEntity(groupBuyEntity);
			groupBuyUserEntity.setUserEntity(userEntity);
			groupBuyUserEntity.setOwner(false);
			groupBuyUserEntity.setJoinTime(groupBuyEntity.getStartTime());
			
			OrderDTO orderDTO = groupUserDTO.getOrderDTO();
			OrderEntity orderEntity = orderDAO.getOrderByOrderNo(orderDTO.getOrderNo());
			groupBuyUserEntity.setOrderEntity(orderEntity);
			groupBuyUserDAO.addGroupBuyUser(groupBuyUserEntity);
			
			return groupBuyEntity.getGroupBuyUuid();
		}catch(DAOException e) {
			logger.error("参与拼团发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}
	
	@Override
	public GroupBuyProductDTO getGroupBuy(String uuid) throws BusinessException {
		try {
			GroupBuyProductEntity entity = groupProductDAO.getGroupBuyByUuid(uuid);
			if(entity != null) {
				GroupBuyProductDTO dto = new GroupBuyProductDTO();
				groupBuyProductDetailEntity2DTO(entity, dto);
				return dto;
			}
		}catch(DAOException e) {
			logger.error("根据ID查询团购商品发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return null;
	}
	
	@Override
	public GroupBuyDTO groupBuyDetail(String uuid) throws BusinessException {
		try {
				GroupBuyEntity groupBuyEntity = groupBuyDAO.getGroupBuyByUuid(uuid);
				GroupBuyDTO groupBuyDTO = new GroupBuyDTO();
				groupBuyEntity2DTO(groupBuyEntity, groupBuyDTO);
				return groupBuyDTO;
		}catch(DAOException e) {
			logger.error("搜索团购单发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}

	@Override
	public List<GroupBuyDTO> searchGroupBuy(GroupBuySearchDTO searchDTO) throws BusinessException {
		List<GroupBuyDTO> groupBuyDTOList = new ArrayList<GroupBuyDTO>();
		try {
			List<GroupBuyEntity>  entityList = groupBuyDAO.searchGroupBuy(searchDTO);
			for (GroupBuyEntity groupBuyEntity : entityList) {
				GroupBuyDTO groupBuyDTO = new GroupBuyDTO();
				groupBuyEntity2DTO(groupBuyEntity, groupBuyDTO);
				groupBuyDTOList.add(groupBuyDTO);
			}
			
		}catch(DAOException e) {
			logger.error("搜索团购单发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return groupBuyDTOList;
	}

	@Override
	public int searchGroupBuyTotal(GroupBuySearchDTO searchDTO) throws BusinessException {
		try {
			return groupBuyDAO.searchGroupBuyTotal(searchDTO);
		}catch(DAOException e) {
			logger.error("搜索团购单数量发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public List<GroupBuyProductDTO> getBeingGroupBuyProducts() throws BusinessException {
		List<GroupBuyProductDTO> list = new ArrayList<GroupBuyProductDTO>();
		try {
			List<GroupBuyProductEntity> entityList = groupProductDAO.getBeingGroupBuyProducts();
			for (Iterator<GroupBuyProductEntity> iterator = entityList.iterator(); iterator.hasNext();) {
				GroupBuyProductEntity entity = iterator.next();
				GroupBuyProductDTO dto = new GroupBuyProductDTO();
				groupBuyProductListEntity2DTO(entity, dto);
				list.add(dto);
			}
		}catch(DAOException e) {
			logger.error("查询正在团购商品发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return list;
	}

	@Override
	public List<GroupBuyProductDTO> getComingGroupBuyProducts() throws BusinessException {
		List<GroupBuyProductDTO> list = new ArrayList<GroupBuyProductDTO>();
		try {
			List<GroupBuyProductEntity> entityList = groupProductDAO.getComingGroupBuyProducts();
			for (Iterator<GroupBuyProductEntity> iterator = entityList.iterator(); iterator.hasNext();) {
				GroupBuyProductEntity entity = iterator.next();
				GroupBuyProductDTO dto = new GroupBuyProductDTO();
				groupBuyProductListEntity2DTO(entity, dto);
				list.add(dto);
			}
		}catch(DAOException e) {
			logger.error("查询即将开始团购商品发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return list;
	}
	
	void groupBuyEntity2DTO(GroupBuyEntity groupBuyEntity, GroupBuyDTO groupBuyDTO) {
		groupBuyDTO.setGroupBuyUuid(groupBuyEntity.getGroupBuyUuid());
		groupBuyDTO.setEndTime(groupBuyEntity.getEndTime());
		groupBuyDTO.setStartTime(groupBuyEntity.getStartTime());
		groupBuyDTO.setStatus(groupBuyEntity.getStatus());
		//如果状态字段为进行中但时间已过期则返回失败
		if(GroupBuyConstants.STATUS_IN_PROGRESS.equals(groupBuyEntity.getStatus()) && new Date().after(groupBuyEntity.getEndTime())) {
			groupBuyDTO.setStatus(GroupBuyConstants.STATUS_FAILED);
		}
		UserEntity ownerEntity = groupBuyEntity.getOwnerEntity();
		if(ownerEntity != null) {
			UserDTO ownerDTO = new UserDTO();
			ownerDTO.setUserUuid(ownerEntity.getUserUuid());
			ownerDTO.setName(ownerEntity.getName());
			ownerDTO.setPhotoUrl(ownerEntity.getPhotoUrl());
			groupBuyDTO.setOwnerDTO(ownerDTO);
		}
		GroupBuyProductEntity groupBuyProductEntity = groupBuyEntity.getGroupBuyProductEntity();
		if(groupBuyProductEntity != null) {
			GroupBuyProductDTO groupBuyProductDTO = new GroupBuyProductDTO();
			groupBuyProductDTO.setGroupBuyProductUuid(groupBuyProductEntity.getGroupBuyProductUuid());
			groupBuyProductDTO.setMaxTranDays(groupBuyProductEntity.getGroupBuyDefEntity().getMaxTranDays());
			groupBuyProductDTO.setMinUserCount(groupBuyProductEntity.getGroupBuyDefEntity().getMinUserCount());
			groupBuyProductDTO.setUnitPrice(groupBuyProductEntity.getUnitPrice());
			groupBuyProductDTO.setStock(groupBuyProductEntity.getStock());
			ProductEntity productEntity = groupBuyProductEntity.getProductEntity();
			ProductDTO productDTO = new ProductDTO();
			productService.productDetailEntity2DTO(productEntity, productDTO);
			groupBuyProductDTO.setProductDTO(productDTO);
			groupBuyDTO.setGroupBuyProductDTO(groupBuyProductDTO);
			GroupBuyDefEntity groupBuyDefEntity = groupBuyProductEntity.getGroupBuyDefEntity();
			GroupBuyDefDTO groupBuyDefDTO = new GroupBuyDefDTO();
			groupBuyDefDTO.setName(groupBuyDefEntity.getName());
			groupBuyDefDTO.setStartTime(groupBuyDefEntity.getStartTime());
			groupBuyDefDTO.setEndTime(groupBuyDefEntity.getEndTime());
			groupBuyDefDTO.setMaxTranDays(groupBuyDefEntity.getMaxTranDays());
			groupBuyDefDTO.setMinUserCount(groupBuyDefEntity.getMinUserCount());
			groupBuyDefDTO.setGroupBuyDefUuid(groupBuyDefEntity.getGroupBuyDefUuid());
			groupBuyProductDTO.setGroupBuyDefDTO(groupBuyDefDTO);
			groupBuyDTO.setGroupBuyProductDTO(groupBuyProductDTO);
		}
		
		List<GroupBuyUserEntity> groupBuyUserEntityList =  groupBuyEntity.getGroupBuyUserList();
		List<GroupBuyUserDTO> groupBuyUserDTOList = new ArrayList<GroupBuyUserDTO>();
		for (GroupBuyUserEntity groupBuyUserEntity : groupBuyUserEntityList) {
			GroupBuyUserDTO groupBuyUserDTO = new GroupBuyUserDTO();
			OrderEntity orderEntity = groupBuyUserEntity.getOrderEntity();
			if(orderEntity != null && !OrderConstants.ORDER_STATUS_NOT_PAYED.equals(orderEntity.getOrderStatus()) && !OrderConstants.ORDER_STATUS_CANCELLED.equals(orderEntity.getOrderStatus())) {
				groupBuyUserDTO.setOwner(groupBuyUserEntity.isOwner());
				groupBuyUserDTO.setJoinTime(groupBuyUserEntity.getJoinTime());
				UserEntity userEntity = groupBuyUserEntity.getUserEntity();
				UserDTO userDTO = new UserDTO();
				userDTO.setUserUuid(userEntity.getUserUuid());
				userDTO.setName(userEntity.getName());
				userDTO.setPhotoUrl(userEntity.getPhotoUrl());
				groupBuyUserDTO.setUserDTO(userDTO);
				OrderDTO orderDTO = new OrderDTO();
				orderDTO.setOrderNo(orderEntity.getOrderNo());
				orderDTO.setOrderStatus(orderEntity.getOrderStatus());
				groupBuyUserDTO.setOrderDTO(orderDTO);
				groupBuyUserDTOList.add(groupBuyUserDTO);
			}

		}
		groupBuyDTO.setGroupBuyUserList(groupBuyUserDTOList);
	}
	
}
