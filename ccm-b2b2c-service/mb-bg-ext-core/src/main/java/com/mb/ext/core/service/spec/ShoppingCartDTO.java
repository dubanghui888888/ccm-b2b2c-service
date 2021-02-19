
package com.mb.ext.core.service.spec;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.product.ProductDTO;
import com.mb.ext.core.service.spec.product.ProductSkuDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingCartDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;
	
	public String getShoppingCartUuid() {
		return shoppingCartUuid;
	}

	public void setShoppingCartUuid(String shoppingCartUuid) {
		this.shoppingCartUuid = shoppingCartUuid;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public ProductDTO getProductDTO() {
		return productDTO;
	}

	public void setProductDTO(ProductDTO productDTO) {
		this.productDTO = productDTO;
	}

	public int getUnit() {
		return unit;
	}

	public ProductSkuDTO getProductSkuDTO() {
		return productSkuDTO;
	}

	public void setProductSkuDTO(ProductSkuDTO productSkuDTO) {
		this.productSkuDTO = productSkuDTO;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	private String shoppingCartUuid;
	
	private UserDTO userDTO;
	
	private ProductDTO productDTO;
	
	private ProductSkuDTO productSkuDTO;
	
	private int unit;
	}
