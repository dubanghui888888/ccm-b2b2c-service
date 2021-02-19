package com.mb.ext.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.SysLogDAO;
import com.mb.ext.core.entity.SysLogEntity;
import com.mb.ext.core.service.spec.SysLogSearchDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("sysLogDAO")
@Qualifier("sysLogDAO")
public class SysLogDAOImpl extends AbstractBaseDAO<SysLogEntity> implements SysLogDAO
{
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public SysLogDAOImpl()
	{
		super();
		this.setEntityClass(SysLogEntity.class);
	}

	/**
	 * This method is used for inserting user information.
	 * 
	 * @param user
	 */
	@Override
	public void addSysLog(SysLogEntity sysLogEntity) throws DAOException
	{
		save(sysLogEntity);
	}
	
	/**
	 * @param name
	 * @return 
	 * @throws Exception
	 */

	@Override
	public void updateSysLog(SysLogEntity sysLogEntity) throws DAOException {
		update(sysLogEntity);
		
	}

	@Override
	public void deleteSysLog(SysLogEntity sysLogEntity) throws DAOException {
		
		deletePhysical(sysLogEntity);
		
	}
	
	@Override
	public List<SysLogEntity> searchSysLog(SysLogSearchDTO sysLogSearchDTO,
			int startIndex, int pageSize) throws DAOException {
		List<SysLogEntity> sysLogEntityList = new ArrayList<SysLogEntity>();
		String query = "select b from SysLogEntity b where  b.isDeleted=:isDeleted";
		String[] keyArray = sysLogSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (SysLogSearchDTO.KEY_LOGTYPE.equals(key)) {
				query = query + " and b.logType = :LOGTYPE";
			} else if (SysLogSearchDTO.KEY_LOGCATEGORY.equals(key)) {
				query = query + " and b.logCategory = :LOGCATEGORY";
			} else if (SysLogSearchDTO.KEY_NAME.equals(key)) {
				query = query + " and b.logUserId = :NAME";
			} else if (SysLogSearchDTO.KEY_LOGDATE.equals(key)) {
				query = query
						+ " and date(b.logDate) >= date(:LOGDATESTART) and date(b.logDate) <= date(:LOGDATEEND)";
			}
		}
		// String sortBy = userSearchDTO.getSortBy();
		// if("默认排序".equals(sortBy)){
		// query = query + " order by b.createDate desc";
		// }else if("车价最高".equals(sortBy)){
		// query = query + " order by b.priceSale desc";
		// }
		query = query + " order by b.createDate desc";
		try {
			TypedQuery typedQuery = em.createQuery(query, SysLogEntity.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (SysLogSearchDTO.KEY_NAME.equals(key)) {
					typedQuery.setParameter("NAME",
							sysLogSearchDTO.getName());
				} else if (SysLogSearchDTO.KEY_LOGCATEGORY.equals(key)) {
					typedQuery.setParameter("LOGCATEGORY",
							sysLogSearchDTO.getLogCategory());
				} else if (SysLogSearchDTO.KEY_LOGTYPE.equals(key)) {
					typedQuery.setParameter("LOGTYPE", 
							sysLogSearchDTO.getLogType());
				} else if (SysLogSearchDTO.KEY_LOGDATE.equals(key)) {
					typedQuery.setParameter("LOGDATESTART",
							sysLogSearchDTO.getLogDateStart());
					typedQuery.setParameter("LOGDATEEND",
							sysLogSearchDTO.getLogDateEnd());
				}
			}
			sysLogEntityList = typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(startIndex).setMaxResults(pageSize)
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return sysLogEntityList;
	}
	
	@Override
	public int searchSysLogTotal(SysLogSearchDTO sysLogSearchDTO
			) throws DAOException {
		Long total = new Long(0);
		String query = "select count(b) from SysLogEntity b where  b.isDeleted=:isDeleted";
		String[] keyArray = sysLogSearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			if (SysLogSearchDTO.KEY_LOGTYPE.equals(key)) {
				query = query + " and b.logType = :LOGTYPE";
			} else if (SysLogSearchDTO.KEY_LOGCATEGORY.equals(key)) {
				query = query + " and b.logCategory = :LOGCATEGORY";
			} else if (SysLogSearchDTO.KEY_NAME.equals(key)) {
				query = query + " and b.logUserId = :NAME";
			} else if (SysLogSearchDTO.KEY_LOGDATE.equals(key)) {
				query = query
						+ " and b.logDate >= :LOGDATESTART and b.logDate <= :LOGDATEEND";
			}
		}
		// String sortBy = userSearchDTO.getSortBy();
		// if("默认排序".equals(sortBy)){
		// query = query + " order by b.createDate desc";
		// }else if("车价最高".equals(sortBy)){
		// query = query + " order by b.priceSale desc";
		// }
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (SysLogSearchDTO.KEY_NAME.equals(key)) {
					typedQuery.setParameter("NAME",
							sysLogSearchDTO.getName());
				} else if (SysLogSearchDTO.KEY_LOGCATEGORY.equals(key)) {
					typedQuery.setParameter("LOGCATEGORY",
							sysLogSearchDTO.getLogCategory());
				} else if (SysLogSearchDTO.KEY_LOGTYPE.equals(key)) {
					typedQuery.setParameter("LOGTYPE", 
							sysLogSearchDTO.getLogType());
				} else if (SysLogSearchDTO.KEY_LOGDATE.equals(key)) {
					typedQuery.setParameter("LOGDATESTART",
							sysLogSearchDTO.getLogDateStart());
					typedQuery.setParameter("LOGDATEEND",
							sysLogSearchDTO.getLogDateEnd());
				}
			}
			total = (Long)typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return total.intValue();
	}
	
	@Override
	public List<SysLogEntity> getSysLogs() throws DAOException {
		List<SysLogEntity> sysLogEntityList = new ArrayList<SysLogEntity>();
		try {
			sysLogEntityList = em.createQuery("select b from SysLogEntity b where b.isDeleted=:isDeleted",SysLogEntity.class)
					.setParameter("isDeleted", Boolean.FALSE)
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for user: ");
		}
		return sysLogEntityList;
	}
	
	@Override
	public SysLogEntity getSysLogByUuid(String uuid) throws DAOException {
		SysLogEntity sysLogEntity = null;
		try {
			sysLogEntity = em.createQuery("select b from SysLogEntity b where b.sysLogUuid = :UUID and b.isDeleted=:isDeleted",SysLogEntity.class)
					.setParameter("UUID", uuid)
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for user: ");
		}
		return sysLogEntity;
	}
}
