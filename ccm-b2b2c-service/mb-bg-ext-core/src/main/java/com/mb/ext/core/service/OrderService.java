
package com.mb.ext.core.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.entity.order.OrderEntity;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.ChartDTO;
import com.mb.ext.core.service.spec.OrderSearchDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.order.CourierDTO;
import com.mb.ext.core.service.spec.order.GroupBuyOrderDTO;
import com.mb.ext.core.service.spec.order.OrderDTO;
import com.mb.ext.core.service.spec.order.OrderProductDTO;
import com.mb.ext.core.service.spec.order.PointOrderDTO;
import com.mb.ext.core.service.spec.order.SecKillOrderDTO;
import com.mb.framework.exception.BusinessException;

/**
 * 订单接口
 * @author B2B2C商城
 *
 * @param <T>
 */
@Transactional
public interface OrderService<T extends BodyDTO>
{

	/**拆分订单
	 * @param orderDTO
	 * @return
	 * @throws BusinessException
	 */
	List<OrderDTO> splitOrder(OrderDTO orderDTO) throws BusinessException;
	
	/**生成订单
	 * @param orderDTO
	 * @return
	 * @throws BusinessException
	 */
	String addOrder(OrderDTO orderDTO) throws BusinessException;
	
	
	/**生成秒杀订单
	 * @param orderDTO
	 * @return
	 * @throws BusinessException
	 */
	String addSecKillOrder(SecKillOrderDTO orderDTO) throws BusinessException;
	
	/**生成团购订单
	 * @param orderDTO
	 * @return
	 * @throws BusinessException
	 */
	String addGroupBuyOrder(GroupBuyOrderDTO orderDTO) throws BusinessException;
	
	/**生成积分订单
	 * @param orderDTO
	 * @return
	 * @throws BusinessException
	 */
	String addPointOrder(PointOrderDTO pointOrderDTO) throws BusinessException;
	
	/**支付订单
	 * @param orderDTO
	 * @throws BusinessException
	 */
	void payOrder(OrderDTO orderDTO) throws BusinessException;
	
	/**订单确认收货
	 * @param orderDTO
	 * @throws BusinessException
	 */
	void confirmOrder(OrderDTO orderDTO) throws BusinessException;
	
	/**订单结算(商户资金, 佣金, 积分等)
	 * @param orderDTO
	 * @throws BusinessException
	 */
	void accountOrder(OrderDTO orderDTO) throws BusinessException;
	
	/**订单取消
	 * @param orderDTO
	 * @throws BusinessException
	 */
	void cancelOrder(OrderDTO orderDTO) throws BusinessException;
	/**更新微信退款通知状态
	 * @param afterSaleNo
	 * @param refundStatus
	 * @throws BusinessException
	 */
	void updateRefundStatus(String orderNo, String refundStatus) throws BusinessException;
	/**订单删除
	 * @param orderDTO
	 * @throws BusinessException
	 */
	void deleteOrder(OrderDTO orderDTO) throws BusinessException;
	
	/**编辑订单(收货人信息和订单总金额)
	 * @param orderDTO
	 * @throws BusinessException
	 */
	void editOrder(OrderDTO orderDTO) throws BusinessException;
	
	/**订单发货(快递公司和快递单号)
	 * @param orderDTO
	 * @throws BusinessException
	 */
	void deliverOrder(OrderDTO orderDTO) throws BusinessException;
	
	/**根据主订单号查询拆分的订单
	 * @param pOrderNo - 主订单号
	 * @return
	 * @throws BusinessException
	 */
	List<OrderDTO> getOrderByPOrderNo(String pOrderNo) throws BusinessException;
	
	/**根据订单号查询订单
	 * @param orderNo - 订单号
	 * @return
	 * @throws BusinessException
	 */
	OrderDTO getOrderByOrderNo(String orderNo) throws BusinessException;
	
	/**根据订单id查询订单
	 * @param orderUuid - 订单id
	 * @return
	 * @throws BusinessException
	 */
	OrderDTO getOrderByOrderUuid(String orderUuid) throws BusinessException;
	
	/** 查询商家所有订单
	 * @param merchantDTO - 商家
	 * @return
	 * @throws BusinessException
	 */
	List<OrderDTO> getOrderByMerchant(MerchantDTO merchantDTO) throws BusinessException;
	
