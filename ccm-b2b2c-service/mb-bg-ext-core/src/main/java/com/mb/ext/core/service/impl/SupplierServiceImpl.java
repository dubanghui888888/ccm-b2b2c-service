package com.mb.ext.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mb.ext.core.dao.FileDAO;
import com.mb.ext.core.dao.PartnerDAO;
import com.mb.ext.core.dao.SequenceDAO;
import com.mb.ext.core.dao.SettingDAO;
import com.mb.ext.core.dao.SupplierDAO;
import com.mb.ext.core.dao.UserAuthDAO;
import com.mb.ext.core.dao.UserBalanceDAO;
import com.mb.ext.core.dao.UserDAO;
import com.mb.ext.core.dao.UserStatementDAO;
import com.mb.ext.core.dao.product.ProductDAO;
import com.mb.ext.core.entity.merchant.MerchantEntity;
import com.mb.ext.core.entity.supplier.SupplierEntity;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.DeliveryService;
import com.mb.ext.core.service.FundService;
import com.mb.ext.core.service.NoteService;
import com.mb.ext.core.service.OrderService;
import com.mb.ext.core.service.SupplierService;
import com.mb.ext.core.service.UserService;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.ChartDTO;
import com.mb.ext.core.service.spec.SupplierSearchDTO;
import com.mb.ext.core.service.spec.supplier.SupplierDTO;
import com.mb.ext.core.util.MailSenderUtil;
import com.mb.ext.core.util.SMSSenderUtil;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.service.AbstractService;
import com.mb.framework.util.log.LogHelper;
import com.mb.framework.util.property.PropertyRepository;

