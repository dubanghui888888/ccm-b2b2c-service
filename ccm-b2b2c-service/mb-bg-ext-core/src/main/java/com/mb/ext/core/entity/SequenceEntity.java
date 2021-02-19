package com.mb.ext.core.entity;

import java.util.Date;

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
 * The persistent class for the sequence database table.
 * 
 */
@Entity
@Table(name = "sequence")
@NamedQuery(name = "SequenceSequenceity.findAll", query = "SELECT u FROM SequenceEntity u")
public class SequenceEntity extends AbstractBaseEntity
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "SEQUENCE_UUID")
	@GenericGenerator(name = "SEQUENCE_UUID", strategy = "uuid")
	@Column(name = "SEQUENCE_UUID", nullable = false, length = 36)
	private String sequenceUuid;

	public String getSequenceUuid() {
		return sequenceUuid;
	}

	public Date getTranDate() {
		return tranDate;
	}

	public void setTranDate(Date tranDate) {
		this.tranDate = tranDate;
	}

	public void setSequenceUuid(String entUuid) {
		this.sequenceUuid = entUuid;
	}


	@Column(name = "SEQNAME", nullable = false, length = 30)
	private String seqName;

	@Column(name = "NEXTNUM")
	private int nextNum;
	
	@Column(name = "TRANDATE")
	private Date tranDate;
	
	public String getSeqName() {
		return seqName;
	}

	public void setSeqName(String seqName) {
		this.seqName = seqName;
	}

	public int getNextNum() {
		return nextNum;
	}

	public void setNextNum(int nextNum) {
		this.nextNum = nextNum;
	}

	
}