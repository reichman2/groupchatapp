package me.brianr.groupchat.security;

import java.io.Serializable;

/**
 * Represents credentials used to log in as a registered user.
 * @author Brian Reich
 * @version 1.0
 * @since 1.0
 */
public class LoginCredentials implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6760412196036087310L;
	
	private String username;
	private String password;
	
	/**
	 * Construct an instance of this class.
	 * @param username the username.
	 * @param password the password.
	 */
	public LoginCredentials(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Get the username.
	 * @return the username.
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * Get the password.
	 * @return the password.
	 */
	public String getPassword() {
		return this.password;
	}
}
