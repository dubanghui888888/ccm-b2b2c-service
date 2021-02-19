package com.mb.ext.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mb.ext.core.constant.OrderConstants;
import com.mb.ext.core.constant.PointConstants;
import com.mb.ext.core.constant.ProductConstants;
import com.mb.ext.core.dao.AreaDAO;
import com.mb.ext.core.dao.FileDAO;
import com.mb.ext.core.dao.MerchantDAO;
import com.mb.ext.core.dao.SupplierDAO;
import com.mb.ext.core.dao.UserBalanceDAO;
import com.mb.ext.core.dao.UserDAO;
import com.mb.ext.core.dao.UserPointStatementDAO;
import com.mb.ext.core.dao.UserSignDAO;
import com.mb.ext.core.dao.coupon.CouponDAO;
import com.mb.ext.core.dao.order.OrderDAO;
import com.mb.ext.core.dao.point.PointProductAttrValueDAO;
import com.mb.ext.core.dao.point.PointProductCateAttrDAO;
import com.mb.ext.core.dao.point.PointProductCateDAO;
import com.mb.ext.core.dao.point.PointProductCommentDAO;
import com.mb.ext.core.dao.point.PointProductCommentImageDAO;
import com.mb.ext.core.dao.point.PointProductDAO;
import com.mb.ext.core.dao.point.PointProductDescImageDAO;
import com.mb.ext.core.dao.point.PointProductImageDAO;
import com.mb.ext.core.dao.point.PointProductSkuAttrDAO;
import com.mb.ext.core.dao.point.PointProductSkuAttrValueDAO;
import com.mb.ext.core.dao.point.PointProductSkuDAO;
import com.mb.ext.core.dao.point.PointProductVideoDAO;
import com.mb.ext.core.dao.point.SignSettingDAO;
import com.mb.ext.core.dao.product.ProductBrandDAO;
import com.mb.ext.core.dao.product.ProductFreightDAO;
import com.mb.ext.core.dao.product.ProductFreightRegionDAO;
import com.mb.ext.core.entity.AreaEntity;
import com.mb.ext.core.entity.FileEntity;
import com.mb.ext.core.entity.UserBalanceEntity;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.UserPointStatementEntity;
import com.mb.ext.core.entity.UserSignEntity;
import com.mb.ext.core.entity.coupon.CouponEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.order.OrderEntity;
import com.mb.ext.core.entity.point.PointProductAttrValueEntity;
import com.mb.ext.core.entity.point.PointProductCateAttrEntity;
import com.mb.ext.core.entity.point.PointProductCateEntity;
import com.mb.ext.core.entity.point.PointProductCommentEntity;
import com.mb.ext.core.entity.point.PointProductCommentImageEntity;
import com.mb.ext.core.entity.point.PointProductDescImageEntity;
import com.mb.ext.core.entity.point.PointProductEntity;
import com.mb.ext.core.entity.point.PointProductImageEntity;
import com.mb.ext.core.entity.point.PointProductSkuAttrEntity;
import com.mb.ext.core.entity.point.PointProductSkuAttrValueEntity;
import com.mb.ext.core.entity.point.PointProductSkuEntity;
import com.mb.ext.core.entity.point.PointProductVideoEntity;
import com.mb.ext.core.entity.point.SignSettingEntity;
import com.mb.ext.core.entity.product.ProductBrandEntity;
import com.mb.ext.core.entity.product.ProductFreightEntity;
import com.mb.ext.core.entity.product.ProductFreightRegionEntity;
import com.mb.ext.core.entity.supplier.SupplierEntity;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.PointService;
import com.mb.ext.core.service.ProductService;
import com.mb.ext.core.service.spec.AreaDTO;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.FileDTO;
import com.mb.ext.core.service.spec.ProductCommentSearchDTO;
import com.mb.ext.core.service.spec.ProductSearchDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.UserPointStatementDTO;
import com.mb.ext.core.service.spec.UserSignDTO;
import com.mb.ext.core.service.spec.UserStatementSearchDTO;
import com.mb.ext.core.service.spec.coupon.CouponDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.order.OrderDTO;
import com.mb.ext.core.service.spec.point.PointProductAttrValueDTO;
import com.mb.ext.core.service.spec.point.PointProductCateAttrDTO;
import com.mb.ext.core.service.spec.point.PointProductCateDTO;
import com.mb.ext.core.service.spec.point.PointProductCommentDTO;
import com.mb.ext.core.service.spec.point.PointProductDTO;
import com.mb.ext.core.service.spec.point.PointProductSkuAttrDTO;
import com.mb.ext.core.service.spec.point.PointProductSkuAttrValueDTO;
import com.mb.ext.core.service.spec.point.PointProductSkuDTO;
import com.mb.ext.core.service.spec.product.ProductBrandDTO;
import com.mb.ext.core.service.spec.product.ProductFreightDTO;
import com.mb.ext.core.service.spec.product.ProductFreightRegionDTO;
import com.mb.ext.core.service.spec.supplier.SupplierDTO;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.service.AbstractService;
import com.mb.framework.util.log.LogHelper;

@Service
@Qualifier("PointService")
public class PointServiceImpl extends AbstractService implements PointService<BodyDTO> {


	@Autowired
	@Qualifier("fileDAO")
	private FileDAO fileDAO;
	
	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;

	@Autowired
	@Qualifier("userBalanceDAO")
	private UserBalanceDAO userBalanceDAO;
	
	@Autowired
	@Qualifier("userSignDAO")
	private UserSignDAO userSignDAO;
	
	@Autowired
	@Qualifier("orderDAO")
	private OrderDAO orderDAO;
	
	@Autowired
	@Qualifier("signSettingDAO")
	private SignSettingDAO signSettingDAO;
	
	@Autowired
	@Qualifier("userPointStatementDAO")
	private UserPointStatementDAO userPointStatementDAO;
	
	@Autowired
	@Qualifier("pointProductDAO")
	private PointProductDAO pointProductDAO;
	
	@Autowired
	@Qualifier("pointProductSkuDAO")
	private PointProductSkuDAO pointProductSkuDAO;
	
	@Autowired
	@Qualifier("pointProductDAO")
	private PointProductDAO productDAO;
	
	@Autowired
	@Qualifier("pointProductCateDAO")
	private PointProductCateDAO productCateDAO;
	
	@Autowired
	@Qualifier("pointProductCateAttrDAO")
	private PointProductCateAttrDAO productCateAttrDAO;
	
	@Autowired
	@Qualifier("productBrandDAO")
	private ProductBrandDAO productBrandDAO;
	
	@Autowired
	@Qualifier("pointProductImageDAO")
	private PointProductImageDAO productImageDAO;
	
