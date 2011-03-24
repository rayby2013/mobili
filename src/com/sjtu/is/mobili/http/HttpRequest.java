package com.sjtu.is.mobili.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

import android.util.Log;



public class HttpRequest  {
	
	public HttpRequest(){
		try{
			CookieManager.initilize();
		}catch( Exception e){
			Log.d("http", e.toString());
		}
	}
	
	
	/**
	* Sends an HTTP GET request to a url
	*
	* @param endpoint - The URL of the server. (Example: " http://www.yahoo.com/search")
	* @param requestParameters - all the request parameters (Example: "param1=val1&param2=val2"). Note: This method will add the question mark (?) to the request - DO NOT add it yourself
	* @return - The response from the end point
	*/
	public String sendGetRequest(String endpoint, String requestParameters)
	{
		String result = null;
		if (endpoint.startsWith("http://"))
		{
			// Send a GET request to the servlet
			try
			{
				// Send data
				String urlStr = endpoint;
				if (requestParameters != null && requestParameters.length () > 0)
				{
					urlStr += "?" + requestParameters;
				}
				URL url = new URL(urlStr);
				URLConnection conn = url.openConnection ();
				// Get the response
				BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuffer sb = new StringBuffer();
				String line;
				while ((line = rd.readLine()) != null)
				{
					sb.append(line);
				}
				rd.close();
				result = sb.toString();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}
	
	
	/**
	* Reads data from the data reader and posts it to a server via POST request.
	* @param data - The data you want to send
	* @param endpoint - The server's address
	* @param output - writes the server's response to output
	* @throws Exception
	*/
	@SuppressWarnings("finally")
	public String postData(String data,
						   URL endpoint) throws Exception
	{
		boolean is_ssl = false;
		if (!endpoint.toString().startsWith("http://")) is_ssl = true;
		
		Log.v("http", "is ssl:"+ new Boolean(is_ssl).toString() );
		HttpURLConnection urlc = null;
		Writer output = new StringWriter();
		try
		{
			if (!is_ssl) urlc = (HttpURLConnection) endpoint.openConnection();
			else urlc = (HttpsURLConnection) endpoint.openConnection();
			try
			{
				urlc.setRequestMethod("POST");
				HttpURLConnection.setFollowRedirects(true); 
			} catch (ProtocolException e)
			{
				throw new Exception(
						"Shouldn't happen: HttpURLConnection doesn't support POST??",
						e);
			}
			urlc.setDoOutput(true);
			urlc.setDoInput(true);
			urlc.setUseCaches(true);
			urlc.setAllowUserInteraction(false);
			//urlc.setRequestProperty("Content-length",String.valueOf (data.length())); 
			urlc.setRequestProperty("Content-type", 
									"application/x-www-form-urlencoded");
			
			OutputStream out = urlc.getOutputStream();
			try
			{
				
				DataOutputStream data_s = new DataOutputStream( urlc.getOutputStream() ); 
				data_s.writeBytes(data);
				Log.v("http", "post data");
				data_s.close();
				
			} catch (IOException e)
			{
				throw new Exception("IOException while posting data", 
									e);
			} finally
			{
				if (out != null)
					out.close();
			}
			InputStream in = urlc.getInputStream();
			try
			{
				Reader reader = new InputStreamReader(in);
				pipe(reader, output);
				reader.close();
				Log.v("http", urlc.getHeaderField("set-cookie"));
				
			} catch (IOException e)
			{
				throw new Exception(
						"IOException while reading response",
						e
				);
			} finally
			{
				if (in != null)
					in.close();
			}
		} catch (IOException e)
		{
			throw new Exception(
					"Connection error (is server running at " +
					endpoint + " ?): " + e);
		} finally
		{
			if (urlc != null)
				Log.v("http", "urlc is not null");
				urlc.disconnect();
				return output.toString();
		}
	}

	private static void pipe(Reader reader, Writer output) 
	throws IOException
	{
		char[] buf = new char[1024];
		int read = 0;
		while ((read = reader.read(buf)) >= 0)
		{
			output.write(buf, 0, read);
		}
		output.flush();
		
	}
}
