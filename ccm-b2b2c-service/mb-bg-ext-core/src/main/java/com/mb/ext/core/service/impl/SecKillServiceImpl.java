package com.mb.ext.core.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mb.ext.core.constant.SecKillConstants;
import com.mb.ext.core.dao.FileDAO;
import com.mb.ext.core.dao.MerchantDAO;
import com.mb.ext.core.dao.SequenceDAO;
import com.mb.ext.core.dao.UserDAO;
import com.mb.ext.core.dao.product.ProductDAO;
import com.mb.ext.core.dao.seckill.SecKillProductDAO;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.entity.seckill.SecKillProductEntity;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.ProductService;
import com.mb.ext.core.service.SecKillService;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.ProductSearchDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.order.SecKillOrderDTO;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.ext.core.service.spec.seckill.SecKillProductDTO;
import com.mb.ext.core.util.RedisUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.service.AbstractService;
import com.mb.framework.util.log.LogHelper;



@Service
@Qualifier("SecKillService")
public class SecKillServiceImpl extends AbstractService implements
		SecKillService<BodyDTO> {
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
	@Qualifier("secKillProductDAO")
	private SecKillProductDAO secKillProductDAO;
	
	@Autowired
	@Qualifier("merchantDAO")
	private MerchantDAO merchantDAO;
	
	@Autowired
	@Qualifier("productDAO")
	private ProductDAO productDAO;
	
	@Autowired
	@Qualifier("ProductService")
	private ProductService ProductService;
	
	@Autowired
	private RedisUtil redisUtil;
	
	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	@Override
	public void addSecKill(SecKillProductDTO secKillProductDTO) throws BusinessException {
		try {
			SecKillProductEntity entity = new SecKillProductEntity();
			ProductDTO productDTO = secKillProductDTO.getProductDTO();
			ProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
			entity.setProductEntity(productEntity);
			entity.setStartTime(secKillProductDTO.getStartTime());
			entity.setEndTime(secKillProductDTO.getEndTime());
			entity.setStatus(SecKillConstants.STATUS_INACTIVE);
			entity.setStock(secKillProductDTO.getStock());
			entity.setUnitPrice(secKillProductDTO.getUnitPrice());
			
			MerchantDTO merchantDTO = secKillProductDTO.getMerchantDTO();
			if(merchantDTO != null){
				MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
				entity.setMerchantEntity(merchantEntity);
			}
			
			secKillProductDAO.addSecKill(entity);
		}catch(DAOException e) {
			logger.error("添加秒杀商品发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
				
		
	}

	@Override
	public void updateSecKill(SecKillProductDTO secKillProductDTO) throws BusinessException {
		try {
			SecKillProductEntity entity = secKillProductDAO.getSecKillByUuid(secKillProductDTO.getSecKillProductUuid());
			ProductDTO productDTO = secKillProductDTO.getProductDTO();
			ProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
			entity.setProductEntity(productEntity);
			entity.setStartTime(secKillProductDTO.getStartTime());
			entity.setEndTime(secKillProductDTO.getEndTime());
			entity.setStock(secKillProductDTO.getStock());
			entity.setUnitPrice(secKillProductDTO.getUnitPrice());
			secKillProductDAO.addSecKill(entity);
		}catch(DAOException e) {
			logger.error("更新秒杀商品发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public void deleteSecKill(SecKillProductDTO secKillProductDTO) throws BusinessException {
		
		try {
			SecKillProductEntity entity = secKillProductDAO.getSecKillByUuid(secKillProductDTO.getSecKillProductUuid());
			if(entity != null) {
				secKillProductDAO.deleteSecKill(entity);
			}
		}catch(DAOException e) {
			logger.error("删除秒杀商品发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public void enableSecKill(SecKillProductDTO secKillProductDTO) throws BusinessException {
		
		try {
			SecKillProductEntity entity = secKillProductDAO.getSecKillByUuid(secKillProductDTO.getSecKillProductUuid());
			if(entity != null) {
				entity.setStatus(SecKillConstants.STATUS_ACTIVE);
				secKillProductDAO.updateSecKill(entity);
			}
		}catch(DAOException e) {
			logger.error("上线秒杀商品发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public void disableSecKill(SecKillProductDTO secKillProductDTO) throws BusinessException {
		
		try {
			SecKillProductEntity entity = secKillProductDAO.getSecKillByUuid(secKillProductDTO.getSecKillProductUuid());
			if(entity != null) {
				entity.setStatus(SecKillConstants.STATUS_INACTIVE);
				secKillProductDAO.updateSecKill(entity);
			}
		}catch(DAOException e) {
			logger.error("下线秒杀商品发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public SecKillProductDTO getSecKill(String uuid) throws BusinessException {
		try {
			SecKillProductEntity entity = secKillProductDAO.getSecKillByUuid(uuid);
			if(entity != null) {
				SecKillProductDTO dto = new SecKillProductDTO();
				secKillProductDetailEntity2DTO(entity, dto);
				return dto;
			}
		}catch(DAOException e) {
			logger.error("根据ID查询秒杀商品发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return null;
	}

	@Override
	public List<SecKillProductDTO> getSecKills() throws BusinessException {
		List<SecKillProductDTO> list = new ArrayList<SecKillProductDTO>();
		try {
			List<SecKillProductEntity> entityList = secKillProductDAO.getAllSecKills();
			for (Iterator<SecKillProductEntity> iterator = entityList.iterator(); iterator.hasNext();) {
				SecKillProductEntity entity = iterator.next();
					SecKillProductDTO dto = new SecKillProductDTO();
					secKillProductListEntity2DTO(entity, dto);
					list.add(dto);
			}
		}catch(DAOException e) {
			logger.error("查询所有秒杀商品发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return list;
	}
	
	@Override
	public List<SecKillProductDTO> getSecKillsByMerchant(MerchantDTO merchantDTO) throws BusinessException {
		List<SecKillProductDTO> list = new ArrayList<SecKillProductDTO>();
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			List<SecKillProductEntity> entityList = secKillProductDAO.getSecKillsByMerchant(merchantEntity);
			for (Iterator<SecKillProductEntity> iterator = entityList.iterator(); iterator.hasNext();) {
				SecKillProductEntity entity = iterator.next();
					SecKillProductDTO dto = new SecKillProductDTO();
					secKillProductListEntity2DTO(entity, dto);
					list.add(dto);
			}
		}catch(DAOException e) {
			logger.error("查询所有秒杀商品发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return list;
	}

	@Override
	public List<SecKillProductDTO> getActiveSecKills() throws BusinessException {
		List<SecKillProductDTO> list = new ArrayList<SecKillProductDTO>();
		try {
			List<SecKillProductEntity> entityList = secKillProductDAO.getActiveSecKills();
			for (Iterator<SecKillProductEntity> iterator = entityList.iterator(); iterator.hasNext();) {
				SecKillProductEntity entity = iterator.next();
					SecKillProductDTO dto = new SecKillProductDTO();
					secKillProductListEntity2DTO(entity, dto);
					list.add(dto);
			}
		}catch(DAOException e) {
			logger.error("查询上线的秒杀商品发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return list;
	}
	
	@Override
	public List<SecKillProductDTO> searchSecKill(ProductSearchDTO productSearchDTO) throws BusinessException {
		List<SecKillProductDTO> list = new ArrayList<SecKillProductDTO>();
		try {
			List<SecKillProductEntity> entityList = secKillProductDAO.searchSecKill(productSearchDTO);
			for (Iterator<SecKillProductEntity> iterator = entityList.iterator(); iterator.hasNext();) {
				SecKillProductEntity entity = iterator.next();
					SecKillProductDTO dto = new SecKillProductDTO();
					secKillProductListEntity2DTO(entity, dto);
					list.add(dto);
			}
		}catch(DAOException e) {
			logger.error("查询上线的秒杀商品发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return list;
	}
	
	@Override
	public int searchSecKillTotal(ProductSearchDTO productSearchDTO) throws BusinessException {
		try {
			return secKillProductDAO.searchSecKillTotal(productSearchDTO);
		}catch(DAOException e) {
			logger.error("查询上线的秒杀商品发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public String getSecKillQuanId(SecKillOrderDTO secKillOrderDTO) throws BusinessException {
		try {
			String userId = secKillOrderDTO.getUserDTO().getUserUuid();
			String secKillId = secKillOrderDTO.getSecKillProduct().getSecKillProductUuid();
			
			//禁止为某个用户生成多个资格ID
			String key = userId+"-"+secKillId;
			Object quanlificationId = redisUtil.get(key);
			if(quanlificationId != null)	
				throw new BusinessException(BusinessErrorCode.SECKILL_ORDER_STOCK_DUPLICATE);
			
			//检查库存是否足够, 如果足够则减库存
			Object stock = redisUtil.get("STOCK-"+secKillId);
			if(stock == null) {
				//首次从数据库加载库存
				SecKillProductEntity secKillEntity = secKillProductDAO.getSecKillByUuid(secKillId);
				stock = ((Integer)secKillEntity.getStock()).longValue();
				redisUtil.set("STOCK-"+secKillId,stock);
			}
			if((Long)stock==0) {
				throw new BusinessException(BusinessErrorCode.SECKILL_ORDER_STOCK_INSUFFICIENT);
			}
			//库存减1
			redisUtil.set("STOCK-"+secKillId, (Long)stock-1);
			
			//生成资格ID并缓存
			String randomStr = RandomStringUtils.randomAlphanumeric(32);
			redisUtil.set(userId+"-"+secKillId, randomStr);
			
			return randomStr;
			
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		} catch (BusinessException e) {
			throw e;
		}
	}
	
	void secKillProductDetailEntity2DTO(SecKillProductEntity entity, SecKillProductDTO dto){
		dto.setSecKillProductUuid(entity.getSecKillProductUuid());
		dto.setStartTime(entity.getStartTime());
		dto.setEndTime(entity.getEndTime());
		dto.setStatus(entity.getStatus());
		dto.setStock(entity.getStock());
		dto.setSoldUnit(entity.getSoldUnit());
		dto.setUnitPrice(entity.getUnitPrice());
		ProductEntity productEntity = entity.getProductEntity();
		ProductDTO productDTO = new ProductDTO();
		ProductService.productDetailEntity2DTO(productEntity, productDTO);
		dto.setProductDTO(productDTO);
	}
	
	void secKillProductListEntity2DTO(SecKillProductEntity entity, SecKillProductDTO dto){
		dto.setSecKillProductUuid(entity.getSecKillProductUuid());
		dto.setStartTime(entity.getStartTime());
		dto.setEndTime(entity.getEndTime());
		dto.setStatus(entity.getStatus());
		dto.setStock(entity.getStock());
		dto.setSoldUnit(entity.getSoldUnit());
		dto.setUnitPrice(entity.getUnitPrice());
		ProductEntity productEntity = entity.getProductEntity();
		ProductDTO productDTO = new ProductDTO();
		ProductService.productEntity2DTO(productEntity, productDTO);
		dto.setProductDTO(productDTO);
	}
	
}
