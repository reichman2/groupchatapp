package me.brianr.groupchat.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import me.brianr.groupchat.filemanager.FileManager;
import me.brianr.groupchat.users.User;

/**
 * This class represents a room of users.
 * <p>
 * Rooms are used to separate users and their chat messages when there are multiple rooms.
 * Currently, having multiple rooms is not implemented.
 * </p>
 * @author Brian Reich
 * @version 1.0
 * @since 1.0
 */
public class Room {
	
	private String name;
	private final ArrayList<User> users;
	private int id;
	private Logger log;
	private volatile FileManager fm;
	
	
	/**
	 * 
	 * @param name
	 * @param id
	 */
	public Room(String name, int id) {
		this.name = name;
		this.id = id;
		this.users = new ArrayList<User>();
		
		this.fm = new FileManager("");
	}
	
	
	/**
	 * Add a user to the room.
	 * @param user the user to add.
	 */
	public void addUser(User user) {
		if (!users.contains(user)) {
			users.add(user);
		}
	}
	
	/**
	 * Remove the user from the room.
	 * @param user
	 */
	public void removeUser(User user) {
		for (User u : users) {
			if (u.equals(user)) {
				users.remove(u);
			}
		}
	}
	
	/**
	 * Add a user the unsafe way.  Doesn't check if the user is already in the room.
	 * @param user the user to add.
	 */
	public void addUserUnsafe(User user) {
		users.add(user);
	}
	
	/**
	 * Remove a user in the unsafe way.
	 * @param user the user to remove.
	 */
	public void removeUserUnsafe(User user) {
		users.remove(user);
	}
	
//	public void addMessage(Message msg) {
//		
//	}
	
	
//	private void processCommand(Message msg) {
//		
//	}
	
	/**
	 * Get the room id.
	 * @return the room id.
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Get the name of the room.
	 * @return the name of the room.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Get the list of users in the room.
	 * @return the list of users in the room.
	 */
	public List<User> getUsers() {
		return this.users;
	}
}
