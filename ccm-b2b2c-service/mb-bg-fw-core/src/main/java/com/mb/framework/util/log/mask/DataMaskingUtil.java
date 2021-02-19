package com.mb.framework.util.log.mask;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * DataMaskingUtil class.
 * 
 */
@Component
public class DataMaskingUtil {
	@Autowired
	private DataMaskingConfiguration config;
	
	/**
	 * Default Constructor
	 */
	public DataMaskingUtil(){
	}
  
  /**
   * Removing all the Data Privacy values from the actual log entry and return masked String
   * log entry.
   * 
   * @param strClassSimpleName - Name of MaskData class to match
   * @param strActualLogEntry - Actual Log entry.
   *  
   * @return masked String - Masked Log entry
   */
	public String getMaskedLogEntry(String strClassSimpleName, String strActualLogEntry) {
		
		String actualTagList [] = null;
		String maskedTagList [] = null;
		try {			
			//check the interface name 
			if(config.getInterfaceList().contains(strClassSimpleName)) {			
				actualTagList = (config.getActualTagList()).split(",");
				maskedTagList = (config.getMaskedTagList()).split(",");
			 
				Matcher matcher = null;
				StringBuffer sb = null;
			  
				for(int i = 0; i < actualTagList.length; i++) {
					sb = new StringBuffer();
					Pattern p = Pattern.compile(actualTagList[i]);
					matcher = p.matcher(strActualLogEntry);
				  
					while(matcher.find()) {
						matcher.appendReplacement(sb, maskedTagList[i]);
					}
					sb = matcher.appendTail(sb);
					strActualLogEntry = sb.toString();
				}
				//return masked log entry
				return sb.toString();
			}
			else {
				return strActualLogEntry;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			//don't remove this; its mandatory to return actualLogEntry in case of any exception 
			return strActualLogEntry;
		}
	}
	
	/**
	 * 
	 * This method is used to return masked data 
	 * @param data
	 * @return
	 */
	public String maskData(String data) {
		
		if(!config.isMaskenable()) return data; 

		String actualTagArray[] = null;
		String maskedTagArray[] = null;
		try {
			actualTagArray = config.getActualTagList().split(",");
			maskedTagArray = config.getMaskedTagList().split(",");

			Matcher matcher = null;
			StringBuffer sb = null;

			for (int i = 0; i < actualTagArray.length; i++) {
				sb = new StringBuffer();
				Pattern p = Pattern.compile(actualTagArray[i]);
				matcher = p.matcher(data);

				while (matcher.find()) {
					matcher.appendReplacement(sb, maskedTagArray[i]);
				}
				sb = matcher.appendTail(sb);
				data = sb.toString();
			}
			// return masked log entry
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			// don't remove this; its mandatory to return actualLogEntry in case
			// of any exception
			return data;
		}
	}
	
	public String jsonMaskData(String data) {
		
		if(!config.isMaskenable()) return data; 

		String jsonActualTagArray[] = null;
		String josnMaskedTagArray[] = null;
		try {
			jsonActualTagArray = config.getJsonTagList().split(";");
			josnMaskedTagArray = config.getJsonMaskList().split(";");

			Matcher matcher = null;
			StringBuffer sb = null;

			for (int i = 0; i < jsonActualTagArray.length; i++) {
				sb = new StringBuffer();
				Pattern p = Pattern.compile(jsonActualTagArray[i]);
				matcher = p.matcher(data);

				while (matcher.find()) {
					matcher.appendReplacement(sb, josnMaskedTagArray[i]);
				}
				sb = matcher.appendTail(sb);
				data = sb.toString();
			}
			// return masked log entry
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			// don't remove this; its mandatory to return actualLogEntry in case
			// of any exception
			return data;
		}
	}
	
}
