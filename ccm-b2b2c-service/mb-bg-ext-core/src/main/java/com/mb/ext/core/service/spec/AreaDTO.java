package com.mb.ext.core.service.spec;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)

public class AreaDTO extends AbstractBaseDTO{
	
	private static final long serialVersionUID = 1262614150217776706L;

	private String areaId;

	private String name;
	
	private int depth;
	
	private String postalCode;
	
	private String firstCharacter;
	
	private String abbrCharacter;
	
	public String getFirstCharacter() {
		return firstCharacter;
	}

	public void setFirstCharacter(String firstCharacter) {
		this.firstCharacter = firstCharacter;
	}

	public String getAbbrCharacter() {
		return abbrCharacter;
	}

	public void setAbbrCharacter(String abbrCharacter) {
		this.abbrCharacter = abbrCharacter;
	}

	public String getFullCharacter() {
		return fullCharacter;
	}

	public void setFullCharacter(String fullCharacter) {
		this.fullCharacter = fullCharacter;
	}

	private String fullCharacter;

	public AreaDTO() {
		super();
	}
	
	public AreaDTO(String areaId, String name, int depth, String postalCode, int sort, String parentId, String firstCharacter, String abbrCharacter, String fullCharacter) {
		super();
		this.areaId = areaId;
		this.name = name;
		this.depth = depth;
		this.postalCode = postalCode;
		this.sort = sort;
		this.parentId = parentId;
		this.firstCharacter = firstCharacter;
		this.abbrCharacter = abbrCharacter;
		this.fullCharacter = fullCharacter;
	}

	public String getAreaId() {
		return areaId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	private int sort;

	private String parentId;
	
	
}
