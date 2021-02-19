package com.mb.ext.core.service.spec.order;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.point.PointProductDTO;
import com.mb.ext.core.service.spec.point.PointProductSkuDTO;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.ext.core.service.spec.product.ProductSkuDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class OrderProductDTO extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	public int getProductUnit() {
		return productUnit;
	}

	public void setProductUnit(int productUnit) {
		this.productUnit = productUnit;
	}

	public BigDecimal getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(BigDecimal productAmount) {
		this.productAmount = productAmount;
	}

	public BigDecimal getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}

	public BigDecimal getProductUnitPrice() {
		return productUnitPrice;
	}

	public void setProductUnitPrice(BigDecimal productUnitPrice) {
		this.productUnitPrice = productUnitPrice;
	}

	private static final long serialVersionUID = 1L;

	private String orderProductUuid;

	private int productUnitPoint;
	
	private BigDecimal productUnitPrice;
	
	private int productUnit;
	
	private String productCode;
	
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public int getProductUnitPoint() {
		return productUnitPoint;
	}

	public void setProductUnitPoint(int productUnitPoint) {
		this.productUnitPoint = productUnitPoint;
	}

	private BigDecimal productAmount;
	
	private int productPoint;

	public int getProductPoint() {
		return productPoint;
	}

	public void setProductPoint(int productPoint) {
		this.productPoint = productPoint;
	}

	public int getActualPoint() {
		return actualPoint;
	}

	public void setActualPoint(int actualPoint) {
		this.actualPoint = actualPoint;
	}

	private BigDecimal actualAmount;
	
	private int actualPoint;
	
	private ProductDTO productDTO;
	
	private PointProductDTO pointProductDTO;
	
	private PointProductSkuDTO pointProductSkuDTO;
	
	public String getOrderProductUuid() {
		return orderProductUuid;
	}

	public void setOrderProductUuid(String orderProductUuid) {
		this.orderProductUuid = orderProductUuid;
	}

	public PointProductDTO getPointProductDTO() {
		return pointProductDTO;
	}

	public void setPointProductDTO(PointProductDTO pointProductDTO) {
		this.pointProductDTO = pointProductDTO;
	}

	public PointProductSkuDTO getPointProductSkuDTO() {
		return pointProductSkuDTO;
	}

	public void setPointProductSkuDTO(PointProductSkuDTO pointProductSkuDTO) {
		this.pointProductSkuDTO = pointProductSkuDTO;
	}

	private OrderDTO orderDTO;
	
	private ProductSkuDTO productSkuDTO;
	
	public ProductDTO getProductDTO() {
		return productDTO;
	}

	public void setProductDTO(ProductDTO productDTO) {
		this.productDTO = productDTO;
	}

	public OrderDTO getOrderDTO() {
		return orderDTO;
	}

	public void setOrderDTO(OrderDTO orderDTO) {
		this.orderDTO = orderDTO;
	}

	public ProductSkuDTO getProductSkuDTO() {
		return productSkuDTO;
	}

	public void setProductSkuDTO(ProductSkuDTO productSkuDTO) {
		this.productSkuDTO = productSkuDTO;
	}

	private String productName;
	
	private String productImageUrl;
	
	private String productSkuDesc;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductImageUrl() {
		return productImageUrl;
	}

	public void setProductImageUrl(String productImageUrl) {
		this.productImageUrl = productImageUrl;
	}

	public String getProductSkuDesc() {
		return productSkuDesc;
	}

	public void setProductSkuDesc(String productSkuDesc) {
		this.productSkuDesc = productSkuDesc;
	}
	
}
