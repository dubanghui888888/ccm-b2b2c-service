package com.mb.ext.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the file database table.
 * 
 */
@Entity
@Table(name = "file")
@NamedQuery(name = "FileEntity.findAll", query = "SELECT u FROM FileEntity u")
public class FileEntity extends AbstractBaseEntity
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "FILE_UUID")
	@GenericGenerator(name = "FILE_UUID", strategy = "uuid")
	@Column(name = "FILE_UUID", nullable = false, length = 36)
	private String fileUuid;

	@Column(name = "FILE_NAME", nullable = false, length = 100)
	private String fileName;
	
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


	@Column(name = "SIZE")
	private int size;
	
	@Column(name = "OSS_KEY", length = 200)
	private String ossKey;
	
	@Column(name = "URL", length = 500)
	private String url;

	
}