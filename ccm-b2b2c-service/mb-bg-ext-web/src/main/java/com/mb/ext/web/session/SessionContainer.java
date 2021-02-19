package com.mb.ext.web.session;

import java.util.ArrayList;
import java.util.List;

public class SessionContainer {

	private static List<String> activeSessionList = new ArrayList<String>();
	
	public static void addSession(String loginId){
		activeSessionList.add(loginId);
	}
	public static void removeSession(String loginId){
		activeSessionList.remove(loginId);
	}
	public static boolean isSessionValid(String loginId){
		return activeSessionList.contains(loginId);
	}
	
}
