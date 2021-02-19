package com.mb.ext.core.context;

import java.util.HashMap;

public class UserContext {

	private static final ThreadLocal<HashMap<String, Object>> threadLocal = new ThreadLocal<HashMap<String, Object>>();
	
	public static void set(String key, Object value){
		if(threadLocal.get() == null){
			HashMap<String, Object> map = new HashMap<String, Object>();
			threadLocal.set(map);
		}
		threadLocal.get().put(key, value);
	}
	
	public static Object get(String Key){
		if(threadLocal.get() == null){
			HashMap<String, Object> map = new HashMap<String, Object>();
			threadLocal.set(map);
		}
		return threadLocal.get().get(Key);
	}
	
}
