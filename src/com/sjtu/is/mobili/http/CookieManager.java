package com.sjtu.is.mobili.http;

import java.net.CookieHandler;

import android.util.Log;

public class CookieManager {
	
	private static boolean created = false;
	private static ListCookieHandler lch = null;
	
	public static void initilize() throws Exception {
		if (!created){
			lch = new ListCookieHandler();
			CookieHandler.setDefault(lch);
			created = true;
			Log.v("cookie", "initlized cookie");
		} else {
			//CookieHandler.setDefault(lch);
			Log.v("cookie", "with cookie");
		}
	}

	public static boolean isCreated() {
		return created;
	}
	
}