	/**获取会员所有订单
	 * @param userDTO - 会员
	 * @return
	 * @throws BusinessException
	 */
	List<OrderDTO> getOrderByUser(UserDTO userDTO) throws BusinessException;
	
	/**查询拆分订单实际订单金额
	 * @param pOrderNo - 拆分订单父订单号
	 * @return
	 * @throws BusinessException
	 */
	BigDecimal getActualAmountByPOrderNo(String pOrderNo) throws BusinessException;
	
	/**备注订单
	 * @param orderDTO - 订单
	 * @throws BusinessException
	 */
	void commentOrder(OrderDTO orderDTO) throws BusinessException;
	
	/**根据条件分页查询订单
	 * @param orderSearchDTO - 搜索条件
	 * @param startIndex - 分页起始标签
	 * @param pageSize - 每页数据条数
	 * @return 订单列表
	 * @throws BusinessException
	 */
	List<OrderDTO> searchOrders(OrderSearchDTO orderSearchDTO, int startIndex, int pageSize) throws BusinessException;
	
	/**根据条件查询订单数量
	 * @param orderSearchDTO - 搜索条件
	 * @return 订单数量
	 * @throws BusinessException
	 */
	int searchOrderTotal(OrderSearchDTO orderSearchDTO) throws BusinessException;
	
	/**根据条件查询订单总积分
	 * @param orderSearchDTO - 搜索条件
	 * @return 订单总积分
	 * @throws BusinessException
	 */
	int searchOrderTotalPoint(OrderSearchDTO orderSearchDTO) throws BusinessException;
	
	/** 根据条件查询订单总金额
	 * @param orderSearchDTO - 搜索条件
	 * @return 订单总金额
	 * @throws BusinessException
	 */
	BigDecimal searchOrderTotalAmount(OrderSearchDTO orderSearchDTO) throws BusinessException;
	
	/**查询某个时间段的订单商品总数量
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @return 订单商品总数量
	 * @throws BusinessException
	 */
	int getOrderProductUnitTotal(Date startDate, Date endDate) throws BusinessException;
	
	/**查询某个时间段商家的订单商品总数量
	 * @param merchantDTO - 商家
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @return 订单商品总数量
	 * @throws BusinessException
	 */
	int getOrderProductUnitTotalByMerchant(MerchantDTO merchantDTO, Date startDate, Date endDate) throws BusinessException;
	
	/**查询某个时间段会员的订单商品总数量
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @param userDTO - 会员
	 * @return 订单商品总数量
	 * @throws BusinessException
	 */
	int getOrderProductUnitTotalByUser(Date startDate, Date endDate, UserDTO userDTO) throws BusinessException;
	
	/**查询某个时间段特定类型的订单商品总数量
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @param orderType - 订单类型
	 * @return 订单商品总数量
	 * @throws BusinessException
	 */
	int getOrderProductUnitTotalByOrderType(Date startDate, Date endDate, String orderType) throws BusinessException;
	
	/**查询某个时间段商家的特定类型订单商品总数量
	 * @param merchantDTO - 商家
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @param orderType - 订单类型
	 * @return 订单商品总数量
	 * @throws BusinessException
	 */
	int getOrderProductUnitTotalByOrderTypeAndMerchant(MerchantDTO merchantDTO, Date startDate, Date endDate, String orderType) throws BusinessException;
	
	/** 查询某个时间段的订单总金额
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @return 订单总金额
	 * @throws BusinessException
	 */
	BigDecimal getOrderProductAmountTotal(Date startDate, Date endDate) throws BusinessException;
	
	/** 查询某个时间段商家的订单总金额
	 * @param merchantDTO - 商家
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @return
	 * @throws BusinessException
	 */
	BigDecimal getOrderProductAmountTotalByMerchant(MerchantDTO merchantDTO, Date startDate, Date endDate) throws BusinessException;
	
	/**查询某个时间段会员的订单总金额
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @param userDTO - 会员
	 * @return
	 * @throws BusinessException
	 */
	BigDecimal getOrderProductAmountTotalByUser(Date startDate, Date endDate, UserDTO userDTO) throws BusinessException;
	
