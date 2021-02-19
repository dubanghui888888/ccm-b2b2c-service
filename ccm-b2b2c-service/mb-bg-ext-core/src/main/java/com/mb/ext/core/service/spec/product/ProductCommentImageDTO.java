package com.mb.ext.core.service.spec.product;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.service.spec.UserDTO;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class ProductCommentImageDTO extends AbstractBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7516252502556396978L;
	
	private String productCommentUuid;

	private String commentContent;

	private String replayContent;
	
	private String commentRank;
	
	private boolean showStatus;
	
	private UserDTO userDTO;
	
	private List<ProductCommentImageDTO> imageList;

	
}
