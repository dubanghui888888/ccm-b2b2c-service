
package com.mb.ext.core.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.service.spec.AwardSearchDTO;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.ChartDTO;
import com.mb.ext.core.service.spec.PerformanceSearchDTO;
import com.mb.ext.core.service.spec.UserAwardDTO;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.UserPerformanceDTO;
import com.mb.ext.core.service.spec.UserStatementDTO;
import com.mb.ext.core.service.spec.UserWithdrawDTO;
import com.mb.ext.core.service.spec.WithdrawSearchDTO;
import com.mb.ext.core.service.spec.merchant.MerchantAccountDTO;
import com.mb.ext.core.service.spec.merchant.MerchantChargeDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.merchant.MerchantStatementDTO;
import com.mb.ext.core.service.spec.merchant.MerchantWithdrawDTO;
import com.mb.ext.core.service.spec.merchant.PlatformStatementDTO;
import com.mb.framework.exception.BusinessException;

/**资金接口, 处理和资金相关的业务
 * @author B2B2C商城
 *
 * @param <T>
 */
@Transactional
public interface FundService<T extends BodyDTO>
{

	/** 商家申请提现
	 * @param withdrawDTO - 提现数据
	 * @throws BusinessException
	 */
	void applyMerchantWithdraw(MerchantWithdrawDTO withdrawDTO) throws BusinessException;
	
	/**同意商家提现申请
	 * @param withdrawDTO - 提现数据
	 * @throws BusinessException
	 */
	void approveMerchantWithdraw(MerchantWithdrawDTO withdrawDTO) throws BusinessException;
	
	/**拒绝商家提现申请
	 * @param withdrawDTO - 提现数据
	 * @throws BusinessException
	 */
	void rejectMerchantWithdraw(MerchantWithdrawDTO withdrawDTO) throws BusinessException;
	
	/**标记商家提现申请成功
	 * @param withdrawDTO - 提现数据
	 * @throws BusinessException
	 */
	void successMerchantWithdraw(MerchantWithdrawDTO withdrawDTO) throws BusinessException;
	
	/**标记商家提现申请失败
	 * @param withdrawDTO - 提现数据
	 * @throws BusinessException
	 */
	void failMerchantWithdraw(MerchantWithdrawDTO withdrawDTO) throws BusinessException;
	
	/**备注商家提现申请
	 * @param withdrawDTO - 提现数据
	 * @throws BusinessException
	 */
	void commentMerchantWithdraw(MerchantWithdrawDTO withdrawDTO) throws BusinessException;
	
	/**根据条件搜索商家提现申请
	 * @param withdrawSearchDTO - 搜索条件
	 * @param startIndex - 分页起始标签
	 * @param pageSize - 每页数据条数
	 * @return
	 * @throws BusinessException
	 */
	List<MerchantWithdrawDTO> searchMerchantWithdraw(WithdrawSearchDTO withdrawSearchDTO, int startIndex, int pageSize) throws BusinessException;
	
	/** 根据条件搜索商家提现申请记录数量
	 * @param withdrawSearchDTO - 搜索条件
	 * @return 记录数量
	 * @throws BusinessException
	 */
	int searchMerchantWithdrawTotal(WithdrawSearchDTO withdrawSearchDTO) throws BusinessException;
	
	/**根据条件搜索商家提现总金额
	 * @param withdrawSearchDTO - 搜索条件
	 * @return 提现总金额
	 * @throws BusinessException
	 */
	BigDecimal searchMerchantWithdrawTotalAmount(WithdrawSearchDTO withdrawSearchDTO) throws BusinessException;
	
	/**获取所有商家提现记录
	 * @return 提现记录列表
	 * @throws BusinessException
	 */
	List<MerchantWithdrawDTO> getMerchantWithdraws() throws BusinessException;
	
	/**会员提现申请
	 * @param withdrawDTO - 会员提现数据
	 * @throws BusinessException
	 */
	void applyUserWithdraw(UserWithdrawDTO withdrawDTO) throws BusinessException;
	
	/**同意会员提现申请
	 * @param withdrawDTO - 会员提现数据
	 * @throws BusinessException
	 */
	void approveUserWithdraw(UserWithdrawDTO withdrawDTO) throws BusinessException;
	
	/**拒绝会员提现申请
	 * @param withdrawDTO - 会员提现数据
	 * @throws BusinessException
	 */
	void rejectUserWithdraw(UserWithdrawDTO withdrawDTO) throws BusinessException;
	
