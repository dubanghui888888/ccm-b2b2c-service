
package com.mb.ext.core.message;


public class BusinessErrorCode
{
	public final static String ENT_DUPLICATE_NAME = "ErrorCode.ent.0001";
	public final static String ENT_PLATFORM_EXPIRED = "ErrorCode.ent.0002";
	
	public final static String COMMON_SYSTEM_ERROR = "ErrorCode.common.0001";
	public final static String COMMON_TOKEN_EMPTY = "ErrorCode.common.0002";
	public final static String COMMON_TOKEN_INVALID = "ErrorCode.common.0003";
	public final static String COMMON_TOKEN_EXPIRED = "ErrorCode.common.0004";
	public final static String COMMON_SYSTEM_PARAMETER_ERROR = "ErrorCode.common.0005";
	
	//Verification Code
	public final static String VERIFICATION_INCORRECT = "ErrorCode.verification.0001";
	public final static String VERIFICATION_EXPIRE = "ErrorCode.verification.0002";
	public final static String VERIFICATION_TYPE_INCORRECT = "ErrorCode.verification.0003";
	
	//Merchant
	public static final String MERCHANT_APPLICATION_APPROVED = "ErrorCode.Merchant.0001";
	public static final String MERCHANT_APPLICATION_REJECTED = "ErrorCode.Merchant.0002";
	public static final String MERCHANT_APPLICATION_DUPLICATE = "ErrorCode.Merchant.0003";
	public static final String MERCHANT_NOT_FOUND = "ErrorCode.Merchant.0000";
	public static final String MERCHANT_CLOSED = "ErrorCode.Merchant.0004";
	
	//Fund
	public static final String FUND_WITHDRAW_INSUFFICIENT = "ErrorCode.Fund.0001";
	
	//Product
	public static final String PRODUCT_GROUP_DUPLICATE= "ErrorCode.Product.0001";
	public static final String PRODUCT_CATE_DELETE= "ErrorCode.Product.0002";
	public static final String PRODUCT_STOCK_INSUFFICIENT= "ErrorCode.Product.0003";
	public static final String PRODUCT_UNIT_INCORRECT= "ErrorCode.Product.0004";
	
	//Order
	public static final String ORDER_NOT_FOUND= "ErrorCode.Order.0001";
	public static final String ORDER_NOT_CANCEL= "ErrorCode.Order.0002";
	public static final String ORDER_NOT_DELIVER= "ErrorCode.Order.0003";
	public static final String ORDER_NOT_RECEIVE= "ErrorCode.Order.0004";
	public static final String ORDER_RETURN_NOT_DELIVER = "ErrorCode.Order.0005";
	public static final String ORDER_RETURN_NOT_RECEIVE = "ErrorCode.Order.0006";
	public static final String INSUFFICIENT_POINT = "ErrorCode.Order.0007";
	public static final String ORDER_PRODUCT_EMPTY = "ErrorCode.Order.0008";
	public static final String ORDER_PRODUCT_UNIT_EMPTY = "ErrorCode.Order.0009";
	public static final String ORDER_USER_EMPTY = "ErrorCode.Order.0010";
	public static final String ORDER_USER_ERROR = "ErrorCode.Order.0011";
	public static final String ORDER_ADDRESS_EMPTY = "ErrorCode.Order.0012";
	public static final String ORDER_ADDRESS_ERROR = "ErrorCode.Order.0013";
	public static final String ORDER_COMPLETED_CANCEL = "ErrorCode.Order.0014";
	public static final String ORDER_NOT_DELETE= "ErrorCode.Order.0015";
	public static final String ORDER_PAY_DUPLICATE= "ErrorCode.Order.0016";
	public static final String ORDER_PRINTER_NOT_SETTING = "ErrorCode.Order.0017";
	
	//SecKill Order
	public static final String SECKILL_ORDER_STOCK_INSUFFICIENT= "ErrorCode.SecKillOrder.0001";
	public static final String SECKILL_ORDER_QUANLIFICATION_INVALID= "ErrorCode.SecKillOrder.0002";
	public static final String SECKILL_ORDER_STOCK_DUPLICATE= "ErrorCode.SecKillOrder.0003";
	
	//After Sale
	public static final String ORDER_AFTER_SALE_DUPLICATE_APPLICATION = "ErrorCode.AfterSale.0001";
	
