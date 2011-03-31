package com.sjtu.is.mobili.user;

import com.j256.ormlite.field.DatabaseField;

public class UserData {
	@DatabaseField(id = true)
	private String username;
	@DatabaseField
	private String userId;
	
	@DatabaseField
	private String password=null;
	
	UserData(){
	}
	
	public UserData(String un, String ui){
		username = un;
		userId = ui;
	}
	
	public UserData(String un, String ui, String pd){
		username = un;
		userId = ui;
		password = pd;
	}

	public String getUsername() {
		return username;
	}

	public String getUserId() {
		return userId;
	}
	
	public String getPassword() {
		return password;
	}
	
}
