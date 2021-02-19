package com.mb.ext.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.mb.framework.entity.AbstractBaseEntity;

/**
 * The persistent class for the user_tree database table.
 * 
 */
@Entity
@Table(name = "user_tree")
@NamedQuery(name = "UserTreeEntity.findAll", query = "SELECT u FROM UserTreeEntity u")
public class UserTreeEntity extends AbstractBaseEntity
{

	public String getUserTreeUuid() {
		return userTreeUuid;
	}


	public void setUserTreeUuid(String userTreeUuid) {
		this.userTreeUuid = userTreeUuid;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}


	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}


	public UserEntity getAncestorEntity() {
		return ancestorEntity;
	}


	public void setAncestorEntity(UserEntity ancestorEntity) {
		this.ancestorEntity = ancestorEntity;
	}


	public int getLevel() {
		return level;
	}


	public void setLevel(int level) {
		this.level = level;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "USER_TREE_UUID")
	@GenericGenerator(name = "USER_TREE_UUID", strategy = "uuid")
	@Column(name = "USER_TREE_UUID", nullable = false, length = 36)
	private String userTreeUuid;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_UUID")
	private UserEntity userEntity;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ANCESTOR_UUID")
	private UserEntity ancestorEntity;

	@Column(name = "LEVEL")
	private int level;

}