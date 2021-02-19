package com.mb.ext.core.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mb.ext.core.constant.ProductConstants;
import com.mb.ext.core.dao.product.ProductDAO;
import com.mb.ext.core.dao.product.ProductInventoryInboundDAO;
import com.mb.ext.core.dao.product.ProductInventoryOutboundDAO;
import com.mb.ext.core.dao.product.ProductInventoryTakingDAO;
import com.mb.ext.core.dao.product.ProductSkuAttrValueDAO;
import com.mb.ext.core.dao.product.ProductSkuDAO;
import com.mb.ext.core.entity.FileEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.entity.product.ProductInventoryInboundEntity;
import com.mb.ext.core.entity.product.ProductInventoryOutboundEntity;
import com.mb.ext.core.entity.product.ProductInventoryTakingEntity;
import com.mb.ext.core.entity.product.ProductSkuAttrValueEntity;
import com.mb.ext.core.entity.product.ProductSkuEntity;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.InventoryService;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.FileDTO;
import com.mb.ext.core.service.spec.ProductSearchDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.ext.core.service.spec.product.ProductInventoryInboundDTO;
import com.mb.ext.core.service.spec.product.ProductInventoryOutboundDTO;
import com.mb.ext.core.service.spec.product.ProductInventoryTakingDTO;
import com.mb.ext.core.service.spec.product.ProductSkuAttrValueDTO;
import com.mb.ext.core.service.spec.product.ProductSkuDTO;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.service.AbstractService;
import com.mb.framework.util.log.LogHelper;

