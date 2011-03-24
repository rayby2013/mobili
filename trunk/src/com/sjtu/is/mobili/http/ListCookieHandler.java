package com.sjtu.is.mobili.http;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ListCookieHandler extends CookieHandler {
	private List<Cookie> cookieJar = new LinkedList<Cookie>();

	public void put(URI uri, Map<String, List<String>> responseHeaders) throws IOException {
		List<String> setCookieList = responseHeaders.get("Set-Cookie");
		if (setCookieList != null) {
			for (String item : setCookieList) {
				Cookie cookie = new Cookie(uri, item);
				for (Cookie existingCookie : cookieJar) {
					if ((cookie.getURI().equals(existingCookie.getURI()))
							&& (cookie.getName().equals(existingCookie.getName()))) {
						cookieJar.remove(existingCookie);
						break;
					}
				}
				cookieJar.add(cookie);
			}
		}
	}

	public Map<String, List<String>> get(URI uri, Map<String, List<String>> requestHeaders)
	throws IOException {
		StringBuilder cookies = new StringBuilder();
		for (Cookie cookie : cookieJar) {
			// Remove cookies that have expired
			if (cookie.hasExpired()) {
				cookieJar.remove(cookie);
			} else if (cookie.matches(uri)) {
				if (cookies.length() > 0) {
					cookies.append(", ");
				}
				cookies.append(cookie.toString());
			}
		}

		Map<String, List<String>> cookieMap = new HashMap<String, List<String>>(requestHeaders);

		if (cookies.length() > 0) {
			List<String> list = Collections.singletonList(cookies.toString());
			cookieMap.put("Cookie", list);
		}
		System.out.println("CookieMap: " + cookieMap);
		return Collections.unmodifiableMap(cookieMap);
	}
}