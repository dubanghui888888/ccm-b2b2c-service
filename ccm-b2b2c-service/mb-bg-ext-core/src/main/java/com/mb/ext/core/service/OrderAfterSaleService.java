
package com.mb.ext.core.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.OrderAfterSaleSearchDTO;
import com.mb.ext.core.service.spec.order.OrderAfterSaleDTO;
import com.mb.framework.exception.BusinessException;

/**售后单服务
 * @author B2B2C商城
 *
 * @param <T>
 */
@Transactional
public interface OrderAfterSaleService<T extends BodyDTO>
{

	/**申请售后
	 * @param afterSaleDTO
	 * @throws BusinessException
	 */
	void applyOrderAfterSale(OrderAfterSaleDTO afterSaleDTO) throws BusinessException;
	/**撤销售后申请
	 * @param afterSaleDTO
	 * @throws BusinessException
	 */
	void cancelOrderAfterSale(OrderAfterSaleDTO afterSaleDTO) throws BusinessException;
	/**更新售后内容
	 * @param afterSaleDTO
	 * @throws BusinessException
	 */
	void updateOrderAfterSale(OrderAfterSaleDTO afterSaleDTO) throws BusinessException;
	/**买家退款发货
	 * @param afterSaleDTO
	 * @throws BusinessException
	 */
	void courierOrderAfterSale(OrderAfterSaleDTO afterSaleDTO) throws BusinessException;
	/**申请售后审核通过
	 * @param afterSaleDTO
	 * @throws BusinessException
	 */
	void approveOrderAfterSale(OrderAfterSaleDTO afterSaleDTO) throws BusinessException;
	/**申请售后审核拒绝
	 * @param afterSaleDTO
	 * @throws BusinessException
	 */
	void rejectOrderAfterSale(OrderAfterSaleDTO afterSaleDTO) throws BusinessException;
	/**确认已收到退款货物
	 * @param afterSaleDTO
	 * @throws BusinessException
	 */
	void confirmOrderAfterSale(OrderAfterSaleDTO afterSaleDTO) throws BusinessException;
	/**根据售后单号查询售后单
	 * @param afterSaleDTO
	 * @throws BusinessException
	 */
	OrderAfterSaleDTO getOrderAfterSaleByAfterSaleNo(String afterSaleNo) throws BusinessException;
	/**根据订单号查询售后单
	 * @param afterSaleDTO
	 * @throws BusinessException
	 */
	OrderAfterSaleDTO getOrderAfterSaleByOrderNo(String orderNo) throws BusinessException;
	/**分页查询售后单
	 * @param afterSaleDTO
	 * @throws BusinessException
	 */
	List<OrderAfterSaleDTO> searchOrderAfterSale(OrderAfterSaleSearchDTO searchDTO) throws BusinessException;
	/**查询售后单总数量
	 * @param afterSaleDTO
	 * @throws BusinessException
	 */
	int searchOrderAfterSaleTotal(OrderAfterSaleSearchDTO searchDTO) throws BusinessException;
	/**查询售后单总金额
	 * @param afterSaleDTO
	 * @throws BusinessException
	 */
	BigDecimal searchOrderAfterSaleAmount(OrderAfterSaleSearchDTO searchDTO) throws BusinessException;
	
	/**微信支付订单退款
	 * @param afterSaleNo
	 * @throws BusinessException
	 */
	void orderAfterSaleRefund(String afterSaleNo) throws BusinessException;
	
	/**微信支付订单退款
	 * @param afterSaleNo
	 * @throws BusinessException
	 */
	void wechatRefund(String afterSaleNo) throws BusinessException;
	
	/**支付宝支付订单退款
	 * @param afterSaleNo
	 * @throws BusinessException
	 */
	void alipayRefund(String afterSaleNo) throws BusinessException;
	
	/**余额支付订单退款
	 * @param afterSaleNo
	 * @throws BusinessException
	 */
	void balanceRefund(String afterSaleNo) throws BusinessException;
	
	/**更新微信退款通知状态
	 * @param afterSaleNo
	 * @param refundStatus
	 * @throws BusinessException
	 */
	void updateRefundStatus(String afterSaleNo, String refundStatus) throws BusinessException;
}
