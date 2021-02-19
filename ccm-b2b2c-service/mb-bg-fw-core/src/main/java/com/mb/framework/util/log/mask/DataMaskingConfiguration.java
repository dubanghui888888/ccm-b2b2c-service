package com.mb.framework.util.log.mask;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mb.framework.util.property.PropertyRepository;

/**
 * DataMaskingConfiguration class.
 *
 */
@Component
public class DataMaskingConfiguration {

	@Autowired
	private PropertyRepository repository;
	
   /**
    * list of intefaces.
    */
   private List<String> interfaceList;
   
   /**
    * String holds actaul tag list.
    */
   private String actualTagList;   
   
   /**
    * String holds masked tag list.
    */
   private String maskedTagList;
   
   /**
    * String holds actaul json tag list.
    */
   private String jsonTagList;
   
   /**
    * String holds actaul json tag list.
    */
   private String jsonMaskList;
   
   /**
    * boolean maskon
    */
   private boolean maskenable;
   
   
   /**
    *  Default Constructor
    */
   private DataMaskingConfiguration() {
   }
   
   /**
    * Method will return the List of Interfaces.
    * 
    * @return interfacesList - List
    */
	public List<String> getInterfaceList() {
		if (interfaceList == null) {
			String strInterfaceList = repository.getProperty("interfaceList");
			if (strInterfaceList != null) {
				String array[] = strInterfaceList.split(",");
				// initialize List instance
				interfaceList = new ArrayList<String>();

				for (int i = 0; i < array.length; i++) {
					interfaceList.add(array[i]);
				}
			}
		}
		return interfaceList;
	}
   
   /**
    * Getter method for actualTagList.
    * 
    * @return actualTagList - String
    */
	public String getActualTagList() {
		if (actualTagList == null) {
			actualTagList = repository.getProperty("actualTagList");
		}
		return actualTagList;
	}
   
   /**
    * Getter method for maskedTagList.
    * 
    * @return maskedTagList - String
    */
	public String getMaskedTagList() {
		if (maskedTagList == null) {
			maskedTagList = repository.getProperty("maskedTagList");
		}
		return maskedTagList;
	}

	/**
	 * @return the jsonTagList
	 */
	public String getJsonTagList() {
		if (jsonTagList == null) {
			jsonTagList = repository.getProperty("jsonTagList");
		}
		return jsonTagList;
	}
	
	/**
	 * @return the jsonMaskList
	 */
	public String getJsonMaskList() {
		if (jsonMaskList == null) {
			jsonMaskList = repository.getProperty("jsonMaskList");
		}
		return jsonMaskList;
	}

	/**
	 * @return the maskenable
	 */
	public boolean isMaskenable() {
		maskenable = false;
		if(repository.getProperty("mask.on")!=null 
				&& "true".equalsIgnoreCase(repository.getProperty("mask.on"))){
			maskenable = true;
		}
		return maskenable;
	}

	/**
	 * @param maskenable the maskenable to set
	 */
	public void setMaskenable(boolean maskenable) {
		this.maskenable = maskenable;
	}

}
