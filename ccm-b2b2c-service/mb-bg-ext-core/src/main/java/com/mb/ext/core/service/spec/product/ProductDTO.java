package com.mb.ext.core.service.spec.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.entity.product.ProductDeliveryEntity;
import com.mb.ext.core.service.spec.FileDTO;
import com.mb.ext.core.service.spec.coupon.CouponDTO;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.promote.PromoteDiscountDTO;
import com.mb.ext.core.service.spec.promote.PromoteFreightOffDTO;
import com.mb.ext.core.service.spec.promote.PromoteMoneyOffDTO;
import com.mb.ext.core.service.spec.supplier.SupplierDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class ProductDTO extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	

	private String productUuid;

	private String productCode;
	
	private String productName;
	
	private String productBrief;
	
	public String getProductQrCodeUrl() {
		return productQrCodeUrl;
	}

	public void setProductQrCodeUrl(String productQrCodeUrl) {
		this.productQrCodeUrl = productQrCodeUrl;
	}

	//商品链接地址
	private String productQrCodeUrl;
	
	//商品海报地址
	private String posterUrl;
	
	public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	private BigDecimal productWeight;
	
	private BigDecimal productCostPrice;
	
	public BigDecimal getProductCostPrice() {
		return productCostPrice;
	}

	public void setProductCostPrice(BigDecimal productCostPrice) {
		this.productCostPrice = productCostPrice;
	}

	public BigDecimal getProductWeight() {
		return productWeight;
	}

	public void setProductWeight(BigDecimal productWeight) {
		this.productWeight = productWeight;
	}

	public List<ProductAttrValueDTO> getAttrList() {
		return attrList;
	}

	public void setAttrList(List<ProductAttrValueDTO> attrList) {
		this.attrList = attrList;
	}

	public List<ProductSkuDTO> getSkuList() {
		return skuList;
	}

	public void setSkuList(List<ProductSkuDTO> skuList) {
		this.skuList = skuList;
	}

	public List<ProductSkuAttrDTO> getSkuAttrList() {
		return skuAttrList;
	}

	public void setSkuAttrList(List<ProductSkuAttrDTO> skuAttrList) {
		this.skuAttrList = skuAttrList;
	}

	List<ProductAttrValueDTO> attrList = new ArrayList<ProductAttrValueDTO>();
	
	List<ProductSkuAttrDTO> skuAttrList = new ArrayList<ProductSkuAttrDTO>();
	
	List<ProductSkuDTO> skuList = new ArrayList<ProductSkuDTO>();
	
	public String getProductBrief() {
		return productBrief;
	}

	public void setProductBrief(String productBrief) {
		this.productBrief = productBrief;
	}

	private String productDesc;
	
	private String freightDesc;
	
	private BigDecimal baseNum;
	
	private String validType;
	
	private int validDays;
	
	private Date validStartDate;
	
	private Date validEndDate;
	
	public BigDecimal getBaseNum() {
		return baseNum;
	}

	public String getValidType() {
		return validType;
	}

	public void setValidType(String validType) {
		this.validType = validType;
	}

	public int getValidDays() {
		return validDays;
	}

	public void setValidDays(int validDays) {
		this.validDays = validDays;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	public Date getValidStartDate() {
		return validStartDate;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	public void setValidStartDate(Date validStartDate) {
		this.validStartDate = validStartDate;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	public Date getValidEndDate() {
		return validEndDate;
	}
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	public void setValidEndDate(Date validEndDate) {
		this.validEndDate = validEndDate;
	}

	public void setBaseNum(BigDecimal baseNum) {
		this.baseNum = baseNum;
	}

	public String getFreightDesc() {
		return freightDesc;
	}

	public void setFreightDesc(String freightDesc) {
		this.freightDesc = freightDesc;
	}

	private int totalUnit;
	
	private int soldUnit;
	
	private boolean isSkuEnabled;
	
	private BigDecimal promoteUnitPrice;
	
	private BigDecimal membershipUnitPrice;
	
	public BigDecimal getMembershipUnitPrice() {
		return membershipUnitPrice;
	}

	public String getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public String getVerifyMsg() {
		return verifyMsg;
	}

	public void setVerifyMsg(String verifyMsg) {
		this.verifyMsg = verifyMsg;
	}

	public void setMembershipUnitPrice(BigDecimal membershipUnitPrice) {
		this.membershipUnitPrice = membershipUnitPrice;
	}

	private BigDecimal marketUnitPrice;
	
	private Date promoteStartDate;
	
	private Date promoteEndDate;

	private int warnUnit;
	
	private String verifyStatus;
	
	private String verifyMsg;
	
	public int getUnitPoint() {
		return unitPoint;
	}

	public void setUnitPoint(int unitPoint) {
		this.unitPoint = unitPoint;
	}

	public int getUnitPointMin() {
		return unitPointMin;
	}

	public void setUnitPointMin(int unitPointMin) {
		this.unitPointMin = unitPointMin;
	}

	public int getUnitPointMax() {
		return unitPointMax;
	}

	public void setUnitPointMax(int unitPointMax) {
		this.unitPointMax = unitPointMax;
	}

	public int getUnitPointStandard() {
		return unitPointStandard;
	}

	public void setUnitPointStandard(int unitPointStandard) {
		this.unitPointStandard = unitPointStandard;
	}

	private boolean isNew;
	
	private boolean isHot;
	
	private boolean isPromote;
	
	private boolean isDeliveryExpressEnabled;
	
	private boolean isDeliveryCityEnabled;
	
	public boolean isDeliveryExpressEnabled() {
		return isDeliveryExpressEnabled;
	}

	public void setDeliveryExpressEnabled(boolean isDeliveryExpressEnabled) {
		this.isDeliveryExpressEnabled = isDeliveryExpressEnabled;
	}

	public boolean isDeliveryCityEnabled() {
		return isDeliveryCityEnabled;
	}

	public void setDeliveryCityEnabled(boolean isDeliveryCityEnabled) {
		this.isDeliveryCityEnabled = isDeliveryCityEnabled;
	}

	public boolean isDeliveryPickEnabled() {
		return isDeliveryPickEnabled;
	}

	public void setDeliveryPickEnabled(boolean isDeliveryPickEnabled) {
		this.isDeliveryPickEnabled = isDeliveryPickEnabled;
	}

	private boolean isDeliveryPickEnabled;
	
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getUnitPriceStandard() {
		return unitPriceStandard;
	}

	public void setUnitPriceStandard(BigDecimal unitPriceStandard) {
		this.unitPriceStandard = unitPriceStandard;
	}

	private int unitPoint;
	
	private BigDecimal unitPrice;
	
	private BigDecimal unitPriceStandard;
	
	public int getUnitPointStandardMin() {
		return unitPointStandardMin;
	}

	public void setUnitPointStandardMin(int unitPointStandardMin) {
		this.unitPointStandardMin = unitPointStandardMin;
	}

	public int getUnitPointStandardMax() {
		return unitPointStandardMax;
	}

	public void setUnitPointStandardMax(int unitPointStandardMax) {
		this.unitPointStandardMax = unitPointStandardMax;
	}

	private int unitPointMin;
	
	private int unitPointMax;
	
	private int unitPointStandard;
	
	private int unitPointStandardMin;
	
	private int unitPointStandardMax;
	
	public BigDecimal getUnitPriceMin() {
		return unitPriceMin;
	}

	public void setUnitPriceMin(BigDecimal unitPriceMin) {
		this.unitPriceMin = unitPriceMin;
	}

	public BigDecimal getUnitPriceMax() {
		return unitPriceMax;
	}

	public void setUnitPriceMax(BigDecimal unitPriceMax) {
		this.unitPriceMax = unitPriceMax;
	}

	public BigDecimal getUnitPriceStandardMin() {
		return unitPriceStandardMin;
	}

	public void setUnitPriceStandardMin(BigDecimal unitPriceStandardMin) {
		this.unitPriceStandardMin = unitPriceStandardMin;
	}

	public BigDecimal getUnitPriceStandardMax() {
		return unitPriceStandardMax;
	}

	public void setUnitPriceStandardMax(BigDecimal unitPriceStandardMax) {
		this.unitPriceStandardMax = unitPriceStandardMax;
	}

	private BigDecimal unitPriceMin;
	
	private BigDecimal unitPriceMax;
	
	private BigDecimal unitPriceStandardMin;
	
	private BigDecimal unitPriceStandardMax;
	
	private boolean isRecommend;
	
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

	public boolean isRecommend() {
		return isRecommend;
	}

	public void setRecommend(boolean isRecommend) {
		this.isRecommend = isRecommend;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public CouponDTO getCouponDTO() {
		return couponDTO;
	}

	public void setCouponDTO(CouponDTO couponDTO) {
		this.couponDTO = couponDTO;
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

	private String productType;
	
	private CouponDTO couponDTO;
	
	private boolean isOnSale;
	
	private boolean isShipping;
	
	private String memo;

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

	public ProductCateDTO getProductCateDTO() {
		return productCateDTO;
	}

	public void setProductCateDTO(ProductCateDTO productCateDTO) {
		this.productCateDTO = productCateDTO;
	}

	public ProductBrandDTO getProductBrandDTO() {
		return productBrandDTO;
	}

	public void setProductBrandDTO(ProductBrandDTO productBrandDTO) {
		this.productBrandDTO = productBrandDTO;
	}

	public FileDTO getProductMainImage() {
		return productMainImage;
	}

	public void setProductMainImage(FileDTO productMainImage) {
		this.productMainImage = productMainImage;
	}

	private ProductCateDTO productCateDTO;
	
	private ProductBrandDTO productBrandDTO;
	
	private FileDTO productMainImage;
	
	public ProductGroupDTO getProductGroupDTO() {
		return productGroupDTO;
	}

	public void setProductGroupDTO(ProductGroupDTO productGroupDTO) {
		this.productGroupDTO = productGroupDTO;
	}

	private ProductGroupDTO productGroupDTO;
	
	private List<FileDTO> productImages;
	
	private List<FileDTO> productDescImages;
	
	public List<FileDTO> getProductDescImages() {
		return productDescImages;
	}

	public void setProductDescImages(List<FileDTO> productDescImages) {
		this.productDescImages = productDescImages;
	}

	public List<FileDTO> getProductVideos() {
		return productVideos;
	}

	public void setProductVideos(List<FileDTO> productVideos) {
		this.productVideos = productVideos;
	}

	private List<FileDTO> productVideos;
	
	public List<FileDTO> getProductImages() {
		return productImages;
	}

	public void setProductImages(List<FileDTO> productImages) {
		this.productImages = productImages;
	}

	private SupplierDTO supplierDTO;
	
	public MerchantDTO getMerchantDTO() {
		return merchantDTO;
	}

	public void setMerchantDTO(MerchantDTO merchantDTO) {
		this.merchantDTO = merchantDTO;
	}

	private MerchantDTO merchantDTO;
	
	public String getMerchantName(){
		String merchantName = "";
		if(merchantDTO != null)
			merchantName = merchantDTO.getMerchantName();
		return merchantName;
	}
	
	public void setMerchantName(String merchnatName){
		
	}
	
	public String getMerchantMobileNo(){
		String merchantMobileNo = "";
		if(merchantDTO != null)
			merchantMobileNo = merchantDTO.getMobileNo();
		return merchantMobileNo;
	}
	
	public void setMerchantMobileNo(String merchnatMobileNo){
		
	}

	public SupplierDTO getSupplierDTO() {
		return supplierDTO;
	}

	public void setSupplierDTO(SupplierDTO supplierDTO) {
		this.supplierDTO = supplierDTO;
	}
	
	private BigDecimal freeFreightThreold;

	public BigDecimal getFreeFreightThreold() {
		return freeFreightThreold;
	}

	public void setFreeFreightThreold(BigDecimal freeFreightThreold) {
		this.freeFreightThreold = freeFreightThreold;
	}

	public BigDecimal getUnifiedFreightFee() {
		return unifiedFreightFee;
	}

	public void setUnifiedFreightFee(BigDecimal unifiedFreightFee) {
		this.unifiedFreightFee = unifiedFreightFee;
	}

	public ProductFreightDTO getProductFreightDTO() {
		return productFreightDTO;
	}

	public void setProductFreightDTO(ProductFreightDTO productFreightDTO) {
		this.productFreightDTO = productFreightDTO;
	}

	private BigDecimal unifiedFreightFee;

	private ProductFreightDTO productFreightDTO;
	
	private ProductDeliveryDTO productDeliveryDTO;
	
	public PromoteMoneyOffDTO getMoneyOffDTO() {
		return moneyOffDTO;
	}

	public ProductDeliveryDTO getProductDeliveryDTO() {
		return productDeliveryDTO;
	}

	public void setProductDeliveryDTO(ProductDeliveryDTO productDeliveryDTO) {
		this.productDeliveryDTO = productDeliveryDTO;
	}

	public PromoteDiscountDTO getDiscountDTO() {
		return discountDTO;
	}

	public void setDiscountDTO(PromoteDiscountDTO discountDTO) {
		this.discountDTO = discountDTO;
	}

	public void setMoneyOffDTO(PromoteMoneyOffDTO moneyOffDTO) {
		this.moneyOffDTO = moneyOffDTO;
	}

	private PromoteMoneyOffDTO moneyOffDTO;
	
	private PromoteDiscountDTO discountDTO;
	
	private PromoteFreightOffDTO freightOffDTO;

	public PromoteFreightOffDTO getFreightOffDTO() {
		return freightOffDTO;
	}

	public void setFreightOffDTO(PromoteFreightOffDTO freightOffDTO) {
		this.freightOffDTO = freightOffDTO;
	}
	
	int startIndex;
	
	int pageSize;

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
}