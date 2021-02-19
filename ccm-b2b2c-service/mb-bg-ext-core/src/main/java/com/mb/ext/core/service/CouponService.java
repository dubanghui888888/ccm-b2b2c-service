
package com.mb.ext.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.coupon.CouponDTO;
import com.mb.ext.core.service.spec.coupon.CouponSearchDTO;
import com.mb.ext.core.service.spec.coupon.UserCouponDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.framework.exception.BusinessException;

/** 优惠券接口
 * @author B2B2C商城
 *
 * @param <T>
 */
@Transactional
public interface CouponService<T extends BodyDTO>
{

	/** 添加优惠券
	 * @param couponDTO
	 * @throws BusinessException
	 */
	void addCoupon(CouponDTO couponDTO) throws BusinessException;
	
	/**更新优惠券
	 * @param couponDTO
	 * @throws BusinessException
	 */
	void updateCoupon(CouponDTO couponDTO) throws BusinessException;
	
	/**删除优惠券
	 * @param couponDTO
	 * @throws BusinessException
	 */
	void deleteCoupon(CouponDTO couponDTO) throws BusinessException;
	
	/**发放优惠券
	 * @param couponUuid
	 * @throws BusinessException
	 */
	void enableCoupon(String couponUuid) throws BusinessException;
	
	/**根据优惠券ID查详情
	 * @param couponUuid
	 * @return
	 * @throws BusinessException
	 */
	CouponDTO getCoupon(String couponUuid) throws BusinessException;
	
	/**查询会员领取的优惠券
	 * @param userCouponUuid
	 * @return
	 * @throws BusinessException
	 */
	UserCouponDTO getUserCouponByUuid(String userCouponUuid) throws BusinessException;
	
	/**查询所有优惠券
	 * @return
	 * @throws BusinessException
	 */
	List<CouponDTO> getCoupons() throws BusinessException;

	/**查询有效优惠券
	 * @return
	 * @throws BusinessException
	 */
	List<CouponDTO> getActiveCoupons() throws BusinessException;
	
	/**查询某个商家适用的所有优惠券
	 * @param merchantDTO
	 * @return
	 * @throws BusinessException
	 */
	List<CouponDTO> getCouponByMerchant(MerchantDTO merchantDTO) throws BusinessException;
	
	/**查询某种优惠券的领取详情
	 * @param couponDTO
	 * @return
	 * @throws BusinessException
	 */
	List<UserCouponDTO> getUserCouponByCoupon(CouponDTO couponDTO) throws BusinessException;
	
	/**通过查询条件搜索已领取的优惠券
	 * @param couponSearchDTO
	 * @return
	 * @throws BusinessException
	 */
	List<UserCouponDTO> searchUserCoupon(CouponSearchDTO couponSearchDTO) throws BusinessException;
	
	/**通过查询条件搜索已领取的优惠券总数
	 * @param couponSearchDTO
	 * @return
	 * @throws BusinessException
	 */
	int searchUserCouponTotal(CouponSearchDTO couponSearchDTO) throws BusinessException;
	
	/**查询某个商家下的会员适用的可用优惠券
	 * @param userDTO
	 * @param merchantDTO
	 * @return
	 * @throws BusinessException
	 */
	List<UserCouponDTO> getCouponByUserMerchant(UserDTO userDTO,MerchantDTO merchantDTO) throws BusinessException;
	/**查询某个商家下的会员适用的可用优惠券
	 * @param userDTO
	 * @param merchantDTO
	 * @return
	 * @throws BusinessException
	 */
	List<UserCouponDTO> getValidCouponByUserMerchant(UserDTO userDTO,MerchantDTO merchantDTO) throws BusinessException;
	
	/**查询会员所有优惠券
	 * @param userDTO
	 * @return
	 * @throws BusinessException
	 */
	public List<UserCouponDTO> getUserCoupon(UserDTO userDTO) throws BusinessException;
	
	/**将优惠券设置为不可领取状态
	 * @param couponUuid
	 * @throws BusinessException
	 */
	void disableCoupon(String couponUuid) throws BusinessException;
	
	/**领取优惠券
	 * @param couponUuid
	 * @param userDTO
	 * @param channel
	 * @throws BusinessException
	 */
	void receiveCoupon(String couponUuid, UserDTO userDTO, String channel) throws BusinessException;
	
	/**使用优惠券
	 * @param userCouponDTO
	 * @throws BusinessException
	 */
	void useCoupon(UserCouponDTO userCouponDTO) throws BusinessException;
	
	/**生成优惠券核销二维码
	 * @param userCouponUuid
	 * @return
	 * @throws BusinessException
	 */
	String generateCouponBarCode(String userCouponUuid) throws BusinessException;
	
	/**搜索查询优惠券
	 * @param couponSearchDTO
	 * @return
	 * @throws BusinessException
	 */
	List<CouponDTO> searchCoupon(CouponSearchDTO couponSearchDTO) throws BusinessException;
	
	/**搜索查询优惠券记录数量
	 * @param couponSearchDTO
	 * @return
	 * @throws BusinessException
	 */
	int searchCouponTotal(CouponSearchDTO couponSearchDTO) throws BusinessException;
	
}
