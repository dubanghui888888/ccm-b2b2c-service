package com.mb.ext.core.service.spec.product;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class ProductCommentDTOList extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private List<ProductCommentDTO> commentList = new ArrayList<ProductCommentDTO>();
	
	private int total;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<ProductCommentDTO> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<ProductCommentDTO> commentList) {
		this.commentList = commentList;
	}

	
}
