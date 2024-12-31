package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest {
	
	String urlString;
	String charset;
	int timeout;
	String data;
	
	
	public HttpRequest(String urlString) {
		this.urlString = urlString;
		this.charset = "UTF-8";
		this.timeout = 5000;
		this.data = "";
	}
	
	
	public String getWebContentByGet(String urlString, final String charset, int timeout) throws IOException {
		System.out.println("GET Method Executed");
		
		if(urlString == null || urlString.length() == 0) {
			return null;
		}
		
		urlString = (urlString.startsWith("http://") || urlString.startsWith("https://")) ? urlString
				: ("http://" + urlString).intern();
		
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		
		conn.setRequestProperty("User-Agent", "2020030819/NAMYUNKIM/Client/COMPUTERNETWORK"); // Mission 1
		// conn.setRequestProperty("Accept", "text/html");
		conn.setConnectTimeout(timeout);
		
		try {
			if(conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		InputStream input = conn.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input, charset));
		String line = null;
		StringBuffer sb = new StringBuffer();
		while((line = reader.readLine()) != null) {
			sb.append(line).append("\r\n");	
		}
		
		if(reader != null) {
			reader.close();
		}
		
		if(conn != null) {
			conn.disconnect();
		}
		
		return sb.toString();
	}
	
	public String getWebContentByPost(String urlString, String data, final String charset, int timeout) throws IOException {
		System.out.println("POST Method Executed");
		
		if(urlString == null || urlString.length() == 0) {
			return null;
		}
		
		urlString = (urlString.startsWith("http://") || urlString.startsWith("https://")) ? urlString
				: ("http://" + urlString).intern();
		
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod("POST");
		conn.setUseCaches(false);
		conn.setInstanceFollowRedirects(true);
		
		conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
		conn.setRequestProperty("User-Agent", "2020030819/NAMYUNKIM/Client/COMPUTERNETWORK");
		conn.setRequestProperty("Accept", "text/html");
		conn.setConnectTimeout(timeout);
		conn.connect();
		
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		byte[] content = data.getBytes("UTF-8");
		
		out.write(content);
		out.flush();
		out.close();
		try {
			if(conn.getResponseCode()!= HttpURLConnection.HTTP_OK) {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		
		InputStream stream = conn.getInputStream();
		if(stream == null) return null;
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset));
		String line = null;
		StringBuffer sb = new StringBuffer();
		
		while((line = reader.readLine()) != null) {
			sb.append(line).append("\r\n");	
		}
		
		if(reader != null) {
			reader.close();
		}
		
		if(conn != null) {
			conn.disconnect();
		}
		return sb.toString();
	}
}
