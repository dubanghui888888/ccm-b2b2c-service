
package com.mb.ext.core.service.spec.coupon;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class VoucherDTOList extends AbstractBaseDTO
{
	
	private static final long serialVersionUID = 1L;

	private List<UserVoucherDTO> vouchers = new ArrayList<UserVoucherDTO>();
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	private int total;

	public List<UserVoucherDTO> getVouchers() {
		return vouchers;
	}

	public void setVouchers(List<UserVoucherDTO> vouchers) {
		this.vouchers = vouchers;
	}
}
