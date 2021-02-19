
package com.mb.ext.core.service.spec;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.merchant.MerchantDTO;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class GroupDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private String groupUuid;

	private String groupName;
	
	private String groupDescription;
	
	private MerchantDTO merchantDTO;
	
	public boolean isRegister() {
		return isRegister;
	}

	public MerchantDTO getMerchantDTO() {
		return merchantDTO;
	}

	public void setMerchantDTO(MerchantDTO merchantDTO) {
		this.merchantDTO = merchantDTO;
	}

	public void setRegister(boolean isRegister) {
		this.isRegister = isRegister;
	}

	private boolean isRegister;
	
	public String getGroupUuid() {
		return groupUuid;
	}

	public void setGroupUuid(String groupUuid) {
		this.groupUuid = groupUuid;
	}

	public String getGroupName() {
		return groupName;
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

	public List<ProductDTO> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductDTO> productList) {
		this.productList = productList;
	}

	private boolean isDisplayedHome;
	
	private int sortNumber;
	
	private String backgroundUrl;
	
	private String iconUrl;

	private List<ProductDTO> productList;


}
