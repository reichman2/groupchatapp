package me.brianr.groupchat.login;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import me.brianr.groupchat.GroupChat;
import me.brianr.groupchat.users.Role;
import me.brianr.groupchat.users.User;

/**
 * A class to Verify the login attempt.
 * @author Brian Reich
 * @version 1.0
 * @since 1.0
 */
public class LoginVerifier {
	
	/**
	 * This class should not be instantiated.
	 */
	private LoginVerifier() {
		
	}
	
	
	/**
	 * Login a user.
	 * @param username the username of the user to check.
	 * @param password the password of the user to check.
	 * @return the user if it exists in the database on the server.
	 */
	public static User login(final String username, final String password) {
		String hostAddr = GroupChat.getInstance().getConfig().getServerAddress();
		
		try {
			String urlParameters = "username=" + URLEncoder.encode(username, "UTF-8") + "&password=" + URLEncoder.encode(password, "UTF-8");
			String response = executePost("http://" + hostAddr + "/verify.php", urlParameters);
			response = response.trim();
			
			if (response.equals("") || response.equals("null") || response == null) {
				return null;
			}
			
			
			String[] userInfo = response.split(",");
			
			return new User(userInfo[1].replaceAll("name=", ""), userInfo[2].replaceAll("displayName=", ""), Integer.parseInt(userInfo[0].replaceAll("id=", "")), Role.valueOf(userInfo[3].replaceAll("role=", "")));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Execute an HTTP POST request to the given url.
	 * @param targetURL the url to send the request to.
	 * @param urlParameters the message parameters to send to the targetURL.
	 * @return the HTTP response.
	 */
	public static String executePost(String targetURL, String urlParameters) {
		// This message is to communicate with the verify page running on the server.
		
		HttpURLConnection connection = null;
		try {
			String ln;
			URL url = new URL(targetURL);
			
			connection = (HttpURLConnection) url.openConnection(); // Open a connection to the targetURL.
			connection.setRequestMethod("POST"); // Use the "POST" HTTP request method because we aren't getting information from the address bar (using the "GET" method).
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // Set the type of the content being sent to an encoded form.
			connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length)); // Set the content length property to the length of the content ;p.
			connection.setRequestProperty("Content-Language", "en-US"); // Set the content language property to US English
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.writeBytes(urlParameters);
			out.close(); // Flush and close the output stream.
			
			DataInputStream in = new DataInputStream(connection.getInputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			
			StringBuilder response = new StringBuilder();
			
			while ((ln = reader.readLine()) != null) {
				response.append(ln);
			}
			
			String str = response.toString();
			return str;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (connection != null) {
				// Disconnect when finished if the connection isn't null.
				connection.disconnect();
			}
		}
	}
	
	
	// TESTER
	public static void main(String[] args) {
		System.out.println(login("user", "password"));
	}
}
