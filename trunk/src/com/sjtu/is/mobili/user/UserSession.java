package com.sjtu.is.mobili.user;

public class UserSession {

	private static boolean login = false;
	
	private static String userName = "";
	private static String userId = "";
	
	public UserSession(String un, String ui){
		userName = un;
		userId = ui;
	}

	public static boolean isLogin() {
		return login;
	}

	public static void setLogin(boolean login) {
		UserSession.login = login;
	}
	
	public static String getUserName() {
		return userName;
	}

	public static String getUserId() {
		return userId;
	}
	
}
