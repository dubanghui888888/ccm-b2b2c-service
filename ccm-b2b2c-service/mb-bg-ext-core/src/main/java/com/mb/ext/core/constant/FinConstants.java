package com.mb.ext.core.constant;

import java.math.BigDecimal;

public class FinConstants {
	
	public final static String DEFAULT_ACCOUNT = "默认账套";
	
	public final static String SUBJECTTYPE_ZICHAN = "资产";
	
	public final static String SUBJECTTYPE_FUZHAI = "负债";
	
	public final static String SUBJECTTYPE_QUANYI = "权益";
	
	public final static String SUBJECTTYPE_CHENGBEN = "成本";
	
	public final static String SUBJECTTYPE_SUNYI = "损益";
	
	public final static String SUBJECT_YEARPROFIT_CODE= "3103";
	
	public final static int DIRECTION_DEBIT = 1;
	
	public final static int DIRECTION_CREDIT = 2;
	
	public final static String ACCOUNT_STATUS_ACTIVE = "1";
	
	public final static String ACCOUNT_STATUS_INACTIVE = "0";
	
	public final static String VOUCHER_STATUS_NEW = "未审核";
	
	public final static String VOUCHER_STATUS_AUDITED = "已审核";
	
	public final static String DAYBOOK_INCOME = "收入";
	
	public final static String DAYBOOK_OUTCOME = "支出";
	
	public final static String PRINT_TYPE_VOUCHER = "VOUCHER";
	
	public final static String EXPORT_TYPE_ACCOUNTSUM = "ACCOUNTSUM";
	
	public final static BigDecimal ZERODecimal = new BigDecimal(0);
}