	@Autowired
	@Qualifier("productFreightDAO")
	private ProductFreightDAO productFreightDAO;
	
	@Autowired
	@Qualifier("productFreightRegionDAO")
	private ProductFreightRegionDAO productFreightRegionDAO;
	
	@Autowired
	@Qualifier("pointProductDescImageDAO")
	private PointProductDescImageDAO productDescImageDAO;
	
	@Autowired
	@Qualifier("pointProductVideoDAO")
	private PointProductVideoDAO productVideoDAO;
	
	@Autowired
	@Qualifier("pointProductAttrValueDAO")
	private PointProductAttrValueDAO productAttrValueDAO;

	@Autowired
	@Qualifier("pointProductSkuAttrDAO")
	private PointProductSkuAttrDAO productSkuAttrDAO;

	@Autowired
	@Qualifier("pointProductSkuAttrValueDAO")
	private PointProductSkuAttrValueDAO productSkuAttrValueDAO;
	
	@Autowired
	@Qualifier("pointProductSkuDAO")
	private PointProductSkuDAO productSkuDAO;
	
	@Autowired
	@Qualifier("couponDAO")
	private CouponDAO couponDAO;
	
	@Autowired
	@Qualifier("areaDAO")
	private AreaDAO areaDAO;
	
	@Autowired
	@Qualifier("supplierDAO")
	private SupplierDAO supplierDAO;
	
	@Autowired
	@Qualifier("pointProductCommentDAO")
	private PointProductCommentDAO productCommentDAO;

	@Autowired
	@Qualifier("pointProductCommentImageDAO")
	private PointProductCommentImageDAO productCommentImageDAO;
	
	@Autowired
	@Qualifier("merchantDAO")
	private MerchantDAO merchantDAO;
	
