
package com.mb.ext.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.coupon.UserVoucherDTO;
import com.mb.ext.core.service.spec.coupon.VoucherSearchDTO;
import com.mb.ext.core.service.spec.order.OrderDTO;
import com.mb.framework.exception.BusinessException;

/** 电子卡券接口
 * @author B2B2C商城
 *
 * @param <T>
 */
@Transactional
public interface VoucherService<T extends BodyDTO>
{
	/**删除电子卡券
	 * @param userVoucherUuid
	 * @return
	 * @throws BusinessException
	 */
	void deleteVoucher(String userVoucherUuid) throws BusinessException;

	/**根据电子卡券ID查详情
	 * @param userVoucherUuid
	 * @return
	 * @throws BusinessException
	 */
	UserVoucherDTO getUserVoucherByUuid(String userVoucherUuid) throws BusinessException;
	
	/**根据电子卡券码查详情
	 * @param voucherCode
	 * @return
	 * @throws BusinessException
	 */
	UserVoucherDTO getUserVoucherByCode(String voucherCode) throws BusinessException;
	
	/**通过订单查询电子卡券
	 * @param voucherSearchDTO
	 * @return
	 * @throws BusinessException
	 */
	List<UserVoucherDTO> getVoucherByOrder(OrderDTO orderDTO) throws BusinessException;
	
	/**通过查询条件搜索电子卡券
	 * @param couponSearchDTO
	 * @return
	 * @throws BusinessException
	 */
	List<UserVoucherDTO> searchUserVoucher(VoucherSearchDTO couponSearchDTO) throws BusinessException;
	
	/**通过查询条件搜索已领取的电子卡券总数
	 * @param couponSearchDTO
	 * @return
	 * @throws BusinessException
	 */
	int searchUserVoucherTotal(VoucherSearchDTO couponSearchDTO) throws BusinessException;
	
	/**核销电子卡券
	 * @param userVoucherDTO
	 * @throws BusinessException
	 */
	void writeOffVoucher(UserVoucherDTO userVoucherDTO) throws BusinessException;
	
	/**延长电子卡券有效期
	 * @param userVoucherDTO
	 * @throws BusinessException
	 */
	void extendVoucher(UserVoucherDTO userVoucherDTO) throws BusinessException;
	
	/**生成电子卡券核销二维码
	 * @param userVoucherUuid
	 * @return
	 * @throws BusinessException
	 */
	String generateVoucherBarCode(String voucherCode) throws BusinessException;
	
}
