package com.mb.ext.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mb.ext.core.constant.CouponConstants;
import com.mb.ext.core.dao.AreaDAO;
import com.mb.ext.core.dao.FileDAO;
import com.mb.ext.core.dao.SequenceDAO;
import com.mb.ext.core.dao.SupplierDAO;
import com.mb.ext.core.dao.UserDAO;
import com.mb.ext.core.dao.coupon.CouponDAO;
import com.mb.ext.core.dao.product.ProductAttrValueDAO;
import com.mb.ext.core.dao.product.ProductBrandDAO;
import com.mb.ext.core.dao.product.ProductCateAttrDAO;
import com.mb.ext.core.dao.product.ProductCateDAO;
import com.mb.ext.core.dao.product.ProductCommentDAO;
import com.mb.ext.core.dao.product.ProductCommentImageDAO;
import com.mb.ext.core.dao.product.ProductDAO;
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
import com.mb.ext.core.dao.promote.PromoteFreightOffDAO;
import com.mb.ext.core.dao.promote.PromoteMoneyOffDAO;
import com.mb.ext.core.dao.shoppingcart.ShoppingCartDAO;
import com.mb.ext.core.entity.AreaEntity;
import com.mb.ext.core.entity.UserEntity;
import com.mb.ext.core.entity.coupon.CouponEntity;
import com.mb.ext.core.entity.coupon.CouponProductEntity;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.entity.product.ProductFreightEntity;
import com.mb.ext.core.entity.product.ProductFreightRegionEntity;
import com.mb.ext.core.entity.product.ProductSkuEntity;
import com.mb.ext.core.entity.shoppingcart.ShoppingCartEntity;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.ProductService;
import com.mb.ext.core.service.SettingService;
import com.mb.ext.core.service.ShoppingCartService;
import com.mb.ext.core.service.UserService;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.MapDTO;
import com.mb.ext.core.service.spec.ShoppingCartDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.point.PointProductDTO;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.ext.core.service.spec.product.ProductDeliveryDTO;
import com.mb.ext.core.service.spec.product.ProductFreightDTO;
import com.mb.ext.core.service.spec.product.ProductSkuDTO;
import com.mb.ext.core.util.MapUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.service.AbstractService;
import com.mb.framework.util.log.LogHelper;

