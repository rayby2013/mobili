package com.sjtu.is.mobili.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;



public class HttpRequest  {
	
	private static DefaultHttpClient httpclient = null;
	
	public HttpRequest(){
		
		if (httpclient==null){
			httpclient = new DefaultHttpClient();
		}
	}
	
	
	
	public String sendGetRequest(String endpoint, String requestParameters)
			throws Exception {

		HttpGet httpget = new HttpGet(endpoint + "?" + requestParameters);
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		return convertStreamToString(entity.getContent());

	}
	
	

	public String postData(String endpoint, List<NameValuePair> data) throws Exception
	{
		HttpPost httpost = new HttpPost(endpoint);
		httpost.setEntity(new UrlEncodedFormEntity(data, HTTP.UTF_8));
		
		HttpResponse response = httpclient.execute(httpost);
		HttpEntity entity = response.getEntity();
		return convertStreamToString(entity.getContent());
		
	}
	
	private String convertStreamToString(InputStream is) throws IOException {
		/*
		 * To convert the InputStream to String we use the Reader.read(char[]
		 * buffer) method. We iterate until the Reader return -1 which means
		 * there's no more data to read. We use the StringWriter class to
		 * produce the string.
		 */
		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is,
						"UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {
			return "";
		}
	}
}
