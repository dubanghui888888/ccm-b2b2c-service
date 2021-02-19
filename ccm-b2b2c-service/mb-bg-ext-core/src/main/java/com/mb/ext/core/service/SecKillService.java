
package com.mb.ext.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.ProductSearchDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.order.SecKillOrderDTO;
import com.mb.ext.core.service.spec.seckill.SecKillProductDTO;
import com.mb.framework.exception.BusinessException;

/**秒杀接口
 * @author B2B2C商城
 *
 * @param <T>
 */
@Transactional
public interface SecKillService<T extends BodyDTO>
{

	/** 添加秒殺商品
	 * @param secKillProductDTO
	 * @throws BusinessException
	 */
	void addSecKill(SecKillProductDTO secKillProductDTO) throws BusinessException;
	
	/**更新秒殺商品
	 * @param secKillProductDTO
	 * @throws BusinessException
	 */
	void updateSecKill(SecKillProductDTO secKillProductDTO) throws BusinessException;
	
	/**删除秒殺商品
	 * @param couponDTO
	 * @throws BusinessException
	 */
	void deleteSecKill(SecKillProductDTO secKillProductDTO) throws BusinessException;
	
	/**上线秒杀商品
	 * @param secKillProductDTO
	 * @throws BusinessException
	 */
	void enableSecKill(SecKillProductDTO secKillProductDTO) throws BusinessException;
	
	/**下线秒杀商品
	 * @param secKillProductDTO
	 * @throws BusinessException
	 */
	void disableSecKill(SecKillProductDTO secKillProductDTO) throws BusinessException;
	
	/**根据秒杀ID查询秒杀详情
	 * @param couponUuid
	 * @return
	 * @throws BusinessException
	 */
	SecKillProductDTO getSecKill(String uuid) throws BusinessException;
	
	/**查询所有秒杀商品
	 * @param 
	 * @return
	 * @throws BusinessException
	 */
	List<SecKillProductDTO> getSecKills() throws BusinessException;
	
	/**查询单个商家秒杀商品
	 * @param 
	 * @return
	 * @throws BusinessException
	 */
	List<SecKillProductDTO> getSecKillsByMerchant(MerchantDTO merchantDTO) throws BusinessException;
	
	/**查询已上线的秒杀商品
	 * @param 
	 * @return
	 * @throws BusinessException
	 */
	List<SecKillProductDTO> getActiveSecKills() throws BusinessException;
	
	/**获取秒杀资格ID
	 * @param orderDTO
	 * @return
	 * @throws BusinessException
	 */
	String getSecKillQuanId(SecKillOrderDTO secKillOrderDTO) throws BusinessException;
	
	/**分页查询秒杀商品
	 * @param productSearchDTO 查询条件
	 * @return
	 * @throws BusinessException
	 */
	List<SecKillProductDTO> searchSecKill(ProductSearchDTO productSearchDTO) throws BusinessException;
	
	/**分页秒杀商品数量
	 * @param productSearchDTO 查询条件
	 * @return
	 * @throws BusinessException
	 */
	int searchSecKillTotal(ProductSearchDTO productSearchDTO) throws BusinessException;
	
}