@Service
@Qualifier("ShoppingCartService")
public class ShoppingCartServiceImpl extends AbstractService implements
		ShoppingCartService<BodyDTO> {

	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;

	@Autowired
	@Qualifier("fileDAO")
	private FileDAO fileDAO;
	
	@Autowired
	@Qualifier("areaDAO")
	private AreaDAO areaDAO;

	@Autowired
	@Qualifier("supplierProductDAO")
	private SupplierProductDAO supplierProductDAO;

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
	@Qualifier("userService")
	private UserService userService;
	
	@Autowired
	@Qualifier("shoppingCartDAO")
	private ShoppingCartDAO shoppingCartDAO;
	
	@Autowired
	@Qualifier("ProductService")
	private ProductService productService;
	
	@Autowired
	@Qualifier("settingService")
	private SettingService settingService;

	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	@Override
	public int getCartNum(UserDTO userDTO) throws BusinessException {
		try{
			int cartNum = 0;
			UserEntity userEntity = userService.getUser(userDTO);
			if(userEntity != null) {
				cartNum = shoppingCartDAO.getShoppingCartNumByUser(userEntity);
			}
			return cartNum;
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}

	@Override
	public List<ShoppingCartDTO> getProducts(UserDTO userDTO) throws BusinessException {
		List<ShoppingCartDTO> ShoppingCartDTOList = new ArrayList<ShoppingCartDTO>();
		try{
			UserEntity userEntity = userService.getUser(userDTO);
			if(userEntity != null) {
				List<ShoppingCartEntity> cardEntityList = shoppingCartDAO.getShoppingCartByUser(userEntity);
				for (Iterator<ShoppingCartEntity> iterator = cardEntityList.iterator(); iterator.hasNext();) {
					ShoppingCartEntity shoppingCartEntity = iterator.next();
					ProductEntity productEntity = shoppingCartEntity.getProductEntity();
					ProductSkuEntity productSkuEntity = shoppingCartEntity.getProductSkuEntity();
					ShoppingCartDTO cartDTO = new ShoppingCartDTO();
					cartDTO.setUnit(shoppingCartEntity.getUnit());
					cartDTO.setShoppingCartUuid(shoppingCartEntity.getShoppingCartUuid());
					ProductDTO productDTO = new ProductDTO();
					productService.productEntity2DTO(productEntity, productDTO);
					cartDTO.setProductDTO(productDTO);
					if(productSkuEntity != null) {
						ProductSkuDTO productSkuDTO = new ProductSkuDTO();
						productService.productSkuEntity2DTO(productSkuEntity, productSkuDTO);
						cartDTO.setProductSkuDTO(productSkuDTO);
					}
					ShoppingCartDTOList.add(cartDTO);
				}
			}
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		return ShoppingCartDTOList;
	}
	@Override
	public List<ShoppingCartDTO> getShoppingCartByUuids(List<String> uuidList) throws BusinessException {
		List<ShoppingCartDTO> ShoppingCartDTOList = new ArrayList<ShoppingCartDTO>();
		try{
			for (Iterator<String> iterator = uuidList.iterator(); iterator.hasNext();) {
				String shoppingCartUuid = iterator.next();
				ShoppingCartEntity shoppingCartEntity = shoppingCartDAO.getShoppingCartByUuid(shoppingCartUuid);
				ProductEntity productEntity = shoppingCartEntity.getProductEntity();
				ProductSkuEntity productSkuEntity = shoppingCartEntity.getProductSkuEntity();
				ShoppingCartDTO cartDTO = new ShoppingCartDTO();
				cartDTO.setUnit(shoppingCartEntity.getUnit());
				cartDTO.setShoppingCartUuid(shoppingCartEntity.getShoppingCartUuid());
				ProductDTO productDTO = new ProductDTO();
				productService.productEntity2DTO(productEntity, productDTO);
				cartDTO.setProductDTO(productDTO);
				if(productSkuEntity != null) {
					ProductSkuDTO productSkuDTO = new ProductSkuDTO();
					productService.productSkuEntity2DTO(productSkuEntity, productSkuDTO);
					cartDTO.setProductSkuDTO(productSkuDTO);
				}
				ShoppingCartDTOList.add(cartDTO);
			}
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		return ShoppingCartDTOList;
	}

	@Override
	public String addProductToShoppingCart(ProductDTO productDTO, UserDTO userDTO, int unit) throws BusinessException {
		try{
			UserEntity userEntity = userService.getUser(userDTO);
			ProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
			if(unit<1) {
				throw new BusinessException(BusinessErrorCode.PRODUCT_UNIT_INCORRECT);
			}
			if(productEntity.getTotalUnit()<unit) {
				throw new BusinessException(BusinessErrorCode.PRODUCT_STOCK_INSUFFICIENT);
			}
			ShoppingCartEntity cartEntity = shoppingCartDAO.getShoppingCartByUserProduct(userEntity, productEntity);
			if(cartEntity == null) {
				cartEntity = new ShoppingCartEntity();
				cartEntity.setUserEntity(userEntity);
				cartEntity.setProductEntity(productEntity);
				cartEntity.setUnit(unit);
				shoppingCartDAO.addShoppingCart(cartEntity);
			}else {
				cartEntity.setUnit(unit);
				shoppingCartDAO.updateShoppingCart(cartEntity);
			}
			return cartEntity.getShoppingCartUuid();
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}catch(BusinessException e) {
			throw e;
		}
		
	}
	@Override
	public String addProductSkuToShoppingCart(ProductSkuDTO productSkuDTO, UserDTO userDTO, int unit) throws BusinessException {
		try{
			UserEntity userEntity = userService.getUser(userDTO);
			ProductSkuEntity productSkuEntity = productSkuDAO.getProductSkuByUuid(productSkuDTO.getProductSkuUuid());
			if(unit<1) {
				throw new BusinessException(BusinessErrorCode.PRODUCT_UNIT_INCORRECT);
			}
			if(productSkuEntity.getSkuTotalUnit()<unit) {
				throw new BusinessException(BusinessErrorCode.PRODUCT_STOCK_INSUFFICIENT);
			}
			ProductEntity productEntity = productSkuEntity.getProductEntity();
			ShoppingCartEntity cartEntity = shoppingCartDAO.getShoppingCartByUserProductSku(userEntity, productSkuEntity);
			if(cartEntity == null) {
				cartEntity = new ShoppingCartEntity();
				cartEntity.setUserEntity(userEntity);
				cartEntity.setProductEntity(productEntity);
				cartEntity.setProductSkuEntity(productSkuEntity);
				cartEntity.setUnit(unit);
				shoppingCartDAO.addShoppingCart(cartEntity);
			}else {
				cartEntity.setUnit(unit);
				shoppingCartDAO.updateShoppingCart(cartEntity);
			}
			return cartEntity.getShoppingCartUuid();
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}catch(BusinessException e) {
			throw e;
		}
	}

	@Override
	public void removeProductFromShoppingCart(ProductDTO productDTO, UserDTO userDTO) throws BusinessException {
		try{
			UserEntity userEntity = userService.getUser(userDTO);
			ProductEntity productEntity = productDAO.getProductByUuid(productDTO.getProductUuid());
			ShoppingCartEntity cartEntity = shoppingCartDAO.getShoppingCartByUserProduct(userEntity, productEntity);
			if(cartEntity != null) {
				int unit = cartEntity.getUnit();
				if(unit>1) {
					cartEntity.setUnit(cartEntity.getUnit()-1);
					shoppingCartDAO.updateShoppingCart(cartEntity);
				}else {
					shoppingCartDAO.deleteShoppingCart(cartEntity);
				}
			}
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		
	}

	@Override
	public void removeProductSkuFromShoppingCart(ProductSkuDTO productSkuDTO, UserDTO userDTO) throws BusinessException {
		try{
			UserEntity userEntity = userService.getUser(userDTO);
			ProductSkuEntity productSkuEntity = productSkuDAO.getProductSkuByUuid(productSkuDTO.getProductSkuUuid());
			ShoppingCartEntity cartEntity = shoppingCartDAO.getShoppingCartByUserProductSku(userEntity, productSkuEntity);
			if(cartEntity != null) {
				int unit = cartEntity.getUnit();
				if(unit>1) {
					cartEntity.setUnit(cartEntity.getUnit()-1);
					shoppingCartDAO.updateShoppingCart(cartEntity);
				}else {
					shoppingCartDAO.deleteShoppingCart(cartEntity);
				}
			}
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		
	}
	
	@Override
	public void updateShoppingCartProductNum(ShoppingCartDTO shoppingCartDTO) throws BusinessException {
		try{
			ShoppingCartEntity cartEntity = shoppingCartDAO.getShoppingCartByUuid(shoppingCartDTO.getShoppingCartUuid());
			if(cartEntity != null) {
				int unit = shoppingCartDTO.getUnit();
				cartEntity.setUnit(unit);
				shoppingCartDAO.updateShoppingCart(cartEntity);
			}
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		
	}
	
	@Override
	public void deleteProductFromShoppingCart(ShoppingCartDTO shoppingCartDTO) throws BusinessException {
		try{
			ShoppingCartEntity cartEntity = shoppingCartDAO.getShoppingCartByUuid(shoppingCartDTO.getShoppingCartUuid());
			if(cartEntity != null) {
				shoppingCartDAO.deleteShoppingCart(cartEntity);
			}
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		
	}
	
	@Override
	public void clearShoppingCart(UserDTO userDTO) throws BusinessException {
		try{
			UserEntity userEntity = userDAO.getUserByUuid(userDTO.getUserUuid());
			shoppingCartDAO.clearShoppingCart(userEntity);
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		
	}

	@Override
	public BigDecimal calculateOrderCouponAmount(List<String> uuidList, String couponUuid) throws BusinessException {
		BigDecimal couponAmount = BigDecimal.valueOf(0);
		try{
			//第一步验证该优惠券是否适用于该订单
			CouponEntity couponEntity = couponDAO.getCouponByUuid(couponUuid);
			BigDecimal conditionAmount = couponEntity.getConditionAmount();
			List<ShoppingCartDTO> shoppingCartDTOList = getShoppingCartByUuids(uuidList); 
			BigDecimal totalAmount = calculateTotalAmount(shoppingCartDTOList);
			validateCoupon(uuidList, couponEntity, conditionAmount, totalAmount, shoppingCartDTOList);
			
			//计算优惠金额
			String couponType = couponEntity.getType();
			if(CouponConstants.COUPON_TYPE_CASH.equals(couponType)) {
				couponAmount = couponEntity.getBenefitCash().setScale(2,BigDecimal.ROUND_HALF_UP);
			}else if(CouponConstants.COUPON_TYPE_DISCOUNT.equals(couponType)) {
				BigDecimal discount = couponEntity.getBenefitDiscount();
				couponAmount = totalAmount.subtract(totalAmount.multiply(discount).divide(BigDecimal.valueOf(100),2,BigDecimal.ROUND_HALF_UP)).setScale(2,BigDecimal.ROUND_HALF_UP);
			}
			
		}catch(DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		return couponAmount;
	}
	
	@Override
	public BigDecimal calculateOrderFreightAmount(List<String> uuidList, String province, String city, String area) throws BusinessException {
		BigDecimal freightAmount = BigDecimal.valueOf(0);
		try{
			
			List<ShoppingCartDTO> shoppingCartDTOList = getShoppingCartByUuids(uuidList); 
			//将商品按商家分类, 分开计算运费
			Map<String, List<ShoppingCartDTO>> merchantProductMap = new HashMap<String, List<ShoppingCartDTO>>();
			for (ShoppingCartDTO shoppingCartDTO : shoppingCartDTOList) {
				String merchantUuid = shoppingCartDTO.getProductDTO().getMerchantDTO().getMerchantUuid();
				List<ShoppingCartDTO> shoppinCartList = merchantProductMap.get(merchantUuid);
				if(shoppinCartList == null) {
					shoppinCartList = new ArrayList<ShoppingCartDTO>();
				}
				shoppinCartList.add(shoppingCartDTO);
				merchantProductMap.put(merchantUuid, shoppinCartList);
			}
			for (String key:merchantProductMap.keySet()) {
				//计算每个商家的运费并累计
				List<ShoppingCartDTO> list = merchantProductMap.get(key);
				//订单中只有一种商品, 直接按照该商品运费模板计算
				if(list.size()==1) {
					BigDecimal amount = calculateSingleProductFreight(list.get(0),province,city,area);
					freightAmount = freightAmount.add(amount);
				}else {
					BigDecimal amount = calculateMultipleProductFreight(list,province,city,area);
					freightAmount = freightAmount.add(amount);
				}
			}
		}catch(BusinessException e) {
			throw e;
		}
		return freightAmount;
	}
	
	@Override
	public BigDecimal calculateOrderDeliveryAmount(List<String> uuidList, BigDecimal latitue, BigDecimal longitude) throws BusinessException {
		BigDecimal deliveryAmount = BigDecimal.valueOf(0);
		try{
			
			List<ShoppingCartDTO> shoppingCartDTOList = getShoppingCartByUuids(uuidList); 
			//将商品按商家分类, 分开计算运费
			Map<String, List<ShoppingCartDTO>> merchantProductMap = new HashMap<String, List<ShoppingCartDTO>>();
			for (ShoppingCartDTO shoppingCartDTO : shoppingCartDTOList) {
				String merchantUuid = shoppingCartDTO.getProductDTO().getMerchantDTO().getMerchantUuid();
				List<ShoppingCartDTO> shoppinCartList = merchantProductMap.get(merchantUuid);
				if(shoppinCartList == null) {
					shoppinCartList = new ArrayList<ShoppingCartDTO>();
				}
				shoppinCartList.add(shoppingCartDTO);
				merchantProductMap.put(merchantUuid, shoppinCartList);
			}
			for (String key:merchantProductMap.keySet()) {
				//计算每个商家的运费并累计
				List<ShoppingCartDTO> list = merchantProductMap.get(key);
				BigDecimal totalAmount = calculateTotalAmount(list);
				//订单中只有一种商品, 直接按照该商品运费模板计算
				if(list.size()==1) {
					BigDecimal amount = calculateSingleProductDelivery(list.get(0),totalAmount,latitue,longitude);
					deliveryAmount = deliveryAmount.add(amount);
				}else {
					BigDecimal amount = calculateMultipleProductDelivery(list,totalAmount,latitue,longitude);
					deliveryAmount = deliveryAmount.add(amount);
				}
			}
		}catch(BusinessException e) {
			throw e;
		}
		return deliveryAmount;
	}
	
	@Override
	public BigDecimal calculateProductDeliveryAmount(ProductDTO productDTO , BigDecimal unitPrice, int totalUnit, BigDecimal latitude, BigDecimal longitude) throws BusinessException {
		BigDecimal deliveryAmount = BigDecimal.valueOf(0);
		//订单总金额
		BigDecimal totalAmount = unitPrice.multiply(BigDecimal.valueOf(totalUnit));
		//获取该商品同城配送模板
		ProductDeliveryDTO deliveryDTO = productDTO.getProductDeliveryDTO();
		BigDecimal fromLatitude = deliveryDTO.getDeliveryFromLatitude();
		BigDecimal fromLongitude = deliveryDTO.getDeliveryFromLongitude();
		int conditionDistance = deliveryDTO.getDeliveryConditionDistance(); //配送范围
		BigDecimal conditionAmount = deliveryDTO.getDeliveryConditionAmount();	//起送金额
		int firstDistance = deliveryDTO.getFirstDistance();//x公里内免费
		BigDecimal firstPrice = deliveryDTO.getFirstPrice();//起步价
		int nextDistance = deliveryDTO.getNextDistance();//每增加x公里, 增加x元
		BigDecimal nextPrice = deliveryDTO.getNextPrice();
		
		//配送距离
		String ak = settingService.getGlobalMapSetting().getAmapWebServiceKey();
		MapDTO mapDTO = MapUtil.getLineDistance(ak, fromLatitude.doubleValue(), fromLongitude.doubleValue(), latitude.doubleValue(), longitude.doubleValue());
		double distance = mapDTO.getDistance()/1000;//实际配送距离(千米)
		//超出配送范围
		if(distance>conditionDistance) {
			throw new BusinessException(BusinessErrorCode.DELIVERY_CONDITION_OVER_DISTANCE);
		}
		//未到起送金额
		if(totalAmount.compareTo(conditionAmount)<0) {
			throw new BusinessException(BusinessErrorCode.DELIVERY_CONDITION_LESS_AMOUNT);
		}
		
		//x公里内免费
		if(distance<=firstDistance){
			deliveryAmount = BigDecimal.valueOf(0);
		}else {
			deliveryAmount = deliveryAmount.add(firstPrice);	//起步价
			double remainingDistance = distance - firstDistance;
			//超过起步距离费用计算
			if(remainingDistance > 0) {
				int num = (int)Math.floor(remainingDistance/nextDistance);
				if(num>0) deliveryAmount = deliveryAmount.add(nextPrice.multiply(BigDecimal.valueOf(num)));
			}
			
		}
		return deliveryAmount;
	}
	
	@Override
	public BigDecimal calculateProductFreightAmount(ProductDTO productDTO, BigDecimal unitPrice, int totalUnit, String province, String city, String area) throws BusinessException {
		BigDecimal freightAmount = BigDecimal.valueOf(0);
		try{
			//订单总重量
			BigDecimal totalWeight = productDTO.getProductWeight().multiply(BigDecimal.valueOf(totalUnit));
			//订单总金额
			BigDecimal totalAmount = unitPrice.multiply(BigDecimal.valueOf(totalUnit));
			//获取该商品运费模板
			ProductFreightDTO freightDTO = productDTO.getProductFreightDTO();
			
			//检查快递区域是否在该运费模板特定区域以内
			checkFreightRegion(province, freightDTO);
			
			//满足包邮条件, 运费为0
			if(freightDTO.isShippingFree() && freightDTO.getShippingFreeConditionAmount().compareTo(totalAmount)<=0){
				return freightAmount;
			}
			int chargeType = freightDTO.getChargeType();
			//按件计费
			if(chargeType==1) {
				freightAmount = calculateFreightByUnit(freightDTO, totalUnit, true);
			}
			//按重量计费
			else if(chargeType==2) {
				freightAmount = calculateFreightByWeight(freightDTO, totalWeight, true);
			}
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return freightAmount;
	}
	
	@Override
	public BigDecimal calculatePointProductFreightAmount(PointProductDTO productDTO, BigDecimal unitPrice, int totalUnit, String province, String city, String area) throws BusinessException {
		BigDecimal freightAmount = BigDecimal.valueOf(0);
		try{
			//订单总重量
			BigDecimal totalWeight = productDTO.getProductWeight().multiply(BigDecimal.valueOf(totalUnit));
			//订单总金额
			BigDecimal totalAmount = unitPrice.multiply(BigDecimal.valueOf(totalUnit));
			//获取该商品运费模板
			ProductFreightDTO freightDTO = productDTO.getProductFreightDTO();
			
			//检查快递区域是否在该运费模板特定区域以内
			checkFreightRegion(province, freightDTO);
			
			//满足包邮条件, 运费为0
			if(freightDTO.isShippingFree() && freightDTO.getShippingFreeConditionAmount().compareTo(totalAmount)<=0){
				return freightAmount;
			}
			int chargeType = freightDTO.getChargeType();
			//按件计费
			if(chargeType==1) {
				freightAmount = calculateFreightByUnit(freightDTO, totalUnit, true);
			}
			//按重量计费
			else if(chargeType==2) {
				freightAmount = calculateFreightByWeight(freightDTO, totalWeight, true);
			}
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return freightAmount;
	}
	
	/**计算购物车中商品需要的运费(只支持同商家商品)
	 * @param shoppingCartDTOList
	 * @param province
	 * @param city
	 * @param area
	 * @return
	 */
	private BigDecimal calculateMultipleProductFreight(List<ShoppingCartDTO> shoppingCartDTOList, String province, String city, String area) {
		
/*		1）取最大首重价格的模版首重作为订单首重价格和首重门槛，然后你分别计算商品独自的续重价格。
		2）当有最大首重价格相同，首重单位价格相同，续重价格不同的模版时，选取最小续重的模版作为订单首重。
		3）当有最大首重价格相同，单位价格不同时，取单位价格最大的模版作为订单的首重。
		4）包邮商品不参与运费计算*/
		BigDecimal freightAmount = BigDecimal.valueOf(0);
		
		List<ProductDTO> productList = new ArrayList<ProductDTO>();
		for (ShoppingCartDTO cartDTO : shoppingCartDTOList) {
			productList.add(cartDTO.getProductDTO());
		}
		//检查是否所有商品都存在满多少包邮, 只要有一个不满足就按照正常计算运费
		BigDecimal totalAmount = calculateTotalAmount(shoppingCartDTOList);
		
		//满足包邮条件, 运费为0
		boolean isShippingFree = true;
		for (ProductDTO productDTO : productList) {
			ProductFreightDTO freightDTO = productDTO.getProductFreightDTO();
			if(!freightDTO.isShippingFree() || freightDTO.getShippingFreeConditionAmount().compareTo(totalAmount)>0){
				isShippingFree = false;
				break;
			}
		}
		if(isShippingFree) return freightAmount;
		
		BigDecimal firstPrice = BigDecimal.valueOf(0);
		//计算商品中首重或首件最大价格
		for (ProductDTO productDTO : productList) {
			ProductFreightDTO freightDTO = productDTO.getProductFreightDTO();
			//检查快递区域是否在该运费模板特定区域以内(如在则使用特殊区域价格)
			checkFreightRegion(province, freightDTO);
			
			if(freightDTO.getDefaultFirstPrice().compareTo(firstPrice)>0)
				firstPrice = freightDTO.getDefaultFirstPrice();
		}
		freightAmount = freightAmount.add(firstPrice);
		//然后计算每个商品的续费价格
		for (ShoppingCartDTO cartDTO : shoppingCartDTOList) {
			ProductDTO productDTO = cartDTO.getProductDTO();
			ProductFreightDTO freightDTO = productDTO.getProductFreightDTO();
			
			//检查快递区域是否在该运费模板特定区域以内(如在则使用特殊区域价格)
			checkFreightRegion(province, freightDTO);
			
			int totalUnit = cartDTO.getUnit();
			BigDecimal totalWeight = productDTO.getProductWeight().multiply(BigDecimal.valueOf(totalUnit));
			int chargeType = freightDTO.getChargeType();
			//按件计费
			if(chargeType==1) {
				freightAmount = freightAmount.add(calculateFreightByUnit(freightDTO, cartDTO.getUnit(), false));
			}
			//按重量计费
			else if(chargeType==2) {
				freightAmount = freightAmount.add(calculateFreightByWeight(freightDTO, totalWeight, false));
			}
		}
		return freightAmount;
	}

	private BigDecimal calculateSingleProductFreight(ShoppingCartDTO shoppingCartDTO, String province, String city, String area) {
		
		BigDecimal freightAmount = BigDecimal.valueOf(0);
		
		ProductDTO productDTO = shoppingCartDTO.getProductDTO();
		//订单总件数
		int totalUnit = shoppingCartDTO.getUnit();
		//订单总重量
		BigDecimal totalWeight = productDTO.getProductWeight().multiply(BigDecimal.valueOf(totalUnit));
		List<ShoppingCartDTO> shoppingCartDTOList = new ArrayList<ShoppingCartDTO>();
		shoppingCartDTOList.add(shoppingCartDTO);
		//订单总金额
		BigDecimal totalAmount = calculateTotalAmount(shoppingCartDTOList);
		//获取该商品运费模板
		ProductFreightDTO freightDTO = productDTO.getProductFreightDTO();
		
		//检查快递区域是否在该运费模板特定区域以内
		checkFreightRegion(province, freightDTO);
		
		//满足包邮条件, 运费为0
		if(freightDTO.isShippingFree() && freightDTO.getShippingFreeConditionAmount().compareTo(totalAmount)<=0){
			return freightAmount;
		}
		int chargeType = freightDTO.getChargeType();
		//按件计费
		if(chargeType==1) {
			freightAmount = calculateFreightByUnit(freightDTO, totalUnit, true);
		}
		//按重量计费
		else if(chargeType==2) {
			freightAmount = calculateFreightByWeight(freightDTO, totalWeight, true);
		}
		return freightAmount;
	}
	
	private void checkFreightRegion(String province, ProductFreightDTO freightDTO) {
		try {
			AreaEntity areaEntity = areaDAO.getAreaByNameAndDepth(province,1);
			if(areaEntity != null) {
				String areaId = areaEntity.getAreaId();
				ProductFreightEntity freightEntity = productFreightDAO.getFreightByUuid(freightDTO.getProductFreightUuid());
				List<ProductFreightRegionEntity> regionList = productFreightRegionDAO.getRegionsByFreightAreaId(freightEntity, areaId);
				//如果找到多个特定区域, 使用第一个
				if(regionList.size()>0) {
					ProductFreightRegionEntity regionEntity = regionList.get(0);
					freightDTO.setDefaultFirstPrice(regionEntity.getFirstPrice());
					freightDTO.setDefaultFirstUnit(regionEntity.getFirstUnit());
					freightDTO.setDefaultFirstWeight(regionEntity.getFirstWeight());
					freightDTO.setDefaultNextPrice(regionEntity.getNextPrice());
					freightDTO.setDefaultNextUnit(regionEntity.getNextUnit());
					freightDTO.setDefaultNextWeight(regionEntity.getNextWeight());
				}
			}
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param freightDTO
	 * @param totalUnit
	 * @param isFirstPriceInvolved(计算时是否包含首件金额)
	 * @return
	 */
	private BigDecimal calculateFreightByUnit(ProductFreightDTO freightDTO, int totalUnit, boolean isFirstPriceInvolved) {
		
		int firstUnit = freightDTO.getDefaultFirstUnit();
		int nextUnit = freightDTO.getDefaultNextUnit();
		BigDecimal firstPrice = freightDTO.getDefaultFirstPrice();
		BigDecimal nextPrice = freightDTO.getDefaultNextPrice();
		
		BigDecimal freightAmount = BigDecimal.valueOf(0);
		int remainingUnit = 0;
		if(isFirstPriceInvolved) {
			freightAmount = freightAmount.add(firstPrice);
			remainingUnit = totalUnit - firstUnit;
		}else {
			remainingUnit = totalUnit;
		}
		
		//超过首件部分
		if(remainingUnit > 0) {
			int num = (int)Math.ceil(remainingUnit/nextUnit);
			freightAmount = freightAmount.add(nextPrice.multiply(BigDecimal.valueOf(num)));
		}
		return freightAmount;
		
	}

	/**
	 * @param freightDTO
	 * @param totalWeight
	 * @param isFirstPriceInvolved(计算时是否包含首重金额)
	 * @return
	 */
	private BigDecimal calculateFreightByWeight(ProductFreightDTO freightDTO, BigDecimal totalWeight, boolean isFirstPriceInvolved) {
		BigDecimal firstWeight = freightDTO.getDefaultFirstWeight();
		BigDecimal nextWeight = freightDTO.getDefaultNextWeight();
		BigDecimal firstPrice = freightDTO.getDefaultFirstPrice();
		BigDecimal nextPrice = freightDTO.getDefaultNextPrice();
		
		BigDecimal freightAmount = BigDecimal.valueOf(0);
		BigDecimal remainingWeight = BigDecimal.valueOf(0);
		if(isFirstPriceInvolved) {
			freightAmount = freightAmount.add(firstPrice);
			remainingWeight = totalWeight.subtract(firstWeight);
		}else {
			remainingWeight = totalWeight;
		}
		//超过首件部分
		if(remainingWeight.compareTo(BigDecimal.valueOf(0)) > 0) {
			BigDecimal num = remainingWeight.divide(nextWeight,0,BigDecimal.ROUND_UP);
			freightAmount = freightAmount.add(nextPrice.multiply(num));
		}
		return freightAmount;
		
	}
	
	private BigDecimal calculateSingleProductDelivery(ShoppingCartDTO shoppingCartDTO, BigDecimal totalAmount, BigDecimal latitude, BigDecimal longitude) throws BusinessException{
		
		BigDecimal deliveryAmount = BigDecimal.valueOf(0);
		ProductDTO productDTO = shoppingCartDTO.getProductDTO();
		//获取该商品同城配送模板
		ProductDeliveryDTO deliveryDTO = productDTO.getProductDeliveryDTO();
		BigDecimal fromLatitude = deliveryDTO.getDeliveryFromLatitude();
		BigDecimal fromLongitude = deliveryDTO.getDeliveryFromLongitude();
		int conditionDistance = deliveryDTO.getDeliveryConditionDistance(); //配送范围
		BigDecimal conditionAmount = deliveryDTO.getDeliveryConditionAmount();	//起送金额
		int firstDistance = deliveryDTO.getFirstDistance();//x公里内免费
		BigDecimal firstPrice = deliveryDTO.getFirstPrice();//起步价
		int nextDistance = deliveryDTO.getNextDistance();//每增加x公里, 增加x元
		BigDecimal nextPrice = deliveryDTO.getNextPrice();
		
		
		List<ShoppingCartDTO> shoppingCartDTOList = new ArrayList<ShoppingCartDTO>();
		shoppingCartDTOList.add(shoppingCartDTO);
		//配送距离
		String ak = settingService.getGlobalMapSetting().getAmapWebServiceKey();
		MapDTO mapDTO = MapUtil.getLineDistance(ak, fromLatitude.doubleValue(), fromLongitude.doubleValue(), latitude.doubleValue(), longitude.doubleValue());
		double distance = mapDTO.getDistance()/1000;//实际配送距离(千米)
		//超出配送范围
		if(distance>conditionDistance) {
			throw new BusinessException(BusinessErrorCode.DELIVERY_CONDITION_OVER_DISTANCE);
		}
		//未到起送金额
		if(totalAmount.compareTo(conditionAmount)<0) {
			throw new BusinessException(BusinessErrorCode.DELIVERY_CONDITION_LESS_AMOUNT);
		}
		
		//x公里内免费
		if(distance<=firstDistance){
			deliveryAmount = BigDecimal.valueOf(0);
		}else {
			deliveryAmount = deliveryAmount.add(firstPrice);	//起步价
			double remainingDistance = distance - firstDistance;
			//超过起步距离费用计算
			if(remainingDistance > 0) {
				int num = (int)Math.floor(remainingDistance/nextDistance);
				if(num>0) deliveryAmount = deliveryAmount.add(nextPrice.multiply(BigDecimal.valueOf(num)));
			}
			
		}
		return deliveryAmount;
	}
	
	private BigDecimal calculateMultipleProductDelivery(List<ShoppingCartDTO> shoppingCartDTOList, BigDecimal totalAmount, BigDecimal latitude, BigDecimal longitude) throws BusinessException{
		
		BigDecimal deliveryAmount = BigDecimal.valueOf(0);
		for (ShoppingCartDTO shoppingCartDTO : shoppingCartDTOList) {
			BigDecimal singleDeliveryAmount = calculateSingleProductDelivery(shoppingCartDTO, totalAmount, latitude, longitude);
			//取单种商品的最大配送费
			if(singleDeliveryAmount.compareTo(deliveryAmount)>0) {deliveryAmount = singleDeliveryAmount;}
		}
		return deliveryAmount;
	}

	private void validateCoupon(List<String> uuidList, CouponEntity couponEntity, BigDecimal conditionAmount,BigDecimal totalAmount,
			List<ShoppingCartDTO> shoppingCartDTOList) throws BusinessException {
		//检查优惠券是否适用于商品
		if(CouponConstants.BENEFIT_TYPE_PRODUCT.equals(couponEntity.getBenefitType())){
			List<CouponProductEntity> benefitProductList = couponEntity.getBenefitProductList();
			for (ShoppingCartDTO shoppingCartDTO : shoppingCartDTOList) {
				String productUuid = shoppingCartDTO.getProductDTO().getProductUuid();
				boolean isBenefitProduct = false;
				for (CouponProductEntity couponProductEntity : benefitProductList) {
					String tProductUuid = couponProductEntity.getProductEntity().getProductUuid();
					if(tProductUuid.equals(productUuid)) {
						isBenefitProduct = true;
						break;
					}
				}
				if(!isBenefitProduct) {
					throw new BusinessException(BusinessErrorCode.COUPON_PRODUCT_NOT_INVOLVED);
				}
			}
		}

		//检查是否达到满减条件
		if(conditionAmount.compareTo(BigDecimal.valueOf(0))!=0) {
			if(totalAmount.compareTo(conditionAmount)<0) {
				throw new BusinessException(BusinessErrorCode.COUPON_NOT_REACH_CONDITION_AMOUNT);
			}
		}
	}

	private BigDecimal calculateTotalAmount(List<ShoppingCartDTO> shoppingCartDTOList) {
		BigDecimal totalAmount = BigDecimal.valueOf(0);
		int totalUnit = 0;
		for (ShoppingCartDTO shoppingCartDTO : shoppingCartDTOList) {
			ProductDTO productDTO = shoppingCartDTO.getProductDTO();
			ProductSkuDTO productSkuDTO = shoppingCartDTO.getProductSkuDTO();
			totalUnit = totalUnit + shoppingCartDTO.getUnit();
			BigDecimal productAmount = BigDecimal.valueOf(0);
			if(productDTO.isSkuEnabled()) {
				productAmount = productSkuDTO.getSkuUnitPrice().multiply(BigDecimal.valueOf(shoppingCartDTO.getUnit()));
			}else {
				productAmount = productDTO.getUnitPrice().multiply(BigDecimal.valueOf(shoppingCartDTO.getUnit()));
			}
			totalAmount = totalAmount.add(productAmount);
		}
		return totalAmount;
	}
}
