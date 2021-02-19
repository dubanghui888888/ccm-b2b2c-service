package com.mb.ext.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.constant.OrderConstants;
import com.mb.ext.core.constant.ProductConstants;
import com.mb.ext.core.constant.PromoteConstants;
import com.mb.ext.core.dao.AreaDAO;
import com.mb.ext.core.dao.FileDAO;
import com.mb.ext.core.dao.GroupDAO;
import com.mb.ext.core.dao.MerchantDAO;
import com.mb.ext.core.dao.SequenceDAO;
import com.mb.ext.core.dao.SupplierDAO;
import com.mb.ext.core.dao.UserDAO;
import com.mb.ext.core.dao.coupon.CouponDAO;
import com.mb.ext.core.dao.order.OrderDAO;
import com.mb.ext.core.dao.product.ProductAttrValueDAO;
import com.mb.ext.core.dao.product.ProductBrandDAO;
import com.mb.ext.core.dao.product.ProductCateAttrDAO;
import com.mb.ext.core.dao.product.ProductCateDAO;
import com.mb.ext.core.dao.product.ProductCommentDAO;
import com.mb.ext.core.dao.product.ProductCommentImageDAO;
import com.mb.ext.core.dao.product.ProductDAO;
import com.mb.ext.core.dao.product.ProductDeliveryDAO;
import com.mb.ext.core.dao.product.ProductDescImageDAO;
import com.mb.ext.core.dao.product.ProductFreightDAO;
import com.mb.ext.core.dao.product.ProductFreightRegionDAO;
import com.mb.ext.core.dao.product.ProductGroupDAO;
import com.mb.ext.core.dao.product.ProductImageDAO;
import com.mb.ext.core.dao.product.ProductSkuAttrDAO;
import com.mb.ext.core.dao.product.ProductSkuAttrValueDAO;
import com.mb.ext.core.dao.product.ProductSkuDAO;
import com.mb.ext.core.dao.product.ProductVideoDAO;
import com.mb.ext.core.dao.product.SupplierProductDAO;
import com.mb.ext.core.dao.promote.ProductPromoteDAO;
import com.mb.ext.core.dao.promote.PromoteDiscountDAO;
import com.mb.ext.core.dao.promote.PromoteFreightOffDAO;
import com.mb.ext.core.dao.promote.PromoteMoneyOffDAO;
import com.mb.ext.core.dao.shoppingcart.ShoppingCartDAO;
import com.mb.ext.core.entity.AreaEntity;
import com.mb.ext.core.entity.FileEntity;
import com.mb.ext.core.entity.GroupEntity;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.coupon.CouponEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.order.OrderEntity;
import com.mb.ext.core.entity.product.ProductAttrValueEntity;
import com.mb.ext.core.entity.product.ProductBrandEntity;
import com.mb.ext.core.entity.product.ProductCateAttrEntity;
import com.mb.ext.core.entity.product.ProductCateEntity;
import com.mb.ext.core.entity.product.ProductCommentEntity;
import com.mb.ext.core.entity.product.ProductCommentImageEntity;
import com.mb.ext.core.entity.product.ProductDeliveryEntity;
import com.mb.ext.core.entity.product.ProductDescImageEntity;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.entity.product.ProductFreightEntity;
import com.mb.ext.core.entity.product.ProductFreightRegionEntity;
import com.mb.ext.core.entity.product.ProductGroupEntity;
import com.mb.ext.core.entity.product.ProductImageEntity;
import com.mb.ext.core.entity.product.ProductSkuAttrEntity;
import com.mb.ext.core.entity.product.ProductSkuAttrValueEntity;
import com.mb.ext.core.entity.product.ProductSkuEntity;
import com.mb.ext.core.entity.product.ProductVideoEntity;
import com.mb.ext.core.entity.promote.ProductPromoteEntity;
import com.mb.ext.core.entity.promote.PromoteDiscountEntity;
import com.mb.ext.core.entity.promote.PromoteFreightOffEntity;
import com.mb.ext.core.entity.promote.PromoteMoneyOffEntity;
import com.mb.ext.core.entity.shoppingcart.ShoppingCartEntity;
import com.mb.ext.core.entity.supplier.SupplierEntity;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.NoteService;
import com.mb.ext.core.service.ProductService;
import com.mb.ext.core.service.SettingService;
import com.mb.ext.core.service.spec.AreaDTO;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.FileDTO;
import com.mb.ext.core.service.spec.GroupDTO;
import com.mb.ext.core.service.spec.MerchantSearchDTO;
import com.mb.ext.core.service.spec.ProductBrandSearchDTO;
import com.mb.ext.core.service.spec.ProductCommentSearchDTO;
import com.mb.ext.core.service.spec.ProductSearchDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.coupon.CouponDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.order.OrderDTO;
import com.mb.ext.core.service.spec.product.ProductAttrValueDTO;
import com.mb.ext.core.service.spec.product.ProductBrandDTO;
import com.mb.ext.core.service.spec.product.ProductCateAttrDTO;
import com.mb.ext.core.service.spec.product.ProductCateDTO;
import com.mb.ext.core.service.spec.product.ProductCommentDTO;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.ext.core.service.spec.product.ProductDeliveryDTO;
import com.mb.ext.core.service.spec.product.ProductFreightDTO;
import com.mb.ext.core.service.spec.product.ProductFreightRegionDTO;
import com.mb.ext.core.service.spec.product.ProductSkuAttrDTO;
import com.mb.ext.core.service.spec.product.ProductSkuAttrValueDTO;
import com.mb.ext.core.service.spec.product.ProductSkuDTO;
import com.mb.ext.core.service.spec.promote.PromoteDiscountDTO;
import com.mb.ext.core.service.spec.promote.PromoteFreightOffDTO;
import com.mb.ext.core.service.spec.promote.PromoteMoneyOffDTO;
import com.mb.ext.core.service.spec.supplier.SupplierDTO;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.service.AbstractService;
import com.mb.framework.util.log.LogHelper;

@Service
@Qualifier("ProductService")
public class ProductServiceImpl extends AbstractService implements ProductService<BodyDTO> {

	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;

	@Autowired
	@Qualifier("fileDAO")
	private FileDAO fileDAO;

	@Autowired
	@Qualifier("supplierProductDAO")
	private SupplierProductDAO supplierProductDAO;

	@Autowired
	@Qualifier("areaDAO")
	private AreaDAO areaDAO;
	
	@Autowired
	@Qualifier("merchantDAO")
	private MerchantDAO merchantDAO;

	@Autowired
	@Qualifier("sequenceDAO")
	private SequenceDAO sequenceDAO;

	@Autowired
	@Qualifier("supplierDAO")
	private SupplierDAO supplierDAO;

	@Autowired
	@Qualifier("productCateDAO")
	private ProductCateDAO productCateDAO;

	@Autowired
	@Qualifier("productBrandDAO")
	private ProductBrandDAO productBrandDAO;

	@Autowired
	@Qualifier("couponDAO")
	private CouponDAO couponDAO;

	@Autowired
	@Qualifier("productGroupDAO")
	private ProductGroupDAO productGroupDAO;

	@Autowired
	@Qualifier("groupDAO")
	private GroupDAO groupDAO;

	@Autowired
	@Qualifier("productSkuDAO")
	private ProductSkuDAO productSkuDAO;

	@Autowired
	@Qualifier("productImageDAO")
	private ProductImageDAO productImageDAO;

	@Autowired
	@Qualifier("productVideoDAO")
	private ProductVideoDAO productVideoDAO;

	@Autowired
	@Qualifier("productDescImageDAO")
	private ProductDescImageDAO productDescImageDAO;

	@Autowired
	@Qualifier("productCateAttrDAO")
	private ProductCateAttrDAO productCateAttrDAO;

	@Autowired
	@Qualifier("productAttrValueDAO")
	private ProductAttrValueDAO productAttrValueDAO;

	@Autowired
	@Qualifier("productSkuAttrDAO")
	private ProductSkuAttrDAO productSkuAttrDAO;

	@Autowired
	@Qualifier("productSkuAttrValueDAO")
	private ProductSkuAttrValueDAO productSkuAttrValueDAO;

	@Autowired
	@Qualifier("productFreightDAO")
	private ProductFreightDAO productFreightDAO;
	
	@Autowired
	@Qualifier("productDeliveryDAO")
	private ProductDeliveryDAO productDeliveryDAO;

	@Autowired
	@Qualifier("productFreightRegionDAO")
	private ProductFreightRegionDAO productFreightRegionDAO;

	@Autowired
	@Qualifier("productDAO")
	private ProductDAO productDAO;

	@Autowired
	@Qualifier("productCommentDAO")
	private ProductCommentDAO productCommentDAO;

	@Autowired
	@Qualifier("productCommentImageDAO")
	private ProductCommentImageDAO productCommentImageDAO;

	@Autowired
	@Qualifier("promoteMoneyOffDAO")
	private PromoteMoneyOffDAO promoteMoneyOffDAO;

	@Autowired
	@Qualifier("promoteFreightOffDAO")
	private PromoteFreightOffDAO promoteFreightOffDAO;

	@Autowired
	@Qualifier("promoteDiscountDAO")
	private PromoteDiscountDAO promoteDiscountDAO;

	@Autowired
	@Qualifier("productPromoteDAO")
	private ProductPromoteDAO productPromoteDAO;

	@Autowired
	@Qualifier("shoppingCartDAO")
	private ShoppingCartDAO shoppingCartDAO;

	@Autowired
	@Qualifier("orderDAO")
	private OrderDAO orderDAO;

	@Autowired
	@Qualifier("NoteService")
	private NoteService noteService;
	
	@Autowired
	@Qualifier("settingService")
	private SettingService settingService;

	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	private void cateEntity2DTO(ProductCateDTO productCateDTO, ProductCateEntity productCateEntity) {
		if (productCateDTO != null && productCateEntity != null) {
			productCateDTO.setProductCateUuid(productCateEntity.getProductCateUuid());
			productCateDTO.setCateName(productCateEntity.getCateName());
			productCateDTO.setCatePicUrl(productCateEntity.getCatePicUrl());
			productCateDTO.setSortNumber(productCateEntity.getSortNumber());
			productCateDTO.setLeaf(productCateEntity.isLeaf());
			productCateDTO.setDisplayedHome(productCateEntity.isDisplayedHome());

			List<ProductCateAttrEntity> attrEntityList = productCateEntity.getAttrList();
			if (attrEntityList != null && attrEntityList.size() > 0) {
				List<ProductCateAttrDTO> attrDTOList = new ArrayList<ProductCateAttrDTO>();
				for (Iterator<ProductCateAttrEntity> iter = attrEntityList.iterator(); iter.hasNext();) {
					ProductCateAttrEntity attrEntity = iter.next();
					ProductCateAttrDTO attrDTO = new ProductCateAttrDTO();
					attrDTO.setAttrName(attrEntity.getAttrName());
					// 将字符串属性值V1||V2||V3转化为数组
					String attrValue = attrEntity.getAttrValue();
					if (!StringUtils.isEmpty(attrValue)) {
						StringTokenizer token = new StringTokenizer(attrValue, ProductConstants.DELIMETER);
						List<String> valueList = new ArrayList<String>();
						while (token.hasMoreTokens()) {
							String tValue = token.nextToken();
							valueList.add(tValue);
						}
						attrDTO.setAttrValueArray(valueList.toArray(new String[valueList.size()]));
					}

					attrDTO.setSpuAttr(attrEntity.isSpuAttr());
					attrDTO.setSkuAttr(attrEntity.isSkuAttr());
					attrDTO.setSearchAttr(attrEntity.isSearchAttr());
					attrDTO.setMandatory(attrEntity.isMandatory());
					attrDTO.setInput(attrEntity.isInput());
					attrDTO.setMultiple(attrEntity.isMultiple());
					attrDTOList.add(attrDTO);
				}
				productCateDTO.setAttrList(attrDTOList);
			}
		}
	}

