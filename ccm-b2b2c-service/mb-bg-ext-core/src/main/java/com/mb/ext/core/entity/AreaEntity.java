package com.mb.ext.core.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "area")
@NamedQuery(name = "AreaEntity.findAll", query = "SELECT u FROM AreaEntity u")
public class AreaEntity 
{
	
	@Id
	@GeneratedValue(generator = "AREA_ID")
	@GenericGenerator(name = "AREA_ID", strategy = "uuid")
	@Column(name = "AREA_ID", nullable = false, length = 36)
	private String areaId;

	public List<AreaEntity> getChildList() {
		return childList;
	}

	public void setChildList(List<AreaEntity> childList) {
		this.childList = childList;
	}

	public String getAreaId() {
		return areaId;
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

	public AreaEntity getParentAreaEntity() {
		return parentAreaEntity;
	}

	public void setParentAreaEntity(AreaEntity parentAreaEntity) {
		this.parentAreaEntity = parentAreaEntity;
	}

	@Column(name = "NAME")
	private String name;
	
	@Column(name = "DEPTH")
	private int depth;
	
	@Column(name = "POSTAL_CODE")
	private String postalCode;
	
	@Column(name = "FIRST_CHARACTER")
	private String firstCharacter;
	
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

	@Column(name = "ABBR_CHARACTER")
	private String abbrCharacter;
	
	@Column(name = "FULL_CHARACTER")
	private String fullCharacter;
	
	@Column(name = "SORT")
	private int sort;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_ID")
	private AreaEntity parentAreaEntity;
	
	@OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name = "PARENT_ID")    
    private List<AreaEntity> childList;
		
}