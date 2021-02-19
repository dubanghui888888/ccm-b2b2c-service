
package com.mb.ext.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.ProductSearchDTO;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.ext.core.service.spec.product.ProductInventoryInboundDTO;
import com.mb.ext.core.service.spec.product.ProductInventoryOutboundDTO;
import com.mb.ext.core.service.spec.product.ProductInventoryTakingDTO;
import com.mb.framework.exception.BusinessException;

/**库存管理接口
 * @author B2B2C商城
 *
 * @param <T>
 */
@Transactional
public interface InventoryService<T extends BodyDTO>
{
	/**商品入库
	 * @param inboundDTO - 入库详情
	 * @throws BusinessException
	 */
	void addProductInventoryInbound(ProductInventoryInboundDTO inboundDTO) throws BusinessException;
	
	/**商品出库
	 * @param outboundDTO - 出库详情
	 * @throws BusinessException
	 */
	void addProductInventoryOutbound(ProductInventoryOutboundDTO outboundDTO) throws BusinessException;
	
	/**商品盘点
	 * @param takingDTO - 盘点详情
	 * @throws BusinessException
	 */
	void addProductInventoryTaking(ProductInventoryTakingDTO takingDTO) throws BusinessException;
	
	
	/**根据主键查询入库单
	 * @param uuid 主键ID
	 * @return
	 * @throws BusinessException
	 */
	ProductInventoryInboundDTO getProducctInventoryInboundByUuid(String uuid) throws BusinessException;
	
	/**根据主键查询出库单
	 * @param uuid 主键ID
	 * @return
	 * @throws BusinessException
	 */
	ProductInventoryOutboundDTO getProducctInventoryOutboundByUuid(String uuid) throws BusinessException;
	
	/**根据主键查询盘点单
	 * @param uuid 主键ID
	 * @return
	 * @throws BusinessException
	 */
	ProductInventoryTakingDTO getProducctInventoryTakingByUuid(String uuid) throws BusinessException;
	
	/**分页查询入库单
	 * @param productSearchDTO 查询条件
	 * @return
	 * @throws BusinessException
	 */
	List<ProductInventoryInboundDTO> searchProducctInventoryInbound(ProductSearchDTO productSearchDTO) throws BusinessException;
	
	/**查询入库单数量
	 * @param productSearchDTO 查询条件
	 * @return
	 * @throws BusinessException
	 */
	int searchProducctInventoryInboundTotal(ProductSearchDTO productSearchDTO) throws BusinessException;
	
	/**分页查询出库单
	 * @param productSearchDTO 查询条件
	 * @return
	 * @throws BusinessException
	 */
	List<ProductInventoryOutboundDTO> searchProducctInventoryOutbound(ProductSearchDTO productSearchDTO) throws BusinessException;
	
	/**查询出库单数量
	 * @param productSearchDTO 查询条件
	 * @return
	 * @throws BusinessException
	 */
	int searchProducctInventoryOutboundTotal(ProductSearchDTO productSearchDTO) throws BusinessException;
	
	/**分页查询盘点单
	 * @param productSearchDTO 查询条件
	 * @return
	 * @throws BusinessException
	 */
	List<ProductInventoryTakingDTO> searchProducctInventoryTaking(ProductSearchDTO productSearchDTO) throws BusinessException;
	
	/**查询盘点单数量
	 * @param productSearchDTO 查询条件
	 * @return
	 * @throws BusinessException
	 */
	int searchProducctInventoryTakingTotal(ProductSearchDTO productSearchDTO) throws BusinessException;
	
	/**分页搜索商品
	 * @param searchDTO - 查询条件
	 * @return 商品列表
	 * @throws BusinessException
	 */
	List<ProductDTO> searchProduct(ProductSearchDTO searchDTO) throws BusinessException;
	
	/**根据条件查询商品数量
	 * @param searchDTO - 查询条件
	 * @return 商品数量
	 * @throws BusinessException
	 */
	int searchProductTotal(ProductSearchDTO searchDTO) throws BusinessException;
}