	/**会员提现申请成功
	 * @param withdrawDTO - 会员提现数据
	 * @throws BusinessException
	 */
	void successUserWithdraw(UserWithdrawDTO withdrawDTO) throws BusinessException;
	
	/**会员提现申请失败
	 * @param withdrawDTO - 会员提现数据
	 * @throws BusinessException
	 */
	void failUserWithdraw(UserWithdrawDTO withdrawDTO) throws BusinessException;
	
	/**备注会员提现申请
	 * @param withdrawDTO - 会员提现数据
	 * @throws BusinessException
	 */
	void commentUserWithdraw(UserWithdrawDTO withdrawDTO) throws BusinessException;
	
	/**查询所有会员提现记录
	 * @return
	 * @throws BusinessException
	 */
	List<UserWithdrawDTO> getUserWithdraws() throws BusinessException;
	
	/**根据条件搜索会员提现申请
	 * @param withdrawSearchDTO - 搜索条件
	 * @param startIndex - 分页起始标签
	 * @param pageSize - 每页数据条数
	 * @return 提现记录
	 * @throws BusinessException
	 */
	List<UserWithdrawDTO> searchUserWithdraw(WithdrawSearchDTO withdrawSearchDTO, int startIndex, int pageSize) throws BusinessException;
	
	/**根据条件搜索会员提现申请数量
	 * @param withdrawSearchDTO - 搜索条件
	 * @return 提现记录数量
	 * @throws BusinessException
	 */
	int searchUserWithdrawTotal(WithdrawSearchDTO withdrawSearchDTO) throws BusinessException;
	
	/**根据条件搜索会员奖金记录
	 * @param awardSearchDTO - 搜索条件
	 * @param startIndex - 分页起始标签
	 * @param pageSize - 每页数据条数
	 * @return
	 * @throws BusinessException
	 */
	List<UserAwardDTO> searchUserAward(AwardSearchDTO awardSearchDTO, int startIndex, int pageSize) throws BusinessException;
	
	/**根据条件搜索会员奖金记录数量
	 * @param awardSearchDTO - 搜索条件
	 * @return 记录数量
	 * @throws BusinessException
	 */
	int searchUserAwardTotal(AwardSearchDTO awardSearchDTO) throws BusinessException;
	
	/**根据条件搜索会员奖金总金额
	 * @param awardSearchDTO - 搜索条件
	 * @return 奖金总金额
	 * @throws BusinessException
	 */
	BigDecimal searchUserAwardAmount(AwardSearchDTO awardSearchDTO) throws BusinessException;
	
	/**获取所有商家充值记录
	 * @return
	 * @throws BusinessException
	 */
	List<MerchantChargeDTO> getMerchantCharges() throws BusinessException;
	
	/**根据商家充值单号查询充值记录
	 * @param chargeNo - 充值单号
	 * @return 充值记录
	 * @throws BusinessException
	 */
	MerchantChargeDTO getMerchantChargeByChargeNo(String chargeNo) throws BusinessException;
	
	/**根据商家充值id查询充值记录
	 * @param uuid - 充值id
	 * @return
	 * @throws BusinessException
	 */
	MerchantChargeDTO getMerchantChargeByUuid(String uuid) throws BusinessException;
	
	/**查询商家所有提现记录
	 * @param merchantDTO - 商家
	 * @return
	 * @throws BusinessException
	 */
	List<MerchantWithdrawDTO> getMerchantWithdrawByMerchant(MerchantDTO merchantDTO) throws BusinessException;
	
	/**查询所有会员提现记录
	 * @param userDTO - 会员
	 * @return
	 * @throws BusinessException
	 */
	List<UserWithdrawDTO> getUserWithdrawByUser(UserDTO userDTO) throws BusinessException;
	
	/**查询所有商家充值记录
	 * @param merchantDTO - 商家
	 * @return
	 * @throws BusinessException
	 */
	List<MerchantChargeDTO> getMerchantChargeByMerchant(MerchantDTO merchantDTO) throws BusinessException;
	
	/** 查询所有未审批的商家提现申请
	 * @return
	 * @throws BusinessException
	 */
	List<MerchantWithdrawDTO> getPendingVerifyMerchantWithdraw() throws BusinessException;
	
	/**查询所有未审批的会员提现申请
	 * @return
	 * @throws BusinessException
	 */
	List<UserWithdrawDTO> getPendingVerifyUserWithdraw() throws BusinessException;
	