	@Autowired
	@Qualifier("ProductService")
	private ProductService ProductService;

	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	@Override
	public List<UserPointStatementDTO> searchUserPointStatement(UserStatementSearchDTO searchDTO)
			throws BusinessException {
		List<UserPointStatementDTO> userStatementDTOList = new ArrayList<UserPointStatementDTO>();

		try {
			List<UserPointStatementEntity> userStatementEntityList = userPointStatementDAO.searchUserPointStatement(searchDTO);
			if (userStatementEntityList != null)
				for (Iterator<UserPointStatementEntity> iterator = userStatementEntityList.iterator(); iterator.hasNext();) {
					UserPointStatementEntity userStatementEntity = iterator.next();
					UserPointStatementDTO userStatementDTO = new UserPointStatementDTO();
					userStatementDTO.setPointBefore(userStatementEntity.getPointBefore());
					userStatementDTO.setCreateBy(userStatementEntity.getCreateBy());
					userStatementDTO.setPointAfter(userStatementEntity.getPointAfter());
					userStatementDTO.setPointBefore(userStatementEntity.getPointBefore());
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
	public int searchUserPointStatementTotal(UserStatementSearchDTO searchDTO) throws BusinessException {
		try {
			return userPointStatementDAO.searchUserPointStatementTotal(searchDTO);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public int signPoint(UserDTO userDTO) throws BusinessException {
		int signPoint = 0;
		try {
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			Date currDate = new Date();
			UserSignEntity userSignEntity = userSignDAO.getUserSignByDate(userEntity, currDate);
			//不能重复签到
			if(userSignEntity != null) {
				throw new BusinessException(BusinessErrorCode.POINT_SIGNED);
			}
			UserBalanceEntity balanceEntity = userEntity.getUserBalanceEntity();
			//当前积分
			int pointBefore = balanceEntity.getAvailablePoint();
			//计算签到可获得积分
			signPoint = getSignPoint(userEntity, currDate);
			//获取后积分
			int pointAfter = pointBefore + signPoint;
			balanceEntity.setAvailablePoint(pointAfter);
			userBalanceDAO.updateUserBalance(balanceEntity);
			
			//签到记录
			userSignEntity = new UserSignEntity();
			userSignEntity.setUserEntity(userEntity);
			userSignEntity.setSignTime(currDate);
			userSignEntity.setSignPoint(signPoint);
			userSignDAO.createUserSign(userSignEntity);
			
			//积分明细
			UserPointStatementEntity userStatementEntity = new UserPointStatementEntity();
			userStatementEntity.setUserEntity(userEntity);
			userStatementEntity.setTransactionTime(currDate);
			userStatementEntity.setTransactionType(PointConstants.TRAN_TYPE_SIGN);
			userStatementEntity.setTransactionDesc("签到获取" + signPoint+ "积分");
			userStatementEntity.setTransactionPoint(signPoint);
			userStatementEntity.setPointBefore(pointBefore);
			userStatementEntity.setPointAfter(pointAfter);
			userPointStatementDAO.createUserPointStatement(userStatementEntity);
			
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (BusinessException e) {
			throw e;
		}
		return signPoint;
	}
	
	int getSignPoint(UserEntity userEntity, Date signDate) throws BusinessException{
		
		int signPoint = 0;
		try {
			SignSettingEntity signSetting = signSettingDAO.getSignSetting();
			if(signSetting != null && signSetting.isEnabled()) {
				int day1Point = signSetting.getDay1Point();
				int day2Point = signSetting.getDay2Point();
				int day3Point = signSetting.getDay3Point();
				int day4Point = signSetting.getDay4Point();
				int day5Point = signSetting.getDay5Point();
				int day6Point = signSetting.getDay6Point();
				int day7Point = signSetting.getDay7Point();
			
				//连续签到第x天
				int x = 1;
				for(int i = 1; i<=6; i++) {
					UserSignEntity userSignEntity = userSignDAO.getUserSignByDate(userEntity, DateUtils.addDays(signDate, 0-i));
					if(userSignEntity != null) {
						x ++;
					}else {
						break;
					}
				}
				switch (x) {
				case 1:{
					signPoint = day1Point;
					break;
				}
				case 2:{
					signPoint = day2Point;
					break;
				}
				case 3:{
					signPoint = day3Point;
					break;
				}
				case 4:{
					signPoint = day4Point;
					break;
				}
				case 5:{
					signPoint = day5Point;
					break;
				}
				case 6:{
					signPoint = day6Point;
					break;
				}
				case 7:{
					signPoint = day7Point;
					break;
				}
				default:
					break;
				}
			}
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR);
		}
		
		return signPoint;
	}

	@Override
	public List<UserSignDTO> getSignByUser(UserDTO userDTO) throws BusinessException {
		
		List<UserSignDTO> userSignDTOList = new ArrayList<UserSignDTO>();
		try {
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			List<UserSignEntity> userSignEntityList = userSignDAO.getSignByUser(userEntity);
			if (userSignEntityList != null)
				for (Iterator<UserSignEntity> iterator = userSignEntityList.iterator(); iterator.hasNext();) {
					UserSignEntity userSignEntity = iterator.next();
					UserSignDTO userSignDTO = new UserSignDTO();
					userSignDTO.setUserSignUuid(userSignEntity.getUserSignUuid());
					userSignDTO.setSignPoint(userSignEntity.getSignPoint());
					userSignDTO.setSignTime(userSignEntity.getSignTime());
					userSignDTOList.add(userSignDTO);
				}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return userSignDTOList;
	}

	@Override
	public boolean isUserSigned(UserDTO userDTO, Date signDate) throws BusinessException {
		boolean isUserSigned = false;
		try {
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			Date currDate = new Date();
			UserSignEntity userSignEntity = userSignDAO.getUserSignByDate(userEntity, currDate);
			if(userSignEntity != null) {
				isUserSigned = true;
			}
			
		} catch (DAOException e) {
			logger.error(e.getMessage());
		}
		return isUserSigned;
	}

	@Override
	public int getSignedDateNum(UserDTO userDTO, Date signTime) throws BusinessException {
		int x = 0;
		try {
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			UserSignEntity userSignEntity = userSignDAO.getUserSignByDate(userEntity, signTime);
			if(userSignEntity != null) {
				//连续签到第x天
				x = 1;
				for(int i = 1; i<7; i++) {
					UserSignEntity signEntity = userSignDAO.getUserSignByDate(userEntity, DateUtils.addDays(userSignEntity.getSignTime(), 0-i));
					if(signEntity != null) {
						x ++;
					}else {
						break;
					}
				}
			}
		} catch (DAOException e) {
			logger.error(e.getMessage());
		}
		return x;
	}
	
	@Override
	public void addProductCate(PointProductCateDTO productCateDTO) throws BusinessException {
		try {
			PointProductCateEntity cateEntity = new PointProductCateEntity();
			cateEntity.setCateName(productCateDTO.getCateName());
			cateEntity.setCatePicUrl(productCateDTO.getCatePicUrl());
			cateEntity.setSortNumber(productCateDTO.getSortNumber());
			cateEntity.setUpdateBy(productCateDTO.getUpdateBy());
			cateEntity.setCreateBy(productCateDTO.getCreateBy());

			PointProductCateDTO parentCateDTO = productCateDTO.getParentCateDTO();
			if (parentCateDTO != null && !StringUtils.isEmpty(parentCateDTO.getProductCateUuid())) {
				PointProductCateEntity parentCateEntity = productCateDAO
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
	public void deleteProductCate(PointProductCateDTO productCateDTO) throws BusinessException {
		try {
			PointProductCateEntity cateEntity = productCateDAO.getProductCateByUuid(productCateDTO.getProductCateUuid());
			if (cateEntity != null) {
				List<PointProductEntity> productList = productDAO.getProductByCate(cateEntity);
				if (productList.size() > 0) {
					throw new BusinessException(BusinessErrorCode.PRODUCT_CATE_DELETE);
				}
				PointProductCateEntity parentCateEntity = cateEntity.getParentCateEntity();

				// 删除类目属性
				List<PointProductCateAttrEntity> attrEntityList = cateEntity.getAttrList();
				cateEntity.setAttrList(null);
				for (Iterator<PointProductCateAttrEntity> iter = attrEntityList.iterator(); iter.hasNext();) {
					productCateAttrDAO.deleteProductCateAttr(iter.next());
				}
				// 删除类目
				productCateDAO.deleteProductCate(cateEntity);

				// 检查父类目下是否还有其他子类目, 如果没有则父类目变为叶子类目
				if (parentCateEntity != null) {
					List<PointProductCateEntity> list = productCateDAO.getChildProductCate(parentCateEntity);
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
	public void updateProductCate(PointProductCateDTO productCateDTO) throws BusinessException {
		try {
			PointProductCateEntity cateEntity = productCateDAO.getProductCateByUuid(productCateDTO.getProductCateUuid());
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
	public List<PointProductCateDTO> getRootProductCate() throws BusinessException {

		List<PointProductCateDTO> cateDTOList = new ArrayList<PointProductCateDTO>();
		try {
			List<PointProductCateEntity> cateEntityList = productCateDAO.getRootProductCate();
			if (cateEntityList != null && cateEntityList.size() > 0) {
				for (Iterator<PointProductCateEntity> iter = cateEntityList.iterator(); iter.hasNext();) {
					PointProductCateEntity cateEntity = iter.next();
					PointProductCateDTO cateDTO = new PointProductCateDTO();
					cateEntity2DTO(cateDTO, cateEntity);

					List<PointProductCateEntity> childCateEntityList = cateEntity.getChildList();
					List<PointProductCateDTO> childCateDTOList = new ArrayList<PointProductCateDTO>();
					for (PointProductCateEntity childCateEntity : childCateEntityList) {
						PointProductCateDTO childCateDTO = new PointProductCateDTO();
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
	public List<PointProductCateDTO> getHomeProductCate() throws BusinessException {

		List<PointProductCateDTO> cateDTOList = new ArrayList<PointProductCateDTO>();
		try {
			List<PointProductCateEntity> cateEntityList = productCateDAO.getHomeProductCate();
			if (cateEntityList != null && cateEntityList.size() > 0) {
				for (Iterator<PointProductCateEntity> iter = cateEntityList.iterator(); iter.hasNext();) {
					PointProductCateEntity cateEntity = iter.next();
					PointProductCateDTO cateDTO = new PointProductCateDTO();
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
	public List<PointProductCateDTO> getLeafProductCate() throws BusinessException {

		List<PointProductCateDTO> cateDTOList = new ArrayList<PointProductCateDTO>();
		try {
			List<PointProductCateEntity> cateEntityList = productCateDAO.getLeafProductCate();
			if (cateEntityList != null && cateEntityList.size() > 0) {
				for (Iterator<PointProductCateEntity> iter = cateEntityList.iterator(); iter.hasNext();) {
					PointProductCateEntity cateEntity = iter.next();
					PointProductCateDTO cateDTO = new PointProductCateDTO();
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
	public List<PointProductCateDTO> getChildProductCate(PointProductCateDTO productCateDTO) throws BusinessException {

		List<PointProductCateDTO> cateDTOList = new ArrayList<PointProductCateDTO>();
		try {
			PointProductCateEntity parentEntity = productCateDAO.getProductCateByUuid(productCateDTO.getProductCateUuid());
			if (parentEntity != null) {
				List<PointProductCateEntity> cateEntityList = productCateDAO.getChildProductCate(parentEntity);
				if (cateEntityList != null && cateEntityList.size() > 0) {
					for (Iterator<PointProductCateEntity> iter = cateEntityList.iterator(); iter.hasNext();) {
						PointProductCateEntity cateEntity = iter.next();
						PointProductCateDTO cateDTO = new PointProductCateDTO();
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
	public PointProductCateDTO getProductCateByUuid(String uuid) throws BusinessException {
		PointProductCateDTO cateDTO = null;
		try {
			PointProductCateEntity cateEntity = productCateDAO.getProductCateByUuid(uuid);
			if (cateEntity != null) {
				cateDTO = new PointProductCateDTO();
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
	public void updateProduct(PointProductDTO productDTO) throws BusinessException {
		try {

			// 基本属性
			PointProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
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
			PointProductCateDTO cateDTO = productDTO.getProductCateDTO();
			if (cateDTO != null && !StringUtils.isEmpty(cateDTO.getProductCateUuid())) {
				PointProductCateEntity cateEntity = productCateDAO.getProductCateByUuid(cateDTO.getProductCateUuid());
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

			// 运费模板
			ProductFreightDTO freightDTO = productDTO.getProductFreightDTO();
			if (freightDTO != null) {
				ProductFreightEntity freightEntity = productFreightDAO
						.getFreightByUuid(freightDTO.getProductFreightUuid());
				productEntity.setProductFreightEntity(freightEntity);
			}

			productDAO.updateProduct(productEntity);

			// 商品图片
			List<PointProductImageEntity> imageEntityList = productImageDAO.getImagesByProduct(productEntity);
			for (Iterator<PointProductImageEntity> iter = imageEntityList.iterator(); iter.hasNext();) {
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
					PointProductImageEntity productImageEntity = new PointProductImageEntity();
					productImageEntity.setProductEntity(productEntity);
					productImageEntity.setFileEntity(imageEntity);
					productImageDAO.createProductImage(productImageEntity);
				}
			}

			// 商品描述图片
			List<PointProductDescImageEntity> descImageEntityList = productDescImageDAO
					.getDescImagesByProduct(productEntity);
			for (Iterator<PointProductDescImageEntity> iter = descImageEntityList.iterator(); iter.hasNext();) {
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
					PointProductDescImageEntity productImageEntity = new PointProductDescImageEntity();
					productImageEntity.setProductEntity(productEntity);
					productImageEntity.setFileEntity(imageEntity);
					productDescImageDAO.createProductDescImage(productImageEntity);
				}
			}

			// 商品视频
			List<PointProductVideoEntity> videoEntityList = productVideoDAO.getVideosByProduct(productEntity);
			for (Iterator<PointProductVideoEntity> iter = videoEntityList.iterator(); iter.hasNext();) {
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
					PointProductVideoEntity videoEntity = new PointProductVideoEntity();
					videoEntity.setProductEntity(productEntity);
					videoEntity.setFileEntity(imageEntity);
					productVideoDAO.createProductVideo(videoEntity);
				}
			}

			// 设置商品属性
			List<PointProductAttrValueEntity> valueEntityList = productAttrValueDAO.getProductAttrByProduct(productEntity);
			for (Iterator<PointProductAttrValueEntity> iter = valueEntityList.iterator(); iter.hasNext();) {
				productAttrValueDAO.deleteProductAttr(iter.next());
			}
			List<PointProductAttrValueDTO> attrList = productDTO.getAttrList();
			if (attrList != null) {
				for (Iterator<PointProductAttrValueDTO> iter = attrList.iterator(); iter.hasNext();) {
					PointProductAttrValueDTO attrDTO = iter.next();
					PointProductAttrValueEntity attrEntity = new PointProductAttrValueEntity();
					attrEntity.setCateAttr(attrDTO.isCateAttr());
					if (attrDTO.isCateAttr()) {
						PointProductCateAttrDTO cateAttrDTO = attrDTO.getProductCateAttrDTO();
						PointProductCateAttrEntity cateAttrEntity = productCateAttrDAO
								.getProductCateAttrByUuid(cateAttrDTO.getProductCateAttrUuid());
						attrEntity.setProductCateAttrEntity(cateAttrEntity);
					}
					attrEntity.setProductAttrName(attrDTO.getProductAttrName());
					attrEntity.setProductAttrValue(attrDTO.getProductAttrValue());
					attrEntity.setProductEntity(productEntity);
					productAttrValueDAO.addProductAttr(attrEntity);
				}
			}
			List<PointProductSkuEntity> skuEntityList = productSkuDAO.getProductSkuByProduct(productEntity);
			for (Iterator<PointProductSkuEntity> iter = skuEntityList.iterator(); iter.hasNext();) {
				productSkuDAO.deleteProductSku(iter.next());
			}
			List<PointProductSkuAttrValueEntity> skuValueEntityList = productSkuAttrValueDAO
					.getProductSkuAttrValuesByProduct(productEntity);
			for (Iterator<PointProductSkuAttrValueEntity> iter = skuValueEntityList.iterator(); iter.hasNext();) {
				productSkuAttrValueDAO.deleteProductAttrValue(iter.next());
			}
			List<PointProductSkuAttrEntity> attrEntityList = productSkuAttrDAO.getProductAttrByProduct(productEntity);
			for (Iterator<PointProductSkuAttrEntity> iter = attrEntityList.iterator(); iter.hasNext();) {
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

	public void updateCouponProduct(PointProductDTO productDTO) throws BusinessException {
		try {

			PointProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
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
	public void deleteProduct(PointProductDTO productDTO) throws BusinessException {
		try {
			// 基本属性
			PointProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
//			if (productEntity != null) {
//				productDAO.deleteProduct(productEntity);
//			}
			if (!productEntity.isOnSale()) {
				// 商品视频
				List<PointProductVideoEntity> videoEntityList = productVideoDAO.getVideosByProduct(productEntity);
				for (Iterator<PointProductVideoEntity> iter = videoEntityList.iterator(); iter.hasNext();) {
					productVideoDAO.deleteProductVideo(iter.next());
				}

				// 删除商品SKU
				List<PointProductSkuEntity> skuEntityList = productSkuDAO.getProductSkuByProduct(productEntity);
				for (Iterator<PointProductSkuEntity> iter = skuEntityList.iterator(); iter.hasNext();) {
					productSkuDAO.deleteProductSku(iter.next());
				}
				List<PointProductSkuAttrValueEntity> skuValueEntityList = productSkuAttrValueDAO
						.getProductSkuAttrValuesByProduct(productEntity);
				for (Iterator<PointProductSkuAttrValueEntity> iter = skuValueEntityList.iterator(); iter.hasNext();) {
					productSkuAttrValueDAO.deleteProductAttrValue(iter.next());
				}
				List<PointProductSkuAttrEntity> attrEntityList = productSkuAttrDAO.getProductAttrByProduct(productEntity);
				for (Iterator<PointProductSkuAttrEntity> iter = attrEntityList.iterator(); iter.hasNext();) {
					productSkuAttrDAO.deleteProductAttr(iter.next());
				}

				// 商品图片
				List<PointProductImageEntity> imageEntityList = productImageDAO.getImagesByProduct(productEntity);
				for (Iterator<PointProductImageEntity> iter = imageEntityList.iterator(); iter.hasNext();) {
					productImageDAO.deleteProductImage(iter.next());
				}

				// 商品图片描述productdescimage
				List<PointProductDescImageEntity> descImageEntityList = productDescImageDAO
						.getDescImagesByProduct(productEntity);
				for (Iterator<PointProductDescImageEntity> iter = descImageEntityList.iterator(); iter.hasNext();) {
					productDescImageDAO.deleteProductDescImage(iter.next());
				}

				// 设置商品属性
				List<PointProductAttrValueEntity> valueEntityList = productAttrValueDAO
						.getProductAttrByProduct(productEntity);
				for (Iterator<PointProductAttrValueEntity> iter = valueEntityList.iterator(); iter.hasNext();) {
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
	public void enableProduct(PointProductDTO productDTO) throws BusinessException {
		try {
			// 基本属性
			PointProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
			if (productEntity != null) {
				productEntity.setOnSale(true);
				productDAO.updateProduct(productEntity);
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void disableProduct(PointProductDTO productDTO) throws BusinessException {
		try {
			// 基本属性
			PointProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
			if (productEntity != null) {
				productEntity.setOnSale(false);
				productDAO.updateProduct(productEntity);
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}
	
	@Override
	public void addProductComment(PointProductCommentDTO productCommentDTO) throws BusinessException {
		try {
			PointProductCommentEntity commentEntity = new PointProductCommentEntity();
			commentEntity.setCommentContent(productCommentDTO.getCommentContent());
			commentEntity.setCommentRank(productCommentDTO.getCommentRank());
			commentEntity.setShow(true);
			commentEntity.setEvaluateTime(new Date());
			OrderEntity orderEntity = orderDAO.getOrderByUuid(productCommentDTO.getOrderUuid());
			PointProductEntity productEntity = productDAO.getProductByUuid(productCommentDTO.getProductUuid());
			UserEntity userEntity = orderEntity.getUserEntity();
			commentEntity.setProductEntity(productEntity);
			commentEntity.setOrderEntity(orderEntity);
			commentEntity.setUserEntity(userEntity);
			productCommentDAO.addProductComment(commentEntity);

			List<String> urlList = productCommentDTO.getImageUrlList();
			if (urlList != null) {
				for (Iterator<String> iterator = urlList.iterator(); iterator.hasNext();) {
					PointProductCommentImageEntity imageEntity = new PointProductCommentImageEntity();
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
	public void updateProductComment(PointProductCommentDTO productCommentDTO) throws BusinessException {
		try {
			PointProductCommentEntity commentEntity = productCommentDAO
					.getProductCommentByUuid(productCommentDTO.getProductCommentUuid());
			commentEntity.setCommentContent(productCommentDTO.getCommentContent());
			commentEntity.setCommentRank(productCommentDTO.getCommentRank());
			commentEntity.setShow(true);
			commentEntity.setEvaluateTime(new Date());
//			UserDTO userDTO = productCommentDTO.getUserDTO();
//			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
//			commentEntity.setUserEntity(userEntity);
			productCommentDAO.updateProductComment(commentEntity);

			List<PointProductCommentImageEntity> imageEntityList = productCommentImageDAO
					.getProductCommentImagesByProductComment(commentEntity);
			for (Iterator<PointProductCommentImageEntity> iterator = imageEntityList.iterator(); iterator.hasNext();) {
				productCommentImageDAO.deleteProductCommentImage(iterator.next());
			}
			List<String> urlList = productCommentDTO.getImageUrlList();
			for (Iterator<String> iterator = urlList.iterator(); iterator.hasNext();) {
				PointProductCommentImageEntity imageEntity = new PointProductCommentImageEntity();
				imageEntity.setProductCommentEntity(commentEntity);
				imageEntity.setImageUrl(iterator.next());
				productCommentImageDAO.addProductCommentImage(imageEntity);
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void setProductCommentShowStatus(PointProductCommentDTO commentDTO, boolean showStatus) throws BusinessException {
		try {
			PointProductCommentEntity commentEntity = productCommentDAO
					.getProductCommentByUuid(commentDTO.getProductCommentUuid());
			commentEntity.setShow(showStatus);
			productCommentDAO.updateProductComment(commentEntity);

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void deleteProductComment(PointProductCommentDTO productCommentDTO) throws BusinessException {
		try {
			PointProductCommentEntity commentEntity = productCommentDAO
					.getProductCommentByUuid(productCommentDTO.getProductCommentUuid());
			List<PointProductCommentImageEntity> imageEntityList = productCommentImageDAO
					.getProductCommentImagesByProductComment(commentEntity);
			for (Iterator<PointProductCommentImageEntity> iterator = imageEntityList.iterator(); iterator.hasNext();) {
				productCommentImageDAO.deleteProductCommentImage(iterator.next());
			}

			productCommentDAO.deleteProductComment(commentEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}

	@Override
	public void replayProductComment(PointProductCommentDTO productCommentDTO) throws BusinessException {
		try {
			PointProductCommentEntity commentEntity = productCommentDAO
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
	public void updateProductCommentReply(PointProductCommentDTO productCommentDTO) throws BusinessException {
		try {
			PointProductCommentEntity commentEntity = productCommentDAO
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
	public List<PointProductCommentDTO> getProductComments(PointProductDTO productDTO) throws BusinessException {
		List<PointProductCommentDTO> commentDTOList = new ArrayList<PointProductCommentDTO>();
		try {
			PointProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
			List<PointProductCommentEntity> commentEntityList = productCommentDAO.getProductCommentByProduct(productEntity);

			for (Iterator<PointProductCommentEntity> iter = commentEntityList.iterator(); iter.hasNext();) {
				PointProductCommentEntity commentEntity = iter.next();
				PointProductCommentDTO commentDTO = new PointProductCommentDTO();
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

				List<PointProductCommentImageEntity> imageEntityList = commentEntity.getImageList();
				if (imageEntityList != null) {
					List<String> imageUrlList = new ArrayList<String>();
					for (Iterator<PointProductCommentImageEntity> iterator = imageEntityList.iterator(); iterator
							.hasNext();) {
						PointProductCommentImageEntity imageEntity = iterator.next();
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
	public PointProductCommentDTO getProductCommentByUuid(String uuid) throws BusinessException {
		try {
			PointProductCommentEntity commentEntity = productCommentDAO.getProductCommentByUuid(uuid);
			PointProductCommentDTO commentDTO = new PointProductCommentDTO();
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

			List<PointProductCommentImageEntity> imageEntityList = commentEntity.getImageList();
			List<String> imageUrlList = new ArrayList<String>();
			for (Iterator<PointProductCommentImageEntity> iterator = imageEntityList.iterator(); iterator.hasNext();) {
				PointProductCommentImageEntity imageEntity = iterator.next();
				imageUrlList.add(imageEntity.getImageUrl());
			}
			commentDTO.setImageUrlList(imageUrlList);
			return commentDTO;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}

	@Override
	public List<PointProductCommentDTO> searchProductComment(ProductCommentSearchDTO searchDTO, boolean isShowOnly)
			throws BusinessException {
		List<PointProductCommentDTO> commentDTOList = new ArrayList<PointProductCommentDTO>();
		try {
			List<PointProductCommentEntity> commentEntityList = productCommentDAO.searchProductComment(searchDTO,
					isShowOnly);

			for (Iterator<PointProductCommentEntity> iter = commentEntityList.iterator(); iter.hasNext();) {
				PointProductCommentEntity commentEntity = iter.next();
				PointProductCommentDTO commentDTO = new PointProductCommentDTO();
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

				PointProductEntity productEntity = commentEntity.getProductEntity();
				if (productEntity != null) {
					PointProductDTO productDTO = new PointProductDTO();
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

				List<PointProductCommentImageEntity> imageEntityList = commentEntity.getImageList();
				if (imageEntityList != null) {
					List<String> imageUrlList = new ArrayList<String>();
					for (Iterator<PointProductCommentImageEntity> iterator = imageEntityList.iterator(); iterator
							.hasNext();) {
						PointProductCommentImageEntity imageEntity = iterator.next();
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
	public int getProductCommentTotal(PointProductDTO productDTO) throws BusinessException {
		try {
			return productCommentDAO.getProductCommentTotal(productDTO);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}
	
	
	@Override
	public PointProductDTO getProductByUuid(String uuid) throws BusinessException {
		try {
			PointProductEntity entity = pointProductDAO.getProductByUuid(uuid);
			if(entity != null) {
				PointProductDTO dto = new PointProductDTO();
				productDetailEntity2DTO(entity, dto);
				return dto;
			}
		}catch(DAOException e) {
			logger.error("根据ID查询团购商品发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return null;
	}
	
	@Override
	public PointProductSkuDTO getProductSkuByUuid(String uuid) throws BusinessException {
		try {
			PointProductSkuEntity entity = pointProductSkuDAO.getProductSkuByUuid(uuid);
			if(entity != null) {
				PointProductSkuDTO dto = new PointProductSkuDTO();
				productSkuEntity2DTO(entity, dto);
				return dto;
			}
		}catch(DAOException e) {
			logger.error("根据ID查询团购商品规格发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return null;
	}
	
	@Override
	public List<PointProductDTO> searchProduct(ProductSearchDTO productSearchDTO) throws BusinessException {
		List<PointProductDTO> productDTOList = new ArrayList<PointProductDTO>();
		try {
			List<PointProductEntity> productEntityList = productDAO.searchProduct(productSearchDTO);
			for (Iterator<PointProductEntity> iter = productEntityList.iterator(); iter.hasNext();) {
				PointProductEntity productEntity = iter.next();
				PointProductDTO productDTO = new PointProductDTO();
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
	public List<PointProductDTO> searchPointProduct(ProductSearchDTO searchDTO) throws BusinessException {
		List<PointProductDTO> pointProductDTOList = new ArrayList<PointProductDTO>();
		try {
			List<PointProductEntity>  entityList = pointProductDAO.searchProduct(searchDTO);
			for (PointProductEntity pointProductEntity : entityList) {
				PointProductDTO pointProductDTO = new PointProductDTO();
				productEntity2DTO(pointProductEntity, pointProductDTO);
				pointProductDTOList.add(pointProductDTO);
			}
			
		}catch(DAOException e) {
			logger.error("搜索团购商品发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
		return pointProductDTOList;
	}

	@Override
	public int searchPointProductTotal(ProductSearchDTO searchDTO) throws BusinessException {
		try {
			return pointProductDAO.searchProductTotal(searchDTO);
		}catch(DAOException e) {
			logger.error("搜索团购商品数量发生异常:"+e.getMessage());
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		}
	}

	
	private void cateEntity2DTO(PointProductCateDTO productCateDTO, PointProductCateEntity productCateEntity) {
		if (productCateDTO != null && productCateEntity != null) {
			productCateDTO.setProductCateUuid(productCateEntity.getProductCateUuid());
			productCateDTO.setCateName(productCateEntity.getCateName());
			productCateDTO.setCatePicUrl(productCateEntity.getCatePicUrl());
			productCateDTO.setSortNumber(productCateEntity.getSortNumber());
			productCateDTO.setLeaf(productCateEntity.isLeaf());
			productCateDTO.setDisplayedHome(productCateEntity.isDisplayedHome());

			List<PointProductCateAttrEntity> attrEntityList = productCateEntity.getAttrList();
			if (attrEntityList != null && attrEntityList.size() > 0) {
				List<PointProductCateAttrDTO> attrDTOList = new ArrayList<PointProductCateAttrDTO>();
				for (Iterator<PointProductCateAttrEntity> iter = attrEntityList.iterator(); iter.hasNext();) {
					PointProductCateAttrEntity attrEntity = iter.next();
					PointProductCateAttrDTO attrDTO = new PointProductCateAttrDTO();
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
	public void addProduct(PointProductDTO productDTO) throws BusinessException {
		try {
			// 基本属性
			PointProductEntity productEntity = new PointProductEntity();
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
			PointProductCateDTO cateDTO = productDTO.getProductCateDTO();
			if (cateDTO != null && !StringUtils.isEmpty(cateDTO.getProductCateUuid())) {
				PointProductCateEntity cateEntity = productCateDAO.getProductCateByUuid(cateDTO.getProductCateUuid());
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

			// 运费模板
			ProductFreightDTO freightDTO = productDTO.getProductFreightDTO();
			if (freightDTO != null) {
				ProductFreightEntity freightEntity = productFreightDAO
						.getFreightByUuid(freightDTO.getProductFreightUuid());
				productEntity.setProductFreightEntity(freightEntity);
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
					PointProductImageEntity productImageEntity = new PointProductImageEntity();
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
					PointProductDescImageEntity productImageEntity = new PointProductDescImageEntity();
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
					PointProductVideoEntity videoEntity = new PointProductVideoEntity();
					videoEntity.setProductEntity(productEntity);
					videoEntity.setFileEntity(imageEntity);
					productVideoDAO.createProductVideo(videoEntity);
				}
			}

			// 设置商品属性
			List<PointProductAttrValueDTO> attrList = productDTO.getAttrList();
			if (attrList != null) {
				for (Iterator<PointProductAttrValueDTO> iter = attrList.iterator(); iter.hasNext();) {
					PointProductAttrValueDTO attrDTO = iter.next();
					PointProductAttrValueEntity attrEntity = new PointProductAttrValueEntity();
					attrEntity.setCateAttr(attrDTO.isCateAttr());
					if (attrDTO.isCateAttr()) {
						PointProductCateAttrDTO cateAttrDTO = attrDTO.getProductCateAttrDTO();
						PointProductCateAttrEntity cateAttrEntity = productCateAttrDAO
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
	
	void addCouponProduct(PointProductDTO productDTO) throws BusinessException {
		try {
			// 优惠券商品
			PointProductEntity productEntity = new PointProductEntity();
			CouponDTO couponDTO = productDTO.getCouponDTO();
			CouponEntity couponEntity = couponDAO.getCouponByUuid(couponDTO.getCouponUuid());
			productDTO2Entity(productEntity, productDTO);
			productEntity.setSkuEnabled(false);
			productDAO.addProduct(productEntity);

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}

	}


	private void buildSKU(PointProductDTO productDTO) throws DAOException {
		PointProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
		List<PointProductSkuAttrDTO> skuAttrDTOList = productDTO.getSkuAttrList();
		if (skuAttrDTOList != null) {
			for (Iterator<PointProductSkuAttrDTO> iter = skuAttrDTOList.iterator(); iter.hasNext();) {
				PointProductSkuAttrDTO skuAttrDTO = iter.next();
				List<PointProductSkuAttrValueDTO> skuAttrValueList = skuAttrDTO.getProductAttrValueList();
				// 1. 添加SKU屬性
				PointProductSkuAttrEntity skuAttrEntity = new PointProductSkuAttrEntity();
				skuAttrEntity.setProductEntity(productEntity);
				skuAttrEntity.setSkuAttrName(skuAttrDTO.getSkuAttrName());
				skuAttrEntity.setCateAttr(skuAttrDTO.isCateAttr());
				if (skuAttrDTO.isCateAttr()) {
					PointProductCateAttrDTO cateAttrDTO = skuAttrDTO.getProductCateAttrDTO();
					PointProductCateAttrEntity cateAttrEntity = productCateAttrDAO
							.getProductCateAttrByUuid(cateAttrDTO.getProductCateAttrUuid());
					skuAttrEntity.setProductCateAttrEntity(cateAttrEntity);
				}
				productSkuAttrDAO.addProductAttr(skuAttrEntity);
				// 2. 添加SKU屬性值
				if (skuAttrValueList != null) {
					for (Iterator<PointProductSkuAttrValueDTO> valueIter = skuAttrValueList.iterator(); valueIter
							.hasNext();) {
						PointProductSkuAttrValueDTO valueDTO = valueIter.next();
						PointProductSkuAttrValueEntity valueEntity = new PointProductSkuAttrValueEntity();
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
		List<PointProductSkuDTO> skuDTOList = productDTO.getSkuList();
		if (skuDTOList != null) {
			for (Iterator<PointProductSkuDTO> iter = skuDTOList.iterator(); iter.hasNext();) {
				PointProductSkuDTO skuDTO = iter.next();
				List<PointProductSkuAttrValueDTO> skuAttrValueList = skuDTO.getSkuAttrValueList();
				String[] skuAttrValueUuidArray = new String[skuAttrValueList.size()];
				// 將各屬性值組合存儲成[48324:67473:7889]格式
				for (int i = 0; i < skuAttrValueList.size(); i++) {
					PointProductSkuAttrValueDTO valueDTO = (PointProductSkuAttrValueDTO) skuAttrValueList.get(i);
					String name = valueDTO.getSkuAttrName();
					String value = valueDTO.getSkuAttrValue();
					PointProductSkuAttrValueEntity valueEntity = productSkuAttrValueDAO
							.getProductSkuAttrValuesByNameValue(productEntity, name, value);
					if (valueEntity != null) {
						skuAttrValueUuidArray[i] = valueEntity.getProductSkuAttrValueUuid();
					}
				}
				Arrays.sort(skuAttrValueUuidArray);
				PointProductSkuEntity skuEntity = new PointProductSkuEntity();
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
	
	void productDTO2Entity(PointProductEntity productEntity, PointProductDTO productDTO) {
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
		productEntity.setProductWeight(productDTO.getProductWeight());
	}

	@Override
	public void productEntity2DTO(PointProductEntity productEntity, PointProductDTO productDTO) {
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
		PointProductCateEntity cateEntity = productEntity.getProductCateEntity();
		if (cateEntity != null) {
			PointProductCateDTO cateDTO = new PointProductCateDTO();
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
	public void productDetailEntity2DTO(PointProductEntity productEntity, PointProductDTO productDTO) {
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
		PointProductCateEntity cateEntity = productEntity.getProductCateEntity();
		if (cateEntity != null) {
			PointProductCateDTO cateDTO = new PointProductCateDTO();
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
			List<PointProductImageEntity> images = productImageDAO.getImagesByProduct(productEntity);
			if (images != null) {
				List<FileDTO> fileDTOList = new ArrayList<FileDTO>();
				for (Iterator<PointProductImageEntity> iter = images.iterator(); iter.hasNext();) {
					FileEntity imageEntity = iter.next().getFileEntity();
					FileDTO imageDTO = new FileDTO();
					imageDTO.setUrl(imageEntity.getUrl());
					fileDTOList.add(imageDTO);
				}
				productDTO.setProductImages(fileDTOList);
			}

			// 商品视频
			List<PointProductVideoEntity> videos = productVideoDAO.getVideosByProduct(productEntity);
			if (videos != null) {
				List<FileDTO> fileDTOList = new ArrayList<FileDTO>();
				for (Iterator<PointProductVideoEntity> iter = videos.iterator(); iter.hasNext();) {
					FileEntity imageEntity = iter.next().getFileEntity();
					FileDTO videoDTO = new FileDTO();
					videoDTO.setUrl(imageEntity.getUrl());
					fileDTOList.add(videoDTO);
				}
				productDTO.setProductVideos(fileDTOList);
			}

			// 商品描述图片
			List<PointProductDescImageEntity> descImages = productDescImageDAO.getDescImagesByProduct(productEntity);
			if (descImages != null) {
				List<FileDTO> fileDTOList = new ArrayList<FileDTO>();
				for (Iterator<PointProductDescImageEntity> iter = descImages.iterator(); iter.hasNext();) {
					FileEntity imageEntity = iter.next().getFileEntity();
					FileDTO videoDTO = new FileDTO();
					videoDTO.setUrl(imageEntity.getUrl());
					fileDTOList.add(videoDTO);
				}
				productDTO.setProductDescImages(fileDTOList);
			}

			// 商品属性
			List<PointProductAttrValueDTO> attrDTOList = new ArrayList<PointProductAttrValueDTO>();
			List<PointProductAttrValueEntity> attrEntityList = productAttrValueDAO.getProductAttrByProduct(productEntity);
			for (Iterator<PointProductAttrValueEntity> iter = attrEntityList.iterator(); iter.hasNext();) {
				PointProductAttrValueEntity attrEntity = iter.next();
				PointProductAttrValueDTO attrDTO = new PointProductAttrValueDTO();
				attrDTO.setProductAttrValueUuid(attrEntity.getProductAttrValueUuid());
				attrDTO.setCateAttr(attrEntity.isCateAttr());
				// 如果继承自品类属性, 关联品类属性
				if (attrEntity.isCateAttr()) {
					PointProductCateAttrDTO cateAttrDTO = attrDTO.getProductCateAttrDTO();
					PointProductCateAttrEntity cateAttrEntity = attrEntity.getProductCateAttrEntity();
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
			List<PointProductSkuAttrEntity> skuAttrEntityList = productSkuAttrDAO.getProductAttrByProduct(productEntity);
			List<PointProductSkuAttrDTO> skuAttrDTOList = new ArrayList<PointProductSkuAttrDTO>();
			for (Iterator<PointProductSkuAttrEntity> iter = skuAttrEntityList.iterator(); iter.hasNext();) {
				PointProductSkuAttrEntity skuAttrEntity = iter.next();
				PointProductSkuAttrDTO skuAttrDTO = new PointProductSkuAttrDTO();
				skuAttrDTO.setProductSkuAttrUuid(skuAttrEntity.getProductSkuAttrUuid());
				skuAttrDTO.setSkuAttrName(skuAttrEntity.getSkuAttrName());
				skuAttrDTO.setCateAttr(skuAttrEntity.isCateAttr());
				if (skuAttrEntity.isCateAttr()) {
					PointProductCateAttrEntity cateAttrEntity = skuAttrEntity.getProductCateAttrEntity();
					PointProductCateAttrDTO cateAttrDTO = new PointProductCateAttrDTO();
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
				List<PointProductSkuAttrValueEntity> skuAttrValueEntityList = productSkuAttrValueDAO
						.getProductSkuAttrValuesByAttr(skuAttrEntity);
				List<PointProductSkuAttrValueDTO> skuAttrValueDTOList = new ArrayList<PointProductSkuAttrValueDTO>();
				for (Iterator<PointProductSkuAttrValueEntity> valueIter = skuAttrValueEntityList.iterator(); valueIter
						.hasNext();) {
					PointProductSkuAttrValueEntity skuAttrValueEntity = valueIter.next();
					PointProductSkuAttrValueDTO skuAttrValueDTO = new PointProductSkuAttrValueDTO();
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
			List<PointProductSkuEntity> skuEntityList = productSkuDAO.getProductSkuByProduct(productEntity);
			List<PointProductSkuDTO> skuDTOList = new ArrayList<PointProductSkuDTO>();
			if (skuEntityList != null) {
				for (Iterator<PointProductSkuEntity> iter = skuEntityList.iterator(); iter.hasNext();) {
					PointProductSkuEntity skuEntity = iter.next();
					PointProductSkuDTO skuDTO = new PointProductSkuDTO();
					String skuAttrValueUuids = skuEntity.getSkuAttrValueUuids();
					String tSkuAttrValueUuids = skuAttrValueUuids.replace("[", "").replace("]", "");
					String[] skuAttrValueUuidArray = tSkuAttrValueUuids.split(ProductConstants.DELIMETER + " ");
					List<PointProductSkuAttrValueDTO> attrValueDTOList = new ArrayList<PointProductSkuAttrValueDTO>();
					for (int i = 0; i < skuAttrValueUuidArray.length; i++) {
						String skuAttrValueUuid = skuAttrValueUuidArray[i];
						PointProductSkuAttrValueEntity skuAttrValueEntity = productSkuAttrValueDAO
								.getProductSkuAttrValueByUuid(skuAttrValueUuid);
						PointProductSkuAttrValueDTO skuAttrValueDTO = new PointProductSkuAttrValueDTO();
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
	public void productSkuEntity2DTO(PointProductSkuEntity productSkuEntity, PointProductSkuDTO productSkuDTO) {
		String skuAttrValueUuids = productSkuEntity.getSkuAttrValueUuids();
		String tSkuAttrValueUuids = skuAttrValueUuids.replace("[", "").replace("]", "");
		String[] skuAttrValueUuidArray = tSkuAttrValueUuids.split(ProductConstants.DELIMETER + " ");
		List<PointProductSkuAttrValueDTO> attrValueDTOList = new ArrayList<PointProductSkuAttrValueDTO>();

		for (int i = 0; i < skuAttrValueUuidArray.length; i++) {
			String skuAttrValueUuid = skuAttrValueUuidArray[i];
			try {
				PointProductSkuAttrValueEntity skuAttrValueEntity = productSkuAttrValueDAO
						.getProductSkuAttrValueByUuid(skuAttrValueUuid);
				PointProductSkuAttrValueDTO skuAttrValueDTO = new PointProductSkuAttrValueDTO();
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
		productSkuDTO.setProductSkuUuid(productSkuEntity.getProductSkuUuid());
		productSkuDTO.setSkuImageUrl(productSkuEntity.getSkuImageUrl());
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
	
	void productBrandEntity2DTO(ProductBrandEntity productBrandEntity, ProductBrandDTO productBrandDTO) {
		productBrandDTO.setProductBrandUuid(productBrandEntity.getProductBrandUuid());
		productBrandDTO.setName(productBrandEntity.getName());
		productBrandDTO.setDescription(productBrandEntity.getDescription());
		productBrandDTO.setLogoUrl(productBrandEntity.getLogoUrl());
		productBrandDTO.setSortNumber(productBrandEntity.getSortNumber());
	}
}