	/**查询某个时间段会员的订单总积分
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @param userDTO - 会员
	 * @return 订单总积分
	 * @throws BusinessException
	 */
	int getOrderProductPointTotalByUser(Date startDate, Date endDate, UserDTO userDTO) throws BusinessException;
	
	/**查询某个时间段某种订单类型的总金额
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @param orderType - 订单类型
	 * @return 订单总金额
	 * @throws BusinessException
	 */
	BigDecimal getOrderProductAmountTotalByOrderType(Date startDate, Date endDate, String orderType) throws BusinessException;
	
	/**查询某个时间段商家某种订单类型的总金额
	 * @param merchantDTO - 商家
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @param orderType - 订单类型
	 * @return
	 * @throws BusinessException
	 */
	BigDecimal getOrderProductAmountTotalByOrderTypeAndMerchant(MerchantDTO merchantDTO, Date startDate, Date endDate, String orderType) throws BusinessException;
	
	/**获取某个时间段的订单金额曲线数据
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @return 订单金额曲线数据
	 * @throws BusinessException
	 */
	List<ChartDTO> getIncrementOrderAmountChart(Date startDate, Date endDate) throws BusinessException;
	
	/**获取某个时间段商家的订单金额曲线数据
	 * @param merchantDTO - 商家
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @return 订单金额曲线数据
	 * @throws BusinessException
	 */
	List<ChartDTO> getIncrementOrderAmountChartByMerchant(MerchantDTO merchantDTO, Date startDate, Date endDate) throws BusinessException;
	
	/**获取某个时间段的订单数量曲线数据
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @return 订单数量曲线数据
	 * @throws BusinessException
	 */
	List<ChartDTO> getIncrementOrderCountChart(Date startDate, Date endDate) throws BusinessException;
	
	/** 获取某个时间段商家的订单数量曲线数据
	 * @param merchantDTO - 商家
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @return 订单数量曲线数据
	 * @throws BusinessException
	 */
	List<ChartDTO> getIncrementOrderCountChartByMerchant(MerchantDTO merchantDTO, Date startDate, Date endDate) throws BusinessException;
	
	/**获取某个时间段的订单商品数量曲线数据
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @return 商品数量曲线数据
	 * @throws BusinessException
	 */
	List<ChartDTO> getIncrementOrderUnitChart(Date startDate, Date endDate) throws BusinessException;
	
	/**获取某个时间段的订单积分曲线数据
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @return 订单积分曲线数据
	 * @throws BusinessException
	 */
	List<ChartDTO> getIncrementOrderPointChart(Date startDate, Date endDate) throws BusinessException;

	/**通过csv导入订单物流信息
	 * @param orderDTO - 订单
	 * @throws BusinessException
	 */
	void importLogistics(OrderDTO orderDTO)throws BusinessException;
	
	//某段时间内商品按销售金额top x
	List<OrderProductDTO> getTopxProductByAmount(Date startDate, Date endDate, int x) throws BusinessException;
	
	//商家某段时间内商品按销售金额top x
	List<OrderProductDTO> getTopxMerchantProductByAmount(MerchantDTO merchantDTO, Date startDate, Date endDate, int x) throws BusinessException;
	
	//某段时间内商品按销售数量top x
	List<OrderProductDTO> getTopxProductByUnit(Date startDate, Date endDate, int x) throws BusinessException;
	
	//商家某段时间内商品按销售数量top x
	List<OrderProductDTO> getTopxMerchantProductByUnit(MerchantDTO merchantDTO, Date startDate, Date endDate, int x) throws BusinessException;
	
	//商家按订单数量
	List<MerchantDTO> searchTopxMerchantByUnit(OrderSearchDTO searchDTO) throws BusinessException;
	
	//商家按订单金额
	List<MerchantDTO> searchTopxMerchantByAmount(OrderSearchDTO searchDTO) throws BusinessException;
	
	/**查询物流信息(阿里云市场的服务商:四川涪擎大数据技术有限公司提供的接口)
	 * @param no-快递单号
	 * @param type-快递公司字母简写(可不填)
	 * @return
	 * @throws BusinessException
	 */
	CourierDTO getCourierInfoByNo(String no, String type) throws BusinessException;
	
	/**将entity转化为DTO
	 * @param orderEntity
	 * @param orderDTO
	 */
	public void orderEntity2DTO(OrderEntity orderEntity,
			OrderDTO orderDTO);
}
