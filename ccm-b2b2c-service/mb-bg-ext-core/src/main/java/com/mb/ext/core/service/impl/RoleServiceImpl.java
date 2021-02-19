package com.mb.ext.core.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mb.ext.core.dao.RoleDAO;
import com.mb.ext.core.dao.SequenceDAO;
import com.mb.ext.core.dao.UserDAO;
import com.mb.ext.core.entity.RoleEntity;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.AdminService;
import com.mb.ext.core.service.RoleService;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.RoleDTO;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.service.AbstractService;
import com.mb.framework.util.log.LogHelper;

@Service
@Qualifier("RoleService")
public class RoleServiceImpl extends AbstractService implements
		RoleService<BodyDTO> {
	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;

	@Autowired
	@Qualifier("sequenceDAO")
	private SequenceDAO sequenceDAO;

	@Autowired
	@Qualifier("roleDAO")
	private RoleDAO roleDAO;

	@Autowired
	@Qualifier("AdminService")
	private AdminService adminService;

	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	private void DTO2Entity(RoleDTO roleDTO, RoleEntity roleEntity) {
		if (roleDTO != null && roleEntity != null) {
			roleEntity.setName(roleDTO.getName());
			roleEntity.setDesc(roleDTO.getDesc());
		}
	}

	private void Entity2DTO(RoleEntity roleEntity, RoleDTO roleDTO) {
		if (roleDTO != null && roleEntity != null) {
			roleDTO.setCreateBy(roleEntity.getCreateBy());
			roleDTO.setUpdateBy(roleEntity.getUpdateBy());
			roleDTO.setName(roleEntity.getName());
			roleDTO.setDesc(roleEntity.getDesc());
		}
	}

	@Override
	public void addRole(RoleDTO roleDTO) throws BusinessException {

		try {
			RoleEntity roleEntity = roleDAO.getRoleByName(roleDTO.getName());
			if (roleEntity != null) {
				throw new BusinessException(BusinessErrorCode.ROLE_DUPLICATE);
			}

			roleEntity = new RoleEntity();
			DTO2Entity(roleDTO, roleEntity);
			String id = adminService.getAdminProfile().getId();
			roleEntity.setCreateBy(id);
			roleEntity.setUpdateBy(id);
			roleDAO.addRole(roleEntity);

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		} catch (BusinessException e1) {
			throw e1;
		}

	}

	@Override
	public void updateRole(RoleDTO roleDTO) throws BusinessException {

		try {
			String roleName = roleDTO.getName();
			String newName = roleDTO.getNewName();
			String desc = roleDTO.getDesc();
			if (!StringUtils.isEmpty(roleName)) {
					
				RoleEntity roleEntity = roleDAO.getRoleByName(roleName);
				if (!StringUtils.isEmpty(newName)&&!newName.equals(roleName)) {
					RoleEntity pEntity = roleDAO.getRoleByName(newName);
					if(pEntity != null){
						throw new BusinessException(BusinessErrorCode.ROLE_DUPLICATE);
					}
					roleEntity.setName(newName);
				}
				if (!StringUtils.isEmpty(desc)) {
					roleEntity.setDesc(desc);
				}

				roleDAO.updateRole(roleEntity);

			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		} catch (BusinessException e) {
			throw e;
		}

	}

	@Override
	public RoleDTO getRoleByName(String name) throws BusinessException {

		RoleDTO roleDTO = new RoleDTO();
		try {
			RoleEntity roleEntity = roleDAO.getRoleByName(name);
			if (roleEntity == null) {
				return null;
			} else {
				Entity2DTO(roleEntity, roleDTO);
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		return roleDTO;
	}

	@Override
	public List<RoleDTO> getRoles() throws BusinessException {
		List<RoleDTO> roleDTOList = new ArrayList<RoleDTO>();

		try {
			List<RoleEntity> roleEntityList = roleDAO.getRoles();
			for (Iterator<RoleEntity> iter = roleEntityList.iterator(); iter
					.hasNext();) {
				RoleEntity roleEntity = iter.next();
				RoleDTO roleDTO = new RoleDTO();
				Entity2DTO(roleEntity, roleDTO);
				roleDTOList.add(roleDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		return roleDTOList;
	}

	@Override
	public void deleteRole(RoleDTO roleDTO) throws BusinessException {

		String name = roleDTO.getName();

		try {
			if (propertyRepository.getProperty("default.role.admin")
					.equals(name))
				throw new BusinessException(
						BusinessErrorCode.ROLE_ADMIN_RESERVED);
			RoleEntity roleEntity = roleDAO.getRoleByName(name);

			if (roleEntity != null) {

				roleDAO.deleteRole(roleEntity);
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		} catch (BusinessException e) {
			throw e;
		}
	}

	@Override
	public void enableRole(RoleDTO roleDTO) throws BusinessException {
		try {
			RoleEntity roleEntity = roleDAO.getRoleByName(roleDTO.getName());
			if (roleEntity != null) {
				roleEntity.setActive(true);
				roleDAO.updateRole(roleEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}

	}

	@Override
	public void disableRole(RoleDTO roleDTO) throws BusinessException {
		try {
			RoleEntity roleEntity = roleDAO.getRoleByName(roleDTO.getName());
			if (roleEntity != null) {
				roleEntity.setActive(false);
				roleDAO.updateRole(roleEntity);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}

	}

}
