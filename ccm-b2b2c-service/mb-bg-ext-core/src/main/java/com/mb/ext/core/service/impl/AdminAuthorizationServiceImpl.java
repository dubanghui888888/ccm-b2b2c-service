package com.mb.ext.core.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mb.ext.core.dao.AdminDAO;
import com.mb.ext.core.dao.FunctionDAO;
import com.mb.ext.core.dao.PermissionDAO;
import com.mb.ext.core.dao.RoleDAO;
import com.mb.ext.core.dao.SequenceDAO;
import com.mb.ext.core.dao.UserDAO;
import com.mb.ext.core.entity.AdminEntity;
import com.mb.ext.core.entity.AdminRoleEntity;
import com.mb.ext.core.entity.FunctionEntity;
import com.mb.ext.core.entity.PermissionEntity;
import com.mb.ext.core.entity.RoleEntity;
import com.mb.ext.core.message.BusinessErrorCode;
import com.mb.ext.core.service.AdminAuthorizationService;
import com.mb.ext.core.service.AdminService;
import com.mb.ext.core.service.RoleService;
import com.mb.ext.core.service.spec.AdminDTO;
import com.mb.ext.core.service.spec.BodyDTO;
import com.mb.ext.core.service.spec.FunctionDTO;
import com.mb.ext.core.service.spec.PermissionDTO;
import com.mb.framework.exception.BusinessException;
import com.mb.framework.exception.DAOException;
import com.mb.framework.service.AbstractService;
import com.mb.framework.util.log.LogHelper;

