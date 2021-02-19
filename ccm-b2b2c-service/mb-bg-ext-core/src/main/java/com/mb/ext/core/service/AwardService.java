
package com.mb.ext.core.service;

import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.framework.exception.BusinessException;

/** 会员奖金接口
 * @author B2B2C商城
 *
 * @param <T>
 */
@Transactional
public interface AwardService<T extends BodyDTO>
{

	/**处理指定订单的涉及的奖金
	 * @param orderNo - 订单号
	 * @throws BusinessException
	 */
	void processAward(String orderNo) throws BusinessException;
	
	/**处理指定订单的涉及的分销分佣
	 * @param orderNo - 订单号
	 * @throws BusinessException
	 */
	void processDistribution(String orderNo) throws BusinessException;
	
	/**处理会员升级, 检查会员是否满足升级条件, 如果满足做升级操作
	 * @param userDTO - 会员
	 * @throws BusinessException
	 */
	void processUpgrade(UserDTO userDTO) throws BusinessException;
}