	//Group Buy
	public static final String ORDER_GROUP_BUY_NOT_COMPLETED = "ErrorCode.GroupBuy.0001";
	
	//PayOrder
	public static final String PAYORDER_POINT_RECEIVE_DUPLICATE= "ErrorCode.PayOrder.0001";
	
	//Delivery
	public static final String DELIVERY_DUPLICATE= "ErrorCode.Delivery.0001";
	public static final String DELIVERY_INVALID_QUANTITY = "ErrorCode.Delivery.0002";
	public static final String DELIVERY_INSUFFICIENT_INVENTORY= "ErrorCode.Delivery.0003";
	
	//Point
	public static final String POINT_SIGNED= "ErrorCode.Point.0001";
	
	//Coupon
	public static final String COUPON_INACTIVE= "ErrorCode.Coupon.0001";
	public static final String COUPON_EXCEED_LIMIT= "ErrorCode.Coupon.0002";
	public static final String COUPON_NOT_STARTED= "ErrorCode.Coupon.0003";
	public static final String COUPON_NOT_VALID= "ErrorCode.Coupon.0004";
	public static final String COUPON_NOT_USE= "ErrorCode.Coupon.0005";
	public static final String COUPON_END= "ErrorCode.Coupon.0006";
	public static final String COUPON_USED= "ErrorCode.Coupon.0007";
	public static final String COUPON_CONDITION_LIMIT= "ErrorCode.Coupon.0008";
	public static final String COUPON_STOCK_INSUFFICIENT= "ErrorCode.Coupon.0009";
	public static final String COUPON_NOT_IN_MERCHANT= "ErrorCode.Coupon.0010";
	public static final String COUPON_NOT_FOUND= "ErrorCode.Coupon.0011";
	public static final String COUPON_PRODUCT_NOT_INVOLVED = "ErrorCode.Coupon.0012";
	public static final String COUPON_NOT_REACH_CONDITION_AMOUNT = "ErrorCode.Coupon.0013";
	
	//Voucher
	public static final String VOUCHER_USED = "ErrorCode.Voucher.0001";
	public static final String VOUCHER_EXPIRED = "ErrorCode.Voucher.0002";
	public static final String VOUCHER_NOT_PERMITTED = "ErrorCode.Voucher.0003";
	public static final String VOUCHER_NOT_STARTED = "ErrorCode.Voucher.0004";
	
	//Merchant
	public static final String MERCHANT_DUPLICATE_MOBILENO = "ErrorCode.merchant.0001";
	public static final String MERCHANT_ASSIGN_POINT_INSUFFICIENT = "ErrorCode.merchant.0002";
	
	//Supplier
	public static final String SUPPLIER_DUPLICATE_MOBILENO = "ErrorCode.supplier.0001";
	public static final String SUPPLIER_NOT_FOUND = "ErrorCode.supplier.0002";
	public static final String SUPPLIER_NOT_DELETE = "ErrorCode.supplier.0003";
	
