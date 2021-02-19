package com.mb.ext.core.dao.impl;

import java.util.Date;

import javax.persistence.NoResultException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.SequenceDAO;
import com.mb.ext.core.entity.SequenceEntity;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.framework.dao.AbstractDAO;
import com.mb.framework.exception.DAOException;

@Repository("sequenceDAO")
@Qualifier("sequenceDAO")
public class SequenceDAOImpl extends AbstractDAO<SequenceEntity> implements SequenceDAO
{
	
	/**
	 * Initializing Entity.
	 */
	public SequenceDAOImpl()
	{
		super();
		this.setEntityClass(SequenceEntity.class);
	}

	
	/**
	 * @param seqName
	 * @return 
	 * @throws Exception
	 */
	@Override
	public int next(String seqName, String entId) throws DAOException {
		SequenceEntity entity = null;
		int nextNum ;
		try {
			if(!StringUtils.isEmpty(entId)){
				String queryStr = "select b from SequenceEntity b where b.entEntity.id= :ENTID and b.seqName = :SEQNAME and b.isDeleted=:isDeleted";
				entity = (SequenceEntity)em.createQuery(queryStr,SequenceEntity.class).setParameter("SEQNAME", seqName).setParameter("ENTID", entId).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
			}else{
				String queryStr = "select b from SequenceEntity b where  b.seqName = :SEQNAME and b.isDeleted=:isDeleted";
				entity = (SequenceEntity)em.createQuery(queryStr,SequenceEntity.class).setParameter("SEQNAME", seqName).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
			}
			nextNum = entity.getNextNum();
			entity.setNextNum(nextNum+1);
			update(entity);
			return nextNum;
		} catch (NoResultException e) {
			SequenceEntity newEntity = new SequenceEntity();
			newEntity.setSeqName(seqName);
			newEntity.setNextNum(2);
			newEntity.setCreateBy("system");
			newEntity.setUpdateBy("system");

			save(newEntity);
			return 1;
			
		} catch(Exception e){
			throw new DAOException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} 
		
	}


	@Override
	public int nextByDate(String seqName, Date tranDate, String entId)
			throws DAOException {
		SequenceEntity entity = null;
		int nextNum ;
		try {
			if(!StringUtils.isEmpty(entId)){
				String queryStr = "select b from SequenceEntity b where b.entEntity.id= :ENTID and b.seqName = :SEQNAME and b.tranDate = date(:TRANDATE) and b.isDeleted=:isDeleted";
				entity = (SequenceEntity)em.createQuery(queryStr,SequenceEntity.class).setParameter("SEQNAME", seqName).setParameter("TRANDATE", tranDate).setParameter("ENTID", entId).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
			}else{
				String queryStr = "select b from SequenceEntity b where  b.seqName = :SEQNAME and b.tranDate = date(:TRANDATE) and b.isDeleted=:isDeleted";
				entity = (SequenceEntity)em.createQuery(queryStr,SequenceEntity.class).setParameter("SEQNAME", seqName).setParameter("TRANDATE", tranDate).setParameter("isDeleted", Boolean.FALSE).getSingleResult();
			}
			nextNum = entity.getNextNum();
			entity.setNextNum(nextNum+1);
			update(entity);
			return nextNum;
		} catch (NoResultException e) {
			SequenceEntity newEntity = new SequenceEntity();
			newEntity.setSeqName(seqName);
			newEntity.setNextNum(2);
			newEntity.setTranDate(new Date());
			newEntity.setCreateBy("system");
			newEntity.setUpdateBy("system");

			save(newEntity);
			return 1;
			
		} catch(Exception e){
			throw new DAOException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} 
	}


	@Override
	public void updateNext(String name, String entId) throws DAOException {
		try {
			if(!StringUtils.isEmpty(entId)){
				String queryStr = "update SequenceEntity b set b.nextNum = b.nextNum +1  where b.entEntity.id= :ENTID and b.seqName = :SEQNAME  and b.isDeleted=:isDeleted";
				em.createQuery(queryStr).setParameter("ENTID", entId).setParameter("SEQNAME", name).executeUpdate();
			}else{
				String queryStr = "update SequenceEntity b set b.nextNum = b.nextNum +1  where b.seqName = :SEQNAME  and b.isDeleted=:isDeleted";
				em.createQuery(queryStr).setParameter("SEQNAME", name).executeUpdate();
			}
		} catch(Exception e){
			throw new DAOException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} 
		
	}


	@Override
	public void updateNextByDate(String name, Date tranDate, String entId)
			throws DAOException {
		try {
			if(!StringUtils.isEmpty(entId)){
				String queryStr = "update SequenceEntity b set b.nextNum = b.nextNum +1  where b.entEntity.id= :ENTID and b.tranDate = date(:TRANDATE) and b.seqName = :SEQNAME  and b.isDeleted=:isDeleted";
				em.createQuery(queryStr).setParameter("ENTID", entId).setParameter("SEQNAME", name).setParameter("TRANDATE", tranDate).executeUpdate();
			}else{
				String queryStr = "update SequenceEntity b set b.nextNum = b.nextNum +1  where b.seqName = :SEQNAME and b.tranDate = date(:TRANDATE) and b.isDeleted=:isDeleted";
				em.createQuery(queryStr).setParameter("SEQNAME", name).setParameter("TRANDATE", tranDate).executeUpdate();
			}
		} catch(Exception e){
			throw new DAOException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} 
		
	}

}
