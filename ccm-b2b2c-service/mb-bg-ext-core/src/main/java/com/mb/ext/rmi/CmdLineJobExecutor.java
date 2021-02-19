package com.mb.ext.rmi;

import java.util.Map;

public interface CmdLineJobExecutor {

	public String executeJob(String processname, Map<String, Object> paramters);
}
