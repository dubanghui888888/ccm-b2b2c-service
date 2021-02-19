package com.mb.ext.rmi;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.mb.framework.util.log.LogHelper;

public class CmdLineJobExecutorImpl implements CmdLineJobExecutor {
	
	private final LogHelper logger = LogHelper.getInstance(this.getClass().getName());
	
	private final Map<String, String> implementors= new HashMap<String, String>();
	
	public CmdLineJobExecutorImpl() {
		implementors.put("EP_DAILY_USGAE_REPORT", "com.mb.ext.rmi.util.GenerateDailyUsgaeReport");
	}
	

	@Override
	public String executeJob(String processname, Map<String, Object> paramters) {
		logger.info("calling the rmi service.."+processname + " with parameter ..." +paramters.size());
		
		if(implementors.containsKey(processname)){
			String classPath = implementors.get(processname);
			try {
				Class theClass = Class.forName(classPath);
//				
//				UtilService processor;
//				
//					processor = (UtilService) theClass.getDeclaredConstructor(AccessInfoService.class).newInstance(accessInfoService);
//				 
//				
//				Boolean result = processor.process(paramters);
				return "";
			} catch (ClassNotFoundException e1) {
				logger.debug("class not found exception .."+e1);
				return "false";
			}catch (Exception e) {
				logger.debug("exception .."+e);
				return "false";
			}
			
		}else{
			return "false";
		}
		
	}

}
