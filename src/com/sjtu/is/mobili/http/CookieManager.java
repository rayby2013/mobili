package com.sjtu.is.mobili.http;

import java.net.CookieHandler;

import android.util.Log;

public class CookieManager {
	
	private static boolean created = false;
	
	public static void initilize() throws Exception {
		if (!created){
			CookieHandler.setDefault(new ListCookieHandler());
			created = true;
			Log.v("cookie", "initlized cookie");
		} else {
			Log.v("cookie", "with cookie");
		}
	}

	public static boolean isCreated() {
		return created;
	}
	
}
