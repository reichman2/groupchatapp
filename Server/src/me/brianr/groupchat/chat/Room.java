package me.brianr.groupchat.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import me.brianr.groupchat.users.User;

public class Room {
	public static final String CMD_IDENTIFIER = "!";
	
	private String name;
	private final ArrayList<User> users;
	private int id;
	private Logger log;
	
	
	public Room(String name, int id) {
		this.name = name;
		this.id = id;
		this.users = new ArrayList<User>();
	}
	
	
	public void addUser(User user) {
		users.add(user);
	}
	
	public void removeUser(User user) {
		for (User u : users) {
			if (u.equals(user)) {
				users.remove(u);
			}
		}
	}
	
	public void addUserUnsafe(User user) {
		users.add(user);
	}
	
	public void removeUserUnsafe(User user) {
		users.remove(user);
	}
	
	public void addMessage(Message msg) {
		
	}
	
	
	private void processCommand(Message msg) {
		
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public List<User> getUsers() {
		return this.users;
	}
}
