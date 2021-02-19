package com.mb.ext.core.entity.product;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the product_cate database table.
 * 
 */
@Entity
@Table(name = "product_cate")
@NamedQuery(name = "ProductCateEntity.findAll", query = "SELECT u FROM ProductCateEntity u")
public class ProductCateEntity extends AbstractBaseEntity
{
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "PRODUCTCATE_UUID")
	@GenericGenerator(name = "PRODUCTCATE_UUID", strategy = "uuid")
	@Column(name = "PRODUCTCATE_UUID", nullable = false, length = 36)
	private String productCateUuid;

	public List<ProductCateEntity> getChildList() {
		return childList;
	}

	public void setChildList(List<ProductCateEntity> childList) {
		this.childList = childList;
	}

	@Column(name = "CATE_NAME", nullable = false, length = 45)
	private String cateName;
	
	@Column(name = "CATE_PATH", length = 500)
	private String catePath;
	
	public boolean isDisplayedHome() {
		return isDisplayedHome;
	}

	public void setDisplayedHome(boolean isDisplayedHome) {
		this.isDisplayedHome = isDisplayedHome;
	}

	@Column(name = "CATE_PIC_URL", length = 200)
	private String catePicUrl;
	
	@Column(name = "SORTNUMBER")
	private int sortNumber;
	
	@Column(name = "IS_DISPLAYED_HOME")
	private boolean isDisplayedHome;
	
	@Column(name = "IS_LEAF")
	private boolean isLeaf;
	
	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getProductCateUuid() {
		return productCateUuid;
	}

	public void setProductCateUuid(String productCateUuid) {
		this.productCateUuid = productCateUuid;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public String getCatePath() {
		return catePath;
	}

	public void setCatePath(String catePath) {
		this.catePath = catePath;
	}

	public String getCatePicUrl() {
		return catePicUrl;
	}

	public void setCatePicUrl(String catePicUrl) {
		this.catePicUrl = catePicUrl;
	}

	public int getSortNumber() {
		return sortNumber;
	}

	public void setSortNumber(int sortNumber) {
		this.sortNumber = sortNumber;
	}

	public ProductCateEntity getParentCateEntity() {
		return parentCateEntity;
	}

	public void setParentCateEntity(ProductCateEntity parentCateEntity) {
		this.parentCateEntity = parentCateEntity;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENTCATE_UUID")
	private ProductCateEntity parentCateEntity;
	
	@OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name = "PARENTCATE_UUID")
	@Where(clause="IS_DELETED = 0")
    private List<ProductCateEntity> childList;
	
	public List<ProductCateAttrEntity> getAttrList() {
		return attrList;
	}

	public void setAttrList(List<ProductCateAttrEntity> attrList) {
		this.attrList = attrList;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "productCateEntity")
	private List<ProductCateAttrEntity> attrList;
	
}