@Service
@Qualifier("SupplierService")
public class SupplierServiceImpl extends AbstractService implements
		SupplierService<BodyDTO> {
	@Autowired
	@Qualifier("supplierDAO")
	private SupplierDAO supplierDAO;
	
	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;
	
	@Autowired
	@Qualifier("userBalanceDAO")
	private UserBalanceDAO userBalanceDAO;
	
	@Autowired
	@Qualifier("userStatementDAO")
	private UserStatementDAO userStatementDAO;
	
	@Autowired
	@Qualifier("fileDAO")
	private FileDAO fileDAO;

	@Autowired
	@Qualifier("sequenceDAO")
	private SequenceDAO sequenceDAO;

	@Autowired
	@Qualifier("userAuthDAO")
	private UserAuthDAO userAuthDAO;

	@Autowired
	@Qualifier("settingDAO")
	private SettingDAO settingDAO;
	
	@Autowired
	@Qualifier("partnerDAO")
	private PartnerDAO partnerDAO;
	
	@Autowired
	@Qualifier("productDAO")
	private ProductDAO productDAO;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Autowired
	@Qualifier("OrderService")
	private OrderService orderService;
	
	@Autowired
	@Qualifier("DeliveryService")
	private DeliveryService deliveryService;
	
	@Autowired
	@Qualifier("FundService")
	private FundService fundService;

	@Autowired
	@Qualifier("NoteService")
	private NoteService noteService;

	@Autowired
	private MailSenderUtil mailSenderUtil;

	@Autowired
	private SMSSenderUtil smsSenderUtil;

	@Autowired
	private PropertyRepository propertyRepository;

	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	@Override
	public void deleteSupplier(SupplierDTO supplierDTO) throws BusinessException {
		try {
			String mobileNo = supplierDTO.getMobileNo();
			String supplierUuid = supplierDTO.getSupplierUuid();
			SupplierEntity supplierEntity = null;
			if(!StringUtils.isEmpty(mobileNo))
				supplierEntity = supplierDAO.getSupplierByMobileNo(mobileNo);
			else if(!StringUtils.isEmpty(supplierUuid))
				supplierEntity = supplierDAO.getSupplierByUuid(supplierUuid);
			if (supplierEntity != null) {
				supplierDAO.deleteSupplier(supplierEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}
	
	@Override
	public String createSupplier(SupplierDTO supplierDTO) throws BusinessException {
		try {
			String mobileNo = supplierDTO.getMobileNo();
			//联系电话不能重复
			SupplierEntity supplierEntity = supplierDAO.getSupplierByMobileNo(mobileNo);
			if (supplierEntity != null) {
				throw new BusinessException(BusinessErrorCode.SUPPLIER_DUPLICATE_MOBILENO);
			}
			supplierEntity = new SupplierEntity();
			//创建商户
			supplierEntity.setMobileNo(supplierDTO.getMobileNo());
			supplierEntity.setSupplierName(supplierDTO.getSupplierName());
			supplierEntity.setSupplierAddress(supplierDTO.getSupplierAddress());
			supplierEntity.setSupplierDescription(supplierDTO.getSupplierDescription());
			supplierEntity.setContactName(supplierDTO.getContactName());
			supplierEntity.setRegisterDate(new Date());
			supplierEntity.setCreateBy(supplierDTO.getMobileNo());
			supplierEntity.setUpdateBy(supplierDTO.getMobileNo());
			supplierDAO.addSupplier(supplierEntity);
			
			return supplierEntity.getSupplierUuid();
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}

	}
	
	@Override
	public void updateSupplier(SupplierDTO supplierDTO) throws BusinessException {
		try {
			String mobileNo = supplierDTO.getMobileNo();
			String supplierUuid = supplierDTO.getSupplierUuid();
			SupplierEntity supplierEntity = supplierDAO.getSupplierByMobileNo(mobileNo);
			//更新供应商时, 电话号码不能与其他供应商重复重复
			if(supplierEntity != null && !supplierEntity.getSupplierUuid().equals(supplierUuid)) {
				throw new BusinessException(BusinessErrorCode.SUPPLIER_DUPLICATE_MOBILENO);
			}
			supplierEntity = supplierDAO.getSupplierByUuid(supplierUuid);
			if (supplierEntity != null) {
				supplierEntity.setSupplierName(supplierDTO.getSupplierName());
				supplierEntity.setMobileNo(supplierDTO.getMobileNo());
				supplierEntity.setSupplierAddress(supplierDTO.getSupplierAddress());
				supplierEntity.setContactName(supplierDTO.getContactName());
				supplierEntity.setSupplierDescription(supplierDTO.getSupplierDescription());
				supplierDAO.updateSupplier(supplierEntity);
				
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}

	}
	
	@Override
	public void closeSupplier(SupplierDTO supplierDTO) throws BusinessException {
		try {
			String mobileNo = supplierDTO.getMobileNo();
			String supplierUuid = supplierDTO.getSupplierUuid();
			SupplierEntity supplierEntity = null;
			if(!StringUtils.isEmpty(mobileNo))
				supplierEntity = supplierDAO.getSupplierByMobileNo(mobileNo);
			else if(!StringUtils.isEmpty(supplierUuid))
				supplierEntity = supplierDAO.getSupplierByUuid(supplierUuid);
			if(supplierEntity != null){
				supplierEntity.setClosed(true);
				supplierDAO.updateSupplier(supplierEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}

	
	@Override
	public SupplierDTO getSupplierByUuid(String uuid) throws BusinessException {
		SupplierDTO supplierDTO = null;
		try {
			SupplierEntity supplierEntity = supplierDAO.getSupplierByUuid(uuid);
			if (supplierEntity != null) {
				supplierDTO = new SupplierDTO();
				entity2DTO(supplierEntity, supplierDTO);
			}else
				throw new BusinessException(BusinessErrorCode.SUPPLIER_NOT_FOUND);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		return supplierDTO;

	}
	
	@Override
	public SupplierDTO getSupplierByMobileNo(String mobileNo) throws BusinessException {
		SupplierDTO supplierDTO = null;
		try {
			SupplierEntity supplierEntity = supplierDAO.getSupplierByMobileNo(mobileNo);
			if (supplierEntity != null) {
				supplierDTO = new SupplierDTO();
				entity2DTO(supplierEntity, supplierDTO);
			}else
				throw new BusinessException(BusinessErrorCode.SUPPLIER_NOT_FOUND);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		return supplierDTO;

	}

	@Override
	public List<SupplierDTO> getSuppliers() throws BusinessException {
		List<SupplierDTO> supplierDTOList = new ArrayList<SupplierDTO>();
		try {
			List<SupplierEntity> supplierEntityList = supplierDAO.getSuppliers();
			if (supplierEntityList != null && supplierEntityList.size() > 0) {
				for (Iterator<SupplierEntity> iter = supplierEntityList.iterator(); iter
						.hasNext();) {
					SupplierEntity supplierEntity = iter.next();
					SupplierDTO supplierDTO = new SupplierDTO();
					entity2DTO(supplierEntity, supplierDTO);
					supplierDTOList.add(supplierDTO);
				}
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		return supplierDTOList;
	}
	
	@Override
	public List<SupplierDTO> searchSuppliers(SupplierSearchDTO searchDTO) throws BusinessException {
		List<SupplierDTO> supplierDTOList = new ArrayList<SupplierDTO>();
		try {
			List<SupplierEntity> supplierEntityList = new ArrayList<SupplierEntity>();
			if(!StringUtils.isEmpty(searchDTO.getSortBy())&&"soldUnit".equals(searchDTO.getSortBy())){
				List<Object> supplierUuidList = supplierDAO.searchSupplierSoldUnit(searchDTO);
				for (int i = 0; i < supplierUuidList.size(); i++) {
					Object[] result = (Object[]) supplierUuidList.get(i);
					String supplierUuid = String.valueOf(result[0]);
					SupplierEntity supplierEntity = supplierDAO.getSupplierByUuid(supplierUuid);
					SupplierDTO supplierDTO = new SupplierDTO();
					entity2DTO(supplierEntity, supplierDTO);
					supplierDTO.setSoldUnitTotal(Integer.parseInt(result[1].toString()));
					supplierDTOList.add(supplierDTO);
				}
			}else{
				supplierEntityList = supplierDAO.searchSuppliers(searchDTO);
				if (supplierEntityList != null)
					for (Iterator<SupplierEntity> iterator = supplierEntityList.iterator(); iterator
							.hasNext();) {
						SupplierEntity supplierEntity = (SupplierEntity) iterator.next();
						SupplierDTO supplierDTO = new SupplierDTO();
						entity2DTO(supplierEntity, supplierDTO);
						supplierDTO.setSoldUnitTotal(productDAO.getSoldUnitTotalBySupplier(supplierEntity));
						supplierDTOList.add(supplierDTO);
					}
			}
			return supplierDTOList;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} catch (Exception e2){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e2);
		}
	}
	
	@Override
	public int searchSupplierTotal(SupplierSearchDTO searchDTO) throws BusinessException {
		try {
			return supplierDAO.searchSupplierTotal(searchDTO);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e);
		} catch (Exception e2){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,e2);
		}
	}

	private void entity2DTO(SupplierEntity supplierEntity, SupplierDTO supplierDTO) {
		if (supplierEntity != null && supplierDTO != null) {
			supplierDTO.setSupplierUuid(supplierEntity.getSupplierUuid());
			supplierDTO.setMobileNo(supplierEntity.getMobileNo());
			supplierDTO.setSupplierName(supplierEntity.getSupplierName());
			supplierDTO.setSupplierAddress(supplierEntity.getSupplierAddress());
			supplierDTO.setSupplierDescription(supplierEntity.getSupplierDescription());
			supplierDTO.setContactName(supplierEntity.getContactName());
			supplierDTO.setSupplierDescription(supplierEntity.getSupplierDescription());
			supplierDTO.setRegisterDate(supplierEntity.getRegisterDate());
			supplierDTO.setClosed(supplierEntity.isClosed());
			supplierDTO.setMemo(supplierEntity.getMemo());
		}
	}

	
	@Override
	public int getSupplierCount()
			throws BusinessException {
		try {
			int supplierCount = supplierDAO.searchSupplierTotal(new SupplierSearchDTO());
			return supplierCount;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}
	
	@Override
	public int getIncrementSupplierCountByDate(Date startDate, Date endDate) throws BusinessException{
		int count = 0;
		try{
			count = supplierDAO.getIncrementSupplierCountByDate(startDate, endDate);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}catch(Exception e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
		return count;
	}

	@Override
	public List<ChartDTO> getIncrementSupplierCountChartByDate(Date startDate,
			Date endDate) throws BusinessException {
		try{
			return supplierDAO.getIncrementSupplierChart(startDate, endDate);
		}catch(DAOException e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}catch(Exception e){
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR, e);
		}
	}
}
