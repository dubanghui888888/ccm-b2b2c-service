
package com.mb.ext.core.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.ShoppingCartDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.point.PointProductDTO;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.ext.core.service.spec.product.ProductSkuDTO;
import com.mb.framework.exception.BusinessException;

/**购物车接口
 * @author B2B2C商城
 *
 * @param <T>
 */
@Transactional
public interface ShoppingCartService<T extends BodyDTO>
{

	/**获取会员购物车商品数量
	 * @param userDTO - 会员
	 * @return - 商品数量
	 * @throws BusinessException
	 */
	int getCartNum(UserDTO userDTO) throws BusinessException;
	
	/**获取会员购物车商品详情
	 * @param uuidList - 购物车商品记录id列表
	 * @return 购物车商品详情
	 * @throws BusinessException
	 */
	List<ShoppingCartDTO> getShoppingCartByUuids(List<String> uuidList) throws BusinessException;
	
	/**获取会员购物车商品
	 * @param userDTO - 会员
	 * @return 购物车商品详情
	 * @throws BusinessException
	 */
	List<ShoppingCartDTO> getProducts(UserDTO userDTO) throws BusinessException;
	
	/**添加商品到购物车
	 * @param productDTO - 商品
	 * @param userDTO - 会员
	 * @param unit - 商品数量
	 * @return 购物车记录Id
	 * @throws BusinessException
	 */
	String addProductToShoppingCart(ProductDTO productDTO, UserDTO userDTO, int unit) throws BusinessException;
	
	/**添加多规格商品到购物车
	 * @param productSkuDTO - 商品规格
	 * @param userDTO - 会员
	 * @param unit - 商品数量
	 * @return 购物车记录Id
	 * @throws BusinessException
	 */
	String addProductSkuToShoppingCart(ProductSkuDTO productSkuDTO, UserDTO userDTO, int unit) throws BusinessException;
	
	/** 从购物车移除商品
	 * @param productDTO - 商品
	 * @param userDTO - 会员
	 * @throws BusinessException
	 */
	void removeProductFromShoppingCart(ProductDTO productDTO, UserDTO userDTO) throws BusinessException;
	
	/** 从购物车移除多规格商品
	 * @param productSkuDTO - 规格商品
	 * @param userDTO - 会员
	 * @throws BusinessException
	 */
	void removeProductSkuFromShoppingCart(ProductSkuDTO productSkuDTO, UserDTO userDTO) throws BusinessException;
	
	/**设置购物车某个商品的数量
	 * @param shoppingCartDTO - 购物车记录
	 * @throws BusinessException
	 */
	void updateShoppingCartProductNum(ShoppingCartDTO shoppingCartDTO) throws BusinessException;
	
	/** 将某个商品记录从购物车移除
	 * @param shoppingCartDTO - 购物车记录
	 * @throws BusinessException
	 */
	void deleteProductFromShoppingCart(ShoppingCartDTO shoppingCartDTO) throws BusinessException;
	
	/**将所有商品从购物车移除
	 * @param userDTO - 会员
	 * @throws BusinessException
	 */
	void clearShoppingCart(UserDTO userDTO) throws BusinessException;
	
	/**计算订单中优惠券所减金额
	 * @param uuidList (购物车中的商品item)
	 * @param couponUuid(优惠券ID)
	 * @return
	 * @throws BusinessException
	 */
	BigDecimal calculateOrderCouponAmount(List<String> uuidList, String couponUuid) throws BusinessException;
	
	/**计算订单运费
	 * @param uuidList (购物车中的商品item)
	 * @return
	 * @throws BusinessException
	 */
	BigDecimal calculateOrderFreightAmount(List<String> uuidList, String province, String city, String area) throws BusinessException;
	
	/**计算订单配送费
	 * @param uuidList (购物车中的商品item)
	 * @return
	 * @throws BusinessException
	 */
	BigDecimal calculateOrderDeliveryAmount(List<String> uuidList, BigDecimal latitude, BigDecimal longitude) throws BusinessException;
	
	/**根据商品,规格, 实际单价, 收货地址计算运费(只有一种商品)
	 * @param 
	 * @return
	 * @throws BusinessException
	 */
	BigDecimal calculateProductFreightAmount(ProductDTO productDTO, BigDecimal unitPrice, int unit, String province, String city, String area) throws BusinessException;
	
	/**根据商品,规格, 实际单价, 收货地址计算运费(只有一种商品)
	 * @param 
	 * @return
	 * @throws BusinessException
	 */
	public BigDecimal calculateProductDeliveryAmount(ProductDTO productDTO, BigDecimal unitPrice, int totalUnit, BigDecimal latitude, BigDecimal longitude) throws BusinessException;
	
	/**根据积分商品,规格, 实际单价, 收货地址计算同城配送费(只有一种商品)
	 * @param 
	 * @return
	 * @throws BusinessException
	 */
	BigDecimal calculatePointProductFreightAmount(PointProductDTO pointProductDTO, BigDecimal unitPrice, int unit, String province, String city, String area) throws BusinessException;
	
}
