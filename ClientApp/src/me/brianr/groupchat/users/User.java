package me.brianr.groupchat.users;

import java.awt.Color;
import java.io.Serializable;

import me.brianr.groupchat.chat.Message;

/**
 * This class represents a user connected to the server.
 * @author reich
 * @version 1.0
 * @since 1.0
 */
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7323464842230723088L;
	
	protected String name;
	protected String displayName;
	protected long id;
	protected Role role;
	protected UserState state;
	protected Color color;
//	protected Socket socket;
//	protected boolean isLoggedIn;
	
	
	/**
	 * Construct a user.
	 * @param name the name of the user.
	 * @param displayName the display name of the user.
	 * @param id the id of the user.
	 * @param role the role of the user.
	 */
	public User(String name, String displayName, long id, Role role) {
		this.name = name;
		this.displayName = displayName;
		this.id = id;
		this.role = role;
		this.state = UserState.ONLINE;
	}
	
	/**
	 * Construct a user.
	 * @param name the name of the user.
	 * @param displayName the display name of the user.
	 * @param role the role of the user.
	 */
	public User(String name, String displayName, Role role) {
		this.name = name;
		this.displayName = displayName;
		this.role = role;
		this.id = -1;
		this.state = UserState.ONLINE;
	}
	
	
	/**
	 * 
	 * @param message
	 * @return
	 */
	public boolean sendMessage(Message message) {
		// TODO send message.
		return false;
	}
	
	/**
	 * Logs the user out.
	 * @return true if the log out was successful, otherwise returns false.
	 */
	public boolean logout() {
		
		return false;
	}


	/**
	 * Get the name of the user.
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the displayname of the user.
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Get the id of the user.
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Get the role of the user.
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}
	
	/**
	 * Get the state of the user.
	 * @return the user's state.
	 */
	public UserState getUserState() {
		return this.state;
	}
	
	
	@Override
	public String toString() {
		String str = "id: " + id + ", name: " + name + ", displayName: " + displayName + ", role: " + role.toString().replaceAll("[\\[\\]]", "");
		
		return str;
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof User))
			return false;
		
		User otherUser = (User) other;
		
		return this.name.equals(otherUser.getName()) &&
				   this.displayName.equals(otherUser.displayName) &&
				   this.id == otherUser.id &&
				   this.role == otherUser.role;
	}
}