@Service
@Qualifier("InventoryService")
public class InventoryServiceImpl extends AbstractService implements
		InventoryService<BodyDTO> {

	@Autowired
	@Qualifier("productDAO")
	private ProductDAO productDAO;
	
	@Autowired
	@Qualifier("productSkuDAO")
	private ProductSkuDAO productSkuDAO;

	@Autowired
	@Qualifier("productInventoryInboundDAO")
	private ProductInventoryInboundDAO productInventoryInboundDAO;
	
	@Autowired
	@Qualifier("productInventoryOutboundDAO")
	private ProductInventoryOutboundDAO productInventoryOutboundDAO;
	
	@Autowired
	@Qualifier("productInventoryTakingDAO")
	private ProductInventoryTakingDAO productInventoryTakingDAO;
	
	@Autowired
	@Qualifier("productSkuAttrValueDAO")
	private ProductSkuAttrValueDAO productSkuAttrValueDAO;
	
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());
	
	@Override
	public void addProductInventoryInbound(ProductInventoryInboundDTO inboundDTO) throws BusinessException {

		
		try {
			ProductDTO productDTO = inboundDTO.getProductDTO();
			ProductSkuDTO productSkuDTO = inboundDTO.getProductSkuDTO();
			ProductInventoryInboundEntity inboundEntity = new ProductInventoryInboundEntity();
			ProductEntity productEntity = null;
			ProductSkuEntity productSkuEntity = null;
			inboundEntity.setTranType(inboundDTO.getTranType());
			inboundEntity.setTranUnit(inboundDTO.getTranUnit());
			inboundEntity.setTranTime(inboundDTO.getTranTime());
			inboundEntity.setMemo(inboundDTO.getMemo());
			productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
			inboundEntity.setProductEntity(productEntity);
			if(productSkuDTO != null) {
				productSkuEntity = productSkuDAO.getProductSkuByUuid(productSkuDTO.getProductSkuUuid());
				inboundEntity.setSkuName(inboundDTO.getSkuName());
				inboundEntity.setProductSkuEntity(productSkuEntity);
			}
			//创建出库记录
			productInventoryInboundDAO.createProductInventoryInbound(inboundEntity);
			
			//更新出库后商品库存
			if(productSkuEntity != null) {
				productSkuEntity.setSkuTotalUnit(productSkuEntity.getSkuTotalUnit()+inboundDTO.getTranUnit());
				productSkuDAO.updateProductSku(productSkuEntity);
			}else {
				productEntity.setTotalUnit(productEntity.getTotalUnit()+inboundDTO.getTranUnit());
				productDAO.updateProduct(productEntity);
			}
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public void addProductInventoryOutbound(ProductInventoryOutboundDTO outboundDTO) throws BusinessException {
		
		try {
			ProductDTO productDTO = outboundDTO.getProductDTO();
			ProductSkuDTO productSkuDTO = outboundDTO.getProductSkuDTO();
			ProductInventoryOutboundEntity outboundEntity = new ProductInventoryOutboundEntity();
			ProductEntity productEntity = null;
			ProductSkuEntity productSkuEntity = null;
			outboundEntity.setTranType(outboundDTO.getTranType());
			outboundEntity.setTranUnit(outboundDTO.getTranUnit());
			outboundEntity.setTranTime(outboundDTO.getTranTime());
			outboundEntity.setMemo(outboundDTO.getMemo());
			productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
			outboundEntity.setProductEntity(productEntity);
			if(productSkuDTO != null) {
				productSkuEntity = productSkuDAO.getProductSkuByUuid(productSkuDTO.getProductSkuUuid());
				outboundEntity.setProductSkuEntity(productSkuEntity);
				outboundEntity.setSkuName(outboundDTO.getSkuName());
				int skuUnit = productSkuEntity.getSkuTotalUnit();
				//库存不足
				if(skuUnit<outboundDTO.getTranUnit()) {
					throw new BusinessException(BusinessErrorCode.PRODUCT_STOCK_INSUFFICIENT);
				}
			}else {
				int totalUnit = productEntity.getTotalUnit();
				//库存不足
				if(totalUnit<outboundDTO.getTranUnit()) {
					throw new BusinessException(BusinessErrorCode.PRODUCT_STOCK_INSUFFICIENT);
				}
			}
			//创建出库记录
			productInventoryOutboundDAO.createProductInventoryOutbound(outboundEntity);
			
			//更新出库后商品库存
			if(productSkuEntity != null) {
				productSkuEntity.setSkuTotalUnit(productSkuEntity.getSkuTotalUnit()-outboundDTO.getTranUnit());
				productSkuDAO.updateProductSku(productSkuEntity);
			}else {
				productEntity.setTotalUnit(productEntity.getTotalUnit()-outboundDTO.getTranUnit());
				productDAO.updateProduct(productEntity);
			}
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}catch(BusinessException e) {
			throw e;
		}
		
	}
	
	@Override
	public void addProductInventoryTaking(ProductInventoryTakingDTO takingDTO) throws BusinessException {

		
		try {
			ProductDTO productDTO = takingDTO.getProductDTO();
			ProductSkuDTO productSkuDTO = takingDTO.getProductSkuDTO();
			ProductInventoryTakingEntity takingEntity = new ProductInventoryTakingEntity();
			ProductEntity productEntity = null;
			ProductSkuEntity productSkuEntity = null;
			takingEntity.setTranUnit(takingDTO.getTranUnit());
			takingEntity.setTranTime(takingDTO.getTranTime());
			takingEntity.setMemo(takingDTO.getMemo());
			productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
			takingEntity.setProductEntity(productEntity);
			if(productSkuDTO != null) {
				productSkuEntity = productSkuDAO.getProductSkuByUuid(productSkuDTO.getProductSkuUuid());
				takingEntity.setSkuName(takingDTO.getSkuName());
				takingEntity.setProductSkuEntity(productSkuEntity);
				//盘亏(赢)
				takingEntity.setProfitUnit(takingDTO.getTranUnit()-productSkuEntity.getSkuTotalUnit());
			}else {
				//盘亏(赢)
				takingEntity.setProfitUnit(takingDTO.getTranUnit()-productEntity.getTotalUnit());
			}
			//创建出库记录
			productInventoryTakingDAO.createProductInventoryTaking(takingEntity);
			
			//更新出库后商品库存
			if(productSkuEntity != null) {
				productSkuEntity.setSkuTotalUnit(takingDTO.getTranUnit());
				productSkuDAO.updateProductSku(productSkuEntity);
			}else {
				productEntity.setTotalUnit(takingDTO.getTranUnit());
				productDAO.updateProduct(productEntity);
			}
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		
	}

	@Override
	public ProductInventoryInboundDTO getProducctInventoryInboundByUuid(String uuid) throws BusinessException {
		try {
			ProductInventoryInboundDTO inboundDTO = new ProductInventoryInboundDTO();
			ProductInventoryInboundEntity inboundEntity =  productInventoryInboundDAO.getInventoryInboundByUuid(uuid);
			inboundEntity2DTO(inboundEntity, inboundDTO);
			return inboundDTO;
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}

	@Override
	public ProductInventoryOutboundDTO getProducctInventoryOutboundByUuid(String uuid) throws BusinessException {
		try {
			ProductInventoryOutboundDTO outboundDTO = new ProductInventoryOutboundDTO();
			ProductInventoryOutboundEntity outboundEntity =  productInventoryOutboundDAO.getInventoryOutboundByUuid(uuid);
			outboundEntity2DTO(outboundEntity, outboundDTO);
			return outboundDTO;
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public ProductInventoryTakingDTO getProducctInventoryTakingByUuid(String uuid) throws BusinessException {
		try {
			ProductInventoryTakingDTO takingDTO = new ProductInventoryTakingDTO();
			ProductInventoryTakingEntity takingEntity =  productInventoryTakingDAO.getInventoryTakingByUuid(uuid);
			takingEntity2DTO(takingEntity, takingDTO);
			return takingDTO;
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}

	@Override
	public List<ProductInventoryInboundDTO> searchProducctInventoryInbound(ProductSearchDTO productSearchDTO)
			throws BusinessException {
		List<ProductInventoryInboundDTO> inboundDTOList = new ArrayList<ProductInventoryInboundDTO>();
		try {
			List<ProductInventoryInboundEntity> inboundEntityList= productInventoryInboundDAO.searchProductInventoryInbound(productSearchDTO);
			for (ProductInventoryInboundEntity inboundEntity : inboundEntityList) {
				ProductInventoryInboundDTO inboundDTO = new ProductInventoryInboundDTO();
				inboundEntity2DTO(inboundEntity, inboundDTO);
				inboundDTOList.add(inboundDTO);
			}
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return inboundDTOList;
	}

	@Override
	public int searchProducctInventoryInboundTotal(ProductSearchDTO productSearchDTO) throws BusinessException {
		try {
			return productInventoryInboundDAO.searchProductInventoryInboundTotal(productSearchDTO);
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	@Override
	public List<ProductInventoryTakingDTO> searchProducctInventoryTaking(ProductSearchDTO productSearchDTO)
			throws BusinessException {
		List<ProductInventoryTakingDTO> takingDTOList = new ArrayList<ProductInventoryTakingDTO>();
		try {
			List<ProductInventoryTakingEntity> takingEntityList= productInventoryTakingDAO.searchProductInventoryTaking(productSearchDTO);
			for (ProductInventoryTakingEntity takingEntity : takingEntityList) {
				ProductInventoryTakingDTO takingDTO = new ProductInventoryTakingDTO();
				takingEntity2DTO(takingEntity, takingDTO);
				takingDTOList.add(takingDTO);
			}
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return takingDTOList;
	}

	@Override
	public int searchProducctInventoryTakingTotal(ProductSearchDTO productSearchDTO) throws BusinessException {
		try {
			return productInventoryTakingDAO.searchProductInventoryTakingTotal(productSearchDTO);
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}

	@Override
	public List<ProductInventoryOutboundDTO> searchProducctInventoryOutbound(ProductSearchDTO productSearchDTO)
			throws BusinessException {
		List<ProductInventoryOutboundDTO> outboundDTOList = new ArrayList<ProductInventoryOutboundDTO>();
		try {
			List<ProductInventoryOutboundEntity> outboundEntityList= productInventoryOutboundDAO.searchProductInventoryOutbound(productSearchDTO);
			for (ProductInventoryOutboundEntity outboundEntity : outboundEntityList) {
				ProductInventoryOutboundDTO outboundDTO = new ProductInventoryOutboundDTO();
				outboundEntity2DTO(outboundEntity, outboundDTO);
				outboundDTOList.add(outboundDTO);
			}
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return outboundDTOList;
	}
	
	@Override
	public List<ProductDTO> searchProduct(ProductSearchDTO productSearchDTO) throws BusinessException {
		List<ProductDTO> productDTOList = new ArrayList<ProductDTO>();
		try {
			List<ProductEntity> productEntityList = productDAO.searchProduct(productSearchDTO);
			for (Iterator<ProductEntity> iter = productEntityList.iterator(); iter.hasNext();) {
				ProductEntity productEntity = iter.next();
				ProductDTO productDTO = new ProductDTO();
				productEntity2DTO(productEntity, productDTO);
				productDTOList.add(productDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return productDTOList;
	}
	
	@Override
	public int searchProductTotal(ProductSearchDTO productSearchDTO) throws BusinessException {
		try {
			return productDAO.searchProductTotal(productSearchDTO);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public int searchProducctInventoryOutboundTotal(ProductSearchDTO productSearchDTO) throws BusinessException {
		try {
			return productInventoryOutboundDAO.searchProductInventoryOutboundTotal(productSearchDTO);
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}
	
	void inboundEntity2DTO(ProductInventoryInboundEntity inboundEntity, ProductInventoryInboundDTO inboundDTO) {
		inboundDTO.setProductInventoryInboundUuid(inboundEntity.getProductInventoryInboundUuid());
		inboundDTO.setTranTime(inboundEntity.getTranTime());
		inboundDTO.setTranType(inboundEntity.getTranType());
		inboundDTO.setTranUnit(inboundEntity.getTranUnit());
		inboundDTO.setMemo(inboundEntity.getMemo());
		inboundDTO.setSkuName(inboundEntity.getSkuName());
		ProductEntity productEntity = inboundEntity.getProductEntity();
		ProductSkuEntity productSkuEntity = inboundEntity.getProductSkuEntity();
		if(productEntity != null) {
			ProductDTO productDTO = new ProductDTO();
			productDTO.setProductUuid(productEntity.getProductUuid());
			productDTO.setProductName(productEntity.getProductName());
			MerchantEntity merchantEntity = productEntity.getMerchantEntity();
			if(merchantEntity != null) {
				MerchantDTO merchantDTO = new MerchantDTO();
				merchantDTO.setMerchantUuid(merchantEntity.getMerchantUuid());
				merchantDTO.setMerchantName(merchantEntity.getMerchantName());
				productDTO.setMerchantDTO(merchantDTO);
			}
			inboundDTO.setProductDTO(productDTO);
		}
		if(productSkuEntity != null) {
			ProductSkuDTO productSkuDTO = new ProductSkuDTO();
			productSkuDTO.setProductSkuUuid(productSkuEntity.getProductSkuUuid());
			inboundDTO.setProductSkuDTO(productSkuDTO);
		}
	}
	
	void outboundEntity2DTO(ProductInventoryOutboundEntity outboundEntity, ProductInventoryOutboundDTO outboundDTO) {
		outboundDTO.setProductInventoryOutboundUuid(outboundEntity.getProductInventoryOutboundUuid());
		outboundDTO.setTranTime(outboundEntity.getTranTime());
		outboundDTO.setTranType(outboundEntity.getTranType());
		outboundDTO.setTranUnit(outboundEntity.getTranUnit());
		outboundDTO.setMemo(outboundEntity.getMemo());
		outboundDTO.setSkuName(outboundEntity.getSkuName());
		ProductEntity productEntity = outboundEntity.getProductEntity();
		ProductSkuEntity productSkuEntity = outboundEntity.getProductSkuEntity();
		if(productEntity != null) {
			ProductDTO productDTO = new ProductDTO();
			productDTO.setProductUuid(productEntity.getProductUuid());
			productDTO.setProductName(productEntity.getProductName());
			MerchantEntity merchantEntity = productEntity.getMerchantEntity();
			if(merchantEntity != null) {
				MerchantDTO merchantDTO = new MerchantDTO();
				merchantDTO.setMerchantUuid(merchantEntity.getMerchantUuid());
				merchantDTO.setMerchantName(merchantEntity.getMerchantName());
				productDTO.setMerchantDTO(merchantDTO);
			}
			outboundDTO.setProductDTO(productDTO);
		}
		if(productSkuEntity != null) {
			ProductSkuDTO productSkuDTO = new ProductSkuDTO();
			productSkuDTO.setProductSkuUuid(productSkuEntity.getProductSkuUuid());
			outboundDTO.setProductSkuDTO(productSkuDTO);
		}
	}
	
	void takingEntity2DTO(ProductInventoryTakingEntity takingEntity, ProductInventoryTakingDTO takingDTO) {
		takingDTO.setProductInventoryTakingUuid(takingEntity.getProductInventoryTakingUuid());
		takingDTO.setTranTime(takingEntity.getTranTime());
		takingDTO.setProfitUnit(takingEntity.getProfitUnit());
		takingDTO.setTranUnit(takingEntity.getTranUnit());
		takingDTO.setMemo(takingEntity.getMemo());
		takingDTO.setSkuName(takingEntity.getSkuName());
		ProductEntity productEntity = takingEntity.getProductEntity();
		ProductSkuEntity productSkuEntity = takingEntity.getProductSkuEntity();
		if(productEntity != null) {
			ProductDTO productDTO = new ProductDTO();
			productDTO.setProductUuid(productEntity.getProductUuid());
			productDTO.setProductName(productEntity.getProductName());
			MerchantEntity merchantEntity = productEntity.getMerchantEntity();
			if(merchantEntity != null) {
				MerchantDTO merchantDTO = new MerchantDTO();
				merchantDTO.setMerchantUuid(merchantEntity.getMerchantUuid());
				merchantDTO.setMerchantName(merchantEntity.getMerchantName());
				productDTO.setMerchantDTO(merchantDTO);
			}
			takingDTO.setProductDTO(productDTO);
		}
		if(productSkuEntity != null) {
			ProductSkuDTO productSkuDTO = new ProductSkuDTO();
			productSkuDTO.setProductSkuUuid(productSkuEntity.getProductSkuUuid());
			takingDTO.setProductSkuDTO(productSkuDTO);
		}
	}
	
	public void productEntity2DTO(ProductEntity productEntity, ProductDTO productDTO) {
		productDTO.setProductUuid(productEntity.getProductUuid());
		productDTO.setProductName(productEntity.getProductName());
		productDTO.setSkuEnabled(productEntity.isSkuEnabled());
		productDTO.setSoldUnit(productEntity.getSoldUnit());
		productDTO.setTotalUnit(productEntity.getTotalUnit());
		productDTO.setUnitPoint(productEntity.getUnitPoint());
		productDTO.setUnitPointStandard(productEntity.getUnitPointStandard());
		productDTO.setUnitPrice(productEntity.getUnitPrice());
		productDTO.setUnitPriceStandard(productEntity.getUnitPriceStandard());
		// 商家
		MerchantEntity merchantEntity = productEntity.getMerchantEntity();
		if (merchantEntity != null) {
			MerchantDTO merchantDTO = new MerchantDTO();
			merchantDTO.setMerchantUuid(merchantEntity.getMerchantUuid());
			merchantDTO.setMerchantName(merchantEntity.getMerchantName());
			productDTO.setMerchantDTO(merchantDTO);
		}
		// 主图
		FileEntity fileEntity = productEntity.getProductMainImage();
		if (fileEntity != null) {
			FileDTO fileDTO = new FileDTO();
			fileDTO.setUrl(fileEntity.getUrl());
			productDTO.setProductMainImage(fileDTO);
		}

		try {
			// 商品SKU
			List<ProductSkuEntity> skuEntityList = productSkuDAO.getProductSkuByProduct(productEntity);
			List<ProductSkuDTO> skuDTOList = new ArrayList<ProductSkuDTO>();
			if (skuEntityList != null) {
				for (Iterator<ProductSkuEntity> iter = skuEntityList.iterator(); iter.hasNext();) {
					ProductSkuEntity skuEntity = iter.next();
					ProductSkuDTO skuDTO = new ProductSkuDTO();
					String skuAttrValueUuids = skuEntity.getSkuAttrValueUuids();
					String tSkuAttrValueUuids = skuAttrValueUuids.replace("[", "").replace("]", "");
					String[] skuAttrValueUuidArray = tSkuAttrValueUuids.split(ProductConstants.DELIMETER + " ");
					List<ProductSkuAttrValueDTO> attrValueDTOList = new ArrayList<ProductSkuAttrValueDTO>();
					for (int i = 0; i < skuAttrValueUuidArray.length; i++) {
						String skuAttrValueUuid = skuAttrValueUuidArray[i];
						ProductSkuAttrValueEntity skuAttrValueEntity = productSkuAttrValueDAO
								.getProductSkuAttrValueByUuid(skuAttrValueUuid);
						ProductSkuAttrValueDTO skuAttrValueDTO = new ProductSkuAttrValueDTO();
						skuAttrValueDTO.setProductSkuAttrUuid(
								skuAttrValueEntity.getProductSkuAttrEntity().getProductSkuAttrUuid());
						skuAttrValueDTO.setProductSkuAttrValueUuid(skuAttrValueEntity.getProductSkuAttrValueUuid());
						skuAttrValueDTO.setSkuAttrName(skuAttrValueEntity.getProductSkuAttrEntity().getSkuAttrName());
						skuAttrValueDTO.setSkuAttrValue(skuAttrValueEntity.getSkuAttrValue());
						attrValueDTOList.add(skuAttrValueDTO);
					}
					skuDTO.setSkuAttrValueList(attrValueDTOList);
					skuDTO.setSkuCode(skuEntity.getSkuCode());
					skuDTO.setSkuSoldUnit(skuEntity.getSkuSoldUnit());
					skuDTO.setSkuTotalUnit(skuEntity.getSkuTotalUnit());
					skuDTO.setSkuUnitPoint(skuEntity.getSkuUnitPoint());
					skuDTO.setSkuUnitPointStandard(skuEntity.getSkuUnitPointStandard());
					skuDTO.setSkuUnitPrice(skuEntity.getSkuUnitPrice());
					skuDTO.setSkuUnitPriceStandard(skuEntity.getSkuUnitPriceStandard());
					skuDTO.setProductSkuUuid(skuEntity.getProductSkuUuid());
					skuDTO.setSkuImageUrl(skuEntity.getSkuImageUrl());
					skuDTOList.add(skuDTO);
				}
			}
			productDTO.setSkuList(skuDTOList);
		} catch (DAOException e) {
			logger.error(e.getMessage());
		}
	}
}
