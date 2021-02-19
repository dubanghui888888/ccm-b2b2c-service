
package com.mb.ext.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.GroupBuySearchDTO;
import com.mb.ext.core.service.spec.ProductSearchDTO;
import com.mb.ext.core.service.spec.group.GroupBuyDTO;
import com.mb.ext.core.service.spec.group.GroupBuyDefDTO;
import com.mb.ext.core.service.spec.group.GroupBuyProductDTO;
import com.mb.ext.core.service.spec.group.GroupBuyUserDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.framework.exception.BusinessException;

/**团购接口
 * @author B2B2C商城
 *
 * @param <T>
 */
@Transactional
public interface GroupBuyService<T extends BodyDTO>
{

	/** 添加团购活动
	 * @param groupBuyDefDTO
	 * @throws BusinessException
	 */
	void addGroupBuyDef(GroupBuyDefDTO groupBuyDefDTO) throws BusinessException;
	
	/**更新团购活动
	 * @param groupBuyDefDTO
	 * @throws BusinessException
	 */
	void updateGroupBuyDef(GroupBuyDefDTO groupBuyDefDTO) throws BusinessException;
	
	/**删除团购活动
	 * @param groupBuyDefDTO
	 * @throws BusinessException
	 */
	void deleteGroupBuyDef(GroupBuyDefDTO groupBuyDefDTO) throws BusinessException;
	
	/**上线团购商品
	 * @param groupBuyDefDTO
	 * @throws BusinessException
	 */
	void enableGroupBuyDef(GroupBuyDefDTO groupBuyDefDTO) throws BusinessException;
	
	/**下线团购活动
	 * @param groupBuyDefDTO
	 * @throws BusinessException
	 */
	void disableGroupBuyDef(GroupBuyDefDTO groupBuyDefDTO) throws BusinessException;
	
	/**根据团购ID查询团购详情
	 * @param uuid
	 * @return
	 * @throws BusinessException
	 */
	GroupBuyDefDTO getGroupBuyDef(String uuid) throws BusinessException;
	
	/**查询所有团购活动
	 * @param 
	 * @returns
	 * @throws BusinessException
	 */
	List<GroupBuyDefDTO> getGroupBuyDefs() throws BusinessException;
	
	/**查询已上线的团购活动
	 * @param 
	 * @return
	 * @throws BusinessException
	 */
	List<GroupBuyDefDTO> getActiveGroupBuyDefs() throws BusinessException;
	
	/**分页查询团购活动
	 * @param 
	 * @return
	 * @throws BusinessException
	 */
	List<GroupBuyDefDTO> searchGroupBuyDef(ProductSearchDTO productSearchDTO) throws BusinessException;
	
	/**查询团购活动数量
	 * @param 
	 * @return
	 * @throws BusinessException
	 */
	int searchGroupBuyDefTotal(ProductSearchDTO productSearchDTO) throws BusinessException;
	
	/**查询团购商品详情
	 * @param uuid
	 * @return
	 * @throws BusinessException
	 */
	GroupBuyProductDTO getGroupBuyProduct(String uuid) throws BusinessException;
	
	/**查询某个商家发布的团购活动
	 * @param 
	 * @returns
	 * @throws BusinessException
	 */
	List<GroupBuyDefDTO> getGroupBuyDefsByMerchant(MerchantDTO merchantDTO) throws BusinessException;
	

	
	/** 发起拼团
	 * @param groupUserDTO
	 * @throws BusinessException
	 */
	String addGroupBuy(GroupBuyUserDTO groupUserDTO) throws BusinessException;
	
	/** 参与拼团
	 * @param groupUserDTO
	 * @throws BusinessException
	 */
	String joinGroupBuy(GroupBuyUserDTO groupUserDTO) throws BusinessException;
	
	/**根据团购ID查询团购详情
	 * @param uuid
	 * @return
	 * @throws BusinessException
	 */
	GroupBuyProductDTO getGroupBuy(String uuid) throws BusinessException;
	
	/**搜索拼团单
	 * @param uuid
	 * @return
	 * @throws BusinessException
	 */
	GroupBuyDTO groupBuyDetail(String uuid) throws BusinessException;
	
	/**搜索拼团单
	 * @param searchDTO
	 * @return
	 * @throws BusinessException
	 */
	List<GroupBuyDTO> searchGroupBuy(GroupBuySearchDTO searchDTO) throws BusinessException;
	
	/**搜索拼团单数量
	 * @param searchDTO
	 * @return
	 * @throws BusinessException
	 */
	int searchGroupBuyTotal(GroupBuySearchDTO searchDTO) throws BusinessException;
	
	/**查询正在拼团的商品
	 * @param 
	 * @return
	 * @throws BusinessException
	 */
	List<GroupBuyProductDTO> getBeingGroupBuyProducts() throws BusinessException;
	
	/**查询即将拼团的商品
	 * @param 
	 * @return
	 * @throws BusinessException
	 */
	List<GroupBuyProductDTO> getComingGroupBuyProducts() throws BusinessException;

	/**根据拼团商品查询正在进行中的拼团
	 * @param groupBuyProductUuid
	 * @return
	 * @throws BusinessException
	 */
	List<GroupBuyDTO> getActiveGroupBuysByGroupBuyProduct(String groupBuyProductUuid) throws BusinessException;
}
