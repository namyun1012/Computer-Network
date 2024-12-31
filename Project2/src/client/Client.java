package client;

import java.awt.Frame;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Panel;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.sql.Savepoint;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Client {
	
	
	
	
	public static void main(String[] args) throws Exception{
		Scanner keyboard = new Scanner(System.in);
		
		String urlString = keyboard.nextLine();
		
		String charset = "UTF-8";
		int timeout = 5000;
		String data = keyboard.nextLine();
		int func = keyboard.nextInt(); // 0 get 1 post 2 GUI
		
		System.out.println("url: " + urlString);
		System.out.println("data " + data);
		System.out.println("func: " + func);
		
		String result = "";
		
		if(func == 0) { // 0 - GET else - POST
			result = getWebContentByGet(urlString, charset, timeout);
			System.out.println(result);
		} else if(func == 1) {
			result = getWebContentByPost(urlString, data, charset, timeout);
			System.out.println(result);
		} else if(func == 2) { // GUI 
			saveImage(urlString);

		} else {
			System.out.println("Nothing");
		}
		
		
		keyboard.close();
	}
	
	public static String getWebContentByGet(String urlString, final String charset, int timeout) throws IOException {
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
	
	public static String getWebContentByPost(String urlString, String data, final String charset, int timeout) throws IOException {
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
		conn.setRequestProperty("Accept", "text/xml");
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
	
	public static void saveImage(String urlString) throws IOException {
		String destination = "src/client/image.jpg";
		
		URL url = new URL(urlString);
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(destination);
		
		byte[] b = new byte[2048];
		int length;
		
		Image image = ImageIO.read(url);
		JFrame frame = new JFrame();
		frame.setSize(300, 300);
		JLabel label = new JLabel(new ImageIcon(image));
		frame.add(label);
		frame.setVisible(true);
		
		while((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}
		
		is.close();
		os.close();
		
		System.out.println("image.jpg created" );
	}
	
	
	
}
