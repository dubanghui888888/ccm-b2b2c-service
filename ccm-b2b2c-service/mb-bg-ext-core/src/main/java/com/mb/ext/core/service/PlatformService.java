
package com.mb.ext.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.merchant.PlatformAccountDTO;
import com.mb.framework.exception.BusinessException;

/**平台账号接口
 * @author B2B2C商城
 *
 * @param <T>
 */
@Transactional
public interface PlatformService<T extends BodyDTO>
{
	/**添加平台银行账号
	 * @param platformAccountDTO - 平台账号
	 * @throws BusinessException
	 */
	void addPlatformAccount(PlatformAccountDTO platformAccountDTO) throws BusinessException;
	
	/**更新平台银行账号
	 * @param platformAccountDTO - 平台账号
	 * @throws BusinessException
	 */
	void updatePlatformAccount(PlatformAccountDTO platformAccountDTO) throws BusinessException;
	
	/**删除平台银行账号
	 * @param platformAccountDTO - 平台账号
	 * @throws BusinessException
	 */
	void deletePlatformAccount(PlatformAccountDTO platformAccountDTO) throws BusinessException;
	
	/** 查询平台银行账号列表
	 * @return 银行账号列表
	 * @throws BusinessException
	 */
	List<PlatformAccountDTO> inquiryPlatformAccount() throws BusinessException;
	
	/**根据id查询平台银行账号
	 * @param uid - 账号id
	 * @return
	 * @throws BusinessException
	 */
	PlatformAccountDTO inquiryPlatformAccountByUid(String uid) throws BusinessException;
	
}
