
package com.mb.ext.core.sybpay;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.framework.exception.BusinessException;

@Transactional
public interface SybPayService<T extends BodyDTO>
{
	
	/**通联网上收银统一下单接口
	 * @param cusid - 商户号, 必填
	 * @param trxamt - 交易金额(单位分), 必填
	 * @param reqsn - 商户交易单号(商户的交易订单号), 必填
	 * @param paytype - 交易方式(W01-微信扫码支付, W02-微信JS支付 , A02-支付宝JS支付, A01-支付宝扫码支付), 必填
	 * @param body - 订单标题(订单商品名称，为空则以商户名作为商品名称), 非必填
	 * @param remark - 备注(备注信息), 非必填
	 * @param acct - 支付平台用户标识(JS支付时使用 微信支付-用户的微信openid, 支付宝支付-用户user_id, 微信小程序-用户小程序的openid), 必填
	 * @param authcode(非必填)
	 * @param notify_url(交易结果通知地址, 接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。), 必填
	 * @param limit_pay(支付限制, no_credit--指定不能使用信用卡支付), 非必填
	 * @param idno(证件号, 实名交易必填.填了此字段就会验证证件号和姓名), 非必填
	 * @param truename(付款人真实姓名, 实名交易必填.填了此字段就会验证证件号和姓名), 非必填
	 * @param asinfo(分账信息), 必填
	 * @return
	 * @throws BusinessException
	 */
	Map<String, String> payNet (String cusid,long trxamt,String reqsn,String paytype,String body,String remark,String acct,String authcode,String notify_url,String limit_pay,String idno,String truename,String asinfo) throws BusinessException;
	
	/**
	 * @param cusid - 商户号, 必填
	 * @param trxamt - 交易金额(单位分), 必填
	 * @param reqsn - 商户交易单号(商户的交易订单号), 必填
	 * @param body - 订单标题(订单商品名称，为空则以商户名作为商品名称), 非必填
	 * @param remark - 备注(备注信息), 非必填
	 * @param notify_url(交易结果通知地址, 接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。), 必填
	 * @param return_url(页面跳转同步通知页面路径), 必填
	 * @return
	 * @throws BusinessException
	 */
	public String payH5(String cusid,long trxamt,String reqsn,String body,String remark,String notify_url,String return_url) throws BusinessException;

}
