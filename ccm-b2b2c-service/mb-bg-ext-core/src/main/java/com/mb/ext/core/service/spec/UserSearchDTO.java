
package com.mb.ext.core.service.spec;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.util.JsonDateSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class UserSearchDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	public static final String KEY_NAME = "NAME";
	
	public static final String KEY_PERSONALPHONE = "PERSONALPHONE";
	
	public static final String KEY_TRAINERLEVEL = "TRAINERLEVEL";
	
	public static final String KEY_PARENTTRAINER = "PARENTTRAINER";
	
	public static final String KEY_MERCHANT = "MERCHANT";
	
	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public static String getKeyPersonalphone() {
		return KEY_PERSONALPHONE;
	}

	public String getTrainerLevel() {
		return trainerLevel;
	}

	public void setTrainerLevel(String trainerLevel) {
		this.trainerLevel = trainerLevel;
	}

	public String getParentTrainerUuid() {
		return parentTrainerUuid;
	}

	public void setParentTrainerUuid(String parentTrainerUuid) {
		this.parentTrainerUuid = parentTrainerUuid;
	}

	public static final String KEY_IDCARDNO = "IDCARDNO";
	
	public static final String KEY_REGISTERDATE = "REGISTERDATE";
	
	public static final String KEY_EFFECTIVEDATE = "EFFECTIVEDATE";
	
	public static final String KEY_LEVEL = "LEVEL";
	
	public static final String SORT_BY = "SORT_BY";
	
	public static final String KEY_RANKING_DATE = "RANKINGDATE";

	String[] keyArray = new String[]{};
	
	String sortBy;
	
	public String getMerchantUuid() {
		return merchantUuid;
	}

	public void setMerchantUuid(String merchantUuid) {
		this.merchantUuid = merchantUuid;
	}

	private String userLevelUuid;
	
	String idCardNo; 	//身份证号码
	
	String trainerLevel; 	//训练师级别
	
	String parentTrainerUuid;	//所属训练师
	
	String merchantUuid;
	
	public String getUserLevelUuid() {
		return userLevelUuid;
	}

	public void setUserLevelUuid(String userLevelUuid) {
		this.userLevelUuid = userLevelUuid;
	}

	int startIndex;
	
	int pageSize;
	
	int total;
	
	String sorts;

	public String getSorts() {
		return sorts;
	}

	public void setSorts(String sorts) {
		this.sorts = sorts;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	String name;	//姓名
	
	String personalPhone;	//电话号码

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPersonalPhone() {
		return personalPhone;
	}

	public void setPersonalPhone(String personalPhone) {
		this.personalPhone = personalPhone;
	}
	
	Date registerDateStart;	//注册日期
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getRegisterDateStart() {
		return registerDateStart;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setRegisterDateStart(Date registerDateStart) {
		this.registerDateStart = registerDateStart;
	}

	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getRegisterDateEnd() {
		return registerDateEnd;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setRegisterDateEnd(Date registerDateEnd) {
		this.registerDateEnd = registerDateEnd;
	}
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getEffectiveDateStart() {
		return effectiveDateStart;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setEffectiveDateStart(Date effectiveDateStart) {
		this.effectiveDateStart = effectiveDateStart;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getEffectiveDateEnd() {
		return effectiveDateEnd;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public void setEffectiveDateEnd(Date effectiveDateEnd) {
		this.effectiveDateEnd = effectiveDateEnd;
	}

	public MerchantDTO getMerchantDTO() {
		return merchantDTO;
	}

	public void setMerchantDTO(MerchantDTO merchantDTO) {
		this.merchantDTO = merchantDTO;
	}

	Date registerDateEnd;	//注册日期
	
	Date effectiveDateEnd;
	
	Date effectiveDateStart;

	public String[] getKeyArray() {
		return keyArray;
	}

	public void setKeyArray(String[] keyArray) {
		this.keyArray = keyArray;
	}
	
	MerchantDTO merchantDTO;
	
}
