
package com.mb.ext.core.service.spec;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.util.JsonDateSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class MerchantSearchDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	public static final String KEY_MERCHANTUUID = "MERCHANTUUID";
	
	public static final String KEY_MERCHANTADDRESS = "MERCHANTADDRESS";
	
	public static final String KEY_MERCHANTNAME = "MERCHANTNAME";
	
	public static final String KEY_NAME = "NAME";
	
	public static final String KEY_PROVINCE = "PROVINCE";
	
	public static final String KEY_CITY = "CITY";
	
	public static final String KEY_LOCATION = "LOCATION";

	public static final String KEY_HAS_COUPON = "HAS_COUPON";
	
	public static final String KEY_MOBILENO = "MOBILENO";

	public static final String KEY_USER = "USER";
	
	public static final String KEY_REFERRER = "REFERRER";
	
	public static final String KEY_REGISTERDATE = "REGISTERDATE";
	
	public static final String KEY_RANKING_DATE = "RANKINGDATE";
	
	public static final String SORT_BY = "SORT_BY";

	String[] keyArray = new String[]{};
	
	String sortBy;
	
	int startIndex;
	
	int pageSize;
	
	int total;
	
	String sorts;
	
	Date rankingDateStart;//排行开始日期
	
	Date rankingDateEnd;//排行结束日期

	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getRankingDateStart() {
		return rankingDateStart;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public void setRankingDateStart(Date rankingDateStart) {
		this.rankingDateStart = rankingDateStart;
	}

	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getRankingDateEnd() {
		return rankingDateEnd;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public void setRankingDateEnd(Date rankingDateEnd) {
		this.rankingDateEnd = rankingDateEnd;
	}

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

	public String getReferrer() {
		return referrer;
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	
	String referrer;	//推荐人

	String merchantAddress;	//地址
	
	String merchantName;	//商家名称
	
	String name;	//姓名
	
	public Double getLatitude() {
		return latitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	String mobileNo;	//电话号码
	
	String province;	//所在省
	
	private Double latitude;	//经纬度
	
	private Double longitude;
	
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	String city;	//所在城市

	public String getMerchantUuid() {
		return merchantUuid;
	}

	public void setMerchantUuid(String merchantUuid) {
		this.merchantUuid = merchantUuid;
	}

	String merchantUuid;

	String userUuid;

	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	public String getMerchantAddress() {
		return merchantAddress;
	}

	public void setMerchantAddress(String merchantAddress) {
		this.merchantAddress = merchantAddress;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
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

	Date registerDateEnd;	//注册日期
	
	public String[] getKeyArray() {
		return keyArray;
	}

	public void setKeyArray(String[] keyArray) {
		this.keyArray = keyArray;
	}
	
}