	/** 查询所有已审批未打款的商家提现申请
	 * @return
	 * @throws BusinessException
	 */
	List<MerchantWithdrawDTO> getPendingCompleteMerchantWithdraw() throws BusinessException;
	
	/** 根据条件搜索会员销售总业绩
	 * @param performanceSearchDTO - 查询条件
	 * @param startIndex - 分页起始标签
	 * @param pageSize - 每页数据条数
	 * @return 会员销售业绩
	 * @throws BusinessException
	 */
	List<UserPerformanceDTO> searchUserTotalPerformance(PerformanceSearchDTO performanceSearchDTO, int startIndex, int pageSize) throws BusinessException;
	
	/**根据条件搜索会员销售总业绩记录数量
	 * @param performanceSearchDTO - 查询条件
	 * @return 记录数量
	 * @throws BusinessException
	 */
	int searchUserTotalPerformanceTotal(PerformanceSearchDTO performanceSearchDTO) throws BusinessException;
	
	/**根据条件搜索会员销售业绩总金额
	 * @param performanceSearchDTO - 查询条件
	 * @return 业绩总金额
	 * @throws BusinessException
	 */
	BigDecimal searchUserTotalPerformanceAmount(PerformanceSearchDTO performanceSearchDTO) throws BusinessException;
	
	/**根据条件搜索会员销售业绩总奖金
	 * @param performanceSearchDTO - 查询条件
	 * @return 业绩总奖金
	 * @throws BusinessException
	 */
	BigDecimal searchUserTotalPerformanceAward(PerformanceSearchDTO performanceSearchDTO) throws BusinessException;
	
	/**根据条件搜索会员销售业绩
	 * @param performanceSearchDTO - 查询条件
	 * @param startIndex - 分页起始标签
	 * @param pageSize - 每页数据条数
	 * @return 会员销售业绩
	 * @throws BusinessException
	 */
	public List<UserPerformanceDTO> searchUserPerformance(PerformanceSearchDTO performanceSearchDTO, int startIndex, int pageSize) throws BusinessException;
	
	/**根据条件搜索会员销售业绩记录数量
	 * @param performanceSearchDTO - 查询条件
	 * @return 记录数量
	 * @throws BusinessException
	 */
	public int searchUserPerformanceTotal(PerformanceSearchDTO performanceSearchDTO) throws BusinessException;
	
	/**获取所有未打款会员提现记录
	 * @return
	 * @throws BusinessException
	 */
	List<UserWithdrawDTO> getPendingCompleteUserWithdraw() throws BusinessException;
	
	/** 获取所有已打款商家提现记录
	 * @return
	 * @throws BusinessException
	 */
	List<MerchantWithdrawDTO> getSuccessMerchantWithdraw() throws BusinessException;
	
	/**获取所有已打款会员提现记录
	 * @return
	 * @throws BusinessException
	 */
	List<UserWithdrawDTO> getSuccessUserWithdraw() throws BusinessException;
	
	/**获取所有打款失败商家提现记录
	 * @return
	 * @throws BusinessException
	 */
	List<MerchantWithdrawDTO> getFailMerchantWithdraw() throws BusinessException;
	
	/**获取所有打款失败会员提现记录
	 * @return
	 * @throws BusinessException
	 */
	List<UserWithdrawDTO> getFailUserWithdraw() throws BusinessException;
	
	/**获取所有审批未通过商家提现记录
	 * @return
	 * @throws BusinessException
	 */
	List<MerchantWithdrawDTO> getRejectMerchantWithdraw() throws BusinessException;
	
	/**获取所有审批未通过会员提现记录
	 * @return
	 * @throws BusinessException
	 */
	List<UserWithdrawDTO> getRejectUserWithdraw() throws BusinessException;
	
	/**添加商家银行账号
	 * @param accountDTO - 银行账号
	 * @throws BusinessException
	 */
	void addMerchantAccount(MerchantAccountDTO accountDTO) throws BusinessException;
	
	/**更新商家银行账号
	 * @param accountDTO - 银行账号
	 * @throws BusinessException
	 */
	void updateMerchantAccount(MerchantAccountDTO accountDTO) throws BusinessException;
	
	/**删除商家银行账号
	 * @param accountDTO - 银行账号
	 * @throws BusinessException
	 */
	void deleteMerchantAccount(MerchantAccountDTO accountDTO) throws BusinessException;
	
	/**查询商家已添加的银行账号
	 * @param merchantDTO - 商家
	 * @return
	 * @throws BusinessException
	 */
	List<MerchantAccountDTO> getMerchantAccount(MerchantDTO merchantDTO) throws BusinessException;
	
