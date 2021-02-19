
package com.mb.ext.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.MerchantSearchDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.UserDeliveryDTO;
import com.mb.ext.core.service.spec.UserDeliverySearchDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.order.UserDeliveryAddressDTO;
import com.mb.ext.core.service.spec.product.ProductDeliveryDTO;
import com.mb.ext.core.service.spec.product.ProductFreightDTO;
import com.mb.framework.exception.BusinessException;

/**收货发货接口
 * @author B2B2C商城
 *
 * @param <T>
 */
@Transactional
public interface DeliveryService<T extends BodyDTO>
{
	/**添加会员收货地址
	 * @param addressDTO - 会员收货地址
	 * @throws BusinessException
	 */
	void addUserDeliveryAddress(UserDeliveryAddressDTO addressDTO) throws BusinessException;
	
	/** 更新会员收货地址
	 * @param addressDTO - 会员收货地址
	 * @throws BusinessException
	 */
	void updateUserDeliveryAddress(UserDeliveryAddressDTO addressDTO) throws BusinessException;
	
	/**删除会员收货地址
	 * @param addressDTO - 会员收货地址
	 * @throws BusinessException
	 */
	void deleteUserDeliveryAddress(UserDeliveryAddressDTO addressDTO) throws BusinessException;
	
	/**设置会员默认收货地址
	 * @param addressDTO - 会员收货地址
	 * @throws BusinessException
	 */
	void setDefaultUserDeliveryAddress(UserDeliveryAddressDTO addressDTO) throws BusinessException;
	
	/**查询会员收货地址列表
	 * @param userDTO - 会员
	 * @return
	 * @throws BusinessException
	 */
	List<UserDeliveryAddressDTO> inquiryUserDeliveryAddress(UserDTO userDTO) throws BusinessException;
	
	/**根据id查询收货地址
	 * @param uuid - 收货地址id
	 * @return
	 * @throws BusinessException
	 */
	UserDeliveryAddressDTO inquiryUserDeliveryAddressByUuid(String uuid) throws BusinessException;
	
	/** 备注会员提货
	 * @param userDeliveryDTO - 会员提货请求
	 * @throws BusinessException
	 */
	void commentUserDelivery(UserDeliveryDTO userDeliveryDTO) throws BusinessException;
	
	/**更新物流单号
	 * @param userDeliveryDTO - 会员提货请求
	 * @throws BusinessException
	 */
	void updateUserDeliveryCourier(UserDeliveryDTO userDeliveryDTO) throws BusinessException;
	
	/**标记发货
	 * @param userDeliveryDTO - 会员提货请求
	 * @throws BusinessException
	 */
	void sendUserDelivery(UserDeliveryDTO userDeliveryDTO)
			throws BusinessException;
	
	/** 查询客户提货记录
	 * @param userDTO - 会员
	 * @return
	 * @throws BusinessException
	 */
	List<UserDeliveryDTO> getDeliveryByUser(UserDTO userDTO) throws BusinessException;
	
	/**查询某个驿站所有客户提货记录
	 * @param merchantDTO - 驿站
	 * @return
	 * @throws BusinessException
	 */
	List<UserDeliveryDTO> getDeliveryByMerchant(MerchantDTO merchantDTO) throws BusinessException;
	
	/**根据id查询提货记录
	 * @param uuid - 提货记录id
	 * @return
	 * @throws BusinessException
	 */
	UserDeliveryDTO getDeliveryByUuid(String uuid) throws BusinessException;
	
	/** 根据条件搜索提货记录
	 * @param userDeliverySearchDTO - 查询条件
	 * @param startIndex
	 * @param pageSize
	 * @return
	 * @throws BusinessException
	 */
	List<UserDeliveryDTO> searchUserDelivery(UserDeliverySearchDTO userDeliverySearchDTO, int startIndex, int pageSize) throws BusinessException;
	
	/**根据条件搜索提货记录总数
	 * @param userDeliverySearchDTO - 查询条件
	 * @return
	 * @throws BusinessException
	 */
	int searchUserDeliveryTotal(UserDeliverySearchDTO userDeliverySearchDTO) throws BusinessException;
	
	/**添加同城配送模板
	 * @param deliveryDTO - 同城配送模板
	 * @throws BusinessException
	 */
	void addProductDelivery(ProductDeliveryDTO deliveryDTO) throws BusinessException;
	
	/**修改同城配送模板
	 * @param deliveryDTO - 同城配送模板
	 * @throws BusinessException
	 */
	void updateProductDelivery(ProductDeliveryDTO deliveryDTO) throws BusinessException;
	
	/**删除同城配送模板
	 * @param deliveryDTO - 同城配送模板
	 * @throws BusinessException
	 */
	void deleteProductDelivery(ProductDeliveryDTO deliveryDTO) throws BusinessException;
	
	/**启用同城配送模板
	 * @param deliveryDTO - 同城配送模板
	 * @throws BusinessException
	 */
	void enableProductDelivery(ProductDeliveryDTO deliveryDTO) throws BusinessException;
	
	/**停用同城配送模板
	 * @param deliveryDTO - 同城配送模板
	 * @throws BusinessException
	 */
	void disableProductDelivery(ProductDeliveryDTO deliveryDTO) throws BusinessException;
	
	/**默认同城配送模板
	 * @param deliveryDTO - 同城配送模板
	 * @throws BusinessException
	 */
	void defaultProductDelivery(ProductDeliveryDTO deliveryDTO) throws BusinessException;
	
	/**取消默认同城配送模板
	 * @param deliveryDTO - 同城配送模板
	 * @throws BusinessException
	 */
	void cancelDefaultProductDelivery(ProductDeliveryDTO deliveryDTO) throws BusinessException;
	
	/**查询所有同城配送模板
	 * @return 同城配送模板列表
	 * @throws BusinessException
	 */
	List<ProductDeliveryDTO> getProductDeliveries() throws BusinessException;
	
	/**查询商家同城配送模板
	 * @param merchantDTO - 商家
	 * @return 同城配送模板列表
	 * @throws BusinessException
	 */
	List<ProductDeliveryDTO> getProductDeliverysByMerchant(MerchantDTO merchantDTO) throws BusinessException;

	/**分页查询同城配送模板
	 * @param merchantSearchDTO - 查询条件
	 * @return 同城配送模板列表
	 * @throws BusinessException
	 */
	List<ProductDeliveryDTO> searchProductDelivery(MerchantSearchDTO merchantSearchDTO) throws BusinessException;

	/**查询同城配送模板数量
	 * @param merchantSearchDTO - 查询条件
	 * @return 同城配送模板数量
	 * @throws BusinessException
	 */
	int searchProductDeliveryTotal(MerchantSearchDTO merchantSearchDTO) throws BusinessException;
	
	/**根据id查询同城配送模板
	 * @param uuid - 同城配送模板Id
	 * @return
	 * @throws BusinessException
	 */
	ProductDeliveryDTO getProductDeliveryByUuid(String uuid) throws BusinessException;
}
