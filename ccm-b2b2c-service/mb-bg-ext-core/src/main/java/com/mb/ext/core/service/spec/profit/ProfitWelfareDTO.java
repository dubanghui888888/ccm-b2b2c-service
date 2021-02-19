
package com.mb.ext.core.service.spec.profit;

import com.mb.ext.core.entity.coupon.CouponEntity;
import com.mb.ext.core.entity.product.ProductEntity;
import com.mb.ext.core.service.spec.coupon.CouponDTO;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfitWelfareDTO extends AbstractBaseDTO
{
	private String profitWelfareUuid;

	private CouponDTO couponDTO;

	private int couponCount;

	public String getProfitWelfareUuid() {
		return profitWelfareUuid;
	}

	public void setProfitWelfareUuid(String profitWelfareUuid) {
		this.profitWelfareUuid = profitWelfareUuid;
	}

	public CouponDTO getCouponDTO() {
		return couponDTO;
	}

	public void setCouponDTO(CouponDTO couponDTO) {
		this.couponDTO = couponDTO;
	}

	public int getCouponCount() {
		return couponCount;
	}

	public void setCouponCount(int couponCount) {
		this.couponCount = couponCount;
	}

	public ProductDTO getProductDTO() {
		return productDTO;
	}

	public void setProductDTO(ProductDTO productDTO) {
		this.productDTO = productDTO;
	}

	public BigDecimal getProductUnitPrice() {
		return productUnitPrice;
	}

	public void setProductUnitPrice(BigDecimal productUnitPrice) {
		this.productUnitPrice = productUnitPrice;
	}

	public String getWelfareType() {
		return welfareType;
	}

	public void setWelfareType(String welfareType) {
		this.welfareType = welfareType;
	}

	public int getPointGiven() {
		return pointGiven;
	}

	public void setPointGiven(int pointGiven) {
		this.pointGiven = pointGiven;
	}

	public boolean isPointEnabled() {
		return pointEnabled;
	}

	public void setPointEnabled(boolean pointEnabled) {
		this.pointEnabled = pointEnabled;
	}

	public boolean isCouponEnabled() {
		return couponEnabled;
	}

	public void setCouponEnabled(boolean couponEnabled) {
		this.couponEnabled = couponEnabled;
	}

	public boolean isProductEnabled() {
		return productEnabled;
	}

	public void setProductEnabled(boolean productEnabled) {
		this.productEnabled = productEnabled;
	}

	private ProductDTO productDTO;

	private BigDecimal productUnitPrice;

	private String welfareType;

	private int pointGiven;

	private boolean pointEnabled;

	private boolean couponEnabled;

	private boolean productEnabled;

}
