package com.mb.ext.core.service.spec.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class ProductSkuDTO extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private String productSkuUuid;
	//规格编码
	private String skuCode;
	//规格属性, 该值可确定唯一规格
//	private String productAttrUuids;
	
	private List<ProductSkuAttrValueDTO> skuAttrValueList = new ArrayList<ProductSkuAttrValueDTO>();


	public List<ProductSkuAttrValueDTO> getSkuAttrValueList() {
		return skuAttrValueList;
	}

	public void setSkuAttrValueList(List<ProductSkuAttrValueDTO> skuAttrValueList) {
		this.skuAttrValueList = skuAttrValueList;
	}

	private int skuUnitPoint;
	
	private int skuUnitPointStandard;
	
	private BigDecimal skuUnitPrice;
	
	public BigDecimal getSkuUnitPrice() {
		return skuUnitPrice;
	}

	public void setSkuUnitPrice(BigDecimal skuUnitPrice) {
		this.skuUnitPrice = skuUnitPrice;
	}

	public BigDecimal getSkuUnitPriceStandard() {
		return skuUnitPriceStandard;
	}

	public void setSkuUnitPriceStandard(BigDecimal skuUnitPriceStandard) {
		this.skuUnitPriceStandard = skuUnitPriceStandard;
	}

	private BigDecimal skuUnitPriceStandard;
	
	private int skuTotalUnit;
	
	private int skuWarnUnit;
	
	private String skuImageUrl;
	
	public String getSkuImageUrl() {
		return skuImageUrl;
	}

	public void setSkuImageUrl(String skuImageUrl) {
		this.skuImageUrl = skuImageUrl;
	}

	public int getSkuUnitPointStandard() {
		return skuUnitPointStandard;
	}

	public void setSkuUnitPointStandard(int skuUnitPointStandard) {
		this.skuUnitPointStandard = skuUnitPointStandard;
	}

	private int skuSoldUnit;

	public int getSkuUnitPoint() {
		return skuUnitPoint;
	}

	public void setSkuUnitPoint(int skuUnitPoint) {
		this.skuUnitPoint = skuUnitPoint;
	}

	public int getSkuWarnUnit() {
		return skuWarnUnit;
	}

	public void setSkuWarnUnit(int skuWarnUnit) {
		this.skuWarnUnit = skuWarnUnit;
	}

	public String getProductSkuUuid() {
		return productSkuUuid;
	}

	public void setProductSkuUuid(String productSkuUuid) {
		this.productSkuUuid = productSkuUuid;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
/*
	public String getProductAttrUuids() {
		return productAttrUuids;
	}

	public void setProductAttrUuids(String productAttrUuids) {
		this.productAttrUuids = productAttrUuids;
	}

	public String getProductAttrValues() {
		return productAttrValues;
	}

	public void setProductAttrValues(String productAttrValues) {
		this.productAttrValues = productAttrValues;
	}*/

	public int getSkuTotalUnit() {
		return skuTotalUnit;
	}

	public void setSkuTotalUnit(int skuTotalUnit) {
		this.skuTotalUnit = skuTotalUnit;
	}

	public int getSkuSoldUnit() {
		return skuSoldUnit;
	}

	public void setSkuSoldUnit(int skuSoldUnit) {
		this.skuSoldUnit = skuSoldUnit;
	}

	public ProductDTO getProductDTO() {
		return productDTO;
	}

	public void setProductDTO(ProductDTO productDTO) {
		this.productDTO = productDTO;
	}

	private ProductDTO productDTO;

	
	/*public String toAttrUuids(){
		String result = "";
		result = result + "[";
		if(this.getAttrValueList() != null){
			Collections.sort(this.getAttrValueList());
			for(int i = 0; i<this.getAttrValueList().size();i++){
				ProductSkuAttrValue attrValue = this.getAttrValueList().get(i);
				if(i==0)
					result = attrValue.getProductAttrUuid();
				else
					result = ":"+attrValue.getProductAttrUuid();
			}
		}
		result = result + "]";
		return result;
	}
	public String toAttrValues(){
		String result = "";
		result = result + "[";
		if(this.getAttrValueList() != null){
			Collections.sort(this.getAttrValueList());
			for(int i = 0; i<this.getAttrValueList().size();i++){
				ProductSkuAttrValue attrValue = this.getAttrValueList().get(i);
				if(i==0)
					result = attrValue.getProductAttrValue();
				else
					result = ":"+attrValue.getProductAttrValue();
			}
		}
		result = result + "]";
		return result;
	}
	
	public void convertAttrValueToList(String attrUuids, String attrValues){
		String vUuids= attrUuids.replace("[", "").replace("]", "");
		String vValues = attrUuids.replace("[", "").replace("]", "");

		String[] uuidArray = vUuids.split("\\:");
		String[] valueArray = vValues.split("\\:");
		
		if(uuidArray.length==valueArray.length){
			List<ProductSkuAttrValue> list = new ArrayList<ProductSkuAttrValue>();
			for(int i=0;i<uuidArray.length;i++){
				String uuid = uuidArray[i];
				String value = valueArray[i];
				ProductSkuAttrValue obj = new ProductSkuAttrValue();
				obj.setProductAttrUuid(uuid);
				obj.setProductAttrValue(value);
				list.add(obj);
			}
			this.setAttrValueList(list);
		}
		
		
		
	}*/
	
}
