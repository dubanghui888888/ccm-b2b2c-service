package com.mb.ext.rmi.util;

import java.util.Date;
import java.util.Map;

import com.mb.framework.util.log.LogHelper;

public class GenerateDailyUsgaeReport extends UtilService {

	private final LogHelper logger = LogHelper.getInstance(this.getClass()
			.getName());

	
	private final String SEPERATOR = "|";
	
	
	
	public boolean process(Map<String, Object> data) {
		logger.debug("parameters : "+data.size());
		Boolean result = false;
		try {
			
			
			
			if(null!=data){
				String filePath = (String)data.get("key1");
				
				String startDateStr = (String)data.get("key2");
				String endDateStr =  (String)data.get("key3");
				Date startDate = null;
				Date endDate = null;
				
				if(null!=startDateStr && !"".equals(startDateStr.trim())){
					startDate = retrieveDate(startDateStr);
				}
				
				if(null!=endDateStr && !"".equals(endDateStr.trim())){
					endDate = retrieveDate(endDateStr);
				}
				
				startDate = getStartDate(startDate);
				endDate = getEndDate(endDate);
				
//				List<AccessInfoEntity> entities = accessInfoService.getAccessInfo(startDate, endDate);
//				result = generateReport(filePath, entities);
				
			}
		
		} catch (Exception e) {
			logger.debug("Error in fetching daily usgae report :"+e);
			return result;
		}
		return result;
	}
	

}
