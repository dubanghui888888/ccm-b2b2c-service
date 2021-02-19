package com.mb.ext.core.entity.point;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the point_product_cate_attr database table.
 * 
 */
@Entity
@Table(name = "point_product_cate_attr")
@NamedQuery(name = "PointProductCateAttrEntity.findAll", query = "SELECT u FROM PointProductCateAttrEntity u")
public class PointProductCateAttrEntity extends AbstractBaseEntity
{
	

	public String getProductCateAttrUuid() {
		return productCateAttrUuid;
	}

	public void setProductCateAttrUuid(String productCateAttrUuid) {
		this.productCateAttrUuid = productCateAttrUuid;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}

	public boolean isSpuAttr() {
		return isSpuAttr;
	}

	public void setSpuAttr(boolean isSpuAttr) {
		this.isSpuAttr = isSpuAttr;
	}

	public boolean isMandatory() {
		return isMandatory;
	}

	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	public boolean isMultiple() {
		return isMultiple;
	}

	public void setMultiple(boolean isMultiple) {
		this.isMultiple = isMultiple;
	}

	public boolean isInput() {
		return isInput;
	}

	public void setInput(boolean isInput) {
		this.isInput = isInput;
	}

	public PointProductCateEntity getProductCateEntity() {
		return productCateEntity;
	}

	public void setProductCateEntity(PointProductCateEntity productCateEntity) {
		this.productCateEntity = productCateEntity;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "PRODUCTCATEATTR_UUID")
	@GenericGenerator(name = "PRODUCTCATEATTR_UUID", strategy = "uuid")
	@Column(name = "PRODUCTCATEATTR_UUID", nullable = false, length = 36)
	private String productCateAttrUuid;
	//类目属性名称
	@Column(name = "ATTR_NAME", nullable = false, length = 55)
	private String attrName;
	//类目预定义属性值, 当IS_INPUT为false时需要填写; 如洗发水类目有品牌属性, 则可选的属性值可设置为['海飞丝, 潘婷'], 这样创建商品时就可以直接通过下拉框选择品牌
	@Column(name = "ATTR_VALUE", length = 500)
	private String attrValue;
	public boolean isSkuAttr() {
		return isSkuAttr;
	}

	public void setSkuAttr(boolean isSkuAttr) {
		this.isSkuAttr = isSkuAttr;
	}

	public boolean isSearchAttr() {
		return isSearchAttr;
	}

	public void setSearchAttr(boolean isSearchAttr) {
		this.isSearchAttr = isSearchAttr;
	}

	//是否关键属性, SPU代表一个商品单元, 关键属性必须填写
	@Column(name = "IS_SPU_ATTR")
	private boolean isSpuAttr;
	//是否销售属性, SKU代表一个商品存储单元, 如颜色, 尺寸, 每个SKU可有不同库存及价格
	@Column(name = "IS_SKU_ATTR")
	private boolean isSkuAttr;
	//属性是否可搜索
	@Column(name = "IS_SEARCH_ATTR")
	private boolean isSearchAttr;
	//创建商品时该属性是否必须填写值
	@Column(name = "IS_MANDATORY")
	private boolean isMandatory;
	//表示该属性值是否可多选
	@Column(name = "IS_MULTIPLE")
	private boolean isMultiple;
	//表示该属性值在创建商品时由创建人手动输入, 否则通过下拉框或者多选框选择
	@Column(name = "IS_INPUT")
	private boolean isInput;

	//类目外键
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTCATE_UUID")
	private PointProductCateEntity productCateEntity;
	
}