	//User
	public static final String USER_NOT_FOUND = "ErrorCode.user.0001";
	public static final String USER_EMPTY = "ErrorCode.user.0002";
	public static final String USER_DUPLICATE = "ErrorCode.user.0003";
	public static final String USER_DELETE_ACTIVITY = "ErrorCode.user.0004";
	public static final String USER_DUPLICATE_EMAIL = "ErrorCode.user.0005";
	public static final String USER_DUPLICATE_PHONE = "ErrorCode.user.0006";
	public static final String USER_DUPLICATE_ID = "ErrorCode.user.0007";
	public static final String USER_DELETE_PROJECT = "ErrorCode.user.0008";
	public static final String USER_DELETE_ASSIGNED_TASK = "ErrorCode.user.0010";
	public static final String USER_DELETE_CREATED_TASK = "ErrorCode.user.0009";
	public static final String USER_DELETE_CUSTOMER = "ErrorCode.user.0011";
	public static final String USER_DELETE_SALE = "ErrorCode.user.0012";
	public static final String USER_DELETE_CAMPAIGN = "ErrorCode.user.0013";
	public static final String USER_BIND_WECHAT = "ErrorCode.user.0014";
	public static final String USER_UNBIND_WECHAT = "ErrorCode.user.0015";
	public static final String USER_NOT_ORG_MANAGER = "ErrorCode.user.0016";
	public static final String USER_WECHAT_NOT_BIND = "ErrorCode.user.0017";
	public static final String USER_SOME_SERVICE_EXCEED_MAXCOUNT = "ErrorCode.user.0018";
	public static final String USER_OA_SERVICE_EXCEED_MAXCOUNT = "ErrorCode.user.0019";
	public static final String USER_HR_SERVICE_EXCEED_MAXCOUNT = "ErrorCode.user.0020";
	public static final String USER_PROJECT_SERVICE_EXCEED_MAXCOUNT = "ErrorCode.user.0021";
	public static final String USER_CRM_SERVICE_EXCEED_MAXCOUNT = "ErrorCode.user.0022";
	public static final String USER_ERP_SERVICE_EXCEED_MAXCOUNT = "ErrorCode.user.0023";
	public static final String USER_FINANCE_SERVICE_EXCEED_MAXCOUNT = "ErrorCode.user.0024";
	public static final String USER_SOME_SERVICE_EXCEED_DUEDATE = "ErrorCode.user.0025";
	public static final String USER_DOWNGRADE_SERVICE_EXCEED_MAXCOUNT = "ErrorCode.user.0026";
	public static final String USER_DEACTIVATED = "ErrorCode.user.0027";
	public static final String SUPERVISOR_EMPTY = "ErrorCode.user.0028";
	public static final String USER_DUPLICATE_IDCARDNO = "ErrorCode.user.0029";
	public static final String USER_DUPLICATE_UPGRADE = "ErrorCode.user.0030";
	public static final String USER_DUPLICATE_REFUND = "ErrorCode.user.0031";
	public static final String USER_CHANGE_SUPERVISOR_ONCE = "ErrorCode.user.0032";
	public static final String USER_POINT_INSUFFICIENT = "ErrorCode.user.0033";
	public static final String USER_NO_INVITATION = "ErrorCode.user.0034";
	public static final String USER_BALANCE_INSUFFICIENT = "ErrorCode.user.0035";
	
	//trainer
	public static final String TRAINER_NOT_SET_EMPTY = "ErrorCode.trainer.0001";
	public static final String TRAINER_NOT_DOWNGRADE = "ErrorCode.trainer.0002";
	public static final String TRAINER_PARENT_INVALID = "ErrorCode.trainer.0003";
	public static final String TRAINER_INVALID = "ErrorCode.trainer.0004";
	
	//performance
	public static final String PERFORMANCE_DUPLICATE = "ErrorCode.performance.0001";
	
	//task
	public static final String TASK_NOT_FOUND = "ErrorCode.task.0001";
	
	
	//login
	public static final String LOGIN_ID_PASSWORD_INCORRECT = "ErrorCode.login.0001";
	public static final String LOGIN_ID_LOCKED = "ErrorCode.login.0002";
	public static final String LOGIN_EXPIRED = "ErrorCode.login.0003";
	public static final String LOGIN_NOT_LOGIN = "ErrorCode.login.0004";
	public static final String LOGIN_USER_DISABLED = "ErrorCode.login.0005";
	public static final String LOGIN_PASSWORD_INCORRECT = "ErrorCode.login.0006";
	public static final String TRAN_PASSWORD_INCORRECT = "ErrorCode.login.0007";
	
	//Setting
	public static final String SETTING_NOT_FOUND = "ErrorCode.setting.0001";
	
	//Role
	public static final String ROLE_ADMIN_RESERVED = "ErrorCode.role.0001";
	public static final String ROLE_EMPTY = "ErrorCode.role.0002";
	public static final String ROLE_DUPLICATE = "ErrorCode.role.0003";
	public static final String ROLE_ADMIN_PERMISSION_DELETE = "ErrorCode.role.0004";
	public static final String ROLE_NO_PERMISSION = "ErrorCode.role.0005";
	
	
	public static final String MAIL_SENDER = null;
	public static final String MAIL_SUBJECT = null;
	public static final String RETRY_COUNT = null;
	public static final Throwable MAIL_SENT_ERR_CD = null;
	//通联支付
	public static final String ALLINPAY_CREATE_MEMBER_ERROR = "ErrorCode.allinpay.0001";
	public static final String ALLINPAY_APPLY_BIND_ACCT_ERROR = "ErrorCode.allinpay.0002";
	//同城配送
	public static final String DELIVERY_CONDITION_OVER_DISTANCE = "ErrorCode.delivery.0001";
	public static final String DELIVERY_CONDITION_LESS_AMOUNT = "ErrorCode.delivery.0002";
}