	/**根据Id查询商家银行账号
	 * @param merchantDTO - 银行账号Id
	 * @return
	 * @throws BusinessException
	 */
	MerchantAccountDTO getMerchantAccountByUuid(String uuid) throws BusinessException;
	
	/**根据id查询商家提现记录
	 * @param uuid - 提现记录id
	 * @return
	 * @throws BusinessException
	 */
	MerchantWithdrawDTO getMerchantWithdrawByUuid(String uuid) throws BusinessException;
	
	/** 根据id查询会员提现记录
	 * @param uuid - 提现记录id
	 * @return
	 * @throws BusinessException
	 */
	UserWithdrawDTO getUserWithdrawByUuid(String uuid) throws BusinessException;
	
	/**获取商家资金余额
	 * @param merchantDTO - 商家
	 * @return 资金余额
	 * @throws BusinessException
	 */
	MerchantDTO getMerchantBalance(MerchantDTO merchantDTO) throws BusinessException;
	
	/**查询商家余额明细
	 * @param merchantDTO - 商家
	 * @return 明细记录
	 * @throws BusinessException
	 */
	List <MerchantStatementDTO> getMerchantStatement(MerchantDTO merchantDTO) throws BusinessException;
	
	/**查询会员资金明细
	 * @param userDTO - 会员
	 * @return
	 * @throws BusinessException
	 */
	List <UserStatementDTO> getUserStatement(UserDTO userDTO) throws BusinessException;
	
	/**备注会员奖金记录
	 * @param userAwardDTO - 会员奖金记录
	 * @throws BusinessException
	 */
	void commentUserAward(UserAwardDTO userAwardDTO) throws BusinessException;
	
	/**根据id查询会员奖金记录
	 * @param uuid - 奖金记录id
	 * @return
	 * @throws BusinessException
	 */
	UserAwardDTO getUserAwardByUuid(String uuid) throws BusinessException;
	
	/**查询会员所有奖金明细
	 * @param userDTO - 会员
	 * @return
	 * @throws BusinessException
	 */
	List <UserAwardDTO> getUserAward(UserDTO userDTO) throws BusinessException;
	
	/**根据交易类型查询商家资金明细记录
	 * @param merchantDTO - 商家
	 * @param type - 资金明细交易类型
	 * @return 资金明细记录
	 * @throws BusinessException
	 */
	List <MerchantStatementDTO> getMerchantStatementByType(MerchantDTO merchantDTO, String type) throws BusinessException;
	
	/**根据交易类型查询会员资金明细记录
	 * @param userDTO - 会员
	 * @param type - 交易类型
	 * @return
	 * @throws BusinessException
	 */
	List <UserStatementDTO> getUserStatementByType(UserDTO userDTO, String type) throws BusinessException;
	
	/**查询会员某种类型的奖金记录
	 * @param userDTO - 会员
	 * @param type - 奖金类型
	 * @return 奖金记录
	 * @throws BusinessException
	 */
	List <UserAwardDTO> getUserAwardByType(UserDTO userDTO, String type) throws BusinessException;
	
	/**查询某个时间段商家的资金明细
	 * @param merchantDTO - 商家
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @return 资金明细
	 * @throws BusinessException
	 */
	List <MerchantStatementDTO> getMerchantStatementByDate(MerchantDTO merchantDTO, Date startDate, Date endDate) throws BusinessException;
	
	/**查询某个时间段会员的资金明细
	 * @param userDTO - 会员
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @return
	 * @throws BusinessException
	 */
	List <UserStatementDTO> getUserStatementByDate(UserDTO userDTO, Date startDate, Date endDate) throws BusinessException;
	
	/**查询某个时间段会员的奖金明细
	 * @param userDTO - 会员
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @return 奖金明细
	 * @throws BusinessException
	 */
	List <UserAwardDTO> getUserAwardByDate(UserDTO userDTO, Date startDate, Date endDate) throws BusinessException;
	
	/** 查询交易类型查询某个时间段所有商家的资金明细
	 * @param type - 交易类型
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @return 资金明细
	 * @throws BusinessException
	 */
	List <MerchantStatementDTO> getMerchantStatementByTypeDate(String type, Date startDate, Date endDate) throws BusinessException;
	
	/**查询交易类型查询某个时间段所有会员的资金明细
	 * @param type - 交易类型
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @return 资金明细
	 * @throws BusinessException
	 */
	List <UserStatementDTO> getUserStatementByTypeDate(String type, Date startDate, Date endDate) throws BusinessException;
	
