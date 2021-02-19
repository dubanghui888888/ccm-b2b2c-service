package com.mb.ext.core.entity;

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

import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.product.ProductGroupEntity;
import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the group database table.
 * 
 */
@Entity
@Table(name = "`group`")
@NamedQuery(name = "GroupEntity.findAll", query = "SELECT u FROM GroupEntity u")
public class GroupEntity extends AbstractBaseEntity
{
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "GROUP_UUID")
	@GenericGenerator(name = "GROUP_UUID", strategy = "uuid")
	@Column(name = "GROUP_UUID", nullable = false, length = 36)
	private String groupUuid;

	@Column(name = "GROUP_NAME")
	private String groupName;
	
	@Column(name = "GROUP_DESCRIPTION")
	private String groupDescription;
	
	@Column(name = "IS_DISPLAYED_HOME")
	private boolean isDisplayedHome;
	
	public boolean isRegister() {
		return isRegister;
	}

	public void setRegister(boolean isRegister) {
		this.isRegister = isRegister;
	}

	@Column(name = "IS_REGISTER")
	private boolean isRegister;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "groupEntity")
	private List<ProductGroupEntity> productGroupList;
	
	public String getGroupName() {
		return groupName;
	}

	public List<ProductGroupEntity> getProductGroupList() {
		return productGroupList;
	}

	public void setProductGroupList(List<ProductGroupEntity> productGroupList) {
		this.productGroupList = productGroupList;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupDescription() {
		return groupDescription;
	}

	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}

	public boolean isDisplayedHome() {
		return isDisplayedHome;
	}

	public void setDisplayedHome(boolean isDisplayedHome) {
		this.isDisplayedHome = isDisplayedHome;
	}

	public String getBackgroundUrl() {
		return backgroundUrl;
	}

	public int getSortNumber() {
		return sortNumber;
	}

	public void setSortNumber(int sortNumber) {
		this.sortNumber = sortNumber;
	}

	public void setBackgroundUrl(String backgroundUrl) {
		this.backgroundUrl = backgroundUrl;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public MerchantEntity getMerchantEntity() {
		return merchantEntity;
	}

	public void setMerchantEntity(MerchantEntity merchantEntity) {
		this.merchantEntity = merchantEntity;
	}

	@Column(name = "SORT_NUMBER")
	private int sortNumber;
	
	@Column(name = "BACKGROUND_URL")
	private String backgroundUrl;
	
	@Column(name = "ICON_URL")
	private String iconUrl;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_UUID")
	private MerchantEntity merchantEntity;
	

	public String getGroupUuid() {
		return groupUuid;
	}

	public void setGroupUuid(String groupUuid) {
		this.groupUuid = groupUuid;
	}

}