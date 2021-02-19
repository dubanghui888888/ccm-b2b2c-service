package com.mb.ext.core.dao.impl.group;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.mb.ext.core.service.spec.UserDTO;
import com.mb.ext.core.service.spec.group.GroupBuyDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mb.ext.core.dao.AbstractBaseDAO;
import com.mb.ext.core.dao.group.GroupBuyDAO;
import com.mb.ext.core.entity.group.GroupBuyEntity;
import com.mb.ext.core.service.spec.GroupBuySearchDTO;
import com.mb.framework.exception.DAOException;
import com.mb.framework.util.log.LogHelper;

@Repository("groupBuyDAO")
@Qualifier("groupBuyDAO")
public class GroupBuyDAOImpl extends AbstractBaseDAO<GroupBuyEntity> implements GroupBuyDAO {
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());

	/**
	 * Initializing Entity.
	 */
	public GroupBuyDAOImpl() {
		super();
		this.setEntityClass(GroupBuyEntity.class);
	}

	@Override
	public void addGroupBuy(GroupBuyEntity groupBuyEntity) throws DAOException {

		this.save(groupBuyEntity);

	}

	@Override
	public void updateGroupBuy(GroupBuyEntity groupBuyEntity) throws DAOException {

		this.update(groupBuyEntity);

	}

	@Override
	public void deleteGroupBuy(GroupBuyEntity groupBuyEntity) throws DAOException {

		this.delete(groupBuyEntity);

	}

	@Override
	public GroupBuyEntity getGroupBuyByUuid(String uuid) throws DAOException {
		GroupBuyEntity groupBuyEntity = null;
		try {
			groupBuyEntity = (GroupBuyEntity) em.createQuery(
					"select b from GroupBuyEntity b where  b.groupBuyUuid = :UUID and b.isDeleted=:isDeleted",
					GroupBuyEntity.class).setParameter("UUID", uuid).setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found for product : " + uuid);
		}
		return groupBuyEntity;
	}

	@Override
	public List<GroupBuyEntity> getActiveGroupBuys() throws DAOException {
		List<GroupBuyEntity> groupBuyEntityList = new ArrayList<GroupBuyEntity>();
		try {
			groupBuyEntityList = em.createQuery(
					"select b from GroupBuyEntity b where b.status = :STATUS and b.isDeleted=:isDeleted order by b.createDate desc",
					GroupBuyEntity.class)
					.setParameter("STATUS", "1")
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for sec kill");
		}
		return groupBuyEntityList;
	}

	@Override
	public List<GroupBuyEntity> getExpiredGroupBuys() throws DAOException {
		List<GroupBuyEntity> groupBuyEntityList = new ArrayList<GroupBuyEntity>();
		try {
			groupBuyEntityList = em.createQuery(
					"select b from GroupBuyEntity b where b.status = :STATUS and now() > b.endTime and b.isDeleted=:isDeleted order by b.createDate desc",
					GroupBuyEntity.class)
					.setParameter("STATUS", "1")
					.setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for sec kill");
		}
		return groupBuyEntityList;
	}

	@Override
	public List<GroupBuyEntity> getAllGroupBuys() throws DAOException {
		List<GroupBuyEntity> groupBuyEntityList = new ArrayList<GroupBuyEntity>();
		try {
			groupBuyEntityList = em.createQuery("select b from GroupBuyEntity b where  b.isDeleted=:isDeleted order by b.createDate desc",GroupBuyEntity.class).setParameter("isDeleted", Boolean.FALSE).getResultList();
		} catch (NoResultException e) {
			logger.info("No record found for sec kill");
		}
		return groupBuyEntityList;
	}

	@Override
	public List<GroupBuyEntity> searchGroupBuy(GroupBuySearchDTO groupBuySearchDTO) throws DAOException {

		List<GroupBuyEntity> orderEntityList = new ArrayList<GroupBuyEntity>();
		String query = "select b.groupBuyEntity from GroupBuyUserEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = groupBuySearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			 if (GroupBuySearchDTO.KEY_USER.equals(key)) {
				query = query + " and b.userEntity.userUuid = :USERUUID";
			} else if (GroupBuySearchDTO.KEY_GROUP_BUY_STATUS.equals(key)) {
				query = query + " and b.groupBuyEntity.status = :STATUS";
			} else if (GroupBuySearchDTO.KEY_GROUP_BUY_PRODUCT.equals(key)) {
				query = query + " and b.groupBuyEntity.groupBuyProductEntity.groupBuyProductUuid= :GROUPBUYPRODUCTUUID";
			} else if (GroupBuySearchDTO.KEY_GROUP_BUY_DEF.equals(key)) {
				query = query + " and b.groupBuyEntity.groupBuyProductEntity.groupBuyDefEntity.groupBuyDefUuid= :GROUPBUYDEFUUID";
			}
		}
		query = query + " order by b.groupBuyEntity.startTime desc";	//按时间降序排列
		try {
			TypedQuery typedQuery = em.createQuery(query, GroupBuyEntity.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (GroupBuySearchDTO.KEY_GROUP_BUY_STATUS.equals(key)) {
					typedQuery.setParameter("STATUS",
							groupBuySearchDTO.getGroupBuyStatus());
				} else if (GroupBuySearchDTO.KEY_USER.equals(key)) {
					typedQuery.setParameter("USERUUID",
							groupBuySearchDTO.getUserUuid());
				} else if (GroupBuySearchDTO.KEY_GROUP_BUY_PRODUCT.equals(key)) {
					typedQuery.setParameter("GROUPBUYPRODUCTUUID",
							groupBuySearchDTO.getGroupBuyProductUuid());
				} else if (GroupBuySearchDTO.KEY_GROUP_BUY_DEF.equals(key)) {
					typedQuery.setParameter("GROUPBUYDEFUUID",
							groupBuySearchDTO.getGroupBuyDefUuid());
				}
			}
			orderEntityList = typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.setFirstResult(groupBuySearchDTO.getStartIndex()).setMaxResults(groupBuySearchDTO.getPageSize())
					.getResultList();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return orderEntityList;
	
	}
	
	@Override
	public int searchGroupBuyTotal(GroupBuySearchDTO groupBuySearchDTO) throws DAOException {

		Long total = null;
		String query = "select count(b.groupBuyEntity) from GroupBuyUserEntity b where b.isDeleted=:isDeleted";
		String[] keyArray = groupBuySearchDTO.getKeyArray();
		for (int i = 0; i < keyArray.length; i++) {
			String key = keyArray[i];
			 if (GroupBuySearchDTO.KEY_USER.equals(key)) {
				query = query + " and b.userEntity.userUuid = :USERUUID";
			} else if (GroupBuySearchDTO.KEY_GROUP_BUY_STATUS.equals(key)) {
				query = query + " and b.groupBuyEntity.status = :STATUS";
			} else if (GroupBuySearchDTO.KEY_GROUP_BUY_PRODUCT.equals(key)) {
				query = query + " and b.groupBuyEntity.groupBuyProductEntity.groupBuyProductUuid= :GROUPBUYPRODUCTUUID";
			} else if (GroupBuySearchDTO.KEY_GROUP_BUY_DEF.equals(key)) {
				query = query + " and b.groupBuyEntity.groupBuyProductEntity.groupBuyDefEntity.groupBuyDefUuid= :GROUPBUYDEFUUID";
			}
		}
		query = query + " order by b.groupBuyEntity.startTime desc";	//按时间降序排列
		try {
			TypedQuery typedQuery = em.createQuery(query, Long.class);
			for (int i = 0; i < keyArray.length; i++) {
				String key = keyArray[i];
				if (GroupBuySearchDTO.KEY_GROUP_BUY_STATUS.equals(key)) {
					typedQuery.setParameter("STATUS",
							groupBuySearchDTO.getGroupBuyStatus());
				} else if (GroupBuySearchDTO.KEY_USER.equals(key)) {
					typedQuery.setParameter("USERUUID",
							groupBuySearchDTO.getUserUuid());
				} else if (GroupBuySearchDTO.KEY_GROUP_BUY_PRODUCT.equals(key)) {
					typedQuery.setParameter("GROUPBUYPRODUCTUUID",
							groupBuySearchDTO.getGroupBuyProductUuid());
				} else if (GroupBuySearchDTO.KEY_GROUP_BUY_DEF.equals(key)) {
					typedQuery.setParameter("GROUPBUYDEFUUID",
							groupBuySearchDTO.getGroupBuyDefUuid());
				}
			}
			total = (Long) typedQuery
					.setParameter("isDeleted", Boolean.FALSE)
					.getSingleResult();
		} catch (NoResultException e) {
			logger.info("No record found");
		}
		return total == null ? 0 : total.intValue();
	
	}

	@Override
	public List<GroupBuyDTO> getActiveGroupBuysByGroupBuyProduct(String groupBuyProductUuid) throws DAOException {
		List<GroupBuyDTO> dtoList = new ArrayList<GroupBuyDTO>();
		try {
			List<Object> resultList = new ArrayList<Object>();
			String sqlStr = "select a.*,IFNULL(b.join_user_count,0) as join_user_count from (select gb.GROUP_BUY_UUID as group_buy_uuid,u.PHOTOURL as photo_url,u.name,gb.START_TIME as start_time,gb.END_TIME as end_time,gbd.MIN_USER_COUNT as min_user_count from group_buy as gb,group_buy_product as gbp,group_buy_def as gbd,user as u where gb.owner_uuid = u.user_uuid and gb.GROUP_BUY_PRODUCT_UUID = gbp.GROUP_BUY_PRODUCT_UUID and gbp.GROUP_BUY_DEF_UUID = gbd.GROUP_BUY_DEF_UUID and gb.GROUP_BUY_PRODUCT_UUID = :GROUPBUYPRODUCTUUID and gb.STATUS = '1' and gb.END_TIME>CURRENT_TIMESTAMP) as a left join (select gbu.GROUP_BUY_UUID as group_buy_uuid,count(gbu.group_buy_user_uuid) as join_user_count from group_buy_user as gbu,`order` as o where o.order_uuid = gbu.order_uuid and o.order_status not in ('0','4') group by gbu.group_buy_uuid) as b on a.group_buy_uuid = b.group_buy_uuid where b.join_user_count>0 order by (min_user_count - join_user_count)";
			resultList = em
					.createNativeQuery(sqlStr)
					.setParameter("GROUPBUYPRODUCTUUID", groupBuyProductUuid).getResultList();
			for (int i = 0; i < resultList.size(); i++) {
				Object[] result = (Object[]) resultList.get(i);
				String group_buy_uuid = String.valueOf(result[0]);
				String photo_url = String.valueOf(result[1]);
				String name = String.valueOf(result[2]);
				Date start_time = (Date) result[3];
				Date end_time = (Date) result[4];
				Integer min_user_count = (Integer) result[5];
				BigInteger join_user_count = (BigInteger) result[6];
				GroupBuyDTO groupBuyDTO = new GroupBuyDTO();
				groupBuyDTO.setGroupBuyUuid(group_buy_uuid);
				UserDTO userDTO = new UserDTO();
				userDTO.setPhotoUrl(photo_url);
				userDTO.setName(name);
				groupBuyDTO.setOwnerDTO(userDTO);
				groupBuyDTO.setStartTime(start_time);
				groupBuyDTO.setEndTime(end_time);
				groupBuyDTO.setMinUserCount(min_user_count.intValue());
				groupBuyDTO.setJoinUserCount(join_user_count.intValue());
				dtoList.add(groupBuyDTO);
			}
		} catch (NoResultException e) {
			logger.info("No record found for group buy");
		}
		return dtoList;
	}
}
