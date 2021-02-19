package com.mb.ext.core.entity.point;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.ext.core.entity.FileEntity;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.product.ProductBrandEntity;
import com.mb.ext.core.entity.product.ProductFreightEntity;
import com.mb.ext.core.entity.supplier.SupplierEntity;
import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the point_product database table.
 * 
 */
@Entity
@Table(name = "point_product")
@NamedQuery(name = "PointProductEntity.findAll", query = "SELECT u FROM PointProductEntity u")
public class PointProductEntity extends AbstractBaseEntity
{

	private static final long serialVersionUID = 1L;

	public int getUnitPointStandard() {
		return unitPointStandard;
	}

	public void setUnitPointStandard(int unitPointStandard) {
		this.unitPointStandard = unitPointStandard;
	}
	@Id
	@GeneratedValue(generator = "PRODUCT_UUID")
	@GenericGenerator(name = "PRODUCT_UUID", strategy = "uuid")
	@Column(name = "PRODUCT_UUID", nullable = false, length = 36)
	private String productUuid;

	public String getProductUuid() {
		return productUuid;
	}

	public void setProductUuid(String productUuid) {
		this.productUuid = productUuid;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getTotalUnit() {
		return totalUnit;
	}

	public void setTotalUnit(int totalUnit) {
		this.totalUnit = totalUnit;
	}

	public int getSoldUnit() {
		return soldUnit;
	}

	public void setSoldUnit(int soldUnit) {
		this.soldUnit = soldUnit;
	}

	public boolean isSkuEnabled() {
		return isSkuEnabled;
	}

	public void setSkuEnabled(boolean isSkuEnabled) {
		this.isSkuEnabled = isSkuEnabled;
	}

	public ProductBrandEntity getProductBrandEntity() {
		return productBrandEntity;
	}

	public void setProductBrandEntity(ProductBrandEntity productBrandEntity) {
		this.productBrandEntity = productBrandEntity;
	}
	//商品编码
	@Column(name = "PRODUCT_CODE", length = 45)
	private String productCode;
	//商品名称
	@Column(name = "PRODUCT_NAME", length = 45)
	private String productName;
	//商品简短描述
	@Column(name = "PRODUCT_BRIEF", length = 100)
	private String productBrief;
	//商品详细描述
	@Column(name = "PRODUCT_DESC")
	private String productDesc;
	//商品成本价
	@Column(name = "PRODUCT_COST_PRICE")
	private BigDecimal productCostPrice;
	
	public BigDecimal getProductCostPrice() {
		return productCostPrice;
	}

	public void setProductCostPrice(BigDecimal productCostPrice) {
		this.productCostPrice = productCostPrice;
	}

	//商品重量
	@Column(name = "PRODUCT_WEIGHT")
	private BigDecimal productWeight;
	public BigDecimal getProductWeight() {
		return productWeight;
	}

	public void setProductWeight(BigDecimal productWeight) {
		this.productWeight = productWeight;
	}
	//商品单价
	@Column(name = "UNIT_PRICE")
	private BigDecimal unitPrice;
	//商品市场价格
	@Column(name = "UNIT_PRICE_STANDARD")
	private BigDecimal unitPriceStandard;
	public BigDecimal getUnitPriceStandard() {
		return unitPriceStandard;
	}

	public void setUnitPriceStandard(BigDecimal unitPriceStandard) {
		this.unitPriceStandard = unitPriceStandard;
	}
	//商品总库存数量
	@Column(name = "TOTALUNIT")
	private int totalUnit;
	//商品已售数量
	@Column(name = "SOLDUNIT")
	private int soldUnit;
	//是否多规格商品
	@Column(name = "IS_SKU_ENABLED")
	private boolean isSkuEnabled;
	
	public PointProductCateEntity getProductCateEntity() {
		return productCateEntity;
	}

	public void setProductCateEntity(PointProductCateEntity productCateEntity) {
		this.productCateEntity = productCateEntity;
	}
	//商品品类
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTCATE_UUID")
	private PointProductCateEntity productCateEntity;
	//品牌
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTBRAND_UUID")
	private ProductBrandEntity productBrandEntity;
	
	public FileEntity getProductMainImage() {
		return productMainImage;
	}

	public void setProductMainImage(FileEntity productMainImage) {
		this.productMainImage = productMainImage;
	}
	//商品主图
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_MAIN_IMAGE_UUID")
	private FileEntity productMainImage;
	//商品所属供应商
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUPPLIER_UUID")
	private SupplierEntity supplierEntity;
	//商品所属商家
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_UUID")
	private MerchantEntity merchantEntity;
	
	public MerchantEntity getMerchantEntity() {
		return merchantEntity;
	}

	public void setMerchantEntity(MerchantEntity merchantEntity) {
		this.merchantEntity = merchantEntity;
	}

	public ProductFreightEntity getProductFreightEntity() {
		return productFreightEntity;
	}

	public void setProductFreightEntity(ProductFreightEntity productFreightEntity) {
		this.productFreightEntity = productFreightEntity;
	}
	//商品运费模板
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTFREIGHT_UUID")
	private ProductFreightEntity productFreightEntity;
	
	public SupplierEntity getSupplierEntity() {
		return supplierEntity;
	}

	public void setSupplierEntity(SupplierEntity supplierEntity) {
		this.supplierEntity = supplierEntity;
	}

	//促销单价
	@Column(name = "PROMOTE_UNIT_PRICE")
	private BigDecimal promoteUnitPrice;
	
	//会员单价
	@Column(name = "MEMBERSHIP_UNIT_PRICE")
	private BigDecimal membershipUnitPrice;
	//市场价
	@Column(name = "MARKET_UNIT_PRICE")
	private BigDecimal marketUnitPrice;
	//促销开始时间
	@Column(name = "PROMOTE_START_DATE")
	private Date promoteStartDate;
	//促销结束时间
	@Column(name = "PROMOTE_END_DATE")
	private Date promoteEndDate;
	//库存预警数量
	@Column(name = "WARN_UNIT")
	private int warnUnit;
	//是否新品
	@Column(name = "IS_NEW")
	private boolean isNew;
	
	public BigDecimal getPromoteUnitPrice() {
		return promoteUnitPrice;
	}

	public void setPromoteUnitPrice(BigDecimal promoteUnitPrice) {
		this.promoteUnitPrice = promoteUnitPrice;
	}

	public BigDecimal getMarketUnitPrice() {
		return marketUnitPrice;
	}

	public void setMarketUnitPrice(BigDecimal marketUnitPrice) {
		this.marketUnitPrice = marketUnitPrice;
	}


	public String getProductBrief() {
		return productBrief;
	}

	public void setProductBrief(String productBrief) {
		this.productBrief = productBrief;
	}

	public Date getPromoteStartDate() {
		return promoteStartDate;
	}

	public void setPromoteStartDate(Date promoteStartDate) {
		this.promoteStartDate = promoteStartDate;
	}

	public Date getPromoteEndDate() {
		return promoteEndDate;
	}

	public void setPromoteEndDate(Date promoteEndDate) {
		this.promoteEndDate = promoteEndDate;
	}

	public int getWarnUnit() {
		return warnUnit;
	}

	public void setWarnUnit(int warnUnit) {
		this.warnUnit = warnUnit;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public boolean isHot() {
		return isHot;
	}

	public void setHot(boolean isHot) {
		this.isHot = isHot;
	}

	public boolean isPromote() {
		return isPromote;
	}

	public void setPromote(boolean isPromote) {
		this.isPromote = isPromote;
	}

	public int getUnitPoint() {
		return unitPoint;
	}

	public void setUnitPoint(int unitPoint) {
		this.unitPoint = unitPoint;
	}

	public boolean isRecommend() {
		return isRecommend;
	}

	public void setRecommend(boolean isRecommend) {
		this.isRecommend = isRecommend;
	}

	public boolean isOnSale() {
		return isOnSale;
	}

	public void setOnSale(boolean isOnSale) {
		this.isOnSale = isOnSale;
	}

	public boolean isShipping() {
		return isShipping;
	}

	public void setShipping(boolean isShipping) {
		this.isShipping = isShipping;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	//是否热销
	@Column(name = "IS_HOT")
	private boolean isHot;
	//是否参与促销
	@Column(name = "IS_PROMOTE")
	private boolean isPromote;
	//购买需要实际积分数
	@Column(name = "UNIT_POINT")
	private int unitPoint;
	
	//购买需要h划线积分数
	@Column(name = "UNIT_POINT_STANDARD")
	private int unitPointStandard;
	
	public BigDecimal getMembershipUnitPrice() {
		return membershipUnitPrice;
	}

	public void setMembershipUnitPrice(BigDecimal membershipUnitPrice) {
		this.membershipUnitPrice = membershipUnitPrice;
	}
	//是否推荐商品
	@Column(name = "IS_RECOMMEND")
	private boolean isRecommend;
	//商品类型, 实物或者优惠券
	@Column(name = "PRODUCT_TYPE")
	private String productType;
	//上架,下架
	@Column(name = "IS_ON_SALE")
	private boolean isOnSale;
	//是否需要邮寄
	@Column(name = "IS_SHIPPING")
	private boolean isShipping;
	//商家备注
	@Column(name = "MEMO")
	private String memo;
	
	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}
	
}