@Service
@Qualifier("AdminAuthorizationService")
public class AdminAuthorizationServiceImpl extends AbstractService implements
		AdminAuthorizationService<BodyDTO> {
	@Autowired
	@Qualifier("userDAO")
	private UserDAO userDAO;

	@Autowired
	@Qualifier("sequenceDAO")
	private SequenceDAO sequenceDAO;

	@Autowired
	@Qualifier("permissionDAO")
	private PermissionDAO permissionDAO;

	@Autowired
	@Qualifier("adminDAO")
	private AdminDAO adminDAO;

	@Autowired
	@Qualifier("RoleService")
	private RoleService roleService;

	@Autowired
	@Qualifier("roleDAO")
	private RoleDAO roleDAO;
	
	@Autowired
	@Qualifier("AdminService")
	private AdminService adminService;

	@Autowired
	@Qualifier("functionDAO")
	private FunctionDAO functionDAO;

	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	@Override
	public List<PermissionDTO> getPermissions(AdminDTO adminDTO)
			throws BusinessException {
		try {
			List<PermissionDTO> permissionDTOList = new ArrayList<PermissionDTO>();
			AdminEntity adminEntity = adminDAO.getAdminById(adminDTO.getId());
			if (adminEntity != null) {
				List<AdminRoleEntity> adminRoleList = adminEntity
						.getAdminRoleList();
				if (adminRoleList != null) {
					for (Iterator<AdminRoleEntity> iter = adminRoleList
							.iterator(); iter.hasNext();) {
						AdminRoleEntity adminRoleEntity = iter.next();
						RoleEntity roleEntity = adminRoleEntity.getRoleEntity();
						PermissionDTO permissionDTO = new PermissionDTO();
						List<FunctionDTO> functionDTOList = new ArrayList<FunctionDTO>();
						permissionDTO.setRoleName(roleEntity.getName());
						List<PermissionEntity> permissionList = roleEntity
								.getPermissionEntityList();
						if (permissionList != null) {
							for (Iterator<PermissionEntity> iter1 = permissionList
									.iterator(); iter1.hasNext();) {
								PermissionEntity permissionEntity = iter1
										.next();
								FunctionDTO functionDTO = new FunctionDTO();
								String applicationCode = permissionEntity
										.getFunctionEntity()
										.getApplicationEntity().getCode();
								String applicationName = permissionEntity
										.getFunctionEntity()
										.getApplicationEntity().getName();
								String rootApplication = permissionEntity
										.getFunctionEntity()
										.getApplicationEntity()
										.getRootApplication();
								functionDTO.setFunctionCode(permissionEntity
										.getFunctionEntity().getCode());
								functionDTO.setFunctionName(permissionEntity
										.getFunctionEntity().getName());
								functionDTO.setApplicationCode(applicationCode);
								functionDTO.setApplicationName(applicationName);
								functionDTO.setRootApplication(rootApplication);
								functionDTOList.add(functionDTO);

							}
						}
						permissionDTO.setFunctionList(functionDTOList);
						permissionDTOList.add(permissionDTO);
					}
				}
			}

			return permissionDTOList;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
	}

	@Override
	public boolean isAuthorized(AdminDTO adminDTO, FunctionDTO functionDTO)
			throws BusinessException {
		try {
			AdminEntity adminEntity = adminDAO.getAdminById(adminDTO.getId());
			if (adminEntity != null) {
				List<AdminRoleEntity> adminRoleList = adminEntity
						.getAdminRoleList();
				if (adminRoleList != null) {
					for (Iterator<AdminRoleEntity> iter = adminRoleList
							.iterator(); iter.hasNext();) {
						AdminRoleEntity userRoleEntity = iter.next();
						RoleEntity roleEntity = userRoleEntity.getRoleEntity();
						String roleName = roleEntity.getName();
						String functionCode = functionDTO.getFunctionCode();
						PermissionEntity permissionEntity = permissionDAO
								.getPermissionByRoleFunction(roleName,
										functionCode);
						if (permissionEntity != null)
							return true;
					}
				}
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		return false;
	}

	@Override
	public void grantPermissions(PermissionDTO permissionDTO)
			throws BusinessException {

		try {
			String roleName = permissionDTO.getRoleName();
			if (StringUtils.isEmpty(roleName))
				throw new BusinessException(BusinessErrorCode.ROLE_EMPTY);
			List<FunctionDTO> functionDTOList = permissionDTO.getFunctionList();

			// put new function code list into array list
			List<String> nFunctionCodeList = new ArrayList<String>();
			for (int j = 0; j < functionDTOList.size(); j++) {
				FunctionDTO functionDTO = functionDTOList.get(j);
				nFunctionCodeList.add(functionDTO.getFunctionCode());
			}

			// put old function code list into array list
			RoleEntity roleEntity = roleDAO.getRoleByName(roleName);
			List<PermissionEntity> permissionEntityList = roleEntity
					.getPermissionEntityList();
			roleEntity.setPermissionEntityList(null);
			List<String> oFunctionCodeList = new ArrayList<String>();
			for (int i = 0; i < permissionEntityList.size(); i++) {
				PermissionEntity permissionEntity = permissionEntityList.get(i);
				oFunctionCodeList.add(permissionEntity.getFunctionEntity()
						.getCode());
			}

			// 1. Delete old function list
			for (Iterator<PermissionEntity> iter = permissionEntityList
					.iterator(); iter.hasNext();) {
				PermissionEntity permissiontity = iter.next();
				String functionCode = permissiontity.getFunctionEntity()
						.getCode();
				if (!nFunctionCodeList.contains(functionCode))
					permissionDAO.deletePermission(permissiontity);
			}
			
			String loginId = adminService.getAdminProfile().getId();
			// 2. Add new function list
			for (int j = 0; j < functionDTOList.size(); j++) {
				FunctionDTO functionDTO = functionDTOList.get(j);
				String functionCode = functionDTO.getFunctionCode();
				if (!oFunctionCodeList.contains(functionCode)) {
					FunctionEntity functionEntity = functionDAO
							.getFunctionByCode(functionCode);
					PermissionEntity permissionEntity = new PermissionEntity();
					permissionEntity.setFunctionEntity(functionEntity);
					permissionEntity.setRoleEntity(roleEntity);
					permissionEntity.setCreateBy(loginId);
					permissionEntity.setUpdateBy(loginId);
					permissionDAO.addPermission(permissionEntity);
				}
			}

		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		} catch (BusinessException e) {
			throw e;
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}

	}

	@Override
	public List<PermissionDTO> getAllPermissions() throws BusinessException {
		List<PermissionDTO> permissionDTOList = new ArrayList<PermissionDTO>();
		try {
			List<RoleEntity> roleEntityList = roleDAO.getRoles();
			for (Iterator<RoleEntity> iter = roleEntityList.iterator(); iter
					.hasNext();) {
				RoleEntity roleEntity = iter.next();
				PermissionDTO permissionDTO = getPermissionsByRoleEntity(roleEntity);
				permissionDTOList.add(permissionDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		return permissionDTOList;
	}

	@Override
	public PermissionDTO getPermissionByRoleName(String roleName)
			throws BusinessException {
		PermissionDTO permissionDTO = new PermissionDTO();

		try {
			RoleEntity roleEntity = roleDAO.getRoleByName(roleName);
			permissionDTO = getPermissionsByRoleEntity(roleEntity);
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		return permissionDTO;
	}

	private PermissionDTO getPermissionsByRoleEntity(RoleEntity roleEntity)
			throws BusinessException {

		PermissionDTO permissionDTO = new PermissionDTO();
		try {
			List<FunctionDTO> functionDTOList = new ArrayList<FunctionDTO>();
			permissionDTO.setRoleName(roleEntity.getName());
			permissionDTO.setRoleDesc(roleEntity.getDesc());
			permissionDTO.setCreationDate(roleEntity.getCreateDate());
			String functionSummary = "";
			List<PermissionEntity> permissionList = roleEntity
					.getPermissionEntityList();
			if (permissionList != null) {
				for (Iterator<PermissionEntity> iter1 = permissionList
						.iterator(); iter1.hasNext();) {
					PermissionEntity permissionEntity = iter1.next();
					FunctionDTO functionDTO = new FunctionDTO();
					String applicationCode = permissionEntity
							.getFunctionEntity().getApplicationEntity()
							.getCode();
					String applicationName = permissionEntity
							.getFunctionEntity().getApplicationEntity()
							.getName();
					String rootApplication = permissionEntity
							.getFunctionEntity().getApplicationEntity()
							.getRootApplication();
					functionDTO.setFunctionCode(permissionEntity
							.getFunctionEntity().getCode());
					functionDTO.setFunctionName(permissionEntity
							.getFunctionEntity().getName());
					functionDTO.setApplicationCode(applicationCode);
					functionDTO.setApplicationName(applicationName);
					functionDTO.setRootApplication(rootApplication);
					functionDTOList.add(functionDTO);
					if (StringUtils.isEmpty(functionSummary))
						functionSummary = permissionEntity.getFunctionEntity()
								.getName();
					else
						functionSummary = functionSummary
								+ ","
								+ permissionEntity.getFunctionEntity()
										.getName();
				}
				permissionDTO.setFunctionSummary(functionSummary);
				permissionDTO.setFunctionList(functionDTOList);
			}
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		return permissionDTO;

	}

	@Override
	public List<FunctionDTO> getFunctions() throws BusinessException {

		List<FunctionDTO> functionDTOList = new ArrayList<FunctionDTO>();
		try {
			List<FunctionEntity> functionEntityList = functionDAO
					.getFunctions();
			for (Iterator<FunctionEntity> iter = functionEntityList.iterator(); iter
					.hasNext();) {
				FunctionEntity functionEntity = iter.next();
				FunctionDTO functionDTO = new FunctionDTO();
				functionDTO.setApplicationCode(functionEntity
						.getApplicationEntity().getCode());
				functionDTO.setApplicationName(functionEntity
						.getApplicationEntity().getName());
				functionDTO.setApplicationDesc(functionEntity
						.getApplicationEntity().getDesc());
				functionDTO.setFunctionCode(functionEntity.getCode());
				functionDTO.setFunctionName(functionEntity.getName());
				functionDTO.setFunctionDesc(functionEntity.getDesc());
				functionDTO.setRootApplication(functionEntity
						.getApplicationEntity().getRootApplication());
				functionDTOList.add(functionDTO);
			}
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}
		return functionDTOList;
	}

	@Override
	public List<FunctionDTO> getFunctions(AdminDTO adminDTO)
			throws BusinessException {
		try {
			List<FunctionDTO> functionDTOList = new ArrayList<FunctionDTO>();

			AdminEntity adminEntity = adminDAO.getAdminById(adminDTO.getId());
			if (adminEntity != null) {
				List<AdminRoleEntity> adminRoleList = adminEntity
						.getAdminRoleList();
				if (adminRoleList != null) {
					for (Iterator<AdminRoleEntity> iter = adminRoleList
							.iterator(); iter.hasNext();) {
						AdminRoleEntity userRoleEntity = iter.next();
						RoleEntity roleEntity = userRoleEntity.getRoleEntity();
						List<PermissionEntity> permissionList = roleEntity
								.getPermissionEntityList();
						if (permissionList != null) {
							for (Iterator<PermissionEntity> iter1 = permissionList
									.iterator(); iter1.hasNext();) {
								PermissionEntity permissionEntity = iter1
										.next();
								FunctionDTO functionDTO = new FunctionDTO();
								String applicationCode = permissionEntity
										.getFunctionEntity()
										.getApplicationEntity().getCode();
								String applicationName = permissionEntity
										.getFunctionEntity()
										.getApplicationEntity().getName();
								functionDTO.setFunctionCode(permissionEntity
										.getFunctionEntity().getCode());
								functionDTO.setFunctionName(permissionEntity
										.getFunctionEntity().getName());
								functionDTO.setApplicationCode(applicationCode);
								functionDTO.setApplicationName(applicationName);
								functionDTO.setRootApplication(permissionEntity
										.getFunctionEntity()
										.getApplicationEntity()
										.getRootApplication());
								functionDTOList.add(functionDTO);

							}
						}
					}
				}
			}

			return functionDTOList;
		} catch (DAOException e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		} catch (Exception e) {
			throw new BusinessException(BusinessErrorCode.COMMON_SYSTEM_ERROR,
					e);
		}

	}

}
