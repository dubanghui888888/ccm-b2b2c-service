
package com.mb.ext.core.service.spec;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class FileDTO extends AbstractBaseDTO
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4913561824427270117L;

	private String fileUuid;

	private String fileName;
	
	private int size;
	
	private String ossKey;
	
	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	private UserDTO userDTO;
	
	public String getFileUuid() {
		return fileUuid;
	}

	public void setFileUuid(String fileUuid) {
		this.fileUuid = fileUuid;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getOssKey() {
		return ossKey;
	}

	public void setOssKey(String ossKey) {
		this.ossKey = ossKey;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	private String url;
	

}