	/**查询某个时间段的平台资金明细
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @return 资金明细
	 * @throws BusinessException
	 */
	List <PlatformStatementDTO> getPlatformStatementByDate(Date startDate, Date endDate) throws BusinessException;

	/**获取商家资金余额
	 * @param merchantDTO - 商家
	 * @return
	 * @throws BusinessException
	 */
	BigDecimal getAvailableBalanceByMerchant(MerchantDTO merchantDTO) throws BusinessException;
	
	/**获取平台资金余额
	 * @return
	 * @throws BusinessException
	 */
	BigDecimal getAvailableBalance() throws BusinessException;
	
	/**获取会员可提现资金总额
	 * @return 可提现资金总额
	 * @throws BusinessException
	 */
	BigDecimal getTotalUserAvailableBalance() throws BusinessException;
	
	/**获取某个时间段的平台交易总金额
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @return 交易总金额
	 * @throws BusinessException
	 */
	BigDecimal getPlatformTranAmountByDate(Date startDate, Date endDate) throws BusinessException;
	
	/**获取平台资金余额
	 * @return
	 * @throws BusinessException
	 */
	BigDecimal getPlatformBalance() throws BusinessException;
	
	/**获取某段时间内商家交易资金总额
	 * @param merchantDTO - 商家
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @return
	 * @throws BusinessException
	 */
	BigDecimal getTranAmountByMerchantDate(MerchantDTO merchantDTO, Date startDate, Date endDate) throws BusinessException;
	
	/**根据交易类型获取某段时间内商家交易资金总额
	 * @param merchantDTO - 商家
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @param tranType - 交易类型
	 * @return 交易资金总额
	 * @throws BusinessException
	 */
	BigDecimal getTranAmountByMerchantDateType(MerchantDTO merchantDTO, Date startDate, Date endDate, String tranType) throws BusinessException;
	
	/**根据交易类型获取某段时间内交易资金总额
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @param tranType - 交易类型
	 * @return 交易资金总额
	 * @throws BusinessException
	 */
	BigDecimal getTranAmountByDateType(Date startDate, Date endDate, String tranType) throws BusinessException;
	
	/**获取某段时间内商家交易资金曲线数据
	 * @param merchantDTO - 商家
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @return
	 * @throws BusinessException
	 */
	List<ChartDTO> getTranAmountChartByMerchantDate(MerchantDTO merchantDTO, Date startDate, Date endDate) throws BusinessException;
	
	/**获取某段时间内交易资金总额
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @return 交易资金总额
	 * @throws BusinessException
	 */
	BigDecimal getTranAmountByDate(Date startDate, Date endDate) throws BusinessException;
	
	/**获取某个时间的交易金额曲线数据
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @return
	 * @throws BusinessException
	 */
	List<ChartDTO> getTranAmountChartByDate(Date startDate, Date endDate) throws BusinessException;
	
	/** 根据交易类型查询某个时间段的奖金总金额
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @param tranType - 交易类型
	 * @return 奖金总金额
	 * @throws BusinessException
	 */
	BigDecimal getAwardAmountByDateType(Date startDate, Date endDate, String transactionType) throws BusinessException;
	
	/**根据交易类型查询商家在某个时间段的奖金总金额
	 * @param merchantDTO - 商家
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @param tranType - 交易类型
	 * @return 奖金总金额
	 * @throws BusinessException
	 */
	BigDecimal getAwardAmountByDateTypeAndMerchant(MerchantDTO merchantDTO, Date startDate, Date endDate, String transactionType) throws BusinessException;
	
	/** 查询某个时间段的奖金总金额
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @return 奖金总金额
	 * @throws BusinessException
	 */
	BigDecimal getAwardAmountByDate(Date startDate, Date endDate) throws BusinessException;
	
	/**查询商家某个时间段的奖金总金额
	 * @param merchantDTO - 商家
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @return
	 * @throws BusinessException
	 */
	BigDecimal getAwardAmountByDateAndMerchant(MerchantDTO merchantDTO, Date startDate, Date endDate) throws BusinessException;
	
	/**查询某个时间段内的奖金金额曲线数据
	 * @param startDate - 开始时间
	 * @param endDate - 结束时间
	 * @return 奖金金额曲线数据
	 * @throws BusinessException
	 */
	List<ChartDTO> getAwardAmountChartByDate(Date startDate, Date endDate) throws BusinessException;
}
