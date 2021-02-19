
package com.mb.ext.core.service.spec;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class FileDTOList extends AbstractBaseDTO
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4913561824427270117L;

	private List<FileDTO> files = new ArrayList<FileDTO>();

	public List<FileDTO> getFiles() {
		return files;
	}

	public void setFiles(List<FileDTO> files) {
		this.files = files;
	}
	

}