	@Override
	public void addProductCate(ProductCateDTO productCateDTO) throws BusinessException {
		try {
			ProductCateEntity cateEntity = new ProductCateEntity();
			cateEntity.setCateName(productCateDTO.getCateName());
			cateEntity.setCatePicUrl(productCateDTO.getCatePicUrl());
			cateEntity.setSortNumber(productCateDTO.getSortNumber());
			cateEntity.setUpdateBy(productCateDTO.getUpdateBy());
			cateEntity.setCreateBy(productCateDTO.getCreateBy());

			ProductCateDTO parentCateDTO = productCateDTO.getParentCateDTO();
			if (parentCateDTO != null && !StringUtils.isEmpty(parentCateDTO.getProductCateUuid())) {
				ProductCateEntity parentCateEntity = productCateDAO
						.getProductCateByUuid(parentCateDTO.getProductCateUuid());
				// 父类目一定不是叶子类目
				parentCateEntity.setLeaf(false);
				cateEntity.setParentCateEntity(parentCateEntity);
				cateEntity.setCatePath(parentCateEntity.getCatePath() + "/" + cateEntity.getCateName());
			} else {
				cateEntity.setCatePath("/" + cateEntity.getCateName());
			}
			// 新添加的类目一定是叶子类目, 除非在下面继续添加子类目
			cateEntity.setLeaf(true);
			cateEntity.setDisplayedHome(productCateDTO.isDisplayedHome());
			productCateDAO.addProductCate(cateEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void addProductBrand(ProductBrandDTO productBrandDTO) throws BusinessException {
		try {
			ProductBrandEntity brandEntity = new ProductBrandEntity();
			brandEntity.setName(productBrandDTO.getName());
			brandEntity.setDescription(productBrandDTO.getDescription());
			brandEntity.setLogoUrl(productBrandDTO.getLogoUrl());
			brandEntity.setSortNumber(productBrandDTO.getSortNumber());
			productBrandDAO.createProductBrand(brandEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void updateProductBrand(ProductBrandDTO productBrandDTO) throws BusinessException {
		try {
			ProductBrandEntity brandEntity = productBrandDAO.getBrandByUuid(productBrandDTO.getProductBrandUuid());
			brandEntity.setName(productBrandDTO.getName());
			brandEntity.setDescription(productBrandDTO.getDescription());
			brandEntity.setLogoUrl(productBrandDTO.getLogoUrl());
			brandEntity.setSortNumber(productBrandDTO.getSortNumber());
			productBrandDAO.updateProductBrand(brandEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	@Transactional
	public void deleteProductBrand(ProductBrandDTO productBrandDTO) throws BusinessException {
		try {
			ProductBrandEntity brandEntity = productBrandDAO.getBrandByUuid(productBrandDTO.getProductBrandUuid());
			productDAO.deleteProductByBrand(brandEntity);
			productBrandDAO.deleteProductBrand(brandEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public List<ProductBrandDTO> getProductBrands() throws BusinessException {
		List<ProductBrandDTO> dtoList = new ArrayList<ProductBrandDTO>();
		try {
			List<ProductBrandEntity> list = productBrandDAO.getBrands();
			for (Iterator<ProductBrandEntity> iter = list.iterator(); iter.hasNext();) {
				ProductBrandEntity entity = iter.next();
				ProductBrandDTO dto = new ProductBrandDTO();
				productBrandEntity2DTO(entity, dto);
				dtoList.add(dto);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return dtoList;
	}

	@Override
	public ProductBrandDTO getProductBrandByUuid(String uuid) throws BusinessException {
		try {
			ProductBrandEntity productBrandEntity = productBrandDAO.getBrandByUuid(uuid);
			ProductBrandDTO productBrandDTO = new ProductBrandDTO();
			productBrandEntity2DTO(productBrandEntity, productBrandDTO);
			return productBrandDTO;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public List<ProductBrandDTO> searchProductBrand(ProductBrandSearchDTO productBrandSearchDTO)
			throws BusinessException {
		List<ProductBrandDTO> productBrandDTOList = new ArrayList<ProductBrandDTO>();
		try {
			List<ProductBrandEntity> productBrandEntityList = productBrandDAO.searchBrand(productBrandSearchDTO);
			for (Iterator<ProductBrandEntity> iter = productBrandEntityList.iterator(); iter.hasNext();) {
				ProductBrandEntity productBrandEntity = iter.next();
				ProductBrandDTO productBrandDTO = new ProductBrandDTO();
				productBrandEntity2DTO(productBrandEntity, productBrandDTO);
				productBrandDTOList.add(productBrandDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return productBrandDTOList;
	}

	@Override
	public int searchProductBrandTotal(ProductBrandSearchDTO productBrandSearchDTO) throws BusinessException {
		try {
			return productBrandDAO.searchBrandTotal(productBrandSearchDTO);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public void deleteProductCate(ProductCateDTO productCateDTO) throws BusinessException {
		try {
			ProductCateEntity cateEntity = productCateDAO.getProductCateByUuid(productCateDTO.getProductCateUuid());
			if (cateEntity != null) {
				List<ProductEntity> productList = productDAO.getProductByCate(cateEntity);
				if (productList.size() > 0) {
					throw new BusinessException(BusinessErrorCode.PRODUCT_CATE_DELETE);
				}
				ProductCateEntity parentCateEntity = cateEntity.getParentCateEntity();

				// 删除类目属性
				List<ProductCateAttrEntity> attrEntityList = cateEntity.getAttrList();
				cateEntity.setAttrList(null);
				for (Iterator<ProductCateAttrEntity> iter = attrEntityList.iterator(); iter.hasNext();) {
					productCateAttrDAO.deleteProductCateAttr(iter.next());
				}
				// 删除类目
				productCateDAO.deleteProductCate(cateEntity);

				// 检查父类目下是否还有其他子类目, 如果没有则父类目变为叶子类目
				if (parentCateEntity != null) {
					List<ProductCateEntity> list = productCateDAO.getChildProductCate(parentCateEntity);
					if (list == null || list.size() == 0) {
						parentCateEntity.setLeaf(true);
						productCateDAO.updateProductCate(parentCateEntity);
					}
				}
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (BusinessException e) {
			throw e;
		}

	}

	@Override
	public void updateProductCate(ProductCateDTO productCateDTO) throws BusinessException {
		try {
			ProductCateEntity cateEntity = productCateDAO.getProductCateByUuid(productCateDTO.getProductCateUuid());
			if (cateEntity != null) {
				String oldCatePath = cateEntity.getCatePath();
				String oldCateName = cateEntity.getCateName();
				String newCateName = productCateDTO.getCateName();
				String newCatePath = oldCatePath.replace(oldCateName, newCateName);
				cateEntity.setCateName(newCateName);
				cateEntity.setCatePicUrl(productCateDTO.getCatePicUrl());
				cateEntity.setCatePath(newCatePath);
				cateEntity.setSortNumber(productCateDTO.getSortNumber());
				cateEntity.setDisplayedHome(productCateDTO.isDisplayedHome());
				productCateDAO.updateProductCate(cateEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void addProductCateAttr(ProductCateAttrDTO productCateAttrDTO) throws BusinessException {
		try {
			ProductCateEntity cateEntity = productCateDAO
					.getProductCateByUuid(productCateAttrDTO.getProductCateDTO().getProductCateUuid());
			if (cateEntity != null) {
				ProductCateAttrEntity attrEntity = new ProductCateAttrEntity();
				attrEntity.setProductCateEntity(cateEntity);
				attrEntity.setAttrName(productCateAttrDTO.getAttrName());
				String[] valueArray = productCateAttrDTO.getAttrValueArray();
				String attrValue = "";
				if (valueArray != null && valueArray.length > 0) {
					for (int i = 0; i < valueArray.length; i++) {
						String value = valueArray[i];
						if (i == 0)
							attrValue = value;
						else
							attrValue = attrValue + ProductConstants.DELIMETER + value;
					}
				}
				attrEntity.setAttrValue(attrValue);
				attrEntity.setSpuAttr(productCateAttrDTO.isSpuAttr());
				attrEntity.setSkuAttr(productCateAttrDTO.isSkuAttr());
				attrEntity.setSearchAttr(productCateAttrDTO.isSearchAttr());
				attrEntity.setMandatory(productCateAttrDTO.isMandatory());
				attrEntity.setInput(productCateAttrDTO.isInput());
				attrEntity.setMultiple(productCateAttrDTO.isMultiple());
				attrEntity.setUpdateBy(productCateAttrDTO.getUpdateBy());
				attrEntity.setCreateBy(productCateAttrDTO.getCreateBy());
				productCateAttrDAO.addProductCateAttr(attrEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void updateProductCateAttr(ProductCateAttrDTO productCateAttrDTO) throws BusinessException {
		try {
			ProductCateAttrEntity attrEntity = productCateAttrDAO
					.getProductCateAttrByUuid(productCateAttrDTO.getProductCateAttrUuid());
			if (attrEntity != null) {
				attrEntity.setAttrName(productCateAttrDTO.getAttrName());
				String[] valueArray = productCateAttrDTO.getAttrValueArray();
				String attrValue = "";
				if (valueArray != null && valueArray.length > 0) {
					for (int i = 0; i < valueArray.length; i++) {
						String value = valueArray[i];
						if (i == 0)
							attrValue = value;
						else
							attrValue = attrValue + ProductConstants.DELIMETER + value;
					}
				}
				attrEntity.setAttrValue(attrValue);
				attrEntity.setSpuAttr(productCateAttrDTO.isSpuAttr());
				attrEntity.setSkuAttr(productCateAttrDTO.isSkuAttr());
				attrEntity.setSearchAttr(productCateAttrDTO.isSearchAttr());
				attrEntity.setMandatory(productCateAttrDTO.isMandatory());
				attrEntity.setInput(productCateAttrDTO.isInput());
				attrEntity.setMultiple(productCateAttrDTO.isMultiple());
				productCateAttrDAO.updateProductCateAttr(attrEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void deleteProductCateAttr(ProductCateAttrDTO productCateAttrDTO) throws BusinessException {
		try {
			ProductCateAttrEntity attrEntity = productCateAttrDAO
					.getProductCateAttrByUuid(productCateAttrDTO.getProductCateAttrUuid());
			if (attrEntity != null) {
				productCateAttrDAO.deleteProductCateAttr(attrEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public List<ProductCateDTO> getRootProductCate() throws BusinessException {

		List<ProductCateDTO> cateDTOList = new ArrayList<ProductCateDTO>();
		try {
			List<ProductCateEntity> cateEntityList = productCateDAO.getRootProductCate();
			if (cateEntityList != null && cateEntityList.size() > 0) {
				for (Iterator<ProductCateEntity> iter = cateEntityList.iterator(); iter.hasNext();) {
					ProductCateEntity cateEntity = iter.next();
					ProductCateDTO cateDTO = new ProductCateDTO();
					cateEntity2DTO(cateDTO, cateEntity);

					List<ProductCateEntity> childCateEntityList = cateEntity.getChildList();
					List<ProductCateDTO> childCateDTOList = new ArrayList<ProductCateDTO>();
					for (ProductCateEntity childCateEntity : childCateEntityList) {
						ProductCateDTO childCateDTO = new ProductCateDTO();
						cateEntity2DTO(childCateDTO, childCateEntity);
						childCateDTOList.add(childCateDTO);
					}
					cateDTO.setChildList(childCateDTOList);
					cateDTOList.add(cateDTO);
				}
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

		return cateDTOList;

	}

	@Override
	public List<ProductCateDTO> getHomeProductCate() throws BusinessException {

		List<ProductCateDTO> cateDTOList = new ArrayList<ProductCateDTO>();
		try {
			List<ProductCateEntity> cateEntityList = productCateDAO.getHomeProductCate();
			if (cateEntityList != null && cateEntityList.size() > 0) {
				for (Iterator<ProductCateEntity> iter = cateEntityList.iterator(); iter.hasNext();) {
					ProductCateEntity cateEntity = iter.next();
					ProductCateDTO cateDTO = new ProductCateDTO();
					cateEntity2DTO(cateDTO, cateEntity);
					cateDTOList.add(cateDTO);
				}
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

		return cateDTOList;

	}

	@Override
	public List<ProductCateDTO> getLeafProductCate() throws BusinessException {

		List<ProductCateDTO> cateDTOList = new ArrayList<ProductCateDTO>();
		try {
			List<ProductCateEntity> cateEntityList = productCateDAO.getLeafProductCate();
			if (cateEntityList != null && cateEntityList.size() > 0) {
				for (Iterator<ProductCateEntity> iter = cateEntityList.iterator(); iter.hasNext();) {
					ProductCateEntity cateEntity = iter.next();
					ProductCateDTO cateDTO = new ProductCateDTO();
					cateEntity2DTO(cateDTO, cateEntity);
					cateDTOList.add(cateDTO);
				}
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

		return cateDTOList;

	}

	@Override
	public List<ProductCateDTO> getChildProductCate(ProductCateDTO productCateDTO) throws BusinessException {

		List<ProductCateDTO> cateDTOList = new ArrayList<ProductCateDTO>();
		try {
			ProductCateEntity parentEntity = productCateDAO.getProductCateByUuid(productCateDTO.getProductCateUuid());
			if (parentEntity != null) {
				List<ProductCateEntity> cateEntityList = productCateDAO.getChildProductCate(parentEntity);
				if (cateEntityList != null && cateEntityList.size() > 0) {
					for (Iterator<ProductCateEntity> iter = cateEntityList.iterator(); iter.hasNext();) {
						ProductCateEntity cateEntity = iter.next();
						ProductCateDTO cateDTO = new ProductCateDTO();
						cateEntity2DTO(cateDTO, cateEntity);
						cateDTOList.add(cateDTO);
					}
				}
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

		return cateDTOList;

	}

	@Override
	public List<ProductBrandDTO> getProductBrandByProductCate(ProductCateDTO productCateDTO) throws BusinessException {

		List<ProductBrandDTO> brandDTOList = new ArrayList<ProductBrandDTO>();
		try {
			ProductCateEntity productCateEntity = productCateDAO
					.getProductCateByUuid(productCateDTO.getProductCateUuid());
			if (productCateEntity != null) {
				List<ProductBrandEntity> brandEntityList = productBrandDAO
						.getProductBrandsByProductCate(productCateEntity);
				if (brandEntityList != null && brandEntityList.size() > 0) {
					for (Iterator<ProductBrandEntity> iter = brandEntityList.iterator(); iter.hasNext();) {
						ProductBrandEntity brandEntity = iter.next();
						ProductBrandDTO brandDTO = new ProductBrandDTO();
						productBrandEntity2DTO(brandEntity, brandDTO);
						brandDTOList.add(brandDTO);
					}
				}
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return brandDTOList;
	}

	@Override
	public ProductCateDTO getProductCateByUuid(String uuid) throws BusinessException {
		ProductCateDTO cateDTO = null;
		try {
			ProductCateEntity cateEntity = productCateDAO.getProductCateByUuid(uuid);
			if (cateEntity != null) {
				cateDTO = new ProductCateDTO();
				cateEntity2DTO(cateDTO, cateEntity);

			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

		return cateDTO;
	}

	@Override
	public void addProduct(ProductDTO productDTO) throws BusinessException {
		try {
			// 基本属性
			ProductEntity productEntity = new ProductEntity();
			productDTO2Entity(productEntity, productDTO);

			// 商户
			SupplierDTO supplierDTO = productDTO.getSupplierDTO();
			if (supplierDTO != null) {
				SupplierEntity supplierEntity = supplierDAO.getSupplierByUuid(supplierDTO.getSupplierUuid());
				productEntity.setSupplierEntity(supplierEntity);
			}
			
			// 商家
			MerchantDTO merchantDTO = productDTO.getMerchantDTO();
			if (merchantDTO != null) {
				MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
				productEntity.setMerchantEntity(merchantEntity);
			}

			// 品牌
			ProductBrandDTO brandDTO = productDTO.getProductBrandDTO();
			if (brandDTO != null && !StringUtils.isEmpty(brandDTO.getProductBrandUuid())) {
				ProductBrandEntity brandEntity = productBrandDAO.getBrandByUuid(brandDTO.getProductBrandUuid());
				productEntity.setProductBrandEntity(brandEntity);
			}
			// 品类
			ProductCateDTO cateDTO = productDTO.getProductCateDTO();
			if (cateDTO != null && !StringUtils.isEmpty(cateDTO.getProductCateUuid())) {
				ProductCateEntity cateEntity = productCateDAO.getProductCateByUuid(cateDTO.getProductCateUuid());
				productEntity.setProductCateEntity(cateEntity);
			}
			// 主图
			FileDTO imageDTO = productDTO.getProductMainImage();
			if (imageDTO != null && !StringUtils.isEmpty(imageDTO.getUrl())) {
				FileEntity fileEntity = new FileEntity();
				fileEntity.setFileName(imageDTO.getFileName());
				fileEntity.setOssKey(imageDTO.getOssKey());
				fileEntity.setSize(imageDTO.getSize());
				fileEntity.setUrl(imageDTO.getUrl());
				fileDAO.createFile(fileEntity);
				productEntity.setProductMainImage(fileEntity);
			}

			//设置实物商品相关属性
			if(ProductConstants.PRODUCT_TYPE_REAL.equals(productDTO.getProductType())) {
				productEntity.setProductWeight(productDTO.getProductWeight());
				productEntity.setDeliveryExpressEnabled(productDTO.isDeliveryExpressEnabled());
				productEntity.setDeliveryCityEnabled(productDTO.isDeliveryCityEnabled());
				productEntity.setDeliveryPickEnabled(productDTO.isDeliveryPickEnabled());
				// 运费模板
				ProductFreightDTO freightDTO = productDTO.getProductFreightDTO();
				if (freightDTO != null) {
					ProductFreightEntity freightEntity = productFreightDAO
							.getFreightByUuid(freightDTO.getProductFreightUuid());
					productEntity.setProductFreightEntity(freightEntity);
				}
				
				// 同城配送模板
				ProductDeliveryDTO deliveryDTO = productDTO.getProductDeliveryDTO();
				if (deliveryDTO != null) {
					ProductDeliveryEntity deliveryEntity = productDeliveryDAO
							.getDeliveryByUuid(deliveryDTO.getProductDeliveryUuid());
					productEntity.setProductDeliveryEntity(deliveryEntity);
				}
			}
			//设置虚拟商品相关属性
			if(ProductConstants.PRODUCT_TYPE_VIRTUAL.equals(productDTO.getProductType())) {
				
			}
			
			//设置卡券商品相关属性
			if(ProductConstants.PRODUCT_TYPE_VOUCHER.equals(productDTO.getProductType())) {
				productEntity.setValidType(productDTO.getValidType());
				productEntity.setValidDays(productDTO.getValidDays());
				productEntity.setValidStartDate(productDTO.getValidStartDate());
				productEntity.setValidEndDate(productDTO.getValidEndDate());
			}

			productDAO.addProduct(productEntity);

			// 商品图片
			List<FileDTO> images = productDTO.getProductImages();
			if (images != null) {
				for (Iterator<FileDTO> iter = images.iterator(); iter.hasNext();) {
					FileDTO image = iter.next();
					FileEntity imageEntity = new FileEntity();
					imageEntity.setFileName(image.getFileName());
					imageEntity.setOssKey(image.getOssKey());
					imageEntity.setUrl(image.getUrl());
					imageEntity.setSize(image.getSize());
					fileDAO.createFile(imageEntity);
					ProductImageEntity productImageEntity = new ProductImageEntity();
					productImageEntity.setProductEntity(productEntity);
					productImageEntity.setFileEntity(imageEntity);
					productImageDAO.createProductImage(productImageEntity);
				}
			}

			// 商品描述图片
			List<FileDTO> descImages = productDTO.getProductDescImages();
			if (descImages != null) {
				for (Iterator<FileDTO> iter = descImages.iterator(); iter.hasNext();) {
					FileDTO image = iter.next();
					FileEntity imageEntity = new FileEntity();
					imageEntity.setFileName(image.getFileName());
					imageEntity.setUrl(image.getUrl());
					imageEntity.setSize(image.getSize());
					fileDAO.createFile(imageEntity);
					ProductDescImageEntity productImageEntity = new ProductDescImageEntity();
					productImageEntity.setProductEntity(productEntity);
					productImageEntity.setFileEntity(imageEntity);
					productDescImageDAO.createProductDescImage(productImageEntity);
				}
			}

			// 商品视频
			List<FileDTO> videos = productDTO.getProductVideos();
			if (videos != null) {
				for (Iterator<FileDTO> iter = videos.iterator(); iter.hasNext();) {
					FileDTO image = iter.next();
					FileEntity imageEntity = new FileEntity();
					imageEntity.setFileName(image.getFileName());
					imageEntity.setUrl(image.getUrl());
					imageEntity.setSize(image.getSize());
					fileDAO.createFile(imageEntity);
					ProductVideoEntity videoEntity = new ProductVideoEntity();
					videoEntity.setProductEntity(productEntity);
					videoEntity.setFileEntity(imageEntity);
					productVideoDAO.createProductVideo(videoEntity);
				}
			}

			// 设置商品属性
			List<ProductAttrValueDTO> attrList = productDTO.getAttrList();
			if (attrList != null) {
				for (Iterator<ProductAttrValueDTO> iter = attrList.iterator(); iter.hasNext();) {
					ProductAttrValueDTO attrDTO = iter.next();
					ProductAttrValueEntity attrEntity = new ProductAttrValueEntity();
					attrEntity.setCateAttr(attrDTO.isCateAttr());
					if (attrDTO.isCateAttr()) {
						ProductCateAttrDTO cateAttrDTO = attrDTO.getProductCateAttrDTO();
						ProductCateAttrEntity cateAttrEntity = productCateAttrDAO
								.getProductCateAttrByUuid(cateAttrDTO.getProductCateAttrUuid());
						attrEntity.setProductCateAttrEntity(cateAttrEntity);
					}
					attrEntity.setProductAttrName(attrDTO.getProductAttrName());
					attrEntity.setProductAttrValue(attrDTO.getProductAttrValue());
					attrEntity.setProductEntity(productEntity);
					productAttrValueDAO.addProductAttr(attrEntity);
				}
			}
			// 设置SKU屬性, 屬性值
			if (productDTO.isSkuEnabled()) {
				productDTO.setProductUuid(productEntity.getProductUuid());
				buildSKU(productDTO);
				// 更新商品总库存
				productEntity.setTotalUnit(productDTO.getTotalUnit());
				productDAO.updateProduct(productEntity);
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	private void buildSKU(ProductDTO productDTO) throws DAOException {
		ProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
		List<ProductSkuAttrDTO> skuAttrDTOList = productDTO.getSkuAttrList();
		if (skuAttrDTOList != null) {
			for (Iterator<ProductSkuAttrDTO> iter = skuAttrDTOList.iterator(); iter.hasNext();) {
				ProductSkuAttrDTO skuAttrDTO = iter.next();
				List<ProductSkuAttrValueDTO> skuAttrValueList = skuAttrDTO.getProductAttrValueList();
				// 1. 添加SKU屬性
				ProductSkuAttrEntity skuAttrEntity = new ProductSkuAttrEntity();
				skuAttrEntity.setProductEntity(productEntity);
				skuAttrEntity.setSkuAttrName(skuAttrDTO.getSkuAttrName());
				skuAttrEntity.setCateAttr(skuAttrDTO.isCateAttr());
				if (skuAttrDTO.isCateAttr()) {
					ProductCateAttrDTO cateAttrDTO = skuAttrDTO.getProductCateAttrDTO();
					ProductCateAttrEntity cateAttrEntity = productCateAttrDAO
							.getProductCateAttrByUuid(cateAttrDTO.getProductCateAttrUuid());
					skuAttrEntity.setProductCateAttrEntity(cateAttrEntity);
				}
				productSkuAttrDAO.addProductAttr(skuAttrEntity);
				// 2. 添加SKU屬性值
				if (skuAttrValueList != null) {
					for (Iterator<ProductSkuAttrValueDTO> valueIter = skuAttrValueList.iterator(); valueIter
							.hasNext();) {
						ProductSkuAttrValueDTO valueDTO = valueIter.next();
						ProductSkuAttrValueEntity valueEntity = new ProductSkuAttrValueEntity();
						valueEntity.setSkuAttrValue(valueDTO.getSkuAttrValue());
						valueEntity.setProductSkuAttrEntity(skuAttrEntity);
						valueEntity.setImageUrl(valueDTO.getImageUrl());
						valueEntity.setProductEntity(productEntity);
						productSkuAttrValueDAO.addProductAttrValue(valueEntity);
					}
				}
			}
		}
		// 設置SKU庫存
		int totalUnit = 0; // 计算所有sku库存之和并付给商品库存
		List<ProductSkuDTO> skuDTOList = productDTO.getSkuList();
		if (skuDTOList != null) {
			for (Iterator<ProductSkuDTO> iter = skuDTOList.iterator(); iter.hasNext();) {
				ProductSkuDTO skuDTO = iter.next();
				List<ProductSkuAttrValueDTO> skuAttrValueList = skuDTO.getSkuAttrValueList();
				String[] skuAttrValueUuidArray = new String[skuAttrValueList.size()];
				// 將各屬性值組合存儲成[48324:67473:7889]格式
				for (int i = 0; i < skuAttrValueList.size(); i++) {
					ProductSkuAttrValueDTO valueDTO = (ProductSkuAttrValueDTO) skuAttrValueList.get(i);
					String name = valueDTO.getSkuAttrName();
					String value = valueDTO.getSkuAttrValue();
					ProductSkuAttrValueEntity valueEntity = productSkuAttrValueDAO
							.getProductSkuAttrValuesByNameValue(productEntity, name, value);
					if (valueEntity != null) {
						skuAttrValueUuidArray[i] = valueEntity.getProductSkuAttrValueUuid();
					}
				}
				Arrays.sort(skuAttrValueUuidArray);
				ProductSkuEntity skuEntity = new ProductSkuEntity();
				skuEntity.setProductEntity(productEntity);
				skuEntity.setSkuAttrValueUuids(Arrays.toString(skuAttrValueUuidArray));
				skuEntity.setSkuCode(skuDTO.getSkuCode());
				skuEntity.setSkuTotalUnit(skuDTO.getSkuTotalUnit());
				skuEntity.setSkuUnitPoint(skuDTO.getSkuUnitPoint());
				skuEntity.setSkuUnitPointStandard(skuDTO.getSkuUnitPointStandard());
				skuEntity.setSkuUnitPrice(skuDTO.getSkuUnitPrice());
				skuEntity.setSkuUnitPriceStandard(skuDTO.getSkuUnitPriceStandard());
				skuEntity.setSkuImageUrl(skuDTO.getSkuImageUrl());
				productSkuDAO.addProductSku(skuEntity);
				totalUnit = totalUnit + skuDTO.getSkuTotalUnit();
			}
		}
		productDTO.setTotalUnit(totalUnit);
	}

	@Override
	public void updateProduct(ProductDTO productDTO) throws BusinessException {
		try {

			// 基本属性
			ProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
			productDTO2Entity(productEntity, productDTO);

			// 供应商
			SupplierDTO supplierDTO = productDTO.getSupplierDTO();
			if (supplierDTO != null) {
				SupplierEntity supplierEntity = supplierDAO.getSupplierByUuid(supplierDTO.getSupplierUuid());
				productEntity.setSupplierEntity(supplierEntity);
			}
			
			// 商家
			MerchantDTO merchantDTO = productDTO.getMerchantDTO();
			if (merchantDTO != null) {
				MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
				productEntity.setMerchantEntity(merchantEntity);
			}
			
			// 品牌
			ProductBrandDTO brandDTO = productDTO.getProductBrandDTO();
			if (brandDTO != null && !StringUtils.isEmpty(brandDTO.getProductBrandUuid())) {
				ProductBrandEntity brandEntity = productBrandDAO.getBrandByUuid(brandDTO.getProductBrandUuid());
				productEntity.setProductBrandEntity(brandEntity);
			} else {
				productEntity.setProductBrandEntity(null);
			}
			// 品类
			ProductCateDTO cateDTO = productDTO.getProductCateDTO();
			if (cateDTO != null && !StringUtils.isEmpty(cateDTO.getProductCateUuid())) {
				ProductCateEntity cateEntity = productCateDAO.getProductCateByUuid(cateDTO.getProductCateUuid());
				productEntity.setProductCateEntity(cateEntity);
			}

			// 主图
			FileDTO imageDTO = productDTO.getProductMainImage();
			if (StringUtils.isEmpty(imageDTO.getFileUuid())) { // UUID為空表明圖片有更新
				if (imageDTO != null && !StringUtils.isEmpty(imageDTO.getUrl())) {
					FileEntity fileEntity = new FileEntity();
					fileEntity.setFileName(imageDTO.getFileName());
					fileEntity.setOssKey(imageDTO.getOssKey());
					fileEntity.setSize(imageDTO.getSize());
					fileEntity.setUrl(imageDTO.getUrl());
					fileDAO.createFile(fileEntity);
					productEntity.setProductMainImage(fileEntity);
				}
			}

			//设置实物商品相关属性
			if(ProductConstants.PRODUCT_TYPE_REAL.equals(productDTO.getProductType())) {
				productEntity.setProductWeight(productDTO.getProductWeight());
				productEntity.setDeliveryExpressEnabled(productDTO.isDeliveryExpressEnabled());
				productEntity.setDeliveryCityEnabled(productDTO.isDeliveryCityEnabled());
				productEntity.setDeliveryPickEnabled(productDTO.isDeliveryPickEnabled());
				// 运费模板
				ProductFreightDTO freightDTO = productDTO.getProductFreightDTO();
				if (freightDTO != null) {
					ProductFreightEntity freightEntity = productFreightDAO
							.getFreightByUuid(freightDTO.getProductFreightUuid());
					productEntity.setProductFreightEntity(freightEntity);
				}
				
				// 同城配送模板
				ProductDeliveryDTO deliveryDTO = productDTO.getProductDeliveryDTO();
				if (deliveryDTO != null) {
					ProductDeliveryEntity deliveryEntity = productDeliveryDAO
							.getDeliveryByUuid(deliveryDTO.getProductDeliveryUuid());
					productEntity.setProductDeliveryEntity(deliveryEntity);
				}
			}
			//设置虚拟商品相关属性
			if(ProductConstants.PRODUCT_TYPE_VIRTUAL.equals(productDTO.getProductType())) {
				
			}
			
			//设置卡券商品相关属性
			if(ProductConstants.PRODUCT_TYPE_VOUCHER.equals(productDTO.getProductType())) {
				productEntity.setValidType(productDTO.getValidType());
				productEntity.setValidDays(productDTO.getValidDays());
				productEntity.setValidStartDate(productDTO.getValidStartDate());
				productEntity.setValidEndDate(productDTO.getValidEndDate());
			}

			productDAO.updateProduct(productEntity);

			// 商品图片
			List<ProductImageEntity> imageEntityList = productImageDAO.getImagesByProduct(productEntity);
			for (Iterator<ProductImageEntity> iter = imageEntityList.iterator(); iter.hasNext();) {
				productImageDAO.deleteProductImage(iter.next());
			}
			List<FileDTO> images = productDTO.getProductImages();
			if (images != null) {
				for (Iterator<FileDTO> iter = images.iterator(); iter.hasNext();) {
					FileDTO image = iter.next();
					FileEntity imageEntity = new FileEntity();
					imageEntity.setFileName(image.getFileName());
					imageEntity.setOssKey(image.getOssKey());
					imageEntity.setUrl(image.getUrl());
					imageEntity.setSize(image.getSize());
					fileDAO.createFile(imageEntity);
					ProductImageEntity productImageEntity = new ProductImageEntity();
					productImageEntity.setProductEntity(productEntity);
					productImageEntity.setFileEntity(imageEntity);
					productImageDAO.createProductImage(productImageEntity);
				}
			}

			// 商品描述图片
			List<ProductDescImageEntity> descImageEntityList = productDescImageDAO
					.getDescImagesByProduct(productEntity);
			for (Iterator<ProductDescImageEntity> iter = descImageEntityList.iterator(); iter.hasNext();) {
				productDescImageDAO.deleteProductDescImage(iter.next());
			}
			List<FileDTO> descImages = productDTO.getProductDescImages();
			if (descImages != null) {
				for (Iterator<FileDTO> iter = descImages.iterator(); iter.hasNext();) {
					FileDTO image = iter.next();
					FileEntity imageEntity = new FileEntity();
					imageEntity.setFileName(image.getFileName());
					imageEntity.setUrl(image.getUrl());
					imageEntity.setSize(image.getSize());
					fileDAO.createFile(imageEntity);
					ProductDescImageEntity productImageEntity = new ProductDescImageEntity();
					productImageEntity.setProductEntity(productEntity);
					productImageEntity.setFileEntity(imageEntity);
					productDescImageDAO.createProductDescImage(productImageEntity);
				}
			}

			// 商品视频
			List<ProductVideoEntity> videoEntityList = productVideoDAO.getVideosByProduct(productEntity);
			for (Iterator<ProductVideoEntity> iter = videoEntityList.iterator(); iter.hasNext();) {
				productVideoDAO.deleteProductVideo(iter.next());
			}
			List<FileDTO> videos = productDTO.getProductVideos();
			if (videos != null) {
				for (Iterator<FileDTO> iter = videos.iterator(); iter.hasNext();) {
					FileDTO image = iter.next();
					FileEntity imageEntity = new FileEntity();
					imageEntity.setFileName(image.getFileName());
					imageEntity.setUrl(image.getUrl());
					imageEntity.setSize(image.getSize());
					fileDAO.createFile(imageEntity);
					ProductVideoEntity videoEntity = new ProductVideoEntity();
					videoEntity.setProductEntity(productEntity);
					videoEntity.setFileEntity(imageEntity);
					productVideoDAO.createProductVideo(videoEntity);
				}
			}

			// 设置商品属性
			List<ProductAttrValueEntity> valueEntityList = productAttrValueDAO.getProductAttrByProduct(productEntity);
			for (Iterator<ProductAttrValueEntity> iter = valueEntityList.iterator(); iter.hasNext();) {
				productAttrValueDAO.deleteProductAttr(iter.next());
			}
			List<ProductAttrValueDTO> attrList = productDTO.getAttrList();
			if (attrList != null) {
				for (Iterator<ProductAttrValueDTO> iter = attrList.iterator(); iter.hasNext();) {
					ProductAttrValueDTO attrDTO = iter.next();
					ProductAttrValueEntity attrEntity = new ProductAttrValueEntity();
					attrEntity.setCateAttr(attrDTO.isCateAttr());
					if (attrDTO.isCateAttr()) {
						ProductCateAttrDTO cateAttrDTO = attrDTO.getProductCateAttrDTO();
						ProductCateAttrEntity cateAttrEntity = productCateAttrDAO
								.getProductCateAttrByUuid(cateAttrDTO.getProductCateAttrUuid());
						attrEntity.setProductCateAttrEntity(cateAttrEntity);
					}
					attrEntity.setProductAttrName(attrDTO.getProductAttrName());
					attrEntity.setProductAttrValue(attrDTO.getProductAttrValue());
					attrEntity.setProductEntity(productEntity);
					productAttrValueDAO.addProductAttr(attrEntity);
				}
			}
			// 重建商品SKU
			List<ShoppingCartEntity> shoppingCartEntity = shoppingCartDAO.getShoppingCartByProductUuid(productEntity);
			for (Iterator<ShoppingCartEntity> iter = shoppingCartEntity.iterator(); iter.hasNext();) {
				shoppingCartDAO.deleteShoppingCart(iter.next());
			}
			List<ProductSkuEntity> skuEntityList = productSkuDAO.getProductSkuByProduct(productEntity);
			for (Iterator<ProductSkuEntity> iter = skuEntityList.iterator(); iter.hasNext();) {
				productSkuDAO.deleteProductSku(iter.next());
			}
			List<ProductSkuAttrValueEntity> skuValueEntityList = productSkuAttrValueDAO
					.getProductSkuAttrValuesByProduct(productEntity);
			for (Iterator<ProductSkuAttrValueEntity> iter = skuValueEntityList.iterator(); iter.hasNext();) {
				productSkuAttrValueDAO.deleteProductAttrValue(iter.next());
			}
			List<ProductSkuAttrEntity> attrEntityList = productSkuAttrDAO.getProductAttrByProduct(productEntity);
			for (Iterator<ProductSkuAttrEntity> iter = attrEntityList.iterator(); iter.hasNext();) {
				productSkuAttrDAO.deleteProductAttr(iter.next());
			}
			if (productDTO.isSkuEnabled()) {
				productDTO.setProductUuid(productEntity.getProductUuid());
				buildSKU(productDTO);

				// 更新商品总库存
				productEntity.setTotalUnit(productDTO.getTotalUnit());
				productDAO.updateProduct(productEntity);
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	public void updateCouponProduct(ProductDTO productDTO) throws BusinessException {
		try {

			ProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
			CouponDTO couponDTO = productDTO.getCouponDTO();
			CouponEntity couponEntity = couponDAO.getCouponByUuid(couponDTO.getCouponUuid());
			productDTO2Entity(productEntity, productDTO);
			productEntity.setSkuEnabled(false);
			productDAO.updateProduct(productEntity);

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void deleteProduct(ProductDTO productDTO) throws BusinessException {
		try {
			// 基本属性
			ProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
//			if (productEntity != null) {
//				productDAO.deleteProduct(productEntity);
//			}
			if (!productEntity.isOnSale()) {
				// 商品视频
				List<ProductVideoEntity> videoEntityList = productVideoDAO.getVideosByProduct(productEntity);
				for (Iterator<ProductVideoEntity> iter = videoEntityList.iterator(); iter.hasNext();) {
					productVideoDAO.deleteProductVideo(iter.next());
				}

				// 删除商品SKU
				List<ProductSkuEntity> skuEntityList = productSkuDAO.getProductSkuByProduct(productEntity);
				for (Iterator<ProductSkuEntity> iter = skuEntityList.iterator(); iter.hasNext();) {
					productSkuDAO.deleteProductSku(iter.next());
				}
				List<ProductSkuAttrValueEntity> skuValueEntityList = productSkuAttrValueDAO
						.getProductSkuAttrValuesByProduct(productEntity);
				for (Iterator<ProductSkuAttrValueEntity> iter = skuValueEntityList.iterator(); iter.hasNext();) {
					productSkuAttrValueDAO.deleteProductAttrValue(iter.next());
				}
				List<ProductSkuAttrEntity> attrEntityList = productSkuAttrDAO.getProductAttrByProduct(productEntity);
				for (Iterator<ProductSkuAttrEntity> iter = attrEntityList.iterator(); iter.hasNext();) {
					productSkuAttrDAO.deleteProductAttr(iter.next());
				}

				// 商品图片
				List<ProductImageEntity> imageEntityList = productImageDAO.getImagesByProduct(productEntity);
				for (Iterator<ProductImageEntity> iter = imageEntityList.iterator(); iter.hasNext();) {
					productImageDAO.deleteProductImage(iter.next());
				}

				// 商品图片描述productdescimage
				List<ProductDescImageEntity> descImageEntityList = productDescImageDAO
						.getDescImagesByProduct(productEntity);
				for (Iterator<ProductDescImageEntity> iter = descImageEntityList.iterator(); iter.hasNext();) {
					productDescImageDAO.deleteProductDescImage(iter.next());
				}

				// 设置商品属性
				List<ProductAttrValueEntity> valueEntityList = productAttrValueDAO
						.getProductAttrByProduct(productEntity);
				for (Iterator<ProductAttrValueEntity> iter = valueEntityList.iterator(); iter.hasNext();) {
					productAttrValueDAO.deleteProductAttr(iter.next());
				}

				// 删除商品
				productDAO.deleteProduct(productEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}
	
	@Override
	public void submitProduct(ProductDTO productDTO) throws BusinessException {
		try {
			// 基本属性
			ProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
			if (productEntity != null) {
				boolean isApplicationProductEnabled = settingService.getGlobalApplicationSetting().isApplicationProductEnabled();
				//商品需要审核
				if(isApplicationProductEnabled) {
					productEntity.setOnSale(false);
					productEntity.setVerifyStatus(ProductConstants.PRODUCT_VERIFY_STATUS_SUBMITTED);
				}else {
					productEntity.setOnSale(true);
				}
				productDAO.updateProduct(productEntity);
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}
	

	@Override
	public void enableProduct(ProductDTO productDTO) throws BusinessException {
		try {
			// 基本属性
			ProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
			if (productEntity != null) {
				productEntity.setOnSale(true);
				productEntity.setVerifyStatus(ProductConstants.PRODUCT_VERIFY_STATUS_APPROVED);
				productDAO.updateProduct(productEntity);
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public void disableProduct(ProductDTO productDTO) throws BusinessException {
		try {
			// 基本属性
			ProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
			if (productEntity != null) {
				productEntity.setOnSale(false);
				productEntity.setVerifyStatus(null);
				productDAO.updateProduct(productEntity);
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}
	
	@Override
	public void approveProduct(ProductDTO productDTO) throws BusinessException {
		try {
			// 基本属性
			ProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
			if (productEntity != null) {
				productEntity.setOnSale(true);
				productEntity.setVerifyStatus(ProductConstants.PRODUCT_VERIFY_STATUS_APPROVED);
				productEntity.setVerifyMsg(productDTO.getVerifyMsg());
				productDAO.updateProduct(productEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}
	
	@Override
	public void rejectProduct(ProductDTO productDTO) throws BusinessException {
		try {
			// 基本属性
			ProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
			if (productEntity != null) {
				productEntity.setOnSale(false);
				productEntity.setVerifyStatus(ProductConstants.PRODUCT_VERIFY_STATUS_REJECTED);
				productEntity.setVerifyMsg(productDTO.getVerifyMsg());
				productDAO.updateProduct(productEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	void productDTO2Entity(ProductEntity productEntity, ProductDTO productDTO) {
		productEntity.setProductName(productDTO.getProductName());
		productEntity.setHot(productDTO.isHot());
		productEntity.setMarketUnitPrice(productDTO.getMarketUnitPrice());
		productEntity.setMemo(productDTO.getMemo());
		productEntity.setNew(productDTO.isNew());
		productEntity.setOnSale(productDTO.isOnSale());
		productEntity.setProductCode(productDTO.getProductCode());
		productEntity.setProductDesc(productDTO.getProductDesc());
		productEntity.setPromote(productDTO.isPromote());
		productEntity.setPromoteEndDate(productEntity.getPromoteEndDate());
		productEntity.setPromoteStartDate(productDTO.getPromoteStartDate());
		productEntity.setPromoteUnitPrice(productDTO.getPromoteUnitPrice());
		productEntity.setMembershipUnitPrice(productDTO.getMembershipUnitPrice());
		productEntity.setProductType(productDTO.getProductType());
		productEntity.setRecommend(productDTO.isRecommend());
		productEntity.setShipping(productDTO.isShipping());
		productEntity.setSkuEnabled(productDTO.isSkuEnabled());
		productEntity.setTotalUnit(productDTO.getTotalUnit());
		productEntity.setUnitPoint(productDTO.getUnitPoint());
		productEntity.setUnitPointStandard(productDTO.getUnitPointStandard());
		productEntity.setUnitPrice(productDTO.getUnitPrice());
		productEntity.setUnitPriceStandard(productDTO.getUnitPriceStandard());
		productEntity.setWarnUnit(productDTO.getWarnUnit());
		productEntity.setProductCostPrice(productDTO.getProductCostPrice());
	}

	@Override
	public void productEntity2DTO(ProductEntity productEntity, ProductDTO productDTO) {
		productDTO.setProductUuid(productEntity.getProductUuid());
		productDTO.setProductName(productEntity.getProductName());
		productDTO.setHot(productEntity.isHot());
		productDTO.setMemo(productEntity.getMemo());
		productDTO.setNew(productEntity.isNew());
		productDTO.setOnSale(productEntity.isOnSale());
		productDTO.setProductCode(productEntity.getProductCode());
		productDTO.setProductDesc(productEntity.getProductDesc());
		productDTO.setProductType(productEntity.getProductType());
		productDTO.setRecommend(productEntity.isRecommend());
		productDTO.setSkuEnabled(productEntity.isSkuEnabled());
		productDTO.setSoldUnit(productEntity.getSoldUnit());
		productDTO.setTotalUnit(productEntity.getTotalUnit());
		productDTO.setUnitPoint(productEntity.getUnitPoint());
		productDTO.setUnitPointStandard(productEntity.getUnitPointStandard());
		productDTO.setUnitPrice(productEntity.getUnitPrice());
		productDTO.setUnitPriceStandard(productEntity.getUnitPriceStandard());
		productDTO.setWarnUnit(productEntity.getWarnUnit());
		productDTO.setProductCostPrice(productEntity.getProductCostPrice());
		productDTO.setProductWeight(productEntity.getProductWeight());
		productDTO.setVerifyStatus(productEntity.getVerifyStatus());
		productDTO.setVerifyMsg(productEntity.getVerifyMsg());
		productDTO.setDeliveryExpressEnabled(productEntity.isDeliveryExpressEnabled());
		productDTO.setDeliveryCityEnabled(productEntity.isDeliveryCityEnabled());
		productDTO.setDeliveryPickEnabled(productEntity.isDeliveryPickEnabled());
		productDTO.setValidDays(productEntity.getValidDays());
		productDTO.setValidType(productEntity.getValidType());
		productDTO.setValidStartDate(productEntity.getValidStartDate());
		productDTO.setValidEndDate(productEntity.getValidEndDate());
		// 供应商
		SupplierEntity supplierEntity = productEntity.getSupplierEntity();
		if (supplierEntity != null) {
			SupplierDTO supplierDTO = new SupplierDTO();
			supplierDTO.setSupplierUuid(supplierEntity.getSupplierUuid());
			supplierDTO.setSupplierName(supplierEntity.getSupplierName());
			productDTO.setSupplierDTO(supplierDTO);
		}
		// 商家
		MerchantEntity merchantEntity = productEntity.getMerchantEntity();
		if (merchantEntity != null) {
			MerchantDTO merchantDTO = new MerchantDTO();
			merchantDTO.setMerchantUuid(merchantEntity.getMerchantUuid());
			merchantDTO.setMerchantName(merchantEntity.getMerchantName());
			merchantDTO.setMobileNo(merchantEntity.getMobileNo());
			merchantDTO.setContactName(merchantEntity.getContactName());
			merchantDTO.setMerchantDescription(merchantEntity.getMerchantDescription());
			merchantDTO.setMerchantAddress(merchantEntity.getMerchantAddress());
			merchantDTO.setLongitude(merchantEntity.getLongitude());
			merchantDTO.setLatitude(merchantEntity.getLatitude());
			productDTO.setMerchantDTO(merchantDTO);
		}
		// 品牌
		ProductBrandEntity brandEntity = productEntity.getProductBrandEntity();
		ProductBrandDTO brandDTO = new ProductBrandDTO();
		if (brandEntity != null) {
			brandDTO.setName(brandEntity.getName());
			brandDTO.setLogoUrl(brandEntity.getLogoUrl());
			brandDTO.setSortNumber(brandEntity.getSortNumber());
			brandDTO.setProductBrandUuid(brandEntity.getProductBrandUuid());
			productDTO.setProductBrandDTO(brandDTO);
		}
		// 品类
		ProductCateEntity cateEntity = productEntity.getProductCateEntity();
		if (cateEntity != null) {
			ProductCateDTO cateDTO = new ProductCateDTO();
			cateDTO.setProductCateUuid(cateEntity.getProductCateUuid());
			cateDTO.setCateName(cateEntity.getCateName());
			productDTO.setProductCateDTO(cateDTO);
		}
		// 主图
		FileEntity fileEntity = productEntity.getProductMainImage();
		if (fileEntity != null) {
			FileDTO fileDTO = new FileDTO();
			fileDTO.setUrl(fileEntity.getUrl());
			productDTO.setProductMainImage(fileDTO);
		}
		// 运费模板
		ProductFreightEntity productFreightEntity = productEntity.getProductFreightEntity();
		if (productFreightEntity != null) {
			ProductFreightDTO productFreightDTO = new ProductFreightDTO();
			freightEntity2DTO(productFreightEntity, productFreightDTO);
			productFreightDTO.setName(productFreightEntity.getName());
			productDTO.setProductFreightDTO(productFreightDTO);
		}
		// 配送模板
		ProductDeliveryEntity productDeliveryEntity = productEntity.getProductDeliveryEntity();
		if (productDeliveryEntity != null) {
			ProductDeliveryDTO productDeliveryDTO = new ProductDeliveryDTO();
			deliveryEntity2DTO(productDeliveryEntity, productDeliveryDTO);
			productDTO.setProductDeliveryDTO(productDeliveryDTO);
		}
		try {
			if (productEntity.isSkuEnabled()) {
				int minUnitPoint = productSkuDAO.getMinUnitPointByProduct(productEntity);
				int maxUnitPoint = productSkuDAO.getMaxUnitPointByProduct(productEntity);
				BigDecimal minUnitPrice = productSkuDAO.getMinUnitPriceByProduct(productEntity);
				BigDecimal maxUnitPrice = productSkuDAO.getMaxUnitPriceByProduct(productEntity);
				int minUnitPointStandard = productSkuDAO.getMinUnitPointStandardByProduct(productEntity);
				int maxUnitPointStandard = productSkuDAO.getMaxUnitPointStandardByProduct(productEntity);
				BigDecimal minUnitPriceStandard = productSkuDAO.getMinUnitPriceStandardByProduct(productEntity);
				BigDecimal maxUnitPriceStandard = productSkuDAO.getMaxUnitPriceStandardByProduct(productEntity);
				productDTO.setUnitPointMin(minUnitPoint);
				productDTO.setUnitPointMax(maxUnitPoint);
				productDTO.setUnitPointStandardMin(minUnitPointStandard);
				productDTO.setUnitPointStandardMax(maxUnitPointStandard);
				productDTO.setUnitPriceMin(minUnitPrice);
				productDTO.setUnitPriceMax(maxUnitPrice);
				productDTO.setUnitPriceStandardMin(minUnitPriceStandard);
				productDTO.setUnitPriceStandardMax(maxUnitPriceStandard);
			}
		} catch (DAOException e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public void productSkuEntity2DTO(ProductSkuEntity productSkuEntity, ProductSkuDTO productSkuDTO) {
		String skuAttrValueUuids = productSkuEntity.getSkuAttrValueUuids();
		String tSkuAttrValueUuids = skuAttrValueUuids.replace("[", "").replace("]", "");
		String[] skuAttrValueUuidArray = tSkuAttrValueUuids.split(ProductConstants.DELIMETER + " ");
		List<ProductSkuAttrValueDTO> attrValueDTOList = new ArrayList<ProductSkuAttrValueDTO>();

		for (int i = 0; i < skuAttrValueUuidArray.length; i++) {
			String skuAttrValueUuid = skuAttrValueUuidArray[i];
			try {
				ProductSkuAttrValueEntity skuAttrValueEntity = productSkuAttrValueDAO
						.getProductSkuAttrValueByUuid(skuAttrValueUuid);
				ProductSkuAttrValueDTO skuAttrValueDTO = new ProductSkuAttrValueDTO();
				skuAttrValueDTO
						.setProductSkuAttrUuid(skuAttrValueEntity.getProductSkuAttrEntity().getProductSkuAttrUuid());
				skuAttrValueDTO.setProductSkuAttrValueUuid(skuAttrValueEntity.getProductSkuAttrValueUuid());
				skuAttrValueDTO.setSkuAttrName(skuAttrValueEntity.getProductSkuAttrEntity().getSkuAttrName());
				skuAttrValueDTO.setSkuAttrValue(skuAttrValueEntity.getSkuAttrValue());
				attrValueDTOList.add(skuAttrValueDTO);
			} catch (DAOException e) {
				logger.error("获取skuAttrValueUuid属性值失败:" + skuAttrValueUuid);
			}
		}

		productSkuDTO.setSkuAttrValueList(attrValueDTOList);
		productSkuDTO.setSkuCode(productSkuEntity.getSkuCode());
		productSkuDTO.setSkuSoldUnit(productSkuEntity.getSkuSoldUnit());
		productSkuDTO.setSkuTotalUnit(productSkuEntity.getSkuTotalUnit());
		productSkuDTO.setSkuUnitPoint(productSkuEntity.getSkuUnitPoint());
		productSkuDTO.setSkuUnitPointStandard(productSkuEntity.getSkuUnitPointStandard());
		productSkuDTO.setSkuUnitPrice(productSkuEntity.getSkuUnitPrice());
		productSkuDTO.setSkuUnitPriceStandard(productSkuEntity.getSkuUnitPriceStandard());
		productSkuDTO.setSkuImageUrl(productSkuEntity.getSkuImageUrl());
		productSkuDTO.setProductSkuUuid(productSkuEntity.getProductSkuUuid());
	}

	void productBrandEntity2DTO(ProductBrandEntity productBrandEntity, ProductBrandDTO productBrandDTO) {
		productBrandDTO.setProductBrandUuid(productBrandEntity.getProductBrandUuid());
		productBrandDTO.setName(productBrandEntity.getName());
		productBrandDTO.setDescription(productBrandEntity.getDescription());
		productBrandDTO.setLogoUrl(productBrandEntity.getLogoUrl());
		productBrandDTO.setSortNumber(productBrandEntity.getSortNumber());
	}
	@Override
	public void productDetailEntity2DTO(ProductEntity productEntity, ProductDTO productDTO) {
		productDTO.setProductUuid(productEntity.getProductUuid());
		productDTO.setProductName(productEntity.getProductName());
		productDTO.setHot(productEntity.isHot());
		productDTO.setMarketUnitPrice(productEntity.getMarketUnitPrice());
		productDTO.setMemo(productEntity.getMemo());
		productDTO.setNew(productEntity.isNew());
		productDTO.setOnSale(productEntity.isOnSale());
		productDTO.setProductCode(productEntity.getProductCode());
		productDTO.setProductDesc(productEntity.getProductDesc());
		productDTO.setPromote(productEntity.isPromote());
		productDTO.setPromoteEndDate(productEntity.getPromoteEndDate());
		productDTO.setPromoteStartDate(productEntity.getPromoteStartDate());
		productDTO.setPromoteUnitPrice(productEntity.getPromoteUnitPrice());
		productDTO.setMembershipUnitPrice(productEntity.getMembershipUnitPrice());
		productDTO.setProductType(productEntity.getProductType());
		productDTO.setRecommend(productEntity.isRecommend());
		productDTO.setShipping(productEntity.isShipping());
		productDTO.setSkuEnabled(productEntity.isSkuEnabled());
		productDTO.setSoldUnit(productEntity.getSoldUnit());
		productDTO.setTotalUnit(productEntity.getTotalUnit());
		productDTO.setUnitPoint(productEntity.getUnitPoint());
		productDTO.setUnitPointStandard(productEntity.getUnitPointStandard());
		productDTO.setUnitPrice(productEntity.getUnitPrice());
		productDTO.setUnitPriceStandard(productEntity.getUnitPriceStandard());
		productDTO.setWarnUnit(productEntity.getWarnUnit());
		productDTO.setProductCostPrice(productEntity.getProductCostPrice());
		productDTO.setProductWeight(productEntity.getProductWeight());
		productDTO.setVerifyStatus(productEntity.getVerifyStatus());
		productDTO.setVerifyMsg(productEntity.getVerifyMsg());
		productDTO.setDeliveryExpressEnabled(productEntity.isDeliveryExpressEnabled());
		productDTO.setDeliveryCityEnabled(productEntity.isDeliveryCityEnabled());
		productDTO.setDeliveryPickEnabled(productEntity.isDeliveryPickEnabled());
		productDTO.setValidDays(productEntity.getValidDays());
		productDTO.setValidType(productEntity.getValidType());
		productDTO.setValidStartDate(productEntity.getValidStartDate());
		productDTO.setValidEndDate(productEntity.getValidEndDate());

		// 供应商
		SupplierEntity supplierEntity = productEntity.getSupplierEntity();
		if (supplierEntity != null) {
			SupplierDTO supplierDTO = new SupplierDTO();
			supplierDTO.setSupplierUuid(supplierEntity.getSupplierUuid());
			supplierDTO.setSupplierName(supplierEntity.getSupplierName());
			productDTO.setSupplierDTO(supplierDTO);
		}
		// 商家
		MerchantEntity merchantEntity = productEntity.getMerchantEntity();
		if (merchantEntity != null) {
			MerchantDTO merchantDTO = new MerchantDTO();
			merchantDTO.setMerchantUuid(merchantEntity.getMerchantUuid());
			merchantDTO.setMerchantName(merchantEntity.getMerchantName());
			merchantDTO.setMobileNo(merchantEntity.getMobileNo());
			merchantDTO.setContactName(merchantEntity.getContactName());
			merchantDTO.setMerchantDescription(merchantEntity.getMerchantDescription());
			merchantDTO.setScore(merchantEntity.getScore());
			merchantDTO.setSaleUnit(merchantEntity.getSaleUnit());
			merchantDTO.setSoldUnit(merchantEntity.getSoldUnit());
			merchantDTO.setWeight(merchantEntity.getWeight());
			merchantDTO.setMerchantAddress(merchantEntity.getMerchantAddress());
			merchantDTO.setLatitude(merchantEntity.getLatitude());
			merchantDTO.setLongitude(merchantEntity.getLongitude());
			productDTO.setMerchantDTO(merchantDTO);
		}
		// 品牌
		ProductBrandEntity brandEntity = productEntity.getProductBrandEntity();
		ProductBrandDTO brandDTO = new ProductBrandDTO();
		if (brandEntity != null) {
			brandDTO.setName(brandEntity.getName());
			brandDTO.setLogoUrl(brandEntity.getLogoUrl());
			brandDTO.setSortNumber(brandEntity.getSortNumber());
			brandDTO.setProductBrandUuid(brandEntity.getProductBrandUuid());
			productDTO.setProductBrandDTO(brandDTO);
		}
		// 品类
		ProductCateEntity cateEntity = productEntity.getProductCateEntity();
		if (cateEntity != null) {
			ProductCateDTO cateDTO = new ProductCateDTO();
			cateDTO.setProductCateUuid(cateEntity.getProductCateUuid());
			cateDTO.setCateName(cateEntity.getCateName());
			productDTO.setProductCateDTO(cateDTO);
		}
		// 主图
		FileEntity fileEntity = productEntity.getProductMainImage();
		if (fileEntity != null) {
			FileDTO fileDTO = new FileDTO();
			fileDTO.setUrl(fileEntity.getUrl());
			productDTO.setProductMainImage(fileDTO);
		}
		// 运费模板
		ProductFreightEntity productFreightEntity = productEntity.getProductFreightEntity();
		if (productFreightEntity != null) {
			ProductFreightDTO productFreightDTO = new ProductFreightDTO();
			freightEntity2DTO(productFreightEntity, productFreightDTO);
			productDTO.setProductFreightDTO(productFreightDTO);
		}
		// 同城配送模板
		ProductDeliveryEntity productDeliveryEntity = productEntity.getProductDeliveryEntity();
		if (productDeliveryEntity != null) {
			ProductDeliveryDTO productDeliveryDTO = new ProductDeliveryDTO();
			deliveryEntity2DTO(productDeliveryEntity, productDeliveryDTO);
			productDTO.setProductDeliveryDTO(productDeliveryDTO);
		}
		// 虚拟商品优惠券
		/*
		 * CouponEntity couponEntity = productEntity.getCouponEntity(); if(couponEntity
		 * != null){ CouponDTO couponDTO = new CouponDTO();
		 * couponDTO.setName(couponEntity.getName());
		 * couponDTO.setMemo(couponEntity.getMemo());
		 * couponDTO.setType(couponEntity.getType());
		 * couponDTO.setValidType(couponEntity.getValidType());
		 * couponDTO.setValidDays(couponEntity.getValidDays());
		 * couponDTO.setStartDate(couponEntity.getStartDate());
		 * couponDTO.setEndDate(couponEntity.getEndDate());
		 * couponDTO.setTotalCount(couponEntity.getTotalCount());
		 * couponDTO.setLimitPerUser(couponEntity.getLimitPerUser());
		 * couponDTO.setActive(couponEntity.isActive());
		 * couponDTO.setAvailableCount(couponEntity.getAvailableCount());
		 * couponDTO.setCouponCode(couponEntity.getCouponCode());
		 * couponDTO.setCouponUuid(couponEntity.getCouponUuid());
		 * couponDTO.setBenefitCash(couponEntity.getBenefitCash());
		 * couponDTO.setBenefitDiscount(couponEntity.getBenefitDiscount());
		 * couponDTO.setAllProductBenefit(couponEntity.isAllProductBenefit());
		 * productDTO.setCouponDTO(couponDTO); }
		 */

		try {
			// 商品图片
			List<ProductImageEntity> images = productImageDAO.getImagesByProduct(productEntity);
			if (images != null) {
				List<FileDTO> fileDTOList = new ArrayList<FileDTO>();
				for (Iterator<ProductImageEntity> iter = images.iterator(); iter.hasNext();) {
					FileEntity imageEntity = iter.next().getFileEntity();
					FileDTO imageDTO = new FileDTO();
					imageDTO.setUrl(imageEntity.getUrl());
					fileDTOList.add(imageDTO);
				}
				productDTO.setProductImages(fileDTOList);
			}

			// 商品视频
			List<ProductVideoEntity> videos = productVideoDAO.getVideosByProduct(productEntity);
			if (videos != null) {
				List<FileDTO> fileDTOList = new ArrayList<FileDTO>();
				for (Iterator<ProductVideoEntity> iter = videos.iterator(); iter.hasNext();) {
					FileEntity imageEntity = iter.next().getFileEntity();
					FileDTO videoDTO = new FileDTO();
					videoDTO.setUrl(imageEntity.getUrl());
					fileDTOList.add(videoDTO);
				}
				productDTO.setProductVideos(fileDTOList);
			}

			// 商品描述图片
			List<ProductDescImageEntity> descImages = productDescImageDAO.getDescImagesByProduct(productEntity);
			if (descImages != null) {
				List<FileDTO> fileDTOList = new ArrayList<FileDTO>();
				for (Iterator<ProductDescImageEntity> iter = descImages.iterator(); iter.hasNext();) {
					FileEntity imageEntity = iter.next().getFileEntity();
					FileDTO videoDTO = new FileDTO();
					videoDTO.setUrl(imageEntity.getUrl());
					fileDTOList.add(videoDTO);
				}
				productDTO.setProductDescImages(fileDTOList);
			}

			// 商品属性
			List<ProductAttrValueDTO> attrDTOList = new ArrayList<ProductAttrValueDTO>();
			List<ProductAttrValueEntity> attrEntityList = productAttrValueDAO.getProductAttrByProduct(productEntity);
			for (Iterator<ProductAttrValueEntity> iter = attrEntityList.iterator(); iter.hasNext();) {
				ProductAttrValueEntity attrEntity = iter.next();
				ProductAttrValueDTO attrDTO = new ProductAttrValueDTO();
				attrDTO.setProductAttrValueUuid(attrEntity.getProductAttrValueUuid());
				attrDTO.setCateAttr(attrEntity.isCateAttr());
				// 如果继承自品类属性, 关联品类属性
				if (attrEntity.isCateAttr()) {
					ProductCateAttrDTO cateAttrDTO = attrDTO.getProductCateAttrDTO();
					ProductCateAttrEntity cateAttrEntity = attrEntity.getProductCateAttrEntity();
					cateAttrDTO.setProductCateAttrUuid(cateAttrEntity.getProductCateAttrUuid());
					cateAttrDTO.setAttrName(cateAttrEntity.getAttrName());
					// 将字符串属性值V1:V2:V3转化为数组
					String attrValue = cateAttrEntity.getAttrValue();
					if (!StringUtils.isEmpty(attrValue)) {
						StringTokenizer token = new StringTokenizer(attrValue, ProductConstants.DELIMETER);
						List<String> valueList = new ArrayList<String>();
						while (token.hasMoreTokens()) {
							String tValue = token.nextToken();
							valueList.add(tValue);
						}
						cateAttrDTO.setAttrValueArray(valueList.toArray(new String[valueList.size()]));
					}

					cateAttrDTO.setSpuAttr(cateAttrEntity.isSpuAttr());
					cateAttrDTO.setSkuAttr(cateAttrEntity.isSkuAttr());
					cateAttrDTO.setSearchAttr(cateAttrEntity.isSearchAttr());
					cateAttrDTO.setMandatory(cateAttrEntity.isMandatory());
					cateAttrDTO.setInput(cateAttrEntity.isInput());
					cateAttrDTO.setMultiple(cateAttrEntity.isMultiple());
					attrDTO.setProductCateAttrDTO(cateAttrDTO);
				}
				attrDTO.setProductAttrName(attrEntity.getProductAttrName());
				attrDTO.setProductAttrValue(attrEntity.getProductAttrValue());
				attrDTOList.add(attrDTO);
			}
			productDTO.setAttrList(attrDTOList);

			// 商品SKU属性
			List<ProductSkuAttrEntity> skuAttrEntityList = productSkuAttrDAO.getProductAttrByProduct(productEntity);
			List<ProductSkuAttrDTO> skuAttrDTOList = new ArrayList<ProductSkuAttrDTO>();
			for (Iterator<ProductSkuAttrEntity> iter = skuAttrEntityList.iterator(); iter.hasNext();) {
				ProductSkuAttrEntity skuAttrEntity = iter.next();
				ProductSkuAttrDTO skuAttrDTO = new ProductSkuAttrDTO();
				skuAttrDTO.setProductSkuAttrUuid(skuAttrEntity.getProductSkuAttrUuid());
				skuAttrDTO.setSkuAttrName(skuAttrEntity.getSkuAttrName());
				skuAttrDTO.setCateAttr(skuAttrEntity.isCateAttr());
				if (skuAttrEntity.isCateAttr()) {
					ProductCateAttrEntity cateAttrEntity = skuAttrEntity.getProductCateAttrEntity();
					ProductCateAttrDTO cateAttrDTO = new ProductCateAttrDTO();
					cateAttrDTO.setProductCateAttrUuid(cateAttrEntity.getProductCateAttrUuid());
					cateAttrDTO.setAttrName(cateAttrEntity.getAttrName());
					// 将字符串属性值V1:V2:V3转化为数组
					String attrValue = cateAttrEntity.getAttrValue();
					if (!StringUtils.isEmpty(attrValue)) {
						StringTokenizer token = new StringTokenizer(attrValue, ProductConstants.DELIMETER);
						List<String> valueList = new ArrayList<String>();
						while (token.hasMoreTokens()) {
							String tValue = token.nextToken();
							valueList.add(tValue);
						}
						cateAttrDTO.setAttrValueArray(valueList.toArray(new String[valueList.size()]));
					}

					cateAttrDTO.setSpuAttr(cateAttrEntity.isSpuAttr());
					cateAttrDTO.setSkuAttr(cateAttrEntity.isSkuAttr());
					cateAttrDTO.setSearchAttr(cateAttrEntity.isSearchAttr());
					cateAttrDTO.setMandatory(cateAttrEntity.isMandatory());
					cateAttrDTO.setInput(cateAttrEntity.isInput());
					cateAttrDTO.setMultiple(cateAttrEntity.isMultiple());
					skuAttrDTO.setProductCateAttrDTO(cateAttrDTO);
				}
				List<ProductSkuAttrValueEntity> skuAttrValueEntityList = productSkuAttrValueDAO
						.getProductSkuAttrValuesByAttr(skuAttrEntity);
				List<ProductSkuAttrValueDTO> skuAttrValueDTOList = new ArrayList<ProductSkuAttrValueDTO>();
				for (Iterator<ProductSkuAttrValueEntity> valueIter = skuAttrValueEntityList.iterator(); valueIter
						.hasNext();) {
					ProductSkuAttrValueEntity skuAttrValueEntity = valueIter.next();
					ProductSkuAttrValueDTO skuAttrValueDTO = new ProductSkuAttrValueDTO();
					skuAttrValueDTO.setProductSkuAttrValueUuid(skuAttrValueEntity.getProductSkuAttrValueUuid());
					skuAttrValueDTO.setSkuAttrValue(skuAttrValueEntity.getSkuAttrValue());
					skuAttrValueDTO.setProductSkuAttrUuid(skuAttrEntity.getProductSkuAttrUuid());
					skuAttrValueDTOList.add(skuAttrValueDTO);
				}
				skuAttrDTO.setProductAttrValueList(skuAttrValueDTOList);
				skuAttrDTOList.add(skuAttrDTO);
			}
			productDTO.setSkuAttrList(skuAttrDTOList);

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

			// 参与的优惠活动
			List<ProductPromoteEntity> promoteEntityList = productPromoteDAO.getPromoteByProduct(productEntity);
			for (Iterator<ProductPromoteEntity> iter = promoteEntityList.iterator(); iter.hasNext();) {
				ProductPromoteEntity promoteEntity = iter.next();
				String promoteType = promoteEntity.getPromoteType();
				String promoteUuid = promoteEntity.getPromoteUuid();
				if (PromoteConstants.PROMOTE_TYPE_MONEYOFF.equals(promoteType)) {
					PromoteMoneyOffEntity moneyOffEntity = promoteMoneyOffDAO.getPromoteMoneyOffByUuid(promoteUuid);
					PromoteMoneyOffDTO moneyOffDTO = new PromoteMoneyOffDTO();
					moneyOffDTO.setName(moneyOffEntity.getName());
					moneyOffDTO.setValidStartDate(moneyOffEntity.getValidStartDate());
					moneyOffDTO.setValidEndDate(moneyOffEntity.getValidEndDate());
					moneyOffDTO.setBenefitCash(moneyOffEntity.getBenefitCash());
					moneyOffDTO.setConditionAmount(moneyOffEntity.getConditionAmount());
					productDTO.setMoneyOffDTO(moneyOffDTO);
				} else if (PromoteConstants.PROMOTE_TYPE_DISCOUNT.equals(promoteType)) {
					PromoteDiscountEntity discountEntity = promoteDiscountDAO.getPromoteDiscountByUuid(promoteUuid);
					PromoteDiscountDTO discountDTO = new PromoteDiscountDTO();
					discountDTO.setName(discountEntity.getName());
					discountDTO.setValidStartDate(discountEntity.getValidStartDate());
					discountDTO.setValidEndDate(discountEntity.getValidEndDate());
					discountDTO.setBenefitDiscount(discountEntity.getBenefitDiscount());
					discountDTO.setConditionAmount(discountEntity.getConditionAmount());
					productDTO.setDiscountDTO(discountDTO);
				} else if (PromoteConstants.PROMOTE_TYPE_FREIGHTOFF.equals(promoteType)) {
					PromoteFreightOffEntity freightOffEntity = promoteFreightOffDAO
							.getPromoteFreightOffByUuid(promoteUuid);
					PromoteFreightOffDTO freightOffDTO = new PromoteFreightOffDTO();
					freightOffDTO.setName(freightOffEntity.getName());
					freightOffDTO.setValidStartDate(freightOffEntity.getValidStartDate());
					freightOffDTO.setValidEndDate(freightOffEntity.getValidEndDate());
					freightOffDTO.setConditionAmount(freightOffEntity.getConditionAmount());
					productDTO.setFreightOffDTO(freightOffDTO);
				}
			}
			try {
				if (productEntity.isSkuEnabled()) {
					int minUnitPoint = productSkuDAO.getMinUnitPointByProduct(productEntity);
					int maxUnitPoint = productSkuDAO.getMaxUnitPointByProduct(productEntity);
					int minUnitPointStandard = productSkuDAO.getMinUnitPointStandardByProduct(productEntity);
					int maxUnitPointStandard = productSkuDAO.getMaxUnitPointStandardByProduct(productEntity);
					productDTO.setUnitPointMin(minUnitPoint);
					productDTO.setUnitPointMax(maxUnitPoint);
					productDTO.setUnitPointStandardMin(minUnitPointStandard);
					productDTO.setUnitPointStandardMax(maxUnitPointStandard);
				}
			} catch (DAOException e) {
				logger.error(e.getMessage());
			}
		} catch (DAOException e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public void addProductGroup(GroupDTO groupDTO) throws BusinessException {
		try {
			List<ProductDTO> productList = groupDTO.getProductList();
			GroupEntity groupEntity = groupDAO.getGroupByName(groupDTO.getGroupName());
			if (groupEntity != null)
				throw new BusinessException(BusinessErrorCode.PRODUCT_GROUP_DUPLICATE);
			groupEntity = new GroupEntity();
			groupEntity.setGroupName(groupDTO.getGroupName());
			groupEntity.setGroupDescription(groupDTO.getGroupDescription());
			groupEntity.setBackgroundUrl(groupDTO.getBackgroundUrl());
			groupEntity.setIconUrl(groupDTO.getIconUrl());
			groupEntity.setDisplayedHome(groupDTO.isDisplayedHome());
			groupEntity.setRegister(groupDTO.isRegister());
			groupEntity.setSortNumber(groupDTO.getSortNumber());
			
			MerchantDTO merchantDTO = groupDTO.getMerchantDTO();
			if(merchantDTO != null) {
				MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
				groupEntity.setMerchantEntity(merchantEntity);
			}
			
			groupDAO.addGroup(groupEntity);

			for (Iterator<ProductDTO> iterator = productList.iterator(); iterator.hasNext();) {
				ProductDTO productDTO = iterator.next();
				ProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
				ProductGroupEntity pgEntity = new ProductGroupEntity();
				pgEntity.setProductEntity(productEntity);
				pgEntity.setGroupEntity(groupEntity);
				productGroupDAO.createProductGroup(pgEntity);
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void updateProductGroup(GroupDTO groupDTO) throws BusinessException {
		try {
			if(groupDTO.isRegister()) {
				//专用商品组只允许有一个, 先取消所有专用商品组, 后续再更新
				List<GroupEntity> entityList = groupDAO.getGroups();
				for (GroupEntity groupEntity : entityList) {
					groupEntity.setRegister(false);
					groupDAO.updateGroup(groupEntity);
				}
			}
			GroupEntity groupEntity = groupDAO.getGroupByUuid(groupDTO.getGroupUuid());
			groupEntity.setGroupName(groupDTO.getGroupName());
			groupEntity.setGroupDescription(groupDTO.getGroupDescription());
			groupEntity.setBackgroundUrl(groupDTO.getBackgroundUrl());
			groupEntity.setIconUrl(groupDTO.getIconUrl());
			groupEntity.setDisplayedHome(groupDTO.isDisplayedHome());
			groupEntity.setRegister(groupDTO.isRegister());
			groupEntity.setSortNumber(groupDTO.getSortNumber());
			groupDAO.updateGroup(groupEntity);

			List<ProductGroupEntity> pgEntityList = groupEntity.getProductGroupList();
			groupEntity.setProductGroupList(null);
			for (Iterator<ProductGroupEntity> iterator = pgEntityList.iterator(); iterator.hasNext();) {
				ProductGroupEntity productGroupEntity = iterator.next();
				productGroupDAO.deleteProductGroup(productGroupEntity);
			}

			List<ProductDTO> productList = groupDTO.getProductList();
			for (Iterator<ProductDTO> iterator = productList.iterator(); iterator.hasNext();) {
				ProductDTO productDTO = iterator.next();
				ProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
				ProductGroupEntity pgEntity = new ProductGroupEntity();
				pgEntity.setProductEntity(productEntity);
				pgEntity.setGroupEntity(groupEntity);
				productGroupDAO.createProductGroup(pgEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}
	
	@Override
	public void setProductGroupDisplayedHome(GroupDTO groupDTO) throws BusinessException {
		try {
			GroupEntity groupEntity = groupDAO.getGroupByUuid(groupDTO.getGroupUuid());
			groupEntity.setDisplayedHome(true);
			groupDAO.updateGroup(groupEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}
	
	@Override
	public void cancelProductGroupDisplayedHome(GroupDTO groupDTO) throws BusinessException {
		try {
			GroupEntity groupEntity = groupDAO.getGroupByUuid(groupDTO.getGroupUuid());
			groupEntity.setDisplayedHome(false);
			groupDAO.updateGroup(groupEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}
	
	@Override
	public void setProductGroupForRegister(GroupDTO groupDTO) throws BusinessException {
		try {
			List<GroupEntity> entityList = groupDAO.getGroups();
			for (GroupEntity groupEntity : entityList) {
				groupEntity.setRegister(false);
				groupDAO.updateGroup(groupEntity);
			}
			GroupEntity groupEntity = groupDAO.getGroupByUuid(groupDTO.getGroupUuid());
			groupEntity.setRegister(true);
			groupDAO.updateGroup(groupEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}
	
	@Override
	public void cancelProductGroupForRegister(GroupDTO groupDTO) throws BusinessException {
		try {
			GroupEntity groupEntity = groupDAO.getGroupByUuid(groupDTO.getGroupUuid());
			groupEntity.setRegister(false);
			groupDAO.updateGroup(groupEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public void deleteProductGroup(GroupDTO groupDTO) throws BusinessException {
		try {
			GroupEntity groupEntity = groupDAO.getGroupByUuid(groupDTO.getGroupUuid());

			List<ProductGroupEntity> pgEntityList = groupEntity.getProductGroupList();
			groupEntity.setProductGroupList(null);
			for (Iterator<ProductGroupEntity> iterator = pgEntityList.iterator(); iterator.hasNext();) {
				ProductGroupEntity productGroupEntity = iterator.next();
				productGroupDAO.deleteProductGroup(productGroupEntity);
			}

			groupDAO.deleteGroup(groupEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public List<GroupDTO> getProductGroups() throws BusinessException {
		List<GroupDTO> groupList = new ArrayList<GroupDTO>();
		try {
			List<GroupEntity> groupEntityList = groupDAO.getGroups();
			for (Iterator<GroupEntity> iterator = groupEntityList.iterator(); iterator.hasNext();) {
				GroupEntity groupEntity = iterator.next();
				GroupDTO groupDTO = new GroupDTO();
				groupDTO.setGroupUuid(groupEntity.getGroupUuid());
				groupDTO.setGroupName(groupEntity.getGroupName());
				groupDTO.setGroupDescription(groupEntity.getGroupDescription());
				groupDTO.setBackgroundUrl(groupEntity.getBackgroundUrl());
				groupDTO.setIconUrl(groupEntity.getIconUrl());
				groupDTO.setDisplayedHome(groupEntity.isDisplayedHome());
				groupDTO.setRegister(groupEntity.isRegister());
				groupDTO.setSortNumber(groupEntity.getSortNumber());

				List<ProductGroupEntity> pgList = groupEntity.getProductGroupList();
				List<ProductDTO> productDTOList = new ArrayList<ProductDTO>();
				for (Iterator<ProductGroupEntity> iterator2 = pgList.iterator(); iterator2.hasNext();) {
					ProductGroupEntity productGroupEntity = iterator2.next();
					ProductEntity productEntity = productGroupEntity.getProductEntity();
					ProductDTO productDTO = new ProductDTO();
					productDTO.setProductUuid(productEntity.getProductUuid());
					productDTO.setProductName(productEntity.getProductName());
					productDTO.setUnitPoint(productEntity.getUnitPoint());
					productDTO.setUnitPointStandard(productEntity.getUnitPointStandard());
					productDTO.setUnitPrice(productEntity.getUnitPrice());
					productDTO.setUnitPriceStandard(productEntity.getUnitPriceStandard());
					FileEntity fileEntity = productEntity.getProductMainImage();
					FileDTO fileDTO = new FileDTO();
					fileDTO.setUrl(fileEntity.getUrl());
					productDTO.setProductMainImage(fileDTO);
					productDTOList.add(productDTO);
				}
				groupDTO.setProductList(productDTOList);
				groupList.add(groupDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return groupList;
	}
	
	@Override
	public List<GroupDTO> getProductGroupsByMerchant(MerchantDTO merchantDTO) throws BusinessException {
		List<GroupDTO> groupList = new ArrayList<GroupDTO>();
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			List<GroupEntity> groupEntityList = groupDAO.getGroupsByMerchant(merchantEntity);
			for (Iterator<GroupEntity> iterator = groupEntityList.iterator(); iterator.hasNext();) {
				GroupEntity groupEntity = iterator.next();
				GroupDTO groupDTO = new GroupDTO();
				groupDTO.setGroupUuid(groupEntity.getGroupUuid());
				groupDTO.setGroupName(groupEntity.getGroupName());
				groupDTO.setGroupDescription(groupEntity.getGroupDescription());
				groupDTO.setBackgroundUrl(groupEntity.getBackgroundUrl());
				groupDTO.setIconUrl(groupEntity.getIconUrl());
				groupDTO.setDisplayedHome(groupEntity.isDisplayedHome());
				groupDTO.setRegister(groupEntity.isRegister());
				groupDTO.setSortNumber(groupEntity.getSortNumber());

				List<ProductGroupEntity> pgList = groupEntity.getProductGroupList();
				List<ProductDTO> productDTOList = new ArrayList<ProductDTO>();
				for (Iterator<ProductGroupEntity> iterator2 = pgList.iterator(); iterator2.hasNext();) {
					ProductGroupEntity productGroupEntity = iterator2.next();
					ProductEntity productEntity = productGroupEntity.getProductEntity();
					ProductDTO productDTO = new ProductDTO();
					productDTO.setProductUuid(productEntity.getProductUuid());
					productDTO.setProductName(productEntity.getProductName());
					productDTO.setUnitPoint(productEntity.getUnitPoint());
					productDTO.setUnitPointStandard(productEntity.getUnitPointStandard());
					productDTO.setUnitPrice(productEntity.getUnitPrice());
					productDTO.setUnitPriceStandard(productEntity.getUnitPriceStandard());
					FileEntity fileEntity = productEntity.getProductMainImage();
					FileDTO fileDTO = new FileDTO();
					fileDTO.setUrl(fileEntity.getUrl());
					productDTO.setProductMainImage(fileDTO);
					productDTOList.add(productDTO);
				}
				groupDTO.setProductList(productDTOList);
				groupList.add(groupDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return groupList;
	}

	@Override
	public List<GroupDTO> getHomeProductGroups() throws BusinessException {
		List<GroupDTO> groupList = new ArrayList<GroupDTO>();
		try {
			List<GroupEntity> groupEntityList = groupDAO.getHomeGroups();
			for (Iterator<GroupEntity> iterator = groupEntityList.iterator(); iterator.hasNext();) {
				GroupEntity groupEntity = iterator.next();
				GroupDTO groupDTO = new GroupDTO();
				groupDTO.setGroupUuid(groupEntity.getGroupUuid());
				groupDTO.setGroupName(groupEntity.getGroupName());
				groupDTO.setGroupDescription(groupEntity.getGroupDescription());
				groupDTO.setBackgroundUrl(groupEntity.getBackgroundUrl());
				groupDTO.setIconUrl(groupEntity.getIconUrl());
				groupDTO.setDisplayedHome(groupEntity.isDisplayedHome());
				groupDTO.setRegister(groupEntity.isRegister());
				groupDTO.setSortNumber(groupEntity.getSortNumber());

				List<ProductGroupEntity> pgList = groupEntity.getProductGroupList();
				List<ProductDTO> productDTOList = new ArrayList<ProductDTO>();
				for (Iterator<ProductGroupEntity> iterator2 = pgList.iterator(); iterator2.hasNext();) {
					ProductGroupEntity productGroupEntity = iterator2.next();
					ProductEntity productEntity = productGroupEntity.getProductEntity();
					ProductDTO productDTO = new ProductDTO();
					productDTO.setProductUuid(productEntity.getProductUuid());
					productDTO.setProductName(productEntity.getProductName());
					productDTO.setUnitPoint(productEntity.getUnitPoint());
					productDTO.setUnitPointStandard(productEntity.getUnitPointStandard());
					productDTO.setUnitPrice(productEntity.getUnitPrice());
					productDTO.setUnitPriceStandard(productEntity.getUnitPriceStandard());
					FileEntity fileEntity = productEntity.getProductMainImage();
					FileDTO fileDTO = new FileDTO();
					fileDTO.setUrl(fileEntity.getUrl());
					productDTO.setProductMainImage(fileDTO);
					productDTOList.add(productDTO);
				}
				groupDTO.setProductList(productDTOList);
				groupList.add(groupDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return groupList;
	}

	@Override
	public GroupDTO getProductGroupByUuid(String uuid) throws BusinessException {
		try {
			GroupEntity groupEntity = groupDAO.getGroupByUuid(uuid);
			GroupDTO groupDTO = new GroupDTO();
			groupDTO.setGroupUuid(groupEntity.getGroupUuid());
			groupDTO.setGroupName(groupEntity.getGroupName());
			groupDTO.setGroupDescription(groupEntity.getGroupDescription());
			groupDTO.setBackgroundUrl(groupEntity.getBackgroundUrl());
			groupDTO.setIconUrl(groupEntity.getIconUrl());
			groupDTO.setDisplayedHome(groupEntity.isDisplayedHome());
			groupDTO.setRegister(groupEntity.isRegister());
			groupDTO.setSortNumber(groupEntity.getSortNumber());

			List<ProductGroupEntity> pgList = groupEntity.getProductGroupList();
			List<ProductDTO> productDTOList = new ArrayList<ProductDTO>();
			for (Iterator<ProductGroupEntity> iterator2 = pgList.iterator(); iterator2.hasNext();) {
				ProductGroupEntity productGroupEntity = iterator2.next();
				ProductEntity productEntity = productGroupEntity.getProductEntity();
				ProductDTO productDTO = new ProductDTO();
				productDTO.setProductUuid(productEntity.getProductUuid());
				productDTO.setProductName(productEntity.getProductName());
				productDTO.setUnitPoint(productEntity.getUnitPoint());
				productDTO.setUnitPointStandard(productEntity.getUnitPointStandard());
				productDTO.setUnitPrice(productEntity.getUnitPrice());
				productDTO.setUnitPriceStandard(productEntity.getUnitPriceStandard());
				FileEntity fileEntity = productEntity.getProductMainImage();
				FileDTO fileDTO = new FileDTO();
				fileDTO.setUrl(fileEntity.getUrl());
				productDTO.setProductMainImage(fileDTO);
				productDTOList.add(productDTO);
			}
			groupDTO.setProductList(productDTOList);
			return groupDTO;

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}
	
	@Override
	public GroupDTO getProductGroupForRegister() throws BusinessException {
		GroupDTO groupDTO = null;
		try {
			GroupEntity groupEntity = groupDAO.getGroupForRegister();
			if(groupEntity != null) {
				groupDTO = new GroupDTO();
				groupDTO.setGroupUuid(groupEntity.getGroupUuid());
				groupDTO.setGroupName(groupEntity.getGroupName());
				groupDTO.setGroupDescription(groupEntity.getGroupDescription());
				groupDTO.setBackgroundUrl(groupEntity.getBackgroundUrl());
				groupDTO.setIconUrl(groupEntity.getIconUrl());
				groupDTO.setDisplayedHome(groupEntity.isDisplayedHome());
				groupDTO.setRegister(groupEntity.isRegister());
				groupDTO.setSortNumber(groupEntity.getSortNumber());

				List<ProductGroupEntity> pgList = groupEntity.getProductGroupList();
				List<ProductDTO> productDTOList = new ArrayList<ProductDTO>();
				for (Iterator<ProductGroupEntity> iterator2 = pgList.iterator(); iterator2.hasNext();) {
					ProductGroupEntity productGroupEntity = iterator2.next();
					ProductEntity productEntity = productGroupEntity.getProductEntity();
					ProductDTO productDTO = new ProductDTO();
					productDTO.setProductUuid(productEntity.getProductUuid());
					productDTO.setProductName(productEntity.getProductName());
					productDTO.setUnitPoint(productEntity.getUnitPoint());
					productDTO.setUnitPointStandard(productEntity.getUnitPointStandard());
					productDTO.setUnitPrice(productEntity.getUnitPrice());
					productDTO.setUnitPriceStandard(productEntity.getUnitPriceStandard());
					FileEntity fileEntity = productEntity.getProductMainImage();
					FileDTO fileDTO = new FileDTO();
					fileDTO.setUrl(fileEntity.getUrl());
					productDTO.setProductMainImage(fileDTO);
					productDTOList.add(productDTO);
				}
				groupDTO.setProductList(productDTOList);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return groupDTO;
	}

	@Override
	public GroupDTO getProductGroupByName(String name) throws BusinessException {
		try {
			GroupEntity groupEntity = groupDAO.getGroupByName(name);
			GroupDTO groupDTO = new GroupDTO();
			groupDTO.setGroupName(groupEntity.getGroupName());
			groupDTO.setGroupDescription(groupEntity.getGroupDescription());
			groupDTO.setBackgroundUrl(groupEntity.getBackgroundUrl());
			groupDTO.setIconUrl(groupEntity.getIconUrl());
			groupDTO.setDisplayedHome(groupEntity.isDisplayedHome());
			groupDTO.setRegister(groupEntity.isRegister());
			groupDTO.setSortNumber(groupEntity.getSortNumber());

			List<ProductGroupEntity> pgList = groupEntity.getProductGroupList();
			List<ProductDTO> productDTOList = new ArrayList<ProductDTO>();
			for (Iterator<ProductGroupEntity> iterator2 = pgList.iterator(); iterator2.hasNext();) {
				ProductGroupEntity productGroupEntity = iterator2.next();
				ProductEntity productEntity = productGroupEntity.getProductEntity();
				ProductDTO productDTO = new ProductDTO();
				productDTO.setProductUuid(productEntity.getProductUuid());
				productDTO.setProductName(productEntity.getProductName());
				productDTO.setUnitPoint(productEntity.getUnitPoint());
				productDTO.setUnitPointStandard(productEntity.getUnitPointStandard());
				productDTO.setUnitPrice(productEntity.getUnitPrice());
				productDTO.setUnitPriceStandard(productEntity.getUnitPriceStandard());
				FileEntity fileEntity = productEntity.getProductMainImage();
				FileDTO fileDTO = new FileDTO();
				fileDTO.setUrl(fileEntity.getUrl());
				productDTO.setProductMainImage(fileDTO);
				productDTOList.add(productDTO);
			}
			groupDTO.setProductList(productDTOList);
			return groupDTO;

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public void addProductFreight(ProductFreightDTO productFreightDTO) throws BusinessException {
		try {
			ProductFreightEntity freightEntity = new ProductFreightEntity();
			freightEntity.setName(productFreightDTO.getName());
			freightEntity.setChargeType(productFreightDTO.getChargeType());
			freightEntity.setCourierName(productFreightDTO.getCourierName());
			freightEntity.setShippingFree(productFreightDTO.isShippingFree());
			freightEntity.setShippingFreeConditionAmount(productFreightDTO.getShippingFreeConditionAmount());
			freightEntity.setDefaultFirstPrice(productFreightDTO.getDefaultFirstPrice());
			freightEntity.setDefaultFirstUnit(productFreightDTO.getDefaultFirstUnit());
			freightEntity.setDefaultFirstWeight(productFreightDTO.getDefaultFirstWeight());
			freightEntity.setDefaultNextPrice(productFreightDTO.getDefaultNextPrice());
			freightEntity.setDefaultNextUnit(productFreightDTO.getDefaultNextUnit());
			freightEntity.setDefaultNextWeight(productFreightDTO.getDefaultNextWeight());

			// 商家
			MerchantDTO merchantDTO = productFreightDTO.getMerchantDTO();
			if (merchantDTO != null) {
				MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
				freightEntity.setMerchantEntity(merchantEntity);
			}
			
			productFreightDAO.createProductFreight(freightEntity);

			List<ProductFreightRegionDTO> regionDTOList = productFreightDTO.getFreghtRegionList();
			if (regionDTOList != null) {
				for (Iterator<ProductFreightRegionDTO> iterator = regionDTOList.iterator(); iterator.hasNext();) {
					ProductFreightRegionDTO dto = iterator.next();
					ProductFreightRegionEntity entity = new ProductFreightRegionEntity();
					entity.setFirstPrice(dto.getFirstPrice());
					entity.setFirstUnit(dto.getFirstUnit());
					entity.setFirstWeight(dto.getFirstWeight());
					entity.setNextUnit(dto.getNextUnit());
					entity.setNextWeight(dto.getNextWeight());
					entity.setNextPrice(dto.getNextPrice());
					entity.setProductFreightEntity(freightEntity);
					if (dto.getAreaList().size() > 0) {
						String areaIds = "";
						List<AreaDTO> areaList = dto.getAreaList();
						for (AreaDTO areaDTO : areaList) {
							if (StringUtils.isEmpty(areaIds))
								areaIds = areaDTO.getAreaId();
							else
								areaIds = areaIds + "," + areaDTO.getAreaId();
						}
						entity.setAreaIds(areaIds);
					}
					productFreightRegionDAO.createProductFreightRegion(entity);
				}
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void updateProductFreight(ProductFreightDTO productFreightDTO) throws BusinessException {
		try {
			ProductFreightEntity freightEntity = productFreightDAO
					.getFreightByUuid(productFreightDTO.getProductFreightUuid());
			freightEntity.setName(productFreightDTO.getName());
			freightEntity.setChargeType(productFreightDTO.getChargeType());
			freightEntity.setCourierName(productFreightDTO.getCourierName());
			freightEntity.setShippingFree(productFreightDTO.isShippingFree());
			freightEntity.setShippingFreeConditionAmount(productFreightDTO.getShippingFreeConditionAmount());
			freightEntity.setDefaultFirstPrice(productFreightDTO.getDefaultFirstPrice());
			freightEntity.setDefaultFirstUnit(productFreightDTO.getDefaultFirstUnit());
			freightEntity.setDefaultFirstWeight(productFreightDTO.getDefaultFirstWeight());
			freightEntity.setDefaultNextPrice(productFreightDTO.getDefaultNextPrice());
			freightEntity.setDefaultNextUnit(productFreightDTO.getDefaultNextUnit());
			freightEntity.setDefaultNextWeight(productFreightDTO.getDefaultNextWeight());
			
			// 商家
			MerchantDTO merchantDTO = productFreightDTO.getMerchantDTO();
			if (merchantDTO != null) {
				MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
				freightEntity.setMerchantEntity(merchantEntity);
			}
			
			productFreightDAO.updateProductFreight(freightEntity);

			List<ProductFreightRegionEntity> regionEntityList = productFreightRegionDAO
					.getRegionsByFreight(freightEntity);
			for (Iterator<ProductFreightRegionEntity> iterator = regionEntityList.iterator(); iterator.hasNext();) {
				productFreightRegionDAO.deleteProductFreightRegion(iterator.next());
			}

			List<ProductFreightRegionDTO> regionDTOList = productFreightDTO.getFreghtRegionList();
			if (regionDTOList != null) {
				for (Iterator<ProductFreightRegionDTO> iterator = regionDTOList.iterator(); iterator.hasNext();) {
					ProductFreightRegionDTO dto = iterator.next();
					ProductFreightRegionEntity entity = new ProductFreightRegionEntity();
					entity.setFirstPrice(dto.getFirstPrice());
					entity.setFirstUnit(dto.getFirstUnit());
					entity.setFirstWeight(dto.getFirstWeight());
					entity.setNextUnit(dto.getNextUnit());
					entity.setNextWeight(dto.getNextWeight());
					entity.setNextPrice(dto.getNextPrice());
					entity.setProductFreightEntity(freightEntity);
					if (dto.getAreaList().size() > 0) {
						String areaIds = "";
						List<AreaDTO> areaList = dto.getAreaList();
						for (AreaDTO areaDTO : areaList) {
							if (StringUtils.isEmpty(areaIds))
								areaIds = areaDTO.getAreaId();
							else
								areaIds = areaIds + "," + areaDTO.getAreaId();
						}
						entity.setAreaIds(areaIds);
					}
					productFreightRegionDAO.createProductFreightRegion(entity);
				}
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void deleteProductFreight(ProductFreightDTO productFreightDTO) throws BusinessException {
		try {
			ProductFreightEntity freightEntity = productFreightDAO
					.getFreightByUuid(productFreightDTO.getProductFreightUuid());

			List<ProductFreightRegionEntity> regionEntityList = productFreightRegionDAO
					.getRegionsByFreight(freightEntity);
			for (Iterator<ProductFreightRegionEntity> iterator = regionEntityList.iterator(); iterator.hasNext();) {
				productFreightRegionDAO.deleteProductFreightRegion(iterator.next());
			}

			productFreightDAO.deleteProductFreight(freightEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void defaultProductFreight(ProductFreightDTO productFreightDTO) throws BusinessException {
		try {
			ProductFreightEntity freightEntity = productFreightDAO
					.getFreightByUuid(productFreightDTO.getProductFreightUuid());
			List<ProductFreightEntity> freightEntityList = productFreightDAO.getFreightsByMerchant(freightEntity.getMerchantEntity());
			for (ProductFreightEntity productFreightEntity : freightEntityList) {
				//将其他运费模板设置为非默认
				if(!productFreightEntity.getProductFreightUuid().equals(freightEntity.getProductFreightUuid())){
					productFreightEntity.setDefault(false);
					productFreightDAO.updateProductFreight(productFreightEntity);
				}
			}
			freightEntity.setDefault(true);
			productFreightDAO.updateProductFreight(freightEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void cancelDefaultProductFreight(ProductFreightDTO productFreightDTO) throws BusinessException {
		try {
			ProductFreightEntity freightEntity = productFreightDAO
					.getFreightByUuid(productFreightDTO.getProductFreightUuid());
			freightEntity.setDefault(false);
			productFreightDAO.updateProductFreight(freightEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void enableProductFreight(ProductFreightDTO productFreightDTO) throws BusinessException {
		try {
			ProductFreightEntity freightEntity = productFreightDAO
					.getFreightByUuid(productFreightDTO.getProductFreightUuid());
			freightEntity.setActive(true);
			productFreightDAO.updateProductFreight(freightEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void disableProductFreight(ProductFreightDTO productFreightDTO) throws BusinessException {
		try {
			ProductFreightEntity freightEntity = productFreightDAO
					.getFreightByUuid(productFreightDTO.getProductFreightUuid());
			freightEntity.setActive(false);
			productFreightDAO.updateProductFreight(freightEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public List<ProductFreightDTO> getProductFreights() throws BusinessException {
		List<ProductFreightDTO> freightDTOList = new ArrayList<ProductFreightDTO>();
		try {
			List<ProductFreightEntity> freightEntityList = productFreightDAO.getFreights();
			for (Iterator<ProductFreightEntity> iterator = freightEntityList.iterator(); iterator.hasNext();) {
				ProductFreightEntity freightEntity = iterator.next();
				ProductFreightDTO freightDTO = new ProductFreightDTO();
				freightEntity2DTO(freightEntity, freightDTO);
				freightDTOList.add(freightDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return freightDTOList;
	}
	
	@Override
	public List<ProductFreightDTO> getProductFreightsByMerchant(MerchantDTO merchantDTO) throws BusinessException {
		List<ProductFreightDTO> freightDTOList = new ArrayList<ProductFreightDTO>();
		try {
			MerchantEntity merchantEntity = merchantDAO.getMerchantByUuid(merchantDTO.getMerchantUuid());
			List<ProductFreightEntity> freightEntityList = productFreightDAO.getFreightsByMerchant(merchantEntity);
			for (Iterator<ProductFreightEntity> iterator = freightEntityList.iterator(); iterator.hasNext();) {
				ProductFreightEntity freightEntity = iterator.next();
				ProductFreightDTO freightDTO = new ProductFreightDTO();
				freightEntity2DTO(freightEntity, freightDTO);
				freightDTOList.add(freightDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return freightDTOList;
	}

	@Override
	public List<ProductFreightDTO> searchProductFreight(MerchantSearchDTO merchantSearchDTO) throws BusinessException {
		List<ProductFreightDTO> freightDTOList = new ArrayList<ProductFreightDTO>();
		try {
			List<ProductFreightEntity> freightEntityList = productFreightDAO.searchFreight(merchantSearchDTO);
			for (Iterator<ProductFreightEntity> iterator = freightEntityList.iterator(); iterator.hasNext();) {
				ProductFreightEntity freightEntity = iterator.next();
				ProductFreightDTO freightDTO = new ProductFreightDTO();
				freightEntity2DTO(freightEntity, freightDTO);
				freightDTOList.add(freightDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return freightDTOList;
	}

	@Override
	public int searchProductFreightTotal(MerchantSearchDTO merchantSearchDTO) throws BusinessException {
		List<ProductFreightDTO> freightDTOList = new ArrayList<ProductFreightDTO>();
		try {
			return productFreightDAO.searchFreightTotal(merchantSearchDTO);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public ProductFreightDTO getProductFreightByUuid(String uuid) throws BusinessException {
		try {
			ProductFreightEntity freightEntity = productFreightDAO.getFreightByUuid(uuid);
			ProductFreightDTO freightDTO = new ProductFreightDTO();
			freightEntity2DTO(freightEntity, freightDTO);
			return freightDTO;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}
	
	private void deliveryEntity2DTO(ProductDeliveryEntity deliveryEntity, ProductDeliveryDTO deliveryDTO) {
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

	private void freightEntity2DTO(ProductFreightEntity freightEntity, ProductFreightDTO freightDTO) {
		freightDTO.setActive(freightEntity.isActive());
		freightDTO.setDefault(freightEntity.isDefault());
		freightDTO.setName(freightEntity.getName());
		freightDTO.setChargeType(freightEntity.getChargeType());
		freightDTO.setCourierName(freightEntity.getCourierName());
		freightDTO.setShippingFree(freightEntity.isShippingFree());
		freightDTO.setShippingFreeConditionAmount(freightEntity.getShippingFreeConditionAmount());
		freightDTO.setDefaultFirstPrice(freightEntity.getDefaultFirstPrice());
		freightDTO.setDefaultFirstUnit(freightEntity.getDefaultFirstUnit());
		freightDTO.setDefaultFirstWeight(freightEntity.getDefaultFirstWeight());
		freightDTO.setDefaultNextPrice(freightEntity.getDefaultNextPrice());
		freightDTO.setDefaultNextUnit(freightEntity.getDefaultNextUnit());
		freightDTO.setDefaultNextWeight(freightEntity.getDefaultNextWeight());
		freightDTO.setProductFreightUuid(freightEntity.getProductFreightUuid());

		MerchantEntity merchantEntity = freightEntity.getMerchantEntity();
		if (merchantEntity != null) {
			MerchantDTO merchantDTO = new MerchantDTO();
			merchantDTO.setMerchantUuid(merchantEntity.getMerchantUuid());
			merchantDTO.setMerchantName(merchantEntity.getMerchantName());
			merchantDTO.setMobileNo(merchantEntity.getMobileNo());
			merchantDTO.setContactName(merchantEntity.getContactName());
			freightDTO.setMerchantDTO(merchantDTO);
		}
		
		List<ProductFreightRegionDTO> regionDTOList = new ArrayList<ProductFreightRegionDTO>();
		List<ProductFreightRegionEntity> regionEntityList = freightEntity.getFreghtRegionList();
		if (regionEntityList != null) {
			for (Iterator<ProductFreightRegionEntity> rIter = regionEntityList.iterator(); rIter.hasNext();) {
				ProductFreightRegionDTO dto = new ProductFreightRegionDTO();
				ProductFreightRegionEntity entity = rIter.next();
				dto.setFirstPrice(entity.getFirstPrice());
				dto.setFirstUnit(entity.getFirstUnit());
				dto.setFirstWeight(entity.getFirstWeight());
				dto.setNextUnit(entity.getNextUnit());
				dto.setNextWeight(entity.getNextWeight());
				dto.setNextPrice(entity.getNextPrice());
				String areaIds = entity.getAreaIds();
				if (!StringUtils.isEmpty(areaIds)) {
					List<AreaDTO> areaList = new ArrayList<AreaDTO>();
					String[] areaArray = areaIds.split(",");
					try {
						for (String areaId : areaArray) {
							AreaEntity areaEntity = areaDAO.getAreaById(areaId);
							AreaDTO areaDTO = new AreaDTO();
							areaDTO.setAreaId(areaEntity.getAreaId());
							areaDTO.setDepth(areaEntity.getDepth());
							areaDTO.setName(areaEntity.getName());
							areaDTO.setPostalCode(areaEntity.getPostalCode());
							areaDTO.setSort(areaEntity.getSort());
							areaList.add(areaDTO);
						}
					} catch (DAOException e) {
						e.printStackTrace();
					}
					dto.setAreaList(areaList);
				}
				regionDTOList.add(dto);
			}
		}
		freightDTO.setFreghtRegionList(regionDTOList);
	}

	@Override
	public List<ProductDTO> getProductBySupplier(SupplierDTO supplierDTO) throws BusinessException {
		List<ProductDTO> productDTOList = new ArrayList<ProductDTO>();
		try {
			SupplierEntity supplierEntity = supplierDAO.getSupplierByUuid(supplierDTO.getSupplierUuid());
			List<ProductEntity> productEntityList = supplierProductDAO.getProductsBySupplier(supplierEntity);
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
	public List<ProductDTO> getProductByCate(ProductCateDTO cateDTO) throws BusinessException {
		List<ProductDTO> productDTOList = new ArrayList<ProductDTO>();
		try {
			ProductCateEntity cateEntity = productCateDAO.getProductCateByUuid(cateDTO.getProductCateUuid());
			List<ProductEntity> productEntityList = productDAO.getProductByCate(cateEntity);
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
	public List<ProductDTO> getProductByType(String productType) throws BusinessException {
		List<ProductDTO> productDTOList = new ArrayList<ProductDTO>();
		try {
			List<ProductEntity> productEntityList = productDAO.getProductByType(productType);
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
	public ProductDTO getProductByUuid(String uuid) throws BusinessException {
		try {
			ProductEntity productEntity = productDAO.getProductByUuid(uuid);
			ProductDTO productDTO = new ProductDTO();
			productDetailEntity2DTO(productEntity, productDTO);
			return productDTO;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public void addProductComment(ProductCommentDTO productCommentDTO) throws BusinessException {
		try {
			ProductCommentEntity commentEntity = new ProductCommentEntity();
			commentEntity.setCommentContent(productCommentDTO.getCommentContent());
			commentEntity.setCommentRank(productCommentDTO.getCommentRank());
			commentEntity.setShow(true);
			commentEntity.setEvaluateTime(new Date());
			OrderEntity orderEntity = orderDAO.getOrderByUuid(productCommentDTO.getOrderUuid());
			ProductEntity productEntity = productDAO.getProductByUuid(productCommentDTO.getProductUuid());
			UserEntity userEntity = orderEntity.getUserEntity();
			commentEntity.setProductEntity(productEntity);
			commentEntity.setOrderEntity(orderEntity);
			commentEntity.setUserEntity(userEntity);
			productCommentDAO.addProductComment(commentEntity);

			List<String> urlList = productCommentDTO.getImageUrlList();
			if (urlList != null) {
				for (Iterator<String> iterator = urlList.iterator(); iterator.hasNext();) {
					ProductCommentImageEntity imageEntity = new ProductCommentImageEntity();
					imageEntity.setProductCommentEntity(commentEntity);
//					imageEntity.setProductEntity(productEntity);
					imageEntity.setImageUrl(iterator.next());
					productCommentImageDAO.addProductCommentImage(imageEntity);
				}
			}
			orderEntity.setOrderStatus(OrderConstants.ORDER_STATUS_EVALUATED);
			orderDAO.updateOrder(orderEntity);

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void updateProductComment(ProductCommentDTO productCommentDTO) throws BusinessException {
		try {
			ProductCommentEntity commentEntity = productCommentDAO
					.getProductCommentByUuid(productCommentDTO.getProductCommentUuid());
			commentEntity.setCommentContent(productCommentDTO.getCommentContent());
			commentEntity.setCommentRank(productCommentDTO.getCommentRank());
			commentEntity.setShow(true);
			commentEntity.setEvaluateTime(new Date());
//			UserDTO userDTO = productCommentDTO.getUserDTO();
//			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
//			commentEntity.setUserEntity(userEntity);
			productCommentDAO.updateProductComment(commentEntity);

			List<ProductCommentImageEntity> imageEntityList = productCommentImageDAO
					.getProductCommentImagesByProductComment(commentEntity);
			for (Iterator<ProductCommentImageEntity> iterator = imageEntityList.iterator(); iterator.hasNext();) {
				productCommentImageDAO.deleteProductCommentImage(iterator.next());
			}
			List<String> urlList = productCommentDTO.getImageUrlList();
			for (Iterator<String> iterator = urlList.iterator(); iterator.hasNext();) {
				ProductCommentImageEntity imageEntity = new ProductCommentImageEntity();
				imageEntity.setProductCommentEntity(commentEntity);
				imageEntity.setImageUrl(iterator.next());
				productCommentImageDAO.addProductCommentImage(imageEntity);
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void setProductCommentShowStatus(ProductCommentDTO commentDTO, boolean showStatus) throws BusinessException {
		try {
			ProductCommentEntity commentEntity = productCommentDAO
					.getProductCommentByUuid(commentDTO.getProductCommentUuid());
			commentEntity.setShow(showStatus);
			productCommentDAO.updateProductComment(commentEntity);

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void deleteProductComment(ProductCommentDTO productCommentDTO) throws BusinessException {
		try {
			ProductCommentEntity commentEntity = productCommentDAO
					.getProductCommentByUuid(productCommentDTO.getProductCommentUuid());
			List<ProductCommentImageEntity> imageEntityList = productCommentImageDAO
					.getProductCommentImagesByProductComment(commentEntity);
			for (Iterator<ProductCommentImageEntity> iterator = imageEntityList.iterator(); iterator.hasNext();) {
				productCommentImageDAO.deleteProductCommentImage(iterator.next());
			}

			productCommentDAO.deleteProductComment(commentEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void replayProductComment(ProductCommentDTO productCommentDTO) throws BusinessException {
		try {
			ProductCommentEntity commentEntity = productCommentDAO
					.getProductCommentByUuid(productCommentDTO.getProductCommentUuid());
			commentEntity.setReplayContent(productCommentDTO.getReplayContent());
			commentEntity.setReplayTime(new Date());
			productCommentDAO.updateProductComment(commentEntity);

			OrderEntity orderEntity = commentEntity.getOrderEntity();
//			String productName = orderEntity.getProductName();
			UserEntity userEntity = commentEntity.getUserEntity();
			UserDTO userDTO = new UserDTO();
			userDTO.setUserUuid(userEntity.getUserUuid());

//			noteService.sendNotification(userDTO, "comment", new String[]{productName});
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void updateProductCommentReply(ProductCommentDTO productCommentDTO) throws BusinessException {
		try {
			ProductCommentEntity commentEntity = productCommentDAO
					.getProductCommentByUuid(productCommentDTO.getProductCommentUuid());
			commentEntity.setReplayContent(productCommentDTO.getReplayContent());
			commentEntity.setReplayTime(new Date());
			productCommentDAO.updateProductComment(commentEntity);

			OrderEntity orderEntity = commentEntity.getOrderEntity();
//			String productName = orderEntity.getProductName();
			UserEntity userEntity = orderEntity.getUserEntity();
			UserDTO userDTO = new UserDTO();
			userDTO.setUserUuid(userEntity.getUserUuid());

//			noteService.sendNotification(userDTO, "comment", new String[]{productName});
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public List<ProductCommentDTO> getProductComments(ProductDTO productDTO) throws BusinessException {
		List<ProductCommentDTO> commentDTOList = new ArrayList<ProductCommentDTO>();
		try {
			ProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
			List<ProductCommentEntity> commentEntityList = productCommentDAO.getProductCommentByProduct(productEntity);

			for (Iterator<ProductCommentEntity> iter = commentEntityList.iterator(); iter.hasNext();) {
				ProductCommentEntity commentEntity = iter.next();
				ProductCommentDTO commentDTO = new ProductCommentDTO();
				commentDTO.setProductCommentUuid(commentEntity.getProductCommentUuid());
				commentDTO.setCommentContent(commentEntity.getCommentContent());
				commentDTO.setCommentRank(commentEntity.getCommentRank());
				commentDTO.setShow(commentEntity.isShow());
				commentDTO.setReplayContent(commentEntity.getReplayContent());
				commentDTO.setEvaluateTime(commentEntity.getEvaluateTime());
				commentDTO.setReplayTime(commentEntity.getReplayTime());
				UserEntity userEntity = commentEntity.getUserEntity();
				UserDTO userDTO = new UserDTO();
				userDTO.setUserUuid(userEntity.getUserUuid());
				userDTO.setName(userEntity.getName());
				userDTO.setPersonalPhone(userEntity.getPersonalPhone());
				commentDTO.setUserDTO(userDTO);

				OrderEntity orderEntity = commentEntity.getOrderEntity();
				OrderDTO orderDTO = new OrderDTO();
				orderDTO.setOrderNo(orderEntity.getOrderNo());
//				orderDTO.setProductName(orderEntity.getProductName());
//				orderDTO.setProductImageUrl(orderEntity.getProductImageUrl());
//				orderDTO.setSupplierName(orderEntity.getSupplierName());
//				orderDTO.setProductSkuDesc(orderEntity.getProductSkuDesc());
				commentDTO.setOrderDTO(orderDTO);

				List<ProductCommentImageEntity> imageEntityList = commentEntity.getImageList();
				if (imageEntityList != null) {
					List<String> imageUrlList = new ArrayList<String>();
					for (Iterator<ProductCommentImageEntity> iterator = imageEntityList.iterator(); iterator
							.hasNext();) {
						ProductCommentImageEntity imageEntity = iterator.next();
						imageUrlList.add(imageEntity.getImageUrl());
					}
					commentDTO.setImageUrlList(imageUrlList);
				}
				commentDTOList.add(commentDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return commentDTOList;
	}

	@Override
	public ProductCommentDTO getProductCommentByUuid(String uuid) throws BusinessException {
		try {
			ProductCommentEntity commentEntity = productCommentDAO.getProductCommentByUuid(uuid);
			ProductCommentDTO commentDTO = new ProductCommentDTO();
			commentDTO.setProductCommentUuid(commentEntity.getProductCommentUuid());
			commentDTO.setCommentContent(commentEntity.getCommentContent());
			commentDTO.setCommentRank(commentEntity.getCommentRank());
			commentDTO.setShow(commentEntity.isShow());
			commentDTO.setReplayContent(commentEntity.getReplayContent());
			commentDTO.setEvaluateTime(commentEntity.getEvaluateTime());
			commentDTO.setReplayTime(commentEntity.getReplayTime());
			UserEntity userEntity = commentEntity.getUserEntity();
			UserDTO userDTO = new UserDTO();
			userDTO.setUserUuid(userEntity.getUserUuid());
			userDTO.setName(userEntity.getName());
			userDTO.setPersonalPhone(userEntity.getPersonalPhone());
			commentDTO.setUserDTO(userDTO);

			OrderEntity orderEntity = commentEntity.getOrderEntity();
			OrderDTO orderDTO = new OrderDTO();
			orderDTO.setOrderNo(orderEntity.getOrderNo());
//			orderDTO.setProductName(orderEntity.getProductName());
//			orderDTO.setProductImageUrl(orderEntity.getProductImageUrl());
//			orderDTO.setSupplierName(orderEntity.getSupplierName());
//			orderDTO.setProductSkuDesc(orderEntity.getProductSkuDesc());
			commentDTO.setOrderDTO(orderDTO);

			List<ProductCommentImageEntity> imageEntityList = commentEntity.getImageList();
			List<String> imageUrlList = new ArrayList<String>();
			for (Iterator<ProductCommentImageEntity> iterator = imageEntityList.iterator(); iterator.hasNext();) {
				ProductCommentImageEntity imageEntity = iterator.next();
				imageUrlList.add(imageEntity.getImageUrl());
			}
			commentDTO.setImageUrlList(imageUrlList);
			return commentDTO;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public List<ProductCommentDTO> searchProductComment(ProductCommentSearchDTO searchDTO, boolean isShowOnly)
			throws BusinessException {
		List<ProductCommentDTO> commentDTOList = new ArrayList<ProductCommentDTO>();
		try {
			List<ProductCommentEntity> commentEntityList = productCommentDAO.searchProductComment(searchDTO,
					isShowOnly);

			for (Iterator<ProductCommentEntity> iter = commentEntityList.iterator(); iter.hasNext();) {
				ProductCommentEntity commentEntity = iter.next();
				ProductCommentDTO commentDTO = new ProductCommentDTO();
				commentDTO.setProductCommentUuid(commentEntity.getProductCommentUuid());
				commentDTO.setCommentContent(commentEntity.getCommentContent());
				commentDTO.setCommentRank(commentEntity.getCommentRank());
				commentDTO.setShow(commentEntity.isShow());
				commentDTO.setReplayContent(commentEntity.getReplayContent());
				commentDTO.setEvaluateTime(commentEntity.getEvaluateTime());
				commentDTO.setReplayTime(commentEntity.getReplayTime());
				UserEntity userEntity = commentEntity.getUserEntity();

				UserDTO userDTO = new UserDTO();
				userDTO.setUserUuid(userEntity.getUserUuid());
				userDTO.setName(userEntity.getName());
				userDTO.setPersonalPhone(userEntity.getPersonalPhone());
				userDTO.setPhotoUrl(userEntity.getPhotoUrl());
				commentDTO.setUserDTO(userDTO);

				OrderEntity orderEntity = commentEntity.getOrderEntity();
				OrderDTO orderDTO = new OrderDTO();
				orderDTO.setOrderNo(orderEntity.getOrderNo());
//				orderDTO.setProductName(orderEntity.getProductName());
//				orderDTO.setProductImageUrl(orderEntity.getProductImageUrl());
//				orderDTO.setSupplierName(orderEntity.getSupplierName());
//				orderDTO.setProductSkuDesc(orderEntity.getProductSkuDesc());
				commentDTO.setOrderDTO(orderDTO);

				ProductEntity productEntity = commentEntity.getProductEntity();
				if (productEntity != null) {
					ProductDTO productDTO = new ProductDTO();
					productDTO.setProductUuid(productEntity.getProductUuid());
					productDTO.setProductName(productEntity.getProductName());
					FileEntity fileEntity = productEntity.getProductMainImage();
					if (fileEntity != null) {
						FileDTO fileDTO = new FileDTO();
						fileDTO.setUrl(fileEntity.getUrl());
						productDTO.setProductMainImage(fileDTO);
					}
					commentDTO.setProductDTO(productDTO);
				}

				List<ProductCommentImageEntity> imageEntityList = commentEntity.getImageList();
				if (imageEntityList != null) {
					List<String> imageUrlList = new ArrayList<String>();
					for (Iterator<ProductCommentImageEntity> iterator = imageEntityList.iterator(); iterator
							.hasNext();) {
						ProductCommentImageEntity imageEntity = iterator.next();
						imageUrlList.add(imageEntity.getImageUrl());
					}
					commentDTO.setImageUrlList(imageUrlList);
				}
				commentDTOList.add(commentDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return commentDTOList;
	}

	@Override
	public int searchProductCommentTotal(ProductCommentSearchDTO searchDTO, boolean isShowOnly)
			throws BusinessException {
		try {
			return productCommentDAO.searchProductCommentTotal(searchDTO, isShowOnly);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public int getProductCommentTotal(ProductDTO productDTO) throws BusinessException {
		try {
			return productCommentDAO.getProductCommentTotal(productDTO);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public void addProductPromoteMoneyOff(List<ProductDTO> productDTOList, PromoteMoneyOffDTO moneyOffDTO)
			throws BusinessException {
		try {
			PromoteMoneyOffEntity moneyOffEntity = promoteMoneyOffDAO
					.getPromoteMoneyOffByUuid(moneyOffDTO.getPromoteMoneyOffUuid());
			for (Iterator<ProductDTO> iter = productDTOList.iterator(); iter.hasNext();) {
				ProductDTO productDTO = iter.next();
				ProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
				// 检查是否已添加过其他满减送活动, 如果已添加则不能再次添加
				if (productPromoteDAO.getPromoteByType(productEntity, PromoteConstants.PROMOTE_TYPE_MONEYOFF)
						.size() > 0)
					continue;
				ProductPromoteEntity promoteEntity = new ProductPromoteEntity();
				promoteEntity.setSupplierEntity(productEntity.getSupplierProductEntity().getSupplierEntity());
				promoteEntity.setProductEntity(productEntity);
				promoteEntity.setPromoteType(PromoteConstants.PROMOTE_TYPE_MONEYOFF);
				promoteEntity.setPromoteUuid(moneyOffEntity.getPromoteMoneyOffUuid());
				productPromoteDAO.createProductPromote(promoteEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public void addProductPromoteFreightOff(List<ProductDTO> productDTOList, PromoteFreightOffDTO freightOffDTO)
			throws BusinessException {
		try {
			PromoteFreightOffEntity freightOffEntity = promoteFreightOffDAO
					.getPromoteFreightOffByUuid(freightOffDTO.getPromoteFreightOffUuid());
			for (Iterator<ProductDTO> iter = productDTOList.iterator(); iter.hasNext();) {
				ProductDTO productDTO = iter.next();
				ProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
				// 检查是否已添加过其他满减送活动, 如果已添加则不能再次添加
				if (productPromoteDAO.getPromoteByType(productEntity, PromoteConstants.PROMOTE_TYPE_FREIGHTOFF)
						.size() > 0)
					continue;
				ProductPromoteEntity promoteEntity = new ProductPromoteEntity();
				promoteEntity.setSupplierEntity(productEntity.getSupplierProductEntity().getSupplierEntity());
				promoteEntity.setProductEntity(productEntity);
				promoteEntity.setPromoteType(PromoteConstants.PROMOTE_TYPE_FREIGHTOFF);
				promoteEntity.setPromoteUuid(freightOffEntity.getPromoteFreightOffUuid());
				productPromoteDAO.createProductPromote(promoteEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public void addProductPromoteDiscount(List<ProductDTO> productDTOList, PromoteDiscountDTO discountDTO)
			throws BusinessException {
		try {
			PromoteDiscountEntity discountEntity = promoteDiscountDAO
					.getPromoteDiscountByUuid(discountDTO.getPromoteDiscountUuid());
			for (Iterator<ProductDTO> iter = productDTOList.iterator(); iter.hasNext();) {
				ProductDTO productDTO = iter.next();
				ProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
				// 检查是否已添加过其他限时折扣活动, 如果已添加则不能再次添加
				if (productPromoteDAO.getPromoteByType(productEntity, PromoteConstants.PROMOTE_TYPE_DISCOUNT)
						.size() > 0)
					continue;
				ProductPromoteEntity promoteEntity = new ProductPromoteEntity();
				promoteEntity.setSupplierEntity(productEntity.getSupplierProductEntity().getSupplierEntity());
				promoteEntity.setProductEntity(productEntity);
				promoteEntity.setPromoteType(PromoteConstants.PROMOTE_TYPE_DISCOUNT);
				promoteEntity.setPromoteUuid(discountEntity.getPromoteDiscountUuid());
				productPromoteDAO.createProductPromote(promoteEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public void deleteProductPromoteMoneyOff(List<ProductDTO> productDTOList, PromoteMoneyOffDTO moneyOffDTO)
			throws BusinessException {
		try {
			PromoteMoneyOffEntity moneyOffEntity = promoteMoneyOffDAO
					.getPromoteMoneyOffByUuid(moneyOffDTO.getPromoteMoneyOffUuid());
			for (Iterator<ProductDTO> iter = productDTOList.iterator(); iter.hasNext();) {
				ProductDTO productDTO = iter.next();
				ProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
				ProductPromoteEntity promoteEntity = productPromoteDAO.getPromoteByTypeAndUuid(productEntity,
						PromoteConstants.PROMOTE_TYPE_MONEYOFF, moneyOffEntity.getPromoteMoneyOffUuid());
				productPromoteDAO.deleteProductPromote(promoteEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public void deleteProductPromoteFreightOff(List<ProductDTO> productDTOList, PromoteFreightOffDTO freightOffDTO)
			throws BusinessException {
		try {
			PromoteFreightOffEntity freightOffEntity = promoteFreightOffDAO
					.getPromoteFreightOffByUuid(freightOffDTO.getPromoteFreightOffUuid());
			for (Iterator<ProductDTO> iter = productDTOList.iterator(); iter.hasNext();) {
				ProductDTO productDTO = iter.next();
				ProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
				ProductPromoteEntity promoteEntity = productPromoteDAO.getPromoteByTypeAndUuid(productEntity,
						PromoteConstants.PROMOTE_TYPE_FREIGHTOFF, freightOffEntity.getPromoteFreightOffUuid());
				productPromoteDAO.deleteProductPromote(promoteEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public void deleteProductPromoteDiscount(List<ProductDTO> productDTOList, PromoteDiscountDTO discountDTO)
			throws BusinessException {
		try {
			PromoteDiscountEntity discountEntity = promoteDiscountDAO
					.getPromoteDiscountByUuid(discountDTO.getPromoteDiscountUuid());
			for (Iterator<ProductDTO> iter = productDTOList.iterator(); iter.hasNext();) {
				ProductDTO productDTO = iter.next();
				ProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
				ProductPromoteEntity promoteEntity = productPromoteDAO.getPromoteByTypeAndUuid(productEntity,
						PromoteConstants.PROMOTE_TYPE_DISCOUNT, discountEntity.getPromoteDiscountUuid());
				productPromoteDAO.deleteProductPromote(promoteEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}
}
