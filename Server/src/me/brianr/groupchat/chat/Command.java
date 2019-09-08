package me.brianr.groupchat.chat;

import me.brianr.groupchat.users.User;

/**
 * A class that defines a command.
 * <p>
 * Commands are executed by users in chat by using the command character defined in the config.
 * By default, this character is '!'
 * </p>
 * @author reich
 * @version 1.0
 */
public class Command {
	
	private User sender;
	private String name;
	private String[] args;
	
	
	/**
	 * Construct a command.
	 * @param command the command.
	 * @param sender the sender of the command.
	 */
	public Command(final String command, final User sender) {
		String name = command.split(" ")[0];
		String[] args = command.substring(command.indexOf(" "), command.length())
				.split(" ");
		
		this.sender = sender;
		this.name = name;
		this.args = args;
	}
	
	/**
	 * Construct a command.
	 * @param msg the message to construct the command with.
	 */
	public Command(final Message msg) {
		String text = msg.getText();
		if (text.charAt(0) == '!') {
			
		}
		
		this.sender = msg.getSender();
	}
	
	
	/**
	 * Get the name of a command.
	 * @return the name of the command.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Get the arguments of the command.
	 * @return the arguments of the command.
	 */
	public String[] getArgs() {
		return this.args;
	}
	
	/**
	 * Get the command sender.
	 * @return the command sender.
	 */
	public User getSender() {
		return this.sender;
	}
	
}
