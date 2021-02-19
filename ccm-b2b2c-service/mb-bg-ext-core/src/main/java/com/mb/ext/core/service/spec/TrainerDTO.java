
package com.mb.ext.core.service.spec;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mb.ext.core.util.JsonDateSerializer;
import com.mb.framework.service.spec.AbstractBaseDTO;
@JsonInclude(Include.NON_NULL)
public class TrainerDTO extends AbstractBaseDTO
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1370446594187372075L;

	private String trainerUuid;
	
	private TrainerDTO parentTrainer;
	
	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	private String trainerLevel;
	
	private int startIndex;
	
	private int pageSize;
	
	public String getTrainerUuid() {
		return trainerUuid;
	}

	public void setTrainerUuid(String trainerUuid) {
		this.trainerUuid = trainerUuid;
	}

	public TrainerDTO getParentTrainer() {
		return parentTrainer;
	}

	public void setParentTrainer(TrainerDTO parentTrainer) {
		this.parentTrainer = parentTrainer;
	}

	public String getTrainerLevel() {
		return trainerLevel;
	}

	public void setTrainerLevel(String trainerLevel) {
		this.trainerLevel = trainerLevel;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd") 
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	@JsonSerialize(using=JsonDateSerializer.class) 
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public List<UserDTO> getTraineeList() {
		return traineeList;
	}

	public void setTraineeList(List<UserDTO> traineeList) {
		this.traineeList = traineeList;
	}

	private UserDTO userDTO;
	
	private List<UserDTO> traineeList;
	
	private Date effectiveDate;
	
	public String getId() {
		String id = "";
		if(userDTO != null) {
			id = userDTO.getId();
		}
		return id;
	}
	public void setId(String id) {
		
	}
	public String getName() {
		String name = "";
		if(userDTO != null) {
			name = userDTO.getName();
		}
		return name;
	}
	public void setName(String name) {
		
	}
	
	public String getParentTrainerName() {
		String parentTrainerName = "";
		if(parentTrainer != null && parentTrainer.getUserDTO() != null) {
			parentTrainerName = parentTrainer.getUserDTO().getName();
		}
		return parentTrainerName;
	}
	public void setParentTrainerName(String parentTrainerName) {
		
	}
	
	public String getPersonalPhone() {
		String personalPhone = "";
		if(userDTO != null) {
			personalPhone = userDTO.getPersonalPhone();
		}
		return personalPhone;
	}
	public void setPeronalPhone(String personalPhone) {
		
	}
	
	public String getPersonalPhoneCountryCode() {
		String personalPhoneCountryCode = "";
		if(userDTO != null) {
			personalPhoneCountryCode = userDTO.getPersonalPhoneCountryCode();
		}
		return personalPhoneCountryCode;
	}
	public void setPeronalPhoneCountryCode(String personalPhoneCountryCode) {
		
	}
	
	public String getIdCardNo() {
		String idCardNo = "";
		if(userDTO != null) {
			idCardNo = userDTO.getIdCardNo();
		}
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		
	}
}
