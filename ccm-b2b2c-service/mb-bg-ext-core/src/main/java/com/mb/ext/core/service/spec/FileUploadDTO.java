package com.mb.ext.core.service.spec;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;

@JsonInclude(Include.NON_NULL)
public class FileUploadDTO extends AbstractBaseDTO
{
	private static final long serialVersionUID = 5069755318813643726L;

	private HeaderDTO header;

	private String documentName;

	private String inputFile;
	
	private String accessInfoId;

	/**
	 * @return the header
	 */
	public HeaderDTO getHeader()
	{
		return header;
	}

	/**
	 * @param header
	 *            the header to set
	 */
	public void setHeader(HeaderDTO header)
	{
		this.header = header;
	}

	/**
	 * @return the documentName
	 */
	public String getDocumentName()
	{
		return documentName;
	}

	/**
	 * @param documentName
	 *            the documentName to set
	 */
	public void setDocumentName(String documentName)
	{
		this.documentName = documentName;
	}

	/**
	 * @return the inputFile
	 */
	public String getInputFile()
	{
		return inputFile;
	}

	/**
	 * @param inputFile
	 *            the inputFile to set
	 */
	public void setInputFile(String inputFile)
	{
		this.inputFile = inputFile;
	}

	public String getAccessInfoId() {
		return accessInfoId;
	}

	public void setAccessInfoId(String accessInfoId) {
		this.accessInfoId = accessInfoId;
	}
	
	

}
